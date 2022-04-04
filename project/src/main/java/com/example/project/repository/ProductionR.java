package com.example.project.repository;

import com.example.project.entity.ProductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionR extends JpaRepository<ProductionEntity,Integer> {
}
