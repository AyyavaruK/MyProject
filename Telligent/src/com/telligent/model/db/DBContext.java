package com.telligent.model.db;

import com.bitmechanic.sql.ConnectionPoolManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * <p> This class is responsible for opening the JDBC connection with the database.
 *
 *
 * Objects instantiated from this class are not to be shared across
 * threads, or held onto for long periods of time ( in member_variables, caches etc)
 * These are supposed to be short-lifetime objects with thread-local scope
 *
 * @author spothu
 */
public class DBContext {
	private String m_poolName;
	private Connection m_localConnection = null;
	private ResultSet m_resultSet = null;
	private boolean dbTransactionMode = false;
	
	
	/**
	 * This is the default constructor for this class. This constructor is
	 * responsible for opening the JDBC connection with the database. The connection
	 * pool mechanism implemented by the bitmechanic opensource driver.
	 */
	public DBContext(String poolName) {
		m_poolName = poolName;
		dbTransactionMode = false;
	}
	
	
	/**
	 * This is the default constructor for this class. This constructor is
	 * responsible for opening the JDBC connection with the database. This
	 * constructor also accepts a string to make sure that there is a commit
	 * isoloation level that can be set. If the user of this class invokes this
	 * particular constructor, then he can maintain the context between method
	 * calls there by ensuring the transaction support. In future we can use
	 * this string to implement different types of commit isoloation levels.
	 *
	 * @param commitType is used for setting the transaction context
	 */
	public DBContext(String poolName,String commitType) {
		try {
			m_poolName = poolName;
			dbTransactionMode = true;
			m_localConnection =
				DriverManager.getConnection(ConnectionPoolManager.URL_PREFIX
						+ m_poolName, null, null);
		} catch (Exception e) {
			System.out.println(ErrorMessages.CONNECTION_ERROR);
		}
	}
	
	/**
	 * The Method to set the result set
	 * 
	 * @param rs the ResultSet object
	 */
	public void setResultSet(ResultSet rs)
	{
		this.m_resultSet = rs;
	}
	
	/**
	 * The Method to return the result set
	 * 
	 * @return the ResultSet object
	 */
	public ResultSet getResultSet()
	{
		return m_resultSet;
	}
	
	/**
	 * This method will actually returns the java.sql.Connection object
	 * May cause the thread to block if there are no connections available.
	 *
	 * @return java.sql.Connection
	 * @throws DBException
	 */
	public Connection getConnection() throws DBException {
		try {
			if (dbTransactionMode) {
				if (m_localConnection == null) {
					m_localConnection =
						DriverManager.getConnection(ConnectionPoolManager.URL_PREFIX
								+ m_poolName, null, null);
				}
				return m_localConnection;
			} else {
				Connection con = DriverManager.getConnection(ConnectionPoolManager.URL_PREFIX
						+ m_poolName, null, null);
				con.setAutoCommit(true);
				return con;
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}
	
	/**
	 * This method is used as a wrapper method for java.sql.Connection class commit method
	 * 
	 * @param autoCommit can be set to true or false
	 * @throws DBException
	 */
	public void setAutoCommit(boolean autoCommit) throws DBException {
		try {
			if (dbTransactionMode) {
				m_localConnection.setAutoCommit(autoCommit);
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}
	
	/**
	 * This method is a wrapper method over a java.sql.Connection class commit method
	 * 
	 * @throws DBException
	 */
	public void commit() throws DBException {
		try {
			if (dbTransactionMode) {
				m_localConnection.commit();
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}
	
	/**
	 * This method is a wrapper method over a java.sql.Connection class rollback method
	 * 
	 * @throws DBException
	 */
	public void rollback() throws DBException {
		try {
			if (dbTransactionMode) {
				m_localConnection.rollback();
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}
		
	/**
	 * This method release the connection that is obtained from the pool
	 * 
	 * @throws DBException
	 */
	public void release() throws DBException {
		try {
			if (dbTransactionMode) {
				if(m_resultSet != null)
					m_resultSet.close();
				if(m_localConnection != null)
					m_localConnection.close();
				m_localConnection = null;
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}
	
	/**
	 * If the transactions are maintainted by the user, then user should manually
	 * release connections and this method is used for that
	 * 
	 * @param conn java.sql.Connection object that needs to be released
	 * @throws DBException
	 */
	public void release(Connection conn) throws DBException {
		try {
			if (!dbTransactionMode) {
				conn.close();
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}
		
	/**
	 * This method is used for closing the connections
	 * 
	 * @throws DBException
	 */
	public void txrelease() throws DBException {
		try {
			if (dbTransactionMode) {
				if(m_resultSet != null)
					m_resultSet.close();
				if(m_localConnection != null)
					m_localConnection.close();
				m_localConnection = null;
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}
	
	/**
	 * The Method to return pool name
	 * 
	 * @return String containing the pool name
	 */
	public String getPoolName()
	{
		return m_poolName;
	}
	
}
