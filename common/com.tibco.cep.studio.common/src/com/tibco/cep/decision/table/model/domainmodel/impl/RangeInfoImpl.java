/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.RangeInfoImpl#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.RangeInfoImpl#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.RangeInfoImpl#isLowerBoundIncluded <em>Lower Bound Included</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.RangeInfoImpl#isUpperBoundIncluded <em>Upper Bound Included</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangeInfoImpl extends EntryValueImpl implements RangeInfo {
	/**
	 * The default value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBound()
	 * @generated
	 * @ordered
	 */
	protected static final String LOWER_BOUND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBound()
	 * @generated
	 * @ordered
	 */
	protected String lowerBound = LOWER_BOUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected static final String UPPER_BOUND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected String upperBound = UPPER_BOUND_EDEFAULT;

	/**
	 * The default value of the '{@link #isLowerBoundIncluded() <em>Lower Bound Included</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLowerBoundIncluded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOWER_BOUND_INCLUDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLowerBoundIncluded() <em>Lower Bound Included</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLowerBoundIncluded()
	 * @generated
	 * @ordered
	 */
	protected boolean lowerBoundIncluded = LOWER_BOUND_INCLUDED_EDEFAULT;

	/**
	 * The default value of the '{@link #isUpperBoundIncluded() <em>Upper Bound Included</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUpperBoundIncluded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UPPER_BOUND_INCLUDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUpperBoundIncluded() <em>Upper Bound Included</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUpperBoundIncluded()
	 * @generated
	 * @ordered
	 */
	protected boolean upperBoundIncluded = UPPER_BOUND_INCLUDED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RangeInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainModelPackage.Literals.RANGE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLowerBound() {
		return lowerBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerBound(String newLowerBound) {
		String oldLowerBound = lowerBound;
		lowerBound = newLowerBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.RANGE_INFO__LOWER_BOUND, oldLowerBound, lowerBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUpperBound() {
		return upperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperBound(String newUpperBound) {
		String oldUpperBound = upperBound;
		upperBound = newUpperBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.RANGE_INFO__UPPER_BOUND, oldUpperBound, upperBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLowerBoundIncluded() {
		return lowerBoundIncluded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerBoundIncluded(boolean newLowerBoundIncluded) {
		boolean oldLowerBoundIncluded = lowerBoundIncluded;
		lowerBoundIncluded = newLowerBoundIncluded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.RANGE_INFO__LOWER_BOUND_INCLUDED, oldLowerBoundIncluded, lowerBoundIncluded));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUpperBoundIncluded() {
		return upperBoundIncluded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperBoundIncluded(boolean newUpperBoundIncluded) {
		boolean oldUpperBoundIncluded = upperBoundIncluded;
		upperBoundIncluded = newUpperBoundIncluded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.RANGE_INFO__UPPER_BOUND_INCLUDED, oldUpperBoundIncluded, upperBoundIncluded));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomainModelPackage.RANGE_INFO__LOWER_BOUND:
				return getLowerBound();
			case DomainModelPackage.RANGE_INFO__UPPER_BOUND:
				return getUpperBound();
			case DomainModelPackage.RANGE_INFO__LOWER_BOUND_INCLUDED:
				return isLowerBoundIncluded() ? Boolean.TRUE : Boolean.FALSE;
			case DomainModelPackage.RANGE_INFO__UPPER_BOUND_INCLUDED:
				return isUpperBoundIncluded() ? Boolean.TRUE : Boolean.FALSE;
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
			case DomainModelPackage.RANGE_INFO__LOWER_BOUND:
				setLowerBound((String)newValue);
				return;
			case DomainModelPackage.RANGE_INFO__UPPER_BOUND:
				setUpperBound((String)newValue);
				return;
			case DomainModelPackage.RANGE_INFO__LOWER_BOUND_INCLUDED:
				setLowerBoundIncluded(((Boolean)newValue).booleanValue());
				return;
			case DomainModelPackage.RANGE_INFO__UPPER_BOUND_INCLUDED:
				setUpperBoundIncluded(((Boolean)newValue).booleanValue());
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
			case DomainModelPackage.RANGE_INFO__LOWER_BOUND:
				setLowerBound(LOWER_BOUND_EDEFAULT);
				return;
			case DomainModelPackage.RANGE_INFO__UPPER_BOUND:
				setUpperBound(UPPER_BOUND_EDEFAULT);
				return;
			case DomainModelPackage.RANGE_INFO__LOWER_BOUND_INCLUDED:
				setLowerBoundIncluded(LOWER_BOUND_INCLUDED_EDEFAULT);
				return;
			case DomainModelPackage.RANGE_INFO__UPPER_BOUND_INCLUDED:
				setUpperBoundIncluded(UPPER_BOUND_INCLUDED_EDEFAULT);
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
			case DomainModelPackage.RANGE_INFO__LOWER_BOUND:
				return LOWER_BOUND_EDEFAULT == null ? lowerBound != null : !LOWER_BOUND_EDEFAULT.equals(lowerBound);
			case DomainModelPackage.RANGE_INFO__UPPER_BOUND:
				return UPPER_BOUND_EDEFAULT == null ? upperBound != null : !UPPER_BOUND_EDEFAULT.equals(upperBound);
			case DomainModelPackage.RANGE_INFO__LOWER_BOUND_INCLUDED:
				return lowerBoundIncluded != LOWER_BOUND_INCLUDED_EDEFAULT;
			case DomainModelPackage.RANGE_INFO__UPPER_BOUND_INCLUDED:
				return upperBoundIncluded != UPPER_BOUND_INCLUDED_EDEFAULT;
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
		result.append(" (lowerBound: ");
		result.append(lowerBound);
		result.append(", upperBound: ");
		result.append(upperBound);
		result.append(", lowerBoundIncluded: ");
		result.append(lowerBoundIncluded);
		result.append(", upperBoundIncluded: ");
		result.append(upperBoundIncluded);
		result.append(')');
		return result.toString();
	}

} //RangeInfoImpl
