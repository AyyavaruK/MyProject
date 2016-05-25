/**
 * 
 */
package com.telligent.common.handlers;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageHandlerImpl implements MessageHandler,
		ApplicationContextAware {
	
	private Object[] defaultParameters = new Object[0];
	private String defaultMessage = ""; //$NON-NLS-1$
	private Locale defaultLocale = Locale.getDefault();
	private MessageSource resources;


	public String getMessage(String messageId) {
		return getMessage(messageId, defaultParameters, defaultMessage,
				defaultLocale);
	}

	public String getMessage(String messageId, Object[] parameters) {
		return getMessage(messageId, parameters, defaultMessage, defaultLocale);
	}

	public String getMessage(String messageId, Object[] parameters,
			String message) {
		return getMessage(messageId, defaultParameters, message, defaultLocale);
	}

	public String getMessage(String messageId, Object[] parameters,
			String defaultMessage, Locale locale) {
		String message = resources.getMessage(messageId, parameters,
				defaultMessage, locale);
		/*
		 * if the message we got back is not empty we're done. Otherwise, if
		 * default message is not empty use that. Otherwise return the message
		 * id if that is not null or "message not found"
		 */

		if ((message == null) || (message.length() == 0)) {
			if ((messageId != null) && (messageId.length() > 0)) {
				message = messageId;
			} else {
				message = "message id not found"; //$NON-NLS-1$
			}

		}
		return message;
	}
	
	public String getMessage(String messageId, Locale locale) {
		String message = resources.getMessage(messageId, defaultParameters,
				defaultMessage, locale);
		if ((message == null) || (message.length() == 0)) {
			if ((messageId != null) && (messageId.length() > 0)) {
				message = messageId;
			} else {
				message = "message id not found"; //$NON-NLS-1$
			}

		}
		return message;
	}
	
	public void setApplicationContext(ApplicationContext ctx)
		throws BeansException {
		this.resources = ctx;
	}

}
