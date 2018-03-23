package com.FYP.Club.Security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.FYP.Club.repository.UserLoginRepository;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	   UserLoginRepository userLoginRepository;

	//http.authorizeRequests().antMatchers("/", "/home", "/registeruser").permitAll().antMatchers("/admin").hasRole("ADMIN")

	 @Autowired
	 DataSource dataSource;
	
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/", "/home", "/registeruser", "/updatePlayer").permitAll().antMatchers("/admin").hasRole("ADMIN")
					.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
					.permitAll();
			http.exceptionHandling().accessDeniedPage("/403");
			http.csrf().disable();
			//disable csrf to allow communication (we also dont need for this fyp as its not live)
		}
		
		@Override
	    public void configure(WebSecurity web) {
	        web.ignoring().antMatchers("/fonts/**", "/images/**", "/css/**");
	    }
	

		
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        
   
    	   auth.jdbcAuthentication().dataSource(dataSource)
    	  .usersByUsernameQuery("select user_name,password,user_status from user_login where user_name=?")
     	  .authoritiesByUsernameQuery("select user_name, password from user_login where user_name=?");    	   


}

    }
 
