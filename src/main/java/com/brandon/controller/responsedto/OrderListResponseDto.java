package com.brandon.controller.responsedto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderListResponseDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private int totalProductQuantity;
    private int totalOrderPrice;
    private Enum orderStatus;

    public OrderListResponseDto(Long orderId,
                                LocalDateTime orderDate,
                                int totalProductQuantity,
                                int totalOrderPrice,
                                Enum orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalProductQuantity = totalProductQuantity;
        this.totalOrderPrice = totalOrderPrice;
        this.orderStatus = orderStatus;
    }
}
