package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>{

}
