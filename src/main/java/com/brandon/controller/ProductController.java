package com.brandon.controller;

import com.brandon.controller.responsedto.ProductResponseDto;
import com.brandon.domain.Product;
import com.brandon.service.ProductCacheService;
import com.brandon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductCacheService productCacheService;



    // 상품목록 전체 5개짜리 페이징해서 보여주는 컨트롤러
    @GetMapping("/products/paging")
    public Page<ProductResponseDto> findAllByPage(@RequestParam(defaultValue = "1") Integer page) {
        if (page.equals(1)) {
            return  productCacheService.getFirstPostPage()
                    .map(p -> new ProductResponseDto().productEntityToDto(p));
        } else {
            return productService.getPageProductList(page)
                    .map(p -> new ProductResponseDto().productEntityToDto(p));
        }
    }

    @GetMapping("/products")
    public List<ProductResponseDto> findAll(){
        System.out.println(LocalDateTime.now().minusMonths(1).getMonthValue());
        return productService.getProductList()
                .stream()
                .map(p -> new ProductResponseDto().productEntityToDto(p))
                .collect(Collectors.toList());
    }
}
