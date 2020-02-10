/**
 */
package com.tibco.cep.studio.core.index.model;

import com.tibco.cep.designtime.core.model.java.JavaResource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Resource Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.JavaResourceElement#getJavaResource <em>Java Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getJavaResourceElement()
 * @model
 * @generated
 */
public interface JavaResourceElement extends EntityElement {
	/**
	 * Returns the value of the '<em><b>Java Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Resource</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Resource</em>' containment reference.
	 * @see #setJavaResource(JavaResource)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getJavaResourceElement_JavaResource()
	 * @model containment="true" required="true"
	 * @generated
	 */
	JavaResource getJavaResource();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.JavaResourceElement#getJavaResource <em>Java Resource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Resource</em>' containment reference.
	 * @see #getJavaResource()
	 * @generated
	 */
	void setJavaResource(JavaResource value);

} // JavaResourceElement
