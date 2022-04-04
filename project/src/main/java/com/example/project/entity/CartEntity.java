package com.example.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartCode;
    private Integer quantity;
    @JsonIgnore
    @OneToOne
    private SkuEntity skuEntity;

    public Integer getCartCode() {
        return cartCode;
    }

    public void setCartCode(Integer cartCode) {
        this.cartCode = cartCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public SkuEntity getSkuEntity() {
        return skuEntity;
    }

    public void setSkuEntity(SkuEntity skuEntity) {
        this.skuEntity = skuEntity;
    }


}
