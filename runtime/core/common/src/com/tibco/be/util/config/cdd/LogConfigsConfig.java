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
 * A representation of the model object '<em><b>Log Configs Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LogConfigsConfig#getLogConfig <em>Log Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLogConfigsConfig()
 * @model extendedMetaData="name='log-configs-type' kind='elementOnly'"
 * @generated
 */
public interface LogConfigsConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Log Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.LogConfigConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Log Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Log Config</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLogConfigsConfig_LogConfig()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='log-config' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<LogConfigConfig> getLogConfig();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // LogConfigsConfig
