/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage
 * @generated
 */
public interface ConfigurationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConfigurationFactory eINSTANCE = com.tibco.cep.studio.common.configuration.impl.ConfigurationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Studio Project Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Studio Project Configuration</em>'.
	 * @generated
	 */
	StudioProjectConfiguration createStudioProjectConfiguration();

	/**
	 * Returns a new object of class '<em>Project Configuration Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Configuration Entry</em>'.
	 * @generated
	 */
	ProjectConfigurationEntry createProjectConfigurationEntry();

	/**
	 * Returns a new object of class '<em>Build Path Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Build Path Entry</em>'.
	 * @generated
	 */
	BuildPathEntry createBuildPathEntry();

	/**
	 * Returns a new object of class '<em>Project Library Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Library Entry</em>'.
	 * @generated
	 */
	ProjectLibraryEntry createProjectLibraryEntry();

	/**
	 * Returns a new object of class '<em>Third Party Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Third Party Lib Entry</em>'.
	 * @generated
	 */
	ThirdPartyLibEntry createThirdPartyLibEntry();

	/**
	 * Returns a new object of class '<em>Java Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Lib Entry</em>'.
	 * @generated
	 */
	JavaLibEntry createJavaLibEntry();

	/**
	 * Returns a new object of class '<em>Core Java Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Core Java Lib Entry</em>'.
	 * @generated
	 */
	CoreJavaLibEntry createCoreJavaLibEntry();

	/**
	 * Returns a new object of class '<em>Custom Function Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Custom Function Lib Entry</em>'.
	 * @generated
	 */
	CustomFunctionLibEntry createCustomFunctionLibEntry();

	/**
	 * Returns a new object of class '<em>Enterprise Archive Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enterprise Archive Entry</em>'.
	 * @generated
	 */
	EnterpriseArchiveEntry createEnterpriseArchiveEntry();

	/**
	 * Returns a new object of class '<em>Bpmn Process Settings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bpmn Process Settings</em>'.
	 * @generated
	 */
	BpmnProcessSettings createBpmnProcessSettings();

	/**
	 * Returns a new object of class '<em>Bpmn Palette Path Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bpmn Palette Path Entry</em>'.
	 * @generated
	 */
	BpmnPalettePathEntry createBpmnPalettePathEntry();

	/**
	 * Returns a new object of class '<em>Bpmn Process Path Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bpmn Process Path Entry</em>'.
	 * @generated
	 */
	BpmnProcessPathEntry createBpmnProcessPathEntry();

	/**
	 * Returns a new object of class '<em>Name Prefix</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Name Prefix</em>'.
	 * @generated
	 */
	NamePrefix createNamePrefix();

	/**
	 * Returns a new object of class '<em>Native Library Path</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Native Library Path</em>'.
	 * @generated
	 */
	NativeLibraryPath createNativeLibraryPath();

	/**
	 * Returns a new object of class '<em>Java Classpath Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Classpath Entry</em>'.
	 * @generated
	 */
	JavaClasspathEntry createJavaClasspathEntry();

	/**
	 * Returns a new object of class '<em>Java Source Folder Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Source Folder Entry</em>'.
	 * @generated
	 */
	JavaSourceFolderEntry createJavaSourceFolderEntry();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ConfigurationPackage getConfigurationPackage();

} //ConfigurationFactory
