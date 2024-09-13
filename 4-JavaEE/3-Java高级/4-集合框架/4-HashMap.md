一、HashMap的键值对

1、HashMap储存数据的方式是—— 键值对

    import java.util.HashMap;

    public class TestCollection {
    public static void main(String[] args) {
    HashMap<String,String> dictionary = new HashMap<>();
    dictionary.put("adc", "物理英雄");
    dictionary.put("apc", "魔法英雄");
    dictionary.put("t", "坦克");
        System.out.println(dictionary.get("t"));
        }
    }

2、键不能重复，值可以重复

对于HashMap而言，key是唯一的，不可以重复的。

所以，以相同的key 把不同的value插入到 Map中会导致旧元素被覆盖，只留下最后插入的元素。

不过，同一个对象可以作为值插入到map中，只要对应的key不一样

    import java.util.HashMap;

    public class TestCollection {
        public static void main(String[] args) {
        HashMap<String,Hero> heroMap = new HashMap<String,Hero>();
        heroMap.put("gareen", new Hero("gareen1"));
        System.out.println(heroMap);
        //key为gareen已经有value了，再以gareen作为key放入数据，会导致原英雄，被覆盖
        //不会增加新的元素到Map中
        heroMap.put("gareen", new Hero("gareen2"));
        System.out.println(heroMap);
        //清空map
        heroMap.clear();
        Hero gareen = new Hero("gareen");
        //同一个对象可以作为值插入到map中，只要对应的key不一样
        heroMap.put("hero1", gareen);
        heroMap.put("hero2", gareen);
        System.out.println(heroMap);
        }
    }


3、准备一个ArrayList其中存放3000000(三百万个)Hero对象，其名称是随机的,格式是hero-[4位随机数]
hero-3229
hero-6232
hero-9365
...

因为总数很大，所以几乎每种都有重复，把名字叫做 hero-5555的所有对象找出来
要求使用两种办法来寻找
1. 不使用HashMap，直接使用for循环找出来，并统计花费的时间
2. 借助HashMap，找出结果，并统计花费的时间

    public class TestCollection {
        public static void main(String[] args) {
            List<Hero> hs = new ArrayList<>();
            System.out.println("初始化开始");
            for (int n = 0; 300000 > n; n++) {
                Hero hero = new Hero("hero-" + random());
                hs.add(hero);
            }
            // 将名字作为Key
            // 将名字相同的hero，放在一个List中，作为value
            HashMap<String, List<Hero>> heroMap = new HashMap<>();
            for (Hero h : hs) {
                List<Hero> list = heroMap.get(h.name);
                if (list == null) {
                    list = new ArrayList<>();
                    heroMap.put(h.name, list);
                }
                list.add(h);
            }
            findByIteration(hs);
            findByMap(heroMap);
        }
    
        public static List<Hero> findByIteration(List<Hero> hs) {
            long start = System.currentTimeMillis();
            List<Hero> result = new ArrayList<>();
            for (Hero h : hs) {
                if (h.name.equals("hero-5555")) {
                    result.add(h);
                }
            }
            long end = System.currentTimeMillis();
            System.out.printf("通过for查找，一共找到%d个英雄，耗时%d 毫秒%n", result.size(), end - start);
            return result;
        }
    
        public static List<Hero> findByMap(HashMap<String, List<Hero>> m) {
            long start = System.currentTimeMillis();
            List<Hero> result = m.get("hero-5555");
            long end = System.currentTimeMillis();
            System.out.printf("通过map查找，一共找到%d个英雄，耗时%d 毫秒%n", result.size(), end - start);
            return result;
        }
    
        public static int random() {
            return (int) ((Math.random() * 9000) + 1000);
        }
    
        public static class Hero {
            public String name;
            public float hp;
            public int damage;
    
            public Hero() {
            }
            public Hero(String name) {
    
                this.name = name;
            }
            public String toString() {
                return name;
            }
        }
    }

