package com.tibco.be.dbutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEnd;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateSubmachine;
import com.tibco.cep.designtime.model.element.stategraph.mutable.impl.MutableInternalStateTransition;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Nov 26, 2006
 * Time: 5:21:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class StateMachineHelper {
/* From 1.4 com.tibco.be.engine.model.stategraph.TransitionLink */ 
    public static final byte TRANSITION_STATUS_WAITING    =  0; // Transition idle,
    public static final byte TRANSITION_STATUS_READY       = 1; // Transition ready, and Waiting for an event
    public static final byte TRANSITION_STATUS_TIMEOUT     = 2; //Transition timedout
    public static final byte TRANSITION_STATUS_COMPLETE    = 3; //Transition completed,
    public static final byte TRANSITION_STATUS_LAMBDA      = 4; //Transition had not rule, just went thru
    public static final byte TRANSITION_STATUS_SKIPPED     = 5; //Transition had skipped
    public static final byte TRANSITION_STATUS_AMBIGUOUS    = 6; //Could not say the status of transition.
    //added by ISS
    public static final byte TRANSITION_STATUS_WILL_BE_READY    = 7; //About to become enabled (executing onEntry)
    public static final String[] STATUSES = new String[] {"WAITING", "READY", "TIMED-OUT", "COMPLETED", "LAMBDA", "SKIPPED", "INDETERMINATE", "WILL-ENABLE" };
/* end of stuff from 1.4 TransitionLink */
    
    public static boolean isStateMachineConcept(String conceptName, Ontology o) {
        return o.getConcept(conceptName) == null;
    }
    
    public static StateMachine getMainStateMachine(Concept c) {
        if(c == null) return null;
        StateMachine sm = c.getMainStateMachine();
        if(sm != null) return sm;
        return getMainStateMachine(c.getSuperConcept());
    }
    
    public static boolean isStateMachineConceptProperty(Concept owner, String smName) {
        return owner.getPropertyDefinition(smName, false) == null;
    }
    
    public static String getStateMachineConceptName(StateMachine sm) {
        return className(sm.getOwnerConcept(), sm);
    }

    public static String getStateMachinePropertyName(StateMachine sm) {
        return className(sm.getOwnerConcept(), sm);
    }
    
    public static String className (Concept cept, StateMachine sm) {
        return cept.getName() + "$" + sm.getName();
    }
    
    public static String getStateMachineNamespace(StateMachine sm) {
        Concept owner = sm.getOwnerConcept();
        return owner.getFullPath();
    }
    
    public static String getStatePropertyName(State state) {
        return getStatePath(state);
    }
    
    public static String getStatePropertyClassName(State state) {
        StateMachine sm = state.getOwnerStateMachine();
        return getStateMachineClassName(sm) + "$$1z" + getStatePropertyName(state);
    }
    
    public static String getStateMachinePropertyClassName(StateMachine sm) {
        return ModelNameUtil.modelPathToGeneratedClassName(sm.getOwnerConcept().getFullPath()) + "$" + ModelNameUtil.generatedStateMachinePropertyClassName(sm.getOwnerConcept(),sm);
    }
    public static String getStateMachineClassName(StateMachine sm) {
        return ModelNameUtil.modelPathToGeneratedClassName(sm.getOwnerConcept().getFullPath()) + "$" + sm.getOwnerConcept().getName() + ModelNameUtil.STATEMACHINE_PREFIX + sm.getName();
    }
    
    public final static boolean hasChildren(State state) {
        return ((state instanceof StateComposite) && !(state instanceof MutableStateSubmachine));
    }
    
    public static String getStatePath(State state) {
        ArrayList parents = new ArrayList();
        StringBuffer fullName= new StringBuffer();
        StateEntity tmp= state;
        while (tmp != null) {
            parents.add(tmp.getName());
            tmp=tmp.getParent();
            if (hasChildren((State) tmp)) {
                StateComposite s = (StateComposite) tmp;
                if (s.getParent() == null) {
                    break;
                }
            }
        }
        for (int i=0; i < parents.size(); i++) {
            if (i > 0) {
                fullName.append("$");
            }
            fullName.append(parents.get( parents.size() -1 - i));
        }
        return fullName.toString();
    }
    
    public static void getStateMachineStateNames(StateComposite stateComposite, List stateNames) {
        stateNames.add(getStatePath(stateComposite));
        List entities = stateComposite.getEntities();
        if(entities != null) {
            for(Iterator it = entities.iterator(); it.hasNext();) {
                State state = (State)it.next();
                if(state instanceof StateComposite) {
                    getStateMachineStateNames((StateComposite)state, stateNames);
                } else {
                    stateNames.add(getStatePropertyName(state));
                }
            }
        }
        List regions = stateComposite.getRegions();
        if(regions != null) {
            for(Iterator it = regions.iterator(); it.hasNext();) {
                State state = (StateComposite)it.next();
                getStateMachineStateNames((StateComposite)state, stateNames);
            }
        }
    }
    
    //need the context argument separate from the sm argument because of situations like
    //SuperConcept with state machines:main, sub
    //SubConcept with state machine:sub
    //the main machine may run with context of SuperConcept or SubConcept
    //and will call a different sub state machine depending on the context
    public static StateTransition[] makeTransitionsArray(Concept context, StateMachine sm) throws RuntimeException {
        if(sm == null) return null;
        LinkedHashSet states = new LinkedHashSet();
        LinkedList transitionList = new LinkedList();
        LinkedHashSet subMachines = new LinkedHashSet();
        Stack recursiveSubMachines = new Stack();
        Set endStates = new LinkedHashSet();

        addStates(context, sm.getMachineRoot(), null, states, transitionList, endStates, subMachines, recursiveSubMachines);
        
        //System.out.println("States Set " + sm.getFullPath());
        //for(Iterator it = states.iterator(); it.hasNext();) {
        //    System.out.println(it.next());
        //}
        //System.out.println("Transitions List");
        //for(Iterator it = transitionList.iterator(); it.hasNext();) {
        //    StateTransition tn = (StateTransition)it.next();
        //    State from = tn.getFromState();
        //    State to = tn.getToState();
        //    String fromName = from.getOwnerStateMachine().getName() + "/" + from.getName();
        //    String toName = to.getOwnerStateMachine().getName() + "/" + to.getName();
        //    System.out.println(fromName + " -> " + tn.getName() + " -> " + toName);
        //}
        //System.out.println("Submachines Set");
        //for(Iterator it = subMachines.iterator(); it.hasNext();) {
        //    StateComposite m = (StateComposite)it.next();
        //    System.out.println(m.getName());
        //}
        //System.out.println("Recursive Submachines Stack");
        //for(Iterator it = recursiveSubMachines.iterator(); it.hasNext();) {
        //    StateMachine m = (StateMachine)it.next();
        //    System.out.println(m.getName());
        //}
        //System.out.println("End States Set");
        //for(Iterator it = endStates.iterator(); it.hasNext();) {
        //    System.out.println(it.next());
        //}
        //System.out.println();
        return (StateTransition[])transitionList.toArray(new StateTransition[transitionList.size()]);
    }
    
    private static void addStates(Concept concept, State s, String parent, Set states, List transitions, Set endStates, Set subMachines, Stack recursiveSubMachines) {
        String name = getRuntimeStateName(s);
        boolean isComposite = false;

        if (parent == null)  { //root state
            name = "$$root";
        }

        if (s instanceof StateEnd && endStates != null && isMachineEnd((StateEnd) s)) {
            endStates.add(name);
        }
   
        else if (s instanceof StateComposite) {
            isComposite = true;
        }

        states.add(name + s.hashCode());
        
        addFromTransitions(s, transitions);

        if (isComposite) {
            addCompositeState(concept, s, name, states, transitions, endStates, subMachines, recursiveSubMachines);
        }
    }

    private static boolean isMachineEnd(StateEnd end) {
        StateMachine sm = end.getOwnerStateMachine();
        StateComposite cs = sm.getMachineRoot();
        return(cs.getEntities().contains(end));
    }

    private static void addFromTransitions(State s, List transitions) {
        try {
            if(s.isInternalTransitionEnabled()) {
                Rule r = s.getInternalTransition(false);
                if (r != null) {
                    String name = getRuntimeStateName(s) + "_InternalTransition";
                    MutableInternalStateTransition ist = new MutableInternalStateTransition(name,s,(MutableRule)r);
                    transitions.add(ist);
                }
            }
            StateMachine sm = s.getOwnerStateMachine();
            List l = sm.getTransitions();
            if(l == null) return;
            Iterator itr = l.iterator();
            while (itr.hasNext()) {
                StateTransition st = (StateTransition) itr.next();
                State fromState = st.getFromState();
                if (fromState == s) {
                    transitions.add(st);
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void addCompositeState(Concept concept, State s, String parent, Set states, List transitions, Set endStates, Set subMachines, Stack recursiveSubMachines) {
        StateComposite composite = (StateComposite) s;
        if (s instanceof MutableStateSubmachine) {
            MutableStateSubmachine sub = (MutableStateSubmachine) s;
            StateMachine sm = getReferencedStateMachine(concept, sub);
            if(recursiveSubMachines.contains(sm)) throw new RuntimeException("Recursive State Machine calls not allowed: " + s.getName() + ":" + sub.getSubmachineURI());

            State root = sm.getMachineRoot();
            subMachines.add(sub);

            recursiveSubMachines.push(sm);
            addStates(concept, root,parent,states, transitions, null, subMachines, recursiveSubMachines);
            recursiveSubMachines.pop();
        }
        else if(composite.isConcurrentState()) {
            List regions = composite.getRegions();
            Iterator regionIt = regions.iterator();
            while(regionIt.hasNext()) {
                State regionState = (State) regionIt.next();
                addStates(concept, regionState, parent, states, transitions, null, subMachines, recursiveSubMachines);
            }
        }
        else {
            List l = composite.getEntities();
            if (l == null) return; //Composite state creates null entities
            Iterator itr = l.iterator();
            while (itr.hasNext()) {
                Object o = itr.next();
                if(!(o instanceof State)) continue;
                State st = (State) o;
                addStates(concept, st, parent, states, transitions, endStates, subMachines, recursiveSubMachines);
            }
        }
    }

    private static StateMachine getReferencedStateMachine(Concept concept, MutableStateSubmachine s) {
        String uri = s.getSubmachineURI();
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash == -1) {
            throw new RuntimeException("StateSubMachine: Incorrect URI format - " + uri);
        }

        String conceptPath = uri.substring(0,lastSlash);
        int lastDot = conceptPath.lastIndexOf('.'); //sometimes it could be abc.concept (So remove the .concept);
        String conceptName = conceptPath;
        if (lastDot != -1) conceptName = conceptPath.substring(0,lastDot);
        String smName = uri.substring(lastSlash+1);

        Concept c = concept.getOntology().getConcept(conceptName);
        if (c == null) throw new RuntimeException("StateSubMachine: Invalid Concept Name - " + conceptName);

        // Return precisely the state machine referenced
        if(s.callExplicitly()) {
            List l = c.getStateMachines();
            if (l == null) throw new RuntimeException("StateSubMachineState: Invalid Machine Name - " + smName);
            Iterator it = l.iterator();
            while(it.hasNext()) {
                StateMachine sm = getLocalStateMachineByName(c, smName);
                if(sm != null) return sm;
            }
            throw new RuntimeException("StateSubMachine: Unknown Machine Reference - " + smName);
        } else {
            // Search for the closest inherited SM that has the same name, including our own.
            while(concept != null) {
                StateMachine sm = getLocalStateMachineByName(concept, smName);
                if(sm != null) return sm;
                concept = concept.getSuperConcept();
            }
            throw new RuntimeException("StateSubMachine: Unknown Machine Reference - " + smName);
        }
    }

    private static StateMachine getLocalStateMachineByName(Concept concept, String smName) {
        List sms = concept.getStateMachines();
        if(sms == null) return null;

        for(Iterator it = sms.iterator(); it.hasNext();) {
            StateMachine sm = (StateMachine) it.next();
            if(smName.equals(sm.getName())) return sm;
        }

        return null;
    }

    private static String getRuntimeStateName(State s) {
        return s.getName();
    }

    public static Collection getActiveStates(PropertyArrayInt transitionStatuses, StateTransition[] transitions) {
        if (transitionStatuses.length() > 0) {
            HashMap activeStates = new HashMap();            
            for (int i=0; i < transitionStatuses.length();i++ ) {
                int status = ((PropertyAtomInt)transitionStatuses.get(i)).getInt();
                StateTransition st = transitions[i];
                if(status == TRANSITION_STATUS_READY) {
                    State state = st.getFromState();
                    activeStates.put(getStatePropertyName(state), state);
                    //composite or concurrent states which contain the above state are also considered active
                    for(StateEntity parent = state.getParent(); parent != null; parent = parent.getParent()){
                        //parent may also be a StateMachine which is not useful
                        if(parent instanceof State) {
                            activeStates.put(getStatePropertyName((State)parent), parent);
                        }
                    }
                }
            }
            return activeStates.values();
        }
        return null;
    } 
}