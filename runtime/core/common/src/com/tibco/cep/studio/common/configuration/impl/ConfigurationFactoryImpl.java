/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.impl;

import com.tibco.cep.studio.common.configuration.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.NamePrefix;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;
import com.tibco.cep.studio.common.configuration.ProjectConfigurationEntry;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigurationFactoryImpl extends EFactoryImpl implements ConfigurationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConfigurationFactory init() {
		try {
			ConfigurationFactory theConfigurationFactory = (ConfigurationFactory)EPackage.Registry.INSTANCE.getEFactory(ConfigurationPackage.eNS_URI);
			if (theConfigurationFactory != null) {
				return theConfigurationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ConfigurationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationFactoryImpl() {
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
			case ConfigurationPackage.STUDIO_PROJECT_CONFIGURATION: return createStudioProjectConfiguration();
			case ConfigurationPackage.PROJECT_CONFIGURATION_ENTRY: return createProjectConfigurationEntry();
			case ConfigurationPackage.BUILD_PATH_ENTRY: return createBuildPathEntry();
			case ConfigurationPackage.PROJECT_LIBRARY_ENTRY: return createProjectLibraryEntry();
			case ConfigurationPackage.THIRD_PARTY_LIB_ENTRY: return createThirdPartyLibEntry();
			case ConfigurationPackage.JAVA_LIB_ENTRY: return createJavaLibEntry();
			case ConfigurationPackage.CORE_JAVA_LIB_ENTRY: return createCoreJavaLibEntry();
			case ConfigurationPackage.CUSTOM_FUNCTION_LIB_ENTRY: return createCustomFunctionLibEntry();
			case ConfigurationPackage.ENTERPRISE_ARCHIVE_ENTRY: return createEnterpriseArchiveEntry();
			case ConfigurationPackage.BPMN_PROCESS_SETTINGS: return createBpmnProcessSettings();
			case ConfigurationPackage.BPMN_PALETTE_PATH_ENTRY: return createBpmnPalettePathEntry();
			case ConfigurationPackage.BPMN_PROCESS_PATH_ENTRY: return createBpmnProcessPathEntry();
			case ConfigurationPackage.NAME_PREFIX: return createNamePrefix();
			case ConfigurationPackage.NATIVE_LIBRARY_PATH: return createNativeLibraryPath();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY: return createJavaClasspathEntry();
			case ConfigurationPackage.JAVA_SOURCE_FOLDER_ENTRY: return createJavaSourceFolderEntry();
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
			case ConfigurationPackage.LIBRARY_PATH_TYPE:
				return createLIBRARY_PATH_TYPEFromString(eDataType, initialValue);
			case ConfigurationPackage.XPATH_VERSION:
				return createXPATH_VERSIONFromString(eDataType, initialValue);
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY_TYPE:
				return createJAVA_CLASSPATH_ENTRY_TYPEFromString(eDataType, initialValue);
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
			case ConfigurationPackage.LIBRARY_PATH_TYPE:
				return convertLIBRARY_PATH_TYPEToString(eDataType, instanceValue);
			case ConfigurationPackage.XPATH_VERSION:
				return convertXPATH_VERSIONToString(eDataType, instanceValue);
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY_TYPE:
				return convertJAVA_CLASSPATH_ENTRY_TYPEToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StudioProjectConfiguration createStudioProjectConfiguration() {
		StudioProjectConfigurationImpl studioProjectConfiguration = new StudioProjectConfigurationImpl();
		return studioProjectConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectConfigurationEntry createProjectConfigurationEntry() {
		ProjectConfigurationEntryImpl projectConfigurationEntry = new ProjectConfigurationEntryImpl();
		return projectConfigurationEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildPathEntry createBuildPathEntry() {
		BuildPathEntryImpl buildPathEntry = new BuildPathEntryImpl();
		return buildPathEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectLibraryEntry createProjectLibraryEntry() {
		ProjectLibraryEntryImpl projectLibraryEntry = new ProjectLibraryEntryImpl();
		return projectLibraryEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThirdPartyLibEntry createThirdPartyLibEntry() {
		ThirdPartyLibEntryImpl thirdPartyLibEntry = new ThirdPartyLibEntryImpl();
		return thirdPartyLibEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaLibEntry createJavaLibEntry() {
		JavaLibEntryImpl javaLibEntry = new JavaLibEntryImpl();
		return javaLibEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoreJavaLibEntry createCoreJavaLibEntry() {
		CoreJavaLibEntryImpl coreJavaLibEntry = new CoreJavaLibEntryImpl();
		return coreJavaLibEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomFunctionLibEntry createCustomFunctionLibEntry() {
		CustomFunctionLibEntryImpl customFunctionLibEntry = new CustomFunctionLibEntryImpl();
		return customFunctionLibEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnterpriseArchiveEntry createEnterpriseArchiveEntry() {
		EnterpriseArchiveEntryImpl enterpriseArchiveEntry = new EnterpriseArchiveEntryImpl();
		return enterpriseArchiveEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BpmnProcessSettings createBpmnProcessSettings() {
		BpmnProcessSettingsImpl bpmnProcessSettings = new BpmnProcessSettingsImpl();
		return bpmnProcessSettings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BpmnPalettePathEntry createBpmnPalettePathEntry() {
		BpmnPalettePathEntryImpl bpmnPalettePathEntry = new BpmnPalettePathEntryImpl();
		return bpmnPalettePathEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BpmnProcessPathEntry createBpmnProcessPathEntry() {
		BpmnProcessPathEntryImpl bpmnProcessPathEntry = new BpmnProcessPathEntryImpl();
		return bpmnProcessPathEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamePrefix createNamePrefix() {
		NamePrefixImpl namePrefix = new NamePrefixImpl();
		return namePrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NativeLibraryPath createNativeLibraryPath() {
		NativeLibraryPathImpl nativeLibraryPath = new NativeLibraryPathImpl();
		return nativeLibraryPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaClasspathEntry createJavaClasspathEntry() {
		JavaClasspathEntryImpl javaClasspathEntry = new JavaClasspathEntryImpl();
		return javaClasspathEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaSourceFolderEntry createJavaSourceFolderEntry() {
		JavaSourceFolderEntryImpl javaSourceFolderEntry = new JavaSourceFolderEntryImpl();
		return javaSourceFolderEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LIBRARY_PATH_TYPE createLIBRARY_PATH_TYPEFromString(EDataType eDataType, String initialValue) {
		LIBRARY_PATH_TYPE result = LIBRARY_PATH_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLIBRARY_PATH_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XPATH_VERSION createXPATH_VERSIONFromString(EDataType eDataType, String initialValue) {
		XPATH_VERSION result = XPATH_VERSION.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXPATH_VERSIONToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JAVA_CLASSPATH_ENTRY_TYPE createJAVA_CLASSPATH_ENTRY_TYPEFromString(EDataType eDataType, String initialValue) {
		JAVA_CLASSPATH_ENTRY_TYPE result = JAVA_CLASSPATH_ENTRY_TYPE.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJAVA_CLASSPATH_ENTRY_TYPEToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationPackage getConfigurationPackage() {
		return (ConfigurationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ConfigurationPackage getPackage() {
		return ConfigurationPackage.eINSTANCE;
	}

} //ConfigurationFactoryImpl
