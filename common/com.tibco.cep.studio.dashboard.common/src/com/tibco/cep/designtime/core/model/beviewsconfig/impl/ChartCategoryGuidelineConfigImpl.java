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
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.PlacementEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Chart Category Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartCategoryGuidelineConfigImpl#getRelativePosition <em>Relative Position</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartCategoryGuidelineConfigImpl#getPlacement <em>Placement</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartCategoryGuidelineConfigImpl#getRotation <em>Rotation</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartCategoryGuidelineConfigImpl#getSkipFactor <em>Skip Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChartCategoryGuidelineConfigImpl extends CategoryGuidelineConfigImpl implements ChartCategoryGuidelineConfig {
	/**
	 * The default value of the '{@link #getRelativePosition() <em>Relative Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelativePosition()
	 * @generated
	 * @ordered
	 */
	protected static final RelativeAxisPositionEnum RELATIVE_POSITION_EDEFAULT = RelativeAxisPositionEnum.BELOW;

	/**
	 * The cached value of the '{@link #getRelativePosition() <em>Relative Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelativePosition()
	 * @generated
	 * @ordered
	 */
	protected RelativeAxisPositionEnum relativePosition = RELATIVE_POSITION_EDEFAULT;

	/**
	 * This is true if the Relative Position attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean relativePositionESet;

	/**
	 * The default value of the '{@link #getPlacement() <em>Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlacement()
	 * @generated
	 * @ordered
	 */
	protected static final PlacementEnum PLACEMENT_EDEFAULT = PlacementEnum.AUTOMATIC;

	/**
	 * The cached value of the '{@link #getPlacement() <em>Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlacement()
	 * @generated
	 * @ordered
	 */
	protected PlacementEnum placement = PLACEMENT_EDEFAULT;

	/**
	 * This is true if the Placement attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean placementESet;

	/**
	 * The default value of the '{@link #getRotation() <em>Rotation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRotation()
	 * @generated
	 * @ordered
	 */
	protected static final int ROTATION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRotation() <em>Rotation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRotation()
	 * @generated
	 * @ordered
	 */
	protected int rotation = ROTATION_EDEFAULT;

	/**
	 * This is true if the Rotation attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rotationESet;

	/**
	 * The default value of the '{@link #getSkipFactor() <em>Skip Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSkipFactor()
	 * @generated
	 * @ordered
	 */
	protected static final int SKIP_FACTOR_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSkipFactor() <em>Skip Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSkipFactor()
	 * @generated
	 * @ordered
	 */
	protected int skipFactor = SKIP_FACTOR_EDEFAULT;

	/**
	 * This is true if the Skip Factor attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean skipFactorESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChartCategoryGuidelineConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getChartCategoryGuidelineConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelativeAxisPositionEnum getRelativePosition() {
		return relativePosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelativePosition(RelativeAxisPositionEnum newRelativePosition) {
		RelativeAxisPositionEnum oldRelativePosition = relativePosition;
		relativePosition = newRelativePosition == null ? RELATIVE_POSITION_EDEFAULT : newRelativePosition;
		boolean oldRelativePositionESet = relativePositionESet;
		relativePositionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__RELATIVE_POSITION, oldRelativePosition, relativePosition, !oldRelativePositionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRelativePosition() {
		RelativeAxisPositionEnum oldRelativePosition = relativePosition;
		boolean oldRelativePositionESet = relativePositionESet;
		relativePosition = RELATIVE_POSITION_EDEFAULT;
		relativePositionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__RELATIVE_POSITION, oldRelativePosition, RELATIVE_POSITION_EDEFAULT, oldRelativePositionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRelativePosition() {
		return relativePositionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlacementEnum getPlacement() {
		return placement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlacement(PlacementEnum newPlacement) {
		PlacementEnum oldPlacement = placement;
		placement = newPlacement == null ? PLACEMENT_EDEFAULT : newPlacement;
		boolean oldPlacementESet = placementESet;
		placementESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__PLACEMENT, oldPlacement, placement, !oldPlacementESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPlacement() {
		PlacementEnum oldPlacement = placement;
		boolean oldPlacementESet = placementESet;
		placement = PLACEMENT_EDEFAULT;
		placementESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__PLACEMENT, oldPlacement, PLACEMENT_EDEFAULT, oldPlacementESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPlacement() {
		return placementESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getRotation() {
		return rotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRotation(int newRotation) {
		int oldRotation = rotation;
		rotation = newRotation;
		boolean oldRotationESet = rotationESet;
		rotationESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__ROTATION, oldRotation, rotation, !oldRotationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRotation() {
		int oldRotation = rotation;
		boolean oldRotationESet = rotationESet;
		rotation = ROTATION_EDEFAULT;
		rotationESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__ROTATION, oldRotation, ROTATION_EDEFAULT, oldRotationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRotation() {
		return rotationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSkipFactor() {
		return skipFactor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSkipFactor(int newSkipFactor) {
		int oldSkipFactor = skipFactor;
		skipFactor = newSkipFactor;
		boolean oldSkipFactorESet = skipFactorESet;
		skipFactorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__SKIP_FACTOR, oldSkipFactor, skipFactor, !oldSkipFactorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSkipFactor() {
		int oldSkipFactor = skipFactor;
		boolean oldSkipFactorESet = skipFactorESet;
		skipFactor = SKIP_FACTOR_EDEFAULT;
		skipFactorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__SKIP_FACTOR, oldSkipFactor, SKIP_FACTOR_EDEFAULT, oldSkipFactorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSkipFactor() {
		return skipFactorESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__RELATIVE_POSITION:
				return getRelativePosition();
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__PLACEMENT:
				return getPlacement();
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__ROTATION:
				return getRotation();
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__SKIP_FACTOR:
				return getSkipFactor();
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
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__RELATIVE_POSITION:
				setRelativePosition((RelativeAxisPositionEnum)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__PLACEMENT:
				setPlacement((PlacementEnum)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__ROTATION:
				setRotation((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__SKIP_FACTOR:
				setSkipFactor((Integer)newValue);
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
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__RELATIVE_POSITION:
				unsetRelativePosition();
				return;
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__PLACEMENT:
				unsetPlacement();
				return;
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__ROTATION:
				unsetRotation();
				return;
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__SKIP_FACTOR:
				unsetSkipFactor();
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
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__RELATIVE_POSITION:
				return isSetRelativePosition();
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__PLACEMENT:
				return isSetPlacement();
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__ROTATION:
				return isSetRotation();
			case BEViewsConfigurationPackage.CHART_CATEGORY_GUIDELINE_CONFIG__SKIP_FACTOR:
				return isSetSkipFactor();
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
		result.append(" (relativePosition: ");
		if (relativePositionESet) result.append(relativePosition); else result.append("<unset>");
		result.append(", placement: ");
		if (placementESet) result.append(placement); else result.append("<unset>");
		result.append(", rotation: ");
		if (rotationESet) result.append(rotation); else result.append("<unset>");
		result.append(", skipFactor: ");
		if (skipFactorESet) result.append(skipFactor); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ChartCategoryGuidelineConfigImpl
