/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule;
import com.tibco.cep.designtime.core.model.beviewsconfig.Alert;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataSource;
import com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam;
import com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionRuleImpl#getDataSource <em>Data Source</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionRuleImpl#getThreshold <em>Threshold</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionRuleImpl#getThresholdUnit <em>Threshold Unit</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionRuleImpl#getAlert <em>Alert</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionRuleImpl#getDrillableFields <em>Drillable Fields</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionRuleImpl#getParams <em>Params</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionRuleImpl extends BEViewsElementImpl implements ActionRule {
	/**
	 * The cached value of the '{@link #getDataSource() <em>Data Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataSource()
	 * @generated
	 * @ordered
	 */
	protected DataSource dataSource;

	/**
	 * The default value of the '{@link #getThreshold() <em>Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreshold()
	 * @generated
	 * @ordered
	 */
	protected static final int THRESHOLD_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getThreshold() <em>Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThreshold()
	 * @generated
	 * @ordered
	 */
	protected int threshold = THRESHOLD_EDEFAULT;

	/**
	 * This is true if the Threshold attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean thresholdESet;

	/**
	 * The default value of the '{@link #getThresholdUnit() <em>Threshold Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThresholdUnit()
	 * @generated
	 * @ordered
	 */
	protected static final ThresholdUnitEnum THRESHOLD_UNIT_EDEFAULT = ThresholdUnitEnum.COUNT;

	/**
	 * The cached value of the '{@link #getThresholdUnit() <em>Threshold Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThresholdUnit()
	 * @generated
	 * @ordered
	 */
	protected ThresholdUnitEnum thresholdUnit = THRESHOLD_UNIT_EDEFAULT;

	/**
	 * This is true if the Threshold Unit attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean thresholdUnitESet;

	/**
	 * The cached value of the '{@link #getAlert() <em>Alert</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlert()
	 * @generated
	 * @ordered
	 */
	protected EList<Alert> alert;

	/**
	 * The cached value of the '{@link #getDrillableFields() <em>Drillable Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDrillableFields()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyDefinition> drillableFields;

	/**
	 * The cached value of the '{@link #getParams() <em>Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParams()
	 * @generated
	 * @ordered
	 */
	protected EList<QueryParam> params;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getActionRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataSource getDataSource() {
		if (dataSource != null && dataSource.eIsProxy()) {
			InternalEObject oldDataSource = (InternalEObject)dataSource;
			dataSource = (DataSource)eResolveProxy(oldDataSource);
			if (dataSource != oldDataSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BEViewsConfigurationPackage.ACTION_RULE__DATA_SOURCE, oldDataSource, dataSource));
			}
		}
		return dataSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataSource basicGetDataSource() {
		return dataSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataSource(DataSource newDataSource) {
		DataSource oldDataSource = dataSource;
		dataSource = newDataSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ACTION_RULE__DATA_SOURCE, oldDataSource, dataSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThreshold(int newThreshold) {
		int oldThreshold = threshold;
		threshold = newThreshold;
		boolean oldThresholdESet = thresholdESet;
		thresholdESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD, oldThreshold, threshold, !oldThresholdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetThreshold() {
		int oldThreshold = threshold;
		boolean oldThresholdESet = thresholdESet;
		threshold = THRESHOLD_EDEFAULT;
		thresholdESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD, oldThreshold, THRESHOLD_EDEFAULT, oldThresholdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetThreshold() {
		return thresholdESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThresholdUnitEnum getThresholdUnit() {
		return thresholdUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThresholdUnit(ThresholdUnitEnum newThresholdUnit) {
		ThresholdUnitEnum oldThresholdUnit = thresholdUnit;
		thresholdUnit = newThresholdUnit == null ? THRESHOLD_UNIT_EDEFAULT : newThresholdUnit;
		boolean oldThresholdUnitESet = thresholdUnitESet;
		thresholdUnitESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD_UNIT, oldThresholdUnit, thresholdUnit, !oldThresholdUnitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetThresholdUnit() {
		ThresholdUnitEnum oldThresholdUnit = thresholdUnit;
		boolean oldThresholdUnitESet = thresholdUnitESet;
		thresholdUnit = THRESHOLD_UNIT_EDEFAULT;
		thresholdUnitESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD_UNIT, oldThresholdUnit, THRESHOLD_UNIT_EDEFAULT, oldThresholdUnitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetThresholdUnit() {
		return thresholdUnitESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Alert> getAlert() {
		if (alert == null) {
			alert = new EObjectContainmentEList<Alert>(Alert.class, this, BEViewsConfigurationPackage.ACTION_RULE__ALERT);
		}
		return alert;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyDefinition> getDrillableFields() {
		if (drillableFields == null) {
			drillableFields = new EObjectResolvingEList<PropertyDefinition>(PropertyDefinition.class, this, BEViewsConfigurationPackage.ACTION_RULE__DRILLABLE_FIELDS);
		}
		return drillableFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<QueryParam> getParams() {
		if (params == null) {
			params = new EObjectContainmentEList<QueryParam>(QueryParam.class, this, BEViewsConfigurationPackage.ACTION_RULE__PARAMS);
		}
		return params;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ACTION_RULE__ALERT:
				return ((InternalEList<?>)getAlert()).basicRemove(otherEnd, msgs);
			case BEViewsConfigurationPackage.ACTION_RULE__PARAMS:
				return ((InternalEList<?>)getParams()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.ACTION_RULE__DATA_SOURCE:
				if (resolve) return getDataSource();
				return basicGetDataSource();
			case BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD:
				return getThreshold();
			case BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD_UNIT:
				return getThresholdUnit();
			case BEViewsConfigurationPackage.ACTION_RULE__ALERT:
				return getAlert();
			case BEViewsConfigurationPackage.ACTION_RULE__DRILLABLE_FIELDS:
				return getDrillableFields();
			case BEViewsConfigurationPackage.ACTION_RULE__PARAMS:
				return getParams();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ACTION_RULE__DATA_SOURCE:
				setDataSource((DataSource)newValue);
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD:
				setThreshold((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD_UNIT:
				setThresholdUnit((ThresholdUnitEnum)newValue);
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__ALERT:
				getAlert().clear();
				getAlert().addAll((Collection<? extends Alert>)newValue);
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__DRILLABLE_FIELDS:
				getDrillableFields().clear();
				getDrillableFields().addAll((Collection<? extends PropertyDefinition>)newValue);
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__PARAMS:
				getParams().clear();
				getParams().addAll((Collection<? extends QueryParam>)newValue);
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
			case BEViewsConfigurationPackage.ACTION_RULE__DATA_SOURCE:
				setDataSource((DataSource)null);
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD:
				unsetThreshold();
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD_UNIT:
				unsetThresholdUnit();
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__ALERT:
				getAlert().clear();
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__DRILLABLE_FIELDS:
				getDrillableFields().clear();
				return;
			case BEViewsConfigurationPackage.ACTION_RULE__PARAMS:
				getParams().clear();
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
			case BEViewsConfigurationPackage.ACTION_RULE__DATA_SOURCE:
				return dataSource != null;
			case BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD:
				return isSetThreshold();
			case BEViewsConfigurationPackage.ACTION_RULE__THRESHOLD_UNIT:
				return isSetThresholdUnit();
			case BEViewsConfigurationPackage.ACTION_RULE__ALERT:
				return alert != null && !alert.isEmpty();
			case BEViewsConfigurationPackage.ACTION_RULE__DRILLABLE_FIELDS:
				return drillableFields != null && !drillableFields.isEmpty();
			case BEViewsConfigurationPackage.ACTION_RULE__PARAMS:
				return params != null && !params.isEmpty();
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
		result.append(" (threshold: ");
		if (thresholdESet) result.append(threshold); else result.append("<unset>");
		result.append(", thresholdUnit: ");
		if (thresholdUnitESet) result.append(thresholdUnit); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ActionRuleImpl
