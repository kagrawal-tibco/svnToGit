/**
 * 
 */
package com.tibco.cep.webstudio.client.i18n;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author vpatil
 */
@RemoteServiceRelativePath("locale")
public interface I18nService extends RemoteService {
	
	public static final String GLOBAL_MESSAGES = "GlobalMessages";
	public static final String DT_MESSAGES = "DTMessages";
	public static final String PROCESS_MESSAGES = "ProcessMessages";
	public static final String RMS_MESSAGES = "RMSMessages";
	public static final String DOMAIN_MESSAGES = "DomainMessages";
	public static final String RTI_MESSAGES = "RTIMessages";
	public static final String CUSTOM_SGWT_MESSAGES = "CustomizedSmartgwtMessages";
	
	/**
	 * Fetch the Internationalized messages
	 * 
	 * @param locale
	 * @return
	 */
	public I18nRegistry getMessages(String locale);
}
