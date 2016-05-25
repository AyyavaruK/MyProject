/*
 * ConnectionPool.java 
 *
 */
package com.telligent.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.telligent.ui.controller.LoginController;
import com.telligent.util.exceptions.SQLException;

/** 
 * Connection pooling initialization class
 * 
 * @author spothu
 */
public class ConnectionPool implements IDBAdapter {
	
	public final Logger logger = Logger.getLogger(ConnectionPool.class);
    private Properties connproperties = new Properties();
    //private Config config;
    private boolean _transactionMode;
    
    
    /** Creates a new instance of SAPool */
    public ConnectionPool() {
    }
    
    public void init(){    	
    }
    
    /** 
	 * This method is for initialize the connection pool
     * @param prop related to datasource
     */
    public void init(java.util.Properties prop) {
        try{        	
        	Properties props = new Properties();
			props.setProperty("url", prop.getProperty("tool-db/properties/url"));
			props.setProperty("driver", prop.getProperty("tool-db/properties/driver"));
			props.setProperty("user", prop.getProperty("tool-db/properties/username"));
			props.setProperty("password", prop.getProperty("tool-db/properties/password", ""));
			connproperties = props;
			DBContextFactory.createDefaultContextPool(props);
        }
        catch(Exception ex) {
            logger.error("Exception thrown while looking up for DataSource", ex);
        }
    }    
    /**
	 * This method is for getting the connection
     * @return java.sql.Connection
     * @throws DBException Unable to get the Connection
     */
    public Connection getConnection() throws DBException {
        Connection connection=null;
        try {
        	//logger.logDebug("before getting connection ");
        	DBContext ctx = DBContextFactory.getContext("defaultpool");
			connection = ctx.getConnection();     
            if(connection==null)
                throw new DBException("Unable to get the Connection null");
        }
        catch(DBException dbex) {
            logger.error("Unable to get the Connection DBException", dbex);
        }
        
        return connection;
    }
    
    /**
	 * This method is for commit
     * @throws SQLException while commit
	 * @return <code>true</code> if commit, or
        <code>false</code> if not commit.
     */    
    public boolean commit() throws SQLException {
        return true;
    }
    
    /**
	 * This method is for getting the connection depending on the transaction mode
     * @param transactionMode type of connection
     * @throws DBException while getting the connection
     * @return the connection
     */    
    public Connection getConnection(boolean transactionMode) throws DBException {
        this._transactionMode = transactionMode;
        return this.getConnection();
    }
    
    /**
	 * This method is for setting status
     * @throws SQLException while getting the status
     * @return OK if the status is fine
     */    
    public String getStatus() throws SQLException {
        return "OK";
    }
    
    /**
	 * This method is for killing the connection
     * @param connection takes as connection
     * @throws DBException while killing the connection
	 * @return <code>true</code> if connected, or
        <code>false</code> if not connected.
     */    
    public boolean killConnection( Connection connection ) throws DBException {
        try {
            if( connection != null )
                connection.close();
            return true;
        }
        catch( Exception exp ) {
            return false;
        }
    }
    
    /**
	 * This method is for releasing the connection
     * @param connection takes as connection
     * @throws DBException while release connection
	 * @return <code>true</code> if connection released, or
        <code>false</code> if not connection released.
     */    
    public boolean releaseConnection( Connection connection ) throws DBException {
        try {
            if( connection != null && ! this._transactionMode )
                connection.close();
            return true;
        }
        catch( Exception exp ) {
            return false;
        }
    }
    
    /**
	 * This method is for setting auto commit
     * @param connection takes as connection
     * @throws DBException while setting the auto commit
	 * @return <code>true</code> if autocommit, or
        <code>false</code> if not autocommit.
     */    
    public boolean setAutoCommit( Connection connection ) throws DBException {
        try {
            if( connection != null )
                connection.setAutoCommit(true);
            return true;
        }
        catch( Exception exp ) {
            return false;
        }
    }
    
    /**
	 * This method is for testing
     * @return true testing
     */    
    public boolean test() {
        return true;
    }
    
    public Connection getPoolFreeConnection(String url, String user, String password) throws DBException{
    	return null;
    }
    
    /**
	 * This method will return a pool free connection. This is neccessary for 
	 * long operations that takes more time than the actual connection time.
	 * But be ware of closing this connection.
	 *
	 * @return java.sql.Connection
	 * @throws DBException
	 */
	public Connection getPoolFreeConnection() throws DBException {
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(connproperties.getProperty("url") , connproperties.getProperty("user"), connproperties.getProperty("password"));
			return con;
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}
	
}