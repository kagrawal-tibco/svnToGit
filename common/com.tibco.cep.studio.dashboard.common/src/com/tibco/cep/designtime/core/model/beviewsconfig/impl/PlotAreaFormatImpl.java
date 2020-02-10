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
import com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.ForegroundFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Plot Area Format</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PlotAreaFormatImpl#getBackground <em>Background</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PlotAreaFormatImpl#getForeground <em>Foreground</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PlotAreaFormatImpl extends BEViewsElementImpl implements PlotAreaFormat {
	/**
	 * The cached value of the '{@link #getBackground() <em>Background</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackground()
	 * @generated
	 * @ordered
	 */
	protected BackgroundFormat background;

	/**
	 * The cached value of the '{@link #getForeground() <em>Foreground</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForeground()
	 * @generated
	 * @ordered
	 */
	protected ForegroundFormat foreground;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlotAreaFormatImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getPlotAreaFormat();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackgroundFormat getBackground() {
		return background;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackground(BackgroundFormat newBackground, NotificationChain msgs) {
		BackgroundFormat oldBackground = background;
		background = newBackground;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND, oldBackground, newBackground);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackground(BackgroundFormat newBackground) {
		if (newBackground != background) {
			NotificationChain msgs = null;
			if (background != null)
				msgs = ((InternalEObject)background).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND, null, msgs);
			if (newBackground != null)
				msgs = ((InternalEObject)newBackground).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND, null, msgs);
			msgs = basicSetBackground(newBackground, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND, newBackground, newBackground));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForegroundFormat getForeground() {
		return foreground;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetForeground(ForegroundFormat newForeground, NotificationChain msgs) {
		ForegroundFormat oldForeground = foreground;
		foreground = newForeground;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND, oldForeground, newForeground);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForeground(ForegroundFormat newForeground) {
		if (newForeground != foreground) {
			NotificationChain msgs = null;
			if (foreground != null)
				msgs = ((InternalEObject)foreground).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND, null, msgs);
			if (newForeground != null)
				msgs = ((InternalEObject)newForeground).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND, null, msgs);
			msgs = basicSetForeground(newForeground, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND, newForeground, newForeground));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND:
				return basicSetBackground(null, msgs);
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND:
				return basicSetForeground(null, msgs);
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
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND:
				return getBackground();
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND:
				return getForeground();
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
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND:
				setBackground((BackgroundFormat)newValue);
				return;
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND:
				setForeground((ForegroundFormat)newValue);
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
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND:
				setBackground((BackgroundFormat)null);
				return;
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND:
				setForeground((ForegroundFormat)null);
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
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__BACKGROUND:
				return background != null;
			case BEViewsConfigurationPackage.PLOT_AREA_FORMAT__FOREGROUND:
				return foreground != null;
		}
		return super.eIsSet(featureID);
	}

} //PlotAreaFormatImpl
