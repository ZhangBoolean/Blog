1、while 循环

只要while中的表达式成立，就会不断地循环执行

    public class HelloWorld {
            public static void main(String[] args) {
        
                //打印0到4    
                int i = 0;
                while(i<5){
                    System.out.println(i);
                    i++;
                }
            }
    }


2、do{ } while 循环

与while的区别是，无论是否成立，先执行一次，再进行判断

    public class HelloWorld {
        public static void main(String[] args) {

                //打印0到4
                //与while的区别是，无论是否成立，先执行一次，再进行判断
                int i = 0;
                do{
                    System.out.println(i);
                    i++;           
                } while(i<5);
                 
        }
    }