/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Primitive Holder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.PrimitiveHolder#getPrimitiveType <em>Primitive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getPrimitiveHolder()
 * @model
 * @generated
 */
public interface PrimitiveHolder extends AbstractResource, ArgumentResource {
	/**
	 * Returns the value of the '<em><b>Primitive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primitive Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primitive Type</em>' attribute.
	 * @see #setPrimitiveType(int)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getPrimitiveHolder_PrimitiveType()
	 * @model required="true"
	 * @generated
	 */
	int getPrimitiveType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.PrimitiveHolder#getPrimitiveType <em>Primitive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primitive Type</em>' attribute.
	 * @see #getPrimitiveType()
	 * @generated
	 */
	void setPrimitiveType(int value);

} // PrimitiveHolder
