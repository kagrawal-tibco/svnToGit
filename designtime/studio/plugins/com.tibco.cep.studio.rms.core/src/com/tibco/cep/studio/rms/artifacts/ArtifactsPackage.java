/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsFactory
 * @model kind="package"
 * @generated
 */
public interface ArtifactsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "artifacts";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/rms/artifacts/artifacts_summary.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rms";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArtifactsPackage eINSTANCE = com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl <em>Summary</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactsSummary()
	 * @generated
	 */
	int ARTIFACTS_SUMMARY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACTS_SUMMARY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Added Artifacts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACTS_SUMMARY__ADDED_ARTIFACTS = 1;

	/**
	 * The feature id for the '<em><b>Modified Artifacts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS = 2;

	/**
	 * The feature id for the '<em><b>Deleted Artifacts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACTS_SUMMARY__DELETED_ARTIFACTS = 3;

	/**
	 * The feature id for the '<em><b>Rms Repo</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACTS_SUMMARY__RMS_REPO = 4;

	/**
	 * The number of structural features of the '<em>Summary</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACTS_SUMMARY_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl <em>Artifact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifact()
	 * @generated
	 */
	int ARTIFACT = 1;

	/**
	 * The feature id for the '<em><b>Artifact Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__ARTIFACT_PATH = 0;

	/**
	 * The feature id for the '<em><b>Artifact Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__ARTIFACT_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Artifact Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__ARTIFACT_CONTENT = 2;

	/**
	 * The feature id for the '<em><b>Artifact Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__ARTIFACT_EXTENSION = 3;

	/**
	 * The feature id for the '<em><b>Container Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__CONTAINER_PATH = 4;

	/**
	 * The feature id for the '<em><b>Update Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__UPDATE_TIME = 5;

	/**
	 * The feature id for the '<em><b>Committed Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT__COMMITTED_VERSION = 6;

	/**
	 * The number of structural features of the '<em>Artifact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactSummaryEntryImpl <em>Artifact Summary Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactSummaryEntryImpl
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactSummaryEntry()
	 * @generated
	 */
	int ARTIFACT_SUMMARY_ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Artifact</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_SUMMARY_ENTRY__ARTIFACT = 0;

	/**
	 * The feature id for the '<em><b>Operation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_SUMMARY_ENTRY__OPERATION_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Artifact Changer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_SUMMARY_ENTRY__ARTIFACT_CHANGER = 2;

	/**
	 * The feature id for the '<em><b>Commit Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_SUMMARY_ENTRY__COMMIT_STATUS = 3;

	/**
	 * The number of structural features of the '<em>Artifact Summary Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARTIFACT_SUMMARY_ENTRY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.artifacts.impl.RMSRepoImpl <em>RMS Repo</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.artifacts.impl.RMSRepoImpl
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getRMSRepo()
	 * @generated
	 */
	int RMS_REPO = 3;

	/**
	 * The feature id for the '<em><b>Repo URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RMS_REPO__REPO_URL = 0;

	/**
	 * The feature id for the '<em><b>Rms Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RMS_REPO__RMS_PROJECT = 1;

	/**
	 * The number of structural features of the '<em>RMS Repo</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RMS_REPO_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsType <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsType
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactsType()
	 * @generated
	 */
	int ARTIFACTS_TYPE = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactOperation <em>Artifact Operation</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactOperation
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactOperation()
	 * @generated
	 */
	int ARTIFACT_OPERATION = 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactChanger <em>Artifact Changer</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactChanger
	 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactChanger()
	 * @generated
	 */
	int ARTIFACT_CHANGER = 6;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary <em>Summary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Summary</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsSummary
	 * @generated
	 */
	EClass getArtifactsSummary();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getName()
	 * @see #getArtifactsSummary()
	 * @generated
	 */
	EAttribute getArtifactsSummary_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getAddedArtifacts <em>Added Artifacts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Added Artifacts</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getAddedArtifacts()
	 * @see #getArtifactsSummary()
	 * @generated
	 */
	EReference getArtifactsSummary_AddedArtifacts();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getModifiedArtifacts <em>Modified Artifacts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Modified Artifacts</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getModifiedArtifacts()
	 * @see #getArtifactsSummary()
	 * @generated
	 */
	EReference getArtifactsSummary_ModifiedArtifacts();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getDeletedArtifacts <em>Deleted Artifacts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deleted Artifacts</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getDeletedArtifacts()
	 * @see #getArtifactsSummary()
	 * @generated
	 */
	EReference getArtifactsSummary_DeletedArtifacts();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getRmsRepo <em>Rms Repo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rms Repo</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsSummary#getRmsRepo()
	 * @see #getArtifactsSummary()
	 * @generated
	 */
	EReference getArtifactsSummary_RmsRepo();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.artifacts.Artifact <em>Artifact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.Artifact
	 * @generated
	 */
	EClass getArtifact();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactPath <em>Artifact Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact Path</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactPath()
	 * @see #getArtifact()
	 * @generated
	 */
	EAttribute getArtifact_ArtifactPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactType <em>Artifact Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact Type</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactType()
	 * @see #getArtifact()
	 * @generated
	 */
	EAttribute getArtifact_ArtifactType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactContent <em>Artifact Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact Content</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactContent()
	 * @see #getArtifact()
	 * @generated
	 */
	EAttribute getArtifact_ArtifactContent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactExtension <em>Artifact Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact Extension</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.Artifact#getArtifactExtension()
	 * @see #getArtifact()
	 * @generated
	 */
	EAttribute getArtifact_ArtifactExtension();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getContainerPath <em>Container Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Container Path</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.Artifact#getContainerPath()
	 * @see #getArtifact()
	 * @generated
	 */
	EAttribute getArtifact_ContainerPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getUpdateTime <em>Update Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Update Time</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.Artifact#getUpdateTime()
	 * @see #getArtifact()
	 * @generated
	 */
	EAttribute getArtifact_UpdateTime();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.Artifact#getCommittedVersion <em>Committed Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Committed Version</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.Artifact#getCommittedVersion()
	 * @see #getArtifact()
	 * @generated
	 */
	EAttribute getArtifact_CommittedVersion();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry <em>Artifact Summary Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Artifact Summary Entry</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry
	 * @generated
	 */
	EClass getArtifactSummaryEntry();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getArtifact <em>Artifact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Artifact</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getArtifact()
	 * @see #getArtifactSummaryEntry()
	 * @generated
	 */
	EReference getArtifactSummaryEntry_Artifact();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getOperationType <em>Operation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operation Type</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getOperationType()
	 * @see #getArtifactSummaryEntry()
	 * @generated
	 */
	EAttribute getArtifactSummaryEntry_OperationType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getArtifactChanger <em>Artifact Changer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact Changer</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#getArtifactChanger()
	 * @see #getArtifactSummaryEntry()
	 * @generated
	 */
	EAttribute getArtifactSummaryEntry_ArtifactChanger();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#isCommitStatus <em>Commit Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commit Status</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry#isCommitStatus()
	 * @see #getArtifactSummaryEntry()
	 * @generated
	 */
	EAttribute getArtifactSummaryEntry_CommitStatus();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.rms.artifacts.RMSRepo <em>RMS Repo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>RMS Repo</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.RMSRepo
	 * @generated
	 */
	EClass getRMSRepo();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.RMSRepo#getRepoURL <em>Repo URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repo URL</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.RMSRepo#getRepoURL()
	 * @see #getRMSRepo()
	 * @generated
	 */
	EAttribute getRMSRepo_RepoURL();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.rms.artifacts.RMSRepo#getRmsProject <em>Rms Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rms Project</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.RMSRepo#getRmsProject()
	 * @see #getRMSRepo()
	 * @generated
	 */
	EAttribute getRMSRepo_RmsProject();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsType
	 * @generated
	 */
	EEnum getArtifactsType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.rms.artifacts.ArtifactOperation <em>Artifact Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Artifact Operation</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactOperation
	 * @generated
	 */
	EEnum getArtifactOperation();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.rms.artifacts.ArtifactChanger <em>Artifact Changer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Artifact Changer</em>'.
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactChanger
	 * @generated
	 */
	EEnum getArtifactChanger();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ArtifactsFactory getArtifactsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl <em>Summary</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactsSummary()
		 * @generated
		 */
		EClass ARTIFACTS_SUMMARY = eINSTANCE.getArtifactsSummary();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACTS_SUMMARY__NAME = eINSTANCE.getArtifactsSummary_Name();

		/**
		 * The meta object literal for the '<em><b>Added Artifacts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACTS_SUMMARY__ADDED_ARTIFACTS = eINSTANCE.getArtifactsSummary_AddedArtifacts();

		/**
		 * The meta object literal for the '<em><b>Modified Artifacts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS = eINSTANCE.getArtifactsSummary_ModifiedArtifacts();

		/**
		 * The meta object literal for the '<em><b>Deleted Artifacts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACTS_SUMMARY__DELETED_ARTIFACTS = eINSTANCE.getArtifactsSummary_DeletedArtifacts();

		/**
		 * The meta object literal for the '<em><b>Rms Repo</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACTS_SUMMARY__RMS_REPO = eINSTANCE.getArtifactsSummary_RmsRepo();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl <em>Artifact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifact()
		 * @generated
		 */
		EClass ARTIFACT = eINSTANCE.getArtifact();

		/**
		 * The meta object literal for the '<em><b>Artifact Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT__ARTIFACT_PATH = eINSTANCE.getArtifact_ArtifactPath();

		/**
		 * The meta object literal for the '<em><b>Artifact Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT__ARTIFACT_TYPE = eINSTANCE.getArtifact_ArtifactType();

		/**
		 * The meta object literal for the '<em><b>Artifact Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT__ARTIFACT_CONTENT = eINSTANCE.getArtifact_ArtifactContent();

		/**
		 * The meta object literal for the '<em><b>Artifact Extension</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT__ARTIFACT_EXTENSION = eINSTANCE.getArtifact_ArtifactExtension();

		/**
		 * The meta object literal for the '<em><b>Container Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT__CONTAINER_PATH = eINSTANCE.getArtifact_ContainerPath();

		/**
		 * The meta object literal for the '<em><b>Update Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT__UPDATE_TIME = eINSTANCE.getArtifact_UpdateTime();

		/**
		 * The meta object literal for the '<em><b>Committed Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT__COMMITTED_VERSION = eINSTANCE.getArtifact_CommittedVersion();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactSummaryEntryImpl <em>Artifact Summary Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactSummaryEntryImpl
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactSummaryEntry()
		 * @generated
		 */
		EClass ARTIFACT_SUMMARY_ENTRY = eINSTANCE.getArtifactSummaryEntry();

		/**
		 * The meta object literal for the '<em><b>Artifact</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARTIFACT_SUMMARY_ENTRY__ARTIFACT = eINSTANCE.getArtifactSummaryEntry_Artifact();

		/**
		 * The meta object literal for the '<em><b>Operation Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_SUMMARY_ENTRY__OPERATION_TYPE = eINSTANCE.getArtifactSummaryEntry_OperationType();

		/**
		 * The meta object literal for the '<em><b>Artifact Changer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_SUMMARY_ENTRY__ARTIFACT_CHANGER = eINSTANCE.getArtifactSummaryEntry_ArtifactChanger();

		/**
		 * The meta object literal for the '<em><b>Commit Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARTIFACT_SUMMARY_ENTRY__COMMIT_STATUS = eINSTANCE.getArtifactSummaryEntry_CommitStatus();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.artifacts.impl.RMSRepoImpl <em>RMS Repo</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.artifacts.impl.RMSRepoImpl
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getRMSRepo()
		 * @generated
		 */
		EClass RMS_REPO = eINSTANCE.getRMSRepo();

		/**
		 * The meta object literal for the '<em><b>Repo URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RMS_REPO__REPO_URL = eINSTANCE.getRMSRepo_RepoURL();

		/**
		 * The meta object literal for the '<em><b>Rms Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RMS_REPO__RMS_PROJECT = eINSTANCE.getRMSRepo_RmsProject();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactsType <em>Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsType
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactsType()
		 * @generated
		 */
		EEnum ARTIFACTS_TYPE = eINSTANCE.getArtifactsType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactOperation <em>Artifact Operation</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.artifacts.ArtifactOperation
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactOperation()
		 * @generated
		 */
		EEnum ARTIFACT_OPERATION = eINSTANCE.getArtifactOperation();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.rms.artifacts.ArtifactChanger <em>Artifact Changer</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.rms.artifacts.ArtifactChanger
		 * @see com.tibco.cep.studio.rms.artifacts.impl.ArtifactsPackageImpl#getArtifactChanger()
		 * @generated
		 */
		EEnum ARTIFACT_CHANGER = eINSTANCE.getArtifactChanger();

	}

} //ArtifactsPackage
