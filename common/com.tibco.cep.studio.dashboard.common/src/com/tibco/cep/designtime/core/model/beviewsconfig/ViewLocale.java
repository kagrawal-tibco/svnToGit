/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View Locale</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             We may want to make it reusable element.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getTimeZone <em>Time Zone</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getTimeFormat <em>Time Format</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewLocale()
 * @model extendedMetaData="name='BEViewsLocale' kind='elementOnly'"
 * @generated
 */
public interface ViewLocale extends BEViewsElement {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewLocale_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Locale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locale</em>' attribute.
	 * @see #setLocale(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewLocale_Locale()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='locale'"
	 * @generated
	 */
	String getLocale();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getLocale <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locale</em>' attribute.
	 * @see #getLocale()
	 * @generated
	 */
	void setLocale(String value);

	/**
	 * Returns the value of the '<em><b>Time Zone</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Zone</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Zone</em>' attribute.
	 * @see #setTimeZone(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewLocale_TimeZone()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='timeZone'"
	 * @generated
	 */
	String getTimeZone();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getTimeZone <em>Time Zone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Zone</em>' attribute.
	 * @see #getTimeZone()
	 * @generated
	 */
	void setTimeZone(String value);

	/**
	 * Returns the value of the '<em><b>Time Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Format</em>' attribute.
	 * @see #setTimeFormat(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getViewLocale_TimeFormat()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='timeFormat'"
	 * @generated
	 */
	String getTimeFormat();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale#getTimeFormat <em>Time Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Format</em>' attribute.
	 * @see #getTimeFormat()
	 * @generated
	 */
	void setTimeFormat(String value);

} // ViewLocale
