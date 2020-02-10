/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Series Config Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator#getField <em>Field</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfigGenerator()
 * @model extendedMetaData="name='SeriesConfigGenerator' kind='elementOnly'"
 * @generated
 */
public interface SeriesConfigGenerator extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Field</em>' containment reference.
	 * @see #setField(EObject)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfigGenerator_Field()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='field'"
	 * @generated
	 */
	EObject getField();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator#getField <em>Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Field</em>' containment reference.
	 * @see #getField()
	 * @generated
	 */
	void setField(EObject value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' attribute.
	 * @see #setCondition(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfigGenerator_Condition()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='condition'"
	 * @generated
	 */
	String getCondition();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator#getCondition <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' attribute.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(String value);

} // SeriesConfigGenerator
