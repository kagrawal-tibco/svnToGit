/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IDP Entry Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPEntryTypeImpl#getLoc <em>Loc</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPEntryTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPEntryTypeImpl#getProviderID <em>Provider ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IDPEntryTypeImpl extends EObjectImpl implements IDPEntryType {
	/**
	 * The default value of the '{@link #getLoc() <em>Loc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoc()
	 * @generated
	 * @ordered
	 */
	protected static final String LOC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLoc() <em>Loc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoc()
	 * @generated
	 * @ordered
	 */
	protected String loc = LOC_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getProviderID() <em>Provider ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProviderID()
	 * @generated
	 * @ordered
	 */
	protected static final String PROVIDER_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProviderID() <em>Provider ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProviderID()
	 * @generated
	 * @ordered
	 */
	protected String providerID = PROVIDER_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IDPEntryTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.IDP_ENTRY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLoc() {
		return loc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoc(String newLoc) {
		String oldLoc = loc;
		loc = newLoc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.IDP_ENTRY_TYPE__LOC, oldLoc, loc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.IDP_ENTRY_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProviderID() {
		return providerID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProviderID(String newProviderID) {
		String oldProviderID = providerID;
		providerID = newProviderID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.IDP_ENTRY_TYPE__PROVIDER_ID, oldProviderID, providerID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProtocolPackage.IDP_ENTRY_TYPE__LOC:
				return getLoc();
			case ProtocolPackage.IDP_ENTRY_TYPE__NAME:
				return getName();
			case ProtocolPackage.IDP_ENTRY_TYPE__PROVIDER_ID:
				return getProviderID();
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
			case ProtocolPackage.IDP_ENTRY_TYPE__LOC:
				setLoc((String)newValue);
				return;
			case ProtocolPackage.IDP_ENTRY_TYPE__NAME:
				setName((String)newValue);
				return;
			case ProtocolPackage.IDP_ENTRY_TYPE__PROVIDER_ID:
				setProviderID((String)newValue);
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
			case ProtocolPackage.IDP_ENTRY_TYPE__LOC:
				setLoc(LOC_EDEFAULT);
				return;
			case ProtocolPackage.IDP_ENTRY_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ProtocolPackage.IDP_ENTRY_TYPE__PROVIDER_ID:
				setProviderID(PROVIDER_ID_EDEFAULT);
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
			case ProtocolPackage.IDP_ENTRY_TYPE__LOC:
				return LOC_EDEFAULT == null ? loc != null : !LOC_EDEFAULT.equals(loc);
			case ProtocolPackage.IDP_ENTRY_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ProtocolPackage.IDP_ENTRY_TYPE__PROVIDER_ID:
				return PROVIDER_ID_EDEFAULT == null ? providerID != null : !PROVIDER_ID_EDEFAULT.equals(providerID);
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
		result.append(" (loc: ");
		result.append(loc);
		result.append(", name: ");
		result.append(name);
		result.append(", providerID: ");
		result.append(providerID);
		result.append(')');
		return result.toString();
	}

} //IDPEntryTypeImpl
