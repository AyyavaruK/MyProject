/**
 * 
 */
package com.telligent.security.services.impl;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.telligent.common.user.TelligentUser;

/**
 * @author spothu
 *
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
		TelligentAuthenticationService userDetailService = new TelligentAuthenticationService();
		TelligentUser user = (TelligentUser) userDetailService.loadUserByUsername(token.getPrincipal()+"");
		
		if(user==null)
			throw new UsernameNotFoundException("Invalid user name");
		if(!(token.getCredentials()+"").equals(user.getPassword()))
			throw new BadCredentialsException("Invalid password :: "+token.getPrincipal());
		
		UsernamePasswordAuthenticationToken success  = new UsernamePasswordAuthenticationToken(token.getPrincipal(),token.getCredentials(),user.getAuthorities());
		success.setDetails(user);
		return success;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<? extends Object> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
