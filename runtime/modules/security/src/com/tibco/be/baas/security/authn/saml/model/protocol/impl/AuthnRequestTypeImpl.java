/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authn Request Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getNameIDPolicy <em>Name ID Policy</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getConditions <em>Conditions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getRequestedAuthnContext <em>Requested Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getScoping <em>Scoping</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getAssertionConsumerServiceIndex <em>Assertion Consumer Service Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getAssertionConsumerServiceURL <em>Assertion Consumer Service URL</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getAttributeConsumingServiceIndex <em>Attribute Consuming Service Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#isForceAuthn <em>Force Authn</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#isIsPassive <em>Is Passive</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getProtocolBinding <em>Protocol Binding</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnRequestTypeImpl#getProviderName <em>Provider Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthnRequestTypeImpl extends RequestAbstractTypeImpl implements AuthnRequestType {
	/**
	 * The cached value of the '{@link #getSubject() <em>Subject</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected SubjectType subject;

	/**
	 * The cached value of the '{@link #getNameIDPolicy() <em>Name ID Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameIDPolicy()
	 * @generated
	 * @ordered
	 */
	protected NameIDPolicyType nameIDPolicy;

	/**
	 * The cached value of the '{@link #getConditions() <em>Conditions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditions()
	 * @generated
	 * @ordered
	 */
	protected ConditionsType conditions;

	/**
	 * The cached value of the '{@link #getRequestedAuthnContext() <em>Requested Authn Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestedAuthnContext()
	 * @generated
	 * @ordered
	 */
	protected RequestedAuthnContextType requestedAuthnContext;

	/**
	 * The cached value of the '{@link #getScoping() <em>Scoping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScoping()
	 * @generated
	 * @ordered
	 */
	protected ScopingType scoping;

	/**
	 * The default value of the '{@link #getAssertionConsumerServiceIndex() <em>Assertion Consumer Service Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionConsumerServiceIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int ASSERTION_CONSUMER_SERVICE_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAssertionConsumerServiceIndex() <em>Assertion Consumer Service Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionConsumerServiceIndex()
	 * @generated
	 * @ordered
	 */
	protected int assertionConsumerServiceIndex = ASSERTION_CONSUMER_SERVICE_INDEX_EDEFAULT;

	/**
	 * This is true if the Assertion Consumer Service Index attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean assertionConsumerServiceIndexESet;

	/**
	 * The default value of the '{@link #getAssertionConsumerServiceURL() <em>Assertion Consumer Service URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionConsumerServiceURL()
	 * @generated
	 * @ordered
	 */
	protected static final String ASSERTION_CONSUMER_SERVICE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAssertionConsumerServiceURL() <em>Assertion Consumer Service URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionConsumerServiceURL()
	 * @generated
	 * @ordered
	 */
	protected String assertionConsumerServiceURL = ASSERTION_CONSUMER_SERVICE_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getAttributeConsumingServiceIndex() <em>Attribute Consuming Service Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConsumingServiceIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int ATTRIBUTE_CONSUMING_SERVICE_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAttributeConsumingServiceIndex() <em>Attribute Consuming Service Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConsumingServiceIndex()
	 * @generated
	 * @ordered
	 */
	protected int attributeConsumingServiceIndex = ATTRIBUTE_CONSUMING_SERVICE_INDEX_EDEFAULT;

	/**
	 * This is true if the Attribute Consuming Service Index attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean attributeConsumingServiceIndexESet;

	/**
	 * The default value of the '{@link #isForceAuthn() <em>Force Authn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForceAuthn()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FORCE_AUTHN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isForceAuthn() <em>Force Authn</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForceAuthn()
	 * @generated
	 * @ordered
	 */
	protected boolean forceAuthn = FORCE_AUTHN_EDEFAULT;

	/**
	 * This is true if the Force Authn attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean forceAuthnESet;

	/**
	 * The default value of the '{@link #isIsPassive() <em>Is Passive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsPassive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_PASSIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsPassive() <em>Is Passive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsPassive()
	 * @generated
	 * @ordered
	 */
	protected boolean isPassive = IS_PASSIVE_EDEFAULT;

	/**
	 * This is true if the Is Passive attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean isPassiveESet;

	/**
	 * The default value of the '{@link #getProtocolBinding() <em>Protocol Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocolBinding()
	 * @generated
	 * @ordered
	 */
	protected static final String PROTOCOL_BINDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProtocolBinding() <em>Protocol Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocolBinding()
	 * @generated
	 * @ordered
	 */
	protected String protocolBinding = PROTOCOL_BINDING_EDEFAULT;

	/**
	 * The default value of the '{@link #getProviderName() <em>Provider Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProviderName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROVIDER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProviderName() <em>Provider Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProviderName()
	 * @generated
	 * @ordered
	 */
	protected String providerName = PROVIDER_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthnRequestTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.AUTHN_REQUEST_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectType getSubject() {
		return subject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubject(SubjectType newSubject, NotificationChain msgs) {
		SubjectType oldSubject = subject;
		subject = newSubject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT, oldSubject, newSubject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubject(SubjectType newSubject) {
		if (newSubject != subject) {
			NotificationChain msgs = null;
			if (subject != null)
				msgs = ((InternalEObject)subject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT, null, msgs);
			if (newSubject != null)
				msgs = ((InternalEObject)newSubject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT, null, msgs);
			msgs = basicSetSubject(newSubject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT, newSubject, newSubject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDPolicyType getNameIDPolicy() {
		return nameIDPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameIDPolicy(NameIDPolicyType newNameIDPolicy, NotificationChain msgs) {
		NameIDPolicyType oldNameIDPolicy = nameIDPolicy;
		nameIDPolicy = newNameIDPolicy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY, oldNameIDPolicy, newNameIDPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameIDPolicy(NameIDPolicyType newNameIDPolicy) {
		if (newNameIDPolicy != nameIDPolicy) {
			NotificationChain msgs = null;
			if (nameIDPolicy != null)
				msgs = ((InternalEObject)nameIDPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY, null, msgs);
			if (newNameIDPolicy != null)
				msgs = ((InternalEObject)newNameIDPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY, null, msgs);
			msgs = basicSetNameIDPolicy(newNameIDPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY, newNameIDPolicy, newNameIDPolicy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionsType getConditions() {
		return conditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConditions(ConditionsType newConditions, NotificationChain msgs) {
		ConditionsType oldConditions = conditions;
		conditions = newConditions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS, oldConditions, newConditions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConditions(ConditionsType newConditions) {
		if (newConditions != conditions) {
			NotificationChain msgs = null;
			if (conditions != null)
				msgs = ((InternalEObject)conditions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS, null, msgs);
			if (newConditions != null)
				msgs = ((InternalEObject)newConditions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS, null, msgs);
			msgs = basicSetConditions(newConditions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS, newConditions, newConditions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequestedAuthnContextType getRequestedAuthnContext() {
		return requestedAuthnContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequestedAuthnContext(RequestedAuthnContextType newRequestedAuthnContext, NotificationChain msgs) {
		RequestedAuthnContextType oldRequestedAuthnContext = requestedAuthnContext;
		requestedAuthnContext = newRequestedAuthnContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT, oldRequestedAuthnContext, newRequestedAuthnContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequestedAuthnContext(RequestedAuthnContextType newRequestedAuthnContext) {
		if (newRequestedAuthnContext != requestedAuthnContext) {
			NotificationChain msgs = null;
			if (requestedAuthnContext != null)
				msgs = ((InternalEObject)requestedAuthnContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT, null, msgs);
			if (newRequestedAuthnContext != null)
				msgs = ((InternalEObject)newRequestedAuthnContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT, null, msgs);
			msgs = basicSetRequestedAuthnContext(newRequestedAuthnContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT, newRequestedAuthnContext, newRequestedAuthnContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopingType getScoping() {
		return scoping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScoping(ScopingType newScoping, NotificationChain msgs) {
		ScopingType oldScoping = scoping;
		scoping = newScoping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING, oldScoping, newScoping);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScoping(ScopingType newScoping) {
		if (newScoping != scoping) {
			NotificationChain msgs = null;
			if (scoping != null)
				msgs = ((InternalEObject)scoping).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING, null, msgs);
			if (newScoping != null)
				msgs = ((InternalEObject)newScoping).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING, null, msgs);
			msgs = basicSetScoping(newScoping, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING, newScoping, newScoping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getAssertionConsumerServiceIndex() {
		return assertionConsumerServiceIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertionConsumerServiceIndex(int newAssertionConsumerServiceIndex) {
		int oldAssertionConsumerServiceIndex = assertionConsumerServiceIndex;
		assertionConsumerServiceIndex = newAssertionConsumerServiceIndex;
		boolean oldAssertionConsumerServiceIndexESet = assertionConsumerServiceIndexESet;
		assertionConsumerServiceIndexESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX, oldAssertionConsumerServiceIndex, assertionConsumerServiceIndex, !oldAssertionConsumerServiceIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAssertionConsumerServiceIndex() {
		int oldAssertionConsumerServiceIndex = assertionConsumerServiceIndex;
		boolean oldAssertionConsumerServiceIndexESet = assertionConsumerServiceIndexESet;
		assertionConsumerServiceIndex = ASSERTION_CONSUMER_SERVICE_INDEX_EDEFAULT;
		assertionConsumerServiceIndexESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX, oldAssertionConsumerServiceIndex, ASSERTION_CONSUMER_SERVICE_INDEX_EDEFAULT, oldAssertionConsumerServiceIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAssertionConsumerServiceIndex() {
		return assertionConsumerServiceIndexESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAssertionConsumerServiceURL() {
		return assertionConsumerServiceURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertionConsumerServiceURL(String newAssertionConsumerServiceURL) {
		String oldAssertionConsumerServiceURL = assertionConsumerServiceURL;
		assertionConsumerServiceURL = newAssertionConsumerServiceURL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_URL, oldAssertionConsumerServiceURL, assertionConsumerServiceURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getAttributeConsumingServiceIndex() {
		return attributeConsumingServiceIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeConsumingServiceIndex(int newAttributeConsumingServiceIndex) {
		int oldAttributeConsumingServiceIndex = attributeConsumingServiceIndex;
		attributeConsumingServiceIndex = newAttributeConsumingServiceIndex;
		boolean oldAttributeConsumingServiceIndexESet = attributeConsumingServiceIndexESet;
		attributeConsumingServiceIndexESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX, oldAttributeConsumingServiceIndex, attributeConsumingServiceIndex, !oldAttributeConsumingServiceIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAttributeConsumingServiceIndex() {
		int oldAttributeConsumingServiceIndex = attributeConsumingServiceIndex;
		boolean oldAttributeConsumingServiceIndexESet = attributeConsumingServiceIndexESet;
		attributeConsumingServiceIndex = ATTRIBUTE_CONSUMING_SERVICE_INDEX_EDEFAULT;
		attributeConsumingServiceIndexESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ProtocolPackage.AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX, oldAttributeConsumingServiceIndex, ATTRIBUTE_CONSUMING_SERVICE_INDEX_EDEFAULT, oldAttributeConsumingServiceIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAttributeConsumingServiceIndex() {
		return attributeConsumingServiceIndexESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isForceAuthn() {
		return forceAuthn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForceAuthn(boolean newForceAuthn) {
		boolean oldForceAuthn = forceAuthn;
		forceAuthn = newForceAuthn;
		boolean oldForceAuthnESet = forceAuthnESet;
		forceAuthnESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__FORCE_AUTHN, oldForceAuthn, forceAuthn, !oldForceAuthnESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetForceAuthn() {
		boolean oldForceAuthn = forceAuthn;
		boolean oldForceAuthnESet = forceAuthnESet;
		forceAuthn = FORCE_AUTHN_EDEFAULT;
		forceAuthnESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ProtocolPackage.AUTHN_REQUEST_TYPE__FORCE_AUTHN, oldForceAuthn, FORCE_AUTHN_EDEFAULT, oldForceAuthnESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetForceAuthn() {
		return forceAuthnESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsPassive() {
		return isPassive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsPassive(boolean newIsPassive) {
		boolean oldIsPassive = isPassive;
		isPassive = newIsPassive;
		boolean oldIsPassiveESet = isPassiveESet;
		isPassiveESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__IS_PASSIVE, oldIsPassive, isPassive, !oldIsPassiveESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIsPassive() {
		boolean oldIsPassive = isPassive;
		boolean oldIsPassiveESet = isPassiveESet;
		isPassive = IS_PASSIVE_EDEFAULT;
		isPassiveESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ProtocolPackage.AUTHN_REQUEST_TYPE__IS_PASSIVE, oldIsPassive, IS_PASSIVE_EDEFAULT, oldIsPassiveESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIsPassive() {
		return isPassiveESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProtocolBinding() {
		return protocolBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocolBinding(String newProtocolBinding) {
		String oldProtocolBinding = protocolBinding;
		protocolBinding = newProtocolBinding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__PROTOCOL_BINDING, oldProtocolBinding, protocolBinding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProviderName(String newProviderName) {
		String oldProviderName = providerName;
		providerName = newProviderName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_REQUEST_TYPE__PROVIDER_NAME, oldProviderName, providerName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT:
				return basicSetSubject(null, msgs);
			case ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY:
				return basicSetNameIDPolicy(null, msgs);
			case ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS:
				return basicSetConditions(null, msgs);
			case ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT:
				return basicSetRequestedAuthnContext(null, msgs);
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING:
				return basicSetScoping(null, msgs);
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
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT:
				return getSubject();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY:
				return getNameIDPolicy();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS:
				return getConditions();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT:
				return getRequestedAuthnContext();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING:
				return getScoping();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX:
				return new Integer(getAssertionConsumerServiceIndex());
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_URL:
				return getAssertionConsumerServiceURL();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX:
				return new Integer(getAttributeConsumingServiceIndex());
			case ProtocolPackage.AUTHN_REQUEST_TYPE__FORCE_AUTHN:
				return isForceAuthn() ? Boolean.TRUE : Boolean.FALSE;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__IS_PASSIVE:
				return isIsPassive() ? Boolean.TRUE : Boolean.FALSE;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__PROTOCOL_BINDING:
				return getProtocolBinding();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__PROVIDER_NAME:
				return getProviderName();
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
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT:
				setSubject((SubjectType)newValue);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY:
				setNameIDPolicy((NameIDPolicyType)newValue);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS:
				setConditions((ConditionsType)newValue);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT:
				setRequestedAuthnContext((RequestedAuthnContextType)newValue);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING:
				setScoping((ScopingType)newValue);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX:
				setAssertionConsumerServiceIndex(((Integer)newValue).intValue());
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_URL:
				setAssertionConsumerServiceURL((String)newValue);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX:
				setAttributeConsumingServiceIndex(((Integer)newValue).intValue());
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__FORCE_AUTHN:
				setForceAuthn(((Boolean)newValue).booleanValue());
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__IS_PASSIVE:
				setIsPassive(((Boolean)newValue).booleanValue());
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__PROTOCOL_BINDING:
				setProtocolBinding((String)newValue);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__PROVIDER_NAME:
				setProviderName((String)newValue);
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
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT:
				setSubject((SubjectType)null);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY:
				setNameIDPolicy((NameIDPolicyType)null);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS:
				setConditions((ConditionsType)null);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT:
				setRequestedAuthnContext((RequestedAuthnContextType)null);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING:
				setScoping((ScopingType)null);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX:
				unsetAssertionConsumerServiceIndex();
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_URL:
				setAssertionConsumerServiceURL(ASSERTION_CONSUMER_SERVICE_URL_EDEFAULT);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX:
				unsetAttributeConsumingServiceIndex();
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__FORCE_AUTHN:
				unsetForceAuthn();
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__IS_PASSIVE:
				unsetIsPassive();
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__PROTOCOL_BINDING:
				setProtocolBinding(PROTOCOL_BINDING_EDEFAULT);
				return;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__PROVIDER_NAME:
				setProviderName(PROVIDER_NAME_EDEFAULT);
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
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SUBJECT:
				return subject != null;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__NAME_ID_POLICY:
				return nameIDPolicy != null;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__CONDITIONS:
				return conditions != null;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT:
				return requestedAuthnContext != null;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__SCOPING:
				return scoping != null;
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX:
				return isSetAssertionConsumerServiceIndex();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_URL:
				return ASSERTION_CONSUMER_SERVICE_URL_EDEFAULT == null ? assertionConsumerServiceURL != null : !ASSERTION_CONSUMER_SERVICE_URL_EDEFAULT.equals(assertionConsumerServiceURL);
			case ProtocolPackage.AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX:
				return isSetAttributeConsumingServiceIndex();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__FORCE_AUTHN:
				return isSetForceAuthn();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__IS_PASSIVE:
				return isSetIsPassive();
			case ProtocolPackage.AUTHN_REQUEST_TYPE__PROTOCOL_BINDING:
				return PROTOCOL_BINDING_EDEFAULT == null ? protocolBinding != null : !PROTOCOL_BINDING_EDEFAULT.equals(protocolBinding);
			case ProtocolPackage.AUTHN_REQUEST_TYPE__PROVIDER_NAME:
				return PROVIDER_NAME_EDEFAULT == null ? providerName != null : !PROVIDER_NAME_EDEFAULT.equals(providerName);
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
		result.append(" (assertionConsumerServiceIndex: ");
		if (assertionConsumerServiceIndexESet) result.append(assertionConsumerServiceIndex); else result.append("<unset>");
		result.append(", assertionConsumerServiceURL: ");
		result.append(assertionConsumerServiceURL);
		result.append(", attributeConsumingServiceIndex: ");
		if (attributeConsumingServiceIndexESet) result.append(attributeConsumingServiceIndex); else result.append("<unset>");
		result.append(", forceAuthn: ");
		if (forceAuthnESet) result.append(forceAuthn); else result.append("<unset>");
		result.append(", isPassive: ");
		if (isPassiveESet) result.append(isPassive); else result.append("<unset>");
		result.append(", protocolBinding: ");
		result.append(protocolBinding);
		result.append(", providerName: ");
		result.append(providerName);
		result.append(')');
		return result.toString();
	}

} //AuthnRequestTypeImpl
