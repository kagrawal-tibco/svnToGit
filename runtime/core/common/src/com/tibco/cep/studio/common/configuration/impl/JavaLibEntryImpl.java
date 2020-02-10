/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.JavaLibEntry;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Lib Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.JavaLibEntryImpl#getNativeLibraryLocation <em>Native Library Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaLibEntryImpl extends BuildPathEntryImpl implements JavaLibEntry {
	/**
	 * The cached value of the '{@link #getNativeLibraryLocation() <em>Native Library Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNativeLibraryLocation()
	 * @generated
	 * @ordered
	 */
	protected NativeLibraryPath nativeLibraryLocation;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaLibEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.JAVA_LIB_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NativeLibraryPath getNativeLibraryLocation() {
		return nativeLibraryLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNativeLibraryLocation(NativeLibraryPath newNativeLibraryLocation, NotificationChain msgs) {
		NativeLibraryPath oldNativeLibraryLocation = nativeLibraryLocation;
		nativeLibraryLocation = newNativeLibraryLocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION, oldNativeLibraryLocation, newNativeLibraryLocation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNativeLibraryLocation(NativeLibraryPath newNativeLibraryLocation) {
		if (newNativeLibraryLocation != nativeLibraryLocation) {
			NotificationChain msgs = null;
			if (nativeLibraryLocation != null)
				msgs = ((InternalEObject)nativeLibraryLocation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION, null, msgs);
			if (newNativeLibraryLocation != null)
				msgs = ((InternalEObject)newNativeLibraryLocation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION, null, msgs);
			msgs = basicSetNativeLibraryLocation(newNativeLibraryLocation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION, newNativeLibraryLocation, newNativeLibraryLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION:
				return basicSetNativeLibraryLocation(null, msgs);
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
			case ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION:
				return getNativeLibraryLocation();
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
			case ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION:
				setNativeLibraryLocation((NativeLibraryPath)newValue);
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
			case ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION:
				setNativeLibraryLocation((NativeLibraryPath)null);
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
			case ConfigurationPackage.JAVA_LIB_ENTRY__NATIVE_LIBRARY_LOCATION:
				return nativeLibraryLocation != null;
		}
		return super.eIsSet(featureID);
	}

} //JavaLibEntryImpl
