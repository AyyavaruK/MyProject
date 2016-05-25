package com.telligent.model.daos;

import com.telligent.common.user.TelligentUser;
import com.telligent.model.dtos.User;


public interface ILoginDAO{
	public User authenticateUser(String userName);
	public boolean changePassword(TelligentUser telligentUser,String newPassword);
	public boolean forgotPassword(String userName);
	public boolean increaseBadLoginCount(String userName,String status);
	public boolean resetBadLoginCount(String userName);
}
