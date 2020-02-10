/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;

import java.io.Serializable;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @extends Serializable
 * @generated
 */
public interface Binding extends Serializable, IRuleTemplateInstanceObject {
	
	void setId(String id);
    
	void setValue(String value);
    
	String getId();
    
	String getValue();
    
}