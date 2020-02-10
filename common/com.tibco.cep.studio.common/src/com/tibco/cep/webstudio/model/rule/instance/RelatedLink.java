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
 * A representation of the model object '<em><b>Related Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.RelatedLink#getLinkText <em>Link Text</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface RelatedLink extends Serializable, IRuleTemplateInstanceObject {
	
	public final int LINK_TEXT_FEATURE_ID = 0;

	/**
	 * Returns the value of the '<em><b>Link Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Text</em>' attribute.
	 * @see #setLinkText(String)
	 * @generated
	 */
	String getLinkText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.RelatedLink#getLinkText <em>Link Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link Text</em>' attribute.
	 * @see #getLinkText()
	 * @generated
	 */
	void setLinkText(String value);
	
	String getLinkType();
	
	void setLinkType(String type);

} // RelatedLink
