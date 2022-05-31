package com.brandon.service;

import com.brandon.domain.*;
import com.brandon.exception.ResourceNotFoundException;
import com.brandon.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

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
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("요청하신 주문 ID: "+orderId));
        order.cancel();
    }

    @Transactional
    public void orderUpdateProductQuantity(Long orderId, Long orderProductId,int orderProductUpdateQuantity) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getOrderStatus().equals(OrderStatus.ORDER))
                .orElseThrow(() -> new ResourceNotFoundException("요청하신 주문 ID: "+orderId));

        OrderProduct orderProduct =order.getOrderProducts()
                .stream()
                .filter(op -> op.getId().equals(orderProductId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("요청하신 주문 ID: "+orderId));

        orderProduct.updateProductOrderQuantity(orderProductUpdateQuantity);
    }

}
