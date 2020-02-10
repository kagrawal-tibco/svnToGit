/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Match</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.ElementMatchImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.ElementMatchImpl#getMatchedElement <em>Matched Element</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.ElementMatchImpl#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ElementMatchImpl extends EObjectImpl implements ElementMatch {
	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected EObject feature;

	/**
	 * The cached value of the '{@link #getMatchedElement() <em>Matched Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchedElement()
	 * @generated
	 * @ordered
	 */
	protected EObject matchedElement;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementMatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SearchPackage.Literals.ELEMENT_MATCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getFeature() {
		if (feature != null && feature.eIsProxy()) {
			InternalEObject oldFeature = (InternalEObject)feature;
			feature = eResolveProxy(oldFeature);
			if (feature != oldFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SearchPackage.ELEMENT_MATCH__FEATURE, oldFeature, feature));
			}
		}
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(EObject newFeature) {
		EObject oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.ELEMENT_MATCH__FEATURE, oldFeature, feature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getMatchedElement() {
		if (matchedElement != null && matchedElement.eIsProxy()) {
			InternalEObject oldMatchedElement = (InternalEObject)matchedElement;
			matchedElement = eResolveProxy(oldMatchedElement);
			if (matchedElement != oldMatchedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SearchPackage.ELEMENT_MATCH__MATCHED_ELEMENT, oldMatchedElement, matchedElement));
			}
		}
		return matchedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetMatchedElement() {
		return matchedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMatchedElement(EObject newMatchedElement) {
		EObject oldMatchedElement = matchedElement;
		matchedElement = newMatchedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.ELEMENT_MATCH__MATCHED_ELEMENT, oldMatchedElement, matchedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.ELEMENT_MATCH__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SearchPackage.ELEMENT_MATCH__FEATURE:
				if (resolve) return getFeature();
				return basicGetFeature();
			case SearchPackage.ELEMENT_MATCH__MATCHED_ELEMENT:
				if (resolve) return getMatchedElement();
				return basicGetMatchedElement();
			case SearchPackage.ELEMENT_MATCH__LABEL:
				return getLabel();
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
			case SearchPackage.ELEMENT_MATCH__FEATURE:
				setFeature((EObject)newValue);
				return;
			case SearchPackage.ELEMENT_MATCH__MATCHED_ELEMENT:
				setMatchedElement((EObject)newValue);
				return;
			case SearchPackage.ELEMENT_MATCH__LABEL:
				setLabel((String)newValue);
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
			case SearchPackage.ELEMENT_MATCH__FEATURE:
				setFeature((EObject)null);
				return;
			case SearchPackage.ELEMENT_MATCH__MATCHED_ELEMENT:
				setMatchedElement((EObject)null);
				return;
			case SearchPackage.ELEMENT_MATCH__LABEL:
				setLabel(LABEL_EDEFAULT);
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
			case SearchPackage.ELEMENT_MATCH__FEATURE:
				return feature != null;
			case SearchPackage.ELEMENT_MATCH__MATCHED_ELEMENT:
				return matchedElement != null;
			case SearchPackage.ELEMENT_MATCH__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
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
		result.append(" (label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ElementMatch)) {
			return false;
		}
		ElementMatch match = (ElementMatch) obj;
		Object feature1 = match.getFeature();
		Object feature2 = this.getFeature();
		if (feature1 instanceof EntityElement) {
			feature1 = ((EntityElement) feature1).getEntity();
		}
		if (feature2 instanceof EntityElement) {
			feature2 = ((EntityElement) feature2).getEntity();
		}

		Object matchedEl1 = match.getMatchedElement();
		if (matchedEl1 instanceof EntityElement) {
			matchedEl1 = ((EntityElement) matchedEl1).getEntity();
		}
		Object matchedEl2 = this.getMatchedElement();
		if (matchedEl2 instanceof EntityElement) {
			matchedEl2 = ((EntityElement) matchedEl2).getEntity();
		}
		return (feature1 == feature2
				&& matchedEl1 == matchedEl2);
	}

} //ElementMatchImpl
