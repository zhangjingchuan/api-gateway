package com.mangmangbang.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *
 * 后置过滤器
 * created by zhangjingchuan on 2020/1/8
 */
@Component
public class AddResponseHeaderFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1 ;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //通过zuul封装的RequestContext获取上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取response
        HttpServletResponse response = requestContext.getResponse();
        //想头信息中设置x-Foo的值为一个随机数
        response.setHeader("x-Foo",UUID.randomUUID().toString());
        return null;
    }
}
