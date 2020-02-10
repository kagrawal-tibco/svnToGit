/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage
 * @generated
 */
public class PolicyAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PolicyPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PolicyPackage.eINSTANCE;
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
	protected PolicySwitch<Adapter> modelSwitch =
		new PolicySwitch<Adapter>() {
			@Override
			public Adapter caseConfigPropertyType(ConfigPropertyType object) {
				return createConfigPropertyTypeAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter casePolicyConfigType(PolicyConfigType object) {
				return createPolicyConfigTypeAdapter();
			}
			@Override
			public Adapter casePolicyTemplateType(PolicyTemplateType object) {
				return createPolicyTemplateTypeAdapter();
			}
			@Override
			public Adapter casePolicyType(PolicyType object) {
				return createPolicyTypeAdapter();
			}
			@Override
			public Adapter caseConfigPropertyValue(ConfigPropertyValue object) {
				return createConfigPropertyValueAdapter();
			}
			@Override
			public Adapter caseAuthnPolicy(AuthnPolicy object) {
				return createAuthnPolicyAdapter();
			}
			@Override
			public Adapter caseAuthnPolicyTemplate(AuthnPolicyTemplate object) {
				return createAuthnPolicyTemplateAdapter();
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
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType <em>Config Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType
	 * @generated
	 */
	public Adapter createConfigPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType <em>Config Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType
	 * @generated
	 */
	public Adapter createPolicyConfigTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType <em>Template Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType
	 * @generated
	 */
	public Adapter createPolicyTemplateTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyType
	 * @generated
	 */
	public Adapter createPolicyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue <em>Config Property Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue
	 * @generated
	 */
	public Adapter createConfigPropertyValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicy <em>Authn Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicy
	 * @generated
	 */
	public Adapter createAuthnPolicyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicyTemplate <em>Authn Policy Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicyTemplate
	 * @generated
	 */
	public Adapter createAuthnPolicyTemplateAdapter() {
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

} //PolicyAdapterFactory
