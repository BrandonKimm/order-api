package com.brandon.domain;

import com.brandon.exception.NotEnoughStockException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    public void removeStock(int productOrderQuantity) {
        int stockQuantity = this.stockQuantity - productOrderQuantity;

        if (stockQuantity < 0) {
            throw new NotEnoughStockException("현 재고수량: "+this.stockQuantity+" 주문수량: "+productOrderQuantity);
        }
        this.stockQuantity = stockQuantity;
    }

    public void addStock(int productOrderQuantity) {
        this.stockQuantity += productOrderQuantity;
    }
}
