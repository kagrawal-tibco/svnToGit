/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mm Execute Command Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.MmExecuteCommandConfig#getCommand <em>Command</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getMmExecuteCommandConfig()
 * @model extendedMetaData="name='mm-execute-command-type' kind='empty'"
 * @generated
 */
public interface MmExecuteCommandConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Command</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Command</em>' attribute.
	 * @see #setCommand(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmExecuteCommandConfig_Command()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='command'"
	 * @generated
	 */
	String getCommand();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmExecuteCommandConfig#getCommand <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Command</em>' attribute.
	 * @see #getCommand()
	 * @generated
	 */
	void setCommand(String value);

} // MmExecuteCommandConfig
