package com.blogapi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapi.helper.BlogConstants;
import com.blogapi.helper.BlogPagination;
import com.blogapi.helper.CategoryResponse;
import com.blogapi.payloads.BlogDto;
import com.blogapi.service.impl.BlogServiceImpl;

@RestController
@RequestMapping("/api/")
@Validated
public class BlogController {

	@Autowired
	private BlogServiceImpl serviceImpl;

	@Value("${project.image}")
	private String path;

	//adding blog without image...........
	@PostMapping("user/{userId}/category/{catName}/posts")
	public ResponseEntity<BlogDto> addBlog(@Valid @RequestBody BlogDto blogDto, @PathVariable Integer userId,
			@PathVariable String catName) throws Exception {

		BlogDto dto = this.serviceImpl.createBlog(blogDto, userId, catName);

		return new ResponseEntity<BlogDto>(dto, HttpStatus.CREATED);
	}

	
	//creating blog wth Image
	
	/*
	 * @PostMapping("user/{userId}/category/{catId}/posts") public
	 * ResponseEntity<BlogDto> addBlog(@RequestParam(value = "blogDto", required =
	 * true) String string, @PathVariable Integer userId,
	 * 
	 * @PathVariable Integer catId, @RequestParam("image") MultipartFile file)
	 * throws IOException {
	 * 
	 *  //for json to object & vice versa..
	 * ObjectMapper objectMapper = new ObjectMapper();
	 * 
	 * BlogDto blogDto = objectMapper.readValue(string, BlogDto.class);
	 * 
	 * 
	 * String fileName = this.serviceImpl.uploadImage(this.path, file);
	 * 
	 * //setting image if (fileName.isEmpty()) { blogDto.setImage("defult.png");
	 * }else { blogDto.setImage(fileName);
	 * 
	 * }
	 * 
	 * BlogDto dto = this.serviceImpl.createBlog(blogDto, userId, catId);
	 * 
	 * 
	 * return new ResponseEntity<BlogDto>(dto, HttpStatus.CREATED);
	 * 
	 * }
	 */

	@GetMapping("user/{userId}/posts")
	public ResponseEntity<List<BlogDto>> getPostsByUser(@PathVariable Integer userId) {

		List<BlogDto> list = serviceImpl.getByUser(userId);
		/*
		 * if (list.isEmpty()) { return ResponseEntity.ok().build(); }
		 */

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("category/{catId}/posts")
	public ResponseEntity<List<BlogDto>> getPostsByCat(@PathVariable Integer catId) {

		List<BlogDto> byCategory = serviceImpl.getByCategory(catId);

		return new ResponseEntity<List<BlogDto>>(byCategory, HttpStatus.OK);
	}

	@GetMapping("/{blogId}/blog")
	public ResponseEntity<BlogDto> getSingleBlog(@PathVariable Integer blogId) {

		BlogDto blogDto = serviceImpl.getOne(blogId);

		return new ResponseEntity<>(blogDto, HttpStatus.OK);

	}

	@GetMapping("/posts")
	public ResponseEntity<BlogPagination> getAllBlogs(
			@RequestParam(value = "pageNumber", defaultValue = BlogConstants.PAGE_NUMBER, required = false) Integer pageN,
			@RequestParam(value = "pageSize", defaultValue = BlogConstants.PAGE_SIZE, required = false) Integer pageS,
			@RequestParam(value = "sortBy", defaultValue = BlogConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortType", defaultValue = BlogConstants.SORT_TYPE, required = false) String sortType) {

		BlogPagination blogPagination = this.serviceImpl.getAll(pageN, pageS, sortBy, sortType);

		return new ResponseEntity<BlogPagination>(blogPagination, HttpStatus.OK);
	}

	@PutMapping("/{blogId}/posts")
	public ResponseEntity<BlogDto> updateBlog(@Valid @RequestBody BlogDto dto, @PathVariable Integer blogId) {

		BlogDto blogDto = serviceImpl.updateBlog(dto, blogId);
		return new ResponseEntity<BlogDto>(blogDto, HttpStatus.OK);

	}

	@DeleteMapping("/{blogId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryResponse> deleteBlog(@PathVariable Integer blogId) {

		serviceImpl.deleteBlog(blogId);

		return ResponseEntity.ok(new CategoryResponse("Blog with Id " + blogId + " deleted ", true));
	}

	@GetMapping("search/{keyword}")
	public ResponseEntity<List<BlogDto>> searchDto(@PathVariable("keyword") String keyword) {
		System.out.println("keyword......." + keyword);
		List<BlogDto> searchPost = serviceImpl.searchPost(keyword);
		return new ResponseEntity<List<BlogDto>>(searchPost, HttpStatus.OK);
	}

	@PostMapping("upload/image/{postId}")
	public ResponseEntity<CategoryResponse> uploadImg(@PathVariable Integer postId,
			@RequestParam("image") MultipartFile file) throws IOException {

		String contentType = file.getContentType();
		if (!file.isEmpty() && contentType.equalsIgnoreCase("image/jpeg")
				|| contentType.equalsIgnoreCase("image/png")) {

			BlogDto blogDto = serviceImpl.getOne(postId);

			String fileName = serviceImpl.uploadImage(path, file);
			blogDto.setImage(fileName);

			BlogDto updateBlog = serviceImpl.updateBlog(blogDto, postId);
			return new ResponseEntity<>(new CategoryResponse("Image " + fileName + " uploaded", true), HttpStatus.OK);

		}

		return new ResponseEntity<>(new CategoryResponse("Invalid file type  Or Image not found", false),
				HttpStatus.NOT_FOUND);

	}

	@GetMapping(value = "image/{fileName}", produces = "image/jpeg")
	public void getImage(@PathVariable String fileName, HttpServletResponse servletResponse) throws IOException {
		// reading image
		InputStream inputStream = serviceImpl.serveFile(path, fileName);

		// what type of content will be there , telling the servlets
		servletResponse.setContentType("image/jpeg");
		// reading & writing image
		StreamUtils.copy(inputStream, servletResponse.getOutputStream());

	}
}
