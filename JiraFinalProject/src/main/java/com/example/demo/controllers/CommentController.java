package com.example.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.check.UserCheck;
import com.example.demo.dto.AddCommentDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.UserException;
import com.example.demo.service.CommentService;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserCheck userCheck;

	@PostMapping("issues/{issueId}/addComment")
	public void addCommentToIssue(@PathVariable long issueId, @RequestBody AddCommentDTO comment, HttpServletRequest request, HttpServletResponse response) {
		if(!userCheck.isLoggedIn(request, response)) {
			return;
		}
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		try {
			commentService.addCommentToIssue(issueId,  userId, comment);
		} catch (IssueException | UserException | CommentException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.getMessage();
		}
	}
	
	@GetMapping("issues/{issueId}/comments")
	public List<CommentDTO> getIssueComments(@PathVariable long issueId,  HttpServletRequest request, HttpServletResponse response) {
		if(!userCheck.isLoggedIn(request, response)) {
			return null;
		}
		try {
			return commentService.getIssueComments(issueId);
		} catch (IssueException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
}
