/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Index Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.CompositeIndexConfig#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.CompositeIndexConfig#getCompositeIndexProperties <em>Composite Index Properties</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getCompositeIndexConfig()
 * @model extendedMetaData="name='composite-index-type' kind='elementOnly'"
 * @generated
 */
public interface CompositeIndexConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' containment reference.
	 * @see #setName(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCompositeIndexConfig_Name()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CompositeIndexConfig#getName <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Composite Index Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Index Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Index Properties</em>' containment reference.
	 * @see #setCompositeIndexProperties(CompositeIndexPropertiesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCompositeIndexConfig_CompositeIndexProperties()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='properties' namespace='##targetNamespace'"
	 * @generated
	 */
	CompositeIndexPropertiesConfig getCompositeIndexProperties();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.CompositeIndexConfig#getCompositeIndexProperties <em>Composite Index Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite Index Properties</em>' containment reference.
	 * @see #getCompositeIndexProperties()
	 * @generated
	 */
	void setCompositeIndexProperties(CompositeIndexPropertiesConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // CompositeIndexConfig
