package com.brandon.repository.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QMonthlySettlementClosingDto {
    private Long memberId;
    private String memberName;
    private String orderDate;
    private Long vendorId;
    private String vendorName;
    private Long categoryId;
    private String categoryName;
    private int productOrderQuantity;
    private int productOrderPrice;

}
