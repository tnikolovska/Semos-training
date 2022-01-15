//package com.project.holidaymanagementproject.model;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//public class SecurityConfiguration  extends WebSecurityConfigurerAdapter{
//	 
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		http.httpBasic()
//		.and()
//		.authorizeRequests()
//		.antMatchers("/person")
//		.hasRole("ADMIN")
//		.and()
//		.formLogin();
//
//		
//		
//		
//	}
//		
//}
