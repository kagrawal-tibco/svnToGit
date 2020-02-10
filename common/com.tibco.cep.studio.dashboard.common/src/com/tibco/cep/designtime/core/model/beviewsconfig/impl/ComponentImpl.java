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

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.LayoutConstraint;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfigGenerator;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getHelpText <em>Help Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getComponentColorSetIndex <em>Component Color Set Index</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getSeriesColorIndex <em>Series Color Index</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getBackground <em>Background</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getVisualization <em>Visualization</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getLayoutConstraint <em>Layout Constraint</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getSeriesConfigGenerator <em>Series Config Generator</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentImpl#getRelatedElement <em>Related Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ComponentImpl extends BEViewsElementImpl implements Component {
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
	 * The default value of the '{@link #getHelpText() <em>Help Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpText()
	 * @generated
	 * @ordered
	 */
	protected static final String HELP_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHelpText() <em>Help Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpText()
	 * @generated
	 * @ordered
	 */
	protected String helpText = HELP_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getComponentColorSetIndex() <em>Component Color Set Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentColorSetIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPONENT_COLOR_SET_INDEX_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getComponentColorSetIndex() <em>Component Color Set Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentColorSetIndex()
	 * @generated
	 * @ordered
	 */
	protected int componentColorSetIndex = COMPONENT_COLOR_SET_INDEX_EDEFAULT;

	/**
	 * This is true if the Component Color Set Index attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean componentColorSetIndexESet;

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
	 * The cached value of the '{@link #getBackground() <em>Background</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackground()
	 * @generated
	 * @ordered
	 */
	protected BackgroundFormat background;

	/**
	 * The cached value of the '{@link #getVisualization() <em>Visualization</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisualization()
	 * @generated
	 * @ordered
	 */
	protected EList<Visualization> visualization;

	/**
	 * The cached value of the '{@link #getLayoutConstraint() <em>Layout Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayoutConstraint()
	 * @generated
	 * @ordered
	 */
	protected LayoutConstraint layoutConstraint;

	/**
	 * The cached value of the '{@link #getSeriesConfigGenerator() <em>Series Config Generator</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeriesConfigGenerator()
	 * @generated
	 * @ordered
	 */
	protected EList<SeriesConfigGenerator> seriesConfigGenerator;

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
	protected ComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getComponent();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.COMPONENT__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHelpText() {
		return helpText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHelpText(String newHelpText) {
		String oldHelpText = helpText;
		helpText = newHelpText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.COMPONENT__HELP_TEXT, oldHelpText, helpText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getComponentColorSetIndex() {
		return componentColorSetIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentColorSetIndex(int newComponentColorSetIndex) {
		int oldComponentColorSetIndex = componentColorSetIndex;
		componentColorSetIndex = newComponentColorSetIndex;
		boolean oldComponentColorSetIndexESet = componentColorSetIndexESet;
		componentColorSetIndexESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.COMPONENT__COMPONENT_COLOR_SET_INDEX, oldComponentColorSetIndex, componentColorSetIndex, !oldComponentColorSetIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetComponentColorSetIndex() {
		int oldComponentColorSetIndex = componentColorSetIndex;
		boolean oldComponentColorSetIndexESet = componentColorSetIndexESet;
		componentColorSetIndex = COMPONENT_COLOR_SET_INDEX_EDEFAULT;
		componentColorSetIndexESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.COMPONENT__COMPONENT_COLOR_SET_INDEX, oldComponentColorSetIndex, COMPONENT_COLOR_SET_INDEX_EDEFAULT, oldComponentColorSetIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetComponentColorSetIndex() {
		return componentColorSetIndexESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.COMPONENT__SERIES_COLOR_INDEX, oldSeriesColorIndex, seriesColorIndex, !oldSeriesColorIndexESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.COMPONENT__SERIES_COLOR_INDEX, oldSeriesColorIndex, SERIES_COLOR_INDEX_EDEFAULT, oldSeriesColorIndexESet));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.COMPONENT__BACKGROUND, oldBackground, newBackground);
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
				msgs = ((InternalEObject)background).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.COMPONENT__BACKGROUND, null, msgs);
			if (newBackground != null)
				msgs = ((InternalEObject)newBackground).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.COMPONENT__BACKGROUND, null, msgs);
			msgs = basicSetBackground(newBackground, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.COMPONENT__BACKGROUND, newBackground, newBackground));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Visualization> getVisualization() {
		if (visualization == null) {
			visualization = new EObjectContainmentEList<Visualization>(Visualization.class, this, BEViewsConfigurationPackage.COMPONENT__VISUALIZATION);
		}
		return visualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LayoutConstraint getLayoutConstraint() {
		return layoutConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLayoutConstraint(LayoutConstraint newLayoutConstraint, NotificationChain msgs) {
		LayoutConstraint oldLayoutConstraint = layoutConstraint;
		layoutConstraint = newLayoutConstraint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT, oldLayoutConstraint, newLayoutConstraint);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLayoutConstraint(LayoutConstraint newLayoutConstraint) {
		if (newLayoutConstraint != layoutConstraint) {
			NotificationChain msgs = null;
			if (layoutConstraint != null)
				msgs = ((InternalEObject)layoutConstraint).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT, null, msgs);
			if (newLayoutConstraint != null)
				msgs = ((InternalEObject)newLayoutConstraint).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT, null, msgs);
			msgs = basicSetLayoutConstraint(newLayoutConstraint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT, newLayoutConstraint, newLayoutConstraint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SeriesConfigGenerator> getSeriesConfigGenerator() {
		if (seriesConfigGenerator == null) {
			seriesConfigGenerator = new EObjectContainmentEList<SeriesConfigGenerator>(SeriesConfigGenerator.class, this, BEViewsConfigurationPackage.COMPONENT__SERIES_CONFIG_GENERATOR);
		}
		return seriesConfigGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BEViewsElement> getRelatedElement() {
		if (relatedElement == null) {
			relatedElement = new EObjectResolvingEList<BEViewsElement>(BEViewsElement.class, this, BEViewsConfigurationPackage.COMPONENT__RELATED_ELEMENT);
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
			case BEViewsConfigurationPackage.COMPONENT__BACKGROUND:
				return basicSetBackground(null, msgs);
			case BEViewsConfigurationPackage.COMPONENT__VISUALIZATION:
				return ((InternalEList<?>)getVisualization()).basicRemove(otherEnd, msgs);
			case BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT:
				return basicSetLayoutConstraint(null, msgs);
			case BEViewsConfigurationPackage.COMPONENT__SERIES_CONFIG_GENERATOR:
				return ((InternalEList<?>)getSeriesConfigGenerator()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.COMPONENT__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.COMPONENT__HELP_TEXT:
				return getHelpText();
			case BEViewsConfigurationPackage.COMPONENT__COMPONENT_COLOR_SET_INDEX:
				return getComponentColorSetIndex();
			case BEViewsConfigurationPackage.COMPONENT__SERIES_COLOR_INDEX:
				return getSeriesColorIndex();
			case BEViewsConfigurationPackage.COMPONENT__BACKGROUND:
				return getBackground();
			case BEViewsConfigurationPackage.COMPONENT__VISUALIZATION:
				return getVisualization();
			case BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT:
				return getLayoutConstraint();
			case BEViewsConfigurationPackage.COMPONENT__SERIES_CONFIG_GENERATOR:
				return getSeriesConfigGenerator();
			case BEViewsConfigurationPackage.COMPONENT__RELATED_ELEMENT:
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
			case BEViewsConfigurationPackage.COMPONENT__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT__HELP_TEXT:
				setHelpText((String)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT__COMPONENT_COLOR_SET_INDEX:
				setComponentColorSetIndex((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT__SERIES_COLOR_INDEX:
				setSeriesColorIndex((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT__BACKGROUND:
				setBackground((BackgroundFormat)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT__VISUALIZATION:
				getVisualization().clear();
				getVisualization().addAll((Collection<? extends Visualization>)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT:
				setLayoutConstraint((LayoutConstraint)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT__SERIES_CONFIG_GENERATOR:
				getSeriesConfigGenerator().clear();
				getSeriesConfigGenerator().addAll((Collection<? extends SeriesConfigGenerator>)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT__RELATED_ELEMENT:
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
			case BEViewsConfigurationPackage.COMPONENT__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.COMPONENT__HELP_TEXT:
				setHelpText(HELP_TEXT_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.COMPONENT__COMPONENT_COLOR_SET_INDEX:
				unsetComponentColorSetIndex();
				return;
			case BEViewsConfigurationPackage.COMPONENT__SERIES_COLOR_INDEX:
				unsetSeriesColorIndex();
				return;
			case BEViewsConfigurationPackage.COMPONENT__BACKGROUND:
				setBackground((BackgroundFormat)null);
				return;
			case BEViewsConfigurationPackage.COMPONENT__VISUALIZATION:
				getVisualization().clear();
				return;
			case BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT:
				setLayoutConstraint((LayoutConstraint)null);
				return;
			case BEViewsConfigurationPackage.COMPONENT__SERIES_CONFIG_GENERATOR:
				getSeriesConfigGenerator().clear();
				return;
			case BEViewsConfigurationPackage.COMPONENT__RELATED_ELEMENT:
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
			case BEViewsConfigurationPackage.COMPONENT__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.COMPONENT__HELP_TEXT:
				return HELP_TEXT_EDEFAULT == null ? helpText != null : !HELP_TEXT_EDEFAULT.equals(helpText);
			case BEViewsConfigurationPackage.COMPONENT__COMPONENT_COLOR_SET_INDEX:
				return isSetComponentColorSetIndex();
			case BEViewsConfigurationPackage.COMPONENT__SERIES_COLOR_INDEX:
				return isSetSeriesColorIndex();
			case BEViewsConfigurationPackage.COMPONENT__BACKGROUND:
				return background != null;
			case BEViewsConfigurationPackage.COMPONENT__VISUALIZATION:
				return visualization != null && !visualization.isEmpty();
			case BEViewsConfigurationPackage.COMPONENT__LAYOUT_CONSTRAINT:
				return layoutConstraint != null;
			case BEViewsConfigurationPackage.COMPONENT__SERIES_CONFIG_GENERATOR:
				return seriesConfigGenerator != null && !seriesConfigGenerator.isEmpty();
			case BEViewsConfigurationPackage.COMPONENT__RELATED_ELEMENT:
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
		result.append(", helpText: ");
		result.append(helpText);
		result.append(", componentColorSetIndex: ");
		if (componentColorSetIndexESet) result.append(componentColorSetIndex); else result.append("<unset>");
		result.append(", seriesColorIndex: ");
		if (seriesColorIndexESet) result.append(seriesColorIndex); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ComponentImpl
