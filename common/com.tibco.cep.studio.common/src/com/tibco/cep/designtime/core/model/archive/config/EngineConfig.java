/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Engine Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.config.EngineConfig#getArchives <em>Archives</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.config.EngineConfig#getEntityResources <em>Entity Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage#getEngineConfig()
 * @model
 * @generated
 */
public interface EngineConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Archives</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Archives</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Archives</em>' containment reference.
	 * @see #setArchives(Archives)
	 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage#getEngineConfig_Archives()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Archives getArchives();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.config.EngineConfig#getArchives <em>Archives</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Archives</em>' containment reference.
	 * @see #getArchives()
	 * @generated
	 */
	void setArchives(Archives value);

	/**
	 * Returns the value of the '<em><b>Entity Resources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Resources</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Resources</em>' containment reference.
	 * @see #setEntityResources(EntityResources)
	 * @see com.tibco.cep.designtime.core.model.archive.config.ConfigPackage#getEngineConfig_EntityResources()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EntityResources getEntityResources();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.config.EngineConfig#getEntityResources <em>Entity Resources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Resources</em>' containment reference.
	 * @see #getEntityResources()
	 * @generated
	 */
	void setEntityResources(EntityResources value);

} // EngineConfig
