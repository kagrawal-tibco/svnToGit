/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Login</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Contains information which enables customization of the
 * 				BEViews login dialog. Only one instance would exist in a
 * 				repository
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Login#getTitle <em>Title</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.Login#getImageURL <em>Image URL</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLogin()
 * @model extendedMetaData="name='BEViewsLogin' kind='elementOnly'"
 * @generated
 */
public interface Login extends BEViewsElement {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLogin_Title()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='title'"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Login#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Image URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image URL</em>' attribute.
	 * @see #setImageURL(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLogin_ImageURL()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='imageURL'"
	 * @generated
	 */
	String getImageURL();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.Login#getImageURL <em>Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image URL</em>' attribute.
	 * @see #getImageURL()
	 * @generated
	 */
	void setImageURL(String value);

} // Login
