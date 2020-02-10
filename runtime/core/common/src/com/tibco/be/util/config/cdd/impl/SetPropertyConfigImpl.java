/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ChildClusterMemberConfig;
import com.tibco.be.util.config.cdd.NotificationConfig;
import com.tibco.be.util.config.cdd.SetPropertyConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Property Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SetPropertyConfigImpl#getChildClusterMember <em>Child Cluster Member</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SetPropertyConfigImpl#getNotification <em>Notification</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SetPropertyConfigImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SetPropertyConfigImpl#getSetPropertyName <em>Set Property Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SetPropertyConfigImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SetPropertyConfigImpl extends EObjectImpl implements SetPropertyConfig {
	/**
	 * The cached value of the '{@link #getChildClusterMember() <em>Child Cluster Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildClusterMember()
	 * @generated
	 * @ordered
	 */
	protected ChildClusterMemberConfig childClusterMember;

	/**
	 * The cached value of the '{@link #getNotification() <em>Notification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotification()
	 * @generated
	 * @ordered
	 */
	protected NotificationConfig notification;

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
	 * The default value of the '{@link #getSetPropertyName() <em>Set Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetPropertyName()
	 * @generated
	 * @ordered
	 */
	protected static final String SET_PROPERTY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSetPropertyName() <em>Set Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetPropertyName()
	 * @generated
	 * @ordered
	 */
	protected String setPropertyName = SET_PROPERTY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SetPropertyConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getSetPropertyConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildClusterMemberConfig getChildClusterMember() {
		return childClusterMember;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChildClusterMember(ChildClusterMemberConfig newChildClusterMember, NotificationChain msgs) {
		ChildClusterMemberConfig oldChildClusterMember = childClusterMember;
		childClusterMember = newChildClusterMember;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER, oldChildClusterMember, newChildClusterMember);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildClusterMember(ChildClusterMemberConfig newChildClusterMember) {
		if (newChildClusterMember != childClusterMember) {
			NotificationChain msgs = null;
			if (childClusterMember != null)
				msgs = ((InternalEObject)childClusterMember).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER, null, msgs);
			if (newChildClusterMember != null)
				msgs = ((InternalEObject)newChildClusterMember).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER, null, msgs);
			msgs = basicSetChildClusterMember(newChildClusterMember, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER, newChildClusterMember, newChildClusterMember));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationConfig getNotification() {
		return notification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNotification(NotificationConfig newNotification, NotificationChain msgs) {
		NotificationConfig oldNotification = notification;
		notification = newNotification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION, oldNotification, newNotification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotification(NotificationConfig newNotification) {
		if (newNotification != notification) {
			NotificationChain msgs = null;
			if (notification != null)
				msgs = ((InternalEObject)notification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION, null, msgs);
			if (newNotification != null)
				msgs = ((InternalEObject)newNotification).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION, null, msgs);
			msgs = basicSetNotification(newNotification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION, newNotification, newNotification));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SET_PROPERTY_CONFIG__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSetPropertyName() {
		return setPropertyName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSetPropertyName(String newSetPropertyName) {
		String oldSetPropertyName = setPropertyName;
		setPropertyName = newSetPropertyName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SET_PROPERTY_CONFIG__SET_PROPERTY_NAME, oldSetPropertyName, setPropertyName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SET_PROPERTY_CONFIG__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER:
				return basicSetChildClusterMember(null, msgs);
			case CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION:
				return basicSetNotification(null, msgs);
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
			case CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER:
				return getChildClusterMember();
			case CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION:
				return getNotification();
			case CddPackage.SET_PROPERTY_CONFIG__NAME:
				return getName();
			case CddPackage.SET_PROPERTY_CONFIG__SET_PROPERTY_NAME:
				return getSetPropertyName();
			case CddPackage.SET_PROPERTY_CONFIG__VALUE:
				return getValue();
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
			case CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER:
				setChildClusterMember((ChildClusterMemberConfig)newValue);
				return;
			case CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION:
				setNotification((NotificationConfig)newValue);
				return;
			case CddPackage.SET_PROPERTY_CONFIG__NAME:
				setName((String)newValue);
				return;
			case CddPackage.SET_PROPERTY_CONFIG__SET_PROPERTY_NAME:
				setSetPropertyName((String)newValue);
				return;
			case CddPackage.SET_PROPERTY_CONFIG__VALUE:
				setValue((String)newValue);
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
			case CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER:
				setChildClusterMember((ChildClusterMemberConfig)null);
				return;
			case CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION:
				setNotification((NotificationConfig)null);
				return;
			case CddPackage.SET_PROPERTY_CONFIG__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CddPackage.SET_PROPERTY_CONFIG__SET_PROPERTY_NAME:
				setSetPropertyName(SET_PROPERTY_NAME_EDEFAULT);
				return;
			case CddPackage.SET_PROPERTY_CONFIG__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case CddPackage.SET_PROPERTY_CONFIG__CHILD_CLUSTER_MEMBER:
				return childClusterMember != null;
			case CddPackage.SET_PROPERTY_CONFIG__NOTIFICATION:
				return notification != null;
			case CddPackage.SET_PROPERTY_CONFIG__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CddPackage.SET_PROPERTY_CONFIG__SET_PROPERTY_NAME:
				return SET_PROPERTY_NAME_EDEFAULT == null ? setPropertyName != null : !SET_PROPERTY_NAME_EDEFAULT.equals(setPropertyName);
			case CddPackage.SET_PROPERTY_CONFIG__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", setPropertyName: ");
		result.append(setPropertyName);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //SetPropertyConfigImpl
