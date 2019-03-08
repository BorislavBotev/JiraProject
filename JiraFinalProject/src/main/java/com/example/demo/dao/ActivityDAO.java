package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.ActivityRepository;

@Component
public class ActivityDAO {

	@Autowired
	private ActivityRepository activityRepository;
	
//	public void addActivity() {
//	
//	}
}
