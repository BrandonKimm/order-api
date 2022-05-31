package com.brandon.repository.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QDailyCloseOrderListDto {
    private String email;
    private List<QDailyCloseOrderDto> orderedList;
}
