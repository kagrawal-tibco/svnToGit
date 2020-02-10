/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Event#getTtl <em>Ttl</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Event#getTtlUnits <em>Ttl Units</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Event#getSuperEventPath <em>Super Event Path</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Event#getDefaultDestinationName <em>Default Destination Name</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Event#getExpiryAction <em>Expiry Action</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Event#getUserProperty <em>User Property</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Event#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends ParentResource, ArgumentResource {
	/**
	 * Returns the value of the '<em><b>Ttl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ttl</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ttl</em>' attribute.
	 * @see #setTtl(int)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getEvent_Ttl()
	 * @model
	 * @generated
	 */
	int getTtl();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Event#getTtl <em>Ttl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ttl</em>' attribute.
	 * @see #getTtl()
	 * @generated
	 */
	void setTtl(int value);

	/**
	 * Returns the value of the '<em><b>Ttl Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ttl Units</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ttl Units</em>' attribute.
	 * @see #setTtlUnits(int)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getEvent_TtlUnits()
	 * @model
	 * @generated
	 */
	int getTtlUnits();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Event#getTtlUnits <em>Ttl Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ttl Units</em>' attribute.
	 * @see #getTtlUnits()
	 * @generated
	 */
	void setTtlUnits(int value);

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
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getEvent_SuperEventPath()
	 * @model
	 * @generated
	 */
	String getSuperEventPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Event#getSuperEventPath <em>Super Event Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Event Path</em>' attribute.
	 * @see #getSuperEventPath()
	 * @generated
	 */
	void setSuperEventPath(String value);

	/**
	 * Returns the value of the '<em><b>Default Destination Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Destination Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Destination Name</em>' attribute.
	 * @see #setDefaultDestinationName(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getEvent_DefaultDestinationName()
	 * @model
	 * @generated
	 */
	String getDefaultDestinationName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Event#getDefaultDestinationName <em>Default Destination Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Destination Name</em>' attribute.
	 * @see #getDefaultDestinationName()
	 * @generated
	 */
	void setDefaultDestinationName(String value);

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
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getEvent_ExpiryAction()
	 * @model containment="true"
	 * @generated
	 */
	Rule getExpiryAction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Event#getExpiryAction <em>Expiry Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiry Action</em>' containment reference.
	 * @see #getExpiryAction()
	 * @generated
	 */
	void setExpiryAction(Rule value);

	/**
	 * Returns the value of the '<em><b>User Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decisionproject.ontology.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Property</em>' containment reference list.
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getEvent_UserProperty()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getUserProperty();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(int)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getEvent_Type()
	 * @model default="-1"
	 * @generated
	 */
	int getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Event#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(int value);

} // Event
