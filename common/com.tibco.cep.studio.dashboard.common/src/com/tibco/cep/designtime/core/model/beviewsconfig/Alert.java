/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Alert</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Alert#getAction <em>Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Alert#isEnabled <em>Enabled</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlert()
 * @model
 * @generated
 */
public interface Alert extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Action</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertAction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlert_Action()
	 * @model containment="true"
	 * @generated
	 */
	EList<AlertAction> getAction();

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlert_Enabled()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Alert#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
	void setEnabled(boolean value);

} // Alert
