package com.example.project.repository;

import com.example.project.entity.CartEntity;
import com.example.project.entity.SkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartR extends JpaRepository<CartEntity,Integer> {
}
