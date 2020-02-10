/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Annotation Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink#getToStateEntity <em>To State Entity</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink#getFromAnnotation <em>From Annotation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateAnnotationLink()
 * @model
 * @generated
 */
public interface StateAnnotationLink extends StateLink {
	/**
	 * Returns the value of the '<em><b>To State Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To State Entity</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To State Entity</em>' reference.
	 * @see #setToStateEntity(StateEntity)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateAnnotationLink_ToStateEntity()
	 * @model
	 * @generated
	 */
	StateEntity getToStateEntity();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink#getToStateEntity <em>To State Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To State Entity</em>' reference.
	 * @see #getToStateEntity()
	 * @generated
	 */
	void setToStateEntity(StateEntity value);

	/**
	 * Returns the value of the '<em><b>From Annotation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From Annotation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From Annotation</em>' attribute.
	 * @see #setFromAnnotation(String)
	 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getStateAnnotationLink_FromAnnotation()
	 * @model
	 * @generated
	 */
	String getFromAnnotation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink#getFromAnnotation <em>From Annotation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From Annotation</em>' attribute.
	 * @see #getFromAnnotation()
	 * @generated
	 */
	void setFromAnnotation(String value);

} // StateAnnotationLink
