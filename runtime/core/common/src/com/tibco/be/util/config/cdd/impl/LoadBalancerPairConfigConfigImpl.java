/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import java.util.Map;
import java.util.Properties;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Load Balancer Pair Config Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigConfigImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigConfigImpl#getChannel <em>Channel</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigConfigImpl#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigConfigImpl#getRouter <em>Router</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigConfigImpl#getReceiver <em>Receiver</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadBalancerPairConfigConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoadBalancerPairConfigConfigImpl extends ArtifactConfigImpl implements LoadBalancerPairConfigConfig {
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig name;

	/**
	 * The cached value of the '{@link #getChannel() <em>Channel</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChannel()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig channel;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig key;

	/**
	 * The cached value of the '{@link #getRouter() <em>Router</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRouter()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig router;

	/**
	 * The cached value of the '{@link #getReceiver() <em>Receiver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReceiver()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig receiver;

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
	protected LoadBalancerPairConfigConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLoadBalancerPairConfigConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetName(OverrideConfig newName, NotificationChain msgs) {
		OverrideConfig oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME, oldName, newName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(OverrideConfig newName) {
		if (newName != name) {
			NotificationChain msgs = null;
			if (name != null)
				msgs = ((InternalEObject)name).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME, null, msgs);
			if (newName != null)
				msgs = ((InternalEObject)newName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME, null, msgs);
			msgs = basicSetName(newName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME, newName, newName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getChannel() {
		return channel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChannel(OverrideConfig newChannel, NotificationChain msgs) {
		OverrideConfig oldChannel = channel;
		channel = newChannel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL, oldChannel, newChannel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChannel(OverrideConfig newChannel) {
		if (newChannel != channel) {
			NotificationChain msgs = null;
			if (channel != null)
				msgs = ((InternalEObject)channel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL, null, msgs);
			if (newChannel != null)
				msgs = ((InternalEObject)newChannel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL, null, msgs);
			msgs = basicSetChannel(newChannel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL, newChannel, newChannel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKey(OverrideConfig newKey, NotificationChain msgs) {
		OverrideConfig oldKey = key;
		key = newKey;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY, oldKey, newKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(OverrideConfig newKey) {
		if (newKey != key) {
			NotificationChain msgs = null;
			if (key != null)
				msgs = ((InternalEObject)key).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY, null, msgs);
			if (newKey != null)
				msgs = ((InternalEObject)newKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY, null, msgs);
			msgs = basicSetKey(newKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY, newKey, newKey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getRouter() {
		return router;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRouter(OverrideConfig newRouter, NotificationChain msgs) {
		OverrideConfig oldRouter = router;
		router = newRouter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER, oldRouter, newRouter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRouter(OverrideConfig newRouter) {
		if (newRouter != router) {
			NotificationChain msgs = null;
			if (router != null)
				msgs = ((InternalEObject)router).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER, null, msgs);
			if (newRouter != null)
				msgs = ((InternalEObject)newRouter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER, null, msgs);
			msgs = basicSetRouter(newRouter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER, newRouter, newRouter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getReceiver() {
		return receiver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReceiver(OverrideConfig newReceiver, NotificationChain msgs) {
		OverrideConfig oldReceiver = receiver;
		receiver = newReceiver;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER, oldReceiver, newReceiver);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReceiver(OverrideConfig newReceiver) {
		if (newReceiver != receiver) {
			NotificationChain msgs = null;
			if (receiver != null)
				msgs = ((InternalEObject)receiver).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER, null, msgs);
			if (newReceiver != null)
				msgs = ((InternalEObject)newReceiver).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER, null, msgs);
			msgs = basicSetReceiver(newReceiver, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER, newReceiver, newReceiver));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
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
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME:
				return basicSetName(null, msgs);
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL:
				return basicSetChannel(null, msgs);
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY:
				return basicSetKey(null, msgs);
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER:
				return basicSetRouter(null, msgs);
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER:
				return basicSetReceiver(null, msgs);
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME:
				return getName();
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL:
				return getChannel();
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY:
				return getKey();
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER:
				return getRouter();
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER:
				return getReceiver();
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME:
				setName((OverrideConfig)newValue);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL:
				setChannel((OverrideConfig)newValue);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY:
				setKey((OverrideConfig)newValue);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER:
				setRouter((OverrideConfig)newValue);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER:
				setReceiver((OverrideConfig)newValue);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME:
				setName((OverrideConfig)null);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL:
				setChannel((OverrideConfig)null);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY:
				setKey((OverrideConfig)null);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER:
				setRouter((OverrideConfig)null);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER:
				setReceiver((OverrideConfig)null);
				return;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME:
				return name != null;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL:
				return channel != null;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY:
				return key != null;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER:
				return router != null;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER:
				return receiver != null;
			case CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();
		if (null != this.propertyGroup) {
            p.putAll(this.propertyGroup.toProperties());
        }

        p.put(CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__NAME, getName());
        p.put(CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__CHANNEL, getChannel());
        p.put(CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__KEY, getKey());
        p.put(CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__ROUTER, getRouter());
        p.put(CddPackage.LOAD_BALANCER_PAIR_CONFIG_CONFIG__RECEIVER, getReceiver());

		return p;
	}
} //LoadBalancerPairConfigConfigImpl
