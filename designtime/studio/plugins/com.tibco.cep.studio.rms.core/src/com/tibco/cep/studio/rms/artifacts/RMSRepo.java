/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>RMS Repo</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.RMSRepo#getRepoURL <em>Repo URL</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.RMSRepo#getRmsProject <em>Rms Project</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getRMSRepo()
 * @model
 * @generated
 */
public interface RMSRepo extends EObject {
	/**
	 * Returns the value of the '<em><b>Repo URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repo URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repo URL</em>' attribute.
	 * @see #setRepoURL(String)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getRMSRepo_RepoURL()
	 * @model
	 * @generated
	 */
	String getRepoURL();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.RMSRepo#getRepoURL <em>Repo URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repo URL</em>' attribute.
	 * @see #getRepoURL()
	 * @generated
	 */
	void setRepoURL(String value);

	/**
	 * Returns the value of the '<em><b>Rms Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rms Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rms Project</em>' attribute.
	 * @see #setRmsProject(String)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getRMSRepo_RmsProject()
	 * @model
	 * @generated
	 */
	String getRmsProject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.RMSRepo#getRmsProject <em>Rms Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rms Project</em>' attribute.
	 * @see #getRmsProject()
	 * @generated
	 */
	void setRmsProject(String value);

} // RMSRepo
