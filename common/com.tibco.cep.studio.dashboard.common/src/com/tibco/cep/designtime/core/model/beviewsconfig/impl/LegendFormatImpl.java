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

import com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Legend Format</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LegendFormatImpl#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LegendFormatImpl#getAnchor <em>Anchor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LegendFormatImpl extends BEViewsElementImpl implements LegendFormat {
	/**
	 * The default value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected static final OrientationEnum ORIENTATION_EDEFAULT = OrientationEnum.VERTICAL;

	/**
	 * The cached value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected OrientationEnum orientation = ORIENTATION_EDEFAULT;

	/**
	 * This is true if the Orientation attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean orientationESet;

	/**
	 * The default value of the '{@link #getAnchor() <em>Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnchor()
	 * @generated
	 * @ordered
	 */
	protected static final AnchorEnum ANCHOR_EDEFAULT = AnchorEnum.NORTH;

	/**
	 * The cached value of the '{@link #getAnchor() <em>Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnchor()
	 * @generated
	 * @ordered
	 */
	protected AnchorEnum anchor = ANCHOR_EDEFAULT;

	/**
	 * This is true if the Anchor attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean anchorESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LegendFormatImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getLegendFormat();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrientationEnum getOrientation() {
		return orientation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrientation(OrientationEnum newOrientation) {
		OrientationEnum oldOrientation = orientation;
		orientation = newOrientation == null ? ORIENTATION_EDEFAULT : newOrientation;
		boolean oldOrientationESet = orientationESet;
		orientationESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LEGEND_FORMAT__ORIENTATION, oldOrientation, orientation, !oldOrientationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOrientation() {
		OrientationEnum oldOrientation = orientation;
		boolean oldOrientationESet = orientationESet;
		orientation = ORIENTATION_EDEFAULT;
		orientationESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LEGEND_FORMAT__ORIENTATION, oldOrientation, ORIENTATION_EDEFAULT, oldOrientationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOrientation() {
		return orientationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnchorEnum getAnchor() {
		return anchor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnchor(AnchorEnum newAnchor) {
		AnchorEnum oldAnchor = anchor;
		anchor = newAnchor == null ? ANCHOR_EDEFAULT : newAnchor;
		boolean oldAnchorESet = anchorESet;
		anchorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LEGEND_FORMAT__ANCHOR, oldAnchor, anchor, !oldAnchorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAnchor() {
		AnchorEnum oldAnchor = anchor;
		boolean oldAnchorESet = anchorESet;
		anchor = ANCHOR_EDEFAULT;
		anchorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LEGEND_FORMAT__ANCHOR, oldAnchor, ANCHOR_EDEFAULT, oldAnchorESet));
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
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.LEGEND_FORMAT__ORIENTATION:
				return getOrientation();
			case BEViewsConfigurationPackage.LEGEND_FORMAT__ANCHOR:
				return getAnchor();
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
			case BEViewsConfigurationPackage.LEGEND_FORMAT__ORIENTATION:
				setOrientation((OrientationEnum)newValue);
				return;
			case BEViewsConfigurationPackage.LEGEND_FORMAT__ANCHOR:
				setAnchor((AnchorEnum)newValue);
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
			case BEViewsConfigurationPackage.LEGEND_FORMAT__ORIENTATION:
				unsetOrientation();
				return;
			case BEViewsConfigurationPackage.LEGEND_FORMAT__ANCHOR:
				unsetAnchor();
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
			case BEViewsConfigurationPackage.LEGEND_FORMAT__ORIENTATION:
				return isSetOrientation();
			case BEViewsConfigurationPackage.LEGEND_FORMAT__ANCHOR:
				return isSetAnchor();
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
		result.append(" (orientation: ");
		if (orientationESet) result.append(orientation); else result.append("<unset>");
		result.append(", anchor: ");
		if (anchorESet) result.append(anchor); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //LegendFormatImpl
