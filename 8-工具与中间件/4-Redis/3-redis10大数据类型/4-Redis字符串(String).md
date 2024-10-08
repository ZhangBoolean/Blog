# Redis字符串(String)

官网：https://redis.io/docs/data-types/strings/

![](images/13string介绍.jpg)

单值单value

案例：

### 1.$\textcolor{red}{最 常 用：set \quad key \quad value  }$ |  get key

![](images/14.string参数.jpg)

**返回值：**

设置成功则返回OK，返回nil为未执行Set命令，如不满足NX，XX条件等。

若使用GET参数，则返回该键原来的值，或在键不存在时nil。

![](images/15.string参数解析.jpg)

如何获得设置指定的key过期的Unix时间，单位为秒

```java
System.out.println(Long.toString(System.currentTimeMillis()/1000L));
```

![](images/16.设置过期时间.jpg)

### 2.同时设置/获取多个键值

MSET key value [key value...]

MGET key [key ...]

mset/mget/msetnx

![](images/17.string多值操作.jpg)

### 3.获取指定区间范围内的值

getrange/setrange

![](images/18.getrange和setrange用法.jpg)

### 4.数值增减

$\textcolor{red}{一定要是数据才能进行加减}$

递增数字：INCR key

增加指定的整数：INCRBY key increment

递减数值：DECR key

减少指定的整数：DECRBY key decrement

![](images/19.string类型自增自减.jpg)

### 5.获取字符串长度和内容追加

获取字符串长度：strlen key

字符串内容追加：append key value

![](images/20字符串长度获取和内容追加.jpg)

### 6.分布式锁

setnx key value

setex(set with expire)键秒值/setnx(set if not exist)

![](images/21.分布式锁.jpg)

### 7.getset(先get再set)

getset：将给定key的值设为value，并返回key的旧值(old value)。

简单一句话：先get然后立即set

![](images/22.getset命令.jpg)