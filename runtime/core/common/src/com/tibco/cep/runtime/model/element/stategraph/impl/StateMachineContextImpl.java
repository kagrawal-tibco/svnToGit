package com.tibco.cep.runtime.model.element.stategraph.impl;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.helper.TimerTaskOnce;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.stategraph.CompositeStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StartStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StateGraphDefinition;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineContext;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineCorrelation;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;
import com.tibco.cep.runtime.model.element.stategraph.TransitionLink;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.util.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Oct 10, 2004
 * Time: 4:33:29 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * StateMachineContextImpl : A Context created for every concept instance in working memory which cranks the StateMachine
 * The context runs out of an array (flattened) of Transition. Each of the element in the Array corresponds to an instance
 * of Transition Edge in the model. For saving memory, the metainformation of the model is not copied but referenced by
 * each element. The metainformation is a directed Graph (could be cyclic too), containing of vertices and edges.
 *
 * The StateMachineContextImpl evaluates each vertex status by computing the edges status and then inferencing the state
 * status.
 *
 * The Transition or edge status has history (in generation terms) as to how many time the transition was visited.
 *
 * Limitation
 * 1.> As of version 1.0, the state machine has to be non-recursive statemachine. in the essence that a sub-machine cannot
 * call itself. To circumvent this problem, loopback is provided
 *
 * 2.> StateMachine inheritance is not available. ??? May be both the feature is available
 * 3.> Language Support for the Machine is still to be thought of

 */

public class StateMachineContextImpl implements StateMachineContext {
    StateGraphDefinition mModel;
    byte[] mConcurrentStates = null;
    ConceptImpl instance;
    public transient boolean isRecovered = false;

    //todo-- avoid using these classes?
    public transient HashMap m_activeTimers = null;
    public transient Set m_currStates = new HashSet();
    private final transient Logger logger;

    // properties used by sm:
    // PropertyArrayInt for transition statuses
    // StateMachineCorrelation for correlation statuses
    // PropertyAtomBoolean for machine closed status


    public StateMachineContextImpl(StateGraphDefinition model, ConceptImpl inst) {
        logger = inst.getWorkingMemory().getLogManager().getLogger(StateMachineContextImpl.class);
        instance = inst;
        mModel = model;
        mConcurrentStates = new byte[mModel.numberOfConcurrentStates()];
        for (int i=0; i < mConcurrentStates.length; i++) {
            mConcurrentStates[i] = 0;
        }
        initTransitions();
    }

    protected void initTransitions() {
        int nos = mModel.numberOfTransitions();
        PropertyArrayInt paInt = instance.getTransitionStatuses();
        if (paInt.length() > 0) {
            //Compute Concurrent States state
            if(logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG,"Recovered concept " + instance );
            }
            isRecovered = true;

            /* Restart timers if recovered */
            Object [] args = new Object[] {instance};
            List stateTimers = new ArrayList();
            for (int i=0; i < paInt.length();i++ ) {
                int status = ((PropertyAtomInt) paInt.get(i)).getInt() ;
                TransitionLink tl = (TransitionLink) mModel.getTransitionLink(i);
                StateVertex source = tl.source();
                StateVertex target = tl.target();
                switch(status) {
                    case TransitionLink.STATUS_READY:
                        long timeout = 0L;
                        try {
                            timeout = source.getTimeout(args) * source.getTimeoutMultiplier();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }

                        if (timeout > 0) {
                            if (!stateTimers.contains(source)) {
                                this.watchState(source, timeout);
                                stateTimers.add(source);
                            }
                        }

                        m_currStates.add(source);
                        break;
                    case TransitionLink.STATUS_COMPLETE:
                    case TransitionLink.STATUS_TIMEOUT:
                        if(!(target instanceof FinalStateVertexImpl)) {
                            continue;
                        }
                        CompositeStateVertexImpl parent = (CompositeStateVertexImpl) target.getSuperState();
                        if(parent == null) {  // end of the graph, not region
                            continue;
                        }

                        CompositeStateVertexImpl grandparent = (CompositeStateVertexImpl) parent.getSuperState();
                        if(grandparent != null && grandparent.isConcurrent()) { // concurrent, so mark subregions as complete
                            regionEnded(grandparent);
                        }

                        break;
                }
            }

            return;
        }

        for (int i=0; i < nos; i++ ) {
            paInt.add(TransitionLink.STATUS_WAITING);
        }
    }


    public boolean forwardCorrelates() {
        return mModel.forwardCorrelates();
    }

    public void setCorrelationStatus(TransitionLink t, boolean correlated) {
        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,ResourceManager.getInstance().formatMessage(
                    "be.statemachine.transition.correlation.status", t, String.valueOf(correlated)));
        }
        StateMachineCorrelation corrStatuses = instance.getCorrelationStatuses();
        corrStatuses.setCorrelated(t.getIndex(), correlated);
    }

    public boolean isCorrelated(TransitionLink t) {
        StateMachineCorrelation corr = instance.getCorrelationStatuses();
        return corr.isCorrelated(t.getIndex());
    }

    public void setTransitionActive(TransitionLink t) {
        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,
                    ResourceManager.getInstance().formatMessage("be.statemachine.transition.activation",t));
        }
        PropertyAtomInt pai = (PropertyAtomInt)instance.getTransitionStatuses().get(t.getIndex());
        pai.setInt(TransitionLink.STATUS_READY);
    }

    public void setTransitionInActive(TransitionLink t) {
        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,
                    ResourceManager.getInstance().formatMessage("be.statemachine.transition.inactivation",t));
        }
        PropertyAtomInt pai = (PropertyAtomInt)instance.getTransitionStatuses().get(t.getIndex());
        pai.setInt(TransitionLink.STATUS_WAITING);
    }

    public void setTransitionStatus(TransitionLink t, byte status) {
        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,
                    ResourceManager.getInstance().formatMessage("be.statemachine.transition.status",t,TransitionLink.STATUSES[status]));
        }
        PropertyAtomInt pai = (PropertyAtomInt) instance.getTransitionStatuses().get(t.getIndex());
        pai.setInt(status);
    }

    public byte getTransitionStatus(TransitionLink t)
    {
        PropertyAtomInt pai = (PropertyAtomInt)instance.getTransitionStatuses().get(t.getIndex());
        byte val = (byte)pai.getInt();

//        if (mTrace.hasDebugRole()) {
//            mTrace.trace(Trace.DEBUG, TraceMessage.build("be.statemachine.transition.status", new Object[] {t, TransitionLink.STATUSES[val]}));
//        }

        return val;
    }

    public boolean isTransitionActive(TransitionLink t) {
        PropertyAtomInt pai = (PropertyAtomInt)instance.getTransitionStatuses().get(t.getIndex());
        return (pai.getInt() == TransitionLink.STATUS_READY);
    }

    /**
     * Checks for all transition's active status. If any one of them is not active, then it returns false;
     * @param state
     */
    public boolean isStateActive(StateVertex state) {
        TransitionLink[] links = state.getFromTransitions();
        boolean isActive = true;
        boolean isComposite = state instanceof CompositeStateVertex;

        for (int i=0; i < links.length; i++) {
            TransitionLink link = links[i];
            boolean b = isTransitionActive(link);
            if(isComposite) b = b || link.isLambda();

            isActive = isActive && b;
            if (!isActive)
                return isActive;
        }
        return isActive;

    }

    public boolean isStateComplete(StateVertex state) {
        TransitionLink[] links = state.getFromTransitions();
        for (int i=0; i < links.length; i++) {
            TransitionLink link = links[i];
            if (getTransitionStatus(link) == TransitionLink.STATUS_COMPLETE)
                return true;
        }
        return false;
    }

    public boolean isStateTimedOut(StateVertex state) {

        TransitionLink[] links = state.getFromTransitions();
        for (int i=0; i < links.length; i++) {
            TransitionLink link = links[i];
            if (getTransitionStatus(link) == TransitionLink.STATUS_TIMEOUT)
                return true;
        }
        return false;
    }

    public boolean isStateWaiting(StateVertex state) {
        TransitionLink[] links = state.getFromTransitions();
        boolean isWaiting = true;
        for (int i=0; i < links.length; i++) {
            TransitionLink link = links[i];
            boolean b = getTransitionStatus(link) == TransitionLink.STATUS_WAITING;
            isWaiting = isWaiting && b;
            if (!isWaiting)
                return false;
        }
        return true;
    }

    public boolean isStateDeterministic(StateVertex state) {
        TransitionLink[] links = state.getFromTransitions();
        boolean isWaiting = true;
        for (int i=0; i < links.length; i++) {
            TransitionLink link = links[i];
            boolean b = getTransitionStatus(link) == TransitionLink.STATUS_AMBIGUOUS;
            isWaiting = isWaiting && b;
            if (!isWaiting)
                return false;
        }
        return true;
    }

    public int getStateStatus(StateVertex state) {
        TransitionLink[] links = state.getFromTransitions();
        for (int i=0; i < links.length; i++) {
            TransitionLink link = links[i];
            int linkStatus = getTransitionStatus(link);
            switch (linkStatus) {
                //If any one of the transition is not skipped, then we can determine, the state's status from the
                //link status
                case TransitionLink.STATUS_COMPLETE:
                case TransitionLink.STATUS_TIMEOUT:
                case TransitionLink.STATUS_READY:
                case TransitionLink.STATUS_LAMBDA:
                case TransitionLink.STATUS_WAITING:
                case TransitionLink.STATUS_AMBIGUOUS:
                    return linkStatus;

                case TransitionLink.STATUS_SKIPPED:
                    break;
            }
        }
        return TransitionLink.STATUS_SKIPPED;
    }


    public StateVertex[] getCurrentStates() {
        return (StateVertex[]) m_currStates.toArray(new StateVertex[0]);
    }

    public int regionEnded(CompositeStateVertex csv) {
        return ++mConcurrentStates[((CompositeStateVertexImpl)csv).getConcurrentIndex()];
    }

    public void concurrentEntered(CompositeStateVertex concurrent) {
        mConcurrentStates[((CompositeStateVertexImpl)concurrent).getConcurrentIndex()] = 0;
    }

    public Concept getSubject() {
        return instance;
    }

    public void closeMachine() {
        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,"Closing State Machine for instance:" + instance);
        }
        PropertyAtomBoolean isClosed = instance.getMachineClosed();
        isClosed.setBoolean(true);
        cancelAllTimeouts();
    }

    public void clearCorrelationMap() {
    }

    public boolean isMachineClosed() {
        PropertyAtomBoolean isClosed = instance.getMachineClosed();
        return isClosed.getBoolean();
    }

    public void cancelAllTimeouts() {
        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,"Cancelling all timeouts on instance:" + instance);
        }
        if(m_activeTimers == null || m_activeTimers.isEmpty()) return;
        Set entries = m_activeTimers.entrySet();
        Iterator it = entries.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            TimeoutAction ta = (TimeoutAction) entry.getValue();
            if(logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG,"Cancelling timeout on state: " + ta.state.getName() + " [" + ta.state.getClass().getName() + "] for instance: " + instance);
            }
            ta.cancel();
            it.remove();
        }
    }

    public void watchState(final StateVertex state, long period) {
        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,"Setting " + period + " ms timeout on state: " + state.getName() + " [" + state.getClass().getName() + "] for instance: " + instance);
        }
        if(m_activeTimers == null) m_activeTimers = new HashMap();
        TimeoutAction ta = (TimeoutAction) m_activeTimers.get(state);
        if(ta != null) {
            // TODO: restart timeout, or continue with old timeout?  right now restarting
            if(ta.cancel() && logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG,"Cancelling existing timeout on state: " + state.getName() + " [" + state.getClass().getName() + "] for instance: " + instance);
            }
        }

        ta = new TimeoutAction(StateMachineContextImpl.this, (StateVertexImpl) state);
        m_activeTimers.put(state, ta);

        TimeManager timeMgr = getTimeManager();
        timeMgr.scheduleOnceOnly(ta, period);
    }

    private TimeManager getTimeManager() {
        return instance.getWorkingMemory().getTimeManager();
    }

    public void invalidateTimersFor(StateVertex state) {
        if(m_activeTimers == null || m_activeTimers.isEmpty()) return;
        AbstractStateGraphDefinitionImpl graph = (AbstractStateGraphDefinitionImpl) mModel;

        Set validSet = graph.getValidStateTimeouts(state.getSuperState());
        if(validSet == null) {
            return;
        }

        Object[] entries = m_activeTimers.entrySet().toArray(); // todo optimize
        for(int i = 0; i < entries.length; i++) {
            Map.Entry entry = (Map.Entry) entries[i];
            Object key = entry.getKey();
            if(!validSet.contains(key)) {
                StateVertex sv = (StateVertex) entry.getKey();
                removeTimeout(sv, false);
                m_currStates.remove(sv);
            };
        }
    }

    public void removeTimeout(StateVertex state, boolean revertTransitions) {
        if(m_activeTimers != null) {
            TimeoutAction ta = (TimeoutAction) m_activeTimers.remove(state);
            if(ta != null) {
                if (ta.cancel() && logger.isEnabledFor(Level.DEBUG)) {
                    logger.log(Level.DEBUG,"Cancelling timeout on state: " + state.getName() + " [" + state.getClass().getName() + "] for instance: " + instance);
                }
                if(!revertTransitions) return;

                /**
                 * Revert transitions to their previous statuses.
                 */
                PropertyArrayInt paInt = instance.getTransitionStatuses();
                TransitionLink[] fromLinks = state.getFromTransitions();
                if(fromLinks == null) return;
                for(int i = 0; i < fromLinks.length; i++) {
                    TransitionLink tl = fromLinks[i];
                    int idx = tl.getIndex();
                    PropertyAtomInt pInt = (PropertyAtomInt) paInt.get(idx);
                    Integer valObj = (Integer) pInt.getPreviousValue();

                    byte val = (valObj == null) ? TransitionLink.STATUS_WAITING : valObj.byteValue();
                    if(logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG,"Reverting transition " + tl.toString() + " from " + TransitionLink.STATUSES[pInt.getInt()] + " to " + TransitionLink.STATUSES[val]);
                    }
                    setTransitionStatus(tl, val);
                }
            }
        }
    }

    public class TimeoutAction extends TimerTaskOnce implements Action {
        StateMachineContextImpl ctx;
        StateVertexImpl state;

        public TimeoutAction(StateMachineContextImpl ctx, StateVertexImpl state) {
            super();
            this.ctx = ctx;
            this.state = state;
        }

        public void execute(WorkingMemory workingMemory) {
            try {

                if(!this.cancel()) {
                    if(logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG,"Invoking Timeout Action on state: " + state.getName() + " [" + state.getClass().getName() + "] for instance: " + instance);
                    }
                    workingMemory.invoke(this, new Object[] {instance});
                }
                else {
                    if(logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG,"Timeout Task cancelled: Did not invoke Timeout Action on state" + state.getName() +" [" + state.getClass().getName() + "] for instance: " + instance);
                    }
                }
            } catch(RuntimeException re) {
                re.printStackTrace();
            }
        }

        public Identifier[] getIdentifiers() {
            return null;
        }

        public void execute(Object[] objects) {
            timeoutState(state, objects);
        }

        public Rule getRule() {
            return null;
        }

        public void timeoutState(StateVertexImpl state, Object[] args) {
            if (!instance.isDeleted() && !isMachineClosed() && isStateActive(state)) {
                if(logger.isEnabledFor(Level.DEBUG)) {
                    logger.log(Level.DEBUG,ResourceManager.getInstance().formatMessage(
                            "be.statemachine.state.timeout", state.getName(), " [" + state.getClass().getName() + "]", instance));
                }

                /** Invoke the timeout action */
                state.onTimeout(args);

                /**
                 * The machine's timeout is handled by the root state, so close it when
                 * the root times out
                 */
                CompositeStateVertex root = mModel.getRoot();
                if (root == state) {
                    closeMachine();
                    return;
                }

                /** Handle the timeout policy */
                byte policy = state.getTimeoutPolicy();
                if(policy != StateVertex.NO_ACTION_TIMEOUT_POLICY) {
                    applyTimeoutPolicy(state, policy, args);
                    removeStateTimeout(state);
                    return;
                }
                else if(state instanceof CompositeStateVertex) {
                    CompositeStateVertexImpl csv = (CompositeStateVertexImpl) state;
                    CompositeStateVertexImpl csvSuper = (CompositeStateVertexImpl) csv.getSuperState();

                    /**
                     * If a region just timed out, notify the parent
                     */
                    if(csvSuper != null && csvSuper.isConcurrent()) {
                        csvSuper.leave(StateMachineContextImpl.this, null, StateVertex.STATUS_TIMEOUT_CHILD_FINAL);
                        removeStateTimeout(state);
                        return;
                    }

                    /**
                     * If a composite timed out, enable the completion
                     */
                    TransitionLink[] tls = state.getFromTransitions();
                    for(int i = 0; i < tls.length; i++) {
                        if(tls[i].isLambda()) {
                            tls[i].enable(StateMachineContextImpl.this);
                            removeStateTimeout(state);
                            return;
                        }
                    }
                }

                // restart timer
                removeStateTimeout(state);
                long timeout = 0L;
                try {
                    timeout = state.getTimeout(args) * state.getTimeoutMultiplier();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                if(timeout > 0) {
                    if(logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG,"Re-enabling timeout on " + state);
                    }
                    watchState(state, timeout);
                }
            }
            else {
                if(logger.isEnabledFor(Level.DEBUG)) {
                    logger.log(Level.DEBUG,"Invocation cancelled: " + state.getName() + " [" + state.getClass().getName() + "]" + instance);
                }
            }
        }

        private void removeStateTimeout(StateVertex state) {
            if(this.equals(m_activeTimers.get(state))) {
                StateMachineContextImpl.this.removeTimeout(state, false);
            }
        }
    }

    public void applyTimeoutPolicy(StateVertexImpl state, byte policy, Object[] args) {
        /**
         * One transition is marked as timed out and the rest
         * are marked as skipped
         */
        byte triggerStatus = TransitionLink.STATUS_SKIPPED;
        StateVertexImpl timeoutState = (StateVertexImpl) state.getTimeoutState();

        /** All transitions are marked as ambiguous */
        if(policy == StateVertex.NON_DETERMINISTIC_STATE_TIMEOUT_POLICY) {
            triggerStatus = TransitionLink.STATUS_AMBIGUOUS;
            timeoutState = null;
        }

        /**
         * To mark as ambiguous, mark this state's transitions as ambiguous and
         * enable the transitions of the next states.
         */
        if(timeoutState == null || triggerStatus == TransitionLink.STATUS_AMBIGUOUS) {
            if(state instanceof CompositeStateVertexImpl) {
                CompositeStateVertexImpl csv = (CompositeStateVertexImpl) state;
                CompositeStateVertexImpl csvSuper = (CompositeStateVertexImpl) csv.getSuperState();

                if(csvSuper != null && csvSuper.isConcurrent()) {
                    csvSuper.leave(this, null, StateVertex.STATUS_TIMEOUT_CHILD_FINAL);
                    return;
                }
            }

            m_currStates.remove(state);
            TransitionLink[] tls = state.getFromTransitions();
            for(int i = 0; i < tls.length; i++) {
                TransitionLinkImpl tli = (TransitionLinkImpl) tls[i];
                if(tli.isLambda() || (getTransitionStatus(tli) == TransitionLink.STATUS_READY)) {
                    if(tli.isLambda()) {
                        tli.enable(this);
                        return; // only allow one lambda
                    }
                    tli.triggerSilently(this, args);
                }
            }
        }

        /**
         * In the case that the timeout is a state to which the current state is not a direct predecessor,
         * we explicitly enter that state without a transition triggering.
         */
        else if(timeoutState != null) {
            gotoTimeoutState(state, timeoutState);
            return;
        }
    }

    private void gotoTimeoutState(StateVertexImpl state, StateVertexImpl timeoutState) {
        Object[] args = new Object[] { instance };
        state.leave(this, null, StateVertex.STATUS_TIMEOUT); // handles concurrent / composite child notification

        invalidateTransitionsFor(state);
        invalidateTimersFor(state);

        timeoutState.enter(this, args, null);

        if(!(timeoutState instanceof CompositeStateVertexImpl)) return;
        // Instantiate the default transition that takes it to the start or history state.
        CompositeStateVertexImpl comp = (CompositeStateVertexImpl) timeoutState;
        if (comp.isConcurrent()) {
            for (int jj=0; jj<comp.subStates.length; jj++) {
                CompositeStateVertexImpl region = (CompositeStateVertexImpl) comp.subStates[jj];
                SimpleStateVertexImpl sv = (SimpleStateVertexImpl)region.getDefaultState();
                sv.enter(this, args, null);
            }
        }
        else {
            SimpleStateVertexImpl sv = (SimpleStateVertexImpl)comp.getDefaultState(); //Could be a StartState or History State
            sv.enter(this, args, null);
        }
    }

    public void invalidateTransitionsFor(StateVertex state) {
        AbstractStateGraphDefinitionImpl graph = (AbstractStateGraphDefinitionImpl) mModel;
        BitSet validTransitions = graph.getValidTransitions(state);
        if(validTransitions == null) return;

        PropertyArrayInt paInt = instance.getTransitionStatuses();
        int size = paInt.length();
        for(int i = 0; i < size; i++) {
            PropertyAtomInt pInt = (PropertyAtomInt) paInt.get(i);
            int status = pInt.getInt();

            if((status != TransitionLink.STATUS_READY) && (status != TransitionLink.STATUS_AMBIGUOUS)) {
                continue;
            }

            if(!validTransitions.get(i)) {
                revertTransition(pInt, i);
                TransitionLink tl = mModel.getTransitionLink(i);
                m_currStates.remove(tl.source());
            }
        }
    }

    private void revertTransition(PropertyAtomInt pInt, int i) {
        Integer valObj = (Integer) pInt.getPreviousValue();
        TransitionLink tl = mModel.getTransitionLink(i);

        byte val = (valObj == null) ? TransitionLink.STATUS_WAITING : valObj.byteValue();
        if(val == TransitionLink.STATUS_READY) val = TransitionLink.STATUS_AMBIGUOUS;

        if(logger.isEnabledFor(Level.DEBUG)) {
            logger.log(Level.DEBUG,"Reverting transition " + tl.toString() + " from " + TransitionLink.STATUSES[pInt.getInt()] + " to " + TransitionLink.STATUSES[val]);
        }
        setTransitionStatus(tl, val);
    }

    public void addCorrelationObjects(int index, Object[] objs) {
        StateMachineCorrelation correlations = instance.getCorrelationStatuses();
        correlations.addCorrelation(index, objs);
    }

    /**
     * Methods to be used by StateMachineHelper
     */
    private BitSet calculatePathSignature() {
        // calculate a signature based on the data we've seen
        PropertyArrayInt transStatuses = instance.getTransitionStatuses();
        StateMachineCorrelation corrStatuses = instance.getCorrelationStatuses();
        BitSet signature = new BitSet(mModel.numberOfStates());
        for(int i = 0; i < transStatuses.length(); i++) {
            PropertyAtomInt statusAtom = (PropertyAtomInt) transStatuses.get(i);
            int status = statusAtom.getInt();

            boolean correlated = false;
            if(corrStatuses != null) {
                correlated = corrStatuses.isCorrelated(i);
                correlated |= (corrStatuses.getCorrelationCount(i) > 0);
            }

            boolean addToSignature = status == TransitionLink.STATUS_COMPLETE ||
                    status == TransitionLink.STATUS_CORRELATED ||
                    status == TransitionLink.STATUS_TIMEOUT ||
                    status == TransitionLink.STATUS_LAMBDA ||
                    correlated;


            if(!addToSignature) {
                continue;
            }

            TransitionLink tl = mModel.getTransitionLink(i);
            StateVertex source = tl.source();
            signature.set(source.getIndex());

            StateVertex target = tl.target();
            signature.set(target.getIndex());
        }

        return signature;
    }

    public StartStateVertex getPathStart(int pathID) {
        Path path = (Path) mModel.getPaths().get(pathID);
        List l = (List) path.states.get(mModel.getRoot());
        return (StartStateVertex) l.get(0);
    }

    public List getNextStates(int pathID, int stateID) {
        Path path = (Path) mModel.getPaths().get(pathID);
        return (List) path.states.get(mModel.getStateVertex(stateID));
    }

    public long[] getConceptIDs(int stateId) {
        return getObjectIDs(stateId, true);
    }

    public long[] getEventIDs(int stateId) {
        return getObjectIDs(stateId, false);
    }

    public long[] getObjectIDs(int stateId, boolean forConcept) {
        StateMachineCorrelation smc = instance.getCorrelationStatuses();
        StateVertex state = mModel.getStateVertex(stateId);
        TransitionLink[] tos = state.getToTransitions();
        int numIDs = 0;

        // this isn't *that* slow
        for(int i = 0; i < tos.length; i++) {
            numIDs += smc.getCorrelationCount(tos[i].getIndex());
        }

        long[] ids = new long[numIDs];
        copyIDs(ids, tos, smc, forConcept);

        return ids;
    }

    private void copyIDs(long[] ids, TransitionLink[] tos, StateMachineCorrelation smc, boolean forConcept) {
        int numIDs = 0;
        for(int i = 0; i < tos.length; i++) {
            StateMachineCorrelation.TransitionCorrelation[] corrs = smc.getCorrelations(tos[i].getIndex());
            if(corrs == null) {
                continue;
            }

            for(int j = 0; j < corrs.length; j++) {
                long[] copyIDs = forConcept ? corrs[j].getConceptIDs() : corrs[j].getEventIDs();
                System.arraycopy(copyIDs, 0, ids, numIDs, copyIDs.length);
            }

            numIDs += corrs.length;
        }
    }

    public int getCorrelationCount(int stateID) {
        int corrCount = 0;
        StateMachineCorrelation correlation = instance.getCorrelationStatuses();
        StateVertex state = mModel.getStateVertex(stateID);
        TransitionLink[] tos = state.getToTransitions();
        for(int i = 0; i < tos.length; i++) {
            corrCount += correlation.getCorrelationCount(tos[i].getIndex());
        }
        return corrCount;
    }

    public StateMachineCorrelation.TransitionCorrelation getCorrelation(int stateID, int correlationID) {
        StateVertex state = mModel.getStateVertex(stateID);
        StateMachineCorrelation smc = instance.getCorrelationStatuses();
        TransitionLink[] tos = state.getToTransitions();
        for(int i = 0; i < tos.length; i++) {
            int corrCount = smc.getCorrelationCount(tos[i].getIndex());
            if(correlationID < corrCount) {
                return smc.getCorrelation(tos[i].getIndex(), correlationID);
            }

            correlationID = correlationID - corrCount;
        }

        return null;
    }

    public long[] getConceptIDs(int stateID, int correlationID) {
        StateMachineCorrelation.TransitionCorrelation correlation = getCorrelation(stateID, correlationID);
        return correlation.getConceptIDs();
    }

    public long[] getEventIDs(int stateID, int correlationID) {
        StateMachineCorrelation.TransitionCorrelation correlation = getCorrelation(stateID, correlationID);
        return correlation.getEventIDs();
    }

    public TimeEvent[] getTimeEvents(int stateID, int correlationID) {
        StateMachineCorrelation.TransitionCorrelation correlation = getCorrelation(stateID, correlationID);
        return correlation.getTimeEvents();
    }

    public List getTimeEvents(int stateID) {
        StateVertex state = mModel.getStateVertex(stateID);
        StateMachineCorrelation smc = instance.getCorrelationStatuses();

        List tEvents = new LinkedList();
        TransitionLink[] tos = state.getToTransitions();
        for(int i = 0; i < tos.length; i++) {
            int corrCount = smc.getCorrelationCount(tos[i].getIndex());
            for(int j = 0; j < corrCount; j++) {
                TimeEvent[] corr = smc.getTimeEvents(tos[i].getIndex(), j);
                for(int k = 0; k < tos[i].getIndex(); k++) {
                    tEvents.add(corr[k]);
                }
            }
        }

        return tEvents;
    }
    public int[] calculatePathIDs() {
        BitSet signature = calculatePathSignature();
        BitSet matches = null;
        BitSet temp = (BitSet) signature.clone();
        List paths = mModel.getPaths();
        for(int i = paths.size() - 1; i >= 0; i--) {
            Path path = (Path) paths.get(i);

            // see if temp is a subset of a path by clearing all bits in temp
            // that are set in path, then checking that temp has zero bits set
            temp.andNot(path.signature);
            if(temp.cardinality() == 0) {
                if(matches == null) {
                    matches = new BitSet(paths.size());
                }

                matches.set(i);

                // only one exact match possible
                if(path.signature.cardinality() == signature.cardinality()) {
                    break;
                }
            }

            // clear and try the next path
            temp.clear();
            temp.or(signature);
        }

        if(matches == null || matches.cardinality() == 0) {
            return new int[0];
        }

        int[] pathIDs = new int[matches.cardinality()];
        int i = 0;
        for(int pathID = matches.nextSetBit(0); pathID >= 0; pathID = matches.nextSetBit(pathID + 1)) {
            pathIDs[i] = pathID;
            i++;
        }

        return pathIDs;
    }
}