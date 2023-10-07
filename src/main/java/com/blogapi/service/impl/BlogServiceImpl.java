package com.blogapi.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapi.entities.Blog;
import com.blogapi.entities.Category;
import com.blogapi.entities.User;
import com.blogapi.exception.CheckedException;
import com.blogapi.helper.BlogPagination;
import com.blogapi.payloads.BlogDto;
import com.blogapi.repositories.BlogRepo;
import com.blogapi.repositories.CategoryRepository;
import com.blogapi.repositories.UserRepository;
import com.blogapi.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private BlogRepo blogRepo;
	
	@Autowired
	private ModelMapper mapper;

	
	@Override
	public BlogDto createBlog(BlogDto blogDto, Integer userId, String categoryName) throws Exception {
		
		Blog blog = mapper.map(blogDto, Blog.class);
		
		
		//getting user
		User user = userRepo.findById(userId)
				.orElseThrow(()-> new CheckedException("User " , "user Id ", userId));
		
		//getting category
		Category category = catRepo.findByCatName(categoryName);
		
		if (Objects.nonNull(category)) {
			
			blog.setDate(new Date());
			blog.setImage("default.jpg");
			blog.setUser(user);
			blog.setCategory(category);
			
			System.out.println("from api "+blog);
			
			Blog savedBlog = blogRepo.save(blog);
			
			return this.mapper.map(savedBlog, BlogDto.class);
			
			}else {
		
					throw new CheckedException("Category ", "category name "+categoryName, 0)	;		
			}
		
		
		 
	}
		
	

	@Override
	public BlogDto updateBlog(BlogDto dto, Integer blogId) {
		Blog blog = blogRepo.findById(blogId)
				.orElseThrow(() -> new CheckedException("Blog " , "blog Id", blogId));
		
		blog.setTitle(dto.getTitle());
		blog.setContent(dto.getContent());
		blog.setImage(dto.getImage());
	
		Blog savedBlog = blogRepo.save(blog);
		
		return mapper.map(savedBlog, BlogDto.class);
	}

	@Override
	public void deleteBlog(Integer blogId) {
			Blog blog = blogRepo.findById(blogId)
					.orElseThrow(() -> new CheckedException("Blog " , "blog Id ", blogId));
			blogRepo.delete(blog);
			
	}

	@Override
	public BlogDto getOne(Integer blogId) {
		Blog blog = blogRepo.findById(blogId)
				.orElseThrow(() -> new CheckedException("Blog " , "blog Id ", blogId));
		
		BlogDto blogDto = this.mapper.map(blog, BlogDto.class);
			
		return blogDto;
	}

	@Override
	public BlogPagination getAll(int pageNum, int pageSize, String sortBy, String sortType) {
		BlogPagination blogPagination = new BlogPagination();
	
		/* throug ternary operator......*/
		Sort sort = (sortType.equalsIgnoreCase("desc"))?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		
		/*
		 * if (sortType.equalsIgnoreCase("desc")) {
		 * 		 sort = Sort.by(sortBy).descending();
		 * 	}else 
		 * 		{ sort = Sort.by(sortBy).ascending();
		 *  	}
		 */
		
		Pageable page =PageRequest.of(pageNum, pageSize, sort);
		
		 Page<Blog> blogPages = blogRepo.findAll(page);
		
		 List<BlogDto> dtoList = blogPages.stream().map((list)-> mapper.map(list, BlogDto.class)).collect(Collectors.toList());
		
		 blogPagination.setContent(dtoList);
		 blogPagination.setPageNumber(blogPages.getNumber()+1); /* adding one because page Index is zero*/
		 blogPagination.setPageSize(blogPages.getSize());
		 blogPagination.setTotalPages(blogPages.getTotalPages());
		 blogPagination.setTotalElements(blogPages.getTotalElements());
		 blogPagination.setLastPage(blogPages.isLast());
		
		 return blogPagination;
	}

	@Override
	public List<BlogDto> getByUser(Integer userId) {
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new CheckedException("User " , "user Id " , userId));
		List<Blog> usersList = blogRepo.findByUser(user);
		
		List<BlogDto> dtoList = new ArrayList<BlogDto>();
		
		for (Blog blog: usersList) {
				dtoList.add(mapper.map(blog, BlogDto.class));
		}
		
		return dtoList;
	}

	@Override
	public List<BlogDto> getByCategory(Integer catId) {
		
		Category category = catRepo.findById(catId)
				.orElseThrow(() -> new CheckedException("Category " , "category Id ", catId));
		
		List<Blog> listBlogs = blogRepo.findByCategory(category);
		
		List<BlogDto> listDto = listBlogs.stream().map((list)-> this.mapper.map(list, BlogDto.class)).collect(Collectors.toList());
		
		return listDto;
	}


	@Override
	public List<BlogDto> searchPost(String keyword) {
	
		List<Blog> searchBlog = blogRepo.findByTitleContaining(keyword);

		List<BlogDto> searchDto = searchBlog.stream().map((t)-> mapper.map(t, BlogDto.class)).collect(Collectors.toList());
		return searchDto;
	}


	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//file name;
		String filename = file.getOriginalFilename();
		
		//file full path
		String filePath = path+File.separator+filename;
		
		//create folder if does not exist
		File folder = new File(path);
		
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		//copy file to folder
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return filename;
	}


	@Override
	public InputStream serveFile(String path, String fileName) throws FileNotFoundException {
		String fullPaht = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPaht);
		return is;
	}

	
}
