/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Role Preference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getRole <em>Role</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getGallery <em>Gallery</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getView <em>View</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRolePreference()
 * @model
 * @generated
 */
public interface RolePreference extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role</em>' attribute.
	 * @see #setRole(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRolePreference_Role()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getRole();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getRole <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role</em>' attribute.
	 * @see #getRole()
	 * @generated
	 */
	void setRole(String value);

	/**
	 * Returns the value of the '<em><b>Gallery</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gallery</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gallery</em>' containment reference.
	 * @see #setGallery(ComponentGalleryFolder)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRolePreference_Gallery()
	 * @model containment="true"
	 * @generated
	 */
	ComponentGalleryFolder getGallery();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getGallery <em>Gallery</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gallery</em>' containment reference.
	 * @see #getGallery()
	 * @generated
	 */
	void setGallery(ComponentGalleryFolder value);

	/**
	 * Returns the value of the '<em><b>View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>View</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>View</em>' reference.
	 * @see #setView(View)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRolePreference_View()
	 * @model
	 * @generated
	 */
	View getView();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference#getView <em>View</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View</em>' reference.
	 * @see #getView()
	 * @generated
	 */
	void setView(View value);

} // RolePreference
