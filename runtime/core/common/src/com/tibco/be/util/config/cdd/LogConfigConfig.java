/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Log Config Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LogConfigConfig#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LogConfigConfig#getRoles <em>Roles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LogConfigConfig#getLineLayout <em>Line Layout</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LogConfigConfig#getTerminal <em>Terminal</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LogConfigConfig#getFiles <em>Files</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLogConfigConfig()
 * @model extendedMetaData="name='log-config-type' kind='elementOnly'"
 * @generated
 */
public interface LogConfigConfig extends ArtifactConfig {
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLogConfigConfig_Enabled()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getEnabled <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' containment reference.
	 * @see #getEnabled()
	 * @generated
	 */
	void setEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Roles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roles</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roles</em>' containment reference.
	 * @see #setRoles(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLogConfigConfig_Roles()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='roles' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getRoles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getRoles <em>Roles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Roles</em>' containment reference.
	 * @see #getRoles()
	 * @generated
	 */
	void setRoles(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Line Layout</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Layout</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Layout</em>' containment reference.
	 * @see #setLineLayout(LineLayoutConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLogConfigConfig_LineLayout()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='line-layout' namespace='##targetNamespace'"
	 * @generated
	 */
	LineLayoutConfig getLineLayout();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getLineLayout <em>Line Layout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Layout</em>' containment reference.
	 * @see #getLineLayout()
	 * @generated
	 */
	void setLineLayout(LineLayoutConfig value);

	/**
	 * Returns the value of the '<em><b>Terminal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terminal</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terminal</em>' containment reference.
	 * @see #setTerminal(TerminalConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLogConfigConfig_Terminal()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='terminal' namespace='##targetNamespace'"
	 * @generated
	 */
	TerminalConfig getTerminal();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getTerminal <em>Terminal</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terminal</em>' containment reference.
	 * @see #getTerminal()
	 * @generated
	 */
	void setTerminal(TerminalConfig value);

	/**
	 * Returns the value of the '<em><b>Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Files</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Files</em>' containment reference.
	 * @see #setFiles(FilesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLogConfigConfig_Files()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='files' namespace='##targetNamespace'"
	 * @generated
	 */
	FilesConfig getFiles();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LogConfigConfig#getFiles <em>Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Files</em>' containment reference.
	 * @see #getFiles()
	 * @generated
	 */
	void setFiles(FilesConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // LogConfigConfig
