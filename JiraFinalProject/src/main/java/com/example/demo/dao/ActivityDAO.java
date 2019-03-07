package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Activity;
import com.example.demo.repositories.ActivityRepository;

public class ActivityDAO {

	@Autowired
	private ActivityRepository activityRepository;
	
	public void addActivity() {
//		Activity activity = new Activity(id, text, createDate, user);
	}
}
