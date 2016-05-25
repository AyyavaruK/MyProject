package com.telligent.model.dtos;


public class EmployeeCompensationDTO extends EmployeeCommonDTO{

	private String payEntity;
	private String payGroup;
	private String payFrequency;
	private String compActionType;
	private String compActionReason;
	private String lastperfEvaluationDate;
	private String nextEvalDueDate;
	private String grade;
	private String increasePercent;
	private String increaseAmount;
	private String baseRate;
	private String baseRateFrequency;
	private String hourlyRate;
	private String periodRate;
	private String annualRate;
	private String defaultEarningCode;
	private String defaultHours;
	private String defaultHoursFrequency;
	private String dailyHours;
	private String payPeriodHours;
	private String annualHours;
	private String compRatio;
	private String quartile;
	private String performacePlan;
	private String bonusPlan;
	private String jobGroup;
	private String useJobRate;
	private String scheduledHours;
	private String hoursFrequency;
	private String weeklyHours;
	private String eligibleJobGroup;
	private String effectiveDate;
	private String employeeId;
	private String employeeNo;
	
	
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	/**
	 * @return the payEntity
	 */
	public String getPayEntity() {
		return payEntity;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @param payEntity the payEntity to set
	 */
	public void setPayEntity(String payEntity) {
		this.payEntity = payEntity;
	}
	/**
	 * @return the payGroup
	 */
	public String getPayGroup() {
		return payGroup;
	}
	/**
	 * @param payGroup the payGroup to set
	 */
	public void setPayGroup(String payGroup) {
		this.payGroup = payGroup;
	}
	/**
	 * @return the payFrequency
	 */
	public String getPayFrequency() {
		return payFrequency;
	}
	/**
	 * @param payFrequency the payFrequency to set
	 */
	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}
	/**
	 * @return the compActionType
	 */
	public String getCompActionType() {
		return compActionType;
	}
	/**
	 * @param compActionType the compActionType to set
	 */
	public void setCompActionType(String compActionType) {
		this.compActionType = compActionType;
	}
	/**
	 * @return the compActionReason
	 */
	public String getCompActionReason() {
		return compActionReason;
	}
	/**
	 * @param compActionReason the compActionReason to set
	 */
	public void setCompActionReason(String compActionReason) {
		this.compActionReason = compActionReason;
	}
	/**
	 * @return the lastperfEvaluationDate
	 */
	public String getLastperfEvaluationDate() {
		return lastperfEvaluationDate;
	}
	/**
	 * @param lastperfEvaluationDate the lastperfEvaluationDate to set
	 */
	public void setLastperfEvaluationDate(String lastperfEvaluationDate) {
		this.lastperfEvaluationDate = lastperfEvaluationDate;
	}
	/**
	 * @return the nextEvalDueDate
	 */
	public String getNextEvalDueDate() {
		return nextEvalDueDate;
	}
	/**
	 * @param nextEvalDueDate the nextEvalDueDate to set
	 */
	public void setNextEvalDueDate(String nextEvalDueDate) {
		this.nextEvalDueDate = nextEvalDueDate;
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
	 * @return the increasePercent
	 */
	public String getIncreasePercent() {
		return increasePercent;
	}
	/**
	 * @param increasePercent the increasePercent to set
	 */
	public void setIncreasePercent(String increasePercent) {
		this.increasePercent = increasePercent;
	}
	/**
	 * @return the increaseAmount
	 */
	public String getIncreaseAmount() {
		return increaseAmount;
	}
	/**
	 * @param increaseAmount the increaseAmount to set
	 */
	public void setIncreaseAmount(String increaseAmount) {
		this.increaseAmount = increaseAmount;
	}
	/**
	 * @return the baseRate
	 */
	public String getBaseRate() {
		return baseRate;
	}
	/**
	 * @param baseRate the baseRate to set
	 */
	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}
	/**
	 * @return the baseRateFrequency
	 */
	public String getBaseRateFrequency() {
		return baseRateFrequency;
	}
	/**
	 * @param baseRateFrequency the baseRateFrequency to set
	 */
	public void setBaseRateFrequency(String baseRateFrequency) {
		this.baseRateFrequency = baseRateFrequency;
	}
	/**
	 * @return the hourlyRate
	 */
	public String getHourlyRate() {
		return hourlyRate;
	}
	/**
	 * @param hourlyRate the hourlyRate to set
	 */
	public void setHourlyRate(String hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	/**
	 * @return the periodRate
	 */
	public String getPeriodRate() {
		return periodRate;
	}
	/**
	 * @param periodRate the periodRate to set
	 */
	public void setPeriodRate(String periodRate) {
		this.periodRate = periodRate;
	}
	/**
	 * @return the annualRate
	 */
	public String getAnnualRate() {
		return annualRate;
	}
	/**
	 * @param annualRate the annualRate to set
	 */
	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}
	/**
	 * @return the defaultEarningCode
	 */
	public String getDefaultEarningCode() {
		return defaultEarningCode;
	}
	/**
	 * @param defaultEarningCode the defaultEarningCode to set
	 */
	public void setDefaultEarningCode(String defaultEarningCode) {
		this.defaultEarningCode = defaultEarningCode;
	}
	/**
	 * @return the defaultHours
	 */
	public String getDefaultHours() {
		return defaultHours;
	}
	/**
	 * @param defaultHours the defaultHours to set
	 */
	public void setDefaultHours(String defaultHours) {
		this.defaultHours = defaultHours;
	}
	/**
	 * @return the defaultHoursFrequency
	 */
	public String getDefaultHoursFrequency() {
		return defaultHoursFrequency;
	}
	/**
	 * @param defaultHoursFrequency the defaultHoursFrequency to set
	 */
	public void setDefaultHoursFrequency(String defaultHoursFrequency) {
		this.defaultHoursFrequency = defaultHoursFrequency;
	}
	/**
	 * @return the dailyHours
	 */
	public String getDailyHours() {
		return dailyHours;
	}
	/**
	 * @param dailyHours the dailyHours to set
	 */
	public void setDailyHours(String dailyHours) {
		this.dailyHours = dailyHours;
	}
	/**
	 * @return the payPeriodHours
	 */
	public String getPayPeriodHours() {
		return payPeriodHours;
	}
	/**
	 * @param payPeriodHours the payPeriodHours to set
	 */
	public void setPayPeriodHours(String payPeriodHours) {
		this.payPeriodHours = payPeriodHours;
	}
	/**
	 * @return the annualHours
	 */
	public String getAnnualHours() {
		return annualHours;
	}
	/**
	 * @param annualHours the annualHours to set
	 */
	public void setAnnualHours(String annualHours) {
		this.annualHours = annualHours;
	}
	/**
	 * @return the compRatio
	 */
	public String getCompRatio() {
		return compRatio;
	}
	/**
	 * @param compRatio the compRatio to set
	 */
	public void setCompRatio(String compRatio) {
		this.compRatio = compRatio;
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
		this.quartile = quartile;
	}
	/**
	 * @return the performacePlan
	 */
	public String getPerformacePlan() {
		return performacePlan;
	}
	/**
	 * @param performacePlan the performacePlan to set
	 */
	public void setPerformacePlan(String performacePlan) {
		this.performacePlan = performacePlan;
	}
	/**
	 * @return the bonusPlan
	 */
	public String getBonusPlan() {
		return bonusPlan;
	}
	/**
	 * @param bonusPlan the bonusPlan to set
	 */
	public void setBonusPlan(String bonusPlan) {
		this.bonusPlan = bonusPlan;
	}
	/**
	 * @return the jobGroup
	 */
	public String getJobGroup() {
		return jobGroup;
	}
	/**
	 * @param jobGroup the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	/**
	 * @return the useJobRate
	 */
	public String getUseJobRate() {
		return useJobRate;
	}
	/**
	 * @param useJobRate the useJobRate to set
	 */
	public void setUseJobRate(String useJobRate) {
		this.useJobRate = useJobRate;
	}
	/**
	 * @return the scheduledHours
	 */
	public String getScheduledHours() {
		return scheduledHours;
	}
	/**
	 * @param scheduledHours the scheduledHours to set
	 */
	public void setScheduledHours(String scheduledHours) {
		this.scheduledHours = scheduledHours;
	}
	/**
	 * @return the hoursFrequency
	 */
	public String getHoursFrequency() {
		return hoursFrequency;
	}
	/**
	 * @param hoursFrequency the hoursFrequency to set
	 */
	public void setHoursFrequency(String hoursFrequency) {
		this.hoursFrequency = hoursFrequency;
	}
	/**
	 * @return the weeklyHours
	 */
	public String getWeeklyHours() {
		return weeklyHours;
	}
	/**
	 * @param weeklyHours the weeklyHours to set
	 */
	public void setWeeklyHours(String weeklyHours) {
		this.weeklyHours = weeklyHours;
	}
	/**
	 * @return the eligibleJobGroup
	 */
	public String getEligibleJobGroup() {
		return eligibleJobGroup;
	}
	/**
	 * @param eligibleJobGroup the eligibleJobGroup to set
	 */
	public void setEligibleJobGroup(String eligibleJobGroup) {
		this.eligibleJobGroup = eligibleJobGroup;
	}

}
