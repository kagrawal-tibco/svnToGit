/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Summary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getAddedArtifacts <em>Added Artifacts</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getModifiedArtifacts <em>Modified Artifacts</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getDeletedArtifacts <em>Deleted Artifacts</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getRmsRepo <em>Rms Repo</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactsSummary()
 * @model
 * @generated
 */
public interface ArtifactsSummary extends EObject {
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
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactsSummary_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Added Artifacts</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Added Artifacts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Added Artifacts</em>' containment reference list.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactsSummary_AddedArtifacts()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArtifactSummaryEntry> getAddedArtifacts();

	/**
	 * Returns the value of the '<em><b>Modified Artifacts</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modified Artifacts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modified Artifacts</em>' containment reference list.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactsSummary_ModifiedArtifacts()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArtifactSummaryEntry> getModifiedArtifacts();

	/**
	 * Returns the value of the '<em><b>Deleted Artifacts</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deleted Artifacts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Artifacts</em>' containment reference list.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactsSummary_DeletedArtifacts()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArtifactSummaryEntry> getDeletedArtifacts();

	/**
	 * Returns the value of the '<em><b>Rms Repo</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rms Repo</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rms Repo</em>' containment reference.
	 * @see #setRmsRepo(RMSRepo)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactsSummary_RmsRepo()
	 * @model containment="true"
	 * @generated
	 */
	RMSRepo getRmsRepo();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getRmsRepo <em>Rms Repo</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rms Repo</em>' containment reference.
	 * @see #getRmsRepo()
	 * @generated
	 */
	void setRmsRepo(RMSRepo value);

} // ArtifactsSummary
