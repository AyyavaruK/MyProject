package com.telligent.model.daos.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import com.telligent.common.handlers.MessageHandler;
import com.telligent.common.user.TelligentUser;
import com.telligent.core.system.annotation.SpringBean;
import com.telligent.model.daos.ILoginDAO;
import com.telligent.model.db.AbstractDBManager;
import com.telligent.model.db.Generic;
import com.telligent.model.dtos.SecurityGroupDTO;
import com.telligent.model.dtos.User;
import com.telligent.util.BASE64DecodedMultipartFile;
import com.telligent.util.BouncyCastleEncryptor;
import com.telligent.util.MailUtility;

/**
 * @author spothu
 *
 */
@SpringBean
public class LoginDAO extends AbstractDBManager implements ILoginDAO{

	public final Logger logger = Logger.getLogger(LoginDAO.class);

	@Autowired
	BouncyCastleEncryptor bouncyCastleEncryptor;
	
	@Autowired
	MessageHandler handler;
	
	Map prop=new Generic().getPropertyNameValue("telligent.properties");
	
	/**
	 * 
	 * Method to authenticate user
	 * @param String userName
	 * 
	 */
	public User authenticateUser(String userName) {
		logger.info("in authenticateUser");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//SecurityGroupDAO dao = new SecurityGroupDAO();
		//SecurityGroupDTO securityGropDto = null;
//		String query = "SELECT user_name,password,a.employee_id employee_id,role_name,email_id,isChangePassword FROM users a,role_master b,employee c WHERE user_name=? and a.role_id=b.role_id and a.employee_id=c.employee_id";
		//String query = "SELECT user_name,password,a.employee_id employee_id,role_name,work_email,isChangePassword,PICTURE FROM users a,role_master b,EMP_PERSONAL c WHERE user_name=? and a.role_id=b.role_id and a.employee_id=c.emp_id";
		String query = "SELECT user_name,password,a.employee_id employee_id,role_id,work_email,isChangePassword,Security_Group,user_IP,BadLogin_Count,a.effective_Date effective_Date,PICTURE FROM users a,EMP_PERSONAL c WHERE user_name=? and a.employee_id=c.emp_id and Status = 'A'";
		User user = new User();
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if(rs.next()){
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setEmployeeId(rs.getString("employee_id"));
				//user.setRole(rs.getString("role_name"));
				user.setRole(rs.getString("role_id"));
				user.setEmailId(rs.getString("work_email"));;
				user.setChangePassword(rs.getBoolean("isChangePassword"));
				user.setBadLoginCount(rs.getString("BadLogin_Count"));
				user.setIpAddress(rs.getString("user_IP"));
				user.setEffectiveDate(rs.getString("effective_Date"));
				user.setSecuirtyGroup(rs.getString("Security_Group"));
				//securityGropDto = dao.getSecurityGroupDetailsId("NONE",rs.getString("Security_Group"));
				try {
					Blob blob = rs.getBlob("PICTURE");
					user.setPictureBase64(Base64.toBase64String(blob.getBytes(1, (int)blob.length())));
				} catch (Exception e) {}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in authenticateUser"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return user;
	}

	@Override
	public boolean changePassword(TelligentUser telligentUser,String newPassword) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			return changePassword(conn, ps, telligentUser.getUserName(),false, newPassword);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in changePassword"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps);
		}
		return false;
	}
	
	private boolean changePassword(Connection conn,PreparedStatement ps,String userName,boolean isChangePassword,String newPassword){
		String query = "update users set password = ?,isChangePassword=? where user_name=?";
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, bouncyCastleEncryptor.encryptString(newPassword));
			ps.setBoolean(2, isChangePassword);
			ps.setString(3, userName);
			int i = ps.executeUpdate();
			if(i>0){
				return true;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in changePassword"+ex.getMessage());
		} 
		return false;
	}

	@Override
	public boolean forgotPassword(String userName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		String query = "SELECT a.email_id email_id FROM users a,EMP_PERSONAL c WHERE user_name=? and a.employee_id=c.EMP_ID";
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if(rs.next()){
				String newPassword = RandomStringUtils.randomAlphabetic(8).toUpperCase();
				String emailId = rs.getString("email_id");
				StringBuffer buffer = new StringBuffer();
				buffer.append("Dear "+userName+",\n");
				buffer.append("\t Your new password is :"+newPassword);
				buffer.append("\nRegards\n");
				buffer.append("Admin");
				if(changePassword(conn, ps, userName,true, newPassword)){
					resetBadLoginCount(userName);
					MailUtility.sendMail(emailId, "", "", prop.get("smtpUsername").toString(), handler.getMessage("label.passwordReset"), buffer.toString(), "text/plain");;
					return true;
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in forgotPassword"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps,rs);
		}
		return false;
	}

	@Override
	public boolean increaseBadLoginCount(String userName,String status) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		String query = "select BadLogin_Count+1 badLoginCount from users where user_name = ?";
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if(rs.next()){
				String count = rs.getString("badLoginCount");
				ps.close();
				if(status.equalsIgnoreCase(""))
					ps = conn.prepareStatement("update users set BadLogin_Count=? where user_name = ?");
				else
					ps = conn.prepareStatement("update users set BadLogin_Count=?,status='"+status+"' where user_name = ?");
				ps.setString(1, count);
				ps.setString(2, userName);
				int i =ps.executeUpdate();
				if(i>0)
					return true;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in increaseBadLoginCount"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps,rs);
		}
		return false;
	}
	@Override
	public boolean resetBadLoginCount(String userName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement("update users set BadLogin_Count=0,Status='A' where user_name = ?");
			ps.setString(1, userName);
			ps.executeUpdate();
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in decreaseBadLoginCount"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps,rs);
		}
		return true;
	}

}
