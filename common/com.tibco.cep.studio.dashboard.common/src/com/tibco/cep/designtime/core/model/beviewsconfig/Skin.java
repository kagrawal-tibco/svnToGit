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
 * A representation of the model object '<em><b>Skin</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Contains information which enables skinning in BEViews.
 * 				Contains a default component color set reference and a
 * 				array of all supported color sets Contains the font
 * 				color and component background color , optional
 * 				background gradient color and foreground color
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getDefaultComponentColorSet <em>Default Component Color Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentColorSet <em>Component Color Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getFontColor <em>Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentBackGroundColor <em>Component Back Ground Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentBackGroundGradientEndColor <em>Component Back Ground Gradient End Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentForeGroundColor <em>Component Fore Ground Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationBackGroundColor <em>Visualization Back Ground Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationBackGroundGradientEndColor <em>Visualization Back Ground Gradient End Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationForeGroundColor <em>Visualization Fore Ground Color</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin()
 * @model extendedMetaData="name='BEViewsSkin' kind='elementOnly'"
 * @generated
 */
public interface Skin extends BEViewsElement {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Default Component Color Set</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Component Color Set</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Component Color Set</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_DefaultComponentColorSet()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='defaultComponentColorSet'"
	 * @generated
	 */
	EList<ComponentColorSet> getDefaultComponentColorSet();

	/**
	 * Returns the value of the '<em><b>Component Color Set</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Color Set</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Color Set</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_ComponentColorSet()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='componentColorSet'"
	 * @generated
	 */
	EList<ComponentColorSet> getComponentColorSet();

	/**
	 * Returns the value of the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Color</em>' attribute.
	 * @see #setFontColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_FontColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='fontColor'"
	 * @generated
	 */
	String getFontColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getFontColor <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Color</em>' attribute.
	 * @see #getFontColor()
	 * @generated
	 */
	void setFontColor(String value);

	/**
	 * Returns the value of the '<em><b>Component Back Ground Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Back Ground Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Back Ground Color</em>' attribute.
	 * @see #setComponentBackGroundColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_ComponentBackGroundColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='componentBackGroundColor'"
	 * @generated
	 */
	String getComponentBackGroundColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentBackGroundColor <em>Component Back Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Back Ground Color</em>' attribute.
	 * @see #getComponentBackGroundColor()
	 * @generated
	 */
	void setComponentBackGroundColor(String value);

	/**
	 * Returns the value of the '<em><b>Component Back Ground Gradient End Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Back Ground Gradient End Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Back Ground Gradient End Color</em>' attribute.
	 * @see #setComponentBackGroundGradientEndColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_ComponentBackGroundGradientEndColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='componentBackGroundGradientEndColor'"
	 * @generated
	 */
	String getComponentBackGroundGradientEndColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentBackGroundGradientEndColor <em>Component Back Ground Gradient End Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Back Ground Gradient End Color</em>' attribute.
	 * @see #getComponentBackGroundGradientEndColor()
	 * @generated
	 */
	void setComponentBackGroundGradientEndColor(String value);

	/**
	 * Returns the value of the '<em><b>Component Fore Ground Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Fore Ground Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Fore Ground Color</em>' attribute.
	 * @see #setComponentForeGroundColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_ComponentForeGroundColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='componentForeGroundColor'"
	 * @generated
	 */
	String getComponentForeGroundColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getComponentForeGroundColor <em>Component Fore Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Fore Ground Color</em>' attribute.
	 * @see #getComponentForeGroundColor()
	 * @generated
	 */
	void setComponentForeGroundColor(String value);

	/**
	 * Returns the value of the '<em><b>Visualization Back Ground Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visualization Back Ground Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visualization Back Ground Color</em>' attribute.
	 * @see #setVisualizationBackGroundColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_VisualizationBackGroundColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='visualizationBackGroundColor'"
	 * @generated
	 */
	String getVisualizationBackGroundColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationBackGroundColor <em>Visualization Back Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visualization Back Ground Color</em>' attribute.
	 * @see #getVisualizationBackGroundColor()
	 * @generated
	 */
	void setVisualizationBackGroundColor(String value);

	/**
	 * Returns the value of the '<em><b>Visualization Back Ground Gradient End Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visualization Back Ground Gradient End Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visualization Back Ground Gradient End Color</em>' attribute.
	 * @see #setVisualizationBackGroundGradientEndColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_VisualizationBackGroundGradientEndColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='visualizationBackGroundGradientEndColor'"
	 * @generated
	 */
	String getVisualizationBackGroundGradientEndColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationBackGroundGradientEndColor <em>Visualization Back Ground Gradient End Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visualization Back Ground Gradient End Color</em>' attribute.
	 * @see #getVisualizationBackGroundGradientEndColor()
	 * @generated
	 */
	void setVisualizationBackGroundGradientEndColor(String value);

	/**
	 * Returns the value of the '<em><b>Visualization Fore Ground Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visualization Fore Ground Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visualization Fore Ground Color</em>' attribute.
	 * @see #setVisualizationForeGroundColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSkin_VisualizationForeGroundColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='visualizationForeGroundColor'"
	 * @generated
	 */
	String getVisualizationForeGroundColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Skin#getVisualizationForeGroundColor <em>Visualization Fore Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visualization Fore Ground Color</em>' attribute.
	 * @see #getVisualizationForeGroundColor()
	 * @generated
	 */
	void setVisualizationForeGroundColor(String value);

} // Skin
