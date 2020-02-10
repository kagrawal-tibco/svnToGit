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

import com.tibco.cep.decisionproject.ontologyfunctions.Concept;
import com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Concept</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.ConceptImpl#getFunctionLabel <em>Function Label</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConceptImpl extends AbstractResourceImpl implements Concept {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConceptImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyFunctionsPackage.Literals.CONCEPT;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL, oldFunctionLabel, newFunctionLabel);
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
				msgs = ((InternalEObject)functionLabel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL, null, msgs);
			if (newFunctionLabel != null)
				msgs = ((InternalEObject)newFunctionLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL, null, msgs);
			msgs = basicSetFunctionLabel(newFunctionLabel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL, newFunctionLabel, newFunctionLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL:
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
			case OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL:
				return getFunctionLabel();
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
			case OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL:
				setFunctionLabel((FunctionLabel)newValue);
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
			case OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL:
				setFunctionLabel((FunctionLabel)null);
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
			case OntologyFunctionsPackage.CONCEPT__FUNCTION_LABEL:
				return functionLabel != null;
		}
		return super.eIsSet(featureID);
	}

} //ConceptImpl
