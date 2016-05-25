package com.telligent.model.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Statement;

import com.telligent.common.handlers.MessageHandler;
import com.telligent.common.user.TelligentUser;
import com.telligent.core.system.annotation.SpringBean;
import com.telligent.model.daos.IMeritAdministrationDAO;
import com.telligent.model.db.AbstractDBManager;
import com.telligent.model.db.Generic;
import com.telligent.model.dtos.AppUserListDTO;
import com.telligent.model.dtos.BudgetSummaryDTO;
import com.telligent.model.dtos.RatingsAndIncreaseDTO;
import com.telligent.model.dtos.SalarPlanningDTO;
import com.telligent.model.dtos.SalaryPositionRangeDTO;
import com.telligent.model.dtos.TeamDTO;
import com.telligent.util.MailUtility;
import com.telligent.util.ParseNumberUtility;
import com.telligent.util.TelligentUtility;

/**
 * @author spothu
 *
 */
@SpringBean
public class MeritAdministrationDAO extends AbstractDBManager implements IMeritAdministrationDAO{

	public final Logger logger = Logger.getLogger(MeritAdministrationDAO.class);
	@Autowired
	MessageHandler handler;
	@Autowired
	TelligentUtility telligentUtility;
	Map prop=new Generic().getPropertyNameValue("telligent.properties");
	NumberFormat nf = NumberFormat.getInstance(Locale.US);

	@Override
	public ArrayList<SalarPlanningDTO> getSalaryPlanningDetails(String columnName,String order,String teamName,String teamId,String employeeId) {
		logger.info("in getSalaryPlanningDetails");
		StringBuffer query = new StringBuffer();
		TelligentUser user = telligentUtility.getTelligentUser();
		query.append("select mt.id id,coworker_name,mt.emp_id emp_id,concat(emp.L_NAME,',',emp.F_NAME) empName,supervisor,pj.value job_title,grade,flsa.value type,rate,compa_ratio,mt.minimum minimum,mt.midpoint midpoint,mt.maximum maximum,quartile, ");
		query.append("perf_grade,increment_percentage,new_rate,lumsum,DATE_FORMAT(mt.updated_date,'%y/%m/%d') updated_date,category,pj.value primary_job,t.team_name team,entity.value pay_entity, ");
		query.append("o1.value org1,o2.value org2,o3.value org3,o4.value org4,o5.value org5,o6.value org6,o7.value org7,o8.value org8,o9.value org9,o10.value org10, ");
		query.append("current_approval_level,approved_by,DATE_FORMAT(approved_date,'%y/%m/%d') approved_date,DATE_FORMAT(date_submitted,'%y/%m/%d') date_submitted,submitted_by,current_submit_level,merit_period,error_code ");
		query.append("from merit_transaction mt ");
		query.append("left join team t on t.team_id = mt.team ");
		query.append("left join pay_entity entity on entity.id = mt.pay_entity ");
		query.append("left join org1 o1 on o1.id = mt.org1 ");
		query.append("left join org2 o2 on o2.id = mt.org2 ");
		query.append("left join org3 o3 on o3.id = mt.org3 ");
		query.append("left join org4 o4 on o4.id = mt.org4 ");
		query.append("left join org5 o5 on o5.id = mt.org5 ");
		query.append("left join org6 o6 on o6.id = mt.org6 ");
		query.append("left join org7 o7 on o7.id = mt.org7 ");
		query.append("left join org8 o8 on o8.id = mt.org8 ");
		query.append("left join FLSA_Category flsa on flsa.id = mt.type ");
		query.append("left join org9 o9 on o9.id = mt.org9 ");
		query.append("left join org10 o10 on o10.id = mt.org10 ");
		query.append("left join primary_job pj on pj.id = mt.job_title ");
		query.append("left join EMP_PERSONAL emp on emp.emp_id = mt.EMP_ID ");
		query.append("where mt.team in("+teamId+")  ");
		//query.append("where (mt.supervisor like ?  ");
		//query.append("or  mt.team in("+teamId+") )");
		query.append("and merit_period = ?");
		query.append(" Order by mt.error_code desc");
		ArrayList<SalarPlanningDTO> list = new ArrayList<SalarPlanningDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			String meritPeriod = getMeritPeriod(conn,ps,rs);
			ps = conn.prepareStatement(query.toString());
			//ps.setString(1, employeeId+"%");
			ps.setString(1, meritPeriod);
			rs = ps.executeQuery();
			DecimalFormat df1 = new DecimalFormat("###.00");
			while(rs.next()){
				SalarPlanningDTO dto = new SalarPlanningDTO();
				dto.setId(rs.getInt("id"));
				dto.setCoworker_name(checkifNull(rs.getString("empName")));
				dto.setEmployeeId(checkifNull(rs.getString("emp_id")));
				dto.setSupervisor(checkifNull(rs.getString("supervisor")));
				dto.setJobTitle(checkifNull(rs.getString("job_title")));
				dto.setGrade(checkifNull(rs.getString("grade")));
				dto.setType(checkifNull(rs.getString("type")));
				dto.setCompaRatio(checkifNull(rs.getString("compa_ratio")));
				try {
					dto.setRate(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("rate")+""))+""));
					} catch (Exception e) {
						e.printStackTrace();
					}
				try {dto.setMinimum(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("minimum")+""))+""));} catch (Exception e) {}
				try {dto.setMidpoint(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("midpoint")+""))+""));} catch (Exception e) {}
				try {dto.setMaximum(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("maximum")+""))+""));} catch (Exception e) {}
				try {dto.setLumsum(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("lumsum")+""))+""));} catch (Exception e) {}
				dto.setQuartile(checkifNull(rs.getString("quartile")));
				dto.setPerfGrade(checkifNull(rs.getString("perf_grade")));
				if(rs.getString("increment_percentage")!=null && Float.parseFloat(rs.getString("increment_percentage")) > 0)
					try {
						dto.setIncrementPercentage(df1.format(Float.parseFloat(rs.getString("increment_percentage"))) );
					} catch (Exception e1) {}
				else
					dto.setIncrementPercentage("00.00");
				try {
					dto.setNewRate(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("new_rate")+""))+""));
				} catch (Exception e) {}
				dto.setUpdatedDate(rs.getString("updated_date"));
				dto.setCategory(checkifNull(rs.getString("category")));
				dto.setPrimaryJob(checkifNull(rs.getString("primary_job")));
				dto.setTeam(checkifNull(rs.getString("team")));
				dto.setPayEntity(checkifNull(rs.getString("pay_entity")));
				dto.setOrg1(checkifNull(rs.getString("org1")));
				dto.setOrg2(checkifNull(rs.getString("org2")));
				dto.setOrg3(checkifNull(rs.getString("org3")));
				dto.setOrg4(checkifNull(rs.getString("org4")));
				dto.setOrg5(checkifNull(rs.getString("org5")));
				dto.setOrg6(checkifNull(rs.getString("org6")));
				dto.setOrg7(checkifNull(rs.getString("org7")));
				dto.setOrg8(checkifNull(rs.getString("org8")));
				dto.setOrg9(checkifNull(rs.getString("org9")));
				dto.setOrg10(checkifNull(rs.getString("org10")));
				dto.setCurrentApprovalLevel(checkifNull(rs.getString("current_approval_level")));
				dto.setApprovedBy(checkifNull(rs.getString("approved_by")));
				dto.setApprovedDate(checkifNull(rs.getString("approved_date")));
				dto.setSubmittedDate(checkifNull(rs.getString("date_submitted")));
				dto.setSubmittedBy(checkifNull(rs.getString("submitted_by")));
				dto.setCurrentSubmitLevel(checkifNull(rs.getString("current_submit_level")));
				dto.setMeritPeriod(checkifNull(rs.getString("merit_period")));
				dto.setErrorCode(checkifNull(rs.getString("error_code")));
				String temp = getCurrentApprovalLevel(conn, ps, rs, user.getEmployeeId(), dto.getTeam());
				try{
					if(!"".equalsIgnoreCase(dto.getCurrentApprovalLevel())){
						if(Integer.parseInt(dto.getCurrentApprovalLevel()) <= Integer.parseInt(temp)){
							dto.setCurrentApprovalLevelDisableStatus("true"); // Editable
						}
						else{
							dto.setCurrentApprovalLevelDisableStatus("false"); // Not Editable
						}
					}else{
						dto.setCurrentApprovalLevelDisableStatus("true"); // Editable
					}
												
				}catch(Exception e){
					e.printStackTrace();
				}
				list.add(dto);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in getEmployeeTeam"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	
	// Old method starts
	/*@Override
	public ArrayList<SalarPlanningDTO> getSalaryPlanningDetails(String columnName,String order,String teamName,String teamId,String employeeId) {
		logger.info("in getSalaryPlanningDetails");
		StringBuffer query = new StringBuffer();
		query.append("select id,coworker_name,emp_id,supervisor,job_title,grade,type,rate,compa_ratio,minimum,midpoint,maximum,quartile,perf_grade,increment_percentage,new_rate,lumsum,DATE_FORMAT(updated_date,'%y/%m/%d') updated_date ");
		query.append("from salary_planning sp,employee e,team t");
		query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in("+teamId+")");
		if(columnName !=null && !columnName.equalsIgnoreCase("") && order!=null && !order.equalsIgnoreCase("")){
			if(columnName.equalsIgnoreCase("coworker_name"))
				query.append(" order by coworker_name "+order);
			else if(columnName.equalsIgnoreCase("employeeId"))
				query.append(" order by emp_id "+order);
		}
		ArrayList<SalarPlanningDTO> list = new ArrayList<SalarPlanningDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, employeeId);
			rs = ps.executeQuery();
			DecimalFormat df1 = new DecimalFormat("###.00");
			while(rs.next()){
				SalarPlanningDTO dto = new SalarPlanningDTO();
				dto.setId(rs.getInt("id"));
				dto.setCoworker_name(rs.getString("coworker_name"));
				dto.setEmployeeId(rs.getString("emp_id"));
				dto.setSupervisor(rs.getString("supervisor"));
				dto.setJobTitle(rs.getString("job_title"));
				dto.setGrade(rs.getString("grade"));
				dto.setType(rs.getString("type"));
				dto.setCompaRatio(rs.getString("compa_ratio"));
				try {dto.setRate(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("rate")))));} catch (Exception e) {}
				try {dto.setMinimum(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("minimum")))));} catch (Exception e) {}
				try {dto.setMidpoint(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("midpoint")))));} catch (Exception e) {}
				try {dto.setMaximum(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("maximum")))));} catch (Exception e) {}
				try {dto.setLumsum(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("lumsum")))));} catch (Exception e) {}
				dto.setQuartile(rs.getString("quartile"));
				dto.setPerfGrade(rs.getString("perf_grade"));
				if(Float.parseFloat(rs.getString("increment_percentage")) > 0)
					try {
						dto.setIncrementPercentage(df1.format(Float.parseFloat(rs.getString("increment_percentage"))) );
					} catch (Exception e1) {}
				else
					dto.setIncrementPercentage(rs.getString("increment_percentage"));
				try {
					dto.setNewRate(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(rs.getDouble("new_rate")))));
				} catch (Exception e) {}
				dto.setUpdatedDate(rs.getString("updated_date"));
				list.add(dto);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in getEmployeeTeam"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}*/
	// Old method ends

	@Override
	public boolean updateEmployeeDetails(JSONArray list) {

		logger.info("in updateEmployeeDetails");
		StringBuffer updateQuery = new StringBuffer();
		boolean updated = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = this.getConnection();
			conn.setAutoCommit(false);
			for(int i=0;i<list.size();i++){
				String empId = list.getJSONObject(i).getString("employeeId");;
				String empGrade = list.getJSONObject(i).getString("perfGrade");
				String increment = list.getJSONObject(i).getString("incrementPercentage");
				String meritPeriod = list.getJSONObject(i).getString("meritPeriod");
				String quartile = list.getJSONObject(i).getString("quartile");
				String payEntity = list.getJSONObject(i).getString("payEntity");
				String errorCode = getErrorCode(empGrade, increment, quartile,meritPeriod,payEntity);
				String type = list.getJSONObject(i).getString("type");
				float rate = list.getJSONObject(i).getString("rate")!=null && !list.getJSONObject(i).getString("rate").equalsIgnoreCase("") ? Float.parseFloat(list.getJSONObject(i).getString("rate").replaceAll(",", "")):0;
				float newRate = list.getJSONObject(i).getString("newRate")!=null && !list.getJSONObject(i).getString("newRate").equalsIgnoreCase("") ? Float.parseFloat(list.getJSONObject(i).getString("newRate").replaceAll(",", "")):0;
				float  incrementPer = increment !=null && !increment.equalsIgnoreCase("") ? Float.parseFloat(increment):0;
				float maximum =list.getJSONObject(i).getString("maximum")!=null && !list.getJSONObject(i).getString("maximum").equalsIgnoreCase("") ? Float.parseFloat(list.getJSONObject(i).getString("maximum").replaceAll(",", "")):0; 
				float lumsum = list.getJSONObject(i).getString("lumsum")!=null && !list.getJSONObject(i).getString("lumsum").equalsIgnoreCase("") ? Float.parseFloat(list.getJSONObject(i).getString("lumsum").replaceAll(",", "")):0;
				float preUpdateNewRate ;
				if (incrementPer > 0){
					preUpdateNewRate = rate*(1+(incrementPer/100));
					if (preUpdateNewRate <= maximum){
						newRate = preUpdateNewRate;
						lumsum = 0;
					}else{
						if(type.equalsIgnoreCase("hourly") || type.equalsIgnoreCase("nonexempt")){
							newRate = maximum;	
							lumsum = ((preUpdateNewRate - maximum) * 2080);
						}else{
							newRate = maximum;	
							lumsum = preUpdateNewRate - maximum;
						}
					}
				}
				updateQuery = new StringBuffer();
				if(list.getJSONObject(i).getString("incrementPercentage")!=null && !list.getJSONObject(i).getString("incrementPercentage").equalsIgnoreCase("") 
						&& list.getJSONObject(i).getString("perfGrade")!=null && !list.getJSONObject(i).getString("perfGrade").equalsIgnoreCase(""))
					updateQuery.append("update merit_transaction set perf_grade= ? ,increment_percentage= ? ,new_rate= ?,lumsum=?,updated_date = sysdate(),error_code = ? where emp_id = ? ");
				else if(list.getJSONObject(i).getString("perfGrade")==null || list.getJSONObject(i).getString("perfGrade").equalsIgnoreCase(""))
					updateQuery.append("update merit_transaction set increment_percentage= ? ,new_rate= ?,lumsum=?,updated_date = sysdate(),error_code = ? where emp_id = ? ");
				else if(list.getJSONObject(i).getString("incrementPercentage")==null || list.getJSONObject(i).getString("incrementPercentage").equalsIgnoreCase(""))
					updateQuery.append("update merit_transaction set perf_grade= ? ,new_rate= ?,lumsum=?,updated_date = sysdate(),error_code = ? where emp_id = ? ");
				ps = conn.prepareStatement(updateQuery.toString());
				if(increment !=null && !increment.equalsIgnoreCase("") && empGrade !=null && !empGrade.equalsIgnoreCase("")){
					ps.setString(1, empGrade);
					ps.setFloat(2, incrementPer);
					ps.setFloat(3, newRate);
					ps.setFloat(4, lumsum);
					//ps.setDate(5, (java.sql.Date) new Date());
					ps.setString(5, errorCode);
					ps.setString(6, empId);
				}else if(empGrade ==null || empGrade.equalsIgnoreCase("")){
					ps.setFloat(1, incrementPer);
					ps.setFloat(2, newRate);
					ps.setFloat(3, lumsum);
					//ps.setDate(5, (java.sql.Date) new Date());
					ps.setString(4, errorCode);
					ps.setString(5, empId);
				}else if(increment ==null || increment.equalsIgnoreCase("")){
					ps.setString(1, empGrade);
					ps.setFloat(2, newRate);
					ps.setFloat(3, lumsum);
					//ps.setDate(5, (java.sql.Date) new Date());
					ps.setString(4, errorCode);
					ps.setString(5, empId);
				}
				ps.addBatch();
				empId = null;empGrade=null;increment=null;
			}
			int i[] = ps.executeBatch();
			if(i.length == list.size()){
				conn.commit();
				updated = true;
			}
		}catch(Exception ex){
			updated = false;
			ex.printStackTrace();
			logger.error("Exception in updateEmployeeDetails :: "+ex);
			try {
				conn.rollback();
			} catch (SQLException e) {}
		}finally{
			this.closeAll(conn, ps);
		}
		return updated;
		// throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public String getErrorCode(String performanceGrade,String incrementPercent,String quartile,String meritPeriod,String payEntity){
		
		String errorCode ="";
		
		logger.info("in getErrorCode");
		StringBuffer query = new StringBuffer();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String firstQuaMin ="";
		String firstQuaMax ="";
		String secondQuaMin ="";
		String secondQuaMax ="";
		String thirdQuaMin ="";
		String thirdQuaMax ="";
		String fourthQuaMin ="";
		String fourthQuaMax ="";
		try{
			conn = this.getConnection();
			conn.setAutoCommit(false);
			query.append( "SELECT entity,first_Quartile_Min,first_Quartile_Max,second_Quartile_Min,second_Quartile_Max,third_Quartile_Min,third_Quartile_Max");
			query.append( " ,fourth_Quartile_Min,fourth_Quartile_Max,aggregate_expected from merit_admin_guidelines where overallPerformanceRating = '"+performanceGrade+"' and merit_period= '"+meritPeriod+"'" );
			query.append( " and entity = (SELECT id from pay_entity where value ='"+payEntity+"')");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query.toString());
			
			if (rs.next()){
				firstQuaMin = rs.getString("first_Quartile_Min");
				firstQuaMax = rs.getString("first_Quartile_Max");
				secondQuaMin = rs.getString("second_Quartile_Min");
				secondQuaMax = rs.getString("second_Quartile_Max");
				thirdQuaMin = rs.getString("third_Quartile_Min");
				thirdQuaMax = rs.getString("third_Quartile_Max");
				fourthQuaMin = rs.getString("fourth_Quartile_Min");
				fourthQuaMax = rs.getString("fourth_Quartile_Max");
				
			}
			float incrementPer = Float.parseFloat(incrementPercent);
			if (quartile != null && quartile.equalsIgnoreCase("1st")){
				float min = Float.parseFloat(firstQuaMin);
				float max = Float.parseFloat(firstQuaMax);
				if ( incrementPer < min){
					//ErrorCode03
					errorCode = "ERROR1";
				}else if (incrementPer > max) {
					errorCode = "ERROR2";
				}else{
					
					//errorCode = "VALIDATED";
					errorCode = "ERROR0";
				}
				
			}
			
			if (quartile != null && quartile.equalsIgnoreCase("2nd")){
				float min = Float.parseFloat(secondQuaMin);
				float max = Float.parseFloat(secondQuaMax);
				if ( incrementPer < min){
					errorCode = "ERROR1";
				}else if (incrementPer > max) {
					errorCode = "ERROR2";
				}else{
					
					errorCode = "ERROR0";
				}
				
			}
			if (quartile != null && quartile.equalsIgnoreCase("3rd")){
				float min = Float.parseFloat(thirdQuaMin);
				float max = Float.parseFloat(thirdQuaMax);
				if ( incrementPer < min){
					errorCode = "ERROR1";
				}else if (incrementPer > max) {
					errorCode = "ERROR2";
				}else{
					
					errorCode = "ERROR0";
				}
				
			}
			if (quartile != null && quartile.equalsIgnoreCase("4th")){
				float min = Float.parseFloat(fourthQuaMin);
				float max = Float.parseFloat(fourthQuaMax);
				if ( incrementPer < min){
					errorCode = "ERROR1";
				}else if (incrementPer > max) {
					errorCode = "ERROR2";
				}else{
					
					errorCode = "ERROR0";
				}
				
			}
			
			if (quartile != null && quartile.equalsIgnoreCase("Below Minimum")){

					errorCode = "ERROR3";
			}
			if (quartile != null && quartile.equalsIgnoreCase("Above Maximum")){

				errorCode = "ERROR4";
		}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("Exception in getErrorCode :: "+ex);
			try {
				conn.rollback();
			} catch (SQLException e) {}
		}finally{
			this.closeAll(conn, stmt,rs);
		}
		
		return errorCode;
	}


	@Override
	public ArrayList<BudgetSummaryDTO> budgetSummary(String supervisorId,String teamId,String teamName,String employeeId) {
		// TODO Auto-generated method stub

		StringBuffer query = new StringBuffer();
		//query.append("select type,sum(rate) current,sum(new_rate) new from salary_planning group by type ");
		//query.append("select type,sum(rate) current,sum(new_rate) new ");
		//query.append("from salary_planning sp,employee e,team t ");
		//query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type");
		query.append("select type,sum(rate) current,sum(new_rate) new from merit_transaction sp where  sp.team in ("+teamId+") group by type");
		ArrayList<BudgetSummaryDTO> list = new ArrayList<BudgetSummaryDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			//ps.setString(1, employeeId);
			rs = ps.executeQuery();
			float totalCurrentBudget=0;
			float totalnewBudget=0;
			float totalChange=0;

			while(rs.next()){
				BudgetSummaryDTO dto = new BudgetSummaryDTO();
				float currentBudget;
				float newBudget;
				float change;
				dto.setAnualBudgetType(rs.getString("type"));
				if (dto.getAnualBudgetType() != null && dto.getAnualBudgetType().equalsIgnoreCase("Hourly")){
					currentBudget = Float.parseFloat(ParseNumberUtility.parseNumber(rs.getFloat("current")*2080+""));
					newBudget = Float.parseFloat(ParseNumberUtility.parseNumber(rs.getFloat("new")*2080+""));
				}else{
					currentBudget = Float.parseFloat(ParseNumberUtility.parseNumber(rs.getFloat("current")+""));
					newBudget = Float.parseFloat(ParseNumberUtility.parseNumber(rs.getFloat("new")+""));
				}
				change = Float.parseFloat(ParseNumberUtility.parseNumber(1-(currentBudget/newBudget)+""));
				totalCurrentBudget = totalCurrentBudget+currentBudget;
				totalnewBudget = totalnewBudget+newBudget;
				totalChange = totalChange+change;
				try {dto.setCurrentBudget(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(currentBudget+"")+""));} catch (Exception e) {}
				try {dto.setNewBudget(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(newBudget+"")+""));} catch (Exception e) {}
				try {dto.setChangeBudget(ParseNumberUtility.parseNumber(change*100+""));} catch (Exception e) {}
				list.add(dto);
			}
			BudgetSummaryDTO dto1 = new BudgetSummaryDTO();
			dto1.setAnualBudgetType("Total");
			try {dto1.setCurrentBudget(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(totalCurrentBudget+""))+""));} catch (Exception e) {}
			try {dto1.setNewBudget(ParseNumberUtility.parseNumberInUSFormat(Double.parseDouble(ParseNumberUtility.parseNumber(totalnewBudget+""))+""));} catch (Exception e) {}
			try {dto1.setChangeBudget(ParseNumberUtility.parseNumber(totalChange*100.00+""));} catch (Exception e) {}
			list.add(dto1);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in annualBudgetSummary"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}

	// New Method
	@Override
	public ArrayList<RatingsAndIncreaseDTO> ratingsAndIncreaseSummary(String teamId,String teamName,String employeeId) {
		logger.info("in ratingsAndIncreaseSummary");
		StringBuffer query = new StringBuffer();
		//query.append("select type,perf_grade,count(perf_grade) count from salary_planning group by type,perf_grade");
		int hourlyEmployees = 0;
		int officeEmployees = 0;
		ArrayList<RatingsAndIncreaseDTO> list = new ArrayList<RatingsAndIncreaseDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			// Start End query to get Count
			//query.append("select type,perf_grade,count(perf_grade) count ");
			//query.append("from merit_transaction sp,employee e,team t ");
			//query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");
			query.append("select type,perf_grade,count(perf_grade) count from merit_transaction sp  where sp.team in ("+teamId+") and type is not null and perf_grade is not null group by type,perf_grade");
			ps = conn.prepareStatement(query.toString());
			//ps.setString(1, employeeId);
			rs = ps.executeQuery();
			RatingsAndIncreaseDTO dto = null;
			dto = new RatingsAndIncreaseDTO();
			dto.setType("Count");
			HashMap<String, Integer> countMap = new HashMap<String, Integer>();
			while(rs.next()){
				if(rs.getString("type").equalsIgnoreCase("3") && rs.getString("perf_grade")!=null){
					if(rs.getString("perf_grade").equalsIgnoreCase("A")){
						dto.setHourlyA(rs.getString("count"));
						countMap.put("hourlyA", rs.getInt("count"));
					}else if(rs.getString("perf_grade").equalsIgnoreCase("B")){
						dto.setHourlyB(rs.getString("count"));
						countMap.put("hourlyB", rs.getInt("count"));
					}else if(rs.getString("perf_grade").equalsIgnoreCase("C")){
						dto.setHourlyC(rs.getString("count"));
						countMap.put("hourlyC", rs.getInt("count"));
					}
				}else if(rs.getString("type").equalsIgnoreCase("5") && rs.getString("perf_grade")!=null){
					if(rs.getString("perf_grade").equalsIgnoreCase("A")){
						dto.setOfficeA(rs.getString("count"));
						countMap.put("officeA", rs.getInt("count"));
					}else if(rs.getString("perf_grade").equalsIgnoreCase("B")){
						dto.setOfficeB(rs.getString("count"));
						countMap.put("officeB", rs.getInt("count"));
					}else if(rs.getString("perf_grade").equalsIgnoreCase("C")){
						dto.setOfficeC(rs.getString("count"));
						countMap.put("officeC", rs.getInt("count"));
					}
				}
			}
			list.add(dto);
			rs.close();
			ps.close();
			query = new StringBuffer();
			// End query to get Count
			// Start query to get total hourly / Office employees
			//query.append("select type,count(type) count ");
			//query.append("from merit_transaction sp,employee e,team t ");
			//query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type");
			query.append("select type,count(type) count from merit_transaction sp where sp.team  in ("+teamId+")  and sp.type is not null and sp.perf_grade is not null group by type");
			ps = conn.prepareStatement(query.toString());
			//ps.setString(1, employeeId);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("type")!=null && rs.getString("type").equalsIgnoreCase("3")){
					hourlyEmployees = rs.getInt("count");
				}else if(rs.getString("type")!=null && rs.getString("type").equalsIgnoreCase("5")){
					officeEmployees = rs.getInt("count");
				}
			}
			rs.close();
			ps.close();
			// End query to get total hourly / Office employees
			query = new StringBuffer();
			rs = null;
			// Start - % for Group
			//query.append("select type,perf_grade,(count/(select count(perf_grade) from salary_planning where b.type=type)) percentage from (select type,perf_grade,count(perf_grade) count from salary_planning b group by type,perf_grade) b");
			//query.append("select type,perf_grade,(count/(select count(perf_grade) from salary_planning where b.type=type)) percentage from (select type,perf_grade,count(perf_grade) count from salary_planning b,employee e,team t  where b.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade) b");
			//query.append("select type,perf_grade,count(perf_grade) count ");
			//query.append("from merit_transaction sp,employee e,team t ");
			//query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");
			query.append("select type,perf_grade,count(increment_percentage) count from merit_transaction sp where sp.team in ("+teamId+" ) and sp.type is not null and sp.perf_grade is not null group by type,perf_grade");
			ps = conn.prepareStatement(query.toString());
			//ps.setString(1, employeeId);
			rs = ps.executeQuery();
			dto = new RatingsAndIncreaseDTO();
			dto.setType("% for Group");
			while(rs.next()){
				if(rs.getString("type").equalsIgnoreCase("3") && rs.getString("perf_grade")!=null){
					if(rs.getString("perf_grade").equalsIgnoreCase("A"))
						dto.setHourlyA(ParseNumberUtility.parseNumber((rs.getDouble(3)/hourlyEmployees)*100+""));
					else if(rs.getString("perf_grade").equalsIgnoreCase("B"))
						dto.setHourlyB(ParseNumberUtility.parseNumber((rs.getDouble(3)/hourlyEmployees)*100+""));
					else if(rs.getString("perf_grade").equalsIgnoreCase("C"))
						dto.setHourlyC(ParseNumberUtility.parseNumber((rs.getDouble(3)/hourlyEmployees)*100+""));
				}else if(rs.getString("type").equalsIgnoreCase("5") && rs.getString("perf_grade")!=null){
					if(rs.getString("perf_grade").equalsIgnoreCase("A"))
						dto.setOfficeA(ParseNumberUtility.parseNumber((rs.getDouble(3)/officeEmployees)*100+""));
					else if(rs.getString("perf_grade").equalsIgnoreCase("B"))
						dto.setOfficeB(ParseNumberUtility.parseNumber((rs.getDouble(3)/officeEmployees)*100+""));
					else if(rs.getString("perf_grade").equalsIgnoreCase("C"))
						dto.setOfficeC(ParseNumberUtility.parseNumber((rs.getDouble(3)/officeEmployees)*100+""));
				}
			}
			list.add(dto);

			// End - % for Group
			rs.close();
			ps.close();
			query = new StringBuffer();
			rs = null;
			//query.append("select type,perf_grade,avg(increment_percentage) count from salary_planning group by type,perf_grade");
			//query.append("select type,perf_grade,sum(increment_percentage) count ");
			//query.append("from merit_transaction sp,employee e,team t ");
			//query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");
			query.append("select type,perf_grade,sum(increment_percentage) count from merit_transaction sp where sp.team in ("+teamId+")  and type is not null and perf_grade is not null group by type,perf_grade");
			ps = conn.prepareStatement(query.toString());
			//ps.setString(1, employeeId);
			rs = ps.executeQuery();
			dto = new RatingsAndIncreaseDTO();
			dto.setType("Avg Increase");
			dto = setAvg(rs,dto,countMap);
			list.add(dto);
			rs.close();
			ps.close();
			query = new StringBuffer();
			rs = null;
			//query.append("select type,perf_grade,max(increment_percentage) count from salary_planning group by type,perf_grade");
			//query.append("select type,perf_grade,max(increment_percentage) count ");
			//query.append("from merit_transaction sp,employee e,team t ");
			//query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");
			query.append("select type,perf_grade,max(increment_percentage) count from merit_transaction sp where sp.team in ("+teamId+")  and type is not null and perf_grade is not null group by type,perf_grade");
			ps = conn.prepareStatement(query.toString());
			//ps.setString(1, employeeId);
			rs = ps.executeQuery();
			dto = new RatingsAndIncreaseDTO();
			dto.setType("High Increase");
			dto = setHighLowIncrease(rs,dto);
			list.add(dto);
			rs.close();
			ps.close();
			query = new StringBuffer();
			rs = null;
			//query.append("select type,perf_grade,min(increment_percentage) count from salary_planning group by type,perf_grade");
			//query.append("select type,perf_grade,min(increment_percentage) count ");
			//query.append("from merit_transaction sp,employee e,team t ");
			//query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");
			query.append("select type,perf_grade,min(increment_percentage) count from merit_transaction sp where sp.team in ("+teamId+")  and type is not null and perf_grade is not null group by type,perf_grade");
			ps = conn.prepareStatement(query.toString());
			//ps.setString(1, employeeId);
			rs = ps.executeQuery();
			dto = new RatingsAndIncreaseDTO();
			dto.setType("Low Increase");
			dto = setHighLowIncrease(rs,dto);
			list.add(dto);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in ratingsAndIncreaseSummary"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	// Old method
	/*@Override
	public ArrayList<RatingsAndIncreaseDTO> ratingsAndIncreaseSummary(String teamId,String teamName,String employeeId) {
		logger.info("in ratingsAndIncreaseSummary");
		StringBuffer query = new StringBuffer();
		//query.append("select type,perf_grade,count(perf_grade) count from salary_planning group by type,perf_grade");
		query.append("select type,perf_grade,count(perf_grade) count ");
		query.append("from salary_planning sp,employee e,team t ");
		query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");

		ArrayList<RatingsAndIncreaseDTO> list = new ArrayList<RatingsAndIncreaseDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, employeeId);
			rs = ps.executeQuery();
			RatingsAndIncreaseDTO dto = null;
			dto.setType("Performance Rating");
			dto.setHourlyA("A");
			dto.setHourlyB("B");
			dto.setHourlyC("C");
			dto.setOfficeA("A");
			dto.setOfficeB("B");
			dto.setOfficeC("C");
			list.add(dto);
			dto = new RatingsAndIncreaseDTO();
			dto.setType("Count");
			while(rs.next()){
				if(rs.getString("type").equalsIgnoreCase("hourly") && rs.getString("perf_grade")!=null){
					if(rs.getString("perf_grade").equalsIgnoreCase("A"))
						dto.setHourlyA(rs.getString("count"));
					else if(rs.getString("perf_grade").equalsIgnoreCase("B"))
						dto.setHourlyB(rs.getString("count"));
					else if(rs.getString("perf_grade").equalsIgnoreCase("C"))
						dto.setHourlyC(rs.getString("count"));
				}else if(rs.getString("type").equalsIgnoreCase("office") && rs.getString("perf_grade")!=null){
					if(rs.getString("perf_grade").equalsIgnoreCase("A"))
						dto.setOfficeA(rs.getString("count"));
					else if(rs.getString("perf_grade").equalsIgnoreCase("B"))
						dto.setOfficeB(rs.getString("count"));
					else if(rs.getString("perf_grade").equalsIgnoreCase("C"))
						dto.setOfficeC(rs.getString("count"));
				}
			}
			list.add(dto);
			rs.close();
			ps.close();
			query = new StringBuffer();
			rs = null;
			//query.append("select type,perf_grade,(count/(select count(perf_grade) from salary_planning where b.type=type)) percentage from (select type,perf_grade,count(perf_grade) count from salary_planning b group by type,perf_grade) b");
			query.append("select type,perf_grade,(count/(select count(perf_grade) from salary_planning where b.type=type)) percentage from (select type,perf_grade,count(perf_grade) count from salary_planning b,employee e,team t  where b.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade) b");
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, employeeId);
			rs = ps.executeQuery();
			dto = new RatingsAndIncreaseDTO();
			dto.setType("% for Group");
			while(rs.next()){
				if(rs.getString("type").equalsIgnoreCase("hourly") && rs.getString("perf_grade")!=null){
					if(rs.getString("perf_grade").equalsIgnoreCase("A"))
						dto.setHourlyA(ParseNumberUtility.parseNumber(rs.getDouble(3)*100));
					else if(rs.getString("perf_grade").equalsIgnoreCase("B"))
						dto.setHourlyB(ParseNumberUtility.parseNumber(rs.getDouble(3)*100));
					else if(rs.getString("perf_grade").equalsIgnoreCase("C"))
						dto.setHourlyC(ParseNumberUtility.parseNumber(rs.getDouble(3)*100));
				}else if(rs.getString("type").equalsIgnoreCase("office") && rs.getString("perf_grade")!=null){
					if(rs.getString("perf_grade").equalsIgnoreCase("A"))
						dto.setOfficeA(ParseNumberUtility.parseNumber(rs.getDouble(3)*100));
					else if(rs.getString("perf_grade").equalsIgnoreCase("B"))
						dto.setOfficeB(ParseNumberUtility.parseNumber(rs.getDouble(3)*100));
					else if(rs.getString("perf_grade").equalsIgnoreCase("C"))
						dto.setOfficeC(ParseNumberUtility.parseNumber(rs.getDouble(3)*100));
				}
			}
			list.add(dto);


			rs.close();
			ps.close();
			query = new StringBuffer();
			rs = null;
			//query.append("select type,perf_grade,avg(increment_percentage) count from salary_planning group by type,perf_grade");
			query.append("select type,perf_grade,avg(increment_percentage) count ");
			query.append("from salary_planning sp,employee e,team t ");
			query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, employeeId);
			rs = ps.executeQuery();
			dto = new RatingsAndIncreaseDTO();
			dto.setType("Avg Increase");
			dto = setPercentages(rs,dto);
			list.add(dto);
			rs.close();
			ps.close();
			query = new StringBuffer();
			rs = null;
			//query.append("select type,perf_grade,max(increment_percentage) count from salary_planning group by type,perf_grade");
			query.append("select type,perf_grade,max(increment_percentage) count ");
			query.append("from salary_planning sp,employee e,team t ");
			query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, employeeId);
			rs = ps.executeQuery();
			dto = new RatingsAndIncreaseDTO();
			dto.setType("High Increase");
			dto = setPercentages(rs,dto);
			list.add(dto);
			rs.close();
			ps.close();
			query = new StringBuffer();
			rs = null;
			//query.append("select type,perf_grade,min(increment_percentage) count from salary_planning group by type,perf_grade");
			query.append("select type,perf_grade,min(increment_percentage) count ");
			query.append("from salary_planning sp,employee e,team t ");
			query.append(" where sp.emp_id=e.employee_id and t.team_id=e.team_id and t.supervisor_employee_id =? and t.team_id in ("+teamId+") group by type,perf_grade");
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, employeeId);
			rs = ps.executeQuery();
			dto = new RatingsAndIncreaseDTO();
			dto.setType("Low Increase");
			dto = setPercentages(rs,dto);
			list.add(dto);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in ratingsAndIncreaseSummary"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}*/
	private RatingsAndIncreaseDTO setAvg(ResultSet rs, RatingsAndIncreaseDTO dto,HashMap<String, Integer> countMap) throws SQLException{
		while(rs.next()){//{hourlyA=1, hourlyc=1, officeA=3, officeC=3, officeB=1}
			if(rs.getString("type").equalsIgnoreCase("3") && rs.getString("perf_grade")!=null){
				if(rs.getString("perf_grade").equalsIgnoreCase("A")){
					dto.setHourlyA(ParseNumberUtility.parseNumber(rs.getDouble(3)/countMap.get("hourlyA")+""));
				}else if(rs.getString("perf_grade").equalsIgnoreCase("B")){
					dto.setHourlyB(ParseNumberUtility.parseNumber(rs.getDouble(3)/countMap.get("hourlyB")+""));
				}else if(rs.getString("perf_grade").equalsIgnoreCase("C")){
					dto.setHourlyC(ParseNumberUtility.parseNumber(rs.getDouble(3)/countMap.get("hourlyC")+""));
				}
			}else if(rs.getString("type").equalsIgnoreCase("5") && rs.getString("perf_grade")!=null){
				if(rs.getString("perf_grade").equalsIgnoreCase("A")){
					dto.setOfficeA(ParseNumberUtility.parseNumber(rs.getDouble(3)/countMap.get("officeA")+""));
				}else if(rs.getString("perf_grade").equalsIgnoreCase("B")){
					dto.setOfficeB(ParseNumberUtility.parseNumber(rs.getDouble(3)/countMap.get("officeB")+""));
				}else if(rs.getString("perf_grade").equalsIgnoreCase("C")){
					dto.setOfficeC(ParseNumberUtility.parseNumber(rs.getDouble(3)/countMap.get("officeC")+""));
				}
			}
		}
		return dto;
	}

	private RatingsAndIncreaseDTO setHighLowIncrease(ResultSet rs, RatingsAndIncreaseDTO dto) throws SQLException{
		while(rs.next()){
			if(rs.getString("type").equalsIgnoreCase("3") && rs.getString("perf_grade")!=null){
				if(rs.getString("perf_grade").equalsIgnoreCase("A"))
					dto.setHourlyA(ParseNumberUtility.parseNumber(rs.getDouble(3)+""));
				else if(rs.getString("perf_grade").equalsIgnoreCase("B"))
					dto.setHourlyB(ParseNumberUtility.parseNumber(rs.getDouble(3)+""));
				else if(rs.getString("perf_grade").equalsIgnoreCase("C"))
					dto.setHourlyC(ParseNumberUtility.parseNumber(rs.getDouble(3)+""));
			}else if(rs.getString("type").equalsIgnoreCase("5") && rs.getString("perf_grade")!=null){
				if(rs.getString("perf_grade").equalsIgnoreCase("A"))
					dto.setOfficeA(ParseNumberUtility.parseNumber(rs.getDouble(3)+""));
				else if(rs.getString("perf_grade").equalsIgnoreCase("B"))
					dto.setOfficeB(ParseNumberUtility.parseNumber(rs.getDouble(3)+""));
				else if(rs.getString("perf_grade").equalsIgnoreCase("C"))
					dto.setOfficeC(ParseNumberUtility.parseNumber(rs.getDouble(3)+""));
			}
		}
		return dto;
	}

	@Override
	public ArrayList<SalaryPositionRangeDTO> salaryPositionRangeDetails() {
		logger.info("in salaryPositionRangeDetails");
		StringBuffer query = new StringBuffer();
		//query.append("select id,overallperformanceRating,aggregate_expected,first_quartile,second_quartile,third_quartile,fourth_quartile from position_sal_range order by id");
		query.append("select overallperformanceRating,aggregate_expected,concat(first_Quartile_Min,' - ',first_Quartile_Max) first_quartile,concat(second_Quartile_Min,' - ',second_Quartile_Max) second_quartile,concat(third_Quartile_Min,' - ',third_Quartile_Max) third_quartile,concat(fourth_Quartile_Min,' - ',fourth_Quartile_Max) fourth_quartile from merit_admin_guidelines where merit_period = ?");
		ArrayList<SalaryPositionRangeDTO> list = new ArrayList<SalaryPositionRangeDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			String meritPeriod = getMeritPeriod(conn,ps,rs);
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, meritPeriod);
			rs = ps.executeQuery();
			while(rs.next()){
				SalaryPositionRangeDTO dto = new SalaryPositionRangeDTO();
				dto.setOverallPerformanceRating(rs.getString("overallperformanceRating"));
				dto.setAggregateExpected(rs.getString("aggregate_expected"));
				dto.setFirstQuartile(rs.getString("first_quartile"));
				dto.setSecondQuartile(rs.getString("second_quartile"));
				dto.setThirdQuartile(rs.getString("third_quartile"));
				dto.setFourthQuartile(rs.getString("fourth_quartile"));
				list.add(dto);				
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in salaryPositionRangeDetails"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}

	@Override
	public HashMap<String, Integer> getSalaryPlanningGridView(String empId) {
		logger.info("in getSalaryPlanningGridView");
		StringBuffer query = new StringBuffer();
		query.append("select employeeIdwidth,coworker_name,type,rate,perfGrade,incrementPercentage,newRate,lumsum,jobTitle,updatedDate,grade,compaRatio,minimum,midpoint,maximum,quartile from salary_planning_gridView where employeeId=?");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			if(rs.next()){
				map.put("employeeId", rs.getInt("employeeIdwidth"));
				map.put("coworker_name", rs.getInt("coworker_name"));
				map.put("type", rs.getInt("type"));
				map.put("rate", rs.getInt("rate"));
				map.put("perfGrade", rs.getInt("perfGrade"));
				map.put("incrementPercentage", rs.getInt("incrementPercentage"));
				map.put("newRate", rs.getInt("newRate"));
				map.put("lumsum", rs.getInt("lumsum"));
				map.put("jobTitle", rs.getInt("jobTitle"));
				map.put("updatedDate", rs.getInt("updatedDate"));
				map.put("grade", rs.getInt("grade"));
				map.put("compaRatio", rs.getInt("compaRatio"));
				map.put("minimum", rs.getInt("minimum"));
				map.put("midpoint", rs.getInt("midpoint"));
				map.put("maximum", rs.getInt("maximum"));
				map.put("quartile", rs.getInt("quartile"));
			}else{ // Set default width
				map.put("employeeId", 50);
				map.put("coworker_name", 50);
				map.put("type", 50);
				map.put("rate", 50);
				map.put("perfGrade", 50);
				map.put("incrementPercentage", 50);
				map.put("newRate", 50);
				map.put("lumsum", 50);
				map.put("jobTitle", 50);
				map.put("updatedDate", 50);
				map.put("grade", 50);
				map.put("compaRatio", 50);
				map.put("minimum", 50);
				map.put("midpoint", 50);
				map.put("maximum", 50);
				map.put("quartile", 50);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in getSalaryPlanningGridView"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return map;
	}

	public int setDefaultWidth(int width){
		//if(width != null)

		return width;
	}

	@Override
	public void updateSalaryPlanningColumnWidth(String field, String width,String empId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer(); 
		try {
			if(!field.equalsIgnoreCase("id")){
				if(field.equalsIgnoreCase("employeeId"))
					field = "employeeIdwidth";
				conn = this.getConnection();
				boolean flag = checkSalaryPlanningColumnWidthExists(conn, ps, rs, empId);
				if(flag){ // update
					query.append("update salary_planning_gridView set ");
					query.append(" "+field+" = '"+width+"'");
					query.append(" where employeeId = ?");
					ps = conn.prepareStatement(query.toString());
					ps.setString(1, empId);
				}else{ // insert
					query.append("Insert into salary_planning_gridView (employeeId,"+field+" ) values (?,?)");
					ps = conn.prepareStatement(query.toString());
					ps.setString(1, empId);
					ps.setString(2, width);
				}
				int i =ps.executeUpdate();
				logger.info("Result in updateSalaryPlanningColumnWidth = field "+field+" width = "+width+" employee Id = "+empId+" i =="+i);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in updateSalaryPlanningColumnWidth"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}

	private boolean checkSalaryPlanningColumnWidthExists(Connection conn,PreparedStatement ps,ResultSet rs,String empId){
		try{
			ps = conn.prepareStatement("select employeeId from salary_planning_gridView where employeeId=?");
			ps.setString(1, empId);
			rs = ps.executeQuery();
			if(rs.next())
				return true;
			else
				return false;
		}catch(Exception e){

		}
		return false;
	}
	private String getMeritPeriod(Connection conn,PreparedStatement ps,ResultSet rs){
		try{
			ps = conn.prepareStatement("select value from merit_period_update");
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getString("value");
			else
				return "";
		}catch(Exception e){

		}
		return "";
	}
	private String getCurrentApprovalLevel(String empId,String teamName){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getCurrentApprovalLevel(conn, ps, rs, empId, teamName);
		}catch (Exception ex) {
			logger.info("Excpetion in getCurrentApprovalLevel"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	private String getCurrentApprovalLevel(Connection conn,PreparedStatement ps,ResultSet rs,String empId,String teamName){
		try {
			ps = conn.prepareStatement("select Approval_Level from Team_Approval_Level where EMP_ID=? and Team=?");
			ps.setString(1, empId);
			ps.setString(2, teamName);
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString("Approval_Level");
			}else{
				ps = conn.prepareStatement("SELECT Merti_Admin_Approaval_Level FROM users where employee_id=?");
				ps.setString(1, empId);
				rs = ps.executeQuery();
				if(rs.next())
					return rs.getString("Merti_Admin_Approaval_Level");
			}
			return "";
		}catch (Exception ex) {
			logger.info("Excpetion in getCurrentApprovalLevel"+ex.getMessage());
		}
		return null;
	}
	
	public String approveAll(JSONArray list,TelligentUser telligentUser,String team){
		String status = "";
		
		logger.info("in approveAll");
		StringBuffer updateQuery = new StringBuffer();
		//boolean updated = false;
		Connection conn = null;
		PreparedStatement ps = null;
		String currentApprovalLevel = "";
		try{
			conn = this.getConnection();
			conn.setAutoCommit(false);
			updateQuery.append("update merit_transaction set current_approval_level =?,approved_by =?,approved_date = sysdate() where emp_id = ?");
			ps = conn.prepareStatement(updateQuery.toString());
			currentApprovalLevel = getCurrentApprovalLevel(telligentUser.getEmployeeId(),team);
			for(int i=0;i<list.size();i++){
				String empId = list.getJSONObject(i).getString("employeeId");
				ps.setString(1, currentApprovalLevel);
				ps.setString(2, telligentUser.getUsername());
				ps.setString(3, empId);
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			status = "All records approved successfully";
			
		}catch(Exception ex){
			status = "Error while approving records.";
			ex.printStackTrace();
			logger.error("Exception in approveAll :: "+ex);
			try {
				conn.rollback();
			} catch (SQLException e) {}
		}finally{
			this.closeAll(conn, ps);
		}
		
		return status;
	}
	public String sendForApproval(JSONArray list,TelligentUser telligentUser,String team,String submittedTo,String submittedLevel){
		String status = "";
		
		logger.info("in sendForApproval");
		StringBuffer updateQuery = new StringBuffer();
		Connection conn = null;
		PreparedStatement ps = null;
		String currentApprovalLevel = "";
		try{
			conn = this.getConnection();
			conn.setAutoCommit(false);
			updateQuery.append("update merit_transaction set date_submitted = sysdate(),submitted_by = ?,current_submit_level=? where emp_id = ?");
			ps = conn.prepareStatement(updateQuery.toString());
			for(int i=0;i<list.size();i++){
				String empId = list.getJSONObject(i).getString("employeeId");
				ps.setString(1, telligentUser.getUsername());
				ps.setString(2, submittedLevel);
				ps.setString(3, empId);
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			sendEmail(list,submittedTo);
			status = "All records sent for approval successfully.";
			
		}catch(Exception ex){
			status = "Error while sending for approval.";
			ex.printStackTrace();
			logger.error("Exception in approveAll :: "+ex);
			try {
				conn.rollback();
			} catch (SQLException e) {}
		}finally{
			this.closeAll(conn, ps);
		}
		
		return status;
	}
	
	public ArrayList<AppUserListDTO> getApprovalLevels(String teamName,String userEmpID){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		EmployeeDAO empDao =  new EmployeeDAO();
		TelligentUser user = telligentUtility.getTelligentUser();
		ArrayList<TeamDTO> list =empDao.getEmployeeTeams(user.getEmployeeId());
		ArrayList<AppUserListDTO> finalList  = null;
		String teams ="";
		int i =0;
		for(TeamDTO dto : list){
			if (i > 0 && i < list.size()){
				teams = teams+",";
			}
			teams = teams+"'"+dto.getTeamName()+"'";
			
			i = i+1;
		}
		 ArrayList<AppUserListDTO> teamApprovalLevelList = new ArrayList<AppUserListDTO>();
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("select a.EMP_ID,TEAM,a.Approval_Level,a.EFFECTIVE_DATE,a.END_EFFECTIVE_DATE,b.L_name l_name,b.f_name f_name,b.m_name from Team_Approval_Level a,EMP_PERSONAL b where a.emp_id = b.emp_id and team in ("+teams+")order by l_name,f_name asc");
			//ps.setString(1, userEmpID);
			//ps.setString(2, teamName);
			rs = ps.executeQuery();
			while(rs.next()){
				AppUserListDTO dto = new AppUserListDTO();
				String l_name = rs.getString("l_name");
				String f_name = rs.getString("f_name");
				String m_name = rs.getString("m_name");
				dto.setEmpID(l_name + ", "+f_name +" "+m_name+" ("+rs.getString("EMP_ID")+")");
				dto.setTeam(rs.getString("TEAM"));
				dto.setMeritApprovalLevel(rs.getString("Approval_Level"));
				dto.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
				dto.setEndDate(rs.getString("END_EFFECTIVE_DATE"));
				teamApprovalLevelList.add(dto);
			}
			HashSet<AppUserListDTO> teamApprovalLevelSet = new HashSet<AppUserListDTO>(teamApprovalLevelList);
			finalList = new ArrayList<AppUserListDTO>(teamApprovalLevelSet);
		}catch (Exception ex) {
			logger.info("Excpetion in getApprovalLevels"+ex.getMessage());
		}finally{
			this.closeAll(conn,ps,rs);
		}
		return finalList;
	}
	
	public boolean sendEmail(JSONArray list,String empID) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		String query = "SELECT email_id,user_name FROM users where employee_id = ?";
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, empID);
			rs = ps.executeQuery();
			if(rs.next()){
				String emailId = rs.getString("email_id");
				String userName = rs.getString("user_name");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<!DOCTYPE html>");
				buffer.append("<HTML><head>");
				buffer.append("<style>");
				buffer.append("table, th, td {");
				buffer.append("border: 1px solid black;");
				buffer.append("border-collapse: collapse;}");
				buffer.append("th, td {");
				buffer.append("padding: 5px;");
				buffer.append("text-align: center;}");
				buffer.append("</style>");
				buffer.append("</head>");
				buffer.append("<BODy>");
				buffer.append("Dear "+userName+",\n");
				buffer.append("\t <br/> Below records are waiting for you approval :\n<br/>");
				buffer.append("<table border=\"1\" style=\"width:100%\">");
				buffer.append("<tr><th>Team</th><th>Employee ID</th><th>Employee Name</th><th>Performance Grade</th><th>Increase %</th></tr>");
				for(int i=0;i<list.size();i++){
					String empId = list.getJSONObject(i).getString("employeeId");
					String empName = list.getJSONObject(i).getString("coworker_name");
					String team = list.getJSONObject(i).getString("team");
					String grade = list.getJSONObject(i).getString("perfGrade");
					String increment = list.getJSONObject(i).getString("incrementPercentage");
					buffer.append("<tr> <td>"+team+"</td>");
					buffer.append("<td>"+empId+"</td>");		
					buffer.append("<td>"+empName+"</td>");
					buffer.append("<td>"+grade+"</td>");
					buffer.append("<td>"+increment+"</td> </tr>");
				}
				buffer.append("<br/>Talent Admin link for approval : "+handler.getMessage("label.applicationURL"));
				buffer.append("<br/></table></BODY></HTML>");
				buffer.append("\nRegards,\n<br/>");
				buffer.append("Talent Admin.");
					MailUtility.sendMail(emailId, "", "", prop.get("smtpUsername").toString(), handler.getMessage("label.sendForApprovalSubject"), buffer.toString(), "application/html");;
					return true;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in sendEmail"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps);
		}
		return false;
	}
	
}
