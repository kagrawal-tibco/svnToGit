package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import java.util.Map;

import com.tibco.be.ws.notification.INotification;
import com.tibco.be.ws.notification.INotificationContext;
import com.tibco.be.ws.notification.NotificationFactory;
import com.tibco.be.ws.notification.NotificationStatus;
import com.tibco.cep.security.authen.UserDataProviderFactory;
import com.tibco.cep.security.dataprovider.IUserDataProvider;

/**
 * @.category WS.Common.Notify
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Common.Notify",
        synopsis = "Web Studio Notification Functions",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Common.Notify", value=true))

public class WebstudioNotificationFunctions {
	
	private static INotification<INotificationContext> notificationImpl = null; 
	
    @com.tibco.be.model.functions.BEFunction(
            name = "initializeNotificationContext",
            synopsis = "",
            signature = "Object initializeNotificationContext(String notificationContextClass, Object contextPropertiesMapObj)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "notificationContextClass", type = "String", desc = "Notification Context Class name."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "contextPropertiesMapObj", type = "Object", desc = "Context properties Map.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Notification Context object."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a Notification context object as per the Implementation class name provided.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )	
	@SuppressWarnings("unchecked")
    public static Object initializeNotificationContext(String notificationImplClass, String notificationContextClass, Object contextPropertiesMapObj) {		

		if (notificationContextClass == null) {
			throw new IllegalArgumentException("Parameter notificationContextClass cannot be NULL");
		}		
		Map<String, String> contextPropertiesMap;
		if (contextPropertiesMapObj instanceof Map) {
			contextPropertiesMap = (Map<String, String>) contextPropertiesMapObj; 
		} else {
			throw new IllegalArgumentException("Parameter contextPropertiesMapObj should be of type java.util.Map");
		}
		
		INotificationContext notificationContext;
		try {
			notificationContext = NotificationFactory.getNotificationContext(notificationContextClass);
			notificationContext.initialize(contextPropertiesMap);
			if (notificationImpl == null) {
				notificationImpl = NotificationFactory.getNotificationImpl(notificationImplClass, notificationContext);
			}
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		
		return notificationContext;
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "openConnection",
            synopsis = "",
            signature = "void openConnection(Object notificationContextObj)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "notificationContextObj", type = "Object", desc = "Context Obj.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Notification Context object."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Establishes a connection as per the Implementation class name provided.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )	
    public static void openConnection(Object notificationContextObj) {		

		INotificationContext notificationContext;
		if (notificationContextObj instanceof INotificationContext) {
			notificationContext = (INotificationContext) notificationContextObj; 
		} else {
			throw new IllegalArgumentException("Parameter notificationContextObj should be of type com.tibco.be.ws.notification.INotificationContext");
		}
		if (notificationImpl == null) {
			throw new IllegalStateException("NotificationImpl has not been initialized");
		}	
		
		try {
			notificationImpl.openConnection(notificationContext);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}	
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "loadMessageTemplates",
            synopsis = "",
            signature = "loadMessageTemplates(String messageTemplateContents)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "messageTemplateContents", type = "String", desc = "Message template contents.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Loads the notification message templates.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )	
    public static void loadMessageTemplates(String messageTemplateContents) {		

		if (messageTemplateContents == null) {
			throw new IllegalArgumentException("Parameter messageTemplateContents is NULL");
		}	
		if (notificationImpl == null) {
			throw new IllegalStateException("NotificationImpl has not been initialized");
		}	
		
		try {
			notificationImpl.loadMessageTemplates(messageTemplateContents);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}	
	}
        
    @com.tibco.be.model.functions.BEFunction(
            name = "prepareNotificationMessage",
            synopsis = "",
            signature = "void prepareNotificationMessage(Object notificationContextObj, String notificationType, String messageTemplateContents, Object messageDataMapObj)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "notificationContextObj", type = "Object", desc = "Notification Context Class name."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "notificationType", type = "String", desc = "Type of notification (approval, commit etc.)."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "messageTemplateContents", type = "String", desc = "Message templates contents."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "messageDataMapObj", type = "Object", desc = "Message Data values (project name, user etc.).")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Notification Context object with the message."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a Notification context object as per the Implementation class name provided.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	@SuppressWarnings("unchecked")
	public static void prepareNotificationMessage(Object notificationContextObj, String notificationType, String messageTemplateContents, Object messageDataMapObj) {

		INotificationContext notificationContext;
		if (notificationContextObj instanceof INotificationContext) {
			notificationContext = (INotificationContext) notificationContextObj; 
		} else {
			throw new IllegalArgumentException("Parameter notificationContextObj should be of type com.tibco.be.ws.notification.INotificationContext");
		}
		
		Map<String, String> messageDataMap;
		if (messageDataMapObj instanceof Map) {
			messageDataMap = (Map<String, String>) messageDataMapObj; 
		} else {
			throw new IllegalArgumentException("Parameter messageDataMapObj should be of type java.util.Map");
		}

		if (notificationImpl == null) {
			throw new IllegalStateException("NotificationImpl has not been initialized");
		}	
		
		try {
			notificationImpl.prepareMessage(notificationContext, notificationType, messageTemplateContents, messageDataMap);			
		} catch (Throwable t) {
			throw new RuntimeException(t);
		} 
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "getUserNotifyId",
            synopsis = "",
            signature = "getUserNotifyId(String messageTemplateContents)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "username", type = "String", desc = "User Name.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "User Notify Id"),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Fetches the User notify Id.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )	
    public static String getUserNotifyId(String username) {		
		if (username == null) {
			throw new IllegalArgumentException("Parameter username is NULL");
		}	
		String userNotifyId = null;
		try {
            IUserDataProvider provider = UserDataProviderFactory.INSTANCE.getProvider();
            userNotifyId = provider.getUserNotifyId(username);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		
		return userNotifyId;
	}
        
    @com.tibco.be.model.functions.BEFunction(
            name = "sendNotification",
            synopsis = "",
            signature = "NotificationStatus sendNotification(Object notificationContextObj)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "notificationContextObj", type = "Object", desc = "Notification Context Object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Notification Status object."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sends Notification as per the context and returns the notification status.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
	public static Object sendNotification(Object notificationContextObj) {
	
		if (notificationImpl == null) {
			throw new IllegalStateException("NotificationImpl has not been initialized");
		}	

    	if (notificationContextObj instanceof INotificationContext) {
			INotificationContext notificationContext = (INotificationContext) notificationContextObj;
			try {
				return notificationImpl.notify(notificationContext);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		} else {
			throw new IllegalArgumentException("Parameter notificationContextObj should be of type com.tibco.be.ws.notification.INotificationContext");
		}							
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "isNotificationSuccess",
            synopsis = "",
            signature = "boolean isNotificationSuccess(Object notificationStatusObj)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "notificationStatusObj", type = "Object", desc = "Notification Status Object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Notification Status isSuccess flag."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if Notification was successful, false other-wise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
	public static boolean isNotificationSuccess(Object notificationStatusObj) {
		if (notificationStatusObj instanceof NotificationStatus) {
			return ((NotificationStatus)notificationStatusObj).isSuccess();
		} else {
			throw new IllegalArgumentException("Parameter notificationStatusObj should be of type com.tibco.be.ws.notification.NotificationStatus");
		}
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "getErrorMessage",
            synopsis = "",
            signature = "String getErrorMessage(Object notificationStatusObj)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "notificationStatusObj", type = "Object", desc = "Notification Status Object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Error message."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the error message if the status is not successful.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
	public static String getErrorMessage(Object notificationStatusObj) {
		if (notificationStatusObj instanceof NotificationStatus) {
			if (!((NotificationStatus)notificationStatusObj).isSuccess()) {
				return ((NotificationStatus)notificationStatusObj).getErrorMessage();
			}
			return null;
		} else {
			throw new IllegalArgumentException("Parameter notificationStatusObj should be of type com.tibco.be.ws.notification.NotificationStatus");
		}
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "closeConnection",
            synopsis = "",
            signature = "void closeConnection()",
            params = {},
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Closes a connection as per the Implementation class name provided.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )	
    public static void closeConnection() {			
		if (notificationImpl == null) {
			throw new IllegalStateException("NotificationImpl has not been initialized");
		}	
		
    	try {
			notificationImpl.closeConnection();
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}	
	}        
}
