package com.telligent.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.telligent.common.user.TelligentUser;

/**
 * The class is for Implementing DRY across all possible classes. Most of the redundant code will be moved to this class 
 * @author spothu
 */
@Component
public class TelligentUtility {

	
	//to do  o check if User Creation can be pooled.
	// Pooling can be out of scope
	public TelligentUser getTelligentUser(){
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		TelligentUser telligentUser = (TelligentUser)authentication.getPrincipal();
		
		return telligentUser;
		
	}
	
}
