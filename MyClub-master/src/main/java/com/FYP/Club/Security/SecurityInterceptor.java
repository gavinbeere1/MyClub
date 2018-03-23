package com.FYP.Club.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.FYP.Club.model.Role;

public class SecurityInterceptor extends HandlerInterceptorAdapter {


	  @Override
	  public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
	        System.out.println("Interceptor: Pre-handle");
	    // Avoid a redirect loop for some urls
	    if(request.getRequestURI().equals("/admin")){
	          Role  role = (Role) request.getSession().getAttribute("LOGGEDIN_USER_ROLE");
	          if(!role.getRole().equalsIgnoreCase("ADMIN")){
	             response.sendRedirect("/403/");
	            return false;
	       }   
	      }

	    return true;
	}

	 public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {

	  }

	 }