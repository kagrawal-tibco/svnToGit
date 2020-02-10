/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology;

import java.util.Date;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Implementation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Implementation#getStyle <em>Style</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Implementation#getImplements <em>Implements</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Implementation#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Implementation#getLastModifiedBy <em>Last Modified By</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Implementation#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Implementation#isDirty <em>Dirty</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Implementation#isLocked <em>Locked</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.Implementation#isShowDescription <em>Show Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation()
 * @model abstract="true"
 * @generated
 */
public interface Implementation extends AbstractResource {
	/**
	 * Returns the value of the '<em><b>Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Style</em>' attribute.
	 * @see #setStyle(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation_Style()
	 * @model
	 * @generated
	 */
	String getStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Implementation#getStyle <em>Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Style</em>' attribute.
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(String value);

	/**
	 * Returns the value of the '<em><b>Implements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implements</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implements</em>' attribute.
	 * @see #setImplements(String)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation_Implements()
	 * @model required="true"
	 * @generated
	 */
	String getImplements();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Implementation#getImplements <em>Implements</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implements</em>' attribute.
	 * @see #getImplements()
	 * @generated
	 */
	void setImplements(String value);

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
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation_Version()
	 * @model required="true"
	 * @generated
	 */
	double getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Implementation#getVersion <em>Version</em>}' attribute.
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
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation_LastModifiedBy()
	 * @model
	 * @generated
	 */
	String getLastModifiedBy();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Implementation#getLastModifiedBy <em>Last Modified By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Modified By</em>' attribute.
	 * @see #getLastModifiedBy()
	 * @generated
	 */
	void setLastModifiedBy(String value);

	/**
	 * Returns the value of the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Modified</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Modified</em>' attribute.
	 * @see #setLastModified(Date)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation_LastModified()
	 * @model
	 * @generated
	 */
	Date getLastModified();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Implementation#getLastModified <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Modified</em>' attribute.
	 * @see #getLastModified()
	 * @generated
	 */
	void setLastModified(Date value);

	/**
	 * Returns the value of the '<em><b>Dirty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dirty</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dirty</em>' attribute.
	 * @see #setDirty(boolean)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation_Dirty()
	 * @model transient="true"
	 * @generated
	 */
	boolean isDirty();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Implementation#isDirty <em>Dirty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dirty</em>' attribute.
	 * @see #isDirty()
	 * @generated
	 */
	void setDirty(boolean value);

	/**
	 * Returns the value of the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locked</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locked</em>' attribute.
	 * @see #setLocked(boolean)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation_Locked()
	 * @model
	 * @generated
	 */
	boolean isLocked();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Implementation#isLocked <em>Locked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locked</em>' attribute.
	 * @see #isLocked()
	 * @generated
	 */
	void setLocked(boolean value);

	/**
	 * Returns the value of the '<em><b>Show Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Description</em>' attribute.
	 * @see #setShowDescription(boolean)
	 * @see com.tibco.cep.decisionproject.ontology.OntologyPackage#getImplementation_ShowDescription()
	 * @model
	 * @generated
	 */
	boolean isShowDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decisionproject.ontology.Implementation#isShowDescription <em>Show Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Description</em>' attribute.
	 * @see #isShowDescription()
	 * @generated
	 */
	void setShowDescription(boolean value);

} // Implementation
