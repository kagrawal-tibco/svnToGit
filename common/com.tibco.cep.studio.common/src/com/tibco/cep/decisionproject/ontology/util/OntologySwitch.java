/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage
 * @generated
 */
public class OntologySwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OntologyPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologySwitch() {
		if (modelPackage == null) {
			modelPackage = OntologyPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case OntologyPackage.ABSTRACT_RESOURCE: {
				AbstractResource abstractResource = (AbstractResource)theEObject;
				T result = caseAbstractResource(abstractResource);
				if (result == null) result = caseAccessControlCandidate(abstractResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.FOLDER: {
				Folder folder = (Folder)theEObject;
				T result = caseFolder(folder);
				if (result == null) result = caseParentResource(folder);
				if (result == null) result = caseAbstractResource(folder);
				if (result == null) result = caseAccessControlCandidate(folder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.CONCEPT: {
				Concept concept = (Concept)theEObject;
				T result = caseConcept(concept);
				if (result == null) result = caseParentResource(concept);
				if (result == null) result = caseArgumentResource(concept);
				if (result == null) result = caseAbstractResource(concept);
				if (result == null) result = caseAccessControlCandidate(concept);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.EVENT: {
				Event event = (Event)theEObject;
				T result = caseEvent(event);
				if (result == null) result = caseParentResource(event);
				if (result == null) result = caseArgumentResource(event);
				if (result == null) result = caseAbstractResource(event);
				if (result == null) result = caseAccessControlCandidate(event);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.RULE_SET: {
				RuleSet ruleSet = (RuleSet)theEObject;
				T result = caseRuleSet(ruleSet);
				if (result == null) result = caseParentResource(ruleSet);
				if (result == null) result = caseAbstractResource(ruleSet);
				if (result == null) result = caseAccessControlCandidate(ruleSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.RULE: {
				Rule rule = (Rule)theEObject;
				T result = caseRule(rule);
				if (result == null) result = caseRuleSetParticipant(rule);
				if (result == null) result = caseAbstractResource(rule);
				if (result == null) result = caseAccessControlCandidate(rule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.RULE_FUNCTION: {
				RuleFunction ruleFunction = (RuleFunction)theEObject;
				T result = caseRuleFunction(ruleFunction);
				if (result == null) result = caseAbstractResource(ruleFunction);
				if (result == null) result = caseAccessControlCandidate(ruleFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.PROPERTY: {
				Property property = (Property)theEObject;
				T result = caseProperty(property);
				if (result == null) result = caseAbstractResource(property);
				if (result == null) result = caseAccessControlCandidate(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.PARENT_RESOURCE: {
				ParentResource parentResource = (ParentResource)theEObject;
				T result = caseParentResource(parentResource);
				if (result == null) result = caseAbstractResource(parentResource);
				if (result == null) result = caseAccessControlCandidate(parentResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.ONTOLOGY: {
				Ontology ontology = (Ontology)theEObject;
				T result = caseOntology(ontology);
				if (result == null) result = caseParentResource(ontology);
				if (result == null) result = caseAbstractResource(ontology);
				if (result == null) result = caseAccessControlCandidate(ontology);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.RULE_SET_PARTICIPANT: {
				RuleSetParticipant ruleSetParticipant = (RuleSetParticipant)theEObject;
				T result = caseRuleSetParticipant(ruleSetParticipant);
				if (result == null) result = caseAbstractResource(ruleSetParticipant);
				if (result == null) result = caseAccessControlCandidate(ruleSetParticipant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.DT_RULE: {
				DTRule dtRule = (DTRule)theEObject;
				T result = caseDTRule(dtRule);
				if (result == null) result = caseRuleSetParticipant(dtRule);
				if (result == null) result = caseAbstractResource(dtRule);
				if (result == null) result = caseAccessControlCandidate(dtRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.HEADER: {
				Header header = (Header)theEObject;
				T result = caseHeader(header);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.ARGUMENTS: {
				Arguments arguments = (Arguments)theEObject;
				T result = caseArguments(arguments);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.IMPLEMENTATION: {
				Implementation implementation = (Implementation)theEObject;
				T result = caseImplementation(implementation);
				if (result == null) result = caseAbstractResource(implementation);
				if (result == null) result = caseAccessControlCandidate(implementation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.ARGUMENT: {
				Argument argument = (Argument)theEObject;
				T result = caseArgument(argument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.ACCESS_CONTROL_CANDIDATE: {
				AccessControlCandidate accessControlCandidate = (AccessControlCandidate)theEObject;
				T result = caseAccessControlCandidate(accessControlCandidate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.PRIMITIVE_HOLDER: {
				PrimitiveHolder primitiveHolder = (PrimitiveHolder)theEObject;
				T result = casePrimitiveHolder(primitiveHolder);
				if (result == null) result = caseAbstractResource(primitiveHolder);
				if (result == null) result = caseArgumentResource(primitiveHolder);
				if (result == null) result = caseAccessControlCandidate(primitiveHolder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyPackage.ARGUMENT_RESOURCE: {
				ArgumentResource argumentResource = (ArgumentResource)theEObject;
				T result = caseArgumentResource(argumentResource);
				if (result == null) result = caseAbstractResource(argumentResource);
				if (result == null) result = caseAccessControlCandidate(argumentResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractResource(AbstractResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Folder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Folder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFolder(Folder object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Concept</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Concept</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConcept(Concept object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvent(Event object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleSet(RuleSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRule(Rule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleFunction(RuleFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parent Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parent Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParentResource(ParentResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ontology</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ontology</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOntology(Ontology object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Set Participant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Set Participant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleSetParticipant(RuleSetParticipant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DT Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DT Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDTRule(DTRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Header</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Header</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHeader(Header object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arguments</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arguments</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArguments(Arguments object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Implementation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Implementation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImplementation(Implementation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArgument(Argument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Access Control Candidate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Access Control Candidate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAccessControlCandidate(AccessControlCandidate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Primitive Holder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Primitive Holder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrimitiveHolder(PrimitiveHolder object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Argument Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Argument Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArgumentResource(ArgumentResource object) {
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
	public T defaultCase(EObject object) {
		return null;
	}

} //OntologySwitch
