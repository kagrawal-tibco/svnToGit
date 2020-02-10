package com.tibco.cep.bpmn.ui;

import com.tibco.xml.schema.SmSequenceType;

/**
 * 
 * @author sasahoo
 *
 */
public interface IProcessXPathValidate {

	/**
	 * 
	 * @param xtype
	 * @return
	 */
	boolean isValid(SmSequenceType xtype);
	
	/**
	 * 
	 * @return
	 */
	String getExpectedType();
	
}
