/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Member Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.MemberElement#getOffset <em>Offset</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.MemberElement#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getMemberElement()
 * @model abstract="true"
 * @generated
 */
public interface MemberElement extends DesignerElement {
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
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getMemberElement_Offset()
	 * @model required="true"
	 * @generated
	 */
	int getOffset();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.MemberElement#getOffset <em>Offset</em>}' attribute.
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
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getMemberElement_Length()
	 * @model required="true"
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.MemberElement#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

} // MemberElement
