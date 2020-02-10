/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Argument Match</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch#getFunction <em>Function</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch#getArgNode <em>Arg Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getMethodArgumentMatch()
 * @model
 * @generated
 */
public interface MethodArgumentMatch extends RuleSourceMatch {
	/**
	 * Returns the value of the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' attribute.
	 * @see #setFunction(Object)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getMethodArgumentMatch_Function()
	 * @model transient="true"
	 * @generated
	 */
	Object getFunction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch#getFunction <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' attribute.
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(Object value);

	/**
	 * Returns the value of the '<em><b>Arg Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arg Node</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arg Node</em>' attribute.
	 * @see #setArgNode(Object)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getMethodArgumentMatch_ArgNode()
	 * @model transient="true"
	 * @generated
	 */
	Object getArgNode();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch#getArgNode <em>Arg Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arg Node</em>' attribute.
	 * @see #getArgNode()
	 * @generated
	 */
	void setArgNode(Object value);

} // MethodArgumentMatch
