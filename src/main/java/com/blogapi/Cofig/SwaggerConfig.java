package com.blogapi.Cofig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	
	//all chaining method inside Docket class
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.securityContexts(securityContext())
				.securitySchemes(schemes())
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		
		return new ApiInfo("Blogging Application - Web Service ", "This is developed By Muneer Ahmed ", "1.0", "Terms of Services ", new Contact("Muneer Ahmed", "Website Url", "munirahmedjamali@gmail.com"), "License ", "License URL", Collections.EMPTY_LIST);
	}
	
	//apply security while making documentation........
	
	public List<SecurityScheme> schemes() {
	
		//can also user Arrays.aslist(new apikey())  but this version is not supported.......
		
		List<SecurityScheme> securitySchemes  = new ArrayList<>();
		
		securitySchemes.add(new ApiKey("JWT", AUTHORIZATION_HEADER, "header"));
		
		return securitySchemes;
	}
	
	public List<SecurityContext> securityContext(){
		
		List<SecurityContext> contexts = new ArrayList<>();
		
		contexts.add(SecurityContext.builder().securityReferences(securityRef()).build());
		
		return contexts;
		
	}
	
	public List<SecurityReference> securityRef(){
		
		List<SecurityReference> references = new ArrayList<>();
		
		AuthorizationScope[] authorizationScope= {new AuthorizationScope("global", "accessEverything")};
		
		references.add(new SecurityReference("JWT", authorizationScope));
		
		return references;
	}
	
}
