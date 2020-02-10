/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Request Abstract Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl#getIssuer <em>Issuer</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl#getConsent <em>Consent</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl#getDestination <em>Destination</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl#getIssueInstant <em>Issue Instant</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.RequestAbstractTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class RequestAbstractTypeImpl extends EObjectImpl implements RequestAbstractType {
	/**
	 * The cached value of the '{@link #getIssuer() <em>Issuer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIssuer()
	 * @generated
	 * @ordered
	 */
	protected NameIDType issuer;

	/**
	 * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensions()
	 * @generated
	 * @ordered
	 */
	protected ExtensionsType extensions;

	/**
	 * The default value of the '{@link #getConsent() <em>Consent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConsent()
	 * @generated
	 * @ordered
	 */
	protected static final String CONSENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConsent() <em>Consent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConsent()
	 * @generated
	 * @ordered
	 */
	protected String consent = CONSENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDestination() <em>Destination</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestination()
	 * @generated
	 * @ordered
	 */
	protected static final String DESTINATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDestination() <em>Destination</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestination()
	 * @generated
	 * @ordered
	 */
	protected String destination = DESTINATION_EDEFAULT;

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
	protected String iD = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getIssueInstant() <em>Issue Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIssueInstant()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar ISSUE_INSTANT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIssueInstant() <em>Issue Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIssueInstant()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar issueInstant = ISSUE_INSTANT_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequestAbstractTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.REQUEST_ABSTRACT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDType getIssuer() {
		return issuer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIssuer(NameIDType newIssuer, NotificationChain msgs) {
		NameIDType oldIssuer = issuer;
		issuer = newIssuer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER, oldIssuer, newIssuer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIssuer(NameIDType newIssuer) {
		if (newIssuer != issuer) {
			NotificationChain msgs = null;
			if (issuer != null)
				msgs = ((InternalEObject)issuer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER, null, msgs);
			if (newIssuer != null)
				msgs = ((InternalEObject)newIssuer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER, null, msgs);
			msgs = basicSetIssuer(newIssuer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER, newIssuer, newIssuer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionsType getExtensions() {
		return extensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtensions(ExtensionsType newExtensions, NotificationChain msgs) {
		ExtensionsType oldExtensions = extensions;
		extensions = newExtensions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS, oldExtensions, newExtensions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensions(ExtensionsType newExtensions) {
		if (newExtensions != extensions) {
			NotificationChain msgs = null;
			if (extensions != null)
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS, newExtensions, newExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConsent() {
		return consent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConsent(String newConsent) {
		String oldConsent = consent;
		consent = newConsent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__CONSENT, oldConsent, consent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestination(String newDestination) {
		String oldDestination = destination;
		destination = newDestination;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__DESTINATION, oldDestination, destination));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getID() {
		return iD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setID(String newID) {
		String oldID = iD;
		iD = newID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__ID, oldID, iD));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getIssueInstant() {
		return issueInstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIssueInstant(XMLGregorianCalendar newIssueInstant) {
		XMLGregorianCalendar oldIssueInstant = issueInstant;
		issueInstant = newIssueInstant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT, oldIssueInstant, issueInstant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.REQUEST_ABSTRACT_TYPE__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER:
				return basicSetIssuer(null, msgs);
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS:
				return basicSetExtensions(null, msgs);
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
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER:
				return getIssuer();
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS:
				return getExtensions();
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__CONSENT:
				return getConsent();
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__DESTINATION:
				return getDestination();
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ID:
				return getID();
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT:
				return getIssueInstant();
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__VERSION:
				return getVersion();
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
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER:
				setIssuer((NameIDType)newValue);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__CONSENT:
				setConsent((String)newValue);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__DESTINATION:
				setDestination((String)newValue);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ID:
				setID((String)newValue);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT:
				setIssueInstant((XMLGregorianCalendar)newValue);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__VERSION:
				setVersion((String)newValue);
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
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER:
				setIssuer((NameIDType)null);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__CONSENT:
				setConsent(CONSENT_EDEFAULT);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__DESTINATION:
				setDestination(DESTINATION_EDEFAULT);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ID:
				setID(ID_EDEFAULT);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT:
				setIssueInstant(ISSUE_INSTANT_EDEFAULT);
				return;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUER:
				return issuer != null;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__EXTENSIONS:
				return extensions != null;
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__CONSENT:
				return CONSENT_EDEFAULT == null ? consent != null : !CONSENT_EDEFAULT.equals(consent);
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__DESTINATION:
				return DESTINATION_EDEFAULT == null ? destination != null : !DESTINATION_EDEFAULT.equals(destination);
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ID:
				return ID_EDEFAULT == null ? iD != null : !ID_EDEFAULT.equals(iD);
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT:
				return ISSUE_INSTANT_EDEFAULT == null ? issueInstant != null : !ISSUE_INSTANT_EDEFAULT.equals(issueInstant);
			case ProtocolPackage.REQUEST_ABSTRACT_TYPE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
		result.append(" (consent: ");
		result.append(consent);
		result.append(", destination: ");
		result.append(destination);
		result.append(", iD: ");
		result.append(iD);
		result.append(", issueInstant: ");
		result.append(issueInstant);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //RequestAbstractTypeImpl
