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

import com.tibco.cep.studio.tester.emf.model.DataTypeType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.PropertyModificationType;
import com.tibco.cep.studio.tester.emf.model.ValueType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Modification Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl#isMultiple <em>Multiple</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.PropertyModificationTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyModificationTypeImpl extends EObjectImpl implements PropertyModificationType {
	/**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected ValueType oldValue;

	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected ValueType newValue;

	/**
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final DataTypeType DATA_TYPE_EDEFAULT = DataTypeType.STRING;

	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected DataTypeType dataType = DATA_TYPE_EDEFAULT;

	/**
	 * This is true if the Data Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean dataTypeESet;

	/**
	 * The default value of the '{@link #isMultiple() <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiple()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTIPLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultiple() <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiple()
	 * @generated
	 * @ordered
	 */
	protected boolean multiple = MULTIPLE_EDEFAULT;

	/**
	 * This is true if the Multiple attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean multipleESet;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyModificationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PROPERTY_MODIFICATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueType getOldValue() {
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOldValue(ValueType newOldValue, NotificationChain msgs) {
		ValueType oldOldValue = oldValue;
		oldValue = newOldValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE, oldOldValue, newOldValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldValue(ValueType newOldValue) {
		if (newOldValue != oldValue) {
			NotificationChain msgs = null;
			if (oldValue != null)
				msgs = ((InternalEObject)oldValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE, null, msgs);
			if (newOldValue != null)
				msgs = ((InternalEObject)newOldValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE, null, msgs);
			msgs = basicSetOldValue(newOldValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE, newOldValue, newOldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueType getNewValue() {
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNewValue(ValueType newNewValue, NotificationChain msgs) {
		ValueType oldNewValue = newValue;
		newValue = newNewValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE, oldNewValue, newNewValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewValue(ValueType newNewValue) {
		if (newNewValue != newValue) {
			NotificationChain msgs = null;
			if (newValue != null)
				msgs = ((InternalEObject)newValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE, null, msgs);
			if (newNewValue != null)
				msgs = ((InternalEObject)newNewValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE, null, msgs);
			msgs = basicSetNewValue(newNewValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE, newNewValue, newNewValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypeType getDataType() {
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataType(DataTypeType newDataType) {
		DataTypeType oldDataType = dataType;
		dataType = newDataType == null ? DATA_TYPE_EDEFAULT : newDataType;
		boolean oldDataTypeESet = dataTypeESet;
		dataTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY_MODIFICATION_TYPE__DATA_TYPE, oldDataType, dataType, !oldDataTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDataType() {
		DataTypeType oldDataType = dataType;
		boolean oldDataTypeESet = dataTypeESet;
		dataType = DATA_TYPE_EDEFAULT;
		dataTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PROPERTY_MODIFICATION_TYPE__DATA_TYPE, oldDataType, DATA_TYPE_EDEFAULT, oldDataTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDataType() {
		return dataTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMultiple() {
		return multiple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultiple(boolean newMultiple) {
		boolean oldMultiple = multiple;
		multiple = newMultiple;
		boolean oldMultipleESet = multipleESet;
		multipleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY_MODIFICATION_TYPE__MULTIPLE, oldMultiple, multiple, !oldMultipleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMultiple() {
		boolean oldMultiple = multiple;
		boolean oldMultipleESet = multipleESet;
		multiple = MULTIPLE_EDEFAULT;
		multipleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PROPERTY_MODIFICATION_TYPE__MULTIPLE, oldMultiple, MULTIPLE_EDEFAULT, oldMultipleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMultiple() {
		return multipleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROPERTY_MODIFICATION_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE:
				return basicSetOldValue(null, msgs);
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE:
				return basicSetNewValue(null, msgs);
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
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE:
				return getOldValue();
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE:
				return getNewValue();
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__DATA_TYPE:
				return getDataType();
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__MULTIPLE:
				return isMultiple();
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NAME:
				return getName();
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
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE:
				setOldValue((ValueType)newValue);
				return;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE:
				setNewValue((ValueType)newValue);
				return;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__DATA_TYPE:
				setDataType((DataTypeType)newValue);
				return;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__MULTIPLE:
				setMultiple((Boolean)newValue);
				return;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NAME:
				setName((String)newValue);
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
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE:
				setOldValue((ValueType)null);
				return;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE:
				setNewValue((ValueType)null);
				return;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__DATA_TYPE:
				unsetDataType();
				return;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__MULTIPLE:
				unsetMultiple();
				return;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NAME:
				setName(NAME_EDEFAULT);
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
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__OLD_VALUE:
				return oldValue != null;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NEW_VALUE:
				return newValue != null;
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__DATA_TYPE:
				return isSetDataType();
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__MULTIPLE:
				return isSetMultiple();
			case ModelPackage.PROPERTY_MODIFICATION_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		if (dataTypeESet) result.append(dataType); else result.append("<unset>");
		result.append(", multiple: ");
		if (multipleESet) result.append(multiple); else result.append("<unset>");
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PropertyModificationTypeImpl
