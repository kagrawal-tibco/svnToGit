/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

import com.tibco.cep.studio.tester.emf.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.OperationObjectType;
import com.tibco.cep.studio.tester.emf.model.OperationType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Object Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.OperationObjectTypeImpl#getExecutionObject <em>Execution Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.OperationObjectTypeImpl#getOperation <em>Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperationObjectTypeImpl extends EObjectImpl implements OperationObjectType {
	/**
	 * The cached value of the '{@link #getExecutionObject() <em>Execution Object</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutionObject()
	 * @generated
	 * @ordered
	 */
	protected EList<ExecutionObjectType> executionObject;

	/**
	 * The default value of the '{@link #getOperation() <em>Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperation()
	 * @generated
	 * @ordered
	 */
	protected static final OperationType OPERATION_EDEFAULT = OperationType.CREATED;

	/**
	 * The cached value of the '{@link #getOperation() <em>Operation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperation()
	 * @generated
	 * @ordered
	 */
	protected OperationType operation = OPERATION_EDEFAULT;

	/**
	 * This is true if the Operation attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean operationESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationObjectTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.OPERATION_OBJECT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExecutionObjectType> getExecutionObject() {
		if (executionObject == null) {
			executionObject = new EObjectContainmentEList<ExecutionObjectType>(ExecutionObjectType.class, this, ModelPackage.OPERATION_OBJECT_TYPE__EXECUTION_OBJECT);
		}
		return executionObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationType getOperation() {
		return operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperation(OperationType newOperation) {
		OperationType oldOperation = operation;
		operation = newOperation == null ? OPERATION_EDEFAULT : newOperation;
		boolean oldOperationESet = operationESet;
		operationESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.OPERATION_OBJECT_TYPE__OPERATION, oldOperation, operation, !oldOperationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOperation() {
		OperationType oldOperation = operation;
		boolean oldOperationESet = operationESet;
		operation = OPERATION_EDEFAULT;
		operationESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.OPERATION_OBJECT_TYPE__OPERATION, oldOperation, OPERATION_EDEFAULT, oldOperationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOperation() {
		return operationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.OPERATION_OBJECT_TYPE__EXECUTION_OBJECT:
				return ((InternalEList<?>)getExecutionObject()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.OPERATION_OBJECT_TYPE__EXECUTION_OBJECT:
				return getExecutionObject();
			case ModelPackage.OPERATION_OBJECT_TYPE__OPERATION:
				return getOperation();
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
			case ModelPackage.OPERATION_OBJECT_TYPE__EXECUTION_OBJECT:
				getExecutionObject().clear();
				getExecutionObject().addAll((Collection<? extends ExecutionObjectType>)newValue);
				return;
			case ModelPackage.OPERATION_OBJECT_TYPE__OPERATION:
				setOperation((OperationType)newValue);
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
			case ModelPackage.OPERATION_OBJECT_TYPE__EXECUTION_OBJECT:
				getExecutionObject().clear();
				return;
			case ModelPackage.OPERATION_OBJECT_TYPE__OPERATION:
				unsetOperation();
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
			case ModelPackage.OPERATION_OBJECT_TYPE__EXECUTION_OBJECT:
				return executionObject != null && !executionObject.isEmpty();
			case ModelPackage.OPERATION_OBJECT_TYPE__OPERATION:
				return isSetOperation();
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
		result.append(" (operation: ");
		if (operationESet) result.append(operation); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //OperationObjectTypeImpl
