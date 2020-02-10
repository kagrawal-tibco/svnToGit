/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon.impl;

import com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource;
import com.tibco.be.util.config.sharedresources.sharedjmscon.Config;
import com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bw Shared Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.BwSharedResourceImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.BwSharedResourceImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.BwSharedResourceImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.impl.BwSharedResourceImpl#getConfig <em>Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BwSharedResourceImpl extends EObjectImpl implements BwSharedResource {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
	protected static final ResourceType RESOURCE_TYPE_EDEFAULT = ResourceType.AE_SHARED_JMS_CONNECTION_KEY;

	/**
	 * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
	protected ResourceType resourceType = RESOURCE_TYPE_EDEFAULT;

	/**
	 * This is true if the Resource Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean resourceTypeESet;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfig() <em>Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfig()
	 * @generated
	 * @ordered
	 */
	protected Config config;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BwSharedResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SharedjmsconPackage.Literals.BW_SHARED_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.BW_SHARED_RESOURCE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceType(ResourceType newResourceType) {
		ResourceType oldResourceType = resourceType;
		resourceType = newResourceType == null ? RESOURCE_TYPE_EDEFAULT : newResourceType;
		boolean oldResourceTypeESet = resourceTypeESet;
		resourceTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.BW_SHARED_RESOURCE__RESOURCE_TYPE, oldResourceType, resourceType, !oldResourceTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetResourceType() {
		ResourceType oldResourceType = resourceType;
		boolean oldResourceTypeESet = resourceTypeESet;
		resourceType = RESOURCE_TYPE_EDEFAULT;
		resourceTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SharedjmsconPackage.BW_SHARED_RESOURCE__RESOURCE_TYPE, oldResourceType, RESOURCE_TYPE_EDEFAULT, oldResourceTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetResourceType() {
		return resourceTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.BW_SHARED_RESOURCE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConfig(Config newConfig, NotificationChain msgs) {
		Config oldConfig = config;
		config = newConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG, oldConfig, newConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfig(Config newConfig) {
		if (newConfig != config) {
			NotificationChain msgs = null;
			if (config != null)
				msgs = ((InternalEObject)config).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG, null, msgs);
			if (newConfig != null)
				msgs = ((InternalEObject)newConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG, null, msgs);
			msgs = basicSetConfig(newConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG, newConfig, newConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG:
				return basicSetConfig(null, msgs);
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
			case SharedjmsconPackage.BW_SHARED_RESOURCE__NAME:
				return getName();
			case SharedjmsconPackage.BW_SHARED_RESOURCE__RESOURCE_TYPE:
				return getResourceType();
			case SharedjmsconPackage.BW_SHARED_RESOURCE__DESCRIPTION:
				return getDescription();
			case SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG:
				return getConfig();
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
			case SharedjmsconPackage.BW_SHARED_RESOURCE__NAME:
				setName((String)newValue);
				return;
			case SharedjmsconPackage.BW_SHARED_RESOURCE__RESOURCE_TYPE:
				setResourceType((ResourceType)newValue);
				return;
			case SharedjmsconPackage.BW_SHARED_RESOURCE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG:
				setConfig((Config)newValue);
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
			case SharedjmsconPackage.BW_SHARED_RESOURCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SharedjmsconPackage.BW_SHARED_RESOURCE__RESOURCE_TYPE:
				unsetResourceType();
				return;
			case SharedjmsconPackage.BW_SHARED_RESOURCE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG:
				setConfig((Config)null);
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
			case SharedjmsconPackage.BW_SHARED_RESOURCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SharedjmsconPackage.BW_SHARED_RESOURCE__RESOURCE_TYPE:
				return isSetResourceType();
			case SharedjmsconPackage.BW_SHARED_RESOURCE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case SharedjmsconPackage.BW_SHARED_RESOURCE__CONFIG:
				return config != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", resourceType: ");
		if (resourceTypeESet) result.append(resourceType); else result.append("<unset>");
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //BwSharedResourceImpl
