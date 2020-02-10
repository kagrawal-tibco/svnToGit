/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteModel#getPaletteToolSet <em>Palette Tool Set</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.PaletteModel#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteModel()
 * @model extendedMetaData="name='paletteModel' kind='elementOnly'"
 * @generated
 */
public interface PaletteModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Palette Tool Set</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.common.palette.PaletteGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palette Tool Set</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Palette Tool Set</em>' containment reference list.
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteModel_PaletteToolSet()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='paletteToolSet' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PaletteGroup> getPaletteToolSet();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #isSetName()
	 * @see #unsetName()
	 * @see #setName(String)
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getPaletteModel_Name()
	 * @model default="" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #isSetName()
	 * @see #unsetName()
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.common.palette.PaletteModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetName()
	 * @see #getName()
	 * @see #setName(String)
	 * @generated
	 */
	void unsetName();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.common.palette.PaletteModel#getName <em>Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Name</em>' attribute is set.
	 * @see #unsetName()
	 * @see #getName()
	 * @see #setName(String)
	 * @generated
	 */
	boolean isSetName();

} // PaletteModel
