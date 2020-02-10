/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.states.StateAnnotationLink;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Annotation Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateAnnotationLinkImpl#getToStateEntity <em>To State Entity</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateAnnotationLinkImpl#getFromAnnotation <em>From Annotation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateAnnotationLinkImpl extends StateLinkImpl implements StateAnnotationLink {
	/**
	 * The cached value of the '{@link #getToStateEntity() <em>To State Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToStateEntity()
	 * @generated
	 * @ordered
	 */
	protected StateEntity toStateEntity;

	/**
	 * The default value of the '{@link #getFromAnnotation() <em>From Annotation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromAnnotation()
	 * @generated
	 * @ordered
	 */
	protected static final String FROM_ANNOTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFromAnnotation() <em>From Annotation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromAnnotation()
	 * @generated
	 * @ordered
	 */
	protected String fromAnnotation = FROM_ANNOTATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateAnnotationLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE_ANNOTATION_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateEntity getToStateEntity() {
		if (toStateEntity != null && toStateEntity.eIsProxy()) {
			InternalEObject oldToStateEntity = (InternalEObject)toStateEntity;
			toStateEntity = (StateEntity)eResolveProxy(oldToStateEntity);
			if (toStateEntity != oldToStateEntity) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatesPackage.STATE_ANNOTATION_LINK__TO_STATE_ENTITY, oldToStateEntity, toStateEntity));
			}
		}
		return toStateEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateEntity basicGetToStateEntity() {
		return toStateEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToStateEntity(StateEntity newToStateEntity) {
		StateEntity oldToStateEntity = toStateEntity;
		toStateEntity = newToStateEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_ANNOTATION_LINK__TO_STATE_ENTITY, oldToStateEntity, toStateEntity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFromAnnotation() {
		return fromAnnotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFromAnnotation(String newFromAnnotation) {
		String oldFromAnnotation = fromAnnotation;
		fromAnnotation = newFromAnnotation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_ANNOTATION_LINK__FROM_ANNOTATION, oldFromAnnotation, fromAnnotation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StatesPackage.STATE_ANNOTATION_LINK__TO_STATE_ENTITY:
				if (resolve) return getToStateEntity();
				return basicGetToStateEntity();
			case StatesPackage.STATE_ANNOTATION_LINK__FROM_ANNOTATION:
				return getFromAnnotation();
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
			case StatesPackage.STATE_ANNOTATION_LINK__TO_STATE_ENTITY:
				setToStateEntity((StateEntity)newValue);
				return;
			case StatesPackage.STATE_ANNOTATION_LINK__FROM_ANNOTATION:
				setFromAnnotation((String)newValue);
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
			case StatesPackage.STATE_ANNOTATION_LINK__TO_STATE_ENTITY:
				setToStateEntity((StateEntity)null);
				return;
			case StatesPackage.STATE_ANNOTATION_LINK__FROM_ANNOTATION:
				setFromAnnotation(FROM_ANNOTATION_EDEFAULT);
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
			case StatesPackage.STATE_ANNOTATION_LINK__TO_STATE_ENTITY:
				return toStateEntity != null;
			case StatesPackage.STATE_ANNOTATION_LINK__FROM_ANNOTATION:
				return FROM_ANNOTATION_EDEFAULT == null ? fromAnnotation != null : !FROM_ANNOTATION_EDEFAULT.equals(fromAnnotation);
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
		result.append(" (fromAnnotation: ");
		result.append(fromAnnotation);
		result.append(')');
		return result.toString();
	}

} //StateAnnotationLinkImpl
