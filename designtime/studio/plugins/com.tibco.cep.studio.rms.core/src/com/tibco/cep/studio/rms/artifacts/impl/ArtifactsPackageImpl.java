/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsPackage;
import com.tibco.cep.studio.rms.artifacts.ArtifactsSummary;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.artifacts.RMSRepo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArtifactsPackageImpl extends EPackageImpl implements ArtifactsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactsSummaryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactSummaryEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rmsRepoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum artifactsTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum artifactOperationEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum artifactChangerEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ArtifactsPackageImpl() {
		super(eNS_URI, ArtifactsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ArtifactsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ArtifactsPackage init() {
		if (isInited) return (ArtifactsPackage)EPackage.Registry.INSTANCE.getEPackage(ArtifactsPackage.eNS_URI);

		// Obtain or create and register package
		ArtifactsPackageImpl theArtifactsPackage = (ArtifactsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ArtifactsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ArtifactsPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theArtifactsPackage.createPackageContents();

		// Initialize created meta-data
		theArtifactsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theArtifactsPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ArtifactsPackage.eNS_URI, theArtifactsPackage);
		return theArtifactsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifactsSummary() {
		return artifactsSummaryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactsSummary_Name() {
		return (EAttribute)artifactsSummaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifactsSummary_AddedArtifacts() {
		return (EReference)artifactsSummaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifactsSummary_ModifiedArtifacts() {
		return (EReference)artifactsSummaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifactsSummary_DeletedArtifacts() {
		return (EReference)artifactsSummaryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifactsSummary_RmsRepo() {
		return (EReference)artifactsSummaryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifact() {
		return artifactEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifact_ArtifactPath() {
		return (EAttribute)artifactEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifact_ArtifactType() {
		return (EAttribute)artifactEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifact_ArtifactContent() {
		return (EAttribute)artifactEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifact_ArtifactExtension() {
		return (EAttribute)artifactEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifact_ContainerPath() {
		return (EAttribute)artifactEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifact_UpdateTime() {
		return (EAttribute)artifactEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifact_CommittedVersion() {
		return (EAttribute)artifactEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifactSummaryEntry() {
		return artifactSummaryEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifactSummaryEntry_Artifact() {
		return (EReference)artifactSummaryEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactSummaryEntry_OperationType() {
		return (EAttribute)artifactSummaryEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactSummaryEntry_ArtifactChanger() {
		return (EAttribute)artifactSummaryEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactSummaryEntry_CommitStatus() {
		return (EAttribute)artifactSummaryEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRMSRepo() {
		return rmsRepoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRMSRepo_RepoURL() {
		return (EAttribute)rmsRepoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRMSRepo_RmsProject() {
		return (EAttribute)rmsRepoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getArtifactsType() {
		return artifactsTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getArtifactOperation() {
		return artifactOperationEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getArtifactChanger() {
		return artifactChangerEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactsFactory getArtifactsFactory() {
		return (ArtifactsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		artifactsSummaryEClass = createEClass(ARTIFACTS_SUMMARY);
		createEAttribute(artifactsSummaryEClass, ARTIFACTS_SUMMARY__NAME);
		createEReference(artifactsSummaryEClass, ARTIFACTS_SUMMARY__ADDED_ARTIFACTS);
		createEReference(artifactsSummaryEClass, ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS);
		createEReference(artifactsSummaryEClass, ARTIFACTS_SUMMARY__DELETED_ARTIFACTS);
		createEReference(artifactsSummaryEClass, ARTIFACTS_SUMMARY__RMS_REPO);

		artifactEClass = createEClass(ARTIFACT);
		createEAttribute(artifactEClass, ARTIFACT__ARTIFACT_PATH);
		createEAttribute(artifactEClass, ARTIFACT__ARTIFACT_TYPE);
		createEAttribute(artifactEClass, ARTIFACT__ARTIFACT_CONTENT);
		createEAttribute(artifactEClass, ARTIFACT__ARTIFACT_EXTENSION);
		createEAttribute(artifactEClass, ARTIFACT__CONTAINER_PATH);
		createEAttribute(artifactEClass, ARTIFACT__UPDATE_TIME);
		createEAttribute(artifactEClass, ARTIFACT__COMMITTED_VERSION);

		artifactSummaryEntryEClass = createEClass(ARTIFACT_SUMMARY_ENTRY);
		createEReference(artifactSummaryEntryEClass, ARTIFACT_SUMMARY_ENTRY__ARTIFACT);
		createEAttribute(artifactSummaryEntryEClass, ARTIFACT_SUMMARY_ENTRY__OPERATION_TYPE);
		createEAttribute(artifactSummaryEntryEClass, ARTIFACT_SUMMARY_ENTRY__ARTIFACT_CHANGER);
		createEAttribute(artifactSummaryEntryEClass, ARTIFACT_SUMMARY_ENTRY__COMMIT_STATUS);

		rmsRepoEClass = createEClass(RMS_REPO);
		createEAttribute(rmsRepoEClass, RMS_REPO__REPO_URL);
		createEAttribute(rmsRepoEClass, RMS_REPO__RMS_PROJECT);

		// Create enums
		artifactsTypeEEnum = createEEnum(ARTIFACTS_TYPE);
		artifactOperationEEnum = createEEnum(ARTIFACT_OPERATION);
		artifactChangerEEnum = createEEnum(ARTIFACT_CHANGER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(artifactsSummaryEClass, ArtifactsSummary.class, "ArtifactsSummary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArtifactsSummary_Name(), ecorePackage.getEString(), "name", null, 0, 1, ArtifactsSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArtifactsSummary_AddedArtifacts(), this.getArtifactSummaryEntry(), null, "addedArtifacts", null, 0, -1, ArtifactsSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArtifactsSummary_ModifiedArtifacts(), this.getArtifactSummaryEntry(), null, "modifiedArtifacts", null, 0, -1, ArtifactsSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArtifactsSummary_DeletedArtifacts(), this.getArtifactSummaryEntry(), null, "deletedArtifacts", null, 0, -1, ArtifactsSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArtifactsSummary_RmsRepo(), this.getRMSRepo(), null, "rmsRepo", null, 0, 1, ArtifactsSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(artifactEClass, Artifact.class, "Artifact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArtifact_ArtifactPath(), ecorePackage.getEString(), "artifactPath", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifact_ArtifactType(), this.getArtifactsType(), "artifactType", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifact_ArtifactContent(), ecorePackage.getEString(), "artifactContent", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifact_ArtifactExtension(), ecorePackage.getEString(), "artifactExtension", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifact_ContainerPath(), ecorePackage.getEString(), "containerPath", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifact_UpdateTime(), ecorePackage.getEDate(), "updateTime", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifact_CommittedVersion(), ecorePackage.getEString(), "committedVersion", "", 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(artifactSummaryEntryEClass, ArtifactSummaryEntry.class, "ArtifactSummaryEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArtifactSummaryEntry_Artifact(), this.getArtifact(), null, "artifact", null, 0, 1, ArtifactSummaryEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifactSummaryEntry_OperationType(), this.getArtifactOperation(), "operationType", null, 0, 1, ArtifactSummaryEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifactSummaryEntry_ArtifactChanger(), this.getArtifactChanger(), "artifactChanger", null, 0, 1, ArtifactSummaryEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArtifactSummaryEntry_CommitStatus(), ecorePackage.getEBoolean(), "commitStatus", "false", 0, 1, ArtifactSummaryEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rmsRepoEClass, RMSRepo.class, "RMSRepo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRMSRepo_RepoURL(), ecorePackage.getEString(), "repoURL", null, 0, 1, RMSRepo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRMSRepo_RmsProject(), ecorePackage.getEString(), "rmsProject", null, 0, 1, RMSRepo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(artifactsTypeEEnum, ArtifactsType.class, "ArtifactsType");
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.DECISIONTABLE);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.DOMAIN);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.RULE);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.RULEFUNCTION);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.CONCEPT);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.EVENT);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.TIMEEVENT);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.SCORECARD);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.METRIC);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.CHANNEL);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.STATEMACHINE);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.SHAREDHTTP);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.RVTRANSPORT);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.SHAREDJMSCON);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.SHAREDJDBC);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.SHAREDJNDICONFIG);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.IDENTITY);
		addEEnumLiteral(artifactsTypeEEnum, ArtifactsType.GLOBALVARIABLES);

		initEEnum(artifactOperationEEnum, ArtifactOperation.class, "ArtifactOperation");
		addEEnumLiteral(artifactOperationEEnum, ArtifactOperation.ADD);
		addEEnumLiteral(artifactOperationEEnum, ArtifactOperation.MODIFY);
		addEEnumLiteral(artifactOperationEEnum, ArtifactOperation.DELETE);

		initEEnum(artifactChangerEEnum, ArtifactChanger.class, "ArtifactChanger");
		addEEnumLiteral(artifactChangerEEnum, ArtifactChanger.LOCAL);
		addEEnumLiteral(artifactChangerEEnum, ArtifactChanger.REMOTE);

		// Create resource
		createResource(eNS_URI);
	}

} //ArtifactsPackageImpl
