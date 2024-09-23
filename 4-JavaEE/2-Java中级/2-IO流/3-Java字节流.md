一、字节流

InputStream字节输入流
OutputStream字节输出流
用于以字节的形式读取和写入数据

1、ASCII码 

概念:
所有的数据存放在计算机中都是以数字的形式存放的。 所以字母就需要转换为数字才能够存放。
比如A就对应的数字65，a对应的数字97. 不同的字母和符号对应不同的数字，就是一张码表。
ASCII是这样的一种码表。 只包含简单的英文字母，符号，数字等等。 不包含中文，德文，俄语等复杂的。

2、以字节流的形式读取文件内容

InputStream是字节输入流，同时也是抽象类，只提供方法声明，不提供方法的具体实现。
FileInputStream 是InputStream子类，以FileInputStream 为例进行文件读取

    package stream;
      
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.IOException;
      
    public class TestStream {
      
        public static void main(String[] args) {
            try {
                //准备文件lol.txt其中的内容是AB，对应的ASCII分别是65 66
                File f =new File("d:/lol.txt");
                //创建基于文件的输入流
                FileInputStream fis =new FileInputStream(f);
                //创建字节数组，其长度就是文件的长度
                byte[] all =new byte[(int) f.length()];
                //以字节流的形式读取文件所有内容
                fis.read(all);
                for (byte b : all) {
                    //打印出来是65 66
                    System.out.println(b);
                }
                //每次使用完流，都应该进行关闭
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

3、以字节流的形式向文件写入数据

OutputStream是字节输出流，同时也是抽象类，只提供方法声明，不提供方法的具体实现。
FileOutputStream 是OutputStream子类，以FileOutputStream 为例向文件写出数据

注: 如果文件d:/lol2.txt不存在，写出操作会自动创建该文件。
但是如果是文件 d:/xyz/lol2.txt，而目录xyz又不存在，会抛出异常

    package stream;
     
    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
     
    public class TestStream {
     
        public static void main(String[] args) {
            try {
                // 准备文件lol2.txt其中的内容是空的
                File f = new File("d:/lol2.txt");
                // 准备长度是2的字节数组，用88,89初始化，其对应的字符分别是X,Y
                byte data[] = { 88, 89 };
                // 创建基于文件的输出流
                FileOutputStream fos = new FileOutputStream(f);
                // 把数据写入到输出流
                fos.write(data);
                // 关闭输出流
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

4、写入数据到文件

以字节流的形式向文件写入数据 中的例子，当lol2.txt不存在的时候，是会自动创建lol2.txt文件的。
但是，如果是写入数据到d:/xyz/lol2.txt，而目录xyz又不存在的话，就会抛出异常。
那么怎么自动创建xyz目录？
如果是多层目录 d:/xyz/abc/def/lol2.txt 呢？

    package stream;
     
    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
     
    public class TestStream {
     
        public static void main(String[] args) {
            try {
                File f = new File("d:/xyz/abc/def/lol2.txt");
                //因为默认情况下，文件系统中不存在 d:\xyz\abc\def，所以输出会失败
                 
                //首先获取文件所在的目录
                File dir = f.getParentFile();
                //如果该目录不存在，则创建该目录
                if(!dir.exists()){
    //              dir.mkdir(); //使用mkdir会抛出异常，因为该目录的父目录也不存在
                    dir.mkdirs(); //使用mkdirs则会把不存在的目录都创建好
                }
                byte data[] = { 88, 89 };
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

5、拆分文件 

找到一个大于100k的文件，按照100k为单位，拆分成多个子文件，并且以编号作为文件名结束。
比如文件 eclipse.exe，大小是309k。
拆分之后，成为
eclipse.exe-0
eclipse.exe-1
eclipse.exe-2
eclipse.exe-3

拆分的思路，先把源文件的所有内容读取到内存中，然后从内存中挨个分到子文件里

提示，这里用到了数组复制Arrays.copyOfRange

    package stream;
      
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.util.Arrays;
      
    public class TestStream {
      
        public static void main(String[] args) {
            int eachSize = 100 * 1024; // 100k
            File srcFile = new File("d:/eclipse.exe");
            splitFile(srcFile, eachSize);
        }
      
        /**
         * 拆分的思路，先把源文件的所有内容读取到内存中，然后从内存中挨个分到子文件里
         * @param srcFile 要拆分的源文件
         * @param eachSize 按照这个大小，拆分
         */
        private static void splitFile(File srcFile, int eachSize) {
      
            if (0 == srcFile.length())
                throw new RuntimeException("文件长度为0，不可拆分");
      
            byte[] fileContent = new byte[(int) srcFile.length()];
            // 先把文件读取到数组中
            try {
                FileInputStream fis = new FileInputStream(srcFile);
                fis.read(fileContent);
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 计算需要被划分成多少份子文件
            int fileNumber;
            // 文件是否能被整除得到的子文件个数是不一样的
            // (假设文件长度是25，每份的大小是5，那么就应该是5个)
            // (假设文件长度是26，每份的大小是5，那么就应该是6个)
            if (0 == fileContent.length % eachSize)
                fileNumber = (int) (fileContent.length / eachSize);
            else
                fileNumber = (int) (fileContent.length / eachSize) + 1;
      
            for (int i = 0; i < fileNumber; i++) {
                String eachFileName = srcFile.getName() + "-" + i;
                File eachFile = new File(srcFile.getParent(), eachFileName);
                byte[] eachContent;
      
                // 从源文件的内容里，复制部分数据到子文件
                // 除开最后一个文件，其他文件大小都是100k
                // 最后一个文件的大小是剩余的
                if (i != fileNumber - 1) // 不是最后一个
                    eachContent = Arrays.copyOfRange(fileContent, eachSize * i, eachSize * (i + 1));
                else // 最后一个
                    eachContent = Arrays.copyOfRange(fileContent, eachSize * i, fileContent.length);
      
                try {
                    // 写出去
                    FileOutputStream fos = new FileOutputStream(eachFile);
                    fos.write(eachContent);
                    // 记得关闭
                    fos.close();
                    System.out.printf("输出子文件%s，其大小是 %d字节%n", eachFile.getAbsoluteFile(), eachFile.length());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

6、合并文件

把上述拆分出来的文件，合并成一个原文件。

以是否能正常运行，验证合并是否正确

与拆分文件不同(先把所有数据读取到内存中)，合并文件采用另一种思路。

这种思路，不需要把所有的子文件都先读取到内存中，而是一边读取子文件的内容，一边写出到目标文件

即从eclipse.exe-0开始，读取到一个文件，就开始写出到 eclipse.exe中，然后处理eclipse.exe-1eclipse.exe-2 eclipse.exe-3 ... 直到没有文件可以读

    package stream;
     
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
     
    import javax.security.auth.DestroyFailedException;
     
    public class TestStream {
     
        public static void main(String[] args) {
            murgeFile("d:/", "eclipse.exe");
        }
     
        /**
         * 合并的思路，就是从eclipse.exe-0开始，读取到一个文件，就开始写出到 eclipse.exe中，直到没有文件可以读
         * @param folder
         *            需要合并的文件所处于的目录
         * @param fileName
         *            需要合并的文件的名称
         * @throws FileNotFoundException
         */
        private static void murgeFile(String folder, String fileName) {
     
            try {
                // 合并的目标文件
                File destFile = new File(folder, fileName);
                FileOutputStream fos = new FileOutputStream(destFile);
                int index = 0;
                while (true) {
                    //子文件
                    File eachFile = new File(folder, fileName + "-" + index++);
                    //如果子文件不存在了就结束
                    if (!eachFile.exists())
                        break;
     
                    //读取子文件的内容
                    FileInputStream fis = new FileInputStream(eachFile);
                    byte[] eachContent = new byte[(int) eachFile.length()];
                    fis.read(eachContent);
                    fis.close();
                     
                    //把子文件的内容写出去
                    fos.write(eachContent);
                    fos.flush();
                    System.out.printf("把子文件 %s写出到目标文件中%n",eachFile);
                }
                fos.close();
                System.out.printf("最后目标文件的大小：%,d字节" , destFile.length());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
