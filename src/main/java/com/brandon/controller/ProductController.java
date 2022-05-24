package com.brandon.controller;

import com.brandon.domain.Product;
import com.brandon.service.ProductCacheService;
import com.brandon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductCacheService productCacheService;

    // 상품목록 전체 5개짜리 페이징해서 보여주는 컨트롤러
    @GetMapping("/products/paging")
    public Page<Product> getProductPageList(@RequestParam(defaultValue = "1") Integer page) {
        if (page.equals(1)) {
            return productCacheService.getFirstPostPage();
        } else {
            return productService.getPageProductList(page);
        }
    }

    @GetMapping("/products")
    public List<Product> getProductList(){
        return productService.getProductList();
    }
}
