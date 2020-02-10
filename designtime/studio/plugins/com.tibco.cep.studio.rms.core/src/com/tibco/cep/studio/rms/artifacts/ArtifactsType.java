/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;


/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.rms.artifacts.ArtifactsPackage#getArtifactsType()
 * @model
 * @generated
 */
public enum ArtifactsType implements Enumerator {
	/**
	 * The '<em><b>DECISIONTABLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DECISIONTABLE_VALUE
	 * @generated
	 * @ordered
	 */
	DECISIONTABLE(0, "DECISIONTABLE", "rulefunctionimpl"),

	/**
	 * The '<em><b>DOMAIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOMAIN_VALUE
	 * @generated
	 * @ordered
	 */
	DOMAIN(1, "DOMAIN", "domain"),

	/**
	 * The '<em><b>RULE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_VALUE
	 * @generated
	 * @ordered
	 */
	RULE(2, "RULE", "rule"),

	/**
	 * The '<em><b>RULEFUNCTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULEFUNCTION_VALUE
	 * @generated
	 * @ordered
	 */
	RULEFUNCTION(3, "RULEFUNCTION", "rulefunction"),

	/**
	 * The '<em><b>CONCEPT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_VALUE
	 * @generated
	 * @ordered
	 */
	CONCEPT(4, "CONCEPT", "concept"),

	/**
	 * The '<em><b>EVENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	EVENT(5, "EVENT", "event"),

	/**
	 * The '<em><b>TIMEEVENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIMEEVENT_VALUE
	 * @generated
	 * @ordered
	 */
	TIMEEVENT(6, "TIMEEVENT", "time"),

	/**
	 * The '<em><b>SCORECARD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCORECARD_VALUE
	 * @generated
	 * @ordered
	 */
	SCORECARD(7, "SCORECARD", "scorecard"),

	/**
	 * The '<em><b>METRIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METRIC_VALUE
	 * @generated
	 * @ordered
	 */
	METRIC(8, "METRIC", "metric"),

	/**
	 * The '<em><b>CHANNEL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANNEL_VALUE
	 * @generated
	 * @ordered
	 */
	CHANNEL(9, "CHANNEL", "channel"),

	/**
	 * The '<em><b>STATEMACHINE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STATEMACHINE_VALUE
	 * @generated
	 * @ordered
	 */
	STATEMACHINE(10, "STATEMACHINE", "statemachine"),

	/**
	 * The '<em><b>SHAREDHTTP</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHAREDHTTP_VALUE
	 * @generated
	 * @ordered
	 */
	SHAREDHTTP(11, "SHAREDHTTP", "sharedhttp"),

	/**
	 * The '<em><b>RVTRANSPORT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RVTRANSPORT_VALUE
	 * @generated
	 * @ordered
	 */
	RVTRANSPORT(12, "RVTRANSPORT", "rvtransport"),

	/**
	 * The '<em><b>SHAREDJMSCON</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHAREDJMSCON_VALUE
	 * @generated
	 * @ordered
	 */
	SHAREDJMSCON(13, "SHAREDJMSCON", "sharedjmscon"),

	/**
	 * The '<em><b>SHAREDJDBC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHAREDJDBC_VALUE
	 * @generated
	 * @ordered
	 */
	SHAREDJDBC(14, "SHAREDJDBC", "sharedjdbc"),

	/**
	 * The '<em><b>SHAREDJNDICONFIG</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHAREDJNDICONFIG_VALUE
	 * @generated
	 * @ordered
	 */
	SHAREDJNDICONFIG(15, "SHAREDJNDICONFIG", "sharedjndiconfig"),

	/**
	 * The '<em><b>IDENTITY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IDENTITY_VALUE
	 * @generated
	 * @ordered
	 */
	IDENTITY(16, "IDENTITY", "id"),

	/**
	 * The '<em><b>GLOBALVARIABLES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GLOBALVARIABLES_VALUE
	 * @generated
	 * @ordered
	 */
	GLOBALVARIABLES(17, "GLOBALVARIABLES", "substvar"),
	
	/**
	 * The '<em><b>RULETEMPLATE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULETEMPLATE_VALUE
	 * @generated NOT
	 * @ordered
	 */
	RULETEMPLATE(18, "RULETEMPLATE", "ruletemplate"),
	
	/**
	 * The '<em><b>RULETEMPLATEVIEW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULETEMPLATEVIEW_VALUE
	 * @generated NOT
	 * @ordered
	 */
	RULETEMPLATEVIEW(19, "RULETEMPLATEVIEW", "ruletemplateview"),
	
	/**
	 * The '<em><b>RULETEMPLATEINSTANCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULETEMPLATEVIEW_VALUE
	 * @generated NOT
	 * @ordered
	 */
	RULETEMPLATEINSTANCE(20, "RULETEMPLATEINSTANCE", "ruletemplateinstance"),
	
	/**
	 * The '<em><b>WSDL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WSDL_VALUE
	 * @generated NOT
	 * @ordered
	 */
	WSDL(21, "WSDL", "wsdl"),
	
	/**
	 * The '<em><b>XSD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XSD_VALUE
	 * @generated NOT
	 * @ordered
	 */
	XSD(22, "XSD", "xsd"),
	
	/**
	 * The '<em><b>XSD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BEPROCESS_VALUE
	 * @generated NOT
	 * @ordered
	 */
	BEPROCESS(23, "BEPROCESS", "beprocess"),
	
	/**
	 * The '<em><b>SHAREDASCON</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHAREDASCON_VALUE
	 * @generated NOT
	 * @ordered
	 */
	SHAREDASCON(24, "SHAREDASCON", "sharedascon"),
	
	/**
	 * The '<em><b>SHAREDSB</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHAREDSB_VALUE
	 * @generated NOT
	 * @ordered
	 */
	SHAREDSB(25, "SHAREDSB", "sharedsb"),
	
	/**
	 * The '<em><b>CDD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CDD_VALUE
	 * @generated NOT
	 * @ordered
	 */
	CDD(26, "CDD", "cdd");

	/**
	 * The '<em><b>DECISIONTABLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DECISIONTABLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DECISIONTABLE
	 * @model literal="rulefunctionimpl"
	 * @generated
	 * @ordered
	 */
	public static final int DECISIONTABLE_VALUE = 0;

	/**
	 * The '<em><b>DOMAIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DOMAIN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DOMAIN
	 * @model literal="domain"
	 * @generated
	 * @ordered
	 */
	public static final int DOMAIN_VALUE = 1;

	/**
	 * The '<em><b>RULE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE
	 * @model literal="rule"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_VALUE = 2;

	/**
	 * The '<em><b>RULEFUNCTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULEFUNCTION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULEFUNCTION
	 * @model literal="rulefunction"
	 * @generated
	 * @ordered
	 */
	public static final int RULEFUNCTION_VALUE = 3;

	/**
	 * The '<em><b>CONCEPT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CONCEPT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONCEPT
	 * @model literal="concept"
	 * @generated
	 * @ordered
	 */
	public static final int CONCEPT_VALUE = 4;

	/**
	 * The '<em><b>EVENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EVENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EVENT
	 * @model literal="event"
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_VALUE = 5;

	/**
	 * The '<em><b>TIMEEVENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TIMEEVENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIMEEVENT
	 * @model literal="time"
	 * @generated
	 * @ordered
	 */
	public static final int TIMEEVENT_VALUE = 6;

	/**
	 * The '<em><b>SCORECARD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SCORECARD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCORECARD
	 * @model literal="scorecard"
	 * @generated
	 * @ordered
	 */
	public static final int SCORECARD_VALUE = 7;

	/**
	 * The '<em><b>METRIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>METRIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METRIC
	 * @model literal="metric"
	 * @generated
	 * @ordered
	 */
	public static final int METRIC_VALUE = 8;

	/**
	 * The '<em><b>CHANNEL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANNEL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANNEL
	 * @model literal="channel"
	 * @generated
	 * @ordered
	 */
	public static final int CHANNEL_VALUE = 9;

	/**
	 * The '<em><b>STATEMACHINE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>STATEMACHINE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STATEMACHINE
	 * @model literal="statemachine"
	 * @generated
	 * @ordered
	 */
	public static final int STATEMACHINE_VALUE = 10;

	/**
	 * The '<em><b>SHAREDHTTP</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHAREDHTTP</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHAREDHTTP
	 * @model literal="sharedhttp"
	 * @generated
	 * @ordered
	 */
	public static final int SHAREDHTTP_VALUE = 11;

	/**
	 * The '<em><b>RVTRANSPORT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RVTRANSPORT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RVTRANSPORT
	 * @model literal="rvtransport"
	 * @generated
	 * @ordered
	 */
	public static final int RVTRANSPORT_VALUE = 12;

	/**
	 * The '<em><b>SHAREDJMSCON</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHAREDJMSCON</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHAREDJMSCON
	 * @model literal="sharedjmscon"
	 * @generated
	 * @ordered
	 */
	public static final int SHAREDJMSCON_VALUE = 13;

	/**
	 * The '<em><b>SHAREDJDBC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHAREDJDBC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHAREDJDBC
	 * @model literal="sharedjdbc"
	 * @generated
	 * @ordered
	 */
	public static final int SHAREDJDBC_VALUE = 14;

	/**
	 * The '<em><b>SHAREDJNDICONFIG</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHAREDJNDICONFIG</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHAREDJNDICONFIG
	 * @model literal="sharedjndiconfig"
	 * @generated
	 * @ordered
	 */
	public static final int SHAREDJNDICONFIG_VALUE = 15;

	/**
	 * The '<em><b>IDENTITY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>IDENTITY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IDENTITY
	 * @model literal="id"
	 * @generated
	 * @ordered
	 */
	public static final int IDENTITY_VALUE = 16;

	/**
	 * The '<em><b>GLOBALVARIABLES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>GLOBALVARIABLES</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GLOBALVARIABLES
	 * @model literal="substvar"
	 * @generated
	 * @ordered
	 */
	public static final int GLOBALVARIABLES_VALUE = 17;
	
	/**
	 * The '<em><b>RULETEMPLATE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULETEMPLATE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULETEMPLATE
	 * @model literal="ruletemplate"
	 * @generated NOT
	 * @ordered
	 */
	public static final int RULETEMPLATE_VALUE = 18;
	
	/**
	 * The '<em><b>RULETEMPLATEVIEW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULETEMPLATEVIEW</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULETEMPLATEVIEW
	 * @model literal="ruletemplateview"
	 * @generated NOT
	 * @ordered
	 */
	public static final int RULETEMPLATEVIEW_VALUE = 19;
	
	/**
	 * The '<em><b>RULETEMPLATEINSTANCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULETEMPLATEINSTANCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULETEMPLATEVIEW
	 * @model literal="ruletemplateinstance"
	 * @generated NOT
	 * @ordered
	 */
	public static final int RULETEMPLATEINSTANCE_VALUE = 20;
	
	/**
	 * The '<em><b>WSDL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WSDL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WSDL
	 * @model literal="wsdl"
	 * @generated NOT
	 * @ordered
	 */
	public static final int WSDL_VALUE = 21;
	
	/**
	 * The '<em><b>XSD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XSD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XSD
	 * @model literal="xsd"
	 * @generated NOT
	 * @ordered
	 */
	public static final int XSD_VALUE = 22;
	
	/**
	 * The '<em><b>BEPROCESS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BEPROCESS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BEPROCESS
	 * @model literal="beprocess"
	 * @generated NOT
	 * @ordered
	 */
	public static final int BEPROCESS_VALUE = 23;
	
	/**
	 * The '<em><b>SHAREDASCON</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHAREDASCON</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHAREDASCON
	 * @model literal="sharedascon"
	 * @generated NOT
	 * @ordered
	 */
	public static final int SHAREDASCON_VALUE = 24;
	
	/**
	 * The '<em><b>SHAREDSB</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHAREDSB</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHAREDSB
	 * @model literal="sharedsb"
	 * @generated NOT
	 * @ordered
	 */
	public static final int SHAREDSB_VALUE = 25;
	
	/**
	 * The '<em><b>CDD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CDD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CDD
	 * @model literal="cdd"
	 * @generated NOT
	 * @ordered
	 */
	public static final int CDD_VALUE = 26;

	/**
	 * An array of all the '<em><b>Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ArtifactsType[] VALUES_ARRAY =
		new ArtifactsType[] {
			DECISIONTABLE,
			DOMAIN,
			RULE,
			RULEFUNCTION,
			CONCEPT,
			EVENT,
			TIMEEVENT,
			SCORECARD,
			METRIC,
			CHANNEL,
			STATEMACHINE,
			SHAREDHTTP,
			RVTRANSPORT,
			SHAREDJMSCON,
			SHAREDJDBC,
			SHAREDJNDICONFIG,
			IDENTITY,
			GLOBALVARIABLES,
			RULETEMPLATE,
			RULETEMPLATEVIEW,
			RULETEMPLATEINSTANCE,
			WSDL,
			XSD,
			BEPROCESS,
			SHAREDASCON,
			SHAREDSB,
			CDD
		};

	/**
	 * A public read-only list of all the '<em><b>Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ArtifactsType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ArtifactsType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ArtifactsType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ArtifactsType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ArtifactsType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ArtifactsType get(int value) {
		switch (value) {
			case DECISIONTABLE_VALUE: return DECISIONTABLE;
			case DOMAIN_VALUE: return DOMAIN;
			case RULE_VALUE: return RULE;
			case RULEFUNCTION_VALUE: return RULEFUNCTION;
			case CONCEPT_VALUE: return CONCEPT;
			case EVENT_VALUE: return EVENT;
			case TIMEEVENT_VALUE: return TIMEEVENT;
			case SCORECARD_VALUE: return SCORECARD;
			case METRIC_VALUE: return METRIC;
			case CHANNEL_VALUE: return CHANNEL;
			case STATEMACHINE_VALUE: return STATEMACHINE;
			case SHAREDHTTP_VALUE: return SHAREDHTTP;
			case RVTRANSPORT_VALUE: return RVTRANSPORT;
			case SHAREDJMSCON_VALUE: return SHAREDJMSCON;
			case SHAREDJDBC_VALUE: return SHAREDJDBC;
			case SHAREDJNDICONFIG_VALUE: return SHAREDJNDICONFIG;
			case IDENTITY_VALUE: return IDENTITY;
			case GLOBALVARIABLES_VALUE: return GLOBALVARIABLES;
			case RULETEMPLATE_VALUE: return RULETEMPLATE;
			case RULETEMPLATEVIEW_VALUE: return RULETEMPLATEVIEW;
			case RULETEMPLATEINSTANCE_VALUE: return RULETEMPLATEINSTANCE;
			case WSDL_VALUE: return WSDL;
			case XSD_VALUE: return XSD;
			case BEPROCESS_VALUE: return BEPROCESS;
			case SHAREDASCON_VALUE: return SHAREDASCON;
			case SHAREDSB_VALUE: return SHAREDSB;
			case CDD_VALUE: return CDD;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ArtifactsType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ArtifactsType
