解决 java.util.ConcurrentModificationException 并发修改异常

1、ArrayList

    public class ListTest{
        public ststic void main(String[] args){
            // 并发下ArrayList是不安全的

            List<String> list = new ArrayList<>();
            for(int i = 1; i<10;i++{
                new Thread(()->{
                    list.add(UUID.randomUUID().toString().substring(0,5));
                    Sysrem.out.println(list);
                },String.valueOf(i)).start();
            }
        }
    }

 // 解决方案：

            // a、把ArrayList换成Vector  
            List<String> list = new Vector<>();
            // b、把ArrayList换成Collections.synchronizedList(new ArrayList);
            List<String> list = Collections.synchronizedList(new ArratList);
            // c、把ArrayList换成CopyOnWriteArrayList<>();
            List<String> list = CopyOnWriteArrayList<>();
                /**
                 * CopyOnWrite  写入时复制   COW  计算机程序设计领域的一种优化策略
                 * 多个线程调用的时候，list，读取的时候，固定的，写入（覆盖）
                 * 避免在写入的时候数据覆盖，造成数据问题
                 * 读写分离
                 * CopyOnWriteArrayList 比Vector好在哪里？
                 * CopyOnWriteArrayList底层用的Lock锁的原来，而Vector用的Synchronized。Lock锁效率比Synchronized高
                 */ 


2、Set

        public class ListTest{
        public ststic void main(String[] args){
            // 并发下ArrayList是不安全的

            Set<String> set = new HashSet<>();
            for(int i = 1; i<30;i++{
                new Thread(()->{
                    list.add(UUID.randomUUID().toString().substring(0,5));
                    Sysrem.out.println(list);
                },String.valueOf(i)).start();
            }
        }
    }

// 解决方案
        
        // a、把HashSet换成Collections.synchronizedSet(new HashSet<>());
        Set<String> set = Collections.synchromizedSet(new HashSet<>));
        // b、把HashSet换成CopyOnWriteArraySet<>(new HashSet<>);