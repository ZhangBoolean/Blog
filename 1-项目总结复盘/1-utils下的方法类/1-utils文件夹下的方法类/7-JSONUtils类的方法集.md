
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


@Slf4j
public class JSONUtils {

    /**
     * 将obj转json字符串
     *
     * @param obj
     * @return str
     */
    public static String toJSONString(Object obj) {
        String str = null;
        try {
            if (Objects.nonNull(obj)) {
                // 允许输出空值, 数值字段如果为null,输出0
                // str = JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero);
                str = JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
            }
        } catch (Exception e) {
            log.error("obj to str error", e);
        }
        return str;
    }

    /**
     * 将json字符串转obj
     *
     * @param str
     * @param tClass
     * @return obj
     * @param <T>
     */
    public static <T> T parseObject(String str, Class<T> tClass) {
        T obj = null;
        try {
            if (StringUtils.isNotBlank(str) && Objects.nonNull(tClass)) {
                obj = JSON.parseObject(str, tClass);
            }
        } catch (Exception e) {
            log.error("str to obj error", e);
        }
        return obj;
    }

}
