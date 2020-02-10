/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Composite</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateCompositeImpl#getTimeoutComposite <em>Timeout Composite</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateCompositeImpl#isConcurrentState <em>Concurrent State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateCompositeImpl#getRegions <em>Regions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateCompositeImpl#getStateEntities <em>State Entities</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateCompositeImpl extends StateImpl implements StateComposite {
	/**
	 * The default value of the '{@link #getTimeoutComposite() <em>Timeout Composite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutComposite()
	 * @generated
	 * @ordered
	 */
	protected static final int TIMEOUT_COMPOSITE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTimeoutComposite() <em>Timeout Composite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutComposite()
	 * @generated
	 * @ordered
	 */
	protected int timeoutComposite = TIMEOUT_COMPOSITE_EDEFAULT;

	/**
	 * The default value of the '{@link #isConcurrentState() <em>Concurrent State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConcurrentState()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONCURRENT_STATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConcurrentState() <em>Concurrent State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConcurrentState()
	 * @generated
	 * @ordered
	 */
	protected boolean concurrentState = CONCURRENT_STATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRegions() <em>Regions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegions()
	 * @generated
	 * @ordered
	 */
	protected EList<StateComposite> regions;

	/**
	 * The cached value of the '{@link #getStateEntities() <em>State Entities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateEntities()
	 * @generated
	 * @ordered
	 */
	protected EList<StateEntity> stateEntities;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateCompositeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE_COMPOSITE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTimeoutComposite() {
		return timeoutComposite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeoutComposite(int newTimeoutComposite) {
		int oldTimeoutComposite = timeoutComposite;
		timeoutComposite = newTimeoutComposite;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_COMPOSITE__TIMEOUT_COMPOSITE, oldTimeoutComposite, timeoutComposite));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConcurrentState() {
		return concurrentState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConcurrentState(boolean newConcurrentState) {
		boolean oldConcurrentState = concurrentState;
		concurrentState = newConcurrentState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_COMPOSITE__CONCURRENT_STATE, oldConcurrentState, concurrentState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateComposite> getRegions() {
		if (regions == null) {
			regions = new EObjectContainmentEList<StateComposite>(StateComposite.class, this, StatesPackage.STATE_COMPOSITE__REGIONS);
		}
		return regions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateEntity> getStateEntities() {
		if (stateEntities == null) {
			stateEntities = new EObjectContainmentEList<StateEntity>(StateEntity.class, this, StatesPackage.STATE_COMPOSITE__STATE_ENTITIES);
		}
		return stateEntities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isRegion() {
		return (eContainer() instanceof StateComposite && 
				  ((StateComposite) eContainer()).isConcurrentState());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StatesPackage.STATE_COMPOSITE__REGIONS:
				return ((InternalEList<?>)getRegions()).basicRemove(otherEnd, msgs);
			case StatesPackage.STATE_COMPOSITE__STATE_ENTITIES:
				return ((InternalEList<?>)getStateEntities()).basicRemove(otherEnd, msgs);
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
			case StatesPackage.STATE_COMPOSITE__TIMEOUT_COMPOSITE:
				return getTimeoutComposite();
			case StatesPackage.STATE_COMPOSITE__CONCURRENT_STATE:
				return isConcurrentState();
			case StatesPackage.STATE_COMPOSITE__REGIONS:
				return getRegions();
			case StatesPackage.STATE_COMPOSITE__STATE_ENTITIES:
				return getStateEntities();
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
			case StatesPackage.STATE_COMPOSITE__TIMEOUT_COMPOSITE:
				setTimeoutComposite((Integer)newValue);
				return;
			case StatesPackage.STATE_COMPOSITE__CONCURRENT_STATE:
				setConcurrentState((Boolean)newValue);
				return;
			case StatesPackage.STATE_COMPOSITE__REGIONS:
				getRegions().clear();
				getRegions().addAll((Collection<? extends StateComposite>)newValue);
				return;
			case StatesPackage.STATE_COMPOSITE__STATE_ENTITIES:
				getStateEntities().clear();
				getStateEntities().addAll((Collection<? extends StateEntity>)newValue);
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
			case StatesPackage.STATE_COMPOSITE__TIMEOUT_COMPOSITE:
				setTimeoutComposite(TIMEOUT_COMPOSITE_EDEFAULT);
				return;
			case StatesPackage.STATE_COMPOSITE__CONCURRENT_STATE:
				setConcurrentState(CONCURRENT_STATE_EDEFAULT);
				return;
			case StatesPackage.STATE_COMPOSITE__REGIONS:
				getRegions().clear();
				return;
			case StatesPackage.STATE_COMPOSITE__STATE_ENTITIES:
				getStateEntities().clear();
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
			case StatesPackage.STATE_COMPOSITE__TIMEOUT_COMPOSITE:
				return timeoutComposite != TIMEOUT_COMPOSITE_EDEFAULT;
			case StatesPackage.STATE_COMPOSITE__CONCURRENT_STATE:
				return concurrentState != CONCURRENT_STATE_EDEFAULT;
			case StatesPackage.STATE_COMPOSITE__REGIONS:
				return regions != null && !regions.isEmpty();
			case StatesPackage.STATE_COMPOSITE__STATE_ENTITIES:
				return stateEntities != null && !stateEntities.isEmpty();
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
		result.append(" (timeoutComposite: ");
		result.append(timeoutComposite);
		result.append(", concurrentState: ");
		result.append(concurrentState);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = super.getModelErrors();
		if (this instanceof StateSubmachine){
			return errorList;
		}
		List<Object> args = new ArrayList<Object>();
		// if composite state has more than on start state
		
		boolean startStateEqualToOne = isStartStateEqualToOne();
		if (!startStateEqualToOne && !isConcurrentState()){
			args.clear();
			args.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "StateComposite.errors.compositesMustHaveOneStartState", 
					                                              args, false);
			errorList.add(me);
		}
		// if Concurrent state does not have any region
		if(isConcurrentState()){
			List<StateComposite> regions = this.getRegions();
			if (isConcurrentState() && regions.size() < 2){
				// concurrent state should at least have 2 regions
				args.clear();
				args.add(this.getName());
				ModelError me = CommonValidationUtils.constructModelError(this, "StateComposite.errors.concurrentStateMustContainTwoRegions", args, false);
				errorList.add(me);			
			}
		}
		if (isRegion()){
			// Region must have only one end state
			if (isEndStateCountZero()){
				args.clear();
				args.add(this.getName());
				ModelError me = CommonValidationUtils.constructModelError(this, "StateComposite.errors.regionsMustHaveOneEndState", args, false);
				errorList.add(me);
			}
			
			
		}
		//Added by Anand - 01/17/2011 to fix BE-10395		
		if (TimeOutUnitsUtils.isValid(getTimeoutUnits()) == false) {
			args.clear();
			args.add(this.getName());
			if (isConcurrentState()) {
				ModelError me = CommonValidationUtils.constructModelError(this, "StateComposite.errors.concurrentInvalidTimeOutUnits", 
						args, false);
				errorList.add(me);
			}
			else if (isRegion()){
				ModelError me = CommonValidationUtils.constructModelError(this, "StateComposite.errors.regionInvalidTimeOutUnits", 
						args, false);
				errorList.add(me);
			}
			else {
				ModelError me = CommonValidationUtils.constructModelError(this, "StateComposite.errors.compositeInvalidTimeOutUnits", 
						args, false);
				errorList.add(me);
			}
		}
		return errorList;
	}
	
	public boolean isEndStateCountZero(){

		List<StateEntity> states = this.getStateEntities();
		for (StateEntity se : states){
			if (se instanceof StateEnd){				
				return false;	
			}
		}

		return true;		
	}
	
	public boolean isStartStateEqualToOne(){
		boolean found = false;
		List<StateEntity> states = this.getStateEntities();
		for (StateEntity se : states){
			if (se instanceof StateStart){
				if (found){
					return false;
				} else {
					found = true;
				}
			}
		}
		if (found){
			return true;
		}
		return false;		
	}
	
	

} //StateCompositeImpl
