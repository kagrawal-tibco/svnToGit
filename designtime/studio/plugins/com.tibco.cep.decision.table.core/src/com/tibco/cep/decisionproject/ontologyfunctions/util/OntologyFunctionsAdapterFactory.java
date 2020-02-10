/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource;
import com.tibco.cep.decisionproject.ontologyfunctions.Concept;
import com.tibco.cep.decisionproject.ontologyfunctions.Event;
import com.tibco.cep.decisionproject.ontologyfunctions.Folder;
import com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot;
import com.tibco.cep.decisionproject.ontologyfunctions.ParentResource;
import com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage
 * @generated
 */
public class OntologyFunctionsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OntologyFunctionsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFunctionsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = OntologyFunctionsPackage.eINSTANCE;
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
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OntologyFunctionsSwitch<Adapter> modelSwitch =
		new OntologyFunctionsSwitch<Adapter>() {
			@Override
			public Adapter caseOntologyFunctionsRoot(OntologyFunctionsRoot object) {
				return createOntologyFunctionsRootAdapter();
			}
			@Override
			public Adapter caseOntologyFunctions(OntologyFunctions object) {
				return createOntologyFunctionsAdapter();
			}
			@Override
			public Adapter caseAbstractResource(AbstractResource object) {
				return createAbstractResourceAdapter();
			}
			@Override
			public Adapter caseConcept(Concept object) {
				return createConceptAdapter();
			}
			@Override
			public Adapter caseParentResource(ParentResource object) {
				return createParentResourceAdapter();
			}
			@Override
			public Adapter caseFolder(Folder object) {
				return createFolderAdapter();
			}
			@Override
			public Adapter caseEvent(Event object) {
				return createEventAdapter();
			}
			@Override
			public Adapter caseRuleFunction(RuleFunction object) {
				return createRuleFunctionAdapter();
			}
			@Override
			public Adapter caseFunctionLabel(FunctionLabel object) {
				return createFunctionLabelAdapter();
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
	 * @generated not
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		if (target != null && target instanceof EObject){
			return modelSwitch.doSwitch((EObject)target);
		}
		return null;
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot
	 * @generated
	 */
	public Adapter createOntologyFunctionsRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions <em>Ontology Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions
	 * @generated
	 */
	public Adapter createOntologyFunctionsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource <em>Abstract Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource
	 * @generated
	 */
	public Adapter createAbstractResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.Concept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Concept
	 * @generated
	 */
	public Adapter createConceptAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.ParentResource <em>Parent Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.ParentResource
	 * @generated
	 */
	public Adapter createParentResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Folder
	 * @generated
	 */
	public Adapter createFolderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.Event
	 * @generated
	 */
	public Adapter createEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction <em>Rule Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction
	 * @generated
	 */
	public Adapter createRuleFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel <em>Function Label</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel
	 * @generated
	 */
	public Adapter createFunctionLabelAdapter() {
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

} //OntologyFunctionsAdapterFactory
