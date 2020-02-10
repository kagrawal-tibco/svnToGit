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
import com.tibco.cep.designtime.core.model.beviewsconfig.PieChartDirectionEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.PieChartVisualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pie Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PieChartVisualizationImpl#getStartingAngle <em>Starting Angle</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PieChartVisualizationImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PieChartVisualizationImpl#getSector <em>Sector</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PieChartVisualizationImpl extends ChartVisualizationImpl implements PieChartVisualization {
	/**
	 * The default value of the '{@link #getStartingAngle() <em>Starting Angle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartingAngle()
	 * @generated
	 * @ordered
	 */
	protected static final int STARTING_ANGLE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartingAngle() <em>Starting Angle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartingAngle()
	 * @generated
	 * @ordered
	 */
	protected int startingAngle = STARTING_ANGLE_EDEFAULT;

	/**
	 * This is true if the Starting Angle attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean startingAngleESet;

	/**
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final PieChartDirectionEnum DIRECTION_EDEFAULT = PieChartDirectionEnum.CLOCKWISE;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected PieChartDirectionEnum direction = DIRECTION_EDEFAULT;

	/**
	 * This is true if the Direction attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean directionESet;

	/**
	 * The default value of the '{@link #getSector() <em>Sector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSector()
	 * @generated
	 * @ordered
	 */
	protected static final int SECTOR_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSector() <em>Sector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSector()
	 * @generated
	 * @ordered
	 */
	protected int sector = SECTOR_EDEFAULT;

	/**
	 * This is true if the Sector attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean sectorESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PieChartVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getPieChartVisualization();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartingAngle() {
		return startingAngle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartingAngle(int newStartingAngle) {
		int oldStartingAngle = startingAngle;
		startingAngle = newStartingAngle;
		boolean oldStartingAngleESet = startingAngleESet;
		startingAngleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__STARTING_ANGLE, oldStartingAngle, startingAngle, !oldStartingAngleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStartingAngle() {
		int oldStartingAngle = startingAngle;
		boolean oldStartingAngleESet = startingAngleESet;
		startingAngle = STARTING_ANGLE_EDEFAULT;
		startingAngleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__STARTING_ANGLE, oldStartingAngle, STARTING_ANGLE_EDEFAULT, oldStartingAngleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetStartingAngle() {
		return startingAngleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PieChartDirectionEnum getDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(PieChartDirectionEnum newDirection) {
		PieChartDirectionEnum oldDirection = direction;
		direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
		boolean oldDirectionESet = directionESet;
		directionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__DIRECTION, oldDirection, direction, !oldDirectionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDirection() {
		PieChartDirectionEnum oldDirection = direction;
		boolean oldDirectionESet = directionESet;
		direction = DIRECTION_EDEFAULT;
		directionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__DIRECTION, oldDirection, DIRECTION_EDEFAULT, oldDirectionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDirection() {
		return directionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSector() {
		return sector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSector(int newSector) {
		int oldSector = sector;
		sector = newSector;
		boolean oldSectorESet = sectorESet;
		sectorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__SECTOR, oldSector, sector, !oldSectorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSector() {
		int oldSector = sector;
		boolean oldSectorESet = sectorESet;
		sector = SECTOR_EDEFAULT;
		sectorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__SECTOR, oldSector, SECTOR_EDEFAULT, oldSectorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSector() {
		return sectorESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__STARTING_ANGLE:
				return getStartingAngle();
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__DIRECTION:
				return getDirection();
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__SECTOR:
				return getSector();
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
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__STARTING_ANGLE:
				setStartingAngle((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__DIRECTION:
				setDirection((PieChartDirectionEnum)newValue);
				return;
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__SECTOR:
				setSector((Integer)newValue);
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
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__STARTING_ANGLE:
				unsetStartingAngle();
				return;
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__DIRECTION:
				unsetDirection();
				return;
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__SECTOR:
				unsetSector();
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
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__STARTING_ANGLE:
				return isSetStartingAngle();
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__DIRECTION:
				return isSetDirection();
			case BEViewsConfigurationPackage.PIE_CHART_VISUALIZATION__SECTOR:
				return isSetSector();
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
		result.append(" (startingAngle: ");
		if (startingAngleESet) result.append(startingAngle); else result.append("<unset>");
		result.append(", direction: ");
		if (directionESet) result.append(direction); else result.append("<unset>");
		result.append(", sector: ");
		if (sectorESet) result.append(sector); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PieChartVisualizationImpl
