/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Method Argument Match</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.MethodArgumentMatchImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.impl.MethodArgumentMatchImpl#getArgNode <em>Arg Node</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MethodArgumentMatchImpl extends RuleSourceMatchImpl implements MethodArgumentMatch {
	/**
	 * The default value of the '{@link #getFunction() <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunction()
	 * @generated
	 * @ordered
	 */
	protected static final Object FUNCTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFunction() <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunction()
	 * @generated
	 * @ordered
	 */
	protected Object function = FUNCTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getArgNode() <em>Arg Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArgNode()
	 * @generated
	 * @ordered
	 */
	protected static final Object ARG_NODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArgNode() <em>Arg Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArgNode()
	 * @generated
	 * @ordered
	 */
	protected Object argNode = ARG_NODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MethodArgumentMatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SearchPackage.Literals.METHOD_ARGUMENT_MATCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getFunction() {
		return function;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunction(Object newFunction) {
		Object oldFunction = function;
		function = newFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.METHOD_ARGUMENT_MATCH__FUNCTION, oldFunction, function));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getArgNode() {
		return argNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArgNode(Object newArgNode) {
		Object oldArgNode = argNode;
		argNode = newArgNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.METHOD_ARGUMENT_MATCH__ARG_NODE, oldArgNode, argNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SearchPackage.METHOD_ARGUMENT_MATCH__FUNCTION:
				return getFunction();
			case SearchPackage.METHOD_ARGUMENT_MATCH__ARG_NODE:
				return getArgNode();
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
			case SearchPackage.METHOD_ARGUMENT_MATCH__FUNCTION:
				setFunction(newValue);
				return;
			case SearchPackage.METHOD_ARGUMENT_MATCH__ARG_NODE:
				setArgNode(newValue);
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
			case SearchPackage.METHOD_ARGUMENT_MATCH__FUNCTION:
				setFunction(FUNCTION_EDEFAULT);
				return;
			case SearchPackage.METHOD_ARGUMENT_MATCH__ARG_NODE:
				setArgNode(ARG_NODE_EDEFAULT);
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
			case SearchPackage.METHOD_ARGUMENT_MATCH__FUNCTION:
				return FUNCTION_EDEFAULT == null ? function != null : !FUNCTION_EDEFAULT.equals(function);
			case SearchPackage.METHOD_ARGUMENT_MATCH__ARG_NODE:
				return ARG_NODE_EDEFAULT == null ? argNode != null : !ARG_NODE_EDEFAULT.equals(argNode);
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
		result.append(" (function: ");
		result.append(function);
		result.append(", argNode: ");
		result.append(argNode);
		result.append(')');
		return result.toString();
	}

} //MethodArgumentMatchImpl
