/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.tester.emf.model.CasualObjectsType;
import com.tibco.cep.studio.tester.emf.model.ConceptType;
import com.tibco.cep.studio.tester.emf.model.EventType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Casual Objects Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.CasualObjectsTypeImpl#getConcept <em>Concept</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.CasualObjectsTypeImpl#getEvent <em>Event</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CasualObjectsTypeImpl extends EObjectImpl implements CasualObjectsType {
	/**
	 * The cached value of the '{@link #getConcept() <em>Concept</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConcept()
	 * @generated
	 * @ordered
	 */
	protected EList<ConceptType> concept;

	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected EList<EventType> event;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CasualObjectsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.CASUAL_OBJECTS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConceptType> getConcept() {
		if (concept == null) {
			concept = new EObjectContainmentEList<ConceptType>(ConceptType.class, this, ModelPackage.CASUAL_OBJECTS_TYPE__CONCEPT);
		}
		return concept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EventType> getEvent() {
		if (event == null) {
			event = new EObjectContainmentEList<EventType>(EventType.class, this, ModelPackage.CASUAL_OBJECTS_TYPE__EVENT);
		}
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.CASUAL_OBJECTS_TYPE__CONCEPT:
				return ((InternalEList<?>)getConcept()).basicRemove(otherEnd, msgs);
			case ModelPackage.CASUAL_OBJECTS_TYPE__EVENT:
				return ((InternalEList<?>)getEvent()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.CASUAL_OBJECTS_TYPE__CONCEPT:
				return getConcept();
			case ModelPackage.CASUAL_OBJECTS_TYPE__EVENT:
				return getEvent();
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
			case ModelPackage.CASUAL_OBJECTS_TYPE__CONCEPT:
				getConcept().clear();
				getConcept().addAll((Collection<? extends ConceptType>)newValue);
				return;
			case ModelPackage.CASUAL_OBJECTS_TYPE__EVENT:
				getEvent().clear();
				getEvent().addAll((Collection<? extends EventType>)newValue);
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
			case ModelPackage.CASUAL_OBJECTS_TYPE__CONCEPT:
				getConcept().clear();
				return;
			case ModelPackage.CASUAL_OBJECTS_TYPE__EVENT:
				getEvent().clear();
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
			case ModelPackage.CASUAL_OBJECTS_TYPE__CONCEPT:
				return concept != null && !concept.isEmpty();
			case ModelPackage.CASUAL_OBJECTS_TYPE__EVENT:
				return event != null && !event.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CasualObjectsTypeImpl
