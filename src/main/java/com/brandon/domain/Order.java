package com.brandon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;




    public static Order createOrder(Member member, List<OrderProduct> orderProducts) {
        Order order = new Order();
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ORDER);
        orderProducts.forEach(op -> order.addOrderItem(op, order));
        return order;
    }

    public void addOrderItem(OrderProduct orderProduct,Order order) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(order);
    }

    //주문 전체 가격 조회
    public int getTotalPrice(){
        return this.orderProducts
                .stream()
                .mapToInt(op -> op.getTotalPrice())
                .sum();
    }

    //주문상품 종류갯수 조회
    public int getTotalQuantity(){
        return this.orderProducts.size();
    }

    public void cancel() {
        if (orderStatus != OrderStatus.ORDER) {
            throw new IllegalStateException("already order completed");
        }
        System.out.println(orderStatus);
        this.setOrderStatus(OrderStatus.CANCEL);
        System.out.println(orderStatus);
        for (OrderProduct op : this.orderProducts) {
            op.cancel();
        }
    }

}
