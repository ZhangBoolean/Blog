Throwable是类，Exception和Error都继承了该类
所以在捕捉的时候，也可以使用Throwable进行捕捉
如图： 异常分Error和Exception
Exception里又分运行时异常和可查异常。

    package exception;
     
    import java.io.File;
    import java.io.FileInputStream;
 
    public class TestException {
 
        public static void main(String[] args) {
     
            File f = new File("d:/LOL.exe");
     
            try {
                new FileInputStream(f);
                //使用Throwable进行异常捕捉
            } catch (Throwable t) {
                // TODO Auto-generated catch block
                t.printStackTrace();
            }
        }
    }

2、练习-Throwable

在方法声明上，可以抛出指定的异常，比如FileNotFoundException
那么能否抛出Throwable这个类？

这个方法的调用者又该如何处理？

可以抛出Throwable，并且在调用的时候必须进行catch处理。

但是这样的设计方法做不好，因为不知道抛出的类型到底是哪种具体问题，无法针对性的处理。


    package exception;
     
    import java.io.File;
    import java.io.FileInputStream;
     
    public class TestException {
        public static void method() throws Throwable {
            File f = new File("d:/LOL.exe");
            new FileInputStream(f);
        }
    
        public static void main(String[] args) {
            try {
                method();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
