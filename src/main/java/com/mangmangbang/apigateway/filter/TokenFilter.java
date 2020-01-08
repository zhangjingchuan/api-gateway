package com.mangmangbang.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * zuul的前置过滤器
 * created by zhangjingchuan on 2020/1/8
 */
@Component
public class TokenFilter extends ZuulFilter {

    /**
     * 过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤顺序
     * 在PRE_DECORATION_FILTER_ORDER 的前面
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //通过zuul封装的RequestContext获取上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取request请求
        HttpServletRequest request = requestContext.getRequest();
        //从url参数中获取token值
        String token = request.getParameter("token");
        //判断token的值
        if(StringUtils.isEmpty(token)){
            //设置不通过
//            requestContext.setSendZuulResponse(false);
//            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
