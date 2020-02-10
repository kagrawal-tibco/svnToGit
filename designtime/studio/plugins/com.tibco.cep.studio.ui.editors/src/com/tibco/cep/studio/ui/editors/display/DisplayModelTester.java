package com.tibco.cep.studio.ui.editors.display;

import java.util.Locale;
import java.util.ResourceBundle;

import com.tibco.cep.studio.ui.util.DisplayModelControl;

public class DisplayModelTester {
	
	public static void main(String[] args) {
		String baseName = "com.tibco.cep.studio.ui.editors.display.Applicant";
		String key = "CreditLimit.displayText";
		DisplayModelControl control = new DisplayModelControl();
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, control);
		String dt = bundle.getString(key);
		System.out.println("Credit Limit Translation for default locale: "+dt);
		
		Locale targetLocale = new Locale("fr");
		dt = ResourceBundle.getBundle(baseName, targetLocale, control).getString(key);
		System.out.println("Credit Limit Translation for locale "+targetLocale.toString()+": "+dt);
		
		targetLocale = new Locale("de", "", "");
		dt = ResourceBundle.getBundle(baseName, targetLocale, control).getString(key);
		System.out.println("Credit Limit Translation for locale "+targetLocale.toString()+": "+dt);
		
		targetLocale = new Locale("de", "", "formal");
		dt = ResourceBundle.getBundle(baseName, targetLocale, control).getString(key);
		System.out.println("Credit Limit Translation for locale "+targetLocale.toString()+": "+dt);
		
	}

}
