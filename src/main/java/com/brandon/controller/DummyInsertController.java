package com.brandon.controller;

import com.brandon.domain.Category;
import com.brandon.domain.Member;
import com.brandon.domain.Product;
import com.brandon.domain.Vendor;
import com.brandon.repository.CategoryRepository;
import com.brandon.repository.MemberRepository;
import com.brandon.repository.ProductRepository;
import com.brandon.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dummy")
public class DummyInsertController {

    private final MemberRepository memberRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @PostMapping("/members/new")
    public Member saveMember(@RequestBody Member member){
        return memberRepository.save(member);
    }
    @PostMapping("/vendors/new")
    public Vendor saveMember(@RequestBody Vendor vendor){
        return vendorRepository.save(vendor);
    }
    @PostMapping("/categories/new")
    public Category saveMember(@RequestBody Category category){
        return categoryRepository.save(category);
    }
    @PostMapping("/products/new")
    public Product saveMember(@RequestBody Product product, @RequestParam Long categoryId, @RequestParam Long vendorId){

        Product p = new Product();
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setStockQuantity(product.getStockQuantity());
        p.setCategory(categoryRepository.findById(categoryId).get());
        p.setVendor(vendorRepository.findById(vendorId).get());
        return productRepository.save(p);
    }

}
