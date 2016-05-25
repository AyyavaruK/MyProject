package com.telligent.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;
/**
 * The class for String Utility
 * @author spothu
 * 29 Oct 2014
 */
public class StringUtility {

	public static final Logger logger = Logger.getLogger(StringUtility.class);
	
	
	/**
	 * The method to build a string array values as a comma separated string.
	 * @author spothu
	 * @param input an ArrayList
	 * @return retString a String object.
	 * @throws Exception
	 */
	public static String buildCommaSeperatedValues(ArrayList input) throws Exception{
		String retString = "";
		if(input != null){
		for(int i=0; i<input.size(); i++){
			retString = retString + "," +input.get(i);
		}
		retString = retString.substring(1);
		}
		return retString;		
	}
	
	/**
     * Converts a string array to a string
     *
     * @param strA
     * @return String
     */
    public static String stringArrayToString(String[] strA, String delimiter) {
        StringBuffer str = new StringBuffer();
        if (null == strA || strA.length == 0)
            return null;
        for (int i = 0; i < strA.length; i++) {
            str.append(strA[i]);
            if (i < strA.length - 1)
                str.append(delimiter);
        }
        return str.toString();
    }
}
