/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.JavaElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.JavaElementImpl#getJavaSource <em>Java Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaElementImpl extends EntityElementImpl implements JavaElement {
	/**
	 * The cached value of the '{@link #getJavaSource() <em>Java Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaSource()
	 * @generated
	 * @ordered
	 */
	protected JavaSource javaSource;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.JAVA_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaSource getJavaSource() {
		return javaSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJavaSource(JavaSource newJavaSource, NotificationChain msgs) {
		JavaSource oldJavaSource = javaSource;
		javaSource = newJavaSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.JAVA_ELEMENT__JAVA_SOURCE, oldJavaSource, newJavaSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaSource(JavaSource newJavaSource) {
		if (newJavaSource != javaSource) {
			NotificationChain msgs = null;
			if (javaSource != null)
				msgs = ((InternalEObject)javaSource).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.JAVA_ELEMENT__JAVA_SOURCE, null, msgs);
			if (newJavaSource != null)
				msgs = ((InternalEObject)newJavaSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.JAVA_ELEMENT__JAVA_SOURCE, null, msgs);
			msgs = basicSetJavaSource(newJavaSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.JAVA_ELEMENT__JAVA_SOURCE, newJavaSource, newJavaSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.JAVA_ELEMENT__JAVA_SOURCE:
				return basicSetJavaSource(null, msgs);
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
			case IndexPackage.JAVA_ELEMENT__JAVA_SOURCE:
				return getJavaSource();
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
			case IndexPackage.JAVA_ELEMENT__JAVA_SOURCE:
				setJavaSource((JavaSource)newValue);
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
			case IndexPackage.JAVA_ELEMENT__JAVA_SOURCE:
				setJavaSource((JavaSource)null);
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
			case IndexPackage.JAVA_ELEMENT__JAVA_SOURCE:
				return javaSource != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Entity getEntity() {
		return getJavaSource();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public void setEntity(Entity newEntity) {
		setJavaSource((JavaSource) newEntity);
	}

	
} //JavaElementImpl
