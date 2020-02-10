/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage;
import com.tibco.cep.decision.table.model.domainmodel.EntryValue;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;
import com.tibco.cep.decision.table.model.domainmodel.SingleValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainEntryImpl#getEntryName <em>Entry Name</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainEntryImpl#getEntryValue <em>Entry Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DomainEntryImpl extends EObjectImpl implements DomainEntry {
	/**
	 * The default value of the '{@link #getEntryName() <em>Entry Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryName()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTRY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntryName() <em>Entry Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryName()
	 * @generated
	 * @ordered
	 */
	protected String entryName = ENTRY_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntryValue() <em>Entry Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryValue()
	 * @generated
	 * @ordered
	 */
	protected EntryValue entryValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainModelPackage.Literals.DOMAIN_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntryName() {
		return entryName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryName(String newEntryName) {
		String oldEntryName = entryName;
		entryName = newEntryName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN_ENTRY__ENTRY_NAME, oldEntryName, entryName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntryValue getEntryValue() {
		return entryValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntryValue(EntryValue newEntryValue, NotificationChain msgs) {
		EntryValue oldEntryValue = entryValue;
		entryValue = newEntryValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE, oldEntryValue, newEntryValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryValue(EntryValue newEntryValue) {
		if (newEntryValue != entryValue) {
			NotificationChain msgs = null;
			if (entryValue != null)
				msgs = ((InternalEObject)entryValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE, null, msgs);
			if (newEntryValue != null)
				msgs = ((InternalEObject)newEntryValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE, null, msgs);
			msgs = basicSetEntryValue(newEntryValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE, newEntryValue, newEntryValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getActualValue() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE:
				return basicSetEntryValue(null, msgs);
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
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_NAME:
				return getEntryName();
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE:
				return getEntryValue();
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
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_NAME:
				setEntryName((String)newValue);
				return;
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE:
				setEntryValue((EntryValue)newValue);
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
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_NAME:
				setEntryName(ENTRY_NAME_EDEFAULT);
				return;
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE:
				setEntryValue((EntryValue)null);
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
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_NAME:
				return ENTRY_NAME_EDEFAULT == null ? entryName != null : !ENTRY_NAME_EDEFAULT.equals(entryName);
			case DomainModelPackage.DOMAIN_ENTRY__ENTRY_VALUE:
				return entryValue != null;
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
		result.append(" (entryName: ");
		result.append(entryName);
		result.append(')');
		return result.toString();
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean equals(Object obj) {		
		if (this == obj) return true;
		if (! (obj instanceof DomainEntry)) return false;
		EntryValue entryValue1 = this.getEntryValue();
		DomainEntry otherDMEntry = (DomainEntry)obj;
		EntryValue entryValue2 = otherDMEntry.getEntryValue();
		/*
		 * too much checking
		if (entryValue1 == null && entryValue2 == null) return true;
		if (entryValue1 == null && entryValue2 != null) return false;
		if (entryValue1 != null && entryValue2 == null) return false;
		*/
		if (entryValue1 == entryValue2) return true;
		if (entryValue1.getClass().getName().equals(entryValue2.getClass().getName())){
			if (entryValue1 instanceof SingleValue){
				String val1 = entryValue1.getValue();
				String val2 = entryValue2.getValue();
				/*
				 * too much checking
				if (val1 == null && val2 == null) return true;
				if (val1 == null && val2 != null) return false;
				if (val1 != null && val2 == null) return false;
				*/
				return val1.equals(val2);
				
			} else if (entryValue1 instanceof RangeInfo){
				RangeInfo rangeInfo1 = (RangeInfo)entryValue1;
				RangeInfo rangeInfo2 = (RangeInfo)entryValue2;
				/*
				 * too much checking
				if (rangeInfo1 == null && rangeInfo2 == null) return true;
				if (rangeInfo1 == null && rangeInfo2 != null) return false;
				if (rangeInfo1 != null && rangeInfo2 == null) return false;
				*/
				if (rangeInfo1 == rangeInfo2) return true;
				String lb1 = rangeInfo1.getLowerBound();
				boolean isLBIncluded1 = rangeInfo1.isLowerBoundIncluded();
				String ub1 = rangeInfo1.getUpperBound();
				boolean isUBIncluded1 = rangeInfo1.isUpperBoundIncluded();
			
				String lb2 = rangeInfo2.getLowerBound();
				boolean isLBIncluded2 = rangeInfo2.isLowerBoundIncluded();
				String ub2 = rangeInfo2.getUpperBound();
				boolean isUBIncluded2 = rangeInfo2.isUpperBoundIncluded();
				
				if ((lb1.equals(lb2)) && (isLBIncluded1 == isLBIncluded2) && 
									(ub1.equals(ub2)) && (isUBIncluded1 == isUBIncluded2)){
					return true;
				} else {
					return false;
				}
				
			}
			
			
		} else {
			return false;
		}
		return true;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public int hashCode() {
		int hashCode = 7 ;
		EntryValue entryValue = this.getEntryValue();		
		if (entryValue instanceof SingleValue){
			String val = entryValue.getValue();
			int valHashCode = val == null ? 0 : val.hashCode();		
			return 31*hashCode + valHashCode;
				
		} else if (entryValue instanceof RangeInfo){
			RangeInfo rangeInfo = (RangeInfo)entryValue;	
			
			String lb = rangeInfo.getLowerBound();
			int lbHashCode = lb == null ? 0 : lb.hashCode();
			boolean isLBIncluded = rangeInfo.isLowerBoundIncluded();
			int isLBIncludedHashCode = isLBIncluded ? 1 : 0;
			String ub = rangeInfo.getUpperBound();
			int ubHashCode = ub == null ? 0 : ub.hashCode();
			boolean isUBIncluded = rangeInfo.isUpperBoundIncluded();
			int isUBIncludedHashCode = isUBIncluded ? 1 : 0;
			return 31*hashCode + lbHashCode + isLBIncludedHashCode + ubHashCode + isUBIncludedHashCode;
		
				
		}
			
			

		return super.hashCode();
	}
	
	

} //DomainEntryImpl
