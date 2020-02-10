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
import com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Background Format</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BackgroundFormatImpl#getGradientDirection <em>Gradient Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BackgroundFormatImpl extends BEViewsElementImpl implements BackgroundFormat {
	/**
	 * The default value of the '{@link #getGradientDirection() <em>Gradient Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGradientDirection()
	 * @generated
	 * @ordered
	 */
	protected static final GradientDirectionEnum GRADIENT_DIRECTION_EDEFAULT = GradientDirectionEnum.TOPTOBOTTOM;

	/**
	 * The cached value of the '{@link #getGradientDirection() <em>Gradient Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGradientDirection()
	 * @generated
	 * @ordered
	 */
	protected GradientDirectionEnum gradientDirection = GRADIENT_DIRECTION_EDEFAULT;

	/**
	 * This is true if the Gradient Direction attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean gradientDirectionESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BackgroundFormatImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getBackgroundFormat();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GradientDirectionEnum getGradientDirection() {
		return gradientDirection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGradientDirection(GradientDirectionEnum newGradientDirection) {
		GradientDirectionEnum oldGradientDirection = gradientDirection;
		gradientDirection = newGradientDirection == null ? GRADIENT_DIRECTION_EDEFAULT : newGradientDirection;
		boolean oldGradientDirectionESet = gradientDirectionESet;
		gradientDirectionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.BACKGROUND_FORMAT__GRADIENT_DIRECTION, oldGradientDirection, gradientDirection, !oldGradientDirectionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetGradientDirection() {
		GradientDirectionEnum oldGradientDirection = gradientDirection;
		boolean oldGradientDirectionESet = gradientDirectionESet;
		gradientDirection = GRADIENT_DIRECTION_EDEFAULT;
		gradientDirectionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.BACKGROUND_FORMAT__GRADIENT_DIRECTION, oldGradientDirection, GRADIENT_DIRECTION_EDEFAULT, oldGradientDirectionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetGradientDirection() {
		return gradientDirectionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.BACKGROUND_FORMAT__GRADIENT_DIRECTION:
				return getGradientDirection();
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
			case BEViewsConfigurationPackage.BACKGROUND_FORMAT__GRADIENT_DIRECTION:
				setGradientDirection((GradientDirectionEnum)newValue);
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
			case BEViewsConfigurationPackage.BACKGROUND_FORMAT__GRADIENT_DIRECTION:
				unsetGradientDirection();
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
			case BEViewsConfigurationPackage.BACKGROUND_FORMAT__GRADIENT_DIRECTION:
				return isSetGradientDirection();
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
		result.append(" (gradientDirection: ");
		if (gradientDirectionESet) result.append(gradientDirection); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //BackgroundFormatImpl
