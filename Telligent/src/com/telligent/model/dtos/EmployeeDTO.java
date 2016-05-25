package com.telligent.model.dtos;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class EmployeeDTO extends EmployeeCommonDTO{
	
	private String teamId;
	private String teamName;
	private String salary;
	private ArrayList<TeamDTO> teamsList = new ArrayList<TeamDTO>();
	private String badgeNo;
	private String socialSecNo;
	private String dateOfBirth;
	private boolean minor;

	private String homePhone;
	private String mobilePhone;
	private String email;
	private String personalEmail;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
	
	private String workPhone;
	private String workExt;
	private String workEmail;
	private String workMobilePhone;
	
	private String emergencyLastName;
	private String emergencyFirstName;
	private String emergencyRelationShip;
	private String emergencyHomePhone;
	private String emergencyMobilePhone;
	private String emergencyEmail;
	
	private MultipartFile picture;
	
	private String pictureBase64;
	
	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * @return the salary
	 */
	public String getSalary() {
		return salary;
	}
	/**
	 * @param salary the salary to set
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}
	/**
	 * @return the teamsList
	 */
	public ArrayList<TeamDTO> getTeamsList() {
		return teamsList;
	}
	/**
	 * @param teamsList the teamsList to set
	 */
	public void setTeamsList(ArrayList<TeamDTO> teamsList) {
		this.teamsList = teamsList;
	}
	/**
	 * @return the badgeNo
	 */
	public String getBadgeNo() {
		return badgeNo;
	}
	/**
	 * @param badgeNo the badgeNo to set
	 */
	public void setBadgeNo(String badgeNo) {
		this.badgeNo = badgeNo;
	}
	/**
	 * @return the socialSecNo
	 */
	public String getSocialSecNo() {
		return socialSecNo;
	}
	/**
	 * @param socialSecNo the socialSecNo to set
	 */
	public void setSocialSecNo(String socialSecNo) {
		this.socialSecNo = socialSecNo;
	}
	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the isMinor
	 */
	public boolean isMinor() {
		return minor;
	}
	/**
	 * @param isMinor the isMinor to set
	 */
	public void setMinor(boolean minor) {
		this.minor = minor;
	}
	/**
	 * @return the homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}
	/**
	 * @param homePhone the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	/**
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * @param mobilePhone the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the personalEmail
	 */
	public String getPersonalEmail() {
		return personalEmail;
	}
	/**
	 * @param personalEmail the personalEmail to set
	 */
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the workPhone
	 */
	public String getWorkPhone() {
		return workPhone;
	}
	/**
	 * @param workPhone the workPhone to set
	 */
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	/**
	 * @return the workExt
	 */
	public String getWorkExt() {
		return workExt;
	}
	/**
	 * @param workExt the workExt to set
	 */
	public void setWorkExt(String workExt) {
		this.workExt = workExt;
	}
	/**
	 * @return the workEmail
	 */
	public String getWorkEmail() {
		return workEmail;
	}
	/**
	 * @param workEmail the workEmail to set
	 */
	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}
	/**
	 * @return the workMobilePhone
	 */
	public String getWorkMobilePhone() {
		return workMobilePhone;
	}
	/**
	 * @param workMobilePhone the workMobilePhone to set
	 */
	public void setWorkMobilePhone(String workMobilePhone) {
		this.workMobilePhone = workMobilePhone;
	}
	/**
	 * @return the emergencyLastName
	 */
	public String getEmergencyLastName() {
		return emergencyLastName;
	}
	/**
	 * @param emergencyLastName the emergencyLastName to set
	 */
	public void setEmergencyLastName(String emergencyLastName) {
		this.emergencyLastName = emergencyLastName;
	}
	/**
	 * @return the emergencyFirstName
	 */
	public String getEmergencyFirstName() {
		return emergencyFirstName;
	}
	/**
	 * @param emergencyFirstName the emergencyFirstName to set
	 */
	public void setEmergencyFirstName(String emergencyFirstName) {
		this.emergencyFirstName = emergencyFirstName;
	}
	/**
	 * @return the emergencyRelationShip
	 */
	public String getEmergencyRelationShip() {
		return emergencyRelationShip;
	}
	/**
	 * @param emergencyRelationShip the emergencyRelationShip to set
	 */
	public void setEmergencyRelationShip(String emergencyRelationShip) {
		this.emergencyRelationShip = emergencyRelationShip;
	}
	/**
	 * @return the emergencyHomePhone
	 */
	public String getEmergencyHomePhone() {
		return emergencyHomePhone;
	}
	/**
	 * @param emergencyHomePhone the emergencyHomePhone to set
	 */
	public void setEmergencyHomePhone(String emergencyHomePhone) {
		this.emergencyHomePhone = emergencyHomePhone;
	}
	/**
	 * @return the emergencyMobilePhone
	 */
	public String getEmergencyMobilePhone() {
		return emergencyMobilePhone;
	}
	/**
	 * @param emergencyMobilePhone the emergencyMobilePhone to set
	 */
	public void setEmergencyMobilePhone(String emergencyMobilePhone) {
		this.emergencyMobilePhone = emergencyMobilePhone;
	}
	/**
	 * @return the emergencyEmail
	 */
	public String getEmergencyEmail() {
		return emergencyEmail;
	}
	/**
	 * @param emergencyEmail the emergencyEmail to set
	 */
	public void setEmergencyEmail(String emergencyEmail) {
		this.emergencyEmail = emergencyEmail;
	}
	/**
	 * @return the picture
	 */
	public MultipartFile getPicture() {
		return picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}
	/**
	 * @return the pictureBase64
	 */
	public String getPictureBase64() {
		return pictureBase64;
	}
	/**
	 * @param pictureBase64 the pictureBase64 to set
	 */
	public void setPictureBase64(String pictureBase64) {
		this.pictureBase64 = pictureBase64;
	}
}
