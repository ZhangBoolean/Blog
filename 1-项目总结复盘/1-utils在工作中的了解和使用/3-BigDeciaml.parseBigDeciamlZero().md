解析方法的参数或对象是否为null(Y:输出；N:转出为：0)
/**
  * <p>Description: 解析BigDecimal</p>
  * @param o 对象
  * @return BigDecimal
*/
    public static BigDecimal parseBigDecimalZero(Object o) {
        if (null == o || "".equals(o.toString())) {
            return new BigDecimal(0);
        }
        return new BigDecimal(o.toString());
    }


/**
  * <p>Description: 解析BigDecimal</p>
  * @param o 对象
  * @return BigDecimal
*/
    public static BigDecimal parseBigDecimalZero(BigDecimal o) {
        if (null == o) {
            return BigDecimal.ZERO;
        }
        return o;
    }