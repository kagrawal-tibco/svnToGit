/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.archive.config.Archives;
import com.tibco.cep.designtime.core.model.archive.config.ConfigPackage;
import com.tibco.cep.designtime.core.model.archive.config.EngineConfig;
import com.tibco.cep.designtime.core.model.archive.config.EntityResources;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Engine Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.config.impl.EngineConfigImpl#getArchives <em>Archives</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.config.impl.EngineConfigImpl#getEntityResources <em>Entity Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EngineConfigImpl extends EObjectImpl implements EngineConfig {
	/**
	 * The cached value of the '{@link #getArchives() <em>Archives</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchives()
	 * @generated
	 * @ordered
	 */
	protected Archives archives;

	/**
	 * The cached value of the '{@link #getEntityResources() <em>Entity Resources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityResources()
	 * @generated
	 * @ordered
	 */
	protected EntityResources entityResources;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EngineConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.ENGINE_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Archives getArchives() {
		return archives;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArchives(Archives newArchives, NotificationChain msgs) {
		Archives oldArchives = archives;
		archives = newArchives;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConfigPackage.ENGINE_CONFIG__ARCHIVES, oldArchives, newArchives);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArchives(Archives newArchives) {
		if (newArchives != archives) {
			NotificationChain msgs = null;
			if (archives != null)
				msgs = ((InternalEObject)archives).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConfigPackage.ENGINE_CONFIG__ARCHIVES, null, msgs);
			if (newArchives != null)
				msgs = ((InternalEObject)newArchives).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConfigPackage.ENGINE_CONFIG__ARCHIVES, null, msgs);
			msgs = basicSetArchives(newArchives, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.ENGINE_CONFIG__ARCHIVES, newArchives, newArchives));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityResources getEntityResources() {
		return entityResources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntityResources(EntityResources newEntityResources, NotificationChain msgs) {
		EntityResources oldEntityResources = entityResources;
		entityResources = newEntityResources;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES, oldEntityResources, newEntityResources);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntityResources(EntityResources newEntityResources) {
		if (newEntityResources != entityResources) {
			NotificationChain msgs = null;
			if (entityResources != null)
				msgs = ((InternalEObject)entityResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES, null, msgs);
			if (newEntityResources != null)
				msgs = ((InternalEObject)newEntityResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES, null, msgs);
			msgs = basicSetEntityResources(newEntityResources, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES, newEntityResources, newEntityResources));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigPackage.ENGINE_CONFIG__ARCHIVES:
				return basicSetArchives(null, msgs);
			case ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES:
				return basicSetEntityResources(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigPackage.ENGINE_CONFIG__ARCHIVES:
				return getArchives();
			case ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES:
				return getEntityResources();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConfigPackage.ENGINE_CONFIG__ARCHIVES:
				setArchives((Archives)newValue);
				return;
			case ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES:
				setEntityResources((EntityResources)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ConfigPackage.ENGINE_CONFIG__ARCHIVES:
				setArchives((Archives)null);
				return;
			case ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES:
				setEntityResources((EntityResources)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConfigPackage.ENGINE_CONFIG__ARCHIVES:
				return archives != null;
			case ConfigPackage.ENGINE_CONFIG__ENTITY_RESOURCES:
				return entityResources != null;
		}
		return super.eIsSet(featureID);
	}

} //EngineConfigImpl
