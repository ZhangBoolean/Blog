# Redis地理空间(GEO)

### 简介：

移动互联网时代LBS应用越来越多，交友软件中附近的小姐姐、外卖软件中附近的美食店铺、高德地图附近的核酸检查点等等，那这种附近各种形形色色的XXX地址位置选择是如何实现的?
地球上的地理位置是使用二维的经纬度表示，经度范围(-180,180]，纬度范围(-90，90]，只要我们确定一个点的经纬度就可以取得他在地球的位置。
例如滴滴打车，最直观的操作就是实时记录更新各个车的位置，
然后当我们要找车时，在数据库中查找距离我们(坐标x0,y0)附近r公里范围内部的车辆
使用如下SQL即可:

```sql
select taxi from position where x0-r< X < x0 + r and y0-r< y < y0+r
```

$\textcolor{red}{但是这样会有什么问题呢?}$
1.查询性能问题，如果并发高，数据量大这种查询是要搞垮数据库的
2.这个查询的是一个矩形访问，而不是以我为中心r公里为半径的圆形访问。
3.精准度的问题，我们知道地球不是平面坐标系，而是一个圆球，这种矩形计算在长距离计算时会有很大误差

### 原理

![](images/71.GEO原理.jpg)

redis在3.2版本以后增加了地址位置的处理

### 命令

### 1.GEOADD key longitude latitude member [longitude latitude member]

多个经度(longitude)、纬度(latitude)、位置名称(member)添加到指定的key中

命令：GEOADD city 116.403963 39.915119 "天安门" 116.403414 39.924091 "故宫" 116.024067 40.362639 "长城"

geo类型实际上是zset，可以使用zset相关的命令对其进行遍历，如果遍历出现中文乱码可以使用如下命令：redis-cli --raw

![](images/72.GEO-geoadd.png)

### 2.GEOPOS key member [member]

从键里面返回所有指定名称(member )元素的位置（经度和纬度），不存在返回nil

GEOPOS city 天安门 故宫 长城

![](images/73.GEO-geopos.png)

### 3.GEODIST key member1 member2 [M|KM|FT|MI]

返回两个给定位置之间的距离

m-米

km-千米

ft-英寸

mi-英里

![](images/75.GEO-GEODIST.png)

### 4.GEORADIUS key longitude latitude radius M|KM|FT|MI \[WITHCOORD] \[WITHDIST] \[WITHHASH] [COUNT count [ANY]

以给定的经纬度为中心，返回与中心的距离不超过给定最大距离的所有元素位置

WITHDIST: 在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回。 距离的单位和用户给定的范围单位保持一致。
WITHCOORD: 将位置元素的经度和维度也一并返回。
WITHHASH:以 52 位有符号整数的形式， 返回位置元素经过原始 geohash 编码的有序集合分值。 这个选项主要用于底层应用或者调试，实际中的作用并不大
COUNT 限定返回的记录数。

![](images/76.GEO-georadius.png)

### 5.GEORADIUSBYMEMBER

跟GEORADIUS类似

![](images/77.GEO-georadiusbymember.png)

### 6.GEOHASH

返回一个或多个位置元素的GEOhash表示

geohash 算法生成的base32编码值，3维变2维变1维

![](images/74.GEO-GEOhash.png)