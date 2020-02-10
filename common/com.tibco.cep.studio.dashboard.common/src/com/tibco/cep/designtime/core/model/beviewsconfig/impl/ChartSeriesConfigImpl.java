/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.ValueLabelStyle;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Chart Series Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartSeriesConfigImpl#getAnchor <em>Anchor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartSeriesConfigImpl#getValueLabelStyle <em>Value Label Style</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChartSeriesConfigImpl extends TwoDimSeriesConfigImpl implements ChartSeriesConfig {
	/**
	 * The default value of the '{@link #getAnchor() <em>Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnchor()
	 * @generated
	 * @ordered
	 */
	protected static final SeriesAnchorEnum ANCHOR_EDEFAULT = SeriesAnchorEnum.Q1;

	/**
	 * The cached value of the '{@link #getAnchor() <em>Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnchor()
	 * @generated
	 * @ordered
	 */
	protected SeriesAnchorEnum anchor = ANCHOR_EDEFAULT;

	/**
	 * This is true if the Anchor attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean anchorESet;

	/**
	 * The cached value of the '{@link #getValueLabelStyle() <em>Value Label Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueLabelStyle()
	 * @generated
	 * @ordered
	 */
	protected ValueLabelStyle valueLabelStyle;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChartSeriesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getChartSeriesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeriesAnchorEnum getAnchor() {
		return anchor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnchor(SeriesAnchorEnum newAnchor) {
		SeriesAnchorEnum oldAnchor = anchor;
		anchor = newAnchor == null ? ANCHOR_EDEFAULT : newAnchor;
		boolean oldAnchorESet = anchorESet;
		anchorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_SERIES_CONFIG__ANCHOR, oldAnchor, anchor, !oldAnchorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAnchor() {
		SeriesAnchorEnum oldAnchor = anchor;
		boolean oldAnchorESet = anchorESet;
		anchor = ANCHOR_EDEFAULT;
		anchorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CHART_SERIES_CONFIG__ANCHOR, oldAnchor, ANCHOR_EDEFAULT, oldAnchorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAnchor() {
		return anchorESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueLabelStyle getValueLabelStyle() {
		return valueLabelStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValueLabelStyle(ValueLabelStyle newValueLabelStyle, NotificationChain msgs) {
		ValueLabelStyle oldValueLabelStyle = valueLabelStyle;
		valueLabelStyle = newValueLabelStyle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE, oldValueLabelStyle, newValueLabelStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueLabelStyle(ValueLabelStyle newValueLabelStyle) {
		if (newValueLabelStyle != valueLabelStyle) {
			NotificationChain msgs = null;
			if (valueLabelStyle != null)
				msgs = ((InternalEObject)valueLabelStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE, null, msgs);
			if (newValueLabelStyle != null)
				msgs = ((InternalEObject)newValueLabelStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE, null, msgs);
			msgs = basicSetValueLabelStyle(newValueLabelStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE, newValueLabelStyle, newValueLabelStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE:
				return basicSetValueLabelStyle(null, msgs);
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
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__ANCHOR:
				return getAnchor();
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE:
				return getValueLabelStyle();
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
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__ANCHOR:
				setAnchor((SeriesAnchorEnum)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE:
				setValueLabelStyle((ValueLabelStyle)newValue);
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
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__ANCHOR:
				unsetAnchor();
				return;
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE:
				setValueLabelStyle((ValueLabelStyle)null);
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
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__ANCHOR:
				return isSetAnchor();
			case BEViewsConfigurationPackage.CHART_SERIES_CONFIG__VALUE_LABEL_STYLE:
				return valueLabelStyle != null;
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
		result.append(" (anchor: ");
		if (anchorESet) result.append(anchor); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ChartSeriesConfigImpl
