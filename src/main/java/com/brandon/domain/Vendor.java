package com.brandon.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Vendor {

    @Id
    @GeneratedValue
    @Column(name = "vendor_id")
    private Long id;

    private String name;

    private String email;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
