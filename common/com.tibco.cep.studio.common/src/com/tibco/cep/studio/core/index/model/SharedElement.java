/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shared Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.SharedElement#getArchivePath <em>Archive Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.SharedElement#getEntryPath <em>Entry Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.SharedElement#getFileName <em>File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedElement()
 * @model
 * @generated
 */
public interface SharedElement extends DesignerElement {
	/**
	 * Returns the value of the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Archive Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Archive Path</em>' attribute.
	 * @see #setArchivePath(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedElement_ArchivePath()
	 * @model
	 * @generated
	 */
	String getArchivePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.SharedElement#getArchivePath <em>Archive Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Archive Path</em>' attribute.
	 * @see #getArchivePath()
	 * @generated
	 */
	void setArchivePath(String value);

	/**
	 * Returns the value of the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Path</em>' attribute.
	 * @see #setEntryPath(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedElement_EntryPath()
	 * @model
	 * @generated
	 */
	String getEntryPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.SharedElement#getEntryPath <em>Entry Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Path</em>' attribute.
	 * @see #getEntryPath()
	 * @generated
	 */
	void setEntryPath(String value);

	/**
	 * Returns the value of the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Name</em>' attribute.
	 * @see #setFileName(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedElement_FileName()
	 * @model
	 * @generated
	 */
	String getFileName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.SharedElement#getFileName <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Name</em>' attribute.
	 * @see #getFileName()
	 * @generated
	 */
	void setFileName(String value);

} // SharedElement
