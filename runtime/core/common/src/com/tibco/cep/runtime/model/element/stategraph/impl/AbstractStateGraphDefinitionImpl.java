package com.tibco.cep.runtime.model.element.stategraph.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.FinalStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.GroupTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.InternalTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StartStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StartTransitionLink;
import com.tibco.cep.runtime.model.element.stategraph.StateGraphDefinition;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLink;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 19, 2004
 * Time: 3:54:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractStateGraphDefinitionImpl implements StateGraphDefinition{
    CompositeStateVertex root;
    int numberOfStates=0,
    numberOfTransitions=0;
    Class[] rules;
    StateVertexImpl[] states;
    TransitionLinkImpl[] transitions;
    int nosofConcurrentStates=0;

    protected HashMap /*<CompositeStateVertex, Set<StateVertex>>*/ m_parent2childValidTimeouts = new HashMap(); // todo-- optimize
    protected HashMap /*<StateVertex, BitSet>*/ m_parent2childValidTransitions= new HashMap(); // todo-- optimize
    protected List /*<Path>*/ m_paths;

    public AbstractStateGraphDefinitionImpl() {
    }

    public void initialize(CompositeStateVertexImpl root, StateVertexImpl[] states, TransitionLinkImpl[] transitions, Class[] rules ) {
        this.root=root;
        // Setup the Graph
        setupGraph(states,transitions);
        // Initialize the states
        numberOfStates=setIndicesForStates(root,0);
        Arrays.sort(states, new Comparator() {

            public int compare(Object o1, Object o2) {
                StateVertex sv1, sv2;
                sv1 = (StateVertex) o1;
                sv2 = (StateVertex) o2;
                return sv1.getIndex() - sv2.getIndex();
            }
        });

        // Initialize the transitions
        numberOfTransitions=setIndicesForTransitions(transitions);
        this.rules = rules;
        this.states = states;
        this.transitions = transitions;
        initValidStateTimeouts(root);
        initValidTransitions(root);
        createPaths();
    }

    // todo -- optimize this
    private List getInterrupts(Map interruptMap, CompositeStateVertex comp) {
        if(comp == null || comp == root) {
            return null;
        }

        List interrupts = (List) interruptMap.get(comp);
        if(interrupts == null) {
            interrupts = new ArrayList();
            interruptMap.put(comp, interrupts);

            // a composite state's interrupts are it's own interrupts, and all of its ancestors' interrupts
            while(comp != null) {
                TransitionLink[] froms = comp.getFromTransitions();
                for(int i = 0; i < froms.length; i++) {
                    if(froms[i].emptyCondition() || froms[i].isLambda()) {
                        continue;
                    }

                    interrupts.add(froms[i]);
                }
                comp = comp.getSuperState();
            }
        }

        return interrupts;
    }

    private List getLambdaTargets(CompositeStateVertex csv) {
        List l = null;
        TransitionLink[] tls = csv.getFromTransitions();
        for(int i = 0; i < tls.length; i++) {
            if(tls[i].emptyCondition() || tls[i].isLambda()) {
                if(l == null) {
                    l = new ArrayList();
                }
                l.add(tls[i].target());
            }
        }

        return l;
    }

    private List timeoutStatesFor(StateVertex state) {
        List l = null;
        byte policy = state.getTimeoutPolicy();

        if(policy == StateVertex.NO_ACTION_TIMEOUT_POLICY) {
            return null;
        }

        // this type of policy only matters for composite states with
        // from empty condition / lambda transitions-- such transitions effectively
        // act as intterupts in this case.  Transitions with conditiosn are not
        // included here, since they are already dealt with as interrupts
        else if(policy == StateVertex.NON_DETERMINISTIC_STATE_TIMEOUT_POLICY) {

            if(state instanceof CompositeStateVertex) {
                CompositeStateVertex csv = (CompositeStateVertex) state;
                l = getLambdaTargets(csv);
            }
        }

        else {
            StateVertex timeoutState = state.getTimeoutState();
            if(timeoutState != null) {
                l = new ArrayList();
                l.add(timeoutState);
            }
        }

        return l;
    }

    // todo -- optimize this
    private List getTimeoutStates(Map timeoutMap, StateVertex state) {
        List timeoutStates = (List) timeoutMap.get(state);
        if(timeoutStates == null) {
            timeoutStates = new ArrayList();
            timeoutMap.put(state, timeoutStates);

            while(state != null) {
                List subTimeoutStates = timeoutStatesFor(state);
                if(subTimeoutStates != null) {
                    timeoutStates.addAll(subTimeoutStates);
                }
                state = state.getSuperState();
            }
        }

        return timeoutStates;
    }

    // todo -- "timeout" states
    // todo -- interrupts from inside concurrent regions
    // todo -- interrupts from inside composites
    // todo -- cache composite paths
    private void createPaths() {
        StateVertex start = root.getDefaultState();
        m_paths = new ArrayList();
        HashMap interruptMap = new HashMap();
        HashMap timeoutMap = new HashMap();
        createPaths(root, start, new Path(numberOfStates()), interruptMap, timeoutMap);
        HashSet temp = new HashSet(m_paths);
        m_paths = new ArrayList(temp); // clears duplicates

        interruptMap.clear();
        interruptMap = null;

        timeoutMap.clear();
        timeoutMap = null;
    }

    private void createPaths(StateVertex prev, StateVertex state, Path path, Map interruptMap, Map timeoutMap) {
        // add this state to our path
        path.add(prev, state);

        // store the path only when a top level end is reached
        if(isTopEnd(state)) {
            path.setEnd((FinalStateVertex) state);
            m_paths.add(path);
        }

        // use the parents' transitions to continue the path
        else if(isCompositeEnd(state)) {
            CompositeStateVertex parent = state.getSuperState();
            // todo fix this extra root state issue in code generation
            if(isSubmachineRoot(parent)) {
                parent = parent.getSuperState();
            }
            TransitionLink[] tls = parent.getFromTransitions();
            for(int i = 0; i < tls.length; i++) {
                TransitionLink tl = tls[i];
                StateVertex target = tl.target();
                if(!path.get(target.getIndex())) {
                    createPaths(state, target, new Path(path), interruptMap, timeoutMap);
                }
            }
        }

        // if we hit a Composite, then do paths for the substates
        else if(state instanceof CompositeStateVertex) {
            CompositeStateVertex csv = (CompositeStateVertex) state;
            if(csv.isConcurrent()) {
                //todo concurrent
            }
            else {
                StateVertex defaultState = csv.getDefaultState();
                createPaths(state, defaultState, new Path(path), interruptMap, timeoutMap);
            }
        }

        else {
            // create paths taken through timeout states
            List timeoutStates = getTimeoutStates(timeoutMap, state);
            if(timeoutStates != null && timeoutStates.size() > 0) {
                for(int i = (timeoutStates.size() - 1); i >= 0; i--) {
                    StateVertex timeoutState = (StateVertex) timeoutStates.get(i);
                    if(!path.signature.get(timeoutState.getIndex())) {
                        createPaths(state, timeoutState, new Path(path), interruptMap, timeoutMap);
                    }
                }
            }

            // create paths caused by an ancestor states' interrupts
            List interrupts = getInterrupts(interruptMap, state.getSuperState());
            if(interrupts != null && interrupts.size() > 0) {
                for(int i = (interrupts.size() - 1); i >= 0; i--) {
                    TransitionLink interrupt = (TransitionLink) interrupts.get(i);
                    StateVertex target = interrupt.target();

                    // don't revisit states
                    if(!path.get(target.getIndex())) {
                        createPaths(state, target, new Path(path), interruptMap, timeoutMap);
                    }
                }
            }

            // create paths taken through transitions
            TransitionLink[] tls = state.getFromTransitions();
            for(int i = 0; i < tls.length; i++) {
                StateVertex next = tls[i].target();

                // don't revisit a state
                if(path.get(next.getIndex())) {
                    continue;
                }

                // reuse the base path object for the first transition
                Path pathToUse = (i == tls.length - 1) ? path : new Path(path);
                createPaths(state, next, pathToUse, interruptMap, timeoutMap);
            }
        }
    }

    public boolean isTopEnd(StateVertex state) {
        return (state.getSuperState() == root) && (state instanceof FinalStateVertex);
    }

    public boolean isCompositeEnd(StateVertex state) {
        return (state.getSuperState() != root) && (state instanceof FinalStateVertex);
    }

    public boolean isSubmachineRoot(CompositeStateVertex csv) {
        CompositeStateVertex parent = csv.getSuperState();
        if(parent == null) {
            return false;
        }

        return parent.isSubMachine();
    }

    private boolean isConcurrentRegionEnd(StateVertex state) {
        boolean isFinalState =  (state instanceof FinalStateVertex);

        CompositeStateVertex region = state.getSuperState();
        if(region == null) {
            return false;
        }

        return isFinalState && region.getSuperState().isConcurrent();
    }

    private boolean isRegionInterrupt(StateVertex state, StateVertex target) {
        CompositeStateVertex stateParent = state.getSuperState();
        if(stateParent == null) {
            return false;
        }

        CompositeStateVertex concurrent = stateParent.getSuperState();
        if(concurrent == null || !concurrent.isConcurrent()) {
            return false;
        }

        return hasAncestor(target, stateParent);
    }

    protected boolean hasAncestor(StateVertex state, CompositeStateVertex comp) {
        CompositeStateVertex parent = state.getSuperState();
        while(parent != null) {
            if(comp.equals(parent)) {
                return true;
            }
        }

        return false;
    }

//    private void createConcurrentPaths(CompositeStateVertex concurrent, BitSet path) {
//        StateVertex[] regions = concurrent.subStates();
//        List[] regionPathLists = new List[regions.length];
//        for(int i = 0; i < regions.length; i++) {
//            regionPathLists[i] = createRegionPaths((CompositeStateVertex) regions[i], path);
//        }
//
//        if(regionPathLists.length < 1) {
//            throw new RuntimeException("Concurrent State " + concurrent + " has no regions...");
//        }
//
//        int[] pathToORIndex = new int[regions.length];
//        int rotatingRegionIndex = 0;
//        List rotator = regionPathLists[rotatingRegionIndex];
//        for(int i = 0; i < rotator.size(); i++) {
//            List regionPaths = regionPathLists[i];
//            if(regionPaths == null || regionPaths.size() == 0) {
//                continue;
//            }
//
//            if(pathToORIndex[i] == regionPaths.size()) {
//
//            }
//
//            BitSet pathToOR = (BitSet) regionPaths.get(pathToORIndex[i]);
//
//        }
//    }
//
//    private List createRegionPaths(CompositeStateVertex region, BitSet basePath) {
//        List regionPaths = new ArrayList();
//        BitSet bs = (BitSet) basePath.clone();
//        StateVertex start = region.getDefaultState();
//        createPaths(start, bs, regionPaths);
//        return regionPaths;
//    }

    public Set/*<StateVertex>*/ getValidStateTimeouts(CompositeStateVertex parent) {
        return (Set) m_parent2childValidTimeouts.get(parent);
    }

    public BitSet getValidTransitions(StateVertex state) {
        return (BitSet) m_parent2childValidTransitions.get(state);
    }

    /**
     * TODO reuse each level's valid timeouts, since each level's timeouts is parents' levels plus those of some children
     */
    private void initValidStateTimeouts(CompositeStateVertex csv) {
        StateVertex[] substates = csv.subStates();
        if(substates != null) {
            for(int i = 0; i < substates.length; i++) {
                if(substates[i] instanceof CompositeStateVertex) {
                    CompositeStateVertex subCSV = (CompositeStateVertex) substates[i];
                    initValidStateTimeouts(subCSV);
                }
            }
        }

        /**
         * States with valid timeouts are:
         * superstates
         * concurrent co-regions of superstates
         * children of concurrent co-regions of superstates
         * concurrent co-regions
         * children of concurrent co-regions
         */
        HashSet validStateSet = new HashSet();
        CompositeStateVertex parent = csv;
        while(parent != null) {
            validStateSet.add(parent);
            if(parent.isConcurrent()) addAllStates(parent, validStateSet);
            parent = parent.getSuperState();
        }

        m_parent2childValidTimeouts.put(csv, validStateSet);
    }

    private void addAllStates(CompositeStateVertex csv, HashSet validSet) {
        StateVertex[] substates = csv.subStates();
        if(substates == null) return;

        for(int i = 0; i < substates.length; i++) {
            StateVertex sv = substates[i];
            validSet.add(sv);
            if(sv instanceof CompositeStateVertex) addAllStates((CompositeStateVertex) sv, validSet);
        }
    }


    /**
     * Valid transitions are:
     * from transitions
     * parents' froms
     * transitions inside concurrent co-regions
     * transitions inside parents' concurrent co-regions
     */

    private void initValidTransitions(CompositeStateVertex root) {
        initValidTransitions(root, null);
    }

    private void initValidTransitions(StateVertex state, BitSet validTransitions) {
        if(state instanceof CompositeStateVertex) {
            CompositeStateVertex csv = (CompositeStateVertex) state;
            StateVertex[] substates = csv.subStates();
            for(int i = 0; i < substates.length; i++) {
                initValidTransitions(substates[i], new BitSet(numberOfTransitions));
            }
        }

        if(validTransitions != null) m_parent2childValidTransitions.put(state, validTransitions);

        addFromTransitions(state, validTransitions);
        CompositeStateVertex parent = state.getSuperState();
        while(parent != null) {
            addFromTransitions(parent, validTransitions);
            if(parent.isConcurrent()) {
                addAllInteriorTransitions(parent, validTransitions);
            }
            parent = parent.getSuperState();
        }
    }

    private void addAllInteriorTransitions(CompositeStateVertex csv, BitSet validTransitions) {
        StateVertex[] substates = csv.subStates();
        if(substates == null) return;

        for(int i = 0; i < substates.length; i++) {
            StateVertex sv = substates[i];
            addFromTransitions(sv, validTransitions);
            if(sv instanceof CompositeStateVertex) {
                addAllInteriorTransitions((CompositeStateVertex) sv, validTransitions);
            }
        }
    }

    private void addFromTransitions(StateVertex state, BitSet validTransitions) {
        TransitionLink[] toLinks = state.getFromTransitions();
        if(toLinks != null) {
            for(int i = 0; i < toLinks.length; i++){
                TransitionLink toLink = toLinks[i];
                int idx = toLink.getIndex();
                validTransitions.set(idx, true);
            }
        }
    }

    private void setupGraph(StateVertexImpl [] states,TransitionLinkImpl  [] transitions ) {
        for (int i=0; i < states.length; i++) {
            if (states[i] instanceof SimpleStateVertexImpl) {
                setTransitions((SimpleStateVertexImpl)states[i],transitions);
            } else {
                setSubStates  ((CompositeStateVertexImpl) states[i],states);
                setTransitions((CompositeStateVertexImpl)states[i],transitions);
            }
        }

        // Calculate the LCAs
        for (int i=0; i < transitions.length; i++) {
            if (!((transitions[i] instanceof StartTransitionLinkImpl) ||
                    (transitions[i] instanceof InternalTransitionLinkImpl) ||
                    (transitions[i].target().equals(transitions[i].source())))) {
                setupTrailForTransition((TransitionLinkImpl) transitions[i]);
            }
        }
    }

    private int setIndicesForStates(CompositeStateVertex root, int startIndex) {
        root.setIndex(startIndex++);
        StateVertex [] states=root.subStates();
        for (int i=0; i < states.length; i++) {
            if (states[i] instanceof CompositeStateVertex) {
                CompositeStateVertexImpl csv = (CompositeStateVertexImpl)states[i];
                if (csv.isConcurrent()) {
                    csv.setConcurrentIndex(nosofConcurrentStates++);
                }
                startIndex=setIndicesForStates(csv, startIndex);
            } else {
                states[i].setIndex(startIndex++);
            }
        }
        return startIndex;
    }

    private int setCorrelationIndicesForStates(StateVertex root, int startIndex) {
        if(root.getIndex() != -1) {
            return startIndex;
        }

        root.setIndex(startIndex++);

        /**
         * First go through the substates
         */
        if(root instanceof CompositeStateVertex) {
            CompositeStateVertexImpl csv = (CompositeStateVertexImpl) root;
            StateVertex[] states = csv.subStates();
            for(int i = 0; i < states.length; i++) {
                startIndex = setCorrelationIndicesForStates(root, startIndex);
            }
        }

        /**
         * Now handle links
         */
        TransitionLink[] fromLinks = root.getFromTransitions();
        for(int i = 0; i < fromLinks.length; i++) {
            TransitionLink link = fromLinks[i];
            StateVertex target = link.target();
            startIndex = setCorrelationIndicesForStates(target, startIndex);
        }

        return startIndex;
    }

    private void setTransitions (SimpleStateVertexImpl state,TransitionLinkImpl  [] transitions ) {
        fromTransitions(state,transitions);
        toTransitions(state,transitions);
        internalTransitions(state,transitions);
    }

    private void setTransitions (CompositeStateVertexImpl state,TransitionLinkImpl  [] transitions ) {
        fromTransitions(state,transitions);
        toTransitions(state,transitions);
        internalTransitions(state,transitions);
    }

    private void internalTransitions (StateVertexImpl state,TransitionLinkImpl  [] transitions ) {
        for (int i=0; i < transitions.length; i++) {
            if ((transitions[i] instanceof InternalTransitionLink) && (transitions[i].target().equals(state))) {
                state.setInternalTransition((InternalTransitionLink) transitions[i]);
                break;
            }
        }
    }

    private void toTransitions (SimpleStateVertexImpl state,TransitionLinkImpl  [] transitions ) {
        LinkedList toTransitions = new LinkedList();
        for (int i=0; i < transitions.length; i++) {
            if (transitions[i].target().equals(state)) {
                toTransitions.add(transitions[i]);
            }
        }
        TransitionLinkImpl [] toTransitionsArray = new TransitionLinkImpl[toTransitions.size()];
        toTransitions.toArray(toTransitionsArray);
        state.setToTransitions(toTransitionsArray);
    }

    private void fromTransitions (SimpleStateVertexImpl state,TransitionLinkImpl  [] transitions ) {
        LinkedList fromTransitions = new LinkedList();
//        int maxtimeout = 0;
        for (int i=0; i < transitions.length; i++) {
            if (transitions[i].source().equals(state)) {
                fromTransitions.add(transitions[i]);
//                maxtimeout = Math.max(maxtimeout, transitions[i].getTimeout());
            }
        }
        TransitionLinkImpl [] fromTransitionsArray = new TransitionLinkImpl[fromTransitions.size()];
        fromTransitions.toArray(fromTransitionsArray);
        state.setFromTransitions(fromTransitionsArray);
//        state.setTimeout(maxtimeout);
    }

    private void toTransitions (CompositeStateVertexImpl state,TransitionLinkImpl  [] transitions ) {
        LinkedList toTransitions = new LinkedList();
        for (int i=0; i < transitions.length; i++) {
            if ((transitions[i] instanceof GroupTransitionLink) && (transitions[i].target().equals(state))) {
                toTransitions.add(transitions[i]);
            }
        }
        GroupTransitionLink [] toTransitionsArray = new GroupTransitionLink[toTransitions.size()];
        for (int i=0; i < toTransitions.size();i++) {
            toTransitionsArray[i]=(GroupTransitionLink) toTransitions.get(i);
        }
        state.setToTransitions(toTransitionsArray);
    }

    private void fromTransitions (CompositeStateVertexImpl state,TransitionLinkImpl  [] transitions ) {
        LinkedList fromTransitions = new LinkedList();
        for (int i=0; i < transitions.length; i++) {
            if ((transitions[i] instanceof GroupTransitionLink) && (transitions[i].source().equals(state))) {
                fromTransitions.add(transitions[i]);
            }
        }
        GroupTransitionLink [] fromTransitionsArray = new GroupTransitionLink[fromTransitions.size()];
        for (int i=0; i < fromTransitions.size();i++) {
            fromTransitionsArray[i]=(GroupTransitionLink) fromTransitions.get(i);
        }
        state.setFromTransitions(fromTransitionsArray);
    }

    private void setSubStates (CompositeStateVertexImpl state,StateVertexImpl [] states) {
        LinkedList subStates = new LinkedList();
        for (int i=0; i < states.length; i++) {
            if ((states[i].getSuperState() != null) && (states[i].getSuperState().equals(state))) {
                StateVertex sv = states[i];
                subStates.add(sv);
                if (sv instanceof StartStateVertex) {
                    state.setDefaultState((StartStateVertex)sv);
                }

            }
        }
        StateVertexImpl [] subStatesArray = new StateVertexImpl[subStates.size()];
        subStates.toArray(subStatesArray);
        state.setSubStates(subStatesArray);
    }

    private int setIndicesForTransitions(TransitionLinkImpl  [] transitions ) {
        int index=0;
        for (int i=0; i < transitions.length;i++) {
            transitions[i].setIndex(index++);
        }
        return transitions.length;
    }

    public CompositeStateVertex findLCA (TransitionLinkImpl t) {
        CompositeStateVertex ta=t.target().getSuperState();
        CompositeStateVertex sa=t.source().getSuperState();

        CompositeStateVertex LCA=null;
        if (ta.equals(sa)) {
            LCA=ta;
        } else {
            ta= t.target().getSuperState();
            while (ta != null) {
                sa= t.source().getSuperState();

                while (sa != null) {
                    if (sa.equals(ta)){
                        LCA=ta;
                        break;
                    }
                    sa=sa.getSuperState();
                }
                ta= ta.getSuperState();
            }

        }
        return LCA;
    }

    private void setupTrailForTransition (TransitionLinkImpl t) {
        CompositeStateVertex LCA = findLCA (t);
        setupFromTrail(t,LCA);
        setupToTrail(t,LCA);
    }

    private void setupToTrail (TransitionLinkImpl t, CompositeStateVertex LCA) {
        LinkedList targetAncestors = new LinkedList();

        CompositeStateVertex ta=t.target().getSuperState();

        // Find LCA
        while (!ta.equals(LCA)) {
            targetAncestors.add(ta);
            ta=ta.getSuperState();
        }
        CompositeStateVertexImpl [] taArray = new CompositeStateVertexImpl[targetAncestors.size()];
        targetAncestors.toArray(taArray);
        t.setTargetPath(taArray);
    }

    private void setupFromTrail (TransitionLinkImpl t,CompositeStateVertex LCA) {
        LinkedList sourceAncestors = new LinkedList();
        CompositeStateVertex ta=t.source().getSuperState();

        // Reach LCA
        while (!ta.equals(LCA)) {
            sourceAncestors.add(ta);
            ta=ta.getSuperState();
        }
        CompositeStateVertexImpl [] srcArray = new CompositeStateVertexImpl[sourceAncestors.size()];
        sourceAncestors.toArray(srcArray);
        t.setSourcePath(srcArray);
    }

    /*
    *
    */
    public CompositeStateVertex getRoot() {
        return root;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
    * @return Number of States, Deep Count
    */
    public int numberOfStates() {
        return numberOfStates;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
    * @return Number of Transitions, Deep Count
    */
    public int numberOfTransitions() {
        return numberOfTransitions;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
    * StartStub Required to create and associate this state machine with the parent
    */

    public StartTransitionLink startStub() {
        return root.getDefaultTransition();
    }

    public Class[] getRuleClasses() {
        return rules;
    }

    public TransitionLink getTransitionLink(int index) {
        if (index >= transitions.length) return null;
        return transitions[index];
    }

    public int numberOfConcurrentStates() {
        return nosofConcurrentStates;
    }

    public FinalStateVertex[] getTopFinalStates() {
        return new FinalStateVertex[0];
    }

    public StateVertex getStateVertex(int index) {
        return states[index];
    }

    public List getPaths() {
        return m_paths;
    }

    public boolean forwardCorrelates() {
        return false;
    }
}