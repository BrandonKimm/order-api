package com.brandon.controller.requeestdto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateOrderDto {

    private Long memberId;
    private LocalDateTime orderDate;
    private List<OrderProductDto> orderProducts;

}
