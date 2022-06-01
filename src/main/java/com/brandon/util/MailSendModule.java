package com.brandon.util;

import com.brandon.controller.responsedto.OrderMailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendModule {
    private final String FOR_ORDER_COMPLETE_QUEUE = "FOR_ORDER_COMPLETE_QUEUE";
    private final String FOR_ORDERED_COMPLETE_QUEUE = "FOR_ORDERED_COMPLETE_QUEUE";

    private final RabbitTemplate rabbitTemplate;

    public void sendToMember(OrderMailResponseDto orderComplete) {
        this.rabbitTemplate.convertAndSend(FOR_ORDER_COMPLETE_QUEUE, orderComplete);
    }


    public void sendToVendor(OrderMailResponseDto orderedComplete) {
        this.rabbitTemplate.convertAndSend(FOR_ORDERED_COMPLETE_QUEUE, orderedComplete);
    }
}
