/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.EntitySetConfig;
import com.tibco.be.util.config.cdd.LDMConnectionConfig;
import com.tibco.be.util.config.cdd.LiveViewAgentClassConfig;
import com.tibco.be.util.config.cdd.LoadConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import com.tibco.be.util.config.cdd.PublisherConfig;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Live View Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LiveViewAgentClassConfigImpl#getLdmConnection <em>Ldm Connection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LiveViewAgentClassConfigImpl#getPublisher <em>Publisher</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LiveViewAgentClassConfigImpl#getEntitySet <em>Entity Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LiveViewAgentClassConfigImpl#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LiveViewAgentClassConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LiveViewAgentClassConfigImpl extends AgentClassConfigImpl implements LiveViewAgentClassConfig {
	/**
	 * The cached value of the '{@link #getLdmConnection() <em>Ldm Connection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLdmConnection()
	 * @generated
	 * @ordered
	 */
	protected LDMConnectionConfig ldmConnection;

	/**
	 * The cached value of the '{@link #getPublisher() <em>Publisher</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublisher()
	 * @generated
	 * @ordered
	 */
	protected PublisherConfig publisher;

	/**
	 * The cached value of the '{@link #getEntitySet() <em>Entity Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntitySet()
	 * @generated
	 * @ordered
	 */
	protected EntitySetConfig entitySet;

	/**
	 * The cached value of the '{@link #getLoad() <em>Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoad()
	 * @generated
	 * @ordered
	 */
	protected LoadConfig load;

	/**
	 * The cached value of the '{@link #getPropertyGroup() <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyGroup()
	 * @generated
	 * @ordered
	 */
	protected PropertyGroupConfig propertyGroup;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LiveViewAgentClassConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLiveViewAgentClassConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LDMConnectionConfig getLdmConnection() {
		return ldmConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLdmConnection(LDMConnectionConfig newLdmConnection, NotificationChain msgs) {
		LDMConnectionConfig oldLdmConnection = ldmConnection;
		ldmConnection = newLdmConnection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION, oldLdmConnection, newLdmConnection);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLdmConnection(LDMConnectionConfig newLdmConnection) {
		if (newLdmConnection != ldmConnection) {
			NotificationChain msgs = null;
			if (ldmConnection != null)
				msgs = ((InternalEObject)ldmConnection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION, null, msgs);
			if (newLdmConnection != null)
				msgs = ((InternalEObject)newLdmConnection).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION, null, msgs);
			msgs = basicSetLdmConnection(newLdmConnection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION, newLdmConnection, newLdmConnection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PublisherConfig getPublisher() {
		return publisher;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPublisher(PublisherConfig newPublisher, NotificationChain msgs) {
		PublisherConfig oldPublisher = publisher;
		publisher = newPublisher;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER, oldPublisher, newPublisher);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublisher(PublisherConfig newPublisher) {
		if (newPublisher != publisher) {
			NotificationChain msgs = null;
			if (publisher != null)
				msgs = ((InternalEObject)publisher).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER, null, msgs);
			if (newPublisher != null)
				msgs = ((InternalEObject)newPublisher).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER, null, msgs);
			msgs = basicSetPublisher(newPublisher, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER, newPublisher, newPublisher));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntitySetConfig getEntitySet() {
		return entitySet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntitySet(EntitySetConfig newEntitySet, NotificationChain msgs) {
		EntitySetConfig oldEntitySet = entitySet;
		entitySet = newEntitySet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET, oldEntitySet, newEntitySet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntitySet(EntitySetConfig newEntitySet) {
		if (newEntitySet != entitySet) {
			NotificationChain msgs = null;
			if (entitySet != null)
				msgs = ((InternalEObject)entitySet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET, null, msgs);
			if (newEntitySet != null)
				msgs = ((InternalEObject)newEntitySet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET, null, msgs);
			msgs = basicSetEntitySet(newEntitySet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET, newEntitySet, newEntitySet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadConfig getLoad() {
		return load;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoad(LoadConfig newLoad, NotificationChain msgs) {
		LoadConfig oldLoad = load;
		load = newLoad;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD, oldLoad, newLoad);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoad(LoadConfig newLoad) {
		if (newLoad != load) {
			NotificationChain msgs = null;
			if (load != null)
				msgs = ((InternalEObject)load).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD, null, msgs);
			if (newLoad != null)
				msgs = ((InternalEObject)newLoad).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD, null, msgs);
			msgs = basicSetLoad(newLoad, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD, newLoad, newLoad));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyGroupConfig getPropertyGroup() {
		return propertyGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyGroup(PropertyGroupConfig newPropertyGroup, NotificationChain msgs) {
		PropertyGroupConfig oldPropertyGroup = propertyGroup;
		propertyGroup = newPropertyGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyGroup(PropertyGroupConfig newPropertyGroup) {
		if (newPropertyGroup != propertyGroup) {
			NotificationChain msgs = null;
			if (propertyGroup != null)
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION:
				return basicSetLdmConnection(null, msgs);
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER:
				return basicSetPublisher(null, msgs);
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET:
				return basicSetEntitySet(null, msgs);
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD:
				return basicSetLoad(null, msgs);
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return basicSetPropertyGroup(null, msgs);
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
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION:
				return getLdmConnection();
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER:
				return getPublisher();
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET:
				return getEntitySet();
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD:
				return getLoad();
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
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
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION:
				setLdmConnection((LDMConnectionConfig)newValue);
				return;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER:
				setPublisher((PublisherConfig)newValue);
				return;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET:
				setEntitySet((EntitySetConfig)newValue);
				return;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD:
				setLoad((LoadConfig)newValue);
				return;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)newValue);
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
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION:
				setLdmConnection((LDMConnectionConfig)null);
				return;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER:
				setPublisher((PublisherConfig)null);
				return;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET:
				setEntitySet((EntitySetConfig)null);
				return;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD:
				setLoad((LoadConfig)null);
				return;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)null);
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
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LDM_CONNECTION:
				return ldmConnection != null;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PUBLISHER:
				return publisher != null;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__ENTITY_SET:
				return entitySet != null;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__LOAD:
				return load != null;
			case CddPackage.LIVE_VIEW_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
		}
		return super.eIsSet(featureID);
	}

} //LiveViewAgentClassConfigImpl
