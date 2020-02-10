package com.tibco.cep.studio.tester.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;

import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;

public class TesterPreferenceInitializer extends AbstractPreferenceInitializer {

	public TesterPreferenceInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore prefStore = StudioTesterUIPlugin.getDefault().getPreferenceStore();
//		prefStore.setDefault(TesterPreferenceConstants.AUTO_DEPLOY_BEFORE_TEST, true);
		
		prefStore.setDefault(TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_BACK_GROUND_COLOR,"255,184,170");
		prefStore.setDefault(TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_FORE_GROUND_COLOR,"128,128,255");
		prefStore.setDefault(TesterPreferenceConstants.TEST_RESULT_NEW_INSTANCE_BACK_GROUND_COLOR,"255,249,193");
		prefStore.setDefault(TesterPreferenceConstants.TEST_RESULT_NEW_INSTANCE_FORE_GROUND_COLOR,"0,0,0");
		
		String defaultFont = "1|Tahoma|11.0|1|WINDOWS|1|0|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma";
		prefStore.setDefault(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT, defaultFont);
		
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_COLOR_DEBUG, "0,0,255");
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_COLOR_INFO,  "92,0,0");
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_COLOR_WARN,  "0,200,200");
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_COLOR_ERROR, "255,0,0");
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_COLOR_FATAL, "255,0,0");
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_COLOR_STDOUT,"200,200,200");
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_COLOR_STDERR, "200,200,200");

		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_STYLE_DEBUG, 0);
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_STYLE_INFO,  0);
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_STYLE_WARN,  0);
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_STYLE_ERROR, SWT.BOLD);
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_STYLE_FATAL, 0);
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_STYLE_STDOUT,SWT.ITALIC);
		prefStore.setDefault(TesterPreferenceConstants.CONSOLE_STYLE_STDERR,SWT.ITALIC|SWT.BOLD);
	}

}