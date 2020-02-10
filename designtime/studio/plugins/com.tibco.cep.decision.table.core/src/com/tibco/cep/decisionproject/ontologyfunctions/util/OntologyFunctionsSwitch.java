/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage
 * @generated
 */
public class OntologyFunctionsSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OntologyFunctionsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFunctionsSwitch() {
		if (modelPackage == null) {
			modelPackage = OntologyFunctionsPackage.eINSTANCE;
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT: {
				OntologyFunctionsRoot ontologyFunctionsRoot = (OntologyFunctionsRoot)theEObject;
				T result = caseOntologyFunctionsRoot(ontologyFunctionsRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS: {
				OntologyFunctions ontologyFunctions = (OntologyFunctions)theEObject;
				T result = caseOntologyFunctions(ontologyFunctions);
				if (result == null) result = caseParentResource(ontologyFunctions);
				if (result == null) result = caseAbstractResource(ontologyFunctions);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyFunctionsPackage.ABSTRACT_RESOURCE: {
				AbstractResource abstractResource = (AbstractResource)theEObject;
				T result = caseAbstractResource(abstractResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyFunctionsPackage.CONCEPT: {
				Concept concept = (Concept)theEObject;
				T result = caseConcept(concept);
				if (result == null) result = caseAbstractResource(concept);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyFunctionsPackage.PARENT_RESOURCE: {
				ParentResource parentResource = (ParentResource)theEObject;
				T result = caseParentResource(parentResource);
				if (result == null) result = caseAbstractResource(parentResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyFunctionsPackage.FOLDER: {
				Folder folder = (Folder)theEObject;
				T result = caseFolder(folder);
				if (result == null) result = caseParentResource(folder);
				if (result == null) result = caseAbstractResource(folder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyFunctionsPackage.EVENT: {
				Event event = (Event)theEObject;
				T result = caseEvent(event);
				if (result == null) result = caseAbstractResource(event);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyFunctionsPackage.RULE_FUNCTION: {
				RuleFunction ruleFunction = (RuleFunction)theEObject;
				T result = caseRuleFunction(ruleFunction);
				if (result == null) result = caseAbstractResource(ruleFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OntologyFunctionsPackage.FUNCTION_LABEL: {
				FunctionLabel functionLabel = (FunctionLabel)theEObject;
				T result = caseFunctionLabel(functionLabel);
				if (result == null) result = caseAbstractResource(functionLabel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOntologyFunctionsRoot(OntologyFunctionsRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ontology Functions</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ontology Functions</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOntologyFunctions(OntologyFunctions object) {
		return null;
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
	 * Returns the result of interpreting the object as an instance of '<em>Function Label</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Label</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionLabel(FunctionLabel object) {
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

} //OntologyFunctionsSwitch
