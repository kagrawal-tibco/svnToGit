/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Access Control Candidate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate#isModified <em>Modified</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getAccessControlCandidate()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface AccessControlCandidate extends EObject {
	/**
	 * Returns the value of the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modified</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modified</em>' attribute.
	 * @see #setModified(boolean)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getAccessControlCandidate_Modified()
	 * @model
	 * @generated
	 */
	boolean isModified();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate#isModified <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modified</em>' attribute.
	 * @see #isModified()
	 * @generated
	 */
	void setModified(boolean value);

	

} // AccessControlCandidate
