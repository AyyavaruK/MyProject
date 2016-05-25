package com.telligent.model.db;

import java.sql.Connection;
import java.util.Properties;

import com.telligent.util.exceptions.SQLException;


/**
 * IDBAdapter is the interface that any database
 * connection provider must conform to
 * seamless integration with cleanser.
 * 
 * @author spothu
 */
public interface IDBAdapter {
	public void init();
    public void init( Properties prop );
    public Connection getConnection() throws DBException;
    public Connection getPoolFreeConnection() throws DBException;
    public Connection getConnection(boolean transactionMode) throws DBException;
    public boolean test();
    public boolean setAutoCommit( Connection connection ) throws DBException;
    public boolean commit() throws SQLException;
    public boolean releaseConnection( Connection connection ) throws DBException;
    public boolean killConnection( Connection connection ) throws DBException;
    public String getStatus() throws SQLException;
    public Connection getPoolFreeConnection(String url, String user, String password) throws DBException;
    
}