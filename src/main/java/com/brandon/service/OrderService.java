package com.brandon.service;

import com.brandon.domain.*;
import com.brandon.exception.ResourceNotFoundException;
import com.brandon.repository.MemberRepository;
import com.brandon.repository.OrderCloseCustomRepository;
import com.brandon.repository.OrderRepository;
import com.brandon.repository.ProductRepository;
import com.brandon.repository.responseDto.QDailyCloseOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCloseCustomRepository orderCloseCustomRepository;


    @Transactional
    public Order order(Order order,Member member, List<OrderProduct> orderProducts) {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOne(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("노오더"));
        order.cancel();
    }

    @Transactional
    public void orderUpdateProductQuantity(Long orderId, Long orderProductId,int orderProductUpdateQuantity) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getOrderStatus().equals(OrderStatus.ORDER))
                .orElseThrow(() -> new ResourceNotFoundException("노오더"));

        OrderProduct orderProduct =order.getOrderProducts()
                .stream()
                .filter(op -> op.getId().equals(orderProductId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("노아이템"));

        orderProduct.updateProductOrderQuantity(orderProductUpdateQuantity);
    }

    @Transactional
    public Map<String, List<QDailyCloseOrderDto>>  closeOrder(LocalDateTime start, LocalDateTime end) {
        List<Order> orders = orderRepository.findAllByOrderDateBetween(start, end);
        List<Order> orderedList =orders.stream()
                .filter(o -> o.getOrderStatus().equals(OrderStatus.ORDER))
                .collect(Collectors.toList());

        orderedList.stream().forEach(o -> o.setOrderStatus(OrderStatus.ORDERED));

        List<QDailyCloseOrderDto> closeOrderDtoList = orderCloseCustomRepository.findVendor(start, end);

        Map<String, List<QDailyCloseOrderDto>> orderedListGroupByVendorEmail = closeOrderDtoList.stream().collect(Collectors.groupingBy(dto -> dto.getVendorEmail()));

        return orderedListGroupByVendorEmail;

    }
}
