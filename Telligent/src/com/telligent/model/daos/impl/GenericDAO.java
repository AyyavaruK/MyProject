package com.telligent.model.daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * The DAO class for generic operations.
 * @author spothu
 */
public class GenericDAO{

	public static GenericDAO _instance = null;

	/**
	 * This method creates the WizardOfDAOz instance
	 * @throws Exception while getting the instance of WizardOfDAOz
	 * @return the WizardOfDAOz
	 */
	public static GenericDAO getInstance() throws Exception {

		return _instance = _instance == null ? new GenericDAO() : _instance;
	}
	/**
	 * The method to get the max id
	 *
	 * @param conn the Connection object
	 * @param ps the PreparedStatement object
	 * @param rs the ResultSet object
	 * @param query the query
	 * @param placeHolders the place holders list
	 * @return int the number of effected records
	 */
	public int getMaxId(Connection conn, PreparedStatement ps, ResultSet rs, String query, String placeHolders) {
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			if(!placeHolders.equals("")) {

				String[] st = placeHolders.split(":");
				for(int it=0; it < st.length; it++) {

					String value = st[it];
					value = value != null ? value : "";
					ps.setObject(it+1, value);
				}
			}
			rs = ps.executeQuery();
			while(rs.next()) {				
				result = rs.getInt(1);
			}
			ps.close();rs.close();
		} catch (Exception ex) {
			//logger.logDebug("Exception in getMaxId while executing query : "+query +"\n\n Exception is :" + ex.getMessage());
			ex.printStackTrace();
		} 
		return result; 

	}
	
	
}
