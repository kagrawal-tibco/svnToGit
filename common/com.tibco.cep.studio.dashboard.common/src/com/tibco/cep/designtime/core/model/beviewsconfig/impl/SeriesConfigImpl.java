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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Series Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigImpl#getToolTip <em>Tool Tip</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigImpl#getActionRule <em>Action Rule</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigImpl#getQueryLink <em>Query Link</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigImpl#getRollOverConfig <em>Roll Over Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesConfigImpl#getRelatedElement <em>Related Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SeriesConfigImpl extends BEViewsElementImpl implements SeriesConfig {
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
	 * The default value of the '{@link #getToolTip() <em>Tool Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolTip()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOL_TIP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getToolTip() <em>Tool Tip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolTip()
	 * @generated
	 * @ordered
	 */
	protected String toolTip = TOOL_TIP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getActionRule() <em>Action Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionRule()
	 * @generated
	 * @ordered
	 */
	protected ActionRule actionRule;

	/**
	 * The cached value of the '{@link #getQueryLink() <em>Query Link</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueryLink()
	 * @generated
	 * @ordered
	 */
	protected EObject queryLink;

	/**
	 * The cached value of the '{@link #getRollOverConfig() <em>Roll Over Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRollOverConfig()
	 * @generated
	 * @ordered
	 */
	protected EObject rollOverConfig;

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
	protected SeriesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getSeriesConfig();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_CONFIG__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getToolTip() {
		return toolTip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToolTip(String newToolTip) {
		String oldToolTip = toolTip;
		toolTip = newToolTip;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_CONFIG__TOOL_TIP, oldToolTip, toolTip));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionRule getActionRule() {
		return actionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActionRule(ActionRule newActionRule, NotificationChain msgs) {
		ActionRule oldActionRule = actionRule;
		actionRule = newActionRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE, oldActionRule, newActionRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionRule(ActionRule newActionRule) {
		if (newActionRule != actionRule) {
			NotificationChain msgs = null;
			if (actionRule != null)
				msgs = ((InternalEObject)actionRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE, null, msgs);
			if (newActionRule != null)
				msgs = ((InternalEObject)newActionRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE, null, msgs);
			msgs = basicSetActionRule(newActionRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE, newActionRule, newActionRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getQueryLink() {
		return queryLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQueryLink(EObject newQueryLink, NotificationChain msgs) {
		EObject oldQueryLink = queryLink;
		queryLink = newQueryLink;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK, oldQueryLink, newQueryLink);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueryLink(EObject newQueryLink) {
		if (newQueryLink != queryLink) {
			NotificationChain msgs = null;
			if (queryLink != null)
				msgs = ((InternalEObject)queryLink).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK, null, msgs);
			if (newQueryLink != null)
				msgs = ((InternalEObject)newQueryLink).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK, null, msgs);
			msgs = basicSetQueryLink(newQueryLink, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK, newQueryLink, newQueryLink));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getRollOverConfig() {
		return rollOverConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRollOverConfig(EObject newRollOverConfig, NotificationChain msgs) {
		EObject oldRollOverConfig = rollOverConfig;
		rollOverConfig = newRollOverConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG, oldRollOverConfig, newRollOverConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRollOverConfig(EObject newRollOverConfig) {
		if (newRollOverConfig != rollOverConfig) {
			NotificationChain msgs = null;
			if (rollOverConfig != null)
				msgs = ((InternalEObject)rollOverConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG, null, msgs);
			if (newRollOverConfig != null)
				msgs = ((InternalEObject)newRollOverConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG, null, msgs);
			msgs = basicSetRollOverConfig(newRollOverConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG, newRollOverConfig, newRollOverConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BEViewsElement> getRelatedElement() {
		if (relatedElement == null) {
			relatedElement = new EObjectResolvingEList<BEViewsElement>(BEViewsElement.class, this, BEViewsConfigurationPackage.SERIES_CONFIG__RELATED_ELEMENT);
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
			case BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE:
				return basicSetActionRule(null, msgs);
			case BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK:
				return basicSetQueryLink(null, msgs);
			case BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG:
				return basicSetRollOverConfig(null, msgs);
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
			case BEViewsConfigurationPackage.SERIES_CONFIG__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.SERIES_CONFIG__TOOL_TIP:
				return getToolTip();
			case BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE:
				return getActionRule();
			case BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK:
				return getQueryLink();
			case BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG:
				return getRollOverConfig();
			case BEViewsConfigurationPackage.SERIES_CONFIG__RELATED_ELEMENT:
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
			case BEViewsConfigurationPackage.SERIES_CONFIG__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__TOOL_TIP:
				setToolTip((String)newValue);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE:
				setActionRule((ActionRule)newValue);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK:
				setQueryLink((EObject)newValue);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG:
				setRollOverConfig((EObject)newValue);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__RELATED_ELEMENT:
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
			case BEViewsConfigurationPackage.SERIES_CONFIG__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__TOOL_TIP:
				setToolTip(TOOL_TIP_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE:
				setActionRule((ActionRule)null);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK:
				setQueryLink((EObject)null);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG:
				setRollOverConfig((EObject)null);
				return;
			case BEViewsConfigurationPackage.SERIES_CONFIG__RELATED_ELEMENT:
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
			case BEViewsConfigurationPackage.SERIES_CONFIG__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.SERIES_CONFIG__TOOL_TIP:
				return TOOL_TIP_EDEFAULT == null ? toolTip != null : !TOOL_TIP_EDEFAULT.equals(toolTip);
			case BEViewsConfigurationPackage.SERIES_CONFIG__ACTION_RULE:
				return actionRule != null;
			case BEViewsConfigurationPackage.SERIES_CONFIG__QUERY_LINK:
				return queryLink != null;
			case BEViewsConfigurationPackage.SERIES_CONFIG__ROLL_OVER_CONFIG:
				return rollOverConfig != null;
			case BEViewsConfigurationPackage.SERIES_CONFIG__RELATED_ELEMENT:
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
		result.append(", toolTip: ");
		result.append(toolTip);
		result.append(')');
		return result.toString();
	}

} //SeriesConfigImpl
