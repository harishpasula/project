package com.example.project.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ProductEntity {
    @Id
    private Integer productCode;
    private String productName;
    private String description;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "productEntity")
    private List<SkuEntity> skuEntities;

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SkuEntity> getSkuEntities() {
        return skuEntities;
    }

    public void setSkuEntities(List<SkuEntity> skuEntities) {
        this.skuEntities = skuEntities;
    }
}
