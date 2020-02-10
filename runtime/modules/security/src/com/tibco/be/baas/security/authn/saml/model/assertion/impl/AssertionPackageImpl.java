/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.be.baas.security.authn.saml.model.assertion.ActionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionFactory;
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
import com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType;
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
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.impl.MetadataPackageImpl;
import com.tibco.be.baas.security.authn.saml.model.namespace.NamespacePackage;
import com.tibco.be.baas.security.authn.saml.model.namespace.impl.NamespacePackageImpl;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;
import com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AssertionPackageImpl extends EPackageImpl implements AssertionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adviceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass assertionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeStatementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass audienceRestrictionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authnContextTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authnStatementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authzDecisionStatementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass baseIDAbstractTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionAbstractTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass evidenceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameIDTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass oneTimeUseTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass proxyRestrictionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statementAbstractTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subjectConfirmationDataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subjectConfirmationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subjectLocalityTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subjectTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum decisionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType decisionTypeObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AssertionPackageImpl() {
		super(eNS_URI, AssertionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AssertionPackage init() {
		if (isInited) return (AssertionPackage)EPackage.Registry.INSTANCE.getEPackage(AssertionPackage.eNS_URI);

		// Obtain or create and register package
		AssertionPackageImpl theAssertionPackage = (AssertionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof AssertionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new AssertionPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		MetadataPackageImpl theMetadataPackage = (MetadataPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(MetadataPackage.eNS_URI) instanceof MetadataPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(MetadataPackage.eNS_URI) : MetadataPackage.eINSTANCE);
		NamespacePackageImpl theNamespacePackage = (NamespacePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NamespacePackage.eNS_URI) instanceof NamespacePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NamespacePackage.eNS_URI) : NamespacePackage.eINSTANCE);
		PolicyPackageImpl thePolicyPackage = (PolicyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PolicyPackage.eNS_URI) instanceof PolicyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PolicyPackage.eNS_URI) : PolicyPackage.eINSTANCE);

		// Create package meta-data objects
		theAssertionPackage.createPackageContents();
		theMetadataPackage.createPackageContents();
		theNamespacePackage.createPackageContents();
		thePolicyPackage.createPackageContents();

		// Initialize created meta-data
		theAssertionPackage.initializePackageContents();
		theMetadataPackage.initializePackageContents();
		theNamespacePackage.initializePackageContents();
		thePolicyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAssertionPackage.freeze();

		return theAssertionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionType() {
		return actionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionType_Value() {
		return (EAttribute)actionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionType_Namespace() {
		return (EAttribute)actionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdviceType() {
		return adviceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdviceType_Group() {
		return (EAttribute)adviceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdviceType_AssertionIDRef() {
		return (EAttribute)adviceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdviceType_AssertionURIRef() {
		return (EAttribute)adviceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAdviceType_Assertion() {
		return (EReference)adviceTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdviceType_Any() {
		return (EAttribute)adviceTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssertionType() {
		return assertionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertionType_Issuer() {
		return (EReference)assertionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertionType_Subject() {
		return (EReference)assertionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertionType_Conditions() {
		return (EReference)assertionTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertionType_Advice() {
		return (EReference)assertionTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssertionType_Group() {
		return (EAttribute)assertionTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertionType_Statement() {
		return (EReference)assertionTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertionType_AuthnStatement() {
		return (EReference)assertionTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertionType_AuthzDecisionStatement() {
		return (EReference)assertionTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssertionType_AttributeStatement() {
		return (EReference)assertionTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssertionType_ID() {
		return (EAttribute)assertionTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssertionType_IssueInstant() {
		return (EAttribute)assertionTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssertionType_Version() {
		return (EAttribute)assertionTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeStatementType() {
		return attributeStatementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeStatementType_Group() {
		return (EAttribute)attributeStatementTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeStatementType_Attribute() {
		return (EReference)attributeStatementTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeType() {
		return attributeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeType_AttributeValue() {
		return (EReference)attributeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeType_FriendlyName() {
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeType_Name() {
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeType_NameFormat() {
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeType_AnyAttribute() {
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAudienceRestrictionType() {
		return audienceRestrictionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAudienceRestrictionType_Audience() {
		return (EAttribute)audienceRestrictionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthnContextType() {
		return authnContextTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnContextType_AuthnContextClassRef() {
		return (EAttribute)authnContextTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnContextType_AuthnContextDecl() {
		return (EReference)authnContextTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnContextType_AuthnContextDeclRef() {
		return (EAttribute)authnContextTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnContextType_AuthnContextDecl1() {
		return (EReference)authnContextTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnContextType_AuthnContextDeclRef1() {
		return (EAttribute)authnContextTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnContextType_AuthenticatingAuthority() {
		return (EAttribute)authnContextTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthnStatementType() {
		return authnStatementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnStatementType_SubjectLocality() {
		return (EReference)authnStatementTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnStatementType_AuthnContext() {
		return (EReference)authnStatementTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnStatementType_AuthnInstant() {
		return (EAttribute)authnStatementTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnStatementType_SessionIndex() {
		return (EAttribute)authnStatementTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnStatementType_SessionNotOnOrAfter() {
		return (EAttribute)authnStatementTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthzDecisionStatementType() {
		return authzDecisionStatementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthzDecisionStatementType_Action() {
		return (EReference)authzDecisionStatementTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthzDecisionStatementType_Evidence() {
		return (EReference)authzDecisionStatementTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthzDecisionStatementType_Decision() {
		return (EAttribute)authzDecisionStatementTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthzDecisionStatementType_Resource() {
		return (EAttribute)authzDecisionStatementTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBaseIDAbstractType() {
		return baseIDAbstractTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBaseIDAbstractType_NameQualifier() {
		return (EAttribute)baseIDAbstractTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBaseIDAbstractType_SPNameQualifier() {
		return (EAttribute)baseIDAbstractTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditionAbstractType() {
		return conditionAbstractTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditionsType() {
		return conditionsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionsType_Group() {
		return (EAttribute)conditionsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionsType_Condition() {
		return (EReference)conditionsTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionsType_AudienceRestriction() {
		return (EReference)conditionsTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionsType_OneTimeUse() {
		return (EReference)conditionsTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionsType_ProxyRestriction() {
		return (EReference)conditionsTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionsType_NotBefore() {
		return (EAttribute)conditionsTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionsType_NotOnOrAfter() {
		return (EAttribute)conditionsTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Action() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Advice() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Assertion() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_AssertionIDRef() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_AssertionURIRef() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Attribute() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AttributeStatement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AttributeValue() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Audience() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AudienceRestriction() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_AuthenticatingAuthority() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthnContext() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_AuthnContextClassRef() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthnContextDecl() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_AuthnContextDeclRef() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthnStatement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthzDecisionStatement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BaseID() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Condition() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Conditions() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Evidence() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Issuer() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_NameID() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_OneTimeUse() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ProxyRestriction() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Statement() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Subject() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SubjectConfirmation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SubjectConfirmationData() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SubjectLocality() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEvidenceType() {
		return evidenceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvidenceType_Group() {
		return (EAttribute)evidenceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvidenceType_AssertionIDRef() {
		return (EAttribute)evidenceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEvidenceType_AssertionURIRef() {
		return (EAttribute)evidenceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEvidenceType_Assertion() {
		return (EReference)evidenceTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNameIDType() {
		return nameIDTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameIDType_Value() {
		return (EAttribute)nameIDTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameIDType_Format() {
		return (EAttribute)nameIDTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameIDType_NameQualifier() {
		return (EAttribute)nameIDTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameIDType_SPNameQualifier() {
		return (EAttribute)nameIDTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameIDType_SPProvidedID() {
		return (EAttribute)nameIDTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOneTimeUseType() {
		return oneTimeUseTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProxyRestrictionType() {
		return proxyRestrictionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProxyRestrictionType_Audience() {
		return (EAttribute)proxyRestrictionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProxyRestrictionType_Count() {
		return (EAttribute)proxyRestrictionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatementAbstractType() {
		return statementAbstractTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubjectConfirmationDataType() {
		return subjectConfirmationDataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationDataType_Mixed() {
		return (EAttribute)subjectConfirmationDataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationDataType_Any() {
		return (EAttribute)subjectConfirmationDataTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationDataType_Address() {
		return (EAttribute)subjectConfirmationDataTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationDataType_InResponseTo() {
		return (EAttribute)subjectConfirmationDataTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationDataType_NotBefore() {
		return (EAttribute)subjectConfirmationDataTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationDataType_NotOnOrAfter() {
		return (EAttribute)subjectConfirmationDataTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationDataType_Recipient() {
		return (EAttribute)subjectConfirmationDataTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationDataType_AnyAttribute() {
		return (EAttribute)subjectConfirmationDataTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubjectConfirmationType() {
		return subjectConfirmationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubjectConfirmationType_BaseID() {
		return (EReference)subjectConfirmationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubjectConfirmationType_NameID() {
		return (EReference)subjectConfirmationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubjectConfirmationType_SubjectConfirmationData() {
		return (EReference)subjectConfirmationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectConfirmationType_Method() {
		return (EAttribute)subjectConfirmationTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubjectLocalityType() {
		return subjectLocalityTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectLocalityType_Address() {
		return (EAttribute)subjectLocalityTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubjectLocalityType_DNSName() {
		return (EAttribute)subjectLocalityTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubjectType() {
		return subjectTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubjectType_BaseID() {
		return (EReference)subjectTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubjectType_NameID() {
		return (EReference)subjectTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubjectType_SubjectConfirmation() {
		return (EReference)subjectTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubjectType_SubjectConfirmation1() {
		return (EReference)subjectTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDecisionType() {
		return decisionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDecisionTypeObject() {
		return decisionTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertionFactory getAssertionFactory() {
		return (AssertionFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		actionTypeEClass = createEClass(ACTION_TYPE);
		createEAttribute(actionTypeEClass, ACTION_TYPE__VALUE);
		createEAttribute(actionTypeEClass, ACTION_TYPE__NAMESPACE);

		adviceTypeEClass = createEClass(ADVICE_TYPE);
		createEAttribute(adviceTypeEClass, ADVICE_TYPE__GROUP);
		createEAttribute(adviceTypeEClass, ADVICE_TYPE__ASSERTION_ID_REF);
		createEAttribute(adviceTypeEClass, ADVICE_TYPE__ASSERTION_URI_REF);
		createEReference(adviceTypeEClass, ADVICE_TYPE__ASSERTION);
		createEAttribute(adviceTypeEClass, ADVICE_TYPE__ANY);

		assertionTypeEClass = createEClass(ASSERTION_TYPE);
		createEReference(assertionTypeEClass, ASSERTION_TYPE__ISSUER);
		createEReference(assertionTypeEClass, ASSERTION_TYPE__SUBJECT);
		createEReference(assertionTypeEClass, ASSERTION_TYPE__CONDITIONS);
		createEReference(assertionTypeEClass, ASSERTION_TYPE__ADVICE);
		createEAttribute(assertionTypeEClass, ASSERTION_TYPE__GROUP);
		createEReference(assertionTypeEClass, ASSERTION_TYPE__STATEMENT);
		createEReference(assertionTypeEClass, ASSERTION_TYPE__AUTHN_STATEMENT);
		createEReference(assertionTypeEClass, ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT);
		createEReference(assertionTypeEClass, ASSERTION_TYPE__ATTRIBUTE_STATEMENT);
		createEAttribute(assertionTypeEClass, ASSERTION_TYPE__ID);
		createEAttribute(assertionTypeEClass, ASSERTION_TYPE__ISSUE_INSTANT);
		createEAttribute(assertionTypeEClass, ASSERTION_TYPE__VERSION);

		attributeStatementTypeEClass = createEClass(ATTRIBUTE_STATEMENT_TYPE);
		createEAttribute(attributeStatementTypeEClass, ATTRIBUTE_STATEMENT_TYPE__GROUP);
		createEReference(attributeStatementTypeEClass, ATTRIBUTE_STATEMENT_TYPE__ATTRIBUTE);

		attributeTypeEClass = createEClass(ATTRIBUTE_TYPE);
		createEReference(attributeTypeEClass, ATTRIBUTE_TYPE__ATTRIBUTE_VALUE);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__FRIENDLY_NAME);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__NAME);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__NAME_FORMAT);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__ANY_ATTRIBUTE);

		audienceRestrictionTypeEClass = createEClass(AUDIENCE_RESTRICTION_TYPE);
		createEAttribute(audienceRestrictionTypeEClass, AUDIENCE_RESTRICTION_TYPE__AUDIENCE);

		authnContextTypeEClass = createEClass(AUTHN_CONTEXT_TYPE);
		createEAttribute(authnContextTypeEClass, AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF);
		createEReference(authnContextTypeEClass, AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL);
		createEAttribute(authnContextTypeEClass, AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF);
		createEReference(authnContextTypeEClass, AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1);
		createEAttribute(authnContextTypeEClass, AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF1);
		createEAttribute(authnContextTypeEClass, AUTHN_CONTEXT_TYPE__AUTHENTICATING_AUTHORITY);

		authnStatementTypeEClass = createEClass(AUTHN_STATEMENT_TYPE);
		createEReference(authnStatementTypeEClass, AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY);
		createEReference(authnStatementTypeEClass, AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT);
		createEAttribute(authnStatementTypeEClass, AUTHN_STATEMENT_TYPE__AUTHN_INSTANT);
		createEAttribute(authnStatementTypeEClass, AUTHN_STATEMENT_TYPE__SESSION_INDEX);
		createEAttribute(authnStatementTypeEClass, AUTHN_STATEMENT_TYPE__SESSION_NOT_ON_OR_AFTER);

		authzDecisionStatementTypeEClass = createEClass(AUTHZ_DECISION_STATEMENT_TYPE);
		createEReference(authzDecisionStatementTypeEClass, AUTHZ_DECISION_STATEMENT_TYPE__ACTION);
		createEReference(authzDecisionStatementTypeEClass, AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE);
		createEAttribute(authzDecisionStatementTypeEClass, AUTHZ_DECISION_STATEMENT_TYPE__DECISION);
		createEAttribute(authzDecisionStatementTypeEClass, AUTHZ_DECISION_STATEMENT_TYPE__RESOURCE);

		baseIDAbstractTypeEClass = createEClass(BASE_ID_ABSTRACT_TYPE);
		createEAttribute(baseIDAbstractTypeEClass, BASE_ID_ABSTRACT_TYPE__NAME_QUALIFIER);
		createEAttribute(baseIDAbstractTypeEClass, BASE_ID_ABSTRACT_TYPE__SP_NAME_QUALIFIER);

		conditionAbstractTypeEClass = createEClass(CONDITION_ABSTRACT_TYPE);

		conditionsTypeEClass = createEClass(CONDITIONS_TYPE);
		createEAttribute(conditionsTypeEClass, CONDITIONS_TYPE__GROUP);
		createEReference(conditionsTypeEClass, CONDITIONS_TYPE__CONDITION);
		createEReference(conditionsTypeEClass, CONDITIONS_TYPE__AUDIENCE_RESTRICTION);
		createEReference(conditionsTypeEClass, CONDITIONS_TYPE__ONE_TIME_USE);
		createEReference(conditionsTypeEClass, CONDITIONS_TYPE__PROXY_RESTRICTION);
		createEAttribute(conditionsTypeEClass, CONDITIONS_TYPE__NOT_BEFORE);
		createEAttribute(conditionsTypeEClass, CONDITIONS_TYPE__NOT_ON_OR_AFTER);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ACTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ADVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ASSERTION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ASSERTION_ID_REF);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ASSERTION_URI_REF);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE_STATEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE_VALUE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__AUDIENCE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUDIENCE_RESTRICTION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__AUTHENTICATING_AUTHORITY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_CONTEXT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__AUTHN_CONTEXT_CLASS_REF);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_CONTEXT_DECL);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__AUTHN_CONTEXT_DECL_REF);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_STATEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHZ_DECISION_STATEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BASE_ID);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CONDITION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CONDITIONS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EVIDENCE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ISSUER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__NAME_ID);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ONE_TIME_USE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PROXY_RESTRICTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__STATEMENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SUBJECT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SUBJECT_CONFIRMATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SUBJECT_CONFIRMATION_DATA);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SUBJECT_LOCALITY);

		evidenceTypeEClass = createEClass(EVIDENCE_TYPE);
		createEAttribute(evidenceTypeEClass, EVIDENCE_TYPE__GROUP);
		createEAttribute(evidenceTypeEClass, EVIDENCE_TYPE__ASSERTION_ID_REF);
		createEAttribute(evidenceTypeEClass, EVIDENCE_TYPE__ASSERTION_URI_REF);
		createEReference(evidenceTypeEClass, EVIDENCE_TYPE__ASSERTION);

		nameIDTypeEClass = createEClass(NAME_ID_TYPE);
		createEAttribute(nameIDTypeEClass, NAME_ID_TYPE__VALUE);
		createEAttribute(nameIDTypeEClass, NAME_ID_TYPE__FORMAT);
		createEAttribute(nameIDTypeEClass, NAME_ID_TYPE__NAME_QUALIFIER);
		createEAttribute(nameIDTypeEClass, NAME_ID_TYPE__SP_NAME_QUALIFIER);
		createEAttribute(nameIDTypeEClass, NAME_ID_TYPE__SP_PROVIDED_ID);

		oneTimeUseTypeEClass = createEClass(ONE_TIME_USE_TYPE);

		proxyRestrictionTypeEClass = createEClass(PROXY_RESTRICTION_TYPE);
		createEAttribute(proxyRestrictionTypeEClass, PROXY_RESTRICTION_TYPE__AUDIENCE);
		createEAttribute(proxyRestrictionTypeEClass, PROXY_RESTRICTION_TYPE__COUNT);

		statementAbstractTypeEClass = createEClass(STATEMENT_ABSTRACT_TYPE);

		subjectConfirmationDataTypeEClass = createEClass(SUBJECT_CONFIRMATION_DATA_TYPE);
		createEAttribute(subjectConfirmationDataTypeEClass, SUBJECT_CONFIRMATION_DATA_TYPE__MIXED);
		createEAttribute(subjectConfirmationDataTypeEClass, SUBJECT_CONFIRMATION_DATA_TYPE__ANY);
		createEAttribute(subjectConfirmationDataTypeEClass, SUBJECT_CONFIRMATION_DATA_TYPE__ADDRESS);
		createEAttribute(subjectConfirmationDataTypeEClass, SUBJECT_CONFIRMATION_DATA_TYPE__IN_RESPONSE_TO);
		createEAttribute(subjectConfirmationDataTypeEClass, SUBJECT_CONFIRMATION_DATA_TYPE__NOT_BEFORE);
		createEAttribute(subjectConfirmationDataTypeEClass, SUBJECT_CONFIRMATION_DATA_TYPE__NOT_ON_OR_AFTER);
		createEAttribute(subjectConfirmationDataTypeEClass, SUBJECT_CONFIRMATION_DATA_TYPE__RECIPIENT);
		createEAttribute(subjectConfirmationDataTypeEClass, SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE);

		subjectConfirmationTypeEClass = createEClass(SUBJECT_CONFIRMATION_TYPE);
		createEReference(subjectConfirmationTypeEClass, SUBJECT_CONFIRMATION_TYPE__BASE_ID);
		createEReference(subjectConfirmationTypeEClass, SUBJECT_CONFIRMATION_TYPE__NAME_ID);
		createEReference(subjectConfirmationTypeEClass, SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA);
		createEAttribute(subjectConfirmationTypeEClass, SUBJECT_CONFIRMATION_TYPE__METHOD);

		subjectLocalityTypeEClass = createEClass(SUBJECT_LOCALITY_TYPE);
		createEAttribute(subjectLocalityTypeEClass, SUBJECT_LOCALITY_TYPE__ADDRESS);
		createEAttribute(subjectLocalityTypeEClass, SUBJECT_LOCALITY_TYPE__DNS_NAME);

		subjectTypeEClass = createEClass(SUBJECT_TYPE);
		createEReference(subjectTypeEClass, SUBJECT_TYPE__BASE_ID);
		createEReference(subjectTypeEClass, SUBJECT_TYPE__NAME_ID);
		createEReference(subjectTypeEClass, SUBJECT_TYPE__SUBJECT_CONFIRMATION);
		createEReference(subjectTypeEClass, SUBJECT_TYPE__SUBJECT_CONFIRMATION1);

		// Create enums
		decisionTypeEEnum = createEEnum(DECISION_TYPE);

		// Create data types
		decisionTypeObjectEDataType = createEDataType(DECISION_TYPE_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		attributeStatementTypeEClass.getESuperTypes().add(this.getStatementAbstractType());
		audienceRestrictionTypeEClass.getESuperTypes().add(this.getConditionAbstractType());
		authnStatementTypeEClass.getESuperTypes().add(this.getStatementAbstractType());
		authzDecisionStatementTypeEClass.getESuperTypes().add(this.getStatementAbstractType());
		oneTimeUseTypeEClass.getESuperTypes().add(this.getConditionAbstractType());
		proxyRestrictionTypeEClass.getESuperTypes().add(this.getConditionAbstractType());

		// Initialize classes and features; add operations and parameters
		initEClass(actionTypeEClass, ActionType.class, "ActionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getActionType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, ActionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getActionType_Namespace(), theXMLTypePackage.getAnyURI(), "namespace", null, 1, 1, ActionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adviceTypeEClass, AdviceType.class, "AdviceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAdviceType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, AdviceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdviceType_AssertionIDRef(), theXMLTypePackage.getNCName(), "assertionIDRef", null, 0, -1, AdviceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdviceType_AssertionURIRef(), theXMLTypePackage.getAnyURI(), "assertionURIRef", null, 0, -1, AdviceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getAdviceType_Assertion(), this.getAssertionType(), null, "assertion", null, 0, -1, AdviceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdviceType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, AdviceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(assertionTypeEClass, AssertionType.class, "AssertionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssertionType_Issuer(), this.getNameIDType(), null, "issuer", null, 1, 1, AssertionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAssertionType_Subject(), this.getSubjectType(), null, "subject", null, 0, 1, AssertionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAssertionType_Conditions(), this.getConditionsType(), null, "conditions", null, 0, 1, AssertionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAssertionType_Advice(), this.getAdviceType(), null, "advice", null, 0, 1, AssertionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssertionType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, AssertionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAssertionType_Statement(), this.getStatementAbstractType(), null, "statement", null, 0, -1, AssertionType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getAssertionType_AuthnStatement(), this.getAuthnStatementType(), null, "authnStatement", null, 0, -1, AssertionType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getAssertionType_AuthzDecisionStatement(), this.getAuthzDecisionStatementType(), null, "authzDecisionStatement", null, 0, -1, AssertionType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getAssertionType_AttributeStatement(), this.getAttributeStatementType(), null, "attributeStatement", null, 0, -1, AssertionType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssertionType_ID(), theXMLTypePackage.getID(), "iD", null, 1, 1, AssertionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssertionType_IssueInstant(), theXMLTypePackage.getDateTime(), "issueInstant", null, 1, 1, AssertionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssertionType_Version(), theXMLTypePackage.getString(), "version", null, 1, 1, AssertionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeStatementTypeEClass, AttributeStatementType.class, "AttributeStatementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeStatementType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, AttributeStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeStatementType_Attribute(), this.getAttributeType(), null, "attribute", null, 0, -1, AttributeStatementType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(attributeTypeEClass, AttributeType.class, "AttributeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeType_AttributeValue(), ecorePackage.getEObject(), null, "attributeValue", null, 0, -1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeType_FriendlyName(), theXMLTypePackage.getString(), "friendlyName", null, 0, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeType_NameFormat(), theXMLTypePackage.getAnyURI(), "nameFormat", null, 0, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(audienceRestrictionTypeEClass, AudienceRestrictionType.class, "AudienceRestrictionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAudienceRestrictionType_Audience(), theXMLTypePackage.getAnyURI(), "audience", null, 1, -1, AudienceRestrictionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authnContextTypeEClass, AuthnContextType.class, "AuthnContextType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAuthnContextType_AuthnContextClassRef(), theXMLTypePackage.getAnyURI(), "authnContextClassRef", null, 0, 1, AuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthnContextType_AuthnContextDecl(), ecorePackage.getEObject(), null, "authnContextDecl", null, 0, 1, AuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnContextType_AuthnContextDeclRef(), theXMLTypePackage.getAnyURI(), "authnContextDeclRef", null, 0, 1, AuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthnContextType_AuthnContextDecl1(), ecorePackage.getEObject(), null, "authnContextDecl1", null, 0, 1, AuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnContextType_AuthnContextDeclRef1(), theXMLTypePackage.getAnyURI(), "authnContextDeclRef1", null, 0, 1, AuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnContextType_AuthenticatingAuthority(), theXMLTypePackage.getAnyURI(), "authenticatingAuthority", null, 0, -1, AuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authnStatementTypeEClass, AuthnStatementType.class, "AuthnStatementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAuthnStatementType_SubjectLocality(), this.getSubjectLocalityType(), null, "subjectLocality", null, 0, 1, AuthnStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthnStatementType_AuthnContext(), this.getAuthnContextType(), null, "authnContext", null, 1, 1, AuthnStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnStatementType_AuthnInstant(), theXMLTypePackage.getDateTime(), "authnInstant", null, 1, 1, AuthnStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnStatementType_SessionIndex(), theXMLTypePackage.getString(), "sessionIndex", null, 0, 1, AuthnStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnStatementType_SessionNotOnOrAfter(), theXMLTypePackage.getDateTime(), "sessionNotOnOrAfter", null, 0, 1, AuthnStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authzDecisionStatementTypeEClass, AuthzDecisionStatementType.class, "AuthzDecisionStatementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAuthzDecisionStatementType_Action(), this.getActionType(), null, "action", null, 1, -1, AuthzDecisionStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthzDecisionStatementType_Evidence(), this.getEvidenceType(), null, "evidence", null, 0, 1, AuthzDecisionStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthzDecisionStatementType_Decision(), this.getDecisionType(), "decision", null, 1, 1, AuthzDecisionStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthzDecisionStatementType_Resource(), theXMLTypePackage.getAnyURI(), "resource", null, 1, 1, AuthzDecisionStatementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(baseIDAbstractTypeEClass, BaseIDAbstractType.class, "BaseIDAbstractType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBaseIDAbstractType_NameQualifier(), theXMLTypePackage.getString(), "nameQualifier", null, 0, 1, BaseIDAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBaseIDAbstractType_SPNameQualifier(), theXMLTypePackage.getString(), "sPNameQualifier", null, 0, 1, BaseIDAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionAbstractTypeEClass, ConditionAbstractType.class, "ConditionAbstractType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(conditionsTypeEClass, ConditionsType.class, "ConditionsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConditionsType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, ConditionsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConditionsType_Condition(), this.getConditionAbstractType(), null, "condition", null, 0, -1, ConditionsType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConditionsType_AudienceRestriction(), this.getAudienceRestrictionType(), null, "audienceRestriction", null, 0, -1, ConditionsType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConditionsType_OneTimeUse(), this.getOneTimeUseType(), null, "oneTimeUse", null, 0, -1, ConditionsType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getConditionsType_ProxyRestriction(), this.getProxyRestrictionType(), null, "proxyRestriction", null, 0, -1, ConditionsType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getConditionsType_NotBefore(), theXMLTypePackage.getDateTime(), "notBefore", null, 0, 1, ConditionsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConditionsType_NotOnOrAfter(), theXMLTypePackage.getDateTime(), "notOnOrAfter", null, 0, 1, ConditionsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Action(), this.getActionType(), null, "action", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Advice(), this.getAdviceType(), null, "advice", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Assertion(), this.getAssertionType(), null, "assertion", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AssertionIDRef(), theXMLTypePackage.getNCName(), "assertionIDRef", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AssertionURIRef(), theXMLTypePackage.getAnyURI(), "assertionURIRef", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Attribute(), this.getAttributeType(), null, "attribute", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AttributeStatement(), this.getAttributeStatementType(), null, "attributeStatement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AttributeValue(), ecorePackage.getEObject(), null, "attributeValue", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Audience(), theXMLTypePackage.getAnyURI(), "audience", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AudienceRestriction(), this.getAudienceRestrictionType(), null, "audienceRestriction", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AuthenticatingAuthority(), theXMLTypePackage.getAnyURI(), "authenticatingAuthority", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnContext(), this.getAuthnContextType(), null, "authnContext", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AuthnContextClassRef(), theXMLTypePackage.getAnyURI(), "authnContextClassRef", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnContextDecl(), ecorePackage.getEObject(), null, "authnContextDecl", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AuthnContextDeclRef(), theXMLTypePackage.getAnyURI(), "authnContextDeclRef", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnStatement(), this.getAuthnStatementType(), null, "authnStatement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthzDecisionStatement(), this.getAuthzDecisionStatementType(), null, "authzDecisionStatement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BaseID(), this.getBaseIDAbstractType(), null, "baseID", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Condition(), this.getConditionAbstractType(), null, "condition", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Conditions(), this.getConditionsType(), null, "conditions", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Evidence(), this.getEvidenceType(), null, "evidence", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Issuer(), this.getNameIDType(), null, "issuer", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_NameID(), this.getNameIDType(), null, "nameID", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_OneTimeUse(), this.getOneTimeUseType(), null, "oneTimeUse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ProxyRestriction(), this.getProxyRestrictionType(), null, "proxyRestriction", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Statement(), this.getStatementAbstractType(), null, "statement", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Subject(), this.getSubjectType(), null, "subject", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SubjectConfirmation(), this.getSubjectConfirmationType(), null, "subjectConfirmation", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SubjectConfirmationData(), this.getSubjectConfirmationDataType(), null, "subjectConfirmationData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SubjectLocality(), this.getSubjectLocalityType(), null, "subjectLocality", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(evidenceTypeEClass, EvidenceType.class, "EvidenceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEvidenceType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, EvidenceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvidenceType_AssertionIDRef(), theXMLTypePackage.getNCName(), "assertionIDRef", null, 0, -1, EvidenceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getEvidenceType_AssertionURIRef(), theXMLTypePackage.getAnyURI(), "assertionURIRef", null, 0, -1, EvidenceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEvidenceType_Assertion(), this.getAssertionType(), null, "assertion", null, 0, -1, EvidenceType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(nameIDTypeEClass, NameIDType.class, "NameIDType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNameIDType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, NameIDType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNameIDType_Format(), theXMLTypePackage.getAnyURI(), "format", null, 0, 1, NameIDType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNameIDType_NameQualifier(), theXMLTypePackage.getString(), "nameQualifier", null, 0, 1, NameIDType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNameIDType_SPNameQualifier(), theXMLTypePackage.getString(), "sPNameQualifier", null, 0, 1, NameIDType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNameIDType_SPProvidedID(), theXMLTypePackage.getString(), "sPProvidedID", null, 0, 1, NameIDType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(oneTimeUseTypeEClass, OneTimeUseType.class, "OneTimeUseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(proxyRestrictionTypeEClass, ProxyRestrictionType.class, "ProxyRestrictionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProxyRestrictionType_Audience(), theXMLTypePackage.getAnyURI(), "audience", null, 0, -1, ProxyRestrictionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProxyRestrictionType_Count(), theXMLTypePackage.getNonNegativeInteger(), "count", null, 0, 1, ProxyRestrictionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statementAbstractTypeEClass, StatementAbstractType.class, "StatementAbstractType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(subjectConfirmationDataTypeEClass, SubjectConfirmationDataType.class, "SubjectConfirmationDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSubjectConfirmationDataType_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, SubjectConfirmationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectConfirmationDataType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, SubjectConfirmationDataType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectConfirmationDataType_Address(), theXMLTypePackage.getString(), "address", null, 0, 1, SubjectConfirmationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectConfirmationDataType_InResponseTo(), theXMLTypePackage.getNCName(), "inResponseTo", null, 0, 1, SubjectConfirmationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectConfirmationDataType_NotBefore(), theXMLTypePackage.getDateTime(), "notBefore", null, 0, 1, SubjectConfirmationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectConfirmationDataType_NotOnOrAfter(), theXMLTypePackage.getDateTime(), "notOnOrAfter", null, 0, 1, SubjectConfirmationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectConfirmationDataType_Recipient(), theXMLTypePackage.getAnyURI(), "recipient", null, 0, 1, SubjectConfirmationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectConfirmationDataType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, SubjectConfirmationDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subjectConfirmationTypeEClass, SubjectConfirmationType.class, "SubjectConfirmationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubjectConfirmationType_BaseID(), this.getBaseIDAbstractType(), null, "baseID", null, 0, 1, SubjectConfirmationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubjectConfirmationType_NameID(), this.getNameIDType(), null, "nameID", null, 0, 1, SubjectConfirmationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubjectConfirmationType_SubjectConfirmationData(), this.getSubjectConfirmationDataType(), null, "subjectConfirmationData", null, 0, 1, SubjectConfirmationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectConfirmationType_Method(), theXMLTypePackage.getAnyURI(), "method", null, 1, 1, SubjectConfirmationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subjectLocalityTypeEClass, SubjectLocalityType.class, "SubjectLocalityType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSubjectLocalityType_Address(), theXMLTypePackage.getString(), "address", null, 0, 1, SubjectLocalityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubjectLocalityType_DNSName(), theXMLTypePackage.getString(), "dNSName", null, 0, 1, SubjectLocalityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subjectTypeEClass, SubjectType.class, "SubjectType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubjectType_BaseID(), this.getBaseIDAbstractType(), null, "baseID", null, 0, 1, SubjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubjectType_NameID(), this.getNameIDType(), null, "nameID", null, 0, 1, SubjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubjectType_SubjectConfirmation(), this.getSubjectConfirmationType(), null, "subjectConfirmation", null, 0, -1, SubjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubjectType_SubjectConfirmation1(), this.getSubjectConfirmationType(), null, "subjectConfirmation1", null, 0, -1, SubjectType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(decisionTypeEEnum, DecisionType.class, "DecisionType");
		addEEnumLiteral(decisionTypeEEnum, DecisionType.PERMIT);
		addEEnumLiteral(decisionTypeEEnum, DecisionType.DENY);
		addEEnumLiteral(decisionTypeEEnum, DecisionType.INDETERMINATE);

		// Initialize data types
		initEDataType(decisionTypeObjectEDataType, DecisionType.class, "DecisionTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// null
		createNullAnnotations();
		// http://java.sun.com/xml/ns/jaxb
		createJaxbAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>null</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createNullAnnotations() {
		String source = null;			
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "appinfo", "\r\n\t    <jxb:schemaBindings xmlns:jxb=\"http://java.sun.com/xml/ns/jaxb\">\r\n\t      <jxb:package name=\"com.tibco.be.baas.security.authn.saml.model\">\r\n\t\t <jxb:javadoc>\r\n\t   <![CDATA[<body> Package level documentation for \r\n\tgenerated package com.tibco.be.baas.security.authn.saml.model</body>]]>\r\n\t\t </jxb:javadoc>\r\n\t      </jxb:package>\r\n\t    </jxb:schemaBindings>\r\n\t  "
		   });																																																																																																																																										
	}

	/**
	 * Initializes the annotations for <b>http://java.sun.com/xml/ns/jaxb</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createJaxbAnnotations() {
		String source = "http://java.sun.com/xml/ns/jaxb";				
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "version", "2.0"
		   });																																																																																																																																									
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";					
		addAnnotation
		  (actionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ActionType",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getActionType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getActionType_Namespace(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Namespace"
		   });		
		addAnnotation
		  (adviceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AdviceType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAdviceType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });		
		addAnnotation
		  (getAdviceType_AssertionIDRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRef",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getAdviceType_AssertionURIRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionURIRef",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getAdviceType_Assertion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Assertion",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getAdviceType_Any(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "wildcards", "##other",
			 "name", ":4",
			 "processing", "lax",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (assertionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AssertionType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAssertionType_Issuer(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Issuer",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAssertionType_Subject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Subject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAssertionType_Conditions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Conditions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAssertionType_Advice(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Advice",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAssertionType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:4"
		   });		
		addAnnotation
		  (getAssertionType_Statement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Statement",
			 "namespace", "##targetNamespace",
			 "group", "#group:4"
		   });		
		addAnnotation
		  (getAssertionType_AuthnStatement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnStatement",
			 "namespace", "##targetNamespace",
			 "group", "#group:4"
		   });		
		addAnnotation
		  (getAssertionType_AuthzDecisionStatement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthzDecisionStatement",
			 "namespace", "##targetNamespace",
			 "group", "#group:4"
		   });		
		addAnnotation
		  (getAssertionType_AttributeStatement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeStatement",
			 "namespace", "##targetNamespace",
			 "group", "#group:4"
		   });		
		addAnnotation
		  (getAssertionType_ID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ID"
		   });		
		addAnnotation
		  (getAssertionType_IssueInstant(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "IssueInstant"
		   });		
		addAnnotation
		  (getAssertionType_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Version"
		   });		
		addAnnotation
		  (attributeStatementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AttributeStatementType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAttributeStatementType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });		
		addAnnotation
		  (getAttributeStatementType_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Attribute",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (attributeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AttributeType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAttributeType_AttributeValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeValue",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAttributeType_FriendlyName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "FriendlyName"
		   });		
		addAnnotation
		  (getAttributeType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Name"
		   });		
		addAnnotation
		  (getAttributeType_NameFormat(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "NameFormat"
		   });		
		addAnnotation
		  (getAttributeType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":4",
			 "processing", "lax"
		   });		
		addAnnotation
		  (audienceRestrictionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AudienceRestrictionType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAudienceRestrictionType_Audience(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Audience",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (authnContextTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthnContextType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAuthnContextType_AuthnContextClassRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextClassRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnContextType_AuthnContextDecl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextDecl",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnContextType_AuthnContextDeclRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextDeclRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnContextType_AuthnContextDecl1(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextDecl",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnContextType_AuthnContextDeclRef1(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextDeclRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnContextType_AuthenticatingAuthority(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthenticatingAuthority",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (authnStatementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthnStatementType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAuthnStatementType_SubjectLocality(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SubjectLocality",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnStatementType_AuthnContext(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContext",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnStatementType_AuthnInstant(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "AuthnInstant"
		   });		
		addAnnotation
		  (getAuthnStatementType_SessionIndex(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "SessionIndex"
		   });		
		addAnnotation
		  (getAuthnStatementType_SessionNotOnOrAfter(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "SessionNotOnOrAfter"
		   });		
		addAnnotation
		  (authzDecisionStatementTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthzDecisionStatementType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAuthzDecisionStatementType_Action(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Action",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthzDecisionStatementType_Evidence(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Evidence",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthzDecisionStatementType_Decision(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Decision"
		   });		
		addAnnotation
		  (getAuthzDecisionStatementType_Resource(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Resource"
		   });		
		addAnnotation
		  (baseIDAbstractTypeEClass, 
		   source, 
		   new String[] {
			 "name", "BaseIDAbstractType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getBaseIDAbstractType_NameQualifier(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "NameQualifier"
		   });		
		addAnnotation
		  (getBaseIDAbstractType_SPNameQualifier(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "SPNameQualifier"
		   });		
		addAnnotation
		  (conditionAbstractTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ConditionAbstractType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (conditionsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ConditionsType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getConditionsType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });		
		addAnnotation
		  (getConditionsType_Condition(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Condition",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getConditionsType_AudienceRestriction(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AudienceRestriction",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getConditionsType_OneTimeUse(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OneTimeUse",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getConditionsType_ProxyRestriction(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ProxyRestriction",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getConditionsType_NotBefore(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "NotBefore"
		   });		
		addAnnotation
		  (getConditionsType_NotOnOrAfter(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "NotOnOrAfter"
		   });		
		addAnnotation
		  (decisionTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "DecisionType"
		   });		
		addAnnotation
		  (decisionTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "DecisionType:Object",
			 "baseType", "DecisionType"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getDocumentRoot_Action(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Action",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Advice(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Advice",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Assertion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Assertion",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AssertionIDRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AssertionURIRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionURIRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Attribute",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AttributeStatement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeStatement",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AttributeValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeValue",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Audience(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Audience",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AudienceRestriction(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AudienceRestriction",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthenticatingAuthority(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthenticatingAuthority",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnContext(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContext",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnContextClassRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextClassRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnContextDecl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextDecl",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnContextDeclRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextDeclRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnStatement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnStatement",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthzDecisionStatement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthzDecisionStatement",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_BaseID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BaseID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Condition(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Condition",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Conditions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Conditions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Evidence(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Evidence",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Issuer(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Issuer",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_NameID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_OneTimeUse(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OneTimeUse",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ProxyRestriction(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ProxyRestriction",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Statement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Statement",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Subject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Subject",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SubjectConfirmation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SubjectConfirmation",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SubjectConfirmationData(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SubjectConfirmationData",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SubjectLocality(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SubjectLocality",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (evidenceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "EvidenceType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getEvidenceType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });		
		addAnnotation
		  (getEvidenceType_AssertionIDRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRef",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getEvidenceType_AssertionURIRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionURIRef",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getEvidenceType_Assertion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Assertion",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (nameIDTypeEClass, 
		   source, 
		   new String[] {
			 "name", "NameIDType",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getNameIDType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getNameIDType_Format(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Format"
		   });		
		addAnnotation
		  (getNameIDType_NameQualifier(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "NameQualifier"
		   });		
		addAnnotation
		  (getNameIDType_SPNameQualifier(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "SPNameQualifier"
		   });		
		addAnnotation
		  (getNameIDType_SPProvidedID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "SPProvidedID"
		   });		
		addAnnotation
		  (oneTimeUseTypeEClass, 
		   source, 
		   new String[] {
			 "name", "OneTimeUseType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (proxyRestrictionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ProxyRestrictionType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getProxyRestrictionType_Audience(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Audience",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getProxyRestrictionType_Count(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Count"
		   });		
		addAnnotation
		  (statementAbstractTypeEClass, 
		   source, 
		   new String[] {
			 "name", "StatementAbstractType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (subjectConfirmationDataTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SubjectConfirmationDataType",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getSubjectConfirmationDataType_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getSubjectConfirmationDataType_Any(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "wildcards", "##any",
			 "name", ":1",
			 "processing", "lax"
		   });		
		addAnnotation
		  (getSubjectConfirmationDataType_Address(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Address"
		   });		
		addAnnotation
		  (getSubjectConfirmationDataType_InResponseTo(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "InResponseTo"
		   });		
		addAnnotation
		  (getSubjectConfirmationDataType_NotBefore(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "NotBefore"
		   });		
		addAnnotation
		  (getSubjectConfirmationDataType_NotOnOrAfter(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "NotOnOrAfter"
		   });		
		addAnnotation
		  (getSubjectConfirmationDataType_Recipient(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Recipient"
		   });		
		addAnnotation
		  (getSubjectConfirmationDataType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":7",
			 "processing", "lax"
		   });		
		addAnnotation
		  (subjectConfirmationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SubjectConfirmationType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSubjectConfirmationType_BaseID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BaseID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSubjectConfirmationType_NameID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSubjectConfirmationType_SubjectConfirmationData(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SubjectConfirmationData",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSubjectConfirmationType_Method(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Method"
		   });		
		addAnnotation
		  (subjectLocalityTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SubjectLocalityType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getSubjectLocalityType_Address(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Address"
		   });		
		addAnnotation
		  (getSubjectLocalityType_DNSName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "DNSName"
		   });		
		addAnnotation
		  (subjectTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SubjectType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSubjectType_BaseID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BaseID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSubjectType_NameID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSubjectType_SubjectConfirmation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SubjectConfirmation",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSubjectType_SubjectConfirmation1(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SubjectConfirmation",
			 "namespace", "##targetNamespace"
		   });
	}

} //AssertionPackageImpl
