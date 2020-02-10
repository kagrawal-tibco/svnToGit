/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.RulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.BindingImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.BindingImpl#getDomainModelPath <em>Domain Model Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BindingImpl extends SymbolImpl implements Binding {
	/**
	 * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPRESSION_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected String expression = EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDomainModelPath() <em>Domain Model Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainModelPath()
	 * @generated
	 * @ordered
	 */
	protected static final String DOMAIN_MODEL_PATH_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getDomainModelPath() <em>Domain Model Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainModelPath()
	 * @generated
	 * @ordered
	 */
	protected String domainModelPath = DOMAIN_MODEL_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpression(String newExpression) {
		String oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.BINDING__EXPRESSION, oldExpression, expression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDomainModelPath() {
		return domainModelPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainModelPath(String newDomainModelPath) {
		String oldDomainModelPath = domainModelPath;
		domainModelPath = newDomainModelPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.BINDING__DOMAIN_MODEL_PATH, oldDomainModelPath, domainModelPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulePackage.BINDING__EXPRESSION:
				return getExpression();
			case RulePackage.BINDING__DOMAIN_MODEL_PATH:
				return getDomainModelPath();
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
			case RulePackage.BINDING__EXPRESSION:
				setExpression((String)newValue);
				return;
			case RulePackage.BINDING__DOMAIN_MODEL_PATH:
				setDomainModelPath((String)newValue);
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
			case RulePackage.BINDING__EXPRESSION:
				setExpression(EXPRESSION_EDEFAULT);
				return;
			case RulePackage.BINDING__DOMAIN_MODEL_PATH:
				setDomainModelPath(DOMAIN_MODEL_PATH_EDEFAULT);
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
			case RulePackage.BINDING__EXPRESSION:
				return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
			case RulePackage.BINDING__DOMAIN_MODEL_PATH:
				return DOMAIN_MODEL_PATH_EDEFAULT == null ? domainModelPath != null : !DOMAIN_MODEL_PATH_EDEFAULT.equals(domainModelPath);
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
		result.append(" (expression: ");
		result.append(expression);
		result.append(", domainModelPath: ");
		result.append(domainModelPath);
		result.append(')');
		return result.toString();
	}

} //BindingImpl
