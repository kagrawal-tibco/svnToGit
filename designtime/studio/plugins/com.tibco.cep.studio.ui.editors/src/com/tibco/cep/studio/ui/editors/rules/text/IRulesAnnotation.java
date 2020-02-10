package com.tibco.cep.studio.ui.editors.rules.text;

/**
 * Interface to distinguish between general Annotations
 * and annotations specific to the Rules Editor
 * 
 * @author rhollom
 *
 */
public interface IRulesAnnotation {

	/**
	 * Returns the text associated with this annotation
	 * @return
	 */
	public String getText();
	
	/**
	 * Return the contextual information which caused the error.  
	 * Can be used for quick assist, for example
	 * @return
	 */
	public IProblemContext getProblemContext();
	
	/**
	 * Returns the problem code for this 
	 * @return
	 */
	public int getProblemCode();
}
