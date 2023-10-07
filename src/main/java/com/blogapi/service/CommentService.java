package com.blogapi.service;

import com.blogapi.payloads.CommentDto;

public interface CommentService {

		CommentDto addComment(CommentDto comDto, Integer blogId, Integer userId);
		void deleteCom(Integer commentId);
		CommentDto updateComment(CommentDto comDto, Integer comId);
		CommentDto singleComment(Integer commentId);
		
}
