/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.InstanceElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instance Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.InstanceElementImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.InstanceElementImpl#getEntityPath <em>Entity Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstanceElementImpl<T extends BaseInstance> extends TypeElementImpl implements InstanceElement<T> {
	/**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<T> instances;
	/**
	 * The default value of the '{@link #getEntityPath() <em>Entity Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityPath()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTITY_PATH_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getEntityPath() <em>Entity Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityPath()
	 * @generated
	 * @ordered
	 */
	protected String entityPath = ENTITY_PATH_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstanceElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.INSTANCE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<T> getInstances() {
		if (instances == null) {
			instances = new EObjectResolvingEList<T>(BaseInstance.class, this, IndexPackage.INSTANCE_ELEMENT__INSTANCES);
		}
		return instances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntityPath() {
		return entityPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntityPath(String newEntityPath) {
		String oldEntityPath = entityPath;
		entityPath = newEntityPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.INSTANCE_ELEMENT__ENTITY_PATH, oldEntityPath, entityPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IndexPackage.INSTANCE_ELEMENT__INSTANCES:
				return getInstances();
			case IndexPackage.INSTANCE_ELEMENT__ENTITY_PATH:
				return getEntityPath();
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
			case IndexPackage.INSTANCE_ELEMENT__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends T>)newValue);
				return;
			case IndexPackage.INSTANCE_ELEMENT__ENTITY_PATH:
				setEntityPath((String)newValue);
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
			case IndexPackage.INSTANCE_ELEMENT__INSTANCES:
				getInstances().clear();
				return;
			case IndexPackage.INSTANCE_ELEMENT__ENTITY_PATH:
				setEntityPath(ENTITY_PATH_EDEFAULT);
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
			case IndexPackage.INSTANCE_ELEMENT__INSTANCES:
				return instances != null && !instances.isEmpty();
			case IndexPackage.INSTANCE_ELEMENT__ENTITY_PATH:
				return ENTITY_PATH_EDEFAULT == null ? entityPath != null : !ENTITY_PATH_EDEFAULT.equals(entityPath);
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
		result.append(" (entityPath: ");
		result.append(entityPath);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#accept(com.tibco.cep.studio.core.index.IStructuredElementVisitor)
	 */
	@Override
	public void accept(IStructuredElementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InstanceElement)) {
			return false;
		}
		InstanceElement<T> other = (InstanceElement<T>)obj;
		if (!getEntityPath().equals(other.getEntityPath())) {
			return false;
		}
//		List<T> otherWrapped = other.getInstances(); // compare actual instances, too?
		
		return true;
	}
	
	
	
	

} //InstanceElementImpl
