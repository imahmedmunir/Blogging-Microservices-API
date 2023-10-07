package com.blogapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogapi.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User as u where u.email =:email")
	  Optional<User> findByEmail(String email);
}
