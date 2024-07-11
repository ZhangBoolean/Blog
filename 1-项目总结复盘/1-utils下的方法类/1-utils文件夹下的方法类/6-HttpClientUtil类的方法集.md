import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class HttpClientUtil {
private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    public static HttpResult doGet(String url) {
        return doGet(url, null, null);
    }

    public static HttpResult doGet(String url, Map<String, String> param, Map<String, String> head) {
        HttpResult httpResult = null;
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            //设置请求头
            if (head != null) {
                for (String key : head.keySet()) {
                    httpGet.setHeader(key, head.get(key));
                }
            }
            // 执行请求
            response = httpclient.execute(httpGet);
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                result = EntityUtils.toString(response.getEntity(), "UTF-8");
//            }
httpResult = buildHttpResult(response);
} catch (Exception e) {
log.error("send get request error", e);
} finally {
try {
if (response != null) {
response.close();
}
} catch (IOException e) {
log.error("close response error", e);
}
}
return httpResult;
}

    public static String doPost(String url) {
        return doPost(url, null, null);
    }

    public static String doPost(String url, Map<String, String> param, Map<String, String> head) {
        String result = null;
        CloseableHttpResponse response = null;

        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //设置请求头
            if (head != null) {
                for (String key : head.keySet()) {
                    httpPost.setHeader(key, head.get(key));
                }
            }
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("send post request error", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("close response error", e);
            }
        }
        return result;
    }

    public static HttpResult doPostJson(String url, Map<String, String> head, String json) {
        HttpResult httpResult = null;
        CloseableHttpResponse response = null;

        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 设置请求头
            if (head != null) {
                for (String key : head.keySet()) {
                    httpPost.setHeader(key, head.get(key));
                }
            }
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 设置请求参数
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpclient.execute(httpPost);
            httpResult = buildHttpResult(response);
        } catch (Exception e) {
            log.error("send post request error", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("close response error", e);
            }
        }
        return httpResult;
    }

    public static HttpResult doPostJsonThrowException(String url, Map<String, String> head, String json) throws IOException {
        HttpResult httpResult;
        CloseableHttpResponse response;
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        if (head != null) {
            for (String key : head.keySet()) {
                httpPost.setHeader(key, head.get(key));
            }
        }
        // 创建请求内容
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        // 设置请求参数
        httpPost.setEntity(entity);
        // 执行http请求
        response = httpclient.execute(httpPost);
        httpResult = buildHttpResult(response);
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            log.error("close response error", e);
        }
        return httpResult;
    }

    public static String doPostXml(String url, Map<String, String> head, String xml) {
        String result = null;
        CloseableHttpResponse response = null;

        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //设置请求头
            if (head != null) {
                for (String key : head.keySet()) {
                    httpPost.setHeader(key, head.get(key));
                }
            }
            // 创建请求内容
            StringEntity entity = new StringEntity(xml, ContentType.create("application/xml", Consts.UTF_8));
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            log.error("send post request error", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("close response error", e);
            }
        }
        return result;
    }

    public static HttpResult doPostXmlReturnResponse(String url, Map<String, String> head, String xml) throws IOException {
        HttpResult httpResult = null;
        CloseableHttpResponse response = null;
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        //设置请求头
        if (head != null) {
            for (String key : head.keySet()) {
                httpPost.setHeader(key, head.get(key));
            }
        }
        // 创建请求内容
        StringEntity entity = new StringEntity(xml, ContentType.create("application/xml", Consts.UTF_8));
        httpPost.setEntity(entity);
        // 执行http请求
        response = httpclient.execute(httpPost);
        httpResult = buildHttpResult(response);
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            log.error("close response error", e);
        }
        return httpResult;
    }




    public static HttpResult buildHttpResult(CloseableHttpResponse response) {
        HttpResult httpResult = new HttpResult();
        if (null == response || null == response.getStatusLine()) {
            return httpResult;
        }
        // 返回状态码
        httpResult.setStatus(response.getStatusLine().getStatusCode());
        try {
            // 返回json格式数据
            httpResult.setMessage(EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch (IOException e) {
            log.error(String.format("将http请求结果转换json格式失败%n%s", Log.exception(e)));
        }
        return httpResult;
    }

    public static String buildHttpResult(Integer status, String message) {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus(status);
        httpResult.setMessage(message);
        return JSONUtils.toJSONString(httpResult);
    }

    public static class HttpResult {
        /**
         * 状态码
         */
        private Integer status;
        /**
         * 描述
         */
        private String message;

        public static final Integer SUCCESS_CODE = 200;
        public static final String SUCCESS_MSG = "ok";
        public static final Integer ERROR_CODE = 500;

        public static HttpResult success() {
            return new HttpResult(SUCCESS_CODE, SUCCESS_MSG);
        }

        public static HttpResult error(String message) {
            return new HttpResult(ERROR_CODE, message);
        }

        public HttpResult(Integer status, String message) {
            this.status = status;
            this.message = message;
        }

        public HttpResult() {
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
