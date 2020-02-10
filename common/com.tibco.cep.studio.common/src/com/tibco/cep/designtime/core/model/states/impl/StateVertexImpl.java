/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StateVertex;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ModelError;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Vertex</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateVertexImpl#getIncomingTransitions <em>Incoming Transitions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateVertexImpl#getOutgoingTransitions <em>Outgoing Transitions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateVertexImpl extends StateEntityImpl implements StateVertex {
	/**
	 * The cached value of the '{@link #getIncomingTransitions() <em>Incoming Transitions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<StateTransition> incomingTransitions;

	/**
	 * The cached value of the '{@link #getOutgoingTransitions() <em>Outgoing Transitions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<StateTransition> outgoingTransitions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateVertexImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE_VERTEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateTransition> getIncomingTransitions() {
		if (incomingTransitions == null) {
			incomingTransitions = new EObjectResolvingEList<StateTransition>(StateTransition.class, this, StatesPackage.STATE_VERTEX__INCOMING_TRANSITIONS);
		}
		return incomingTransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateTransition> getOutgoingTransitions() {
		if (outgoingTransitions == null) {
			outgoingTransitions = new EObjectResolvingEList<StateTransition>(StateTransition.class, this, StatesPackage.STATE_VERTEX__OUTGOING_TRANSITIONS);
		}
		return outgoingTransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFullPath() {
		// TODO: implement this method
		return getFolder() + getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<ModelError> getModelErrors() {
		return new BasicEList<ModelError>();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StatesPackage.STATE_VERTEX__INCOMING_TRANSITIONS:
				return getIncomingTransitions();
			case StatesPackage.STATE_VERTEX__OUTGOING_TRANSITIONS:
				return getOutgoingTransitions();
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
			case StatesPackage.STATE_VERTEX__INCOMING_TRANSITIONS:
				getIncomingTransitions().clear();
				getIncomingTransitions().addAll((Collection<? extends StateTransition>)newValue);
				return;
			case StatesPackage.STATE_VERTEX__OUTGOING_TRANSITIONS:
				getOutgoingTransitions().clear();
				getOutgoingTransitions().addAll((Collection<? extends StateTransition>)newValue);
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
			case StatesPackage.STATE_VERTEX__INCOMING_TRANSITIONS:
				getIncomingTransitions().clear();
				return;
			case StatesPackage.STATE_VERTEX__OUTGOING_TRANSITIONS:
				getOutgoingTransitions().clear();
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
			case StatesPackage.STATE_VERTEX__INCOMING_TRANSITIONS:
				return incomingTransitions != null && !incomingTransitions.isEmpty();
			case StatesPackage.STATE_VERTEX__OUTGOING_TRANSITIONS:
				return outgoingTransitions != null && !outgoingTransitions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateVertexImpl
