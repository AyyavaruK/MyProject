package com.telligent.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.log4j.Logger;
/**
 * The class for String Utility
 * @author spothu
 * 18 Mar 2015
 */
public class ParseNumberUtility {

	public static final Logger logger = Logger.getLogger(ParseNumberUtility.class);

	static DecimalFormat df = new DecimalFormat("###.00");

	static NumberFormat nf = NumberFormat.getInstance(Locale.US);

	public ParseNumberUtility(){
		df.setMaximumFractionDigits(2);
	}
	public static String parseNumber(String str){
		try{
			String temp =df.format(df.parse(str));
			if(temp.split("\\.")[0].equalsIgnoreCase(""))
				return 0+temp;
			else{
				if(temp.split("\\.")[1].equalsIgnoreCase("")){
					return temp+".00";
				}else if(temp.split("\\.")[1].length() == 1 ){
					return temp+"0";
				}else{
					return temp;
				}
			}
				
		}catch(Exception e){
			return "0.00";
		}
	}
	public static String parseNumberInUSFormat(String str){
		String temp = nf.format(Double.parseDouble(parseNumber(str)));
		try{
			if(temp.split("\\.")[0].equalsIgnoreCase(""))
				return 0+temp;
			else{
				if(temp.split("\\.")[1].length() == 1 ){
					return temp+"0";
				}else{
					return temp;
				}
			}
		}catch(Exception e){
			return temp+".00";
		}
	}
	public static void main(String[] args) {
		String str = ParseNumberUtility.parseNumber("0.00");
		System.out.println(str);
		System.out.println(parseNumberInUSFormat(str));
	}
}
