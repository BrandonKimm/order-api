package com.brandon.repository;

import com.brandon.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"orderProducts",
            "orderProducts.product",
            "orderProducts.product.category",
            "orderProducts.product.vendor"}, type = EntityGraph.EntityGraphType.FETCH)
    @Override
    Optional<Order> findById(Long orderId);


    @Override
    @EntityGraph(attributePaths = {"orderProducts",
            "orderProducts.product",
            "orderProducts.product.category",
            "orderProducts.product.vendor"}, type = EntityGraph.EntityGraphType.FETCH)
    <S extends Order> S save(S entity);

    List<Order> findAllByOrderDateBetween(LocalDateTime start, LocalDateTime end);
}
