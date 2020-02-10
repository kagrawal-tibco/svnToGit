/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder;
import com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference;
import com.tibco.cep.designtime.core.model.beviewsconfig.View;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Role Preference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RolePreferenceImpl#getRole <em>Role</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RolePreferenceImpl#getGallery <em>Gallery</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RolePreferenceImpl#getView <em>View</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RolePreferenceImpl extends BEViewsElementImpl implements RolePreference {
	/**
	 * The default value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected static final String ROLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected String role = ROLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGallery() <em>Gallery</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGallery()
	 * @generated
	 * @ordered
	 */
	protected ComponentGalleryFolder gallery;

	/**
	 * The cached value of the '{@link #getView() <em>View</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getView()
	 * @generated
	 * @ordered
	 */
	protected View view;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RolePreferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getRolePreference();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRole() {
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRole(String newRole) {
		String oldRole = role;
		role = newRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ROLE_PREFERENCE__ROLE, oldRole, role));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentGalleryFolder getGallery() {
		return gallery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGallery(ComponentGalleryFolder newGallery, NotificationChain msgs) {
		ComponentGalleryFolder oldGallery = gallery;
		gallery = newGallery;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY, oldGallery, newGallery);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGallery(ComponentGalleryFolder newGallery) {
		if (newGallery != gallery) {
			NotificationChain msgs = null;
			if (gallery != null)
				msgs = ((InternalEObject)gallery).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY, null, msgs);
			if (newGallery != null)
				msgs = ((InternalEObject)newGallery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY, null, msgs);
			msgs = basicSetGallery(newGallery, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY, newGallery, newGallery));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public View getView() {
		if (view != null && view.eIsProxy()) {
			InternalEObject oldView = (InternalEObject)view;
			view = (View)eResolveProxy(oldView);
			if (view != oldView) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BEViewsConfigurationPackage.ROLE_PREFERENCE__VIEW, oldView, view));
			}
		}
		return view;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public View basicGetView() {
		return view;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setView(View newView) {
		View oldView = view;
		view = newView;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ROLE_PREFERENCE__VIEW, oldView, view));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY:
				return basicSetGallery(null, msgs);
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
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__ROLE:
				return getRole();
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY:
				return getGallery();
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__VIEW:
				if (resolve) return getView();
				return basicGetView();
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
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__ROLE:
				setRole((String)newValue);
				return;
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY:
				setGallery((ComponentGalleryFolder)newValue);
				return;
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__VIEW:
				setView((View)newValue);
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
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__ROLE:
				setRole(ROLE_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY:
				setGallery((ComponentGalleryFolder)null);
				return;
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__VIEW:
				setView((View)null);
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
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__ROLE:
				return ROLE_EDEFAULT == null ? role != null : !ROLE_EDEFAULT.equals(role);
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__GALLERY:
				return gallery != null;
			case BEViewsConfigurationPackage.ROLE_PREFERENCE__VIEW:
				return view != null;
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
		result.append(" (role: ");
		result.append(role);
		result.append(')');
		return result.toString();
	}

} //RolePreferenceImpl
