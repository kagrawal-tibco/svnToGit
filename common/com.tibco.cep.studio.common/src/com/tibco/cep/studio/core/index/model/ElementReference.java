/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#isAttRef <em>Att Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#isTypeRef <em>Type Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#getOffset <em>Offset</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#getQualifier <em>Qualifier</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#isArray <em>Array</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#isMethod <em>Method</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ElementReference#getBinding <em>Binding</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference()
 * @model
 * @generated
 */
public interface ElementReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Att Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Att Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Att Ref</em>' attribute.
	 * @see #setAttRef(boolean)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_AttRef()
	 * @model required="true"
	 * @generated
	 */
	boolean isAttRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#isAttRef <em>Att Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Att Ref</em>' attribute.
	 * @see #isAttRef()
	 * @generated
	 */
	void setAttRef(boolean value);

	/**
	 * Returns the value of the '<em><b>Type Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Ref</em>' attribute.
	 * @see #setTypeRef(boolean)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_TypeRef()
	 * @model required="true"
	 * @generated
	 */
	boolean isTypeRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#isTypeRef <em>Type Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Ref</em>' attribute.
	 * @see #isTypeRef()
	 * @generated
	 */
	void setTypeRef(boolean value);

	/**
	 * Returns the value of the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offset</em>' attribute.
	 * @see #setOffset(int)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_Offset()
	 * @model required="true"
	 * @generated
	 */
	int getOffset();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#getOffset <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(int value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_Length()
	 * @model required="true"
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

	/**
	 * Returns the value of the '<em><b>Qualifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualifier</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualifier</em>' containment reference.
	 * @see #setQualifier(ElementReference)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_Qualifier()
	 * @model containment="true"
	 * @generated
	 */
	ElementReference getQualifier();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#getQualifier <em>Qualifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualifier</em>' containment reference.
	 * @see #getQualifier()
	 * @generated
	 */
	void setQualifier(ElementReference value);

	/**
	 * Returns the value of the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array</em>' attribute.
	 * @see #setArray(boolean)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_Array()
	 * @model
	 * @generated
	 */
	boolean isArray();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#isArray <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array</em>' attribute.
	 * @see #isArray()
	 * @generated
	 */
	void setArray(boolean value);

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see #setMethod(boolean)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_Method()
	 * @model
	 * @generated
	 */
	boolean isMethod();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#isMethod <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see #isMethod()
	 * @generated
	 */
	void setMethod(boolean value);

	/**
	 * Returns the value of the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binding</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binding</em>' attribute.
	 * @see #setBinding(Object)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getElementReference_Binding()
	 * @model transient="true"
	 * @generated
	 */
	Object getBinding();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ElementReference#getBinding <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binding</em>' attribute.
	 * @see #getBinding()
	 * @generated
	 */
	void setBinding(Object value);

} // ElementReference
