/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

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

import com.tibco.cep.studio.tester.emf.model.EntityType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.PropertyModificationType;
import com.tibco.cep.studio.tester.emf.model.PropertyType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entity Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl#getModifiedProperty <em>Modified Property</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl#getExtId <em>Ext Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.EntityTypeImpl#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EntityTypeImpl extends EObjectImpl implements EntityType {
	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyType> property;

	/**
	 * The cached value of the '{@link #getModifiedProperty() <em>Modified Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifiedProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyModificationType> modifiedProperty;

	/**
	 * The default value of the '{@link #getExtId() <em>Ext Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtId()
	 * @generated
	 * @ordered
	 */
	protected static final String EXT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExtId() <em>Ext Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtId()
	 * @generated
	 * @ordered
	 */
	protected String extId = EXT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final long ID_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected long id = ID_EDEFAULT;

	/**
	 * This is true if the Id attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean idESet;

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
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected String namespace = NAMESPACE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ENTITY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyType> getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList<PropertyType>(PropertyType.class, this, ModelPackage.ENTITY_TYPE__PROPERTY);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyModificationType> getModifiedProperty() {
		if (modifiedProperty == null) {
			modifiedProperty = new EObjectContainmentEList<PropertyModificationType>(PropertyModificationType.class, this, ModelPackage.ENTITY_TYPE__MODIFIED_PROPERTY);
		}
		return modifiedProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtId() {
		return extId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtId(String newExtId) {
		String oldExtId = extId;
		extId = newExtId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY_TYPE__EXT_ID, oldExtId, extId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(long newId) {
		long oldId = id;
		id = newId;
		boolean oldIdESet = idESet;
		idESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY_TYPE__ID, oldId, id, !oldIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetId() {
		long oldId = id;
		boolean oldIdESet = idESet;
		id = ID_EDEFAULT;
		idESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.ENTITY_TYPE__ID, oldId, ID_EDEFAULT, oldIdESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetId() {
		return idESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(String newNamespace) {
		String oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY_TYPE__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ENTITY_TYPE__PROPERTY:
				return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
			case ModelPackage.ENTITY_TYPE__MODIFIED_PROPERTY:
				return ((InternalEList<?>)getModifiedProperty()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.ENTITY_TYPE__PROPERTY:
				return getProperty();
			case ModelPackage.ENTITY_TYPE__MODIFIED_PROPERTY:
				return getModifiedProperty();
			case ModelPackage.ENTITY_TYPE__EXT_ID:
				return getExtId();
			case ModelPackage.ENTITY_TYPE__ID:
				return getId();
			case ModelPackage.ENTITY_TYPE__NAME:
				return getName();
			case ModelPackage.ENTITY_TYPE__NAMESPACE:
				return getNamespace();
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
			case ModelPackage.ENTITY_TYPE__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends PropertyType>)newValue);
				return;
			case ModelPackage.ENTITY_TYPE__MODIFIED_PROPERTY:
				getModifiedProperty().clear();
				getModifiedProperty().addAll((Collection<? extends PropertyModificationType>)newValue);
				return;
			case ModelPackage.ENTITY_TYPE__EXT_ID:
				setExtId((String)newValue);
				return;
			case ModelPackage.ENTITY_TYPE__ID:
				setId((Long)newValue);
				return;
			case ModelPackage.ENTITY_TYPE__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.ENTITY_TYPE__NAMESPACE:
				setNamespace((String)newValue);
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
			case ModelPackage.ENTITY_TYPE__PROPERTY:
				getProperty().clear();
				return;
			case ModelPackage.ENTITY_TYPE__MODIFIED_PROPERTY:
				getModifiedProperty().clear();
				return;
			case ModelPackage.ENTITY_TYPE__EXT_ID:
				setExtId(EXT_ID_EDEFAULT);
				return;
			case ModelPackage.ENTITY_TYPE__ID:
				unsetId();
				return;
			case ModelPackage.ENTITY_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.ENTITY_TYPE__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
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
			case ModelPackage.ENTITY_TYPE__PROPERTY:
				return property != null && !property.isEmpty();
			case ModelPackage.ENTITY_TYPE__MODIFIED_PROPERTY:
				return modifiedProperty != null && !modifiedProperty.isEmpty();
			case ModelPackage.ENTITY_TYPE__EXT_ID:
				return EXT_ID_EDEFAULT == null ? extId != null : !EXT_ID_EDEFAULT.equals(extId);
			case ModelPackage.ENTITY_TYPE__ID:
				return isSetId();
			case ModelPackage.ENTITY_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.ENTITY_TYPE__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
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
		result.append(" (extId: ");
		result.append(extId);
		result.append(", id: ");
		if (idESet) result.append(id); else result.append("<unset>");
		result.append(", name: ");
		result.append(name);
		result.append(", namespace: ");
		result.append(namespace);
		result.append(')');
		return result.toString();
	}

} //EntityTypeImpl
