package com.example.project.repository;

import com.example.project.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceR extends JpaRepository<PriceEntity,Integer> {
}
