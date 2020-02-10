/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search.util;

import com.tibco.cep.studio.core.index.model.search.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch;
import com.tibco.cep.studio.core.index.model.search.NonEntityMatch;
import com.tibco.cep.studio.core.index.model.search.RuleSourceMatch;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;
import com.tibco.cep.studio.core.index.model.search.StringLiteralMatch;

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
 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage
 * @generated
 */
public class SearchSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SearchPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchSwitch() {
		if (modelPackage == null) {
			modelPackage = SearchPackage.eINSTANCE;
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
			case SearchPackage.ELEMENT_MATCH: {
				ElementMatch elementMatch = (ElementMatch)theEObject;
				T result = caseElementMatch(elementMatch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SearchPackage.METHOD_ARGUMENT_MATCH: {
				MethodArgumentMatch methodArgumentMatch = (MethodArgumentMatch)theEObject;
				T result = caseMethodArgumentMatch(methodArgumentMatch);
				if (result == null) result = caseRuleSourceMatch(methodArgumentMatch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SearchPackage.STRING_LITERAL_MATCH: {
				StringLiteralMatch stringLiteralMatch = (StringLiteralMatch)theEObject;
				T result = caseStringLiteralMatch(stringLiteralMatch);
				if (result == null) result = caseRuleSourceMatch(stringLiteralMatch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SearchPackage.NON_ENTITY_MATCH: {
				NonEntityMatch nonEntityMatch = (NonEntityMatch)theEObject;
				T result = caseNonEntityMatch(nonEntityMatch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SearchPackage.RULE_SOURCE_MATCH: {
				RuleSourceMatch ruleSourceMatch = (RuleSourceMatch)theEObject;
				T result = caseRuleSourceMatch(ruleSourceMatch);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element Match</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element Match</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElementMatch(ElementMatch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Method Argument Match</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Method Argument Match</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMethodArgumentMatch(MethodArgumentMatch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Literal Match</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Literal Match</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringLiteralMatch(StringLiteralMatch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Non Entity Match</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Non Entity Match</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNonEntityMatch(NonEntityMatch object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Source Match</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Source Match</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleSourceMatch(RuleSourceMatch object) {
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

} //SearchSwitch
