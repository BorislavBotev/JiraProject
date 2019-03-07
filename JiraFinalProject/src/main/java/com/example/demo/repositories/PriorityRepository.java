package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PriorityModel;

@Repository
public interface PriorityRepository extends JpaRepository<PriorityModel, Long>{

}
