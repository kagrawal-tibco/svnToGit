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
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Represents the component which can be added in a panel
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getHelpText <em>Help Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getComponentColorSetIndex <em>Component Color Set Index</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesColorIndex <em>Series Color Index</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getBackground <em>Background</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getVisualization <em>Visualization</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getLayoutConstraint <em>Layout Constraint</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesConfigGenerator <em>Series Config Generator</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getRelatedElement <em>Related Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent()
 * @model abstract="true"
 *        extendedMetaData="name='Component' kind='elementOnly'"
 * @generated
 */
public interface Component extends BEViewsElement {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Help Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Help Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Help Text</em>' attribute.
	 * @see #setHelpText(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_HelpText()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='helpText'"
	 * @generated
	 */
	String getHelpText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getHelpText <em>Help Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help Text</em>' attribute.
	 * @see #getHelpText()
	 * @generated
	 */
	void setHelpText(String value);

	/**
	 * Returns the value of the '<em><b>Component Color Set Index</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Color Set Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Color Set Index</em>' attribute.
	 * @see #isSetComponentColorSetIndex()
	 * @see #unsetComponentColorSetIndex()
	 * @see #setComponentColorSetIndex(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_ComponentColorSetIndex()
	 * @model default="-1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='componentColorSetIndex'"
	 * @generated
	 */
	int getComponentColorSetIndex();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getComponentColorSetIndex <em>Component Color Set Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Color Set Index</em>' attribute.
	 * @see #isSetComponentColorSetIndex()
	 * @see #unsetComponentColorSetIndex()
	 * @see #getComponentColorSetIndex()
	 * @generated
	 */
	void setComponentColorSetIndex(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getComponentColorSetIndex <em>Component Color Set Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetComponentColorSetIndex()
	 * @see #getComponentColorSetIndex()
	 * @see #setComponentColorSetIndex(int)
	 * @generated
	 */
	void unsetComponentColorSetIndex();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getComponentColorSetIndex <em>Component Color Set Index</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Component Color Set Index</em>' attribute is set.
	 * @see #unsetComponentColorSetIndex()
	 * @see #getComponentColorSetIndex()
	 * @see #setComponentColorSetIndex(int)
	 * @generated
	 */
	boolean isSetComponentColorSetIndex();

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_SeriesColorIndex()
	 * @model default="-1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='seriesColorIndex'"
	 * @generated
	 */
	int getSeriesColorIndex();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesColorIndex <em>Series Color Index</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesColorIndex <em>Series Color Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSeriesColorIndex()
	 * @see #getSeriesColorIndex()
	 * @see #setSeriesColorIndex(int)
	 * @generated
	 */
	void unsetSeriesColorIndex();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getSeriesColorIndex <em>Series Color Index</em>}' attribute is set.
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
	 * Returns the value of the '<em><b>Background</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Background</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Background</em>' containment reference.
	 * @see #setBackground(BackgroundFormat)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_Background()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='background'"
	 * @generated
	 */
	BackgroundFormat getBackground();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getBackground <em>Background</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Background</em>' containment reference.
	 * @see #getBackground()
	 * @generated
	 */
	void setBackground(BackgroundFormat value);

	/**
	 * Returns the value of the '<em><b>Visualization</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.Visualization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visualization</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visualization</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_Visualization()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='visualization'"
	 * @generated
	 */
	EList<Visualization> getVisualization();

	/**
	 * Returns the value of the '<em><b>Layout Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Layout Constraint</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layout Constraint</em>' containment reference.
	 * @see #setLayoutConstraint(LayoutConstraint)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_LayoutConstraint()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='layoutConstraint'"
	 * @generated
	 */
	LayoutConstraint getLayoutConstraint();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Component#getLayoutConstraint <em>Layout Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layout Constraint</em>' containment reference.
	 * @see #getLayoutConstraint()
	 * @generated
	 */
	void setLayoutConstraint(LayoutConstraint value);

	/**
	 * Returns the value of the '<em><b>Series Config Generator</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Series Config Generator</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Series Config Generator</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_SeriesConfigGenerator()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='seriesConfigGenerator'"
	 * @generated
	 */
	EList<SeriesConfigGenerator> getSeriesConfigGenerator();

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponent_RelatedElement()
	 * @model
	 * @generated
	 */
	EList<BEViewsElement> getRelatedElement();

} // Component
