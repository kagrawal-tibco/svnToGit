/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

import com.tibco.cep.studio.tester.emf.model.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

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
import com.tibco.cep.studio.tester.emf.model.PropertyAttrsType;
import com.tibco.cep.studio.tester.emf.model.PropertyModificationType;
import com.tibco.cep.studio.tester.emf.model.PropertyType;
import com.tibco.cep.studio.tester.emf.model.ReteObjectType;
import com.tibco.cep.studio.tester.emf.model.TesterResultsType;
import com.tibco.cep.studio.tester.emf.model.TesterRoot;
import com.tibco.cep.studio.tester.emf.model.TypeType;
import com.tibco.cep.studio.tester.emf.model.ValueType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelFactoryImpl extends EFactoryImpl implements ModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ModelFactory init() {
		try {
			ModelFactory theModelFactory = (ModelFactory)EPackage.Registry.INSTANCE.getEFactory("www.tibco.com/be/studio/tester"); 
			if (theModelFactory != null) {
				return theModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactoryImpl() {
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
			case ModelPackage.CASUAL_OBJECTS_TYPE: return createCasualObjectsType();
			case ModelPackage.CONCEPT_TYPE: return createConceptType();
			case ModelPackage.ENTITY_TYPE: return createEntityType();
			case ModelPackage.EVENT_TYPE: return createEventType();
			case ModelPackage.EXECUTION_OBJECT_TYPE: return createExecutionObjectType();
			case ModelPackage.INVOCATION_OBJECT_TYPE: return createInvocationObjectType();
			case ModelPackage.NAMESPACE_TYPE: return createNamespaceType();
			case ModelPackage.ONTOLOGY_OBJECT_TYPE: return createOntologyObjectType();
			case ModelPackage.OPERATION_OBJECT_TYPE: return createOperationObjectType();
			case ModelPackage.PROPERTY_ATTRS_TYPE: return createPropertyAttrsType();
			case ModelPackage.PROPERTY_MODIFICATION_TYPE: return createPropertyModificationType();
			case ModelPackage.PROPERTY_TYPE: return createPropertyType();
			case ModelPackage.RETE_OBJECT_TYPE: return createReteObjectType();
			case ModelPackage.TESTER_RESULTS_DATATYPE: return createTesterResultsDatatype();
			case ModelPackage.TESTER_RESULTS_OPERATION: return createTesterResultsOperation();
			case ModelPackage.TESTER_RESULTS_TYPE: return createTesterResultsType();
			case ModelPackage.TESTER_ROOT: return createTesterRoot();
			case ModelPackage.VALUE_TYPE: return createValueType();
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
			case ModelPackage.CHANGE_TYPE_TYPE:
				return createChangeTypeTypeFromString(eDataType, initialValue);
			case ModelPackage.DATA_TYPE_TYPE:
				return createDataTypeTypeFromString(eDataType, initialValue);
			case ModelPackage.DATA_TYPE_TYPE1:
				return createDataTypeType1FromString(eDataType, initialValue);
			case ModelPackage.OPERATION_TYPE:
				return createOperationTypeFromString(eDataType, initialValue);
			case ModelPackage.TYPE_TYPE:
				return createTypeTypeFromString(eDataType, initialValue);
			case ModelPackage.CHANGE_TYPE_TYPE_OBJECT:
				return createChangeTypeTypeObjectFromString(eDataType, initialValue);
			case ModelPackage.DATA_TYPE_TYPE_OBJECT:
				return createDataTypeTypeObjectFromString(eDataType, initialValue);
			case ModelPackage.DATA_TYPE_TYPE_OBJECT1:
				return createDataTypeTypeObject1FromString(eDataType, initialValue);
			case ModelPackage.OPERATION_TYPE_OBJECT:
				return createOperationTypeObjectFromString(eDataType, initialValue);
			case ModelPackage.TYPE_TYPE_OBJECT:
				return createTypeTypeObjectFromString(eDataType, initialValue);
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
			case ModelPackage.CHANGE_TYPE_TYPE:
				return convertChangeTypeTypeToString(eDataType, instanceValue);
			case ModelPackage.DATA_TYPE_TYPE:
				return convertDataTypeTypeToString(eDataType, instanceValue);
			case ModelPackage.DATA_TYPE_TYPE1:
				return convertDataTypeType1ToString(eDataType, instanceValue);
			case ModelPackage.OPERATION_TYPE:
				return convertOperationTypeToString(eDataType, instanceValue);
			case ModelPackage.TYPE_TYPE:
				return convertTypeTypeToString(eDataType, instanceValue);
			case ModelPackage.CHANGE_TYPE_TYPE_OBJECT:
				return convertChangeTypeTypeObjectToString(eDataType, instanceValue);
			case ModelPackage.DATA_TYPE_TYPE_OBJECT:
				return convertDataTypeTypeObjectToString(eDataType, instanceValue);
			case ModelPackage.DATA_TYPE_TYPE_OBJECT1:
				return convertDataTypeTypeObject1ToString(eDataType, instanceValue);
			case ModelPackage.OPERATION_TYPE_OBJECT:
				return convertOperationTypeObjectToString(eDataType, instanceValue);
			case ModelPackage.TYPE_TYPE_OBJECT:
				return convertTypeTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CasualObjectsType createCasualObjectsType() {
		CasualObjectsTypeImpl casualObjectsType = new CasualObjectsTypeImpl();
		return casualObjectsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConceptType createConceptType() {
		ConceptTypeImpl conceptType = new ConceptTypeImpl();
		return conceptType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityType createEntityType() {
		EntityTypeImpl entityType = new EntityTypeImpl();
		return entityType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType createEventType() {
		EventTypeImpl eventType = new EventTypeImpl();
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionObjectType createExecutionObjectType() {
		ExecutionObjectTypeImpl executionObjectType = new ExecutionObjectTypeImpl();
		return executionObjectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InvocationObjectType createInvocationObjectType() {
		InvocationObjectTypeImpl invocationObjectType = new InvocationObjectTypeImpl();
		return invocationObjectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamespaceType createNamespaceType() {
		NamespaceTypeImpl namespaceType = new NamespaceTypeImpl();
		return namespaceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyObjectType createOntologyObjectType() {
		OntologyObjectTypeImpl ontologyObjectType = new OntologyObjectTypeImpl();
		return ontologyObjectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationObjectType createOperationObjectType() {
		OperationObjectTypeImpl operationObjectType = new OperationObjectTypeImpl();
		return operationObjectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyAttrsType createPropertyAttrsType() {
		PropertyAttrsTypeImpl propertyAttrsType = new PropertyAttrsTypeImpl();
		return propertyAttrsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyModificationType createPropertyModificationType() {
		PropertyModificationTypeImpl propertyModificationType = new PropertyModificationTypeImpl();
		return propertyModificationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType createPropertyType() {
		PropertyTypeImpl propertyType = new PropertyTypeImpl();
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReteObjectType createReteObjectType() {
		ReteObjectTypeImpl reteObjectType = new ReteObjectTypeImpl();
		return reteObjectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TesterResultsDatatype createTesterResultsDatatype() {
		TesterResultsDatatypeImpl testerResultsDatatype = new TesterResultsDatatypeImpl();
		return testerResultsDatatype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TesterResultsOperation createTesterResultsOperation() {
		TesterResultsOperationImpl testerResultsOperation = new TesterResultsOperationImpl();
		return testerResultsOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TesterResultsType createTesterResultsType() {
		TesterResultsTypeImpl testerResultsType = new TesterResultsTypeImpl();
		return testerResultsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TesterRoot createTesterRoot() {
		TesterRootImpl testerRoot = new TesterRootImpl();
		return testerRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueType createValueType() {
		ValueTypeImpl valueType = new ValueTypeImpl();
		return valueType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeTypeType createChangeTypeTypeFromString(EDataType eDataType, String initialValue) {
		ChangeTypeType result = ChangeTypeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertChangeTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypeType createDataTypeTypeFromString(EDataType eDataType, String initialValue) {
		DataTypeType result = DataTypeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypeType1 createDataTypeType1FromString(EDataType eDataType, String initialValue) {
		DataTypeType1 result = DataTypeType1.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeType1ToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationType createOperationTypeFromString(EDataType eDataType, String initialValue) {
		OperationType result = OperationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOperationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeType createTypeTypeFromString(EDataType eDataType, String initialValue) {
		TypeType result = TypeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeTypeType createChangeTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createChangeTypeTypeFromString(ModelPackage.Literals.CHANGE_TYPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertChangeTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertChangeTypeTypeToString(ModelPackage.Literals.CHANGE_TYPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypeType createDataTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createDataTypeTypeFromString(ModelPackage.Literals.DATA_TYPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDataTypeTypeToString(ModelPackage.Literals.DATA_TYPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypeType1 createDataTypeTypeObject1FromString(EDataType eDataType, String initialValue) {
		return createDataTypeType1FromString(ModelPackage.Literals.DATA_TYPE_TYPE1, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeTypeObject1ToString(EDataType eDataType, Object instanceValue) {
		return convertDataTypeType1ToString(ModelPackage.Literals.DATA_TYPE_TYPE1, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationType createOperationTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createOperationTypeFromString(ModelPackage.Literals.OPERATION_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOperationTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertOperationTypeToString(ModelPackage.Literals.OPERATION_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeType createTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createTypeTypeFromString(ModelPackage.Literals.TYPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTypeTypeToString(ModelPackage.Literals.TYPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelPackage getModelPackage() {
		return (ModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModelPackage getPackage() {
		return ModelPackage.eINSTANCE;
	}

} //ModelFactoryImpl
