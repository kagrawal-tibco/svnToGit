/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Format</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataFormatImpl#getToolTipFormat <em>Tool Tip Format</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataFormatImpl#getDisplayFormat <em>Display Format</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataFormatImpl#getFormatStyle <em>Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataFormatImpl#getActionConfig <em>Action Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataFormatImpl#isShowLabel <em>Show Label</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DataFormatImpl extends BEViewsElementImpl implements DataFormat {
	/**
	 * The default value of the '{@link #getToolTipFormat() <em>Tool Tip Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolTipFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOL_TIP_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getToolTipFormat() <em>Tool Tip Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolTipFormat()
	 * @generated
	 * @ordered
	 */
	protected String toolTipFormat = TOOL_TIP_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayFormat() <em>Display Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayFormat() <em>Display Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayFormat()
	 * @generated
	 * @ordered
	 */
	protected String displayFormat = DISPLAY_FORMAT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFormatStyle() <em>Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormatStyle()
	 * @generated
	 * @ordered
	 */
	protected FormatStyle formatStyle;

	/**
	 * The cached value of the '{@link #getActionConfig() <em>Action Config</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionConfig()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> actionConfig;

	/**
	 * The default value of the '{@link #isShowLabel() <em>Show Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowLabel()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_LABEL_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isShowLabel() <em>Show Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowLabel()
	 * @generated
	 * @ordered
	 */
	protected boolean showLabel = SHOW_LABEL_EDEFAULT;

	/**
	 * This is true if the Show Label attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showLabelESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataFormatImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getDataFormat();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getToolTipFormat() {
		return toolTipFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToolTipFormat(String newToolTipFormat) {
		String oldToolTipFormat = toolTipFormat;
		toolTipFormat = newToolTipFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_FORMAT__TOOL_TIP_FORMAT, oldToolTipFormat, toolTipFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayFormat() {
		return displayFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayFormat(String newDisplayFormat) {
		String oldDisplayFormat = displayFormat;
		displayFormat = newDisplayFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_FORMAT__DISPLAY_FORMAT, oldDisplayFormat, displayFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FormatStyle getFormatStyle() {
		return formatStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFormatStyle(FormatStyle newFormatStyle, NotificationChain msgs) {
		FormatStyle oldFormatStyle = formatStyle;
		formatStyle = newFormatStyle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE, oldFormatStyle, newFormatStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormatStyle(FormatStyle newFormatStyle) {
		if (newFormatStyle != formatStyle) {
			NotificationChain msgs = null;
			if (formatStyle != null)
				msgs = ((InternalEObject)formatStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE, null, msgs);
			if (newFormatStyle != null)
				msgs = ((InternalEObject)newFormatStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE, null, msgs);
			msgs = basicSetFormatStyle(newFormatStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE, newFormatStyle, newFormatStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getActionConfig() {
		if (actionConfig == null) {
			actionConfig = new EObjectContainmentEList<EObject>(EObject.class, this, BEViewsConfigurationPackage.DATA_FORMAT__ACTION_CONFIG);
		}
		return actionConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowLabel() {
		return showLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowLabel(boolean newShowLabel) {
		boolean oldShowLabel = showLabel;
		showLabel = newShowLabel;
		boolean oldShowLabelESet = showLabelESet;
		showLabelESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_FORMAT__SHOW_LABEL, oldShowLabel, showLabel, !oldShowLabelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowLabel() {
		boolean oldShowLabel = showLabel;
		boolean oldShowLabelESet = showLabelESet;
		showLabel = SHOW_LABEL_EDEFAULT;
		showLabelESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.DATA_FORMAT__SHOW_LABEL, oldShowLabel, SHOW_LABEL_EDEFAULT, oldShowLabelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowLabel() {
		return showLabelESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE:
				return basicSetFormatStyle(null, msgs);
			case BEViewsConfigurationPackage.DATA_FORMAT__ACTION_CONFIG:
				return ((InternalEList<?>)getActionConfig()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.DATA_FORMAT__TOOL_TIP_FORMAT:
				return getToolTipFormat();
			case BEViewsConfigurationPackage.DATA_FORMAT__DISPLAY_FORMAT:
				return getDisplayFormat();
			case BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE:
				return getFormatStyle();
			case BEViewsConfigurationPackage.DATA_FORMAT__ACTION_CONFIG:
				return getActionConfig();
			case BEViewsConfigurationPackage.DATA_FORMAT__SHOW_LABEL:
				return isShowLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BEViewsConfigurationPackage.DATA_FORMAT__TOOL_TIP_FORMAT:
				setToolTipFormat((String)newValue);
				return;
			case BEViewsConfigurationPackage.DATA_FORMAT__DISPLAY_FORMAT:
				setDisplayFormat((String)newValue);
				return;
			case BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE:
				setFormatStyle((FormatStyle)newValue);
				return;
			case BEViewsConfigurationPackage.DATA_FORMAT__ACTION_CONFIG:
				getActionConfig().clear();
				getActionConfig().addAll((Collection<? extends EObject>)newValue);
				return;
			case BEViewsConfigurationPackage.DATA_FORMAT__SHOW_LABEL:
				setShowLabel((Boolean)newValue);
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
			case BEViewsConfigurationPackage.DATA_FORMAT__TOOL_TIP_FORMAT:
				setToolTipFormat(TOOL_TIP_FORMAT_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.DATA_FORMAT__DISPLAY_FORMAT:
				setDisplayFormat(DISPLAY_FORMAT_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE:
				setFormatStyle((FormatStyle)null);
				return;
			case BEViewsConfigurationPackage.DATA_FORMAT__ACTION_CONFIG:
				getActionConfig().clear();
				return;
			case BEViewsConfigurationPackage.DATA_FORMAT__SHOW_LABEL:
				unsetShowLabel();
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
			case BEViewsConfigurationPackage.DATA_FORMAT__TOOL_TIP_FORMAT:
				return TOOL_TIP_FORMAT_EDEFAULT == null ? toolTipFormat != null : !TOOL_TIP_FORMAT_EDEFAULT.equals(toolTipFormat);
			case BEViewsConfigurationPackage.DATA_FORMAT__DISPLAY_FORMAT:
				return DISPLAY_FORMAT_EDEFAULT == null ? displayFormat != null : !DISPLAY_FORMAT_EDEFAULT.equals(displayFormat);
			case BEViewsConfigurationPackage.DATA_FORMAT__FORMAT_STYLE:
				return formatStyle != null;
			case BEViewsConfigurationPackage.DATA_FORMAT__ACTION_CONFIG:
				return actionConfig != null && !actionConfig.isEmpty();
			case BEViewsConfigurationPackage.DATA_FORMAT__SHOW_LABEL:
				return isSetShowLabel();
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
		result.append(" (toolTipFormat: ");
		result.append(toolTipFormat);
		result.append(", displayFormat: ");
		result.append(displayFormat);
		result.append(", showLabel: ");
		if (showLabelESet) result.append(showLabel); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //DataFormatImpl
