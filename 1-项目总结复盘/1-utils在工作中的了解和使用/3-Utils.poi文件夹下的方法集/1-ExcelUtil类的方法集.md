ExcelUtil工具类(Excel业务工具类的集合)


Excel相关处理方法


  public class ExcelUtil<T> {

  private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

  public static final String FORMULA_REGEX_STR = "=|-|\\+|@";

  public static final String[] FORMULA_STR = { "=", "-", "+", "@" };

  /**
    * Excel sheet最大行数，默认65536
      */
      public static final int sheetSize = 65536;

  /**
    * 工作表名称
      */
      private String sheetName;

  /**
    * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
      */
      private Type type;

  /**
    * 工作薄对象
      */
      private Workbook wb;

  /**
    * 工作表对象
      */
      private Sheet sheet;

  /**
    * 样式列表
      */
      private Map<String, CellStyle> styles;

  /**
    * 导入导出数据列表
      */
      private List<T> list;

  /**
    * 注解列表
      */
      private List<Object[]> fields;

  /**
    * 当前行号
      */
      private int rowNum;

  /**
    * 标题
      */
      private String title;

  /**
    * 最大高度
      */
      private short maxHeight;

  /**
    * 统计列表
      */
      private Map<Integer, Double> statistics = new HashMap<Integer, Double>();

  /**
    * 数字格式
      */
      private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");

  /**
    * 实体对象
      */
      public Class<T> clazz;

  public ExcelUtil(Class<T> clazz) {
  this.clazz = clazz;
  }

  /**
    * 对list数据源将其里面的数据导入到excel表单
    * @param list 导出的数据集合
    * @param sheetName 工作表名称
    * @param filename excel文件名
    * @param path excel导出到的路径
    * @param type 导出类型
    * @param source 来源
    * @return
      */
      public FileIdVo exportExcel(List<T> list, String sheetName, String filename, String path, String type, String source) {
      FileIdVo vo = null;
      FileOutputStream fos = null;
      try {
      this.init(list, sheetName, StringUtils.EMPTY, Type.EXPORT);
      File newDir = new File(path);
      FileUtil.createDir(newDir);
      File newFile = new File(newDir, filename);
      // path = newFile.getAbsolutePath();
      fos = new FileOutputStream(newFile);
      writeSheet();
      wb.write(fos);
      vo = FileRecordUtils.saveTemp(newFile.getAbsolutePath(), type, source);
      } catch (Exception e) {
      log.error(String.format("导出数据到Excel失败, 异常信息如下:%n%s", e.getMessage()));
      throw BusinessErrorException.msg("err.export");
      } finally {
      FileUtil.close(wb);
      FileUtil.close(fos);
      }
      return vo;
      }

  public void init(List<T> list, String sheetName, String title, Type type) {
  if (list == null) {
  list = new ArrayList<T>();
  }
  this.list = list;
  this.sheetName = sheetName;
  this.type = type;
  this.title = title;
  createExcelField();
  createWorkbook();
  createTitle();
  }

  /**
    * 创建excel第一行标题
      */
      public void createTitle() {
      if (StringUtils.isNotEmpty(title)) {
      Row titleRow = sheet.createRow(rowNum == 0 ? rowNum++ : 0);
      titleRow.setHeightInPoints(30);
      Cell titleCell = titleRow.createCell(0);
      titleCell.setCellStyle(styles.get("title"));
      titleCell.setCellValue(title);
      sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(), this.fields.size() - 1));
      }
      }

  /**
    * 创建写入数据到Sheet
      */
      public void writeSheet() {
      // 取出一共有多少个sheet.
      int sheetNo = Math.max(1, (int) Math.ceil(list.size() * 1.0 / sheetSize));
      for (int index = 0; index < sheetNo; index++) {
      createSheet(sheetNo, index);
      // 产生一行
      Row row = sheet.createRow(rowNum);
      int column = 0;
      // 写入各个字段的列头名称
      for (Object[] os : fields) {
      Excel excel = (Excel) os[1];
      this.createCell(excel, row, column++);
      }
      if (Type.EXPORT.equals(type)) {
      fillExcelData(index, row);
      addStatisticsRow();
      }
      }
      }

  /**
    * 填充excel数据
    * @param index 序号
    * @param row 单元格行
      */
      public void fillExcelData(int index, Row row) {
      int startNo = index * sheetSize;
      int endNo = Math.min(startNo + sheetSize, list.size());
      for (int i = startNo; i < endNo; i++) {
      row = sheet.createRow(i + 1 + rowNum - startNo);
      // 得到导出对象.
      T vo = (T) list.get(i);
      int column = 0;
      for (Object[] os : fields) {
      Field field = (Field) os[0];
      Excel excel = (Excel) os[1];
      this.addCell(excel, row, vo, field, column++);
      }
      }
      }

  /**
    * 创建表格样式
    * @param wb 工作薄对象
    * @return 样式列表
      */
      private Map<String, CellStyle> createStyles(Workbook wb) {
      // 写入各条记录,每条记录对应excel表中的一行
      Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
      CellStyle style = wb.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      style.setVerticalAlignment(VerticalAlignment.CENTER);
      Font titleFont = wb.createFont();
      titleFont.setFontName("Arial");
      titleFont.setFontHeightInPoints((short) 16);
      titleFont.setBold(true);
      style.setFont(titleFont);
      styles.put("title", style);

      style = wb.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      style.setVerticalAlignment(VerticalAlignment.CENTER);
      style.setBorderRight(BorderStyle.THIN);
      style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      style.setBorderLeft(BorderStyle.THIN);
      style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      style.setBorderTop(BorderStyle.THIN);
      style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      style.setBorderBottom(BorderStyle.THIN);
      style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
      Font dataFont = wb.createFont();
      dataFont.setFontName("Arial");
      dataFont.setFontHeightInPoints((short) 10);
      style.setFont(dataFont);
      styles.put("data", style);

      style = wb.createCellStyle();
      style.cloneStyleFrom(styles.get("data"));
      style.setAlignment(HorizontalAlignment.CENTER);
      style.setVerticalAlignment(VerticalAlignment.CENTER);
      style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
      style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      Font headerFont = wb.createFont();
      headerFont.setFontName("Arial");
      headerFont.setFontHeightInPoints((short) 10);
      headerFont.setBold(true);
      headerFont.setColor(IndexedColors.WHITE.getIndex());
      style.setFont(headerFont);
      styles.put("header", style);

      style = wb.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      style.setVerticalAlignment(VerticalAlignment.CENTER);
      Font totalFont = wb.createFont();
      totalFont.setFontName("Arial");
      totalFont.setFontHeightInPoints((short) 10);
      style.setFont(totalFont);
      styles.put("total", style);

      style = wb.createCellStyle();
      style.cloneStyleFrom(styles.get("data"));
      style.setAlignment(HorizontalAlignment.LEFT);
      styles.put("data1", style);

      style = wb.createCellStyle();
      style.cloneStyleFrom(styles.get("data"));
      style.setAlignment(HorizontalAlignment.CENTER);
      styles.put("data2", style);

      style = wb.createCellStyle();
      style.cloneStyleFrom(styles.get("data"));
      style.setAlignment(HorizontalAlignment.RIGHT);
      styles.put("data3", style);

      return styles;
      }

  /**
    * 创建单元格
      */
      public Cell createCell(Excel attr, Row row, int column) {
      // 创建列
      Cell cell = row.createCell(column);
      // 写入列信息
      cell.setCellValue(attr.name());
      setDataValidation(attr, row, column);
      cell.setCellStyle(styles.get("header"));
      return cell;
      }

  /**
    * 设置单元格信息
    * @param value 单元格值
    * @param attr 注解相关
    * @param cell 单元格信息
      */
      public void setCellVo(Object value, Excel attr, Cell cell) {
      if (ColumnType.STRING == attr.cellType()) {
      String cellValue = Convert.toStr(value);
      // 对于任何以表达式触发字符 =-+@开头的单元格，直接使用tab字符作为前缀，防止CSV注入。
      if (StringUtils.startsWithAny(cellValue, FORMULA_STR)) {
      cellValue = RegExUtils.replaceFirst(cellValue, FORMULA_REGEX_STR, "\t$0");
      }
      cell.setCellValue(Objects.isNull(cellValue) ? attr.defaultValue() : cellValue + attr.suffix());
      } else if (ColumnType.NUMERIC == attr.cellType()) {
      if (Objects.nonNull(value)) {
      cell.setCellValue(StringUtils.contains(Convert.toStr(value), ".") ? Convert.toDouble(value) : Convert.toInt(value));
      }
      }
      }

  /**
    * 创建表格样式
      */
      public void setDataValidation(Excel attr, Row row, int column) {
      if (attr.name().indexOf("注：") >= 0) {
      sheet.setColumnWidth(column, 6000);
      } else {
      // 设置列宽
      sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
      }
      if (StringUtils.isNotEmpty(attr.prompt()) || attr.combo().length > 0) {
      // 提示信息或只能选择不能输入的列内容.
      setPromptOrValidation(sheet, attr.combo(), attr.prompt(), 1, 100, column, column);
      }
      }

  /**
    * 添加单元格
      */
      public Cell addCell(Excel attr, Row row, T vo, Field field, int column) {
      Cell cell = null;
      try {
      // 设置行高
      row.setHeight(maxHeight);
      // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
      if (attr.isExport()) {
      // 创建cell
      cell = row.createCell(column);
      int align = attr.align().value();
      cell.setCellStyle(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));

               // 用于读取对象中的属性
               Object value = getTargetValue(vo, field, attr);
               String dateFormat = attr.dateFormat();
               String readConverterExp = attr.readConverterExp();
               String separator = attr.separator();
               // String dictType = attr.dictType();
               if (StringUtils.isNotEmpty(dateFormat) && Objects.nonNull(value)) {
                   cell.setCellValue(parseDateToStr(dateFormat, value));
               } else if (StringUtils.isNotEmpty(readConverterExp) && Objects.nonNull(value)) {
                   cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
               } else if (value instanceof BigDecimal && -1 != attr.scale()) {
                   cell.setCellValue((((BigDecimal) value).setScale(attr.scale(), attr.roundingMode())).toString());
               }
               /*
                * else if (!attr.handler().equals(ExcelHandlerAdapter.class))
                * {
                * cell.setCellValue(dataFormatHandlerAdapter(value, attr));
                * }
                */
               else {
                   // 设置列类型
                   setCellVo(value, attr, cell);
               }
               addStatisticsData(column, Convert.toStr(value), attr);
           }
      } catch (Exception e) {
      log.error("导出Excel失败{}", e);
      }
      return cell;
      }

  /**
    * 设置 POI XSSFSheet 单元格提示或选择框
    * @param sheet 表单
    * @param textlist 下拉框显示的内容
    * @param promptContent 提示内容
    * @param firstRow 开始行
    * @param endRow 结束行
    * @param firstCol 开始列
    * @param endCol 结束列
      */
      public void setPromptOrValidation(Sheet sheet, String[] textlist, String promptContent, int firstRow, int endRow, int firstCol, int endCol) {
      DataValidationHelper helper = sheet.getDataValidationHelper();
      DataValidationConstraint constraint = textlist.length > 0 ? helper.createExplicitListConstraint(textlist) : helper.createCustomConstraint("DD1");
      CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
      DataValidation dataValidation = helper.createValidation(constraint, regions);
      if (StringUtils.isNotEmpty(promptContent)) {
      // 如果设置了提示信息则鼠标放上去提示
      dataValidation.createPromptBox("", promptContent);
      dataValidation.setShowPromptBox(true);
      }
      // 处理Excel兼容性问题
      if (dataValidation instanceof XSSFDataValidation) {
      dataValidation.setSuppressDropDownArrow(true);
      dataValidation.setShowErrorBox(true);
      } else {
      dataValidation.setSuppressDropDownArrow(false);
      }
      sheet.addValidationData(dataValidation);
      }

  /**
    * 解析导出值 0=男,1=女,2=未知
    * @param propertyValue 参数值
    * @param converterExp 翻译注解
    * @param separator 分隔符
    * @return 解析后值
      */
      public static String convertByExp(String propertyValue, String converterExp, String separator) {
      StringBuilder propertyString = new StringBuilder();
      String[] convertSource = converterExp.split(",");
      for (String item : convertSource) {
      String[] itemArray = item.split("=");
      if (StringUtils.containsAny(separator, propertyValue)) {
      for (String value : propertyValue.split(separator)) {
      if (itemArray[0].equals(value)) {
      propertyString.append(itemArray[1] + separator);
      break;
      }
      }
      } else {
      if (itemArray[0].equals(propertyValue)) {
      return itemArray[1];
      }
      }
      }
      return StringUtils.stripEnd(propertyString.toString(), separator);
      }

  /**
    * 合计统计信息
      */
      private void addStatisticsData(Integer index, String text, Excel entity) {
      if (entity != null && entity.isStatistics()) {
      Double temp = 0D;
      if (!statistics.containsKey(index)) {
      statistics.put(index, temp);
      }
      try {
      temp = Double.valueOf(text);
      } catch (NumberFormatException e) {
      log.error(e.getMessage());
      }
      statistics.put(index, statistics.get(index) + temp);
      }
      }

  /**
    * 创建统计行
      */
      public void addStatisticsRow() {
      if (statistics.size() > 0) {
      Row row = sheet.createRow(sheet.getLastRowNum() + 1);
      Set<Integer> keys = statistics.keySet();
      Cell cell = row.createCell(0);
      cell.setCellStyle(styles.get("total"));
      cell.setCellValue("合计");

           for (Integer key : keys) {
               cell = row.createCell(key);
               cell.setCellStyle(styles.get("total"));
               cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
           }
           statistics.clear();
      }
      }

  /**
    * 获取bean中的属性值
    * @param vo 实体对象
    * @param field 字段
    * @param excel 注解
    * @return 最终的属性值
    * @throws Exception
      */
      private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
      Object o = field.get(vo);
      if (StringUtils.isNotEmpty(excel.targetAttr())) {
      String target = excel.targetAttr();
      if (target.contains(".")) {
      String[] targets = target.split("[.]");
      for (String name : targets) {
      o = getValue(o, name);
      }
      } else {
      o = getValue(o, target);
      }
      }
      return o;
      }

  /**
    * 以类的属性的get方法方法形式获取值
    * @param o
    * @param name
    * @return value
    * @throws Exception
      */
      private Object getValue(Object o, String name) throws Exception {
      if (Objects.nonNull(o) && StringUtils.isNotEmpty(name)) {
      Class<?> clazz = o.getClass();
      Field field = clazz.getDeclaredField(name);
      field.setAccessible(true);
      o = field.get(o);
      }
      return o;
      }

  /**
    * 得到所有定义字段
      */
      private void createExcelField() {
      this.fields = getFields();
      this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort())).collect(Collectors.toList());
      this.maxHeight = getRowHeight();
      }

  /**
    * 获取字段注解信息
      */
      public List<Object[]> getFields() {
      List<Object[]> fields = new ArrayList<Object[]>();
      List<Field> tempFields = new ArrayList<>();
      tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
      tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
      for (Field field : tempFields) {
      Excel attr = field.getAnnotation(Excel.class);
      if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
      field.setAccessible(true);
      fields.add(new Object[] { field, attr });
      }
      }
      return fields;
      }

  /**
    * 根据注解获取最大行高
      */
      public short getRowHeight() {
      double maxHeight = 0;
      for (Object[] os : this.fields) {
      Excel excel = (Excel) os[1];
      maxHeight = Math.max(maxHeight, excel.height());
      }
      return (short) (maxHeight * 20);
      }

  /**
    * 创建一个工作簿
      */
      public void createWorkbook() {
      this.wb = new SXSSFWorkbook(500);
      this.sheet = wb.createSheet();
      wb.setSheetName(0, sheetName);
      this.styles = createStyles(wb);
      }

  /**
    * 创建工作表
    * @param sheetNo sheet数量
    * @param index 序号
      */
      public void createSheet(int sheetNo, int index) {
      // 设置工作表的名称.
      if (sheetNo > 1 && index > 0) {
      this.sheet = wb.createSheet();
      this.createTitle();
      wb.setSheetName(index, sheetName + index);
      }
      }

  /**
    * 格式化日期对象
    * @param dateFormat 日期格式
    * @param val 被格式化的日期对象
    * @return 格式化后的日期字符
      */
      public String parseDateToStr(String dateFormat, Object val) {
      if (val == null) {
      return "";
      }
      String str;
      if (val instanceof Date) {
      str = DateUtils.format((Date) val, dateFormat);
      } else {
      str = val.toString();
      }
      return str;
      }


    /**
     * 设置表头样式
     *
     * @param wb
     * @return
     */
    public static CellStyle setCallStyle(SXSSFWorkbook wb) {
        CellStyle cellStyleHeader = wb.createCellStyle();
        // 标题水平居中
        cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
        // 标题垂直居中
        cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置背景颜色填充方式为SOLID_FOREGROUND，即纯色填充
        cellStyleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 字体加粗
        Font font = wb.createFont();
        font.setBold(true);
        cellStyleHeader.setFont(font);
        return cellStyleHeader;
    }

}