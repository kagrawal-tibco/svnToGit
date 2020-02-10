/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.tibco.cep.decisionproject.ontology.Arguments;
import com.tibco.cep.decisionproject.ontology.Header;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.RuleFunction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.RuleFunctionImpl#getHeader <em>Header</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.RuleFunctionImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.RuleFunctionImpl#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleFunctionImpl extends AbstractResourceImpl implements RuleFunction {
	/**
	 * The cached value of the '{@link #getHeader() <em>Header</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeader()
	 * @generated
	 * @ordered
	 */
	protected Header header;
	/**
	 * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected Arguments arguments;
	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected EList<String> body;

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
		return OntologyPackage.Literals.RULE_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Header getHeader() {
		return header;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHeader(Header newHeader, NotificationChain msgs) {
		Header oldHeader = header;
		header = newHeader;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OntologyPackage.RULE_FUNCTION__HEADER, oldHeader, newHeader);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeader(Header newHeader) {
		if (newHeader != header) {
			NotificationChain msgs = null;
			if (header != null)
				msgs = ((InternalEObject)header).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OntologyPackage.RULE_FUNCTION__HEADER, null, msgs);
			if (newHeader != null)
				msgs = ((InternalEObject)newHeader).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OntologyPackage.RULE_FUNCTION__HEADER, null, msgs);
			msgs = basicSetHeader(newHeader, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.RULE_FUNCTION__HEADER, newHeader, newHeader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getBody() {
		if (body == null) {
			body = new EDataTypeUniqueEList<String>(String.class, this, OntologyPackage.RULE_FUNCTION__BODY);
		}
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Arguments getArguments() {
		return arguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArguments(Arguments newArguments, NotificationChain msgs) {
		Arguments oldArguments = arguments;
		arguments = newArguments;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OntologyPackage.RULE_FUNCTION__ARGUMENTS, oldArguments, newArguments);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArguments(Arguments newArguments) {
		if (newArguments != arguments) {
			NotificationChain msgs = null;
			if (arguments != null)
				msgs = ((InternalEObject)arguments).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OntologyPackage.RULE_FUNCTION__ARGUMENTS, null, msgs);
			if (newArguments != null)
				msgs = ((InternalEObject)newArguments).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OntologyPackage.RULE_FUNCTION__ARGUMENTS, null, msgs);
			msgs = basicSetArguments(newArguments, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.RULE_FUNCTION__ARGUMENTS, newArguments, newArguments));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OntologyPackage.RULE_FUNCTION__HEADER:
				return basicSetHeader(null, msgs);
			case OntologyPackage.RULE_FUNCTION__ARGUMENTS:
				return basicSetArguments(null, msgs);
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
			case OntologyPackage.RULE_FUNCTION__HEADER:
				return getHeader();
			case OntologyPackage.RULE_FUNCTION__ARGUMENTS:
				return getArguments();
			case OntologyPackage.RULE_FUNCTION__BODY:
				return getBody();
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
			case OntologyPackage.RULE_FUNCTION__HEADER:
				setHeader((Header)newValue);
				return;
			case OntologyPackage.RULE_FUNCTION__ARGUMENTS:
				setArguments((Arguments)newValue);
				return;
			case OntologyPackage.RULE_FUNCTION__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends String>)newValue);
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
			case OntologyPackage.RULE_FUNCTION__HEADER:
				setHeader((Header)null);
				return;
			case OntologyPackage.RULE_FUNCTION__ARGUMENTS:
				setArguments((Arguments)null);
				return;
			case OntologyPackage.RULE_FUNCTION__BODY:
				getBody().clear();
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
			case OntologyPackage.RULE_FUNCTION__HEADER:
				return header != null;
			case OntologyPackage.RULE_FUNCTION__ARGUMENTS:
				return arguments != null;
			case OntologyPackage.RULE_FUNCTION__BODY:
				return body != null && !body.isEmpty();
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
		result.append(" (body: ");
		result.append(body);
		result.append(')');
		return result.toString();
	}

} //RuleFunctionImpl
