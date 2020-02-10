/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.designtimelib.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Design Time Lib Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibEntryImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibEntryImpl#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DesignTimeLibEntryImpl extends EObjectImpl implements DesignTimeLibEntry {
	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected String location = LOCATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DesignTimeLibEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DesigntimelibPackage.Literals.DESIGN_TIME_LIB_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(String newLocation) {
		String oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__ALIAS:
				return getAlias();
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__LOCATION:
				return getLocation();
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
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__ALIAS:
				setAlias((String)newValue);
				return;
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__LOCATION:
				setLocation((String)newValue);
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
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__LOCATION:
				setLocation(LOCATION_EDEFAULT);
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
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case DesigntimelibPackage.DESIGN_TIME_LIB_ENTRY__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
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
		result.append(" (alias: ");
		result.append(alias);
		result.append(", location: ");
		result.append(location);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof DesignTimeLibEntry)){
			return false;
		}
		if ( this == obj){
			return true;
		}
		DesignTimeLibEntry other = (DesignTimeLibEntry)obj;
		String otherAlias = other.getAlias();
		String otherLocation = other.getLocation();
		return location == null ? otherLocation == null : location.equals(otherLocation);
		
	}

} //DesignTimeLibEntryImpl
