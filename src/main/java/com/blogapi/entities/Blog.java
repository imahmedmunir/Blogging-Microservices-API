package com.blogapi.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	private String title;
	
	@Column(length = 10000)
	private String content;
	private String image; 
	private Date date;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Category category;

	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
}
