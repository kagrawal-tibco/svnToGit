/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditions Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl#getAudienceRestriction <em>Audience Restriction</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl#getOneTimeUse <em>One Time Use</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl#getProxyRestriction <em>Proxy Restriction</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl#getNotBefore <em>Not Before</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.ConditionsTypeImpl#getNotOnOrAfter <em>Not On Or After</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConditionsTypeImpl extends EObjectImpl implements ConditionsType {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * The default value of the '{@link #getNotBefore() <em>Not Before</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotBefore()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar NOT_BEFORE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNotBefore() <em>Not Before</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotBefore()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar notBefore = NOT_BEFORE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNotOnOrAfter() <em>Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotOnOrAfter()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar NOT_ON_OR_AFTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNotOnOrAfter() <em>Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotOnOrAfter()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar notOnOrAfter = NOT_ON_OR_AFTER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.CONDITIONS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, AssertionPackage.CONDITIONS_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConditionAbstractType> getCondition() {
		return getGroup().list(AssertionPackage.Literals.CONDITIONS_TYPE__CONDITION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AudienceRestrictionType> getAudienceRestriction() {
		return getGroup().list(AssertionPackage.Literals.CONDITIONS_TYPE__AUDIENCE_RESTRICTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OneTimeUseType> getOneTimeUse() {
		return getGroup().list(AssertionPackage.Literals.CONDITIONS_TYPE__ONE_TIME_USE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProxyRestrictionType> getProxyRestriction() {
		return getGroup().list(AssertionPackage.Literals.CONDITIONS_TYPE__PROXY_RESTRICTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getNotBefore() {
		return notBefore;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotBefore(XMLGregorianCalendar newNotBefore) {
		XMLGregorianCalendar oldNotBefore = notBefore;
		notBefore = newNotBefore;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.CONDITIONS_TYPE__NOT_BEFORE, oldNotBefore, notBefore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getNotOnOrAfter() {
		return notOnOrAfter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotOnOrAfter(XMLGregorianCalendar newNotOnOrAfter) {
		XMLGregorianCalendar oldNotOnOrAfter = notOnOrAfter;
		notOnOrAfter = newNotOnOrAfter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.CONDITIONS_TYPE__NOT_ON_OR_AFTER, oldNotOnOrAfter, notOnOrAfter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.CONDITIONS_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case AssertionPackage.CONDITIONS_TYPE__CONDITION:
				return ((InternalEList<?>)getCondition()).basicRemove(otherEnd, msgs);
			case AssertionPackage.CONDITIONS_TYPE__AUDIENCE_RESTRICTION:
				return ((InternalEList<?>)getAudienceRestriction()).basicRemove(otherEnd, msgs);
			case AssertionPackage.CONDITIONS_TYPE__ONE_TIME_USE:
				return ((InternalEList<?>)getOneTimeUse()).basicRemove(otherEnd, msgs);
			case AssertionPackage.CONDITIONS_TYPE__PROXY_RESTRICTION:
				return ((InternalEList<?>)getProxyRestriction()).basicRemove(otherEnd, msgs);
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
			case AssertionPackage.CONDITIONS_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case AssertionPackage.CONDITIONS_TYPE__CONDITION:
				return getCondition();
			case AssertionPackage.CONDITIONS_TYPE__AUDIENCE_RESTRICTION:
				return getAudienceRestriction();
			case AssertionPackage.CONDITIONS_TYPE__ONE_TIME_USE:
				return getOneTimeUse();
			case AssertionPackage.CONDITIONS_TYPE__PROXY_RESTRICTION:
				return getProxyRestriction();
			case AssertionPackage.CONDITIONS_TYPE__NOT_BEFORE:
				return getNotBefore();
			case AssertionPackage.CONDITIONS_TYPE__NOT_ON_OR_AFTER:
				return getNotOnOrAfter();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AssertionPackage.CONDITIONS_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case AssertionPackage.CONDITIONS_TYPE__CONDITION:
				getCondition().clear();
				getCondition().addAll((Collection<? extends ConditionAbstractType>)newValue);
				return;
			case AssertionPackage.CONDITIONS_TYPE__AUDIENCE_RESTRICTION:
				getAudienceRestriction().clear();
				getAudienceRestriction().addAll((Collection<? extends AudienceRestrictionType>)newValue);
				return;
			case AssertionPackage.CONDITIONS_TYPE__ONE_TIME_USE:
				getOneTimeUse().clear();
				getOneTimeUse().addAll((Collection<? extends OneTimeUseType>)newValue);
				return;
			case AssertionPackage.CONDITIONS_TYPE__PROXY_RESTRICTION:
				getProxyRestriction().clear();
				getProxyRestriction().addAll((Collection<? extends ProxyRestrictionType>)newValue);
				return;
			case AssertionPackage.CONDITIONS_TYPE__NOT_BEFORE:
				setNotBefore((XMLGregorianCalendar)newValue);
				return;
			case AssertionPackage.CONDITIONS_TYPE__NOT_ON_OR_AFTER:
				setNotOnOrAfter((XMLGregorianCalendar)newValue);
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
			case AssertionPackage.CONDITIONS_TYPE__GROUP:
				getGroup().clear();
				return;
			case AssertionPackage.CONDITIONS_TYPE__CONDITION:
				getCondition().clear();
				return;
			case AssertionPackage.CONDITIONS_TYPE__AUDIENCE_RESTRICTION:
				getAudienceRestriction().clear();
				return;
			case AssertionPackage.CONDITIONS_TYPE__ONE_TIME_USE:
				getOneTimeUse().clear();
				return;
			case AssertionPackage.CONDITIONS_TYPE__PROXY_RESTRICTION:
				getProxyRestriction().clear();
				return;
			case AssertionPackage.CONDITIONS_TYPE__NOT_BEFORE:
				setNotBefore(NOT_BEFORE_EDEFAULT);
				return;
			case AssertionPackage.CONDITIONS_TYPE__NOT_ON_OR_AFTER:
				setNotOnOrAfter(NOT_ON_OR_AFTER_EDEFAULT);
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
			case AssertionPackage.CONDITIONS_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case AssertionPackage.CONDITIONS_TYPE__CONDITION:
				return !getCondition().isEmpty();
			case AssertionPackage.CONDITIONS_TYPE__AUDIENCE_RESTRICTION:
				return !getAudienceRestriction().isEmpty();
			case AssertionPackage.CONDITIONS_TYPE__ONE_TIME_USE:
				return !getOneTimeUse().isEmpty();
			case AssertionPackage.CONDITIONS_TYPE__PROXY_RESTRICTION:
				return !getProxyRestriction().isEmpty();
			case AssertionPackage.CONDITIONS_TYPE__NOT_BEFORE:
				return NOT_BEFORE_EDEFAULT == null ? notBefore != null : !NOT_BEFORE_EDEFAULT.equals(notBefore);
			case AssertionPackage.CONDITIONS_TYPE__NOT_ON_OR_AFTER:
				return NOT_ON_OR_AFTER_EDEFAULT == null ? notOnOrAfter != null : !NOT_ON_OR_AFTER_EDEFAULT.equals(notOnOrAfter);
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
		result.append(" (group: ");
		result.append(group);
		result.append(", notBefore: ");
		result.append(notBefore);
		result.append(", notOnOrAfter: ");
		result.append(notOnOrAfter);
		result.append(')');
		return result.toString();
	}

} //ConditionsTypeImpl
