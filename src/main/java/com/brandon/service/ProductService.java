package com.brandon.service;

import com.brandon.domain.Product;
import com.brandon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final static int PAGE_SIZE = 5;

    @Cacheable(value ="findAllByPage", key = "#page", condition = "#page == 1")
    public Page<Product> findAllByPage(int page) {
        return productRepository.findAll(PageRequest.of(page - 1, getPageSize(), Sort.by("id").descending()));
    }
    
    public Optional<Product> findOne(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public static Integer getPageSize(){ return PAGE_SIZE; }

}
