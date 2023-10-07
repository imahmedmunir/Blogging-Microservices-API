package com.blogapi.jwtConfig;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blogapi.Cofig.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtToken jwtToke;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String tokenHeader = request.getHeader("Authorization");
		
		
		String userName = null;
		String token = null;
		
		if (tokenHeader!=null && tokenHeader.startsWith("Bearer ")) {
			
			token = tokenHeader.substring(7);
			
			try {
			
				userName=this.jwtToke.extractUsername(token);
				
				
			} catch (IllegalArgumentException e) {
				System.out.println("Illegal argument "+e.getMessage());
			}
			catch (ExpiredJwtException e) {
				System.out.println("Expired Exception "+e.getMessage());
			}
			catch (Exception e) {
					e.printStackTrace();
					System.out.println("Other Exception "+e.getMessage());
			}
		
		}else {
			System.out.println("Token is null or does not with Bearer");
		}
		
		
		
		if (userName != null && SecurityContextHolder.getContext().getAuthentication()==null) {
		
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userName);
	
			if (this.jwtToke.validateToken(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}else {
				System.out.println("Invalid Token ");
			}
			
		}else {
			
			System.out.println("User name not found with token provided or security context is not null");
		}
	
		filterChain.doFilter(request, response);
		
	}

}
