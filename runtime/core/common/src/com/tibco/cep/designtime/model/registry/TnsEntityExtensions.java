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
 * A representation of the model object '<em><b>Tns Entity Extensions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.TnsEntityExtensions#getExtension <em>Extension</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getTnsEntityExtensions()
 * @model extendedMetaData="name='tnsEntityExtensions' kind='elementOnly'"
 * @generated
 */
public interface TnsEntityExtensions extends EObject {
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
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getTnsEntityExtensions_Extension()
	 * @model unique="false"
	 *        extendedMetaData="kind='element' name='extension'"
	 * @generated
	 */
	EList<FileExtensionTypes> getExtension();

} // TnsEntityExtensions
