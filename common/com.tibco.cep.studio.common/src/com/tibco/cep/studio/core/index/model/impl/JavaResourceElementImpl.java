/**
 */
package com.tibco.cep.studio.core.index.model.impl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.JavaResourceElement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Resource Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.JavaResourceElementImpl#getJavaResource <em>Java Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaResourceElementImpl extends EntityElementImpl implements JavaResourceElement {
	/**
	 * The cached value of the '{@link #getJavaResource() <em>Java Resource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaResource()
	 * @generated
	 * @ordered
	 */
	protected JavaResource javaResource;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaResourceElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.JAVA_RESOURCE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaResource getJavaResource() {
		return javaResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJavaResource(JavaResource newJavaResource, NotificationChain msgs) {
		JavaResource oldJavaResource = javaResource;
		javaResource = newJavaResource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE, oldJavaResource, newJavaResource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaResource(JavaResource newJavaResource) {
		if (newJavaResource != javaResource) {
			NotificationChain msgs = null;
			if (javaResource != null)
				msgs = ((InternalEObject)javaResource).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE, null, msgs);
			if (newJavaResource != null)
				msgs = ((InternalEObject)newJavaResource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE, null, msgs);
			msgs = basicSetJavaResource(newJavaResource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE, newJavaResource, newJavaResource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE:
				return basicSetJavaResource(null, msgs);
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
			case IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE:
				return getJavaResource();
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
			case IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE:
				setJavaResource((JavaResource)newValue);
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
			case IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE:
				setJavaResource((JavaResource)null);
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
			case IndexPackage.JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE:
				return javaResource != null;
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public Entity getEntity() {
		return getJavaResource();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void setEntity(Entity newEntity) {
		setJavaResource((JavaResource) newEntity);
	}

} //JavaResourceElementImpl
