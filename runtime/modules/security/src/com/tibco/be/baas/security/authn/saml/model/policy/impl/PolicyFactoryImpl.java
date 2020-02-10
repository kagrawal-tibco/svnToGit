/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicy;
import com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicyTemplate;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue;
import com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType;
import com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyFactory;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PolicyFactoryImpl extends EFactoryImpl implements PolicyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PolicyFactory init() {
		try {
			PolicyFactory thePolicyFactory = (PolicyFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.tibco.com/be/baas/authn/PolicyTemplateSchema"); 
			if (thePolicyFactory != null) {
				return thePolicyFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PolicyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PolicyPackage.CONFIG_PROPERTY_TYPE: return createConfigPropertyType();
			case PolicyPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case PolicyPackage.POLICY_CONFIG_TYPE: return createPolicyConfigType();
			case PolicyPackage.POLICY_TYPE: return createPolicyType();
			case PolicyPackage.CONFIG_PROPERTY_VALUE: return createConfigPropertyValue();
			case PolicyPackage.AUTHN_POLICY: return createAuthnPolicy();
			case PolicyPackage.AUTHN_POLICY_TEMPLATE: return createAuthnPolicyTemplate();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case PolicyPackage.DATA_TYPE_TYPE:
				return createDataTypeTypeFromString(eDataType, initialValue);
			case PolicyPackage.DATA_TYPE_TYPE_OBJECT:
				return createDataTypeTypeObjectFromString(eDataType, initialValue);
			case PolicyPackage.NAME_TYPE:
				return createNameTypeFromString(eDataType, initialValue);
			case PolicyPackage.NAME_TYPE1:
				return createNameType1FromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case PolicyPackage.DATA_TYPE_TYPE:
				return convertDataTypeTypeToString(eDataType, instanceValue);
			case PolicyPackage.DATA_TYPE_TYPE_OBJECT:
				return convertDataTypeTypeObjectToString(eDataType, instanceValue);
			case PolicyPackage.NAME_TYPE:
				return convertNameTypeToString(eDataType, instanceValue);
			case PolicyPackage.NAME_TYPE1:
				return convertNameType1ToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigPropertyType createConfigPropertyType() {
		ConfigPropertyTypeImpl configPropertyType = new ConfigPropertyTypeImpl();
		return configPropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyConfigType createPolicyConfigType() {
		PolicyConfigTypeImpl policyConfigType = new PolicyConfigTypeImpl();
		return policyConfigType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyType createPolicyType() {
		PolicyTypeImpl policyType = new PolicyTypeImpl();
		return policyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigPropertyValue createConfigPropertyValue() {
		ConfigPropertyValueImpl configPropertyValue = new ConfigPropertyValueImpl();
		return configPropertyValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnPolicy createAuthnPolicy() {
		AuthnPolicyImpl authnPolicy = new AuthnPolicyImpl();
		return authnPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnPolicyTemplate createAuthnPolicyTemplate() {
		AuthnPolicyTemplateImpl authnPolicyTemplate = new AuthnPolicyTemplateImpl();
		return authnPolicyTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypeType createDataTypeTypeFromString(EDataType eDataType, String initialValue) {
		DataTypeType result = DataTypeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypeType createDataTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createDataTypeTypeFromString(PolicyPackage.Literals.DATA_TYPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDataTypeTypeToString(PolicyPackage.Literals.DATA_TYPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createNameTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.NAME, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNameTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.NAME, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createNameType1FromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.NAME, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNameType1ToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.NAME, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyPackage getPolicyPackage() {
		return (PolicyPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PolicyPackage getPackage() {
		return PolicyPackage.eINSTANCE;
	}

} //PolicyFactoryImpl
