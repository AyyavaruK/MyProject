/**
 * 
 */
package com.telligent.model.db;

/**
 * 
 */
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;



/**
 * @author spothu
 *
 */
public class ConnectionFactory {
    
	private static IDBAdapter default_dbadapter;
    private static HashMap adapters = new HashMap();
    public static final Logger logger = Logger.getLogger(ConnectionFactory.class); 
    private static Config dbconfig;
    /** 
     * Creates a new instance of Area51ConnectionFactory 
     */
    private  ConnectionFactory() {
    }
    
    public static Config getConfig(){
    	if(dbconfig == null)
    		dbconfig = new Generic().getNewConfigInstance("config.xml");
    	return dbconfig;    		
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
            logger.info("ConnectionFactory Implementation classes: "+ implClass);
            Properties props = config.getNestedProperties("tool-db/properties");
            adapter = (IDBAdapter) Class.forName( implClass ).newInstance();
            adapter.init(props);            
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