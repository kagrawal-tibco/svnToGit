/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decisionproject.ontology.AccessControlCandidate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getDomainEntry <em>Domain Entry</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getResource <em>Resource</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.Domain#isOverride <em>Override</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getDbRef <em>Db Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomain()
 * @model
 * @generated
 */
public interface Domain extends AccessControlCandidate {
	/**
	 * Returns the value of the '<em><b>Domain Entry</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Entry</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Entry</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomain_DomainEntry()
	 * @model containment="true"
	 * @generated
	 */
	EList<DomainEntry> getDomainEntry();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomain_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Resource Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.decision.table.model.dtmodel.ResourceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Type</em>' attribute.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ResourceType
	 * @see #setResourceType(ResourceType)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomain_ResourceType()
	 * @model
	 * @generated
	 */
	ResourceType getResourceType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getResourceType <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Type</em>' attribute.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ResourceType
	 * @see #getResourceType()
	 * @generated
	 */
	void setResourceType(ResourceType value);

	/**
	 * Returns the value of the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' attribute.
	 * @see #setResource(String)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomain_Resource()
	 * @model
	 * @generated
	 */
	String getResource();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getResource <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource</em>' attribute.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(String value);

	/**
	 * Returns the value of the '<em><b>Override</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Override</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Override</em>' attribute.
	 * @see #setOverride(boolean)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomain_Override()
	 * @model default="true"
	 * @generated
	 */
	boolean isOverride();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#isOverride <em>Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Override</em>' attribute.
	 * @see #isOverride()
	 * @generated
	 */
	void setOverride(boolean value);

	/**
	 * Returns the value of the '<em><b>Db Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db Ref</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db Ref</em>' attribute.
	 * @see #setDbRef(String)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomain_DbRef()
	 * @model
	 * @generated
	 */
	String getDbRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getDbRef <em>Db Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db Ref</em>' attribute.
	 * @see #getDbRef()
	 * @generated
	 */
	void setDbRef(String value);

} // Domain
