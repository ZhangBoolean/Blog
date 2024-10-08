# Redis位域(bitfield)

## 能干嘛

![](images/102.bitfield能干嘛.jpg)

位域修改、溢出控制

## 一句话

将一个redis字符串看作是**一个由二进制位组成的数组**并能对变长位宽和任意没有字节对齐的指定整型位域进行寻址和修改

![](images/103.bitfield基本语法.jpg)

# 命令代码实操

Ascii码表：https://ascii.org.cn

### 1.BITFIELD key [GET type offset]

![](images/104.bitfield-get.jpg)

### 2.BITFIELD key set type offstet value

![](images/105.bitfield-set.jpg)

### 3.BITFIELD key [INCRBY type offset increment]

![](images/106.bitfield-incrby.jpg)

如果偏移量后面的值发生溢出（大于127），redis对此也有对应的溢出控制，默认情况下，INCRBY使用WRAP参数

### 4.溢出控制 OVERFLOW [WRAP|SAT|FAIL]

WRAP:使用回绕(wrap around)方法处理有符号整数和无符号整数溢出情况

![](images/107.溢出策略warp.jpg)

SAT:使用饱和计算(saturation arithmetic)方法处理溢出，下溢计算的结果为最小的整数值，而上溢计算的结果为最大的整数值

![](images/108.溢出策略sat.jpg)

fail:命令将拒绝执行那些会导致上溢或者下溢情况出现的计算，并向用户返回空值表示计算未被执行

![](images/109.溢出策略fail.jpg)