package com.telligent.model.dtos;

import java.util.ArrayList;


public class AppUserDTO extends CommonDTO{
	private String userId;
	private String employeeId;
	private String emailId;
	private String effectiveDate;
	private String userIp;
	private String badLoginCount;
	private String status;
	private String securityGroup;
	private String generalRole;
	private String meritAdminApprovalRole;
	private String performanceAdminRole;
	private String bonusAdminRole;
	private String successionRole;
	private String password;
	private String passwordChanged;
	private ArrayList<AppUserListDTO> appUserList = new ArrayList<AppUserListDTO>();
	
	
	
	public String getPasswordChanged() {
		return passwordChanged;
	}
	public void setPasswordChanged(String passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the userIp
	 */
	public String getUserIp() {
		return userIp;
	}
	/**
	 * @param userIp the userIp to set
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	
	public String getBadLoginCount() {
		return badLoginCount;
	}
	public void setBadLoginCount(String badLoginCount) {
		this.badLoginCount = badLoginCount;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the securityGroup
	 */
	public String getSecurityGroup() {
		return securityGroup;
	}
	/**
	 * @param securityGroup the securityGroup to set
	 */
	public void setSecurityGroup(String securityGroup) {
		this.securityGroup = securityGroup;
	}
	/**
	 * @return the generalRole
	 */
	public String getGeneralRole() {
		return generalRole;
	}
	/**
	 * @param generalRole the generalRole to set
	 */
	public void setGeneralRole(String generalRole) {
		this.generalRole = generalRole;
	}
	/**
	 * @return the meritAdminApprovalRole
	 */
	public String getMeritAdminApprovalRole() {
		return meritAdminApprovalRole;
	}
	/**
	 * @param meritAdminApprovalRole the meritAdminApprovalRole to set
	 */
	public void setMeritAdminApprovalRole(String meritAdminApprovalRole) {
		this.meritAdminApprovalRole = meritAdminApprovalRole;
	}
	/**
	 * @return the performanceAdminRole
	 */
	public String getPerformanceAdminRole() {
		return performanceAdminRole;
	}
	/**
	 * @param performanceAdminRole the performanceAdminRole to set
	 */
	public void setPerformanceAdminRole(String performanceAdminRole) {
		this.performanceAdminRole = performanceAdminRole;
	}
	/**
	 * @return the bonusAdminRole
	 */
	public String getBonusAdminRole() {
		return bonusAdminRole;
	}
	/**
	 * @param bonusAdminRole the bonusAdminRole to set
	 */
	public void setBonusAdminRole(String bonusAdminRole) {
		this.bonusAdminRole = bonusAdminRole;
	}
	/**
	 * @return the successionRole
	 */
	public String getSuccessionRole() {
		return successionRole;
	}
	/**
	 * @param successionRole the successionRole to set
	 */
	public void setSuccessionRole(String successionRole) {
		this.successionRole = successionRole;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the appUserList
	 */
	public ArrayList<AppUserListDTO> getAppUserList() {
		return appUserList;
	}
	/**
	 * @param appUserList the appUserList to set
	 */
	public void setAppUserList(ArrayList<AppUserListDTO> appUserList) {
		this.appUserList = appUserList;
	}
}
