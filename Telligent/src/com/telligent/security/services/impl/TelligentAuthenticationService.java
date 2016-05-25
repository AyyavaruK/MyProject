package com.telligent.security.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.telligent.common.user.TelligentUser;
import com.telligent.model.daos.impl.LoginDAO;
import com.telligent.model.daos.impl.SecurityGroupDAO;
import com.telligent.model.dtos.SecurityGroupDTO;
import com.telligent.model.dtos.User;
import com.telligent.util.BouncyCastleEncryptor;

public class TelligentAuthenticationService implements UserDetailsService{

	@Autowired
	BouncyCastleEncryptor bouncyCastleEncryptor;
	
	/*@Autowired
	public TelligentAuthenticationService(ILoginDAO loginDAO){
		this.loginDAO = loginDAO;
	}*/
	SecurityGroupDAO dao = new SecurityGroupDAO();
	SecurityGroupDTO securityGropDto = null;
	
	public TelligentAuthenticationService(){
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		UserDetails userDetails =  null;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		try {
			LoginDAO loginDAO = new LoginDAO();
			User user = loginDAO.authenticateUser(userName);
			securityGropDto = dao.getSecurityGroupDetailsId("NONE",user.getSecuirtyGroup());
			if(user.getUserName() !=null && user.getUserName().equalsIgnoreCase(userName)){
				authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
				if (securityGropDto.isMeritAdmin()){
					authorities.add(new GrantedAuthorityImpl("ShowMeritAdmin"));
				}
				if (securityGropDto.isPerfManager()){
					authorities.add(new GrantedAuthorityImpl("ShowPerformanceManager"));
				}
				if (securityGropDto.isBonusManager()){
					authorities.add(new GrantedAuthorityImpl("ShowBonusManager"));
				}
				if (securityGropDto.isTeams()){
					authorities.add(new GrantedAuthorityImpl("ShowTeams"));
				}
				if (securityGropDto.isEmployees()){
					authorities.add(new GrantedAuthorityImpl("ShowEmployee"));
				}
				if (securityGropDto.isSystemTables()){
					authorities.add(new GrantedAuthorityImpl("ShowReferenceTables"));
				}
				if (securityGropDto.isSuccessionManager()){
					authorities.add(new GrantedAuthorityImpl("ShowSuccession"));
				}
				if (securityGropDto.isSecurity()){
					authorities.add(new GrantedAuthorityImpl("AppUser"));
				}
				/*if(true)// merit cadmin true
				     authorities.add(new GrantedAuthorityImpl("ShowMeritAdmin"));*/
				if(user.getRole()!=null && !"".equalsIgnoreCase(user.getRole().trim()))
					authorities.add(new GrantedAuthorityImpl(String.valueOf(user.getRole())));
				userDetails = new TelligentUser(user.getUserName(), bouncyCastleEncryptor.decryptString(user.getPassword()), Integer.parseInt(user.getBadLoginCount()) >= 3 ? false : true,true,true, true, authorities, true, "", user.getEmailId(),user.getEmployeeId(),"",user.getRole(),user.isChangePassword(),user.getPictureBase64());				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
		}
		return userDetails;
	}

}
