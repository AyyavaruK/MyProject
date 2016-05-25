package com.telligent.model.db;

import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Enumeration;

public class Generic {
	
	private String systemtype = "";
	public Generic(){
		Properties prop = System.getProperties();
		systemtype = prop.get("os.arch").toString();
	}
	
	/**
	 * This method retuns the Config class object. This 
	 * Object is used for getting the parameters defined 
	 * in a configuration file.
	 *
	 * @return Config class object
	 */
	public Config getConfigInstance(String xmlFileName) {
		
		Config phe_config = null;		
		try {			
			URL url = this.getClass().getClassLoader().getResource(xmlFileName);
			String config_file = url.getPath();                                    
			if(systemtype.trim().equals("x86"))
				config_file = config_file.substring(1, config_file.length());
            phe_config = Config.newInstance(config_file);
            
		} catch (Exception e) {
			//logger.logError("Exception in getConfigInstance : " + e.toString());
		}
		return phe_config;
	}

	/**
	 * This method retuns the Config class object. This 
	 * Object is used for getting the parameters defined 
	 * in a configuration file.
	 *
	 * @return Config class object
	 */
	public Config getNewConfigInstance(String xmlFileName) {
		
		Config phe_config = null;		
		try {			
			URL url = this.getClass().getClassLoader().getResource(xmlFileName);
			String config_file = url.getPath();  
			//logger.logDebug("System type  -- "+systemtype);
			if(systemtype.trim().equals("x86"))
            	config_file = config_file.substring(1, config_file.length());
            
            phe_config = Config.getInstance(config_file);
		} catch (Exception e) {			
		//	logger.logError("Exception in getNewConfigInstance : " + e.toString());
		}
		return phe_config;
	}

	
	/**
	 * This method retuns the config file path.
	 *
	 * @return the config file path
	 */
	public String getConfigFilePath(String xmlFileName) {
		
		String ftpConfigFilePath = "";
		try {
			URL url = this.getClass().getClassLoader().getResource( xmlFileName );
			ftpConfigFilePath = url.getPath();			
			if(systemtype.trim().equals("x86"))			
				ftpConfigFilePath = ftpConfigFilePath.substring(1, ftpConfigFilePath.length());			
		} catch (Exception e) {			
			//logger.logError("Exception in getConfigFilePath : " + e.toString());
		}
		return ftpConfigFilePath;
	}	
	
	public boolean checkWindows(){
		if(systemtype.trim().equals("x86"))
			return true;
		else
			return false;
	}
	
	public Map getPropertyNameValue(String fileName){
		Map chartConfig = new HashMap();
		Properties properties = new Properties();
		try{
		URL url = this.getClass().getClassLoader().getResource(fileName);
		String property_file = url.getPath().replaceAll("%20"," ");
		FileInputStream fis = new FileInputStream(property_file);
		properties.load(fis);
		Enumeration keySet = properties.propertyNames();
		while(keySet.hasMoreElements()){
			String keyName = (String)keySet.nextElement();
			chartConfig.put(keyName, properties.getProperty(keyName));
			//keys.add(keyName);
		}
		fis.close();
		fis=null;properties=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return chartConfig;
	}
}
