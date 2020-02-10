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
 * A representation of the model object '<em><b>Component Gallery Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder#getSubFolder <em>Sub Folder</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponentGalleryFolder()
 * @model
 * @generated
 */
public interface ComponentGalleryFolder extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Sub Folder</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Folder</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Folder</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponentGalleryFolder_SubFolder()
	 * @model containment="true"
	 * @generated
	 */
	EList<ComponentGalleryFolder> getSubFolder();

	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.Component}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getComponentGalleryFolder_Component()
	 * @model
	 * @generated
	 */
	EList<Component> getComponent();

} // ComponentGalleryFolder
