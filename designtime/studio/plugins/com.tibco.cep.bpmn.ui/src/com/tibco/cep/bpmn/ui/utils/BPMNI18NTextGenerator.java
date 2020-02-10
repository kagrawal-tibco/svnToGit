package com.tibco.cep.bpmn.ui.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import com.tibco.cep.diagramming.menu.popup.AbstractDiagramI18NTextGenerator;

public class BPMNI18NTextGenerator extends AbstractDiagramI18NTextGenerator {

	@Override
	protected ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(getResourceBundleId(), Locale.getDefault());
	}
	
	@Override
	protected String getResourceBundleId() {
		return "com.tibco.cep.bpmn.ui.editor.BpmnMessages";
	}

}
