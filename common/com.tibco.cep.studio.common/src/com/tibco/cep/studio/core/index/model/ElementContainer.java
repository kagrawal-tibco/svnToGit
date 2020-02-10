/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementContainer#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ElementContainer extends DesignerElement {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.DesignerElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementContainer_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<DesignerElement> getEntries();

} // ElementContainer
