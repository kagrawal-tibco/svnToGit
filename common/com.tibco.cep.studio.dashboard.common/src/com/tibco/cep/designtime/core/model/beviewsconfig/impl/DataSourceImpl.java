/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataSource;
import com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataSourceImpl#getQuery <em>Query</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataSourceImpl#getSrcElement <em>Src Element</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.DataSourceImpl#getParams <em>Params</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataSourceImpl extends BEViewsElementImpl implements DataSource {
	/**
	 * The default value of the '{@link #getQuery() <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected static final String QUERY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQuery() <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected String query = QUERY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSrcElement() <em>Src Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcElement()
	 * @generated
	 * @ordered
	 */
	protected EObject srcElement;

	/**
	 * The cached value of the '{@link #getParams() <em>Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParams()
	 * @generated
	 * @ordered
	 */
	protected EList<QueryParam> params;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getDataSource();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQuery(String newQuery) {
		String oldQuery = query;
		query = newQuery;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_SOURCE__QUERY, oldQuery, query));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getSrcElement() {
		if (srcElement != null && srcElement.eIsProxy()) {
			InternalEObject oldSrcElement = (InternalEObject)srcElement;
			srcElement = eResolveProxy(oldSrcElement);
			if (srcElement != oldSrcElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BEViewsConfigurationPackage.DATA_SOURCE__SRC_ELEMENT, oldSrcElement, srcElement));
			}
		}
		return srcElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetSrcElement() {
		return srcElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrcElement(EObject newSrcElement) {
		EObject oldSrcElement = srcElement;
		srcElement = newSrcElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.DATA_SOURCE__SRC_ELEMENT, oldSrcElement, srcElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<QueryParam> getParams() {
		if (params == null) {
			params = new EObjectContainmentEList<QueryParam>(QueryParam.class, this, BEViewsConfigurationPackage.DATA_SOURCE__PARAMS);
		}
		return params;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.DATA_SOURCE__PARAMS:
				return ((InternalEList<?>)getParams()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.DATA_SOURCE__QUERY:
				return getQuery();
			case BEViewsConfigurationPackage.DATA_SOURCE__SRC_ELEMENT:
				if (resolve) return getSrcElement();
				return basicGetSrcElement();
			case BEViewsConfigurationPackage.DATA_SOURCE__PARAMS:
				return getParams();
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
			case BEViewsConfigurationPackage.DATA_SOURCE__QUERY:
				setQuery((String)newValue);
				return;
			case BEViewsConfigurationPackage.DATA_SOURCE__SRC_ELEMENT:
				setSrcElement((EObject)newValue);
				return;
			case BEViewsConfigurationPackage.DATA_SOURCE__PARAMS:
				getParams().clear();
				getParams().addAll((Collection<? extends QueryParam>)newValue);
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
			case BEViewsConfigurationPackage.DATA_SOURCE__QUERY:
				setQuery(QUERY_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.DATA_SOURCE__SRC_ELEMENT:
				setSrcElement((EObject)null);
				return;
			case BEViewsConfigurationPackage.DATA_SOURCE__PARAMS:
				getParams().clear();
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
			case BEViewsConfigurationPackage.DATA_SOURCE__QUERY:
				return QUERY_EDEFAULT == null ? query != null : !QUERY_EDEFAULT.equals(query);
			case BEViewsConfigurationPackage.DATA_SOURCE__SRC_ELEMENT:
				return srcElement != null;
			case BEViewsConfigurationPackage.DATA_SOURCE__PARAMS:
				return params != null && !params.isEmpty();
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
		result.append(" (query: ");
		result.append(query);
		result.append(')');
		return result.toString();
	}

} //DataSourceImpl
