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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsRootImpl#getOntologyFunctions <em>Ontology Functions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OntologyFunctionsRootImpl extends EObjectImpl implements OntologyFunctionsRoot {
	/**
	 * The cached value of the '{@link #getOntologyFunctions() <em>Ontology Functions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOntologyFunctions()
	 * @generated
	 * @ordered
	 */
	protected AbstractResource ontologyFunctions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OntologyFunctionsRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyFunctionsPackage.Literals.ONTOLOGY_FUNCTIONS_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractResource getOntologyFunctions() {
		return ontologyFunctions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOntologyFunctions(AbstractResource newOntologyFunctions, NotificationChain msgs) {
		AbstractResource oldOntologyFunctions = ontologyFunctions;
		ontologyFunctions = newOntologyFunctions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS, oldOntologyFunctions, newOntologyFunctions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOntologyFunctions(AbstractResource newOntologyFunctions) {
		if (newOntologyFunctions != ontologyFunctions) {
			NotificationChain msgs = null;
			if (ontologyFunctions != null)
				msgs = ((InternalEObject)ontologyFunctions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS, null, msgs);
			if (newOntologyFunctions != null)
				msgs = ((InternalEObject)newOntologyFunctions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS, null, msgs);
			msgs = basicSetOntologyFunctions(newOntologyFunctions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS, newOntologyFunctions, newOntologyFunctions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS:
				return basicSetOntologyFunctions(null, msgs);
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS:
				return getOntologyFunctions();
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS:
				setOntologyFunctions((AbstractResource)newValue);
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS:
				setOntologyFunctions((AbstractResource)null);
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT__ONTOLOGY_FUNCTIONS:
				return ontologyFunctions != null;
		}
		return super.eIsSet(featureID);
	}

} //OntologyFunctionsRootImpl
