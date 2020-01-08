package com.mangmangbang.apigateway.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * created by zhangjingchuan on 2020/1/8
 */
@Slf4j
public class RateLimitException extends RuntimeException {

    public RateLimitException(String message){
        log.error("异常了，限流了"+message);
    }
}
