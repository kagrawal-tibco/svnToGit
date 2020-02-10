/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.config.ConfigPackage;
import com.tibco.cep.designtime.core.model.archive.config.impl.ConfigPackageImpl;
import com.tibco.cep.designtime.core.model.archive.impl.ArchivePackageImpl;
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
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DestinationConcept;
import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.designtime.core.model.service.channel.DriverRegistration;
import com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMap;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry;
import com.tibco.cep.designtime.core.model.service.channel.SerializerConfig;
import com.tibco.cep.designtime.core.model.service.channel.SerializerInfo;
import com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor;
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
public class ChannelPackageImpl extends EPackageImpl implements ChannelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass channelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass destinationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass driverConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass destinationConceptEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass driverManagerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass driverRegistrationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serializerConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass choiceConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass choiceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serializerInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extendedConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass destinationDescriptorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass channelDescriptorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyDescriptorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyDescriptorMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyDescriptorMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass driveR_TYPEEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass driverTypeInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass httpChannelDriverConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass webApplicationDescriptorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum confiG_METHODEEnum = null;

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
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ChannelPackageImpl() {
		super(eNS_URI, ChannelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ChannelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ChannelPackage init() {
		if (isInited) return (ChannelPackage)EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI);

		// Obtain or create and register package
		ChannelPackageImpl theChannelPackage = (ChannelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ChannelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ChannelPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ElementPackageImpl theElementPackage = (ElementPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) instanceof ElementPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI) : ElementPackage.eINSTANCE);
		EventPackageImpl theEventPackage = (EventPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) instanceof EventPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EventPackage.eNS_URI) : EventPackage.eINSTANCE);
		RulePackageImpl theRulePackage = (RulePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) instanceof RulePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI) : RulePackage.eINSTANCE);
		StatesPackageImpl theStatesPackage = (StatesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) instanceof StatesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI) : StatesPackage.eINSTANCE);
		ArchivePackageImpl theArchivePackage = (ArchivePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) instanceof ArchivePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI) : ArchivePackage.eINSTANCE);
		ConfigPackageImpl theConfigPackage = (ConfigPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) instanceof ConfigPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConfigPackage.eNS_URI) : ConfigPackage.eINSTANCE);
		DomainPackageImpl theDomainPackage = (DomainPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) instanceof DomainPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI) : DomainPackage.eINSTANCE);
		ValidationPackageImpl theValidationPackage = (ValidationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) instanceof ValidationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ValidationPackage.eNS_URI) : ValidationPackage.eINSTANCE);
		DesigntimelibPackageImpl theDesigntimelibPackage = (DesigntimelibPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) instanceof DesigntimelibPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DesigntimelibPackage.eNS_URI) : DesigntimelibPackage.eINSTANCE);
		JavaPackageImpl theJavaPackage = (JavaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) instanceof JavaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI) : JavaPackage.eINSTANCE);

		// Create package meta-data objects
		theChannelPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theElementPackage.createPackageContents();
		theEventPackage.createPackageContents();
		theRulePackage.createPackageContents();
		theStatesPackage.createPackageContents();
		theArchivePackage.createPackageContents();
		theConfigPackage.createPackageContents();
		theDomainPackage.createPackageContents();
		theValidationPackage.createPackageContents();
		theDesigntimelibPackage.createPackageContents();
		theJavaPackage.createPackageContents();

		// Initialize created meta-data
		theChannelPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theElementPackage.initializePackageContents();
		theEventPackage.initializePackageContents();
		theRulePackage.initializePackageContents();
		theStatesPackage.initializePackageContents();
		theArchivePackage.initializePackageContents();
		theConfigPackage.initializePackageContents();
		theDomainPackage.initializePackageContents();
		theValidationPackage.initializePackageContents();
		theDesigntimelibPackage.initializePackageContents();
		theJavaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theChannelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ChannelPackage.eNS_URI, theChannelPackage);
		return theChannelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChannel() {
		return channelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChannel_Driver() {
		return (EReference)channelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChannel_DriverManager() {
		return (EReference)channelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDestination() {
		return destinationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestination_EventURI() {
		return (EAttribute)destinationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestination_SerializerDeserializerClass() {
		return (EAttribute)destinationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDestination_Properties() {
		return (EReference)destinationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDestination_DriverConfig() {
		return (EReference)destinationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestination_InputEnabled() {
		return (EAttribute)destinationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestination_OutputEnabled() {
		return (EAttribute)destinationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDriverConfig() {
		return driverConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverConfig_DriverType() {
		return (EReference)driverConfigEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverConfig_Choice() {
		return (EReference)driverConfigEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverConfig_ConfigMethod() {
		return (EAttribute)driverConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverConfig_Reference() {
		return (EAttribute)driverConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverConfig_Label() {
		return (EAttribute)driverConfigEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverConfig_Channel() {
		return (EReference)driverConfigEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverConfig_Properties() {
		return (EReference)driverConfigEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverConfig_Destinations() {
		return (EReference)driverConfigEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverConfig_ExtendedConfiguration() {
		return (EReference)driverConfigEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDestinationConcept() {
		return destinationConceptEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDriverManager() {
		return driverManagerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDriverRegistration() {
		return driverRegistrationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverRegistration_DriverType() {
		return (EReference)driverRegistrationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverRegistration_Choice() {
		return (EReference)driverRegistrationEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverRegistration_Label() {
		return (EAttribute)driverRegistrationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverRegistration_Version() {
		return (EAttribute)driverRegistrationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverRegistration_Description() {
		return (EAttribute)driverRegistrationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverRegistration_ChannelDescriptor() {
		return (EReference)driverRegistrationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverRegistration_DestinationDescriptor() {
		return (EReference)driverRegistrationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverRegistration_ResourcesAllowed() {
		return (EAttribute)driverRegistrationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverRegistration_SerializerConfig() {
		return (EReference)driverRegistrationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverRegistration_ChoiceConfigurations() {
		return (EReference)driverRegistrationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDriverRegistration_ExtendedConfigurations() {
		return (EReference)driverRegistrationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSerializerConfig() {
		return serializerConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSerializerConfig_UserDefined() {
		return (EAttribute)serializerConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSerializerConfig_Serializers() {
		return (EReference)serializerConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChoiceConfiguration() {
		return choiceConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChoiceConfiguration_PropertyName() {
		return (EAttribute)choiceConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChoiceConfiguration_PropertyParent() {
		return (EAttribute)choiceConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChoiceConfiguration_ConfigType() {
		return (EAttribute)choiceConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChoiceConfiguration_Choices() {
		return (EReference)choiceConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChoiceConfiguration_DefaultValue() {
		return (EAttribute)choiceConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChoiceConfiguration_DisplayName() {
		return (EAttribute)choiceConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChoice() {
		return choiceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChoice_DisplayedValue() {
		return (EAttribute)choiceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getChoice_Value() {
		return (EAttribute)choiceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChoice_ExtendedConfiguration() {
		return (EReference)choiceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSerializerInfo() {
		return serializerInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSerializerInfo_SerializerType() {
		return (EAttribute)serializerInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSerializerInfo_SerializerClass() {
		return (EAttribute)serializerInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSerializerInfo_Default() {
		return (EAttribute)serializerInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtendedConfiguration() {
		return extendedConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtendedConfiguration_Properties() {
		return (EReference)extendedConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDestinationDescriptor() {
		return destinationDescriptorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChannelDescriptor() {
		return channelDescriptorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChannelDescriptor_DestinationDescriptor() {
		return (EReference)channelDescriptorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyDescriptor() {
		return propertyDescriptorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyDescriptor_DefaultValue() {
		return (EAttribute)propertyDescriptorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyDescriptor_Name() {
		return (EAttribute)propertyDescriptorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyDescriptor_Pattern() {
		return (EAttribute)propertyDescriptorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyDescriptor_Type() {
		return (EAttribute)propertyDescriptorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyDescriptor_Mandatory() {
		return (EAttribute)propertyDescriptorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyDescriptor_DisplayName() {
		return (EAttribute)propertyDescriptorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyDescriptor_GvToggle() {
		return (EAttribute)propertyDescriptorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyDescriptorMapEntry() {
		return propertyDescriptorMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyDescriptorMapEntry_Key() {
		return (EAttribute)propertyDescriptorMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyDescriptorMapEntry_Value() {
		return (EReference)propertyDescriptorMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyDescriptorMap() {
		return propertyDescriptorMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyDescriptorMap_Descriptors() {
		return (EReference)propertyDescriptorMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCONFIG_METHOD() {
		return confiG_METHODEEnum;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDRIVER_TYPE() {
		return driveR_TYPEEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDriverTypeInfo() {
		return driverTypeInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverTypeInfo_DriverTypeName() {
		return (EAttribute)driverTypeInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDriverTypeInfo_SharedResourceExtension() {
		return (EAttribute)driverTypeInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHttpChannelDriverConfig() {
		return httpChannelDriverConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHttpChannelDriverConfig_WebApplicationDescriptors() {
		return (EReference)httpChannelDriverConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWebApplicationDescriptor() {
		return webApplicationDescriptorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWebApplicationDescriptor_ContextURI() {
		return (EAttribute)webApplicationDescriptorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWebApplicationDescriptor_WebAppSourcePath() {
		return (EAttribute)webApplicationDescriptorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChannelFactory getChannelFactory() {
		return (ChannelFactory)getEFactoryInstance();
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
		channelEClass = createEClass(CHANNEL);
		createEReference(channelEClass, CHANNEL__DRIVER);
		createEReference(channelEClass, CHANNEL__DRIVER_MANAGER);

		destinationEClass = createEClass(DESTINATION);
		createEAttribute(destinationEClass, DESTINATION__EVENT_URI);
		createEAttribute(destinationEClass, DESTINATION__SERIALIZER_DESERIALIZER_CLASS);
		createEReference(destinationEClass, DESTINATION__PROPERTIES);
		createEReference(destinationEClass, DESTINATION__DRIVER_CONFIG);
		createEAttribute(destinationEClass, DESTINATION__INPUT_ENABLED);
		createEAttribute(destinationEClass, DESTINATION__OUTPUT_ENABLED);

		driverConfigEClass = createEClass(DRIVER_CONFIG);
		createEAttribute(driverConfigEClass, DRIVER_CONFIG__CONFIG_METHOD);
		createEAttribute(driverConfigEClass, DRIVER_CONFIG__REFERENCE);
		createEAttribute(driverConfigEClass, DRIVER_CONFIG__LABEL);
		createEReference(driverConfigEClass, DRIVER_CONFIG__CHANNEL);
		createEReference(driverConfigEClass, DRIVER_CONFIG__PROPERTIES);
		createEReference(driverConfigEClass, DRIVER_CONFIG__DESTINATIONS);
		createEReference(driverConfigEClass, DRIVER_CONFIG__EXTENDED_CONFIGURATION);
		createEReference(driverConfigEClass, DRIVER_CONFIG__DRIVER_TYPE);
		createEReference(driverConfigEClass, DRIVER_CONFIG__CHOICE);

		destinationConceptEClass = createEClass(DESTINATION_CONCEPT);

		driverManagerEClass = createEClass(DRIVER_MANAGER);

		driverRegistrationEClass = createEClass(DRIVER_REGISTRATION);
		createEAttribute(driverRegistrationEClass, DRIVER_REGISTRATION__LABEL);
		createEAttribute(driverRegistrationEClass, DRIVER_REGISTRATION__VERSION);
		createEAttribute(driverRegistrationEClass, DRIVER_REGISTRATION__DESCRIPTION);
		createEReference(driverRegistrationEClass, DRIVER_REGISTRATION__CHANNEL_DESCRIPTOR);
		createEReference(driverRegistrationEClass, DRIVER_REGISTRATION__DESTINATION_DESCRIPTOR);
		createEAttribute(driverRegistrationEClass, DRIVER_REGISTRATION__RESOURCES_ALLOWED);
		createEReference(driverRegistrationEClass, DRIVER_REGISTRATION__SERIALIZER_CONFIG);
		createEReference(driverRegistrationEClass, DRIVER_REGISTRATION__CHOICE_CONFIGURATIONS);
		createEReference(driverRegistrationEClass, DRIVER_REGISTRATION__EXTENDED_CONFIGURATIONS);
		createEReference(driverRegistrationEClass, DRIVER_REGISTRATION__DRIVER_TYPE);
		createEReference(driverRegistrationEClass, DRIVER_REGISTRATION__CHOICE);

		serializerConfigEClass = createEClass(SERIALIZER_CONFIG);
		createEAttribute(serializerConfigEClass, SERIALIZER_CONFIG__USER_DEFINED);
		createEReference(serializerConfigEClass, SERIALIZER_CONFIG__SERIALIZERS);

		choiceConfigurationEClass = createEClass(CHOICE_CONFIGURATION);
		createEAttribute(choiceConfigurationEClass, CHOICE_CONFIGURATION__PROPERTY_NAME);
		createEAttribute(choiceConfigurationEClass, CHOICE_CONFIGURATION__PROPERTY_PARENT);
		createEAttribute(choiceConfigurationEClass, CHOICE_CONFIGURATION__CONFIG_TYPE);
		createEReference(choiceConfigurationEClass, CHOICE_CONFIGURATION__CHOICES);
		createEAttribute(choiceConfigurationEClass, CHOICE_CONFIGURATION__DEFAULT_VALUE);
		createEAttribute(choiceConfigurationEClass, CHOICE_CONFIGURATION__DISPLAY_NAME);

		choiceEClass = createEClass(CHOICE);
		createEAttribute(choiceEClass, CHOICE__DISPLAYED_VALUE);
		createEAttribute(choiceEClass, CHOICE__VALUE);
		createEReference(choiceEClass, CHOICE__EXTENDED_CONFIGURATION);

		serializerInfoEClass = createEClass(SERIALIZER_INFO);
		createEAttribute(serializerInfoEClass, SERIALIZER_INFO__SERIALIZER_TYPE);
		createEAttribute(serializerInfoEClass, SERIALIZER_INFO__SERIALIZER_CLASS);
		createEAttribute(serializerInfoEClass, SERIALIZER_INFO__DEFAULT);

		extendedConfigurationEClass = createEClass(EXTENDED_CONFIGURATION);
		createEReference(extendedConfigurationEClass, EXTENDED_CONFIGURATION__PROPERTIES);

		destinationDescriptorEClass = createEClass(DESTINATION_DESCRIPTOR);

		channelDescriptorEClass = createEClass(CHANNEL_DESCRIPTOR);
		createEReference(channelDescriptorEClass, CHANNEL_DESCRIPTOR__DESTINATION_DESCRIPTOR);

		propertyDescriptorEClass = createEClass(PROPERTY_DESCRIPTOR);
		createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__DEFAULT_VALUE);
		createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__NAME);
		createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__PATTERN);
		createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__TYPE);
		createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__MANDATORY);
		createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__DISPLAY_NAME);
		createEAttribute(propertyDescriptorEClass, PROPERTY_DESCRIPTOR__GV_TOGGLE);

		propertyDescriptorMapEntryEClass = createEClass(PROPERTY_DESCRIPTOR_MAP_ENTRY);
		createEAttribute(propertyDescriptorMapEntryEClass, PROPERTY_DESCRIPTOR_MAP_ENTRY__KEY);
		createEReference(propertyDescriptorMapEntryEClass, PROPERTY_DESCRIPTOR_MAP_ENTRY__VALUE);

		propertyDescriptorMapEClass = createEClass(PROPERTY_DESCRIPTOR_MAP);
		createEReference(propertyDescriptorMapEClass, PROPERTY_DESCRIPTOR_MAP__DESCRIPTORS);

		driveR_TYPEEClass = createEClass(DRIVER_TYPE);

		driverTypeInfoEClass = createEClass(DRIVER_TYPE_INFO);
		createEAttribute(driverTypeInfoEClass, DRIVER_TYPE_INFO__DRIVER_TYPE_NAME);
		createEAttribute(driverTypeInfoEClass, DRIVER_TYPE_INFO__SHARED_RESOURCE_EXTENSION);

		httpChannelDriverConfigEClass = createEClass(HTTP_CHANNEL_DRIVER_CONFIG);
		createEReference(httpChannelDriverConfigEClass, HTTP_CHANNEL_DRIVER_CONFIG__WEB_APPLICATION_DESCRIPTORS);

		webApplicationDescriptorEClass = createEClass(WEB_APPLICATION_DESCRIPTOR);
		createEAttribute(webApplicationDescriptorEClass, WEB_APPLICATION_DESCRIPTOR__CONTEXT_URI);
		createEAttribute(webApplicationDescriptorEClass, WEB_APPLICATION_DESCRIPTOR__WEB_APP_SOURCE_PATH);

		// Create enums
		confiG_METHODEEnum = createEEnum(CONFIG_METHOD);
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
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		ElementPackage theElementPackage = (ElementPackage)EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		channelEClass.getESuperTypes().add(theModelPackage.getEntity());
		destinationEClass.getESuperTypes().add(theModelPackage.getEntity());
		destinationConceptEClass.getESuperTypes().add(theElementPackage.getConcept());
		destinationDescriptorEClass.getESuperTypes().add(this.getPropertyDescriptorMap());
		channelDescriptorEClass.getESuperTypes().add(this.getPropertyDescriptorMap());
		driverTypeInfoEClass.getESuperTypes().add(this.getDRIVER_TYPE());
		httpChannelDriverConfigEClass.getESuperTypes().add(this.getDriverConfig());

		// Initialize classes and features; add operations and parameters
		initEClass(channelEClass, Channel.class, "Channel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChannel_Driver(), this.getDriverConfig(), null, "driver", null, 0, 1, Channel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChannel_DriverManager(), this.getDriverManager(), null, "driverManager", null, 0, 1, Channel.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(destinationEClass, Destination.class, "Destination", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDestination_EventURI(), ecorePackage.getEString(), "eventURI", null, 0, 1, Destination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDestination_SerializerDeserializerClass(), ecorePackage.getEString(), "serializerDeserializerClass", null, 0, 1, Destination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDestination_Properties(), theModelPackage.getPropertyMap(), null, "properties", null, 0, 1, Destination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDestination_DriverConfig(), this.getDriverConfig(), null, "driverConfig", null, 0, 1, Destination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDestination_InputEnabled(), ecorePackage.getEBoolean(), "inputEnabled", null, 0, 1, Destination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDestination_OutputEnabled(), ecorePackage.getEBoolean(), "outputEnabled", null, 0, 1, Destination.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(driverConfigEClass, DriverConfig.class, "DriverConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDriverConfig_ConfigMethod(), this.getCONFIG_METHOD(), "configMethod", null, 0, 1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDriverConfig_Reference(), ecorePackage.getEString(), "reference", null, 0, 1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDriverConfig_Label(), ecorePackage.getEString(), "label", null, 0, 1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverConfig_Channel(), this.getChannel(), null, "channel", null, 0, 1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverConfig_Properties(), theModelPackage.getPropertyMap(), null, "properties", null, 0, 1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverConfig_Destinations(), this.getDestination(), null, "destinations", null, 0, -1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverConfig_ExtendedConfiguration(), this.getExtendedConfiguration(), null, "extendedConfiguration", null, 0, 1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverConfig_DriverType(), this.getDRIVER_TYPE(), null, "driverType", null, 0, 1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverConfig_Choice(), this.getChoice(), null, "choice", null, 0, 1, DriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(destinationConceptEClass, DestinationConcept.class, "DestinationConcept", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(driverManagerEClass, DriverManager.class, "DriverManager", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = addEOperation(driverManagerEClass, null, "registerDrivers", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEMap());
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(this.getDRIVER_TYPE());
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType(this.getDriverRegistration());
		g2.getETypeArguments().add(g3);
		addEParameter(op, g1, "resourceDriverMap", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, this.getChannelDescriptor(), "getChannelDescriptor", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, null, "getDriverTypes", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEEList());
		g2 = createEGenericType(this.getDRIVER_TYPE());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = addEOperation(driverManagerEClass, this.getDestinationDescriptor(), "getDestinationDescriptor", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, ecorePackage.getEString(), "getVersion", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, ecorePackage.getEString(), "getDescription", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, ecorePackage.getEString(), "getLabel", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, ecorePackage.getEString(), "getAllowedResourceTypes", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, this.getSerializerInfo(), "getDestinationsSerializerClasses", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, this.getSerializerInfo(), "getDefaultSerializer", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, this.getChoiceConfiguration(), "getPropertyConfigurations", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, this.getChoiceConfiguration(), "getExtendedConfigurations", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "driverType", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driverManagerEClass, this.getChoice(), "getExtendedConfigurationForChoice", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDRIVER_TYPE(), "DRIVER_TYPE", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(driverRegistrationEClass, DriverRegistration.class, "DriverRegistration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDriverRegistration_Label(), ecorePackage.getEString(), "label", null, 0, 1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDriverRegistration_Version(), ecorePackage.getEString(), "version", null, 0, 1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDriverRegistration_Description(), ecorePackage.getEString(), "description", null, 0, 1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverRegistration_ChannelDescriptor(), this.getChannelDescriptor(), null, "channelDescriptor", null, 0, 1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverRegistration_DestinationDescriptor(), this.getDestinationDescriptor(), null, "destinationDescriptor", null, 0, 1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDriverRegistration_ResourcesAllowed(), ecorePackage.getEString(), "resourcesAllowed", null, 0, -1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverRegistration_SerializerConfig(), this.getSerializerConfig(), null, "serializerConfig", null, 0, 1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverRegistration_ChoiceConfigurations(), this.getChoiceConfiguration(), null, "choiceConfigurations", null, 0, -1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverRegistration_ExtendedConfigurations(), this.getChoiceConfiguration(), null, "extendedConfigurations", null, 0, -1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverRegistration_DriverType(), this.getDRIVER_TYPE(), null, "driverType", null, 0, 1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDriverRegistration_Choice(), this.getChoice(), null, "choice", null, 0, -1, DriverRegistration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serializerConfigEClass, SerializerConfig.class, "SerializerConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSerializerConfig_UserDefined(), ecorePackage.getEBoolean(), "userDefined", null, 0, 1, SerializerConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSerializerConfig_Serializers(), this.getSerializerInfo(), null, "serializers", null, 0, -1, SerializerConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(serializerConfigEClass, this.getSerializerInfo(), "getDefaultSerializer", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(choiceConfigurationEClass, ChoiceConfiguration.class, "ChoiceConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChoiceConfiguration_PropertyName(), ecorePackage.getEString(), "propertyName", null, 0, 1, ChoiceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChoiceConfiguration_PropertyParent(), ecorePackage.getEString(), "propertyParent", null, 0, 1, ChoiceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChoiceConfiguration_ConfigType(), ecorePackage.getEString(), "configType", null, 0, 1, ChoiceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChoiceConfiguration_Choices(), this.getChoice(), null, "choices", null, 0, -1, ChoiceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChoiceConfiguration_DefaultValue(), ecorePackage.getEJavaObject(), "defaultValue", null, 0, 1, ChoiceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChoiceConfiguration_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, ChoiceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(choiceEClass, Choice.class, "Choice", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChoice_DisplayedValue(), ecorePackage.getEString(), "displayedValue", null, 0, 1, Choice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChoice_Value(), ecorePackage.getEString(), "value", null, 0, 1, Choice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChoice_ExtendedConfiguration(), this.getExtendedConfiguration(), null, "extendedConfiguration", null, 0, -1, Choice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serializerInfoEClass, SerializerInfo.class, "SerializerInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSerializerInfo_SerializerType(), ecorePackage.getEString(), "serializerType", null, 0, 1, SerializerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSerializerInfo_SerializerClass(), ecorePackage.getEString(), "serializerClass", null, 0, 1, SerializerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSerializerInfo_Default(), ecorePackage.getEBoolean(), "default", null, 0, 1, SerializerInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extendedConfigurationEClass, ExtendedConfiguration.class, "ExtendedConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExtendedConfiguration_Properties(), theModelPackage.getSimpleProperty(), null, "properties", null, 0, -1, ExtendedConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(destinationDescriptorEClass, DestinationDescriptor.class, "DestinationDescriptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(channelDescriptorEClass, ChannelDescriptor.class, "ChannelDescriptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChannelDescriptor_DestinationDescriptor(), this.getDestinationDescriptor(), null, "destinationDescriptor", null, 0, 1, ChannelDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyDescriptorEClass, PropertyDescriptor.class, "PropertyDescriptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyDescriptor_DefaultValue(), ecorePackage.getEString(), "defaultValue", null, 0, 1, PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyDescriptor_Name(), ecorePackage.getEString(), "name", null, 0, 1, PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyDescriptor_Pattern(), ecorePackage.getEString(), "pattern", null, 0, 1, PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyDescriptor_Type(), ecorePackage.getEString(), "type", null, 0, 1, PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyDescriptor_Mandatory(), ecorePackage.getEBoolean(), "mandatory", "false", 0, 1, PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyDescriptor_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyDescriptor_GvToggle(), ecorePackage.getEBoolean(), "gvToggle", "false", 0, 1, PropertyDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyDescriptorMapEntryEClass, PropertyDescriptorMapEntry.class, "PropertyDescriptorMapEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyDescriptorMapEntry_Key(), ecorePackage.getEString(), "key", null, 1, 1, PropertyDescriptorMapEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyDescriptorMapEntry_Value(), this.getPropertyDescriptor(), null, "value", null, 1, 1, PropertyDescriptorMapEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyDescriptorMapEClass, PropertyDescriptorMap.class, "PropertyDescriptorMap", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyDescriptorMap_Descriptors(), this.getPropertyDescriptorMapEntry(), null, "descriptors", null, 0, -1, PropertyDescriptorMap.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(driveR_TYPEEClass, com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE.class, "DRIVER_TYPE", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(driveR_TYPEEClass, ecorePackage.getEString(), "getName", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(driveR_TYPEEClass, ecorePackage.getEString(), "getSharedResourceExtension", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driveR_TYPEEClass, null, "setName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "driverTypeName", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(driveR_TYPEEClass, null, "setSharedResourceExtension", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "sharedResourceExtension", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(driverTypeInfoEClass, DriverTypeInfo.class, "DriverTypeInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDriverTypeInfo_DriverTypeName(), ecorePackage.getEString(), "driverTypeName", null, 0, 1, DriverTypeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDriverTypeInfo_SharedResourceExtension(), ecorePackage.getEString(), "sharedResourceExtension", null, 0, 1, DriverTypeInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(httpChannelDriverConfigEClass, HttpChannelDriverConfig.class, "HttpChannelDriverConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHttpChannelDriverConfig_WebApplicationDescriptors(), this.getWebApplicationDescriptor(), null, "webApplicationDescriptors", null, 0, -1, HttpChannelDriverConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(webApplicationDescriptorEClass, WebApplicationDescriptor.class, "WebApplicationDescriptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWebApplicationDescriptor_ContextURI(), ecorePackage.getEString(), "contextURI", null, 0, 1, WebApplicationDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWebApplicationDescriptor_WebAppSourcePath(), ecorePackage.getEString(), "webAppSourcePath", null, 0, 1, WebApplicationDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(confiG_METHODEEnum, com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD.class, "CONFIG_METHOD");
		addEEnumLiteral(confiG_METHODEEnum, com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD.PROPERTIES);
		addEEnumLiteral(confiG_METHODEEnum, com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD.REFERENCE);
	}

} //ChannelPackageImpl
