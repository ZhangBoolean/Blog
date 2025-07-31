1、continue

继续下一次循环

    public class HelloWorld {
        public static void main(String[] args) {
        
                //打印单数    
                for (int j = 0; j < 10; j++) {
                    if(0==j%2) 
                        continue; //如果是双数，后面的代码不执行，直接进行下一次循环
                     
                    System.out.println(j);
                }
        }
    }


2、break

直接结束当前for循环

    public class HelloWorld {
        public static void main(String[] args) {
        
                //打印单数     
                for (int j = 0; j < 10; j++) {
                    if(0==j%2)  
                        break; //如果是双数，直接结束循环
                    
                    System.out.println(j);
                }
         }
    }

3、使用标签结束外部循环

    在外部循环的前一行，加上标签
    在break的时候使用该标签
    即能达到结束外部循环的效果

    public class HelloWorld {
        public static void main(String[] args) {
        
                //打印单数    
                outloop: //outloop这个标示是可以自定义的比如outloop1,ol2,out5
                for (int i = 0; i < 10; i++) {
                     
                    for (int j = 0; j < 10; j++) {
                        System.out.println(i+":"+j);
                        if(0==j%2) 
                            break outloop; //如果是双数，结束外部循环
                    }
                }
        }
    }