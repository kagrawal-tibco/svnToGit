/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot#getOntologyFunctions <em>Ontology Functions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getOntologyFunctionsRoot()
 * @model
 * @generated
 */
public interface OntologyFunctionsRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Ontology Functions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ontology Functions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ontology Functions</em>' containment reference.
	 * @see #setOntologyFunctions(AbstractResource)
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage#getOntologyFunctionsRoot_OntologyFunctions()
	 * @model containment="true"
	 * @generated
	 */
	AbstractResource getOntologyFunctions();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot#getOntologyFunctions <em>Ontology Functions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ontology Functions</em>' containment reference.
	 * @see #getOntologyFunctions()
	 * @generated
	 */
	void setOntologyFunctions(AbstractResource value);

} // OntologyFunctionsRoot
