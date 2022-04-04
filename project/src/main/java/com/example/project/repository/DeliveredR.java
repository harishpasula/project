package com.example.project.repository;

import com.example.project.entity.DeliveredEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveredR extends JpaRepository<DeliveredEntity,Integer> {

}
