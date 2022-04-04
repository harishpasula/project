package com.example.project.entity;

import javax.persistence.*;

@Entity
public class SkuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skuCode;
    private String size;
    @ManyToOne(cascade = CascadeType.ALL)
    private ProductEntity productEntity;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "skuEntity")
    private PriceEntity priceEntity;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "skuEntity")
    private StockEntity stockEntity;
    @OneToOne(mappedBy = "skuEntity")
    private CartEntity cartEntity;


    public Integer getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(Integer skuCode) {
        this.skuCode = skuCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public PriceEntity getPriceEntity() {
        return priceEntity;
    }

    public void setPriceEntity(PriceEntity priceEntity) {
        this.priceEntity = priceEntity;
    }

    public StockEntity getStockEntity() {
        return stockEntity;
    }

    public void setStockEntity(StockEntity stockEntity) {
        this.stockEntity = stockEntity;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }
}
