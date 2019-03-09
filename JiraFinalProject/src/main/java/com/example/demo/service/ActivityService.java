package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.ActivityRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private UserRepository userRepository;
	
	public void addActivity() {
		
	}
}
