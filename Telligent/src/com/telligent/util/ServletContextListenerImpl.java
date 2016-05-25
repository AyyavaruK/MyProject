package com.telligent.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.telligent.model.db.DBConnectionFactory;

public class ServletContextListenerImpl implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try{   
    		new DBConnectionFactory();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}

}
