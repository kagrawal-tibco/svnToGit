/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Row</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getRow()
 * @model extendedMetaData="name='row_._type' kind='elementOnly'"
 * @generated
 */
public interface Row extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getRow_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='JNDIPropNameCol' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(Object)
	 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getRow_Type()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.JndiPropertyTypeOrGvs" required="true"
	 *        extendedMetaData="kind='element' name='JNDIPropTypeCol' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getType();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(Object value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getRow_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='JNDIPropValueCol' namespace='##targetNamespace'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // Row
