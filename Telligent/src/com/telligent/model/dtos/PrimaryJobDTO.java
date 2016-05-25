package com.telligent.model.dtos;

public class PrimaryJobDTO extends MapDTO{
	private String maximum;
	private String minimum;
	private String midpoint;

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
		this.maximum = maximum;
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
		this.midpoint = midpoint;
	}
}
