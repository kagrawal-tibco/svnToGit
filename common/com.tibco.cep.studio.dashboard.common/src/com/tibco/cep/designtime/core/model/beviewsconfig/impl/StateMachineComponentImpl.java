/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateMachineComponentImpl#getStateMachine <em>State Machine</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateMachineComponentImpl#getStateVisualizationWidth <em>State Visualization Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateMachineComponentImpl#getStateVisualizationHeight <em>State Visualization Height</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateMachineComponentImpl extends ComponentImpl implements StateMachineComponent {
	/**
	 * The cached value of the '{@link #getStateMachine() <em>State Machine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateMachine()
	 * @generated
	 * @ordered
	 */
	protected EObject stateMachine;
	/**
	 * The default value of the '{@link #getStateVisualizationWidth() <em>State Visualization Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateVisualizationWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int STATE_VISUALIZATION_WIDTH_EDEFAULT = 80;
	/**
	 * The cached value of the '{@link #getStateVisualizationWidth() <em>State Visualization Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateVisualizationWidth()
	 * @generated
	 * @ordered
	 */
	protected int stateVisualizationWidth = STATE_VISUALIZATION_WIDTH_EDEFAULT;
	/**
	 * The default value of the '{@link #getStateVisualizationHeight() <em>State Visualization Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateVisualizationHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int STATE_VISUALIZATION_HEIGHT_EDEFAULT = 40;
	/**
	 * The cached value of the '{@link #getStateVisualizationHeight() <em>State Visualization Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateVisualizationHeight()
	 * @generated
	 * @ordered
	 */
	protected int stateVisualizationHeight = STATE_VISUALIZATION_HEIGHT_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateMachineComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getStateMachineComponent();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getStateMachine() {
		return stateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateMachine(EObject newStateMachine) {
		EObject oldStateMachine = stateMachine;
		stateMachine = newStateMachine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_MACHINE, oldStateMachine, stateMachine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStateVisualizationWidth() {
		return stateVisualizationWidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateVisualizationWidth(int newStateVisualizationWidth) {
		int oldStateVisualizationWidth = stateVisualizationWidth;
		stateVisualizationWidth = newStateVisualizationWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_WIDTH, oldStateVisualizationWidth, stateVisualizationWidth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStateVisualizationHeight() {
		return stateVisualizationHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateVisualizationHeight(int newStateVisualizationHeight) {
		int oldStateVisualizationHeight = stateVisualizationHeight;
		stateVisualizationHeight = newStateVisualizationHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_HEIGHT, oldStateVisualizationHeight, stateVisualizationHeight));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_MACHINE:
				return getStateMachine();
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_WIDTH:
				return getStateVisualizationWidth();
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_HEIGHT:
				return getStateVisualizationHeight();
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
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_MACHINE:
				setStateMachine((EObject)newValue);
				return;
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_WIDTH:
				setStateVisualizationWidth((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_HEIGHT:
				setStateVisualizationHeight((Integer)newValue);
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
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_MACHINE:
				setStateMachine((EObject)null);
				return;
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_WIDTH:
				setStateVisualizationWidth(STATE_VISUALIZATION_WIDTH_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_HEIGHT:
				setStateVisualizationHeight(STATE_VISUALIZATION_HEIGHT_EDEFAULT);
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
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_MACHINE:
				return stateMachine != null;
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_WIDTH:
				return stateVisualizationWidth != STATE_VISUALIZATION_WIDTH_EDEFAULT;
			case BEViewsConfigurationPackage.STATE_MACHINE_COMPONENT__STATE_VISUALIZATION_HEIGHT:
				return stateVisualizationHeight != STATE_VISUALIZATION_HEIGHT_EDEFAULT;
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
		result.append(" (stateVisualizationWidth: ");
		result.append(stateVisualizationWidth);
		result.append(", stateVisualizationHeight: ");
		result.append(stateVisualizationHeight);
		result.append(')');
		return result.toString();
	}

} //StateMachineComponentImpl
