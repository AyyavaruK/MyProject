package com.telligent.model.dtos;

public class SalaryPositionRangeDTO {
	
	private int id;
	private String overallPerformanceRating;
	private String aggregateExpected;
	private String firstQuartile;
	private String secondQuartile;
	private String thirdQuartile;
	private String fourthQuartile;
	
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
	 * @return the overallPerformanceRating
	 */
	public String getOverallPerformanceRating() {
		return overallPerformanceRating;
	}
	/**
	 * @param overallPerformanceRating the overallPerformanceRating to set
	 */
	public void setOverallPerformanceRating(String overallPerformanceRating) {
		this.overallPerformanceRating = overallPerformanceRating;
	}
	/**
	 * @return the aggregateExpected
	 */
	public String getAggregateExpected() {
		return aggregateExpected;
	}
	/**
	 * @param aggregateExpected the aggregateExpected to set
	 */
	public void setAggregateExpected(String aggregateExpected) {
		this.aggregateExpected = aggregateExpected;
	}
	/**
	 * @return the firstQuartile
	 */
	public String getFirstQuartile() {
		return firstQuartile;
	}
	/**
	 * @param firstQuartile the firstQuartile to set
	 */
	public void setFirstQuartile(String firstQuartile) {
		this.firstQuartile = firstQuartile;
	}
	/**
	 * @return the secondQuartile
	 */
	public String getSecondQuartile() {
		return secondQuartile;
	}
	/**
	 * @param secondQuartile the secondQuartile to set
	 */
	public void setSecondQuartile(String secondQuartile) {
		this.secondQuartile = secondQuartile;
	}
	/**
	 * @return the thirdQuartile
	 */
	public String getThirdQuartile() {
		return thirdQuartile;
	}
	/**
	 * @param thirdQuartile the thirdQuartile to set
	 */
	public void setThirdQuartile(String thirdQuartile) {
		this.thirdQuartile = thirdQuartile;
	}
	/**
	 * @return the fourthQuartile
	 */
	public String getFourthQuartile() {
		return fourthQuartile;
	}
	/**
	 * @param fourthQuartile the fourthQuartile to set
	 */
	public void setFourthQuartile(String fourthQuartile) {
		this.fourthQuartile = fourthQuartile;
	}
}
