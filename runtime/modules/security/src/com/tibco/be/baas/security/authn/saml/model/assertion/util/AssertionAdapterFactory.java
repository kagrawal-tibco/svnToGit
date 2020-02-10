/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.model.assertion.ActionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.StatementAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage
 * @generated
 */
public class AssertionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AssertionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AssertionPackage.eINSTANCE;
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
	protected AssertionSwitch<Adapter> modelSwitch =
		new AssertionSwitch<Adapter>() {
			@Override
			public Adapter caseActionType(ActionType object) {
				return createActionTypeAdapter();
			}
			@Override
			public Adapter caseAdviceType(AdviceType object) {
				return createAdviceTypeAdapter();
			}
			@Override
			public Adapter caseAssertionType(AssertionType object) {
				return createAssertionTypeAdapter();
			}
			@Override
			public Adapter caseAttributeStatementType(AttributeStatementType object) {
				return createAttributeStatementTypeAdapter();
			}
			@Override
			public Adapter caseAttributeType(AttributeType object) {
				return createAttributeTypeAdapter();
			}
			@Override
			public Adapter caseAudienceRestrictionType(AudienceRestrictionType object) {
				return createAudienceRestrictionTypeAdapter();
			}
			@Override
			public Adapter caseAuthnContextType(AuthnContextType object) {
				return createAuthnContextTypeAdapter();
			}
			@Override
			public Adapter caseAuthnStatementType(AuthnStatementType object) {
				return createAuthnStatementTypeAdapter();
			}
			@Override
			public Adapter caseAuthzDecisionStatementType(AuthzDecisionStatementType object) {
				return createAuthzDecisionStatementTypeAdapter();
			}
			@Override
			public Adapter caseBaseIDAbstractType(BaseIDAbstractType object) {
				return createBaseIDAbstractTypeAdapter();
			}
			@Override
			public Adapter caseConditionAbstractType(ConditionAbstractType object) {
				return createConditionAbstractTypeAdapter();
			}
			@Override
			public Adapter caseConditionsType(ConditionsType object) {
				return createConditionsTypeAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseEvidenceType(EvidenceType object) {
				return createEvidenceTypeAdapter();
			}
			@Override
			public Adapter caseNameIDType(NameIDType object) {
				return createNameIDTypeAdapter();
			}
			@Override
			public Adapter caseOneTimeUseType(OneTimeUseType object) {
				return createOneTimeUseTypeAdapter();
			}
			@Override
			public Adapter caseProxyRestrictionType(ProxyRestrictionType object) {
				return createProxyRestrictionTypeAdapter();
			}
			@Override
			public Adapter caseStatementAbstractType(StatementAbstractType object) {
				return createStatementAbstractTypeAdapter();
			}
			@Override
			public Adapter caseSubjectConfirmationDataType(SubjectConfirmationDataType object) {
				return createSubjectConfirmationDataTypeAdapter();
			}
			@Override
			public Adapter caseSubjectConfirmationType(SubjectConfirmationType object) {
				return createSubjectConfirmationTypeAdapter();
			}
			@Override
			public Adapter caseSubjectLocalityType(SubjectLocalityType object) {
				return createSubjectLocalityTypeAdapter();
			}
			@Override
			public Adapter caseSubjectType(SubjectType object) {
				return createSubjectTypeAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ActionType
	 * @generated
	 */
	public Adapter createActionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType <em>Advice Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType
	 * @generated
	 */
	public Adapter createAdviceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType
	 * @generated
	 */
	public Adapter createAssertionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType <em>Attribute Statement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType
	 * @generated
	 */
	public Adapter createAttributeStatementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType
	 * @generated
	 */
	public Adapter createAttributeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType <em>Audience Restriction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType
	 * @generated
	 */
	public Adapter createAudienceRestrictionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType <em>Authn Context Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType
	 * @generated
	 */
	public Adapter createAuthnContextTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType <em>Authn Statement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType
	 * @generated
	 */
	public Adapter createAuthnStatementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType <em>Authz Decision Statement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType
	 * @generated
	 */
	public Adapter createAuthzDecisionStatementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType <em>Base ID Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType
	 * @generated
	 */
	public Adapter createBaseIDAbstractTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionAbstractType <em>Condition Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionAbstractType
	 * @generated
	 */
	public Adapter createConditionAbstractTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType <em>Conditions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType
	 * @generated
	 */
	public Adapter createConditionsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType <em>Evidence Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType
	 * @generated
	 */
	public Adapter createEvidenceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType <em>Name ID Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType
	 * @generated
	 */
	public Adapter createNameIDTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType <em>One Time Use Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType
	 * @generated
	 */
	public Adapter createOneTimeUseTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType <em>Proxy Restriction Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType
	 * @generated
	 */
	public Adapter createProxyRestrictionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.StatementAbstractType <em>Statement Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.StatementAbstractType
	 * @generated
	 */
	public Adapter createStatementAbstractTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType <em>Subject Confirmation Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType
	 * @generated
	 */
	public Adapter createSubjectConfirmationDataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType <em>Subject Confirmation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType
	 * @generated
	 */
	public Adapter createSubjectConfirmationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType <em>Subject Locality Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType
	 * @generated
	 */
	public Adapter createSubjectLocalityTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType <em>Subject Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType
	 * @generated
	 */
	public Adapter createSubjectTypeAdapter() {
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

} //AssertionAdapterFactory
