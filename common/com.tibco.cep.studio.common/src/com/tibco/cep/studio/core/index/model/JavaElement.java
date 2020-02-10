/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import com.tibco.cep.designtime.core.model.java.JavaSource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.JavaElement#getJavaSource <em>Java Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getJavaElement()
 * @model
 * @generated
 */
public interface JavaElement extends EntityElement {

	/**
	 * Returns the value of the '<em><b>Java Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Source</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Source</em>' containment reference.
	 * @see #setJavaSource(JavaSource)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getJavaElement_JavaSource()
	 * @model containment="true" required="true"
	 * @generated
	 */
	JavaSource getJavaSource();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.JavaElement#getJavaSource <em>Java Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Source</em>' containment reference.
	 * @see #getJavaSource()
	 * @generated
	 */
	void setJavaSource(JavaSource value);

} // JavaElement
