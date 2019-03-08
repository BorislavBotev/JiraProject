package com.example.demo.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.AddUserDTO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.exceptions.UserException;
import com.example.demo.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private UserCheck userCheck;
	
	@PostMapping("/login")
	public User login(@RequestBody LoginDTO loginUser,HttpServletRequest request) throws UserException {

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
	
	
	@PostMapping("/createUser")
	public void createUser(@RequestBody AddUserDTO newUser, HttpServletRequest request, HttpServletResponse response) {
		if(!userCheck.isLoggedIn(request, response)) {
			return;
		}
		if(!userCheck.isAdmin(request, response)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		try {
			userDao.createNewUser(newUser);
		} catch (SQLException | UserException e) {
			System.out.println(e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
		}
	}
	
	
	@PostMapping("/changePassword")
	public void changePassword() {
		
	}
}
