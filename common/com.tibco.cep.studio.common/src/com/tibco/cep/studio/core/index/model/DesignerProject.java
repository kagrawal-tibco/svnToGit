/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.service.channel.DriverManager;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Designer Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getEntityElements <em>Entity Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getDecisionTableElements <em>Decision Table Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getArchiveElements <em>Archive Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getRuleElements <em>Rule Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getRootPath <em>Root Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getLastPersisted <em>Last Persisted</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getReferencedProjects <em>Referenced Projects</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getDriverManager <em>Driver Manager</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getDomainInstanceElements <em>Domain Instance Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getArchivePath <em>Archive Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.DesignerProject#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject()
 * @model
 * @generated
 */
public interface DesignerProject extends ElementContainer {
	/**
	 * Returns the value of the '<em><b>Entity Elements</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.EntityElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Elements</em>' reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_EntityElements()
	 * @model
	 * @generated
	 */
	EList<EntityElement> getEntityElements();

	/**
	 * Returns the value of the '<em><b>Decision Table Elements</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.DecisionTableElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Decision Table Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Decision Table Elements</em>' reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_DecisionTableElements()
	 * @model
	 * @generated
	 */
	EList<DecisionTableElement> getDecisionTableElements();

	/**
	 * Returns the value of the '<em><b>Archive Elements</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.ArchiveElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Archive Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Archive Elements</em>' reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_ArchiveElements()
	 * @model
	 * @generated
	 */
	EList<ArchiveElement> getArchiveElements();

	/**
	 * Returns the value of the '<em><b>Rule Elements</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.RuleElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Elements</em>' reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_RuleElements()
	 * @model
	 * @generated
	 */
	EList<RuleElement> getRuleElements();

	/**
	 * Returns the value of the '<em><b>Root Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Path</em>' attribute.
	 * @see #setRootPath(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_RootPath()
	 * @model required="true"
	 * @generated
	 */
	String getRootPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getRootPath <em>Root Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Path</em>' attribute.
	 * @see #getRootPath()
	 * @generated
	 */
	void setRootPath(String value);

	/**
	 * Returns the value of the '<em><b>Last Persisted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Persisted</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Persisted</em>' attribute.
	 * @see #setLastPersisted(Date)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_LastPersisted()
	 * @model required="true"
	 * @generated
	 */
	Date getLastPersisted();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getLastPersisted <em>Last Persisted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Persisted</em>' attribute.
	 * @see #getLastPersisted()
	 * @generated
	 */
	void setLastPersisted(Date value);

	/**
	 * Returns the value of the '<em><b>Referenced Projects</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.DesignerProject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Projects</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Projects</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_ReferencedProjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<DesignerProject> getReferencedProjects();

	/**
	 * Returns the value of the '<em><b>Driver Manager</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driver Manager</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Driver Manager</em>' reference.
	 * @see #setDriverManager(DriverManager)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_DriverManager()
	 * @model transient="true"
	 * @generated
	 */
	DriverManager getDriverManager();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getDriverManager <em>Driver Manager</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driver Manager</em>' reference.
	 * @see #getDriverManager()
	 * @generated
	 */
	void setDriverManager(DriverManager value);

	/**
	 * Returns the value of the '<em><b>Domain Instance Elements</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.core.index.model.InstanceElement}&lt;?>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Instance Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Instance Elements</em>' containment reference list.
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_DomainInstanceElements()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<InstanceElement<?>> getDomainInstanceElements();

	/**
	 * Returns the value of the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Archive Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Archive Path</em>' attribute.
	 * @see #setArchivePath(String)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_ArchivePath()
	 * @model
	 * @generated
	 */
	String getArchivePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getArchivePath <em>Archive Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Archive Path</em>' attribute.
	 * @see #getArchivePath()
	 * @generated
	 */
	void setArchivePath(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(int)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getDesignerProject_Version()
	 * @model
	 * @generated
	 */
	int getVersion();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(int value);

	
} // DesignerProject
