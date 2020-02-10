package com.tibco.cep.studio.core.validation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic.Kind;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractStudioAnnotationProcessor extends AbstractProcessor {

	protected ProcessingEnvironment processingEnvironment;
	
	public static final String PUBLIC_MODIFIER = "PUBLIC";//$NON-NLS-N$
	public static final String STATIC_MODIFIER = "STATIC";//$NON-NLS-N$
	public static final String ARRAY_SUFFIX = "[]";//$NON-NLS-N$
	
	protected String uri = "";


	/* (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#init(javax.annotation.processing.ProcessingEnvironment)
	 */
	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
		this.processingEnvironment = processingEnvironment;
	}
	
	/**
	 * @param emn
	 * @param mod
	 * @return
	 */
	protected boolean containsModifier(Element emn, String mod) {
		for (Modifier m: emn.getModifiers()) {
			if(m.name().equalsIgnoreCase(mod)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param msg
	 * @param element
	 */
	protected void reportError(String msg, Element element) {
		processingEnvironment.getMessager().printMessage(Kind.ERROR, msg, element);
	}

	/**
	 * @param msg
	 * @param element
	 */
	protected void reportWarning(String msg, Element element) {
		processingEnvironment.getMessager().printMessage(Kind.WARNING, msg, element);
	}
	
}
