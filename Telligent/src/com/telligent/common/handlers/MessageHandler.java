/**
 * 
 */
package com.telligent.common.handlers;

import java.util.Locale;

/**
 * interface to object which returns message give message parameters
 * @author spothu
 *
 */
public interface MessageHandler {
	
	/**
	 * get message given just message id
	 * @param messageId Message identifier which is associated with an entry in some message store like a properties file
	 * @return string associated with message id or message id if messageId invalid or "no message found" if message id is null
	 */
	String getMessage(String messageId);
	
	/**
	 * Get parameterized message
	 * @param messageId Message identifier which is associated with an entry in some message store like a properties file
	 * @param parameters array of parameters which are associated with placeholders in the message in order of position ({0}. {1}, etc)
	 * @return string associated with message id with parameters substituted or message id if message id invalid or "no message found" if null
	 */
	String getMessage(String messageId, Object[] parameters);
	
	/**
	 * Get parameterized message. If message not found, put out default message string
	 * @param messageId Message identifier which is associated with an entry in some message store like a properties file
	 * @param parameters array of parameters which are associated with placeholders in the message in order of position ({0}. {1}, etc)
	 * @param defaultMessage String which is returned if message id not found
	 * @return parameterized message or default string if message id not found, message id if default message null,  or "no message found" if message id is null
	 */
	String getMessage(String messageId, Object[] parameters, String defaultMessage);
	
	/**
	 * Get parameterized message, which message id is associated with a specific locale, using standard internationalization search rules. 
	 * If message not found, put out default message string
	 * @param messageId
	 * @param parameters
	 * @param defaultMessage
	 * @param locale
	 * @return parameterized message or default message if message id not found, message id if default message is null, or "no message id" if null. 
	 * Message returned based on standard locale rules
	 */
	String getMessage(String messageId, Object[] parameters, String defaultMessage, Locale locale);

}
