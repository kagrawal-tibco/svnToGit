/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Indexes Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.IndexesConfig#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getIndexesConfig()
 * @model extendedMetaData="name='indexes-type' kind='elementOnly'"
 * @generated
 */
public interface IndexesConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Index</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.IndexConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getIndexesConfig_Index()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='index' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<IndexConfig> getIndex();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // IndexesConfig
