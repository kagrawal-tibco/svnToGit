/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System Property Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.SystemPropertyConfig#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SystemPropertyConfig#getSystemProperty <em>System Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getSystemPropertyConfig()
 * @model extendedMetaData="name='system-property-type' kind='mixed'"
 * @generated
 */
public interface SystemPropertyConfig extends EObject {
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSystemPropertyConfig_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>System Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System Property</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System Property</em>' attribute.
	 * @see #setSystemProperty(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSystemPropertyConfig_SystemProperty()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='system-property'"
	 * @generated
	 */
	String getSystemProperty();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SystemPropertyConfig#getSystemProperty <em>System Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>System Property</em>' attribute.
	 * @see #getSystemProperty()
	 * @generated
	 */
	void setSystemProperty(String value);

} // SystemPropertyConfig
