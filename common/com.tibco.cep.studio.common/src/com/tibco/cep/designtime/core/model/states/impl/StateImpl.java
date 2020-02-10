/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.util.ModelNameUtil;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#isInternalTransitionEnabled <em>Internal Transition Enabled</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getEntryAction <em>Entry Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getExitAction <em>Exit Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getTimeoutAction <em>Timeout Action</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getInternalTransitionRule <em>Internal Transition Rule</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getTimeoutExpression <em>Timeout Expression</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getTimeoutStateGUID <em>Timeout State GUID</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getTimeoutTransitionGUID <em>Timeout Transition GUID</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getTimeoutPolicy <em>Timeout Policy</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl#getTimeoutState <em>Timeout State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateImpl extends StateVertexImpl implements State {
	/**
	 * The default value of the '{@link #isInternalTransitionEnabled() <em>Internal Transition Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInternalTransitionEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INTERNAL_TRANSITION_ENABLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInternalTransitionEnabled() <em>Internal Transition Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInternalTransitionEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean internalTransitionEnabled = INTERNAL_TRANSITION_ENABLED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntryAction() <em>Entry Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryAction()
	 * @generated
	 * @ordered
	 */
	protected Rule entryAction;

	/**
	 * The cached value of the '{@link #getExitAction() <em>Exit Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExitAction()
	 * @generated
	 * @ordered
	 */
	protected Rule exitAction;

	/**
	 * The cached value of the '{@link #getTimeoutAction() <em>Timeout Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutAction()
	 * @generated
	 * @ordered
	 */
	protected Rule timeoutAction;

	/**
	 * The cached value of the '{@link #getInternalTransitionRule() <em>Internal Transition Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternalTransitionRule()
	 * @generated
	 * @ordered
	 */
	protected Rule internalTransitionRule;

	/**
	 * The cached value of the '{@link #getTimeoutExpression() <em>Timeout Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutExpression()
	 * @generated
	 * @ordered
	 */
	protected RuleFunction timeoutExpression;

	/**
	 * The default value of the '{@link #getTimeoutStateGUID() <em>Timeout State GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutStateGUID()
	 * @generated
	 * @ordered
	 */
	protected static final String TIMEOUT_STATE_GUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeoutStateGUID() <em>Timeout State GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutStateGUID()
	 * @generated
	 * @ordered
	 */
	protected String timeoutStateGUID = TIMEOUT_STATE_GUID_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeoutTransitionGUID() <em>Timeout Transition GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutTransitionGUID()
	 * @generated
	 * @ordered
	 */
	protected static final String TIMEOUT_TRANSITION_GUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeoutTransitionGUID() <em>Timeout Transition GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutTransitionGUID()
	 * @generated
	 * @ordered
	 */
	protected String timeoutTransitionGUID = TIMEOUT_TRANSITION_GUID_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeoutPolicy() <em>Timeout Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutPolicy()
	 * @generated
	 * @ordered
	 */
	protected static final int TIMEOUT_POLICY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTimeoutPolicy() <em>Timeout Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutPolicy()
	 * @generated
	 * @ordered
	 */
	protected int timeoutPolicy = TIMEOUT_POLICY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTimeoutState() <em>Timeout State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeoutState()
	 * @generated
	 * @ordered
	 */
	protected State timeoutState;

    public static final String ENTRY_ACTION_RULE_TAG = "entryAction";
    public static final String EXIT_ACTION_RULE_TAG = "exitAction";
    public static final String TIMEOUT_ACTION_RULE_TAG = "timeoutAction";
    public static final String TIMEOUT_EXPRESSION_RULE_TAG = "timeoutExpression";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInternalTransitionEnabled() {
		return internalTransitionEnabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInternalTransitionEnabled(boolean newInternalTransitionEnabled) {
		boolean oldInternalTransitionEnabled = internalTransitionEnabled;
		internalTransitionEnabled = newInternalTransitionEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__INTERNAL_TRANSITION_ENABLED, oldInternalTransitionEnabled, internalTransitionEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getEntryAction() {
		if (entryAction == null) {
			String ruleName = ModelNameUtil.getStateRuleName(this, ENTRY_ACTION_RULE_TAG);
			entryAction = createRule(ruleName);
		}
		return entryAction;
	}

	private String[] splitParent() {
		StateMachine sm = getOwnerStateMachine();
		if (sm == null) {
			return null;
		}
		String path = sm.getOwnerConceptPath();
		if (path == null) {
			return null;
		}
		int idx = path.lastIndexOf("/");
		if(idx < 0) {
			return new String[]{"", path};
		} else {
			return new String[]{path.substring(0,idx), path.substring(idx+1)};
		}
	}
	
	private Symbol newParentSymbol() {
		StateMachine sm = getOwnerStateMachine();
		if (sm == null) {
			return null;
		}
		String path = sm.getOwnerConceptPath();
		if(path != null && path.length() > 0) {
			Symbol s = RuleFactory.eINSTANCE.createSymbol();
			String[] arr = splitParent();
			if (arr != null && arr.length > 1) {
				s.setIdName(arr[1].toLowerCase());
			}
			s.setType(path);
			return s;
		}
		return null;
	}
	
	private void putParentSymbol(Map<String, Symbol> map) {
		if(map != null) {
			Symbol s = newParentSymbol();
			if(s != null) {
				map.put(s.getIdName(), s);
			}
		}
	}
	
	protected Rule createRule(String ruleName) {
		Rule rule = RuleFactory.eINSTANCE.createRule();
		rule.setName(ruleName);
		rule.setFolder(getOwnerStateMachineFolder());
		putParentSymbol(rule.getSymbols().getSymbolMap());
		return rule;
	}

	protected String getOwnerStateMachineFolder() {
		String ownerPath = getOwnerStateMachinePath();
		int idx = ownerPath == null ? -1 : ownerPath.lastIndexOf('/');
		if (idx != -1) {
			ownerPath = ownerPath.substring(0, idx);
		}
		return ownerPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntryAction(Rule newEntryAction, NotificationChain msgs) {
		Rule oldEntryAction = entryAction;
		entryAction = newEntryAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__ENTRY_ACTION, oldEntryAction, newEntryAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryAction(Rule newEntryAction) {
		if (newEntryAction != entryAction) {
			NotificationChain msgs = null;
			if (entryAction != null)
				msgs = ((InternalEObject)entryAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__ENTRY_ACTION, null, msgs);
			if (newEntryAction != null)
				msgs = ((InternalEObject)newEntryAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__ENTRY_ACTION, null, msgs);
			msgs = basicSetEntryAction(newEntryAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__ENTRY_ACTION, newEntryAction, newEntryAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getExitAction() {
		if (exitAction == null) {
			String ruleName = ModelNameUtil.getStateRuleName(this, EXIT_ACTION_RULE_TAG);
			exitAction = createRule(ruleName);
		}
		if (exitAction != null) {
			// CR BE-9589 - the exit action name was set incorrectly in 4.0 GA, need to check if it ends with "entryAction" before creating adapter
			String name = exitAction.getName();
			int idx = name.lastIndexOf("_"+StatesPackage.eINSTANCE.getState_EntryAction().getName());
			if (idx >= 0) {
				String newName = name.substring(0, idx);
				newName += "_" + StatesPackage.eINSTANCE.getState_ExitAction().getName();
				exitAction.setName(newName);
			}
		}

		return exitAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExitAction(Rule newExitAction, NotificationChain msgs) {
		Rule oldExitAction = exitAction;
		exitAction = newExitAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__EXIT_ACTION, oldExitAction, newExitAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExitAction(Rule newExitAction) {
		if (newExitAction != exitAction) {
			NotificationChain msgs = null;
			if (exitAction != null)
				msgs = ((InternalEObject)exitAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__EXIT_ACTION, null, msgs);
			if (newExitAction != null)
				msgs = ((InternalEObject)newExitAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__EXIT_ACTION, null, msgs);
			msgs = basicSetExitAction(newExitAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__EXIT_ACTION, newExitAction, newExitAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule getTimeoutAction() {
		if (timeoutAction == null) {
			String ruleName = ModelNameUtil.getStateRuleName(this, TIMEOUT_ACTION_RULE_TAG);
			timeoutAction = createRule(ruleName);
		}
		return timeoutAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTimeoutAction(Rule newTimeoutAction, NotificationChain msgs) {
		Rule oldTimeoutAction = timeoutAction;
		timeoutAction = newTimeoutAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__TIMEOUT_ACTION, oldTimeoutAction, newTimeoutAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeoutAction(Rule newTimeoutAction) {
		if (newTimeoutAction != timeoutAction) {
			NotificationChain msgs = null;
			if (timeoutAction != null)
				msgs = ((InternalEObject)timeoutAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__TIMEOUT_ACTION, null, msgs);
			if (newTimeoutAction != null)
				msgs = ((InternalEObject)newTimeoutAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__TIMEOUT_ACTION, null, msgs);
			msgs = basicSetTimeoutAction(newTimeoutAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__TIMEOUT_ACTION, newTimeoutAction, newTimeoutAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getInternalTransitionRule() {
		return internalTransitionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInternalTransitionRule(Rule newInternalTransitionRule, NotificationChain msgs) {
		Rule oldInternalTransitionRule = internalTransitionRule;
		internalTransitionRule = newInternalTransitionRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__INTERNAL_TRANSITION_RULE, oldInternalTransitionRule, newInternalTransitionRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInternalTransitionRule(Rule newInternalTransitionRule) {
		if (newInternalTransitionRule != internalTransitionRule) {
			NotificationChain msgs = null;
			if (internalTransitionRule != null)
				msgs = ((InternalEObject)internalTransitionRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__INTERNAL_TRANSITION_RULE, null, msgs);
			if (newInternalTransitionRule != null)
				msgs = ((InternalEObject)newInternalTransitionRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__INTERNAL_TRANSITION_RULE, null, msgs);
			msgs = basicSetInternalTransitionRule(newInternalTransitionRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__INTERNAL_TRANSITION_RULE, newInternalTransitionRule, newInternalTransitionRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public RuleFunction getTimeoutExpression() {
		if (timeoutExpression == null) {
			timeoutExpression = createTimeoutExpression();
		}
		if (timeoutExpression != null && RDFTypes.INTEGER.getName().equals(timeoutExpression.getReturnType())) {
			timeoutExpression.setReturnType(RDFTypes.LONG.getName());
		}
		return timeoutExpression;
	}

	private RuleFunction createTimeoutExpression() {
		String sm = getOwnerStateMachinePath();
		if (sm == null) {
			return null;
		}

		String[] splits = splitParent();
		if (splits == null || splits.length < 2) {
			return null;
		}
		String conceptName = splits[1];
		String folder = splits[0];

		com.tibco.cep.designtime.core.model.rule.RuleFunction rf = RuleFactory.eINSTANCE.createRuleFunction();
		rf.setFolder(folder);
		rf.setName(conceptName);

		String body = "return " + getTimeout() + ";"; // for backwards compatibility
		rf.setActionText(body);
		rf.setValidity(Validity.CONDITION);
		rf.setReturnType(RDFTypes.LONG.getName());

    	putParentSymbol(rf.getSymbols().getSymbolMap());
    	return rf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTimeoutExpression(RuleFunction newTimeoutExpression, NotificationChain msgs) {
		RuleFunction oldTimeoutExpression = timeoutExpression;
		timeoutExpression = newTimeoutExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__TIMEOUT_EXPRESSION, oldTimeoutExpression, newTimeoutExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeoutExpression(RuleFunction newTimeoutExpression) {
		if (newTimeoutExpression != timeoutExpression) {
			NotificationChain msgs = null;
			if (timeoutExpression != null)
				msgs = ((InternalEObject)timeoutExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__TIMEOUT_EXPRESSION, null, msgs);
			if (newTimeoutExpression != null)
				msgs = ((InternalEObject)newTimeoutExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatesPackage.STATE__TIMEOUT_EXPRESSION, null, msgs);
			msgs = basicSetTimeoutExpression(newTimeoutExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__TIMEOUT_EXPRESSION, newTimeoutExpression, newTimeoutExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimeoutStateGUID() {
		return timeoutStateGUID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeoutStateGUID(String newTimeoutStateGUID) {
		String oldTimeoutStateGUID = timeoutStateGUID;
		timeoutStateGUID = newTimeoutStateGUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__TIMEOUT_STATE_GUID, oldTimeoutStateGUID, timeoutStateGUID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimeoutTransitionGUID() {
		return timeoutTransitionGUID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeoutTransitionGUID(String newTimeoutTransitionGUID) {
		String oldTimeoutTransitionGUID = timeoutTransitionGUID;
		timeoutTransitionGUID = newTimeoutTransitionGUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__TIMEOUT_TRANSITION_GUID, oldTimeoutTransitionGUID, timeoutTransitionGUID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTimeoutPolicy() {
		return timeoutPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeoutPolicy(int newTimeoutPolicy) {
		int oldTimeoutPolicy = timeoutPolicy;
		timeoutPolicy = newTimeoutPolicy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__TIMEOUT_POLICY, oldTimeoutPolicy, timeoutPolicy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public State getTimeoutState() {
		if (timeoutState != null) {
			return timeoutState;
		}
		return getTimeoutState_();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetTimeoutState() {
		return timeoutState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setTimeoutState(State newTimeoutState) {
		setTimeoutState_(newTimeoutState);
		State oldTimeoutState = timeoutState;
		timeoutState = newTimeoutState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatesPackage.STATE__TIMEOUT_STATE, oldTimeoutState, timeoutState));
	}

	/**
	 * getTimeoutState
	 * @return
	 */
	public State getTimeoutState_() {
		String guid = getTimeoutStateGUID();
		if(getOwnerStateMachine()!=null){
		EList<StateEntity> states = getOwnerStateMachine().getStateEntities();
        final State found = getStateByGUID(states, guid);
        if (null != found) {
//        	return new StateAdapter<State>(found, emfOntology);
        	return found;
        }

        // reverse compatibility
        if (ModelUtils.IsEmptyString(getTimeoutTransitionGUID())) {
            return null;
        }

        List<StateTransition> transitions = getOwnerStateMachine().getStateTransitions();
        if (transitions == null) {
            return null;
        }//endif
        
        Iterator<StateTransition> transitionIterator = transitions.iterator();
        while (transitionIterator.hasNext()) {
            StateTransition transition = (StateTransition) transitionIterator.next();
            if (transition.getGUID().equals(guid)) {
                State toState = transition.getToState();
                setTimeoutStateGUID(toState.getGUID());
                return toState;
//                return new StateAdapter<State>(toState, emfOntology);
            }//endif
        }//endwhile
//		return timeoutState;
		}
		return null;
	}
	/**
	 * gets state by GUID
	 * @param statesToVisit
	 * @param guid
	 * @return
	 */
	private static State getStateByGUID(
            List<StateEntity> statesToVisit,
            String guid) {
        return getStateByGUID(statesToVisit, new LinkedList<StateEntity>(), guid);
    }

	/**
	 * gets state by GUID
	 * @param statesToVisit
	 * @param statesVisited
	 * @param guid
	 * @return
	 */
    private static State getStateByGUID(
            List<StateEntity> statesToVisit,
            List<StateEntity> statesVisited,
            String guid) {

        if (null != statesToVisit) {
            for (Object stateObj : statesToVisit) {
                State state = (State) stateObj;
                if (null != state) {
                    if (!statesVisited.contains(state)) {
                        statesVisited.add(state);
                        if (state.getGUID().equals(guid)) {
                            return state;
                        }
                        if (state instanceof StateComposite) {
                            state = getStateByGUID(((StateComposite) state).getStateEntities(), statesVisited, guid);
                            if (null != state) {
                                return state;
                            }
                        }//else
                    }//if
                }//if
            }//for
        }//if
        return null;
    }

	/**
     * set timeout state
     * @param newTimeoutState
     */
	public void setTimeoutState_(State newTimeoutState) {		
		if (newTimeoutState != null) {
			setTimeoutStateGUID(newTimeoutState.getGUID());
        } else {
        	setTimeoutStateGUID(null);
        }
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StatesPackage.STATE__ENTRY_ACTION:
				return basicSetEntryAction(null, msgs);
			case StatesPackage.STATE__EXIT_ACTION:
				return basicSetExitAction(null, msgs);
			case StatesPackage.STATE__TIMEOUT_ACTION:
				return basicSetTimeoutAction(null, msgs);
			case StatesPackage.STATE__INTERNAL_TRANSITION_RULE:
				return basicSetInternalTransitionRule(null, msgs);
			case StatesPackage.STATE__TIMEOUT_EXPRESSION:
				return basicSetTimeoutExpression(null, msgs);
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
			case StatesPackage.STATE__INTERNAL_TRANSITION_ENABLED:
				return isInternalTransitionEnabled();
			case StatesPackage.STATE__ENTRY_ACTION:
				return getEntryAction();
			case StatesPackage.STATE__EXIT_ACTION:
				return getExitAction();
			case StatesPackage.STATE__TIMEOUT_ACTION:
				return getTimeoutAction();
			case StatesPackage.STATE__INTERNAL_TRANSITION_RULE:
				return getInternalTransitionRule();
			case StatesPackage.STATE__TIMEOUT_EXPRESSION:
				return getTimeoutExpression();
			case StatesPackage.STATE__TIMEOUT_STATE_GUID:
				return getTimeoutStateGUID();
			case StatesPackage.STATE__TIMEOUT_TRANSITION_GUID:
				return getTimeoutTransitionGUID();
			case StatesPackage.STATE__TIMEOUT_POLICY:
				return getTimeoutPolicy();
			case StatesPackage.STATE__TIMEOUT_STATE:
				if (resolve) return getTimeoutState();
				return basicGetTimeoutState();
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
			case StatesPackage.STATE__INTERNAL_TRANSITION_ENABLED:
				setInternalTransitionEnabled((Boolean)newValue);
				return;
			case StatesPackage.STATE__ENTRY_ACTION:
				setEntryAction((Rule)newValue);
				return;
			case StatesPackage.STATE__EXIT_ACTION:
				setExitAction((Rule)newValue);
				return;
			case StatesPackage.STATE__TIMEOUT_ACTION:
				setTimeoutAction((Rule)newValue);
				return;
			case StatesPackage.STATE__INTERNAL_TRANSITION_RULE:
				setInternalTransitionRule((Rule)newValue);
				return;
			case StatesPackage.STATE__TIMEOUT_EXPRESSION:
				setTimeoutExpression((RuleFunction)newValue);
				return;
			case StatesPackage.STATE__TIMEOUT_STATE_GUID:
				setTimeoutStateGUID((String)newValue);
				return;
			case StatesPackage.STATE__TIMEOUT_TRANSITION_GUID:
				setTimeoutTransitionGUID((String)newValue);
				return;
			case StatesPackage.STATE__TIMEOUT_POLICY:
				setTimeoutPolicy((Integer)newValue);
				return;
			case StatesPackage.STATE__TIMEOUT_STATE:
				setTimeoutState((State)newValue);
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
			case StatesPackage.STATE__INTERNAL_TRANSITION_ENABLED:
				setInternalTransitionEnabled(INTERNAL_TRANSITION_ENABLED_EDEFAULT);
				return;
			case StatesPackage.STATE__ENTRY_ACTION:
				setEntryAction((Rule)null);
				return;
			case StatesPackage.STATE__EXIT_ACTION:
				setExitAction((Rule)null);
				return;
			case StatesPackage.STATE__TIMEOUT_ACTION:
				setTimeoutAction((Rule)null);
				return;
			case StatesPackage.STATE__INTERNAL_TRANSITION_RULE:
				setInternalTransitionRule((Rule)null);
				return;
			case StatesPackage.STATE__TIMEOUT_EXPRESSION:
				setTimeoutExpression((RuleFunction)null);
				return;
			case StatesPackage.STATE__TIMEOUT_STATE_GUID:
				setTimeoutStateGUID(TIMEOUT_STATE_GUID_EDEFAULT);
				return;
			case StatesPackage.STATE__TIMEOUT_TRANSITION_GUID:
				setTimeoutTransitionGUID(TIMEOUT_TRANSITION_GUID_EDEFAULT);
				return;
			case StatesPackage.STATE__TIMEOUT_POLICY:
				setTimeoutPolicy(TIMEOUT_POLICY_EDEFAULT);
				return;
			case StatesPackage.STATE__TIMEOUT_STATE:
				setTimeoutState((State)null);
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
			case StatesPackage.STATE__INTERNAL_TRANSITION_ENABLED:
				return internalTransitionEnabled != INTERNAL_TRANSITION_ENABLED_EDEFAULT;
			case StatesPackage.STATE__ENTRY_ACTION:
				return entryAction != null;
			case StatesPackage.STATE__EXIT_ACTION:
				return exitAction != null;
			case StatesPackage.STATE__TIMEOUT_ACTION:
				return timeoutAction != null;
			case StatesPackage.STATE__INTERNAL_TRANSITION_RULE:
				return internalTransitionRule != null;
			case StatesPackage.STATE__TIMEOUT_EXPRESSION:
				return timeoutExpression != null;
			case StatesPackage.STATE__TIMEOUT_STATE_GUID:
				return TIMEOUT_STATE_GUID_EDEFAULT == null ? timeoutStateGUID != null : !TIMEOUT_STATE_GUID_EDEFAULT.equals(timeoutStateGUID);
			case StatesPackage.STATE__TIMEOUT_TRANSITION_GUID:
				return TIMEOUT_TRANSITION_GUID_EDEFAULT == null ? timeoutTransitionGUID != null : !TIMEOUT_TRANSITION_GUID_EDEFAULT.equals(timeoutTransitionGUID);
			case StatesPackage.STATE__TIMEOUT_POLICY:
				return timeoutPolicy != TIMEOUT_POLICY_EDEFAULT;
			case StatesPackage.STATE__TIMEOUT_STATE:
				return timeoutState != null;
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
		result.append(" (internalTransitionEnabled: ");
		result.append(internalTransitionEnabled);
		result.append(", timeoutStateGUID: ");
		result.append(timeoutStateGUID);
		result.append(", timeoutTransitionGUID: ");
		result.append(timeoutTransitionGUID);
		result.append(", timeoutPolicy: ");
		result.append(timeoutPolicy);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = super.getModelErrors();
		// if it has multiple lambdas
		if (hasMultipleLambdas()) {
			List<Object> argList = new ArrayList<Object>();
			argList.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "State.errors.multipleLambdas", argList, true);
			errorList.add(me);
		}
		if (isTimeOutStateMissing()) {		
			List<Object> argList = new ArrayList<Object>();
			argList.add(this.getName());
			ModelError me = CommonValidationUtils.constructModelError(this, "State.errors.timeoutStateMissing", argList, false);		
			errorList.add(me);
		}
		
		
		return errorList;
	}
	/**
	 * if there are more than one Transition without any condition
	 */
	private boolean hasMultipleLambdas() {
//      BE-9587: (regression)new studio project:state machine:on creating mutiple transitions 
//		originating from same state, eclispe should throw error 
//		"a mutable state should not have multiple lambda/empty conditions originating from it". 
		
//		if (this instanceof StateStart) {
//			return false;
//		}
		
		// get all out going Transitions from this state
		List<StateTransition> stList = this.getOutgoingTransitions();
		if (stList == null) {
			return false;
		}
		if(stList.size() == 1) {
			return false;
		}
		boolean foundOne = false;
		for (StateTransition st : stList) {	
			boolean islambda = isLambda(st);
			if (islambda) {
				if (foundOne) {
					return true;
				} else {
					foundOne = true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param st
	 * @return
	 */
	protected boolean isLambda(StateTransition st) {
	    if (st.isLambda()) {
	        return true;
	    }

        Rule r = st.getGuardRule();
        if (r == null) {
            return true;
        }

        String c = r.getConditionText();
        return CommonValidationUtils.isStringEmpty(c);
    }
	
	public boolean isTimeOutStateMissing(){
		int timeOutPolicy = getTimeoutPolicy();
		if (timeOutPolicy == STATE_TIMEOUT_POLICY.DETERMINISTIC_STATE_POLICY_VALUE){
			State timeOutState = getTimeoutState();
			if (timeOutState == null){
				return true;
			}
			
		}
		return false;
	}
} //StateImpl