/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Foreground Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat#getLine <em>Line</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getForegroundFormat()
 * @model extendedMetaData="name='ForegroundFormat' kind='elementOnly'"
 * @generated
 */
public interface ForegroundFormat extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Line</b></em>' attribute.
	 * The default value is <code>"both"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum
	 * @see #isSetLine()
	 * @see #unsetLine()
	 * @see #setLine(LineEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getForegroundFormat_Line()
	 * @model default="both" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='line'"
	 * @generated
	 */
	LineEnum getLine();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat#getLine <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum
	 * @see #isSetLine()
	 * @see #unsetLine()
	 * @see #getLine()
	 * @generated
	 */
	void setLine(LineEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat#getLine <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLine()
	 * @see #getLine()
	 * @see #setLine(LineEnum)
	 * @generated
	 */
	void unsetLine();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat#getLine <em>Line</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Line</em>' attribute is set.
	 * @see #unsetLine()
	 * @see #getLine()
	 * @see #setLine(LineEnum)
	 * @generated
	 */
	boolean isSetLine();

} // ForegroundFormat
