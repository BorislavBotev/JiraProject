package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Worklog;

public interface WorklogRepository extends JpaRepository<Worklog, Long>{

}
