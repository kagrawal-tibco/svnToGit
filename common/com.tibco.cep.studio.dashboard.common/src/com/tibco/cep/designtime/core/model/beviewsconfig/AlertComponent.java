/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Alert Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent#getThreshold <em>Threshold</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertComponent()
 * @model extendedMetaData="name='AlertComponent' kind='elementOnly'"
 * @generated
 */
public interface AlertComponent extends ClassifierComponent {
	/**
	 * Returns the value of the '<em><b>Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Threshold</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Threshold</em>' attribute.
	 * @see #isSetThreshold()
	 * @see #unsetThreshold()
	 * @see #setThreshold(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertComponent_Threshold()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='threshold'"
	 * @generated
	 */
	int getThreshold();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent#getThreshold <em>Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Threshold</em>' attribute.
	 * @see #isSetThreshold()
	 * @see #unsetThreshold()
	 * @see #getThreshold()
	 * @generated
	 */
	void setThreshold(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent#getThreshold <em>Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetThreshold()
	 * @see #getThreshold()
	 * @see #setThreshold(int)
	 * @generated
	 */
	void unsetThreshold();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertComponent#getThreshold <em>Threshold</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Threshold</em>' attribute is set.
	 * @see #unsetThreshold()
	 * @see #getThreshold()
	 * @see #setThreshold(int)
	 * @generated
	 */
	boolean isSetThreshold();

} // AlertComponent
