/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Template Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTemplateTypeImpl#getPolicyConfig <em>Policy Config</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTemplateTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTemplateTypeImpl#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTemplateTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PolicyTemplateTypeImpl extends EObjectImpl implements PolicyTemplateType {
	/**
	 * The cached value of the '{@link #getPolicyConfig() <em>Policy Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPolicyConfig()
	 * @generated
	 * @ordered
	 */
	protected PolicyConfigType policyConfig;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolicyTemplateTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PolicyPackage.Literals.POLICY_TEMPLATE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyConfigType getPolicyConfig() {
		return policyConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPolicyConfig(PolicyConfigType newPolicyConfig, NotificationChain msgs) {
		PolicyConfigType oldPolicyConfig = policyConfig;
		policyConfig = newPolicyConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG, oldPolicyConfig, newPolicyConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPolicyConfig(PolicyConfigType newPolicyConfig) {
		if (newPolicyConfig != policyConfig) {
			NotificationChain msgs = null;
			if (policyConfig != null)
				msgs = ((InternalEObject)policyConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG, null, msgs);
			if (newPolicyConfig != null)
				msgs = ((InternalEObject)newPolicyConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG, null, msgs);
			msgs = basicSetPolicyConfig(newPolicyConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG, newPolicyConfig, newPolicyConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_TEMPLATE_TYPE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getID() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setID(String newID) {
		String oldID = id;
		id = newID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_TEMPLATE_TYPE__ID, oldID, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.POLICY_TEMPLATE_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG:
				return basicSetPolicyConfig(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG:
				return getPolicyConfig();
			case PolicyPackage.POLICY_TEMPLATE_TYPE__DESCRIPTION:
				return getDescription();
			case PolicyPackage.POLICY_TEMPLATE_TYPE__ID:
				return getID();
			case PolicyPackage.POLICY_TEMPLATE_TYPE__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG:
				setPolicyConfig((PolicyConfigType)newValue);
				return;
			case PolicyPackage.POLICY_TEMPLATE_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case PolicyPackage.POLICY_TEMPLATE_TYPE__ID:
				setID((String)newValue);
				return;
			case PolicyPackage.POLICY_TEMPLATE_TYPE__NAME:
				setName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG:
				setPolicyConfig((PolicyConfigType)null);
				return;
			case PolicyPackage.POLICY_TEMPLATE_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case PolicyPackage.POLICY_TEMPLATE_TYPE__ID:
				setID(ID_EDEFAULT);
				return;
			case PolicyPackage.POLICY_TEMPLATE_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PolicyPackage.POLICY_TEMPLATE_TYPE__POLICY_CONFIG:
				return policyConfig != null;
			case PolicyPackage.POLICY_TEMPLATE_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case PolicyPackage.POLICY_TEMPLATE_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case PolicyPackage.POLICY_TEMPLATE_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (description: ");
		result.append(description);
		result.append(", ID: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PolicyTemplateTypeImpl
