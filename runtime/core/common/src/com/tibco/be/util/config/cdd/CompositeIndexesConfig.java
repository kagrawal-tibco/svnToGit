/**
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Indexes Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.CompositeIndexesConfig#getCompositeIndex <em>Composite Index</em>}</li>
 * </ul>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getCompositeIndexesConfig()
 * @model extendedMetaData="name='composite-indexes-type' kind='elementOnly'"
 * @generated
 */
public interface CompositeIndexesConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Composite Index</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.CompositeIndexConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Index</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Index</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getCompositeIndexesConfig_CompositeIndex()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='composite-index' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<CompositeIndexConfig> getCompositeIndex();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // CompositeIndexesConfig
