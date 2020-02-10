/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.impl.RuleParticipantImpl;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.designtime.core.model.validation.ValidationFactory;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getSuperEvent <em>Super Event</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getSuperEventPath <em>Super Event Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getSubEvents <em>Sub Events</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getAncestorProperties <em>Ancestor Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getPayloadString <em>Payload String</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getTtl <em>Ttl</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getTtlUnits <em>Ttl Units</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getSpecifiedTime <em>Specified Time</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getSerializationFormat <em>Serialization Format</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getNamespaceEntries <em>Namespace Entries</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getRegistryImportEntries <em>Registry Import Entries</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getSubEventsPath <em>Sub Events Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getExpiryAction <em>Expiry Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#getPayload <em>Payload</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.EventImpl#isRetryOnException <em>Retry On Exception</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EventImpl extends RuleParticipantImpl implements Event {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final EVENT_TYPE TYPE_EDEFAULT = EVENT_TYPE.SIMPLE_EVENT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EVENT_TYPE type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSuperEvent() <em>Super Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperEvent()
	 * @generated
	 * @ordered
	 */
	protected Event superEvent;

	/**
	 * The default value of the '{@link #getSuperEventPath() <em>Super Event Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperEventPath()
	 * @generated
	 * @ordered
	 */
	protected static final String SUPER_EVENT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSuperEventPath() <em>Super Event Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperEventPath()
	 * @generated
	 * @ordered
	 */
	protected String superEventPath = SUPER_EVENT_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubEvents() <em>Sub Events</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubEvents()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> subEvents;

	/**
	 * The cached value of the '{@link #getAncestorProperties() <em>Ancestor Properties</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAncestorProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> ancestorProperties;

	/**
	 * The default value of the '{@link #getPayloadString() <em>Payload String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPayloadString()
	 * @generated
	 * @ordered
	 */
	protected static final String PAYLOAD_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPayloadString() <em>Payload String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPayloadString()
	 * @generated
	 * @ordered
	 */
	protected String payloadString = PAYLOAD_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getTtl() <em>Ttl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTtl()
	 * @generated
	 * @ordered
	 */
	protected static final String TTL_EDEFAULT = "0";

	/**
	 * The cached value of the '{@link #getTtl() <em>Ttl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTtl()
	 * @generated
	 * @ordered
	 */
	protected String ttl = TTL_EDEFAULT;

	/**
	 * The default value of the '{@link #getTtlUnits() <em>Ttl Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTtlUnits()
	 * @generated
	 * @ordered
	 */
	protected static final TIMEOUT_UNITS TTL_UNITS_EDEFAULT = TIMEOUT_UNITS.MILLISECONDS;

	/**
	 * The cached value of the '{@link #getTtlUnits() <em>Ttl Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTtlUnits()
	 * @generated
	 * @ordered
	 */
	protected TIMEOUT_UNITS ttlUnits = TTL_UNITS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyDefinition> properties;

	/**
	 * The cached value of the '{@link #getRuleSet() <em>Rule Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleSet()
	 * @generated
	 * @ordered
	 */
	protected RuleSet ruleSet;

	/**
	 * The default value of the '{@link #getSpecifiedTime() <em>Specified Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTime()
	 * @generated
	 * @ordered
	 */
	protected static final long SPECIFIED_TIME_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getSpecifiedTime() <em>Specified Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedTime()
	 * @generated
	 * @ordered
	 */
	protected long specifiedTime = SPECIFIED_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSerializationFormat() <em>Serialization Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializationFormat()
	 * @generated
	 * @ordered
	 */
	protected static final int SERIALIZATION_FORMAT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSerializationFormat() <em>Serialization Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializationFormat()
	 * @generated
	 * @ordered
	 */
	protected int serializationFormat = SERIALIZATION_FORMAT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNamespaceEntries() <em>Namespace Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaceEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<NamespaceEntry> namespaceEntries;

	/**
	 * The cached value of the '{@link #getRegistryImportEntries() <em>Registry Import Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegistryImportEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<ImportRegistryEntry> registryImportEntries;

	/**
	 * The cached value of the '{@link #getSubEventsPath() <em>Sub Events Path</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubEventsPath()
	 * @generated
	 * @ordered
	 */
	protected EList<String> subEventsPath;

	/**
	 * The cached value of the '{@link #getExpiryAction() <em>Expiry Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpiryAction()
	 * @generated
	 * @ordered
	 */
	protected Rule expiryAction;

	/**
	 * The cached value of the '{@link #getPayload() <em>Payload</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPayload()
	 * @generated
	 * @ordered
	 */
	protected EObject payload;

	/**
	 * The default value of the '{@link #isRetryOnException() <em>Retry On Exception</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRetryOnException()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RETRY_ON_EXCEPTION_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isRetryOnException() <em>Retry On Exception</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRetryOnException()
	 * @generated
	 * @ordered
	 */
	protected boolean retryOnException = RETRY_ON_EXCEPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventPackage.Literals.EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EVENT_TYPE getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EVENT_TYPE newType) {
		EVENT_TYPE oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Event getSuperEvent() {
		if (superEvent != null && superEvent.eIsProxy()) {
			InternalEObject oldSuperEvent = (InternalEObject)superEvent;
			superEvent = (Event)eResolveProxy(oldSuperEvent);
			if (superEvent != oldSuperEvent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EventPackage.EVENT__SUPER_EVENT, oldSuperEvent, superEvent));
			}
		}
		//if (superEvent == null){
			String superEventPath = this.getSuperEventPath();
			if (superEventPath != null && superEventPath.trim().length() > 0){				
				if (EVENT_TYPE.SIMPLE_EVENT == this.getType()){
					superEvent = CommonIndexUtils.getSimpleEvent(ownerProjectName, superEventPath);
				}
				else if (EVENT_TYPE.TIME_EVENT == this.getType()){
					superEvent = CommonIndexUtils.getTimeEvent(ownerProjectName, superEventPath);
				}
			}
		//}
		return superEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event basicGetSuperEvent() {
		return superEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperEvent(Event newSuperEvent) {
		Event oldSuperEvent = superEvent;
		superEvent = newSuperEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__SUPER_EVENT, oldSuperEvent, superEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSuperEventPath() {
		return superEventPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperEventPath(String newSuperEventPath) {
		String oldSuperEventPath = superEventPath;
		superEventPath = newSuperEventPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__SUPER_EVENT_PATH, oldSuperEventPath, superEventPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Event> getSubEvents() {
		if (subEvents == null) {
			subEvents = new EObjectResolvingEList<Event>(Event.class, this, EventPackage.EVENT__SUB_EVENTS);
		}
		return subEvents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getAncestorProperties() {
		if (ancestorProperties == null) {
			ancestorProperties = new EObjectResolvingEList<EObject>(EObject.class, this, EventPackage.EVENT__ANCESTOR_PROPERTIES);
		}
		return ancestorProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getPayload() {
		if (payload != null && payload.eIsProxy()) {
			InternalEObject oldPayload = (InternalEObject)payload;
			payload = eResolveProxy(oldPayload);
			if (payload != oldPayload) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EventPackage.EVENT__PAYLOAD, oldPayload, payload));
			}
		}
		return payload;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetPayload() {
		return payload;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPayload(EObject newPayload) {
		EObject oldPayload = payload;
		payload = newPayload;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__PAYLOAD, oldPayload, payload));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRetryOnException() {
		return retryOnException;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRetryOnException(boolean newRetryOnException) {
		boolean oldRetryOnException = retryOnException;
		retryOnException = newRetryOnException;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__RETRY_ON_EXCEPTION, oldRetryOnException, retryOnException));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPayloadString() {
		return payloadString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPayloadString(String newPayloadString) {
		String oldPayloadString = payloadString;
		payloadString = newPayloadString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__PAYLOAD_STRING, oldPayloadString, payloadString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTtl() {
		return ttl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTtl(String newTtl) {
		String oldTtl = ttl;
		ttl = newTtl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__TTL, oldTtl, ttl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TIMEOUT_UNITS getTtlUnits() {
		return ttlUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTtlUnits(TIMEOUT_UNITS newTtlUnits) {
		TIMEOUT_UNITS oldTtlUnits = ttlUnits;
		ttlUnits = newTtlUnits == null ? TTL_UNITS_EDEFAULT : newTtlUnits;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__TTL_UNITS, oldTtlUnits, ttlUnits));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyDefinition> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<PropertyDefinition>(PropertyDefinition.class, this, EventPackage.EVENT__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleSet getRuleSet() {
		if (ruleSet != null && ruleSet.eIsProxy()) {
			InternalEObject oldRuleSet = (InternalEObject)ruleSet;
			ruleSet = (RuleSet)eResolveProxy(oldRuleSet);
			if (ruleSet != oldRuleSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EventPackage.EVENT__RULE_SET, oldRuleSet, ruleSet));
			}
		}
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleSet basicGetRuleSet() {
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleSet(RuleSet newRuleSet) {
		RuleSet oldRuleSet = ruleSet;
		ruleSet = newRuleSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__RULE_SET, oldRuleSet, ruleSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getSpecifiedTime() {
		return specifiedTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecifiedTime(long newSpecifiedTime) {
		long oldSpecifiedTime = specifiedTime;
		specifiedTime = newSpecifiedTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__SPECIFIED_TIME, oldSpecifiedTime, specifiedTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSerializationFormat() {
		return serializationFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSerializationFormat(int newSerializationFormat) {
		int oldSerializationFormat = serializationFormat;
		serializationFormat = newSerializationFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__SERIALIZATION_FORMAT, oldSerializationFormat, serializationFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NamespaceEntry> getNamespaceEntries() {
		if (namespaceEntries == null) {
			namespaceEntries = new EObjectContainmentEList<NamespaceEntry>(NamespaceEntry.class, this, EventPackage.EVENT__NAMESPACE_ENTRIES);
		}
		return namespaceEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ImportRegistryEntry> getRegistryImportEntries() {
		if (registryImportEntries == null) {
			registryImportEntries = new EObjectContainmentEList<ImportRegistryEntry>(ImportRegistryEntry.class, this, EventPackage.EVENT__REGISTRY_IMPORT_ENTRIES);
		}
		return registryImportEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getSubEventsPath() {
		if (subEventsPath == null) {
			subEventsPath = new EDataTypeUniqueEList<String>(String.class, this, EventPackage.EVENT__SUB_EVENTS_PATH);
		}
		return subEventsPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getExpiryAction() {
		return expiryAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpiryAction(Rule newExpiryAction, NotificationChain msgs) {
		Rule oldExpiryAction = expiryAction;
		expiryAction = newExpiryAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__EXPIRY_ACTION, oldExpiryAction, newExpiryAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpiryAction(Rule newExpiryAction) {
		if (newExpiryAction != expiryAction) {
			NotificationChain msgs = null;
			if (expiryAction != null)
				msgs = ((InternalEObject)expiryAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EventPackage.EVENT__EXPIRY_ACTION, null, msgs);
			if (newExpiryAction != null)
				msgs = ((InternalEObject)newExpiryAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EventPackage.EVENT__EXPIRY_ACTION, null, msgs);
			msgs = basicSetExpiryAction(newExpiryAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.EVENT__EXPIRY_ACTION, newExpiryAction, newExpiryAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<PropertyDefinition> getAllUserProperties() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		EList<PropertyDefinition> allProperties = new EDataTypeEList<PropertyDefinition>(PropertyDefinition.class, this,  EventPackage.EVENT__PROPERTIES);
		// get all events , including super events and then populate properties from top level
		List<Event> eventList = new ArrayList<Event>();
		//allProperties.addAll(this.getProperties());
		eventList.add(this);
		Event superEvent = this.getSuperEvent();
		/*
		if (superEvent == null){
			String superEventPath = this.getSuperEventPath();
			if (superEventPath != null && superEventPath.trim().length() > 0){				
				if (EVENT_TYPE.SIMPLE_EVENT == this.getType()){
					superEvent = IndexUtils.getSimpleEvent(ownerProjectName, superEventPath);
				}
				else if (EVENT_TYPE.TIME_EVENT == this.getType()){
					superEvent = IndexUtils.getTimeEvent(ownerProjectName, superEventPath);
				}
			}
		}
		*/
		while (superEvent != null){
			//allProperties.addAll(superEvent.getProperties());
			//Event temp = superEvent;
			eventList.add(superEvent);
			superEvent = superEvent.getSuperEvent();
			/*
			if (superEvent == null ){
				String superEventPath = temp.getSuperEventPath();
				if (superEventPath != null && superEventPath.trim().length() > 0){
					if (EVENT_TYPE.SIMPLE_EVENT == this.getType()){
						superEvent = IndexUtils.getSimpleEvent(ownerProjectName, superEventPath);
					}
					else if (EVENT_TYPE.TIME_EVENT == this.getType()){
						superEvent = IndexUtils.getTimeEvent(ownerProjectName, superEventPath);
					}
					
				}
				
			}
			*/
		}
	
		for (int i = eventList.size()-1 ; i >= 0 ; --i){
			allProperties.addAll(eventList.get(i).getProperties());
		}


		return allProperties;
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public boolean isSoapEvent() {
		
		if (EVENT_TYPE.SOAP_EVENT != getType()){
			String superEventPath = this.getSuperEventPath();
			if ("SOAPEvent".equals(superEventPath)){
				return true;
			}
	
		} else {
			return true;
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<DomainInstance> getAllDomainInstances() {
		EList<DomainInstance> allDomainInstances = 
			new EDataTypeEList<DomainInstance>(DomainInstance.class, 
					                           this,  
					                           ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES);
		EList<PropertyDefinition> allEventProperties = getAllUserProperties();
		
		for (PropertyDefinition propertyDefinition : allEventProperties) {
			allDomainInstances.addAll(propertyDefinition.getDomainInstances());
		}
		return allDomainInstances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EventPackage.EVENT__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case EventPackage.EVENT__NAMESPACE_ENTRIES:
				return ((InternalEList<?>)getNamespaceEntries()).basicRemove(otherEnd, msgs);
			case EventPackage.EVENT__REGISTRY_IMPORT_ENTRIES:
				return ((InternalEList<?>)getRegistryImportEntries()).basicRemove(otherEnd, msgs);
			case EventPackage.EVENT__EXPIRY_ACTION:
				return basicSetExpiryAction(null, msgs);
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
			case EventPackage.EVENT__TYPE:
				return getType();
			case EventPackage.EVENT__SUPER_EVENT:
				if (resolve) return getSuperEvent();
				return basicGetSuperEvent();
			case EventPackage.EVENT__SUPER_EVENT_PATH:
				return getSuperEventPath();
			case EventPackage.EVENT__SUB_EVENTS:
				return getSubEvents();
			case EventPackage.EVENT__ANCESTOR_PROPERTIES:
				return getAncestorProperties();
			case EventPackage.EVENT__PAYLOAD_STRING:
				return getPayloadString();
			case EventPackage.EVENT__TTL:
				return getTtl();
			case EventPackage.EVENT__TTL_UNITS:
				return getTtlUnits();
			case EventPackage.EVENT__PROPERTIES:
				return getProperties();
			case EventPackage.EVENT__RULE_SET:
				if (resolve) return getRuleSet();
				return basicGetRuleSet();
			case EventPackage.EVENT__SPECIFIED_TIME:
				return getSpecifiedTime();
			case EventPackage.EVENT__SERIALIZATION_FORMAT:
				return getSerializationFormat();
			case EventPackage.EVENT__NAMESPACE_ENTRIES:
				return getNamespaceEntries();
			case EventPackage.EVENT__REGISTRY_IMPORT_ENTRIES:
				return getRegistryImportEntries();
			case EventPackage.EVENT__SUB_EVENTS_PATH:
				return getSubEventsPath();
			case EventPackage.EVENT__EXPIRY_ACTION:
				return getExpiryAction();
			case EventPackage.EVENT__PAYLOAD:
				if (resolve) return getPayload();
				return basicGetPayload();
			case EventPackage.EVENT__RETRY_ON_EXCEPTION:
				return isRetryOnException();
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
			case EventPackage.EVENT__TYPE:
				setType((EVENT_TYPE)newValue);
				return;
			case EventPackage.EVENT__SUPER_EVENT:
				setSuperEvent((Event)newValue);
				return;
			case EventPackage.EVENT__SUPER_EVENT_PATH:
				setSuperEventPath((String)newValue);
				return;
			case EventPackage.EVENT__SUB_EVENTS:
				getSubEvents().clear();
				getSubEvents().addAll((Collection<? extends Event>)newValue);
				return;
			case EventPackage.EVENT__ANCESTOR_PROPERTIES:
				getAncestorProperties().clear();
				getAncestorProperties().addAll((Collection<? extends EObject>)newValue);
				return;
			case EventPackage.EVENT__PAYLOAD_STRING:
				setPayloadString((String)newValue);
				return;
			case EventPackage.EVENT__TTL:
				setTtl((String)newValue);
				return;
			case EventPackage.EVENT__TTL_UNITS:
				setTtlUnits((TIMEOUT_UNITS)newValue);
				return;
			case EventPackage.EVENT__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends PropertyDefinition>)newValue);
				return;
			case EventPackage.EVENT__RULE_SET:
				setRuleSet((RuleSet)newValue);
				return;
			case EventPackage.EVENT__SPECIFIED_TIME:
				setSpecifiedTime((Long)newValue);
				return;
			case EventPackage.EVENT__SERIALIZATION_FORMAT:
				setSerializationFormat((Integer)newValue);
				return;
			case EventPackage.EVENT__NAMESPACE_ENTRIES:
				getNamespaceEntries().clear();
				getNamespaceEntries().addAll((Collection<? extends NamespaceEntry>)newValue);
				return;
			case EventPackage.EVENT__REGISTRY_IMPORT_ENTRIES:
				getRegistryImportEntries().clear();
				getRegistryImportEntries().addAll((Collection<? extends ImportRegistryEntry>)newValue);
				return;
			case EventPackage.EVENT__SUB_EVENTS_PATH:
				getSubEventsPath().clear();
				getSubEventsPath().addAll((Collection<? extends String>)newValue);
				return;
			case EventPackage.EVENT__EXPIRY_ACTION:
				setExpiryAction((Rule)newValue);
				return;
			case EventPackage.EVENT__PAYLOAD:
				setPayload((EObject)newValue);
				return;
			case EventPackage.EVENT__RETRY_ON_EXCEPTION:
				setRetryOnException((Boolean)newValue);
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
			case EventPackage.EVENT__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case EventPackage.EVENT__SUPER_EVENT:
				setSuperEvent((Event)null);
				return;
			case EventPackage.EVENT__SUPER_EVENT_PATH:
				setSuperEventPath(SUPER_EVENT_PATH_EDEFAULT);
				return;
			case EventPackage.EVENT__SUB_EVENTS:
				getSubEvents().clear();
				return;
			case EventPackage.EVENT__ANCESTOR_PROPERTIES:
				getAncestorProperties().clear();
				return;
			case EventPackage.EVENT__PAYLOAD_STRING:
				setPayloadString(PAYLOAD_STRING_EDEFAULT);
				return;
			case EventPackage.EVENT__TTL:
				setTtl(TTL_EDEFAULT);
				return;
			case EventPackage.EVENT__TTL_UNITS:
				setTtlUnits(TTL_UNITS_EDEFAULT);
				return;
			case EventPackage.EVENT__PROPERTIES:
				getProperties().clear();
				return;
			case EventPackage.EVENT__RULE_SET:
				setRuleSet((RuleSet)null);
				return;
			case EventPackage.EVENT__SPECIFIED_TIME:
				setSpecifiedTime(SPECIFIED_TIME_EDEFAULT);
				return;
			case EventPackage.EVENT__SERIALIZATION_FORMAT:
				setSerializationFormat(SERIALIZATION_FORMAT_EDEFAULT);
				return;
			case EventPackage.EVENT__NAMESPACE_ENTRIES:
				getNamespaceEntries().clear();
				return;
			case EventPackage.EVENT__REGISTRY_IMPORT_ENTRIES:
				getRegistryImportEntries().clear();
				return;
			case EventPackage.EVENT__SUB_EVENTS_PATH:
				getSubEventsPath().clear();
				return;
			case EventPackage.EVENT__EXPIRY_ACTION:
				setExpiryAction((Rule)null);
				return;
			case EventPackage.EVENT__PAYLOAD:
				setPayload((EObject)null);
				return;
			case EventPackage.EVENT__RETRY_ON_EXCEPTION:
				setRetryOnException(RETRY_ON_EXCEPTION_EDEFAULT);
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
			case EventPackage.EVENT__TYPE:
				return type != TYPE_EDEFAULT;
			case EventPackage.EVENT__SUPER_EVENT:
				return superEvent != null;
			case EventPackage.EVENT__SUPER_EVENT_PATH:
				return SUPER_EVENT_PATH_EDEFAULT == null ? superEventPath != null : !SUPER_EVENT_PATH_EDEFAULT.equals(superEventPath);
			case EventPackage.EVENT__SUB_EVENTS:
				return subEvents != null && !subEvents.isEmpty();
			case EventPackage.EVENT__ANCESTOR_PROPERTIES:
				return ancestorProperties != null && !ancestorProperties.isEmpty();
			case EventPackage.EVENT__PAYLOAD_STRING:
				return PAYLOAD_STRING_EDEFAULT == null ? payloadString != null : !PAYLOAD_STRING_EDEFAULT.equals(payloadString);
			case EventPackage.EVENT__TTL:
				return TTL_EDEFAULT == null ? ttl != null : !TTL_EDEFAULT.equals(ttl);
			case EventPackage.EVENT__TTL_UNITS:
				return ttlUnits != TTL_UNITS_EDEFAULT;
			case EventPackage.EVENT__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case EventPackage.EVENT__RULE_SET:
				return ruleSet != null;
			case EventPackage.EVENT__SPECIFIED_TIME:
				return specifiedTime != SPECIFIED_TIME_EDEFAULT;
			case EventPackage.EVENT__SERIALIZATION_FORMAT:
				return serializationFormat != SERIALIZATION_FORMAT_EDEFAULT;
			case EventPackage.EVENT__NAMESPACE_ENTRIES:
				return namespaceEntries != null && !namespaceEntries.isEmpty();
			case EventPackage.EVENT__REGISTRY_IMPORT_ENTRIES:
				return registryImportEntries != null && !registryImportEntries.isEmpty();
			case EventPackage.EVENT__SUB_EVENTS_PATH:
				return subEventsPath != null && !subEventsPath.isEmpty();
			case EventPackage.EVENT__EXPIRY_ACTION:
				return expiryAction != null;
			case EventPackage.EVENT__PAYLOAD:
				return payload != null;
			case EventPackage.EVENT__RETRY_ON_EXCEPTION:
				return retryOnException != RETRY_ON_EXCEPTION_EDEFAULT;
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
		result.append(" (type: ");
		result.append(type);
		result.append(", superEventPath: ");
		result.append(superEventPath);
		result.append(", payloadString: ");
		result.append(payloadString);
		result.append(", ttl: ");
		result.append(ttl);
		result.append(", ttlUnits: ");
		result.append(ttlUnits);
		result.append(", specifiedTime: ");
		result.append(specifiedTime);
		result.append(", serializationFormat: ");
		result.append(serializationFormat);
		result.append(", subEventsPath: ");
		result.append(subEventsPath);
		result.append(", retryOnException: ");
		result.append(retryOnException);
		result.append(')');
		return result.toString();
	}
	/**
	 * @generated not
	 */
	public PropertyDefinition getPropertyDefinition(String name , boolean all) {
		// TODO Auto-generated method stub
		if (name == null) return null;
		for (PropertyDefinition propdDefinition : getProperties()){
			if (name.equals(propdDefinition.getName())){
				return propdDefinition;
			}
		}
		if (all){
			Event superEvent = this.getSuperEvent();
			while (superEvent != null){
				for (PropertyDefinition propDef : superEvent.getProperties()){
					if (name.equals(propDef.getName())) return propDef;						
				}
				superEvent = superEvent.getSuperEvent();
			}
		}
		return null;
	}
	
	public PropertyDefinition getPropertyDefinition(String name){
		if (name == null) return null;
		PropertyDefinition propDef = getPropertyDefinition(name, false);
		// check for sub concepts also
		if (propDef == null){
			List<String> subEventsPath = this.getSubEventsPath();
			for (String subEventPath : subEventsPath){
				if (subEventPath.trim().length() > 0){
					Event subEvent = null;
					if (EVENT_TYPE.SIMPLE_EVENT == this.getType()){
						subEvent = CommonIndexUtils.getSimpleEvent(ownerProjectName, subEventPath);
					}
					else if (EVENT_TYPE.TIME_EVENT == this.getType()){
						subEvent = CommonIndexUtils.getTimeEvent(ownerProjectName, subEventPath);
					}
					if (subEvent != null){
						for (PropertyDefinition pDef : subEvent.getProperties()){
							if (name.equals(pDef.getName())){
								return pDef;
							}
						}
					}
				}
			}
		}
		return propDef;
	
	}
	/**
	 * @generated not
	 */
	public boolean isA(Event event) {
	    if (event == null) {
	        return false;
	    }
	    if (this.getType() != event.getType()){
	    	return false;
	    }

	    Event ptr = this;
	    while (ptr != null) {
	    	/* Found a match */
	    	// can't use equals here, as the events could have been loaded in two different places (thus, == would fail)
	    	if (ptr.getFullPath().equals(event.getFullPath())) {
	    		return true;
	    	}

	    	/* Advance the pointer */
	    	Event temp = ptr;
	    	ptr = ptr.getSuperEvent();
	    	/*
	         if (ptr == null){	        	 
	        	 String superEventPath = temp.getSuperEventPath();
	        	 if (superEventPath != null && superEventPath.trim().length() > 0){
	        		 if (EVENT_TYPE.TIME_EVENT == this.getType()){
	        			 ptr = IndexUtils.getTimeEvent(ownerProjectName, superEventPath);
	        		 }
	        		 else if (EVENT_TYPE.SIMPLE_EVENT == this.getType()){
	        			 ptr = IndexUtils.getSimpleEvent(ownerProjectName, superEventPath);
	        		 }

	        	 }
	         }
	    	 */
	    }
	     return false;
	}
	/**
	 * @generated not
	 */
	public PropertyDefinition getAttributeDefinition (String attributeName){
        if (attributeName.equals(BASE_ATTRIBUTE_NAMES[0])) {
           // return new DefaultMutableEventPropertyDefinition(null, BASE_ATTRIBUTE_NAMES[0], (RDFPrimitiveTerm) RDFTypes.LONG);
            PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
            propDef.setName(BASE_ATTRIBUTE_NAMES[0]);
            propDef.setType(PROPERTY_TYPES.LONG);
            return propDef;
        } else if (attributeName.equals(BASE_ATTRIBUTE_NAMES[1])) {
            //return new DefaultMutableEventPropertyDefinition(null, BASE_ATTRIBUTE_NAMES[1], (RDFPrimitiveTerm) RDFTypes.STRING);
            PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
            propDef.setName(BASE_ATTRIBUTE_NAMES[1]);
            propDef.setType(PROPERTY_TYPES.STRING);
            return propDef;
        } else if (attributeName.equals(BASE_ATTRIBUTE_NAMES[2])) {
            //return new DefaultMutableEventPropertyDefinition(null, BASE_ATTRIBUTE_NAMES[2], (RDFPrimitiveTerm) RDFTypes.LONG);
            PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
            propDef.setName(BASE_ATTRIBUTE_NAMES[2]);
            propDef.setType(PROPERTY_TYPES.LONG);
            return propDef;
        } else if (attributeName.equals("closure")) {
            if (EVENT_TYPE.TIME_EVENT == this.getType()) {
                //return new DefaultMutableEventPropertyDefinition(null, "closure", (RDFPrimitiveTerm) RDFTypes.STRING);
                PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
                propDef.setName("closure");
                propDef.setType(PROPERTY_TYPES.STRING);
                return propDef;
            }
        } else if (attributeName.equals("scheduledTime")) {
            if (EVENT_TYPE.TIME_EVENT == this.getType()) {
               // return new DefaultMutableEventPropertyDefinition(null, "scheduledTime", (RDFPrimitiveTerm) RDFTypes.DATETIME);
                PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
                propDef.setName("scheduledTime");
                propDef.setType(PROPERTY_TYPES.DATE_TIME);
                return propDef;
            }
        } else if (attributeName.equals("interval")) {
            if (EVENT_TYPE.TIME_EVENT == this.getType()) {
                //return new DefaultMutableEventPropertyDefinition(null, "interval", (RDFPrimitiveTerm) RDFTypes.LONG);
                PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
                propDef.setName("interval");
                propDef.setType(PROPERTY_TYPES.LONG);
                return propDef;
            }
        } else if (attributeName.equals("payload")) {
            if (EVENT_TYPE.SIMPLE_EVENT == this.getType()) {
                //return new DefaultMutableEventPropertyDefinition(null, "payload", (RDFPrimitiveTerm) RDFTypes.STRING);
                PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
                propDef.setName("payload");
                propDef.setType(PROPERTY_TYPES.STRING);
                return propDef;
            }
        }
        return null;	
	}
	/**
	 * @generated not
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		
		EList<ModelError> errorList = super.getModelErrors();
		
		String superEventResPath = getSuperEventPath();
		if (!CommonValidationUtils.isStringEmpty(superEventResPath)){
			// check if event is not a soap event
			if (!isSoapEvent()){
				Event superEvent = getSuperEvent();
				if (superEvent == null){
					ModelError error = ValidationFactory.eINSTANCE.createModelError();
//					String msg = Messages.getString("EventResource.invalidSuper");
					List<Object> args = new ArrayList<Object>();
					args.add(getFullPath());
					args.add(getSuperEventPath());
					String formattedMsg = CommonValidationUtils.formatMessage("EventResource.invalidSuper", args);
					error.setMessage(formattedMsg);
					error.setSource(this);
					errorList.add(error);
				}
			} else {
				// validate soap event payload
				String payLoadSchema = getPayloadString();
		
				boolean isValid = CommonValidationUtils.isEventPayLoadSchemaValid(isSoapEvent(), payLoadSchema);					
				/*if (!isValid){
					ModelError error = ValidationFactory.eINSTANCE.createModelError();
					String msg = Messages.getString("EventResource.invalidPayLoadSchema");
					List<Object> args = new ArrayList<Object>();
					args.add(getFullPath());
					String formattedMsg = CommonValidationUtils.formatMessage(msg, args);
					error.setMessage(formattedMsg);
					error.setSource(this);
					errorList.add(error);
				}		*/	
			}		
		}
		//Added by Anand - 01/17/2011 to fix BE-10395
		if (TimeOutUnitsUtils.isValid(getTtlUnits()) == false) {
			ModelError error = ValidationFactory.eINSTANCE.createModelError();
			List<Object> args = new ArrayList<Object>();
			args.add(getFullPath());
			String formattedMsg = CommonValidationUtils.formatMessage("EventResource.invalidTTLUnits", args);
			error.setMessage(formattedMsg);
			error.setSource(this);
			errorList.add(error);
		}		
		
		// Import Registry Entry Validation
//		List<ImportRegistryEntry> impoList = getRegistryImportEntries();
//		for (ImportRegistryEntry entry : impoList){
//			if (entry != null){
//				String location = entry.getLocation();
//				String nameSpace = entry.getNamespace();
//				//TODO
//			}
//		}
		// Validate Domain Instances attached with Domain Property Definitions
//		for (PropertyDefinition pd : this.getProperties()){
//			List<ModelError> errList = pd.getModelErrors();
//			errorList.addAll(errList);
//		}
		return errorList;
	}
	
	
} //EventImpl