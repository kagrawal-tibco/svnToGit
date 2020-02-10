/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getDataSource <em>Data Source</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThreshold <em>Threshold</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThresholdUnit <em>Threshold Unit</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getAlert <em>Alert</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getDrillableFields <em>Drillable Fields</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getParams <em>Params</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionRule()
 * @model
 * @generated
 */
public interface ActionRule extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Data Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Source</em>' reference.
	 * @see #setDataSource(DataSource)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionRule_DataSource()
	 * @model
	 * @generated
	 */
	DataSource getDataSource();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getDataSource <em>Data Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Source</em>' reference.
	 * @see #getDataSource()
	 * @generated
	 */
	void setDataSource(DataSource value);

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionRule_Threshold()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='threshold'"
	 * @generated
	 */
	int getThreshold();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThreshold <em>Threshold</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThreshold <em>Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetThreshold()
	 * @see #getThreshold()
	 * @see #setThreshold(int)
	 * @generated
	 */
	void unsetThreshold();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThreshold <em>Threshold</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Threshold</em>' attribute is set.
	 * @see #unsetThreshold()
	 * @see #getThreshold()
	 * @see #setThreshold(int)
	 * @generated
	 */
	boolean isSetThreshold();

	/**
	 * Returns the value of the '<em><b>Threshold Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Threshold Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Threshold Unit</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum
	 * @see #isSetThresholdUnit()
	 * @see #unsetThresholdUnit()
	 * @see #setThresholdUnit(ThresholdUnitEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionRule_ThresholdUnit()
	 * @model unsettable="true" required="true"
	 * @generated
	 */
	ThresholdUnitEnum getThresholdUnit();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThresholdUnit <em>Threshold Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Threshold Unit</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum
	 * @see #isSetThresholdUnit()
	 * @see #unsetThresholdUnit()
	 * @see #getThresholdUnit()
	 * @generated
	 */
	void setThresholdUnit(ThresholdUnitEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThresholdUnit <em>Threshold Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetThresholdUnit()
	 * @see #getThresholdUnit()
	 * @see #setThresholdUnit(ThresholdUnitEnum)
	 * @generated
	 */
	void unsetThresholdUnit();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule#getThresholdUnit <em>Threshold Unit</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Threshold Unit</em>' attribute is set.
	 * @see #unsetThresholdUnit()
	 * @see #getThresholdUnit()
	 * @see #setThresholdUnit(ThresholdUnitEnum)
	 * @generated
	 */
	boolean isSetThresholdUnit();

	/**
	 * Returns the value of the '<em><b>Alert</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.Alert}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alert</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alert</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionRule_Alert()
	 * @model containment="true"
	 * @generated
	 */
	EList<Alert> getAlert();

	/**
	 * Returns the value of the '<em><b>Drillable Fields</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.element.PropertyDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Drillable Fields</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Drillable Fields</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionRule_DrillableFields()
	 * @model
	 * @generated
	 */
	EList<PropertyDefinition> getDrillableFields();

	/**
	 * Returns the value of the '<em><b>Params</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Params</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Params</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionRule_Params()
	 * @model containment="true"
	 * @generated
	 */
	EList<QueryParam> getParams();

} // ActionRule
