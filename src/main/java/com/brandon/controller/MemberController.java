package com.brandon.controller;

import com.brandon.controller.responsedto.OrderListResponseDto;
import com.brandon.controller.responsedto.OrderProductListResponseDto;
import com.brandon.domain.Member;
import com.brandon.exception.ResourceNotFoundException;
import com.brandon.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{memberId}/orders")
    public List<OrderListResponseDto> findOrderListByMember(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("요청하신 멤버 ID: "+memberId));

        System.out.println(member.getOrders());

        if(member.getOrders().isEmpty())
            throw new ResourceNotFoundException("요청하신 "+ member.getName() +"님의 주문내역은 존재하지 않습니다");

        return member.getOrders()
                .stream()
                .map(o -> new OrderListResponseDto(o.getId(),
                        o.getOrderDate(),
                        o.getTotalQuantity(),
                        o.getTotalPrice(),
                        o.getOrderStatus()))
                .collect(Collectors.toList());
    }
}
