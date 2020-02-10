/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.model.topology.Be;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Be</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.BeImpl#getTra <em>Tra</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.BeImpl#getHome <em>Home</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.BeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BeImpl extends EObjectImpl implements Be {
	/**
	 * The default value of the '{@link #getTra() <em>Tra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTra()
	 * @generated
	 * @ordered
	 */
	protected static final String TRA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTra() <em>Tra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTra()
	 * @generated
	 * @ordered
	 */
	protected String tra = TRA_EDEFAULT;

	/**
	 * The default value of the '{@link #getHome() <em>Home</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHome()
	 * @generated
	 * @ordered
	 */
	protected static final String HOME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHome() <em>Home</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHome()
	 * @generated
	 * @ordered
	 */
	protected String home = HOME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = "4.0.0";

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * This is true if the Version attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean versionESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.BE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTra() {
		return tra;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTra(String newTra) {
		String oldTra = tra;
		tra = newTra;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.BE__TRA, oldTra, tra));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHome() {
		return home;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHome(String newHome) {
		String oldHome = home;
		home = newHome;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.BE__HOME, oldHome, home));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		boolean oldVersionESet = versionESet;
		versionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.BE__VERSION, oldVersion, version, !oldVersionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetVersion() {
		String oldVersion = version;
		boolean oldVersionESet = versionESet;
		version = VERSION_EDEFAULT;
		versionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TopologyPackage.BE__VERSION, oldVersion, VERSION_EDEFAULT, oldVersionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetVersion() {
		return versionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TopologyPackage.BE__TRA:
				return getTra();
			case TopologyPackage.BE__HOME:
				return getHome();
			case TopologyPackage.BE__VERSION:
				return getVersion();
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
			case TopologyPackage.BE__TRA:
				setTra((String)newValue);
				return;
			case TopologyPackage.BE__HOME:
				setHome((String)newValue);
				return;
			case TopologyPackage.BE__VERSION:
				setVersion((String)newValue);
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
			case TopologyPackage.BE__TRA:
				setTra(TRA_EDEFAULT);
				return;
			case TopologyPackage.BE__HOME:
				setHome(HOME_EDEFAULT);
				return;
			case TopologyPackage.BE__VERSION:
				unsetVersion();
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
			case TopologyPackage.BE__TRA:
				return TRA_EDEFAULT == null ? tra != null : !TRA_EDEFAULT.equals(tra);
			case TopologyPackage.BE__HOME:
				return HOME_EDEFAULT == null ? home != null : !HOME_EDEFAULT.equals(home);
			case TopologyPackage.BE__VERSION:
				return isSetVersion();
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
		result.append(" (tra: ");
		result.append(tra);
		result.append(", home: ");
		result.append(home);
		result.append(", version: ");
		if (versionESet) result.append(version); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //BeImpl
