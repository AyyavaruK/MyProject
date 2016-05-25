package com.telligent.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
/**
 * The class for String Utility
 * @author spothu
 * 29 Oct 2014
 */
public class DateUtility {

	public static final Logger logger = Logger.getLogger(DateUtility.class);

	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtility.compareDates("15/01/2015", "14/01/2015"));
	}
	/**
	 * 
	 * 
	 * @param dateStr1 Given Date
	 * @param dateStr2 Current Date
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDates(String dateStr1,String dateStr2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	Date date1 = sdf.parse(dateStr1);
    	Date date2 = sdf.parse(dateStr2);
    	if(date1.compareTo(date2)>=0)
    		return false;
    	else 
    		return true;
	}
	
}
