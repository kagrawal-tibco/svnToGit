/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.java;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.JavaSource#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.JavaSource#getFullSourceText <em>Full Source Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.java.JavaPackage#getJavaSource()
 * @model
 * @generated
 */
public interface JavaSource extends Entity {

	/**
	 * Returns the value of the '<em><b>Package Name</b></em>' attribute.
	 * The default value is <code>"null"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Name</em>' attribute.
	 * @see #setPackageName(String)
	 * @see com.tibco.cep.designtime.core.model.java.JavaPackage#getJavaSource_PackageName()
	 * @model default="null"
	 * @generated
	 */
	String getPackageName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.java.JavaSource#getPackageName <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name</em>' attribute.
	 * @see #getPackageName()
	 * @generated
	 */
	void setPackageName(String value);

	/**
	 * Returns the value of the '<em><b>Full Source Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Full Source Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Full Source Text</em>' attribute.
	 * @see #setFullSourceText(byte[])
	 * @see com.tibco.cep.designtime.core.model.java.JavaPackage#getJavaSource_FullSourceText()
	 * @model transient="true"
	 * @generated
	 */
	byte[] getFullSourceText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.java.JavaSource#getFullSourceText <em>Full Source Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Full Source Text</em>' attribute.
	 * @see #getFullSourceText()
	 * @generated
	 */
	void setFullSourceText(byte[] value);
} // JavaSource
