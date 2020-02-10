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
 * A representation of the model object '<em><b>Supported Element Types</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.SupportedElementTypes#getElementType <em>Element Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getSupportedElementTypes()
 * @model extendedMetaData="name='supportedElementTypes' kind='elementOnly'"
 * @generated
 */
public interface SupportedElementTypes extends EObject {
	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' attribute list.
	 * The list contents are of type {@link com.tibco.cep.designtime.model.registry.ElementTypes}.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.model.registry.ElementTypes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Type</em>' attribute list.
	 * @see com.tibco.cep.designtime.model.registry.ElementTypes
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getSupportedElementTypes_ElementType()
	 * @model unique="false" required="true"
	 *        extendedMetaData="kind='element' name='elementType'"
	 * @generated
	 */
	EList<ElementTypes> getElementType();

} // SupportedElementTypes
