/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Category Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the base for all category guideline config. This can manifests as 1st column or the chart category axis
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getHeaderName <em>Header Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getHeaderFormatStyle <em>Header Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getLabelFormatStyle <em>Label Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getSortOrder <em>Sort Order</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#isDuplicatesAllowed <em>Duplicates Allowed</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getCategoryGuidelineConfig()
 * @model abstract="true"
 *        extendedMetaData="name='CategoryGuidelineConfig' kind='elementOnly'"
 * @generated
 */
public interface CategoryGuidelineConfig extends BEViewsElement {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getCategoryGuidelineConfig_HeaderName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='headerName'"
	 * @generated
	 */
	String getHeaderName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getHeaderName <em>Header Name</em>}' attribute.
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getCategoryGuidelineConfig_HeaderFormatStyle()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='headerFormatStyle'"
	 * @generated
	 */
	FormatStyle getHeaderFormatStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getHeaderFormatStyle <em>Header Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Format Style</em>' containment reference.
	 * @see #getHeaderFormatStyle()
	 * @generated
	 */
	void setHeaderFormatStyle(FormatStyle value);

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getCategoryGuidelineConfig_LabelFormatStyle()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='labelFormatStyle'"
	 * @generated
	 */
	FormatStyle getLabelFormatStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getLabelFormatStyle <em>Label Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Format Style</em>' containment reference.
	 * @see #getLabelFormatStyle()
	 * @generated
	 */
	void setLabelFormatStyle(FormatStyle value);

	/**
	 * Returns the value of the '<em><b>Sort Order</b></em>' attribute.
	 * The default value is <code>"unsorted"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sort Order</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sort Order</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum
	 * @see #isSetSortOrder()
	 * @see #unsetSortOrder()
	 * @see #setSortOrder(SortEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getCategoryGuidelineConfig_SortOrder()
	 * @model default="unsorted" unsettable="true"
	 *        extendedMetaData="kind='element' name='sortOrder'"
	 * @generated
	 */
	SortEnum getSortOrder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getSortOrder <em>Sort Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort Order</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum
	 * @see #isSetSortOrder()
	 * @see #unsetSortOrder()
	 * @see #getSortOrder()
	 * @generated
	 */
	void setSortOrder(SortEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getSortOrder <em>Sort Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSortOrder()
	 * @see #getSortOrder()
	 * @see #setSortOrder(SortEnum)
	 * @generated
	 */
	void unsetSortOrder();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#getSortOrder <em>Sort Order</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Sort Order</em>' attribute is set.
	 * @see #unsetSortOrder()
	 * @see #getSortOrder()
	 * @see #setSortOrder(SortEnum)
	 * @generated
	 */
	boolean isSetSortOrder();

	/**
	 * Returns the value of the '<em><b>Duplicates Allowed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Duplicates Allowed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Duplicates Allowed</em>' attribute.
	 * @see #setDuplicatesAllowed(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getCategoryGuidelineConfig_DuplicatesAllowed()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isDuplicatesAllowed();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig#isDuplicatesAllowed <em>Duplicates Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Duplicates Allowed</em>' attribute.
	 * @see #isDuplicatesAllowed()
	 * @generated
	 */
	void setDuplicatesAllowed(boolean value);

} // CategoryGuidelineConfig
