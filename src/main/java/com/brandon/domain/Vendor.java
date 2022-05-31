package com.brandon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Vendor implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "vendor_id")
    private Long id;

    private String name;

    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
