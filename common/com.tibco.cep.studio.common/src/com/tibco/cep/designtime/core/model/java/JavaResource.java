/**
 */
package com.tibco.cep.designtime.core.model.java;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.JavaResource#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.JavaResource#getContent <em>Content</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.JavaResource#getExtension <em>Extension</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.java.JavaPackage#getJavaResource()
 * @model
 * @generated
 */
public interface JavaResource extends Entity {
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
	 * @see com.tibco.cep.designtime.core.model.java.JavaPackage#getJavaResource_PackageName()
	 * @model default="null"
	 * @generated
	 */
	String getPackageName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.java.JavaResource#getPackageName <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name</em>' attribute.
	 * @see #getPackageName()
	 * @generated
	 */
	void setPackageName(String value);

	/**
	 * Returns the value of the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' attribute.
	 * @see #setContent(byte[])
	 * @see com.tibco.cep.designtime.core.model.java.JavaPackage#getJavaResource_Content()
	 * @model transient="true"
	 * @generated
	 */
	byte[] getContent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.java.JavaResource#getContent <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' attribute.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(byte[] value);

	/**
	 * Returns the value of the '<em><b>Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension</em>' attribute.
	 * @see #setExtension(String)
	 * @see com.tibco.cep.designtime.core.model.java.JavaPackage#getJavaResource_Extension()
	 * @model
	 * @generated
	 */
	String getExtension();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.java.JavaResource#getExtension <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension</em>' attribute.
	 * @see #getExtension()
	 * @generated
	 */
	void setExtension(String value);

} // JavaResource
