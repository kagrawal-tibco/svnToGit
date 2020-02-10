/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ClusterMemberConfig;
import com.tibco.be.util.config.cdd.SetPropertyConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cluster Member Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterMemberConfigImpl#getSetProperty <em>Set Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterMemberConfigImpl#getMemberName <em>Member Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ClusterMemberConfigImpl#getPath <em>Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClusterMemberConfigImpl extends EObjectImpl implements ClusterMemberConfig {
	/**
	 * The cached value of the '{@link #getSetProperty() <em>Set Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<SetPropertyConfig> setProperty;

	/**
	 * The default value of the '{@link #getMemberName() <em>Member Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemberName()
	 * @generated
	 * @ordered
	 */
	protected static final String MEMBER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMemberName() <em>Member Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemberName()
	 * @generated
	 * @ordered
	 */
	protected String memberName = MEMBER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClusterMemberConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getClusterMemberConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SetPropertyConfig> getSetProperty() {
		if (setProperty == null) {
			setProperty = new EObjectContainmentEList<SetPropertyConfig>(SetPropertyConfig.class, this, CddPackage.CLUSTER_MEMBER_CONFIG__SET_PROPERTY);
		}
		return setProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemberName(String newMemberName) {
		String oldMemberName = memberName;
		memberName = newMemberName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_MEMBER_CONFIG__MEMBER_NAME, oldMemberName, memberName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CLUSTER_MEMBER_CONFIG__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CLUSTER_MEMBER_CONFIG__SET_PROPERTY:
				return ((InternalEList<?>)getSetProperty()).basicRemove(otherEnd, msgs);
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
			case CddPackage.CLUSTER_MEMBER_CONFIG__SET_PROPERTY:
				return getSetProperty();
			case CddPackage.CLUSTER_MEMBER_CONFIG__MEMBER_NAME:
				return getMemberName();
			case CddPackage.CLUSTER_MEMBER_CONFIG__PATH:
				return getPath();
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
			case CddPackage.CLUSTER_MEMBER_CONFIG__SET_PROPERTY:
				getSetProperty().clear();
				getSetProperty().addAll((Collection<? extends SetPropertyConfig>)newValue);
				return;
			case CddPackage.CLUSTER_MEMBER_CONFIG__MEMBER_NAME:
				setMemberName((String)newValue);
				return;
			case CddPackage.CLUSTER_MEMBER_CONFIG__PATH:
				setPath((String)newValue);
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
			case CddPackage.CLUSTER_MEMBER_CONFIG__SET_PROPERTY:
				getSetProperty().clear();
				return;
			case CddPackage.CLUSTER_MEMBER_CONFIG__MEMBER_NAME:
				setMemberName(MEMBER_NAME_EDEFAULT);
				return;
			case CddPackage.CLUSTER_MEMBER_CONFIG__PATH:
				setPath(PATH_EDEFAULT);
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
			case CddPackage.CLUSTER_MEMBER_CONFIG__SET_PROPERTY:
				return setProperty != null && !setProperty.isEmpty();
			case CddPackage.CLUSTER_MEMBER_CONFIG__MEMBER_NAME:
				return MEMBER_NAME_EDEFAULT == null ? memberName != null : !MEMBER_NAME_EDEFAULT.equals(memberName);
			case CddPackage.CLUSTER_MEMBER_CONFIG__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
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
		result.append(" (memberName: ");
		result.append(memberName);
		result.append(", path: ");
		result.append(path);
		result.append(')');
		return result.toString();
	}

} //ClusterMemberConfigImpl
