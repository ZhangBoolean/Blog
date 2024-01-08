// 调用redis的方法集

@Component
public class ApiResourceUtils {

    private static StringRedisTemplate redis;

    public static void put(User user, List<String> apis) {
        String[] arrays = new String[apis.size()];
        String key = Const.REDIS_API_PREFIX + user.getLoginName();
        getRedis().opsForSet().add(key, apis.toArray(arrays));
        getRedis().expire(key, 240, TimeUnit.MINUTES);
    }

    public static boolean check(String name, String api) {
        String key = Const.REDIS_API_PREFIX + name;
        return getRedis().opsForSet().isMember(key, api);
    }

    public static void putCode(User user, List<String> codes) {
        String[] arrays = new String[codes.size()];
        String key = Const.REDIS_CODE_PREFIX + user.getLoginName();
        getRedis().opsForSet().add(key, codes.toArray(arrays));
        getRedis().expire(key, 240, TimeUnit.MINUTES);
    }

    public static boolean checkCode(String name, String code) {
        return getRedis().opsForSet().isMember(Const.REDIS_CODE_PREFIX + name, code);
    }

    private static StringRedisTemplate getRedis() {
        if (null == redis) {
            redis = ApplicationContextUtils.getBean(StringRedisTemplate.class);
        }
        return redis;
    }
}
