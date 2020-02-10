/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Value Data Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete extension of data config to handler value data extraction,transformation and formatting.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getHeaderName <em>Header Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getHeaderFormatStyle <em>Header Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getWidth <em>Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getAlignment <em>Alignment</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextValueDataConfig()
 * @model extendedMetaData="name='TextValueDataConfig' kind='elementOnly'"
 * @generated
 */
public interface TextValueDataConfig extends DataConfig {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextValueDataConfig_HeaderName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='headerName'"
	 * @generated
	 */
	String getHeaderName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getHeaderName <em>Header Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Name</em>' attribute.
	 * @see #getHeaderName()
	 * @generated
	 */
	void setHeaderName(String value);

	/**
	 * Returns the value of the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Format Style</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Format Style</em>' containment reference.
	 * @see #setHeaderFormatStyle(FormatStyle)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextValueDataConfig_HeaderFormatStyle()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='headerFormatStyle'"
	 * @generated
	 */
	FormatStyle getHeaderFormatStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getHeaderFormatStyle <em>Header Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Format Style</em>' containment reference.
	 * @see #getHeaderFormatStyle()
	 * @generated
	 */
	void setHeaderFormatStyle(FormatStyle value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextValueDataConfig_Width()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='width'"
	 * @generated
	 */
	String getWidth();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(String value);

	/**
	 * Returns the value of the '<em><b>Alignment</b></em>' attribute.
	 * The default value is <code>"center"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alignment</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @see #isSetAlignment()
	 * @see #unsetAlignment()
	 * @see #setAlignment(FieldAlignmentEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextValueDataConfig_Alignment()
	 * @model default="center" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='alignment'"
	 * @generated
	 */
	FieldAlignmentEnum getAlignment();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getAlignment <em>Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alignment</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @see #isSetAlignment()
	 * @see #unsetAlignment()
	 * @see #getAlignment()
	 * @generated
	 */
	void setAlignment(FieldAlignmentEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getAlignment <em>Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAlignment()
	 * @see #getAlignment()
	 * @see #setAlignment(FieldAlignmentEnum)
	 * @generated
	 */
	void unsetAlignment();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig#getAlignment <em>Alignment</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Alignment</em>' attribute is set.
	 * @see #unsetAlignment()
	 * @see #getAlignment()
	 * @see #setAlignment(FieldAlignmentEnum)
	 * @generated
	 */
	boolean isSetAlignment();

} // TextValueDataConfig
