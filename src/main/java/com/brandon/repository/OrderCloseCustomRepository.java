package com.brandon.repository;

import com.brandon.domain.*;
import com.brandon.repository.responseDto.QDailyCloseOrderDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.brandon.domain.QMember.*;
import static com.brandon.domain.QOrder.order;
import static com.brandon.domain.QOrderProduct.*;
import static com.brandon.domain.QProduct.*;
import static com.brandon.domain.QVendor.*;

@Repository
@RequiredArgsConstructor
public class OrderCloseCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<QDailyCloseOrderDto> findVendor(LocalDateTime start, LocalDateTime end) {

        return jpaQueryFactory
                .select(Projections.fields(QDailyCloseOrderDto.class,
                        vendor.id.as("vendorId"),
                        vendor.name.as("vendorName"),
                        vendor.email.as("vendorEmail"),
                        member.id.as("memberId"),
                        member.name.as("memberName"),
                        member.address.as("memberAddress"),
                        product.id.as("productId"),
                        product.name.as("productName"),
                        product.price.as("productPrice"),
                        orderProduct.productOrderQuantity.sum().as("sumQuantity"),
                        (orderProduct.productOrderQuantity.multiply(product.price)).sum().as("sumPrice")
                ))
                .from(order)
                .join(order.member, member)
                .join(order.orderProducts, orderProduct)
                .join(orderProduct.product, product)
                .join(product.vendor, vendor)
                .where(order.orderDate.between(start, end)
                        .and(order.orderStatus.eq(OrderStatus.ORDER)))
                .groupBy(vendor.id,
                        vendor.name,
                        member.id,
                        member.name,
                        member.address,
                        product.id,
                        product.name,
                        product.price)
                .fetch();


    }
}
