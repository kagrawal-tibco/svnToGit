/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.cep.studio.tester.emf.model.CasualObjectsType;
import com.tibco.cep.studio.tester.emf.model.ChangeTypeType;
import com.tibco.cep.studio.tester.emf.model.ConceptType;
import com.tibco.cep.studio.tester.emf.model.DataTypeType;
import com.tibco.cep.studio.tester.emf.model.DataTypeType1;
import com.tibco.cep.studio.tester.emf.model.EntityType;
import com.tibco.cep.studio.tester.emf.model.EventType;
import com.tibco.cep.studio.tester.emf.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.emf.model.InvocationObjectType;
import com.tibco.cep.studio.tester.emf.model.ModelFactory;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.NamespaceType;
import com.tibco.cep.studio.tester.emf.model.OntologyObjectType;
import com.tibco.cep.studio.tester.emf.model.OperationObjectType;
import com.tibco.cep.studio.tester.emf.model.OperationType;
import com.tibco.cep.studio.tester.emf.model.PropertyAttrsType;
import com.tibco.cep.studio.tester.emf.model.PropertyModificationType;
import com.tibco.cep.studio.tester.emf.model.PropertyType;
import com.tibco.cep.studio.tester.emf.model.ReteObjectType;
import com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype;
import com.tibco.cep.studio.tester.emf.model.TesterResultsOperation;
import com.tibco.cep.studio.tester.emf.model.TesterResultsType;
import com.tibco.cep.studio.tester.emf.model.TesterRoot;
import com.tibco.cep.studio.tester.emf.model.TypeType;
import com.tibco.cep.studio.tester.emf.model.ValueType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass casualObjectsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conceptTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executionObjectTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass invocationObjectTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namespaceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ontologyObjectTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationObjectTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyAttrsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyModificationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reteObjectTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testerResultsDatatypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testerResultsOperationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testerResultsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testerRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum changeTypeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dataTypeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dataTypeType1EEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum operationTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum typeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType changeTypeTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dataTypeTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dataTypeTypeObject1EDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType operationTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType typeTypeObjectEDataType = null;

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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCasualObjectsType() {
		return casualObjectsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCasualObjectsType_Concept() {
		return (EReference)casualObjectsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCasualObjectsType_Event() {
		return (EReference)casualObjectsTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConceptType() {
		return conceptTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConceptType_IsScorecard() {
		return (EAttribute)conceptTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntityType() {
		return entityTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityType_Property() {
		return (EReference)entityTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityType_ModifiedProperty() {
		return (EReference)entityTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityType_ExtId() {
		return (EAttribute)entityTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityType_Id() {
		return (EAttribute)entityTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityType_Name() {
		return (EAttribute)entityTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityType_Namespace() {
		return (EAttribute)entityTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventType() {
		return eventTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEventType_Payload() {
		return (EAttribute)eventTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutionObjectType() {
		return executionObjectTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionObjectType_ReteObject() {
		return (EReference)executionObjectTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionObjectType_InvocationObject() {
		return (EReference)executionObjectTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionObjectType_CasualObjects() {
		return (EReference)executionObjectTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInvocationObjectType() {
		return invocationObjectTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInvocationObjectType_Namespace() {
		return (EReference)invocationObjectTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInvocationObjectType_Type() {
		return (EAttribute)invocationObjectTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamespaceType() {
		return namespaceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamespaceType_Value() {
		return (EAttribute)namespaceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOntologyObjectType() {
		return ontologyObjectTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOntologyObjectType_OperationObject() {
		return (EReference)ontologyObjectTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOntologyObjectType_DataType() {
		return (EAttribute)ontologyObjectTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationObjectType() {
		return operationObjectTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationObjectType_ExecutionObject() {
		return (EReference)operationObjectTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationObjectType_Operation() {
		return (EAttribute)operationObjectTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyAttrsType() {
		return propertyAttrsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyAttrsType_Value() {
		return (EAttribute)propertyAttrsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyAttrsType_DataType() {
		return (EAttribute)propertyAttrsTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyAttrsType_Multiple() {
		return (EAttribute)propertyAttrsTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyAttrsType_Name() {
		return (EAttribute)propertyAttrsTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyModificationType() {
		return propertyModificationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyModificationType_OldValue() {
		return (EReference)propertyModificationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyModificationType_NewValue() {
		return (EReference)propertyModificationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyModificationType_DataType() {
		return (EAttribute)propertyModificationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyModificationType_Multiple() {
		return (EAttribute)propertyModificationTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyModificationType_Name() {
		return (EAttribute)propertyModificationTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyType() {
		return propertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReteObjectType() {
		return reteObjectTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReteObjectType_ChangeType() {
		return (EAttribute)reteObjectTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReteObjectType_Concept() {
		return (EReference)reteObjectTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReteObjectType_Event() {
		return (EReference)reteObjectTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTesterResultsDatatype() {
		return testerResultsDatatypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTesterResultsDatatype_RunName() {
		return (EAttribute)testerResultsDatatypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterResultsDatatype_OntologyObject() {
		return (EReference)testerResultsDatatypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTesterResultsDatatype_Project() {
		return (EAttribute)testerResultsDatatypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTesterResultsOperation() {
		return testerResultsOperationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTesterResultsOperation_RunName() {
		return (EAttribute)testerResultsOperationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterResultsOperation_OperationObject() {
		return (EReference)testerResultsOperationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTesterResultsOperation_Project() {
		return (EAttribute)testerResultsOperationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTesterResultsType() {
		return testerResultsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTesterResultsType_RunName() {
		return (EAttribute)testerResultsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterResultsType_ExecutionObject() {
		return (EReference)testerResultsTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTesterResultsType_Project() {
		return (EAttribute)testerResultsTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTesterRoot() {
		return testerRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTesterRoot_Mixed() {
		return (EAttribute)testerRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_XMLNSPrefixMap() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_XSISchemaLocation() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_Concept() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_Event() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_ExecutionObject() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_NewValue() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_OldValue() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_OperationObject() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_ReteObject() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTesterRoot_TesterResults() {
		return (EReference)testerRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueType() {
		return valueTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValueType_Value() {
		return (EAttribute)valueTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getChangeTypeType() {
		return changeTypeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDataTypeType() {
		return dataTypeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDataTypeType1() {
		return dataTypeType1EEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOperationType() {
		return operationTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTypeType() {
		return typeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getChangeTypeTypeObject() {
		return changeTypeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDataTypeTypeObject() {
		return dataTypeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDataTypeTypeObject1() {
		return dataTypeTypeObject1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getOperationTypeObject() {
		return operationTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getTypeTypeObject() {
		return typeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
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
		casualObjectsTypeEClass = createEClass(CASUAL_OBJECTS_TYPE);
		createEReference(casualObjectsTypeEClass, CASUAL_OBJECTS_TYPE__CONCEPT);
		createEReference(casualObjectsTypeEClass, CASUAL_OBJECTS_TYPE__EVENT);

		conceptTypeEClass = createEClass(CONCEPT_TYPE);
		createEAttribute(conceptTypeEClass, CONCEPT_TYPE__IS_SCORECARD);

		entityTypeEClass = createEClass(ENTITY_TYPE);
		createEReference(entityTypeEClass, ENTITY_TYPE__PROPERTY);
		createEReference(entityTypeEClass, ENTITY_TYPE__MODIFIED_PROPERTY);
		createEAttribute(entityTypeEClass, ENTITY_TYPE__EXT_ID);
		createEAttribute(entityTypeEClass, ENTITY_TYPE__ID);
		createEAttribute(entityTypeEClass, ENTITY_TYPE__NAME);
		createEAttribute(entityTypeEClass, ENTITY_TYPE__NAMESPACE);

		eventTypeEClass = createEClass(EVENT_TYPE);
		createEAttribute(eventTypeEClass, EVENT_TYPE__PAYLOAD);

		executionObjectTypeEClass = createEClass(EXECUTION_OBJECT_TYPE);
		createEReference(executionObjectTypeEClass, EXECUTION_OBJECT_TYPE__RETE_OBJECT);
		createEReference(executionObjectTypeEClass, EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT);
		createEReference(executionObjectTypeEClass, EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS);

		invocationObjectTypeEClass = createEClass(INVOCATION_OBJECT_TYPE);
		createEReference(invocationObjectTypeEClass, INVOCATION_OBJECT_TYPE__NAMESPACE);
		createEAttribute(invocationObjectTypeEClass, INVOCATION_OBJECT_TYPE__TYPE);

		namespaceTypeEClass = createEClass(NAMESPACE_TYPE);
		createEAttribute(namespaceTypeEClass, NAMESPACE_TYPE__VALUE);

		ontologyObjectTypeEClass = createEClass(ONTOLOGY_OBJECT_TYPE);
		createEReference(ontologyObjectTypeEClass, ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT);
		createEAttribute(ontologyObjectTypeEClass, ONTOLOGY_OBJECT_TYPE__DATA_TYPE);

		operationObjectTypeEClass = createEClass(OPERATION_OBJECT_TYPE);
		createEReference(operationObjectTypeEClass, OPERATION_OBJECT_TYPE__EXECUTION_OBJECT);
		createEAttribute(operationObjectTypeEClass, OPERATION_OBJECT_TYPE__OPERATION);

		propertyAttrsTypeEClass = createEClass(PROPERTY_ATTRS_TYPE);
		createEAttribute(propertyAttrsTypeEClass, PROPERTY_ATTRS_TYPE__VALUE);
		createEAttribute(propertyAttrsTypeEClass, PROPERTY_ATTRS_TYPE__DATA_TYPE);
		createEAttribute(propertyAttrsTypeEClass, PROPERTY_ATTRS_TYPE__MULTIPLE);
		createEAttribute(propertyAttrsTypeEClass, PROPERTY_ATTRS_TYPE__NAME);

		propertyModificationTypeEClass = createEClass(PROPERTY_MODIFICATION_TYPE);
		createEReference(propertyModificationTypeEClass, PROPERTY_MODIFICATION_TYPE__OLD_VALUE);
		createEReference(propertyModificationTypeEClass, PROPERTY_MODIFICATION_TYPE__NEW_VALUE);
		createEAttribute(propertyModificationTypeEClass, PROPERTY_MODIFICATION_TYPE__DATA_TYPE);
		createEAttribute(propertyModificationTypeEClass, PROPERTY_MODIFICATION_TYPE__MULTIPLE);
		createEAttribute(propertyModificationTypeEClass, PROPERTY_MODIFICATION_TYPE__NAME);

		propertyTypeEClass = createEClass(PROPERTY_TYPE);

		reteObjectTypeEClass = createEClass(RETE_OBJECT_TYPE);
		createEAttribute(reteObjectTypeEClass, RETE_OBJECT_TYPE__CHANGE_TYPE);
		createEReference(reteObjectTypeEClass, RETE_OBJECT_TYPE__CONCEPT);
		createEReference(reteObjectTypeEClass, RETE_OBJECT_TYPE__EVENT);

		testerResultsDatatypeEClass = createEClass(TESTER_RESULTS_DATATYPE);
		createEAttribute(testerResultsDatatypeEClass, TESTER_RESULTS_DATATYPE__RUN_NAME);
		createEReference(testerResultsDatatypeEClass, TESTER_RESULTS_DATATYPE__ONTOLOGY_OBJECT);
		createEAttribute(testerResultsDatatypeEClass, TESTER_RESULTS_DATATYPE__PROJECT);

		testerResultsOperationEClass = createEClass(TESTER_RESULTS_OPERATION);
		createEAttribute(testerResultsOperationEClass, TESTER_RESULTS_OPERATION__RUN_NAME);
		createEReference(testerResultsOperationEClass, TESTER_RESULTS_OPERATION__OPERATION_OBJECT);
		createEAttribute(testerResultsOperationEClass, TESTER_RESULTS_OPERATION__PROJECT);

		testerResultsTypeEClass = createEClass(TESTER_RESULTS_TYPE);
		createEAttribute(testerResultsTypeEClass, TESTER_RESULTS_TYPE__RUN_NAME);
		createEReference(testerResultsTypeEClass, TESTER_RESULTS_TYPE__EXECUTION_OBJECT);
		createEAttribute(testerResultsTypeEClass, TESTER_RESULTS_TYPE__PROJECT);

		testerRootEClass = createEClass(TESTER_ROOT);
		createEAttribute(testerRootEClass, TESTER_ROOT__MIXED);
		createEReference(testerRootEClass, TESTER_ROOT__XMLNS_PREFIX_MAP);
		createEReference(testerRootEClass, TESTER_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(testerRootEClass, TESTER_ROOT__CONCEPT);
		createEReference(testerRootEClass, TESTER_ROOT__EVENT);
		createEReference(testerRootEClass, TESTER_ROOT__EXECUTION_OBJECT);
		createEReference(testerRootEClass, TESTER_ROOT__NEW_VALUE);
		createEReference(testerRootEClass, TESTER_ROOT__OLD_VALUE);
		createEReference(testerRootEClass, TESTER_ROOT__OPERATION_OBJECT);
		createEReference(testerRootEClass, TESTER_ROOT__RETE_OBJECT);
		createEReference(testerRootEClass, TESTER_ROOT__TESTER_RESULTS);

		valueTypeEClass = createEClass(VALUE_TYPE);
		createEAttribute(valueTypeEClass, VALUE_TYPE__VALUE);

		// Create enums
		changeTypeTypeEEnum = createEEnum(CHANGE_TYPE_TYPE);
		dataTypeTypeEEnum = createEEnum(DATA_TYPE_TYPE);
		dataTypeType1EEnum = createEEnum(DATA_TYPE_TYPE1);
		operationTypeEEnum = createEEnum(OPERATION_TYPE);
		typeTypeEEnum = createEEnum(TYPE_TYPE);

		// Create data types
		changeTypeTypeObjectEDataType = createEDataType(CHANGE_TYPE_TYPE_OBJECT);
		dataTypeTypeObjectEDataType = createEDataType(DATA_TYPE_TYPE_OBJECT);
		dataTypeTypeObject1EDataType = createEDataType(DATA_TYPE_TYPE_OBJECT1);
		operationTypeObjectEDataType = createEDataType(OPERATION_TYPE_OBJECT);
		typeTypeObjectEDataType = createEDataType(TYPE_TYPE_OBJECT);
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
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		conceptTypeEClass.getESuperTypes().add(this.getEntityType());
		eventTypeEClass.getESuperTypes().add(this.getEntityType());
		propertyTypeEClass.getESuperTypes().add(this.getPropertyAttrsType());

		// Initialize classes and features; add operations and parameters
		initEClass(casualObjectsTypeEClass, CasualObjectsType.class, "CasualObjectsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCasualObjectsType_Concept(), this.getConceptType(), null, "concept", null, 0, -1, CasualObjectsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCasualObjectsType_Event(), this.getEventType(), null, "event", null, 0, -1, CasualObjectsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conceptTypeEClass, ConceptType.class, "ConceptType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConceptType_IsScorecard(), theXMLTypePackage.getBoolean(), "isScorecard", "false", 0, 1, ConceptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityTypeEClass, EntityType.class, "EntityType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityType_Property(), this.getPropertyType(), null, "property", null, 0, -1, EntityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityType_ModifiedProperty(), this.getPropertyModificationType(), null, "modifiedProperty", null, 0, -1, EntityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityType_ExtId(), theXMLTypePackage.getString(), "extId", null, 0, 1, EntityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityType_Id(), theXMLTypePackage.getLong(), "id", null, 0, 1, EntityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, EntityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityType_Namespace(), theXMLTypePackage.getString(), "namespace", null, 0, 1, EntityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventTypeEClass, EventType.class, "EventType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEventType_Payload(), theXMLTypePackage.getString(), "payload", null, 0, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(executionObjectTypeEClass, ExecutionObjectType.class, "ExecutionObjectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExecutionObjectType_ReteObject(), this.getReteObjectType(), null, "reteObject", null, 1, 1, ExecutionObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExecutionObjectType_InvocationObject(), this.getInvocationObjectType(), null, "invocationObject", null, 1, 1, ExecutionObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExecutionObjectType_CasualObjects(), this.getCasualObjectsType(), null, "casualObjects", null, 1, 1, ExecutionObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(invocationObjectTypeEClass, InvocationObjectType.class, "InvocationObjectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInvocationObjectType_Namespace(), this.getNamespaceType(), null, "namespace", null, 1, 1, InvocationObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInvocationObjectType_Type(), this.getTypeType(), "type", null, 1, 1, InvocationObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namespaceTypeEClass, NamespaceType.class, "NamespaceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamespaceType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, NamespaceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ontologyObjectTypeEClass, OntologyObjectType.class, "OntologyObjectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOntologyObjectType_OperationObject(), this.getOperationObjectType(), null, "operationObject", null, 0, -1, OntologyObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOntologyObjectType_DataType(), theXMLTypePackage.getString(), "dataType", null, 0, 1, OntologyObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationObjectTypeEClass, OperationObjectType.class, "OperationObjectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperationObjectType_ExecutionObject(), this.getExecutionObjectType(), null, "executionObject", null, 0, -1, OperationObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOperationObjectType_Operation(), this.getOperationType(), "operation", null, 0, 1, OperationObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyAttrsTypeEClass, PropertyAttrsType.class, "PropertyAttrsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyAttrsType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, PropertyAttrsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyAttrsType_DataType(), this.getDataTypeType1(), "dataType", null, 0, 1, PropertyAttrsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyAttrsType_Multiple(), theXMLTypePackage.getBoolean(), "multiple", "false", 0, 1, PropertyAttrsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyAttrsType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, PropertyAttrsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyModificationTypeEClass, PropertyModificationType.class, "PropertyModificationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyModificationType_OldValue(), this.getValueType(), null, "oldValue", null, 1, 1, PropertyModificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyModificationType_NewValue(), this.getValueType(), null, "newValue", null, 1, 1, PropertyModificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyModificationType_DataType(), this.getDataTypeType(), "dataType", null, 0, 1, PropertyModificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyModificationType_Multiple(), theXMLTypePackage.getBoolean(), "multiple", "false", 0, 1, PropertyModificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyModificationType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, PropertyModificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyTypeEClass, PropertyType.class, "PropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(reteObjectTypeEClass, ReteObjectType.class, "ReteObjectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReteObjectType_ChangeType(), this.getChangeTypeType(), "changeType", null, 1, 1, ReteObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReteObjectType_Concept(), this.getConceptType(), null, "concept", null, 0, 1, ReteObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReteObjectType_Event(), this.getEventType(), null, "event", null, 0, 1, ReteObjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testerResultsDatatypeEClass, TesterResultsDatatype.class, "TesterResultsDatatype", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTesterResultsDatatype_RunName(), theXMLTypePackage.getString(), "runName", null, 1, 1, TesterResultsDatatype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTesterResultsDatatype_OntologyObject(), this.getOntologyObjectType(), null, "ontologyObject", null, 0, -1, TesterResultsDatatype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTesterResultsDatatype_Project(), theXMLTypePackage.getString(), "project", null, 0, 1, TesterResultsDatatype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testerResultsOperationEClass, TesterResultsOperation.class, "TesterResultsOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTesterResultsOperation_RunName(), theXMLTypePackage.getString(), "runName", null, 1, 1, TesterResultsOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTesterResultsOperation_OperationObject(), this.getOperationObjectType(), null, "operationObject", null, 0, -1, TesterResultsOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTesterResultsOperation_Project(), theXMLTypePackage.getString(), "project", null, 0, 1, TesterResultsOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testerResultsTypeEClass, TesterResultsType.class, "TesterResultsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTesterResultsType_RunName(), theXMLTypePackage.getString(), "runName", null, 1, 1, TesterResultsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTesterResultsType_ExecutionObject(), this.getExecutionObjectType(), null, "executionObject", null, 0, -1, TesterResultsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTesterResultsType_Project(), theXMLTypePackage.getString(), "project", null, 0, 1, TesterResultsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testerRootEClass, TesterRoot.class, "TesterRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTesterRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_Concept(), this.getConceptType(), null, "concept", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_Event(), this.getEventType(), null, "event", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_ExecutionObject(), this.getExecutionObjectType(), null, "executionObject", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_NewValue(), this.getValueType(), null, "newValue", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_OldValue(), this.getValueType(), null, "oldValue", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_OperationObject(), this.getOperationObjectType(), null, "operationObject", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_ReteObject(), this.getReteObjectType(), null, "reteObject", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getTesterRoot_TesterResults(), this.getTesterResultsType(), null, "testerResults", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(valueTypeEClass, ValueType.class, "ValueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValueType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, ValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(changeTypeTypeEEnum, ChangeTypeType.class, "ChangeTypeType");
		addEEnumLiteral(changeTypeTypeEEnum, ChangeTypeType.ASSERT);
		addEEnumLiteral(changeTypeTypeEEnum, ChangeTypeType.MODIFY);
		addEEnumLiteral(changeTypeTypeEEnum, ChangeTypeType.RETRACT);

		initEEnum(dataTypeTypeEEnum, DataTypeType.class, "DataTypeType");
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.STRING);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.INTEGER);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.LONG);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.DOUBLE);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.BOOLEAN);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.DATETIME);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.CONTAINEDCONCEPT);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.CONCEPTREFERENCE);

		initEEnum(dataTypeType1EEnum, DataTypeType1.class, "DataTypeType1");
		addEEnumLiteral(dataTypeType1EEnum, DataTypeType1.STRING);
		addEEnumLiteral(dataTypeType1EEnum, DataTypeType1.INTEGER);
		addEEnumLiteral(dataTypeType1EEnum, DataTypeType1.LONG);
		addEEnumLiteral(dataTypeType1EEnum, DataTypeType1.DOUBLE);
		addEEnumLiteral(dataTypeType1EEnum, DataTypeType1.BOOLEAN);
		addEEnumLiteral(dataTypeType1EEnum, DataTypeType1.DATETIME);
		addEEnumLiteral(dataTypeType1EEnum, DataTypeType1.CONTAINEDCONCEPT);
		addEEnumLiteral(dataTypeType1EEnum, DataTypeType1.CONCEPTREFERENCE);

		initEEnum(operationTypeEEnum, OperationType.class, "OperationType");
		addEEnumLiteral(operationTypeEEnum, OperationType.CREATED);
		addEEnumLiteral(operationTypeEEnum, OperationType.MODIFIED);
		addEEnumLiteral(operationTypeEEnum, OperationType.DELETED);

		initEEnum(typeTypeEEnum, TypeType.class, "TypeType");
		addEEnumLiteral(typeTypeEEnum, TypeType.RULE);
		addEEnumLiteral(typeTypeEEnum, TypeType.RULEFUNCTION);

		// Initialize data types
		initEDataType(changeTypeTypeObjectEDataType, ChangeTypeType.class, "ChangeTypeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(dataTypeTypeObjectEDataType, DataTypeType.class, "DataTypeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(dataTypeTypeObject1EDataType, DataTypeType1.class, "DataTypeTypeObject1", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(operationTypeObjectEDataType, OperationType.class, "OperationTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(typeTypeObjectEDataType, TypeType.class, "TypeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.w3.org/XML/1998/namespace
		createNamespaceAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.w3.org/XML/1998/namespace</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createNamespaceAnnotations() {
		String source = "http://www.w3.org/XML/1998/namespace";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "lang", "en"
		   });																																																																																																				
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";			
		addAnnotation
		  (casualObjectsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "CausalObjectsType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getCasualObjectsType_Concept(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Concept",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getCasualObjectsType_Event(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Event",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (changeTypeTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "ChangeType_._type"
		   });		
		addAnnotation
		  (changeTypeTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "ChangeType_._type:Object",
			 "baseType", "ChangeType_._type"
		   });		
		addAnnotation
		  (conceptTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ConceptType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getConceptType_IsScorecard(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "isScorecard"
		   });		
		addAnnotation
		  (dataTypeTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "dataType_._type"
		   });		
		addAnnotation
		  (dataTypeType1EEnum, 
		   source, 
		   new String[] {
			 "name", "dataType_._1_._type"
		   });		
		addAnnotation
		  (dataTypeTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "dataType_._type:Object",
			 "baseType", "dataType_._type"
		   });		
		addAnnotation
		  (dataTypeTypeObject1EDataType, 
		   source, 
		   new String[] {
			 "name", "dataType_._1_._type:Object",
			 "baseType", "dataType_._1_._type"
		   });			
		addAnnotation
		  (entityTypeEClass, 
		   source, 
		   new String[] {
			 "name", "EntityType",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getEntityType_Property(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "property",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getEntityType_ModifiedProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "modifiedProperty",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getEntityType_ExtId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "extId"
		   });		
		addAnnotation
		  (getEntityType_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Id"
		   });		
		addAnnotation
		  (getEntityType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (getEntityType_Namespace(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "namespace"
		   });		
		addAnnotation
		  (eventTypeEClass, 
		   source, 
		   new String[] {
			 "name", "EventType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getEventType_Payload(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Payload",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (executionObjectTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ExecutionObjectType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getExecutionObjectType_ReteObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ReteObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getExecutionObjectType_InvocationObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "InvocationObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getExecutionObjectType_CasualObjects(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "CausalObjects",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (invocationObjectTypeEClass, 
		   source, 
		   new String[] {
			 "name", "InvocationObjectType",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getInvocationObjectType_Namespace(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Namespace",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getInvocationObjectType_Type(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Type",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (namespaceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "Namespace_._type",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getNamespaceType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (ontologyObjectTypeEClass, 
		   source, 
		   new String[] {
			 "name", "OntologyObjectType",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getOntologyObjectType_OperationObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OperationObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOntologyObjectType_DataType(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "dataType"
		   });		
		addAnnotation
		  (operationObjectTypeEClass, 
		   source, 
		   new String[] {
			 "name", "OperationObjectType",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getOperationObjectType_ExecutionObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ExecutionObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOperationObjectType_Operation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "operation"
		   });		
		addAnnotation
		  (operationTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "OperationType"
		   });		
		addAnnotation
		  (operationTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "OperationType:Object",
			 "baseType", "OperationType"
		   });			
		addAnnotation
		  (propertyAttrsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "PropertyAttrsType",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getPropertyAttrsType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getPropertyAttrsType_DataType(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "dataType"
		   });		
		addAnnotation
		  (getPropertyAttrsType_Multiple(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "multiple"
		   });		
		addAnnotation
		  (getPropertyAttrsType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });			
		addAnnotation
		  (propertyModificationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "PropertyModificationType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPropertyModificationType_OldValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "oldValue",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPropertyModificationType_NewValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "newValue",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPropertyModificationType_DataType(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "dataType"
		   });		
		addAnnotation
		  (getPropertyModificationType_Multiple(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "multiple"
		   });		
		addAnnotation
		  (getPropertyModificationType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (propertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "PropertyType",
			 "kind", "simple"
		   });			
		addAnnotation
		  (reteObjectTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ReteObjectType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getReteObjectType_ChangeType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ChangeType",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getReteObjectType_Concept(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Concept",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getReteObjectType_Event(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Event",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (testerResultsDatatypeEClass, 
		   source, 
		   new String[] {
			 "name", "TesterResultsDatatype",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getTesterResultsDatatype_RunName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RunName",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getTesterResultsDatatype_OntologyObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OntologyObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterResultsDatatype_Project(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "project",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (testerResultsOperationEClass, 
		   source, 
		   new String[] {
			 "name", "TesterResultsOperation",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getTesterResultsOperation_RunName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RunName",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getTesterResultsOperation_OperationObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OperationObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterResultsOperation_Project(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "project",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (testerResultsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "TesterResultsType",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getTesterResultsType_RunName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RunName",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (getTesterResultsType_ExecutionObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ExecutionObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterResultsType_Project(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "project",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (testerRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getTesterRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getTesterRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getTesterRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getTesterRoot_Concept(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Concept",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterRoot_Event(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Event",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterRoot_ExecutionObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ExecutionObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterRoot_NewValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "newValue",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterRoot_OldValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "oldValue",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterRoot_OperationObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OperationObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterRoot_ReteObject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ReteObject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTesterRoot_TesterResults(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TesterResults",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (typeTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "Type_._type"
		   });		
		addAnnotation
		  (typeTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "Type_._type:Object",
			 "baseType", "Type_._type"
		   });		
		addAnnotation
		  (valueTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ValueType",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getValueType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });
	}

} //ModelPackageImpl
