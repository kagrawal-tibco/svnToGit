/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Identity Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType#getIsRef <em>Is Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getIdentityType()
 * @model extendedMetaData="name='identity_._type' kind='mixed'"
 * @generated
 */
public interface IdentityType extends EObject {
	/**
     * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Mixed</em>' attribute list.
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getIdentityType_Mixed()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='elementWildcard' name=':mixed'"
     * @generated
     */
	FeatureMap getMixed();

	/**
     * Returns the value of the '<em><b>Is Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Is Ref</em>' attribute.
     * @see #setIsRef(Object)
     * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage#getIdentityType_IsRef()
     * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs"
     *        extendedMetaData="kind='attribute' name='isRef'"
     * @generated
     */
	Object getIsRef();

	/**
     * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType#getIsRef <em>Is Ref</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Ref</em>' attribute.
     * @see #getIsRef()
     * @generated
     */
	void setIsRef(Object value);

} // IdentityType
