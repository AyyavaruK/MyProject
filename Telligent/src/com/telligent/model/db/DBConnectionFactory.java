package com.telligent.model.db;

import java.util.HashMap;
import java.util.Properties;
/**
 * 
 * @author spothu
 *
 */
public class DBConnectionFactory 
{
	 private Config telligent_config;    
	    private String dbConfig_file;    
	    private DBConfig dbConfig;	
		private static IDBAdapter default_dbadapter;
	    private static HashMap adapters = new HashMap();
	    
	    /** 
	     * Creates a new instance of DBConnectionFactoryConnectionFactory 
	     */    
	    public  DBConnectionFactory() {	       	
	        try {  
	            telligent_config =  new Generic().getNewConfigInstance("config.xml");
	            dbConfig_file = new Generic().getConfigFilePath(telligent_config.getProperty("tool-db/db-type").trim().toUpperCase() + ".xmd");
	            dbConfig = DBConfig.getInstance(dbConfig_file);
	            try {
	            	getDatabaseHandle(telligent_config);
	            } catch (Exception _ex) {
	                _ex.printStackTrace();
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    /**
		 * This method gets database handler
		 * 
	     * @param config related to implementation and properties
	     * @throws Exception while getting data base module
	     * @return the adapter
	     */    
	    public static IDBAdapter getDatabaseHandle( Config config ) throws Exception {

			IDBAdapter adapter = null;
	        if(!adapters.keySet().contains(config) ){
	            String implClass = config.getProperty( "tool-db/impl" );
	            System.out.println("DBConnectionFactory Implementation classes: "+ implClass);
	            Properties props = config.getNestedProperties("tool-db/properties");
	            adapter = (IDBAdapter) Class.forName( implClass ).newInstance();
	            adapter.init( props );
	            default_dbadapter = adapter;
	            adapters.put( config, adapter );
	        }
	        else
	            adapter = (IDBAdapter) adapters.get( config );
	        return adapter;
	    }
	    
	    /**
		 * This method gets default database handler
		 * 
	     * @throws Exception while getting default database handler
	     * @return the default_dbadapter
	     */    
	    public static IDBAdapter getDefaultDatabaseHandle() throws Exception {
	        if( default_dbadapter == null )
	            getDatabaseHandle( Config.getInstance() );
	        return default_dbadapter;
	    }
	
}
