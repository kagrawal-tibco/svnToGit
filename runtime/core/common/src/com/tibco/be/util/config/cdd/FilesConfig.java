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
 * A representation of the model object '<em><b>Files Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.FilesConfig#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.FilesConfig#getDir <em>Dir</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.FilesConfig#getMaxNumber <em>Max Number</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.FilesConfig#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.FilesConfig#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.FilesConfig#getAppend <em>Append</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getFilesConfig()
 * @model extendedMetaData="name='files-type' kind='elementOnly'"
 * @generated
 */
public interface FilesConfig extends EObject {
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFilesConfig_Enabled()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.FilesConfig#getEnabled <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' containment reference.
	 * @see #getEnabled()
	 * @generated
	 */
	void setEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Dir</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dir</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dir</em>' containment reference.
	 * @see #setDir(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFilesConfig_Dir()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='dir' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getDir();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.FilesConfig#getDir <em>Dir</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dir</em>' containment reference.
	 * @see #getDir()
	 * @generated
	 */
	void setDir(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Number</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Number</em>' containment reference.
	 * @see #setMaxNumber(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFilesConfig_MaxNumber()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='max-number' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxNumber();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.FilesConfig#getMaxNumber <em>Max Number</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Number</em>' containment reference.
	 * @see #getMaxNumber()
	 * @generated
	 */
	void setMaxNumber(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Size</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Size</em>' containment reference.
	 * @see #setMaxSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFilesConfig_MaxSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='max-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.FilesConfig#getMaxSize <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Size</em>' containment reference.
	 * @see #getMaxSize()
	 * @generated
	 */
	void setMaxSize(OverrideConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFilesConfig_Name()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.FilesConfig#getName <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Append</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Append</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Append</em>' containment reference.
	 * @see #setAppend(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getFilesConfig_Append()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='append' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getAppend();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.FilesConfig#getAppend <em>Append</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Append</em>' containment reference.
	 * @see #getAppend()
	 * @generated
	 */
	void setAppend(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // FilesConfig
