/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicy;
import com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicyTemplate;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue;
import com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyType;

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
 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage
 * @generated
 */
public class PolicySwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PolicyPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicySwitch() {
		if (modelPackage == null) {
			modelPackage = PolicyPackage.eINSTANCE;
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
			case PolicyPackage.CONFIG_PROPERTY_TYPE: {
				ConfigPropertyType configPropertyType = (ConfigPropertyType)theEObject;
				T result = caseConfigPropertyType(configPropertyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PolicyPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PolicyPackage.POLICY_CONFIG_TYPE: {
				PolicyConfigType policyConfigType = (PolicyConfigType)theEObject;
				T result = casePolicyConfigType(policyConfigType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PolicyPackage.POLICY_TEMPLATE_TYPE: {
				PolicyTemplateType policyTemplateType = (PolicyTemplateType)theEObject;
				T result = casePolicyTemplateType(policyTemplateType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PolicyPackage.POLICY_TYPE: {
				PolicyType policyType = (PolicyType)theEObject;
				T result = casePolicyType(policyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PolicyPackage.CONFIG_PROPERTY_VALUE: {
				ConfigPropertyValue configPropertyValue = (ConfigPropertyValue)theEObject;
				T result = caseConfigPropertyValue(configPropertyValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PolicyPackage.AUTHN_POLICY: {
				AuthnPolicy authnPolicy = (AuthnPolicy)theEObject;
				T result = caseAuthnPolicy(authnPolicy);
				if (result == null) result = casePolicyTemplateType(authnPolicy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PolicyPackage.AUTHN_POLICY_TEMPLATE: {
				AuthnPolicyTemplate authnPolicyTemplate = (AuthnPolicyTemplate)theEObject;
				T result = caseAuthnPolicyTemplate(authnPolicyTemplate);
				if (result == null) result = casePolicyTemplateType(authnPolicyTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Config Property Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Config Property Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigPropertyType(ConfigPropertyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Config Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Config Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePolicyConfigType(PolicyConfigType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Template Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Template Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePolicyTemplateType(PolicyTemplateType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePolicyType(PolicyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Config Property Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Config Property Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigPropertyValue(ConfigPropertyValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authn Policy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authn Policy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthnPolicy(AuthnPolicy object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authn Policy Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authn Policy Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthnPolicyTemplate(AuthnPolicyTemplate object) {
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

} //PolicySwitch
