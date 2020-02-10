/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Chart Category Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             Represents the configuration for category axis in a chart
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRelativePosition <em>Relative Position</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getPlacement <em>Placement</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRotation <em>Rotation</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getSkipFactor <em>Skip Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartCategoryGuidelineConfig()
 * @model extendedMetaData="name='ChartCategoryGuidelineConfig' kind='elementOnly'"
 * @generated
 */
public interface ChartCategoryGuidelineConfig extends CategoryGuidelineConfig {
	/**
	 * Returns the value of the '<em><b>Relative Position</b></em>' attribute.
	 * The default value is <code>"below"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relative Position</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relative Position</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum
	 * @see #isSetRelativePosition()
	 * @see #unsetRelativePosition()
	 * @see #setRelativePosition(RelativeAxisPositionEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartCategoryGuidelineConfig_RelativePosition()
	 * @model default="below" unsettable="true"
	 *        extendedMetaData="kind='element' name='relativePosition'"
	 * @generated
	 */
	RelativeAxisPositionEnum getRelativePosition();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRelativePosition <em>Relative Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relative Position</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum
	 * @see #isSetRelativePosition()
	 * @see #unsetRelativePosition()
	 * @see #getRelativePosition()
	 * @generated
	 */
	void setRelativePosition(RelativeAxisPositionEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRelativePosition <em>Relative Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRelativePosition()
	 * @see #getRelativePosition()
	 * @see #setRelativePosition(RelativeAxisPositionEnum)
	 * @generated
	 */
	void unsetRelativePosition();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRelativePosition <em>Relative Position</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Relative Position</em>' attribute is set.
	 * @see #unsetRelativePosition()
	 * @see #getRelativePosition()
	 * @see #setRelativePosition(RelativeAxisPositionEnum)
	 * @generated
	 */
	boolean isSetRelativePosition();

	/**
	 * Returns the value of the '<em><b>Placement</b></em>' attribute.
	 * The default value is <code>"automatic"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Placement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Placement</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum
	 * @see #isSetPlacement()
	 * @see #unsetPlacement()
	 * @see #setPlacement(PlacementEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartCategoryGuidelineConfig_Placement()
	 * @model default="automatic" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='placement'"
	 * @generated
	 */
	PlacementEnum getPlacement();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getPlacement <em>Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Placement</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum
	 * @see #isSetPlacement()
	 * @see #unsetPlacement()
	 * @see #getPlacement()
	 * @generated
	 */
	void setPlacement(PlacementEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getPlacement <em>Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPlacement()
	 * @see #getPlacement()
	 * @see #setPlacement(PlacementEnum)
	 * @generated
	 */
	void unsetPlacement();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getPlacement <em>Placement</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Placement</em>' attribute is set.
	 * @see #unsetPlacement()
	 * @see #getPlacement()
	 * @see #setPlacement(PlacementEnum)
	 * @generated
	 */
	boolean isSetPlacement();

	/**
	 * Returns the value of the '<em><b>Rotation</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rotation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rotation</em>' attribute.
	 * @see #isSetRotation()
	 * @see #unsetRotation()
	 * @see #setRotation(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartCategoryGuidelineConfig_Rotation()
	 * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='rotation'"
	 * @generated
	 */
	int getRotation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRotation <em>Rotation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rotation</em>' attribute.
	 * @see #isSetRotation()
	 * @see #unsetRotation()
	 * @see #getRotation()
	 * @generated
	 */
	void setRotation(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRotation <em>Rotation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRotation()
	 * @see #getRotation()
	 * @see #setRotation(int)
	 * @generated
	 */
	void unsetRotation();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getRotation <em>Rotation</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Rotation</em>' attribute is set.
	 * @see #unsetRotation()
	 * @see #getRotation()
	 * @see #setRotation(int)
	 * @generated
	 */
	boolean isSetRotation();

	/**
	 * Returns the value of the '<em><b>Skip Factor</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Skip Factor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Skip Factor</em>' attribute.
	 * @see #isSetSkipFactor()
	 * @see #unsetSkipFactor()
	 * @see #setSkipFactor(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartCategoryGuidelineConfig_SkipFactor()
	 * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='skipFactor'"
	 * @generated
	 */
	int getSkipFactor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getSkipFactor <em>Skip Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Skip Factor</em>' attribute.
	 * @see #isSetSkipFactor()
	 * @see #unsetSkipFactor()
	 * @see #getSkipFactor()
	 * @generated
	 */
	void setSkipFactor(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getSkipFactor <em>Skip Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSkipFactor()
	 * @see #getSkipFactor()
	 * @see #setSkipFactor(int)
	 * @generated
	 */
	void unsetSkipFactor();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig#getSkipFactor <em>Skip Factor</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Skip Factor</em>' attribute is set.
	 * @see #unsetSkipFactor()
	 * @see #getSkipFactor()
	 * @see #setSkipFactor(int)
	 * @generated
	 */
	boolean isSetSkipFactor();

} // ChartCategoryGuidelineConfig
