package com.blogapi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = "set")
public class Category {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer catId;
		
		@Column(unique = true)
		private String catName;
		private String catDescription;
		
		@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
		private Set<Blog> set = new HashSet<>();
}
