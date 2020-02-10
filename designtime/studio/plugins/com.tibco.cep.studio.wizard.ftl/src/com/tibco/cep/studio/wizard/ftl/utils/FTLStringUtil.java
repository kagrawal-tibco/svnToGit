package com.tibco.cep.studio.wizard.ftl.utils;

import com.tibco.cep.studio.wizard.ftl.FTLWizardConstants;

public class FTLStringUtil {
	
	public static String formatString(String org){
		return org.replace("-", FTLWizardConstants.ESCAPE_HYPEN);
	}

}
