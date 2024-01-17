* 使用实例

        String filePath = FileRecordUtils.getPath(Const.Folder.TEMP);
        File newDir = new File(filePath);
        FileUtil.createDir(newDir);
        List<File> files = new ArrayList<>();
        for (TiInvoiceHead tiInvoiceHead : list) {
            if (StringUtils.isBlank(tiInvoiceHead.getDocumentPdf())) {
                throw new Exception(tiInvoiceHead.getFinancialDocumentNumber() + " DocumentPdf 为空");
            }
            String fileName = tiInvoiceHead.getFinancialDocumentNumber() + "_" + DateUtils.format(new Date(), DateUtils.DATE8_TIME9) + ".pdf";

            File file = FileUtil.base64ToFile(tiInvoiceHead.getDocumentPdf(), newDir.getAbsolutePath(), fileName);
            files.add(file);
        }



* base64ToFile方法


      public static File base64ToFile(String base64, String filePath, String fileName) throws Exception {
      File file = new File(filePath, fileName);
      byte[] buffer;
      FileOutputStream out = null;
      try {
      BASE64Decoder b = new BASE64Decoder();
      buffer = b.decodeBuffer(base64);
      out = new FileOutputStream(file);
      out.write(buffer);
      out.close();
      } catch (Exception e) {
      log.error("转换文件时出错 FileUtil.base64ToFile ", e);
      throw e;
      } finally {
      close(out);
      }
      return file;
      }