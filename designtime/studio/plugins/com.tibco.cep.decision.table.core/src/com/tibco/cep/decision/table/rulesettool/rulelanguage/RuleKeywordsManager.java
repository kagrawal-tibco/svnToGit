package com.tibco.cep.decision.table.rulesettool.rulelanguage;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


public class RuleKeywordsManager {
	private static final String PRIORITY = "priority";
	private static final String EXTENSION_POINT = "com.tibco.cep.ui.rulelanguage.core.ruleKeyword";
	private static final String CLASS = "class";
	private static RuleKeywords sKeywords = null;
	private static int sPriority = -1;
	
	
	private RuleKeywordsManager(){
	}
	private static void initialize() {
		if( sKeywords != null ) {
			return;
		}
		IConfigurationElement[] cfg = Platform.getExtensionRegistry()
		.getConfigurationElementsFor(EXTENSION_POINT);
		for (int i = 0; i < cfg.length; i++) {
			if( cfg[i].getName().equals("keywords")) {
				int priority = getPriority(cfg[i]);
				if( sPriority ==  -1 || sPriority < priority ) {
					try {
						sKeywords = (RuleKeywords) cfg[i].createExecutableExtension(CLASS);
						sPriority = priority;
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if( sKeywords == null ) {
			sKeywords = new RuleKeywords();
		}
	}
	private static int getPriority(IConfigurationElement config) {
		String priority = config.getAttribute(PRIORITY);
		if (priority == null) {
			return 0;
		}
		try {
			int parseInt = Integer.parseInt(priority);
			return parseInt;
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	public static String[] getKeywords() {
		initialize();
		return sKeywords.getKeywords();
	}

	// gets the keywords from RuleKeywords.
	// at this point of time, it only returns MODULE type keywords
	public static String[] getKeywords(int type) {
		initialize();
		return sKeywords.getKeywords(type);
	}

}
