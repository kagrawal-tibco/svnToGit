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
 * A representation of the model object '<em><b>Supported Extensions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.SupportedExtensions#getExtension <em>Extension</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getSupportedExtensions()
 * @model extendedMetaData="name='supportedExtensions' kind='elementOnly'"
 * @generated
 */
public interface SupportedExtensions extends EObject {
	/**
	 * Returns the value of the '<em><b>Extension</b></em>' attribute list.
	 * The list contents are of type {@link com.tibco.cep.designtime.model.registry.FileExtensionTypes}.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.model.registry.FileExtensionTypes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension</em>' attribute list.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getSupportedExtensions_Extension()
	 * @model unique="false" required="true"
	 *        extendedMetaData="kind='element' name='extension'"
	 * @generated
	 */
	EList<FileExtensionTypes> getExtension();

} // SupportedExtensions
