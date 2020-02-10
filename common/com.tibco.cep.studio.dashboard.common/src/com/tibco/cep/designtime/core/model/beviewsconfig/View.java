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
 * A representation of the model object '<em><b>View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getDefaultPage <em>Default Page</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getAccessiblePage <em>Accessible Page</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getSkin <em>Skin</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getView()
 * @model extendedMetaData="name='BEViewsConfig' kind='elementOnly'"
 * @generated
 */
public interface View extends BEViewsElement {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getView_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Default Page</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Page</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Page</em>' reference.
	 * @see #setDefaultPage(Page)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getView_DefaultPage()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='defaultPageSet'"
	 * @generated
	 */
	Page getDefaultPage();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getDefaultPage <em>Default Page</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Page</em>' reference.
	 * @see #getDefaultPage()
	 * @generated
	 */
	void setDefaultPage(Page value);

	/**
	 * Returns the value of the '<em><b>Accessible Page</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.Page}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accessible Page</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accessible Page</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getView_AccessiblePage()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='accessiblePageSet'"
	 * @generated
	 */
	EList<Page> getAccessiblePage();

	/**
	 * Returns the value of the '<em><b>Skin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Skin</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Skin</em>' reference.
	 * @see #setSkin(Skin)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getView_Skin()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='skin'"
	 * @generated
	 */
	Skin getSkin();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getSkin <em>Skin</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Skin</em>' reference.
	 * @see #getSkin()
	 * @generated
	 */
	void setSkin(Skin value);

	/**
	 * Returns the value of the '<em><b>Locale</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locale</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locale</em>' containment reference.
	 * @see #setLocale(ViewLocale)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getView_Locale()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='locale'"
	 * @generated
	 */
	ViewLocale getLocale();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.View#getLocale <em>Locale</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locale</em>' containment reference.
	 * @see #getLocale()
	 * @generated
	 */
	void setLocale(ViewLocale value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.ViewAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getView_Attribute()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='attribute'"
	 * @generated
	 */
	EList<ViewAttribute> getAttribute();

} // View
