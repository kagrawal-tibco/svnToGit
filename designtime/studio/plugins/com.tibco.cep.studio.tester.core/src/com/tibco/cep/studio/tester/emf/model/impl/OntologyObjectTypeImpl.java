/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.OntologyObjectType;
import com.tibco.cep.studio.tester.emf.model.OperationObjectType;

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
 * An implementation of the model object '<em><b>Ontology Object Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.OntologyObjectTypeImpl#getOperationObject <em>Operation Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.OntologyObjectTypeImpl#getDataType <em>Data Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OntologyObjectTypeImpl extends EObjectImpl implements OntologyObjectType {
	/**
	 * The cached value of the '{@link #getOperationObject() <em>Operation Object</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationObject()
	 * @generated
	 * @ordered
	 */
	protected EList<OperationObjectType> operationObject;

	/**
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final String DATA_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected String dataType = DATA_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OntologyObjectTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ONTOLOGY_OBJECT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OperationObjectType> getOperationObject() {
		if (operationObject == null) {
			operationObject = new EObjectContainmentEList<OperationObjectType>(OperationObjectType.class, this, ModelPackage.ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT);
		}
		return operationObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataType(String newDataType) {
		String oldDataType = dataType;
		dataType = newDataType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ONTOLOGY_OBJECT_TYPE__DATA_TYPE, oldDataType, dataType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT:
				return ((InternalEList<?>)getOperationObject()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT:
				return getOperationObject();
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__DATA_TYPE:
				return getDataType();
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
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT:
				getOperationObject().clear();
				getOperationObject().addAll((Collection<? extends OperationObjectType>)newValue);
				return;
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__DATA_TYPE:
				setDataType((String)newValue);
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
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT:
				getOperationObject().clear();
				return;
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__DATA_TYPE:
				setDataType(DATA_TYPE_EDEFAULT);
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
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__OPERATION_OBJECT:
				return operationObject != null && !operationObject.isEmpty();
			case ModelPackage.ONTOLOGY_OBJECT_TYPE__DATA_TYPE:
				return DATA_TYPE_EDEFAULT == null ? dataType != null : !DATA_TYPE_EDEFAULT.equals(dataType);
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
		result.append(" (dataType: ");
		result.append(dataType);
		result.append(')');
		return result.toString();
	}

} //OntologyObjectTypeImpl
