/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ontology Functions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getOntologyFunctions()
 * @model
 * @generated
 */
public interface OntologyFunctions extends ParentResource {
	/**
	 * Returns the value of the '<em><b>Resource</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' containment reference list.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getOntologyFunctions_Resource()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractResource> getResource();

} // OntologyFunctions
