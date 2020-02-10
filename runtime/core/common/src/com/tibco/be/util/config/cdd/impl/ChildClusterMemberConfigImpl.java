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
import com.tibco.be.util.config.cdd.ChildClusterMemberConfig;
import com.tibco.be.util.config.cdd.PropertyConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Child Cluster Member Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ChildClusterMemberConfigImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ChildClusterMemberConfigImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ChildClusterMemberConfigImpl#getTolerance <em>Tolerance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChildClusterMemberConfigImpl extends EObjectImpl implements ChildClusterMemberConfig {
	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyConfig> property;

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
	 * The default value of the '{@link #getTolerance() <em>Tolerance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTolerance()
	 * @generated
	 * @ordered
	 */
	protected static final String TOLERANCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTolerance() <em>Tolerance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTolerance()
	 * @generated
	 * @ordered
	 */
	protected String tolerance = TOLERANCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChildClusterMemberConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getChildClusterMemberConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyConfig> getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList<PropertyConfig>(PropertyConfig.class, this, CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PROPERTY);
		}
		return property;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTolerance() {
		return tolerance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTolerance(String newTolerance) {
		String oldTolerance = tolerance;
		tolerance = newTolerance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__TOLERANCE, oldTolerance, tolerance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PROPERTY:
				return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
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
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PROPERTY:
				return getProperty();
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PATH:
				return getPath();
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__TOLERANCE:
				return getTolerance();
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
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends PropertyConfig>)newValue);
				return;
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PATH:
				setPath((String)newValue);
				return;
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__TOLERANCE:
				setTolerance((String)newValue);
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
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PROPERTY:
				getProperty().clear();
				return;
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__TOLERANCE:
				setTolerance(TOLERANCE_EDEFAULT);
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
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PROPERTY:
				return property != null && !property.isEmpty();
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case CddPackage.CHILD_CLUSTER_MEMBER_CONFIG__TOLERANCE:
				return TOLERANCE_EDEFAULT == null ? tolerance != null : !TOLERANCE_EDEFAULT.equals(tolerance);
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
		result.append(" (path: ");
		result.append(path);
		result.append(", tolerance: ");
		result.append(tolerance);
		result.append(')');
		return result.toString();
	}

} //ChildClusterMemberConfigImpl
