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
 * A representation of the model object '<em><b>Java Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.palette.JavaType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.palette.PalettePackage#getJavaType()
 * @model extendedMetaData="name='javaType' kind='elementOnly'"
 * @generated
 */
public interface JavaType extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute list.
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#getJavaType_Type()
	 * @model default="" unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getType();

} // JavaType
