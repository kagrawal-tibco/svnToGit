/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Invocation Object Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				The rule/rule function resulting in the reteobject.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getInvocationObjectType()
 * @model extendedMetaData="name='InvocationObjectType' kind='elementOnly'"
 * @generated
 */
public interface InvocationObjectType extends EObject {
	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The full ExpandedName of the BE entity.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Namespace</em>' containment reference.
	 * @see #setNamespace(NamespaceType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getInvocationObjectType_Namespace()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Namespace' namespace='##targetNamespace'"
	 * @generated
	 */
	NamespaceType getNamespace();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getNamespace <em>Namespace</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' containment reference.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(NamespaceType value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.tester.emf.model.TypeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.TypeType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #setType(TypeType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getInvocationObjectType_Type()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='Type' namespace='##targetNamespace'"
	 * @generated
	 */
	TypeType getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.TypeType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #getType()
	 * @generated
	 */
	void setType(TypeType value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetType()
	 * @see #getType()
	 * @see #setType(TypeType)
	 * @generated
	 */
	void unsetType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType#getType <em>Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Type</em>' attribute is set.
	 * @see #unsetType()
	 * @see #getType()
	 * @see #setType(TypeType)
	 * @generated
	 */
	boolean isSetType();

} // InvocationObjectType
