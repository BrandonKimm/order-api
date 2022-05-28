package com.brandon.controller.responsedto;

import com.brandon.domain.OrderProduct;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDetailResponseDto {

    private Long orderId;
    private List<ProductSimpleResponseDto> orderProducts;
    private int orderTotalPrice;
    private int orderTotalProductQuantity;
    private Enum orderStatus;

    public OrderDetailResponseDto(Long orderId,
                                  List<ProductSimpleResponseDto> orderProducts,
                                  int orderTotalPrice,
                                  int orderTotalQuantity,
                                  Enum orderStatus) {
        this.orderId = orderId;
        this.orderProducts = orderProducts;
        this.orderTotalPrice = orderTotalPrice;
        this.orderTotalProductQuantity = orderTotalQuantity;
        this.orderStatus = orderStatus;
    }
}
