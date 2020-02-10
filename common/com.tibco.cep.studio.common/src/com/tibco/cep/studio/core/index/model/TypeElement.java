/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.TypeElement#getFolder <em>Folder</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getTypeElement()
 * @model abstract="true"
 * @generated
 */
public interface TypeElement extends DesignerElement {
	/**
	 * Returns the value of the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Folder</em>' attribute.
	 * @see #setFolder(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getTypeElement_Folder()
	 * @model required="true"
	 * @generated
	 */
	String getFolder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.TypeElement#getFolder <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Folder</em>' attribute.
	 * @see #getFolder()
	 * @generated
	 */
	void setFolder(String value);

} // TypeElement
