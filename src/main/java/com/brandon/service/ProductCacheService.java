package com.brandon.service;

import com.brandon.domain.Product;
import com.brandon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCacheService {

    private final ProductRepository productRepository;
    private Page<Product> firstProductPage;

    //@Scheduled(fixedDelay=600000) //10분
    public void updateFirstProductPage(){
        firstProductPage = productRepository.findAll(
                PageRequest.of(0, ProductService.getPageSize(), Sort.by("id").descending())
        );
    }

    public Page<Product> getFirstPostPage() {
        return this.firstProductPage;
    }
}
