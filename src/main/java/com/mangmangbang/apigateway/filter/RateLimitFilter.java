package com.mangmangbang.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.mangmangbang.apigateway.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * 限流（令牌桶模型）
 * created by zhangjingchuan on 2020/1/8
 */
@Component
public class RateLimitFilter extends ZuulFilter {

    //谷歌开源的令牌桶算法
    //每秒钟向令牌桶中放100个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if(!RATE_LIMITER.tryAcquire()){
            throw new RateLimitException(new Date().toString());
        }
        return null;
    }
}
