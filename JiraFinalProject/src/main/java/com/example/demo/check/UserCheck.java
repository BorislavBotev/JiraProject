package com.example.demo.check;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.repositories.UserRepository;



public class UserCheck {
	private UserRepository repository;
	public boolean isLoggedIn(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
	
		if (session.getAttribute("userId") == null) {
			response.setStatus(401);
			return false;
		}
		return true;
	}
	public  boolean isAdmin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Long id=(Long) session.getAttribute("userId");
		return repository.findAll().stream().filter(u->u.getId().equals(id)).findFirst().get().isAdmin();
	}
}
