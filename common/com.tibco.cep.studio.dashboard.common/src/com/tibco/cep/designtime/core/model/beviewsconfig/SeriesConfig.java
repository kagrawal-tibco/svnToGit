/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Series Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the base for all series configs. A visualization can contain many series configs.
 * 			The concrete subclasses dictate what goes in the series configs.
 * 			DataSource points to MDViewDataSource
 * 			QueryLink points to a MDLanguageContainer
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getToolTip <em>Tool Tip</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getActionRule <em>Action Rule</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getQueryLink <em>Query Link</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getRollOverConfig <em>Roll Over Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getRelatedElement <em>Related Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfig()
 * @model abstract="true"
 *        extendedMetaData="name='SeriesConfig' kind='elementOnly'"
 * @generated
 */
public interface SeriesConfig extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfig_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Tool Tip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tool Tip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tool Tip</em>' attribute.
	 * @see #setToolTip(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfig_ToolTip()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='toolTip'"
	 * @generated
	 */
	String getToolTip();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getToolTip <em>Tool Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tool Tip</em>' attribute.
	 * @see #getToolTip()
	 * @generated
	 */
	void setToolTip(String value);

	/**
	 * Returns the value of the '<em><b>Action Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Rule</em>' containment reference.
	 * @see #setActionRule(ActionRule)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfig_ActionRule()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='dataSource'"
	 * @generated
	 */
	ActionRule getActionRule();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getActionRule <em>Action Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Rule</em>' containment reference.
	 * @see #getActionRule()
	 * @generated
	 */
	void setActionRule(ActionRule value);

	/**
	 * Returns the value of the '<em><b>Query Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query Link</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query Link</em>' containment reference.
	 * @see #setQueryLink(EObject)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfig_QueryLink()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='queryLink'"
	 * @generated
	 */
	EObject getQueryLink();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getQueryLink <em>Query Link</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query Link</em>' containment reference.
	 * @see #getQueryLink()
	 * @generated
	 */
	void setQueryLink(EObject value);

	/**
	 * Returns the value of the '<em><b>Roll Over Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roll Over Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roll Over Config</em>' containment reference.
	 * @see #setRollOverConfig(EObject)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfig_RollOverConfig()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='rollOverConfig'"
	 * @generated
	 */
	EObject getRollOverConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig#getRollOverConfig <em>Roll Over Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Roll Over Config</em>' containment reference.
	 * @see #getRollOverConfig()
	 * @generated
	 */
	void setRollOverConfig(EObject value);

	/**
	 * Returns the value of the '<em><b>Related Element</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Element</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Element</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesConfig_RelatedElement()
	 * @model
	 * @generated
	 */
	EList<BEViewsElement> getRelatedElement();

} // SeriesConfig
