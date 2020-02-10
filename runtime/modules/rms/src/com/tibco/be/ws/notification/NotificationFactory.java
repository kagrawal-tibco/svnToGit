package com.tibco.be.ws.notification;

import com.tibco.be.ws.notification.impl.EmailNotification;
import com.tibco.be.ws.notification.impl.EmailNotificationContext;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/5/12
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationFactory {

    @SuppressWarnings("unchecked")
    public static <N extends INotificationContext> N getNotificationContext(String notificationContextClass) throws Exception {
        if (notificationContextClass == null) {
        	notificationContextClass = EmailNotificationContext.class.getName();
        } 

        Class<?> notificationContextClazz = Class.forName(notificationContextClass);
        if (!INotificationContext.class.isAssignableFrom(notificationContextClazz)) {
            throw new IllegalArgumentException("NotificationContext class should be of type com.tibco.be.ws.notification.INotificationContext");
        }
        return (N)notificationContextClazz.newInstance();
    }

    @SuppressWarnings("unchecked")
    public static <N extends INotificationContext, I extends INotification<N>> I getNotificationImpl(String notificationImplClass, INotificationContext notificationContext) throws Exception {

    	Class<?> notificationImplClazz;
    	if (notificationImplClass != null) {
    		notificationImplClazz = Class.forName(notificationImplClass);
    		if (!INotification.class.isAssignableFrom(notificationImplClazz)) {
    			throw new IllegalArgumentException("NotificationImplClass class should be of type com.tibco.be.ws.notification.INotification");
    		}
    		return (I)notificationImplClazz.newInstance();
    	} else {
	    	if (notificationContext instanceof EmailNotificationContext) {
	    		return (I)(new EmailNotification());
	    	} else {
	    		return null;
	    	}    	
    	}
    }

}
