/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Header</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Header#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getHeader()
 * @model
 * @generated
 */
public interface Header extends EObject {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getHeader_Property()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperty();

} // Header
