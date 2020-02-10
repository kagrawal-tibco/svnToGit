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
 * A representation of the model object '<em><b>Terminal Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.TerminalConfig#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.TerminalConfig#getSysErrRedirect <em>Sys Err Redirect</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.TerminalConfig#getSysOutRedirect <em>Sys Out Redirect</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.TerminalConfig#getEncoding <em>Encoding</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getTerminalConfig()
 * @model extendedMetaData="name='terminal-type' kind='elementOnly'"
 * @generated
 */
public interface TerminalConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' containment reference.
	 * @see #setEnabled(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getTerminalConfig_Enabled()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.TerminalConfig#getEnabled <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' containment reference.
	 * @see #getEnabled()
	 * @generated
	 */
	void setEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Sys Err Redirect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sys Err Redirect</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sys Err Redirect</em>' containment reference.
	 * @see #setSysErrRedirect(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getTerminalConfig_SysErrRedirect()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='sys-err-redirect' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSysErrRedirect();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.TerminalConfig#getSysErrRedirect <em>Sys Err Redirect</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sys Err Redirect</em>' containment reference.
	 * @see #getSysErrRedirect()
	 * @generated
	 */
	void setSysErrRedirect(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Sys Out Redirect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sys Out Redirect</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sys Out Redirect</em>' containment reference.
	 * @see #setSysOutRedirect(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getTerminalConfig_SysOutRedirect()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='sys-out-redirect' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getSysOutRedirect();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.TerminalConfig#getSysOutRedirect <em>Sys Out Redirect</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sys Out Redirect</em>' containment reference.
	 * @see #getSysOutRedirect()
	 * @generated
	 */
	void setSysOutRedirect(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Encoding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Encoding</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Encoding</em>' containment reference.
	 * @see #setEncoding(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getTerminalConfig_Encoding()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='encoding' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEncoding();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.TerminalConfig#getEncoding <em>Encoding</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Encoding</em>' containment reference.
	 * @see #getEncoding()
	 * @generated
	 */
	void setEncoding(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // TerminalConfig
