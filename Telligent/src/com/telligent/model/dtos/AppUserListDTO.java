package com.telligent.model.dtos;


public class AppUserListDTO{
	
	private String team;
	private String meritApprovalLevel;
	private String effectiveDate;
	private String endDate;
	private String empID;
	
	
	public String getEmpID() {
		return empID;
	}
	public void setEmpID(String empID) {
		this.empID = empID;
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
	 * @return the meritApprovalLevel
	 */
	public String getMeritApprovalLevel() {
		return meritApprovalLevel;
	}
	/**
	 * @param meritApprovalLevel the meritApprovalLevel to set
	 */
	public void setMeritApprovalLevel(String meritApprovalLevel) {
		this.meritApprovalLevel = meritApprovalLevel;
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
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	

    @Override
    public int hashCode() {
    	if (this.empID != null)
        return (this.empID.hashCode());
    	else
    		return "".hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AppUserListDTO) {
        	AppUserListDTO temp = (AppUserListDTO) obj;
            if(this.empID.equals(temp.empID)) {
                return true;
            }
        }
        return false;
    }

}
