/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Emf Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.palette.EmfType#getEmfType <em>Emf Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.EmfType#getExtendedType <em>Extended Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.palette.PalettePackage#getEmfType()
 * @model extendedMetaData="name='emfType' kind='elementOnly'"
 * @generated
 */
public interface EmfType extends EObject {
	/**
	 * Returns the value of the '<em><b>Emf Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Emf Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Emf Type</em>' attribute.
	 * @see #isSetEmfType()
	 * @see #unsetEmfType()
	 * @see #setEmfType(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getEmfType_EmfType()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='emfType' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEmfType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.EmfType#getEmfType <em>Emf Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Emf Type</em>' attribute.
	 * @see #isSetEmfType()
	 * @see #unsetEmfType()
	 * @see #getEmfType()
	 * @generated
	 */
	void setEmfType(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.EmfType#getEmfType <em>Emf Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetEmfType()
	 * @see #getEmfType()
	 * @see #setEmfType(String)
	 * @generated
	 */
	void unsetEmfType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.EmfType#getEmfType <em>Emf Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Emf Type</em>' attribute is set.
	 * @see #unsetEmfType()
	 * @see #getEmfType()
	 * @see #setEmfType(String)
	 * @generated
	 */
	boolean isSetEmfType();

	/**
	 * Returns the value of the '<em><b>Extended Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Type</em>' attribute.
	 * @see #isSetExtendedType()
	 * @see #unsetExtendedType()
	 * @see #setExtendedType(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getEmfType_ExtendedType()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='extendedType' namespace='##targetNamespace'"
	 * @generated
	 */
	String getExtendedType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.EmfType#getExtendedType <em>Extended Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Type</em>' attribute.
	 * @see #isSetExtendedType()
	 * @see #unsetExtendedType()
	 * @see #getExtendedType()
	 * @generated
	 */
	void setExtendedType(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.EmfType#getExtendedType <em>Extended Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetExtendedType()
	 * @see #getExtendedType()
	 * @see #setExtendedType(String)
	 * @generated
	 */
	void unsetExtendedType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.EmfType#getExtendedType <em>Extended Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Extended Type</em>' attribute is set.
	 * @see #unsetExtendedType()
	 * @see #getExtendedType()
	 * @see #setExtendedType(String)
	 * @generated
	 */
	boolean isSetExtendedType();

} // EmfType
