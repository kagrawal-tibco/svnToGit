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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualizationImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualizationImpl#getBackground <em>Background</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualizationImpl#getSeriesColorIndex <em>Series Color Index</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualizationImpl#getAction <em>Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualizationImpl#getSeriesConfig <em>Series Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualizationImpl#getRelatedElement <em>Related Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class VisualizationImpl extends BEViewsElementImpl implements Visualization {
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
	 * The cached value of the '{@link #getBackground() <em>Background</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackground()
	 * @generated
	 * @ordered
	 */
	protected BackgroundFormat background;

	/**
	 * The default value of the '{@link #getSeriesColorIndex() <em>Series Color Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeriesColorIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int SERIES_COLOR_INDEX_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getSeriesColorIndex() <em>Series Color Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeriesColorIndex()
	 * @generated
	 * @ordered
	 */
	protected int seriesColorIndex = SERIES_COLOR_INDEX_EDEFAULT;

	/**
	 * This is true if the Series Color Index attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean seriesColorIndexESet;

	/**
	 * The cached value of the '{@link #getAction() <em>Action</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected EList<ActionDefinition> action;

	/**
	 * The cached value of the '{@link #getSeriesConfig() <em>Series Config</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeriesConfig()
	 * @generated
	 * @ordered
	 */
	protected EList<SeriesConfig> seriesConfig;

	/**
	 * The cached value of the '{@link #getRelatedElement() <em>Related Element</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelatedElement()
	 * @generated
	 * @ordered
	 */
	protected EList<BEViewsElement> relatedElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getVisualization();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUALIZATION__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackgroundFormat getBackground() {
		return background;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackground(BackgroundFormat newBackground, NotificationChain msgs) {
		BackgroundFormat oldBackground = background;
		background = newBackground;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND, oldBackground, newBackground);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackground(BackgroundFormat newBackground) {
		if (newBackground != background) {
			NotificationChain msgs = null;
			if (background != null)
				msgs = ((InternalEObject)background).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND, null, msgs);
			if (newBackground != null)
				msgs = ((InternalEObject)newBackground).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND, null, msgs);
			msgs = basicSetBackground(newBackground, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND, newBackground, newBackground));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSeriesColorIndex() {
		return seriesColorIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeriesColorIndex(int newSeriesColorIndex) {
		int oldSeriesColorIndex = seriesColorIndex;
		seriesColorIndex = newSeriesColorIndex;
		boolean oldSeriesColorIndexESet = seriesColorIndexESet;
		seriesColorIndexESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUALIZATION__SERIES_COLOR_INDEX, oldSeriesColorIndex, seriesColorIndex, !oldSeriesColorIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSeriesColorIndex() {
		int oldSeriesColorIndex = seriesColorIndex;
		boolean oldSeriesColorIndexESet = seriesColorIndexESet;
		seriesColorIndex = SERIES_COLOR_INDEX_EDEFAULT;
		seriesColorIndexESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.VISUALIZATION__SERIES_COLOR_INDEX, oldSeriesColorIndex, SERIES_COLOR_INDEX_EDEFAULT, oldSeriesColorIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSeriesColorIndex() {
		return seriesColorIndexESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ActionDefinition> getAction() {
		if (action == null) {
			action = new EObjectContainmentEList<ActionDefinition>(ActionDefinition.class, this, BEViewsConfigurationPackage.VISUALIZATION__ACTION);
		}
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SeriesConfig> getSeriesConfig() {
		if (seriesConfig == null) {
			seriesConfig = new EObjectContainmentEList<SeriesConfig>(SeriesConfig.class, this, BEViewsConfigurationPackage.VISUALIZATION__SERIES_CONFIG);
		}
		return seriesConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BEViewsElement> getRelatedElement() {
		if (relatedElement == null) {
			relatedElement = new EObjectResolvingEList<BEViewsElement>(BEViewsElement.class, this, BEViewsConfigurationPackage.VISUALIZATION__RELATED_ELEMENT);
		}
		return relatedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND:
				return basicSetBackground(null, msgs);
			case BEViewsConfigurationPackage.VISUALIZATION__ACTION:
				return ((InternalEList<?>)getAction()).basicRemove(otherEnd, msgs);
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_CONFIG:
				return ((InternalEList<?>)getSeriesConfig()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.VISUALIZATION__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND:
				return getBackground();
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_COLOR_INDEX:
				return getSeriesColorIndex();
			case BEViewsConfigurationPackage.VISUALIZATION__ACTION:
				return getAction();
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_CONFIG:
				return getSeriesConfig();
			case BEViewsConfigurationPackage.VISUALIZATION__RELATED_ELEMENT:
				return getRelatedElement();
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
			case BEViewsConfigurationPackage.VISUALIZATION__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND:
				setBackground((BackgroundFormat)newValue);
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_COLOR_INDEX:
				setSeriesColorIndex((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__ACTION:
				getAction().clear();
				getAction().addAll((Collection<? extends ActionDefinition>)newValue);
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_CONFIG:
				getSeriesConfig().clear();
				getSeriesConfig().addAll((Collection<? extends SeriesConfig>)newValue);
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__RELATED_ELEMENT:
				getRelatedElement().clear();
				getRelatedElement().addAll((Collection<? extends BEViewsElement>)newValue);
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
			case BEViewsConfigurationPackage.VISUALIZATION__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND:
				setBackground((BackgroundFormat)null);
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_COLOR_INDEX:
				unsetSeriesColorIndex();
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__ACTION:
				getAction().clear();
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_CONFIG:
				getSeriesConfig().clear();
				return;
			case BEViewsConfigurationPackage.VISUALIZATION__RELATED_ELEMENT:
				getRelatedElement().clear();
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
			case BEViewsConfigurationPackage.VISUALIZATION__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.VISUALIZATION__BACKGROUND:
				return background != null;
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_COLOR_INDEX:
				return isSetSeriesColorIndex();
			case BEViewsConfigurationPackage.VISUALIZATION__ACTION:
				return action != null && !action.isEmpty();
			case BEViewsConfigurationPackage.VISUALIZATION__SERIES_CONFIG:
				return seriesConfig != null && !seriesConfig.isEmpty();
			case BEViewsConfigurationPackage.VISUALIZATION__RELATED_ELEMENT:
				return relatedElement != null && !relatedElement.isEmpty();
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
		result.append(", seriesColorIndex: ");
		if (seriesColorIndexESet) result.append(seriesColorIndex); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //VisualizationImpl
