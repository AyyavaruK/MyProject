package com.telligent.model.dtos;

import java.sql.Timestamp;


public class SalarPlanningDTO {
	
	private int id;
	private String coworker_name;
	private String employeeId;
	private String supervisor;
	private String jobTitle;
	private String grade;
	private String type;
	private String rate;
	private String compaRatio;
	private String minimum;
	private String midpoint;
	private String maximum;
	private String quartile;
	private String perfGrade;
	private String incrementPercentage;
	private String newRate;
	private String lumsum;
	private String updatedDate;
	
	private String category;
	private String primaryJob;
	private String team;
	private String payEntity;
	private String org1;
	private String org2;
	private String org3;
	private String org4;
	private String org5;
	private String org6;
	private String org7;
	private String org8;
	private String org9;
	private String org10;
	private String currentApprovalLevel;
	private String approvedBy;
	private String approvedDate;
	private String submittedDate;
	private String submittedBy;
	private String currentSubmitLevel;
	private String meritPeriod;
	private String errorCode;
	private String currentApprovalLevelDisableStatus;
	
	
	/**
	 * @return the updatedDate
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the coworker_name
	 */
	public String getCoworker_name() {
		return coworker_name;
	}
	/**
	 * @param coworker_name the coworker_name to set
	 */
	public void setCoworker_name(String coworker_name) {
		this.coworker_name = coworker_name;
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
	 * @return the supervisor
	 */
	public String getSupervisor() {
		return supervisor;
	}
	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}
	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		if (rate == null || rate.equals("0") || rate.equals("0.0")|| rate.equals("0.00")){
			rate = "";
		}
		this.rate = rate;
	}
	/**
	 * @return the compaRatio
	 */
	public String getCompaRatio() {
		return compaRatio;
	}
	/**
	 * @param compaRatio the compaRatio to set
	 */
	public void setCompaRatio(String compaRatio) {
		
		if (compaRatio == null || compaRatio.equals("0") || compaRatio.equals("0.0")|| compaRatio.equals("0.00")){
			compaRatio = "";
		}
		
		this.compaRatio = compaRatio;
	}
	/**
	 * @return the minimum
	 */
	public String getMinimum() {
		return minimum;
	}
	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(String minimum) {
		
		if (minimum == null || minimum.equals("0") || minimum.equals("0.0")|| minimum.equals("0.00")){
			minimum = "";
		}
		this.minimum = minimum;
	}
	/**
	 * @return the midpoint
	 */
	public String getMidpoint() {
		return midpoint;
	}
	/**
	 * @param midpoint the midpoint to set
	 */
	public void setMidpoint(String midpoint) {
		
		if (midpoint == null || midpoint.equals("0") || midpoint.equals("0.0")|| midpoint.equals("0.00")){
			midpoint = "";
		}
		this.midpoint = midpoint;
	}
	/**
	 * @return the maximum
	 */
	public String getMaximum() {
		return maximum;
	}
	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(String maximum) {
		
		if (maximum == null || maximum.equals("0") || maximum.equals("0.0")|| maximum.equals("0.00")){
			maximum = "";
		}
		this.maximum = maximum;
	}
	/**
	 * @return the quartile
	 */
	public String getQuartile() {
		return quartile;
	}
	/**
	 * @param quartile the quartile to set
	 */
	public void setQuartile(String quartile) {
		
		if (quartile == null || quartile.equals("0") || quartile.equals("0.0")|| quartile.equals("0.00")){
			quartile = "";
		}
		this.quartile = quartile;
	}
	/**
	 * @return the perfGrade
	 */
	public String getPerfGrade() {
		return perfGrade;
	}
	/**
	 * @param perfGrade the perfGrade to set
	 */
	public void setPerfGrade(String perfGrade) {
		this.perfGrade = perfGrade;
	}
	/**
	 * @return the incrementPercentage
	 */
	public String getIncrementPercentage() {
		return incrementPercentage;
	}
	/**
	 * @param incrementPercentage the incrementPercentage to set
	 */
	public void setIncrementPercentage(String incrementPercentage) {
		
		if (incrementPercentage == null || incrementPercentage.equals("0") || incrementPercentage.equals("0.0")|| incrementPercentage.equals("0.00")){
			incrementPercentage = "";
		}
		this.incrementPercentage = incrementPercentage;
	}
	/**
	 * @return the newRate
	 */
	public String getNewRate() {
		return newRate;
	}
	/**
	 * @param newRate the newRate to set
	 */
	public void setNewRate(String newRate) {
		
		if (newRate == null || newRate.equals("0") || newRate.equals("0.0")|| newRate.equals("0.00")){
			newRate = "";
		}
		
		this.newRate = newRate;
	}
	/**
	 * @return the lumsum
	 */
	public String getLumsum() {
		return lumsum;
	}
	/**
	 * @param lumsum the lumsum to set
	 */
	public void setLumsum(String lumsum) {
		if (lumsum == null || lumsum.equals("0") || lumsum.equals("0.0")|| lumsum.equals("0.00")){
			lumsum = "";
		}
		this.lumsum = lumsum;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the primaryJob
	 */
	public String getPrimaryJob() {
		return primaryJob;
	}
	/**
	 * @param primaryJob the primaryJob to set
	 */
	public void setPrimaryJob(String primaryJob) {
		this.primaryJob = primaryJob;
	}
	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}
	/**
	 * @param team the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}
	/**
	 * @return the payEntity
	 */
	public String getPayEntity() {
		return payEntity;
	}
	/**
	 * @param payEntity the payEntity to set
	 */
	public void setPayEntity(String payEntity) {
		this.payEntity = payEntity;
	}
	/**
	 * @return the org1
	 */
	public String getOrg1() {
		return org1;
	}
	/**
	 * @param org1 the org1 to set
	 */
	public void setOrg1(String org1) {
		this.org1 = org1;
	}
	/**
	 * @return the org2
	 */
	public String getOrg2() {
		return org2;
	}
	/**
	 * @param org2 the org2 to set
	 */
	public void setOrg2(String org2) {
		this.org2 = org2;
	}
	/**
	 * @return the org3
	 */
	public String getOrg3() {
		return org3;
	}
	/**
	 * @param org3 the org3 to set
	 */
	public void setOrg3(String org3) {
		this.org3 = org3;
	}
	/**
	 * @return the org4
	 */
	public String getOrg4() {
		return org4;
	}
	/**
	 * @param org4 the org4 to set
	 */
	public void setOrg4(String org4) {
		this.org4 = org4;
	}
	/**
	 * @return the org5
	 */
	public String getOrg5() {
		return org5;
	}
	/**
	 * @param org5 the org5 to set
	 */
	public void setOrg5(String org5) {
		this.org5 = org5;
	}
	/**
	 * @return the org6
	 */
	public String getOrg6() {
		return org6;
	}
	/**
	 * @param org6 the org6 to set
	 */
	public void setOrg6(String org6) {
		this.org6 = org6;
	}
	/**
	 * @return the org7
	 */
	public String getOrg7() {
		return org7;
	}
	/**
	 * @param org7 the org7 to set
	 */
	public void setOrg7(String org7) {
		this.org7 = org7;
	}
	/**
	 * @return the org8
	 */
	public String getOrg8() {
		return org8;
	}
	/**
	 * @param org8 the org8 to set
	 */
	public void setOrg8(String org8) {
		this.org8 = org8;
	}
	/**
	 * @return the org9
	 */
	public String getOrg9() {
		return org9;
	}
	/**
	 * @param org9 the org9 to set
	 */
	public void setOrg9(String org9) {
		this.org9 = org9;
	}
	/**
	 * @return the org10
	 */
	public String getOrg10() {
		return org10;
	}
	/**
	 * @param org10 the org10 to set
	 */
	public void setOrg10(String org10) {
		this.org10 = org10;
	}
	/**
	 * @return the currentApprovalLevel
	 */
	public String getCurrentApprovalLevel() {
		return currentApprovalLevel;
	}
	/**
	 * @param currentApprovalLevel the currentApprovalLevel to set
	 */
	public void setCurrentApprovalLevel(String currentApprovalLevel) {
		this.currentApprovalLevel = currentApprovalLevel;
	}
	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}
	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	/**
	 * @return the approvedDate
	 */
	public String getApprovedDate() {
		return approvedDate;
	}
	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	/**
	 * @return the submittedDate
	 */
	public String getSubmittedDate() {
		return submittedDate;
	}
	/**
	 * @param submittedDate the submittedDate to set
	 */
	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}
	/**
	 * @return the submittedBy
	 */
	public String getSubmittedBy() {
		return submittedBy;
	}
	/**
	 * @param submittedBy the submittedBy to set
	 */
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	/**
	 * @return the currentSubmitLevel
	 */
	public String getCurrentSubmitLevel() {
		return currentSubmitLevel;
	}
	/**
	 * @param currentSubmitLevel the currentSubmitLevel to set
	 */
	public void setCurrentSubmitLevel(String currentSubmitLevel) {
		this.currentSubmitLevel = currentSubmitLevel;
	}
	/**
	 * @return the meritPeriod
	 */
	public String getMeritPeriod() {
		return meritPeriod;
	}
	/**
	 * @param meritPeriod the meritPeriod to set
	 */
	public void setMeritPeriod(String meritPeriod) {
		this.meritPeriod = meritPeriod;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the currentApprovalLevelDisableStatus
	 */
	public String getCurrentApprovalLevelDisableStatus() {
		return currentApprovalLevelDisableStatus;
	}
	/**
	 * @param currentApprovalLevelDisableStatus the currentApprovalLevelDisableStatus to set
	 */
	public void setCurrentApprovalLevelDisableStatus(
			String currentApprovalLevelDisableStatus) {
		this.currentApprovalLevelDisableStatus = currentApprovalLevelDisableStatus;
	}
}
