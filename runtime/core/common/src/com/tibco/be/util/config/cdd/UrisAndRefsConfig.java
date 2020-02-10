/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Uris And Refs Config</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getUrisAndRefsConfig()
 * @model abstract="true"
 *        extendedMetaData="name='uris-and-refs-type' kind='empty'"
 * @generated
 */
public interface UrisAndRefsConfig extends ArtifactConfig {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" many="false"
	 * @generated
	 */
	EList<String> getAllUris();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" many="false"
	 * @generated
	 */
	EList<? extends UrisAndRefsConfig> getRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" many="false"
	 * @generated
	 */
	EList<String> getUri();
	 
} // UrisAndRefsConfig
