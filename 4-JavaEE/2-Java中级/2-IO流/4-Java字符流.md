一、字符流

Reader字符输入流
Writer字符输出流
专门用于字符的形式读取和写入数据

1、使用字符流读取文件

FileReader 是Reader子类，以FileReader 为例进行文件读取

package stream;
 
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
 
public class TestStream {
 
    public static void main(String[] args) {
        // 准备文件lol.txt其中的内容是AB
        File f = new File("d:/lol.txt");
        // 创建基于文件的Reader
        try (FileReader fr = new FileReader(f)) {
            // 创建字符数组，其长度就是文件的长度
            char[] all = new char[(int) f.length()];
            // 以字符流的形式读取文件所有内容
            fr.read(all);
            for (char b : all) {
                // 打印出来是A B
                System.out.println(b);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
    }
}

2、使用字符流把字符串写入到文件

FileWriter 是Writer的子类，以FileWriter 为例把字符串写入到文件

package stream;
  
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
  
public class TestStream {
  
    public static void main(String[] args) {
        // 准备文件lol2.txt
        File f = new File("d:/lol2.txt");
        // 创建基于文件的Writer
        try (FileWriter fr = new FileWriter(f)) {
            // 以字符流的形式把数据写入到文件中
            String data="abcdefg1234567890";
            char[] cs = data.toCharArray();
            fr.write(cs);
  
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
    }
}

3、文件加密 

准备一个文本文件(非二进制)，其中包含ASCII码的字符和中文字符。
设计一个方法
 
public static void encodeFile(File encodingFile, File encodedFile);
 

在这个方法中把encodingFile的内容进行加密，然后保存到encodedFile文件中。
加密算法：
数字：
如果不是9的数字，在原来的基础上加1，比如5变成6, 3变成4
如果是9的数字，变成0
字母字符：
如果是非z字符，向右移动一个，比如d变成e, G变成H
如果是z，z->a, Z-A。
字符需要保留大小写
非字母字符
比如',&^ 保留不变，中文也保留不变
建议： 使用以前学习的练习题中的某个Java文件，比如循环练习，就有很多的字符和数字

package stream;
 
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class TestStream {
    /**
     *
     * @param encodingFile
     *            被加密的文件
     * @param encodedFile
     *            加密后保存的位置
     */
    public static void encodeFile(File encodingFile, File encodedFile) {
 
        try (FileReader fr = new FileReader(encodingFile); FileWriter fw = new FileWriter(encodedFile)) {
            // 读取源文件
            char[] fileContent = new char[(int) encodingFile.length()];
            fr.read(fileContent);
            System.out.println("加密前的内容：");
            System.out.println(new String(fileContent));
 
            // 进行加密
            encode(fileContent);
            // 把加密后的内容保存到目标文件
            System.out.println("加密后的内容：");
            System.out.println(new String(fileContent));
 
            fw.write(fileContent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    private static void encode(char[] fileContent) {
        for (int i = 0; i < fileContent.length; i++) {
            char c = fileContent[i];
            if (isLetterOrDigit(c)) {
                switch (c) {
                case '9':
                    c = '0';
                    break;
                case 'z':
                    c = 'a';
                    break;
                case 'Z':
                    c = 'A';
                    break;
                default:
                    c++;
                    break;
                }
            }
            fileContent[i] = c;
        }
    }
 
    public static boolean isLetterOrDigit(char c) {
        // 不使用Character类的isLetterOrDigit方法是因为，中文也会被判断为字母
        String letterOrDigital = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return -1 == letterOrDigital.indexOf(c) ? false : true;
    }
 
    public static void main(String[] args) {
        File encodingFile = new File("E:/project/j2se/src/Test1.txt");
        File encodedFile = new File("E:/project/j2se/src/Test2.txt");
        encodeFile(encodingFile, encodedFile);
    }
}

4、文件解密

解密在文件加密中生成的文件。
设计一个方法
 
public static void decodeFile(File decodingFile, File decodedFile);
 

在这个方法中把decodingFile的内容进行解密，然后保存到decodedFile文件中。
解密算法：
数字：
如果不是0的数字，在原来的基础上减1，比如6变成5, 4变成3
如果是0的数字，变成9
字母字符：
如果是非a字符，向左移动一个，比如e变成d, H变成G
如果是a，a->z, A-Z。
字符需要保留大小写
非字母字符：
比如',&^ 保留不变，中文也保留不变

package stream;
 
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class TestStream {
 
    /**
     *
     * @param decodingFile
     *            被解密的文件
     * @param decodedFile
     *            解密后保存的位置
     */
    public static void decodeFile(File decodingFile, File decodedFile) {
 
        try (FileReader fr = new FileReader(decodingFile); FileWriter fw = new FileWriter(decodedFile)) {
            // 读取源文件
            char[] fileContent = new char[(int) decodingFile.length()];
            fr.read(fileContent);
            System.out.println("源文件的内容:");
            System.out.println(new String(fileContent));
            // 进行解密
            decode(fileContent);
            System.out.println("解密后的内容:");
            System.out.println(new String(fileContent));
            // 把解密后的内容保存到目标文件
            fw.write(fileContent);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    private static void decode(char[] fileContent) {
        for (int i = 0; i < fileContent.length; i++) {
            char c = fileContent[i];
            if (isLetterOrDigit(c)) {
                switch (c) {
                case '0':
                    c = '9';
                    break;
                case 'a':
                    c = 'z';
                    break;
                case 'A':
                    c = 'Z';
                    break;
                default:
                    c--;
                    break;
                }
            }
            fileContent[i] = c;
        }
    }
 
    public static boolean isLetterOrDigit(char c) {
        // 不使用Character类的isLetterOrDigit方法是因为，中文也会被判断为字母
        String letterOrDigital ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return -1 == letterOrDigital.indexOf(c) ? false : true;
    }
 
    public static void main(String[] args) {
        File decodingFile = new File("E:/project/j2se/src/Test2.txt");
        File decodedFile = new File("E:/project/j2se/src/Test1.txt");
 
        decodeFile(decodingFile, decodedFile);
 
    }
}