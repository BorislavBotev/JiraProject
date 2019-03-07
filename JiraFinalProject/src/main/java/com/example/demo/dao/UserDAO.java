package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserRepository;
import com.example.demo.model.User;

@Component
public class UserDAO {
	@Autowired
	private UserRepository repository;
	@Autowired
	private JdbcTemplate template;
	private List<User> users;
	
	
	public User login(LoginDTO user)  {
		
		return repository.findAll().stream().filter(u->u.getUsername().equals(user.getUsername())
				&& u.getPassword().equals(user.getPassword())).findAny().get();
//		Connection con=template.getDataSource().getConnection();
//		PreparedStatement ps=con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
//		ps.setString(1, user.getUsername());
//		ps.setString(2, user.getPassword());
//		ResultSet rs=ps.executeQuery();
//		if(!rs.next()) {
//			System.out.println("nqma");
//			return null;
//		}
//		//System.out.println(new User(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getBoolean(6)));
//		return new User(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getBoolean(6));
//		
		
		
		
		
//		System.out.println(repository.findAll().stream().filter(u->u.getUsername().equals(user.getUsername()) 
//				&& u.getPassword().equals(user.getPassword())).findFirst().get());
//		return repository.findAll().stream().filter(u->u.getUsername().equals(user.getUsername()) 
//				&& u.getPassword().equals(user.getPassword())).findFirst().get();

	}
	public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
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
		return users.stream().filter(u->u.getId().equals(id)).findFirst().get().isAdmin();
	}
	
	
}
