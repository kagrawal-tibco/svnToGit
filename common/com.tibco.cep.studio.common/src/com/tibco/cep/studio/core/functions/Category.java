/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.functions;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.functions.Category#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Category#isValidInQuery <em>Valid In Query</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Category#isValidInBUI <em>Valid In BUI</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Category#getFunctions <em>Functions</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Category#getCategories <em>Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getCategory()
 * @model extendedMetaData="name='category' kind='elementOnly'"
 * @generated
 */
public interface Category extends EObject {
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
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getCategory_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Category#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Valid In Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Valid In Query</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valid In Query</em>' attribute.
	 * @see #setValidInQuery(boolean)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getCategory_ValidInQuery()
	 * @model required="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isValidInQuery();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Category#isValidInQuery <em>Valid In Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid In Query</em>' attribute.
	 * @see #isValidInQuery()
	 * @generated
	 */
	void setValidInQuery(boolean value);

	/**
	 * Returns the value of the '<em><b>Valid In BUI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Valid In BUI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valid In BUI</em>' attribute.
	 * @see #setValidInBUI(boolean)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getCategory_ValidInBUI()
	 * @model required="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isValidInBUI();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Category#isValidInBUI <em>Valid In BUI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid In BUI</em>' attribute.
	 * @see #isValidInBUI()
	 * @generated
	 */
	void setValidInBUI(boolean value);

	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.functions.Function}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getCategory_Functions()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='function' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Function> getFunctions();

	/**
	 * Returns the value of the '<em><b>Categories</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.functions.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Categories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Categories</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getCategory_Categories()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='function' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Category> getCategories();

} // Category
