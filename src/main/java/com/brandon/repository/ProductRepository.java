package com.brandon.repository;

import com.brandon.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //@Query("select p from Product p join fetch p.category join fetch p.vendor ")
    //@Override
    @EntityGraph(attributePaths = {"vendor", "category"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Product> findAll();

    //@Override
    @EntityGraph(attributePaths = {"vendor", "category"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<Product> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"vendor", "category"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Product> findById(Long productId);
}
