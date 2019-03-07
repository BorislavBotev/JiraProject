package com.example.demo.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;

@Component
public class UserDAO {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JdbcTemplate template;
	private List<User> users;
	
	
	public User login(LoginDTO user)  {
		
		return userRepository.findAll().stream().filter(u->u.getUsername().equals(user.getUsername())
				&& u.getPassword().equals(user.getPassword())).findAny().get();
	}

	public User getCurrentUser(HttpServletRequest request) {
		Long id=(Long) request.getSession().getAttribute("userId");
		return userRepository.findAll().stream().filter(u->u.getId().equals(id)).findAny().get();
	
	}
}
