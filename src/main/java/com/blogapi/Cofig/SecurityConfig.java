package com.blogapi.Cofig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blogapi.jwtConfig.JwtAuthenticationFilter;
import com.blogapi.jwtConfig.JwtEntryPoint;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String[] PUBLIC_URLS = {
			"/api/auth/**",
			"/api/image/**",
			"/api/upload/image/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**",
			"/api/solo-comment/**"
	};
	
	
	@Autowired
	private CustomUserDetailsService service;

	@Autowired
	private JwtEntryPoint entryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(encode());
	}

	/*
	 * @Autowired void configureGlobal(AuthenticationManagerBuilder auth) throws
	 * Exception { auth .inMemoryAuthentication()
	 * .withUser("user").password("{noop}password").authorities("ROLE_USER"); }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeHttpRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers(PUBLIC_URLS).permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(this.entryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
			.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	
}
