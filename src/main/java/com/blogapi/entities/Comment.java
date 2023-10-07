package com.blogapi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int commentId;
	
	private String comment;

	@ManyToOne
	@JoinColumn(name = "blog_id")
	private Blog blog;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
}

