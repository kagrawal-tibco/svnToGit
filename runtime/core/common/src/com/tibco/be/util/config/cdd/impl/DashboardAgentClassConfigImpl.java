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

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DashboardAgentClassConfig;
import com.tibco.be.util.config.cdd.DestinationsConfig;
import com.tibco.be.util.config.cdd.FunctionsConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import com.tibco.be.util.config.cdd.RulesConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dashboard Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DashboardAgentClassConfigImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DashboardAgentClassConfigImpl#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DashboardAgentClassConfigImpl#getStartup <em>Startup</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DashboardAgentClassConfigImpl#getShutdown <em>Shutdown</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DashboardAgentClassConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DashboardAgentClassConfigImpl extends AgentClassConfigImpl implements DashboardAgentClassConfig {
	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected RulesConfig rules;

	/**
	 * The cached value of the '{@link #getDestinations() <em>Destinations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDestinations()
	 * @generated
	 * @ordered
	 */
	protected DestinationsConfig destinations;

	/**
	 * The cached value of the '{@link #getStartup() <em>Startup</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartup()
	 * @generated
	 * @ordered
	 */
	protected FunctionsConfig startup;

	/**
	 * The cached value of the '{@link #getShutdown() <em>Shutdown</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShutdown()
	 * @generated
	 * @ordered
	 */
	protected FunctionsConfig shutdown;

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
	protected DashboardAgentClassConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getDashboardAgentClassConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesConfig getRules() {
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRules(RulesConfig newRules, NotificationChain msgs) {
		RulesConfig oldRules = rules;
		rules = newRules;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES, oldRules, newRules);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRules(RulesConfig newRules) {
		if (newRules != rules) {
			NotificationChain msgs = null;
			if (rules != null)
				msgs = ((InternalEObject)rules).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES, null, msgs);
			if (newRules != null)
				msgs = ((InternalEObject)newRules).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES, null, msgs);
			msgs = basicSetRules(newRules, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES, newRules, newRules));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestinationsConfig getDestinations() {
		return destinations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestinations(DestinationsConfig newDestinations, NotificationChain msgs) {
		DestinationsConfig oldDestinations = destinations;
		destinations = newDestinations;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS, oldDestinations, newDestinations);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestinations(DestinationsConfig newDestinations) {
		if (newDestinations != destinations) {
			NotificationChain msgs = null;
			if (destinations != null)
				msgs = ((InternalEObject)destinations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS, null, msgs);
			if (newDestinations != null)
				msgs = ((InternalEObject)newDestinations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS, null, msgs);
			msgs = basicSetDestinations(newDestinations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS, newDestinations, newDestinations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionsConfig getStartup() {
		return startup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartup(FunctionsConfig newStartup, NotificationChain msgs) {
		FunctionsConfig oldStartup = startup;
		startup = newStartup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP, oldStartup, newStartup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartup(FunctionsConfig newStartup) {
		if (newStartup != startup) {
			NotificationChain msgs = null;
			if (startup != null)
				msgs = ((InternalEObject)startup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP, null, msgs);
			if (newStartup != null)
				msgs = ((InternalEObject)newStartup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP, null, msgs);
			msgs = basicSetStartup(newStartup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP, newStartup, newStartup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionsConfig getShutdown() {
		return shutdown;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShutdown(FunctionsConfig newShutdown, NotificationChain msgs) {
		FunctionsConfig oldShutdown = shutdown;
		shutdown = newShutdown;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN, oldShutdown, newShutdown);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShutdown(FunctionsConfig newShutdown) {
		if (newShutdown != shutdown) {
			NotificationChain msgs = null;
			if (shutdown != null)
				msgs = ((InternalEObject)shutdown).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN, null, msgs);
			if (newShutdown != null)
				msgs = ((InternalEObject)newShutdown).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN, null, msgs);
			msgs = basicSetShutdown(newShutdown, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN, newShutdown, newShutdown));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
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
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES:
				return basicSetRules(null, msgs);
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS:
				return basicSetDestinations(null, msgs);
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP:
				return basicSetStartup(null, msgs);
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN:
				return basicSetShutdown(null, msgs);
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES:
				return getRules();
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS:
				return getDestinations();
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP:
				return getStartup();
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN:
				return getShutdown();
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES:
				setRules((RulesConfig)newValue);
				return;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS:
				setDestinations((DestinationsConfig)newValue);
				return;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP:
				setStartup((FunctionsConfig)newValue);
				return;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN:
				setShutdown((FunctionsConfig)newValue);
				return;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES:
				setRules((RulesConfig)null);
				return;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS:
				setDestinations((DestinationsConfig)null);
				return;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP:
				setStartup((FunctionsConfig)null);
				return;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN:
				setShutdown((FunctionsConfig)null);
				return;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__RULES:
				return rules != null;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__DESTINATIONS:
				return destinations != null;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__STARTUP:
				return startup != null;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__SHUTDOWN:
				return shutdown != null;
			case CddPackage.DASHBOARD_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
		}
		return super.eIsSet(featureID);
	}

	
	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
        final Properties p = new Properties();
//        if (null != this.load) {
//            p.putAll(this.load.toProperties());
//        }
        if (null != this.propertyGroup) {
            p.putAll(this.propertyGroup.toProperties());
        }
        return p;
    }
	
	
} //DashboardAgentClassConfigImpl
