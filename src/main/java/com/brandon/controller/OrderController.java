package com.brandon.controller;

import com.brandon.controller.requeestdto.CreateOrderDto;
import com.brandon.controller.responsedto.OrderDetailResponseDto;
import com.brandon.controller.responsedto.OrderListResponseDto;
import com.brandon.controller.responsedto.OrderMailResponseDto;
import com.brandon.controller.responsedto.ProductSimpleResponseDto;
import com.brandon.domain.*;
import com.brandon.exception.ResourceNotFoundException;
import com.brandon.repository.OrderCloseCustomRepository;
import com.brandon.service.MailService;
import com.brandon.service.MemberService;
import com.brandon.service.OrderService;
import com.brandon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final ProductService productService;
    private final MailService mailService;

    @PostMapping("/order")
    public Order createOrder(@RequestBody CreateOrderDto createOrderDto) {

        Member member = memberService.findById(createOrderDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("요청하신 멤버 ID: "+createOrderDto.getMemberId()));

        List<OrderProduct> orderProducts = createOrderDto.getOrderProducts()
                .stream()
                .map(dto -> new OrderProduct(productService.getProduct(dto.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("요청하신 상품 ID: "+dto.getProductId())),
                        dto.getOrderQuantity()))
                .collect(Collectors.toList());

        Order order = Order.createOrder(member, orderProducts);
        order = orderService.order(order, member, orderProducts);


        mailService.sendToMember( new OrderMailResponseDto(
                member.getEmail(),
                member.getName()+"님 주문내역 확인메일",
                order.getId().toString()+
                order.getOrderDate()+
                member.getEmail()+
                member.getName()+
                order.getOrderProducts()
                        .stream()
                        .map(p -> new ProductSimpleResponseDto(
                                p.getId(),
                                p.getProduct().getName(),
                                p.getProduct().getPrice(),
                                p.getTotalPrice(),
                                p.getProductOrderQuantity()))
                        .collect(Collectors.toList()).toString()+
                order.getTotalPrice()+
                order.getTotalQuantity()));

        return order;

    }

    @GetMapping("/orders/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @GetMapping("/orders/{orderId}/{orderProductId}/update/{orderProductUpdateQuantity}")
    public void updateProductQuantity(@PathVariable("orderId") Long orderId,
                                      @PathVariable("orderProductId") Long orderProductId,
                                      @PathVariable("orderProductUpdateQuantity") int orderProductUpdateQuantity) {
        orderService.orderUpdateProductQuantity(orderId, orderProductId,orderProductUpdateQuantity);
    }

    @GetMapping("/orders")
    public List<OrderListResponseDto> findAll() {
        return orderService.findAll()
                .stream()
                .map(o-> new OrderListResponseDto(
                        o.getId(),
                        o.getOrderDate(),
                        o.getTotalQuantity(),
                        o.getTotalPrice(),
                        o.getOrderStatus()))
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/{orderId}")
    public OrderDetailResponseDto findOne(@PathVariable("orderId") Long orderId) {

        Order order = orderService.findOne(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("요청하신 주문 ID: "+orderId));

        return new OrderDetailResponseDto(
                order.getId(),
                order.getOrderProducts()
                        .stream()
                        .map(p -> new ProductSimpleResponseDto(
                                p.getId(),
                                p.getProduct().getName(),
                                p.getProduct().getPrice(),
                                p.getTotalPrice(),
                                p.getProductOrderQuantity()))
                        .collect(Collectors.toList()),
                order.getTotalPrice(),
                order.getTotalQuantity(),
                order.getOrderStatus());
    }

}
