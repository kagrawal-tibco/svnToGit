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
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle;
import com.tibco.cep.designtime.core.model.beviewsconfig.RelativeAxisPositionEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.ScaleEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Chart Value Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueGuidelineConfigImpl#getLabelFormatStyle <em>Label Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueGuidelineConfigImpl#getHeaderName <em>Header Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueGuidelineConfigImpl#getRelativePosition <em>Relative Position</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueGuidelineConfigImpl#getScale <em>Scale</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartValueGuidelineConfigImpl#getDivision <em>Division</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChartValueGuidelineConfigImpl extends ValueGuidelineConfigImpl implements ChartValueGuidelineConfig {
	/**
	 * The cached value of the '{@link #getLabelFormatStyle() <em>Label Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelFormatStyle()
	 * @generated
	 * @ordered
	 */
	protected FormatStyle labelFormatStyle;

	/**
	 * The default value of the '{@link #getHeaderName() <em>Header Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderName()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderName() <em>Header Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderName()
	 * @generated
	 * @ordered
	 */
	protected String headerName = HEADER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRelativePosition() <em>Relative Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelativePosition()
	 * @generated
	 * @ordered
	 */
	protected static final RelativeAxisPositionEnum RELATIVE_POSITION_EDEFAULT = RelativeAxisPositionEnum.LEFT;

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
	 * The default value of the '{@link #getScale() <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScale()
	 * @generated
	 * @ordered
	 */
	protected static final ScaleEnum SCALE_EDEFAULT = ScaleEnum.NORMAL;

	/**
	 * The cached value of the '{@link #getScale() <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScale()
	 * @generated
	 * @ordered
	 */
	protected ScaleEnum scale = SCALE_EDEFAULT;

	/**
	 * This is true if the Scale attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean scaleESet;

	/**
	 * The default value of the '{@link #getDivision() <em>Division</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDivision()
	 * @generated
	 * @ordered
	 */
	protected static final int DIVISION_EDEFAULT = 5;

	/**
	 * The cached value of the '{@link #getDivision() <em>Division</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDivision()
	 * @generated
	 * @ordered
	 */
	protected int division = DIVISION_EDEFAULT;

	/**
	 * This is true if the Division attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean divisionESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChartValueGuidelineConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getChartValueGuidelineConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FormatStyle getLabelFormatStyle() {
		return labelFormatStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLabelFormatStyle(FormatStyle newLabelFormatStyle, NotificationChain msgs) {
		FormatStyle oldLabelFormatStyle = labelFormatStyle;
		labelFormatStyle = newLabelFormatStyle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE, oldLabelFormatStyle, newLabelFormatStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabelFormatStyle(FormatStyle newLabelFormatStyle) {
		if (newLabelFormatStyle != labelFormatStyle) {
			NotificationChain msgs = null;
			if (labelFormatStyle != null)
				msgs = ((InternalEObject)labelFormatStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE, null, msgs);
			if (newLabelFormatStyle != null)
				msgs = ((InternalEObject)newLabelFormatStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE, null, msgs);
			msgs = basicSetLabelFormatStyle(newLabelFormatStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE, newLabelFormatStyle, newLabelFormatStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderName() {
		return headerName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderName(String newHeaderName) {
		String oldHeaderName = headerName;
		headerName = newHeaderName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__HEADER_NAME, oldHeaderName, headerName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__RELATIVE_POSITION, oldRelativePosition, relativePosition, !oldRelativePositionESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__RELATIVE_POSITION, oldRelativePosition, RELATIVE_POSITION_EDEFAULT, oldRelativePositionESet));
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
	public ScaleEnum getScale() {
		return scale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScale(ScaleEnum newScale) {
		ScaleEnum oldScale = scale;
		scale = newScale == null ? SCALE_EDEFAULT : newScale;
		boolean oldScaleESet = scaleESet;
		scaleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__SCALE, oldScale, scale, !oldScaleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetScale() {
		ScaleEnum oldScale = scale;
		boolean oldScaleESet = scaleESet;
		scale = SCALE_EDEFAULT;
		scaleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__SCALE, oldScale, SCALE_EDEFAULT, oldScaleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetScale() {
		return scaleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDivision() {
		return division;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDivision(int newDivision) {
		int oldDivision = division;
		division = newDivision;
		boolean oldDivisionESet = divisionESet;
		divisionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__DIVISION, oldDivision, division, !oldDivisionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDivision() {
		int oldDivision = division;
		boolean oldDivisionESet = divisionESet;
		division = DIVISION_EDEFAULT;
		divisionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__DIVISION, oldDivision, DIVISION_EDEFAULT, oldDivisionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDivision() {
		return divisionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				return basicSetLabelFormatStyle(null, msgs);
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
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				return getLabelFormatStyle();
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__HEADER_NAME:
				return getHeaderName();
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__RELATIVE_POSITION:
				return getRelativePosition();
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__SCALE:
				return getScale();
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__DIVISION:
				return getDivision();
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
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				setLabelFormatStyle((FormatStyle)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__HEADER_NAME:
				setHeaderName((String)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__RELATIVE_POSITION:
				setRelativePosition((RelativeAxisPositionEnum)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__SCALE:
				setScale((ScaleEnum)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__DIVISION:
				setDivision((Integer)newValue);
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
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				setLabelFormatStyle((FormatStyle)null);
				return;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__HEADER_NAME:
				setHeaderName(HEADER_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__RELATIVE_POSITION:
				unsetRelativePosition();
				return;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__SCALE:
				unsetScale();
				return;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__DIVISION:
				unsetDivision();
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
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				return labelFormatStyle != null;
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__HEADER_NAME:
				return HEADER_NAME_EDEFAULT == null ? headerName != null : !HEADER_NAME_EDEFAULT.equals(headerName);
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__RELATIVE_POSITION:
				return isSetRelativePosition();
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__SCALE:
				return isSetScale();
			case BEViewsConfigurationPackage.CHART_VALUE_GUIDELINE_CONFIG__DIVISION:
				return isSetDivision();
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
		result.append(" (headerName: ");
		result.append(headerName);
		result.append(", relativePosition: ");
		if (relativePositionESet) result.append(relativePosition); else result.append("<unset>");
		result.append(", scale: ");
		if (scaleESet) result.append(scale); else result.append("<unset>");
		result.append(", division: ");
		if (divisionESet) result.append(division); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ChartValueGuidelineConfigImpl
