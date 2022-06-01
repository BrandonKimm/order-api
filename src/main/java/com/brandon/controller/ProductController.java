package com.brandon.controller;

import com.brandon.controller.responsedto.ProductResponseDto;
import com.brandon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products/paging")
    public Page<ProductResponseDto> findAllByPage(@RequestParam(defaultValue = "1") int page) {

        return productService.findAllByPage(page)
                .map(p -> new ProductResponseDto().productEntityToDto(p));

    }

    @GetMapping("/products")
    public List<ProductResponseDto> findAll(){
        return productService.findAll()
                .stream()
                .map(p -> new ProductResponseDto().productEntityToDto(p))
                .collect(Collectors.toList());
    }

}
