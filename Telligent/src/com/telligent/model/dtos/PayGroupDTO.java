package com.telligent.model.dtos;

public class PayGroupDTO {
	
	private String payGroup;
	private String noPayperiods;
	private String payFrequency;
	private String payPeriodBeginDate;
	private String payPeriodEndDate;
	private String payDate;
	private String isActive;
	private String operation;
	private String id;
	private String updatedDate;
	private String updatedBy;
	private String successMessage;
	private String errorMessage;
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getPayGroup() {
		return payGroup;
	}
	public void setPayGroup(String payGroup) {
		this.payGroup = payGroup;
	}
	public String getNoPayperiods() {
		return noPayperiods;
	}
	public void setNoPayperiods(String noPayperiods) {
		this.noPayperiods = noPayperiods;
	}
	public String getPayFrequency() {
		return payFrequency;
	}
	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}
	public String getPayPeriodBeginDate() {
		return payPeriodBeginDate;
	}
	public void setPayPeriodBeginDate(String payPeriodBeginDate) {
		this.payPeriodBeginDate = payPeriodBeginDate;
	}
	public String getPayPeriodEndDate() {
		return payPeriodEndDate;
	}
	public void setPayPeriodEndDate(String payPeriodEndDate) {
		this.payPeriodEndDate = payPeriodEndDate;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
}
