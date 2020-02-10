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
 * A representation of the model object '<em><b>Line Layout Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.LineLayoutConfig#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LineLayoutConfig#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.LineLayoutConfig#getArg <em>Arg</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getLineLayoutConfig()
 * @model extendedMetaData="name='line-layout-type' kind='elementOnly'"
 * @generated
 */
public interface LineLayoutConfig extends EObject {
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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLineLayoutConfig_Enabled()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LineLayoutConfig#getEnabled <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' containment reference.
	 * @see #getEnabled()
	 * @generated
	 */
	void setEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' containment reference.
	 * @see #setClass(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLineLayoutConfig_Class()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='class' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getClass_();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.LineLayoutConfig#getClass_ <em>Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' containment reference.
	 * @see #getClass_()
	 * @generated
	 */
	void setClass(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Arg</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.OverrideConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arg</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arg</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getLineLayoutConfig_Arg()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='arg' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<OverrideConfig> getArg();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // LineLayoutConfig
