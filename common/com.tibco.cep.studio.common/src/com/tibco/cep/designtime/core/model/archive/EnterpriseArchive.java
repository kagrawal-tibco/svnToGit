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
 * A representation of the model object '<em><b>Enterprise Archive</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getOwnerProjectName <em>Owner Project Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getFileLocation <em>File Location</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#isIncludeGlobalVars <em>Include Global Vars</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getBusinessEventsArchives <em>Business Events Archives</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getSharedArchives <em>Shared Archives</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getProcessArchives <em>Process Archives</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive()
 * @model
 * @generated
 */
public interface EnterpriseArchive extends ArchiveResource {
	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive_Version()
	 * @model required="true"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

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
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive_Folder()
	 * @model required="true"
	 * @generated
	 */
	String getFolder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getFolder <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Folder</em>' attribute.
	 * @see #getFolder()
	 * @generated
	 */
	void setFolder(String value);

	/**
	 * Returns the value of the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Project Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Project Name</em>' attribute.
	 * @see #setOwnerProjectName(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive_OwnerProjectName()
	 * @model
	 * @generated
	 */
	String getOwnerProjectName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getOwnerProjectName <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Project Name</em>' attribute.
	 * @see #getOwnerProjectName()
	 * @generated
	 */
	void setOwnerProjectName(String value);

	/**
	 * Returns the value of the '<em><b>File Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Location</em>' attribute.
	 * @see #setFileLocation(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive_FileLocation()
	 * @model required="true"
	 * @generated
	 */
	String getFileLocation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#getFileLocation <em>File Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Location</em>' attribute.
	 * @see #getFileLocation()
	 * @generated
	 */
	void setFileLocation(String value);

	/**
	 * Returns the value of the '<em><b>Include Global Vars</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include Global Vars</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include Global Vars</em>' attribute.
	 * @see #setIncludeGlobalVars(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive_IncludeGlobalVars()
	 * @model required="true"
	 * @generated
	 */
	boolean isIncludeGlobalVars();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.EnterpriseArchive#isIncludeGlobalVars <em>Include Global Vars</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Include Global Vars</em>' attribute.
	 * @see #isIncludeGlobalVars()
	 * @generated
	 */
	void setIncludeGlobalVars(boolean value);

	/**
	 * Returns the value of the '<em><b>Business Events Archives</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Business Events Archives</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Business Events Archives</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive_BusinessEventsArchives()
	 * @model containment="true"
	 * @generated
	 */
	EList<BusinessEventsArchiveResource> getBusinessEventsArchives();

	/**
	 * Returns the value of the '<em><b>Shared Archives</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.archive.SharedArchive}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Archives</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Archives</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive_SharedArchives()
	 * @model containment="true"
	 * @generated
	 */
	EList<SharedArchive> getSharedArchives();

	/**
	 * Returns the value of the '<em><b>Process Archives</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.archive.SharedArchive}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Archives</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Archives</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getEnterpriseArchive_ProcessArchives()
	 * @model containment="true"
	 * @generated
	 */
	EList<SharedArchive> getProcessArchives();

} // EnterpriseArchive
