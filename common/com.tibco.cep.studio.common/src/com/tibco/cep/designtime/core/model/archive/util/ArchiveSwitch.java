/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.util;

import com.tibco.cep.designtime.core.model.archive.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.designtime.core.model.archive.AdapterArchive;
import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BEArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.InputDestination;
import com.tibco.cep.designtime.core.model.archive.ProcessArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;

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
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage
 * @generated
 */
public class ArchiveSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ArchivePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchiveSwitch() {
		if (modelPackage == null) {
			modelPackage = ArchivePackage.eINSTANCE;
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
			case ArchivePackage.ARCHIVE_RESOURCE: {
				ArchiveResource archiveResource = (ArchiveResource)theEObject;
				T result = caseArchiveResource(archiveResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ArchivePackage.ENTERPRISE_ARCHIVE: {
				EnterpriseArchive enterpriseArchive = (EnterpriseArchive)theEObject;
				T result = caseEnterpriseArchive(enterpriseArchive);
				if (result == null) result = caseArchiveResource(enterpriseArchive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ArchivePackage.BE_ARCHIVE_RESOURCE: {
				BEArchiveResource beArchiveResource = (BEArchiveResource)theEObject;
				T result = caseBEArchiveResource(beArchiveResource);
				if (result == null) result = caseArchiveResource(beArchiveResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE: {
				BusinessEventsArchiveResource businessEventsArchiveResource = (BusinessEventsArchiveResource)theEObject;
				T result = caseBusinessEventsArchiveResource(businessEventsArchiveResource);
				if (result == null) result = caseBEArchiveResource(businessEventsArchiveResource);
				if (result == null) result = caseArchiveResource(businessEventsArchiveResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ArchivePackage.INPUT_DESTINATION: {
				InputDestination inputDestination = (InputDestination)theEObject;
				T result = caseInputDestination(inputDestination);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ArchivePackage.ADVANCED_ENTITY_SETTING: {
				AdvancedEntitySetting advancedEntitySetting = (AdvancedEntitySetting)theEObject;
				T result = caseAdvancedEntitySetting(advancedEntitySetting);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ArchivePackage.SHARED_ARCHIVE: {
				SharedArchive sharedArchive = (SharedArchive)theEObject;
				T result = caseSharedArchive(sharedArchive);
				if (result == null) result = caseArchiveResource(sharedArchive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ArchivePackage.PROCESS_ARCHIVE: {
				ProcessArchive processArchive = (ProcessArchive)theEObject;
				T result = caseProcessArchive(processArchive);
				if (result == null) result = caseArchiveResource(processArchive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ArchivePackage.ADAPTER_ARCHIVE: {
				AdapterArchive adapterArchive = (AdapterArchive)theEObject;
				T result = caseAdapterArchive(adapterArchive);
				if (result == null) result = caseArchiveResource(adapterArchive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArchiveResource(ArchiveResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enterprise Archive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enterprise Archive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnterpriseArchive(EnterpriseArchive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>BE Archive Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>BE Archive Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBEArchiveResource(BEArchiveResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Business Events Archive Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Business Events Archive Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBusinessEventsArchiveResource(BusinessEventsArchiveResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Input Destination</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Input Destination</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInputDestination(InputDestination object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Advanced Entity Setting</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Advanced Entity Setting</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdvancedEntitySetting(AdvancedEntitySetting object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Archive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Archive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSharedArchive(SharedArchive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Archive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Archive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProcessArchive(ProcessArchive object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter Archive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter Archive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterArchive(AdapterArchive object) {
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

} //ArchiveSwitch
