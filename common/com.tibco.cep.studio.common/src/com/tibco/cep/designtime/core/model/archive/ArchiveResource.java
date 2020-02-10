/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getAuthor <em>Author</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getArchiveResource()
 * @model abstract="true"
 * @generated
 */
public interface ArchiveResource extends EObject {
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
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getArchiveResource_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getArchiveResource_Description()
	 * @model required="true"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getArchiveResource_Author()
	 * @model required="true"
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.ArchiveResource#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

} // ArchiveResource
