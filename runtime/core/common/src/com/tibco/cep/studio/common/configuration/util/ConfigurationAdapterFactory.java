/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.util;

import com.tibco.cep.studio.common.configuration.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage
 * @generated
 */
public class ConfigurationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ConfigurationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ConfigurationPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationSwitch<Adapter> modelSwitch =
		new ConfigurationSwitch<Adapter>() {
			@Override
			public Adapter caseStudioProjectConfiguration(StudioProjectConfiguration object) {
				return createStudioProjectConfigurationAdapter();
			}
			@Override
			public Adapter caseProjectConfigurationEntry(ProjectConfigurationEntry object) {
				return createProjectConfigurationEntryAdapter();
			}
			@Override
			public Adapter caseBuildPathEntry(BuildPathEntry object) {
				return createBuildPathEntryAdapter();
			}
			@Override
			public Adapter caseProjectLibraryEntry(ProjectLibraryEntry object) {
				return createProjectLibraryEntryAdapter();
			}
			@Override
			public Adapter caseThirdPartyLibEntry(ThirdPartyLibEntry object) {
				return createThirdPartyLibEntryAdapter();
			}
			@Override
			public Adapter caseJavaLibEntry(JavaLibEntry object) {
				return createJavaLibEntryAdapter();
			}
			@Override
			public Adapter caseCoreJavaLibEntry(CoreJavaLibEntry object) {
				return createCoreJavaLibEntryAdapter();
			}
			@Override
			public Adapter caseCustomFunctionLibEntry(CustomFunctionLibEntry object) {
				return createCustomFunctionLibEntryAdapter();
			}
			@Override
			public Adapter caseEnterpriseArchiveEntry(EnterpriseArchiveEntry object) {
				return createEnterpriseArchiveEntryAdapter();
			}
			@Override
			public Adapter caseBpmnProcessSettings(BpmnProcessSettings object) {
				return createBpmnProcessSettingsAdapter();
			}
			@Override
			public Adapter caseBpmnPalettePathEntry(BpmnPalettePathEntry object) {
				return createBpmnPalettePathEntryAdapter();
			}
			@Override
			public Adapter caseBpmnProcessPathEntry(BpmnProcessPathEntry object) {
				return createBpmnProcessPathEntryAdapter();
			}
			@Override
			public Adapter caseNamePrefix(NamePrefix object) {
				return createNamePrefixAdapter();
			}
			@Override
			public Adapter caseNativeLibraryPath(NativeLibraryPath object) {
				return createNativeLibraryPathAdapter();
			}
			@Override
			public Adapter caseJavaClasspathEntry(JavaClasspathEntry object) {
				return createJavaClasspathEntryAdapter();
			}
			@Override
			public Adapter caseJavaSourceFolderEntry(JavaSourceFolderEntry object) {
				return createJavaSourceFolderEntryAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.StudioProjectConfiguration <em>Studio Project Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.StudioProjectConfiguration
	 * @generated
	 */
	public Adapter createStudioProjectConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.ProjectConfigurationEntry <em>Project Configuration Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.ProjectConfigurationEntry
	 * @generated
	 */
	public Adapter createProjectConfigurationEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.BuildPathEntry <em>Build Path Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.BuildPathEntry
	 * @generated
	 */
	public Adapter createBuildPathEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.ProjectLibraryEntry <em>Project Library Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.ProjectLibraryEntry
	 * @generated
	 */
	public Adapter createProjectLibraryEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry <em>Third Party Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry
	 * @generated
	 */
	public Adapter createThirdPartyLibEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.JavaLibEntry <em>Java Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.JavaLibEntry
	 * @generated
	 */
	public Adapter createJavaLibEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.CoreJavaLibEntry <em>Core Java Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.CoreJavaLibEntry
	 * @generated
	 */
	public Adapter createCoreJavaLibEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry <em>Custom Function Lib Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry
	 * @generated
	 */
	public Adapter createCustomFunctionLibEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry <em>Enterprise Archive Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.EnterpriseArchiveEntry
	 * @generated
	 */
	public Adapter createEnterpriseArchiveEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.BpmnProcessSettings <em>Bpmn Process Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessSettings
	 * @generated
	 */
	public Adapter createBpmnProcessSettingsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry <em>Bpmn Palette Path Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.BpmnPalettePathEntry
	 * @generated
	 */
	public Adapter createBpmnPalettePathEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry <em>Bpmn Process Path Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.BpmnProcessPathEntry
	 * @generated
	 */
	public Adapter createBpmnProcessPathEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.NamePrefix <em>Name Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.NamePrefix
	 * @generated
	 */
	public Adapter createNamePrefixAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.NativeLibraryPath <em>Native Library Path</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.NativeLibraryPath
	 * @generated
	 */
	public Adapter createNativeLibraryPathAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.JavaClasspathEntry <em>Java Classpath Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.JavaClasspathEntry
	 * @generated
	 */
	public Adapter createJavaClasspathEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry <em>Java Source Folder Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry
	 * @generated
	 */
	public Adapter createJavaSourceFolderEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ConfigurationAdapterFactory
