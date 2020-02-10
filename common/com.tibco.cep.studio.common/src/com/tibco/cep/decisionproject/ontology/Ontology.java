/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ontology</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Ontology#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getOntology()
 * @model
 * @generated
 */
public interface Ontology extends ParentResource {
	/**
	 * Returns the value of the '<em><b>Resource</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decisionproject.ontology.AbstractResource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' reference list.
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getOntology_Resource()
	 * @model
	 * @generated
	 */
	EList<AbstractResource> getResource();

} // Ontology
