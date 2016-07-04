package com.telligent.model.daos.impl;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import com.telligent.common.handlers.MessageHandler;
import com.telligent.common.user.TelligentUser;
import com.telligent.core.system.annotation.SpringBean;
import com.telligent.model.db.AbstractDBManager;
import com.telligent.model.dtos.CityDTO;
import com.telligent.model.dtos.EmpDTO;
import com.telligent.model.dtos.EmployeeCompensationDTO;
import com.telligent.model.dtos.EmployeeDTO;
import com.telligent.model.dtos.EmployeeOtherDTO;
import com.telligent.model.dtos.EmployeePositionDTO;
import com.telligent.model.dtos.EmploymentDTO;
import com.telligent.model.dtos.JobGradeDTO;
import com.telligent.model.dtos.MapDTO;
import com.telligent.model.dtos.StateDTO;
import com.telligent.model.dtos.TeamDTO;
import com.telligent.model.dtos.User;
import com.telligent.util.BASE64DecodedMultipartFile;
import com.telligent.util.DateUtility;
import com.telligent.util.ParseNumberUtility;
import com.telligent.util.TelligentUtility;

/**
 * @author spothu
 *
 */
@SpringBean
public class EmployeeDAO extends AbstractDBManager{


	public final Logger logger = Logger.getLogger(EmployeeDAO.class);
	@Autowired
	TelligentUtility telligentUtility;

	/**
	 *  Method to get respective teams of logged in employee
	 *  @param employeeId
	 * 
	 */
	public ArrayList<TeamDTO> getEmployeeTeams(TelligentUser user) {
		logger.info("in getEmployeeTeam DAO");
		ArrayList<TeamDTO> list = new ArrayList<TeamDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query;
		if (user.getRole().equalsIgnoreCase("meritAdmin")){
			query = "SELECT t.team_id team_id ,app.team team_name,team_parent FROM team t,Team_Approval_Level app where t.team_name = app.team ";
		}else{
		query = "SELECT t.team_id team_id ,app.team team_name,team_parent FROM team t,Team_Approval_Level app where t.team_name = app.team and app.emp_id=?";
		}
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query);
			if (!user.getRole().equalsIgnoreCase("meritAdmin")){
			ps.setString(1, user.getEmployeeId());
			}
			rs = ps.executeQuery();
			while(rs.next()){
				TeamDTO dto = new TeamDTO();
				dto.setTeamId(rs.getString("team_id"));
				dto.setTeamName(rs.getString("team_name"));
				list.add(dto);
				getSubTeams(conn,ps,rs,rs.getString("team_id"),list);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in getEmployeeTeam"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}


	public void getSubTeams(Connection conn,PreparedStatement ps,ResultSet rs,String superTeam,ArrayList<TeamDTO> list) {
		logger.info("in getSubTeams DAO");


		StringBuffer teamQuery = new StringBuffer();
		teamQuery.append("select t1.team_id as lev1,t2.team_id as lev2 ,t3.team_id as lev3,t4.team_id as lev4,t5.team_id as lev5,");
		teamQuery.append("t6.team_id as lev6,t7.team_id as lev7,t8.team_id as lev8,t9.team_id as lev9,t10.team_id as lev10,t11.team_id as lev11");
		teamQuery.append(" from team as t1");
		teamQuery.append(" left join team as t2 on t2.team_parent =t1.team_id");
		teamQuery.append(" left join team as t3 on t3.team_parent =t2.team_id");
		teamQuery.append(" left join team as t4 on t4.team_parent =t3.team_id");
		teamQuery.append(" left join team as t5 on t5.team_parent =t4.team_id");
		teamQuery.append(" left join team as t6 on t6.team_parent =t5.team_id");
		teamQuery.append(" left join team as t7 on t7.team_parent =t6.team_id");
		teamQuery.append(" left join team as t8 on t8.team_parent =t7.team_id");
		teamQuery.append(" left join team as t9 on t9.team_parent =t8.team_id");
		teamQuery.append(" left join team as t10 on t10.team_parent =t9.team_id");
		teamQuery.append(" left join team as t11 on t11.team_parent =t10.team_id");
		teamQuery.append(" where t1.team_id =?");
		String subTeams ="";
		try {
			ps = conn.prepareStatement(teamQuery.toString());
			ps.setString(1, superTeam);
			rs = ps.executeQuery();

			while(rs.next()){
				if(rs.getString(1) != null && !rs.getString(1).equals(""))
					subTeams = "'"+subTeams+rs.getString(1)+"'";
				if(rs.getString(2) != null && !rs.getString(2).equals(""))
					subTeams = subTeams+",'"+rs.getString(2)+"'";
				if(rs.getString(3) != null && !rs.getString(3).equals(""))
					subTeams = subTeams+",'"+rs.getString(3)+"'";
				if(rs.getString(4) != null && !rs.getString(4).equals(""))
					subTeams = subTeams+",'"+rs.getString(4)+"'";
				if(rs.getString(5) != null && !rs.getString(5).equals(""))
					subTeams = subTeams+",'"+rs.getString(5)+"'";
				if(rs.getString(6) != null && !rs.getString(6).equals(""))
					subTeams = subTeams+",'"+rs.getString(6)+"'";
				if(rs.getString(7) != null && !rs.getString(7).equals(""))
					subTeams = subTeams+",'"+rs.getString(7)+"'";
				if(rs.getString(8) != null && !rs.getString(8).equals(""))
					subTeams = subTeams+",'"+rs.getString(8)+"'";
				if(rs.getString(9) != null && !rs.getString(9).equals(""))
					subTeams = subTeams+",'"+rs.getString(9)+"'";
				if(rs.getString(10) != null && !rs.getString(10).equals(""))
					subTeams = subTeams+",'"+rs.getString(10)+"'";
				if(rs.getString(11) != null && !rs.getString(11).equals(""))
					subTeams = subTeams+",'"+rs.getString(11)+"'";
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in getSubTeams"+ex.getMessage());
		} 

		String query = "SELECT distinct(team_id) team_id,team_name FROM team where team_id in("+subTeams+")";
		try {
			ps = conn.prepareStatement(query);
			//ps.setString(1, superTeam);
			rs = ps.executeQuery();
			while(rs.next()){
				TeamDTO dto = new TeamDTO();
				dto.setTeamId(rs.getString("team_id"));
				dto.setTeamName(rs.getString("team_name"));
				list.add(dto);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in getSubTeams"+ex.getMessage());
		} 
	}

	/**
	 *  Method to get respective team employees
	 *  @param teamId
	 * 
	 */
	public ArrayList<EmployeeDTO> getTeamEmployees(String teamId) {
		logger.info("in getTeamEmployees DAO");
		ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT employee_id,employee_name,last_name,email_id,salary FROM employee e where team_id=?";
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, teamId);
			rs = ps.executeQuery();
			while(rs.next()){
				EmployeeDTO dto = new EmployeeDTO();
				dto.setEmployeeId(rs.getString("employee_id"));
				dto.setEmployeeName(rs.getString("employee_name"));
				dto.setLastName(rs.getString("last_name"));
				dto.setEmail(rs.getString("email_id"));
				dto.setSalary(rs.getString("salary"));
				list.add(dto);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Excpetion in getTeamEmployees "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}

	public boolean checkEmployeeId(Connection conn,PreparedStatement ps,ResultSet rs,String empId){
		try{
			ps = conn.prepareStatement("select emp_id from EMP_PERSONAL where emp_id=?");
			ps.setString(1, empId);
			rs = ps.executeQuery();
			if(rs.next())
				return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	private int employeeHistoryUpdate(Connection conn,PreparedStatement ps,String empId){
		try{
			StringBuffer query = new StringBuffer();
			query.append("insert into EMP_PERSONAL_HIS(EMP_NO,EMP_ID,BADGE,EFFECTIVE_DATE,F_NAME,M_NAME,L_NAME,P_EMAIL,H_PHONE,M_PHONE,ADDRESS_L1,ADDRESS_L2,CITY,STATE,ZIP,");
			query.append("DATE_OF_BIRTH,IS_MINOR,WORK_PHONE,WORK_MOBILE_PHONE,WORK_EMAIL,EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_EMAIL,");
			query.append("EMC_H_PHONE,EMC_M_PHONE,DATE_UPDATED,UPDATED_BY,PICTURE,SOCIAL_SEC_NO,END_EFFECTIVE_DATE)");// Always keep PICTURE column as last one
			query.append("select EMP_NO,EMP_ID,BADGE,EFFECTIVE_DATE,F_NAME,M_NAME,L_NAME,P_EMAIL,H_PHONE,M_PHONE,ADDRESS_L1,ADDRESS_L2,CITY,STATE,ZIP,DATE_OF_BIRTH,IS_MINOR,WORK_PHONE,WORK_MOBILE_PHONE,WORK_EMAIL,EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_EMAIL,EMC_H_PHONE,EMC_M_PHONE,DATE_UPDATED,UPDATED_BY,PICTURE,SOCIAL_SEC_NO,END_EFFECTIVE_DATE from EMP_PERSONAL where EMP_ID='"+empId+"'");
			ps = conn.prepareStatement(query.toString());
			int i = ps.executeUpdate();
			ps.close();
			return i;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	@SuppressWarnings("deprecation")
	public EmployeeDTO saveEmployeeDetails(EmployeeDTO employeeDTO,TelligentUser telligentUser,MessageHandler messageHandler) {
		logger.info("in saveEmployeeDetails DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		String meritPeriod = "";
		DecimalFormat decimalFormat = new DecimalFormat("000000000");
		try {
			conn = this.getConnection();
			String empId = "";
			try {
				empId = decimalFormat.format(Integer.parseInt(employeeDTO.getEmployeeId()));
			} catch (Exception e) {
				empId = employeeDTO.getEmployeeId();
				if(empId.length() <9){
					int length = 9-empId.length();
					StringBuffer temp = new StringBuffer();
					for(int i =0 ;i<length ; i++){
						temp.append("0");
					}
					empId = temp.toString()+empId;
				}
			}
			employeeDTO.setEmployeeId(empId);
			if(employeeDTO.getOperation().equalsIgnoreCase("edit")){
				conn.setAutoCommit(false);
				query = new StringBuffer();
				String str = getEffectiveDate(conn,ps,rs,"EMP_PERSONAL",employeeDTO.getEmployeeId());
				boolean dateFlag = DateUtility.compareDates(employeeDTO.getEffectiveDate(), str);
				if(true){
					if(!employeeDTO.getPicture().getOriginalFilename().equalsIgnoreCase("")){
						query.append("update EMP_PERSONAL set BADGE=?,EFFECTIVE_DATE=?,F_NAME=?,M_NAME=?,L_NAME=?,P_EMAIL=?,H_PHONE=?,M_PHONE=?,ADDRESS_L1=?,ADDRESS_L2=?,CITY=?,STATE=?,ZIP=?,");
						query.append("DATE_OF_BIRTH=?,IS_MINOR=?,WORK_PHONE=?,WORK_MOBILE_PHONE=?,WORK_EMAIL=?,EMC_L_NAME=?,EMC_F_NAME=?,EMC_REL=?,EMC_EMAIL=?,");
						query.append("EMC_H_PHONE=?,EMC_M_PHONE=?,DATE_UPDATED=sysdate(),UPDATED_BY=?,SOCIAL_SEC_NO=?,END_EFFECTIVE_DATE='"+new java.sql.Date(new Date(employeeDTO.getEffectiveDate()).getTime()-1)+"',WORK_EXTN=?,PICTURE=? where EMP_ID='"+empId+"'");					
					}else{
						query.append("update EMP_PERSONAL set BADGE=?,EFFECTIVE_DATE=?,F_NAME=?,M_NAME=?,L_NAME=?,P_EMAIL=?,H_PHONE=?,M_PHONE=?,ADDRESS_L1=?,ADDRESS_L2=?,CITY=?,STATE=?,ZIP=?,");
						query.append("DATE_OF_BIRTH=?,IS_MINOR=?,WORK_PHONE=?,WORK_MOBILE_PHONE=?,WORK_EMAIL=?,EMC_L_NAME=?,EMC_F_NAME=?,EMC_REL=?,EMC_EMAIL=?,");
						query.append("EMC_H_PHONE=?,EMC_M_PHONE=?,DATE_UPDATED=sysdate(),UPDATED_BY=?,END_EFFECTIVE_DATE='"+new java.sql.Date(new Date(employeeDTO.getEffectiveDate()).getTime()-1)+"',SOCIAL_SEC_NO=?,WORK_EXTN=? where EMP_ID='"+empId+"'");					
					}
					ps = conn.prepareStatement(query.toString());
					setPreparedStatementsForSave(conn,ps, employeeDTO, telligentUser,"edit");
				}else{
					employeeDTO.setErrorMessage("Effective Date should be greater than current Effective Date");
					conn.rollback();
				}
			}else{
				boolean flag = checkEmployeeId(conn, ps, rs, empId);
				if(flag){
					employeeDTO.setErrorMessage("Employee ID already Exists");
					return employeeDTO;
				}else{
					conn.setAutoCommit(false);
					query.append("insert into EMP_PERSONAL(EMP_ID,BADGE,EFFECTIVE_DATE,F_NAME,M_NAME,L_NAME,P_EMAIL,H_PHONE,M_PHONE,ADDRESS_L1,ADDRESS_L2,CITY,STATE,ZIP,");
					query.append("DATE_OF_BIRTH,IS_MINOR,WORK_PHONE,WORK_MOBILE_PHONE,WORK_EMAIL,EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_EMAIL,");
					query.append("EMC_H_PHONE,EMC_M_PHONE,DATE_UPDATED,UPDATED_BY,SOCIAL_SEC_NO,END_EFFECTIVE_DATE,WORK_EXTN,PICTURE)");// Always keep PICTURE column as last one
					query.append("values ('"+empId+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?,?,'"+new java.sql.Date(new Date(messageHandler.getMessage("effectiveEndDate")).getTime())+"',?,?)");
					ps = conn.prepareStatement(query.toString());
					setPreparedStatementsForSave(conn,ps, employeeDTO, telligentUser,"save");
					/*int i = ps.executeUpdate();
					ps.close();
					if(i>0){
						conn.commit();
						String temp = employeeDTO.getEmployeeId();
						employeeDTO = new EmployeeDTO();
						employeeDTO.setSuccessMessage("success");
						employeeDTO.setEmployeeId(temp);
						temp = null;
					}else{
						conn.rollback();
						employeeDTO.setErrorMessage("Employee Details not Saved");
					}*/
				}
			}
			int i  = ps.executeUpdate();
			ps = conn.prepareStatement("select value from merit_period_update order by updated_by desc");
			rs = ps.executeQuery();

			if (rs.next()){
				meritPeriod = rs.getString("value");
			}
			if(i>0){
				if(updateMeritAdminEmpPersonal(conn,ps,rs,employeeDTO,telligentUser.getEmployeeId(),meritPeriod)){
					String temp = employeeDTO.getEmployeeId();
					employeeDTO = new EmployeeDTO();
					employeeDTO.setEmployeeId(temp);
					employeeDTO.setSuccessMessage("success");
					conn.commit();
					temp = null;					
				}else{
					employeeDTO.setErrorMessage("Employee Details not Saved");
					conn.rollback();
				}
			}else{
				employeeDTO.setErrorMessage("Employee Details not Saved");
				conn.rollback();
			}
		}catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {}
			ex.printStackTrace();
			employeeDTO.setErrorMessage("Employee Details not Saved :: "+ex.getMessage());
			logger.info("Excpetion in saveEmployeeDetails :: "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return employeeDTO;
	}
	public boolean updateMeritAdminEmpPersonal(Connection conn,PreparedStatement ps,ResultSet rs,EmployeeDTO dto,String loggedInEmpId,String meritPeriod){
		boolean flag = false;
		StringBuffer query = new StringBuffer();
		try{
			ps = conn.prepareStatement("select emp_no from merit_transaction where emp_id=?");
			ps.setString(1, dto.getEmployeeId());
			rs = ps.executeQuery();
			ps=null;
			if(rs.next()){
				query.append("update merit_transaction set emp_id=?,emp_no=?,Fname=?,LName=?,MName=?,updated_date=sysdate(),updated_by=?,merit_period=? ");
				query.append("where emp_id=?");
				ps = conn.prepareStatement(query.toString());
				ps.setString(1, dto.getEmployeeId());
				ps.setString(2, rs.getString("emp_no"));
				ps.setString(3, dto.getFirstName());
				ps.setString(4, dto.getLastName());
				ps.setString(5, dto.getMiddleName());
				ps.setString(6, loggedInEmpId);
				ps.setString(7, meritPeriod);
				ps.setString(8, dto.getEmployeeId());
				int i= ps.executeUpdate();
				if(i>0)
					flag =true;
			}else{
				query.append("insert into merit_transaction (emp_id,emp_no,Fname,LName,MName,updated_date,updated_by,merit_period,current_approval_level) ");
				query.append("select EMP_ID,EMP_NO,F_NAME,L_NAME,M_NAME,sysdate(),UPDATED_BY,'"+meritPeriod+"',0 from EMP_PERSONAL where EMP_ID=?");
				ps = conn.prepareStatement(query.toString());
				ps.setString(1, dto.getEmployeeId());
				int i= ps.executeUpdate();
				if(i>0)
					flag =true;
			}
		}catch(Exception e){
			logger.error("Exception in updateMeritAdminEmpPersonal : "+e.getMessage());
		}
		return flag;
	}
	private JobGradeDTO getJobGradeDetails(Connection conn,PreparedStatement ps,ResultSet rs,String empId,String freq,String primJob){
		JobGradeDTO dto = new JobGradeDTO();
		try{
			ps = conn.prepareStatement("select id,value,description,minimum,midpoint,maximum,quartileLow1,quartileLow2,quartileLow3,quartileLow4,quartileHigh1,quartileHigh2,quartileHigh3,quartileHigh4 from job_grade where primary_job=? and rate_frequency=? and isActive=1");
			ps.setString(1, primJob);
			ps.setString(2, freq);
			rs = ps.executeQuery();
			if(rs.next()){
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setQuartileLow1(ParseNumberUtility.parseNumber(rs.getString("quartileLow1")));
				dto.setQuartileLow2(ParseNumberUtility.parseNumber(rs.getString("quartileLow2")));
				dto.setQuartileLow3(ParseNumberUtility.parseNumber(rs.getString("quartileLow3")));
				dto.setQuartileLow4(ParseNumberUtility.parseNumber(rs.getString("quartileLow4")));
				dto.setQuartileHigh1(ParseNumberUtility.parseNumber(rs.getString("quartileHigh1")));
				dto.setQuartileHigh2(ParseNumberUtility.parseNumber(rs.getString("quartileHigh2")));
				dto.setQuartileHigh3(ParseNumberUtility.parseNumber(rs.getString("quartileHigh3")));
				dto.setQuartileHigh4(ParseNumberUtility.parseNumber(rs.getString("quartileHigh4")));
				dto.setMinimum(ParseNumberUtility.parseNumber(rs.getString("minimum")));
				dto.setMaximum(ParseNumberUtility.parseNumber(rs.getString("maximum")));
				dto.setMidpoint(ParseNumberUtility.parseNumber(rs.getString("midpoint")));
			}
		}catch(Exception e){}
		return dto;
	}
	private float parseFloat(String str){
		try{
			return Float.parseFloat(str);
		}catch(Exception e){
			return 0;
		}
	}
	@SuppressWarnings("deprecation")
	public String saveEmployeeCompensation( EmployeeCompensationDTO compDTO,TelligentUser telligentUser,MessageHandler messageHandler) {
		logger.info("in saveEmployeeCompensation DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		DecimalFormat decimalFormat = new DecimalFormat("000000000");
		try {
			conn = this.getConnection();

			String primJob = "";
			int noOfPayPeriod = 0;
			ps = conn.prepareStatement("SELECT PRIM_JOB from EMP_POSITION_DATA where emp_id=?");
			ps.setString(1, compDTO.getEmployeeId());
			rs = ps.executeQuery();
			if(rs.next())
				primJob = rs.getString("PRIM_JOB");
			ps.close();rs.close();
			ps = conn.prepareStatement("SELECT NO_OF_PAY_PERIODS FROM pay_group where id=?");
			ps.setString(1, compDTO.getPayGroup());
			rs = ps.executeQuery();
			if(rs.next())
				noOfPayPeriod = rs.getInt("NO_OF_PAY_PERIODS");
			ps.close();rs.close();
			int annualStandardHours = 0;
			float compRatio = 0;
			float annualPay = 0;
			String quartile = "";
			JobGradeDTO jobGradeDTO = getJobGradeDetails(conn, ps, rs, compDTO.getEmployeeId(), compDTO.getBaseRateFrequency(),primJob);
			try{
				annualStandardHours = calculateAnnualStandarHours(Integer.parseInt(compDTO.getScheduledHours()), compDTO.getHoursFrequency());
				annualPay = calculateAnnualPay(Float.parseFloat(compDTO.getBaseRate() !=null && !compDTO.getBaseRate().equalsIgnoreCase("") ? compDTO.getBaseRate() : "0"), compDTO.getBaseRateFrequency());
				float annualMidtPoint = calculateAnnualMidpoit(Float.parseFloat(jobGradeDTO.getMidpoint()), compDTO.getBaseRateFrequency());
				compRatio = calculateCompRatio(annualStandardHours, annualPay,annualMidtPoint);
				quartile = calculateQuartile(annualPay, parseFloat(jobGradeDTO.getMinimum()), parseFloat(jobGradeDTO.getMaximum()), compRatio, parseFloat(jobGradeDTO.getQuartileLow1()),parseFloat( jobGradeDTO.getQuartileHigh1()),parseFloat( jobGradeDTO.getQuartileLow2()),parseFloat( jobGradeDTO.getQuartileHigh2()),parseFloat( jobGradeDTO.getQuartileLow3()),parseFloat( jobGradeDTO.getQuartileHigh3()),parseFloat(jobGradeDTO.getQuartileLow4()),parseFloat(jobGradeDTO.getQuartileHigh4()));
			}catch(Exception e){
				logger.error("Exception in calculateAnnualStandarHours "+e.getMessage());
			}
			compDTO.setPayPeriodHours(calculatePayperiodHours(annualStandardHours, noOfPayPeriod)+"");
			compDTO.setPeriodRate(calculatePayperiodRate(annualPay, noOfPayPeriod)+"");
			compDTO.setHourlyRate(calculateHourlyRate(annualPay)+"");
			compDTO.setWeeklyHours(calculateWeeklyHours(annualStandardHours)+"");
			String empId = decimalFormat.format(Integer.parseInt(compDTO.getEmployeeId()));
			ps = conn.prepareStatement("select emp_id from EMP_COMPENSATION where emp_id=?");
			ps.setString(1, compDTO.getEmployeeId());
			rs = ps.executeQuery();
			//ps.close();
			if(rs.next()){
				//String str = getEffectiveDate(conn,ps,rs,"EMP_COMPENSATION",compDTO.getEmployeeId());
				//boolean flag = DateUtility.compareDates(compDTO.getEffectiveDate(), str);
				//ps.close();
				//if(!flag){
				conn.setAutoCommit(false);
				query = new StringBuffer();
				query.append("update EMP_COMPENSATION set EFFECTIVE_DATE=?,COMP_ACTION_TYPE=?,COMP_REASON=?,PAY_ENTITY=?,PAY_GROUP=?,PAY_FREQ=?,LAST_EVALUATION_DATE=?,GRADE=?,NEXT_EVAL_DUE_DATE=?,SCHEDULED_HOURS=?,HOURS_FREQUENCY=?");
				query.append(",PAY_PERIOD_HRS=?,WEEKLY_HOURS=?,BASE_RATE=?,BASE_RATE_FREQ=?,PERIOD_RATE=?,HOURLY_RATE=?,DEFAULT_EARNING_CODE=?,JOB_GROUP=?,USE_JOB_RATE=?,PERFORMACE_PLAN=?");
				query.append(",BONUS_PLAN=?, DATE_UPDATED=sysdate(),UPDATED_BY=?,END_EFFECTIVE_DATE=?,COMP_RATIO=?,QUARTILE=? WHERE EMP_ID='"+empId+"'");
				ps = conn.prepareStatement(query.toString());
				java.sql.Date date = null;
				try {
					date = new java.sql.Date(new Date(compDTO.getEffectiveDate()).getTime()-1);
				} catch (Exception e) {
				}
				setPreparedStatementsForCompensation(ps, compDTO, telligentUser,date,compRatio+"",quartile);
				//int i = ps.executeUpdate();
				/*if(i>0){
						String temp = compDTO.getEmployeeId();
						compDTO = new EmployeeCompensationDTO();
						compDTO.setEmployeeId(temp);
						temp = null;
						conn.commit();
						return "Employee Compensation Details Saved Successfully";
					}else{
						conn.rollback();
						return "Employee Compensation Details not Saved";
					}*/
				/*}else{
					return "error:;Effective Date should be greater than current Effective Date";
				}*/
			}else{
				conn.setAutoCommit(false);
				query.append("INSERT INTO EMP_COMPENSATION(EMP_ID,EFFECTIVE_DATE,COMP_ACTION_TYPE,COMP_REASON,PAY_ENTITY,PAY_GROUP,PAY_FREQ,LAST_EVALUATION_DATE,GRADE,NEXT_EVAL_DUE_DATE,");
				query.append("SCHEDULED_HOURS,HOURS_FREQUENCY,PAY_PERIOD_HRS,WEEKLY_HOURS,BASE_RATE,BASE_RATE_FREQ,PERIOD_RATE,HOURLY_RATE,DEFAULT_EARNING_CODE,");
				query.append("JOB_GROUP,USE_JOB_RATE,PERFORMACE_PLAN,BONUS_PLAN,DATE_UPDATED,UPDATED_BY,END_EFFECTIVE_DATE,COMP_RATIO,QUARTILE )");
				query.append("VALUES ('"+empId+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?,?,?,?)");
				ps = conn.prepareStatement(query.toString());
				setPreparedStatementsForCompensation(ps, compDTO, telligentUser,new java.sql.Date(new Date(messageHandler.getMessage("effectiveEndDate")).getTime()),compRatio+"",quartile);
			}
			int i = ps.executeUpdate();
			//ps.close();
			if(i>0){
				if(updateMeritAdminCompensation(conn,ps,rs,compDTO,telligentUser.getEmployeeId(),compRatio+"",quartile)){
					conn.commit();
					String temp = compDTO.getEmployeeId();
					compDTO = new EmployeeCompensationDTO();
					compDTO.setEmployeeId(temp);
					temp = null;
					return "Employee Compensation Details Saved Successfully";
				}else{
					conn.rollback();
					return "Employee Compensation Details not Saved";
				}
			}else{
				conn.rollback();
				return "Employee Compensation Details not Saved";
			}
			//}
		}catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {}
			ex.printStackTrace();
			compDTO.setErrorMessage("Employee Details not Saved :: "+ex.getMessage());
			logger.info("Excpetion in saveEmployeeDetails :: "+ex.getMessage());
			return "Excpetion in saveEmployeeDetails :: "+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}


	public boolean updateMeritAdminCompensation(Connection conn,PreparedStatement ps,ResultSet rs,EmployeeCompensationDTO dto,String loggedInEmpId,String compRatio,String quartile){
		boolean flag = false;
		StringBuffer query = new StringBuffer();
		try{
			ps = conn.prepareStatement("select emp_no from merit_transaction where emp_id=?");
			ps.setString(1, dto.getEmployeeId());
			rs = ps.executeQuery();
			ps=null;
			if(rs.next()){
				query.append("update merit_transaction set pay_entity = ?,rate = ?,compa_ratio = ? ,quartile = ?, updated_date = sysdate(),updated_by = ? ");
				query.append("where emp_id = ?");
				ps = conn.prepareStatement(query.toString());
				ps.setString(1, dto.getPayEntity());
				ps.setString(2, dto.getBaseRate());
				ps.setString(3, ParseNumberUtility.parseNumberInUSFormat(compRatio));
				ps.setString(4, quartile);
				ps.setString(5, loggedInEmpId);
				ps.setString(6, dto.getEmployeeId());
				int i= ps.executeUpdate();
				if(i>0)
					flag =true;
			}else{
				query.append("insert into merit_transaction (pay_entity,rate,compa_ratio,quartile,updated_date,updated_by) ");
				query.append("select PAY_ENTITY,PERIOD_RATE,COMP_RATIO,QUARTILE,sysdate(),UPDATED_BY from EMP_COMPENSATION where EMP_ID=?");
				ps = conn.prepareStatement(query.toString());
				ps.setString(1, dto.getEmployeeId());
				int i= ps.executeUpdate();
				if(i>0)
					flag =true;
			}
		}catch(Exception e){
			logger.error("Exception in updateMeritAdminCompensation : "+e.getMessage());
		}
		return flag;
	}

	private int compensationHistoryUpdate(Connection conn,PreparedStatement ps,String empId){
		try{
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO EMP_COMPENSATION_HIS(EMP_ID,EFFECTIVE_DATE,COMP_ACTION_TYPE,COMP_REASON,PAY_ENTITY,PAY_GROUP,PAY_FREQ,LAST_EVALUATION_DATE,GRADE,NEXT_EVAL_DUE_DATE,SCHEDULED_HOURS,HOURS_FREQUENCY");
			query.append(",PAY_PERIOD_HRS,WEEKLY_HOURS,BASE_RATE,BASE_RATE_FREQ,PERIOD_RATE,HOURLY_RATE,DEFAULT_EARNING_CODE,JOB_GROUP,USE_JOB_RATE,PERFORMACE_PLAN,BONUS_PLAN)");
			query.append("SELECT EMP_ID,EFFECTIVE_DATE,COMP_ACTION_TYPE,COMP_REASON,PAY_ENTITY,PAY_GROUP,PAY_FREQ,LAST_EVALUATION_DATE,GRADE,NEXT_EVAL_DUE_DATE,SCHEDULED_HOURS,HOURS_FREQUENCY");
			query.append(",PAY_PERIOD_HRS,WEEKLY_HOURS,BASE_RATE,BASE_RATE_FREQ,PERIOD_RATE,HOURLY_RATE,DEFAULT_EARNING_CODE,JOB_GROUP,USE_JOB_RATE,PERFORMACE_PLAN,BONUS_PLAN");
			query.append("  FROM EMP_COMPENSATION where EMP_ID='"+empId+"'");
			ps = conn.prepareStatement(query.toString());
			int i = ps.executeUpdate();
			ps.close();
			return i;
		}catch(Exception e){
			logger.error("Exception in compensationHistoryUpdate : "+e.getMessage());
		}
		return 0;
	}


	@SuppressWarnings("deprecation")
	private void setPreparedStatementsForCompensation(PreparedStatement ps,EmployeeCompensationDTO compDTO,TelligentUser telligentUser,java.sql.Date endEffectiveDate,String compRatio,String quartile) throws java.sql.SQLException,IOException{
		ps.setDate(1, new java.sql.Date(new Date(compDTO.getEffectiveDate()).getTime()));
		ps.setString(2, compDTO.getCompActionType());
		ps.setString(3, compDTO.getCompActionReason());
		ps.setString(4, compDTO.getPayEntity());
		ps.setString(5, compDTO.getPayGroup());
		ps.setString(6, compDTO.getPayFrequency());
		try {
			ps.setDate(7, new java.sql.Date(new Date(compDTO.getLastperfEvaluationDate()).getTime()));
		} catch (Exception e) {
			ps.setDate(7, null);
		}
		ps.setString(8, compDTO.getGrade());
		try {
			ps.setDate(9, new java.sql.Date(new Date(compDTO.getNextEvalDueDate()).getTime()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ps.setDate(9, null);
		}
		ps.setString(10, getDefaultInt(compDTO.getScheduledHours()));
		ps.setString(11, getDefaultInt(compDTO.getHoursFrequency()));
		ps.setString(12, getDefaultInt(compDTO.getPayPeriodHours()));
		ps.setString(13, getDefaultInt(compDTO.getWeeklyHours()));
		ps.setString(14, getDefaultInt(compDTO.getBaseRate()));
		ps.setString(15, compDTO.getBaseRateFrequency());
		ps.setString(16, getDefaultInt(compDTO.getPeriodRate()));
		ps.setString(17, getDefaultInt(compDTO.getHourlyRate()));
		ps.setString(18, compDTO.getDefaultEarningCode());
		ps.setString(19, compDTO.getEligibleJobGroup());
		ps.setString(20, compDTO.getUseJobRate());
		ps.setString(21, compDTO.getPerformacePlan());
		ps.setString(22, compDTO.getBonusPlan());
		ps.setString(23, telligentUser.getEmployeeId());
		ps.setDate(24, endEffectiveDate);
		ps.setString(25, ParseNumberUtility.parseNumberInUSFormat(compRatio));
		ps.setString(26, quartile);
	}
	private String getDefaultInt(String str){
		if(str!=null && !str.equalsIgnoreCase(""))
			return str;
		else
			return "0";

	}
	@SuppressWarnings("deprecation")
	private void setPreparedStatementsForSave(Connection conn,PreparedStatement ps,EmployeeDTO employeeDTO,TelligentUser telligentUser,String operation) throws java.sql.SQLException,IOException{
		String state = getStateId(conn,ps,employeeDTO.getState());
		if(state.equalsIgnoreCase(""))
			employeeDTO.setState(saveState(conn,ps,employeeDTO.getState()));
		else
			employeeDTO.setState(state);
		String city = getCityId(conn,ps,employeeDTO.getCity());
		if(city.equalsIgnoreCase(""))
			employeeDTO.setCity(saveCity(conn,ps,employeeDTO.getCity(),employeeDTO.getState()));
		else
			employeeDTO.setCity(city);
		ps.setString(1, employeeDTO.getBadgeNo());
		ps.setDate(2, new java.sql.Date(new Date(employeeDTO.getEffectiveDate()).getTime()));
		ps.setString(3, employeeDTO.getFirstName());
		ps.setString(4, employeeDTO.getMiddleName());
		ps.setString(5, employeeDTO.getLastName());
		ps.setString(6, employeeDTO.getPersonalEmail());
		ps.setString(7, formatPhoneNo(employeeDTO.getHomePhone()));
		ps.setString(8, formatPhoneNo(employeeDTO.getMobilePhone()));
		ps.setString(9, employeeDTO.getAddressLine1());
		ps.setString(10, employeeDTO.getAddressLine2());
		ps.setString(11, employeeDTO.getCity());
		ps.setString(12, employeeDTO.getState());
		ps.setString(13, formatZipCode(employeeDTO.getZipcode()));
		try {
			ps.setDate(14,new java.sql.Date(new Date(employeeDTO.getDateOfBirth()).getTime()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ps.setInt(15,  employeeDTO.isMinor() ? 0:1);
		ps.setString(16, formatPhoneNo(employeeDTO.getWorkPhone()));
		ps.setString(17, formatPhoneNo(employeeDTO.getWorkMobilePhone()));
		ps.setString(18, employeeDTO.getWorkEmail());
		ps.setString(19, employeeDTO.getEmergencyLastName());
		ps.setString(20, employeeDTO.getEmergencyFirstName());
		ps.setString(21, employeeDTO.getEmergencyRelationShip());
		ps.setString(22, employeeDTO.getEmergencyEmail());
		ps.setString(23, formatPhoneNo(employeeDTO.getEmergencyHomePhone()));
		ps.setString(24, formatPhoneNo(employeeDTO.getEmergencyMobilePhone()));
		ps.setString(25, telligentUser.getEmployeeId());
		ps.setString(26, formatSSN(employeeDTO.getSocialSecNo()));
		ps.setString(27, employeeDTO.getWorkExt());
		// Always keep Picture column as last one
		if(operation.equalsIgnoreCase("save")){
			if(employeeDTO.getPicture() !=null)
				ps.setBinaryStream(28, employeeDTO.getPicture().getInputStream(),(int)employeeDTO.getPicture().getBytes().length);	
			else
				ps.setBinaryStream(28, null);
		}else{
			if(!employeeDTO.getPicture().getOriginalFilename().equalsIgnoreCase(""))
				ps.setBinaryStream(28, employeeDTO.getPicture().getInputStream(),(int)employeeDTO.getPicture().getBytes().length);	
		}

	}
	private String formatZipCode(String zipcode){
		//DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		try {
			//zipcode = new StringBuffer(decimalFormat.format(Long.parseLong(zipcode.replaceAll("-", "")))).insert(5, "-").toString();
			zipcode = new StringBuffer(zipcode).insert(5, "-").toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return zipcode;
	}
	private String formatPhoneNo(String phoneNo){
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		try {
			//phoneNo = new StringBuffer(decimalFormat.format(Long.parseLong(phoneNo.replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "")))).insert(0,"(").insert(4,")-").insert(9, "-").toString();
			phoneNo = new StringBuffer(decimalFormat.format(Long.parseLong(phoneNo.replaceAll("-", "")))).insert(3,"-").insert(7, "-").toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return phoneNo;
	}
	private String formatSSN(String phoneNo){
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		try {
			//phoneNo = new StringBuffer(decimalFormat.format(Long.parseLong(phoneNo.replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "")))).insert(0,"(").insert(4,")-").insert(9, "-").toString();
			phoneNo = new StringBuffer(decimalFormat.format(Long.parseLong(phoneNo.replaceAll("-", "")))).insert(3,"-").insert(6, "-").toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return phoneNo;
	}
	private String saveCity(Connection conn,PreparedStatement  ps,String city,String stateId){
		try{
			int maxId = GenericDAO.getInstance().getMaxId(conn, ps, null, "select max(id)+1 from City", "");
			ps = conn.prepareStatement("Insert into City(id,value,state_id,isActive) values (?,?,?,?)");
			ps.setInt(1, maxId);
			ps.setString(2, city);
			ps.setString(3, stateId);
			ps.setString(4, "1");
			int i = ps.executeUpdate();
			if(i>0)
				return String.valueOf(maxId);
		}catch(Exception e){
			logger.error("Exception in saveCity"+e.getMessage());
		}
		return "";
	}
	private String saveState(Connection conn,PreparedStatement  ps,String state){
		try{
			int maxId = GenericDAO.getInstance().getMaxId(conn, ps, null, "select max(id)+1 from State", "");
			ps = conn.prepareStatement("Insert into State(id,value,isActive) values (?,?,?)");
			ps.setInt(1, maxId);
			ps.setString(2, state);
			ps.setString(3, "1");
			int i = ps.executeUpdate();
			if(i>0)
				return String.valueOf(maxId);
		}catch(Exception e){
			logger.error("Exception in saveState"+e.getMessage());
		}
		return "";
	}
	private String getCityId(Connection conn,PreparedStatement  ps,String city){
		logger.info("in getCityId");
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select id from City where upper(value) = '"+city.toUpperCase()+"'");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString("id");
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getCityId "+ex.getMessage());
		}
		return "";
	}
	private String getStateId(Connection conn,PreparedStatement  ps,String city){
		logger.info("in getStateId");
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select id from State where upper(value) = '"+city.toUpperCase()+"'");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getString("id");
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getStateId "+ex.getMessage());
		}
		return "";
	}
	public ArrayList<MapDTO> searchList(String firstName, String lastName,String empId) {
		logger.info("in searchList DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TelligentUser user = telligentUtility.getTelligentUser();
		ArrayList<TeamDTO> teamList = getEmployeeTeams(user);
		String teamIds = "";
		int i = 0;
		for(TeamDTO dto : teamList){
			if (i == 0){
				teamIds = dto.getTeamId();
			}else{
				teamIds = teamIds+","+dto.getTeamId();
			}
			i++;
		}
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			
			if (user.getRole().equalsIgnoreCase("meritAdmin")){
				query.append("select personal.EMP_ID,personal.F_NAME,personal.L_NAME from EMP_PERSONAL personal where ");
				if(lastName !=null && !lastName.trim().equalsIgnoreCase("")){
					query.append("personal.L_NAME like '"+lastName+"%'");
					//query.append(" and personal.EMP_ID = employement.EMP_ID and personal.EMP_ID = position.EMP_ID and position.team in ("+teamIds+") and employement.STATUS=1 order by personal.L_NAME");
				}else if(firstName !=null && !firstName.trim().equalsIgnoreCase("")){
					query.append("personal.F_NAME like '"+firstName+"%'");
					//query.append(" and personal.EMP_ID = employement.EMP_ID and personal.EMP_ID = position.EMP_ID and position.team in ("+teamIds+") and employement.STATUS=1 order by personal.F_NAME");
				}else if(empId !=null && !empId.trim().equalsIgnoreCase("")){
					query.append("personal.EMP_ID like '"+empId+"%'");
					//query.append(" and personal.EMP_ID = employement.EMP_ID and personal.EMP_ID = position.EMP_ID and position.team in ("+teamIds+") and employement.STATUS=1 order by personal.EMP_ID");
				}
			}else{
			query.append("select personal.EMP_ID,personal.F_NAME,personal.L_NAME from EMP_PERSONAL personal,EMP_EMPLOYEMENT employement,EMP_POSITION_DATA position where ");
			if(lastName !=null && !lastName.trim().equalsIgnoreCase("")){
				query.append("personal.L_NAME like '"+lastName+"%'");
				query.append(" and personal.EMP_ID = employement.EMP_ID and personal.EMP_ID = position.EMP_ID and position.team in ("+teamIds+") and employement.STATUS=1 order by personal.L_NAME");
			}else if(firstName !=null && !firstName.trim().equalsIgnoreCase("")){
				query.append("personal.F_NAME like '"+firstName+"%'");
				query.append(" and personal.EMP_ID = employement.EMP_ID and personal.EMP_ID = position.EMP_ID and position.team in ("+teamIds+") and employement.STATUS=1 order by personal.F_NAME");
			}else if(empId !=null && !empId.trim().equalsIgnoreCase("")){
				query.append("personal.EMP_ID like '"+empId+"%'");
				query.append(" and personal.EMP_ID = employement.EMP_ID and personal.EMP_ID = position.EMP_ID and position.team in ("+teamIds+") and employement.STATUS=1 order by personal.EMP_ID");
			}
			}
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("EMP_ID"));
				//dto.setValue(rs.getString("EMP_ID")+","+rs.getString("L_NAME")+","+rs.getString("F_NAME"));
				if(lastName !=null && !lastName.trim().equalsIgnoreCase(""))
					dto.setValue(rs.getString("L_NAME"));
				else if(firstName !=null && !firstName.trim().equalsIgnoreCase(""))
					dto.setValue(rs.getString("F_NAME"));
				else if(empId !=null && !empId.trim().equalsIgnoreCase(""))
					dto.setValue(rs.getString("EMP_ID"));
				list.add(dto);
			}
		}catch (Exception ex) {
			//ex.printStackTrace();
			logger.info("Excpetion in searchList "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}

	public ArrayList<MapDTO> searchTeamEmployees(String teamName) {
		logger.info("in searchList DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		TelligentUser user = telligentUtility.getTelligentUser();
		ArrayList<TeamDTO> teamList = getEmployeeTeams(user);
		String teamIds = "";
		int i = 0;
		for(TeamDTO dto : teamList){
			if (i == 0){
				teamIds = dto.getTeamId();
			}else{
				teamIds = teamIds+","+dto.getTeamId();
			}
			i++;
		}
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			query.append("select e.emp_id emp_id,concat(e.emp_id,',',L_NAME,',',F_NAME) name,team from merit_transaction mt,EMP_PERSONAL e,team t,EMP_EMPLOYEMENT employement where mt.team=t.team_id and mt.emp_id = employement.emp_id and employement.status = 1 and t.team_id in ("+teamIds+") and mt.emp_id = e.emp_id and upper(team_name) like ? group by e.emp_id");
			//ROld  one
			//query.append("select employee_id,employee_name,last_name from employee e , team t where t.team_name like ? and t.team_id = e.team_id");
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, teamName.toUpperCase()+"%");
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("emp_id"));
				dto.setValue(rs.getString("name"));
				list.add(dto);
			}
		}catch (Exception ex) {
			//ex.printStackTrace();
			logger.info("Excpetion in searchList "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public EmployeeDTO getEmployeeDetails(String empId){
		logger.info("in getEmployeeDetails");
		EmployeeDTO dto = new EmployeeDTO();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select SEQ_NO,EMP_NO,EMP_ID,BADGE,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,F_NAME,M_NAME,L_NAME,P_EMAIL,H_PHONE,M_PHONE,ADDRESS_L1,ADDRESS_L2,C.value CITY,S.value STATE,ZIP,");
			query.append("DATE_FORMAT(DATE_OF_BIRTH,'%m/%d/%Y') DATE_OF_BIRTH,IS_MINOR,WORK_PHONE,WORK_MOBILE_PHONE,WORK_EMAIL,EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_EMAIL,");
			query.append("EMC_H_PHONE,EMC_M_PHONE,PICTURE,DATE_FORMAT(E.DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(E.updated_by,',',EMP.L_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = E.updated_by) UPDATED_BY,SOCIAL_SEC_NO,WORK_EXTN ");
			query.append(" from EMP_PERSONAL_HIS E ");
			query.append("left join City C on C.id = E.CITY ");
			query.append("left join State S on S.id = E.STATE ");
			query.append(" where EMP_ID=?  order by EFFECTIVE_DATE desc");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			if(rs.next()){
				return setEmployeeDetails(rs);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return dto;
	}

	public ArrayList<EmployeeCompensationDTO> getEmployeeCompensationHistory(String empId){
		logger.info("in getEmployeeCompensationHistory");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		ArrayList<EmployeeCompensationDTO> list = new ArrayList<EmployeeCompensationDTO>();
		try{
			query.append("SELECT SEQ_NO,COMP_RATIO,QUARTILE,EMP_ID,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,c.value COMP_ACTION_TYPE, ");
			query.append(" r.value COMP_REASON,et.value PAY_ENTITY,gp.value PAY_GROUP,pf.value PAY_FREQ,DATE_FORMAT(LAST_EVALUATION_DATE,'%m/%d/%Y') LAST_EVALUATION_DATE,");
			query.append(" g.value GRADE,DATE_FORMAT(NEXT_EVAL_DUE_DATE,'%m/%d/%Y') NEXT_EVAL_DUE_DATE, ");
			query.append(" SCHEDULED_HOURS,brf.value HOURS_FREQUENCY,PAY_PERIOD_HRS,WEEKLY_HOURS,BASE_RATE,bf.value BASE_RATE_FREQ,PERIOD_RATE,");
			query.append(" HOURLY_RATE,de.value DEFAULT_EARNING_CODE,jg.value JOB_GROUP,USE_JOB_RATE,pp.value PERFORMACE_PLAN,bp.value BONUS_PLAN,his.DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = his.updated_by) UPDATED_BY");
			query.append(" FROM EMP_COMPENSATION_HIS his");
			query.append(" left join Compensation_Action c on c.id=his.COMP_ACTION_TYPE");
			query.append(" left join Compensation_Action_Reason r on r.id=his.COMP_REASON");
			query.append(" left join pay_entity et on et.id=his.PAY_ENTITY");
			query.append(" left join pay_group gp on gp.id=his.PAY_GROUP");
			query.append(" left join Grade g on g.id=his.GRADE");
			query.append(" left join pay_frequency pf on pf.id=his.PAY_FREQ ");
			query.append(" left join Base_Rate_Frequency bf on bf.id=his.BASE_RATE_FREQ");
			query.append(" left join Base_Rate_Frequency brf on brf.id=his.BASE_RATE_FREQ");
			query.append(" left join Default_Earning_Code de on de.id=his.DEFAULT_EARNING_CODE ");
			query.append(" left join Job_Group jg on jg.id=his.JOB_GROUP ");
			query.append(" left join Performance_Plan pp on pp.id=his.PERFORMACE_PLAN");
			query.append(" left join Bonus_Plan bp on bp.id=his.BONUS_PLAN ");
			query.append("  WHERE EMP_ID=? ORDER BY EFFECTIVE_DATE DESC ");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(setEmployeeCompensationDetails(rs));
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeCompensationHistory "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}


	public ArrayList<EmployeeDTO> getEmployeeDetailsHistory(String empId){
		logger.info("in getEmployeeDetailsHistory");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
		try{
			query.append("select SEQ_NO,EMP_NO,EMP_ID,BADGE,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,F_NAME,M_NAME,L_NAME,P_EMAIL,H_PHONE,M_PHONE,ADDRESS_L1,ADDRESS_L2,C.value CITY,S.value STATE,ZIP,");
			query.append("DATE_FORMAT(DATE_OF_BIRTH,'%m/%d/%Y') DATE_OF_BIRTH,IS_MINOR,WORK_PHONE,WORK_MOBILE_PHONE,WORK_EMAIL,EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_EMAIL,");
			query.append("EMC_H_PHONE,EMC_M_PHONE,PICTURE,DATE_FORMAT(E.DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = E.updated_by) UPDATED_BY,SOCIAL_SEC_NO,WORK_EXTN ");
			query.append(" from EMP_PERSONAL_HIS E ");
			query.append("left join City C on C.id = E.CITY ");
			query.append("left join State S on S.id = E.STATE ");
			//query.append(" where EMP_ID=?  order by seq_no desc");
			query.append(" where EMP_ID=?  order by EFFECTIVE_DATE desc");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(setEmployeeDetails(rs));
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeDetailsHistory "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}

	public EmployeeDTO getEmployeeDetailsFromHistory(String seqNo){
		logger.info("in getEmployeeDetailsFromHistory");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select SEQ_NO,EMP_NO,EMP_ID,BADGE,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,F_NAME,M_NAME,L_NAME,P_EMAIL,H_PHONE,M_PHONE,ADDRESS_L1,ADDRESS_L2,C.value CITY,S.value STATE,ZIP,");
			query.append("DATE_FORMAT(DATE_OF_BIRTH,'%m/%d/%Y') DATE_OF_BIRTH,IS_MINOR,WORK_PHONE,WORK_MOBILE_PHONE,WORK_EMAIL,EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_EMAIL,");
			query.append("EMC_H_PHONE,EMC_M_PHONE,PICTURE,DATE_FORMAT(E.DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = E.updated_by) UPDATED_BY,SOCIAL_SEC_NO,WORK_EXTN ");
			query.append(" from EMP_PERSONAL_HIS E ");
			query.append("left join City C on C.id = E.CITY ");
			query.append("left join State S on S.id = E.STATE ");
			query.append(" where SEQ_NO=? ");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, seqNo);
			rs = ps.executeQuery();
			if(rs.next()){
				return setEmployeeDetails(rs);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeDetailsFromHistory "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}

	private EmployeeDTO setEmployeeDetails(ResultSet rs) throws java.sql.SQLException{
		EmployeeDTO dto = new EmployeeDTO();
		dto.setSeqNo(rs.getString("SEQ_NO"));
		dto.setEmployeeId(rs.getString("EMP_ID"));
		dto.setEmployeeNo(rs.getString("EMP_NO"));
		dto.setLastName(rs.getString("L_NAME"));
		dto.setMiddleName(rs.getString("M_NAME"));
		dto.setFirstName(rs.getString("F_NAME"));
		dto.setPersonalEmail(rs.getString("P_EMAIL"));
		dto.setBadgeNo(rs.getString("BADGE"));
		dto.setDateOfBirth(rs.getString("DATE_OF_BIRTH"));
		dto.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
		dto.setMinor(rs.getString("IS_MINOR").equalsIgnoreCase("0")?true:false);
		dto.setHomePhone(rs.getString("H_PHONE"));
		dto.setMobilePhone(rs.getString("M_PHONE"));
		dto.setAddressLine1(rs.getString("ADDRESS_L1"));
		dto.setAddressLine2(rs.getString("ADDRESS_L2"));
		dto.setCity(rs.getString("CITY"));
		dto.setState(rs.getString("STATE"));
		dto.setZipcode(rs.getString("ZIP"));
		dto.setWorkPhone(rs.getString("WORK_PHONE"));
		dto.setWorkEmail(rs.getString("WORK_EMAIL"));
		dto.setWorkMobilePhone(rs.getString("WORK_MOBILE_PHONE"));
		dto.setEmergencyMobilePhone(rs.getString("EMC_M_PHONE"));
		dto.setEmergencyEmail(rs.getString("EMC_EMAIL"));
		dto.setEmergencyFirstName(rs.getString("EMC_F_NAME"));
		dto.setEmergencyLastName(rs.getString("EMC_L_NAME"));
		dto.setEmergencyHomePhone(rs.getString("EMC_H_PHONE"));
		dto.setEmergencyRelationShip(rs.getString("EMC_REL"));
		dto.setSocialSecNo(rs.getString("SOCIAL_SEC_NO"));
		dto.setWorkExt(rs.getString("WORK_EXTN"));
		try {
			Blob blob = rs.getBlob("PICTURE");
			BASE64DecodedMultipartFile file = new BASE64DecodedMultipartFile(rs.getBlob("PICTURE").getBytes(1, (int)blob.length()));
			dto.setPicture(file);
			dto.setPictureBase64(Base64.toBase64String(blob.getBytes(1, (int)blob.length())));
		} catch (Exception e) {
			dummyBlob(dto);
		}
		dto.setUpdatedDate(rs.getString("DATE_UPDATED"));
		dto.setUpdatedBy(rs.getString("UPDATED_BY"));
		return dto;
	}

	private EmployeeCompensationDTO setEmployeeCompensationDetails(ResultSet rs) throws java.sql.SQLException{
		EmployeeCompensationDTO dto = new EmployeeCompensationDTO();
		dto.setSeqNo(rs.getString("SEQ_NO"));
		dto.setEmployeeId(rs.getString("EMP_ID"));
		dto.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
		dto.setCompActionType(rs.getString("COMP_ACTION_TYPE"));
		dto.setCompActionReason(rs.getString("COMP_REASON"));
		dto.setPayEntity(rs.getString("PAY_ENTITY"));
		dto.setPayGroup(rs.getString("PAY_GROUP"));
		dto.setPayFrequency(rs.getString("PAY_FREQ"));
		dto.setLastperfEvaluationDate(rs.getString("LAST_EVALUATION_DATE"));
		dto.setGrade(rs.getString("GRADE"));
		dto.setNextEvalDueDate(rs.getString("NEXT_EVAL_DUE_DATE"));
		dto.setScheduledHours(rs.getString("SCHEDULED_HOURS"));
		dto.setHoursFrequency(rs.getString("HOURS_FREQUENCY"));
		dto.setPayPeriodHours(rs.getString("PAY_PERIOD_HRS"));
		dto.setWeeklyHours(rs.getString("WEEKLY_HOURS"));
		dto.setBaseRate(rs.getString("BASE_RATE"));
		dto.setBaseRateFrequency(rs.getString("BASE_RATE_FREQ"));
		dto.setPeriodRate(rs.getString("PERIOD_RATE"));
		dto.setHourlyRate(rs.getString("HOURLY_RATE"));
		dto.setDefaultEarningCode(rs.getString("DEFAULT_EARNING_CODE"));
		dto.setEligibleJobGroup(rs.getString("JOB_GROUP"));
		dto.setUseJobRate(rs.getString("USE_JOB_RATE"));
		dto.setPerformacePlan(rs.getString("PERFORMACE_PLAN"));
		dto.setBonusPlan(rs.getString("BONUS_PLAN"));
		dto.setUpdatedDate(rs.getString("DATE_UPDATED"));
		dto.setUpdatedBy(rs.getString("UPDATED_BY"));
		dto.setCompRatio(rs.getString("COMP_RATIO"));
		dto.setQuartile(rs.getString("QUARTILE"));
		return dto;
	}

	public ArrayList<CityDTO> getCityDetails(String stateId){
		logger.info("in getStateDetails");
		ArrayList<CityDTO> list = new ArrayList<CityDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select id,value from City where state_id=? and isActive=1");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, stateId);
			rs = ps.executeQuery();
			while(rs.next()){
				CityDTO dto = new CityDTO();
				dto.setId(rs.getString("id"));
				dto.setCity(rs.getString("value"));
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getStateDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public ArrayList<CityDTO> getCityDetailsAll(){
		logger.info("in getCityDetailsAll");
		ArrayList<CityDTO> list = new ArrayList<CityDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select id,value from City where isActive=1");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				CityDTO dto = new CityDTO();
				dto.setId(rs.getString("id"));
				dto.setCity(rs.getString("value"));
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getCityDetailsAll "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}

	public ArrayList<StateDTO> getStateDetails(){
		logger.info("in getStateDetails");
		ArrayList<StateDTO> list = new ArrayList<StateDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select id,value from State where isActive=1");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				StateDTO dto = new StateDTO();
				dto.setId(rs.getString("id"));
				dto.setStateName(rs.getString("value"));
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getStateDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	/**
	 *  Generic method to get id and value of lookup tables
	 * @return HashMap
	 */
	public HashMap<String, ArrayList<MapDTO>> getEmpCompensationLookup(){
		logger.info("in getEmpCompensationLookup");
		HashMap<String, ArrayList<MapDTO>> map = new HashMap<String, ArrayList<MapDTO>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			map.put("Base_Rate_Frequency", getLookup(conn, ps, rs, "Base_Rate_Frequency"));
			map.put("Bonus_Plan", getLookup(conn, ps, rs, "Bonus_Plan"));
			map.put("Compensation_Action", getLookup(conn, ps, rs, "Compensation_Action"));
			map.put("Compensation_Action_Reason", getLookup(conn, ps, rs, "Compensation_Action_Reason"));
			map.put("Default_Earning_Code", getLookup(conn, ps, rs, "Default_Earning_Code"));
			map.put("Default_Hours_Frequency", getLookup(conn, ps, rs, "Default_Hours_Frequency"));
			map.put("Grade", getLookup(conn, ps, rs, "Grade"));
			map.put("Job_Group", getLookup(conn, ps, rs, "Job_Group"));
			map.put("pay_entity", getLookup(conn, ps, rs, "pay_entity"));
			map.put("pay_frequency", getLookup(conn, ps, rs, "pay_frequency"));
			map.put("pay_group", getLookup(conn, ps, rs, "pay_group"));
			map.put("Performance_Plan", getLookup(conn, ps, rs, "Performance_Plan"));
		}catch (Exception ex) {
			logger.info("Excpetion in getEmpCompensationLookup "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return map;
	}
	public ArrayList<MapDTO> getLookup(Connection conn,PreparedStatement ps,ResultSet rs,String tableName){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		try{
			ps = conn.prepareStatement("select id,value from "+tableName+" where isActive=1");
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getLookup "+e.getMessage());
		}
		return list;
	}

	/**
	 *  Generic method to get id and value of lookup tables for Employment
	 * @return HashMap
	 */
	public HashMap<String, ArrayList<MapDTO>> getEmpEmployementLookup(){
		logger.info("in getEmpEmployementLookup");
		HashMap<String, ArrayList<MapDTO>> map = new HashMap<String, ArrayList<MapDTO>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			map.put("Employement_Action", getLookup(conn, ps, rs, "Employement_Action"));
			map.put("Employement_Action_Reason", getLookup(conn, ps, rs, "Employement_Action_Reason"));
			map.put("Status", getLookup(conn, ps, rs, "Status"));
			map.put("FLSA_Category", getLookup(conn, ps, rs, "FLSA_Category"));
			map.put("Classification", getLookup(conn, ps, rs, "Classification"));
			//map.put("Employement_Category", getLookup(conn, ps, rs, "Employement_Category"));
			//map.put("Full_Time_Equivalency", getLookup(conn, ps, rs, "Full_Time_Equivalency"));
			map.put("Leave_Status_code", getLookup(conn, ps, rs, "Leave_Status_code"));
			map.put("Leave_Status_Reason", getLookup(conn, ps, rs, "Leave_Status_Reason"));
		}catch (Exception ex) {
			logger.info("Excpetion in getEmpEmployementLookup "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return map;
	}
	public ArrayList<MapDTO> getSupervisorList(Connection conn,PreparedStatement ps, ResultSet rs){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		try{
			ps = conn.prepareStatement("select EMP_ID,F_NAME,L_NAME from EMP_PERSONAL");
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("EMP_ID"));
				dto.setValue(rs.getString("EMP_ID")+" "+rs.getString("L_NAME")+","+rs.getString("F_NAME"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getLookup "+e.getMessage());
		}
		return list;
	}
	public ArrayList<MapDTO> getTeams(Connection conn,PreparedStatement ps, ResultSet rs){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		try{
			ps = conn.prepareStatement("SELECT team_id,team_name FROM team t ");
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("team_id"));
				dto.setValue(rs.getString("team_name"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getLookup "+e.getMessage());
		}
		return list;
	}
	public HashMap<String, ArrayList<MapDTO>> getEmpPositionLookup(){
		logger.info("in getEmpPositionLookup");
		HashMap<String, ArrayList<MapDTO>> map = new HashMap<String, ArrayList<MapDTO>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			//map.put("supervisorList",getSupervisorList(conn, ps, rs));
			map.put("teamList",getTeams(conn, ps, rs));
			map.put("position_Action", getLookup(conn, ps, rs, "Position_Action"));
			map.put("position_Action_Reason", getLookup(conn, ps, rs, "Position_Action_Reason"));
			map.put("position_master", getLookup(conn, ps, rs, "position_master"));
			map.put("position_level", getLookup(conn, ps, rs, "position_level"));
			map.put("primary_job", getLookup(conn, ps, rs, "primary_job"));
			map.put("job_grade", getLookup(conn, ps, rs, "job_grade"));
			map.put("union_code", getLookup(conn, ps, rs, "union_code"));
			map.put("org1", getLookup(conn, ps, rs, "org1"));
			map.put("org2", getLookup(conn, ps, rs, "org2"));
			map.put("org3", getLookup(conn, ps, rs, "org3"));
			map.put("org4", getLookup(conn, ps, rs, "org4"));
			map.put("org5", getLookup(conn, ps, rs, "org5"));
			map.put("org6", getLookup(conn, ps, rs, "org6"));
			map.put("org7", getLookup(conn, ps, rs, "org7"));
			map.put("org8", getLookup(conn, ps, rs, "org8"));
			map.put("org9", getLookup(conn, ps, rs, "org9"));
			map.put("org10", getLookup(conn, ps, rs, "org10"));
		}catch (Exception ex) {
			logger.info("Excpetion in getEmpPositionLookup "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return map;
	}
	public HashMap<String, ArrayList<MapDTO>> getEmpOtherLookup(){
		logger.info("in getEmpOtherLookup");
		HashMap<String, ArrayList<MapDTO>> map = new HashMap<String, ArrayList<MapDTO>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			map.put("Ethinicity", getLookup(conn, ps, rs, "Ethinicity"));
			map.put("Marital_Status", getLookup(conn, ps, rs, "Marital_Status"));
			//map.put("Citizenship_Status", getLookup(conn, ps, rs, "Citizenship_Status"));
			//map.put("Military_Status", getLookup(conn, ps, rs, "Military_Status"));
			map.put("VISA_Type", getLookup(conn, ps, rs, "VISA_Type"));
			map.put("Veteran_Status", getLookup(conn, ps, rs, "Veteran_Status"));
		}catch (Exception ex) {
			logger.info("Excpetion in getEmpOtherLookup "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return map;
	}
	@SuppressWarnings("deprecation")
	public String saveEmployeeOtherDetails(EmployeeOtherDTO employeeOtherDTO,TelligentUser telligentUser,MessageHandler messageHandler) {
		logger.info("in saveEmployeeOtherDetails DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement("select emp_id from EMP_OTHER_DATA where emp_id=?");
			ps.setString(1, employeeOtherDTO.getEmployeeId());
			rs = ps.executeQuery();
			if(rs.next()){
				//String str = getEffectiveDate(conn,ps,rs,"EMP_OTHER_DATA",employeeOtherDTO.getEmployeeId());
				//boolean flag = DateUtility.compareDates(employeeOtherDTO.getEffectiveDate(), str);
				ps.close();
				//if(!flag){
				conn.setAutoCommit(false);
				query.append("update EMP_OTHER_DATA set GENDER=?,ETHINICITY=?,MARITAL_STAT=?,CITIZENSHIP=?,VISA_TYPE=?,I9_EXP_DATE=?,VET_STAT=?,");
				query.append("IS_DISABLED=?,DISABILITY_DESC=?,CITY=?,EFFECTIVE_DATE=?,END_EFFECTIVE_DATE=?,EMC_L_NAME=?,EMC_F_NAME=?,EMC_REL=?,EMC_H_PHONE=?,EMC_M_PHONE=?,EMC_EMAIL=?,DATE_UPDATED=sysdate(),UPDATED_BY=? where  EMP_ID=?");
				ps = conn.prepareStatement(query.toString());
				setPSforEmpOther(employeeOtherDTO, ps, telligentUser, messageHandler,"update");
				/*}else{
					return "error:;Effective Date should be greater than current Effective Date";
				}*/
			}else{
				ps.close();
				conn.setAutoCommit(false);
				query.append("insert into EMP_OTHER_DATA (GENDER,ETHINICITY,MARITAL_STAT,CITIZENSHIP,VISA_TYPE,I9_EXP_DATE,VET_STAT,");
				query.append("IS_DISABLED,DISABILITY_DESC,CITY,EFFECTIVE_DATE,END_EFFECTIVE_DATE,EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_H_PHONE,EMC_M_PHONE,EMC_EMAIL,DATE_UPDATED,UPDATED_BY,EMP_ID) ");
				query.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?,?)");
				ps = conn.prepareStatement(query.toString());
				setPSforEmpOther(employeeOtherDTO, ps, telligentUser, messageHandler,"save");
			}
			int i = ps.executeUpdate();
			if(i>0){
				conn.commit();
				return "Details Saved Succuessfully";
			}else{
				conn.rollback();
				return "error:;Details Not Saved";
			}
		}catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {}
			ex.printStackTrace();
			employeeOtherDTO.setErrorMessage("error:;"+ex.getMessage());
			logger.info("Excpetion in saveEmployeeOtherDetails :: "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	public void setPSforEmpOther(EmployeeOtherDTO employeeOtherDTO,PreparedStatement ps,TelligentUser telligentUser,MessageHandler messageHandler,String operation) throws SQLException{
		ps.setString(1, employeeOtherDTO.getGender());
		ps.setString(2, employeeOtherDTO.getEthinicity());
		ps.setString(3, employeeOtherDTO.getMaritalStatus());
		ps.setString(4, employeeOtherDTO.getCitizenShip());
		ps.setString(5, employeeOtherDTO.getVisaType());
		if(employeeOtherDTO.getI9ExpDate() !=null && !employeeOtherDTO.getI9ExpDate().equalsIgnoreCase(""))
			ps.setDate(6, new java.sql.Date(new Date(employeeOtherDTO.getI9ExpDate()).getTime()));
		else
			ps.setDate(6, null);
		ps.setString(7, employeeOtherDTO.getVeteranStatus());
		ps.setString(8, employeeOtherDTO.getDisability());
		ps.setString(9, employeeOtherDTO.getDisabilityDesc());
		ps.setString(10, employeeOtherDTO.getCity()!=null && !employeeOtherDTO.getCity().equalsIgnoreCase("") ? employeeOtherDTO.getCity():null);
		ps.setDate(11, new java.sql.Date(new Date(employeeOtherDTO.getEffectiveDate()).getTime()));
		if(operation.equalsIgnoreCase("save"))
			ps.setDate(12, new java.sql.Date(new Date(messageHandler.getMessage("effectiveEndDate")).getTime()));
		else
			ps.setDate(12, new java.sql.Date(new Date(employeeOtherDTO.getEffectiveDate()).getTime()-1));
		ps.setString(13, employeeOtherDTO.getEmergencyLastName());
		ps.setString(14, employeeOtherDTO.getEmergencyFirstName());
		ps.setString(15, employeeOtherDTO.getEmergencyRelationShip());
		ps.setString(16, employeeOtherDTO.getEmergencyHomePhone());
		ps.setString(17, employeeOtherDTO.getEmergencyMobilePhone());
		ps.setString(18, employeeOtherDTO.getEmergencyEmail());
		ps.setString(19, telligentUser.getEmployeeId());
		ps.setString(20, employeeOtherDTO.getEmployeeId());
	}
	public EmployeeOtherDTO getEmployeeOtherDetails(String empId){
		logger.info("in getEmployeeOtherDetails");
		EmployeeOtherDTO dto = new EmployeeOtherDTO();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select SEQ_NO,GENDER,ETHINICITY,MARITAL_STAT,CITIZENSHIP,VISA_TYPE,DATE_FORMAT(I9_EXP_DATE,'%m/%d/%Y') I9_EXP_DATE,VET_STAT,");
			query.append("IS_DISABLED,DISABILITY_DESC,CITY,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE, ");
			query.append("EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_H_PHONE,EMC_M_PHONE,EMC_EMAIL,DATE_UPDATED,UPDATED_BY from EMP_OTHER_DATA where EMP_ID=?");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			if(rs.next()){
				EmployeeDTO dto1 = getEmployeeDetails(empId);
				dto = setEmployeeOtherDetails(rs);
				dto.setEmployeeId(dto1.getEmployeeId());
				dto.setLastName(dto1.getLastName());
				dto.setFirstName(dto1.getFirstName());
				dto.setMiddleName(dto1.getMiddleName());
			}else{
				/*EmployeeDTO dto1 = dummyBlob(new EmployeeDTO());
				dto.setPicture(dto1.getPicture());*/
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeOtherDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return dto;
	}
	public EmployeeDTO dummyBlob(EmployeeDTO dto){
		try {
			byte[] bytes = "".getBytes();
			BASE64DecodedMultipartFile file = new BASE64DecodedMultipartFile(bytes);
			dto.setPicture(file);
			dto.setPictureBase64(Base64.toBase64String(bytes));
			return dto;
		} catch (Exception e) {}
		return dto;
	}
	public ArrayList<EmployeeOtherDTO> getEmployeeOtherDetailsHistory(String empId){
		logger.info("in getEmployeeOtherDetailsHistory");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		ArrayList<EmployeeOtherDTO> list = new ArrayList<EmployeeOtherDTO>();
		try{
			/*query.append("select SEQ_NO,GENDER,e.value ETHINICITY,m.value MARITAL_STAT,CITIZENSHIP,v.value VISA_TYPE,DATE_FORMAT(I9_EXP_DATE,'%m/%d/%Y') I9_EXP_DATE,ve.value VET_STAT,");
			query.append("IS_DISABLED,DISABILITY_DESC,CITY,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE, ");
			query.append("EMC_L_NAME,EMC_F_NAME,r.value EMC_REL,EMC_H_PHONE,EMC_M_PHONE,EMC_EMAIL,DATE_UPDATED,UPDATED_BY ");
			query.append("from EMP_OTHER_DATA_HIS o,Ethinicity e,Marital_Status m,VISA_Type v,Veteran_Status ve,Relationship r ");
			query.append("where EMP_ID=? and o.ETHINICITY = e.id and o.MARITAL_STAT = m.id and o.VISA_TYPE=v.id and o.VET_STAT=ve.id and o.EMC_REL=r.id order by seq_no desc");*/
			//			query.append("select SEQ_NO,GENDER,ETHINICITY,MARITAL_STAT,CITIZENSHIP,VISA_TYPE,DATE_FORMAT(I9_EXP_DATE,'%m/%d/%Y') I9_EXP_DATE,VET_STAT,");
			//			query.append("IS_DISABLED,DISABILITY_DESC,CITY,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE, ");
			//			query.append("EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_H_PHONE,EMC_M_PHONE,EMC_EMAIL,DATE_UPDATED,UPDATED_BY from EMP_OTHER_DATA_HIS where EMP_ID=? order by seq_no desc");

			query.append("select e.SEQ_NO,GENDER,et.value ETHINICITY,m.value MARITAL_STAT,CITIZENSHIP,v.value VISA_TYPE,DATE_FORMAT(I9_EXP_DATE,'%m/%d/%Y') I9_EXP_DATE,ve.value VET_STAT,");
			query.append("IS_DISABLED,DISABILITY_DESC,e.CITY CITY,DATE_FORMAT(e.EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE, ");
			query.append("e.EMC_L_NAME EMC_L_NAME,e.EMC_F_NAME EMC_F_NAME,r.value EMC_REL,e.EMC_H_PHONE EMC_H_PHONE,e.EMC_M_PHONE EMC_M_PHONE,e.EMC_EMAIL EMC_EMAIL,DATE_FORMAT(e.DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = e.updated_by) UPDATED_BY ");
			query.append("from EMP_OTHER_DATA_HIS e ");
			query.append("left join Ethinicity et on et.id = e.ETHINICITY ");
			query.append("left join Marital_Status m on m.id = e.MARITAL_STAT ");
			//query.append("left join Citizenship_Status c on c.id = e.CITIZENSHIP ");
			query.append("left join VISA_Type v on v.id = e.VISA_TYPE ");
			query.append("left join Veteran_Status ve on ve.id = e.VET_STAT ");
			query.append("left join Relationship r on r.id = e.EMC_REL ");
			query.append("where e.EMP_ID=? order by EFFECTIVE_DATE desc");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(setEmployeeOtherDetails(rs));
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeOtherDetailsHistory "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public EmployeeOtherDTO getEmployeeOtherDetailsFromHistoryAjax(String seqNo){
		logger.info("in getEmployeeOtherDetailsFromHistoryAjax");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select EMP_ID,SEQ_NO,GENDER,ETHINICITY,MARITAL_STAT,CITIZENSHIP,VISA_TYPE,DATE_FORMAT(I9_EXP_DATE,'%m/%d/%Y') I9_EXP_DATE,VET_STAT,");
			query.append("IS_DISABLED,DISABILITY_DESC,CITY,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE, ");
			query.append("EMC_L_NAME,EMC_F_NAME,EMC_REL,EMC_H_PHONE,EMC_M_PHONE,EMC_EMAIL,DATE_FORMAT(e.DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = e.updated_by) UPDATED_BY from EMP_OTHER_DATA_HIS e where SEQ_NO=?");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, seqNo);
			rs = ps.executeQuery();
			if(rs.next()){
				EmployeeOtherDTO dto = setEmployeeOtherDetails(rs);
				dto.setEmployeeId(rs.getString("EMP_ID"));
				EmployeeDTO dto1 = getEmployeeDetails(rs.getString("EMP_ID"));
				dto.setFirstName(dto1.getFirstName());
				dto.setLastName(dto1.getLastName());
				dto.setMiddleName(dto1.getMiddleName());
				return dto;
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeOtherDetailsFromHistoryAjax "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	private EmployeeOtherDTO setEmployeeOtherDetails(ResultSet rs) throws java.sql.SQLException{
		EmployeeOtherDTO dto = new EmployeeOtherDTO();
		dto.setSeqNo(rs.getString("SEQ_NO"));
		dto.setGender(rs.getString("GENDER"));
		dto.setEthinicity(rs.getString("ETHINICITY"));
		dto.setMaritalStatus(rs.getString("MARITAL_STAT"));
		dto.setCitizenShip(rs.getString("CITIZENSHIP"));
		dto.setVisaType(rs.getString("VISA_TYPE"));
		dto.setI9ExpDate(rs.getString("I9_EXP_DATE"));
		dto.setVeteranStatus(rs.getString("VET_STAT"));
		dto.setDisability(rs.getString("IS_DISABLED"));
		dto.setDisabilityDesc(rs.getString("DISABILITY_DESC"));
		dto.setCity(rs.getString("CITY"));
		dto.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
		dto.setEmergencyLastName(rs.getString("EMC_L_NAME"));
		dto.setEmergencyFirstName(rs.getString("EMC_F_NAME"));
		dto.setEmergencyRelationShip(rs.getString("EMC_REL"));
		dto.setEmergencyMobilePhone(rs.getString("EMC_M_PHONE"));
		dto.setEmergencyHomePhone(rs.getString("EMC_H_PHONE"));
		dto.setEmergencyEmail(rs.getString("EMC_EMAIL"));
		dto.setUpdatedDate(rs.getString("DATE_UPDATED"));
		dto.setUpdatedBy(rs.getString("UPDATED_BY"));
		return dto;
	}
	public String getEffectiveDate(String tableName,String empId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			return getEffectiveDate(conn, ps, rs, tableName, empId);
		}catch(Exception e){
			logger.info("Excpetion in setEmployeeOtherDetails "+e.getMessage());
			return "";
		}finally{
			this.closeAll(conn,ps,rs);
		}
	}
	public String getEffectiveDate(Connection conn,PreparedStatement ps,ResultSet rs,String tableName,String empId) throws SQLException{
		ps = conn.prepareStatement("select DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE from "+tableName+" where EMP_ID=? order by EFFECTIVE_DATE DESC");
		ps.setString(1, empId);
		rs = ps.executeQuery();
		if(rs.next())
			return rs.getString("EFFECTIVE_DATE");
		else
			return"";
	}
	public EmployeeCompensationDTO getEmployeeCompensationDetails(String empId){
		logger.info("in getEmployeeCompensationDetails");
		EmployeeCompensationDTO dto = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("SELECT SEQ_NO,COMP_RATIO,QUARTILE,EMP_ID,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,COMP_ACTION_TYPE,COMP_REASON,PAY_ENTITY,PAY_GROUP,PAY_FREQ,DATE_FORMAT(LAST_EVALUATION_DATE,'%m/%d/%Y') LAST_EVALUATION_DATE,GRADE,DATE_FORMAT(NEXT_EVAL_DUE_DATE,'%m/%d/%Y') NEXT_EVAL_DUE_DATE,");
			query.append("SCHEDULED_HOURS,HOURS_FREQUENCY,PAY_PERIOD_HRS,WEEKLY_HOURS,BASE_RATE,BASE_RATE_FREQ,PERIOD_RATE,HOURLY_RATE,DEFAULT_EARNING_CODE,");
			query.append("JOB_GROUP,USE_JOB_RATE,PERFORMACE_PLAN,BONUS_PLAN,DATE_UPDATED,UPDATED_BY FROM EMP_COMPENSATION where EMP_ID=?");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			if(rs.next()){
				return setEmployeeCompensationDetails(rs);
			}else{
				dto = new EmployeeCompensationDTO();
				dto.setEmployeeId(empId);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return dto;
	}

	@SuppressWarnings("deprecation")
	public String saveEmployeePosition(EmployeePositionDTO employeePositionDTO,TelligentUser telligentUser,MessageHandler messageHandler) {
		logger.info("in saveEmployeePosition DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement("select emp_id from EMP_POSITION_DATA where emp_id=?");
			ps.setString(1, employeePositionDTO.getEmployeeId());
			rs = ps.executeQuery();
			if(rs.next()){
				//String str = getEffectiveDate(conn,ps,rs,"EMP_POSITION_DATA",employeePositionDTO.getEmployeeId());
				//boolean flag = DateUtility.compareDates(employeePositionDTO.getEffectiveDate(), str);
				ps.close();
				//if(!flag){
				conn.setAutoCommit(false);
				try{
					Integer.parseInt(employeePositionDTO.getTeam());
				}catch(Exception e){
					ps = conn.prepareStatement("select team_id,team_name from team where upper(team_name) = ? ");
					ps.setString(1, employeePositionDTO.getTeam().toUpperCase());
					rs.close();
					rs = ps.executeQuery();
					if(rs.next())
						employeePositionDTO.setTeam(rs.getString("team_id"));
				}
				query.append("update EMP_POSITION_DATA set STAT_CODE=?,STAT_CODE_REASON=?,SUPERVISOR=?,TEAM=?,POSITION=?,POSITION_LEVEL=?,PRIM_JOB=?,PRIM_JOB_LEVEL=?,UNION_CODE=?, ");
				query.append("ORG_LEVEL_1=?,ORG_LEVEL_2=?,ORG_LEVEL_3=?,ORG_LEVEL_4=?,ORG_LEVEL_5=?,ORG_LEVEL_6=?,ORG_LEVEL_7=?,ORG_LEVEL_8=?,ORG_LEVEL_9=?,ORG_LEVEL_10=?, ");
				query.append("EFFECTIVE_DATE=?,END_EFFECTIVE_DATE=?,DATE_UPDATED=sysdate(),UPDATED_BY=? where EMP_ID=?");
				ps = conn.prepareStatement(query.toString());
				setPSforEmpPosition(employeePositionDTO, ps, telligentUser, messageHandler,"update");
				/*}else{
					return "error:;Effective Date should be greater than current Effective Date";
				}*/
			}else{
				ps.close();
				conn.setAutoCommit(false);
				try{
					Integer.parseInt(employeePositionDTO.getTeam());
				}catch(Exception e){
					ps = conn.prepareStatement("select team_id,team_name from team where upper(team_name) = ? ");
					ps.setString(1, employeePositionDTO.getTeam().toUpperCase());
					rs.close();
					rs = ps.executeQuery();
					if(rs.next())
						employeePositionDTO.setTeam(rs.getString("team_id"));
				}
				query.append("insert into EMP_POSITION_DATA(STAT_CODE,STAT_CODE_REASON,SUPERVISOR,TEAM,POSITION,POSITION_LEVEL,PRIM_JOB,PRIM_JOB_LEVEL,UNION_CODE, ");
				query.append("ORG_LEVEL_1,ORG_LEVEL_2,ORG_LEVEL_3,ORG_LEVEL_4,ORG_LEVEL_5,ORG_LEVEL_6,ORG_LEVEL_7,ORG_LEVEL_8,ORG_LEVEL_9,ORG_LEVEL_10, ");
				query.append("EFFECTIVE_DATE,END_EFFECTIVE_DATE,DATE_UPDATED,UPDATED_BY,EMP_ID) ");
				query.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?,?)");
				ps = conn.prepareStatement(query.toString());
				setPSforEmpPosition(employeePositionDTO, ps, telligentUser, messageHandler,"save");
			}
			int i = ps.executeUpdate();
			if(i>0){
				if(updateMeritAdminPosition(conn, ps, rs, employeePositionDTO, telligentUser.getEmployeeId())){
					conn.commit();
					return "Details Saved Succuessfully";					
				}else{
					conn.rollback();
					return "error:;Details Not Saved";
				}
			}else{
				conn.rollback();
				return "error:;Details Not Saved";
			}
		}catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {}
			ex.printStackTrace();
			employeePositionDTO.setErrorMessage("error:;"+ex.getMessage());
			logger.info("Excpetion in saveEmployeePosition :: "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	public boolean updateMeritAdminPosition(Connection conn,PreparedStatement ps,ResultSet rs,EmployeePositionDTO dto,String loggedInEmpId){
		boolean flag = false;
		StringBuffer query = new StringBuffer();
		try{
			ps = conn.prepareStatement("select description,minimum,maximum,midpoint from primary_job where id=?");
			ps.setString(1, dto.getPrimaryJob());
			rs = ps.executeQuery();
			String description = null;
			String minimum = null;
			String maximum = null;
			String midpoint = null;
			if(rs.next()){
				description = rs.getString("description");
				minimum = rs.getString("minimum");
				maximum = rs.getString("maximum");
				midpoint = rs.getString("midpoint");
			}
			ps.close();rs.close();
			ps = conn.prepareStatement("select emp_no from merit_transaction where emp_id=?");
			ps.setString(1, dto.getEmployeeId());
			rs = ps.executeQuery();
			ps=null;
			if(rs.next()){
				query.append("update merit_transaction set job_title=?,grade=?,team=?,supervisor=?,job_desc=?,minimum=?,midpoint=?,maximum=?, ");
				query.append("org1=?,org2=?,org3=?,org4=?,org5=?,org6=?,org7=?,org8=?,org9=?,org10=?, ");
				query.append("updated_date=sysdate(),updated_by=? ");
				query.append("where emp_id=?");
				ps = conn.prepareStatement(query.toString());
				setPreparedStatementsForMeritPosition(ps, dto, loggedInEmpId,description,minimum,maximum,midpoint);
				int i= ps.executeUpdate();
				if(i>0)
					flag =true;
			}else{
				query.append("insert into merit_transaction (job_title,grade,team,supervisor,job_desc,minimum,midpoint,maximum, ");
				query.append("org1,org2,org3,org4,org5,org6,org7,org8,org9,org10, ");
				query.append("updated_date,updated_by,emp_id) "); // last column should be employee Id
				query.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?,?)");
				ps = conn.prepareStatement(query.toString());
				setPreparedStatementsForMeritPosition(ps, dto, loggedInEmpId,description,minimum,maximum,midpoint);
				int i= ps.executeUpdate();
				if(i>0)
					flag =true;
			}
		}catch(Exception e){
			logger.error("Exception in updateMeritAdminPosition : "+e.getMessage());
		}
		return flag;
	}
	@SuppressWarnings("deprecation")
	private void setPreparedStatementsForMeritPosition(PreparedStatement ps,EmployeePositionDTO dto,String loggedInEmpId,String description,String minimum,String maximum,String midpoint) throws java.sql.SQLException,IOException{
		ps.setString(1, dto.getPrimaryJob());
		ps.setString(2, dto.getPositionLevel());
		ps.setString(3, dto.getTeam());
		ps.setString(4, dto.getSupervisor());
		ps.setString(5, description);
		ps.setString(6, minimum);
		ps.setString(7, midpoint);
		ps.setString(8, maximum);
		ps.setString(9, dto.getOrg1());
		ps.setString(10, dto.getOrg2());
		ps.setString(11, dto.getOrg3());
		ps.setString(12, dto.getOrg4());
		ps.setString(13, dto.getOrg5());
		ps.setString(14, dto.getOrg6());
		ps.setString(15, dto.getOrg7());
		ps.setString(16, dto.getOrg8());
		ps.setString(17, dto.getOrg9());
		ps.setString(18, dto.getOrg10());
		ps.setString(19, loggedInEmpId);
		ps.setString(20, dto.getEmployeeId());
	}
	@SuppressWarnings("deprecation")
	public void setPSforEmpPosition(EmployeePositionDTO employeePositionDTO,PreparedStatement ps,TelligentUser telligentUser,MessageHandler messageHandler,String operation) throws SQLException{
		ps.setString(1, employeePositionDTO.getStatusCode());
		ps.setString(2, employeePositionDTO.getStatusReason());
		ps.setString(3, employeePositionDTO.getSupervisor());
		ps.setString(4, employeePositionDTO.getTeam());
		ps.setString(5, employeePositionDTO.getPosition());
		ps.setString(6, employeePositionDTO.getPositionLevel());
		ps.setString(7, employeePositionDTO.getPrimaryJob());
		ps.setString(8, employeePositionDTO.getPrimaryJobLeave());
		ps.setString(9, employeePositionDTO.getUnionCode());
		ps.setString(10, employeePositionDTO.getOrg1());
		ps.setString(11, employeePositionDTO.getOrg2());
		ps.setString(12, employeePositionDTO.getOrg3());
		ps.setString(13, employeePositionDTO.getOrg4());
		ps.setString(14, employeePositionDTO.getOrg5());
		ps.setString(15, employeePositionDTO.getOrg6());
		ps.setString(16, employeePositionDTO.getOrg7());
		ps.setString(17, employeePositionDTO.getOrg8());
		ps.setString(18, employeePositionDTO.getOrg9());
		ps.setString(19, employeePositionDTO.getOrg10());
		ps.setDate(20, new java.sql.Date(new Date(employeePositionDTO.getEffectiveDate()).getTime()));
		if(operation.equalsIgnoreCase("save"))
			ps.setDate(21, new java.sql.Date(new Date(messageHandler.getMessage("effectiveEndDate")).getTime()));
		else
			ps.setDate(21, new java.sql.Date(new Date(employeePositionDTO.getEffectiveDate()).getTime()-1));
		ps.setString(22, telligentUser.getEmployeeId());
		ps.setString(23, employeePositionDTO.getEmployeeId());
	}

	public EmployeePositionDTO getEmployeePositionDetails(String empId){
		logger.info("in getEmployeePositionDetails");
		EmployeePositionDTO dto = new EmployeePositionDTO();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select SEQ_NO,STAT_CODE,STAT_CODE_REASON,SUPERVISOR,TEAM,POSITION,POSITION_LEVEL,PRIM_JOB,PRIM_JOB_LEVEL,UNION_CODE, ");
			query.append("ORG_LEVEL_1,ORG_LEVEL_2,ORG_LEVEL_3,ORG_LEVEL_4,ORG_LEVEL_5,ORG_LEVEL_6,ORG_LEVEL_7,ORG_LEVEL_8,ORG_LEVEL_9,ORG_LEVEL_10, ");
			query.append("DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,DATE_FORMAT(DATE_UPDATED,'%m/%d/%Y') DATE_UPDATED,UPDATED_BY from EMP_POSITION_DATA where EMP_ID=?");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			if(rs.next()){
				EmployeeDTO dto1 = getEmployeeDetails(empId);
				dto = setEmployeePositionDetails(rs);
				dto.setEmployeeId(dto1.getEmployeeId());
				dto.setLastName(dto1.getLastName());
				dto.setFirstName(dto1.getFirstName());
				dto.setMiddleName(dto1.getMiddleName());
			}else{
				/*EmployeeDTO dto1 = dummyBlob(new EmployeeDTO());
				dto.setPicture(dto1.getPicture());*/
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeePositionDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return dto;
	}
	public ArrayList<EmployeePositionDTO> getEmployeePositionDetailsHistory(String empId){
		logger.info("in getEmployeePositionDetailsHistory");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		ArrayList<EmployeePositionDTO> list = new ArrayList<EmployeePositionDTO>();
		try{
			//query.append("select SEQ_NO,STAT_CODE,STAT_CODE_REASON,SUPERVISOR,TEAM,POSITION,POSITION_LEVEL,PRIM_JOB,PRIM_JOB_LEVEL,UNION_CODE, ");
			//query.append("ORG_LEVEL_1,ORG_LEVEL_2,ORG_LEVEL_3,ORG_LEVEL_4,ORG_LEVEL_5,ORG_LEVEL_6,ORG_LEVEL_7,ORG_LEVEL_8,ORG_LEVEL_9,ORG_LEVEL_10, ");
			//query.append("DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,DATE_FORMAT(DATE_UPDATED,'%m/%d/%Y') DATE_UPDATED,UPDATED_BY from EMP_POSITION_DATA_HIS where EMP_ID=? order by seq_no desc");
			//CONCAT(ep.EMP_ID,',',ep.L_NAME, ',', ep.F_NAME) Supervisor
			// t.team_name

			query.append("select e.SEQ_NO SEQ_NO,sr.value STAT_CODE,sr.value STAT_CODE_REASON,SUPERVISOR,TEAM,pm.value POSITION,pl.value POSITION_LEVEL,pj.value PRIM_JOB,pjl.value PRIM_JOB_LEVEL,uc.value UNION_CODE, "); 
			query.append("ORG_LEVEL_1,ORG_LEVEL_2,ORG_LEVEL_3,ORG_LEVEL_4,ORG_LEVEL_5,ORG_LEVEL_6,ORG_LEVEL_7,ORG_LEVEL_8,ORG_LEVEL_9,ORG_LEVEL_10,  ");
			query.append("DATE_FORMAT(e.EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,DATE_FORMAT(e.DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = e.updated_by) UPDATED_BY "); 
			query.append("from EMP_POSITION_DATA_HIS e ");
			query.append("left join Position_Action sc on sc.id = e.STAT_CODE ");
			query.append("left join Position_Action_Reason sr on sr.id = e.STAT_CODE_REASON ");
			query.append("left join position_master pm on pm.id = e.POSITION ");
			query.append("left join position_level pl on pl.id = e.POSITION_LEVEL ");
			query.append("left join primary_job pj on pj.id = e.PRIM_JOB ");
			query.append("left join job_grade pjl on pjl.id = e.PRIM_JOB_LEVEL ");
			query.append("left join union_code uc on uc.id = e.UNION_CODE ");
			/*query.append("left join EMP_PERSONAL ep on ep.EMP_ID = e.SUPERVISOR ");
			query.append("left join team t on t.team_id = e.team ");*/
			query.append("where e.EMP_ID=? order by EFFECTIVE_DATE desc"); 
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(setEmployeePositionDetails(rs));
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeePositionDetailsHistory "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public EmployeePositionDTO getEmployeePositionDetailsFromHistoryAjax(String seqNo){
		logger.info("in getEmployeePositionDetailsFromHistoryAjax");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select SEQ_NO,STAT_CODE,STAT_CODE_REASON,SUPERVISOR,TEAM,POSITION,POSITION_LEVEL,PRIM_JOB,PRIM_JOB_LEVEL,UNION_CODE, ");
			query.append("ORG_LEVEL_1,ORG_LEVEL_2,ORG_LEVEL_3,ORG_LEVEL_4,ORG_LEVEL_5,ORG_LEVEL_6,ORG_LEVEL_7,ORG_LEVEL_8,ORG_LEVEL_9,ORG_LEVEL_10, ");
			query.append("DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,DATE_FORMAT(DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = his.updated_by) UPDATED_BY,EMP_ID from EMP_POSITION_DATA_HIS his where SEQ_NO=?");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, seqNo);
			rs = ps.executeQuery();
			if(rs.next()){
				EmployeePositionDTO dto = setEmployeePositionDetails(rs);
				dto.setEmployeeId(rs.getString("EMP_ID"));
				EmployeeDTO dto1 = getEmployeeDetails(rs.getString("EMP_ID"));
				dto.setFirstName(dto1.getFirstName());
				dto.setLastName(dto1.getLastName());
				dto.setMiddleName(dto1.getMiddleName());
				return dto;
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeePositionDetailsFromHistoryAjax "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	private EmployeePositionDTO setEmployeePositionDetails(ResultSet rs) throws java.sql.SQLException{
		HashMap<String ,String> map = getTeamMap();
		EmployeePositionDTO dto = new EmployeePositionDTO();
		dto.setSeqNo(rs.getString("SEQ_NO"));
		dto.setStatusCode(rs.getString("STAT_CODE"));
		dto.setStatusReason(rs.getString("STAT_CODE_REASON"));
		dto.setSupervisor(rs.getString("SUPERVISOR"));
		try {
			dto.setTeam(map.get(rs.getString("TEAM")));
		} catch (Exception e) {		}
		dto.setPosition(rs.getString("POSITION"));
		dto.setPositionLevel(rs.getString("POSITION_LEVEL"));
		dto.setPrimaryJob(rs.getString("PRIM_JOB"));
		dto.setPrimaryJobLeave(rs.getString("PRIM_JOB_LEVEL"));
		dto.setUnionCode(rs.getString("UNION_CODE"));
		dto.setOrg1(rs.getString("ORG_LEVEL_1"));
		dto.setOrg2(rs.getString("ORG_LEVEL_2"));
		dto.setOrg3(rs.getString("ORG_LEVEL_3"));
		dto.setOrg4(rs.getString("ORG_LEVEL_4"));
		dto.setOrg5(rs.getString("ORG_LEVEL_5"));
		dto.setOrg6(rs.getString("ORG_LEVEL_6"));
		dto.setOrg7(rs.getString("ORG_LEVEL_7"));
		dto.setOrg8(rs.getString("ORG_LEVEL_8"));
		dto.setOrg9(rs.getString("ORG_LEVEL_9"));
		dto.setOrg10(rs.getString("ORG_LEVEL_10"));
		dto.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
		dto.setUpdatedBy(rs.getString("UPDATED_BY"));
		dto.setUpdatedDate(rs.getString("DATE_UPDATED"));
		return dto;
	}
	public HashMap<String, String> getTeamMap(){
		HashMap<String , String> map = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select team_id,team_name from team ");
			rs = ps.executeQuery();
			while(rs.next())
				map.put(rs.getString("team_id").toUpperCase(), rs.getString("team_name"));
			return map;
		}catch(Exception e){
			logger.info("Excpetion in getTeamMap "+e.getMessage());
		}
		finally{
			this.closeAll(conn,ps,rs);
		}
		return null;
	}
	public EmployeeCompensationDTO getEmployeeCompDetailsFromHistoryAjax(String seqNo){
		logger.info("in getEmployeeCompDetailsFromHistoryAjax");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{

			query.append("SELECT SEQ_NO,COMP_RATIO,QUARTILE,EMP_ID,DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,COMP_ACTION_TYPE,COMP_REASON,PAY_ENTITY,PAY_GROUP,PAY_FREQ,DATE_FORMAT(LAST_EVALUATION_DATE,'%m/%d/%Y') LAST_EVALUATION_DATE,GRADE,DATE_FORMAT(NEXT_EVAL_DUE_DATE,'%m/%d/%Y') NEXT_EVAL_DUE_DATE,");
			query.append("SCHEDULED_HOURS,HOURS_FREQUENCY,PAY_PERIOD_HRS,WEEKLY_HOURS,BASE_RATE,BASE_RATE_FREQ,PERIOD_RATE,HOURLY_RATE,DEFAULT_EARNING_CODE,");
			query.append("JOB_GROUP,USE_JOB_RATE,PERFORMACE_PLAN,BONUS_PLAN,DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = HIS.updated_by) UPDATED_BY FROM EMP_COMPENSATION_HIS HIS WHERE SEQ_NO=? ");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, seqNo);
			rs = ps.executeQuery();
			if(rs.next()){
				EmployeeCompensationDTO dto = setEmployeeCompensationDetails(rs);
				dto.setEmployeeId(rs.getString("EMP_ID"));
				EmployeeDTO dto1 = getEmployeeDetails(rs.getString("EMP_ID"));
				dto.setFirstName(dto1.getFirstName());
				dto.setLastName(dto1.getLastName());
				dto.setMiddleName(dto1.getMiddleName());
				return dto;
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployeeCompDetailsFromHistoryAjax "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	public String saveEmployement(EmploymentDTO employmentDTO,TelligentUser telligentUser,MessageHandler messageHandler) {
		logger.info("in saveEmployement DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement("select emp_id from EMP_EMPLOYEMENT where emp_id=?");
			ps.setString(1, employmentDTO.getEmployeeId());
			rs = ps.executeQuery();
			if(rs.next()){
				//String str = getEffectiveDate(conn,ps,rs,"EMP_EMPLOYEMENT",employmentDTO.getEmployeeId());
				//boolean flag = DateUtility.compareDates(employmentDTO.getEffectiveDate(), str);
				ps.close();
				//if(!flag){
				conn.setAutoCommit(false);
				query.append("update EMP_EMPLOYEMENT set STAT_CODE=?,STAT_CODE_REASON=?,STATUS=?,HIRE_DATE=?,LAST_HIRE_DATE=?,SENIORITY_DATE=?,BENEFIT_DATE=?,TERM_DATE=?,FLSA_CATEGORY=?, ");
				query.append("CLASSIFICATION=?,EMPL_CATEGORY=?,FTE=?,LEAV_STAT_CODE=?,LEAV_REASON=?,LEAV_START_DATE=?,LEAV_END_DATE=?, ");
				query.append("EFFECTIVE_DATE=?,END_EFFECTIVE_DATE=?,DATE_UPDATED=sysdate(),UPDATED_BY=? where EMP_ID=?");
				ps = conn.prepareStatement(query.toString());
				setPSforEmployment(employmentDTO, ps, telligentUser, messageHandler,"update");
				/*}else{
					return "error:;Effective Date should be greater than current Effective Date";
				}*/
			}else{
				ps.close();
				conn.setAutoCommit(false);
				query.append("insert into EMP_EMPLOYEMENT(STAT_CODE,STAT_CODE_REASON,STATUS,HIRE_DATE,LAST_HIRE_DATE,SENIORITY_DATE,BENEFIT_DATE,TERM_DATE,FLSA_CATEGORY, ");
				query.append("CLASSIFICATION,EMPL_CATEGORY,FTE,LEAV_STAT_CODE,LEAV_REASON,LEAV_START_DATE,LEAV_END_DATE, ");
				query.append("EFFECTIVE_DATE,END_EFFECTIVE_DATE,DATE_UPDATED,UPDATED_BY,EMP_ID) ");
				query.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?,?)");
				ps = conn.prepareStatement(query.toString());
				setPSforEmployment(employmentDTO, ps, telligentUser, messageHandler,"save");
			}
			int i = ps.executeUpdate();
			if(i>0){
				if(updateMeritAdminEmployement(conn, ps, rs, employmentDTO, telligentUser.getEmployeeId())){
					conn.commit();
					return "Details Saved Succuessfully";
				}else{
					conn.rollback();
					return "error:;Details Not Saved";
				}
			}else{
				conn.rollback();
				return "error:;Details Not Saved";
			}
		}catch (Exception ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {}
			ex.printStackTrace();
			employmentDTO.setErrorMessage("error:;"+ex.getMessage());
			logger.info("Excpetion in saveEmployement :: "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	public boolean updateMeritAdminEmployement(Connection conn,PreparedStatement ps,ResultSet rs,EmploymentDTO dto,String loggedInEmpId){
		boolean flag = false;
		StringBuffer query = new StringBuffer();
		try{
			ps = conn.prepareStatement("select emp_no from merit_transaction where emp_id=?");
			ps.setString(1, dto.getEmployeeId());
			rs = ps.executeQuery();
			ps=null;
			if(rs.next()){
				query.append("update merit_transaction set type=?,updated_date=sysdate(),updated_by=? ");
				query.append("where emp_id=?");
				ps = conn.prepareStatement(query.toString());
				ps.setString(1, dto.getFLSACategory());
				ps.setString(2, loggedInEmpId);
				ps.setString(3, dto.getEmployeeId());
				int i= ps.executeUpdate();
				if(i>0)
					flag =true;
			}else{
				query.append("insert into merit_transaction (type,updated_date,updated_by) ");
				query.append("select FLSA_CATEGORY,sysdate(),UPDATED_BY from EMP_PERSONAL where EMP_ID=?");
				ps = conn.prepareStatement(query.toString());
				ps.setString(1, dto.getEmployeeId());
				int i= ps.executeUpdate();
				if(i>0)
					flag =true;
			}
		}catch(Exception e){
			logger.error("Exception in updateMeritAdminEmployement : "+e.getMessage());
		}
		return flag;
	}
	@SuppressWarnings("deprecation")
	public void setPSforEmployment(EmploymentDTO dto,PreparedStatement ps,TelligentUser telligentUser,MessageHandler handler,String operation) throws java.sql.SQLException,IOException{
		ps.setString(1, dto.getStatusCode());
		ps.setString(2, dto.getStatusReason());
		ps.setString(3, dto.getStatus());

		if(dto.getMostRecentHireDate() !=null && !dto.getMostRecentHireDate().equalsIgnoreCase(""))
			ps.setDate(4, new java.sql.Date(new Date(dto.getMostRecentHireDate()).getTime()));
		else
			ps.setDate(4, null);
		if(dto.getLastHireDate() !=null && !dto.getLastHireDate().equalsIgnoreCase(""))
			ps.setDate(5, new java.sql.Date(new Date(dto.getLastHireDate()).getTime()));
		else
			ps.setDate(5, null);
		if(dto.getSeniorityDate() !=null && !dto.getSeniorityDate().equalsIgnoreCase(""))
			ps.setDate(6, new java.sql.Date(new Date(dto.getSeniorityDate()).getTime()));
		else
			ps.setDate(6, null);
		if(dto.getBenefitStartDate() !=null && !dto.getBenefitStartDate().equalsIgnoreCase(""))
			ps.setDate(7, new java.sql.Date(new Date(dto.getBenefitStartDate()).getTime()));
		else
			ps.setDate(7, null);
		if(dto.getTerminationDate() !=null && !dto.getTerminationDate().equalsIgnoreCase(""))
			ps.setDate(8, new java.sql.Date(new Date(dto.getTerminationDate()).getTime()));
		else
			ps.setDate(8, null);
		ps.setString(9, dto.getFLSACategory());
		ps.setString(10, dto.getClassification());
		ps.setString(11, dto.getEmploymentCategory());
		ps.setString(12, dto.getFullTimeEquivalency());
		ps.setString(13, dto.getLeaveStatusCode());
		ps.setString(14, dto.getLeaveReason());
		if(dto.getLeaveStartDate() !=null && !dto.getLeaveStartDate().equalsIgnoreCase(""))
			ps.setDate(15, new java.sql.Date(new Date(dto.getLeaveStartDate()).getTime()));
		else
			ps.setDate(15, null);
		if(dto.getExpectedLeaveEndDate() !=null && !dto.getExpectedLeaveEndDate().equalsIgnoreCase(""))
			ps.setDate(16, new java.sql.Date(new Date(dto.getExpectedLeaveEndDate()).getTime()));
		else
			ps.setDate(16, null);
		ps.setDate(17, new java.sql.Date(new Date(dto.getEffectiveDate()).getTime()));
		if(operation.equalsIgnoreCase("save"))
			ps.setDate(18, new java.sql.Date(new Date(handler.getMessage("effectiveEndDate")).getTime()));
		else
			ps.setDate(18, new java.sql.Date(new Date(dto.getEffectiveDate()).getTime()-1));
		ps.setString(19, telligentUser.getEmployeeId());
		ps.setString(20, dto.getEmployeeId());
	}

	public EmploymentDTO getEmployementDetails(String empId){
		logger.info("in getEmployementDetails");
		EmploymentDTO dto = new EmploymentDTO();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select SEQ_NO,STAT_CODE,STAT_CODE_REASON,STATUS,DATE_FORMAT(HIRE_DATE,'%m/%d/%Y') HIRE_DATE,DATE_FORMAT(LAST_HIRE_DATE,'%m/%d/%Y') LAST_HIRE_DATE,DATE_FORMAT(SENIORITY_DATE,'%m/%d/%Y') SENIORITY_DATE,DATE_FORMAT(BENEFIT_DATE,'%m/%d/%Y') BENEFIT_DATE,DATE_FORMAT(TERM_DATE,'%m/%d/%Y') TERM_DATE,FLSA_CATEGORY, ");
			query.append("CLASSIFICATION,EMPL_CATEGORY,FTE,LEAV_STAT_CODE,LEAV_REASON,DATE_FORMAT(LEAV_START_DATE,'%m/%d/%Y') LEAV_START_DATE,DATE_FORMAT(LEAV_END_DATE,'%m/%d/%Y') LEAV_END_DATE, ");
			query.append("DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,DATE_FORMAT(DATE_UPDATED,'%m/%d/%Y') DATE_UPDATED,UPDATED_BY from EMP_EMPLOYEMENT where EMP_ID=?");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			if(rs.next()){
				EmployeeDTO dto1 = getEmployeeDetails(empId);
				dto = setEmployementDetails(rs);
				dto.setEmployeeId(dto1.getEmployeeId());
				dto.setLastName(dto1.getLastName());
				dto.setFirstName(dto1.getFirstName());
				dto.setMiddleName(dto1.getMiddleName());
			}else{
				/*EmployeeDTO dto1 = dummyBlob(new EmployeeDTO());
				dto.setPicture(dto1.getPicture());*/
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployementDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return dto;
	}
	public ArrayList<EmploymentDTO> getEmployementDetailsHistory(String empId){
		logger.info("in getEmployementDetailsHistory");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		ArrayList<EmploymentDTO> list = new ArrayList<EmploymentDTO>();
		try{
			query.append("select e.SEQ_NO,sc.value STAT_CODE,sr.value STAT_CODE_REASON,s.value STATUS,DATE_FORMAT(HIRE_DATE,'%m/%d/%Y') HIRE_DATE,DATE_FORMAT(LAST_HIRE_DATE,'%m/%d/%Y') LAST_HIRE_DATE,DATE_FORMAT(SENIORITY_DATE,'%m/%d/%Y') SENIORITY_DATE,DATE_FORMAT(BENEFIT_DATE,'%m/%d/%Y') BENEFIT_DATE,DATE_FORMAT(TERM_DATE,'%m/%d/%Y') TERM_DATE,flsa.value FLSA_CATEGORY, ");
			query.append("c.value CLASSIFICATION,ec.value EMPL_CATEGORY,fte.value FTE,lsc.value LEAV_STAT_CODE,lsr.value LEAV_REASON,DATE_FORMAT(LEAV_START_DATE,'%m/%d/%Y') LEAV_START_DATE,DATE_FORMAT(LEAV_END_DATE,'%m/%d/%Y') LEAV_END_DATE, ");
			query.append("DATE_FORMAT(e.EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,DATE_FORMAT(e.DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = e.updated_by) UPDATED_BY,e.EMP_ID  EMP_ID ");
			query.append("from EMP_EMPLOYEMENT_HIS e ");
			query.append("left join Employement_Action sc on sc.id = e.STAT_CODE ");
			query.append("left join Employement_Action_Reason sr on sr.id = e.STAT_CODE_REASON ");
			query.append("left join Status s on s.id = e.STATUS ");
			query.append("left join FLSA_Category flsa on flsa.id = e.FLSA_CATEGORY ");
			query.append("left join Classification c on c.id = e.CLASSIFICATION ");
			query.append("left join Employement_Category ec on ec.id = e.EMPL_CATEGORY ");
			query.append("left join Full_Time_Equivalency fte on fte.id = e.FTE ");
			query.append("left join Leave_Status_code lsc on lsc.id = e.LEAV_STAT_CODE ");
			query.append("left join Leave_Status_Reason lsr on lsr.id = e.LEAV_REASON ");
			query.append("where e.EMP_ID=? order by EFFECTIVE_DATE desc"); 
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, empId);
			rs = ps.executeQuery();
			while(rs.next()){
				EmploymentDTO dto = setEmployementDetails(rs);
				dto.setEmployeeId(empId);
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployementDetailsHistory "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public EmploymentDTO getEmployementDetailsFromHistoryAjax(String seqNo){
		logger.info("in getEmployementDetailsFromHistoryAjax");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select SEQ_NO,STAT_CODE,STAT_CODE_REASON,STATUS,DATE_FORMAT(HIRE_DATE,'%m/%d/%Y') HIRE_DATE,DATE_FORMAT(LAST_HIRE_DATE,'%m/%d/%Y') LAST_HIRE_DATE,DATE_FORMAT(SENIORITY_DATE,'%m/%d/%Y') SENIORITY_DATE,DATE_FORMAT(BENEFIT_DATE,'%m/%d/%Y') BENEFIT_DATE,DATE_FORMAT(TERM_DATE,'%m/%d/%Y') TERM_DATE,FLSA_CATEGORY, ");
			query.append("CLASSIFICATION,EMPL_CATEGORY,FTE,LEAV_STAT_CODE,LEAV_REASON,DATE_FORMAT(LEAV_START_DATE,'%m/%d/%Y') LEAV_START_DATE,DATE_FORMAT(LEAV_END_DATE,'%m/%d/%Y') LEAV_END_DATE, ");
			query.append("DATE_FORMAT(EFFECTIVE_DATE,'%m/%d/%Y') EFFECTIVE_DATE,DATE_FORMAT(DATE_UPDATED,'%m/%d/%Y  %H:%i:%S') DATE_UPDATED,(select  concat(EMP.L_NAME,',',EMP.F_NAME) from EMP_PERSONAL EMP where EMP.EMP_ID = his.updated_by) UPDATED_BY,EMP_ID from EMP_EMPLOYEMENT_HIS his where SEQ_NO=?");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, seqNo);
			rs = ps.executeQuery();
			if(rs.next()){
				EmploymentDTO dto = setEmployementDetails(rs);
				dto.setEmployeeId(rs.getString("EMP_ID"));
				EmployeeDTO dto1 = getEmployeeDetails(rs.getString("EMP_ID"));
				dto.setFirstName(dto1.getFirstName());
				dto.setLastName(dto1.getLastName());
				dto.setMiddleName(dto1.getMiddleName());
				return dto;
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getEmployementDetailsFromHistoryAjax "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	private EmploymentDTO setEmployementDetails(ResultSet rs) throws SQLException{
		EmploymentDTO dto = new EmploymentDTO();
		dto.setSeqNo(rs.getString("SEQ_NO"));
		dto.setStatusCode(rs.getString("STAT_CODE"));
		dto.setStatusReason(rs.getString("STAT_CODE_REASON"));
		dto.setStatus(rs.getString("STATUS"));
		dto.setMostRecentHireDate(rs.getString("HIRE_DATE"));
		dto.setLastHireDate(rs.getString("LAST_HIRE_DATE"));
		dto.setSeniorityDate(rs.getString("SENIORITY_DATE"));
		dto.setBenefitStartDate(rs.getString("BENEFIT_DATE"));
		dto.setTerminationDate(rs.getString("TERM_DATE"));
		dto.setFLSACategory(rs.getString("FLSA_CATEGORY"));
		dto.setClassification(rs.getString("CLASSIFICATION"));
		dto.setEmploymentCategory(rs.getString("EMPL_CATEGORY"));
		dto.setFullTimeEquivalency(rs.getString("FTE"));
		dto.setLeaveStatusCode(rs.getString("LEAV_STAT_CODE"));
		dto.setLeaveReason(rs.getString("LEAV_REASON"));
		dto.setLeaveStartDate(rs.getString("LEAV_START_DATE"));
		dto.setExpectedLeaveEndDate(rs.getString("LEAV_END_DATE"));
		dto.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
		dto.setUpdatedBy(rs.getString("UPDATED_BY"));
		dto.setUpdatedDate(rs.getString("DATE_UPDATED"));
		return dto;
	}
	public ArrayList<CityDTO> searchCity(String searchParam){
		logger.info("in searchCity");
		ArrayList<CityDTO> list = new ArrayList<CityDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			if(null == searchParam || "".equalsIgnoreCase(searchParam.trim()))
				query.append("select id,value from City");
			else
				query.append("select id,value from City where upper(value) like '"+searchParam.toUpperCase()+"%'");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				CityDTO dto = new CityDTO();
				dto.setId(rs.getString("id"));
				dto.setCity(rs.getString("value"));
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in searchCity "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	
	
	
	public ArrayList<EmpDTO> searchEmpid(String searchParam){
		logger.info("in searchEmpid");
		ArrayList<EmpDTO> list = new ArrayList<EmpDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			if(null == searchParam || "".equalsIgnoreCase(searchParam.trim()))
				query.append("select EMP_NO from FINGER_PRINT");
			else
				query.append("select EMP_NO from FINGER_PRINT where upper(EMP_NO) like '"+searchParam.toUpperCase()+"%'");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				EmpDTO dto = new EmpDTO();
				dto.setEmployeeId(rs.getString("EMP_NO"));
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in searchEmpid "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}

	public ArrayList<StateDTO> searchState(String searchParam,String city){
		logger.info("in searchState");
		ArrayList<StateDTO> list = new ArrayList<StateDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			if(null == searchParam || "".equalsIgnoreCase(searchParam.trim()))
				query.append("select id,value from State");
			else if(!"".equalsIgnoreCase(searchParam) && ("".equalsIgnoreCase(city)))
				query.append("select id,value from State where upper(value) like '"+searchParam.toUpperCase()+"%'");
			else
				query.append("select S.id id,S.value value from State S,City C where upper(S.value) like '"+searchParam.toUpperCase()+"%' and upper(C.value)='"+city+"' and C.state_id = S.id");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				StateDTO dto = new StateDTO();
				dto.setId(rs.getString("id"));
				dto.setStateName(rs.getString("value"));
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in searchState "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public String checkPrerequisites(EmployeeDTO dto){
		logger.info("in checkPrerequisites");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer retStr = new StringBuffer();
		try{
			conn = this.getConnection();
			String str = getEffectiveDate(conn,ps,rs,"EMP_PERSONAL_HIS",dto.getEmployeeId());
			boolean flag = DateUtility.compareDates(dto.getEffectiveDate(), str);
			if(flag){
				retStr.append("Effective Date less than that of an existing record and History record is being inserted\n");
			}
			if("".equalsIgnoreCase(getCityId(conn, ps, dto.getCity()))){
				retStr.append("The City you are tyring to save is new \n");
			}
			if("".equalsIgnoreCase(getStateId(conn, ps, dto.getState()))){
				retStr.append("The state you are tyring to save is new \n");
			}
			if(retStr.length() > 0){
				retStr.append("Are you sure you want to continue ? ");
			}
		}catch(Exception ex){
			logger.info("Excpetion in checkPrerequisites "+ex.getMessage());
		}finally{
			this.closeAll(conn, ps, rs);
		}
		return retStr.toString();
	}
	public ArrayList<MapDTO> getRelationShipDetails(){
		logger.info("in getRelationShipDetails");
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select id,value from Relationship");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getRelationShipDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public ArrayList<TeamDTO> searchTeams(String param){
		logger.info("in searchTeams");
		ArrayList<TeamDTO> list = new ArrayList<TeamDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select team_id,team_name from team where upper(team_name) like '"+param.toUpperCase()+"%'");
			conn = this.getConnection();
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				TeamDTO dto = new TeamDTO();
				dto.setTeamId(rs.getString("team_id"));
				dto.setTeamName(rs.getString("team_name"));
				list.add(dto);
			}
		}catch (Exception ex) {
			logger.info("Excpetion in searchTeams "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public ArrayList<MapDTO> searchLastNameSupervisor(String lastName) {
		logger.info("in searchLastNameSupervisor DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			query.append("select EMP_ID,F_NAME,L_NAME from EMP_PERSONAL where ");
			if(lastName !=null && !lastName.trim().equalsIgnoreCase("")){
				query.append("L_NAME like '"+lastName+"%'");
			}/*else if(firstName !=null && !firstName.trim().equalsIgnoreCase("")){
				query.append("F_NAME like '"+firstName+"%'");
			}else if(empId !=null && !empId.trim().equalsIgnoreCase("")){
				query.append("EMP_ID like '"+empId+"%'");
			}*/
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("EMP_ID"));
				dto.setValue(rs.getString("EMP_ID")+" "+rs.getString("L_NAME")+","+rs.getString("F_NAME"));
				list.add(dto);
			}
		}catch (Exception ex) {
			//ex.printStackTrace();
			logger.info("Excpetion in searchLastNameSupervisor "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public EmployeePositionDTO getOrgLabels(EmployeePositionDTO dto){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement("select id,org1label,org2label,org3label,org4label,org5label,org6label,org7label,org8label,org9label,org10label from orglabels");
			rs = ps.executeQuery();
			if(rs.next()){
				dto.setOrg1Label(rs.getString("org1label")!=null && !rs.getString("org1label").equalsIgnoreCase("")?rs.getString("org1label"):"org1");
				dto.setOrg2Label(rs.getString("org2label")!=null && !rs.getString("org2label").equalsIgnoreCase("")?rs.getString("org2label"):"org2");
				dto.setOrg3Label(rs.getString("org3label")!=null && !rs.getString("org3label").equalsIgnoreCase("")?rs.getString("org3label"):"org3");
				dto.setOrg4Label(rs.getString("org4label")!=null && !rs.getString("org4label").equalsIgnoreCase("")?rs.getString("org4label"):"org4");
				dto.setOrg5Label(rs.getString("org5label")!=null && !rs.getString("org5label").equalsIgnoreCase("")?rs.getString("org5label"):"org5");
				dto.setOrg6Label(rs.getString("org6label")!=null && !rs.getString("org6label").equalsIgnoreCase("")?rs.getString("org6label"):"org6");
				dto.setOrg7Label(rs.getString("org7label")!=null && !rs.getString("org7label").equalsIgnoreCase("")?rs.getString("org7label"):"org7");
				dto.setOrg8Label(rs.getString("org8label")!=null && !rs.getString("org8label").equalsIgnoreCase("")?rs.getString("org8label"):"org8");
				dto.setOrg9Label(rs.getString("org9label")!=null && !rs.getString("org9label").equalsIgnoreCase("")?rs.getString("org9label"):"org9");
				dto.setOrg10Label(rs.getString("org10label")!=null && !rs.getString("org10label").equalsIgnoreCase("")?rs.getString("org10label"):"org10");
			}
		}catch (Exception ex) {
			logger.info("Excpetion in getOrgLabels "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return dto;
	}
	public float calculateAnnualPay(float baseRate,String baseRateFrequency){
		// Base Rate Frequency
		float annualPay =0;

		if (baseRateFrequency != null && baseRateFrequency.equalsIgnoreCase("2")){
			annualPay = baseRate*52;
		}else if (baseRateFrequency != null && baseRateFrequency.equalsIgnoreCase("3")){
			annualPay = baseRate*26;
		}else if (baseRateFrequency != null && baseRateFrequency.equalsIgnoreCase("4")){
			annualPay = baseRate*24;
		}else if (baseRateFrequency != null && baseRateFrequency.equalsIgnoreCase("5")){
			annualPay = baseRate*12;
		}else if (baseRateFrequency != null && baseRateFrequency.equalsIgnoreCase("1")){
			annualPay = baseRate*2080;
		}else if (baseRateFrequency != null && baseRateFrequency.equalsIgnoreCase("7")){
			annualPay = baseRate*1;
		}

		return annualPay;
	}


	public int calculateAnnualStandarHours(int scheduleHours,String scheduleHoursFrequency){
		// Base Rate Frequency
		int annualStandardHours =0;

		if (scheduleHoursFrequency != null && scheduleHoursFrequency.equalsIgnoreCase("2")){
			annualStandardHours = scheduleHours*52;
		}else if (scheduleHoursFrequency != null && scheduleHoursFrequency.equalsIgnoreCase("3")){
			annualStandardHours = scheduleHours*26;
		}else if (scheduleHoursFrequency != null && scheduleHoursFrequency.equalsIgnoreCase("4")){
			annualStandardHours = scheduleHours*24;
		}else if (scheduleHoursFrequency != null && scheduleHoursFrequency.equalsIgnoreCase("5")){
			annualStandardHours = scheduleHours*12;
		}else if (scheduleHoursFrequency != null && scheduleHoursFrequency.equalsIgnoreCase("1")){
			annualStandardHours = scheduleHours*2080;
		}else if (scheduleHoursFrequency != null && scheduleHoursFrequency.equalsIgnoreCase("7")){
			annualStandardHours = scheduleHours*1;
		}

		return annualStandardHours;
	}

	public float calculateAnnualMidpoit(float midpoint,String rateFrequency){

		float annualMidpoint =0;

		if (rateFrequency != null && rateFrequency.equalsIgnoreCase("1")){
			annualMidpoint = midpoint*2080;
		}else if (rateFrequency != null && rateFrequency.equalsIgnoreCase("2")){
			annualMidpoint = midpoint*52;
		}else if (rateFrequency != null && rateFrequency.equalsIgnoreCase("3")){
			annualMidpoint = midpoint*26;
		}else if (rateFrequency != null && rateFrequency.equalsIgnoreCase("4")){
			annualMidpoint = midpoint*24;
		}else if (rateFrequency != null && rateFrequency.equalsIgnoreCase("5")){
			annualMidpoint = midpoint*12;
		}else if (rateFrequency != null && rateFrequency.equalsIgnoreCase("6")){
			annualMidpoint = midpoint*6;
		}else if (rateFrequency != null && rateFrequency.equalsIgnoreCase("7")){
			annualMidpoint = midpoint*1;
		}

		return annualMidpoint;
	}

	public float calculateCompRatio(int annualStandarHours,float annualPay,float annualMidpoit){
		float compRatio =0;
		try {
			compRatio = annualPay/annualMidpoit;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compRatio;
	}

	public float calculateHourlyRate(float annualPay){
		float hourlyRate =0;
		hourlyRate = annualPay/2080;
		return hourlyRate;
	}


	public String calculateQuartile(float annualPay,float minimunPay,float maxPay,float compRatio,
			float min1st,float max1st,float min2nd,float max2nd,float min3rd,float max3rd,float min4th,float max4th ){
		String quartile="";
		if (compRatio < min1st){
			quartile = "Below Minimum";
		}else if (compRatio > max4th){
			quartile = "Above Maximum";
		}else if (compRatio >= min1st && compRatio <=max1st){
			quartile = "1st";
		}else if (compRatio >= min2nd && compRatio <=max2nd){
			quartile = "2nd";
		}else if (compRatio >= min3rd && compRatio <=max3rd){
			quartile = "3rd";
		}else if (compRatio >= min4th && compRatio <=max4th){
			quartile = "4th";
		}

		return quartile;
	}
	public float calculatePayperiodRate(float annualPay,int noOfPayPeriod){
		float payPeriodRate = 0;
		try {
			payPeriodRate = annualPay/noOfPayPeriod;
		} catch (Exception e) {
		}
		return payPeriodRate;
	}
	public int calculatePayperiodHours(int annualStandardHours,int noOfPayPeriod){
		int payperiodHours =0;
		try {
			payperiodHours = annualStandardHours/noOfPayPeriod;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payperiodHours;
	}
	public int calculateWeeklyHours(int annualStandardHours){
		int weeklyHours = 0;
		try {
			weeklyHours = annualStandardHours/52;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weeklyHours;
	}
    public String fillAllDetails(String empId){
    	logger.info("in fillAllDetails DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			/*query.append("select concat(L_NAME,',',F_NAME,',',e.emp_id) name from EMP_PERSONAL e  where ");
			query.append("EMP_ID = '"+empId+"'");*/
			query.append("select concat(L_NAME,',',F_NAME,',',e.emp_id,',',team_name) name from EMP_PERSONAL e,merit_transaction mt,team t where mt.team=t.team_id and mt.emp_id = e.emp_id ");
			query.append(" and e.emp_id = '"+empId+"'");
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				String retStr = rs.getString("name");
				rs.close();ps.close();
				query = new StringBuffer();
				query.append("select user_name from users where employee_id = '"+empId+"'");
				ps = conn.prepareStatement(query.toString());
				rs = ps.executeQuery();
				if(rs.next())
					return retStr+","+rs.getString("user_name");
				else
					return retStr;
			}
		}catch (Exception ex) {
			logger.info("Excpetion in fillAllDetails "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return "";
    }
public void display(String abc){
	System.out.println("display-->"+abc);
}
}
