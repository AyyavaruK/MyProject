package com.telligent.util;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.telligent.model.db.DBConnectionFactory;


/**
 * Servlet implementation class InitServlet
 * @author spothu
 * 29 Oct 2014
 */
@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Logger logger = Logger.getLogger(InitServlet.class);  
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		 super.init(config);
	     initialize();
	}

	/** method to do startup initializations */
    public void initialize() {
    	try{   
    		new DBConnectionFactory();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
