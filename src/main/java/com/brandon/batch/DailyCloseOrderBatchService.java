package com.brandon.batch;

import com.brandon.repository.responseDto.QDailyCloseOrderDto;
import com.brandon.service.MailService;
import com.brandon.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.OrderedSetType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DailyCloseOrderBatchService {

    private final MailService mailService;
    private final OrderService orderService;

//    @Scheduled(cron = "0 0 17 * * *")
    //@Scheduled(fixedDelay=60000) //1분
    public void closeOrder(){

        LocalDateTime start = LocalDate.now().minusDays(1).atTime(17,0,0,0);
        LocalDateTime end = LocalDate.now().atTime(16,59,59,1000000000-1);
        Map<String, List<QDailyCloseOrderDto>> orderedListByVendor = orderService.closeOrder(start, end);
        Set<String> vendorEmailList = orderedListByVendor.keySet();

        vendorEmailList.stream().forEach(email -> mailService.sendToVendor(email,orderedListByVendor.get(email)));

    }

}
