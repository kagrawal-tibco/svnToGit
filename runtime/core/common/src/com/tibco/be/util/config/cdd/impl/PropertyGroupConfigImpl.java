/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.PropertyConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Group Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.PropertyGroupConfigImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.PropertyGroupConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.PropertyGroupConfigImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.PropertyGroupConfigImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.PropertyGroupConfigImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyGroupConfigImpl extends EObjectImpl implements PropertyGroupConfig {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final Object COMMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected Object comment = COMMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final Object NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected Object name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyGroupConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getPropertyGroupConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, CddPackage.PROPERTY_GROUP_CONFIG__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyGroupConfig> getPropertyGroup() {
		return getGroup().list(CddPackage.eINSTANCE.getPropertyGroupConfig_PropertyGroup());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyConfig> getProperty() {
		return getGroup().list(CddPackage.eINSTANCE.getPropertyGroupConfig_Property());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(Object newComment) {
		Object oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROPERTY_GROUP_CONFIG__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(Object newName) {
		Object oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROPERTY_GROUP_CONFIG__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();
		for (EObject o : this.eContents()) {
			if (o instanceof PropertyGroupConfig) {
				p.putAll(((PropertyGroupConfig) o).toProperties());
			} else {
				final PropertyConfig pc = (PropertyConfig) o;
				p.put(pc.getName(), pc.getValue());
			}
		}
		return p;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PROPERTY_GROUP_CONFIG__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY_GROUP:
				return ((InternalEList<?>)getPropertyGroup()).basicRemove(otherEnd, msgs);
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY:
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
			case CddPackage.PROPERTY_GROUP_CONFIG__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY:
				return getProperty();
			case CddPackage.PROPERTY_GROUP_CONFIG__COMMENT:
				return getComment();
			case CddPackage.PROPERTY_GROUP_CONFIG__NAME:
				return getName();
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
			case CddPackage.PROPERTY_GROUP_CONFIG__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY_GROUP:
				getPropertyGroup().clear();
				getPropertyGroup().addAll((Collection<? extends PropertyGroupConfig>)newValue);
				return;
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends PropertyConfig>)newValue);
				return;
			case CddPackage.PROPERTY_GROUP_CONFIG__COMMENT:
				setComment(newValue);
				return;
			case CddPackage.PROPERTY_GROUP_CONFIG__NAME:
				setName(newValue);
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
			case CddPackage.PROPERTY_GROUP_CONFIG__GROUP:
				getGroup().clear();
				return;
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY_GROUP:
				getPropertyGroup().clear();
				return;
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY:
				getProperty().clear();
				return;
			case CddPackage.PROPERTY_GROUP_CONFIG__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case CddPackage.PROPERTY_GROUP_CONFIG__NAME:
				setName(NAME_EDEFAULT);
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
			case CddPackage.PROPERTY_GROUP_CONFIG__GROUP:
				return group != null && !group.isEmpty();
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY_GROUP:
				return !getPropertyGroup().isEmpty();
			case CddPackage.PROPERTY_GROUP_CONFIG__PROPERTY:
				return !getProperty().isEmpty();
			case CddPackage.PROPERTY_GROUP_CONFIG__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case CddPackage.PROPERTY_GROUP_CONFIG__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (group: ");
		result.append(group);
		result.append(", comment: ");
		result.append(comment);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PropertyGroupConfigImpl
