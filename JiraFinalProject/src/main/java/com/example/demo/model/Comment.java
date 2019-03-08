package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.exceptions.CommentException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private long id;
	
	@Column(name = "comment_text")
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "issue_id")
	private Issue issue;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Setter
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	
	public void setText(String text) throws CommentException {
		if(text!=null && text.trim().length()>0) {
			this.text = text;			
		} else throw new CommentException("Invalid Content!");
	}
	
	public void setIssue(Issue issue) throws CommentException {
		if(issue!=null) {
			this.issue = issue;
		} else throw new CommentException("Invalid Issue!");
	}
	
	public void setUser(User user) throws CommentException {
		if(user!=null) {
			this.user = user;
		} else throw new CommentException("Invalid User!");
	}
}
