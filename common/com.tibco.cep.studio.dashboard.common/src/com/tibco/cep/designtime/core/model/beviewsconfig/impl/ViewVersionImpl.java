/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.ViewVersion;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewVersionImpl#getMajorVersionNumber <em>Major Version Number</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewVersionImpl#getMinorVersionNumber <em>Minor Version Number</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewVersionImpl#getPointVersionNumber <em>Point Version Number</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewVersionImpl#getDescription1 <em>Description1</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ViewVersionImpl extends BEViewsElementImpl implements ViewVersion {
	/**
	 * The default value of the '{@link #getMajorVersionNumber() <em>Major Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMajorVersionNumber()
	 * @generated
	 * @ordered
	 */
	protected static final String MAJOR_VERSION_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMajorVersionNumber() <em>Major Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMajorVersionNumber()
	 * @generated
	 * @ordered
	 */
	protected String majorVersionNumber = MAJOR_VERSION_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getMinorVersionNumber() <em>Minor Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinorVersionNumber()
	 * @generated
	 * @ordered
	 */
	protected static final String MINOR_VERSION_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinorVersionNumber() <em>Minor Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinorVersionNumber()
	 * @generated
	 * @ordered
	 */
	protected String minorVersionNumber = MINOR_VERSION_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getPointVersionNumber() <em>Point Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointVersionNumber()
	 * @generated
	 * @ordered
	 */
	protected static final String POINT_VERSION_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPointVersionNumber() <em>Point Version Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointVersionNumber()
	 * @generated
	 * @ordered
	 */
	protected String pointVersionNumber = POINT_VERSION_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription1() <em>Description1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription1()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION1_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription1() <em>Description1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription1()
	 * @generated
	 * @ordered
	 */
	protected String description1 = DESCRIPTION1_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ViewVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getViewVersion();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMajorVersionNumber() {
		return majorVersionNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMajorVersionNumber(String newMajorVersionNumber) {
		String oldMajorVersionNumber = majorVersionNumber;
		majorVersionNumber = newMajorVersionNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW_VERSION__MAJOR_VERSION_NUMBER, oldMajorVersionNumber, majorVersionNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMinorVersionNumber() {
		return minorVersionNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinorVersionNumber(String newMinorVersionNumber) {
		String oldMinorVersionNumber = minorVersionNumber;
		minorVersionNumber = newMinorVersionNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW_VERSION__MINOR_VERSION_NUMBER, oldMinorVersionNumber, minorVersionNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPointVersionNumber() {
		return pointVersionNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointVersionNumber(String newPointVersionNumber) {
		String oldPointVersionNumber = pointVersionNumber;
		pointVersionNumber = newPointVersionNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW_VERSION__POINT_VERSION_NUMBER, oldPointVersionNumber, pointVersionNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription1() {
		return description1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription1(String newDescription1) {
		String oldDescription1 = description1;
		description1 = newDescription1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW_VERSION__DESCRIPTION1, oldDescription1, description1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.VIEW_VERSION__MAJOR_VERSION_NUMBER:
				return getMajorVersionNumber();
			case BEViewsConfigurationPackage.VIEW_VERSION__MINOR_VERSION_NUMBER:
				return getMinorVersionNumber();
			case BEViewsConfigurationPackage.VIEW_VERSION__POINT_VERSION_NUMBER:
				return getPointVersionNumber();
			case BEViewsConfigurationPackage.VIEW_VERSION__DESCRIPTION1:
				return getDescription1();
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
			case BEViewsConfigurationPackage.VIEW_VERSION__MAJOR_VERSION_NUMBER:
				setMajorVersionNumber((String)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW_VERSION__MINOR_VERSION_NUMBER:
				setMinorVersionNumber((String)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW_VERSION__POINT_VERSION_NUMBER:
				setPointVersionNumber((String)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW_VERSION__DESCRIPTION1:
				setDescription1((String)newValue);
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
			case BEViewsConfigurationPackage.VIEW_VERSION__MAJOR_VERSION_NUMBER:
				setMajorVersionNumber(MAJOR_VERSION_NUMBER_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VIEW_VERSION__MINOR_VERSION_NUMBER:
				setMinorVersionNumber(MINOR_VERSION_NUMBER_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VIEW_VERSION__POINT_VERSION_NUMBER:
				setPointVersionNumber(POINT_VERSION_NUMBER_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VIEW_VERSION__DESCRIPTION1:
				setDescription1(DESCRIPTION1_EDEFAULT);
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
			case BEViewsConfigurationPackage.VIEW_VERSION__MAJOR_VERSION_NUMBER:
				return MAJOR_VERSION_NUMBER_EDEFAULT == null ? majorVersionNumber != null : !MAJOR_VERSION_NUMBER_EDEFAULT.equals(majorVersionNumber);
			case BEViewsConfigurationPackage.VIEW_VERSION__MINOR_VERSION_NUMBER:
				return MINOR_VERSION_NUMBER_EDEFAULT == null ? minorVersionNumber != null : !MINOR_VERSION_NUMBER_EDEFAULT.equals(minorVersionNumber);
			case BEViewsConfigurationPackage.VIEW_VERSION__POINT_VERSION_NUMBER:
				return POINT_VERSION_NUMBER_EDEFAULT == null ? pointVersionNumber != null : !POINT_VERSION_NUMBER_EDEFAULT.equals(pointVersionNumber);
			case BEViewsConfigurationPackage.VIEW_VERSION__DESCRIPTION1:
				return DESCRIPTION1_EDEFAULT == null ? description1 != null : !DESCRIPTION1_EDEFAULT.equals(description1);
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
		result.append(" (majorVersionNumber: ");
		result.append(majorVersionNumber);
		result.append(", minorVersionNumber: ");
		result.append(minorVersionNumber);
		result.append(", pointVersionNumber: ");
		result.append(pointVersionNumber);
		result.append(", description1: ");
		result.append(description1);
		result.append(')');
		return result.toString();
	}

} //ViewVersionImpl
