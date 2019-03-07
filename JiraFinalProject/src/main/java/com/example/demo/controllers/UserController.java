package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
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
	public User login(@RequestBody LoginDTO loginUser,HttpServletRequest request) throws UserException {
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
		
			User user=userDao.login(loginUser);
			if(user==null) {
				throw new UserException("Invalid login");
			}
			HttpSession session=request.getSession();
			session.setAttribute("userId",user.getId());
			return user;
		
	}
	
	@PostMapping("/signout")
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
}
