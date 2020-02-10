/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import org.eclipse.emf.ecore.EClass;

import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.CddPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class AgentClassConfigImpl extends ArtifactConfigImpl implements AgentClassConfig {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgentClassConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getAgentClassConfig();
	}

} //AgentClassConfigImpl
