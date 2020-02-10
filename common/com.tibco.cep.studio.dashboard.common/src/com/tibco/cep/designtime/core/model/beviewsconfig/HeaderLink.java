/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Header Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink#getUrlName <em>Url Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink#getUrlLink <em>Url Link</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeaderLink()
 * @model
 * @generated
 */
public interface HeaderLink extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Url Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url Name</em>' attribute.
	 * @see #setUrlName(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeaderLink_UrlName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getUrlName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink#getUrlName <em>Url Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url Name</em>' attribute.
	 * @see #getUrlName()
	 * @generated
	 */
	void setUrlName(String value);

	/**
	 * Returns the value of the '<em><b>Url Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url Link</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url Link</em>' attribute.
	 * @see #setUrlLink(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeaderLink_UrlLink()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getUrlLink();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink#getUrlLink <em>Url Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url Link</em>' attribute.
	 * @see #getUrlLink()
	 * @generated
	 */
	void setUrlLink(String value);

} // HeaderLink
