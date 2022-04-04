package com.example.project.repository;

import com.example.project.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockR extends JpaRepository<StockEntity,Integer> {
}
