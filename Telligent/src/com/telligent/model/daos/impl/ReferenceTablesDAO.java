package com.telligent.model.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.telligent.common.user.TelligentUser;
import com.telligent.core.system.annotation.SpringBean;
import com.telligent.model.db.AbstractDBManager;
import com.telligent.model.dtos.EmployeeCommonDTO;
import com.telligent.model.dtos.JobGradeDTO;
import com.telligent.model.dtos.MapDTO;
import com.telligent.model.dtos.MeritAdminGuidelinesDTO;
import com.telligent.model.dtos.PayGroupDTO;
import com.telligent.model.dtos.PrimaryJobDTO;
import com.telligent.model.dtos.TeamDTO;
import com.telligent.util.ParseNumberUtility;

/**
 * @author spothu
 *
 */
@SpringBean
public class ReferenceTablesDAO extends AbstractDBManager{

	public final Logger logger = Logger.getLogger(ReferenceTablesDAO.class);
	
	private String save(Connection conn,PreparedStatement ps,MapDTO dto,TelligentUser user,String table){
		try {
			String query = "insert into "+table+" (value,description,isActive,updated_date,updated_by) values(?,?,?,sysdate(),?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getValue());
			ps.setString(2, dto.getDescription());
			ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
			ps.setString(4, user.getEmployeeId());
			int i = ps.executeUpdate();
			if(i>0)
				return "success:;Details Saved Successfully";
			else
				return "error:;Details not saved";
		} catch (SQLException ex) {
			logger.error("Exception in save");
			logger.info("Exception in save"+ex.getMessage());
			return "error:;"+ex.getMessage();
		}
	}
	private String update(Connection conn,PreparedStatement ps,MapDTO dto,TelligentUser user,String tableName,String id){
		try {
			String query = "update "+tableName+" set value=?,description=?,isActive=?,updated_date=sysdate(),updated_by=? where id=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getValue());
			ps.setString(2, dto.getDescription());
			ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
			ps.setString(4, user.getEmployeeId());
			ps.setString(5, id);
			int i = ps.executeUpdate();
			if(i>0)
				return "success:;Details updated Successfully";
			else
				return "error:;Details not updated";
		} catch (SQLException ex) {
			logger.error("Exception in save");
			logger.info("Exception in save"+ex.getMessage());
			return "error:;"+ex.getMessage();
		}
	}
	private String updateMeritAdminGuidelines(Connection conn,PreparedStatement ps,MeritAdminGuidelinesDTO dto,TelligentUser user,String tableName,String id){
		try {
			String query = "update "+tableName+" set value=?,description=?,isActive=?,updated_date=sysdate(),updated_by=? where id=?";
			ps = conn.prepareStatement(query);
			/*ps.setString(1, dto.getValue());
			ps.setString(2, dto.getDescription());
			ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
			 */ps.setString(4, user.getEmployeeId());
			 ps.setString(5, id);
			 int i = ps.executeUpdate();
			 if(i>0)
				 return "success:;Details updated Successfully";
			 else
				 return "error:;Details not updated";
		} catch (SQLException ex) {
			logger.error("Exception in save");
			logger.info("Exception in save"+ex.getMessage());
			return "error:;"+ex.getMessage();
		}
	}

	public boolean checkRecordExistence(Connection conn, PreparedStatement ps,ResultSet rs,String tableName,String value){
		try {
			ps = conn.prepareStatement("select value from "+tableName+" where upper(value)=?");
			ps.setString(1, value.toUpperCase());
			rs = ps.executeQuery();
			if(rs.next())
				return true;
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in checkRecordExistence"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return false;
	}
	public ArrayList<MapDTO> getDetails(String tableName){
		logger.info("in getDetails == "+tableName);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getDetails( conn, ps, rs, tableName);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getDetails"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return new ArrayList<MapDTO>();
	}
	public ArrayList<MapDTO> getDetails(Connection conn,PreparedStatement ps,ResultSet rs,String tableName){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		try{
			ps = conn.prepareStatement("select id,value,description,if(isActive,'true','false') isActive,DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy from "+tableName+" B left join EMP_PERSONAL E on B.updated_by = EMP_ID  order by B.value ");
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setUpdatedDate(rs.getString("updated_date"));
				dto.setUpdatedBy(rs.getString("updatedBy"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getDetails "+e.getMessage());
		}
		return list;
	}
	public MapDTO getDetailsById(String tableName,String id){
		logger.info("in getDetailsById == "+tableName);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getDetailsById( conn, ps, rs, tableName,id);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getDetailsById"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return new MapDTO();
	}
	public MapDTO getDetailsById(Connection conn,PreparedStatement ps,ResultSet rs,String tableName,String id){
		MapDTO dto = new MapDTO();
		try{
			ps = conn.prepareStatement("select id,value,description,if(isActive,'true','false') isActive from "+tableName+" where id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setIsActive(rs.getString("isActive"));
			}
		}catch(Exception e){
			logger.error("Exception in getDetailsById "+e.getMessage());
		}
		return dto;
	}

	public String saveBaseRateFreq(MapDTO dto,TelligentUser user){
		logger.info("in saveBaseRateFreq");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String tablName = "Base_Rate_Frequency";
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){
				return update(conn, ps, dto, user, tablName, dto.getId());
			}else{
				if(!checkRecordExistence(conn, ps, rs,tablName , dto.getValue())){
					return save(conn,ps,dto,user,tablName);
				}else{
					return "error:;Base Rate Frequency already exists";
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in saveBaseRateFreq"+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}



	public String saveData(MapDTO dto,TelligentUser user,String tablName,String errorMsg){
		logger.info("in saveBonusPlan");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//String tablName = "Bonus_Plan";
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){

				return update(conn, ps, dto, user, tablName, dto.getId());
			}else{
				if(!checkRecordExistence(conn, ps, rs,tablName , dto.getValue())){
					return save(conn,ps,dto,user,tablName);
				}else{
					return "error:;"+errorMsg+" already exists";
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in"+errorMsg+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}

	public String saveMeritAdminGuidelines(MeritAdminGuidelinesDTO dto,TelligentUser user,String tablName,String errorMsg){
		logger.info("in saveMeritAdminGuidelines");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){
				String query = "update "+tablName+" set entity=?,merit_period=?,overallperformanceRating=?,aggregate_expected=?,first_quartile_Max=?,second_quartile_Max=?,third_quartile_Max=?,fourth_quartile_Max=?,first_quartile_Min=?,second_quartile_Min=?,third_quartile_Min=?,fourth_quartile_Min=? where entity=? and merit_period=? and overallperformanceRating=?";
				ps = conn.prepareStatement(query);
				setMeritAdminGuideLinesPS(ps, dto,"update");
				int i = ps.executeUpdate();
				if(i>0)
					return "success:;Details Saved Successfully";
				else
					return "error:;Details not saved";
			}else{
				String query = "insert into "+tablName+" (entity,merit_period,overallperformanceRating,aggregate_expected,first_quartile_Max,second_quartile_Max,third_quartile_Max,fourth_quartile_Max,first_quartile_Min,second_quartile_Min,third_quartile_Min,fourth_quartile_Min) values(?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(query);
				setMeritAdminGuideLinesPS(ps, dto,"save");
				int i = ps.executeUpdate();
				if(i>0)
					return "success:;Details Saved Successfully";
				else
					return "error:;Details not saved";
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in"+errorMsg+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}
	public void setMeritAdminGuideLinesPS(PreparedStatement ps,MeritAdminGuidelinesDTO dto,String operation) throws SQLException{
		ps.setString(1, dto.getPayEntity());
		ps.setString(2, dto.getMeritPeriod());
		ps.setString(3, dto.getPerformanceRating());
		ps.setString(4, dto.getTargetPopulationPct());
		ps.setString(5, dto.getFirstQuartileMax());
		ps.setString(6, dto.getSecondQuartileMax());
		ps.setString(7, dto.getThirdQuartileMax());
		ps.setString(8, dto.getFourthQuartileMax());
		ps.setString(9, dto.getFirstQuartileMin());
		ps.setString(10, dto.getSecondQuartileMin());
		ps.setString(11, dto.getThirdQuartileMin());
		ps.setString(12, dto.getFourthQuartileMin());
		if(operation.equalsIgnoreCase("update")){
			ps.setString(13, dto.getPayEntity());
			ps.setString(14, dto.getMeritPeriod());
			ps.setString(15, dto.getPerformanceRating());
		}
	}


	public String savePrimaryJobData(PrimaryJobDTO dto,TelligentUser user){
		logger.info("in savePrimaryJobData");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){
				query = "update primary_job set value=?,description=?,isActive=?,minimum=?,midpoint=?,maximum=?,updated_date=sysdate(),updated_by=? where id=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, dto.getValue());
				ps.setString(2, dto.getDescription());
				ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
				ps.setString(4, dto.getMinimum());
				ps.setString(5, dto.getMidpoint());
				ps.setString(6, dto.getMaximum());
				ps.setString(7, user.getEmployeeId());
				ps.setString(8, dto.getId());
				int i = ps.executeUpdate();
				if(i>0)
					return "success:;Details updated Successfully";
				else
					return "error:;Details not updated";
			}else{
				if(!checkRecordExistence(conn, ps, rs,"primary_job" , dto.getValue())){
					// Save new record
					query = "insert into primary_job (value,description,isActive,minimum,midpoint,maximum,updated_date,updated_by) values(?,?,?,?,?,?,sysdate(),?)";
					ps = conn.prepareStatement(query);
					ps.setString(1, dto.getValue());
					ps.setString(2, dto.getDescription());
					ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
					ps.setString(4, dto.getMinimum());
					ps.setString(5, dto.getMidpoint());
					ps.setString(6, dto.getMaximum());
					ps.setString(7, user.getEmployeeId());
					int i = ps.executeUpdate();
					if(i>0)
						return "success:;Details Saved Successfully";
					else
						return "error:;Details not saved";
				}else{
					return "error:;Primary Job already exists";
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in savePrimaryJobData"+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}
	public ArrayList<PrimaryJobDTO> getPrimaryJobDetails(){
		ArrayList<PrimaryJobDTO> list = new ArrayList<PrimaryJobDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select id,value,description,if(isActive,'true','false') isActive,minimum,midpoint,maximum,DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy from primary_job B left join EMP_PERSONAL E on B.updated_by = EMP_ID  order by B.value ");
			rs = ps.executeQuery();
			while(rs.next()){
				PrimaryJobDTO dto = new PrimaryJobDTO();
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setUpdatedDate(rs.getString("updated_date"));
				dto.setUpdatedBy(rs.getString("updatedBy"));
				dto.setMinimum(rs.getString("minimum"));
				dto.setMaximum(rs.getString("maximum"));
				dto.setMidpoint(rs.getString("midpoint"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getPrimaryJobDetails "+e.getMessage());
		}finally{
			this.closeAll(conn,ps,rs);
		}
		return list;
	}
	public PrimaryJobDTO getPrimaryJobDetailsById(String id){
		PrimaryJobDTO dto = new PrimaryJobDTO();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select id,value,description,if(isActive,'true','false') isActive,minimum,midpoint,maximum from primary_job where id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setMinimum(rs.getString("minimum"));
				dto.setMaximum(rs.getString("maximum"));
				dto.setMidpoint(rs.getString("midpoint"));
			}
		}catch(Exception e){
			logger.error("Exception in getPrimaryJobDetailsById "+e.getMessage());
		}finally{
			this.closeAll(conn,ps,rs);
		}
		return dto;
	}

	public String saveCityData(MapDTO dto,TelligentUser user){
		logger.info("in saveCityData");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){
				query = "update City set value=?,description=?,isActive=?,state_id=?,updated_date=sysdate(),updated_by=? where id=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, dto.getValue());
				ps.setString(2, dto.getDescription());
				ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
				ps.setString(4, dto.getRefId());
				ps.setString(5, user.getEmployeeId());
				ps.setString(6, dto.getId());
				int i = ps.executeUpdate();
				if(i>0)
					return "success:;Details updated Successfully";
				else
					return "error:;Details not updated";
			}else{
				if(!checkRecordExistence(conn, ps, rs,"City" , dto.getValue())){
					// Save new record
					query = "insert into City (value,description,isActive,state_id,updated_date,updated_by) values(?,?,?,?,sysdate(),?)";
					ps = conn.prepareStatement(query);
					ps.setString(1, dto.getValue());
					ps.setString(2, dto.getDescription());
					ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
					ps.setString(4, dto.getRefId());
					ps.setString(5, user.getEmployeeId());
					int i = ps.executeUpdate();
					if(i>0)
						return "success:;Details Saved Successfully";
					else
						return "error:;Details not saved";
				}else{
					return "error:;City already exists";
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in saveCityData"+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}
	public ArrayList<MapDTO> getCityDetails(){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select B.id id,B.value value,B.description description,if(B.isActive,'true','false') isActive,s.value state_id,DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy from City B left join EMP_PERSONAL E on B.updated_by = EMP_ID LEFT JOIN State s on s.id=B.state_id order by B.value ");
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setUpdatedDate(rs.getString("updated_date"));
				dto.setUpdatedBy(rs.getString("updatedBy"));
				dto.setRefId(rs.getString("state_id"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getCityDetails "+e.getMessage());
		}finally{
			this.closeAll(conn,ps,rs);
		}
		return list;
	}
	public MapDTO getCityDetailsById(String id){
		MapDTO dto = new MapDTO();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select id,value,description,if(isActive,'true','false') isActive,state_id from City where id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setRefId(rs.getString("state_id"));
			}
		}catch(Exception e){
			logger.error("Exception in getCityDetailsById "+e.getMessage());
		}finally{
			this.closeAll(conn,ps,rs);
		}
		return dto;
	}
	/**
	 *  Generic method to get id and value of lookup tables
	 * @return HashMap
	 */
	public HashMap<String, ArrayList<MeritAdminGuidelinesDTO>> getPayEntityLookup(){
		logger.info("in getPayEntityLookup");
		HashMap<String, ArrayList<MeritAdminGuidelinesDTO>> map = new HashMap<String, ArrayList<MeritAdminGuidelinesDTO>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			map.put("pay_entity", getLookup(conn, ps, rs, "pay_entity"));

		}catch (Exception ex) {
			logger.info("Excpetion in getPayEntityLookup "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return map;
	}
	public ArrayList<MeritAdminGuidelinesDTO> getLookup(String tableName){
		ArrayList<MeritAdminGuidelinesDTO> list = new ArrayList<MeritAdminGuidelinesDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			return getLookup(conn, ps, rs, tableName);
		}catch(Exception e){
			logger.error("Exception in getLookup "+e.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public ArrayList<MeritAdminGuidelinesDTO> getLookup(Connection conn,PreparedStatement ps,ResultSet rs,String tableName){
		ArrayList<MeritAdminGuidelinesDTO> list = new ArrayList<MeritAdminGuidelinesDTO>();
		try{
			ps = conn.prepareStatement("select id,value from "+tableName+" where isActive=1");
			rs = ps.executeQuery();
			while(rs.next()){
				MeritAdminGuidelinesDTO dto = new MeritAdminGuidelinesDTO();
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getLookup "+e.getMessage());
		}
		return list;
	}

	//merit_admin_guidelines
	public ArrayList<MeritAdminGuidelinesDTO> getMeritAdminDetails(String tableName){
		logger.info("in getMeritAdminDetails == "+tableName);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getMeritAdminDetails( conn, ps, rs, tableName);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getDetails"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return new ArrayList<MeritAdminGuidelinesDTO>();
	}
	public ArrayList<MeritAdminGuidelinesDTO> getMeritAdminDetails(Connection conn,PreparedStatement ps,ResultSet rs,String tableName){
		ArrayList<MeritAdminGuidelinesDTO> list = new ArrayList<MeritAdminGuidelinesDTO>();
		try{
			ps = conn.prepareStatement("select entity,value,merit_period,overallperformanceRating,aggregate_expected,first_Quartile_Max,first_Quartile_Min,second_Quartile_Max,second_Quartile_Min,third_Quartile_Max,third_Quartile_Min,fourth_Quartile_Max,fourth_Quartile_Min from "+tableName+" ,pay_entity where entity = id");
			rs = ps.executeQuery();
			while(rs.next()){

				MeritAdminGuidelinesDTO dto = new MeritAdminGuidelinesDTO();
				dto.setPayEntity(rs.getString("entity"));
				dto.setPayEntityLabel(rs.getString("value"));
				dto.setMeritPeriod(rs.getString("merit_period"));
				dto.setPerformanceRating(rs.getString("overallperformanceRating"));
				dto.setTargetPopulationPct(rs.getString("aggregate_expected"));
				dto.setFirstQuartileMax(ParseNumberUtility.parseNumber(rs.getString("first_Quartile_Max")));
				dto.setFirstQuartileMin(ParseNumberUtility.parseNumber(rs.getString("first_Quartile_Min")));
				dto.setSecondQuartileMax(ParseNumberUtility.parseNumber(rs.getString("second_Quartile_Max")));
				dto.setSecondQuartileMin(ParseNumberUtility.parseNumber(rs.getString("second_Quartile_Min")));
				dto.setThirdQuartileMax(ParseNumberUtility.parseNumber(rs.getString("third_Quartile_Max")));
				dto.setThirdQuartileMin(ParseNumberUtility.parseNumber(rs.getString("third_Quartile_Min")));
				dto.setFourthQuartileMax(ParseNumberUtility.parseNumber(rs.getString("fourth_Quartile_Max")));
				dto.setFourthQuartileMin(ParseNumberUtility.parseNumber(rs.getString("fourth_Quartile_Min")));

				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getDetails "+e.getMessage());
		}
		return list;
	}

	public MeritAdminGuidelinesDTO getMeritAdminGuidelinesDetailsById(String tableName,String payEntity,String meritPeriod,String performanceRating){
		logger.info("in getMeritAdminGuidelinesDetailsById == "+tableName);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getMeritAdminGuidelinesDetailsById( conn, ps, rs, tableName,payEntity,meritPeriod,performanceRating);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getMeritAdminGuidelinesDetailsById"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return new MeritAdminGuidelinesDTO();
	}
	public MeritAdminGuidelinesDTO getMeritAdminGuidelinesDetailsById(Connection conn,PreparedStatement ps,ResultSet rs,String tableName,String payEntity,String meritPeriod,String performanceRating){
		MeritAdminGuidelinesDTO dto = new MeritAdminGuidelinesDTO();
		try{
			ps = conn.prepareStatement("select entity,merit_period,overallperformanceRating,aggregate_expected,first_Quartile_Max,first_Quartile_Min,second_Quartile_Max,second_Quartile_Min,third_Quartile_Max,third_Quartile_Min,fourth_Quartile_Max,fourth_Quartile_Min from "+tableName+" where merit_period = ? and entity=? and overallperformanceRating=?");
			ps.setString(1,meritPeriod);
			ps.setString(2, payEntity);
			ps.setString(3, performanceRating);
			rs = ps.executeQuery();
			if(rs.next()){
				dto.setId(rs.getString("merit_period"));
				dto.setPayEntity(rs.getString("entity"));
				dto.setMeritPeriod(rs.getString("merit_period"));
				dto.setPerformanceRating(rs.getString("overallperformanceRating"));
				dto.setTargetPopulationPct(rs.getString("aggregate_expected"));
				dto.setFirstQuartileMax(ParseNumberUtility.parseNumber(rs.getString("first_Quartile_Max")));
				dto.setFirstQuartileMin(ParseNumberUtility.parseNumber(rs.getString("first_Quartile_Min")));
				dto.setSecondQuartileMax(ParseNumberUtility.parseNumber(rs.getString("second_Quartile_Max")));
				dto.setSecondQuartileMin(ParseNumberUtility.parseNumber(rs.getString("second_Quartile_Min")));
				dto.setThirdQuartileMax(ParseNumberUtility.parseNumber(rs.getString("third_Quartile_Max")));
				dto.setThirdQuartileMin(ParseNumberUtility.parseNumber(rs.getString("third_Quartile_Min")));
				dto.setFourthQuartileMax(ParseNumberUtility.parseNumber(rs.getString("fourth_Quartile_Max")));
				dto.setFourthQuartileMin(ParseNumberUtility.parseNumber(rs.getString("fourth_Quartile_Min")));
				logger.info("merit_period--------->"+dto.getMeritPeriod());
			}
		}catch(Exception e){
			logger.error("Exception in getDetailsById "+e.getMessage());
		}
		return dto;
	}
	public String getCurrentMeritPeriod(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement("select value from merit_period_update");
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getString("value");
			else
				return "";
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getCurrentMeritPeriod"+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}
	public String saveMeritPeriodUpdate(MapDTO dto,TelligentUser user){
		logger.info("in saveMeritPeriodUpdate");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			int count = 0;
			int i = 0;
			conn = this.getConnection();
			ps = conn.prepareStatement("select count(value) from merit_period_update");
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			ps.close();
			if(count  ==  0){
				ps = conn.prepareStatement("insert into merit_period_update (value,updated_date,updated_by) values (?,sysdate(),?)");
				ps.setString(1, dto.getValue());
				ps.setString(2, user.getEmployeeId());
				i = ps.executeUpdate();
				if(i>0)
					return "success:;Merit Period Updated";
				else
					return "success:;Merit Period not Updated";
			}else{
				ps = conn.prepareStatement("truncate table merit_period_update");
				i = ps.executeUpdate();
				ps.close();
				ps = conn.prepareStatement("insert into merit_period_update (value,updated_date,updated_by) values (?,sysdate(),?)");
				ps.setString(1, dto.getValue());
				ps.setString(2, user.getEmployeeId());
				i = ps.executeUpdate();
				if(i>0)
					return "success:;Merit Period Updated";
				else
					return "success:;Merit Period not Updated";
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in saveMeritPeriodUpdate"+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}

	public String saveTeam(TeamDTO dto,TelligentUser user){
		logger.info("in saveTeam");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){
				query.append("update team set team_name=?,description=?,team_parent=?,isActive=?,isDelete=?,updated_date=sysdate(),updated_by=?,");
				query.append("filter_1=?,filter_1_operator=?,filter_1_values=?,filter_1_conjuction=?, ");
				query.append("filter_2=?,filter_2_operator=?,filter_2_values=?,filter_2_conjuction=?, ");
				query.append("filter_3=?,filter_3_operator=?,filter_3_values=?,filter_3_conjuction=?, ");
				query.append("filter_4=?,filter_4_operator=?,filter_4_values=?,filter_4_conjuction=?, ");
				query.append("filter_5=?,filter_5_operator=?,filter_5_values=?,filter_5_conjuction=? ");
				query.append("where team_id=?");
				ps = conn.prepareStatement(query.toString());
				ps.setString(1, dto.getTeamName());
				ps.setString(2, dto.getDescription());
				ps.setString(3, dto.getTeamParent());
				ps.setBoolean(4, dto.isActive());
				ps.setBoolean(5, dto.isDelete());
				ps.setString(6, user.getEmployeeId());
				ps.setString(7, dto.getField1());
				ps.setString(8, dto.getOperator1());
				ps.setString(9, dto.getValue1());
				ps.setString(10, dto.getConjuction1());
				ps.setString(11, dto.getField2());
				ps.setString(12, dto.getOperator2());
				ps.setString(13, dto.getValue2());
				ps.setString(14, dto.getConjuction2());
				ps.setString(15, dto.getField3());
				ps.setString(16, dto.getOperator3());
				ps.setString(17, dto.getValue3());
				ps.setString(18, dto.getConjuction3());
				ps.setString(19, dto.getField4());
				ps.setString(20, dto.getOperator4());
				ps.setString(21, dto.getValue4());
				ps.setString(22, dto.getConjuction4());
				ps.setString(23, dto.getField5());
				ps.setString(24, dto.getOperator5());
				ps.setString(25, dto.getValue5());
				ps.setString(26, dto.getConjuction5());
				ps.setString(27, dto.getTeamId());
				int i = ps.executeUpdate();
				if(i>0){
					updateFilterTeamsEmployees(conn,ps,rs,dto,user);
					return "success:;Team Details Saved Successfully";					
				}else
					return "error:;Team Details not saved";
			}else{
				if(!checkTeamRecordExistence(conn, ps, rs, dto.getTeamName())){
					query.append("insert into team (team_name,description,team_parent,isActive,isDelete,updated_date,updated_by,");
					query.append("filter_1,filter_1_operator,filter_1_values,filter_1_conjuction, ");
					query.append("filter_2,filter_2_operator,filter_2_values,filter_2_conjuction, ");
					query.append("filter_3,filter_3_operator,filter_3_values,filter_3_conjuction, ");
					query.append("filter_4,filter_4_operator,filter_4_values,filter_4_conjuction, ");
					query.append("filter_5,filter_5_operator,filter_5_values,filter_5_conjuction) ");
					query.append("values(?,?,?,?,?,sysdate(),?,");
					query.append("?,?,?,?,");
					query.append("?,?,?,?,");
					query.append("?,?,?,?,");
					query.append("?,?,?,?,");
					query.append("?,?,?,?)");
					ps = conn.prepareStatement(query.toString());
					ps.setString(1, dto.getTeamName());
					ps.setString(2, dto.getDescription());
					ps.setString(3, dto.getTeamParent());
					ps.setBoolean(4, dto.isActive());
					ps.setBoolean(5, dto.isDelete());
					ps.setString(6, user.getEmployeeId());
					ps.setString(7, dto.getField1());
					ps.setString(8, dto.getOperator1());
					ps.setString(9, dto.getValue1());
					ps.setString(10, dto.getConjuction1());
					ps.setString(11, dto.getField2());
					ps.setString(12, dto.getOperator2());
					ps.setString(13, dto.getValue2());
					ps.setString(14, dto.getConjuction2());
					ps.setString(15, dto.getField3());
					ps.setString(16, dto.getOperator3());
					ps.setString(17, dto.getValue3());
					ps.setString(18, dto.getConjuction3());
					ps.setString(19, dto.getField4());
					ps.setString(20, dto.getOperator4());
					ps.setString(21, dto.getValue4());
					ps.setString(22, dto.getConjuction4());
					ps.setString(23, dto.getField5());
					ps.setString(24, dto.getOperator5());
					ps.setString(25, dto.getValue5());
					ps.setString(26, dto.getConjuction5());
					int i = ps.executeUpdate();
					if(i>0){
						ps = conn.prepareStatement("select team_id,team_name from team where upper(team_name) = ? ");
						ps.setString(1, dto.getTeamName().toUpperCase());
						rs = ps.executeQuery();
						if(rs.next())
							dto.setTeamId(rs.getString("team_id"));
						updateFilterTeamsEmployees(conn,ps,rs,dto,user);
						return "success:;Team Details Saved Successfully";					
					}else
						return "error:;Team Details not saved";
				}else{
					return "error:;Team Name already exists";
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in saveTeam"+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}
	public void updateFilterTeamsEmployees(Connection conn, PreparedStatement ps,ResultSet rs,TeamDTO dto,TelligentUser user){
		try{
			int[] i = new int[1];
			ArrayList<EmployeeCommonDTO> employeeDTO = viewListOfEmployeesForSelectedFilter(conn, ps, rs, dto);
			for (Iterator<EmployeeCommonDTO> iterator = employeeDTO.iterator(); iterator.hasNext();) {
				EmployeeCommonDTO employeeCommonDTO = (EmployeeCommonDTO) iterator.next();
				ps = conn.prepareStatement("select emp_id from EMP_POSITION_DATA where emp_id=?");
				ps.setString(1, employeeCommonDTO.getEmployeeId());
				rs = ps.executeQuery();
				// update in Emp position data - start
				if(rs.next()){
					ps.close();
					ps = conn.prepareStatement("update EMP_POSITION_DATA set TEAM=?,DATE_UPDATED=sysdate(),UPDATED_BY=? where EMP_ID=?");
					ps.setString(1, dto.getTeamId());
					ps.setString(2, user.getEmployeeId());
					ps.setString(3, employeeCommonDTO.getEmployeeId());
					ps.addBatch();
				}else{
					ps.close();
					ps = conn.prepareStatement("insert into EMP_POSITION_DATA(TEAM,DATE_UPDATED,UPDATED_BY,EMP_ID) values (?,sysdate(),?,?)");
					ps.setString(1, dto.getTeamId());
					ps.setString(2, user.getEmployeeId());
					ps.setString(3, employeeCommonDTO.getEmployeeId());
					ps.addBatch();
					
					ps.close();
					ps = conn.prepareStatement("insert into merit_transaction(team,updated_date,updated_by,emp_id) values (?,sysdate(),?,?)");
					ps.setString(1, dto.getTeamId());
					ps.setString(2, user.getEmployeeId());
					ps.setString(3, employeeCommonDTO.getEmployeeId());
					ps.addBatch();
				}
				// update in Emp position data - End
			}
			i = ps.executeBatch();
			// update in Merit Admin Transaction- start
			if(i.length>0){
				for (Iterator<EmployeeCommonDTO> iterator = employeeDTO.iterator(); iterator.hasNext();) {
					EmployeeCommonDTO employeeCommonDTO = (EmployeeCommonDTO) iterator.next();
					ps.close();
					ps = conn.prepareStatement("update merit_transaction set team=?,updated_date=sysdate(),updated_by=? where emp_id=?");
					ps.setString(1, dto.getTeamId());
					ps.setString(2, user.getEmployeeId());
					ps.setString(3, employeeCommonDTO.getEmployeeId());
					ps.addBatch();
				}
				ps.executeBatch();
			}
			// update in Merit Admin Transaction- End
		}catch(Exception e){
			logger.error("Exception in updateFilterTeamsEmployees :: "+e.getMessage());
		}
	}
	public boolean checkTeamRecordExistence(Connection conn, PreparedStatement ps,ResultSet rs,String value){
		try {
			ps = conn.prepareStatement("select team_name from team where upper(team_name)=?");
			ps.setString(1, value.toUpperCase());
			rs = ps.executeQuery();
			if(rs.next())
				return true;
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in checkTeamRecordExistence"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return false;
	}
	public ArrayList<TeamDTO> searchTeamName(String param){
		logger.info("in searchTeamName");
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
			logger.info("Excpetion in searchTeamName "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	
	public ArrayList<TeamDTO> searchTeamNameByParent(String param){
		logger.info("in searchTeamName");
		ArrayList<TeamDTO> list = new ArrayList<TeamDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			//query.append("select team_id,team_name from team where upper(team_name) like '"+param.toUpperCase()+"%'");
			
			query.append("select team_id,team_name from team where team_parent in  (select distinct(team_id) from team where upper(team_name) like '"+param.toUpperCase()+"%')");
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
			logger.info("Excpetion in searchTeamName "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public TeamDTO getTeamDetailsById(String id){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TeamDTO dto = new TeamDTO();
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select team_id,team_name,team_parent,description,if(isActive,'true','false') isActive,if(isDelete,'true','false') isDelete,filter_1,filter_1_operator,filter_1_values,filter_1_conjuction,filter_2,filter_2_operator,filter_2_values,filter_2_conjuction,filter_3,filter_3_operator,filter_3_values,filter_3_conjuction,filter_4,filter_4_operator,filter_4_values,filter_4_conjuction,filter_5,filter_5_operator,filter_5_values,filter_5_conjuction,DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy from team  B left join EMP_PERSONAL E on B.updated_by = EMP_ID  where team_id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				return setTeamDetails(rs);
			}
		}catch(Exception e){
			logger.error("Exception in getTeamDetailsById "+e.getMessage());
		}finally {
			this.closeAll(conn, ps, rs);
		}
		return dto;
	}
	public ArrayList<TeamDTO> getTeamDetails(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TeamDTO> list  = new ArrayList<TeamDTO>();
		HashMap<String, String> map = new HashMap<String, String>();
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select team_id,team_name from team  where isActive=1");
			rs = ps.executeQuery();
			while(rs.next())
				map.put(rs.getString("team_id"), rs.getString("team_name"));
			ps.close();rs.close();
			ps = conn.prepareStatement("select team_id,team_name,team_parent,description,if(isActive,'true','false') isActive,if(isDelete,'true','false') isDelete,filter_1,filter_1_operator,filter_1_values,filter_1_conjuction,filter_2,filter_2_operator,filter_2_values,filter_2_conjuction,filter_3,filter_3_operator,filter_3_values,filter_3_conjuction,filter_4,filter_4_operator,filter_4_values,filter_4_conjuction,filter_5,filter_5_operator,filter_5_values,filter_5_conjuction,DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy from team  B left join EMP_PERSONAL E on B.updated_by = EMP_ID  order by team_name");
			rs = ps.executeQuery();
			while(rs.next()){
				TeamDTO dto = setTeamDetails(rs);
				dto.setTeamParent(map.get(dto.getTeamParent()));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getTeamDetails "+e.getMessage());
		}finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	private TeamDTO setTeamDetails(ResultSet rs) throws SQLException{
		TeamDTO dto = new TeamDTO();
		dto.setTeamId(rs.getString("team_id"));
		dto.setTeamName(rs.getString("team_name"));
		dto.setTeamParent(rs.getString("team_parent"));
		dto.setDescription(rs.getString("description"));
		dto.setActive(rs.getBoolean("isActive"));;
		dto.setDelete(rs.getBoolean("isDelete"));
		dto.setField1(rs.getString("filter_1"));
		dto.setOperator1(rs.getString("filter_1_operator"));
		dto.setValue1(rs.getString("filter_1_values"));
		dto.setConjuction1(rs.getString("filter_1_conjuction"));
		dto.setField2(rs.getString("filter_2"));
		dto.setOperator2(rs.getString("filter_2_operator"));
		dto.setValue2(rs.getString("filter_2_values"));
		dto.setConjuction2(rs.getString("filter_2_conjuction"));
		dto.setField3(rs.getString("filter_3"));
		dto.setOperator3(rs.getString("filter_3_operator"));
		dto.setValue3(rs.getString("filter_3_values"));
		dto.setConjuction3(rs.getString("filter_3_conjuction"));
		dto.setField4(rs.getString("filter_4"));
		dto.setOperator4(rs.getString("filter_4_operator"));
		dto.setValue4(rs.getString("filter_4_values"));
		dto.setConjuction4(rs.getString("filter_4_conjuction"));
		dto.setField5(rs.getString("filter_5"));
		dto.setOperator5(rs.getString("filter_5_operator"));
		dto.setValue5(rs.getString("filter_5_values"));
		dto.setConjuction5(rs.getString("filter_5_conjuction"));
		dto.setUpdatedDate(rs.getString("updated_date"));
		dto.setUpdatedBy(rs.getString("updatedBy"));
		return dto;
	}
	public ArrayList<TeamDTO> getTeamParentList(String param){
		logger.info("in getTeamParentList");
		ArrayList<TeamDTO> list = new ArrayList<TeamDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer();
		try{
			query.append("select team_id,team_name from team where isActive=1");
			if(param!=null && !"".equalsIgnoreCase(param))
				query.append(" and upper(team_id) !='"+param.toUpperCase()+"'");
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
			logger.info("Excpetion in getTeamParentList "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return list;
	}
	public ArrayList<EmployeeCommonDTO> viewListOfEmployeesForSelectedFilter(TeamDTO dto){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			return viewListOfEmployeesForSelectedFilter(conn, ps, rs, dto);
		}catch (Exception ex) {
			logger.info("Excpetion in viewListOfEmployeesForSelectedFilter "+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return null;
	}
	public ArrayList<EmployeeCommonDTO> viewListOfEmployeesForSelectedFilter(Connection conn,PreparedStatement ps,ResultSet rs, TeamDTO dto){
		StringBuffer query = new StringBuffer();
		ArrayList<EmployeeCommonDTO> list = new ArrayList<EmployeeCommonDTO>();
		try{
			query.append("select a.EMP_ID EMP_ID,L_NAME,M_NAME,F_NAME from (SELECT EMP_ID FROM EMP_POSITION_DATA  where ");
			if(null!=dto.getField1() && !"".equalsIgnoreCase(dto.getField1())){
				query.append(" upper(").append(dto.getField1()).append(") ").append(dto.getOperator1()).append(" ");
				if(dto.getOperator1().equalsIgnoreCase("="))
					query.append(getOrgId(conn, ps, rs, dto.getField1(), dto.getValue1()));
				else{
					if(dto.getValue1().contains(",")){
						query.append("(");
						String[] str = dto.getValue1().split(",");
						String temp = "";
						for (int i = 0; i < str.length; i++)
							temp = temp+getOrgId(conn, ps, rs, dto.getField1(), str[i])+",";
						if(temp.endsWith(","))
							query.append(temp.substring(0,temp.length()-1));
						else
							query.append(temp);
						query.append(")");
					}else{
						query.append("(").append(getOrgId(conn, ps, rs, dto.getField1(), dto.getValue1())).append(")");
					}
				}
				query.append(" ");
				if(null!=dto.getConjuction1() && !"".equalsIgnoreCase(dto.getConjuction1()))
					query.append(dto.getConjuction1());
			}
			query.append(" ");
			if(null!=dto.getField2() && !"".equalsIgnoreCase(dto.getField2())){
				query.append(" upper(").append(dto.getField2()).append(") ").append(dto.getOperator2()).append(" ");
				if(dto.getOperator1().equalsIgnoreCase("="))
					query.append(getOrgId(conn, ps, rs, dto.getField2(), dto.getValue2()));
				else{
					if(dto.getValue2().contains(",")){
						query.append("(");
						String[] str = dto.getValue2().split(",");
						String temp = "";
						for (int i = 0; i < str.length; i++)
							temp = temp+getOrgId(conn, ps, rs, dto.getField2(), str[i])+",";
						if(temp.endsWith(","))
							query.append(temp.substring(0,temp.length()-1));
						else
							query.append(temp);
						query.append(")");
					}else{
						query.append("(").append(getOrgId(conn, ps, rs, dto.getField2(), dto.getValue2())).append(")");
					}
				}
				query.append(" ");
				if(null!=dto.getConjuction2() && !"".equalsIgnoreCase(dto.getConjuction2()))
					query.append(dto.getConjuction2());
			}
			query.append(" ");
			if(null!=dto.getField3() && !"".equalsIgnoreCase(dto.getField3())){
				query.append(" upper(").append(dto.getField3()).append(") ").append(dto.getOperator3()).append(" ");
				if(dto.getOperator1().equalsIgnoreCase("="))
					query.append(getOrgId(conn, ps, rs, dto.getField3(), dto.getValue3()));
				else{
					if(dto.getValue3().contains(",")){
						query.append("(");
						String[] str = dto.getValue3().split(",");
						String temp = "";
						for (int i = 0; i < str.length; i++)
							temp = temp+getOrgId(conn, ps, rs, dto.getField3(), str[i])+",";
						if(temp.endsWith(","))
							query.append(temp.substring(0,temp.length()-1));
						else
							query.append(temp);
						query.append(")");
					}else{
						query.append("(").append(getOrgId(conn, ps, rs, dto.getField3(), dto.getValue3())).append(")");
					}
				}
				query.append(" ");
				if(null!=dto.getConjuction3() && !"".equalsIgnoreCase(dto.getConjuction3()))
					query.append(dto.getConjuction3());
			}
			query.append(" ");
			if(null!=dto.getField4() && !"".equalsIgnoreCase(dto.getField4())){
				query.append(" upper(").append(dto.getField4()).append(") ").append(dto.getOperator4()).append(" ");
				if(dto.getOperator1().equalsIgnoreCase("="))
					query.append(getOrgId(conn, ps, rs, dto.getField4(), dto.getValue4()));
				else{
					if(dto.getValue4().contains(",")){
						query.append("(");
						String[] str = dto.getValue4().split(",");
						String temp = "";
						for (int i = 0; i < str.length; i++)
							temp = temp+getOrgId(conn, ps, rs, dto.getField4(), str[i])+",";
						if(temp.endsWith(","))
							query.append(temp.substring(0,temp.length()-1));
						else
							query.append(temp);
						query.append(")");
					}else{
						query.append("(").append(getOrgId(conn, ps, rs, dto.getField4(), dto.getValue4())).append(")");
					}
				}
				query.append(" ");
				if(null!=dto.getConjuction4() && !"".equalsIgnoreCase(dto.getConjuction4()))
					query.append(dto.getConjuction4());
			}
			query.append(" ");
			if(null!=dto.getField5() && !"".equalsIgnoreCase(dto.getField5())){
				query.append(" upper(").append(dto.getField5()).append(") ").append(dto.getOperator5()).append(" ");
				if(dto.getOperator1().equalsIgnoreCase("="))
					query.append(getOrgId(conn, ps, rs, dto.getField5(), dto.getValue5()));
				else{
					if(dto.getValue5().contains(",")){
						query.append("(");
						String[] str = dto.getValue5().split(",");
						String temp = "";
						for (int i = 0; i < str.length; i++)
							temp = temp+getOrgId(conn, ps, rs, dto.getField5(), str[i])+",";
						if(temp.endsWith(","))
							query.append(temp.substring(0,temp.length()-1));
						else
							query.append(temp);
						query.append(")");
					}else{
						query.append("(").append(getOrgId(conn, ps, rs, dto.getField5(), dto.getValue5())).append(")");
					}
				}
				query.append(" ");
				if(null!=dto.getConjuction5() && !"".equalsIgnoreCase(dto.getConjuction5()))
					query.append(dto.getConjuction5());
			}
			query.append(" ");
			query.append(") a,EMP_PERSONAL e where a.EMP_ID=e.EMP_ID group by a.EMP_ID");
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				EmployeeCommonDTO dto1 = new EmployeeCommonDTO();
				dto1.setEmployeeId(rs.getString("EMP_ID"));
				dto1.setLastName(rs.getString("L_NAME"));
				dto1.setMiddleName(rs.getString("M_NAME"));
				dto1.setFirstName(rs.getString("F_NAME"));
				list.add(dto1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("Excpetion in viewListOfEmployeesForSelectedFilter "+e.getMessage());
		}
		return list;
	}
	public String getOrgId(Connection conn,PreparedStatement ps,ResultSet rs,String tableName,String value){
		try{
			tableName = tableName.replace("ORG_LEVEL_", "org");
			ps = conn.prepareStatement("select id,value from "+tableName+" where upper(value) = ? and isActive=1");
			ps.setString(1, value.toUpperCase());
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getString("id");
		}catch(Exception e){
			logger.error("Exception in getLookup "+e.getMessage());
		}
		return "''";
	}
	public String savePayGroup(PayGroupDTO dto,TelligentUser user){
		logger.info("in savePayGroup");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){
				query = "update pay_group set value=?,NO_OF_PAY_PERIODS=?,PAY_FREQUENCY=?,PAY_PERIOD_BEGIN=?,PAY_PERIOD_END=?,PAY_DATE=?,isActive=?,updated_date=sysdate(),updated_by=? where id=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, dto.getPayGroup());
				ps.setString(2, dto.getNoPayperiods());
				ps.setString(3, dto.getPayFrequency());
				ps.setDate(4, new java.sql.Date(new Date(dto.getPayPeriodBeginDate()).getTime()));
				ps.setDate(5, new java.sql.Date(new Date(dto.getPayPeriodEndDate()).getTime()));
				ps.setDate(6, new java.sql.Date(new Date(dto.getPayDate()).getTime()));
				ps.setBoolean(7, Boolean.parseBoolean(dto.getIsActive()));
				ps.setString(8, user.getEmployeeId());
				ps.setString(9, dto.getId());
				
				int i = ps.executeUpdate();
				if(i>0)
					return "success:;Details updated Successfully";
				else
					return "error:;Details not updated";
			}else{
				if(!checkPayGroup(conn, ps, rs,dto.getPayGroup())){
					// Save new record
					query = "insert into pay_group (value,NO_OF_PAY_PERIODS,PAY_FREQUENCY,PAY_PERIOD_BEGIN,PAY_PERIOD_END,PAY_DATE,isActive,updated_date,updated_by) values(?,?,?,?,?,?,?,sysdate(),?)";
					ps = conn.prepareStatement(query);
					ps.setString(1, dto.getPayGroup());
					ps.setString(2, dto.getNoPayperiods());
					ps.setString(3, dto.getPayFrequency());
					ps.setDate(4, new java.sql.Date(new Date(dto.getPayPeriodBeginDate()).getTime()));
					ps.setDate(5, new java.sql.Date(new Date(dto.getPayPeriodEndDate()).getTime()));
					ps.setDate(6, new java.sql.Date(new Date(dto.getPayDate()).getTime()));
					ps.setBoolean(7, Boolean.parseBoolean(dto.getIsActive()));
					ps.setString(8, user.getEmployeeId());
					int i = ps.executeUpdate();
					if(i>0)
						return "success:;Details Saved Successfully";
					else
						return "error:;Details not saved";
				}else{
					return "error:;Pay Group already exists";
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in savePayGroup"+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}
	
	public boolean checkPayGroup(Connection conn, PreparedStatement ps,ResultSet rs,String payGroup){
		try {
			ps = conn.prepareStatement("select pay_group from pay_group where upper(pay_group)=?");
			ps.setString(1, payGroup.toUpperCase());
			rs = ps.executeQuery();
			if(rs.next())
				return true;
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in checkPayGroup"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return false;
	}
	
	
	
	public ArrayList<PayGroupDTO> getPayGroupDetails(){
		logger.info("in getPayGroupDetails == ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getPayGroupDetails( conn, ps, rs);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getPayGroupDetails"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return new ArrayList<PayGroupDTO>();
	}
	public ArrayList<PayGroupDTO> getPayGroupDetails(Connection conn,PreparedStatement ps,ResultSet rs){
		ArrayList<PayGroupDTO> list = new ArrayList<PayGroupDTO>();
		try{
			ps = conn.prepareStatement("select B.id id,B.value value,NO_OF_PAY_PERIODS,f.value PAY_FREQUENCY,DATE_FORMAT(PAY_PERIOD_BEGIN,'%m/%d/%Y') PAY_PERIOD_BEGIN,DATE_FORMAT(PAY_PERIOD_END,'%m/%d/%Y') PAY_PERIOD_END,DATE_FORMAT(PAY_DATE,'%m/%d/%Y') PAY_DATE,if(B.isActive,'true','false') isActive,DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updated_by from pay_group B left join EMP_PERSONAL E on B.updated_by = EMP_ID  left join pay_frequency f on f.id = B.id order by B.value ");
			//ps = conn.prepareStatement("select id,value,description,if(isActive,'true','false') isActive,DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy from "+tableName+" B left join EMP_PERSONAL E on B.updated_by = EMP_ID  order by B.value ");
			rs = ps.executeQuery();
			while(rs.next()){
				PayGroupDTO dto = new PayGroupDTO();
				dto.setId(rs.getString("id"));
				dto.setPayGroup(rs.getString("value"));
				dto.setNoPayperiods(rs.getString("NO_OF_PAY_PERIODS"));
				dto.setPayFrequency(rs.getString("PAY_FREQUENCY"));
				dto.setPayPeriodBeginDate(rs.getString("PAY_PERIOD_BEGIN"));
				dto.setPayPeriodEndDate(rs.getString("PAY_PERIOD_END"));
				dto.setPayDate(rs.getString("PAY_DATE"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setUpdatedDate(rs.getString("updated_date"));
				dto.setUpdatedBy(rs.getString("updated_by"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getDetails "+e.getMessage());
		}
		return list;
	}
	
	public PayGroupDTO getPayGroupDetailsById(String id){
		logger.info("in getPayGroupDetailsById == ");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			return getPayGroupDetailsById( conn, ps, rs,id);
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in getPayGroupDetailsById"+ex.getMessage());
		} finally {
			this.closeAll(conn, ps, rs);
		}
		return new PayGroupDTO();
	}
	public PayGroupDTO getPayGroupDetailsById(Connection conn,PreparedStatement ps,ResultSet rs,String id){
		PayGroupDTO dto = new PayGroupDTO();
		try{
			ps = conn.prepareStatement("select id,value,NO_OF_PAY_PERIODS,PAY_FREQUENCY,PAY_PERIOD_BEGIN,PAY_PERIOD_END,PAY_DATE,if(isActive,'true','false') isActive,updated_date,updated_by from pay_group where ID='"+id+"'");
			rs = ps.executeQuery();
			while(rs.next()){
				dto.setId(rs.getString("id"));
				dto.setPayGroup(rs.getString("value"));
				dto.setNoPayperiods(rs.getString("NO_OF_PAY_PERIODS"));
				dto.setPayFrequency(rs.getString("PAY_FREQUENCY"));
				dto.setPayPeriodBeginDate(rs.getString("PAY_PERIOD_BEGIN"));
				dto.setPayPeriodEndDate(rs.getString("PAY_PERIOD_END"));
				dto.setPayDate(rs.getString("PAY_DATE"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setUpdatedDate(rs.getString("updated_date"));
				dto.setUpdatedBy(rs.getString("updated_by"));
			}
		}catch(Exception e){
			logger.error("Exception in getPayGroupDetailsById "+e.getMessage());
		}
		return dto;
	}
	
	public String saveJobGrade(JobGradeDTO dto,TelligentUser user){
		logger.info("in saveJobGrade");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		try {
			conn = this.getConnection();
			if(dto.getOperation().equalsIgnoreCase("update")){
				query = "update job_grade set value=?,description=?,isActive=?,primary_job=?,rate_frequency=?,minimum=?,midpoint=?,maximum=?,quartileLow1=?,quartileLow2=?,quartileLow3=?,quartileLow4=?,quartileHigh1=?,quartileHigh2=?,quartileHigh3=?,quartileHigh4=?,updated_date=sysdate(),updated_by=? where id=?";
				ps = conn.prepareStatement(query);
				ps.setString(1, dto.getValue());
				ps.setString(2, dto.getDescription());
				ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
				ps.setString(4, dto.getPrimaryJobId());
				ps.setString(5, dto.getRateFreq());
				ps.setString(6, dto.getMinimum());
				ps.setString(7, dto.getMidpoint());
				ps.setString(8, dto.getMaximum());
				ps.setString(9, dto.getQuartileLow1());
				ps.setString(10, dto.getQuartileLow2());
				ps.setString(11, dto.getQuartileLow3());
				ps.setString(12, dto.getQuartileLow4());
				ps.setString(13, dto.getQuartileHigh1());
				ps.setString(14, dto.getQuartileHigh2());
				ps.setString(15, dto.getQuartileHigh3());
				ps.setString(16, dto.getQuartileHigh4());
				ps.setString(17, user.getEmployeeId());
				ps.setString(18, dto.getId());
				int i = ps.executeUpdate();
				if(i>0)
					return "success:;Details updated Successfully";
				else
					return "error:;Details not updated";
			}else{
				if(!checkRecordExistence(conn, ps, rs,"job_grade" , dto.getValue())){
					// Save new record
					query = "insert into job_grade (value,description,isActive,primary_job,rate_frequency,minimum,midpoint,maximum,quartileLow1,quartileLow2,quartileLow3,quartileLow4,quartileHigh1,quartileHigh2,quartileHigh3,quartileHigh4,updated_date,updated_by) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?)";
					ps = conn.prepareStatement(query);
					ps.setString(1, dto.getValue());
					ps.setString(2, dto.getDescription());
					ps.setBoolean(3, Boolean.parseBoolean(dto.getIsActive()));
					ps.setString(4, dto.getPrimaryJobId());
					ps.setString(5, dto.getRateFreq());
					ps.setString(6, dto.getMinimum());
					ps.setString(7, dto.getMidpoint());
					ps.setString(8, dto.getMaximum());
					ps.setString(9, dto.getQuartileLow1());
					ps.setString(10, dto.getQuartileLow2());
					ps.setString(11, dto.getQuartileLow3());
					ps.setString(12, dto.getQuartileLow4());
					ps.setString(13, dto.getQuartileHigh1());
					ps.setString(14, dto.getQuartileHigh2());
					ps.setString(15, dto.getQuartileHigh3());
					ps.setString(16, dto.getQuartileHigh4());
					ps.setString(17, user.getEmployeeId());
					int i = ps.executeUpdate();
					if(i>0)
						return "success:;Details Saved Successfully";
					else
						return "error:;Details not saved";
				}else{
					return "error:;Job Grade already exists";
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception in saveJobGrade"+ex.getMessage());
			return "error:;"+ex.getMessage();
		} finally {
			this.closeAll(conn, ps, rs);
		}
	}
	public ArrayList<JobGradeDTO> getJobGradeDetails(){
		ArrayList<JobGradeDTO> list = new ArrayList<JobGradeDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			HashMap<String , String> map = new HashMap<String, String>();
			conn = this.getConnection();
			ps = conn.prepareStatement("select id,value from pay_frequency where isActive=1");
			rs = ps.executeQuery();
			while(rs.next())
				map.put(rs.getString("id"), rs.getString("value"));
			ps.close();rs.close();
			ps = conn.prepareStatement("select B.id id,B.value value,B.description description,if(B.isActive,'true','false') isActive,pj.value primary_job,rate_frequency,B.minimum minimum,B.midpoint midpoint,B.maximum maximum,quartileLow1,quartileLow2,quartileLow3,quartileLow4,quartileHigh1,quartileHigh2,quartileHigh3,quartileHigh4,DATE_FORMAT(B.updated_date,'%m/%d/%Y  %H:%i:%S') updated_date,concat(L_NAME,',',F_NAME) updatedBy from job_grade B left join EMP_PERSONAL E on B.updated_by = EMP_ID left join primary_job pj on pj.id = primary_job order by B.value ");
			rs = ps.executeQuery();
			while(rs.next()){
				JobGradeDTO dto = new JobGradeDTO();
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setPrimaryJobId(rs.getString("primary_job"));
				dto.setRateFreq(map.get(rs.getString("rate_frequency")) != null ? map.get(rs.getString("rate_frequency")): "");
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
				dto.setUpdatedDate(rs.getString("updated_date"));
				dto.setUpdatedBy(rs.getString("updatedBy"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getJobGradeDetails "+e.getMessage());
		}finally{
			this.closeAll(conn,ps,rs);
		}
		return list;
	}
	public JobGradeDTO getJobGradeDetailsById(String id){
		JobGradeDTO dto = new JobGradeDTO();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select id,value,description,if(isActive,'true','false') isActive,primary_job,rate_frequency,minimum,midpoint,maximum,quartileLow1,quartileLow2,quartileLow3,quartileLow4,quartileHigh1,quartileHigh2,quartileHigh3,quartileHigh4 from job_grade where id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				dto.setDescription(rs.getString("description"));
				dto.setIsActive(rs.getString("isActive"));
				dto.setPrimaryJobId(rs.getString("primary_job"));
				dto.setRateFreq(rs.getString("rate_frequency"));
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
		}catch(Exception e){
			logger.error("Exception in getJobGradeDetailsById "+e.getMessage());
		}finally{
			this.closeAll(conn,ps,rs);
		}
		return dto;
	}
	public ArrayList<MapDTO> getPrimaryJobLookup(){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select id,value,description from primary_job where isActive=1  order by value ");
			rs = ps.executeQuery();
			while(rs.next()){
				MapDTO dto = new MapDTO();
				dto.setId(rs.getString("id"));
				dto.setValue(rs.getString("value"));
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("Exception in getPrimaryJobLookup "+e.getMessage());
		}finally{
			this.closeAll(conn,ps,rs);
		}
		return list;
	}
	public ArrayList<MapDTO> getPayFrequency(){
		ArrayList<MapDTO> list = new ArrayList<MapDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			ps = conn.prepareStatement("select id,value from pay_frequency where isActive=1");
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

}

