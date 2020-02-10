/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ConfigurationFactory;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.CoreJavaLibEntry;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry;
import com.tibco.cep.studio.common.configuration.JavaClasspathEntry;
import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.NamePrefix;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;
import com.tibco.cep.studio.common.configuration.ProjectConfigurationEntry;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigurationPackageImpl extends EPackageImpl implements ConfigurationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass studioProjectConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectConfigurationEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass buildPathEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectLibraryEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass thirdPartyLibEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaLibEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass coreJavaLibEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass customFunctionLibEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enterpriseArchiveEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bpmnProcessSettingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bpmnPalettePathEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bpmnProcessPathEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namePrefixEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nativeLibraryPathEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaClasspathEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaSourceFolderEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum librarY_PATH_TYPEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum xpatH_VERSIONEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum javA_CLASSPATH_ENTRY_TYPEEEnum = null;

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
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConfigurationPackageImpl() {
		super(eNS_URI, ConfigurationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ConfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConfigurationPackage init() {
		if (isInited) return (ConfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);

		// Obtain or create and register package
		ConfigurationPackageImpl theConfigurationPackage = (ConfigurationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ConfigurationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ConfigurationPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theConfigurationPackage.createPackageContents();

		// Initialize created meta-data
		theConfigurationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConfigurationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConfigurationPackage.eNS_URI, theConfigurationPackage);
		return theConfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStudioProjectConfiguration() {
		return studioProjectConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStudioProjectConfiguration_ThirdpartyLibEntries() {
		return (EReference)studioProjectConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStudioProjectConfiguration_CoreInternalLibEntries() {
		return (EReference)studioProjectConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStudioProjectConfiguration_CustomFunctionLibEntries() {
		return (EReference)studioProjectConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStudioProjectConfiguration_ProjectLibEntries() {
		return (EReference)studioProjectConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStudioProjectConfiguration_JavaClasspathEntries() {
		return (EReference)studioProjectConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStudioProjectConfiguration_JavaSourceFolderEntries() {
		return (EReference)studioProjectConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStudioProjectConfiguration_EnterpriseArchiveConfiguration() {
		return (EReference)studioProjectConfigurationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStudioProjectConfiguration_Name() {
		return (EAttribute)studioProjectConfigurationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStudioProjectConfiguration_Version() {
		return (EAttribute)studioProjectConfigurationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStudioProjectConfiguration_Build() {
		return (EAttribute)studioProjectConfigurationEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStudioProjectConfiguration_FileTimeStamp() {
		return (EAttribute)studioProjectConfigurationEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStudioProjectConfiguration_XpathVersion() {
		return (EAttribute)studioProjectConfigurationEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStudioProjectConfiguration_BpmnProcessSettings() {
		return (EReference)studioProjectConfigurationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProjectConfigurationEntry() {
		return projectConfigurationEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBuildPathEntry() {
		return buildPathEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildPathEntry_EntryType() {
		return (EAttribute)buildPathEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildPathEntry_Path() {
		return (EAttribute)buildPathEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildPathEntry_Timestamp() {
		return (EAttribute)buildPathEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildPathEntry_Var() {
		return (EAttribute)buildPathEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildPathEntry_ReadOnly() {
		return (EAttribute)buildPathEntryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildPathEntry_Deprecated() {
		return (EAttribute)buildPathEntryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildPathEntry_ResolvedPath() {
		return (EAttribute)buildPathEntryEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProjectLibraryEntry() {
		return projectLibraryEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getThirdPartyLibEntry() {
		return thirdPartyLibEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaLibEntry() {
		return javaLibEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJavaLibEntry_NativeLibraryLocation() {
		return (EReference)javaLibEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCoreJavaLibEntry() {
		return coreJavaLibEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCustomFunctionLibEntry() {
		return customFunctionLibEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnterpriseArchiveEntry() {
		return enterpriseArchiveEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_Name() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_Author() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_Description() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_Version() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_Path() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_IncludeServiceLevelGlobalVars() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_Debug() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_TempOutputPath() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_Overwrite() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_DeleteTempFiles() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchiveEntry_BuildClassesOnly() {
		return (EAttribute)enterpriseArchiveEntryEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBpmnProcessSettings() {
		return bpmnProcessSettingsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBpmnProcessSettings_BuildFolder() {
		return (EAttribute)bpmnProcessSettingsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBpmnProcessSettings_PalettePathEntries() {
		return (EReference)bpmnProcessSettingsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBpmnProcessSettings_SelectedProcessPaths() {
		return (EReference)bpmnProcessSettingsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBpmnProcessSettings_ProcessPrefix() {
		return (EAttribute)bpmnProcessSettingsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBpmnProcessSettings_RulePrefix() {
		return (EAttribute)bpmnProcessSettingsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBpmnProcessSettings_RuleFunctionPrefix() {
		return (EAttribute)bpmnProcessSettingsEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBpmnProcessSettings_ConceptPrefix() {
		return (EAttribute)bpmnProcessSettingsEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBpmnProcessSettings_EventPrefix() {
		return (EAttribute)bpmnProcessSettingsEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBpmnProcessSettings_TimeEventPrefix() {
		return (EAttribute)bpmnProcessSettingsEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBpmnProcessSettings_ScorecardPrefix() {
		return (EAttribute)bpmnProcessSettingsEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBpmnProcessSettings_NamePrefixes() {
		return (EReference)bpmnProcessSettingsEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBpmnPalettePathEntry() {
		return bpmnPalettePathEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBpmnProcessPathEntry() {
		return bpmnProcessPathEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamePrefix() {
		return namePrefixEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamePrefix_Name() {
		return (EAttribute)namePrefixEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamePrefix_Prefix() {
		return (EAttribute)namePrefixEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNativeLibraryPath() {
		return nativeLibraryPathEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaClasspathEntry() {
		return javaClasspathEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaClasspathEntry_ClasspathEntryType() {
		return (EAttribute)javaClasspathEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaClasspathEntry_SourceFolder() {
		return (EAttribute)javaClasspathEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaClasspathEntry_InclusionPattern() {
		return (EAttribute)javaClasspathEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaClasspathEntry_ExclusionPattern() {
		return (EAttribute)javaClasspathEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaClasspathEntry_OutputLocation() {
		return (EAttribute)javaClasspathEntryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaClasspathEntry_SourceAttachmentPath() {
		return (EAttribute)javaClasspathEntryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaClasspathEntry_SourceAttachmentRootPath() {
		return (EAttribute)javaClasspathEntryEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaSourceFolderEntry() {
		return javaSourceFolderEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getLIBRARY_PATH_TYPE() {
		return librarY_PATH_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getXPATH_VERSION() {
		return xpatH_VERSIONEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getJAVA_CLASSPATH_ENTRY_TYPE() {
		return javA_CLASSPATH_ENTRY_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationFactory getConfigurationFactory() {
		return (ConfigurationFactory)getEFactoryInstance();
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
		studioProjectConfigurationEClass = createEClass(STUDIO_PROJECT_CONFIGURATION);
		createEReference(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__THIRDPARTY_LIB_ENTRIES);
		createEReference(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__CORE_INTERNAL_LIB_ENTRIES);
		createEReference(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__CUSTOM_FUNCTION_LIB_ENTRIES);
		createEReference(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__PROJECT_LIB_ENTRIES);
		createEReference(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__JAVA_CLASSPATH_ENTRIES);
		createEReference(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__JAVA_SOURCE_FOLDER_ENTRIES);
		createEReference(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__ENTERPRISE_ARCHIVE_CONFIGURATION);
		createEReference(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__BPMN_PROCESS_SETTINGS);
		createEAttribute(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__NAME);
		createEAttribute(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__VERSION);
		createEAttribute(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__BUILD);
		createEAttribute(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__FILE_TIME_STAMP);
		createEAttribute(studioProjectConfigurationEClass, STUDIO_PROJECT_CONFIGURATION__XPATH_VERSION);

		projectConfigurationEntryEClass = createEClass(PROJECT_CONFIGURATION_ENTRY);

		buildPathEntryEClass = createEClass(BUILD_PATH_ENTRY);
		createEAttribute(buildPathEntryEClass, BUILD_PATH_ENTRY__ENTRY_TYPE);
		createEAttribute(buildPathEntryEClass, BUILD_PATH_ENTRY__PATH);
		createEAttribute(buildPathEntryEClass, BUILD_PATH_ENTRY__TIMESTAMP);
		createEAttribute(buildPathEntryEClass, BUILD_PATH_ENTRY__VAR);
		createEAttribute(buildPathEntryEClass, BUILD_PATH_ENTRY__READ_ONLY);
		createEAttribute(buildPathEntryEClass, BUILD_PATH_ENTRY__DEPRECATED);
		createEAttribute(buildPathEntryEClass, BUILD_PATH_ENTRY__RESOLVED_PATH);

		projectLibraryEntryEClass = createEClass(PROJECT_LIBRARY_ENTRY);

		thirdPartyLibEntryEClass = createEClass(THIRD_PARTY_LIB_ENTRY);

		javaLibEntryEClass = createEClass(JAVA_LIB_ENTRY);
		createEReference(javaLibEntryEClass, JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION);

		coreJavaLibEntryEClass = createEClass(CORE_JAVA_LIB_ENTRY);

		customFunctionLibEntryEClass = createEClass(CUSTOM_FUNCTION_LIB_ENTRY);

		enterpriseArchiveEntryEClass = createEClass(ENTERPRISE_ARCHIVE_ENTRY);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__NAME);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__AUTHOR);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__DESCRIPTION);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__VERSION);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__PATH);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__INCLUDE_SERVICE_LEVEL_GLOBAL_VARS);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__DEBUG);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__TEMP_OUTPUT_PATH);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__OVERWRITE);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__DELETE_TEMP_FILES);
		createEAttribute(enterpriseArchiveEntryEClass, ENTERPRISE_ARCHIVE_ENTRY__BUILD_CLASSES_ONLY);

		bpmnProcessSettingsEClass = createEClass(BPMN_PROCESS_SETTINGS);
		createEAttribute(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__BUILD_FOLDER);
		createEReference(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__PALETTE_PATH_ENTRIES);
		createEReference(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__SELECTED_PROCESS_PATHS);
		createEAttribute(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__PROCESS_PREFIX);
		createEAttribute(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__RULE_PREFIX);
		createEAttribute(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__RULE_FUNCTION_PREFIX);
		createEAttribute(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__CONCEPT_PREFIX);
		createEAttribute(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__EVENT_PREFIX);
		createEAttribute(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__TIME_EVENT_PREFIX);
		createEAttribute(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__SCORECARD_PREFIX);
		createEReference(bpmnProcessSettingsEClass, BPMN_PROCESS_SETTINGS__NAME_PREFIXES);

		bpmnPalettePathEntryEClass = createEClass(BPMN_PALETTE_PATH_ENTRY);

		bpmnProcessPathEntryEClass = createEClass(BPMN_PROCESS_PATH_ENTRY);

		namePrefixEClass = createEClass(NAME_PREFIX);
		createEAttribute(namePrefixEClass, NAME_PREFIX__NAME);
		createEAttribute(namePrefixEClass, NAME_PREFIX__PREFIX);

		nativeLibraryPathEClass = createEClass(NATIVE_LIBRARY_PATH);

		javaClasspathEntryEClass = createEClass(JAVA_CLASSPATH_ENTRY);
		createEAttribute(javaClasspathEntryEClass, JAVA_CLASSPATH_ENTRY__CLASSPATH_ENTRY_TYPE);
		createEAttribute(javaClasspathEntryEClass, JAVA_CLASSPATH_ENTRY__SOURCE_FOLDER);
		createEAttribute(javaClasspathEntryEClass, JAVA_CLASSPATH_ENTRY__INCLUSION_PATTERN);
		createEAttribute(javaClasspathEntryEClass, JAVA_CLASSPATH_ENTRY__EXCLUSION_PATTERN);
		createEAttribute(javaClasspathEntryEClass, JAVA_CLASSPATH_ENTRY__OUTPUT_LOCATION);
		createEAttribute(javaClasspathEntryEClass, JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_PATH);
		createEAttribute(javaClasspathEntryEClass, JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_ROOT_PATH);

		javaSourceFolderEntryEClass = createEClass(JAVA_SOURCE_FOLDER_ENTRY);

		// Create enums
		librarY_PATH_TYPEEEnum = createEEnum(LIBRARY_PATH_TYPE);
		xpatH_VERSIONEEnum = createEEnum(XPATH_VERSION);
		javA_CLASSPATH_ENTRY_TYPEEEnum = createEEnum(JAVA_CLASSPATH_ENTRY_TYPE);
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
		buildPathEntryEClass.getESuperTypes().add(this.getProjectConfigurationEntry());
		projectLibraryEntryEClass.getESuperTypes().add(this.getBuildPathEntry());
		thirdPartyLibEntryEClass.getESuperTypes().add(this.getJavaLibEntry());
		javaLibEntryEClass.getESuperTypes().add(this.getBuildPathEntry());
		coreJavaLibEntryEClass.getESuperTypes().add(this.getJavaLibEntry());
		customFunctionLibEntryEClass.getESuperTypes().add(this.getJavaLibEntry());
		enterpriseArchiveEntryEClass.getESuperTypes().add(this.getProjectConfigurationEntry());
		bpmnPalettePathEntryEClass.getESuperTypes().add(this.getBuildPathEntry());
		bpmnProcessPathEntryEClass.getESuperTypes().add(this.getBuildPathEntry());
		nativeLibraryPathEClass.getESuperTypes().add(this.getBuildPathEntry());
		javaClasspathEntryEClass.getESuperTypes().add(this.getBuildPathEntry());
		javaSourceFolderEntryEClass.getESuperTypes().add(this.getBuildPathEntry());

		// Initialize classes and features; add operations and parameters
		initEClass(studioProjectConfigurationEClass, StudioProjectConfiguration.class, "StudioProjectConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStudioProjectConfiguration_ThirdpartyLibEntries(), this.getThirdPartyLibEntry(), null, "thirdpartyLibEntries", null, 0, -1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getStudioProjectConfiguration_ThirdpartyLibEntries().getEKeys().add(this.getBuildPathEntry_Path());
		initEReference(getStudioProjectConfiguration_CoreInternalLibEntries(), this.getCoreJavaLibEntry(), null, "coreInternalLibEntries", null, 0, -1, StudioProjectConfiguration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getStudioProjectConfiguration_CoreInternalLibEntries().getEKeys().add(this.getBuildPathEntry_Path());
		initEReference(getStudioProjectConfiguration_CustomFunctionLibEntries(), this.getCustomFunctionLibEntry(), null, "customFunctionLibEntries", null, 0, -1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getStudioProjectConfiguration_CustomFunctionLibEntries().getEKeys().add(this.getBuildPathEntry_Path());
		initEReference(getStudioProjectConfiguration_ProjectLibEntries(), this.getProjectLibraryEntry(), null, "projectLibEntries", null, 0, -1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getStudioProjectConfiguration_ProjectLibEntries().getEKeys().add(this.getBuildPathEntry_Path());
		initEReference(getStudioProjectConfiguration_JavaClasspathEntries(), this.getJavaClasspathEntry(), null, "javaClasspathEntries", null, 0, -1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStudioProjectConfiguration_JavaSourceFolderEntries(), this.getJavaSourceFolderEntry(), null, "javaSourceFolderEntries", null, 0, -1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStudioProjectConfiguration_EnterpriseArchiveConfiguration(), this.getEnterpriseArchiveEntry(), null, "enterpriseArchiveConfiguration", null, 0, 1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStudioProjectConfiguration_BpmnProcessSettings(), this.getBpmnProcessSettings(), null, "bpmnProcessSettings", null, 0, 1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStudioProjectConfiguration_Name(), ecorePackage.getEString(), "name", null, 0, 1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStudioProjectConfiguration_Version(), ecorePackage.getEString(), "version", null, 0, 1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStudioProjectConfiguration_Build(), ecorePackage.getEString(), "build", null, 0, 1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStudioProjectConfiguration_FileTimeStamp(), ecorePackage.getELong(), "fileTimeStamp", null, 0, 1, StudioProjectConfiguration.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStudioProjectConfiguration_XpathVersion(), this.getXPATH_VERSION(), "xpathVersion", "1.0", 0, 1, StudioProjectConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(studioProjectConfigurationEClass, null, "getEntriesByType", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLIBRARY_PATH_TYPE(), "type", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEEList());
		EGenericType g2 = createEGenericType(this.getBuildPathEntry());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = addEOperation(studioProjectConfigurationEClass, ecorePackage.getEBoolean(), "removeEntriesByType", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getBuildPathEntry(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLIBRARY_PATH_TYPE(), "type", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(projectConfigurationEntryEClass, ProjectConfigurationEntry.class, "ProjectConfigurationEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(buildPathEntryEClass, BuildPathEntry.class, "BuildPathEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBuildPathEntry_EntryType(), this.getLIBRARY_PATH_TYPE(), "entryType", null, 0, 1, BuildPathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuildPathEntry_Path(), ecorePackage.getEString(), "path", null, 0, 1, BuildPathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuildPathEntry_Timestamp(), ecorePackage.getELong(), "timestamp", null, 0, 1, BuildPathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuildPathEntry_Var(), ecorePackage.getEBoolean(), "var", "false", 0, 1, BuildPathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuildPathEntry_ReadOnly(), ecorePackage.getEBoolean(), "readOnly", "false", 0, 1, BuildPathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuildPathEntry_Deprecated(), ecorePackage.getEBoolean(), "deprecated", "false", 0, 1, BuildPathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuildPathEntry_ResolvedPath(), ecorePackage.getEString(), "resolvedPath", null, 0, 1, BuildPathEntry.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(buildPathEntryEClass, ecorePackage.getEString(), "getPath", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "resolve", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(projectLibraryEntryEClass, ProjectLibraryEntry.class, "ProjectLibraryEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(thirdPartyLibEntryEClass, ThirdPartyLibEntry.class, "ThirdPartyLibEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(javaLibEntryEClass, JavaLibEntry.class, "JavaLibEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJavaLibEntry_NativeLibraryLocation(), this.getNativeLibraryPath(), null, "nativeLibraryLocation", null, 0, 1, JavaLibEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(coreJavaLibEntryEClass, CoreJavaLibEntry.class, "CoreJavaLibEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(customFunctionLibEntryEClass, CustomFunctionLibEntry.class, "CustomFunctionLibEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(enterpriseArchiveEntryEClass, EnterpriseArchiveEntry.class, "EnterpriseArchiveEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnterpriseArchiveEntry_Name(), ecorePackage.getEString(), "name", "", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_Author(), ecorePackage.getEString(), "author", "", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_Description(), ecorePackage.getEString(), "description", "", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_Version(), ecorePackage.getEInt(), "version", "0", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_Path(), ecorePackage.getEString(), "path", "", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_IncludeServiceLevelGlobalVars(), ecorePackage.getEBoolean(), "includeServiceLevelGlobalVars", "true", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_Debug(), ecorePackage.getEBoolean(), "debug", "true", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_TempOutputPath(), ecorePackage.getEString(), "tempOutputPath", "", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_Overwrite(), ecorePackage.getEBoolean(), "overwrite", null, 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_DeleteTempFiles(), ecorePackage.getEBoolean(), "deleteTempFiles", "true", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchiveEntry_BuildClassesOnly(), ecorePackage.getEBoolean(), "buildClassesOnly", "true", 0, 1, EnterpriseArchiveEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bpmnProcessSettingsEClass, BpmnProcessSettings.class, "BpmnProcessSettings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBpmnProcessSettings_BuildFolder(), ecorePackage.getEString(), "buildFolder", null, 0, 1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBpmnProcessSettings_PalettePathEntries(), this.getBpmnPalettePathEntry(), null, "palettePathEntries", null, 0, -1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBpmnProcessSettings_SelectedProcessPaths(), this.getBpmnProcessPathEntry(), null, "selectedProcessPaths", null, 0, -1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBpmnProcessSettings_ProcessPrefix(), ecorePackage.getEString(), "processPrefix", null, 0, 1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBpmnProcessSettings_RulePrefix(), ecorePackage.getEString(), "rulePrefix", null, 0, 1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBpmnProcessSettings_RuleFunctionPrefix(), ecorePackage.getEString(), "ruleFunctionPrefix", null, 0, 1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBpmnProcessSettings_ConceptPrefix(), ecorePackage.getEString(), "conceptPrefix", null, 0, 1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBpmnProcessSettings_EventPrefix(), ecorePackage.getEString(), "eventPrefix", null, 0, 1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBpmnProcessSettings_TimeEventPrefix(), ecorePackage.getEString(), "timeEventPrefix", null, 0, 1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBpmnProcessSettings_ScorecardPrefix(), ecorePackage.getEString(), "scorecardPrefix", null, 0, 1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBpmnProcessSettings_NamePrefixes(), this.getNamePrefix(), null, "namePrefixes", null, 0, -1, BpmnProcessSettings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getBpmnProcessSettings_NamePrefixes().getEKeys().add(this.getNamePrefix_Name());

		op = addEOperation(bpmnProcessSettingsEClass, null, "getNamePrefixMap", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getNamePrefix());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(bpmnPalettePathEntryEClass, BpmnPalettePathEntry.class, "BpmnPalettePathEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(bpmnProcessPathEntryEClass, BpmnProcessPathEntry.class, "BpmnProcessPathEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(namePrefixEClass, NamePrefix.class, "NamePrefix", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamePrefix_Name(), ecorePackage.getEString(), "name", null, 1, 1, NamePrefix.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamePrefix_Prefix(), ecorePackage.getEString(), "prefix", null, 1, 1, NamePrefix.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nativeLibraryPathEClass, NativeLibraryPath.class, "NativeLibraryPath", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(javaClasspathEntryEClass, JavaClasspathEntry.class, "JavaClasspathEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaClasspathEntry_ClasspathEntryType(), this.getJAVA_CLASSPATH_ENTRY_TYPE(), "classpathEntryType", null, 0, 1, JavaClasspathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaClasspathEntry_SourceFolder(), ecorePackage.getEString(), "sourceFolder", null, 0, 1, JavaClasspathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaClasspathEntry_InclusionPattern(), ecorePackage.getEString(), "inclusionPattern", null, 0, -1, JavaClasspathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaClasspathEntry_ExclusionPattern(), ecorePackage.getEString(), "exclusionPattern", null, 0, -1, JavaClasspathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaClasspathEntry_OutputLocation(), ecorePackage.getEString(), "outputLocation", null, 0, 1, JavaClasspathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaClasspathEntry_SourceAttachmentPath(), ecorePackage.getEString(), "sourceAttachmentPath", null, 0, 1, JavaClasspathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaClasspathEntry_SourceAttachmentRootPath(), ecorePackage.getEString(), "sourceAttachmentRootPath", null, 0, 1, JavaClasspathEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaSourceFolderEntryEClass, JavaSourceFolderEntry.class, "JavaSourceFolderEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.class, "LIBRARY_PATH_TYPE");
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.PROJECT_LIBRARY);
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.CUSTOM_FUNCTION_LIBRARY);
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.THIRD_PARTY_LIBRARY);
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.CORE_INTERNAL_LIBRARY);
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.BPMN_PALETTE_LIBRARY);
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.BPMN_PROCESS_PATH_LIBRARY);
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.JAVA_NATIVE_LIBRARY);
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.JAVA_CLASSPATH_ENTRY);
		addEEnumLiteral(librarY_PATH_TYPEEEnum, com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE.JAVA_SOURCE_FOLDER);

		initEEnum(xpatH_VERSIONEEnum, com.tibco.cep.studio.common.configuration.XPATH_VERSION.class, "XPATH_VERSION");
		addEEnumLiteral(xpatH_VERSIONEEnum, com.tibco.cep.studio.common.configuration.XPATH_VERSION.XPATH_10);
		addEEnumLiteral(xpatH_VERSIONEEnum, com.tibco.cep.studio.common.configuration.XPATH_VERSION.XPATH_20);

		initEEnum(javA_CLASSPATH_ENTRY_TYPEEEnum, com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE.class, "JAVA_CLASSPATH_ENTRY_TYPE");
		addEEnumLiteral(javA_CLASSPATH_ENTRY_TYPEEEnum, com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE.CPE_SOURCE);
		addEEnumLiteral(javA_CLASSPATH_ENTRY_TYPEEEnum, com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE.CPE_LIBRARY);
		addEEnumLiteral(javA_CLASSPATH_ENTRY_TYPEEEnum, com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE.CPE_VARIABLE);
		addEEnumLiteral(javA_CLASSPATH_ENTRY_TYPEEEnum, com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE.CPE_PROJECT);
		addEEnumLiteral(javA_CLASSPATH_ENTRY_TYPEEEnum, com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE.CPE_CONTAINER);

		// Create resource
		createResource(eNS_URI);
	}

} //ConfigurationPackageImpl
