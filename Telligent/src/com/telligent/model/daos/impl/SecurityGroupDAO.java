package com.telligent.model.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.telligent.common.user.TelligentUser;
import com.telligent.core.system.annotation.SpringBean;
import com.telligent.model.db.AbstractDBManager;
import com.telligent.model.dtos.EmployeeDTO;
import com.telligent.model.dtos.MapDTO;
import com.telligent.model.dtos.SecurityGroupDTO;

/**
 * @author spothu
 *
 */
@SpringBean
public class SecurityGroupDAO extends AbstractDBManager{
	public final Logger logger = Logger.getLogger(SecurityGroupDAO.class);

	public String saveSecurityGroup(SecurityGroupDTO dto,TelligentUser telligentUser){
		logger.info("in saveSecurityGroup");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){
				conn.setAutoCommit(false);
				query.append("update SECURITY_GROUP set SECURITY_GROUP=?,SECURITY_GROUP_DESC=?,MERIT_ADMIN=?,PERFORMANCE_MGR=?,BONUS_MGR=?,SUCCESSION_MGR=?,TEAMS=?,EMPLOYEES=?,");
				query.append("SYSTEM_TABLES=?,SECURITY=?,MERIT_REPORTS=?,BONUS_REPORTS=?,SUCCESSION_REPORTS=?,EMPLOYEE_REPORTS=?,SYSTEM_REPORTS=?,updated_date=sysdate(),updated_by=? where SEQNO=?");
				ps = conn.prepareStatement(query.toString());
				setPS(dto, ps, telligentUser, "update");
			}else{
				if(!checkRecordExistence(conn, ps, rs,dto.getName())){
					conn.setAutoCommit(false);
					query.append("insert into SECURITY_GROUP (SECURITY_GROUP,SECURITY_GROUP_DESC,MERIT_ADMIN,PERFORMANCE_MGR,BONUS_MGR,SUCCESSION_MGR,TEAMS,EMPLOYEES,");
					query.append("SYSTEM_TABLES,SECURITY,MERIT_REPORTS,BONUS_REPORTS,SUCCESSION_REPORTS,EMPLOYEE_REPORTS,SYSTEM_REPORTS,updated_date,updated_by) ");
					query.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?)");
					ps = conn.prepareStatement(query.toString());
					setPS(dto, ps, telligentUser, "save");
				}else{
					return "error:;Security Group already exists";
				}
			}
			int i = ps.executeUpdate();
			if(i>0){
				conn.commit();
				return "success:;Details Saved Succuessfully";
			}else{
				conn.rollback();
				return "error:;Details Not Saved";
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in saveSecurityGroup"+ex.getMessage());
			return "error :; "+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}
	public void setPS(SecurityGroupDTO dto,PreparedStatement ps,TelligentUser user,String operation) throws SQLException{
		ps.setString(1, dto.getName());
		ps.setString(2, dto.getDescription());
		ps.setBoolean(3, dto.isMeritAdmin());
		ps.setBoolean(4, dto.isPerfManager());
		ps.setBoolean(5, dto.isBonusManager());
		ps.setBoolean(6, dto.isSuccessionManager());
		ps.setBoolean(7, dto.isTeams());
		ps.setBoolean(8, dto.isEmployees());
		ps.setBoolean(9, dto.isSystemTables());
		ps.setBoolean(10, dto.isSecurity());
		ps.setBoolean(11, dto.isMeritReports());
		ps.setBoolean(12, dto.isBonusReports());
		ps.setBoolean(13, dto.isSuccessionReports());
		ps.setBoolean(14, dto.isEmployeeReports());
		ps.setBoolean(15, dto.isSystemReports());
		ps.setString(16, user.getEmployeeId());
		if(operation.equalsIgnoreCase("update"))
			ps.setString(17, dto.getSeqNo());
	}
	public boolean checkRecordExistence(Connection conn, PreparedStatement ps,ResultSet rs,String value){
		try {
			ps = conn.prepareStatement("select SECURITY_GROUP from SECURITY_GROUP where upper(SECURITY_GROUP)=?");
			ps.setString(1, value.toUpperCase());
			rs = ps.executeQuery();
			if(rs.next())
				return true;
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in saveSecurityGroup"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return false;
	}
	public ArrayList<SecurityGroupDTO> getSecurityGroupDetails(){
		logger.info("in getSecurityGroupDetails == ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getSecurityGroupDetails( conn, ps, rs);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getSecurityGroupDetails"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return new ArrayList<SecurityGroupDTO>();
	}
	public ArrayList<SecurityGroupDTO> getSecurityGroupDetails(Connection conn,PreparedStatement ps,ResultSet rs){
		ArrayList<SecurityGroupDTO> list = new ArrayList<SecurityGroupDTO>();
		StringBuffer query = new StringBuffer();
		try{
			query.append("Select SEQNO,SECURITY_GROUP,SECURITY_GROUP_DESC,MERIT_ADMIN,PERFORMANCE_MGR,BONUS_MGR,SUCCESSION_MGR,TEAMS,EMPLOYEES, ");
			query.append("SYSTEM_TABLES,SECURITY,MERIT_REPORTS,BONUS_REPORTS,SUCCESSION_REPORTS,EMPLOYEE_REPORTS,SYSTEM_REPORTS, ");
			query.append("DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy "); 
			query.append("from SECURITY_GROUP  B left join EMP_PERSONAL E on B.updated_by = EMP_ID ");
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(setSecurityGroupDetails(rs));
			}
		}catch(Exception e){
			logger.error("Exception in getSecurityGroupDetails "+e.getMessage());
		}
		return list;
	}
	public SecurityGroupDTO getSecurityGroupDetailsId(String id,String ... securityGroup){
		logger.info("in getSecurityGroupDetailsId == ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getSecurityGroupDetailsById( conn, ps, rs,id,securityGroup);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getSecurityGroupDetailsId"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return new SecurityGroupDTO();
	}
	public SecurityGroupDTO getSecurityGroupDetailsById(Connection conn,PreparedStatement ps,ResultSet rs,String id,String ... securityGroup){
		SecurityGroupDTO dto = new SecurityGroupDTO();
		StringBuffer query = new StringBuffer();
		try{
			query.append("Select SEQNO,SECURITY_GROUP,SECURITY_GROUP_DESC,MERIT_ADMIN,PERFORMANCE_MGR,BONUS_MGR,SUCCESSION_MGR,TEAMS,EMPLOYEES, ");
			query.append("SYSTEM_TABLES,SECURITY,MERIT_REPORTS,BONUS_REPORTS,SUCCESSION_REPORTS,EMPLOYEE_REPORTS,SYSTEM_REPORTS, ");
			query.append("DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy "); 
			if (securityGroup.length > 0){
				query.append("from SECURITY_GROUP  B left join EMP_PERSONAL E on B.updated_by = EMP_ID where SECURITY_GROUP=?");
				id = securityGroup[0];
			}else{
				query.append("from SECURITY_GROUP  B left join EMP_PERSONAL E on B.updated_by = EMP_ID where SEQNO=?");
			}
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				return setSecurityGroupDetails(rs);
			}
		}catch(Exception e){
			logger.error("Exception in getSecurityGroupDetailsById "+e.getMessage());
		}
		return dto;
	}
	private SecurityGroupDTO setSecurityGroupDetails(ResultSet rs) throws java.sql.SQLException{
		SecurityGroupDTO dto = new SecurityGroupDTO();
		dto.setSeqNo(rs.getString("SEQNO"));
		dto.setName(rs.getString("SECURITY_GROUP"));
		dto.setDescription(rs.getString("SECURITY_GROUP_DESC"));
		dto.setMeritAdmin(rs.getBoolean("MERIT_ADMIN"));
		dto.setPerfManager(rs.getBoolean("PERFORMANCE_MGR"));
		dto.setBonusManager(rs.getBoolean("BONUS_MGR"));
		dto.setSuccessionManager(rs.getBoolean("SUCCESSION_MGR"));
		dto.setTeams(rs.getBoolean("TEAMS"));
		dto.setEmployees(rs.getBoolean("EMPLOYEES"));
		dto.setSystemTables(rs.getBoolean("SYSTEM_TABLES"));
		dto.setSecurity(rs.getBoolean("SECURITY"));
		dto.setMeritReports(rs.getBoolean("MERIT_REPORTS"));
		dto.setBonusReports(rs.getBoolean("BONUS_REPORTS"));
		dto.setSuccessionReports(rs.getBoolean("SUCCESSION_REPORTS"));
		dto.setEmployeeReports(rs.getBoolean("EMPLOYEE_REPORTS"));
		dto.setSystemReports(rs.getBoolean("SYSTEM_REPORTS"));
		dto.setUpdatedDate(rs.getString("updated_date"));
		dto.setUpdatedBy(rs.getString("updatedBy"));
		return dto;
	}
}
