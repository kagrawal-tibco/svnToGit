/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id.impl;

import com.tibco.be.util.config.sharedresources.id.Designer;
import com.tibco.be.util.config.sharedresources.id.IdPackage;
import com.tibco.be.util.config.sharedresources.id.ResourceDescriptions;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Designer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.DesignerImpl#getLockedProperties <em>Locked Properties</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.DesignerImpl#getFixedChildren <em>Fixed Children</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.DesignerImpl#getResourceDescriptions <em>Resource Descriptions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DesignerImpl extends EObjectImpl implements Designer {
	/**
	 * The default value of the '{@link #getLockedProperties() <em>Locked Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLockedProperties()
	 * @generated
	 * @ordered
	 */
    protected static final String LOCKED_PROPERTIES_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getLockedProperties() <em>Locked Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLockedProperties()
	 * @generated
	 * @ordered
	 */
    protected String lockedProperties = LOCKED_PROPERTIES_EDEFAULT;
    /**
	 * The default value of the '{@link #getFixedChildren() <em>Fixed Children</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFixedChildren()
	 * @generated
	 * @ordered
	 */
    protected static final String FIXED_CHILDREN_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getFixedChildren() <em>Fixed Children</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFixedChildren()
	 * @generated
	 * @ordered
	 */
    protected String fixedChildren = FIXED_CHILDREN_EDEFAULT;
    /**
	 * The cached value of the '{@link #getResourceDescriptions() <em>Resource Descriptions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceDescriptions()
	 * @generated
	 * @ordered
	 */
	protected ResourceDescriptions resourceDescriptions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DesignerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IdPackage.Literals.DESIGNER;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getLockedProperties()
    {
		return lockedProperties;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLockedProperties(String newLockedProperties)
    {
		String oldLockedProperties = lockedProperties;
		lockedProperties = newLockedProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.DESIGNER__LOCKED_PROPERTIES, oldLockedProperties, lockedProperties));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getFixedChildren()
    {
		return fixedChildren;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFixedChildren(String newFixedChildren)
    {
		String oldFixedChildren = fixedChildren;
		fixedChildren = newFixedChildren;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.DESIGNER__FIXED_CHILDREN, oldFixedChildren, fixedChildren));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceDescriptions getResourceDescriptions() {
		return resourceDescriptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourceDescriptions(ResourceDescriptions newResourceDescriptions, NotificationChain msgs) {
		ResourceDescriptions oldResourceDescriptions = resourceDescriptions;
		resourceDescriptions = newResourceDescriptions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS, oldResourceDescriptions, newResourceDescriptions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceDescriptions(ResourceDescriptions newResourceDescriptions) {
		if (newResourceDescriptions != resourceDescriptions) {
			NotificationChain msgs = null;
			if (resourceDescriptions != null)
				msgs = ((InternalEObject)resourceDescriptions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS, null, msgs);
			if (newResourceDescriptions != null)
				msgs = ((InternalEObject)newResourceDescriptions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS, null, msgs);
			msgs = basicSetResourceDescriptions(newResourceDescriptions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS, newResourceDescriptions, newResourceDescriptions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS:
				return basicSetResourceDescriptions(null, msgs);
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
			case IdPackage.DESIGNER__LOCKED_PROPERTIES:
				return getLockedProperties();
			case IdPackage.DESIGNER__FIXED_CHILDREN:
				return getFixedChildren();
			case IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS:
				return getResourceDescriptions();
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
			case IdPackage.DESIGNER__LOCKED_PROPERTIES:
				setLockedProperties((String)newValue);
				return;
			case IdPackage.DESIGNER__FIXED_CHILDREN:
				setFixedChildren((String)newValue);
				return;
			case IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS:
				setResourceDescriptions((ResourceDescriptions)newValue);
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
			case IdPackage.DESIGNER__LOCKED_PROPERTIES:
				setLockedProperties(LOCKED_PROPERTIES_EDEFAULT);
				return;
			case IdPackage.DESIGNER__FIXED_CHILDREN:
				setFixedChildren(FIXED_CHILDREN_EDEFAULT);
				return;
			case IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS:
				setResourceDescriptions((ResourceDescriptions)null);
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
			case IdPackage.DESIGNER__LOCKED_PROPERTIES:
				return LOCKED_PROPERTIES_EDEFAULT == null ? lockedProperties != null : !LOCKED_PROPERTIES_EDEFAULT.equals(lockedProperties);
			case IdPackage.DESIGNER__FIXED_CHILDREN:
				return FIXED_CHILDREN_EDEFAULT == null ? fixedChildren != null : !FIXED_CHILDREN_EDEFAULT.equals(fixedChildren);
			case IdPackage.DESIGNER__RESOURCE_DESCRIPTIONS:
				return resourceDescriptions != null;
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString()
    {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lockedProperties: ");
		result.append(lockedProperties);
		result.append(", fixedChildren: ");
		result.append(fixedChildren);
		result.append(')');
		return result.toString();
	}

} //DesignerImpl
