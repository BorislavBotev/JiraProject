package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TypeModel;

@Repository
public interface TypeRepository extends JpaRepository<TypeModel, Long> {

}
