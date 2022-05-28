package com.brandon.controller.responsedto;

import com.brandon.domain.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private Long vendorId;
    private String vendorName;
    private Long categoryId;
    private String categoryName;

    public ProductResponseDto(Long id, String name, int price, int stockQuantity, Long vendorId, String vendorName, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public ProductResponseDto(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // ProductEntityToDto
    public  ProductResponseDto productEntityToDto(Product p) {
        return new ProductResponseDto(p.getId(),
                p.getName(),
                p.getPrice(),
                p.getStockQuantity(),
                p.getVendor().getId(),
                p.getVendor().getName(),
                p.getCategory().getId(),
                p.getCategory().getName()
        );
    }
}
