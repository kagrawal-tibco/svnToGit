/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl#getToState <em>To State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl#getFromState <em>From State</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl#getGuardRule <em>Guard Rule</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl#isLambda <em>Lambda</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl#isFwdCorrelates <em>Fwd Correlates</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateTransitionImpl extends StateLinkImpl implements StateTransition {
	/**
	 * The cached value of the '{@link #getToState() <em>To State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToState()
	 * @generated
	 * @ordered
	 */
	protected State toState;

	/**
	 * The cached value of the '{@link #getFromState() <em>From State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromState()
	 * @generated
	 * @ordered
	 */
	protected State fromState;

	/**
	 * The cached value of the '{@link #getGuardRule() <em>Guard Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuardRule()
	 * @generated
	 * @ordered
	 */
	protected Rule guardRule;

	/**
	 * The default value of the '{@link #isLambda() <em>Lambda</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLambda()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LAMBDA_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLambda() <em>Lambda</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLambda()
	 * @generated
	 * @ordered
	 */
	protected boolean lambda = LAMBDA_EDEFAULT;

	/**
	 * The default value of the '{@link #isFwdCorrelates() <em>Fwd Correlates</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFwdCorrelates()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FWD_CORRELATES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFwdCorrelates() <em>Fwd Correlates</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFwdCorrelates()
	 * @generated
	 * @ordered
	 */
	protected boolean fwdCorrelates = FWD_CORRELATES_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateTransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE_TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getToState() {
		if (toState != null && toState.eIsProxy()) {
			InternalEObject oldToState = (InternalEObject)toState;
			toState = (State)eResolveProxy(oldToState);
			if (toState != oldToState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatesPackage.STATE_TRANSITION__TO_STATE, oldToState, toState));
			}
		}
		return toState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetToState() {
		return toState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToState(State newToState) {
		State oldToState = toState;
		toState = newToState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_TRANSITION__TO_STATE, oldToState, toState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getFromState() {
		if (fromState != null && fromState.eIsProxy()) {
			InternalEObject oldFromState = (InternalEObject)fromState;
			fromState = (State)eResolveProxy(oldFromState);
			if (fromState != oldFromState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StatesPackage.STATE_TRANSITION__FROM_STATE, oldFromState, fromState));
			}
		}
		return fromState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetFromState() {
		return fromState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFromState(State newFromState) {
		State oldFromState = fromState;
		fromState = newFromState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_TRANSITION__FROM_STATE, oldFromState, fromState));
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = new BasicEList<ModelError>();
		// if valid Self Transition
		if (!isValidSelfTransition(this)){
			List<Object> argList = new ArrayList<Object>();
			argList.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "StateMachine.errors.selfTransition", argList, false);
			errorList.add(me);
		}
		return errorList;
	}
	
	
	/**
	 * @generated NOT
	 * @param st
	 * @return
	 */
	protected boolean isValidSelfTransition(StateTransition st) {
		if(st.getFromState() == st.getToState()){
			if (st.isLambda()) {
				return false;
			}
			Rule r = st.getGuardRule();
			if (r == null) {
				return false;
			}
			String c = r.getConditionText();
			if(CommonValidationUtils.isStringEmpty(c)) 
				return false;
		}
		return true;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getGuardRule() {
		if (guardRule == null) {
			guardRule = createRule(getOwnerStateMachine().getName() + "_" + getName());
		}
		return guardRule;
	}

	protected Rule createRule(String ruleName) {
		Rule rule = RuleFactory.eINSTANCE.createRule();
		rule.setName(ruleName);
		rule.setFolder(getOwnerStateMachine().getFolder());
		
		String ownerConceptPath = getOwnerStateMachine().getOwnerConceptPath();
		if (ownerConceptPath != null) {
			int idx = ownerConceptPath.lastIndexOf('/');
			String conceptName = ownerConceptPath;
			if (idx != -1) {
				conceptName = ownerConceptPath.substring(idx+1);
			}
			Symbol s = RuleFactory.eINSTANCE.createSymbol();
			s.setIdName(conceptName.toLowerCase());
			s.setType(ownerConceptPath);
			rule.getSymbols().getSymbolMap().put(s.getIdName(),s);
		}
//		Concept concept = CommonIndexUtils.getConcept(getOwnerStateMachine().getOwnerProjectName(), getOwnerStateMachine().getOwnerConceptPath());
//		if (concept != null) {
//			Symbol s = RuleFactory.eINSTANCE.createSymbol();
//			s.setIdName(concept.getName().toLowerCase());
//			s.setType(concept.getFullPath());
//			rule.getSymbols().getSymbolMap().put(s.getIdName(),s);
//		}

		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGuardRule(Rule newGuardRule, NotificationChain msgs) {
		Rule oldGuardRule = guardRule;
		guardRule = newGuardRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_TRANSITION__GUARD_RULE, oldGuardRule, newGuardRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuardRule(Rule newGuardRule) {
		if (newGuardRule != guardRule) {
			NotificationChain msgs = null;
			if (guardRule != null)
				msgs = ((InternalEObject)guardRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE_TRANSITION__GUARD_RULE, null, msgs);
			if (newGuardRule != null)
				msgs = ((InternalEObject)newGuardRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE_TRANSITION__GUARD_RULE, null, msgs);
			msgs = basicSetGuardRule(newGuardRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_TRANSITION__GUARD_RULE, newGuardRule, newGuardRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLambda() {
		return lambda;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLambda(boolean newLambda) {
		boolean oldLambda = lambda;
		lambda = newLambda;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_TRANSITION__LAMBDA, oldLambda, lambda));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFwdCorrelates() {
		return fwdCorrelates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFwdCorrelates(boolean newFwdCorrelates) {
		boolean oldFwdCorrelates = fwdCorrelates;
		fwdCorrelates = newFwdCorrelates;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_TRANSITION__FWD_CORRELATES, oldFwdCorrelates, fwdCorrelates));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE_TRANSITION__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StatesPackage.STATE_TRANSITION__GUARD_RULE:
				return basicSetGuardRule(null, msgs);
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
			case StatesPackage.STATE_TRANSITION__TO_STATE:
				if (resolve) return getToState();
				return basicGetToState();
			case StatesPackage.STATE_TRANSITION__FROM_STATE:
				if (resolve) return getFromState();
				return basicGetFromState();
			case StatesPackage.STATE_TRANSITION__GUARD_RULE:
				return getGuardRule();
			case StatesPackage.STATE_TRANSITION__LAMBDA:
				return isLambda();
			case StatesPackage.STATE_TRANSITION__FWD_CORRELATES:
				return isFwdCorrelates();
			case StatesPackage.STATE_TRANSITION__LABEL:
				return getLabel();
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
			case StatesPackage.STATE_TRANSITION__TO_STATE:
				setToState((State)newValue);
				return;
			case StatesPackage.STATE_TRANSITION__FROM_STATE:
				setFromState((State)newValue);
				return;
			case StatesPackage.STATE_TRANSITION__GUARD_RULE:
				setGuardRule((Rule)newValue);
				return;
			case StatesPackage.STATE_TRANSITION__LAMBDA:
				setLambda((Boolean)newValue);
				return;
			case StatesPackage.STATE_TRANSITION__FWD_CORRELATES:
				setFwdCorrelates((Boolean)newValue);
				return;
			case StatesPackage.STATE_TRANSITION__LABEL:
				setLabel((String)newValue);
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
			case StatesPackage.STATE_TRANSITION__TO_STATE:
				setToState((State)null);
				return;
			case StatesPackage.STATE_TRANSITION__FROM_STATE:
				setFromState((State)null);
				return;
			case StatesPackage.STATE_TRANSITION__GUARD_RULE:
				setGuardRule((Rule)null);
				return;
			case StatesPackage.STATE_TRANSITION__LAMBDA:
				setLambda(LAMBDA_EDEFAULT);
				return;
			case StatesPackage.STATE_TRANSITION__FWD_CORRELATES:
				setFwdCorrelates(FWD_CORRELATES_EDEFAULT);
				return;
			case StatesPackage.STATE_TRANSITION__LABEL:
				setLabel(LABEL_EDEFAULT);
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
			case StatesPackage.STATE_TRANSITION__TO_STATE:
				return toState != null;
			case StatesPackage.STATE_TRANSITION__FROM_STATE:
				return fromState != null;
			case StatesPackage.STATE_TRANSITION__GUARD_RULE:
				return guardRule != null;
			case StatesPackage.STATE_TRANSITION__LAMBDA:
				return lambda != LAMBDA_EDEFAULT;
			case StatesPackage.STATE_TRANSITION__FWD_CORRELATES:
				return fwdCorrelates != FWD_CORRELATES_EDEFAULT;
			case StatesPackage.STATE_TRANSITION__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
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
		result.append(" (lambda: ");
		result.append(lambda);
		result.append(", fwdCorrelates: ");
		result.append(fwdCorrelates);
		result.append(", label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} //StateTransitionImpl
