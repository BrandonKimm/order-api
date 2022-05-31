package com.brandon.exception;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {

        super("재고수량보다 더 많은 주문수량입니다. ("+message+")");
        log.error("재고수량보다 더 많은 주문수량입니다. ("+message+")");
    }

}
