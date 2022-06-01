package com.brandon.batch;

import com.brandon.controller.responsedto.OrderMailResponseDto;
import com.brandon.domain.Order;
import com.brandon.domain.OrderStatus;
import com.brandon.repository.OrderCloseCustomRepository;
import com.brandon.repository.OrderRepository;
import com.brandon.repository.responseDto.QDailyCloseOrderDto;
import com.brandon.repository.responseDto.QDailyCloseOrderListDto;
import com.brandon.util.MailSendModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DailyCloseOrderBatch {

    private final MailSendModule mailSendModule;
    private final OrderRepository orderRepository;
    private final OrderCloseCustomRepository orderCloseCustomRepository;

    private final LocalDateTime START_DATE_TIME = LocalDate.now().minusDays(1).atTime(17,0,0,0);
    private final LocalDateTime END_DATE_TIME = LocalDate.now().atTime(16, 59, 59, 1000000000 - 1);

    //@Scheduled(fixedDelay=60000) //1분
    //@Scheduled(cron = "0 0 17 * * *")
    @Transactional
    public void closeOrder(){
        List<Order> orders = orderRepository.findAllByOrderDateBetween(START_DATE_TIME, END_DATE_TIME);
        List<Order> orderedList =orders.stream()
                .filter(o -> o.getOrderStatus().equals(OrderStatus.ORDER))
                .collect(Collectors.toList());

        List<QDailyCloseOrderDto> closeOrderDtoList = orderCloseCustomRepository.findVendor(START_DATE_TIME, END_DATE_TIME);

        orderedList.forEach(o -> o.setOrderStatus(OrderStatus.ORDERED));

        Map<String, List<QDailyCloseOrderDto>> collect = closeOrderDtoList.stream().collect(Collectors.groupingBy(dto -> dto.getVendorEmail()));

        collect.keySet().forEach(key -> mailSendModule.sendToVendor(new OrderMailResponseDto(key,
                collect.get(key).get(0).getVendorName() + "금일 주문내역 확인메일",
                new QDailyCloseOrderListDto(key, collect.get(key)).toString())));

    }

}
