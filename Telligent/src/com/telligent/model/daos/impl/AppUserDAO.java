package com.telligent.model.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.telligent.common.user.TelligentUser;
import com.telligent.core.system.annotation.SpringBean;
import com.telligent.model.db.AbstractDBManager;
import com.telligent.model.dtos.AppUserDTO;
import com.telligent.model.dtos.AppUserListDTO;
import com.telligent.model.dtos.MapDTO;
import com.telligent.model.dtos.SecurityGroupDTO;
import com.telligent.model.dtos.TeamDTO;
import com.telligent.util.BouncyCastleEncryptor;

/**
 * @author spothu
 *
 */
@SpringBean
public class AppUserDAO extends AbstractDBManager{

	public final Logger logger = Logger.getLogger(AppUserDAO.class);

	@Autowired
	BouncyCastleEncryptor bouncyCastleEncryptor;
	public ArrayList<TeamDTO> getTeamList(){
		logger.info("in getTeamList");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList <TeamDTO> teamList = new ArrayList<TeamDTO>();
		String qry = "select team_id,team_name,department_id,supervisor_employee_id,approval_status from team  where isActive=1";
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(qry);
			rs = ps.executeQuery();
			while(rs.next()){
				TeamDTO dto = new TeamDTO();
				dto.setTeamId(rs.getString("team_id"));
				dto.setTeamName(rs.getString("team_name"));
				dto.setDepartmentId(rs.getString("department_id"));
				dto.setSupervisorEmpId(rs.getString("supervisor_employee_id"));
				dto.setApprovalStatus(rs.getString("approval_status"));
				teamList.add(dto);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getTeamList"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return teamList;
	}
	public ArrayList<SecurityGroupDTO> getSecurityGroupList(){
		logger.info("in getSecurityGroupList");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList <SecurityGroupDTO> securityGroupList = new ArrayList<SecurityGroupDTO>();
		String qry = "select security_group,security_group_desc from SECURITY_GROUP";
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(qry);
			rs = ps.executeQuery();
			while(rs.next()){
				SecurityGroupDTO dto = new SecurityGroupDTO();
				dto.setName(rs.getString("security_group"));
				dto.setDescription(rs.getString("security_group_desc"));
				securityGroupList.add(dto);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getSecurityGroupList"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return securityGroupList;
	}

	public String saveAppUserDetails(AppUserDTO dto,TelligentUser user){
		logger.info("in saveAppUserDetails DAO");
		String retStr = null;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		StringBuffer UserQuery = new StringBuffer();
		StringBuffer sltQuery = new StringBuffer();
		StringBuffer checkEmpIdQuery = new StringBuffer();
		StringBuffer userUpdate = new StringBuffer();
		StringBuffer appUserQuery = new StringBuffer();
		StringBuffer appUserUpdate = new StringBuffer();
		StringBuffer teamApprovalQuery = new StringBuffer();
		StringBuffer deleteApp = new StringBuffer();
		deleteApp.append("delete from Team_Approval_Level where EMP_ID='"+dto.getEmployeeId()+"'");

		sltQuery.append("select user_name from users where user_name='"+dto.getUserId()+"'");
		checkEmpIdQuery.append("select user_name from users where user_name!='"+dto.getUserId()+"' and employee_id='"+dto.getEmployeeId()+"'");
		teamApprovalQuery.append("select EMP_ID from Team_Approval_Level  where EMP_ID= ? and team=?");
		

		UserQuery.append("insert into users (user_name,password,employee_id,role_id,isChangePassword,email_id,effective_Date, ");
		UserQuery.append("user_IP,BadLogin_Count,status,Security_Group,Merti_Admin_Approaval_Level,Performance_Approval_level, ");
		UserQuery.append("Bonus_admin_role,Succession_role,updated_date,updated_by)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?) ");
		appUserQuery.append("insert into Team_Approval_Level (emp_id,team,approval_level,effective_date,end_effective_date)");
		appUserQuery.append("values(?,?,?,?,?)");
		ArrayList<AppUserListDTO> appUserList = new ArrayList<AppUserListDTO>();
		appUserList = dto.getAppUserList();
		try{
			conn = this.getConnection();
			conn.setAutoCommit(false);
			ps2 = conn.prepareStatement(checkEmpIdQuery.toString());
			rs = ps2.executeQuery();
			if(rs.next())
				return "Employeed Id already assigned to "+rs.getString("user_name");
			ps2.close();rs.close();
			ps2 = conn.prepareStatement(sltQuery.toString());
			rs = ps2.executeQuery();
			if (!rs.next()){
				ps = conn.prepareStatement(UserQuery.toString());
				ps.setString(1, dto.getUserId());
				ps.setString(2, bouncyCastleEncryptor.encryptString(dto.getPassword()));
				ps.setString(3, dto.getEmployeeId());
				ps.setString(4, dto.getGeneralRole());
				ps.setString(5, "Yes");
				ps.setString(6, dto.getEmailId());
				ps.setDate(7, new java.sql.Date(new Date(dto.getEffectiveDate()).getTime()));
				ps.setString(8, dto.getUserIp());
				ps.setString(9, null != dto.getBadLoginCount() && !dto.getBadLoginCount().trim().equalsIgnoreCase("") ? dto.getBadLoginCount():"0");
				ps.setString(10, dto.getStatus());
				ps.setString(11, dto.getSecurityGroup());
				ps.setString(12, dto.getMeritAdminApprovalRole());
				ps.setString(13, dto.getPerformanceAdminRole());
				ps.setString(14, dto.getBonusAdminRole());
				ps.setString(15, dto.getSuccessionRole());
				ps.setString(16, user.getEmployeeId());
				int i = ps.executeUpdate();
				ps1 = conn.prepareStatement(appUserQuery.toString());
				if(i>0){
					int count = 0;
					for(AppUserListDTO dto1: appUserList){
						if (dto1.getTeam() != null && !dto1.getTeam().trim().equals("")){
							ps1.setString(1, dto.getEmployeeId());
							ps1.setString(2, dto1.getTeam());
							ps1.setString(3, dto1.getMeritApprovalLevel());
							if(dto1.getEffectiveDate() == null || "".equalsIgnoreCase(dto1.getEffectiveDate()))
								dto1.setEffectiveDate("01/01/1900");
							if(dto1.getEndDate() == null || "".equalsIgnoreCase(dto1.getEndDate()))
								dto1.setEndDate("12/31/3000");
							ps1.setDate(4, new java.sql.Date(new Date(dto1.getEffectiveDate()).getTime()));
							ps1.setDate(5, new java.sql.Date(new Date(dto1.getEndDate()).getTime()));
							ps1.addBatch();
							count++;
						}
					}
					int j[] = ps1.executeBatch();
					if(j.length == count){
						conn.commit();
						retStr = "User Details Saved ";
					}else{
						conn.rollback();
						retStr = "User Details not Saved";
					}
				}
			}else{
				ps3 = conn.prepareStatement(teamApprovalQuery.toString());
				//rs1 = ps3.executeQuery();
				userUpdate.append("update users set user_name=?,password=?,employee_id=?,role_id=?,email_id=?,effective_Date=?, ");
				userUpdate.append("user_IP=?,BadLogin_Count=?,status=?,Security_Group=?,Merti_Admin_Approaval_Level=?,Performance_Approval_level=?, ");
				userUpdate.append("Bonus_admin_role=?,Succession_role=?,updated_date=sysdate(),updated_by=? where employee_id='"+dto.getEmployeeId()+"'");
				appUserUpdate.append("update Team_Approval_Level set emp_id=?,team=?,approval_level=?,effective_date=?,end_effective_date=? where emp_id='"+dto.getEmployeeId()+"'");
				ps = conn.prepareStatement(userUpdate.toString());
				ps.setString(1, dto.getUserId());
				ps.setString(2, bouncyCastleEncryptor.encryptString(dto.getPassword()));
				ps.setString(3, dto.getEmployeeId());
				ps.setString(4, dto.getGeneralRole());
				//ps.setString(5, "Yes");
				ps.setString(5, dto.getEmailId());
				ps.setDate(6, new java.sql.Date(new Date(dto.getEffectiveDate()).getTime()));
				ps.setString(7, dto.getUserIp());
				ps.setString(8, dto.getBadLoginCount());
				ps.setString(9, dto.getStatus());
				ps.setString(10, dto.getSecurityGroup());
				ps.setString(11, dto.getMeritAdminApprovalRole());
				ps.setString(12, dto.getPerformanceAdminRole());
				ps.setString(13, dto.getBonusAdminRole());
				ps.setString(14, dto.getSuccessionRole());
				ps.setString(15, user.getEmployeeId());
				int i = ps.executeUpdate();
				if (i >0 ){
					ps1 = conn.prepareStatement(appUserQuery.toString());
					ps3 = conn.prepareStatement(deleteApp.toString());
					ps3.executeUpdate();
					int count = 0;
					for(AppUserListDTO dto1: appUserList){
						if (dto1.getTeam() != null && !dto1.getTeam().trim().equals("")){
							ps1.setString(1, dto.getEmployeeId());
							ps1.setString(2, dto1.getTeam());
							ps1.setString(3, dto1.getMeritApprovalLevel());
							if(dto1.getEffectiveDate() == null || "".equalsIgnoreCase(dto1.getEffectiveDate()))
								dto1.setEffectiveDate("01/01/1900");
							if(dto1.getEndDate() == null || "".equalsIgnoreCase(dto1.getEndDate()))
								dto1.setEndDate("12/31/3000");
							ps1.setDate(4, new java.sql.Date(new Date(dto1.getEffectiveDate()).getTime()));
							ps1.setDate(5, new java.sql.Date(new Date(dto1.getEndDate()).getTime()));
							ps1.addBatch();
							count++;
						}
					}
					int j[] = ps1.executeBatch();
					if(j.length == count){
						conn.commit();
						retStr = "User Details Saved ";
					}else{
						conn.rollback();
						retStr = "User Details not Saved";
					}
				}
				/*if(i>0){
					int count = 0;
					for(AppUserListDTO dto1: appUserList){
						ps3.setString(1, dto.getEmployeeId());
						ps3.setString(2, dto1.getTeam());
						rs1 = ps3.executeQuery();
						ps1 = conn.prepareStatement(appUserUpdate.toString());
						if (rs1.next()){
							ps1.setString(1, dto.getEmployeeId());
							ps1.setString(2, dto1.getTeam());
							ps1.setString(3, dto1.getMeritApprovalLevel());
							ps1.setDate(4, new java.sql.Date(new Date(dto1.getEffectiveDate()).getTime()));
							ps1.setDate(5, new java.sql.Date(new Date(dto1.getEndDate()).getTime()));
							ps1.addBatch();
							count++;
						
					}else{
						if (dto1.getTeam() !=null && !dto1.getTeam().equals("")){
						ps1 = conn.prepareStatement(appUserQuery.toString());
						ps1.setString(1, dto.getEmployeeId());
						ps1.setString(2, dto1.getTeam());
						ps1.setString(3, dto1.getMeritApprovalLevel());
						ps1.setDate(4, new java.sql.Date(new Date(dto1.getEffectiveDate()).getTime()));
						ps1.setDate(5, new java.sql.Date(new Date(dto1.getEndDate()).getTime()));
						ps1.addBatch();
						count++;
						}
					}
				}
					int j[] = ps1.executeBatch();
					if(j.length > 0){
						conn.commit();
						retStr = "User Details Saved ";
					}else{
						conn.rollback();
						retStr = "User Details not Saved";
					}
				}*/
					/*if (rs1.next()){
					int count = 0;
					ps1 = conn.prepareStatement(appUserUpdate.toString());
					for(AppUserListDTO dto1: appUserList){
						if (dto1.getTeam() != null && !dto1.getTeam().trim().equals("")){
							ps1.setString(1, dto.getEmployeeId());
							ps1.setString(2, dto1.getTeam());
							ps1.setString(3, dto1.getMeritApprovalLevel());
							ps1.setDate(4, new java.sql.Date(new Date(dto1.getEffectiveDate()).getTime()));
							ps1.setDate(5, new java.sql.Date(new Date(dto1.getEndDate()).getTime()));
							ps1.addBatch();
							count++;
						}
					}
					int j[] = ps1.executeBatch();
					if(j.length == count){
						conn.commit();
						retStr = "User Details Saved ";
					}else{
						conn.rollback();
						retStr = "User Details not Saved";
					}
				
				}else{
					ps1 = conn.prepareStatement(appUserQuery.toString());
					int count = 0;
					for(AppUserListDTO dto1: appUserList){
						if (dto1.getTeam() != null && !dto1.getTeam().trim().equals("")){
							ps1.setString(1, dto.getEmployeeId());
							ps1.setString(2, dto1.getTeam());
							ps1.setString(3, dto1.getMeritApprovalLevel());
							ps1.setDate(4, new java.sql.Date(new Date(dto1.getEffectiveDate()).getTime()));
							ps1.setDate(5, new java.sql.Date(new Date(dto1.getEndDate()).getTime()));
							ps1.addBatch();
							count++;
						}
					}
					int j[] = ps1.executeBatch();
					if(j.length == count){
						conn.commit();
						retStr = "User Details Saved ";
					}else{
						conn.rollback();
						retStr = "User Details not Saved";
					}
				}
			}	*/
				}
			
		}catch(Exception ex){
			try {
				conn.rollback();
			} catch (SQLException e) {}
			ex.printStackTrace();
			retStr = "User Details not Saved :: "+ex.getMessage();
			logger.info("Excpetion in saveAppUserDetails :: "+ex.getMessage());
		}finally{
			try{
				ps1.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			this.closeAll( ps1);
			this.closeAll( ps2);
			this.closeAll(conn, ps, rs);

		}
		return retStr;
	}

	public AppUserDTO getUserDetails(String id,String userId){
		//AppUserDTO dto = new AppUserDTO();
		StringBuffer sltQuery = new StringBuffer();
		StringBuffer sltTeamQuery = new StringBuffer();
		sltQuery.append("select user_name,password,employee_id,role_id,isChangePassword,email_id,DATE_FORMAT(effective_Date,'%m/%d/%Y') effective_Date, ");
		sltQuery.append("user_IP,BadLogin_Count,status,Security_Group,Merti_Admin_Approaval_Level,Performance_Approval_level,Bonus_admin_role,Succession_role ");
		if(id !=null && !"".equalsIgnoreCase(id))
			sltQuery.append(" from users where employee_id= ?");
		else
			sltQuery.append(" from users where user_name= ?");
		sltTeamQuery.append("select team,approval_level,DATE_FORMAT(effective_date,'%m/%d/%Y') effective_date,DATE_FORMAT(end_effective_date,'%m/%d/%Y') end_effective_date from Team_Approval_Level where emp_id=?");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement teamPs = null;
		ResultSet teamRs = null;
		AppUserDTO dto = new AppUserDTO();
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement(sltQuery.toString());
			if(id !=null && !"".equalsIgnoreCase(id))
				ps.setString(1, id);
			else
				ps.setString(1, userId);
			rs = ps.executeQuery();
			if(rs.next())
				dto = setUserDetails(rs,dto);
			teamPs = conn.prepareStatement(sltTeamQuery.toString());
			teamPs.setString(1, dto.getEmployeeId());
			teamRs = teamPs.executeQuery();
			ArrayList<AppUserListDTO> appUserList = new ArrayList<AppUserListDTO>();
			while(teamRs.next()){
				AppUserListDTO listDTO = new AppUserListDTO();
				listDTO.setTeam(teamRs.getString("team"));
				listDTO.setMeritApprovalLevel(teamRs.getString("approval_level"));
				listDTO.setEffectiveDate(teamRs.getString("effective_date"));
				listDTO.setEndDate(teamRs.getString("end_effective_date"));
				appUserList.add(listDTO);
			}
			dto.setAppUserList(appUserList);
		}catch(Exception ex){
			this.closeAll(conn,ps,rs);
			this.closeAll(teamPs,teamRs);

		}
		return dto;
	}
	private AppUserDTO setUserDetails(ResultSet rs,AppUserDTO dto) throws Exception{

		dto.setUserId(rs.getString("user_name"));
		dto.setPassword(bouncyCastleEncryptor.decryptString(rs.getString("password")));
		dto.setEmployeeId(rs.getString("employee_id"));
		dto.setGeneralRole(rs.getString("role_id"));
		dto.setPasswordChanged(rs.getString("isChangePassword"));
		dto.setEmailId(rs.getString("email_id"));
		dto.setEffectiveDate(rs.getString("effective_Date"));
		dto.setUserIp(rs.getString("user_IP"));
		dto.setBadLoginCount(rs.getString("BadLogin_Count"));
		dto.setStatus(rs.getString("status"));
		dto.setSecurityGroup(rs.getString("Security_Group"));
		dto.setMeritAdminApprovalRole(rs.getString("Merti_Admin_Approaval_Level"));
		dto.setPerformanceAdminRole(rs.getString("Performance_Approval_level"));
		dto.setBonusAdminRole(rs.getString("Bonus_admin_role"));
		dto.setSuccessionRole(rs.getString("Succession_role"));
		return dto;
	}
	
	public ArrayList<AppUserDTO> getAppUserDetails(){
		StringBuffer sltQuery = new StringBuffer();
		sltQuery.append("select user_name,password,employee_id,role_id,isChangePassword,email_id,DATE_FORMAT(u.effective_Date,'%m/%d/%Y') effective_Date, ");
		sltQuery.append("user_IP,BadLogin_Count,status,Security_Group,Merti_Admin_Approaval_Level,PAR.value Performance_Approval_level,BAR.value Bonus_admin_role,SR.value Succession_role ");
		sltQuery.append(",DATE_FORMAT(u.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy ");
		sltQuery.append(" from users u");
		sltQuery.append(" left join EMP_PERSONAL E on u.updated_by = EMP_ID ");
		sltQuery.append(" left join Performance_Admin_Role PAR on PAR.id= Performance_Approval_level ");
		sltQuery.append(" left join Bonus_Admin_Role BAR on BAR.id=Bonus_admin_role ");
		sltQuery.append(" left join Succession_Role SR on SR.id =Succession_role ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AppUserDTO> list = new ArrayList<AppUserDTO>();
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement(sltQuery.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				AppUserDTO dto = new AppUserDTO();
				dto = setUserDetails(rs,dto);
				if(dto.getStatus()!=null){
					if(dto.getStatus().equalsIgnoreCase("A"))
						dto.setStatus("Active");
					else if(dto.getStatus().equalsIgnoreCase("I"))
						dto.setStatus("InActive");
					else if(dto.getStatus().equalsIgnoreCase("S"))
						dto.setStatus("Suspended for password voilation");
				}
				dto.setUpdatedDate(rs.getString("updated_date"));
				dto.setUpdatedBy(rs.getString("updatedBy"));
				list.add(dto);
			}
		}catch(Exception ex){
			this.closeAll(conn,ps,rs);

		}
		return list;
	}
	public ArrayList<MapDTO> searchUserId(String username) {
		logger.info("in searchUserId DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			query.append("select employee_id,user_name from users where upper(user_name) like '"+username+"%'");
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("employee_id"));
				dto.setValue(rs.getString("user_name"));
				list.add(dto);
			}
		}catch (Exception ex) {
			//ex.printStackTrace();
			logger.info("Excpetion in searchUserId "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public ArrayList<MapDTO> searchTeamEmployeesAppUser(String teamName) {
		logger.info("in searchTeamEmployeesAppUser DAO");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			query.append("SELECT a.EMP_ID EMP_ID,concat(a.EMP_ID,',',L_NAME,',',F_NAME) Name from (SELECT EMP_ID,app.team team_name FROM team t,Team_Approval_Level app where t.team_name = app.team and t.team_name like '"+teamName+"%') a left join EMP_PERSONAL E  on a.EMP_ID = E.EMP_ID group by a.EMP_Id");
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("EMP_ID"));
				dto.setValue(rs.getString("Name"));
				list.add(dto);
			}
		}catch (Exception ex) {
			//ex.printStackTrace();
			logger.info("Excpetion in searchTeamEmployeesAppUser "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}

}
