package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Worklog;

public interface WorklogRepository extends JpaRepository<Worklog, Long>{
	public List<Worklog> findAllByUserId(long userId);
	public List<Worklog> findAllByIssueId(long issueId);
}
