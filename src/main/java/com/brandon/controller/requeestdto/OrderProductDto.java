package com.brandon.controller.requeestdto;

import lombok.Data;

@Data
public class OrderProductDto {

    private Long productId;
    private int orderQuantity;
}
