/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.EntityConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entity Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EntityConfigImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EntityConfigImpl#getFilter <em>Filter</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EntityConfigImpl#getEnableTableTrimming <em>Enable Table Trimming</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EntityConfigImpl#getTrimmingField <em>Trimming Field</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.EntityConfigImpl#getTrimmingRule <em>Trimming Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EntityConfigImpl extends ArtifactConfigImpl implements EntityConfig {
	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFilter() <em>Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig filter;

	/**
	 * The cached value of the '{@link #getEnableTableTrimming() <em>Enable Table Trimming</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnableTableTrimming()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig enableTableTrimming;

	/**
	 * The cached value of the '{@link #getTrimmingField() <em>Trimming Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrimmingField()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig trimmingField;

	/**
	 * The cached value of the '{@link #getTrimmingRule() <em>Trimming Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrimmingRule()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig trimmingRule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getEntityConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__URI, oldUri, uri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getFilter() {
		return filter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFilter(OverrideConfig newFilter, NotificationChain msgs) {
		OverrideConfig oldFilter = filter;
		filter = newFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__FILTER, oldFilter, newFilter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilter(OverrideConfig newFilter) {
		if (newFilter != filter) {
			NotificationChain msgs = null;
			if (filter != null)
				msgs = ((InternalEObject)filter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_CONFIG__FILTER, null, msgs);
			if (newFilter != null)
				msgs = ((InternalEObject)newFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_CONFIG__FILTER, null, msgs);
			msgs = basicSetFilter(newFilter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__FILTER, newFilter, newFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnableTableTrimming() {
		return enableTableTrimming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnableTableTrimming(OverrideConfig newEnableTableTrimming, NotificationChain msgs) {
		OverrideConfig oldEnableTableTrimming = enableTableTrimming;
		enableTableTrimming = newEnableTableTrimming;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING, oldEnableTableTrimming, newEnableTableTrimming);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableTableTrimming(OverrideConfig newEnableTableTrimming) {
		if (newEnableTableTrimming != enableTableTrimming) {
			NotificationChain msgs = null;
			if (enableTableTrimming != null)
				msgs = ((InternalEObject)enableTableTrimming).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING, null, msgs);
			if (newEnableTableTrimming != null)
				msgs = ((InternalEObject)newEnableTableTrimming).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING, null, msgs);
			msgs = basicSetEnableTableTrimming(newEnableTableTrimming, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING, newEnableTableTrimming, newEnableTableTrimming));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTrimmingField() {
		return trimmingField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrimmingField(OverrideConfig newTrimmingField, NotificationChain msgs) {
		OverrideConfig oldTrimmingField = trimmingField;
		trimmingField = newTrimmingField;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__TRIMMING_FIELD, oldTrimmingField, newTrimmingField);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrimmingField(OverrideConfig newTrimmingField) {
		if (newTrimmingField != trimmingField) {
			NotificationChain msgs = null;
			if (trimmingField != null)
				msgs = ((InternalEObject)trimmingField).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_CONFIG__TRIMMING_FIELD, null, msgs);
			if (newTrimmingField != null)
				msgs = ((InternalEObject)newTrimmingField).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_CONFIG__TRIMMING_FIELD, null, msgs);
			msgs = basicSetTrimmingField(newTrimmingField, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__TRIMMING_FIELD, newTrimmingField, newTrimmingField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getTrimmingRule() {
		return trimmingRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrimmingRule(OverrideConfig newTrimmingRule, NotificationChain msgs) {
		OverrideConfig oldTrimmingRule = trimmingRule;
		trimmingRule = newTrimmingRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__TRIMMING_RULE, oldTrimmingRule, newTrimmingRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrimmingRule(OverrideConfig newTrimmingRule) {
		if (newTrimmingRule != trimmingRule) {
			NotificationChain msgs = null;
			if (trimmingRule != null)
				msgs = ((InternalEObject)trimmingRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_CONFIG__TRIMMING_RULE, null, msgs);
			if (newTrimmingRule != null)
				msgs = ((InternalEObject)newTrimmingRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.ENTITY_CONFIG__TRIMMING_RULE, null, msgs);
			msgs = basicSetTrimmingRule(newTrimmingRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ENTITY_CONFIG__TRIMMING_RULE, newTrimmingRule, newTrimmingRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<Object, Object> toProperties() {
		return new java.util.Properties();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.ENTITY_CONFIG__FILTER:
				return basicSetFilter(null, msgs);
			case CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING:
				return basicSetEnableTableTrimming(null, msgs);
			case CddPackage.ENTITY_CONFIG__TRIMMING_FIELD:
				return basicSetTrimmingField(null, msgs);
			case CddPackage.ENTITY_CONFIG__TRIMMING_RULE:
				return basicSetTrimmingRule(null, msgs);
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
			case CddPackage.ENTITY_CONFIG__URI:
				return getUri();
			case CddPackage.ENTITY_CONFIG__FILTER:
				return getFilter();
			case CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING:
				return getEnableTableTrimming();
			case CddPackage.ENTITY_CONFIG__TRIMMING_FIELD:
				return getTrimmingField();
			case CddPackage.ENTITY_CONFIG__TRIMMING_RULE:
				return getTrimmingRule();
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
			case CddPackage.ENTITY_CONFIG__URI:
				setUri((String)newValue);
				return;
			case CddPackage.ENTITY_CONFIG__FILTER:
				setFilter((OverrideConfig)newValue);
				return;
			case CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING:
				setEnableTableTrimming((OverrideConfig)newValue);
				return;
			case CddPackage.ENTITY_CONFIG__TRIMMING_FIELD:
				setTrimmingField((OverrideConfig)newValue);
				return;
			case CddPackage.ENTITY_CONFIG__TRIMMING_RULE:
				setTrimmingRule((OverrideConfig)newValue);
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
			case CddPackage.ENTITY_CONFIG__URI:
				setUri(URI_EDEFAULT);
				return;
			case CddPackage.ENTITY_CONFIG__FILTER:
				setFilter((OverrideConfig)null);
				return;
			case CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING:
				setEnableTableTrimming((OverrideConfig)null);
				return;
			case CddPackage.ENTITY_CONFIG__TRIMMING_FIELD:
				setTrimmingField((OverrideConfig)null);
				return;
			case CddPackage.ENTITY_CONFIG__TRIMMING_RULE:
				setTrimmingRule((OverrideConfig)null);
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
			case CddPackage.ENTITY_CONFIG__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
			case CddPackage.ENTITY_CONFIG__FILTER:
				return filter != null;
			case CddPackage.ENTITY_CONFIG__ENABLE_TABLE_TRIMMING:
				return enableTableTrimming != null;
			case CddPackage.ENTITY_CONFIG__TRIMMING_FIELD:
				return trimmingField != null;
			case CddPackage.ENTITY_CONFIG__TRIMMING_RULE:
				return trimmingRule != null;
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
		result.append(" (uri: ");
		result.append(uri);
		result.append(')');
		return result.toString();
	}

} //EntityConfigImpl
