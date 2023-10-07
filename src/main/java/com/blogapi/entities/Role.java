package com.blogapi.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Role {

	@Id
	private int id;
	private String role;
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	
	
	
}
