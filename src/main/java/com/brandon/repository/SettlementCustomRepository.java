package com.brandon.repository;

import com.brandon.domain.*;
import com.brandon.repository.responseDto.QMonthlySettlementClosingDto;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


import static com.brandon.domain.QCategory.category;
import static com.brandon.domain.QMember.member;
import static com.brandon.domain.QOrder.order;
import static com.brandon.domain.QOrderProduct.orderProduct;
import static com.brandon.domain.QProduct.product;
import static com.brandon.domain.QVendor.vendor;


@Repository
@RequiredArgsConstructor
public class SettlementCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public List<QMonthlySettlementClosingDto> settlement(LocalDateTime start, LocalDateTime end) {

        StringTemplate orderDateTemplate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", order.orderDate, ConstantImpl.create("%Y%c%d"));
        return jpaQueryFactory
                .select(Projections.fields(QMonthlySettlementClosingDto.class,
                        member.id.as("memberId"),
                        member.name.as("memberName"),
                        orderDateTemplate.as("orderDate"),
                        vendor.id.as("vendorId"),
                        vendor.name.as("vendorName"),
                        category.id.as("categoryId"),
                        category.name.as("categoryName"),
                        orderProduct.productOrderQuantity.sum().as("productOrderQuantity"),
                        (orderProduct.productOrderQuantity.multiply(product.price)).sum().as("productOrderPrice"))
                )
                .from(order)
                .join(order.member, member)
                .join(order.orderProducts, orderProduct)
                .join(orderProduct.product, product)
                .join(product.category, category)
                .join(product.vendor, vendor)
                .where(order.orderStatus.eq(OrderStatus.valueOf("ORDERED"))
                        .and(order.orderDate.between(start,end)))
                .groupBy(member.id,
                        member.name,
                        orderDateTemplate,
                        vendor.id,
                        vendor.name,
                        category.id,
                        category.name)
                .fetch();
    }

}
