package com.brandon.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super("요청하신 데이터는 잘못 된 데이터 입니다. ("+message+")");
        log.error("요청하신 데이터는 잘못 된 데이터 입니다. ("+message+")");
    }
}
