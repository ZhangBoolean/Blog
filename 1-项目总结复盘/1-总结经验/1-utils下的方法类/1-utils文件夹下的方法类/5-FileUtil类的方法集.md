创建、压缩、解压、读取等方法集合


    public class FileUtil {
    public static final String BACKUP = "backup";
    public static final String OUT_BACKUP = "-backup";
    public static final String ERROR = "error";

    /**
     * <p>Description: 构造方法</p>
     */
    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * <p>Description: create folder and file</p>
     * @param filePath file path
     * @return file
     */
    public static File createFile(String filePath) {
        String path = filePath.substring(0, filePath.lastIndexOf(File.separatorChar));
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return new File(filePath);
    }

    /**
     * <p>Description: close stream</p>
     * @param ca claseable
     */
    public static void close(Closeable ca) {
        if (null != ca) {
            try {
                ca.close();
            } catch (IOException e) {
                log.error("close", e);
            }
        }
    }

    /**
     * <p>Description: 压缩文件</p>
     * @param path 路径
     * @param files 文件
     */
    public static void zip(String path, List<String> files) {

        ZipOutputStream zos = null;
        try {
            InputStream is = null;
            zos = new ZipOutputStream(new FileOutputStream(new File(path)));
            File file = null;
            for (String f : files) {
                file = new File(f);
                if (null != file && file.exists()) {
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    is = new FileInputStream(file);
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = is.read(buffer)) != -1) {
                        zos.write(buffer, 0, len);
                        zos.flush();
                    }
                    is.close();
                    zos.closeEntry();
                }
            }
            zos.close();
        } catch (Exception e) {
            log.error("zip", e);
        } finally {
            close(zos);
        }
    }

    /**
     * <p>Description: 压缩文件</p>
     * @param path 路径
     * @param folder 文件夹
     */
    public static void zip(String path, String folder) {

        ZipOutputStream zos = null;
        try {
            InputStream is = null;
            zos = new ZipOutputStream(new FileOutputStream(new File(path)));
            File folderFile = new File(folder);
            if (folderFile.isDirectory()) {
                File[] files = folderFile.listFiles();
                for (File f : files) {
                    if (null != f) {
                        zip(f, zos, is, null);
                    }
                }
            }
            zos.close();
        } catch (Exception e) {
            log.error("zip", e);
        } finally {
            close(zos);
        }
    }

    /**
     * <p>Description: 读取文件到字节数组</p>
     * @param file 文件路径
     * @return 字节
     * @throws IOException IOException
     */
    public static byte[] getFile(String file) throws IOException {
        return FileUtils.readFileToByteArray(new File(file));
    }

    /**
     * <p>Description: 压缩</p>
     * @param f 文件
     * @param zos 压缩
     * @param is 输入
     * @param name 名称
     * @throws IOException IOException
     */
    private static void zip(File f, ZipOutputStream zos, InputStream is, String name) throws IOException {
        if (null != f) {
            if (f.isDirectory()) {
                for (File file : f.listFiles()) {
                    zip(file, zos, is, addPath(name) + f.getName());
                }
            } else {
                zos.putNextEntry(new ZipEntry(addPath(name) + f.getName()));
                is = new FileInputStream(f);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
                is.close();
                zos.closeEntry();
            }
        }
    }

    /**
     * <p>Description: 添加路径</p>
     * @param name 名称
     * @return 路径
     */
    private static String addPath(String name) {
        if (StringUtils.isNotBlank(name)) {
            return name + File.separator;
        }
        return "";
    }

    /**
     * <p>Description: 解压</p>
     * @param zipFile 文件
     * @param location 解压路径
     * @throws IOException IOException
     */
    public static void unzip(File zipFile, String location) throws IOException {
        byte[] buffer = new byte[1024];

        File folder = new File(location);
        if (!folder.exists()) {
            folder.mkdir();
        }
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry ze = zis.getNextEntry();
        while (ze != null) {
            String fileName = ze.getName();
            File newFile = new File(location + File.separator + fileName);
            if (ze.isDirectory()) {
                newFile.mkdirs();
            } else {
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            ze = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    /**
     * <p>Description: 创建文件夹</p>
     * @param path 路径
     */
    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * <p>Description: 创建文件夹</p>
     * @param file 文件
     */
    public static void createDir(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * <p>Description: 创建日期+uuid的路径</p>
     * @return 路径
     */
    public static String createPath() {
        return DateUtils.fmtDate(new Date()).replace("-", File.separator).concat(File.separator).concat(Utils.uuid());
    }

    /**
     * <p>Description: 创建日期+uuid的路径</p>
     * @param path 路径
     * @return 路径
     */
    public static String createPath(String path) {
        return path.concat(File.separator).concat(createPath());
    }

    /**
     * <p>Description: 获取备份路径</p>
     * @param path 路径
     * @return 路径
     */
    public static String getBackup(String path) {
        return path.concat(File.separator).concat(BACKUP);
    }

    /**
     * <p>Description: 获取错误路径</p>
     * @param path 路径
     * @return 路径
     */
    public static String getError(String path) {
        return path.concat(File.separator).concat(ERROR);
    }

    /**
     * <p>Description: 获取导出的备份路径</p>
     * @param path 路径
     * @return 路径
     */
    public static String getOutBackup(String path) {
        return path.concat(OUT_BACKUP);
    }

    /**
     * <p>Description: 创建日期的路径</p>
     * @return 路径
     */
    public static String createDatePath() {
        return DateUtils.fmtDate(new Date()).replace("-", File.separator);
    }

    /**
     * <p>Description: 创建日期的路径</p>
     * @param path 路径
     * @return 路径
     */
    public static String createDatePath(String path) {
        return path.concat(File.separator).concat(createDatePath());
    }

    /**
     * <p>Description: 取得文件名</p>
     * @param path 路径
     * @return 文件名
     */
    public static String getName(String path) {
        if (StringUtils.isNotBlank(path)) {
            if (-1 != path.indexOf(File.separator)) {
                return path.substring(path.lastIndexOf(File.separator) + 1, path.lastIndexOf("."));
            } else if (-1 != path.indexOf("/")) {
                return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
            } else if (-1 != path.indexOf("\\")) {
                return path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
            }
        }
        return path;
    }

    /**
     * <p>Description: 取得文件名</p>
     * @param path 路径
     * @return 文件名
     */
    public static String getFileName(String path) {
        if (StringUtils.isNotBlank(path)) {
            if (-1 != path.indexOf(File.separator)) {
                return path.substring(path.lastIndexOf(File.separator) + 1);
            } else if (-1 != path.indexOf("/")) {
                return path.substring(path.lastIndexOf(File.separator) + 1);
            } else if (-1 != path.indexOf("\\")) {
                return path.substring(path.lastIndexOf(File.separator) + 1);
            }
        }
        return path;
    }

    /**
     * <p>Description: 取得文件名</p>
     * @param path 路径
     * @return 文件名
     */
    public static String getPath(String path) {
        if (StringUtils.isNotBlank(path)) {
            if (-1 != path.indexOf(File.separator)) {
                return path.substring(0, path.lastIndexOf(File.separator));
            } else if (-1 != path.indexOf("/")) {
                return path.substring(0, path.lastIndexOf("/"));
            } else if (-1 != path.indexOf("\\")) {
                return path.substring(0, path.lastIndexOf("\\"));
            }
        }
        return path;
    }

    /**
     * <p>Description: 取得文件名后缀</p>
     * @param path 路径
     * @return 文件名
     */
    public static String getSuffix(String name) {
        if (StringUtils.isNotBlank(name)) {
            if (-1 != name.indexOf(".")) {
                return name.substring(name.indexOf(".") + 1);
            }
        }
        return name;
    }

    /**
     * <p>Description: 去掉文件名后缀</p>
     * @param path 路径
     * @return 文件名
     */
    public static String removeSuffix(String name) {
        if (StringUtils.isNotBlank(name)) {
            if (-1 != name.indexOf(".")) {
                return name.substring(0, name.indexOf("."));
            }
        }
        return name;
    }

    /**
     * <p>Description: 文件下载</p>
     * @param response HttpServletResponse
     * @param file 文件
     * @param filename 文件名
     * @throws IOException IOException
     */
    public static void download(HttpServletResponse response, File file, String filename) throws IOException {
        if (file.exists()) {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                response.reset();
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, Const.ENCODE_UTF8));
                response.addHeader("Content-Length", "" + file.length());
                response.setHeader("Accept-Ranges", "bytes");
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Headers", "DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                response.setHeader("Access-Control-Expose-Headers", "Accept-Ranges, Content-Encoding, Content-Length, Content-Range");
                os = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                os.write(buffer);
                os.flush();
            } catch (IOException e) {
                log.error("wodnload", e);
                throw e;
            } finally {
                FileUtil.close(os);
                FileUtil.close(is);
            }
        }
    }

    /**
     * <p>Description: 读取XML文件</p>
     * @param file File
     * @return Document
     * @throws Exception
     */
    public static Document readXml(File file) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            document = saxReader.read(fis);
        } catch (Exception e) {
            throw new Exception("读取文件时出错: " + e);
        } finally {
            close(fis);
        }
        return document;

    }

    /**
     * <p>Description: 获取指定目录的文件</p>
     * @param path
     * @return
     */
    public static List<File> getFiles(String path) {
        // 获取指定路径的文件目录
        File dirFile = null;
        dirFile = new File(path);
        if (!dirFile.isDirectory()) {
            return null;
        }
        // 获取指定路径的所有文件
        File[] allFiles = null;
        allFiles = dirFile.listFiles();
        if (null == allFiles || allFiles.length == 0) {
            return null;
        }
        // 根据名称取出符合条件的文件
        List<File> files = null;
        files = new ArrayList<File>();
        // 文件名称.
        for (int i = 0; i < allFiles.length; i++) {
            if (!allFiles[i].isDirectory()) {
                files.add(allFiles[i]);
            }
        }

        return files;
    }

}