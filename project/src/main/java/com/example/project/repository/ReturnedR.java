package com.example.project.repository;

import com.example.project.entity.ReturnedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnedR extends JpaRepository<ReturnedEntity,Integer> {
}
