/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Chart Value Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             Represents the configuration for value axis in a chart
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getLabelFormatStyle <em>Label Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getHeaderName <em>Header Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getRelativePosition <em>Relative Position</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getScale <em>Scale</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getDivision <em>Division</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartValueGuidelineConfig()
 * @model extendedMetaData="name='ChartValueGuidelineConfig' kind='elementOnly'"
 * @generated
 */
public interface ChartValueGuidelineConfig extends ValueGuidelineConfig {
	/**
	 * Returns the value of the '<em><b>Label Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Format Style</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label Format Style</em>' containment reference.
	 * @see #setLabelFormatStyle(FormatStyle)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartValueGuidelineConfig_LabelFormatStyle()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='labelFormatStyle'"
	 * @generated
	 */
	FormatStyle getLabelFormatStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getLabelFormatStyle <em>Label Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Format Style</em>' containment reference.
	 * @see #getLabelFormatStyle()
	 * @generated
	 */
	void setLabelFormatStyle(FormatStyle value);

	/**
	 * Returns the value of the '<em><b>Header Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Name</em>' attribute.
	 * @see #setHeaderName(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartValueGuidelineConfig_HeaderName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='headerName'"
	 * @generated
	 */
	String getHeaderName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getHeaderName <em>Header Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Name</em>' attribute.
	 * @see #getHeaderName()
	 * @generated
	 */
	void setHeaderName(String value);

	/**
	 * Returns the value of the '<em><b>Relative Position</b></em>' attribute.
	 * The default value is <code>"left"</code>.
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartValueGuidelineConfig_RelativePosition()
	 * @model default="left" unsettable="true"
	 *        extendedMetaData="kind='element' name='relativePosition'"
	 * @generated
	 */
	RelativeAxisPositionEnum getRelativePosition();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getRelativePosition <em>Relative Position</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getRelativePosition <em>Relative Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRelativePosition()
	 * @see #getRelativePosition()
	 * @see #setRelativePosition(RelativeAxisPositionEnum)
	 * @generated
	 */
	void unsetRelativePosition();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getRelativePosition <em>Relative Position</em>}' attribute is set.
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
	 * Returns the value of the '<em><b>Scale</b></em>' attribute.
	 * The default value is <code>"normal"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scale</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum
	 * @see #isSetScale()
	 * @see #unsetScale()
	 * @see #setScale(ScaleEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartValueGuidelineConfig_Scale()
	 * @model default="normal" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='scale'"
	 * @generated
	 */
	ScaleEnum getScale();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getScale <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scale</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum
	 * @see #isSetScale()
	 * @see #unsetScale()
	 * @see #getScale()
	 * @generated
	 */
	void setScale(ScaleEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getScale <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetScale()
	 * @see #getScale()
	 * @see #setScale(ScaleEnum)
	 * @generated
	 */
	void unsetScale();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getScale <em>Scale</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Scale</em>' attribute is set.
	 * @see #unsetScale()
	 * @see #getScale()
	 * @see #setScale(ScaleEnum)
	 * @generated
	 */
	boolean isSetScale();

	/**
	 * Returns the value of the '<em><b>Division</b></em>' attribute.
	 * The default value is <code>"5"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Division</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Division</em>' attribute.
	 * @see #isSetDivision()
	 * @see #unsetDivision()
	 * @see #setDivision(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartValueGuidelineConfig_Division()
	 * @model default="5" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='division'"
	 * @generated
	 */
	int getDivision();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getDivision <em>Division</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Division</em>' attribute.
	 * @see #isSetDivision()
	 * @see #unsetDivision()
	 * @see #getDivision()
	 * @generated
	 */
	void setDivision(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getDivision <em>Division</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDivision()
	 * @see #getDivision()
	 * @see #setDivision(int)
	 * @generated
	 */
	void unsetDivision();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig#getDivision <em>Division</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Division</em>' attribute is set.
	 * @see #unsetDivision()
	 * @see #getDivision()
	 * @see #setDivision(int)
	 * @generated
	 */
	boolean isSetDivision();

} // ChartValueGuidelineConfig
