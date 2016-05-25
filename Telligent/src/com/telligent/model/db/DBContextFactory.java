package com.telligent.model.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import com.bitmechanic.sql.ConnectionPoolManager;
import com.bitmechanic.sql.ConnectionValidator;
import com.bitmechanic.sql.TestQueryConnectionValidator;

/**
 * <p> This class is responsible for getting the DB Context.
 *
 *
 * Objects instantiated from this class are not to be shared across
 * threads, or held onto for long periods of time ( in member_variables, caches etc)
 * These are supposed to be short-lifetime objects with thread-local scope
 *
 * @author spothu
 */
public class DBContextFactory {
	private static final String defaultPool = "defaultpool";
	private static Hashtable m_connectionPools = new Hashtable();
	
	/**
	 * This is the default constructor for this class. This constructor is
	 * responsible for opening the JDBC connection with the database.
	 *
	 */
	private DBContextFactory() { }
	
	/**
	 * The Method is the actual implementation
	 * 
	 * @param poolName the connection pool name
	 * @param props the database credentials
	 * @throws DBException thrown while unable to get the context
	 */
	private DBContextFactory(String poolName, Properties props) throws DBException
	{
            if (isValidConnectionPool(poolName))
			throw new DBException("Connection Pool " + poolName + " already exists");

		try {
			
			String url = props.getProperty("url");
			if(url == null)
				throw new DBException("Connection url cannot be null");
			String driver = props.getProperty("driver");
			if(driver == null)
				throw new DBException("JDBC Driver cannot be null");
			String user = props.getProperty("user");
			if(user == null)
				throw new DBException("Database user cannot be null");
			String password = props.getProperty("password");
			if(password == null)
				throw new DBException("Database user password cannot be null");
			
			if(poolName == null)
				throw new DBException("Connection Pool name cannot be null");
			
			int maxConn = new Integer(props.getProperty("maxConnections","10")).intValue();
			System.out.println("poolName-- "+poolName+" ---maxConn -- "+maxConn);
			int idleTimeOut = new Integer(props.getProperty("idleConnectionTime","1000")).intValue();
			int maxConnectionTime = new Integer(props.getProperty("maxConnectionTime","600")).intValue();
			
			Class clazz = null;
			clazz = Class.forName(driver);    // Load the driver
			
			DriverManager.registerDriver((Driver)clazz.newInstance());
			
			// check for stale connections after 120 mins = 2 hrs
			ConnectionPoolManager poolManager = new ConnectionPoolManager(120);
			
			System.out.println("ConnectionPoolManager " + poolName+ " intialized with url="
					+ url + " user=" + user + " maxConn=" + maxConn
					+ " idleTimeOut=" + idleTimeOut + " maxConnectionTime="
					+ maxConnectionTime);
			poolManager.addAlias(poolName, driver, url, user,
					password, maxConn, idleTimeOut, maxConnectionTime);
			m_connectionPools.put(poolName,this);
			
			//Required for validating the connection
			String testSQL = "select count(*) from USERS";
			com.bitmechanic.sql.ConnectionPool pool = poolManager.getPool(poolName);
			ConnectionValidator connValidator = new TestQueryConnectionValidator(testSQL);
			//pool.setValidator(connValidator);
			
		} catch (Exception e) {
			System.out.println(ErrorMessages.CONNECTION_ERROR);
			throw new DBException(e);
		}
	}
	
	/**
	 * The Method to check the connection pool validity
	 * 
	 * @param poolName the connection pool name
	 * @return true if successful
	 * 		   false otherwise
	 */
	private boolean isValidConnectionPool(String poolName)
	{
		DBContextFactory local = (DBContextFactory) m_connectionPools.get(poolName);
		if (local != null)
			return true;
		return false;
	}
	
	/**
	 * The Method to create a default context pool if nothing is 
	 * specified
	 * 
	 * @param props the database credentials
	 * @throws DBException thrown while unable to get the context
	 */
	public synchronized static void createDefaultContextPool(Properties props) throws DBException
	{
		DBContextFactory ctx = new DBContextFactory(defaultPool,props);
	}
		
	/**
	 * The Method to create context pool with the given name
	 * 
	 * @param poolName the name of the pool to create 
	 * @param props the database credentials
	 * @throws DBException thrown while unable to get the context
	 */
	public synchronized static void createContextPool(String poolName, Properties props) throws DBException
	{
		DBContextFactory ctx = new DBContextFactory(poolName,props);
	}
	
	/**
	 * The Method to get the database context
	 * 
	 * @param poolName the name of the pool
	 * @return DBContext the DBContext instance
	 */
	private DBContext getDBContext(String poolName) {
		
		return new DBContext(poolName);
	}
	
	/**
	 * The overloaded method to get the database context
	 * 
	 * @param transactionType the transaction type
	 * @param poolName the name of the pool
	 * @return DBContext the DBContext instance
	 */
	private DBContext getDBContext(String poolName,String transactionType) {
		return new DBContext(poolName,transactionType);
	}
	
	
	/**
	 * This method returns a instance of DBContext object. Since the constructor is
	 * private, a public method which will return the instance of this class.
	 *
	 * @return DBContext object
	 */
	public synchronized static DBContext createContext() {
		return getInstance(defaultPool).getDBContext(defaultPool);
	}
	
	/**
	 * This method returns a instance of DBContext object. Since the constructor is
	 * private, a public method which will return the instance of this class.
	 *
	 * @return DBContext object
	 */
	private synchronized static DBContext createContext( String transactionType)
	{
		return getInstance(defaultPool).getDBContext(defaultPool,transactionType);
	}
	
	/**
	 * This method returns a instance of DBContext object. Since the constructor is
	 * private, a public method which will return the instance of this class.
	 *
	 * @return DBContext object
	 */
	public synchronized static DBContext getContext(String poolName)
	throws DBException
	{
		DBContextFactory dbf = new DBContextFactory();
		if(dbf.isValidConnectionPool(poolName))
			return getInstance(poolName).getDBContext(poolName);
		else
			throw new DBException("Invalid DB Connection Pool: " + poolName);
	}
	
	/**
	 * This method returns a instance of DBContext object. Since the constructor is
	 * private, a public method which will return the instance of this class.
	 *
	 * @return DBContext object
	 */
	public synchronized static DBContext getContext( String poolName,
			String transactionType)
	throws DBException
	{
		DBContextFactory dbf = new DBContextFactory();
		if(dbf.isValidConnectionPool(poolName))
			return getInstance(poolName).getDBContext(poolName,transactionType);
		else
			throw new DBException("Invalid DB Connection Pool: " + poolName);
	}
	
	/**
	 * The Method to get the instance of DBContextFactory class
	 * 
	 * @param poolName the name of the connection pool
	 * @return DBContextFactory object
	 */
	private static synchronized DBContextFactory getInstance(String poolName)
	{
		if(poolName == null)
			return null;
		
		DBContextFactory localFactory = (DBContextFactory)m_connectionPools.get(poolName);
		return localFactory;
	}
	
	/**
	 * The Method to remove the context pool
	 * 
	 * @param poolName the name of the pool
	 */
	public synchronized static void removeContextPool(String poolName)
	{
		try
		{
			ConnectionPoolManager cpm = new ConnectionPoolManager();
			cpm.removeAlias(poolName);
		}
		catch(SQLException sqe)
		{
		}
		finally {
			m_connectionPools.remove(poolName);
		}
	}
	
	/**
	 * The Method to get the database vendor name
	 * 
	 * @return String containing the vendor name
	 */
	public static String getDatabaseVendor()
	{
		return getDatabaseVendor(defaultPool);
	}
	
	/**
	 * The overloaded method to get the database vendor name
	 *  
	 * @param poolName the name of the connection pool
	 * @return String containing the vendor name
	 */
	public static String getDatabaseVendor(String poolName)
	{
		Connection conn=null;
		String dbVendor=null;
		try
		{
			if (dbVendor == null)
			{
				DBContext ctx = getContext(poolName);
				conn = ctx.getConnection();
				DatabaseMetaData dbm = conn.getMetaData();
				dbVendor = dbm.getDatabaseProductName();
				conn.close();
			}
			return dbVendor;
		}
		catch (Exception e)
		{
			return ErrorMessages.INVALID_DB_DRIVER;
		}
		
		finally {
			try {
				conn.close();
			}
			catch (Exception e) { }
		}
	}
	
}
