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
 * A representation of the model object '<em><b>Header</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Contains information which enables customization of the
 * 				header in BEViews. Only one instance would exist in a
 * 				repository
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getTitle <em>Title</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getBrandingImage <em>Branding Image</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getBrandingText <em>Branding Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getLink <em>Link</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getHeaderLink <em>Header Link</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeader()
 * @model extendedMetaData="name='BEViewsHeader' kind='elementOnly'"
 * @generated
 */
public interface Header extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeader_Title()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='title'"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Branding Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branding Image</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branding Image</em>' attribute.
	 * @see #setBrandingImage(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeader_BrandingImage()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='brandingImage'"
	 * @generated
	 */
	String getBrandingImage();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getBrandingImage <em>Branding Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branding Image</em>' attribute.
	 * @see #getBrandingImage()
	 * @generated
	 */
	void setBrandingImage(String value);

	/**
	 * Returns the value of the '<em><b>Branding Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branding Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branding Text</em>' attribute.
	 * @see #setBrandingText(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeader_BrandingText()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='brandingText'"
	 * @generated
	 */
	String getBrandingText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Header#getBrandingText <em>Branding Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branding Text</em>' attribute.
	 * @see #getBrandingText()
	 * @generated
	 */
	void setBrandingText(String value);

	/**
	 * Returns the value of the '<em><b>Link</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeader_Link()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='link'"
	 * @generated
	 */
	EList<String> getLink();

	/**
	 * Returns the value of the '<em><b>Header Link</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Link</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Link</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getHeader_HeaderLink()
	 * @model containment="true"
	 * @generated
	 */
	EList<HeaderLink> getHeaderLink();

} // Header
