package com.brandon.repository.responseDto;

import lombok.Data;

@Data
public class QDailyCloseOrderDto {

    private Long vendorId;
    private String vendorName;
    private String vendorEmail;
    private Long memberId;
    private String memberName;
    private String memberAddress;
    private Long productId;
    private String productName;
    private int productPrice;
    private int sumQuantity;
    private int sumPrice;


}
