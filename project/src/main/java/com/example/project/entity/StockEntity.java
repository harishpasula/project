package com.example.project.entity;

import javax.persistence.*;

@Entity
public class StockEntity {
    @Id
    private Integer skuCode;
    private Integer quantity;
    @OneToOne(cascade = CascadeType.ALL)
    private SkuEntity skuEntity;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "stockEntity")
    private OrderEntity orderEntity;

    public Integer getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(Integer skuCode) {
        this.skuCode = skuCode;
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

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }


    }

