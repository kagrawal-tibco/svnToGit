/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.util;

import com.tibco.cep.studio.tester.emf.model.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage
 * @generated
 */
public class ModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
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
	protected ModelSwitch<Adapter> modelSwitch =
		new ModelSwitch<Adapter>() {
			@Override
			public Adapter caseCasualObjectsType(CasualObjectsType object) {
				return createCasualObjectsTypeAdapter();
			}
			@Override
			public Adapter caseConceptType(ConceptType object) {
				return createConceptTypeAdapter();
			}
			@Override
			public Adapter caseEntityType(EntityType object) {
				return createEntityTypeAdapter();
			}
			@Override
			public Adapter caseEventType(EventType object) {
				return createEventTypeAdapter();
			}
			@Override
			public Adapter caseExecutionObjectType(ExecutionObjectType object) {
				return createExecutionObjectTypeAdapter();
			}
			@Override
			public Adapter caseInvocationObjectType(InvocationObjectType object) {
				return createInvocationObjectTypeAdapter();
			}
			@Override
			public Adapter caseNamespaceType(NamespaceType object) {
				return createNamespaceTypeAdapter();
			}
			@Override
			public Adapter caseOntologyObjectType(OntologyObjectType object) {
				return createOntologyObjectTypeAdapter();
			}
			@Override
			public Adapter caseOperationObjectType(OperationObjectType object) {
				return createOperationObjectTypeAdapter();
			}
			@Override
			public Adapter casePropertyAttrsType(PropertyAttrsType object) {
				return createPropertyAttrsTypeAdapter();
			}
			@Override
			public Adapter casePropertyModificationType(PropertyModificationType object) {
				return createPropertyModificationTypeAdapter();
			}
			@Override
			public Adapter casePropertyType(PropertyType object) {
				return createPropertyTypeAdapter();
			}
			@Override
			public Adapter caseReteObjectType(ReteObjectType object) {
				return createReteObjectTypeAdapter();
			}
			@Override
			public Adapter caseTesterResultsDatatype(TesterResultsDatatype object) {
				return createTesterResultsDatatypeAdapter();
			}
			@Override
			public Adapter caseTesterResultsOperation(TesterResultsOperation object) {
				return createTesterResultsOperationAdapter();
			}
			@Override
			public Adapter caseTesterResultsType(TesterResultsType object) {
				return createTesterResultsTypeAdapter();
			}
			@Override
			public Adapter caseTesterRoot(TesterRoot object) {
				return createTesterRootAdapter();
			}
			@Override
			public Adapter caseValueType(ValueType object) {
				return createValueTypeAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.CasualObjectsType <em>Casual Objects Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.CasualObjectsType
	 * @generated
	 */
	public Adapter createCasualObjectsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.ConceptType <em>Concept Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.ConceptType
	 * @generated
	 */
	public Adapter createConceptTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.EntityType <em>Entity Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.EntityType
	 * @generated
	 */
	public Adapter createEntityTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.EventType
	 * @generated
	 */
	public Adapter createEventTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.ExecutionObjectType <em>Execution Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.ExecutionObjectType
	 * @generated
	 */
	public Adapter createExecutionObjectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.InvocationObjectType <em>Invocation Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.InvocationObjectType
	 * @generated
	 */
	public Adapter createInvocationObjectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.NamespaceType <em>Namespace Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.NamespaceType
	 * @generated
	 */
	public Adapter createNamespaceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.OntologyObjectType <em>Ontology Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.OntologyObjectType
	 * @generated
	 */
	public Adapter createOntologyObjectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.OperationObjectType <em>Operation Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.OperationObjectType
	 * @generated
	 */
	public Adapter createOperationObjectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.PropertyAttrsType <em>Property Attrs Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyAttrsType
	 * @generated
	 */
	public Adapter createPropertyAttrsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.PropertyModificationType <em>Property Modification Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyModificationType
	 * @generated
	 */
	public Adapter createPropertyModificationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.PropertyType
	 * @generated
	 */
	public Adapter createPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType <em>Rete Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.ReteObjectType
	 * @generated
	 */
	public Adapter createReteObjectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype <em>Tester Results Datatype</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsDatatype
	 * @generated
	 */
	public Adapter createTesterResultsDatatypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsOperation <em>Tester Results Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsOperation
	 * @generated
	 */
	public Adapter createTesterResultsOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.TesterResultsType <em>Tester Results Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterResultsType
	 * @generated
	 */
	public Adapter createTesterResultsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot <em>Tester Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.TesterRoot
	 * @generated
	 */
	public Adapter createTesterRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.tester.emf.model.ValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.tester.emf.model.ValueType
	 * @generated
	 */
	public Adapter createValueTypeAdapter() {
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

} //ModelAdapterFactory
