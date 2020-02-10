/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionprojectmodel;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decisionproject.ontology.Ontology;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Decision Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getOntology <em>Ontology</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getDomainModel <em>Domain Model</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getAuthToken <em>Auth Token</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getMd <em>Md</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDecisionProject()
 * @model
 * @generated
 */
public interface DecisionProject extends EObject {
	/**
	 * Returns the value of the '<em><b>Ontology</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ontology</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ontology</em>' containment reference.
	 * @see #setOntology(Ontology)
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDecisionProject_Ontology()
	 * @model containment="true"
	 * @generated
	 */
	Ontology getOntology();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getOntology <em>Ontology</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ontology</em>' containment reference.
	 * @see #getOntology()
	 * @generated
	 */
	void setOntology(Ontology value);

	/**
	 * Returns the value of the '<em><b>Domain Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Model</em>' reference.
	 * @see #setDomainModel(DomainModel)
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDecisionProject_DomainModel()
	 * @model
	 * @generated
	 */
	DomainModel getDomainModel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getDomainModel <em>Domain Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Model</em>' reference.
	 * @see #getDomainModel()
	 * @generated
	 */
	void setDomainModel(DomainModel value);

	/**
	 * Returns the value of the '<em><b>Auth Token</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auth Token</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auth Token</em>' attribute.
	 * @see #setAuthToken(String)
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDecisionProject_AuthToken()
	 * @model
	 * @generated
	 */
	String getAuthToken();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getAuthToken <em>Auth Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auth Token</em>' attribute.
	 * @see #getAuthToken()
	 * @generated
	 */
	void setAuthToken(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDecisionProject_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Md</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Md</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Md</em>' containment reference.
	 * @see #setMd(MetaData)
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDecisionProject_Md()
	 * @model containment="true"
	 * @generated
	 */
	MetaData getMd();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getMd <em>Md</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Md</em>' containment reference.
	 * @see #getMd()
	 * @generated
	 */
	void setMd(MetaData value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(double)
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage#getDecisionProject_Version()
	 * @model
	 * @generated
	 */
	double getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(double value);

} // DecisionProject
