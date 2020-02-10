/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Archive Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ArchiveElement#getArchive <em>Archive</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getArchiveElement()
 * @model
 * @generated
 */
public interface ArchiveElement extends TypeElement {
	/**
	 * Returns the value of the '<em><b>Archive</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Archive</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Archive</em>' reference.
	 * @see #setArchive(ArchiveResource)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getArchiveElement_Archive()
	 * @model required="true"
	 * @generated
	 */
	ArchiveResource getArchive();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ArchiveElement#getArchive <em>Archive</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Archive</em>' reference.
	 * @see #getArchive()
	 * @generated
	 */
	void setArchive(ArchiveResource value);

} // ArchiveElement
