/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subject Confirmation Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl#getInResponseTo <em>In Response To</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl#getNotBefore <em>Not Before</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl#getNotOnOrAfter <em>Not On Or After</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl#getRecipient <em>Recipient</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationDataTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubjectConfirmationDataTypeImpl extends EObjectImpl implements SubjectConfirmationDataType {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The default value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDRESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected String address = ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getInResponseTo() <em>In Response To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInResponseTo()
	 * @generated
	 * @ordered
	 */
	protected static final String IN_RESPONSE_TO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInResponseTo() <em>In Response To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInResponseTo()
	 * @generated
	 * @ordered
	 */
	protected String inResponseTo = IN_RESPONSE_TO_EDEFAULT;

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
	 * The default value of the '{@link #getRecipient() <em>Recipient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecipient()
	 * @generated
	 * @ordered
	 */
	protected static final String RECIPIENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRecipient() <em>Recipient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecipient()
	 * @generated
	 * @ordered
	 */
	protected String recipient = RECIPIENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnyAttribute()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap anyAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubjectConfirmationDataTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.SUBJECT_CONFIRMATION_DATA_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAny() {
		return (FeatureMap)getMixed().<FeatureMap.Entry>list(AssertionPackage.Literals.SUBJECT_CONFIRMATION_DATA_TYPE__ANY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddress(String newAddress) {
		String oldAddress = address;
		address = newAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ADDRESS, oldAddress, address));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInResponseTo() {
		return inResponseTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInResponseTo(String newInResponseTo) {
		String oldInResponseTo = inResponseTo;
		inResponseTo = newInResponseTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__IN_RESPONSE_TO, oldInResponseTo, inResponseTo));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_BEFORE, oldNotBefore, notBefore));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_ON_OR_AFTER, oldNotOnOrAfter, notOnOrAfter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecipient(String newRecipient) {
		String oldRecipient = recipient;
		recipient = newRecipient;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__RECIPIENT, oldRecipient, recipient));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE);
		}
		return anyAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY:
				return ((InternalEList<?>)getAny()).basicRemove(otherEnd, msgs);
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE:
				return ((InternalEList<?>)getAnyAttribute()).basicRemove(otherEnd, msgs);
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
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY:
				if (coreType) return getAny();
				return ((FeatureMap.Internal)getAny()).getWrapper();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ADDRESS:
				return getAddress();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__IN_RESPONSE_TO:
				return getInResponseTo();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_BEFORE:
				return getNotBefore();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_ON_OR_AFTER:
				return getNotOnOrAfter();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__RECIPIENT:
				return getRecipient();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE:
				if (coreType) return getAnyAttribute();
				return ((FeatureMap.Internal)getAnyAttribute()).getWrapper();
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
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY:
				((FeatureMap.Internal)getAny()).set(newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ADDRESS:
				setAddress((String)newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__IN_RESPONSE_TO:
				setInResponseTo((String)newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_BEFORE:
				setNotBefore((XMLGregorianCalendar)newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_ON_OR_AFTER:
				setNotOnOrAfter((XMLGregorianCalendar)newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__RECIPIENT:
				setRecipient((String)newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE:
				((FeatureMap.Internal)getAnyAttribute()).set(newValue);
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
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__MIXED:
				getMixed().clear();
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY:
				getAny().clear();
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ADDRESS:
				setAddress(ADDRESS_EDEFAULT);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__IN_RESPONSE_TO:
				setInResponseTo(IN_RESPONSE_TO_EDEFAULT);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_BEFORE:
				setNotBefore(NOT_BEFORE_EDEFAULT);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_ON_OR_AFTER:
				setNotOnOrAfter(NOT_ON_OR_AFTER_EDEFAULT);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__RECIPIENT:
				setRecipient(RECIPIENT_EDEFAULT);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE:
				getAnyAttribute().clear();
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
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY:
				return !getAny().isEmpty();
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ADDRESS:
				return ADDRESS_EDEFAULT == null ? address != null : !ADDRESS_EDEFAULT.equals(address);
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__IN_RESPONSE_TO:
				return IN_RESPONSE_TO_EDEFAULT == null ? inResponseTo != null : !IN_RESPONSE_TO_EDEFAULT.equals(inResponseTo);
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_BEFORE:
				return NOT_BEFORE_EDEFAULT == null ? notBefore != null : !NOT_BEFORE_EDEFAULT.equals(notBefore);
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__NOT_ON_OR_AFTER:
				return NOT_ON_OR_AFTER_EDEFAULT == null ? notOnOrAfter != null : !NOT_ON_OR_AFTER_EDEFAULT.equals(notOnOrAfter);
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__RECIPIENT:
				return RECIPIENT_EDEFAULT == null ? recipient != null : !RECIPIENT_EDEFAULT.equals(recipient);
			case AssertionPackage.SUBJECT_CONFIRMATION_DATA_TYPE__ANY_ATTRIBUTE:
				return anyAttribute != null && !anyAttribute.isEmpty();
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(", address: ");
		result.append(address);
		result.append(", inResponseTo: ");
		result.append(inResponseTo);
		result.append(", notBefore: ");
		result.append(notBefore);
		result.append(", notOnOrAfter: ");
		result.append(notOnOrAfter);
		result.append(", recipient: ");
		result.append(recipient);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //SubjectConfirmationDataTypeImpl
