/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shared Archive</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedResources <em>Shared Resources</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedFiles <em>Shared Files</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.SharedArchive#getSharedJarFiles <em>Shared Jar Files</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getSharedArchive()
 * @model
 * @generated
 */
public interface SharedArchive extends ArchiveResource {

	/**
	 * Returns the value of the '<em><b>Shared Resources</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Resources</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Resources</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getSharedArchive_SharedResources()
	 * @model
	 * @generated
	 */
	EList<String> getSharedResources();

	/**
	 * Returns the value of the '<em><b>Shared Files</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Files</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Files</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getSharedArchive_SharedFiles()
	 * @model
	 * @generated
	 */
	EList<String> getSharedFiles();

	/**
	 * Returns the value of the '<em><b>Shared Jar Files</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Jar Files</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Jar Files</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getSharedArchive_SharedJarFiles()
	 * @model
	 * @generated
	 */
	EList<String> getSharedJarFiles();
} // SharedArchive
