package com.a7space.commons.authority.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.a7space.commons.exception.NoFoundException;
import com.a7space.commons.exception.NoLoginException;
import com.a7space.commons.exception.PermissionDeniedException;
import com.a7space.commons.exception.ServiceException;

public class MVCExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			Exception ex) {
		// TODO Auto-generated method stub
		ModelAndView  mv=new ModelAndView("redirect:/err500.jsp");
		
		if(ex instanceof NoLoginException) {
			mv=new ModelAndView("redirect:/login.jsp");
		}
		if(ex instanceof NoFoundException) {
			mv=new ModelAndView("redirect:/err404.jsp");
		}
		if(ex instanceof PermissionDeniedException) {
			mv=new ModelAndView("redirect:/err403.jsp");
		}
		if(ex instanceof ServiceException) {
			mv=new ModelAndView("redirect:/err500.jsp");
		}
		return mv;
	}

}
