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

import com.tibco.cep.studio.tester.emf.model.ChangeTypeType;
import com.tibco.cep.studio.tester.emf.model.ConceptType;
import com.tibco.cep.studio.tester.emf.model.EventType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.ReteObjectType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rete Object Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.ReteObjectTypeImpl#getChangeType <em>Change Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.ReteObjectTypeImpl#getConcept <em>Concept</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.ReteObjectTypeImpl#getEvent <em>Event</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReteObjectTypeImpl extends EObjectImpl implements ReteObjectType {
	/**
	 * The default value of the '{@link #getChangeType() <em>Change Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeType()
	 * @generated
	 * @ordered
	 */
	protected static final ChangeTypeType CHANGE_TYPE_EDEFAULT = ChangeTypeType.ASSERT;

	/**
	 * The cached value of the '{@link #getChangeType() <em>Change Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeType()
	 * @generated
	 * @ordered
	 */
	protected ChangeTypeType changeType = CHANGE_TYPE_EDEFAULT;

	/**
	 * This is true if the Change Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean changeTypeESet;

	/**
	 * The cached value of the '{@link #getConcept() <em>Concept</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConcept()
	 * @generated
	 * @ordered
	 */
	protected ConceptType concept;

	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected EventType event;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReteObjectTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.RETE_OBJECT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeTypeType getChangeType() {
		return changeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChangeType(ChangeTypeType newChangeType) {
		ChangeTypeType oldChangeType = changeType;
		changeType = newChangeType == null ? CHANGE_TYPE_EDEFAULT : newChangeType;
		boolean oldChangeTypeESet = changeTypeESet;
		changeTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.RETE_OBJECT_TYPE__CHANGE_TYPE, oldChangeType, changeType, !oldChangeTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetChangeType() {
		ChangeTypeType oldChangeType = changeType;
		boolean oldChangeTypeESet = changeTypeESet;
		changeType = CHANGE_TYPE_EDEFAULT;
		changeTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.RETE_OBJECT_TYPE__CHANGE_TYPE, oldChangeType, CHANGE_TYPE_EDEFAULT, oldChangeTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetChangeType() {
		return changeTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConceptType getConcept() {
		return concept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConcept(ConceptType newConcept, NotificationChain msgs) {
		ConceptType oldConcept = concept;
		concept = newConcept;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.RETE_OBJECT_TYPE__CONCEPT, oldConcept, newConcept);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConcept(ConceptType newConcept) {
		if (newConcept != concept) {
			NotificationChain msgs = null;
			if (concept != null)
				msgs = ((InternalEObject)concept).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.RETE_OBJECT_TYPE__CONCEPT, null, msgs);
			if (newConcept != null)
				msgs = ((InternalEObject)newConcept).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.RETE_OBJECT_TYPE__CONCEPT, null, msgs);
			msgs = basicSetConcept(newConcept, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.RETE_OBJECT_TYPE__CONCEPT, newConcept, newConcept));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType getEvent() {
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvent(EventType newEvent, NotificationChain msgs) {
		EventType oldEvent = event;
		event = newEvent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.RETE_OBJECT_TYPE__EVENT, oldEvent, newEvent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvent(EventType newEvent) {
		if (newEvent != event) {
			NotificationChain msgs = null;
			if (event != null)
				msgs = ((InternalEObject)event).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.RETE_OBJECT_TYPE__EVENT, null, msgs);
			if (newEvent != null)
				msgs = ((InternalEObject)newEvent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.RETE_OBJECT_TYPE__EVENT, null, msgs);
			msgs = basicSetEvent(newEvent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.RETE_OBJECT_TYPE__EVENT, newEvent, newEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.RETE_OBJECT_TYPE__CONCEPT:
				return basicSetConcept(null, msgs);
			case ModelPackage.RETE_OBJECT_TYPE__EVENT:
				return basicSetEvent(null, msgs);
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
			case ModelPackage.RETE_OBJECT_TYPE__CHANGE_TYPE:
				return getChangeType();
			case ModelPackage.RETE_OBJECT_TYPE__CONCEPT:
				return getConcept();
			case ModelPackage.RETE_OBJECT_TYPE__EVENT:
				return getEvent();
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
			case ModelPackage.RETE_OBJECT_TYPE__CHANGE_TYPE:
				setChangeType((ChangeTypeType)newValue);
				return;
			case ModelPackage.RETE_OBJECT_TYPE__CONCEPT:
				setConcept((ConceptType)newValue);
				return;
			case ModelPackage.RETE_OBJECT_TYPE__EVENT:
				setEvent((EventType)newValue);
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
			case ModelPackage.RETE_OBJECT_TYPE__CHANGE_TYPE:
				unsetChangeType();
				return;
			case ModelPackage.RETE_OBJECT_TYPE__CONCEPT:
				setConcept((ConceptType)null);
				return;
			case ModelPackage.RETE_OBJECT_TYPE__EVENT:
				setEvent((EventType)null);
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
			case ModelPackage.RETE_OBJECT_TYPE__CHANGE_TYPE:
				return isSetChangeType();
			case ModelPackage.RETE_OBJECT_TYPE__CONCEPT:
				return concept != null;
			case ModelPackage.RETE_OBJECT_TYPE__EVENT:
				return event != null;
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
		result.append(" (changeType: ");
		if (changeTypeESet) result.append(changeType); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ReteObjectTypeImpl
