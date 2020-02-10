/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Designer Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerElement#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerElement#getElementType <em>Element Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerElement#isLazilyCreated <em>Lazily Created</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerElement#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerElement#getCreationDate <em>Creation Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerElement()
 * @model abstract="true"
 * @generated
 */
public interface DesignerElement extends StructuredElement {
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
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerElement_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.core.index.model.ELEMENT_TYPES}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Type</em>' attribute.
	 * @see com.tibco.cep.studio.core.index.model.ELEMENT_TYPES
	 * @see #setElementType(ELEMENT_TYPES)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerElement_ElementType()
	 * @model required="true"
	 * @generated
	 */
	ELEMENT_TYPES getElementType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerElement#getElementType <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Type</em>' attribute.
	 * @see com.tibco.cep.studio.core.index.model.ELEMENT_TYPES
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(ELEMENT_TYPES value);

	/**
	 * Returns the value of the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lazily Created</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lazily Created</em>' attribute.
	 * @see #setLazilyCreated(boolean)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerElement_LazilyCreated()
	 * @model required="true"
	 * @generated
	 */
	boolean isLazilyCreated();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerElement#isLazilyCreated <em>Lazily Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lazily Created</em>' attribute.
	 * @see #isLazilyCreated()
	 * @generated
	 */
	void setLazilyCreated(boolean value);

	/**
	 * Returns the value of the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Modified</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Modified</em>' attribute.
	 * @see #setLastModified(Date)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerElement_LastModified()
	 * @model required="true"
	 * @generated
	 */
	Date getLastModified();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerElement#getLastModified <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Modified</em>' attribute.
	 * @see #getLastModified()
	 * @generated
	 */
	void setLastModified(Date value);

	/**
	 * Returns the value of the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Date</em>' attribute.
	 * @see #setCreationDate(Date)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerElement_CreationDate()
	 * @model required="true"
	 * @generated
	 */
	Date getCreationDate();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerElement#getCreationDate <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Date</em>' attribute.
	 * @see #getCreationDate()
	 * @generated
	 */
	void setCreationDate(Date value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorType="com.tibco.cep.studio.core.index.model.IStructuredElementVisitor"
	 * @generated
	 */
	void accept(IStructuredElementVisitor visitor);

	
} // DesignerElement
