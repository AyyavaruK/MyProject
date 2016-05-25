package com.telligent.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class AbstractDBManager {

	
	private IDBAdapter dbAdapter = null;
	private boolean isInit;
	
	/**
	 * Creates a new instance of AbstractDAO
	 */
	public AbstractDBManager(){
		try{
			this.init();
		}catch(Exception e){
			throw new RuntimeException("Unable to initialize DatabaseConnectionFactory");
		}
	}
	
	/**
	 * This method is used for initialization
	 * 
	 * @throws Exception while initialization
	 */
	public void init() throws Exception {
           
       	isInit=true;
       	dbAdapter = DBConnectionFactory.getDatabaseHandle(Config.getInstance());
               
	}
	
	/**
	 * This method is used for getting the connection
	 * 
	 * @throws Exception while getting the connection
	 * @return the dbAdapter.getConnection()
	 */
	public Connection getConnection() throws Exception {
		if(!isInit){
			this.init();
		}
		Connection conn = dbAdapter.getConnection();
		if(conn == null ){ //|| conn.equals("null")
			this.init();
			this.getConnection();
		}        	
		return conn;
	}
	
	/*public Connection getConnectionConcerto() throws Exception {
		if(!isInit){
			this.init();
		}
		Connection conn = dbAdapter.getConnection();
		if(conn == null ){ //|| conn.equals("null")
			this.init();
			this.getConnectionConcerto();
		}        	
		return conn;
	}*/
	/**
	 * This method is of type boolean, it is used for closing connection
	 *
	 * @param connection connection object for closing the connection
	 * @throws Exception while closing the connection
	 * @return the <code>dbAdapter.releaseConnection( connection )</code>
	 */
	public boolean closeConnection( Connection connection ) throws Exception {
		if(!isInit)
			this.init();
		return dbAdapter.releaseConnection( connection );
	}
	

	/**
	 * This method is used for closing connections and prepared statements
	 * 
	 * @param conn for closing all the connections it takes conn as param
	 * @param stat for closing all the prepared statements it takes stat as param
	 */
	public void closeAll( Connection conn, PreparedStatement stat ) {
		try {
			if( stat != null )
				stat.close();
			closeConnection(conn);
		}
		catch( Exception exp ) {
		}
	}
	

	/**
	 * This method is used for closing connections and statements
	 * 
	 * @param conn for closing all the connections it takes conn as param
	 * @param stat for closing all the prepared statements it takes stat as param
	 */
	public void closeAll( Connection conn, Statement stat ) {
		try {
			if( stat != null )
				stat.close();
			closeConnection(conn);
		}
		catch( Exception exp ) {
		}
	}

	/**
	 * This method is used for closing connections and result sets
	 * 
	 * @param conn for closing all the connections it takes conn as param
	 * @param stat for closing all the prepared statements it takes stat as param
	 * @param rs for closing all the result sets it takes rs as param
	 */
	public void closeAll( Connection conn, PreparedStatement stat, ResultSet rs ) {
		try {
			if( rs != null )
				rs.close();
			closeAll(conn, stat);
		}
		catch( Exception exp ) {
		}
	}

	/**
	 * This method is used for closing connections, statements and result sets
	 * 
	 * @param conn for closing all the connections it takes conn as param
	 * @param stat for closing all the prepared statements it takes stat as param
	 * @param rs for closing all the result sets it takes rs as param
	 */
	public void closeAll( Connection conn, Statement stat, ResultSet rs ) {
		try {
			if( rs != null )
				rs.close();
			closeAll(conn, stat);
		}
		catch( Exception exp ) {
		}
	}

	public void closeAll(Statement stat, ResultSet rs ) {
		try {
			if( rs != null )
				rs.close();
			closeAll(stat);
		}
		catch( Exception exp ) {
		}
	}
	public void closeAll( Statement stat) {
		try {
			if( stat != null )
				stat.close();
		}
		catch( Exception exp ) {
		}
	}
	public Connection getPoolFreeConnection(String url, String user, String password) throws Exception {
		return dbAdapter.getPoolFreeConnection(url, user, password);
	}
	
	/**
	 * This method will return a pool free connection. This is necessary for 
	 * long operations that takes more time than the actual connection time.
	 * But be ware of closing this connection.
	 *
	 * @return java.sql.Connection
	 * @throws Exception
	 */
	public Connection getPoolFreeConnection() throws Exception {
		return dbAdapter.getPoolFreeConnection();
	}
	
	public void closeConnectionObject( Connection connection ) {
		try {
			closeConnection(connection);
		}
		catch( Exception exp ) {
		}
	}
	
	public String checkifNull(String str){
		if(str == null )
			return "";
		else
			return str;
	}
}




