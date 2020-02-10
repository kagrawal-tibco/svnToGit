/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices.util;

import com.tibco.be.util.config.sharedresources.aemetaservices.*;

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
 * @see com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage
 * @generated
 */
public class AemetaservicesSwitch<T> extends Switch<T> {
	/**
     * The cached model package
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected static AemetaservicesPackage modelPackage;

	/**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public AemetaservicesSwitch() {
        if (modelPackage == null)
        {
            modelPackage = AemetaservicesPackage.eINSTANCE;
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
            case AemetaservicesPackage.AE_META_SERVICES_ROOT:
            {
                AeMetaServicesRoot aeMetaServicesRoot = (AeMetaServicesRoot)theEObject;
                T result = caseAeMetaServicesRoot(aeMetaServicesRoot);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AemetaservicesPackage.CERT_TYPE:
            {
                CertType certType = (CertType)theEObject;
                T result = caseCertType(certType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AemetaservicesPackage.IDENTITY_TYPE:
            {
                IdentityType identityType = (IdentityType)theEObject;
                T result = caseIdentityType(identityType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case AemetaservicesPackage.SSL:
            {
                Ssl ssl = (Ssl)theEObject;
                T result = caseSsl(ssl);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Ae Meta Services Root</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ae Meta Services Root</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseAeMetaServicesRoot(AeMetaServicesRoot object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Cert Type</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cert Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseCertType(CertType object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Identity Type</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Identity Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseIdentityType(IdentityType object) {
        return null;
    }

	/**
     * Returns the result of interpreting the object as an instance of '<em>Ssl</em>'.
     * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ssl</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
	public T caseSsl(Ssl object) {
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

} //AemetaservicesSwitch
