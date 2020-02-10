/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Component Color Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderColor <em>Header Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderRollOverColor <em>Header Roll Over Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderTextFontColor <em>Header Text Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderSeparatorColor <em>Header Separator Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getCellColor <em>Cell Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getCellTextFontColor <em>Cell Text Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getRowSeparatorColor <em>Row Separator Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getRowRollOverColor <em>Row Roll Over Color</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet()
 * @model extendedMetaData="name='TextComponentColorSet' kind='elementOnly'"
 * @generated
 */
public interface TextComponentColorSet extends ComponentColorSet {
	/**
	 * Returns the value of the '<em><b>Header Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Color</em>' attribute.
	 * @see #setHeaderColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet_HeaderColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='headerColor'"
	 * @generated
	 */
	String getHeaderColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderColor <em>Header Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Color</em>' attribute.
	 * @see #getHeaderColor()
	 * @generated
	 */
	void setHeaderColor(String value);

	/**
	 * Returns the value of the '<em><b>Header Roll Over Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Roll Over Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Roll Over Color</em>' attribute.
	 * @see #setHeaderRollOverColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet_HeaderRollOverColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='headerRollOverColor'"
	 * @generated
	 */
	String getHeaderRollOverColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderRollOverColor <em>Header Roll Over Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Roll Over Color</em>' attribute.
	 * @see #getHeaderRollOverColor()
	 * @generated
	 */
	void setHeaderRollOverColor(String value);

	/**
	 * Returns the value of the '<em><b>Header Text Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Text Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Text Font Color</em>' attribute.
	 * @see #setHeaderTextFontColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet_HeaderTextFontColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='headerTextFontColor'"
	 * @generated
	 */
	String getHeaderTextFontColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderTextFontColor <em>Header Text Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Text Font Color</em>' attribute.
	 * @see #getHeaderTextFontColor()
	 * @generated
	 */
	void setHeaderTextFontColor(String value);

	/**
	 * Returns the value of the '<em><b>Header Separator Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Separator Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Separator Color</em>' attribute.
	 * @see #setHeaderSeparatorColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet_HeaderSeparatorColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='headerSeparatorColor'"
	 * @generated
	 */
	String getHeaderSeparatorColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getHeaderSeparatorColor <em>Header Separator Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Separator Color</em>' attribute.
	 * @see #getHeaderSeparatorColor()
	 * @generated
	 */
	void setHeaderSeparatorColor(String value);

	/**
	 * Returns the value of the '<em><b>Cell Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cell Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cell Color</em>' attribute.
	 * @see #setCellColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet_CellColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='cellColor'"
	 * @generated
	 */
	String getCellColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getCellColor <em>Cell Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cell Color</em>' attribute.
	 * @see #getCellColor()
	 * @generated
	 */
	void setCellColor(String value);

	/**
	 * Returns the value of the '<em><b>Cell Text Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cell Text Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cell Text Font Color</em>' attribute.
	 * @see #setCellTextFontColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet_CellTextFontColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='cellTextFontColor'"
	 * @generated
	 */
	String getCellTextFontColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getCellTextFontColor <em>Cell Text Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cell Text Font Color</em>' attribute.
	 * @see #getCellTextFontColor()
	 * @generated
	 */
	void setCellTextFontColor(String value);

	/**
	 * Returns the value of the '<em><b>Row Separator Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Row Separator Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Row Separator Color</em>' attribute.
	 * @see #setRowSeparatorColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet_RowSeparatorColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='rowSeparatorColor'"
	 * @generated
	 */
	String getRowSeparatorColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getRowSeparatorColor <em>Row Separator Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Row Separator Color</em>' attribute.
	 * @see #getRowSeparatorColor()
	 * @generated
	 */
	void setRowSeparatorColor(String value);

	/**
	 * Returns the value of the '<em><b>Row Roll Over Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Row Roll Over Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Row Roll Over Color</em>' attribute.
	 * @see #setRowRollOverColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextComponentColorSet_RowRollOverColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='rowRollOverColor'"
	 * @generated
	 */
	String getRowRollOverColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet#getRowRollOverColor <em>Row Roll Over Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Row Roll Over Color</em>' attribute.
	 * @see #getRowRollOverColor()
	 * @generated
	 */
	void setRowRollOverColor(String value);

} // TextComponentColorSet
