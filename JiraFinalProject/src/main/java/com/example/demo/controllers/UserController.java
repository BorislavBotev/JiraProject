package com.example.demo.controllers;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserDAO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.exceptions.UserException;
import com.example.demo.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDao;
	
	@PostMapping("/login")
	public User login(@RequestBody LoginDTO loginUser,HttpServletRequest request,HttpServletResponse response) throws UserException {
//		User user;
//		try {
//			user = userDao.login(loginUser);
//			if(user==null) {
//				return null;
//			}
//			HttpSession session=request.getSession();
//			//session.setMaxInactiveInterval(300);
//			session.setAttribute("userId",user.getId());
//			return user;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
			try {
				User user=userDao.login(loginUser);
				HttpSession session=request.getSession();
				session.setAttribute("userId",user.getId());
				return user;
			}
			catch(NoSuchElementException e){
				e.printStackTrace();
				response.setStatus(401);
			}
			return null;
			
		
	}
	
	@PostMapping("/signout")
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
}
