/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import java.io.File;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.core.index.model.SharedStateMachineElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl#getOwnerStateMachine <em>Owner State Machine</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl#getOwnerStateMachinePath <em>Owner State Machine Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl#getTimeout <em>Timeout</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl#getTimeoutUnits <em>Timeout Units</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class StateEntityImpl extends EntityImpl implements StateEntity {
	/**
	 * The cached value of the '{@link #getOwnerStateMachine() <em>Owner State Machine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerStateMachine()
	 * @generated
	 * @ordered
	 */
	protected StateMachine ownerStateMachine;

	/**
	 * The default value of the '{@link #getOwnerStateMachinePath() <em>Owner State Machine Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerStateMachinePath()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_STATE_MACHINE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOwnerStateMachinePath() <em>Owner State Machine Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerStateMachinePath()
	 * @generated
	 * @ordered
	 */
	protected String ownerStateMachinePath = OWNER_STATE_MACHINE_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected StateEntity parent;

	/**
	 * The default value of the '{@link #getTimeout() <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeout()
	 * @generated
	 * @ordered
	 */
	protected static final int TIMEOUT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTimeout() <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeout()
	 * @generated
	 * @ordered
	 */
	protected int timeout = TIMEOUT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeoutUnits() <em>Timeout Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutUnits()
	 * @generated
	 * @ordered
	 */
	protected static final TIMEOUT_UNITS TIMEOUT_UNITS_EDEFAULT = TIMEOUT_UNITS.MILLISECONDS;

	/**
	 * The cached value of the '{@link #getTimeoutUnits() <em>Timeout Units</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutUnits()
	 * @generated
	 * @ordered
	 */
	protected TIMEOUT_UNITS timeoutUnits = TIMEOUT_UNITS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateEntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE_ENTITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public StateMachine getOwnerStateMachine() {
		// NOTE: If this method is changed, must also change StateLinkImpl::getOwnerStateMachine(), as it is identical code
		if (ownerStateMachine != null && ownerStateMachine.eIsProxy()) {
			InternalEObject oldOwnerStateMachine = (InternalEObject)ownerStateMachine;
			ownerStateMachine = (StateMachine)eResolveProxy(oldOwnerStateMachine);
			if (ownerStateMachine != oldOwnerStateMachine) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatesPackage.STATE_VERTEX__OWNER_STATE_MACHINE, oldOwnerStateMachine, ownerStateMachine));
			}
		}
		EObject container = null;
		if (this instanceof StateMachine && eContainer() == null) {
			container = (StateMachine) this;
		} else {
			container = this.eContainer();
			while (container!= null && container.eContainer() != null && !(container instanceof SharedStateMachineElement)) {
				container = container.eContainer();
			}
		}
		String ownerPath = this.getOwnerStateMachinePath();
		if (container instanceof SharedStateMachineElement) {
			container = ((SharedStateMachineElement) container).getSharedEntity();
		}
		if (container instanceof StateMachine) {
			if (ownerPath == null && this instanceof StateMachine) {
				ownerStateMachine = (StateMachine) this;
				return ownerStateMachine;
			} else if (((StateMachine) container).getFullPath().equals(ownerPath)) {
				ownerStateMachine = (StateMachine) container;
				return ownerStateMachine;
			} else if (ownerPath == null && container instanceof StateMachine) {
				ownerStateMachine = (StateMachine) container;
				return ownerStateMachine;
			}
		}
		
		if (ownerPath != null && ownerPath.trim().length() > 0){			
			if(ownerProjectName == null) {
				EObject parent = this.eContainer();
				while(ownerProjectName == null && parent != null) {
					if(parent instanceof Entity) {
						Entity e = (Entity) parent;
						if(e.getOwnerProjectName() != null) {
							setOwnerProjectName(e.getOwnerProjectName());
							break;
						}
					}
					parent = parent.eContainer();
				}
			}
			ownerStateMachine = (StateMachine) CommonIndexUtils.getEntity(ownerProjectName, ownerPath);
			if (ownerStateMachine == null) {
				// look up from disk
				String indexRootPath = CommonIndexUtils.getIndexRootPath(ownerProjectName);
				if (indexRootPath != null) {
					ownerStateMachine = loadStateMachine(indexRootPath, ownerPath);
				}
			}
		}
		return ownerStateMachine;
	}

	private StateMachine loadStateMachine(String indexRootPath, String ownerPath) {
		try {
			File f = new File(indexRootPath+ownerPath+"."+CommonIndexUtils.STATEMACHINE_EXTENSION);
			if (f.exists()) {
				EObject eObj = CommonIndexUtils.loadEObject(f.toURI());
				if (eObj instanceof StateMachine) {
					return (StateMachine) eObj;
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setOwnerStateMachine(StateMachine newOwnerStateMachine) {
		StateMachine oldOwnerStateMachine = ownerStateMachine;
		ownerStateMachine = newOwnerStateMachine;
		String smPath = ownerStateMachine == null ? null : ownerStateMachine.getFullPath();
		setOwnerStateMachinePath(smPath);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_VERTEX__OWNER_STATE_MACHINE, oldOwnerStateMachine, ownerStateMachine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getOwnerStateMachinePath() {
		if (ownerStateMachinePath != null) {
			return ownerStateMachinePath;
		}
		if (ownerStateMachine != null) {
			// for backward compatibility
			return ownerStateMachine.getFullPath();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public StateEntity getParent() {
		if (eContainer() instanceof StateEntity) {
			return (StateEntity) eContainer();
		}
		return null;
//		if (parent != null && parent.eIsProxy()) {
//			InternalEObject oldParent = (InternalEObject)parent;
//			parent = (StateEntity)eResolveProxy(oldParent);
//			if (parent != oldParent) {
//				if (eNotificationRequired())
//					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatesPackage.STATE_VERTEX__PARENT, oldParent, parent));
//			}
//		}
//		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine basicGetOwnerStateMachine() {
		return ownerStateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerStateMachinePath(String newOwnerStateMachinePath) {
		String oldOwnerStateMachinePath = ownerStateMachinePath;
		ownerStateMachinePath = newOwnerStateMachinePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE_PATH, oldOwnerStateMachinePath, ownerStateMachinePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateEntity basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(StateEntity newParent) {
		StateEntity oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_ENTITY__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeout(int newTimeout) {
		int oldTimeout = timeout;
		timeout = newTimeout;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_ENTITY__TIMEOUT, oldTimeout, timeout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TIMEOUT_UNITS getTimeoutUnits() {
		return timeoutUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeoutUnits(TIMEOUT_UNITS newTimeoutUnits) {
		TIMEOUT_UNITS oldTimeoutUnits = timeoutUnits;
		timeoutUnits = newTimeoutUnits == null ? TIMEOUT_UNITS_EDEFAULT : newTimeoutUnits;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_ENTITY__TIMEOUT_UNITS, oldTimeoutUnits, timeoutUnits));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE:
				if (resolve) return getOwnerStateMachine();
				return basicGetOwnerStateMachine();
			case StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE_PATH:
				return getOwnerStateMachinePath();
			case StatesPackage.STATE_ENTITY__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case StatesPackage.STATE_ENTITY__TIMEOUT:
				return getTimeout();
			case StatesPackage.STATE_ENTITY__TIMEOUT_UNITS:
				return getTimeoutUnits();
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
			case StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE:
				setOwnerStateMachine((StateMachine)newValue);
				return;
			case StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE_PATH:
				setOwnerStateMachinePath((String)newValue);
				return;
			case StatesPackage.STATE_ENTITY__PARENT:
				setParent((StateEntity)newValue);
				return;
			case StatesPackage.STATE_ENTITY__TIMEOUT:
				setTimeout((Integer)newValue);
				return;
			case StatesPackage.STATE_ENTITY__TIMEOUT_UNITS:
				setTimeoutUnits((TIMEOUT_UNITS)newValue);
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
			case StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE:
				setOwnerStateMachine((StateMachine)null);
				return;
			case StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE_PATH:
				setOwnerStateMachinePath(OWNER_STATE_MACHINE_PATH_EDEFAULT);
				return;
			case StatesPackage.STATE_ENTITY__PARENT:
				setParent((StateEntity)null);
				return;
			case StatesPackage.STATE_ENTITY__TIMEOUT:
				setTimeout(TIMEOUT_EDEFAULT);
				return;
			case StatesPackage.STATE_ENTITY__TIMEOUT_UNITS:
				setTimeoutUnits(TIMEOUT_UNITS_EDEFAULT);
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
			case StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE:
				return ownerStateMachine != null;
			case StatesPackage.STATE_ENTITY__OWNER_STATE_MACHINE_PATH:
				return OWNER_STATE_MACHINE_PATH_EDEFAULT == null ? ownerStateMachinePath != null : !OWNER_STATE_MACHINE_PATH_EDEFAULT.equals(ownerStateMachinePath);
			case StatesPackage.STATE_ENTITY__PARENT:
				return parent != null;
			case StatesPackage.STATE_ENTITY__TIMEOUT:
				return timeout != TIMEOUT_EDEFAULT;
			case StatesPackage.STATE_ENTITY__TIMEOUT_UNITS:
				return timeoutUnits != TIMEOUT_UNITS_EDEFAULT;
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
		result.append(" (ownerStateMachinePath: ");
		result.append(ownerStateMachinePath);
		result.append(", timeout: ");
		result.append(timeout);
		result.append(", timeoutUnits: ");
		result.append(timeoutUnits);
		result.append(')');
		return result.toString();
	}

} //StateEntityImpl
