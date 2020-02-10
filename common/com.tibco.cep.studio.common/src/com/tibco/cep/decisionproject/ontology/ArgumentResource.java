/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Argument Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.ArgumentResource#getAlias <em>Alias</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getArgumentResource()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ArgumentResource extends AbstractResource {
	/**
	 * Returns the value of the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alias</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias</em>' attribute.
	 * @see #setAlias(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getArgumentResource_Alias()
	 * @model
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.ArgumentResource#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

} // ArgumentResource
