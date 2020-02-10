/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Series Color</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getBaseColor <em>Base Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getHighlightColor <em>Highlight Color</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesColor()
 * @model extendedMetaData="name='SeriesColor' kind='elementOnly'"
 * @generated
 */
public interface SeriesColor extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Base Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Color</em>' attribute.
	 * @see #setBaseColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesColor_BaseColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='baseColor'"
	 * @generated
	 */
	String getBaseColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getBaseColor <em>Base Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Color</em>' attribute.
	 * @see #getBaseColor()
	 * @generated
	 */
	void setBaseColor(String value);

	/**
	 * Returns the value of the '<em><b>Highlight Color</b></em>' attribute.
	 * The default value is <code>"FFFFFF"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Highlight Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Highlight Color</em>' attribute.
	 * @see #isSetHighlightColor()
	 * @see #unsetHighlightColor()
	 * @see #setHighlightColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesColor_HighlightColor()
	 * @model default="FFFFFF" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='highlightColor'"
	 * @generated
	 */
	String getHighlightColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getHighlightColor <em>Highlight Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Highlight Color</em>' attribute.
	 * @see #isSetHighlightColor()
	 * @see #unsetHighlightColor()
	 * @see #getHighlightColor()
	 * @generated
	 */
	void setHighlightColor(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getHighlightColor <em>Highlight Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHighlightColor()
	 * @see #getHighlightColor()
	 * @see #setHighlightColor(String)
	 * @generated
	 */
	void unsetHighlightColor();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor#getHighlightColor <em>Highlight Color</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Highlight Color</em>' attribute is set.
	 * @see #unsetHighlightColor()
	 * @see #getHighlightColor()
	 * @see #setHighlightColor(String)
	 * @generated
	 */
	boolean isSetHighlightColor();

} // SeriesColor
