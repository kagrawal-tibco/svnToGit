/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.functions.impl;

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

import com.tibco.cep.studio.core.functions.Category;
import com.tibco.cep.studio.core.functions.Function;
import com.tibco.cep.studio.core.functions.FunctionsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.CategoryImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.CategoryImpl#isValidInQuery <em>Valid In Query</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.CategoryImpl#isValidInBUI <em>Valid In BUI</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.CategoryImpl#getFunctions <em>Functions</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.impl.CategoryImpl#getCategories <em>Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CategoryImpl extends EObjectImpl implements Category {
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
	 * The default value of the '{@link #isValidInQuery() <em>Valid In Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValidInQuery()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALID_IN_QUERY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isValidInQuery() <em>Valid In Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValidInQuery()
	 * @generated
	 * @ordered
	 */
	protected boolean validInQuery = VALID_IN_QUERY_EDEFAULT;

	/**
	 * The default value of the '{@link #isValidInBUI() <em>Valid In BUI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValidInBUI()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALID_IN_BUI_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isValidInBUI() <em>Valid In BUI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValidInBUI()
	 * @generated
	 * @ordered
	 */
	protected boolean validInBUI = VALID_IN_BUI_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFunctions() <em>Functions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctions()
	 * @generated
	 * @ordered
	 */
	protected EList<Function> functions;

	/**
	 * The cached value of the '{@link #getCategories() <em>Categories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<Category> categories;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CategoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FunctionsPackage.Literals.CATEGORY;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.CATEGORY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValidInQuery() {
		return validInQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidInQuery(boolean newValidInQuery) {
		boolean oldValidInQuery = validInQuery;
		validInQuery = newValidInQuery;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.CATEGORY__VALID_IN_QUERY, oldValidInQuery, validInQuery));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValidInBUI() {
		return validInBUI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidInBUI(boolean newValidInBUI) {
		boolean oldValidInBUI = validInBUI;
		validInBUI = newValidInBUI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.CATEGORY__VALID_IN_BUI, oldValidInBUI, validInBUI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Function> getFunctions() {
		if (functions == null) {
			functions = new EObjectContainmentEList<Function>(Function.class, this, FunctionsPackage.CATEGORY__FUNCTIONS);
		}
		return functions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Category> getCategories() {
		if (categories == null) {
			categories = new EObjectContainmentEList<Category>(Category.class, this, FunctionsPackage.CATEGORY__CATEGORIES);
		}
		return categories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FunctionsPackage.CATEGORY__FUNCTIONS:
				return ((InternalEList<?>)getFunctions()).basicRemove(otherEnd, msgs);
			case FunctionsPackage.CATEGORY__CATEGORIES:
				return ((InternalEList<?>)getCategories()).basicRemove(otherEnd, msgs);
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
			case FunctionsPackage.CATEGORY__NAME:
				return getName();
			case FunctionsPackage.CATEGORY__VALID_IN_QUERY:
				return isValidInQuery() ? Boolean.TRUE : Boolean.FALSE;
			case FunctionsPackage.CATEGORY__VALID_IN_BUI:
				return isValidInBUI() ? Boolean.TRUE : Boolean.FALSE;
			case FunctionsPackage.CATEGORY__FUNCTIONS:
				return getFunctions();
			case FunctionsPackage.CATEGORY__CATEGORIES:
				return getCategories();
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
			case FunctionsPackage.CATEGORY__NAME:
				setName((String)newValue);
				return;
			case FunctionsPackage.CATEGORY__VALID_IN_QUERY:
				setValidInQuery(((Boolean)newValue).booleanValue());
				return;
			case FunctionsPackage.CATEGORY__VALID_IN_BUI:
				setValidInBUI(((Boolean)newValue).booleanValue());
				return;
			case FunctionsPackage.CATEGORY__FUNCTIONS:
				getFunctions().clear();
				getFunctions().addAll((Collection<? extends Function>)newValue);
				return;
			case FunctionsPackage.CATEGORY__CATEGORIES:
				getCategories().clear();
				getCategories().addAll((Collection<? extends Category>)newValue);
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
			case FunctionsPackage.CATEGORY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FunctionsPackage.CATEGORY__VALID_IN_QUERY:
				setValidInQuery(VALID_IN_QUERY_EDEFAULT);
				return;
			case FunctionsPackage.CATEGORY__VALID_IN_BUI:
				setValidInBUI(VALID_IN_BUI_EDEFAULT);
				return;
			case FunctionsPackage.CATEGORY__FUNCTIONS:
				getFunctions().clear();
				return;
			case FunctionsPackage.CATEGORY__CATEGORIES:
				getCategories().clear();
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
			case FunctionsPackage.CATEGORY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FunctionsPackage.CATEGORY__VALID_IN_QUERY:
				return validInQuery != VALID_IN_QUERY_EDEFAULT;
			case FunctionsPackage.CATEGORY__VALID_IN_BUI:
				return validInBUI != VALID_IN_BUI_EDEFAULT;
			case FunctionsPackage.CATEGORY__FUNCTIONS:
				return functions != null && !functions.isEmpty();
			case FunctionsPackage.CATEGORY__CATEGORIES:
				return categories != null && !categories.isEmpty();
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
		result.append(", validInQuery: ");
		result.append(validInQuery);
		result.append(", validInBUI: ");
		result.append(validInBUI);
		result.append(')');
		return result.toString();
	}

} //CategoryImpl
