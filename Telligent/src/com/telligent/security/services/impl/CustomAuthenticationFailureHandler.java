package com.telligent.security.services.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.telligent.common.user.TelligentUser;
import com.telligent.model.daos.impl.LoginDAO;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		super.onAuthenticationFailure(request, response, exception);
		if(exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
			
		}else if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			new LoginDAO().increaseBadLoginCount(exception.getAuthentication().getPrincipal()+"","");
		}else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
			new LoginDAO().increaseBadLoginCount(exception.getAuthentication().getPrincipal()+"","S");
		}
	}
}
