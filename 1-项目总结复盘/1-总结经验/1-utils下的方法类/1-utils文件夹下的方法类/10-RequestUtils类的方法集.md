@Slf4j
public class RequestUtils {

    /**
     * <p>Description: 取得IP地址</p>
     * @param request HttpServletRequest
     * @return IP
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * <p>Description: 取得用户客户端信息</p>
     * @param request HttpServletRequest
     * @return User-Agent
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    /**
     * <p>Description: 取得BODY</p>
     * @param request HttpServletRequest
     * @return 数据
     */
    public static String getRequestBody(HttpServletRequest request) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = request.getReader();
            String bodyStr = IOUtils.toString(bufferedReader);
            String ret = new String(bodyStr.getBytes(), Const.ENCODE_UTF8);
            return ret;
        } catch (IOException e) {
            log.error("getRequestBody", e);
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("getRequestBody", e);
                }
            }
        }
        return null;
    }

    /**
     * <p>Description: 根据URI获取应用名称</p>
     * @param uri URI
     * @return 应用名称
     * @Author: HuangSongqing
     * @Date: 2022-06-22
     */
    public static String getApp(String uri) {
        if (StringUtils.isNotBlank(uri)) {
            if (uri.startsWith("/")) {
                return uri.substring(1, uri.indexOf("/", 1));
            } else {
                return uri.substring(0, uri.indexOf("/"));
            }
        }
        return null;
    }

    /**
     * <p>Description: 取得参数表</p>
     * @param request HttpServletRequest
     * @return 参数表
     */
    public static String getPara(HttpServletRequest request) {
        Map<String, String> map = getParaMap(request);
        if (map.isEmpty()) {
            return null;
        }
        return JSON.toJSONString(map);
    }

    /**
     * <p>Description: 取得参数表</p>
     * @param request HttpServletRequest
     * @return 参数
     */
    public static Map<String, String> getParaMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> paraName = request.getParameterNames();
        String key = null;
        while (paraName.hasMoreElements()) {
            key = paraName.nextElement();
            if (1 == request.getParameterValues(key).length) {
                map.put(key, request.getParameter(key));
            } else {
                map.put(key, String.join(",", request.getParameterValues(key)));
            }
        }
        return map;
    }

}
