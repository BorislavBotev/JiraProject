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

import com.example.demo.exceptions.ActivityException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "activities_log")
public class Activity {
	
	@Id
	@Column(name = "activity_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "activity_content")
	private String text;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public void setText(String text) throws ActivityException {
		if(text != null && text.trim().length() > 0) {
			this.text = text;
		} else throw new ActivityException("Invalid Content!");
	}
}
