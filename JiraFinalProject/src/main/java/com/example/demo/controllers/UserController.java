package com.example.demo.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dao.UserDAO;
import com.example.demo.dto.AddUserDTO;
import com.example.demo.dto.ChangePasswordDTO;
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
	public void login(@RequestBody LoginDTO loginUser,HttpServletRequest request, HttpServletResponse response) {
			User user = null;
			try {
				user = userDao.login(loginUser);
			} catch (UserException e) {
				try {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			HttpSession session=request.getSession();
			session.setAttribute("userId",user.getId());			
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
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@PutMapping("/changePassword")
	public void changePassword(@RequestBody ChangePasswordDTO newPassword, HttpServletRequest request, HttpServletResponse response) {
		if(!userCheck.isLoggedIn(request, response)) {
			return;
		}
		User currentUser = userDao.getCurrentUser(request);
		try {
			userDao.changePassword(newPassword, currentUser);
		} catch (UserException e) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
