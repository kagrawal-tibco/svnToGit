/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Skin</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getDefaultComponentColorSet <em>Default Component Color Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getComponentColorSet <em>Component Color Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getFontColor <em>Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getComponentBackGroundColor <em>Component Back Ground Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getComponentBackGroundGradientEndColor <em>Component Back Ground Gradient End Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getComponentForeGroundColor <em>Component Fore Ground Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getVisualizationBackGroundColor <em>Visualization Back Ground Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getVisualizationBackGroundGradientEndColor <em>Visualization Back Ground Gradient End Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SkinImpl#getVisualizationForeGroundColor <em>Visualization Fore Ground Color</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SkinImpl extends BEViewsElementImpl implements Skin {
	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDefaultComponentColorSet() <em>Default Component Color Set</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultComponentColorSet()
	 * @generated
	 * @ordered
	 */
	protected EList<ComponentColorSet> defaultComponentColorSet;

	/**
	 * The cached value of the '{@link #getComponentColorSet() <em>Component Color Set</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentColorSet()
	 * @generated
	 * @ordered
	 */
	protected EList<ComponentColorSet> componentColorSet;

	/**
	 * The default value of the '{@link #getFontColor() <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final String FONT_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFontColor() <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontColor()
	 * @generated
	 * @ordered
	 */
	protected String fontColor = FONT_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getComponentBackGroundColor() <em>Component Back Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentBackGroundColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPONENT_BACK_GROUND_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComponentBackGroundColor() <em>Component Back Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentBackGroundColor()
	 * @generated
	 * @ordered
	 */
	protected String componentBackGroundColor = COMPONENT_BACK_GROUND_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getComponentBackGroundGradientEndColor() <em>Component Back Ground Gradient End Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentBackGroundGradientEndColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPONENT_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComponentBackGroundGradientEndColor() <em>Component Back Ground Gradient End Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentBackGroundGradientEndColor()
	 * @generated
	 * @ordered
	 */
	protected String componentBackGroundGradientEndColor = COMPONENT_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getComponentForeGroundColor() <em>Component Fore Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentForeGroundColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPONENT_FORE_GROUND_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComponentForeGroundColor() <em>Component Fore Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentForeGroundColor()
	 * @generated
	 * @ordered
	 */
	protected String componentForeGroundColor = COMPONENT_FORE_GROUND_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVisualizationBackGroundColor() <em>Visualization Back Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisualizationBackGroundColor()
	 * @generated
	 * @ordered
	 */
	protected static final String VISUALIZATION_BACK_GROUND_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVisualizationBackGroundColor() <em>Visualization Back Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisualizationBackGroundColor()
	 * @generated
	 * @ordered
	 */
	protected String visualizationBackGroundColor = VISUALIZATION_BACK_GROUND_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVisualizationBackGroundGradientEndColor() <em>Visualization Back Ground Gradient End Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisualizationBackGroundGradientEndColor()
	 * @generated
	 * @ordered
	 */
	protected static final String VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVisualizationBackGroundGradientEndColor() <em>Visualization Back Ground Gradient End Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisualizationBackGroundGradientEndColor()
	 * @generated
	 * @ordered
	 */
	protected String visualizationBackGroundGradientEndColor = VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVisualizationForeGroundColor() <em>Visualization Fore Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisualizationForeGroundColor()
	 * @generated
	 * @ordered
	 */
	protected static final String VISUALIZATION_FORE_GROUND_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVisualizationForeGroundColor() <em>Visualization Fore Ground Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisualizationForeGroundColor()
	 * @generated
	 * @ordered
	 */
	protected String visualizationForeGroundColor = VISUALIZATION_FORE_GROUND_COLOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SkinImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getSkin();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SKIN__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ComponentColorSet> getDefaultComponentColorSet() {
		if (defaultComponentColorSet == null) {
			defaultComponentColorSet = new EObjectEList<ComponentColorSet>(ComponentColorSet.class, this, BEViewsConfigurationPackage.SKIN__DEFAULT_COMPONENT_COLOR_SET);
		}
		return defaultComponentColorSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ComponentColorSet> getComponentColorSet() {
		if (componentColorSet == null) {
			componentColorSet = new EObjectEList<ComponentColorSet>(ComponentColorSet.class, this, BEViewsConfigurationPackage.SKIN__COMPONENT_COLOR_SET);
		}
		return componentColorSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontColor(String newFontColor) {
		String oldFontColor = fontColor;
		fontColor = newFontColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SKIN__FONT_COLOR, oldFontColor, fontColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComponentBackGroundColor() {
		return componentBackGroundColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentBackGroundColor(String newComponentBackGroundColor) {
		String oldComponentBackGroundColor = componentBackGroundColor;
		componentBackGroundColor = newComponentBackGroundColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_COLOR, oldComponentBackGroundColor, componentBackGroundColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComponentBackGroundGradientEndColor() {
		return componentBackGroundGradientEndColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentBackGroundGradientEndColor(String newComponentBackGroundGradientEndColor) {
		String oldComponentBackGroundGradientEndColor = componentBackGroundGradientEndColor;
		componentBackGroundGradientEndColor = newComponentBackGroundGradientEndColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_GRADIENT_END_COLOR, oldComponentBackGroundGradientEndColor, componentBackGroundGradientEndColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComponentForeGroundColor() {
		return componentForeGroundColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentForeGroundColor(String newComponentForeGroundColor) {
		String oldComponentForeGroundColor = componentForeGroundColor;
		componentForeGroundColor = newComponentForeGroundColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SKIN__COMPONENT_FORE_GROUND_COLOR, oldComponentForeGroundColor, componentForeGroundColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVisualizationBackGroundColor() {
		return visualizationBackGroundColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisualizationBackGroundColor(String newVisualizationBackGroundColor) {
		String oldVisualizationBackGroundColor = visualizationBackGroundColor;
		visualizationBackGroundColor = newVisualizationBackGroundColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_COLOR, oldVisualizationBackGroundColor, visualizationBackGroundColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVisualizationBackGroundGradientEndColor() {
		return visualizationBackGroundGradientEndColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisualizationBackGroundGradientEndColor(String newVisualizationBackGroundGradientEndColor) {
		String oldVisualizationBackGroundGradientEndColor = visualizationBackGroundGradientEndColor;
		visualizationBackGroundGradientEndColor = newVisualizationBackGroundGradientEndColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR, oldVisualizationBackGroundGradientEndColor, visualizationBackGroundGradientEndColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVisualizationForeGroundColor() {
		return visualizationForeGroundColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisualizationForeGroundColor(String newVisualizationForeGroundColor) {
		String oldVisualizationForeGroundColor = visualizationForeGroundColor;
		visualizationForeGroundColor = newVisualizationForeGroundColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SKIN__VISUALIZATION_FORE_GROUND_COLOR, oldVisualizationForeGroundColor, visualizationForeGroundColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.SKIN__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.SKIN__DEFAULT_COMPONENT_COLOR_SET:
				return getDefaultComponentColorSet();
			case BEViewsConfigurationPackage.SKIN__COMPONENT_COLOR_SET:
				return getComponentColorSet();
			case BEViewsConfigurationPackage.SKIN__FONT_COLOR:
				return getFontColor();
			case BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_COLOR:
				return getComponentBackGroundColor();
			case BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_GRADIENT_END_COLOR:
				return getComponentBackGroundGradientEndColor();
			case BEViewsConfigurationPackage.SKIN__COMPONENT_FORE_GROUND_COLOR:
				return getComponentForeGroundColor();
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_COLOR:
				return getVisualizationBackGroundColor();
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR:
				return getVisualizationBackGroundGradientEndColor();
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_FORE_GROUND_COLOR:
				return getVisualizationForeGroundColor();
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
			case BEViewsConfigurationPackage.SKIN__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__DEFAULT_COMPONENT_COLOR_SET:
				getDefaultComponentColorSet().clear();
				getDefaultComponentColorSet().addAll((Collection<? extends ComponentColorSet>)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__COMPONENT_COLOR_SET:
				getComponentColorSet().clear();
				getComponentColorSet().addAll((Collection<? extends ComponentColorSet>)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__FONT_COLOR:
				setFontColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_COLOR:
				setComponentBackGroundColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_GRADIENT_END_COLOR:
				setComponentBackGroundGradientEndColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__COMPONENT_FORE_GROUND_COLOR:
				setComponentForeGroundColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_COLOR:
				setVisualizationBackGroundColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR:
				setVisualizationBackGroundGradientEndColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_FORE_GROUND_COLOR:
				setVisualizationForeGroundColor((String)newValue);
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
			case BEViewsConfigurationPackage.SKIN__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SKIN__DEFAULT_COMPONENT_COLOR_SET:
				getDefaultComponentColorSet().clear();
				return;
			case BEViewsConfigurationPackage.SKIN__COMPONENT_COLOR_SET:
				getComponentColorSet().clear();
				return;
			case BEViewsConfigurationPackage.SKIN__FONT_COLOR:
				setFontColor(FONT_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_COLOR:
				setComponentBackGroundColor(COMPONENT_BACK_GROUND_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_GRADIENT_END_COLOR:
				setComponentBackGroundGradientEndColor(COMPONENT_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SKIN__COMPONENT_FORE_GROUND_COLOR:
				setComponentForeGroundColor(COMPONENT_FORE_GROUND_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_COLOR:
				setVisualizationBackGroundColor(VISUALIZATION_BACK_GROUND_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR:
				setVisualizationBackGroundGradientEndColor(VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_FORE_GROUND_COLOR:
				setVisualizationForeGroundColor(VISUALIZATION_FORE_GROUND_COLOR_EDEFAULT);
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
			case BEViewsConfigurationPackage.SKIN__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.SKIN__DEFAULT_COMPONENT_COLOR_SET:
				return defaultComponentColorSet != null && !defaultComponentColorSet.isEmpty();
			case BEViewsConfigurationPackage.SKIN__COMPONENT_COLOR_SET:
				return componentColorSet != null && !componentColorSet.isEmpty();
			case BEViewsConfigurationPackage.SKIN__FONT_COLOR:
				return FONT_COLOR_EDEFAULT == null ? fontColor != null : !FONT_COLOR_EDEFAULT.equals(fontColor);
			case BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_COLOR:
				return COMPONENT_BACK_GROUND_COLOR_EDEFAULT == null ? componentBackGroundColor != null : !COMPONENT_BACK_GROUND_COLOR_EDEFAULT.equals(componentBackGroundColor);
			case BEViewsConfigurationPackage.SKIN__COMPONENT_BACK_GROUND_GRADIENT_END_COLOR:
				return COMPONENT_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT == null ? componentBackGroundGradientEndColor != null : !COMPONENT_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT.equals(componentBackGroundGradientEndColor);
			case BEViewsConfigurationPackage.SKIN__COMPONENT_FORE_GROUND_COLOR:
				return COMPONENT_FORE_GROUND_COLOR_EDEFAULT == null ? componentForeGroundColor != null : !COMPONENT_FORE_GROUND_COLOR_EDEFAULT.equals(componentForeGroundColor);
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_COLOR:
				return VISUALIZATION_BACK_GROUND_COLOR_EDEFAULT == null ? visualizationBackGroundColor != null : !VISUALIZATION_BACK_GROUND_COLOR_EDEFAULT.equals(visualizationBackGroundColor);
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR:
				return VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT == null ? visualizationBackGroundGradientEndColor != null : !VISUALIZATION_BACK_GROUND_GRADIENT_END_COLOR_EDEFAULT.equals(visualizationBackGroundGradientEndColor);
			case BEViewsConfigurationPackage.SKIN__VISUALIZATION_FORE_GROUND_COLOR:
				return VISUALIZATION_FORE_GROUND_COLOR_EDEFAULT == null ? visualizationForeGroundColor != null : !VISUALIZATION_FORE_GROUND_COLOR_EDEFAULT.equals(visualizationForeGroundColor);
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
		result.append(" (displayName: ");
		result.append(displayName);
		result.append(", fontColor: ");
		result.append(fontColor);
		result.append(", componentBackGroundColor: ");
		result.append(componentBackGroundColor);
		result.append(", componentBackGroundGradientEndColor: ");
		result.append(componentBackGroundGradientEndColor);
		result.append(", componentForeGroundColor: ");
		result.append(componentForeGroundColor);
		result.append(", visualizationBackGroundColor: ");
		result.append(visualizationBackGroundColor);
		result.append(", visualizationBackGroundGradientEndColor: ");
		result.append(visualizationBackGroundGradientEndColor);
		result.append(", visualizationForeGroundColor: ");
		result.append(visualizationForeGroundColor);
		result.append(')');
		return result.toString();
	}

} //SkinImpl
