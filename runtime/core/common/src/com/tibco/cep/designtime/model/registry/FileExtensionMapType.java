/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File Extension Map Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.FileExtensionMapType#getItem <em>Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getFileExtensionMapType()
 * @model extendedMetaData="name='fileExtensionMapType' kind='elementOnly'"
 * @generated
 */
public interface FileExtensionMapType extends EObject {
	/**
	 * Returns the value of the '<em><b>Item</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Item</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Item</em>' containment reference list.
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getFileExtensionMapType_Item()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='item'"
	 * @generated
	 */
	EList<FileExtensionMapItemType> getItem();

} // FileExtensionMapType
