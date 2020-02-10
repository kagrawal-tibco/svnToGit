/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionprojectmodel;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decisionproject.ontology.AccessControlCandidate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DomainModel#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DomainModel#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DomainModel#getLastModifiedBy <em>Last Modified By</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDomainModel()
 * @model
 * @generated
 */
public interface DomainModel extends AccessControlCandidate {
	/**
	 * Returns the value of the '<em><b>Domain</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.domainmodel.Domain}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' containment reference list.
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDomainModel_Domain()
	 * @model containment="true"
	 * @generated
	 */
	EList<Domain> getDomain();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(double)
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDomainModel_Version()
	 * @model default="0" required="true"
	 * @generated
	 */
	double getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionprojectmodel.DomainModel#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(double value);

	/**
	 * Returns the value of the '<em><b>Last Modified By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Modified By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Modified By</em>' attribute.
	 * @see #setLastModifiedBy(String)
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDomainModel_LastModifiedBy()
	 * @model
	 * @generated
	 */
	String getLastModifiedBy();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionprojectmodel.DomainModel#getLastModifiedBy <em>Last Modified By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Modified By</em>' attribute.
	 * @see #getLastModifiedBy()
	 * @generated
	 */
	void setLastModifiedBy(String value);

} // DomainModel
