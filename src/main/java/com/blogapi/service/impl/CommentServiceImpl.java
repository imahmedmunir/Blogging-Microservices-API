package com.blogapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.entities.Blog;
import com.blogapi.entities.Comment;
import com.blogapi.entities.User;
import com.blogapi.exception.CheckedException;
import com.blogapi.payloads.CommentDto;
import com.blogapi.repositories.BlogRepo;
import com.blogapi.repositories.CommentRepo;
import com.blogapi.repositories.UserRepository;
import com.blogapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private BlogRepo blogRepo;
	
	@Autowired
	private CommentRepo commRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CommentDto addComment(CommentDto comDto, Integer blogId, Integer userId) {
		Blog blog = blogRepo.findById(blogId)
				.orElseThrow(() -> new CheckedException("Post ", "post Id ", blogId));
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new CheckedException("User ", "user Id ", userId));
	
		Comment comment = mapper.map(comDto, Comment.class);
		
		comment.setUser(user);
		comment.setBlog(blog);
		
		Comment savedComment = commRepo.save(comment);
		
		return mapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteCom(Integer commentId) {
			Comment comment = commRepo.findById(commentId)
					.orElseThrow(() -> new CheckedException("Comment ", "comment Id ", commentId));
	
			commRepo.delete(comment);
	
	}

	@Override
	public CommentDto updateComment(CommentDto comment, Integer comId) {
		
		Comment uCom = commRepo.findById(comId).orElseThrow( ()-> new CheckedException("Comment", "comment Id ", comId));
		
		uCom.setComment(comment.getComment());
		
		Comment updateComment = commRepo.save(uCom);
		
		return this.mapper.map(updateComment, CommentDto.class);
	}

	
	public CommentDto singleComment(Integer commentId) {
		 Comment singleCom = commRepo.findById(commentId)
				 .orElseThrow(()-> new CheckedException("Comment ", "comment Id", commentId));
		 
		 return this.mapper.map(singleCom, CommentDto.class);
	}
}
