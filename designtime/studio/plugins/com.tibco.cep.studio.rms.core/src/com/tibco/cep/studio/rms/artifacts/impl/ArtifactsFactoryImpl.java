/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

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
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArtifactsFactoryImpl extends EFactoryImpl implements ArtifactsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ArtifactsFactory init() {
		try {
			ArtifactsFactory theArtifactsFactory = (ArtifactsFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/rms/artifacts/artifacts_summary.ecore"); 
			if (theArtifactsFactory != null) {
				return theArtifactsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ArtifactsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ArtifactsPackage.ARTIFACTS_SUMMARY: return createArtifactsSummary();
			case ArtifactsPackage.ARTIFACT: return createArtifact();
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY: return createArtifactSummaryEntry();
			case ArtifactsPackage.RMS_REPO: return createRMSRepo();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ArtifactsPackage.ARTIFACTS_TYPE:
				return createArtifactsTypeFromString(eDataType, initialValue);
			case ArtifactsPackage.ARTIFACT_OPERATION:
				return createArtifactOperationFromString(eDataType, initialValue);
			case ArtifactsPackage.ARTIFACT_CHANGER:
				return createArtifactChangerFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ArtifactsPackage.ARTIFACTS_TYPE:
				return convertArtifactsTypeToString(eDataType, instanceValue);
			case ArtifactsPackage.ARTIFACT_OPERATION:
				return convertArtifactOperationToString(eDataType, instanceValue);
			case ArtifactsPackage.ARTIFACT_CHANGER:
				return convertArtifactChangerToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactsSummary createArtifactsSummary() {
		ArtifactsSummaryImpl artifactsSummary = new ArtifactsSummaryImpl();
		return artifactsSummary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Artifact createArtifact() {
		ArtifactImpl artifact = new ArtifactImpl();
		return artifact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactSummaryEntry createArtifactSummaryEntry() {
		ArtifactSummaryEntryImpl artifactSummaryEntry = new ArtifactSummaryEntryImpl();
		return artifactSummaryEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RMSRepo createRMSRepo() {
		RMSRepoImpl rmsRepo = new RMSRepoImpl();
		return rmsRepo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactsType createArtifactsTypeFromString(EDataType eDataType, String initialValue) {
		ArtifactsType result = ArtifactsType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertArtifactsTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactOperation createArtifactOperationFromString(EDataType eDataType, String initialValue) {
		ArtifactOperation result = ArtifactOperation.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertArtifactOperationToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactChanger createArtifactChangerFromString(EDataType eDataType, String initialValue) {
		ArtifactChanger result = ArtifactChanger.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertArtifactChangerToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactsPackage getArtifactsPackage() {
		return (ArtifactsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ArtifactsPackage getPackage() {
		return ArtifactsPackage.eINSTANCE;
	}

} //ArtifactsFactoryImpl
