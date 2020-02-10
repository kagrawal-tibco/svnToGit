package com.tibco.cep.studio.core.adapters.mutable;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.core.adapters.StateAdapter;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class MutableStateAdapter<S extends State> extends StateAdapter<S> implements com.tibco.cep.designtime.model.element.stategraph.State {
//	protected static final String UNKNOWN_ERROR = "DefaultStateEntity.unknownError";
//	protected static final String END_STATE_CANNOT_HAVE_EXITING_TRANSITIONS = "DefaultStateEntity.canStart.endStateCannotHaveExitingTransitions";
//	// All actions appropriate for canStart are listed here
//	protected static final Integer CREATE_TRANSITION_ACTION = new Integer(1);
//	protected Rectangle m_bounds = new Rectangle(0, 0, 0, 0);
//	protected StateMachine m_ownerStateMachine = null;
//	protected StateComposite m_parent = null;
//	protected int m_timeout = 0;
//	protected int m_timeoutUnits = com.tibco.cep.designtime.model.event.Event.SECONDS_UNITS;
//
//	public static final String PERSISTENCE_NAME = "State";
	protected static final String TIMEOUT_STATE_GUID_TAG = "timeoutStateGUID";
	protected static final String TIMEOUT_STATE_TAG = "timeoutState";
	protected static final String TIMEOUT_POLICY_TAG = "timeoutPolicy";

	public static final String ENTRY_ACTION_RULE_TAG = "entryAction";
	public static final String EXIT_ACTION_RULE_TAG = "exitAction";
	public static final String TIMEOUT_ACTION_RULE_TAG = "timeoutAction";
	public static final String TIMEOUT_EXPRESSION_RULE_TAG = "timeoutExpression";

	protected static final String IS_INTERNAL_TRANSITION_ENABLED_TAG = "isInternalTransitionEnabled";
	public static final String INTERNAL_TRANSITION_RULE_TAG = "internalTransition";
//
//
//	protected boolean m_isInternalTransitionEnabled = false;
//	protected Rule m_entryAction = null;
//	protected Rule m_exitAction = null;
//	protected Rule m_timeoutAction = null;
//	protected Rule m_internalTransitionRule = null;
//	protected CGMutableRuleFunctionAdapter m_timeoutExpression = null;
//
//	protected String m_timeoutStateGUID = "";
//
//	// kept for reverse compatibility
//	protected String m_timeoutTransitionGUID = null;
//
//	protected int m_timeoutPolicy = NO_ACTION_TIMEOUT_POLICY;
//
//	public static final String MULTIPLE_LAMBDAS_ERROR = "DefaultState.getModelErrors.multipleLambdas";
//	public static final String TIMEOUT_STATE_MISSING = "DefaultState.getModelErrors.timeoutStateMissing";
	
	public MutableStateAdapter(S adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
		adapted.setTimeoutPolicy(NO_ACTION_TIMEOUT_POLICY);
		adapted.setOwnerStateMachine(null);
		adapted.setEntryAction(null);
		adapted.setExitAction(null);
		adapted.setInternalTransitionRule(null);
		adapted.setTimeoutExpression(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Construct this object within the passed ontology and give it the name
	 * passed.
	 * 
	 * @param ontology
	 *            The ontology (BE model) to add this entity.
	 * @param name
	 *            The name of this element.
	 * @param bounds
	 *            The location of the entity on the main view (only top-left is
	 *            used for iconic entities).
	 * @param ownerStateMachine
	 *            The state machine that owns this state.
	 * @param entryAction
	 *            The action to perform on entry to this state.
	 * @param exitAction
	 *            The action to perform on exit from this state.
	 * @param internalTransitionRule
	 *            The internal transition (if any) for this state.
	 */
	public MutableStateAdapter(Ontology ontology, String name,
			Rectangle bounds, StateMachine ownerStateMachine, com.tibco.cep.designtime.model.rule.Rule entryAction,
			com.tibco.cep.designtime.model.rule.Rule exitAction, com.tibco.cep.designtime.model.rule.Rule internalTransitionRule) {
		this(ontology, name, bounds, ownerStateMachine);
		Rule _entryAction = (Rule) CommonIndexUtils.getEntity(ontology.getName(),entryAction.getFullPath(),ELEMENT_TYPES.RULE);
		adapted.setEntryAction(_entryAction);
		Rule _exitAction = (Rule) CommonIndexUtils.getEntity(ontology.getName(),exitAction.getFullPath(),ELEMENT_TYPES.RULE);
		adapted.setExitAction(_exitAction);
		Rule _internalTran = (Rule) CommonIndexUtils.getEntity(ontology.getName(),internalTransitionRule.getFullPath(),ELEMENT_TYPES.RULE);
		adapted.setInternalTransitionRule(_internalTran);
//		m_entryAction = entryAction;
//		m_exitAction = exitAction;
//		m_internalTransitionRule = internalTransitionRule;
	}// end DefaultState

	

	/**
	 * Construct this object within the passed ontology and give it the name
	 * passed.
	 * 
	 * @param ontology
	 *            The ontology (BE model) to add this entity.
	 * @param name
	 *            The name of this element.
	 * @param bounds
	 *            The location of the entity on the main view (only top-left is
	 *            used for iconic entities).
	 */
	public MutableStateAdapter(Ontology ontology, String name,
			Rectangle bounds, StateMachine ownerStateMachine) {
		this(ontology, name, null, bounds, ownerStateMachine);
	}// end DefaultStateEntity

	/**
	 * Construct this object within the passed ontology and give it the name
	 * passed.
	 * 
	 * @param ontology
	 *            The ontology (BE model) to add this entity.
	 * @param name
	 *            The name of this element.
	 * @param folder
	 *            MutableFolder
	 * @param bounds
	 *            The location of the entity on the main view (only top-left is
	 *            used for iconic entities).
	 * @param ownerStateMachine
	 *            MutableStateMachine
	 */
	protected MutableStateAdapter(Ontology ontology, String name,
			Folder folder, Rectangle bounds, StateMachine ownerStateMachine) {
		this((S) StatesFactory.eINSTANCE.createState(),ontology);
//		super(ontology, folder, name);
//		if (bounds == null) {
//			m_bounds = new Rectangle(0, 0, 0, 0);
//		} else {
//			m_bounds = bounds;
//		}// endif
		StateMachine sm = (StateMachine) CommonIndexUtils.getEntity(ontology.getName(),ownerStateMachine.getFullPath(),ELEMENT_TYPES.STATE_MACHINE);
		adapted.setOwnerStateMachine(sm);
//		m_ownerStateMachine = ownerStateMachine;
	}// end DefaultStateEntity

	/**
	 * Can the object passed be added to this entity.
	 * 
	 * @param addObject
	 *            The Object to add to this entity. The Object can be a List,
	 *            StateEntity or certain subclasses based on the implementing
	 *            object.
	 * @return null if the Object can be added to this object, otherwise return
	 *         a string describing the error.
	 */
//	public String canAdd(Object addObject) {
//		String canAdd = null;
//		if (addObject instanceof List) {
//			// Trying to add several objects to this entity
//			List objects = (List) addObject;
//			Iterator objectIterator = objects.iterator();
//			while (objectIterator.hasNext() && canAdd == null) {
//				canAdd = canAddOne((StateEntity) objectIterator.next());
//			}// endwhile
//		} else {
//			canAdd = canAddOne((StateEntity) addObject);
//		}// endif
//		return canAdd;
//	}// end canAdd

	/**
	 * Can the single object passed be added to this entity.
	 * 
	 * @param addObject
	 *            The Object to add to this entity. The Object can only be a
	 *            single model object (not a List).
	 * @return true if the Object can be added to this object, otherwise return
	 *         false.
	 */
//	protected String canAddOne(StateEntity addObject) {
//		return BEModelBundle.getBundle().getString(UNKNOWN_ERROR);
//	}// end canAddOne

	/**
	 * Can the object passed be deleted from this entity.
	 * 
	 * @param deleteObject
	 *            The Object to delete from this entity. The Object can be a
	 *            List, StateEntity or certain subclasses based on the
	 *            implementing object.
	 * @return null if the Object can be added to this object, otherwise return
	 *         a string describing the error.
	 */
//	public String canDelete(Object deleteObject) {
//		String canDelete = null;
//		if (deleteObject instanceof List) {
//			// Trying to delete several objects from this entity
//			List objects = (List) deleteObject;
//			Iterator objectIterator = objects.iterator();
//			while (objectIterator.hasNext() && canDelete == null) {
//				canDelete = canDeleteOne((StateEntity) objectIterator.next());
//			}// endwhile
//		} else {
//			canDelete = canDeleteOne((StateEntity) deleteObject);
//		}// endif
//		return canDelete;
//	}// end canDelete

	/**
	 * Can the single object passed be deleted from this entity.
	 * 
	 * @param deleteObject
	 *            The Object to delete from this entity. The Object can only be
	 *            a single model object (not a List).
	 * @return null if the Object can be added to this object, otherwise return
	 *         a string describing the error.
	 */
//	protected String canDeleteOne(StateEntity deleteObject) {
//		return BEModelBundle.getBundle().getString(UNKNOWN_ERROR);
//	}// end canDeleteOne

	/**
	 * Can the object passed act as a start for an action.
	 * 
	 * @param startObject
	 *            The Object to test if it can start the requested action. The
	 *            Object can be a List, StateEntity or certain subclasses based
	 *            on the implementing object.
	 * @param actionType
	 *            The type of action to start.
	 * @return null if the Object can be added to this object, otherwise return
	 *         a string describing the error.
	 */
//	public String canStart(Object startObject, Object actionType) {
//		String result = null;
//		if (actionType.equals(CREATE_TRANSITION_ACTION)) {
//			if (startObject instanceof StateEnd) {
//				result = BEModelBundle.getBundle().getString(
//						END_STATE_CANNOT_HAVE_EXITING_TRANSITIONS);
//			}// endif
//		}// endif
//		return result;
//	}// end canStart

	

	
	public void setTimeoutPolicy(int flag) {
		if (flag < NO_ACTION_TIMEOUT_POLICY
				|| flag > DETERMINISTIC_STATE_POLICY) {
			flag = NO_ACTION_TIMEOUT_POLICY;
		}// endif
		adapted.setTimeoutPolicy(flag);
//		m_timeoutPolicy = flag;
	}// end setTimeoutPolicy

	@Override
	public com.tibco.cep.designtime.model.element.stategraph.State getTimeoutState() {
		// TODO expand for other state machines
		List states = getOwnerStateMachine().getMachineRoot().getEntities();
		final com.tibco.cep.designtime.model.element.stategraph.State found = getStateByGUID(states, adapted.getTimeoutStateGUID());
		if (null != found) {
			return found;
		}

		// reverse compatibility
		if (ModelUtils.IsEmptyString(adapted.getTimeoutStateGUID())) {
			return null;
		}

		List transitions = getOwnerStateMachine().getTransitions();
		if (transitions == null) {
			return null;
		}// endif
		Iterator transitionIterator = transitions.iterator();
		while (transitionIterator.hasNext()) {
			StateTransition transition = (StateTransition) transitionIterator
					.next();
			if (transition.getGUID().equals(adapted.getTimeoutStateGUID())) {
				com.tibco.cep.designtime.model.element.stategraph.State toState = transition.getToState();
				adapted.setTimeoutStateGUID(toState.getGUID());
				return toState;
			}// endif
		}// endwhile
		return null;
	}

	private static com.tibco.cep.designtime.model.element.stategraph.State getStateByGUID(List statesToVisit, String guid) {
		return getStateByGUID(statesToVisit, new LinkedList(), guid);
	}

	private static com.tibco.cep.designtime.model.element.stategraph.State getStateByGUID(List statesToVisit, List statesVisited,
			String guid) {

		if (null != statesToVisit) {
			for (Object stateObj : statesToVisit) {
				com.tibco.cep.designtime.model.element.stategraph.State state = (com.tibco.cep.designtime.model.element.stategraph.State) stateObj;
				if (null != state) {
					if (!statesVisited.contains(state)) {
						statesVisited.add(state);
						if (state.getGUID().equals(guid)) {
							return state;
						}
						if (state instanceof com.tibco.cep.designtime.model.element.stategraph.StateComposite) {
							state = getStateByGUID(((com.tibco.cep.designtime.model.element.stategraph.StateComposite) state)
									.getEntities(), statesVisited, guid);
							if (null != state) {
								return state;
							}
						}// else
					}// if
				}// if
			}// for
		}// if
		return null;
	}

	public void setTimeoutState(State state) {
		if (state == null) {
//			m_timeoutStateGUID = null;
			adapted.setTimeoutStateGUID(null);
		} else {
//			m_timeoutStateGUID = state.getGUID();
			adapted.setTimeoutStateGUID(state.getGUID());
		}
	}// end setTimeoutState

	/**
	 * Delete the internal transition on this state (if any).
	 */
//	public void deleteInternalTransition() throws ModelException {
//		deleteRule(INTERNAL_TRANSITION_RULE_TAG); // todo what value?
//	}// end deleteInternalTransition

	/**
	 * Delete the Rule identified by "qualifier" from the RuleSet contained in
	 * the Concept.
	 * 
	 * @param qualifier
	 *            String describing which Rule to delete from the Concept's
	 *            RuleSet.
	 */
//	public void deleteRule(String qualifier) throws ModelException {
//		String ruleName = getRuleName(qualifier);
//		final CGMutableRuleSetAdapter conceptRuleSet = (CGMutableRuleSetAdapter) this.getRuleSet();
//		conceptRuleSet.deleteRule(ruleName);
//	}// end deleteRuleFromConcept
	
	 /**
     * Get the action to perform on entry to this state.
     *
     * @return The action to perform on entry to this state.
     */
    public com.tibco.cep.designtime.model.rule.Rule getEntryAction(boolean create) {
    	com.tibco.cep.designtime.model.rule.Rule rule;
    	Rule r =  adapted.getEntryAction();
        if (r == null) {
            rule = getRule(ENTRY_ACTION_RULE_TAG, create);
            
        } else {
        	
        	rule = CoreAdapterFactory.INSTANCE.createAdapter(r, emfOntology);
        }
        return rule;
//
//        return m_entryAction;
    }// end getEntryAction


    /**
     * Get the action to perform on exit from this state.
     *
     * @ return The action to perform on exit from this state.
     */
    public com.tibco.cep.designtime.model.rule.Rule getExitAction(boolean create)  {
    	com.tibco.cep.designtime.model.rule.Rule rule;
    	Rule r = adapted.getExitAction();
        if (r == null) {
            rule = getRule(EXIT_ACTION_RULE_TAG, create);
        } else {
        	rule = CoreAdapterFactory.INSTANCE.createAdapter(r, emfOntology);
        }

        return rule;
    }// end getExitAction


    public com.tibco.cep.designtime.model.rule.Rule getTimeoutAction(boolean create)  {
    	com.tibco.cep.designtime.model.rule.Rule rule;
    	Rule r = adapted.getTimeoutAction();
        if (r == null) {
            rule = getRule(TIMEOUT_ACTION_RULE_TAG, create);
        } else {
        	rule = CoreAdapterFactory.INSTANCE.createAdapter(r, emfOntology);
        }

        return rule;
    }


    public com.tibco.cep.designtime.model.rule.RuleFunction getTimeoutExpression() {
    	RuleFunction r = adapted.getTimeoutExpression();
        if (r == null) {
            StateMachine sm = adapted.getOwnerStateMachine();
            if (sm == null) {
                return null;
            }

            Concept concept = CommonIndexUtils.getConcept(emfOntology.getName(), sm.getOwnerConceptPath());
            if (concept == null) {
                return null;
            }
            com.tibco.cep.designtime.model.element.Concept conceptAdaptor = CoreAdapterFactory.INSTANCE.createAdapter(concept, emfOntology);

            String conceptName = conceptAdaptor.getName();
            
            RuleFunction rf = RuleFactory.eINSTANCE.createRuleFunction();
            rf.setFolder(concept.getFolder());
            rf.setName(concept.getName());
            

            CGMutableStateRuleFunctionAdapter m_timeoutExpression = new CGMutableStateRuleFunctionAdapter((Ontology)conceptAdaptor.getOntology(), conceptAdaptor.getFolder(), conceptAdaptor.getName());
            m_timeoutExpression.setArgumentType(conceptName.toLowerCase(), concept.getFullPath());
            m_timeoutExpression.setReturnType(RDFTypes.INTEGER.getName());
            m_timeoutExpression.setValidity(Validity.CONDITION);

            String body = "return " + adapted.getTimeout() + ";"; // for backwards compatibility
            m_timeoutExpression.setBody(body);
            return m_timeoutExpression;
        }

        return super.getTimeoutExpression();
    }
    
    public void delete() {
//        m_ownerStateMachine.deleteLinkedTransitions(this);
//        try {
//            CGMutableRuleAdapter r = (CGMutableRuleAdapter) getEntryAction(false);
//            if (r != null) {
//                deleteRule(ENTRY_ACTION_RULE_TAG);
//                r.delete();
//            }
//
//            r = (CGMutableRuleAdapter) getExitAction(false);
//            if (r != null) {
//                deleteRule(EXIT_ACTION_RULE_TAG);
//                r.delete();
//            }
//
//            r = (CGMutableRuleAdapter) getTimeoutAction(false);
//            if (r != null) {
//                deleteRule(TIMEOUT_ACTION_RULE_TAG);
//                r.delete();
//            }
//
//            r = (CGMutableRuleAdapter) getInternalTransition(false);
//            if (r != null) {
//                deleteRule(INTERNAL_TRANSITION_RULE_TAG);
//                r.delete();
//            }
//
//            final MutableRuleFunction rf = (MutableRuleFunction) getTimeoutExpression();
//            if (rf != null) {
////                deleteRuleFromConcept(TIMEOUT_EXPRESSION_RULE_TAG);
//                rf.delete();
//            }
//
//        } catch (ModelException e) {
//        }
//
//        super.delete();
    }


    public void setName(String name, boolean renameOnConflict) {
        MutableRuleAdapter internalTransitionRule = null;
        MutableRuleAdapter timeoutAction = null;
        MutableRuleAdapter entryAction = null;
        MutableRuleAdapter exitAction = null;
//        RuleFunction timeoutExpression = null;

        internalTransitionRule = (MutableRuleAdapter) getInternalTransition(false);
        timeoutAction = (MutableRuleAdapter) getTimeoutAction(false);
        entryAction = (MutableRuleAdapter) getEntryAction(false);
        exitAction = (MutableRuleAdapter) getExitAction(false);
//        timeoutExpression = getTimeoutExpression();

//        super.setName(name, renameOnConflict);
        adapted.setName(name);
         

        com.tibco.cep.designtime.model.element.stategraph.StateMachine sm = getOwnerStateMachine();
        if (sm == null) {
            return;
        }

        if (internalTransitionRule != null) {
            String ruleName = getRuleName(INTERNAL_TRANSITION_RULE_TAG);
//            internalTransitionRule.setName(ruleName, false);
            internalTransitionRule.setName(ruleName);
        }

        if (timeoutAction != null) {
            String ruleName = getRuleName(TIMEOUT_ACTION_RULE_TAG);
//            timeoutAction.setName(ruleName, false);
            timeoutAction.setName(ruleName);
        }

        if (entryAction != null) {
            String ruleName = getRuleName(ENTRY_ACTION_RULE_TAG);
//            entryAction.setName(ruleName, false);
            entryAction.setName(ruleName);
        }

        if (exitAction != null) {
            String ruleName = getRuleName(EXIT_ACTION_RULE_TAG);
//            exitAction.setName(ruleName, false);
            exitAction.setName(ruleName);
        }

//        if(timeoutExpression != null) {
//            String ruleName = getRuleName(TIMEOUT_EXPRESSION_RULE_TAG);
//            timeoutExpression.setName(ruleName, false);
//        }
    }


//    public void setOntology(Ontology ontology) {
//        super.setOntology(ontology);
//        if (m_timeoutExpression != null) {
//            m_timeoutExpression.setOntology(ontology);
//        }
//    }


    /**
     * Get the internal transition on this state (if any).
     *
     * @return The internal transition on this state (if any).
     */
    public com.tibco.cep.designtime.model.rule.Rule getInternalTransition(boolean create) {
    	com.tibco.cep.designtime.model.rule.Rule rule = null;
    	Rule r = adapted.getInternalTransitionRule();
        if (r == null) {
            rule = getRule(INTERNAL_TRANSITION_RULE_TAG, create); // todo what value?
        }

        return rule;
    }// end getInternalTransition
    
    /**
     * Get the Rule identified by "qualifier" from the RuleSet contained in the Concept.
     *
     * @param qualifier String describing which Rule to get from the Concept's RuleSet.
     * @param create    If true, create if not yet present.
     */
    public com.tibco.cep.designtime.model.rule.Rule getRule(
            String qualifier,
            boolean create)  {
    	StateMachine sm = adapted.getOwnerStateMachine();
        if (sm == null) {
            return null;
        }

        String ruleName = getRuleName(qualifier);
        
        final RuleSet ruleSet = getRuleSet();
        if (ruleSet == null) {
            return null;
        }

        com.tibco.cep.designtime.model.rule.Rule rule = (com.tibco.cep.designtime.model.rule.Rule) ruleSet.getRule(ruleName);

        if (rule == null && create) {
        	Rule emfRule = RuleFactory.eINSTANCE.createRule();
        	emfRule.setName(ruleName);
        	emfRule.setFolder(ruleSet.getFullPath());
        	emfRule.setFunction(false);
        	rule = CoreAdapterFactory.INSTANCE.createAdapter(emfRule, emfOntology);
//            rule = ruleSet.createRule(ruleName, false, false);
        }//endif
        return rule;
    }// end getRuleFromConcept


	public void enableInternalTransition(boolean enable) {
//		m_isInternalTransitionEnabled = enable;
		adapted.setInternalTransitionEnabled(enable);
	}// end enableInternalTransition

	/**
	 * Generate a rule name for this state entity.
	 */
	public String getRuleName(String qualifier) {
		return ModelNameUtil.getStateRuleName(this, qualifier);
	}// end getRuleName

	

	@Override
	public com.tibco.cep.designtime.model.element.stategraph.StateMachine getOwnerStateMachine() {
		return CoreAdapterFactory.INSTANCE.createAdapter(adapted.getOwnerStateMachine(),emfOntology);
	}

	@Override
	public com.tibco.cep.designtime.model.element.stategraph.StateEntity getParent() {
		return CoreAdapterFactory.INSTANCE.createAdapter(adapted.getParent(), emfOntology);
	}

	
	@Override
	public int getTimeoutUnits() {
		return adapted.getTimeout();
	}
	
	
	private class CGMutableStateRuleFunctionAdapter extends com.tibco.cep.studio.core.adapters.mutable.MutableRuleFunctionAdapter {

		
        public CGMutableStateRuleFunctionAdapter(com.tibco.cep.designtime.model.rule.RuleFunction rf) {
            this((Ontology)rf.getOntology(), rf.getFolder(), rf.getName());
            setReturnType(rf.getReturnType());
            setBody(rf.getActionText());
            setValidity(rf.getValidity());
            setScopeInternal(new MutableSymbolsAdapter(rf.getScope(),emfOntology));
        }


        public CGMutableStateRuleFunctionAdapter(Ontology ontology, Folder folder, String name) {
            super(ontology, folder, name);
        }

        public void delete() {
////            removeFromParticipant(m_returnType);
////
////            for (Iterator it = getArguments().values().iterator(); it.hasNext();) {
////                final Symbol symbol = (Symbol) it.next();
////                removeFromParticipant(symbol.getType());
////            }
//
//            Concept owner = (DefaultMutableConcept) getOwnerConcept();
//            if(owner != null) {
//                owner.notifyListeners();
//            }
        }//delete

        private com.tibco.cep.designtime.model.element.Concept getOwnerConcept() {
        	com.tibco.cep.designtime.model.element.stategraph.StateMachine sm = MutableStateAdapter.this.getOwnerStateMachine();
            if (sm == null) {
                return null;
            }

            return sm.getOwnerConcept();
        }


        public String getFolderPath() {
        	com.tibco.cep.designtime.model.element.Concept concept = getOwnerConcept();
            if (concept != null) {
                return concept.getFolderPath();
            }

            return super.getFolderPath();
        }


        public String getName() {
        	com.tibco.cep.designtime.model.element.Concept concept = getOwnerConcept();
            if (concept != null) {
                return concept.getName();
            }

            return super.getName();
        }


        public void setName(String name, boolean renameOnConflict) {
        	CGMutableStateRuleFunctionAdapter.this.adapted.setName(name);
        }


        public void setOntology(Ontology ontology) {
//            CGMutableStateRuleFunctionAdapter.this.adapted.setOntology(ontology);
        }


        public Symbols getArguments() {
        	com.tibco.cep.designtime.model.element.Concept concept = getOwnerConcept();
            final MutableSymbolsAdapter args =  (MutableSymbolsAdapter) super.getArguments();
            if (concept == null) {
                return args;
            }

            String path = concept.getFullPath();

            if (args.size() == 0) {
                final String name = concept.getName();
                args.put(new MutableSymbolAdapter(args, name, path));
                return args;
            }

            // update the path
            Object[] objs = args.entrySet().toArray();
            Map.Entry entry = (Map.Entry) objs[0];

            MutableSymbolAdapter symbol = (MutableSymbolAdapter) entry.getValue();
            if (null == symbol) {
                symbol = new MutableSymbolAdapter(args, (String) entry.getKey(), path);
                entry.setValue(symbol);
            } else {
                symbol.setType(path);
            }

            return args;
        }
    }

}
