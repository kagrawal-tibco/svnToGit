/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig.util;

import com.tibco.be.util.config.sharedresources.sharedjndiconfig.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

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
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage
 * @generated
 */
public class SharedjndiconfigSwitch<T> extends Switch<T> {
	/**
     * The cached model package
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected static SharedjndiconfigPackage modelPackage;

	/**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public SharedjndiconfigSwitch() {
        if (modelPackage == null)
        {
            modelPackage = SharedjndiconfigPackage.eINSTANCE;
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
        switch (classifierID)
        {
            case SharedjndiconfigPackage.BW_SHARED_RESOURCE:
            {
                BwSharedResource bwSharedResource = (BwSharedResource)theEObject;
                T result = caseBwSharedResource(bwSharedResource);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case SharedjndiconfigPackage.CONFIG:
            {
                Config config = (Config)theEObject;
                T result = caseConfig(config);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case SharedjndiconfigPackage.OPTIONAL_JNDI_PROPERTIES:
            {
                OptionalJndiProperties optionalJndiProperties = (OptionalJndiProperties)theEObject;
                T result = caseOptionalJndiProperties(optionalJndiProperties);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case SharedjndiconfigPackage.ROW:
            {
                Row row = (Row)theEObject;
                T result = caseRow(row);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case SharedjndiconfigPackage.SHARED_JNDI_CONFIG_ROOT:
            {
                SharedJndiConfigRoot sharedJndiConfigRoot = (SharedJndiConfigRoot)theEObject;
                T result = caseSharedJndiConfigRoot(sharedJndiConfigRoot);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Bw Shared Resource</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bw Shared Resource</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseBwSharedResource(BwSharedResource object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Config</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Config</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseConfig(Config object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Optional Jndi Properties</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Optional Jndi Properties</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseOptionalJndiProperties(OptionalJndiProperties object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Row</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Row</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseRow(Row object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Shared Jndi Config Root</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Shared Jndi Config Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseSharedJndiConfigRoot(SharedJndiConfigRoot object) {
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

} //SharedjndiconfigSwitch
