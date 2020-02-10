/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>BE Views Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             The root of all BE views configuration elements. 
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getOriginalElementIdentifier <em>Original Element Identifier</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getOriginalElementVersion <em>Original Element Version</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBEViewsElement()
 * @model extendedMetaData="name='BEViewsElement' kind='elementOnly'"
 * @generated
 */
public interface BEViewsElement extends Entity {
	/**
	 * Returns the value of the '<em><b>Original Element Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Original Element Identifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Element Identifier</em>' attribute.
	 * @see #setOriginalElementIdentifier(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBEViewsElement_OriginalElementIdentifier()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getOriginalElementIdentifier();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getOriginalElementIdentifier <em>Original Element Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Element Identifier</em>' attribute.
	 * @see #getOriginalElementIdentifier()
	 * @generated
	 */
	void setOriginalElementIdentifier(String value);

	/**
	 * Returns the value of the '<em><b>Original Element Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Original Element Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Element Version</em>' attribute.
	 * @see #setOriginalElementVersion(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBEViewsElement_OriginalElementVersion()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getOriginalElementVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getOriginalElementVersion <em>Original Element Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Element Version</em>' attribute.
	 * @see #getOriginalElementVersion()
	 * @generated
	 */
	void setOriginalElementVersion(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBEViewsElement_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // BEViewsElement
