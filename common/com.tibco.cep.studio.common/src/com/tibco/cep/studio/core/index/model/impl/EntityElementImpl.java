/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entity Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.EntityElementImpl#getEntity <em>Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EntityElementImpl extends TypeElementImpl implements EntityElement {
	/**
	 * The cached value of the '{@link #getEntity() <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntity()
	 * @generated
	 * @ordered
	 */
	protected Entity entity;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.ENTITY_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Entity getEntity() {
		if (entity != null && entity.eIsProxy()) {
			InternalEObject oldEntity = (InternalEObject)entity;
			synchronized (this) {
				// this needs to be sync'ed, as multiple threads could try to
				// load the same resource, which causes errors
				entity = (Entity)eResolveProxy(oldEntity);
			}
			if (entity != oldEntity) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.ENTITY_ELEMENT__ENTITY, oldEntity, entity));
			}
		}
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity basicGetEntity() {
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntity(Entity newEntity) {
		Entity oldEntity = entity;
		entity = newEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ENTITY_ELEMENT__ENTITY, oldEntity, entity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IndexPackage.ENTITY_ELEMENT__ENTITY:
				if (resolve) return getEntity();
				return basicGetEntity();
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
			case IndexPackage.ENTITY_ELEMENT__ENTITY:
				setEntity((Entity)newValue);
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
			case IndexPackage.ENTITY_ELEMENT__ENTITY:
				setEntity((Entity)null);
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
			case IndexPackage.ENTITY_ELEMENT__ENTITY:
				return entity != null;
		}
		return super.eIsSet(featureID);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl#accept(com.tibco.cep.studio.core.index.model.IStructuredElementVisitor)
	 * @generated NOT
	 */
	public void accept(IStructuredElementVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
        	// do nothing, no children to visit
        }
        visitor.postVisit(this);
	}

} //EntityElementImpl
