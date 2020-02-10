/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddFactory;
import com.tibco.be.util.config.cdd.CddPackage;

import java.io.IOException;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CddPackageImpl extends EPackageImpl implements CddPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected String packageFilename = "cdd.ecore";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentClassConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentClassesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertConfigConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alertConfigSetConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass backingStoreConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass backingStoreForDomainObjectConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass backingStoreForPropertiesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass backingStoreForPropertyConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass businessworksConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cacheAgentClassConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cacheManagerConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cacheManagerSecurityConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cddRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass childClusterMemberConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ciphersConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clusterConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clusterMemberConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dashboardAgentClassConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dbConceptsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass destinationConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass destinationGroupsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass destinationsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainObjectConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainObjectsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass evictionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass filesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionGroupsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass getPropertyConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass httpConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass indexConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass indexesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inferenceAgentClassConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inferenceEngineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jobManagerConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lineLayoutConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loadBalancerAdhocConfigConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loadBalancerAdhocConfigsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loadBalancerConfigsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loadBalancerPairConfigConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loadBalancerPairConfigsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loadConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localCacheConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass logConfigConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass logConfigsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass memoryManagerConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmActionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmActionConfigConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmActionConfigSetConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmAgentClassConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmAlertConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmExecuteCommandConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmHealthLevelConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmSendEmailConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mmTriggerConditionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass notificationConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectManagementConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectManagerConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectTableConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass overrideConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processAgentClassConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processEngineConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processGroupsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processingUnitConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processingUnitsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processingUnitSecurityConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyGroupConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providerConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queryAgentClassConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass revisionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleConfigConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rulesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rulesetsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass securityConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass securityControllerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass securityOverrideConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass securityRequesterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass setPropertyConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedQueueConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sslConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass systemPropertyConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass terminalConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass urisAndRefsConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass urisConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldEncryptionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entitySetConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ldmConnectionConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass publisherConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass liveViewAgentClassConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeIndexPropertiesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeIndexConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeIndexesConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum domainObjectModeConfigEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum threadingModelConfigEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType domainObjectModeConfigObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType ontologyUriConfigEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType threadingModelConfigObjectEDataType = null;

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CddPackageImpl() {
		super(eNS_URI, CddFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CddPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @generated
	 */
	public static CddPackage init() {
		if (isInited) return (CddPackage)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI);

		// Obtain or create and register package
		CddPackageImpl theCddPackage = (CddPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CddPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CddPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Load packages
		theCddPackage.loadPackage();

		// Fix loaded packages
		theCddPackage.fixPackageContents();

		// Mark meta-data to indicate it can't be changed
		theCddPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CddPackage.eNS_URI, theCddPackage);
		return theCddPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgentClassConfig() {
		if (agentClassConfigEClass == null) {
			agentClassConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(0);
		}
		return agentClassConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgentClassesConfig() {
		if (agentClassesConfigEClass == null) {
			agentClassesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(1);
		}
		return agentClassesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgentClassesConfig_Group() {
        return (EAttribute)getAgentClassesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentClassesConfig_CacheAgentConfig() {
        return (EReference)getAgentClassesConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentClassesConfig_DashboardAgentConfig() {
        return (EReference)getAgentClassesConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentClassesConfig_InferenceAgentConfig() {
        return (EReference)getAgentClassesConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentClassesConfig_QueryAgentConfig() {
        return (EReference)getAgentClassesConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentClassesConfig_MmAgentConfig() {
        return (EReference)getAgentClassesConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentClassesConfig_ProcessAgentConfig() {
        return (EReference)getAgentClassesConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentClassesConfig_LiveViewAgentConfig() {
        return (EReference)getAgentClassesConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgentConfig() {
		if (agentConfigEClass == null) {
			agentConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(2);
		}
		return agentConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgentConfig_Mixed() {
        return (EAttribute)getAgentConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentConfig_Ref() {
        return (EReference)getAgentConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentConfig_Key() {
        return (EReference)getAgentConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentConfig_Priority() {
        return (EReference)getAgentConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgentsConfig() {
		if (agentsConfigEClass == null) {
			agentsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(3);
		}
		return agentsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgentsConfig_Agent() {
        return (EReference)getAgentsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlertConfigConfig() {
		if (alertConfigConfigEClass == null) {
			alertConfigConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(4);
		}
		return alertConfigConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlertConfigConfig_Condition() {
        return (EReference)getAlertConfigConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlertConfigConfig_Projection() {
        return (EReference)getAlertConfigConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlertConfigConfig_AlertName() {
        return (EAttribute)getAlertConfigConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlertConfigSetConfig() {
		if (alertConfigSetConfigEClass == null) {
			alertConfigSetConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(5);
		}
		return alertConfigSetConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlertConfigSetConfig_AlertConfig() {
        return (EReference)getAlertConfigSetConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifactConfig() {
		if (artifactConfigEClass == null) {
			artifactConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(6);
		}
		return artifactConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactConfig_Id() {
        return (EAttribute)getArtifactConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBackingStoreConfig() {
		if (backingStoreConfigEClass == null) {
			backingStoreConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(7);
		}
		return backingStoreConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_CacheAside() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_CacheLoaderClass() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_DataStorePath() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_EnforcePools() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_PersistenceOption() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_PersistencePolicy() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_ParallelRecovery() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_PrimaryConnection() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_SecondaryConnection() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_Strategy() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreConfig_Type() {
        return (EReference)getBackingStoreConfig().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBackingStoreForDomainObjectConfig() {
		if (backingStoreForDomainObjectConfigEClass == null) {
			backingStoreForDomainObjectConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(8);
		}
		return backingStoreForDomainObjectConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreForDomainObjectConfig_Properties() {
        return (EReference)getBackingStoreForDomainObjectConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreForDomainObjectConfig_Enabled() {
        return (EReference)getBackingStoreForDomainObjectConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreForDomainObjectConfig_TableName() {
        return (EReference)getBackingStoreForDomainObjectConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBackingStoreForPropertiesConfig() {
		if (backingStoreForPropertiesConfigEClass == null) {
			backingStoreForPropertiesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(9);
		}
		return backingStoreForPropertiesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBackingStoreForPropertiesConfig_Group() {
        return (EAttribute)getBackingStoreForPropertiesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreForPropertiesConfig_Property() {
        return (EReference)getBackingStoreForPropertiesConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBackingStoreForPropertyConfig() {
		if (backingStoreForPropertyConfigEClass == null) {
			backingStoreForPropertyConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(10);
		}
		return backingStoreForPropertyConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreForPropertyConfig_ReverseReferences() {
        return (EReference)getBackingStoreForPropertyConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreForPropertyConfig_MaxSize() {
        return (EReference)getBackingStoreForPropertyConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBackingStoreForPropertyConfig_Name() {
        return (EReference)getBackingStoreForPropertyConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBusinessworksConfig() {
		if (businessworksConfigEClass == null) {
			businessworksConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(11);
		}
		return businessworksConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessworksConfig_Uri() {
        return (EAttribute)getBusinessworksConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCacheAgentClassConfig() {
		if (cacheAgentClassConfigEClass == null) {
			cacheAgentClassConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(12);
		}
		return cacheAgentClassConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheAgentClassConfig_PropertyGroup() {
        return (EReference)getCacheAgentClassConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCacheManagerConfig() {
		if (cacheManagerConfigEClass == null) {
			cacheManagerConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(13);
		}
		return cacheManagerConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_CacheAgentQuorum() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_BackingStore() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_DomainObjects() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_Provider() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_BackupCopies() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_EntityCacheSize() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_ObjectTable() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_DiscoveryURL() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_ListenURL() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_RemoteListenURL() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_ProtocolTimeout() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_ReadTimeout() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_WriteTimeout() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_LockTimeout() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_ShoutdownWait() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_WorkerthreadsCount() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_ExplicitTuple() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerConfig_SecurityConfig() {
        return (EReference)getCacheManagerConfig().getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCacheManagerSecurityConfig() {
		if (cacheManagerSecurityConfigEClass == null) {
			cacheManagerSecurityConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(14);
		}
		return cacheManagerSecurityConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerSecurityConfig_Enabled() {
        return (EReference)getCacheManagerSecurityConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerSecurityConfig_Controller() {
        return (EReference)getCacheManagerSecurityConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCacheManagerSecurityConfig_Requester() {
        return (EReference)getCacheManagerSecurityConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCddRoot() {
		if (cddRootEClass == null) {
			cddRootEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(15);
		}
		return cddRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_Mixed() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_XMLNSPrefixMap() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_XSISchemaLocation() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_AcceptCount() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Address() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_AdhocConfig() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_AdhocConfigs() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Agent() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_AgentClasses() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Agents() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_AlertConfig() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_AlertConfigSet() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Append() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Arg() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Author() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_BackingStore() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_BackupCopies() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Businessworks() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Cache() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CacheAgentClass() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CacheAgentQuorum() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CacheAside() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CacheLimited() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CacheLoaderClass() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CacheManager() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_CacheMode() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CacheStorageEnabled() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CertificateKeyFile() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Channel() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CheckForDuplicates() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CheckForVersion() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CheckInterval() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CheckpointInterval() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CheckpointOpsLimit() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(33);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ChildClusterMember() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(34);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Cipher() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(35);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Ciphers() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(36);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Class() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(37);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Cluster() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(38);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ClusterMember() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(39);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Comment() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(40);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ConcurrentRtc() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(41);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Condition() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(42);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ConnectionLinger() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(43);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ConnectionTimeout() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(44);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Constant() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(45);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Controller() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(46);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Daemon() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(47);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DashboardAgentClass() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(48);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DataStorePath() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(49);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Date() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(50);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DbConcepts() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(51);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DbDir() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(52);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DbUris() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(53);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_DefaultMode() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(54);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DeleteRetracted() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(55);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Destination() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(56);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DestinationGroups() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(57);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Destinations() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(58);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Dir() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(59);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DiscoveryUrl() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(60);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DocumentPage() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(61);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DocumentRoot() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(62);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DomainName() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(63);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DomainObject() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(64);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_DomainObjects() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(65);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Enabled() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(66);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_EnableTracking() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(67);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Encoding() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(68);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Encryption() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(69);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_EnforcePools() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(70);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_EntityCacheSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(71);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Eviction() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(72);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_EvictOnUpdate() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(73);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ExplicitTuple() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(74);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Files() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(75);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_FunctionGroups() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(76);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Functions() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(77);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_GetProperty() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(78);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_HotDeploy() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(79);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Http() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(80);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_IdentityPassword() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(81);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_InactivityTimeout() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(82);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Index() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(83);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Indexes() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(84);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_InferenceAgentClass() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(85);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_InferenceEngine() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(86);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_InitialSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(87);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_JobManager() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(88);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_JobPoolQueueSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(89);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_JobPoolThreadCount() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(90);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Key() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(91);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_KeyManagerAlgorithm() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(92);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LineLayout() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(93);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ListenUrl() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(94);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Load() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(95);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LoadBalancerConfigs() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(96);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LocalCache() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(97);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LockTimeout() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(98);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LogConfig() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(99);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LogConfigs() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(100);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MaxActive() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(101);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MaxNumber() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(102);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MaxProcessors() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(103);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MaxSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(104);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MaxTime() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(105);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MemoryManager() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(106);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_MessageEncoding() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(107);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MinSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(108);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmAction() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(109);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmActionConfig() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(110);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmActionConfigSet() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(111);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmAgentClass() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(112);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmAlert() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(113);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmExecuteCommand() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(114);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmHealthLevel() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(115);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmSendEmail() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(116);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_MmTriggerCondition() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(117);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_Mode() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(118);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Name() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(119);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Notification() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(120);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ObjectManagement() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(121);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ObjectTable() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(122);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PairConfig() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(123);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PairConfigs() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(124);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ParallelRecovery() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(125);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PersistenceOption() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(126);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PersistencePolicy() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(127);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PolicyFile() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(128);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Port() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(129);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PreLoadCaches() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(130);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PreLoadEnabled() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(131);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PreLoadFetchSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(132);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PreLoadHandles() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(133);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_PreProcessor() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(134);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PrimaryConnection() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(135);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Priority() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(136);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Process() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(137);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ProcessAgentClass() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(138);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ProcessEngine() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(139);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Processes() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(140);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ProcessGroups() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(141);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ProcessingUnit() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(142);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ProcessingUnits() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(143);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Projection() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(144);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Property() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(145);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PropertyCacheSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(146);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PropertyCheckInterval() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(147);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PropertyGroup() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(148);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Protocol() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(149);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Protocols() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(150);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ProtocolTimeout() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(151);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Provider() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(152);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_QueryAgentClass() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(153);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_QueueSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(154);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ReadTimeout() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(155);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Receiver() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(156);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_RemoteListenUrl() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(157);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Requester() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(158);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_RetryCount() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(159);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ReverseReferences() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(160);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Revision() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(161);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Role() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(162);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Roles() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(163);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Router() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(164);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Rule() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(165);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_RuleConfig() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(166);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Rules() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(167);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Rulesets() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(168);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SecondaryConnection() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(169);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SecurityConfig() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(170);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Service() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(171);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SetProperty() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(172);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SharedAll() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(173);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SharedQueue() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(174);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ShoutdownWait() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(175);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Shutdown() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(176);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Size() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(177);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SkipRecovery() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(178);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SocketOutputBufferSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(179);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Ssl() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(180);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_StaleConnectionCheck() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(181);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Startup() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(182);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Strategy() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(183);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Subject() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(184);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Subscribe() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(185);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SysErrRedirect() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(186);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_SysOutRedirect() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(187);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_TableName() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(188);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_TcpNoDelay() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(189);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Terminal() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(190);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_ThreadAffinityRuleFunction() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(191);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_ThreadCount() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(192);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_ThreadingModel() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(193);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_TokenFile() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(194);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_TrustManagerAlgorithm() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(195);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Ttl() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(196);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Type() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(197);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_Uri() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(198);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_UserName() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(199);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_UserPassword() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(200);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCddRoot_Version() {
        return (EAttribute)getCddRoot().getEStructuralFeatures().get(201);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_WaitTimeout() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(202);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Workers() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(203);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_WorkerthreadsCount() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(204);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_WriteTimeout() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(205);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Entity() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(206);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Filter() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(207);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_EntitySet() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(208);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_EnableTableTrimming() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(209);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_TrimmingField() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(210);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_TrimmingRule() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(211);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_GenerateLVFiles() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(212);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_OutputPath() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(213);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LdmConnection() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(214);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LdmUrl() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(215);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_Publisher() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(216);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PublisherQueueSize() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(217);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_PublisherThreadCount() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(218);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_LiveviewAgentClass() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(219);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CompositeIndexes() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(220);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCddRoot_CompositeIndex() {
        return (EReference)getCddRoot().getEStructuralFeatures().get(221);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChildClusterMemberConfig() {
		if (childClusterMemberConfigEClass == null) {
			childClusterMemberConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(16);
		}
		return childClusterMemberConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChildClusterMemberConfig_Property() {
        return (EReference)getChildClusterMemberConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChildClusterMemberConfig_Path() {
        return (EAttribute)getChildClusterMemberConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChildClusterMemberConfig_Tolerance() {
        return (EAttribute)getChildClusterMemberConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCiphersConfig() {
		if (ciphersConfigEClass == null) {
			ciphersConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(17);
		}
		return ciphersConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCiphersConfig_Cipher() {
        return (EReference)getCiphersConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClusterConfig() {
		if (clusterConfigEClass == null) {
			clusterConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(18);
		}
		return clusterConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_AgentClasses() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_DestinationGroups() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_FunctionGroups() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_LoadBalancerConfigs() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_LogConfigs() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClusterConfig_MessageEncoding() {
        return (EAttribute)getClusterConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_Name() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_ObjectManagement() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_ProcessGroups() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_ProcessingUnits() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_PropertyGroup() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_Revision() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterConfig_Rulesets() {
        return (EReference)getClusterConfig().getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClusterMemberConfig() {
		if (clusterMemberConfigEClass == null) {
			clusterMemberConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(19);
		}
		return clusterMemberConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClusterMemberConfig_SetProperty() {
        return (EReference)getClusterMemberConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClusterMemberConfig_MemberName() {
        return (EAttribute)getClusterMemberConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClusterMemberConfig_Path() {
        return (EAttribute)getClusterMemberConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditionConfig() {
		if (conditionConfigEClass == null) {
			conditionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(20);
		}
		return conditionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionConfig_GetProperty() {
        return (EReference)getConditionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectionConfig() {
		if (connectionConfigEClass == null) {
			connectionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(21);
		}
		return connectionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionConfig_InitialSize() {
        return (EReference)getConnectionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionConfig_MinSize() {
        return (EReference)getConnectionConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionConfig_MaxSize() {
        return (EReference)getConnectionConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionConfig_Uri() {
        return (EAttribute)getConnectionConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDashboardAgentClassConfig() {
		if (dashboardAgentClassConfigEClass == null) {
			dashboardAgentClassConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(22);
		}
		return dashboardAgentClassConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDashboardAgentClassConfig_Rules() {
        return (EReference)getDashboardAgentClassConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDashboardAgentClassConfig_Destinations() {
        return (EReference)getDashboardAgentClassConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDashboardAgentClassConfig_Startup() {
        return (EReference)getDashboardAgentClassConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDashboardAgentClassConfig_Shutdown() {
        return (EReference)getDashboardAgentClassConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDashboardAgentClassConfig_PropertyGroup() {
        return (EReference)getDashboardAgentClassConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDbConceptsConfig() {
		if (dbConceptsConfigEClass == null) {
			dbConceptsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(23);
		}
		return dbConceptsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_CheckInterval() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_DbUris() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_InactivityTimeout() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_InitialSize() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_MaxSize() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_MinSize() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_PropertyCheckInterval() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_RetryCount() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDbConceptsConfig_WaitTimeout() {
        return (EReference)getDbConceptsConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDestinationConfig() {
		if (destinationConfigEClass == null) {
			destinationConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(24);
		}
		return destinationConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestinationConfig_Uri() {
        return (EAttribute)getDestinationConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestinationConfig_PreProcessor() {
        return (EAttribute)getDestinationConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestinationConfig_ThreadingModel() {
        return (EAttribute)getDestinationConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDestinationConfig_ThreadCount() {
        return (EReference)getDestinationConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDestinationConfig_QueueSize() {
        return (EReference)getDestinationConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestinationConfig_ThreadAffinityRuleFunction() {
        return (EAttribute)getDestinationConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDestinationGroupsConfig() {
		if (destinationGroupsConfigEClass == null) {
			destinationGroupsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(25);
		}
		return destinationGroupsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDestinationGroupsConfig_Destinations() {
        return (EReference)getDestinationGroupsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDestinationsConfig() {
		if (destinationsConfigEClass == null) {
			destinationsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(26);
		}
		return destinationsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestinationsConfig_Group() {
        return (EAttribute)getDestinationsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDestinationsConfig_Ref() {
        return (EReference)getDestinationsConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDestinationsConfig_Destination() {
        return (EReference)getDestinationsConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDomainObjectConfig() {
		if (domainObjectConfigEClass == null) {
			domainObjectConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(27);
		}
		return domainObjectConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_BackingStore() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_CacheLimited() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_CheckForVersion() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_Constant() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_EnableTracking() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_Encryption() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_EvictOnUpdate() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_Indexes() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomainObjectConfig_Mode() {
        return (EAttribute)getDomainObjectConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_PreLoadEnabled() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_PreLoadFetchSize() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_PreLoadHandles() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomainObjectConfig_PreProcessor() {
        return (EAttribute)getDomainObjectConfig().getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_Subscribe() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomainObjectConfig_Uri() {
        return (EAttribute)getDomainObjectConfig().getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_ConceptTTL() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectConfig_CompositeIndexes() {
        return (EReference)getDomainObjectConfig().getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDomainObjectsConfig() {
		if (domainObjectsConfigEClass == null) {
			domainObjectsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(30);
		}
		return domainObjectsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDomainObjectsConfig_DefaultMode() {
        return (EAttribute)getDomainObjectsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_CheckForVersion() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_Constant() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_EnableTracking() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_EvictOnUpdate() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_CacheLimited() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_Subscribe() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_PreLoadEnabled() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_PreLoadFetchSize() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_PreLoadHandles() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_DomainObject() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDomainObjectsConfig_ConceptTTL() {
        return (EReference)getDomainObjectsConfig().getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEvictionConfig() {
		if (evictionConfigEClass == null) {
			evictionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(31);
		}
		return evictionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvictionConfig_MaxSize() {
        return (EReference)getEvictionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvictionConfig_MaxTime() {
        return (EReference)getEvictionConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFilesConfig() {
		if (filesConfigEClass == null) {
			filesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(32);
		}
		return filesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFilesConfig_Enabled() {
        return (EReference)getFilesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFilesConfig_Dir() {
        return (EReference)getFilesConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFilesConfig_MaxNumber() {
        return (EReference)getFilesConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFilesConfig_MaxSize() {
        return (EReference)getFilesConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFilesConfig_Name() {
        return (EReference)getFilesConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFilesConfig_Append() {
        return (EReference)getFilesConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionGroupsConfig() {
		if (functionGroupsConfigEClass == null) {
			functionGroupsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(33);
		}
		return functionGroupsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionGroupsConfig_Functions() {
        return (EReference)getFunctionGroupsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionsConfig() {
		if (functionsConfigEClass == null) {
			functionsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(34);
		}
		return functionsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunctionsConfig_Group() {
        return (EAttribute)getFunctionsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionsConfig_Ref() {
        return (EReference)getFunctionsConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunctionsConfig_Uri() {
        return (EAttribute)getFunctionsConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGetPropertyConfig() {
		if (getPropertyConfigEClass == null) {
			getPropertyConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(35);
		}
		return getPropertyConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGetPropertyConfig_Name() {
        return (EAttribute)getGetPropertyConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGetPropertyConfig_Path() {
        return (EAttribute)getGetPropertyConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGetPropertyConfig_Reference() {
        return (EAttribute)getGetPropertyConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGetPropertyConfig_Threshold() {
        return (EAttribute)getGetPropertyConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHttpConfig() {
		if (httpConfigEClass == null) {
			httpConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(36);
		}
		return httpConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_AcceptCount() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_ConnectionLinger() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_ConnectionTimeout() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_DocumentPage() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_DocumentRoot() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_MaxProcessors() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_SocketOutputBufferSize() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_Ssl() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_StaleConnectionCheck() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpConfig_TcpNoDelay() {
        return (EReference)getHttpConfig().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIndexConfig() {
		if (indexConfigEClass == null) {
			indexConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(37);
		}
		return indexConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIndexConfig_Property() {
        return (EReference)getIndexConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIndexesConfig() {
		if (indexesConfigEClass == null) {
			indexesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(38);
		}
		return indexesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIndexesConfig_Index() {
        return (EReference)getIndexesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInferenceAgentClassConfig() {
		if (inferenceAgentClassConfigEClass == null) {
			inferenceAgentClassConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(39);
		}
		return inferenceAgentClassConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_Businessworks() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_CheckForDuplicates() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_ConcurrentRtc() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_Destinations() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_Load() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_LocalCache() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_PropertyGroup() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_Rules() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_SharedQueue() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_Shutdown() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceAgentClassConfig_Startup() {
        return (EReference)getInferenceAgentClassConfig().getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInferenceEngineConfig() {
		if (inferenceEngineConfigEClass == null) {
			inferenceEngineConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(40);
		}
		return inferenceEngineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceEngineConfig_CheckForDuplicates() {
        return (EReference)getInferenceEngineConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceEngineConfig_ConcurrentRtc() {
        return (EReference)getInferenceEngineConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceEngineConfig_LocalCache() {
        return (EReference)getInferenceEngineConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInferenceEngineConfig_PropertyGroup() {
        return (EReference)getInferenceEngineConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJobManagerConfig() {
		if (jobManagerConfigEClass == null) {
			jobManagerConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(41);
		}
		return jobManagerConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJobManagerConfig_JobPoolQueueSize() {
        return (EReference)getJobManagerConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJobManagerConfig_JobPoolThreadCount() {
        return (EReference)getJobManagerConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLineLayoutConfig() {
		if (lineLayoutConfigEClass == null) {
			lineLayoutConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(42);
		}
		return lineLayoutConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLineLayoutConfig_Enabled() {
        return (EReference)getLineLayoutConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLineLayoutConfig_Class() {
        return (EReference)getLineLayoutConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLineLayoutConfig_Arg() {
        return (EReference)getLineLayoutConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoadBalancerAdhocConfigConfig() {
		if (loadBalancerAdhocConfigConfigEClass == null) {
			loadBalancerAdhocConfigConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(43);
		}
		return loadBalancerAdhocConfigConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerAdhocConfigConfig_Name() {
        return (EReference)getLoadBalancerAdhocConfigConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerAdhocConfigConfig_Channel() {
        return (EReference)getLoadBalancerAdhocConfigConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerAdhocConfigConfig_PropertyGroup() {
        return (EReference)getLoadBalancerAdhocConfigConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoadBalancerAdhocConfigsConfig() {
		if (loadBalancerAdhocConfigsConfigEClass == null) {
			loadBalancerAdhocConfigsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(44);
		}
		return loadBalancerAdhocConfigsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerAdhocConfigsConfig_LoadBalancerAdhocConfigs() {
        return (EReference)getLoadBalancerAdhocConfigsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoadBalancerConfigsConfig() {
		if (loadBalancerConfigsConfigEClass == null) {
			loadBalancerConfigsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(45);
		}
		return loadBalancerConfigsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerConfigsConfig_LoadBalancerPairConfigs() {
        return (EReference)getLoadBalancerConfigsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerConfigsConfig_LoadBalancerAdhocConfigs() {
        return (EReference)getLoadBalancerConfigsConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoadBalancerPairConfigConfig() {
		if (loadBalancerPairConfigConfigEClass == null) {
			loadBalancerPairConfigConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(46);
		}
		return loadBalancerPairConfigConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerPairConfigConfig_Name() {
        return (EReference)getLoadBalancerPairConfigConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerPairConfigConfig_Channel() {
        return (EReference)getLoadBalancerPairConfigConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerPairConfigConfig_Key() {
        return (EReference)getLoadBalancerPairConfigConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerPairConfigConfig_Router() {
        return (EReference)getLoadBalancerPairConfigConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerPairConfigConfig_Receiver() {
        return (EReference)getLoadBalancerPairConfigConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerPairConfigConfig_PropertyGroup() {
        return (EReference)getLoadBalancerPairConfigConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoadBalancerPairConfigsConfig() {
		if (loadBalancerPairConfigsConfigEClass == null) {
			loadBalancerPairConfigsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(47);
		}
		return loadBalancerPairConfigsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadBalancerPairConfigsConfig_LoadBalancerPairConfigs() {
        return (EReference)getLoadBalancerPairConfigsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoadConfig() {
		if (loadConfigEClass == null) {
			loadConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(48);
		}
		return loadConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoadConfig_MaxActive() {
        return (EReference)getLoadConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalCacheConfig() {
		if (localCacheConfigEClass == null) {
			localCacheConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(49);
		}
		return localCacheConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLocalCacheConfig_Eviction() {
        return (EReference)getLocalCacheConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogConfigConfig() {
		if (logConfigConfigEClass == null) {
			logConfigConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(50);
		}
		return logConfigConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogConfigConfig_Enabled() {
        return (EReference)getLogConfigConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogConfigConfig_Roles() {
        return (EReference)getLogConfigConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogConfigConfig_LineLayout() {
        return (EReference)getLogConfigConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogConfigConfig_Terminal() {
        return (EReference)getLogConfigConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogConfigConfig_Files() {
        return (EReference)getLogConfigConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogConfigsConfig() {
		if (logConfigsConfigEClass == null) {
			logConfigsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(51);
		}
		return logConfigsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogConfigsConfig_LogConfig() {
        return (EReference)getLogConfigsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMemoryManagerConfig() {
		if (memoryManagerConfigEClass == null) {
			memoryManagerConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(52);
		}
		return memoryManagerConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmActionConfig() {
		if (mmActionConfigEClass == null) {
			mmActionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(53);
		}
		return mmActionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmActionConfig_MmExecuteCommand() {
        return (EReference)getMmActionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmActionConfig_MmSendEmail() {
        return (EReference)getMmActionConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmActionConfigConfig() {
		if (mmActionConfigConfigEClass == null) {
			mmActionConfigConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(54);
		}
		return mmActionConfigConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmActionConfigConfig_MmTriggerCondition() {
        return (EReference)getMmActionConfigConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmActionConfigConfig_MmAction() {
        return (EReference)getMmActionConfigConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmActionConfigConfig_ActionName() {
        return (EAttribute)getMmActionConfigConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmActionConfigSetConfig() {
		if (mmActionConfigSetConfigEClass == null) {
			mmActionConfigSetConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(55);
		}
		return mmActionConfigSetConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmActionConfigSetConfig_MmActionConfig() {
        return (EReference)getMmActionConfigSetConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmAgentClassConfig() {
		if (mmAgentClassConfigEClass == null) {
			mmAgentClassConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(56);
		}
		return mmAgentClassConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmAgentClassConfig_MmInferenceAgentClass() {
        return (EReference)getMmAgentClassConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmAgentClassConfig_MmQueryAgentClass() {
        return (EReference)getMmAgentClassConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmAgentClassConfig_AlertConfigSet() {
        return (EReference)getMmAgentClassConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmAgentClassConfig_RuleConfig() {
        return (EReference)getMmAgentClassConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmAgentClassConfig_MmActionConfigSet() {
        return (EReference)getMmAgentClassConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmAgentClassConfig_PropertyGroup() {
        return (EReference)getMmAgentClassConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmAlertConfig() {
		if (mmAlertConfigEClass == null) {
			mmAlertConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(57);
		}
		return mmAlertConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmAlertConfig_Path() {
        return (EAttribute)getMmAlertConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmAlertConfig_Severity() {
        return (EAttribute)getMmAlertConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmExecuteCommandConfig() {
		if (mmExecuteCommandConfigEClass == null) {
			mmExecuteCommandConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(58);
		}
		return mmExecuteCommandConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmExecuteCommandConfig_Command() {
        return (EAttribute)getMmExecuteCommandConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmHealthLevelConfig() {
		if (mmHealthLevelConfigEClass == null) {
			mmHealthLevelConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(59);
		}
		return mmHealthLevelConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmHealthLevelConfig_Path() {
        return (EAttribute)getMmHealthLevelConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmHealthLevelConfig_Value() {
        return (EAttribute)getMmHealthLevelConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmSendEmailConfig() {
		if (mmSendEmailConfigEClass == null) {
			mmSendEmailConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(60);
		}
		return mmSendEmailConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmSendEmailConfig_Cc() {
        return (EAttribute)getMmSendEmailConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmSendEmailConfig_Msg() {
        return (EAttribute)getMmSendEmailConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmSendEmailConfig_Subject() {
        return (EAttribute)getMmSendEmailConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMmSendEmailConfig_To() {
        return (EAttribute)getMmSendEmailConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMmTriggerConditionConfig() {
		if (mmTriggerConditionConfigEClass == null) {
			mmTriggerConditionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(61);
		}
		return mmTriggerConditionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmTriggerConditionConfig_MmHealthLevel() {
        return (EReference)getMmTriggerConditionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMmTriggerConditionConfig_MmAlert() {
        return (EReference)getMmTriggerConditionConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNotificationConfig() {
		if (notificationConfigEClass == null) {
			notificationConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(62);
		}
		return notificationConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNotificationConfig_Property() {
        return (EReference)getNotificationConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNotificationConfig_Range() {
        return (EAttribute)getNotificationConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNotificationConfig_Tolerance() {
        return (EAttribute)getNotificationConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectManagementConfig() {
		if (objectManagementConfigEClass == null) {
			objectManagementConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(63);
		}
		return objectManagementConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectManagementConfig_MemoryManager() {
        return (EReference)getObjectManagementConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectManagementConfig_CacheManager() {
        return (EReference)getObjectManagementConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectManagementConfig_DbConcepts() {
        return (EReference)getObjectManagementConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectManagerConfig() {
		if (objectManagerConfigEClass == null) {
			objectManagerConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(64);
		}
		return objectManagerConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectTableConfig() {
		if (objectTableConfigEClass == null) {
			objectTableConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(65);
		}
		return objectTableConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectTableConfig_MaxSize() {
        return (EReference)getObjectTableConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOverrideConfig() {
		if (overrideConfigEClass == null) {
			overrideConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(67);
		}
		return overrideConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessAgentClassConfig() {
		if (processAgentClassConfigEClass == null) {
			processAgentClassConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(68);
		}
		return processAgentClassConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessAgentClassConfig_Load() {
        return (EReference)getProcessAgentClassConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessAgentClassConfig_ProcessEngine() {
        return (EReference)getProcessAgentClassConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessAgentClassConfig_PropertyGroup() {
        return (EReference)getProcessAgentClassConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessConfig() {
		if (processConfigEClass == null) {
			processConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(69);
		}
		return processConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessConfig_Group() {
        return (EAttribute)getProcessConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessConfig_Ref() {
        return (EReference)getProcessConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessConfig_Uri() {
        return (EAttribute)getProcessConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessEngineConfig() {
		if (processEngineConfigEClass == null) {
			processEngineConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(70);
		}
		return processEngineConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_Process() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_Startup() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_Shutdown() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_Rules() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_Destinations() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_Businessworks() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_PropertyGroup() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_JobManager() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessEngineConfig_InferenceEngine() {
        return (EReference)getProcessEngineConfig().getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessesConfig() {
		if (processesConfigEClass == null) {
			processesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(71);
		}
		return processesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessesConfig_Group() {
        return (EAttribute)getProcessesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessesConfig_Ref() {
        return (EReference)getProcessesConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessesConfig_Uri() {
        return (EAttribute)getProcessesConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessGroupsConfig() {
		if (processGroupsConfigEClass == null) {
			processGroupsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(72);
		}
		return processGroupsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessGroupsConfig_Processes() {
        return (EReference)getProcessGroupsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessingUnitConfig() {
		if (processingUnitConfigEClass == null) {
			processingUnitConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(73);
		}
		return processingUnitConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitConfig_Agents() {
        return (EReference)getProcessingUnitConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitConfig_CacheStorageEnabled() {
        return (EReference)getProcessingUnitConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitConfig_DbConcepts() {
        return (EReference)getProcessingUnitConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitConfig_Logs() {
        return (EReference)getProcessingUnitConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitConfig_HotDeploy() {
        return (EReference)getProcessingUnitConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitConfig_Http() {
        return (EReference)getProcessingUnitConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitConfig_PropertyGroup() {
        return (EReference)getProcessingUnitConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitConfig_SecurityConfig() {
        return (EReference)getProcessingUnitConfig().getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessingUnitsConfig() {
		if (processingUnitsConfigEClass == null) {
			processingUnitsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(74);
		}
		return processingUnitsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessingUnitsConfig_Group() {
        return (EAttribute)getProcessingUnitsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitsConfig_ProcessingUnit() {
        return (EReference)getProcessingUnitsConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessingUnitSecurityConfig() {
		if (processingUnitSecurityConfigEClass == null) {
			processingUnitSecurityConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(75);
		}
		return processingUnitSecurityConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitSecurityConfig_Role() {
        return (EReference)getProcessingUnitSecurityConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitSecurityConfig_Controller() {
        return (EReference)getProcessingUnitSecurityConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessingUnitSecurityConfig_Requester() {
        return (EReference)getProcessingUnitSecurityConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProjectionConfig() {
		if (projectionConfigEClass == null) {
			projectionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(76);
		}
		return projectionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectionConfig_SetProperty() {
        return (EReference)getProjectionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyConfig() {
		if (propertyConfigEClass == null) {
			propertyConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(77);
		}
		return propertyConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyConfig_Name() {
        return (EAttribute)getPropertyConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyConfig_Type() {
        return (EAttribute)getPropertyConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyConfig_Value() {
        return (EAttribute)getPropertyConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyGroupConfig() {
		if (propertyGroupConfigEClass == null) {
			propertyGroupConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(78);
		}
		return propertyGroupConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyGroupConfig_Group() {
        return (EAttribute)getPropertyGroupConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyGroupConfig_PropertyGroup() {
        return (EReference)getPropertyGroupConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyGroupConfig_Property() {
        return (EReference)getPropertyGroupConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyGroupConfig_Comment() {
        return (EAttribute)getPropertyGroupConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyGroupConfig_Name() {
        return (EAttribute)getPropertyGroupConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocolsConfig() {
		if (protocolsConfigEClass == null) {
			protocolsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(79);
		}
		return protocolsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProtocolsConfig_Protocol() {
        return (EReference)getProtocolsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProviderConfig() {
		if (providerConfigEClass == null) {
			providerConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(80);
		}
		return providerConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProviderConfig_Name() {
        return (EReference)getProviderConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQueryAgentClassConfig() {
		if (queryAgentClassConfigEClass == null) {
			queryAgentClassConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(81);
		}
		return queryAgentClassConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueryAgentClassConfig_Destinations() {
        return (EReference)getQueryAgentClassConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueryAgentClassConfig_Startup() {
        return (EReference)getQueryAgentClassConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueryAgentClassConfig_Shutdown() {
        return (EReference)getQueryAgentClassConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueryAgentClassConfig_Load() {
        return (EReference)getQueryAgentClassConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueryAgentClassConfig_LocalCache() {
        return (EReference)getQueryAgentClassConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueryAgentClassConfig_PropertyGroup() {
        return (EReference)getQueryAgentClassConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueryAgentClassConfig_SharedQueue() {
        return (EReference)getQueryAgentClassConfig().getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRevisionConfig() {
		if (revisionConfigEClass == null) {
			revisionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(82);
		}
		return revisionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRevisionConfig_Version() {
        return (EAttribute)getRevisionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRevisionConfig_Author() {
        return (EReference)getRevisionConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRevisionConfig_Date() {
        return (EReference)getRevisionConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRevisionConfig_Comment() {
        return (EReference)getRevisionConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleConfig() {
		if (ruleConfigEClass == null) {
			ruleConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(83);
		}
		return ruleConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleConfig_Enabled() {
        return (EAttribute)getRuleConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleConfig_Uri() {
        return (EAttribute)getRuleConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleConfigConfig() {
		if (ruleConfigConfigEClass == null) {
			ruleConfigConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(84);
		}
		return ruleConfigConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleConfigConfig_ClusterMember() {
        return (EReference)getRuleConfigConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRulesConfig() {
		if (rulesConfigEClass == null) {
			rulesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(85);
		}
		return rulesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRulesConfig_Group() {
        return (EAttribute)getRulesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRulesConfig_Ref() {
        return (EReference)getRulesConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRulesConfig_Uri() {
        return (EAttribute)getRulesConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRulesetsConfig() {
		if (rulesetsConfigEClass == null) {
			rulesetsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(86);
		}
		return rulesetsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRulesetsConfig_Rules() {
        return (EReference)getRulesetsConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSecurityConfig() {
		if (securityConfigEClass == null) {
			securityConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(87);
		}
		return securityConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSecurityController() {
		if (securityControllerEClass == null) {
			securityControllerEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(88);
		}
		return securityControllerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSecurityController_PolicyFile() {
        return (EReference)getSecurityController().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSecurityController_IdentityPassword() {
        return (EReference)getSecurityController().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSecurityOverrideConfig() {
		if (securityOverrideConfigEClass == null) {
			securityOverrideConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(89);
		}
		return securityOverrideConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSecurityOverrideConfig_Override() {
        return (EAttribute)getSecurityOverrideConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSecurityRequester() {
		if (securityRequesterEClass == null) {
			securityRequesterEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(90);
		}
		return securityRequesterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSecurityRequester_TokenFile() {
        return (EReference)getSecurityRequester().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSecurityRequester_IdentityPassword() {
        return (EReference)getSecurityRequester().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSecurityRequester_CertificateKeyFile() {
        return (EReference)getSecurityRequester().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSecurityRequester_DomainName() {
        return (EReference)getSecurityRequester().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSecurityRequester_UserName() {
        return (EReference)getSecurityRequester().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSecurityRequester_UserPassword() {
        return (EReference)getSecurityRequester().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSetPropertyConfig() {
		if (setPropertyConfigEClass == null) {
			setPropertyConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(91);
		}
		return setPropertyConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSetPropertyConfig_ChildClusterMember() {
        return (EReference)getSetPropertyConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSetPropertyConfig_Notification() {
        return (EReference)getSetPropertyConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSetPropertyConfig_Name() {
        return (EAttribute)getSetPropertyConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSetPropertyConfig_SetPropertyName() {
        return (EAttribute)getSetPropertyConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSetPropertyConfig_Value() {
        return (EAttribute)getSetPropertyConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedQueueConfig() {
		if (sharedQueueConfigEClass == null) {
			sharedQueueConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(92);
		}
		return sharedQueueConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedQueueConfig_Size() {
        return (EReference)getSharedQueueConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedQueueConfig_Workers() {
        return (EReference)getSharedQueueConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSslConfig() {
		if (sslConfigEClass == null) {
			sslConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(93);
		}
		return sslConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSslConfig_Protocols() {
        return (EReference)getSslConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSslConfig_Ciphers() {
        return (EReference)getSslConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSslConfig_KeyManagerAlgorithm() {
        return (EReference)getSslConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSslConfig_TrustManagerAlgorithm() {
        return (EReference)getSslConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSystemPropertyConfig() {
		if (systemPropertyConfigEClass == null) {
			systemPropertyConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(94);
		}
		return systemPropertyConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSystemPropertyConfig_Mixed() {
        return (EAttribute)getSystemPropertyConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSystemPropertyConfig_SystemProperty() {
        return (EAttribute)getSystemPropertyConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTerminalConfig() {
		if (terminalConfigEClass == null) {
			terminalConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(95);
		}
		return terminalConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTerminalConfig_Enabled() {
        return (EReference)getTerminalConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTerminalConfig_SysErrRedirect() {
        return (EReference)getTerminalConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTerminalConfig_SysOutRedirect() {
        return (EReference)getTerminalConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTerminalConfig_Encoding() {
        return (EReference)getTerminalConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUrisAndRefsConfig() {
		if (urisAndRefsConfigEClass == null) {
			urisAndRefsConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(98);
		}
		return urisAndRefsConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUrisConfig() {
		if (urisConfigEClass == null) {
			urisConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(99);
		}
		return urisConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUrisConfig_Group() {
        return (EAttribute)getUrisConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUrisConfig_Uri() {
        return (EAttribute)getUrisConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFieldEncryptionConfig() {
		if (fieldEncryptionConfigEClass == null) {
			fieldEncryptionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(100);
		}
		return fieldEncryptionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFieldEncryptionConfig_Property() {
        return (EReference)getFieldEncryptionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntityConfig() {
		if (entityConfigEClass == null) {
			entityConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(101);
		}
		return entityConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityConfig_Uri() {
        return (EAttribute)getEntityConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityConfig_Filter() {
        return (EReference)getEntityConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityConfig_EnableTableTrimming() {
        return (EReference)getEntityConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityConfig_TrimmingField() {
        return (EReference)getEntityConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityConfig_TrimmingRule() {
        return (EReference)getEntityConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntitySetConfig() {
		if (entitySetConfigEClass == null) {
			entitySetConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(102);
		}
		return entitySetConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntitySetConfig_Entity() {
        return (EReference)getEntitySetConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntitySetConfig_GenerateLVFiles() {
        return (EReference)getEntitySetConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntitySetConfig_OutputPath() {
        return (EReference)getEntitySetConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLDMConnectionConfig() {
		if (ldmConnectionConfigEClass == null) {
			ldmConnectionConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(103);
		}
		return ldmConnectionConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLDMConnectionConfig_LdmUrl() {
        return (EReference)getLDMConnectionConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLDMConnectionConfig_UserName() {
        return (EReference)getLDMConnectionConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLDMConnectionConfig_UserPassword() {
        return (EReference)getLDMConnectionConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLDMConnectionConfig_InitialSize() {
        return (EReference)getLDMConnectionConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLDMConnectionConfig_MinSize() {
        return (EReference)getLDMConnectionConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLDMConnectionConfig_MaxSize() {
        return (EReference)getLDMConnectionConfig().getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPublisherConfig() {
		if (publisherConfigEClass == null) {
			publisherConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(104);
		}
		return publisherConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPublisherConfig_PublisherQueueSize() {
        return (EReference)getPublisherConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPublisherConfig_PublisherThreadCount() {
        return (EReference)getPublisherConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLiveViewAgentClassConfig() {
		if (liveViewAgentClassConfigEClass == null) {
			liveViewAgentClassConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(105);
		}
		return liveViewAgentClassConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLiveViewAgentClassConfig_LdmConnection() {
        return (EReference)getLiveViewAgentClassConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLiveViewAgentClassConfig_Publisher() {
        return (EReference)getLiveViewAgentClassConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLiveViewAgentClassConfig_EntitySet() {
        return (EReference)getLiveViewAgentClassConfig().getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLiveViewAgentClassConfig_Load() {
        return (EReference)getLiveViewAgentClassConfig().getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLiveViewAgentClassConfig_PropertyGroup() {
        return (EReference)getLiveViewAgentClassConfig().getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositeIndexPropertiesConfig() {
		if (compositeIndexPropertiesConfigEClass == null) {
			compositeIndexPropertiesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(106);
		}
		return compositeIndexPropertiesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositeIndexPropertiesConfig_Property() {
        return (EReference)getCompositeIndexPropertiesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositeIndexConfig() {
		if (compositeIndexConfigEClass == null) {
			compositeIndexConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(107);
		}
		return compositeIndexConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositeIndexConfig_Name() {
        return (EReference)getCompositeIndexConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositeIndexConfig_CompositeIndexProperties() {
        return (EReference)getCompositeIndexConfig().getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositeIndexesConfig() {
		if (compositeIndexesConfigEClass == null) {
			compositeIndexesConfigEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(108);
		}
		return compositeIndexesConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositeIndexesConfig_CompositeIndex() {
        return (EReference)getCompositeIndexesConfig().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDomainObjectModeConfig() {
		if (domainObjectModeConfigEEnum == null) {
			domainObjectModeConfigEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(28);
		}
		return domainObjectModeConfigEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getThreadingModelConfig() {
		if (threadingModelConfigEEnum == null) {
			threadingModelConfigEEnum = (EEnum)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(96);
		}
		return threadingModelConfigEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDomainObjectModeConfigObject() {
		if (domainObjectModeConfigObjectEDataType == null) {
			domainObjectModeConfigObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(29);
		}
		return domainObjectModeConfigObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getOntologyUriConfig() {
		if (ontologyUriConfigEDataType == null) {
			ontologyUriConfigEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(66);
		}
		return ontologyUriConfigEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getThreadingModelConfigObject() {
		if (threadingModelConfigObjectEDataType == null) {
			threadingModelConfigObjectEDataType = (EDataType)EPackage.Registry.INSTANCE.getEPackage(CddPackage.eNS_URI).getEClassifiers().get(97);
		}
		return threadingModelConfigObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CddFactory getCddFactory() {
		return (CddFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isLoaded = false;

	/**
	 * Laods the package and any sub-packages from their serialized form.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void loadPackage() {
		if (isLoaded) return;
		isLoaded = true;

		URL url = getClass().getResource(packageFilename);
		if (url == null) {
			throw new RuntimeException("Missing serialized package: " + packageFilename);
		}
		URI uri = URI.createURI(url.toString());
		Resource resource = new EcoreResourceFactoryImpl().createResource(uri);
		try {
			resource.load(null);
		}
		catch (IOException exception) {
			throw new WrappedException(exception);
		}
		initializeFromLoadedEPackage(this, (EPackage)resource.getContents().get(0));
		createResource(eNS_URI);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isFixed = false;

	/**
	 * Fixes up the loaded package, to make it appear as if it had been programmatically built.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fixPackageContents() {
		if (isFixed) return;
		isFixed = true;
		fixEClassifiers();
	}

	/**
	 * Sets the instance class on the given classifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void fixInstanceClass(EClassifier eClassifier) {
		if (eClassifier.getInstanceClassName() == null) {
			eClassifier.setInstanceClassName("com.tibco.be.util.config.cdd." + eClassifier.getName());
			setGeneratedClassName(eClassifier);
		}
	}

} //CddPackageImpl
