package com.brandon.service;

import com.brandon.controller.responsedto.ProductDto;
import com.brandon.controller.responsedto.ProductListDto;
import com.brandon.domain.Product;
import com.brandon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private static Integer PAGE_SIZE = 5;

    public Page<Product> getPageProductList(Integer page) {
        return productRepository.findAll(PageRequest.of(page - 1, PAGE_SIZE, Sort.by("id")));
    }

    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    public static Integer getPageSize(){
        return PAGE_SIZE;
    }

}
