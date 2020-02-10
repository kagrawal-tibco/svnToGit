/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getSuperEvent <em>Super Event</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getSuperEventPath <em>Super Event Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getSubEvents <em>Sub Events</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getAncestorProperties <em>Ancestor Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getPayloadString <em>Payload String</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getTtl <em>Ttl</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getTtlUnits <em>Ttl Units</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getSpecifiedTime <em>Specified Time</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getSerializationFormat <em>Serialization Format</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getNamespaceEntries <em>Namespace Entries</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getRegistryImportEntries <em>Registry Import Entries</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getSubEventsPath <em>Sub Events Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getExpiryAction <em>Expiry Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#getPayload <em>Payload</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.Event#isRetryOnException <em>Retry On Exception</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent()
 * @model abstract="true"
 * @generated
 */
public interface Event extends RuleParticipant {
	/**
	 * @generated NOT
	 */
	 String[] BASE_ATTRIBUTE_NAMES = {"id", "extId", "ttl"};
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.event.EVENT_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.event.EVENT_TYPE
	 * @see #setType(EVENT_TYPE)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_Type()
	 * @model
	 * @generated
	 */
	EVENT_TYPE getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.event.EVENT_TYPE
	 * @see #getType()
	 * @generated
	 */
	void setType(EVENT_TYPE value);

	/**
	 * Returns the value of the '<em><b>Super Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Event</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Event</em>' reference.
	 * @see #setSuperEvent(Event)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_SuperEvent()
	 * @model transient="true"
	 * @generated
	 */
	Event getSuperEvent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getSuperEvent <em>Super Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Event</em>' reference.
	 * @see #getSuperEvent()
	 * @generated
	 */
	void setSuperEvent(Event value);

	/**
	 * Returns the value of the '<em><b>Super Event Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Event Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Event Path</em>' attribute.
	 * @see #setSuperEventPath(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_SuperEventPath()
	 * @model
	 * @generated
	 */
	String getSuperEventPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getSuperEventPath <em>Super Event Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Event Path</em>' attribute.
	 * @see #getSuperEventPath()
	 * @generated
	 */
	void setSuperEventPath(String value);

	/**
	 * Returns the value of the '<em><b>Sub Events</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.event.Event}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Events</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Events</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_SubEvents()
	 * @model
	 * @generated
	 */
	EList<Event> getSubEvents();

	/**
	 * Returns the value of the '<em><b>Ancestor Properties</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ancestor Properties</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ancestor Properties</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_AncestorProperties()
	 * @model
	 * @generated
	 */
	EList<EObject> getAncestorProperties();

	/**
	 * Returns the value of the '<em><b>Payload</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Payload</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Payload</em>' reference.
	 * @see #setPayload(EObject)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_Payload()
	 * @model
	 * @generated
	 */
	EObject getPayload();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getPayload <em>Payload</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Payload</em>' reference.
	 * @see #getPayload()
	 * @generated
	 */
	void setPayload(EObject value);

	/**
	 * Returns the value of the '<em><b>Retry On Exception</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Retry On Exception</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Retry On Exception</em>' attribute.
	 * @see #setRetryOnException(boolean)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_RetryOnException()
	 * @model default="true"
	 * @generated
	 */
	boolean isRetryOnException();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#isRetryOnException <em>Retry On Exception</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retry On Exception</em>' attribute.
	 * @see #isRetryOnException()
	 * @generated
	 */
	void setRetryOnException(boolean value);

	/**
	 * Returns the value of the '<em><b>Payload String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Payload String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Payload String</em>' attribute.
	 * @see #setPayloadString(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_PayloadString()
	 * @model
	 * @generated
	 */
	String getPayloadString();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getPayloadString <em>Payload String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Payload String</em>' attribute.
	 * @see #getPayloadString()
	 * @generated
	 */
	void setPayloadString(String value);

	/**
	 * Returns the value of the '<em><b>Ttl</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ttl</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ttl</em>' attribute.
	 * @see #setTtl(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_Ttl()
	 * @model default="0"
	 * @generated
	 */
	String getTtl();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getTtl <em>Ttl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ttl</em>' attribute.
	 * @see #getTtl()
	 * @generated
	 */
	void setTtl(String value);

	/**
	 * Returns the value of the '<em><b>Ttl Units</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.TIMEOUT_UNITS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ttl Units</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ttl Units</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
	 * @see #setTtlUnits(TIMEOUT_UNITS)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_TtlUnits()
	 * @model
	 * @generated
	 */
	TIMEOUT_UNITS getTtlUnits();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getTtlUnits <em>Ttl Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ttl Units</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
	 * @see #getTtlUnits()
	 * @generated
	 */
	void setTtlUnits(TIMEOUT_UNITS value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.element.PropertyDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyDefinition> getProperties();

	/**
	 * Returns the value of the '<em><b>Rule Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Set</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Set</em>' reference.
	 * @see #setRuleSet(RuleSet)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_RuleSet()
	 * @model
	 * @generated
	 */
	RuleSet getRuleSet();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getRuleSet <em>Rule Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Set</em>' reference.
	 * @see #getRuleSet()
	 * @generated
	 */
	void setRuleSet(RuleSet value);

	/**
	 * Returns the value of the '<em><b>Specified Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Time</em>' attribute.
	 * @see #setSpecifiedTime(long)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_SpecifiedTime()
	 * @model
	 * @generated
	 */
	long getSpecifiedTime();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getSpecifiedTime <em>Specified Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Time</em>' attribute.
	 * @see #getSpecifiedTime()
	 * @generated
	 */
	void setSpecifiedTime(long value);

	/**
	 * Returns the value of the '<em><b>Serialization Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serialization Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serialization Format</em>' attribute.
	 * @see #setSerializationFormat(int)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_SerializationFormat()
	 * @model
	 * @generated
	 */
	int getSerializationFormat();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getSerializationFormat <em>Serialization Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Serialization Format</em>' attribute.
	 * @see #getSerializationFormat()
	 * @generated
	 */
	void setSerializationFormat(int value);

	/**
	 * Returns the value of the '<em><b>Namespace Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.event.NamespaceEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace Entries</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace Entries</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_NamespaceEntries()
	 * @model containment="true"
	 * @generated
	 */
	EList<NamespaceEntry> getNamespaceEntries();

	/**
	 * Returns the value of the '<em><b>Registry Import Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.event.ImportRegistryEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Registry Import Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Registry Import Entries</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_RegistryImportEntries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ImportRegistryEntry> getRegistryImportEntries();

	/**
	 * Returns the value of the '<em><b>Sub Events Path</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Events Path</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Events Path</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_SubEventsPath()
	 * @model
	 * @generated
	 */
	EList<String> getSubEventsPath();

	/**
	 * Returns the value of the '<em><b>Expiry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expiry Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expiry Action</em>' containment reference.
	 * @see #setExpiryAction(Rule)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEvent_ExpiryAction()
	 * @model containment="true"
	 * @generated
	 */
	Rule getExpiryAction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.Event#getExpiryAction <em>Expiry Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiry Action</em>' containment reference.
	 * @see #getExpiryAction()
	 * @generated
	 */
	void setExpiryAction(Rule value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<PropertyDefinition> getAllUserProperties();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isSoapEvent();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<DomainInstance> getAllDomainInstances();

	/**
	 * if localOnly is true then it does not check super Event for property definition else checks super Events also
	 * @generated NOT
	 */
	PropertyDefinition getPropertyDefinition(String name , boolean localOnly) ;
	/**
	 * it checks all super Events and sub Events 
	 * @generated NOT
	 */
	PropertyDefinition getPropertyDefinition(String name) ;
	/**
	 * @generated not
	 */
	boolean isA(Event concept);
	
	/**
	 * @generated not
	 */
	PropertyDefinition getAttributeDefinition (String attributeName);
		
	
} // Event
