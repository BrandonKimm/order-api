package com.brandon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private String memberName;
    private String orderDate;
    private Long vendorId;
    private String vendorName;
    private Long categoryId;
    private String categoryName;
    private int productOrderQuantity;
    private int productOrderPrice;

    public Settlement(Long memberId, String memberName, String orderDate, Long vendorId, String vendorName, Long categoryId, String categoryName, int productOrderQuantity, int productOrderPrice) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productOrderQuantity = productOrderQuantity;
        this.productOrderPrice = productOrderPrice;
    }
}
