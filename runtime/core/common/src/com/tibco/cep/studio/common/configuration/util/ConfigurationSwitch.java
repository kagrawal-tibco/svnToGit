/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.util;

import com.tibco.cep.studio.common.configuration.*;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.common.configuration.NamePrefix;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;
import com.tibco.cep.studio.common.configuration.ProjectConfigurationEntry;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage
 * @generated
 */
public class ConfigurationSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ConfigurationPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationSwitch() {
		if (modelPackage == null) {
			modelPackage = ConfigurationPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION: {
				StudioProjectConfiguration studioProjectConfiguration = (StudioProjectConfiguration)theEObject;
				T result = caseStudioProjectConfiguration(studioProjectConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.PROJECT_CONFIGURATION_ENTRY: {
				ProjectConfigurationEntry projectConfigurationEntry = (ProjectConfigurationEntry)theEObject;
				T result = caseProjectConfigurationEntry(projectConfigurationEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.BUILD_PATH_ENTRY: {
				BuildPathEntry buildPathEntry = (BuildPathEntry)theEObject;
				T result = caseBuildPathEntry(buildPathEntry);
				if (result == null) result = caseProjectConfigurationEntry(buildPathEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.PROJECT_LIBRARY_ENTRY: {
				ProjectLibraryEntry projectLibraryEntry = (ProjectLibraryEntry)theEObject;
				T result = caseProjectLibraryEntry(projectLibraryEntry);
				if (result == null) result = caseBuildPathEntry(projectLibraryEntry);
				if (result == null) result = caseProjectConfigurationEntry(projectLibraryEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.THIRD_PARTY_LIB_ENTRY: {
				ThirdPartyLibEntry thirdPartyLibEntry = (ThirdPartyLibEntry)theEObject;
				T result = caseThirdPartyLibEntry(thirdPartyLibEntry);
				if (result == null) result = caseJavaLibEntry(thirdPartyLibEntry);
				if (result == null) result = caseBuildPathEntry(thirdPartyLibEntry);
				if (result == null) result = caseProjectConfigurationEntry(thirdPartyLibEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.JAVA_LIB_ENTRY: {
				JavaLibEntry javaLibEntry = (JavaLibEntry)theEObject;
				T result = caseJavaLibEntry(javaLibEntry);
				if (result == null) result = caseBuildPathEntry(javaLibEntry);
				if (result == null) result = caseProjectConfigurationEntry(javaLibEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.CORE_JAVA_LIB_ENTRY: {
				CoreJavaLibEntry coreJavaLibEntry = (CoreJavaLibEntry)theEObject;
				T result = caseCoreJavaLibEntry(coreJavaLibEntry);
				if (result == null) result = caseJavaLibEntry(coreJavaLibEntry);
				if (result == null) result = caseBuildPathEntry(coreJavaLibEntry);
				if (result == null) result = caseProjectConfigurationEntry(coreJavaLibEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.CUSTOM_FUNCTION_LIB_ENTRY: {
				CustomFunctionLibEntry customFunctionLibEntry = (CustomFunctionLibEntry)theEObject;
				T result = caseCustomFunctionLibEntry(customFunctionLibEntry);
				if (result == null) result = caseJavaLibEntry(customFunctionLibEntry);
				if (result == null) result = caseBuildPathEntry(customFunctionLibEntry);
				if (result == null) result = caseProjectConfigurationEntry(customFunctionLibEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY: {
				EnterpriseArchiveEntry enterpriseArchiveEntry = (EnterpriseArchiveEntry)theEObject;
				T result = caseEnterpriseArchiveEntry(enterpriseArchiveEntry);
				if (result == null) result = caseProjectConfigurationEntry(enterpriseArchiveEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS: {
				BpmnProcessSettings bpmnProcessSettings = (BpmnProcessSettings)theEObject;
				T result = caseBpmnProcessSettings(bpmnProcessSettings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.BPMN_PALETTE_PATH_ENTRY: {
				BpmnPalettePathEntry bpmnPalettePathEntry = (BpmnPalettePathEntry)theEObject;
				T result = caseBpmnPalettePathEntry(bpmnPalettePathEntry);
				if (result == null) result = caseBuildPathEntry(bpmnPalettePathEntry);
				if (result == null) result = caseProjectConfigurationEntry(bpmnPalettePathEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.BPMN_PROCESS_PATH_ENTRY: {
				BpmnProcessPathEntry bpmnProcessPathEntry = (BpmnProcessPathEntry)theEObject;
				T result = caseBpmnProcessPathEntry(bpmnProcessPathEntry);
				if (result == null) result = caseBuildPathEntry(bpmnProcessPathEntry);
				if (result == null) result = caseProjectConfigurationEntry(bpmnProcessPathEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.NAME_PREFIX: {
				NamePrefix namePrefix = (NamePrefix)theEObject;
				T result = caseNamePrefix(namePrefix);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.NATIVE_LIBRARY_PATH: {
				NativeLibraryPath nativeLibraryPath = (NativeLibraryPath)theEObject;
				T result = caseNativeLibraryPath(nativeLibraryPath);
				if (result == null) result = caseBuildPathEntry(nativeLibraryPath);
				if (result == null) result = caseProjectConfigurationEntry(nativeLibraryPath);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY: {
				JavaClasspathEntry javaClasspathEntry = (JavaClasspathEntry)theEObject;
				T result = caseJavaClasspathEntry(javaClasspathEntry);
				if (result == null) result = caseBuildPathEntry(javaClasspathEntry);
				if (result == null) result = caseProjectConfigurationEntry(javaClasspathEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ConfigurationPackage.JAVA_SOURCE_FOLDER_ENTRY: {
				JavaSourceFolderEntry javaSourceFolderEntry = (JavaSourceFolderEntry)theEObject;
				T result = caseJavaSourceFolderEntry(javaSourceFolderEntry);
				if (result == null) result = caseBuildPathEntry(javaSourceFolderEntry);
				if (result == null) result = caseProjectConfigurationEntry(javaSourceFolderEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Studio Project Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Studio Project Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStudioProjectConfiguration(StudioProjectConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project Configuration Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Configuration Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectConfigurationEntry(ProjectConfigurationEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Build Path Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Build Path Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBuildPathEntry(BuildPathEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project Library Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Library Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectLibraryEntry(ProjectLibraryEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Third Party Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Third Party Lib Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThirdPartyLibEntry(ThirdPartyLibEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Lib Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaLibEntry(JavaLibEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Core Java Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Core Java Lib Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCoreJavaLibEntry(CoreJavaLibEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Custom Function Lib Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Custom Function Lib Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomFunctionLibEntry(CustomFunctionLibEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enterprise Archive Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enterprise Archive Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnterpriseArchiveEntry(EnterpriseArchiveEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bpmn Process Settings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bpmn Process Settings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBpmnProcessSettings(BpmnProcessSettings object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bpmn Palette Path Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bpmn Palette Path Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBpmnPalettePathEntry(BpmnPalettePathEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bpmn Process Path Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bpmn Process Path Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBpmnProcessPathEntry(BpmnProcessPathEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Name Prefix</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Name Prefix</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamePrefix(NamePrefix object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Native Library Path</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Native Library Path</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNativeLibraryPath(NativeLibraryPath object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Classpath Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Classpath Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaClasspathEntry(JavaClasspathEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Source Folder Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Source Folder Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaSourceFolderEntry(JavaSourceFolderEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ConfigurationSwitch
