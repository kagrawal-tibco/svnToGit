/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.ecore.EClass;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.MemoryManagerConfig;
import com.tibco.be.util.packaging.Constants;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Memory Manager Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class MemoryManagerConfigImpl extends ObjectManagerConfigImpl implements MemoryManagerConfig {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MemoryManagerConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getMemoryManagerConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();
        p.setProperty(Constants.PROPERTY_NAME_OM_ENABLE, "false");
        return p;
    }
	
} //MemoryManagerConfigImpl
