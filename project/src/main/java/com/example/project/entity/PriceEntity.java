package com.example.project.entity;

import javax.persistence.*;

@Entity
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skuCode;
    private Double price;
    @OneToOne(cascade = CascadeType.ALL)
    private SkuEntity skuEntity;

    public Integer getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(Integer skuCode) {
        this.skuCode = skuCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SkuEntity getSkuEntity() {
        return skuEntity;
    }

    public void setSkuEntity(SkuEntity skuEntity) {
        this.skuEntity = skuEntity;
    }
}
