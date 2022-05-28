package com.brandon.controller.responsedto;

import lombok.Data;

@Data
public class ProductSimpleResponseDto {

    private Long orderProductId;
    private String name;
    private int price;
    private int orderProductPrice;
    private int orderProductQuantity;

    public ProductSimpleResponseDto(Long orderProductId, String name, int price, int orderProductPrice, int orderProductQuantity) {
        this.orderProductId = orderProductId;
        this.name = name;
        this.price = price;
        this.orderProductPrice = orderProductPrice;
        this.orderProductQuantity = orderProductQuantity;
    }
}
