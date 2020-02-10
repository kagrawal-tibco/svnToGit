/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Index Properties Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.CompositeIndexPropertiesConfig#getProperty <em>Property</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getCompositeIndexPropertiesConfig()
 * @model extendedMetaData="name='composite-index-properties-type' kind='elementOnly'"
 * @generated
 */
public interface CompositeIndexPropertiesConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.OverrideConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCompositeIndexPropertiesConfig_Property()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='property' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<OverrideConfig> getProperty();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // CompositeIndexPropertiesConfig
