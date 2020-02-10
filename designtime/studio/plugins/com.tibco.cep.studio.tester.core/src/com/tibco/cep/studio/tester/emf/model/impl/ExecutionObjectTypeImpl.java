/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.tester.emf.model.CasualObjectsType;
import com.tibco.cep.studio.tester.emf.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.emf.model.InvocationObjectType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.ReteObjectType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Execution Object Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.ExecutionObjectTypeImpl#getReteObject <em>Rete Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.ExecutionObjectTypeImpl#getInvocationObject <em>Invocation Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.ExecutionObjectTypeImpl#getCasualObjects <em>Casual Objects</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExecutionObjectTypeImpl extends EObjectImpl implements ExecutionObjectType {
	/**
	 * The cached value of the '{@link #getReteObject() <em>Rete Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReteObject()
	 * @generated
	 * @ordered
	 */
	protected ReteObjectType reteObject;

	/**
	 * The cached value of the '{@link #getInvocationObject() <em>Invocation Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvocationObject()
	 * @generated
	 * @ordered
	 */
	protected InvocationObjectType invocationObject;

	/**
	 * The cached value of the '{@link #getCasualObjects() <em>Casual Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCasualObjects()
	 * @generated
	 * @ordered
	 */
	protected CasualObjectsType casualObjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecutionObjectTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.EXECUTION_OBJECT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReteObjectType getReteObject() {
		return reteObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReteObject(ReteObjectType newReteObject, NotificationChain msgs) {
		ReteObjectType oldReteObject = reteObject;
		reteObject = newReteObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT, oldReteObject, newReteObject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReteObject(ReteObjectType newReteObject) {
		if (newReteObject != reteObject) {
			NotificationChain msgs = null;
			if (reteObject != null)
				msgs = ((InternalEObject)reteObject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT, null, msgs);
			if (newReteObject != null)
				msgs = ((InternalEObject)newReteObject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT, null, msgs);
			msgs = basicSetReteObject(newReteObject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT, newReteObject, newReteObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InvocationObjectType getInvocationObject() {
		return invocationObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInvocationObject(InvocationObjectType newInvocationObject, NotificationChain msgs) {
		InvocationObjectType oldInvocationObject = invocationObject;
		invocationObject = newInvocationObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT, oldInvocationObject, newInvocationObject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvocationObject(InvocationObjectType newInvocationObject) {
		if (newInvocationObject != invocationObject) {
			NotificationChain msgs = null;
			if (invocationObject != null)
				msgs = ((InternalEObject)invocationObject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT, null, msgs);
			if (newInvocationObject != null)
				msgs = ((InternalEObject)newInvocationObject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT, null, msgs);
			msgs = basicSetInvocationObject(newInvocationObject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT, newInvocationObject, newInvocationObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CasualObjectsType getCasualObjects() {
		return casualObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCasualObjects(CasualObjectsType newCasualObjects, NotificationChain msgs) {
		CasualObjectsType oldCasualObjects = casualObjects;
		casualObjects = newCasualObjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS, oldCasualObjects, newCasualObjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCasualObjects(CasualObjectsType newCasualObjects) {
		if (newCasualObjects != casualObjects) {
			NotificationChain msgs = null;
			if (casualObjects != null)
				msgs = ((InternalEObject)casualObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS, null, msgs);
			if (newCasualObjects != null)
				msgs = ((InternalEObject)newCasualObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS, null, msgs);
			msgs = basicSetCasualObjects(newCasualObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS, newCasualObjects, newCasualObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT:
				return basicSetReteObject(null, msgs);
			case ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT:
				return basicSetInvocationObject(null, msgs);
			case ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS:
				return basicSetCasualObjects(null, msgs);
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
			case ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT:
				return getReteObject();
			case ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT:
				return getInvocationObject();
			case ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS:
				return getCasualObjects();
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
			case ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT:
				setReteObject((ReteObjectType)newValue);
				return;
			case ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT:
				setInvocationObject((InvocationObjectType)newValue);
				return;
			case ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS:
				setCasualObjects((CasualObjectsType)newValue);
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
			case ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT:
				setReteObject((ReteObjectType)null);
				return;
			case ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT:
				setInvocationObject((InvocationObjectType)null);
				return;
			case ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS:
				setCasualObjects((CasualObjectsType)null);
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
			case ModelPackage.EXECUTION_OBJECT_TYPE__RETE_OBJECT:
				return reteObject != null;
			case ModelPackage.EXECUTION_OBJECT_TYPE__INVOCATION_OBJECT:
				return invocationObject != null;
			case ModelPackage.EXECUTION_OBJECT_TYPE__CASUAL_OBJECTS:
				return casualObjects != null;
		}
		return super.eIsSet(featureID);
	}

} //ExecutionObjectTypeImpl
