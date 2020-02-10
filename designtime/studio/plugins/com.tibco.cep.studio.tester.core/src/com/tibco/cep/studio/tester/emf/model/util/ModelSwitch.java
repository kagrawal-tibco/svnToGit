/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.util;

import com.tibco.cep.studio.tester.emf.model.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.studio.tester.emf.model.CasualObjectsType;
import com.tibco.cep.studio.tester.emf.model.ConceptType;
import com.tibco.cep.studio.tester.emf.model.EntityType;
import com.tibco.cep.studio.tester.emf.model.EventType;
import com.tibco.cep.studio.tester.emf.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.emf.model.InvocationObjectType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.NamespaceType;
import com.tibco.cep.studio.tester.emf.model.PropertyAttrsType;
import com.tibco.cep.studio.tester.emf.model.PropertyModificationType;
import com.tibco.cep.studio.tester.emf.model.PropertyType;
import com.tibco.cep.studio.tester.emf.model.ReteObjectType;
import com.tibco.cep.studio.tester.emf.model.TesterResultsType;
import com.tibco.cep.studio.tester.emf.model.TesterRoot;
import com.tibco.cep.studio.tester.emf.model.ValueType;

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
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage
 * @generated
 */
public class ModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelSwitch() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
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
			case ModelPackage.CASUAL_OBJECTS_TYPE: {
				CasualObjectsType casualObjectsType = (CasualObjectsType)theEObject;
				T result = caseCasualObjectsType(casualObjectsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.CONCEPT_TYPE: {
				ConceptType conceptType = (ConceptType)theEObject;
				T result = caseConceptType(conceptType);
				if (result == null) result = caseEntityType(conceptType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.ENTITY_TYPE: {
				EntityType entityType = (EntityType)theEObject;
				T result = caseEntityType(entityType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.EVENT_TYPE: {
				EventType eventType = (EventType)theEObject;
				T result = caseEventType(eventType);
				if (result == null) result = caseEntityType(eventType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.EXECUTION_OBJECT_TYPE: {
				ExecutionObjectType executionObjectType = (ExecutionObjectType)theEObject;
				T result = caseExecutionObjectType(executionObjectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.INVOCATION_OBJECT_TYPE: {
				InvocationObjectType invocationObjectType = (InvocationObjectType)theEObject;
				T result = caseInvocationObjectType(invocationObjectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.NAMESPACE_TYPE: {
				NamespaceType namespaceType = (NamespaceType)theEObject;
				T result = caseNamespaceType(namespaceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.ONTOLOGY_OBJECT_TYPE: {
				OntologyObjectType ontologyObjectType = (OntologyObjectType)theEObject;
				T result = caseOntologyObjectType(ontologyObjectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.OPERATION_OBJECT_TYPE: {
				OperationObjectType operationObjectType = (OperationObjectType)theEObject;
				T result = caseOperationObjectType(operationObjectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PROPERTY_ATTRS_TYPE: {
				PropertyAttrsType propertyAttrsType = (PropertyAttrsType)theEObject;
				T result = casePropertyAttrsType(propertyAttrsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PROPERTY_MODIFICATION_TYPE: {
				PropertyModificationType propertyModificationType = (PropertyModificationType)theEObject;
				T result = casePropertyModificationType(propertyModificationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PROPERTY_TYPE: {
				PropertyType propertyType = (PropertyType)theEObject;
				T result = casePropertyType(propertyType);
				if (result == null) result = casePropertyAttrsType(propertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.RETE_OBJECT_TYPE: {
				ReteObjectType reteObjectType = (ReteObjectType)theEObject;
				T result = caseReteObjectType(reteObjectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TESTER_RESULTS_DATATYPE: {
				TesterResultsDatatype testerResultsDatatype = (TesterResultsDatatype)theEObject;
				T result = caseTesterResultsDatatype(testerResultsDatatype);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TESTER_RESULTS_OPERATION: {
				TesterResultsOperation testerResultsOperation = (TesterResultsOperation)theEObject;
				T result = caseTesterResultsOperation(testerResultsOperation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TESTER_RESULTS_TYPE: {
				TesterResultsType testerResultsType = (TesterResultsType)theEObject;
				T result = caseTesterResultsType(testerResultsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TESTER_ROOT: {
				TesterRoot testerRoot = (TesterRoot)theEObject;
				T result = caseTesterRoot(testerRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.VALUE_TYPE: {
				ValueType valueType = (ValueType)theEObject;
				T result = caseValueType(valueType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Casual Objects Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Casual Objects Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCasualObjectsType(CasualObjectsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Concept Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Concept Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConceptType(ConceptType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityType(EntityType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventType(EventType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Execution Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Execution Object Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExecutionObjectType(ExecutionObjectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Invocation Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Invocation Object Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInvocationObjectType(InvocationObjectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Namespace Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Namespace Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamespaceType(NamespaceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ontology Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ontology Object Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOntologyObjectType(OntologyObjectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operation Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation Object Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperationObjectType(OperationObjectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Attrs Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Attrs Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyAttrsType(PropertyAttrsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Modification Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Modification Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyModificationType(PropertyModificationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyType(PropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rete Object Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rete Object Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReteObjectType(ReteObjectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tester Results Datatype</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tester Results Datatype</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTesterResultsDatatype(TesterResultsDatatype object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tester Results Operation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tester Results Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTesterResultsOperation(TesterResultsOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tester Results Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tester Results Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTesterResultsType(TesterResultsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tester Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tester Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTesterRoot(TesterRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueType(ValueType object) {
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

} //ModelSwitch
