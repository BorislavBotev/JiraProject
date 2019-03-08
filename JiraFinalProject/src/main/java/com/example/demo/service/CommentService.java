package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddCommentDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.exceptions.CommentException;
import com.example.demo.exceptions.IssueException;
import com.example.demo.exceptions.UserException;
import com.example.demo.model.Comment;
import com.example.demo.model.Issue;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class CommentService {

	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	public void addCommentToIssue(long issueId, long userId, AddCommentDTO newComment) throws IssueException, UserException, CommentException {
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		if(!userRepository.findById(userId).isPresent()) {
			throw new UserException("User Not Found!");
		}
		if(newComment==null || newComment.getContent()==null || newComment.getContent().trim().length()==0) {
			throw new CommentException("Invalid Comment!");
		}
		Comment comment = new Comment();
		LocalDateTime updateDate = LocalDateTime.now();
		Issue issue = issueRepository.findById(issueId).get();
		issue.setLastUpdateDate(updateDate);
		issueRepository.save(issue);
		comment.setText(newComment.getContent());
		comment.setIssue(issue);
		comment.setUser(userRepository.findById(userId).get());
		comment.setCreateDate(updateDate);
		commentRepository.save(comment);
	}
	
	public List<CommentDTO> getIssueComments(long issueId) throws IssueException{
		if(!issueRepository.findById(issueId).isPresent()) {
			throw new IssueException("Issue Not Found!");
		}
		List<CommentDTO> comments = commentRepository.findAll().stream()
			.filter(comment -> comment.getIssue().getId() == issueId)
			.map(comment -> new CommentDTO(comment.getUser().getUsername(), comment.getText(), comment.getIssue().getSummary(), comment.getCreateDate()))
			.sorted((c1,c2) -> c1.getDate().compareTo(c2.getDate()))
			.collect(Collectors.toList());
		return comments!=null ? comments : new LinkedList<CommentDTO>();
	}

	public List<CommentDTO> getCurrentUserComments(Long userId) {
		List<CommentDTO> comments = commentRepository.findAll().stream()
				.filter(comment -> comment.getUser().getId() == userId)
				.map(comment -> new CommentDTO(comment.getUser().getUsername(), comment.getText(), comment.getIssue().getSummary(), comment.getCreateDate()))
				.sorted((c1,c2) -> c1.getDate().compareTo(c2.getDate()))
				.collect(Collectors.toList());
		return comments!=null ? comments : new LinkedList<CommentDTO>();
	}
}
