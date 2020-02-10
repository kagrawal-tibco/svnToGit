/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.RuleSet;
import com.tibco.cep.decisionproject.ontology.RuleSetParticipant;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.RuleSetImpl#getRuleSetParicipant <em>Rule Set Paricipant</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleSetImpl extends ParentResourceImpl implements RuleSet {
	/**
	 * The cached value of the '{@link #getRuleSetParicipant() <em>Rule Set Paricipant</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleSetParicipant()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleSetParticipant> ruleSetParicipant;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected RuleSetImpl() {
		super();
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.RULE_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RuleSetParticipant> getRuleSetParicipant() {
		if (ruleSetParicipant == null) {
			ruleSetParicipant = new EObjectResolvingEList<RuleSetParticipant>(RuleSetParticipant.class, this, OntologyPackage.RULE_SET__RULE_SET_PARICIPANT);
		}
		return ruleSetParicipant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OntologyPackage.RULE_SET__RULE_SET_PARICIPANT:
				return getRuleSetParicipant();
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
			case OntologyPackage.RULE_SET__RULE_SET_PARICIPANT:
				getRuleSetParicipant().clear();
				getRuleSetParicipant().addAll((Collection<? extends RuleSetParticipant>)newValue);
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
			case OntologyPackage.RULE_SET__RULE_SET_PARICIPANT:
				getRuleSetParicipant().clear();
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
			case OntologyPackage.RULE_SET__RULE_SET_PARICIPANT:
				return ruleSetParicipant != null && !ruleSetParicipant.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.ontology.ParentResource#addChild(com.tibco.cep.decisionproject.ontology.AbstractResource)
	 */
	public boolean addChild(AbstractResource abstractResource) {
		if (!(abstractResource instanceof RuleSetParticipant)) {
			return false;
		}
		if (ruleSetParicipant == null){
			ruleSetParicipant = new EObjectResolvingEList<RuleSetParticipant>(RuleSetParticipant.class, this, OntologyPackage.RULE_SET__RULE_SET_PARICIPANT);
		}
	
		return ruleSetParicipant.add((RuleSetParticipant)abstractResource);
	
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.model.impl.ParentResourceImpl#getChildren()
	 */
	@Override
	public Iterator<RuleSetParticipant> getChildren() {
		return getRuleSetParicipant().iterator();
	}

} //RuleSetImpl
