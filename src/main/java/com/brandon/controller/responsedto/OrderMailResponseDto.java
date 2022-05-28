package com.brandon.controller.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderMailResponseDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private String memberEmail;
    private String memberName;
    private List<ProductSimpleResponseDto> orderProducts;
    private int orderTotalPrice;
    private int orderTotalProductQuantity;
}
