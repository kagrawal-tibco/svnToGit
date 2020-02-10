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
 * A representation of the model object '<em><b>Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the base for all visualizations.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getBackground <em>Background</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesColorIndex <em>Series Color Index</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getAction <em>Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesConfig <em>Series Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getRelatedElement <em>Related Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualization()
 * @model abstract="true"
 *        extendedMetaData="name='Visualization' kind='elementOnly'"
 * @generated
 */
public interface Visualization extends BEViewsElement {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualization_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Background</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Background</em>' containment reference.
	 * @see #setBackground(BackgroundFormat)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualization_Background()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='background'"
	 * @generated
	 */
	BackgroundFormat getBackground();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getBackground <em>Background</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Background</em>' containment reference.
	 * @see #getBackground()
	 * @generated
	 */
	void setBackground(BackgroundFormat value);

	/**
	 * Returns the value of the '<em><b>Series Color Index</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Series Color Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Series Color Index</em>' attribute.
	 * @see #isSetSeriesColorIndex()
	 * @see #unsetSeriesColorIndex()
	 * @see #setSeriesColorIndex(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualization_SeriesColorIndex()
	 * @model default="-1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='seriesColorIndex'"
	 * @generated
	 */
	int getSeriesColorIndex();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesColorIndex <em>Series Color Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Series Color Index</em>' attribute.
	 * @see #isSetSeriesColorIndex()
	 * @see #unsetSeriesColorIndex()
	 * @see #getSeriesColorIndex()
	 * @generated
	 */
	void setSeriesColorIndex(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesColorIndex <em>Series Color Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSeriesColorIndex()
	 * @see #getSeriesColorIndex()
	 * @see #setSeriesColorIndex(int)
	 * @generated
	 */
	void unsetSeriesColorIndex();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization#getSeriesColorIndex <em>Series Color Index</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Series Color Index</em>' attribute is set.
	 * @see #unsetSeriesColorIndex()
	 * @see #getSeriesColorIndex()
	 * @see #setSeriesColorIndex(int)
	 * @generated
	 */
	boolean isSetSeriesColorIndex();

	/**
	 * Returns the value of the '<em><b>Action</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualization_Action()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='action'"
	 * @generated
	 */
	EList<ActionDefinition> getAction();

	/**
	 * Returns the value of the '<em><b>Series Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Series Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Series Config</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualization_SeriesConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='seriesConfig'"
	 * @generated
	 */
	EList<SeriesConfig> getSeriesConfig();

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualization_RelatedElement()
	 * @model
	 * @generated
	 */
	EList<BEViewsElement> getRelatedElement();

} // Visualization
