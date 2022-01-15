//package com.project.holidaymanagementproject.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import net.bytebuddy.agent.builder.AgentBuilder.InitializationStrategy.NoOp;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@EnableWebSecurity
//public class AppSecurityConfig extends WebSecurityConfigurerAdapter{
//
//
//
//	/*@Autowired
//	private UserDetailsService userDetailsService;
//	@Bean
//	public AuthenticationProvider authProvider()
//	{
//		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService);
//		provider.setPasswordEncoder(new BcryptPasswordEncoder());
//		return provider;
//
//	}*/
////	@Bean
////	@Override
////	protected UserDetailsService userDetailsService() {
////		// TODO Auto-generated method stub
////		List<UserDetails> users=new ArrayList<>();
////		users.add(User.withDefaultPasswordEncoder().username("admin").password("1234").roles("ADMIN").build());
////		users.add(User.withDefaultPasswordEncoder().username("user").password("user").roles("Employee").build());
////		return new InMemoryUserDetailsManager(users);
////
////	}
//
//
//
//
//
//}
