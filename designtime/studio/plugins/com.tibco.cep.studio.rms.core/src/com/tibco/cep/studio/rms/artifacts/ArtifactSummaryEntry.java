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
 * A representation of the model object '<em><b>Artifact Summary Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getArtifact <em>Artifact</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getOperationType <em>Operation Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getArtifactChanger <em>Artifact Changer</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#isCommitStatus <em>Commit Status</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactSummaryEntry()
 * @model
 * @generated
 */
public interface ArtifactSummaryEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Artifact</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact</em>' containment reference.
	 * @see #setArtifact(Artifact)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactSummaryEntry_Artifact()
	 * @model containment="true"
	 * @generated
	 */
	Artifact getArtifact();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getArtifact <em>Artifact</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact</em>' containment reference.
	 * @see #getArtifact()
	 * @generated
	 */
	void setArtifact(Artifact value);

	/**
	 * Returns the value of the '<em><b>Operation Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.rms.artifacts.ArtifactOperation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Type</em>' attribute.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactOperation
	 * @see #setOperationType(ArtifactOperation)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactSummaryEntry_OperationType()
	 * @model
	 * @generated
	 */
	ArtifactOperation getOperationType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getOperationType <em>Operation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation Type</em>' attribute.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactOperation
	 * @see #getOperationType()
	 * @generated
	 */
	void setOperationType(ArtifactOperation value);

	/**
	 * Returns the value of the '<em><b>Artifact Changer</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.rms.artifacts.ArtifactChanger}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact Changer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact Changer</em>' attribute.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactChanger
	 * @see #setArtifactChanger(ArtifactChanger)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactSummaryEntry_ArtifactChanger()
	 * @model
	 * @generated
	 */
	ArtifactChanger getArtifactChanger();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getArtifactChanger <em>Artifact Changer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Changer</em>' attribute.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactChanger
	 * @see #getArtifactChanger()
	 * @generated
	 */
	void setArtifactChanger(ArtifactChanger value);

	/**
	 * Returns the value of the '<em><b>Commit Status</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commit Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit Status</em>' attribute.
	 * @see #setCommitStatus(boolean)
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactSummaryEntry_CommitStatus()
	 * @model default="false"
	 * @generated
	 */
	boolean isCommitStatus();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#isCommitStatus <em>Commit Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit Status</em>' attribute.
	 * @see #isCommitStatus()
	 * @generated
	 */
	void setCommitStatus(boolean value);

} // ArtifactSummaryEntry
