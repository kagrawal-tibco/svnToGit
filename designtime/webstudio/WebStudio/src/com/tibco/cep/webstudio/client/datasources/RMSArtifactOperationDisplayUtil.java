/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;

/**
 * @author apsharma
 * 
 */
public class RMSArtifactOperationDisplayUtil {

	private static RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.RMS_MESSAGES);

	public static String getDisplayName(Object value) {
		String displayName = null;  
		if (value instanceof String && !((String) value).trim().isEmpty()) {
			if ("Create".equals(value) || "Add".equals(value)
					|| "Added".equals(value)) {
				displayName =  rmsMsgBundle.rms_artifactOperation_added();
			} else if (value.equals("Modify") || value.equals("Modified") ) {
				displayName =  rmsMsgBundle.rms_artifactOperation_modified();
			} else if (value.equals("Delete") || value.equals("Deleted")) {
				displayName =  rmsMsgBundle.rms_artifactOperation_deleted();
			} else if ("Committed".equals(value)) {
				displayName =  rmsMsgBundle.rms_artifactStatus_committed();
			} else if (value.equals("Approve")) {
				displayName = rmsMsgBundle.rms_artifactStatus_approve();
			} else if (value.equals("Reject")) {
				displayName = rmsMsgBundle.rms_artifactStatus_reject();
			} else if (value.equals("BuildAndDeploy")) {
				displayName = rmsMsgBundle.rms_artifactStatus_buildanddeploy();
			} else {
				displayName = value.toString();
			}
		}
		return displayName;
	}
}
