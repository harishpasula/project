package com.example.project.repository;

import com.example.project.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderR extends JpaRepository<OrderEntity,Integer> {
}
