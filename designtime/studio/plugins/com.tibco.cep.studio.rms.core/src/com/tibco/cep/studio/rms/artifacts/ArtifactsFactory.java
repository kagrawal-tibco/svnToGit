/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage
 * @generated
 */
public interface ArtifactsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArtifactsFactory eINSTANCE = com.tibco.cep.studio.rms.artifacts.impl.ArtifactsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Summary</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Summary</em>'.
	 * @generated
	 */
	ArtifactsSummary createArtifactsSummary();

	/**
	 * Returns a new object of class '<em>Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Artifact</em>'.
	 * @generated
	 */
	Artifact createArtifact();

	/**
	 * Returns a new object of class '<em>Artifact Summary Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Artifact Summary Entry</em>'.
	 * @generated
	 */
	ArtifactSummaryEntry createArtifactSummaryEntry();

	/**
	 * Returns a new object of class '<em>RMS Repo</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>RMS Repo</em>'.
	 * @generated
	 */
	RMSRepo createRMSRepo();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ArtifactsPackage getArtifactsPackage();

} //ArtifactsFactory
