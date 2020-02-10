/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.AdapterArchive;
import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BEArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.InputDestination;
import com.tibco.cep.designtime.core.model.archive.ProcessArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.designtime.core.model.archive.config.ConfigPackage;
import com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage;
import com.tibco.cep.designtime.core.model.designtimelib.impl.DesigntimelibPackageImpl;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.impl.ElementPackageImpl;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.impl.EventPackageImpl;
import com.tibco.cep.designtime.core.model.impl.ModelPackageImpl;
import com.tibco.cep.designtime.core.model.java.JavaPackage;
import com.tibco.cep.designtime.core.model.java.impl.JavaPackageImpl;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.impl.RulePackageImpl;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.impl.ChannelPackageImpl;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl;
import com.tibco.cep.designtime.core.model.validation.ValidationPackage;
import com.tibco.cep.designtime.core.model.validation.impl.ValidationPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArchivePackageImpl extends EPackageImpl implements ArchivePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass archiveResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enterpriseArchiveEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass beArchiveResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass businessEventsArchiveResourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inputDestinationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass advancedEntitySettingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedArchiveEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processArchiveEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adapterArchiveEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum bE_ARCHIVE_TYPEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum cachE_MODEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum workersEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum objecT_MGMT_TYPEEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum cachE_CONFIG_TYPEEEnum = null;

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
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ArchivePackageImpl() {
		super(eNS_URI, ArchiveFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ArchivePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ArchivePackage init() {
		if (isInited) return (ArchivePackage)EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI);

		// Obtain or create and register package
		ArchivePackageImpl theArchivePackage = (ArchivePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ArchivePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ArchivePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ElementPackageImpl theElementPackage = (ElementPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) instanceof ElementPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) : ElementPackage.eINSTANCE);
		EventPackageImpl theEventPackage = (EventPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) instanceof EventPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) : EventPackage.eINSTANCE);
		ChannelPackageImpl theChannelPackage = (ChannelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) instanceof ChannelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI) : ChannelPackage.eINSTANCE);
		RulePackageImpl theRulePackage = (RulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) instanceof RulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) : RulePackage.eINSTANCE);
		StatesPackageImpl theStatesPackage = (StatesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) instanceof StatesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) : StatesPackage.eINSTANCE);
		ConfigPackageImpl theConfigPackage = (ConfigPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) instanceof ConfigPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) : ConfigPackage.eINSTANCE);
		DomainPackageImpl theDomainPackage = (DomainPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) instanceof DomainPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) : DomainPackage.eINSTANCE);
		ValidationPackageImpl theValidationPackage = (ValidationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) instanceof ValidationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) : ValidationPackage.eINSTANCE);
		DesigntimelibPackageImpl theDesigntimelibPackage = (DesigntimelibPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) instanceof DesigntimelibPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) : DesigntimelibPackage.eINSTANCE);
		JavaPackageImpl theJavaPackage = (JavaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) instanceof JavaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) : JavaPackage.eINSTANCE);

		// Create package meta-data objects
		theArchivePackage.createPackageContents();
		theModelPackage.createPackageContents();
		theElementPackage.createPackageContents();
		theEventPackage.createPackageContents();
		theChannelPackage.createPackageContents();
		theRulePackage.createPackageContents();
		theStatesPackage.createPackageContents();
		theConfigPackage.createPackageContents();
		theDomainPackage.createPackageContents();
		theValidationPackage.createPackageContents();
		theDesigntimelibPackage.createPackageContents();
		theJavaPackage.createPackageContents();

		// Initialize created meta-data
		theArchivePackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theElementPackage.initializePackageContents();
		theEventPackage.initializePackageContents();
		theChannelPackage.initializePackageContents();
		theRulePackage.initializePackageContents();
		theStatesPackage.initializePackageContents();
		theConfigPackage.initializePackageContents();
		theDomainPackage.initializePackageContents();
		theValidationPackage.initializePackageContents();
		theDesigntimelibPackage.initializePackageContents();
		theJavaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theArchivePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ArchivePackage.eNS_URI, theArchivePackage);
		return theArchivePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArchiveResource() {
		return archiveResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArchiveResource_Name() {
		return (EAttribute)archiveResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArchiveResource_Description() {
		return (EAttribute)archiveResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArchiveResource_Author() {
		return (EAttribute)archiveResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnterpriseArchive() {
		return enterpriseArchiveEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchive_Version() {
		return (EAttribute)enterpriseArchiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchive_Folder() {
		return (EAttribute)enterpriseArchiveEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchive_OwnerProjectName() {
		return (EAttribute)enterpriseArchiveEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchive_FileLocation() {
		return (EAttribute)enterpriseArchiveEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnterpriseArchive_IncludeGlobalVars() {
		return (EAttribute)enterpriseArchiveEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnterpriseArchive_BusinessEventsArchives() {
		return (EReference)enterpriseArchiveEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnterpriseArchive_SharedArchives() {
		return (EReference)enterpriseArchiveEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnterpriseArchive_ProcessArchives() {
		return (EReference)enterpriseArchiveEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBEArchiveResource() {
		return beArchiveResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBEArchiveResource_CompileWithDebug() {
		return (EAttribute)beArchiveResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBEArchiveResource_DeleteTempFiles() {
		return (EAttribute)beArchiveResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBEArchiveResource_CompilePath() {
		return (EAttribute)beArchiveResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBEArchiveResource_ExtraClassPath() {
		return (EAttribute)beArchiveResourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBusinessEventsArchiveResource() {
		return businessEventsArchiveResourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_ArchiveType() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_AllRuleSets() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_AllDestinations() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_IncludedRuleSets() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_StartupActions() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_ShutdownActions() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBusinessEventsArchiveResource_InputDestinations() {
		return (EReference)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmCheckPtInterval() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmCacheSize() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmCheckPtOpsLimit() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmDeletePolicy() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmNoRecovery() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmDbEnvironmentDir() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmtgGlobalCache() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmtgCacheConfigFile() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBusinessEventsArchiveResource_AdvancedSettings() {
		return (EReference)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_ObjectMgmtType() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_AgentGroupName() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_CacheConfigType() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessEventsArchiveResource_OmtgCustomRecoveryFactory() {
		return (EAttribute)businessEventsArchiveResourceEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInputDestination() {
		return inputDestinationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInputDestination_DestinationURI() {
		return (EAttribute)inputDestinationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInputDestination_Preprocessor() {
		return (EAttribute)inputDestinationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInputDestination_Default() {
		return (EAttribute)inputDestinationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInputDestination_Enable() {
		return (EAttribute)inputDestinationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInputDestination_Workers() {
		return (EAttribute)inputDestinationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInputDestination_QueueSize() {
		return (EAttribute)inputDestinationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInputDestination_ThreadAffinityRuleFunction() {
		return (EAttribute)inputDestinationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdvancedEntitySetting() {
		return advancedEntitySettingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedEntitySetting_Entity() {
		return (EAttribute)advancedEntitySettingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedEntitySetting_Deployed() {
		return (EAttribute)advancedEntitySettingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedEntitySetting_RecoveryFunction() {
		return (EAttribute)advancedEntitySettingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedEntitySetting_CacheMode() {
		return (EAttribute)advancedEntitySettingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedArchive() {
		return sharedArchiveEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSharedArchive_SharedResources() {
		return (EAttribute)sharedArchiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSharedArchive_SharedFiles() {
		return (EAttribute)sharedArchiveEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSharedArchive_SharedJarFiles() {
		return (EAttribute)sharedArchiveEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessArchive() {
		return processArchiveEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdapterArchive() {
		return adapterArchiveEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBE_ARCHIVE_TYPE() {
		return bE_ARCHIVE_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCACHE_MODE() {
		return cachE_MODEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getWORKERS() {
		return workersEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOBJECT_MGMT_TYPE() {
		return objecT_MGMT_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCACHE_CONFIG_TYPE() {
		return cachE_CONFIG_TYPEEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchiveFactory getArchiveFactory() {
		return (ArchiveFactory)getEFactoryInstance();
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
		archiveResourceEClass = createEClass(ARCHIVE_RESOURCE);
		createEAttribute(archiveResourceEClass, ARCHIVE_RESOURCE__NAME);
		createEAttribute(archiveResourceEClass, ARCHIVE_RESOURCE__DESCRIPTION);
		createEAttribute(archiveResourceEClass, ARCHIVE_RESOURCE__AUTHOR);

		enterpriseArchiveEClass = createEClass(ENTERPRISE_ARCHIVE);
		createEAttribute(enterpriseArchiveEClass, ENTERPRISE_ARCHIVE__VERSION);
		createEAttribute(enterpriseArchiveEClass, ENTERPRISE_ARCHIVE__FOLDER);
		createEAttribute(enterpriseArchiveEClass, ENTERPRISE_ARCHIVE__OWNER_PROJECT_NAME);
		createEAttribute(enterpriseArchiveEClass, ENTERPRISE_ARCHIVE__FILE_LOCATION);
		createEAttribute(enterpriseArchiveEClass, ENTERPRISE_ARCHIVE__INCLUDE_GLOBAL_VARS);
		createEReference(enterpriseArchiveEClass, ENTERPRISE_ARCHIVE__BUSINESS_EVENTS_ARCHIVES);
		createEReference(enterpriseArchiveEClass, ENTERPRISE_ARCHIVE__SHARED_ARCHIVES);
		createEReference(enterpriseArchiveEClass, ENTERPRISE_ARCHIVE__PROCESS_ARCHIVES);

		beArchiveResourceEClass = createEClass(BE_ARCHIVE_RESOURCE);
		createEAttribute(beArchiveResourceEClass, BE_ARCHIVE_RESOURCE__COMPILE_WITH_DEBUG);
		createEAttribute(beArchiveResourceEClass, BE_ARCHIVE_RESOURCE__DELETE_TEMP_FILES);
		createEAttribute(beArchiveResourceEClass, BE_ARCHIVE_RESOURCE__COMPILE_PATH);
		createEAttribute(beArchiveResourceEClass, BE_ARCHIVE_RESOURCE__EXTRA_CLASS_PATH);

		businessEventsArchiveResourceEClass = createEClass(BUSINESS_EVENTS_ARCHIVE_RESOURCE);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__ARCHIVE_TYPE);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_RULE_SETS);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__ALL_DESTINATIONS);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__STARTUP_ACTIONS);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__SHUTDOWN_ACTIONS);
		createEReference(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_INTERVAL);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CACHE_SIZE);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_CHECK_PT_OPS_LIMIT);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DELETE_POLICY);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_NO_RECOVERY);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OM_DB_ENVIRONMENT_DIR);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_GLOBAL_CACHE);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CACHE_CONFIG_FILE);
		createEReference(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__ADVANCED_SETTINGS);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OBJECT_MGMT_TYPE);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__AGENT_GROUP_NAME);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__CACHE_CONFIG_TYPE);
		createEAttribute(businessEventsArchiveResourceEClass, BUSINESS_EVENTS_ARCHIVE_RESOURCE__OMTG_CUSTOM_RECOVERY_FACTORY);

		inputDestinationEClass = createEClass(INPUT_DESTINATION);
		createEAttribute(inputDestinationEClass, INPUT_DESTINATION__DESTINATION_URI);
		createEAttribute(inputDestinationEClass, INPUT_DESTINATION__PREPROCESSOR);
		createEAttribute(inputDestinationEClass, INPUT_DESTINATION__DEFAULT);
		createEAttribute(inputDestinationEClass, INPUT_DESTINATION__ENABLE);
		createEAttribute(inputDestinationEClass, INPUT_DESTINATION__WORKERS);
		createEAttribute(inputDestinationEClass, INPUT_DESTINATION__QUEUE_SIZE);
		createEAttribute(inputDestinationEClass, INPUT_DESTINATION__THREAD_AFFINITY_RULE_FUNCTION);

		advancedEntitySettingEClass = createEClass(ADVANCED_ENTITY_SETTING);
		createEAttribute(advancedEntitySettingEClass, ADVANCED_ENTITY_SETTING__ENTITY);
		createEAttribute(advancedEntitySettingEClass, ADVANCED_ENTITY_SETTING__DEPLOYED);
		createEAttribute(advancedEntitySettingEClass, ADVANCED_ENTITY_SETTING__RECOVERY_FUNCTION);
		createEAttribute(advancedEntitySettingEClass, ADVANCED_ENTITY_SETTING__CACHE_MODE);

		sharedArchiveEClass = createEClass(SHARED_ARCHIVE);
		createEAttribute(sharedArchiveEClass, SHARED_ARCHIVE__SHARED_RESOURCES);
		createEAttribute(sharedArchiveEClass, SHARED_ARCHIVE__SHARED_FILES);
		createEAttribute(sharedArchiveEClass, SHARED_ARCHIVE__SHARED_JAR_FILES);

		processArchiveEClass = createEClass(PROCESS_ARCHIVE);

		adapterArchiveEClass = createEClass(ADAPTER_ARCHIVE);

		// Create enums
		bE_ARCHIVE_TYPEEEnum = createEEnum(BE_ARCHIVE_TYPE);
		cachE_MODEEEnum = createEEnum(CACHE_MODE);
		workersEEnum = createEEnum(WORKERS);
		objecT_MGMT_TYPEEEnum = createEEnum(OBJECT_MGMT_TYPE);
		cachE_CONFIG_TYPEEEnum = createEEnum(CACHE_CONFIG_TYPE);
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

		// Obtain other dependent packages
		ConfigPackage theConfigPackage = (ConfigPackage)EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theConfigPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		enterpriseArchiveEClass.getESuperTypes().add(this.getArchiveResource());
		beArchiveResourceEClass.getESuperTypes().add(this.getArchiveResource());
		businessEventsArchiveResourceEClass.getESuperTypes().add(this.getBEArchiveResource());
		sharedArchiveEClass.getESuperTypes().add(this.getArchiveResource());
		processArchiveEClass.getESuperTypes().add(this.getArchiveResource());
		adapterArchiveEClass.getESuperTypes().add(this.getArchiveResource());

		// Initialize classes and features; add operations and parameters
		initEClass(archiveResourceEClass, ArchiveResource.class, "ArchiveResource", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArchiveResource_Name(), ecorePackage.getEString(), "name", null, 1, 1, ArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArchiveResource_Description(), ecorePackage.getEString(), "description", null, 1, 1, ArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArchiveResource_Author(), ecorePackage.getEString(), "author", null, 1, 1, ArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enterpriseArchiveEClass, EnterpriseArchive.class, "EnterpriseArchive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnterpriseArchive_Version(), ecorePackage.getEString(), "version", null, 1, 1, EnterpriseArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchive_Folder(), ecorePackage.getEString(), "folder", null, 1, 1, EnterpriseArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchive_OwnerProjectName(), ecorePackage.getEString(), "ownerProjectName", null, 0, 1, EnterpriseArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchive_FileLocation(), ecorePackage.getEString(), "fileLocation", null, 1, 1, EnterpriseArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnterpriseArchive_IncludeGlobalVars(), ecorePackage.getEBoolean(), "includeGlobalVars", null, 1, 1, EnterpriseArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEnterpriseArchive_BusinessEventsArchives(), this.getBusinessEventsArchiveResource(), null, "businessEventsArchives", null, 0, -1, EnterpriseArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEnterpriseArchive_SharedArchives(), this.getSharedArchive(), null, "sharedArchives", null, 0, -1, EnterpriseArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEnterpriseArchive_ProcessArchives(), this.getSharedArchive(), null, "processArchives", null, 0, -1, EnterpriseArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(beArchiveResourceEClass, BEArchiveResource.class, "BEArchiveResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBEArchiveResource_CompileWithDebug(), ecorePackage.getEBoolean(), "compileWithDebug", null, 0, 1, BEArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBEArchiveResource_DeleteTempFiles(), ecorePackage.getEBoolean(), "deleteTempFiles", null, 0, 1, BEArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBEArchiveResource_CompilePath(), ecorePackage.getEString(), "compilePath", null, 0, 1, BEArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBEArchiveResource_ExtraClassPath(), ecorePackage.getEString(), "extraClassPath", null, 0, 1, BEArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(businessEventsArchiveResourceEClass, BusinessEventsArchiveResource.class, "BusinessEventsArchiveResource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBusinessEventsArchiveResource_ArchiveType(), this.getBE_ARCHIVE_TYPE(), "archiveType", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_AllRuleSets(), ecorePackage.getEBoolean(), "allRuleSets", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_AllDestinations(), ecorePackage.getEBoolean(), "allDestinations", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_IncludedRuleSets(), ecorePackage.getEString(), "includedRuleSets", null, 0, -1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_StartupActions(), ecorePackage.getEString(), "startupActions", null, 0, -1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_ShutdownActions(), ecorePackage.getEString(), "shutdownActions", null, 0, -1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBusinessEventsArchiveResource_InputDestinations(), this.getInputDestination(), null, "inputDestinations", null, 0, -1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmCheckPtInterval(), ecorePackage.getEInt(), "omCheckPtInterval", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmCacheSize(), ecorePackage.getEInt(), "omCacheSize", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmCheckPtOpsLimit(), ecorePackage.getEInt(), "omCheckPtOpsLimit", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmDeletePolicy(), ecorePackage.getEBoolean(), "omDeletePolicy", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmNoRecovery(), ecorePackage.getEBoolean(), "omNoRecovery", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmDbEnvironmentDir(), ecorePackage.getEString(), "omDbEnvironmentDir", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmtgGlobalCache(), ecorePackage.getEString(), "omtgGlobalCache", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmtgCacheConfigFile(), ecorePackage.getEString(), "omtgCacheConfigFile", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBusinessEventsArchiveResource_AdvancedSettings(), this.getAdvancedEntitySetting(), null, "advancedSettings", null, 0, -1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_ObjectMgmtType(), this.getOBJECT_MGMT_TYPE(), "objectMgmtType", "InMemory", 1, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_AgentGroupName(), ecorePackage.getEString(), "agentGroupName", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_CacheConfigType(), this.getCACHE_CONFIG_TYPE(), "cacheConfigType", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBusinessEventsArchiveResource_OmtgCustomRecoveryFactory(), ecorePackage.getEString(), "omtgCustomRecoveryFactory", null, 0, 1, BusinessEventsArchiveResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inputDestinationEClass, InputDestination.class, "InputDestination", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInputDestination_DestinationURI(), ecorePackage.getEString(), "destinationURI", null, 0, 1, InputDestination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInputDestination_Preprocessor(), ecorePackage.getEString(), "preprocessor", null, 0, 1, InputDestination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInputDestination_Default(), ecorePackage.getEBoolean(), "default", null, 0, 1, InputDestination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInputDestination_Enable(), ecorePackage.getEBoolean(), "enable", null, 0, 1, InputDestination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInputDestination_Workers(), this.getWORKERS(), "workers", null, 0, 1, InputDestination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInputDestination_QueueSize(), ecorePackage.getEInt(), "queueSize", null, 0, 1, InputDestination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInputDestination_ThreadAffinityRuleFunction(), ecorePackage.getEString(), "threadAffinityRuleFunction", null, 0, 1, InputDestination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(advancedEntitySettingEClass, AdvancedEntitySetting.class, "AdvancedEntitySetting", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAdvancedEntitySetting_Entity(), ecorePackage.getEString(), "entity", null, 0, 1, AdvancedEntitySetting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdvancedEntitySetting_Deployed(), ecorePackage.getEBoolean(), "deployed", null, 0, 1, AdvancedEntitySetting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdvancedEntitySetting_RecoveryFunction(), ecorePackage.getEString(), "recoveryFunction", null, 0, 1, AdvancedEntitySetting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdvancedEntitySetting_CacheMode(), this.getCACHE_MODE(), "cacheMode", null, 0, 1, AdvancedEntitySetting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedArchiveEClass, SharedArchive.class, "SharedArchive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSharedArchive_SharedResources(), ecorePackage.getEString(), "sharedResources", null, 0, -1, SharedArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSharedArchive_SharedFiles(), ecorePackage.getEString(), "sharedFiles", null, 0, -1, SharedArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSharedArchive_SharedJarFiles(), ecorePackage.getEString(), "sharedJarFiles", null, 0, -1, SharedArchive.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(processArchiveEClass, ProcessArchive.class, "ProcessArchive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(adapterArchiveEClass, AdapterArchive.class, "AdapterArchive", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(bE_ARCHIVE_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE.class, "BE_ARCHIVE_TYPE");
		addEEnumLiteral(bE_ARCHIVE_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE.INFERENCE);
		addEEnumLiteral(bE_ARCHIVE_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE.QUERY);

		initEEnum(cachE_MODEEEnum, com.tibco.cep.designtime.core.model.archive.CACHE_MODE.class, "CACHE_MODE");
		addEEnumLiteral(cachE_MODEEEnum, com.tibco.cep.designtime.core.model.archive.CACHE_MODE.CACHE_AND_MEMORY);
		addEEnumLiteral(cachE_MODEEEnum, com.tibco.cep.designtime.core.model.archive.CACHE_MODE.CACHE_ONLY);
		addEEnumLiteral(cachE_MODEEEnum, com.tibco.cep.designtime.core.model.archive.CACHE_MODE.MEMORY_ONLY);

		initEEnum(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.class, "WORKERS");
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.SHARED_QUEUE_AND_THREADS);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.CALLERS_THREAD);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.ONE);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.TWO);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.THREE);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.FOUR);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.FIVE);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.SIX);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.SEVEN);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.EIGHT);
		addEEnumLiteral(workersEEnum, com.tibco.cep.designtime.core.model.archive.WORKERS.NINE);

		initEEnum(objecT_MGMT_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE.class, "OBJECT_MGMT_TYPE");
		addEEnumLiteral(objecT_MGMT_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE.IN_MEMORY);
		addEEnumLiteral(objecT_MGMT_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE.CACHE);
		addEEnumLiteral(objecT_MGMT_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE.PERSISTENCE);

		initEEnum(cachE_CONFIG_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE.class, "CACHE_CONFIG_TYPE");
		addEEnumLiteral(cachE_CONFIG_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE.XML_RESOURCE);
		addEEnumLiteral(cachE_CONFIG_TYPEEEnum, com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE.FILE);
	}

} //ArchivePackageImpl
