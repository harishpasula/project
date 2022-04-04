package com.example.project.repository;

import com.example.project.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductR extends JpaRepository<ProductEntity,Integer> {
}
