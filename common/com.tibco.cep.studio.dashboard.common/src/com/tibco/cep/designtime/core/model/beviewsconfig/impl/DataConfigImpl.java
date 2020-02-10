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
import com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataConfigImpl#getExtractor <em>Extractor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataConfigImpl#getFormatter <em>Formatter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DataConfigImpl extends BEViewsElementImpl implements DataConfig {
	/**
	 * The cached value of the '{@link #getExtractor() <em>Extractor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtractor()
	 * @generated
	 * @ordered
	 */
	protected DataExtractor extractor;

	/**
	 * The cached value of the '{@link #getFormatter() <em>Formatter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormatter()
	 * @generated
	 * @ordered
	 */
	protected DataFormat formatter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getDataConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataExtractor getExtractor() {
		return extractor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtractor(DataExtractor newExtractor, NotificationChain msgs) {
		DataExtractor oldExtractor = extractor;
		extractor = newExtractor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR, oldExtractor, newExtractor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtractor(DataExtractor newExtractor) {
		if (newExtractor != extractor) {
			NotificationChain msgs = null;
			if (extractor != null)
				msgs = ((InternalEObject)extractor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR, null, msgs);
			if (newExtractor != null)
				msgs = ((InternalEObject)newExtractor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR, null, msgs);
			msgs = basicSetExtractor(newExtractor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR, newExtractor, newExtractor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataFormat getFormatter() {
		return formatter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFormatter(DataFormat newFormatter, NotificationChain msgs) {
		DataFormat oldFormatter = formatter;
		formatter = newFormatter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER, oldFormatter, newFormatter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormatter(DataFormat newFormatter) {
		if (newFormatter != formatter) {
			NotificationChain msgs = null;
			if (formatter != null)
				msgs = ((InternalEObject)formatter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER, null, msgs);
			if (newFormatter != null)
				msgs = ((InternalEObject)newFormatter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER, null, msgs);
			msgs = basicSetFormatter(newFormatter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER, newFormatter, newFormatter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR:
				return basicSetExtractor(null, msgs);
			case BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER:
				return basicSetFormatter(null, msgs);
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
			case BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR:
				return getExtractor();
			case BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER:
				return getFormatter();
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
			case BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR:
				setExtractor((DataExtractor)newValue);
				return;
			case BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER:
				setFormatter((DataFormat)newValue);
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
			case BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR:
				setExtractor((DataExtractor)null);
				return;
			case BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER:
				setFormatter((DataFormat)null);
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
			case BEViewsConfigurationPackage.DATA_CONFIG__EXTRACTOR:
				return extractor != null;
			case BEViewsConfigurationPackage.DATA_CONFIG__FORMATTER:
				return formatter != null;
		}
		return super.eIsSet(featureID);
	}

} //DataConfigImpl
