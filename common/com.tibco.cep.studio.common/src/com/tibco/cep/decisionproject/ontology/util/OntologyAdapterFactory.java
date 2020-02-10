/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.AccessControlCandidate;
import com.tibco.cep.decisionproject.ontology.Argument;
import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.decisionproject.ontology.Arguments;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.DTRule;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.Folder;
import com.tibco.cep.decisionproject.ontology.Header;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.Ontology;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.ParentResource;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.decisionproject.ontology.Rule;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.ontology.RuleSet;
import com.tibco.cep.decisionproject.ontology.RuleSetParticipant;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage
 * @generated
 */
public class OntologyAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OntologyPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = OntologyPackage.eINSTANCE;
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
	protected OntologySwitch<Adapter> modelSwitch =
		new OntologySwitch<Adapter>() {
			@Override
			public Adapter caseAbstractResource(AbstractResource object) {
				return createAbstractResourceAdapter();
			}
			@Override
			public Adapter caseFolder(Folder object) {
				return createFolderAdapter();
			}
			@Override
			public Adapter caseConcept(Concept object) {
				return createConceptAdapter();
			}
			@Override
			public Adapter caseEvent(Event object) {
				return createEventAdapter();
			}
			@Override
			public Adapter caseRuleSet(RuleSet object) {
				return createRuleSetAdapter();
			}
			@Override
			public Adapter caseRule(Rule object) {
				return createRuleAdapter();
			}
			@Override
			public Adapter caseRuleFunction(RuleFunction object) {
				return createRuleFunctionAdapter();
			}
			@Override
			public Adapter caseProperty(Property object) {
				return createPropertyAdapter();
			}
			@Override
			public Adapter caseParentResource(ParentResource object) {
				return createParentResourceAdapter();
			}
			@Override
			public Adapter caseOntology(Ontology object) {
				return createOntologyAdapter();
			}
			@Override
			public Adapter caseRuleSetParticipant(RuleSetParticipant object) {
				return createRuleSetParticipantAdapter();
			}
			@Override
			public Adapter caseDTRule(DTRule object) {
				return createDTRuleAdapter();
			}
			@Override
			public Adapter caseHeader(Header object) {
				return createHeaderAdapter();
			}
			@Override
			public Adapter caseArguments(Arguments object) {
				return createArgumentsAdapter();
			}
			@Override
			public Adapter caseImplementation(Implementation object) {
				return createImplementationAdapter();
			}
			@Override
			public Adapter caseArgument(Argument object) {
				return createArgumentAdapter();
			}
			@Override
			public Adapter caseAccessControlCandidate(AccessControlCandidate object) {
				return createAccessControlCandidateAdapter();
			}
			@Override
			public Adapter casePrimitiveHolder(PrimitiveHolder object) {
				return createPrimitiveHolderAdapter();
			}
			@Override
			public Adapter caseArgumentResource(ArgumentResource object) {
				return createArgumentResourceAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.AbstractResource <em>Abstract Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.AbstractResource
	 * @generated
	 */
	public Adapter createAbstractResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Folder
	 * @generated
	 */
	public Adapter createFolderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Concept <em>Concept</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Concept
	 * @generated
	 */
	public Adapter createConceptAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Event
	 * @generated
	 */
	public Adapter createEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.RuleSet <em>Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.RuleSet
	 * @generated
	 */
	public Adapter createRuleSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Rule
	 * @generated
	 */
	public Adapter createRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.RuleFunction <em>Rule Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.RuleFunction
	 * @generated
	 */
	public Adapter createRuleFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.ParentResource <em>Parent Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.ParentResource
	 * @generated
	 */
	public Adapter createParentResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Ontology <em>Ontology</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Ontology
	 * @generated
	 */
	public Adapter createOntologyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.RuleSetParticipant <em>Rule Set Participant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.RuleSetParticipant
	 * @generated
	 */
	public Adapter createRuleSetParticipantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.DTRule <em>DT Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.DTRule
	 * @generated
	 */
	public Adapter createDTRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Header <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Header
	 * @generated
	 */
	public Adapter createHeaderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Arguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Arguments
	 * @generated
	 */
	public Adapter createArgumentsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Implementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation
	 * @generated
	 */
	public Adapter createImplementationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Argument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Argument
	 * @generated
	 */
	public Adapter createArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate <em>Access Control Candidate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate
	 * @generated
	 */
	public Adapter createAccessControlCandidateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.PrimitiveHolder <em>Primitive Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.PrimitiveHolder
	 * @generated
	 */
	public Adapter createPrimitiveHolderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.ArgumentResource <em>Argument Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.ArgumentResource
	 * @generated
	 */
	public Adapter createArgumentResourceAdapter() {
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

} //OntologyAdapterFactory
