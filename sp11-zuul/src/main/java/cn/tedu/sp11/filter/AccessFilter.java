package cn.tedu.sp11.filter;

import cn.tedu.web.util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFilter extends ZuulFilter {
    // 指定过滤器的类型,"pre","routes","error"
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    // 指定过滤器添加的位置序号,
    @Override
    public int filterOrder() {
        return 6;
    }

    // 判断针对当前这次请求是否要执行过滤代码
    // 如果请求item-service执行判断
    // 如果请求user-service和order-service直接跳过
    @Override
    public boolean shouldFilter() {
        // 获得正在调用的服务的Id
        RequestContext context = RequestContext.getCurrentContext();
        String serviceId = (String)context.get(FilterConstants.SERVICE_ID_KEY);
        return "item-service".equalsIgnoreCase(serviceId);
    }

    // 过滤代码
    @Override
    public Object run() throws ZuulException {
        // 得到Request对象
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            // 阻止继续访问
            context.setSendZuulResponse(false);
            String result = JsonResult.build(JsonResult.NOT_LOGIN).msg("not log in").toString();
            context.addZuulRequestHeader("Content-Type", "application/json;charset=UTF-8");
            context.setResponseBody(result);
        }
        return null;
    }
}
