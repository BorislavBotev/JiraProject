package com.example.demo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.AddUserDTO;
import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.exceptions.UserException;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;

@Component
public class UserDAO {
	private static final int MIN_PASSWORD_LENGHT = 6;
	private static final String createUserQuery = "INSERT INTO users VALUES(null, ?, ?, ?, sha1(?), ?)";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JdbcTemplate userTemplate;
	
	
	public User login(LoginDTO user) throws UserException  {
		User foundUser =  userRepository.findAll().stream()
								.filter(u->u.getUsername().equals(user.getUsername()))
								.findFirst()
								.get();
		if(passwordVerification(user.getPassword(), foundUser.getPassword())) {
			return foundUser;
		}
		throw new UserException("Wrong username or password!");
	}

	public User getCurrentUser(HttpServletRequest request) {
		Long id=(Long) request.getSession().getAttribute("userId");
		return userRepository.findAll().stream().filter(u->u.getId().equals(id)).findAny().get();
	}
	
	public Long createNewUser(AddUserDTO newUser) throws SQLException, UserException {
		PreparedStatement ps = userTemplate.getDataSource().getConnection().prepareStatement(createUserQuery);
		String email = newUser.getEmail();
		String username = newUser.getUsername();
		String password = newUser.getPassword();
		
		if(email==null || email.trim().length()==0) {
			throw new UserException("Invalid Email!");
		}
		if((userRepository.findAll().stream().filter(user -> user.getEmail().equals(email)).count()) > 0) {
			throw new UserException("Email is already taken!");
		}
		if(email==null || email.trim().length()==0) {
			throw new UserException("Invalid username!");
		}
		if((userRepository.findAll().stream().filter(user -> user.getEmail().equals(username)).count()) > 0) {
			throw new UserException("Username is already taken!");
		}
		if(password==null || password.trim().length() < MIN_PASSWORD_LENGHT) {
			throw new UserException("Invalid password!");
		}
		
		ps.setString(1, email);
		ps.setString(2, username);
		ps.setString(3, newUser.getFullName());
		ps.setString(4, password);
		ps.setBoolean(5, newUser.isAdmin());
		ps.executeUpdate();
		Long userID = (long) Statement.RETURN_GENERATED_KEYS;
		ps.close();
		return userID;
	}
	
	public void changePassword(ChangePasswordDTO newPassword, User user) throws UserException {
		if(newPassword.getOldPassword() == null || newPassword.getNewPassword() == null || newPassword.getNewPasswordConfirm() == null) {
			throw new UserException("Invalid Data!");
		}
		if(!passwordVerification(newPassword.getOldPassword(), user.getPassword())) {
			throw new UserException("Wrong Password!");
		}
		if(!newPassword.getNewPassword().equals(newPassword.getNewPasswordConfirm())) {
			throw new UserException("New passwords are not the same!");
		}
		if(newPassword.getNewPassword().length() < MIN_PASSWORD_LENGHT) {
			throw new UserException("Password is too short!");
		}
		String newPass = DigestUtils.shaHex(newPassword.getNewPassword());
		user.setPassword(newPass);
		userRepository.save(user);
	}
	
	public boolean passwordVerification(String loginPass, String cryptedPass){
		return DigestUtils.shaHex(loginPass).equals(cryptedPass);
	}
}
