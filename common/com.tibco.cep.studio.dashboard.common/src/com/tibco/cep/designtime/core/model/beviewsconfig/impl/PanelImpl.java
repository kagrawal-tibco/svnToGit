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
import org.eclipse.emf.ecore.util.EObjectEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.Layout;
import com.tibco.cep.designtime.core.model.beviewsconfig.Panel;
import com.tibco.cep.designtime.core.model.beviewsconfig.PanelStateEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.ScrollBarConfigEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Panel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl#getSpan <em>Span</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl#getScrollBar <em>Scroll Bar</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl#getState <em>State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl#isMaximizable <em>Maximizable</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl#isMinimizable <em>Minimizable</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PanelImpl#getLayout <em>Layout</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PanelImpl extends BEViewsElementImpl implements Panel {
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
	 * The default value of the '{@link #getSpan() <em>Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpan()
	 * @generated
	 * @ordered
	 */
	protected static final String SPAN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpan() <em>Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpan()
	 * @generated
	 * @ordered
	 */
	protected String span = SPAN_EDEFAULT;

	/**
	 * The default value of the '{@link #getScrollBar() <em>Scroll Bar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScrollBar()
	 * @generated
	 * @ordered
	 */
	protected static final ScrollBarConfigEnum SCROLL_BAR_EDEFAULT = ScrollBarConfigEnum.AUTO;

	/**
	 * The cached value of the '{@link #getScrollBar() <em>Scroll Bar</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScrollBar()
	 * @generated
	 * @ordered
	 */
	protected ScrollBarConfigEnum scrollBar = SCROLL_BAR_EDEFAULT;

	/**
	 * This is true if the Scroll Bar attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean scrollBarESet;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final PanelStateEnum STATE_EDEFAULT = PanelStateEnum.MAXIMIZED;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected PanelStateEnum state = STATE_EDEFAULT;

	/**
	 * This is true if the State attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean stateESet;

	/**
	 * The default value of the '{@link #isMaximizable() <em>Maximizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMaximizable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MAXIMIZABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMaximizable() <em>Maximizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMaximizable()
	 * @generated
	 * @ordered
	 */
	protected boolean maximizable = MAXIMIZABLE_EDEFAULT;

	/**
	 * This is true if the Maximizable attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean maximizableESet;

	/**
	 * The default value of the '{@link #isMinimizable() <em>Minimizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMinimizable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MINIMIZABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMinimizable() <em>Minimizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMinimizable()
	 * @generated
	 * @ordered
	 */
	protected boolean minimizable = MINIMIZABLE_EDEFAULT;

	/**
	 * This is true if the Minimizable attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean minimizableESet;

	/**
	 * The cached value of the '{@link #getComponent() <em>Component</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponent()
	 * @generated
	 * @ordered
	 */
	protected EList<Component> component;

	/**
	 * The cached value of the '{@link #getLayout() <em>Layout</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayout()
	 * @generated
	 * @ordered
	 */
	protected Layout layout;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PanelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getPanel();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PANEL__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSpan() {
		return span;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpan(String newSpan) {
		String oldSpan = span;
		span = newSpan;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PANEL__SPAN, oldSpan, span));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScrollBarConfigEnum getScrollBar() {
		return scrollBar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScrollBar(ScrollBarConfigEnum newScrollBar) {
		ScrollBarConfigEnum oldScrollBar = scrollBar;
		scrollBar = newScrollBar == null ? SCROLL_BAR_EDEFAULT : newScrollBar;
		boolean oldScrollBarESet = scrollBarESet;
		scrollBarESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PANEL__SCROLL_BAR, oldScrollBar, scrollBar, !oldScrollBarESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetScrollBar() {
		ScrollBarConfigEnum oldScrollBar = scrollBar;
		boolean oldScrollBarESet = scrollBarESet;
		scrollBar = SCROLL_BAR_EDEFAULT;
		scrollBarESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PANEL__SCROLL_BAR, oldScrollBar, SCROLL_BAR_EDEFAULT, oldScrollBarESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetScrollBar() {
		return scrollBarESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanelStateEnum getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(PanelStateEnum newState) {
		PanelStateEnum oldState = state;
		state = newState == null ? STATE_EDEFAULT : newState;
		boolean oldStateESet = stateESet;
		stateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PANEL__STATE, oldState, state, !oldStateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetState() {
		PanelStateEnum oldState = state;
		boolean oldStateESet = stateESet;
		state = STATE_EDEFAULT;
		stateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PANEL__STATE, oldState, STATE_EDEFAULT, oldStateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetState() {
		return stateESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMaximizable() {
		return maximizable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaximizable(boolean newMaximizable) {
		boolean oldMaximizable = maximizable;
		maximizable = newMaximizable;
		boolean oldMaximizableESet = maximizableESet;
		maximizableESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PANEL__MAXIMIZABLE, oldMaximizable, maximizable, !oldMaximizableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMaximizable() {
		boolean oldMaximizable = maximizable;
		boolean oldMaximizableESet = maximizableESet;
		maximizable = MAXIMIZABLE_EDEFAULT;
		maximizableESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PANEL__MAXIMIZABLE, oldMaximizable, MAXIMIZABLE_EDEFAULT, oldMaximizableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMaximizable() {
		return maximizableESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMinimizable() {
		return minimizable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinimizable(boolean newMinimizable) {
		boolean oldMinimizable = minimizable;
		minimizable = newMinimizable;
		boolean oldMinimizableESet = minimizableESet;
		minimizableESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PANEL__MINIMIZABLE, oldMinimizable, minimizable, !oldMinimizableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMinimizable() {
		boolean oldMinimizable = minimizable;
		boolean oldMinimizableESet = minimizableESet;
		minimizable = MINIMIZABLE_EDEFAULT;
		minimizableESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PANEL__MINIMIZABLE, oldMinimizable, MINIMIZABLE_EDEFAULT, oldMinimizableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMinimizable() {
		return minimizableESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Component> getComponent() {
		if (component == null) {
			component = new EObjectEList<Component>(Component.class, this, BEViewsConfigurationPackage.PANEL__COMPONENT);
		}
		return component;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Layout getLayout() {
		return layout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLayout(Layout newLayout, NotificationChain msgs) {
		Layout oldLayout = layout;
		layout = newLayout;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PANEL__LAYOUT, oldLayout, newLayout);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLayout(Layout newLayout) {
		if (newLayout != layout) {
			NotificationChain msgs = null;
			if (layout != null)
				msgs = ((InternalEObject)layout).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PANEL__LAYOUT, null, msgs);
			if (newLayout != null)
				msgs = ((InternalEObject)newLayout).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PANEL__LAYOUT, null, msgs);
			msgs = basicSetLayout(newLayout, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PANEL__LAYOUT, newLayout, newLayout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.PANEL__LAYOUT:
				return basicSetLayout(null, msgs);
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
			case BEViewsConfigurationPackage.PANEL__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.PANEL__SPAN:
				return getSpan();
			case BEViewsConfigurationPackage.PANEL__SCROLL_BAR:
				return getScrollBar();
			case BEViewsConfigurationPackage.PANEL__STATE:
				return getState();
			case BEViewsConfigurationPackage.PANEL__MAXIMIZABLE:
				return isMaximizable();
			case BEViewsConfigurationPackage.PANEL__MINIMIZABLE:
				return isMinimizable();
			case BEViewsConfigurationPackage.PANEL__COMPONENT:
				return getComponent();
			case BEViewsConfigurationPackage.PANEL__LAYOUT:
				return getLayout();
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
			case BEViewsConfigurationPackage.PANEL__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.PANEL__SPAN:
				setSpan((String)newValue);
				return;
			case BEViewsConfigurationPackage.PANEL__SCROLL_BAR:
				setScrollBar((ScrollBarConfigEnum)newValue);
				return;
			case BEViewsConfigurationPackage.PANEL__STATE:
				setState((PanelStateEnum)newValue);
				return;
			case BEViewsConfigurationPackage.PANEL__MAXIMIZABLE:
				setMaximizable((Boolean)newValue);
				return;
			case BEViewsConfigurationPackage.PANEL__MINIMIZABLE:
				setMinimizable((Boolean)newValue);
				return;
			case BEViewsConfigurationPackage.PANEL__COMPONENT:
				getComponent().clear();
				getComponent().addAll((Collection<? extends Component>)newValue);
				return;
			case BEViewsConfigurationPackage.PANEL__LAYOUT:
				setLayout((Layout)newValue);
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
			case BEViewsConfigurationPackage.PANEL__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.PANEL__SPAN:
				setSpan(SPAN_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.PANEL__SCROLL_BAR:
				unsetScrollBar();
				return;
			case BEViewsConfigurationPackage.PANEL__STATE:
				unsetState();
				return;
			case BEViewsConfigurationPackage.PANEL__MAXIMIZABLE:
				unsetMaximizable();
				return;
			case BEViewsConfigurationPackage.PANEL__MINIMIZABLE:
				unsetMinimizable();
				return;
			case BEViewsConfigurationPackage.PANEL__COMPONENT:
				getComponent().clear();
				return;
			case BEViewsConfigurationPackage.PANEL__LAYOUT:
				setLayout((Layout)null);
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
			case BEViewsConfigurationPackage.PANEL__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.PANEL__SPAN:
				return SPAN_EDEFAULT == null ? span != null : !SPAN_EDEFAULT.equals(span);
			case BEViewsConfigurationPackage.PANEL__SCROLL_BAR:
				return isSetScrollBar();
			case BEViewsConfigurationPackage.PANEL__STATE:
				return isSetState();
			case BEViewsConfigurationPackage.PANEL__MAXIMIZABLE:
				return isSetMaximizable();
			case BEViewsConfigurationPackage.PANEL__MINIMIZABLE:
				return isSetMinimizable();
			case BEViewsConfigurationPackage.PANEL__COMPONENT:
				return component != null && !component.isEmpty();
			case BEViewsConfigurationPackage.PANEL__LAYOUT:
				return layout != null;
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
		result.append(", span: ");
		result.append(span);
		result.append(", scrollBar: ");
		if (scrollBarESet) result.append(scrollBar); else result.append("<unset>");
		result.append(", state: ");
		if (stateESet) result.append(state); else result.append("<unset>");
		result.append(", maximizable: ");
		if (maximizableESet) result.append(maximizable); else result.append("<unset>");
		result.append(", minimizable: ");
		if (minimizableESet) result.append(minimizable); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PanelImpl
