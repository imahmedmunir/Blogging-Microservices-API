package com.blogapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapi.entities.Blog;
import com.blogapi.entities.Category;
import com.blogapi.entities.User;

public interface BlogRepo extends JpaRepository<Blog, Integer> {

	@Query("select b from Blog b where b.user =:user")
	List<Blog> findByUser(User user);
	
	@Query("from Blog b where b.category =:cat")
	List<Blog> findByCategory(Category cat);
	
	//value = "select * from Blog  where title LIKE :keyword% ", nativeQuery = true
	//@Query(value="select * from Contact  where name LIKE :name% and Status=1 and user_uid=:userId", nativeQuery = true)

	@Query("select b from Blog as b where b.title like :key%")
	List<Blog> findByTitleContaining(@Param("key") String title);

}
