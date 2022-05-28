package com.brandon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    private int productOrderQuantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    public OrderProduct(Product product, int productOrderQuantity) {
        this.productOrderQuantity = productOrderQuantity;
        this.product = product;

        product.removeStock(productOrderQuantity);
    }

    //주문 상품 당 총 금액 조회
    public int getTotalPrice(){
        return product.getPrice() * getProductOrderQuantity();
    }

    public void cancel() {
        this.getProduct().addStock(productOrderQuantity);
        this.productOrderQuantity = 0;
    }

    // 주문상품수량변경
    public void updateProductOrderQuantity(int orderProductUpdateQuantity) {

        if(orderProductUpdateQuantity  < 0 ){
            new IllegalArgumentException("0 이상의 숫자로 변경하세요");
        }
        if(this.productOrderQuantity == orderProductUpdateQuantity){
            return;
        }
        if(this.productOrderQuantity < orderProductUpdateQuantity){
            this.getProduct().removeStock(orderProductUpdateQuantity - productOrderQuantity);
        }
        if(this.productOrderQuantity > orderProductUpdateQuantity){
            this.getProduct().addStock(productOrderQuantity - orderProductUpdateQuantity);
        }
        this.setProductOrderQuantity(orderProductUpdateQuantity);
    }

}
