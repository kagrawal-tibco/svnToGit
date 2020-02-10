/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.functions;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Catalog</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.functions.Catalog#getCategory <em>Category</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getCatalog()
 * @model extendedMetaData="name='catalog' kind='elementOnly'"
 * @generated
 */
public interface Catalog extends EObject {
	/**
	 * Returns the value of the '<em><b>Category</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.functions.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getCatalog_Category()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='category' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Category> getCategory();

} // Catalog
