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
import com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle;
import com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Value Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ValueGuidelineConfigImpl#getHeaderFormatStyle <em>Header Format Style</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ValueGuidelineConfigImpl extends BEViewsElementImpl implements ValueGuidelineConfig {
	/**
	 * The cached value of the '{@link #getHeaderFormatStyle() <em>Header Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderFormatStyle()
	 * @generated
	 * @ordered
	 */
	protected FormatStyle headerFormatStyle;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValueGuidelineConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getValueGuidelineConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FormatStyle getHeaderFormatStyle() {
		return headerFormatStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHeaderFormatStyle(FormatStyle newHeaderFormatStyle, NotificationChain msgs) {
		FormatStyle oldHeaderFormatStyle = headerFormatStyle;
		headerFormatStyle = newHeaderFormatStyle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE, oldHeaderFormatStyle, newHeaderFormatStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderFormatStyle(FormatStyle newHeaderFormatStyle) {
		if (newHeaderFormatStyle != headerFormatStyle) {
			NotificationChain msgs = null;
			if (headerFormatStyle != null)
				msgs = ((InternalEObject)headerFormatStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE, null, msgs);
			if (newHeaderFormatStyle != null)
				msgs = ((InternalEObject)newHeaderFormatStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE, null, msgs);
			msgs = basicSetHeaderFormatStyle(newHeaderFormatStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE, newHeaderFormatStyle, newHeaderFormatStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				return basicSetHeaderFormatStyle(null, msgs);
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
			case BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				return getHeaderFormatStyle();
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
			case BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				setHeaderFormatStyle((FormatStyle)newValue);
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
			case BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				setHeaderFormatStyle((FormatStyle)null);
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
			case BEViewsConfigurationPackage.VALUE_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				return headerFormatStyle != null;
		}
		return super.eIsSet(featureID);
	}

} //ValueGuidelineConfigImpl
