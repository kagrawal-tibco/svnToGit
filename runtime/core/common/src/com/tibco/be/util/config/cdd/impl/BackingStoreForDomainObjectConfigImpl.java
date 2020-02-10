/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig;
import com.tibco.be.util.config.cdd.BackingStoreForPropertiesConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Backing Store For Domain Object Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreForDomainObjectConfigImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreForDomainObjectConfigImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreForDomainObjectConfigImpl#getTableName <em>Table Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BackingStoreForDomainObjectConfigImpl extends EObjectImpl implements BackingStoreForDomainObjectConfig {
	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected BackingStoreForPropertiesConfig properties;

	/**
	 * The cached value of the '{@link #getEnabled() <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnabled()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig enabled;

	/**
	 * The cached value of the '{@link #getTableName() <em>Table Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableName()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig tableName;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BackingStoreForDomainObjectConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getBackingStoreForDomainObjectConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackingStoreForPropertiesConfig getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(BackingStoreForPropertiesConfig newProperties, NotificationChain msgs) {
		BackingStoreForPropertiesConfig oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(BackingStoreForPropertiesConfig newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES, newProperties, newProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnabled(OverrideConfig newEnabled, NotificationChain msgs) {
		OverrideConfig oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED, oldEnabled, newEnabled);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnabled(OverrideConfig newEnabled) {
		if (newEnabled != enabled) {
			NotificationChain msgs = null;
			if (enabled != null)
				msgs = ((InternalEObject)enabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED, null, msgs);
			if (newEnabled != null)
				msgs = ((InternalEObject)newEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED, null, msgs);
			msgs = basicSetEnabled(newEnabled, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED, newEnabled, newEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTableName() {
		return tableName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTableName(OverrideConfig newTableName, NotificationChain msgs) {
		OverrideConfig oldTableName = tableName;
		tableName = newTableName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME, oldTableName, newTableName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTableName(OverrideConfig newTableName) {
		if (newTableName != tableName) {
			NotificationChain msgs = null;
			if (tableName != null)
				msgs = ((InternalEObject)tableName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME, null, msgs);
			if (newTableName != null)
				msgs = ((InternalEObject)newTableName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME, null, msgs);
			msgs = basicSetTableName(newTableName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME, newTableName, newTableName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();

		final Boolean enabled = Boolean.valueOf(CddTools.getValueFromMixed(this.enabled, false));
        
		p.put("be.engine.cluster.${DomainObject}.hasBackingStore", enabled.toString());
		
		if (enabled) {            
            CddTools.addEntryFromMixed(p, "be.engine.cluster.${DomainObject}.tableName", this.tableName, true);
//            CddTools.addEntryFromMixed(p, "be.engine.cluster.${DomainObject}.type", this.type, true);

            if (null != this.properties) {
                p.putAll(this.properties.toProperties());
            }
        }

        return p;
    }

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES:
				return basicSetProperties(null, msgs);
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED:
				return basicSetEnabled(null, msgs);
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME:
				return basicSetTableName(null, msgs);
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
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES:
				return getProperties();
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED:
				return getEnabled();
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME:
				return getTableName();
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
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES:
				setProperties((BackingStoreForPropertiesConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED:
				setEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME:
				setTableName((OverrideConfig)newValue);
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
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES:
				setProperties((BackingStoreForPropertiesConfig)null);
				return;
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED:
				setEnabled((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME:
				setTableName((OverrideConfig)null);
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
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__PROPERTIES:
				return properties != null;
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__ENABLED:
				return enabled != null;
			case CddPackage.BACKING_STORE_FOR_DOMAIN_OBJECT_CONFIG__TABLE_NAME:
				return tableName != null;
		}
		return super.eIsSet(featureID);
	}

} //BackingStoreForDomainObjectConfigImpl
