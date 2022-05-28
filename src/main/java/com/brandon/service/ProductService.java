package com.brandon.service;

import com.brandon.domain.Product;
import com.brandon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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

    private static Integer PAGE_SIZE = 5;

    public Page<Product> getPageProductList(Integer page) {
        return productRepository.findAll(PageRequest.of(page - 1, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public static Integer getPageSize(){
        return PAGE_SIZE;
    }

}
