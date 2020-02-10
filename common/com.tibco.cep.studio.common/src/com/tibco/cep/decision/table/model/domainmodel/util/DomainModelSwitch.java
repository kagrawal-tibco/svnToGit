/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage;
import com.tibco.cep.decision.table.model.domainmodel.EntryValue;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;
import com.tibco.cep.decision.table.model.domainmodel.SingleValue;
import com.tibco.cep.decisionproject.ontology.AccessControlCandidate;

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
 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage
 * @generated
 */
public class DomainModelSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DomainModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainModelSwitch() {
		if (modelPackage == null) {
			modelPackage = DomainModelPackage.eINSTANCE;
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
			case DomainModelPackage.DOMAIN_ENTRY: {
				DomainEntry domainEntry = (DomainEntry)theEObject;
				T result = caseDomainEntry(domainEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainModelPackage.RANGE_INFO: {
				RangeInfo rangeInfo = (RangeInfo)theEObject;
				T result = caseRangeInfo(rangeInfo);
				if (result == null) result = caseEntryValue(rangeInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainModelPackage.SINGLE_VALUE: {
				SingleValue singleValue = (SingleValue)theEObject;
				T result = caseSingleValue(singleValue);
				if (result == null) result = caseEntryValue(singleValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainModelPackage.ENTRY_VALUE: {
				EntryValue entryValue = (EntryValue)theEObject;
				T result = caseEntryValue(entryValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainModelPackage.DOMAIN: {
				Domain domain = (Domain)theEObject;
				T result = caseDomain(domain);
				if (result == null) result = caseAccessControlCandidate(domain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomainEntry(DomainEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Range Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Range Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRangeInfo(RangeInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Single Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Single Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSingleValue(SingleValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entry Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entry Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntryValue(EntryValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomain(Domain object) {
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

} //DomainModelSwitch
