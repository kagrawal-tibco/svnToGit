/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Security Config</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getSecurityConfig()
 * @model abstract="true"
 *        extendedMetaData="name='security-config-type' kind='elementOnly'"
 * @generated
 */
public interface SecurityConfig extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // SecurityConfig
