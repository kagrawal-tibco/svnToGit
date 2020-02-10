/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;

import java.io.Serializable;
import java.util.List;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DisplayModel</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @extends Serializable
 * @generated
 */
public interface DisplayModel extends Serializable, IRuleTemplateInstanceObject {
	
	void setEntity(String entity);

	void setDisplayText(String value);
	
	void setDisplayProperties(List<DisplayProperty> props);
	
	String getDisplayText();
	
	String getEntity();
    
	List<DisplayProperty> getDisplayProperties();
    
}