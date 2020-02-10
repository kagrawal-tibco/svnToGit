package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleContent;

/**
 * @ *
 */
public class SynXSDSimpleContent extends SynXSDPropertyProvider implements ISynXSDSimpleContent {

	private ISynXSDAnnotation annotation;

	/**
	 * @return Returns the annotation.
	 */
	public ISynXSDAnnotation getAnnotation() {
		return annotation;
	}

	/**
	 * @param annotation The annotation to set.
	 */
	public void setAnnotation(ISynXSDAnnotation annotation) {
		this.annotation = annotation;
	}

	/**
     *
     */
	public SynXSDSimpleContent() {
		super();
	}

}
