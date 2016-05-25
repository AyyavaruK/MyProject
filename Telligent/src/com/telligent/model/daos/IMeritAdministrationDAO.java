package com.telligent.model.daos;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONArray;

import com.telligent.common.user.TelligentUser;
import com.telligent.model.dtos.AppUserListDTO;
import com.telligent.model.dtos.BudgetSummaryDTO;
import com.telligent.model.dtos.RatingsAndIncreaseDTO;
import com.telligent.model.dtos.SalarPlanningDTO;
import com.telligent.model.dtos.SalaryPositionRangeDTO;


public interface IMeritAdministrationDAO{

	public ArrayList<SalarPlanningDTO> getSalaryPlanningDetails(String columnName,String order,String teamName,String teamId,String employeeId);
	public ArrayList<BudgetSummaryDTO> budgetSummary(String supervisorId,String teamId,String teamName,String employeeId);
    public boolean updateEmployeeDetails(JSONArray list);//SalarPlanningDTO salaryPlanDTO);
    public ArrayList<RatingsAndIncreaseDTO> ratingsAndIncreaseSummary(String teamId,String teamName,String employeeId);
    public ArrayList<SalaryPositionRangeDTO> salaryPositionRangeDetails();
    public HashMap<String, Integer> getSalaryPlanningGridView(String empId);
    public void updateSalaryPlanningColumnWidth(String field,String width,String empId);
    public String approveAll(JSONArray list,TelligentUser user,String team);
    public ArrayList<AppUserListDTO> getApprovalLevels(String teamName,String userEmpID);
    public String sendForApproval(JSONArray list,TelligentUser telligentUser,String team,String submittedTo,String submittedLevel);
}
