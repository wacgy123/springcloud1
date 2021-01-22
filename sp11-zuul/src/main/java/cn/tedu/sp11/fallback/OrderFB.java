package cn.tedu.sp11.fallback;

import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class OrderFB implements FallbackProvider {
    // 设置针对哪个服务进行降级
    // 如果返回null或*表示针对所有服务都应用当前降级类
    @Override
    public String getRoute() {
        return "order-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.toString();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                String result = JsonResult.err().code(500).msg("调用订单服务失败").toString();
                return new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders=new HttpHeaders();;
                httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
                return httpHeaders;
            }
        };
    }
}
