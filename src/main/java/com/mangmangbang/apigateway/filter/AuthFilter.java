package com.mangmangbang.apigateway.filter;

import com.mangmangbang.apigateway.constant.RedisConstant;
import com.mangmangbang.apigateway.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * 权限拦截（区分买家和卖家）
 * created by zhangjingchuan on 2020/1/8
 */
@Component
public class AuthFilter extends ZuulFilter {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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

        //获取请求地址
        String requestURI = request.getRequestURI();
        /**
         * /order/create 只能买家访问(cookie里有openid)
         * /order/finish 只能卖家访问(cookie里有token，并且对应的redis中有值)
         * /product/list 都可访问
         *
         */
        if("/order/order/create".equals(requestURI)){
            Cookie cookie = CookieUtil.get(request, "openid");
            if(cookie==null||StringUtils.isEmpty(cookie.getValue())){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        if("/order/order/finish".equals(requestURI)){
            Cookie cookie = CookieUtil.get(request, "token");
            if(cookie==null||StringUtils.isEmpty(cookie.getValue())||StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }
        return null;
    }
}
