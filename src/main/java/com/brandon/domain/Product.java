package com.brandon.domain;

import com.brandon.exception.NotEnoughStockException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {

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

    /*
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();*/

    public void removeStock(int productOrderQuantity) {
        int stockQuantity = this.stockQuantity - productOrderQuantity;

        if (stockQuantity < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = stockQuantity;
    }

    public void addStock(int productOrderQuantity) {
        System.out.println(this.stockQuantity);
        this.stockQuantity += productOrderQuantity;
        System.out.println(this.stockQuantity);
    }
}
