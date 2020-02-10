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
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.Panel;
import com.tibco.cep.designtime.core.model.beviewsconfig.Partition;
import com.tibco.cep.designtime.core.model.beviewsconfig.PartitionStateEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Partition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PartitionImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PartitionImpl#isVertical <em>Vertical</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PartitionImpl#getSpan <em>Span</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PartitionImpl#getState <em>State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PartitionImpl#getPanel <em>Panel</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PartitionImpl extends BEViewsElementImpl implements Partition {
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
	 * The default value of the '{@link #isVertical() <em>Vertical</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVertical()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VERTICAL_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVertical() <em>Vertical</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVertical()
	 * @generated
	 * @ordered
	 */
	protected boolean vertical = VERTICAL_EDEFAULT;

	/**
	 * This is true if the Vertical attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean verticalESet;

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
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final PartitionStateEnum STATE_EDEFAULT = PartitionStateEnum.OPEN;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected PartitionStateEnum state = STATE_EDEFAULT;

	/**
	 * This is true if the State attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean stateESet;

	/**
	 * The cached value of the '{@link #getPanel() <em>Panel</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPanel()
	 * @generated
	 * @ordered
	 */
	protected EList<Panel> panel;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PartitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getPartition();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PARTITION__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVertical() {
		return vertical;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVertical(boolean newVertical) {
		boolean oldVertical = vertical;
		vertical = newVertical;
		boolean oldVerticalESet = verticalESet;
		verticalESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PARTITION__VERTICAL, oldVertical, vertical, !oldVerticalESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetVertical() {
		boolean oldVertical = vertical;
		boolean oldVerticalESet = verticalESet;
		vertical = VERTICAL_EDEFAULT;
		verticalESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PARTITION__VERTICAL, oldVertical, VERTICAL_EDEFAULT, oldVerticalESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetVertical() {
		return verticalESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PARTITION__SPAN, oldSpan, span));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartitionStateEnum getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(PartitionStateEnum newState) {
		PartitionStateEnum oldState = state;
		state = newState == null ? STATE_EDEFAULT : newState;
		boolean oldStateESet = stateESet;
		stateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PARTITION__STATE, oldState, state, !oldStateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetState() {
		PartitionStateEnum oldState = state;
		boolean oldStateESet = stateESet;
		state = STATE_EDEFAULT;
		stateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PARTITION__STATE, oldState, STATE_EDEFAULT, oldStateESet));
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
	public EList<Panel> getPanel() {
		if (panel == null) {
			panel = new EObjectContainmentEList<Panel>(Panel.class, this, BEViewsConfigurationPackage.PARTITION__PANEL);
		}
		return panel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.PARTITION__PANEL:
				return ((InternalEList<?>)getPanel()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.PARTITION__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.PARTITION__VERTICAL:
				return isVertical();
			case BEViewsConfigurationPackage.PARTITION__SPAN:
				return getSpan();
			case BEViewsConfigurationPackage.PARTITION__STATE:
				return getState();
			case BEViewsConfigurationPackage.PARTITION__PANEL:
				return getPanel();
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
			case BEViewsConfigurationPackage.PARTITION__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.PARTITION__VERTICAL:
				setVertical((Boolean)newValue);
				return;
			case BEViewsConfigurationPackage.PARTITION__SPAN:
				setSpan((String)newValue);
				return;
			case BEViewsConfigurationPackage.PARTITION__STATE:
				setState((PartitionStateEnum)newValue);
				return;
			case BEViewsConfigurationPackage.PARTITION__PANEL:
				getPanel().clear();
				getPanel().addAll((Collection<? extends Panel>)newValue);
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
			case BEViewsConfigurationPackage.PARTITION__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.PARTITION__VERTICAL:
				unsetVertical();
				return;
			case BEViewsConfigurationPackage.PARTITION__SPAN:
				setSpan(SPAN_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.PARTITION__STATE:
				unsetState();
				return;
			case BEViewsConfigurationPackage.PARTITION__PANEL:
				getPanel().clear();
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
			case BEViewsConfigurationPackage.PARTITION__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.PARTITION__VERTICAL:
				return isSetVertical();
			case BEViewsConfigurationPackage.PARTITION__SPAN:
				return SPAN_EDEFAULT == null ? span != null : !SPAN_EDEFAULT.equals(span);
			case BEViewsConfigurationPackage.PARTITION__STATE:
				return isSetState();
			case BEViewsConfigurationPackage.PARTITION__PANEL:
				return panel != null && !panel.isEmpty();
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
		result.append(", vertical: ");
		if (verticalESet) result.append(vertical); else result.append("<unset>");
		result.append(", span: ");
		result.append(span);
		result.append(", state: ");
		if (stateESet) result.append(state); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PartitionImpl
