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
 * A representation of the model object '<em><b>Mm Alert Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.MmAlertConfig#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmAlertConfig#getSeverity <em>Severity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAlertConfig()
 * @model extendedMetaData="name='mm-alert-type' kind='empty'"
 * @generated
 */
public interface MmAlertConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAlertConfig_Path()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmAlertConfig#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Severity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see #setSeverity(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmAlertConfig_Severity()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='severity'"
	 * @generated
	 */
	String getSeverity();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmAlertConfig#getSeverity <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Severity</em>' attribute.
	 * @see #getSeverity()
	 * @generated
	 */
	void setSeverity(String value);

} // MmAlertConfig
