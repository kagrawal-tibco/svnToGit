/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage;
import com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.RuleFunctionImpl#getFunctionLabel <em>Function Label</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.RuleFunctionImpl#getValidity <em>Validity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleFunctionImpl extends AbstractResourceImpl implements RuleFunction {
	/**
	 * The cached value of the '{@link #getFunctionLabel() <em>Function Label</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionLabel()
	 * @generated
	 * @ordered
	 */
	protected FunctionLabel functionLabel;

	/**
	 * The default value of the '{@link #getValidity() <em>Validity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidity()
	 * @generated
	 * @ordered
	 */
	protected static final String VALIDITY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getValidity() <em>Validity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidity()
	 * @generated
	 * @ordered
	 */
	protected String validity = VALIDITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyFunctionsPackage.Literals.RULE_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionLabel getFunctionLabel() {
		return functionLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctionLabel(FunctionLabel newFunctionLabel, NotificationChain msgs) {
		FunctionLabel oldFunctionLabel = functionLabel;
		functionLabel = newFunctionLabel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL, oldFunctionLabel, newFunctionLabel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionLabel(FunctionLabel newFunctionLabel) {
		if (newFunctionLabel != functionLabel) {
			NotificationChain msgs = null;
			if (functionLabel != null)
				msgs = ((InternalEObject)functionLabel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL, null, msgs);
			if (newFunctionLabel != null)
				msgs = ((InternalEObject)newFunctionLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL, null, msgs);
			msgs = basicSetFunctionLabel(newFunctionLabel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL, newFunctionLabel, newFunctionLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValidity() {
		return validity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidity(String newValidity) {
		String oldValidity = validity;
		validity = newValidity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyFunctionsPackage.RULE_FUNCTION__VALIDITY, oldValidity, validity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL:
				return basicSetFunctionLabel(null, msgs);
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
			case OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL:
				return getFunctionLabel();
			case OntologyFunctionsPackage.RULE_FUNCTION__VALIDITY:
				return getValidity();
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
			case OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL:
				setFunctionLabel((FunctionLabel)newValue);
				return;
			case OntologyFunctionsPackage.RULE_FUNCTION__VALIDITY:
				setValidity((String)newValue);
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
			case OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL:
				setFunctionLabel((FunctionLabel)null);
				return;
			case OntologyFunctionsPackage.RULE_FUNCTION__VALIDITY:
				setValidity(VALIDITY_EDEFAULT);
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
			case OntologyFunctionsPackage.RULE_FUNCTION__FUNCTION_LABEL:
				return functionLabel != null;
			case OntologyFunctionsPackage.RULE_FUNCTION__VALIDITY:
				return VALIDITY_EDEFAULT == null ? validity != null : !VALIDITY_EDEFAULT.equals(validity);
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
		result.append(" (validity: ");
		result.append(validity);
		result.append(')');
		return result.toString();
	}

} //RuleFunctionImpl
