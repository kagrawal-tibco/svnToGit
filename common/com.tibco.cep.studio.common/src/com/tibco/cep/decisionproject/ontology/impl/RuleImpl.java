/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class RuleImpl extends RuleSetParticipantImpl implements Rule {
	
	/**
	 * @generated NOT
	 */
	private com.tibco.cep.designtime.model.rule.Rule cepRule; //Points to the referenced BE rule
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.RULE;
	}
	
	
	/**
	 * @generated NOT
	 * @see com.tibco.cep.decisionproject.ontology.Rule#getCEPRule()
	 */
	public com.tibco.cep.designtime.model.rule.Rule getCEPRule() {
		return cepRule;
	}

	/**
	 * @generated NOT
	 * @see com.tibco.cep.decisionproject.ontology.Rule#setCEPRule(com.tibco.cep.designtime.model.rule.Rule)
	 */
	public void setCEPRule(com.tibco.cep.designtime.model.rule.Rule cepRule) {
		this.cepRule = cepRule;
	}
	
	

} //RuleImpl
