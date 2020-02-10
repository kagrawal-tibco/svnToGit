package com.tibco.be.parser.codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleInfo;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEnd;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateSimple;
import com.tibco.cep.designtime.model.element.stategraph.StateStart;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.core.adapters.SymbolAdapter;
import com.tibco.cep.studio.core.adapters.mutable.MutableRuleAdapter;
import com.tibco.cep.studio.core.adapters.mutable.MutableSymbolAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 2, 2009
 * Time: 6:17:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateMachineBlockLineBuffer extends RuleBlockLineBuffer {
    public static final String METHOD_ON_ENTRY_BLOCK = "onEntry";
    public static final String METHOD_ON_EXIT_BLOCK = "onExit";
    public static final String METHOD_ON_TIMEOUT_BLOCK = "onTimeout";
    protected Map<String, TransitionBlockLineBuffer> transitionList = new LinkedHashMap<String, TransitionBlockLineBuffer>();
    protected Map<String, StateBlockLineBuffer> stateList = new LinkedHashMap<String, StateBlockLineBuffer>();
    protected String className;
    protected Map<String, Integer> stateOffsetMap = new LinkedHashMap<String, Integer>();
    private Map<String, Integer> transitionOffsetMap = new LinkedHashMap<String, Integer>();
    protected Ontology o;

    /**
     * generate statmachine code blocks for all the statemachines in a concept
     * @param cept
     * @return
     * @throws Exception
     */
    public static Map<String, StateMachineBlockLineBuffer> fromConcept(Concept cept, Ontology o) throws Exception {
        Map<String, StateMachineBlockLineBuffer> smlbMap = new HashMap<String, StateMachineBlockLineBuffer>();
        
        StateMachine mainStateMachine = cept.getMainStateMachine();
    	if (mainStateMachine != null && cept.getStateMachines().contains(mainStateMachine)) {
//        if (cept.getMainStateMachine() != null) {
        	StateMachine sm = cept.getMainStateMachine();
        	StateMachineBlockLineBuffer smb = fromStateMachine(sm, o);
            smlbMap.put(sm.getName(), smb);            
        }
        if (cept.getStateMachines() != null) {
//        if (cept.getAllStateMachines() != null) {
        	Iterator allMachines = cept.getStateMachines().iterator();
//            Iterator allMachines = cept.getAllStateMachines().iterator();

            while (allMachines.hasNext()) {
                StateMachine sm = (StateMachine) allMachines.next();
                if (!sm.equals(cept.getMainStateMachine())) {
                	StateMachineBlockLineBuffer smb = fromStateMachine(sm, o);
                	smlbMap.put(sm.getName(), smb);
                }
            }
        }

        return smlbMap;
    }

    /**
     * generate statemachine code block for a given statemachine
     * @param sm
     * @return
     * @throws Exception
     */
    public static StateMachineBlockLineBuffer fromStateMachine(StateMachine sm, Ontology o) throws Exception {
        StateMachineBlockLineBuffer smblb = new StateMachineBlockLineBuffer(sm.getName(), sm.getFullPath(), o);
        smblb.addMembers(sm);
        smblb.addTransitions(sm);
        return smblb;
    }

    /**
     * constructor
     * @param name
     * @param stateMachinePath
     */

    private StateMachineBlockLineBuffer(String name, String stateMachinePath, Ontology ontology) {
        super(name, stateMachinePath);
        o = ontology;
    }

    protected static String className(Concept cept, StateMachine sm) {
        return cept.getName() + "$" + sm.getName();
    }

    public Map<String, StateBlockLineBuffer> getStateMap() {
        return stateList;
    }

    public Map<String, TransitionBlockLineBuffer> getTransitionMap() {
        return transitionList;
    }

    /**
     * get generated class name of the state machine
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     * set generated class name of the state machine
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * add transition blocks
     * @param sm
     */
    private void addTransitions(StateMachine sm) {
        List l = sm.getTransitions();
        Iterator itr = l.iterator();
        int idx = 0;

        while (itr.hasNext()) {
            StateTransition trans = (StateTransition) itr.next();
            addTransitionRule(trans, idx, sm);
            ++idx;
        }
    }

    private void addTransitionRule(StateTransition trans, int transitionIndex, StateMachine sm) {
        Rule r = trans.getGuardRule(false);
        MutableRuleAdapter rule = new MutableRuleAdapter(o, null, r.getName(), false);
        rule.setDefaultRuleSet(r.getRuleSet());
        if (!(trans.isLambda() || StateMachineClassGeneratorSmap.isEmptyRule(trans))) {
            rule.setDeclarations(r.getDeclarations());
            rule.setConditionText(r.getConditionText());
            rule.setActionText(r.getActionText());
            rule.setPriority(r.getPriority());
        } else {
            Symbols decls = r.getDeclarations();
            if (decls.size() > 0) {
            	// Ryan
//            	final CGMutableSymbolAdapter symbol = (CGMutableSymbolAdapter) decls.values().iterator().next();
                final Symbol symbol = (Symbol) decls.values().iterator().next();
                MutableSymbolAdapter mutableSymbol = new MutableSymbolAdapter(symbol);
                mutableSymbol.setTypeInternal(sm.getOwnerConcept().getFullPath());
                rule.addDeclaration(mutableSymbol.getName(), mutableSymbol.getType());
            }

        }
        setSMRuleConcept(rule, sm.getOwnerConcept());
        TransitionBlockLineBuffer tblb = TransitionBlockLineBuffer.fromRule(rule);
        tblb.setFromState(trans.getFromState().getName());
        tblb.setToState(trans.getToState().getName());
        tblb.setPriority(trans.getPriority());
        transitionList.put(rule.getName(), tblb);
    }

    /**
     * add state members
     * @param sm
     * @throws Exception
     */
    private void addMembers(StateMachine sm) throws Exception {
        StateComposite root = sm.getMachineRoot();
        StateBlockLineBuffer sblb = getStateMap().get(root.getName());
        if (sblb == null) {
            sblb = new StateBlockLineBuffer(root.getName(), ModelUtils.convertPackageToPath(root.getFullPath()), root);
            getStateMap().put(StateMachineClassGeneratorSmap.getStatePath(root), sblb);
        }
        addStateMember(sm, root, sblb);
    }

    private void addStateMember(StateMachine sm, State state, StateBlockLineBuffer sblb) throws Exception {
        String interfaceName = ModelNameUtil.generatedMemberClassName(StateMachineClassGeneratorSmap.getStatePath(state));
        addStatePropertyClass(sm, state, sblb);

        if (StateMachineClassGeneratorSmap.hasChildren(state)) {
            StateComposite group = (StateComposite) state;
            Iterator allSubStates = StateMachineClassGeneratorSmap.getChildren(group).iterator();
            while (allSubStates.hasNext()) {
                State childState = (State) allSubStates.next();
                StateBlockLineBuffer childStateLineBuffer = sblb.getChildStateBlocks().get(childState);
                if (childStateLineBuffer == null) {
                    childStateLineBuffer = new StateBlockLineBuffer(childState.getName(), ModelUtils.convertPackageToPath(childState.getFullPath()), childState);
                    sblb.getChildStateBlocks().put(childState.getName(), childStateLineBuffer);
                }
                addStateMember(sm, childState, childStateLineBuffer);
            }
        }
    }

    private void addStatePropertyClass(StateMachine sm, State state, StateBlockLineBuffer sblb) throws Exception {
        //onEntry
        Rule r = state.getEntryAction(false);
        if (r != null && !ModelUtils.IsEmptyString(r.getActionText())) {
            genActionBody("onEntry", r, sblb, sm.getOwnerConcept());
        }
        //onExit
        r = state.getExitAction(false);
        if (r != null && !ModelUtils.IsEmptyString(r.getActionText())) {
            genActionBody("onExit", r, sblb, sm.getOwnerConcept());
        }
        //onTimeout
        r = state.getTimeoutAction(false);
        if (r != null && !ModelUtils.IsEmptyString(r.getActionText())) {
            genActionBody("onTimeout", r, sblb, sm.getOwnerConcept());
        }

        sblb.setTimeoutPolicy(state.getTimeoutPolicy());

        if(state.getTimeoutState() != null) {
            sblb.setTimeoutStateStr(state.getTimeoutState().getName());
        }

        if(state.getDescription() != null ) {
            sblb.setDescription(state.getDescription());
        }

        if(state.getAlias() != null ) {
            sblb.setAlias(state.getAlias());
        }

    }

    /**
     * generate onEntry,onExit,onTimeout function blocks
     * @param method
     * @param rule
     * @param sblb
     * @param cept
     */
    private void genActionBody(String method, Rule rule, StateBlockLineBuffer sblb, Concept cept) {

        MutableRuleAdapter r = new MutableRuleAdapter(o, rule.getRuleSet(), rule.getName(), false);
        r.getExtendedProperties().put("method", method);
        r.setDeclarations(rule.getDeclarations());
        r.setConditionText(rule.getConditionText());
        r.setActionText(rule.getActionText());
        r.setPriority(rule.getPriority());
        setSMRuleConcept(r, cept);
        MethodBlockLineBuffer mblb = sblb.getMethodBlockLineBufferMap().get(method);
        if (mblb == null) {
            mblb = MethodBlockLineBuffer.fromRule(r);
            sblb.getMethodBlockLineBufferMap().put(method, mblb);
        }
    }

    private void setSMRuleConcept(Rule rule, Concept concept) {
        String conceptPath = concept.getFullPath();

        final Symbols decls = rule.getDeclarations();
        if (decls.size() > 0) {
            final SymbolAdapter symbol = (SymbolAdapter) decls.values().iterator().next();
			MutableSymbolAdapter mutableSymbol = new MutableSymbolAdapter(symbol);
			mutableSymbol.setTypeInternal(conceptPath);
        }
    }

    public String toIndentedString(boolean adjust) {
        LineBuffer lineBuffer = new LineBuffer();
        return toIndentedString(0, lineBuffer, adjust);
    }

    public String toIndentedString() {
        return toIndentedString(false);
    }

    public String toIndentedString(int tablevel, LineBuffer lineBuffer, boolean adjust) {
        String idstr;
        lineBuffer.append(indent("statemachine " + getName() + " {" + BRK, tablevel));
        int start = lineBuffer.getJavaLine();

        Map<String, StateBlockLineBuffer> stlist = getStateMap();
        for (Iterator<Map.Entry<String, StateBlockLineBuffer>> stIterator = stlist.entrySet().iterator(); stIterator.hasNext();) {
            Map.Entry<String, StateBlockLineBuffer> entry = stIterator.next();
            StateBlockLineBuffer sblb = entry.getValue();
            int stateOffset = lineBuffer.getJavaLine() + 1;
            getStateOffsetMap().put(getName(), stateOffset);
            sblb.toIndentedString(tablevel + 1, lineBuffer, adjust);
        }

        Map<String, TransitionBlockLineBuffer> trlist = getTransitionMap();
        for (Iterator<Map.Entry<String, TransitionBlockLineBuffer>> trIterator = trlist.entrySet().iterator(); trIterator.hasNext();) {
            Map.Entry<String, TransitionBlockLineBuffer> entry = trIterator.next();
            TransitionBlockLineBuffer trlb = entry.getValue();
            int transOffset = lineBuffer.getJavaLine() + 1;
            getTransitionOffsetMap().put(getName(), transOffset);
            trlb.toIndentedString(tablevel + 1, lineBuffer,adjust);
        }
        int end = lineBuffer.getJavaLine();
        if(adjust == true) {
            Block statemachine_Block = setBlock(getName(),start,end);
//            System.out.println(statemachine_Block);
        }
        lineBuffer.append(BRK + indent("}", tablevel));
        return lineBuffer.toString();
    }


    @Override
    public String toString() {
        return toIndentedString();
    }

    public LineBuffer toLineBuffer() {
        LineBuffer lineBuffer = new LineBuffer();
        return lineBuffer;
    }

    public Map<String, Integer> getStateOffsetMap() {
        return stateOffsetMap;
    }

    public Map<String, Integer> getTransitionOffsetMap() {
        return transitionOffsetMap;
    }

    /**
     * class StateBlockLineBuffer
     */
    public static class StateBlockLineBuffer extends RuleBlockLineBuffer {
        public static final int STATE_COMPOSITE = 1;
        public static final int STATE_CONCURRENT = 2;
        public static final int STATE_REGION = 3;
        public static final int STATE_PSEUDO_START = 4;
        public static final int STATE_PSEUDO_END = 5;
        public static final int STATE_SIMPLE = 6;

        public static final Map<Integer, String> stateTimeoutPolicyMap = new HashMap<Integer, String>();
        static {

            stateTimeoutPolicyMap.put(State.NO_ACTION_TIMEOUT_POLICY, "current");
            stateTimeoutPolicyMap.put(State.DETERMINISTIC_STATE_POLICY, "specified");
            stateTimeoutPolicyMap.put(State.NON_DETERMINISTIC_STATE_TIMEOUT_POLICY, "all");
        }
        public static final Map<Integer, String> stateTypeMap = new HashMap<Integer, String>();

        static {

            stateTypeMap.put(STATE_COMPOSITE, "composite");
            stateTypeMap.put(STATE_CONCURRENT, "concurrent");
            stateTypeMap.put(STATE_REGION, "region");
            stateTypeMap.put(STATE_PSEUDO_START, "pseudo-start");
            stateTypeMap.put(STATE_PSEUDO_END, "pseudo-end");
            stateTypeMap.put(STATE_SIMPLE, "simple");
        }

        public static final String METHOD_ON_ENTRY = "onEntry";
        public static final String METHOD_ON_EXIT = "onExit";
        public static final String METHOD_ON_TIMEOUT = "onTimeout";

        protected Map<String, StateBlockLineBuffer> childStates = new LinkedHashMap<String, StateBlockLineBuffer>();
        private Map<String, MethodBlockLineBuffer> methodBlockLineBufferMap = new HashMap<String, MethodBlockLineBuffer>();
        private int stateType = 0;
        private int timeoutPolicy;
        private String timeoutStateStr;
        private String description;
        private String alias;

        protected StateBlockLineBuffer(String name, String statePath, State state) {
            super(name, statePath);
            if (state instanceof StateComposite) {
                StateComposite sc = (StateComposite) state;
                if (sc.isConcurrentState()) {
                    this.stateType = STATE_CONCURRENT;
                } else if (sc.isRegion()) {
                    this.stateType = STATE_REGION;
                } else if (StateMachineClassGeneratorSmap.hasChildren(state)) {
                    this.stateType = STATE_COMPOSITE;
                }
            } else if (state instanceof StateSimple) {
                this.stateType = STATE_SIMPLE;
            } else if (state instanceof StateStart  ) {
                this.stateType = STATE_PSEUDO_START;
            }else if ( state instanceof StateEnd ) {
                this.stateType = STATE_PSEUDO_END;
            }

        }

        public int getStateType() {
            return stateType;
        }

        public String getStateTypeStr() {
            return stateTypeMap.get(stateType);
        }

        public int getTimeoutPolicy() {
            return timeoutPolicy;
        }

        public String getTimeoutPolicyStr() {
            return stateTimeoutPolicyMap.get(timeoutPolicy);
        }

        public String getTimeoutStateStr() {
            return timeoutStateStr;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public Map<String, StateBlockLineBuffer> getChildStateBlocks() {
            return childStates;
        }


        public static StateBlockLineBuffer fromRule(Rule rul) {
            throw new UnsupportedOperationException("unsupported");
        }

        @Override
        public int getThenOffset() {
            throw new UnsupportedOperationException("unsupported");
        }

        @Override
        public int getWhenOffset() {
            throw new UnsupportedOperationException("unsupported");
        }

        @Override
        public LineBuffer toLineBuffer() {
            LineBuffer lineBuffer = new LineBuffer();
            toIndentedString(0, lineBuffer, false);
            return lineBuffer;
        }

        @Override
        public String toString() {
            return toIndentedString();
        }

        public Map<String, MethodBlockLineBuffer> getMethodBlockLineBufferMap() {
            return methodBlockLineBufferMap;
        }

        public String toIndentedString() {
            LineBuffer lineBuffer = new LineBuffer();
            return toIndentedString(0, lineBuffer, false);
        }


        public String toIndentedString(int tablevel, LineBuffer lineBuffer, boolean adjust) {
            String idstr;
            lineBuffer.append(indent(BRK + "/* timeoutpolicy=\"" + getTimeoutPolicyStr() + "\"  */", tablevel));
            if(getName() != null) {
                lineBuffer.append(indent(BRK + "/* label=\"" + getName() + "\" */", tablevel));
            }
            if(getAlias() != null) {
                lineBuffer.append(indent(BRK + "/* alias=\"" + getAlias() + "\" */", tablevel));
            }
            if(timeoutStateStr != null) {
                lineBuffer.append(indent(BRK + "/* timeoutstate=\"" + getTimeoutStateStr() + "\" */", tablevel));
            }
            if(getDescription() != null && getDescription().length() > 0) {
                lineBuffer.append(indent(BRK + "/* description=\"" + getDescription() + "\" */", tablevel));
            }
            lineBuffer.append(indent(BRK + getStateTypeStr() + " state " + getName() + " {" + BRK, tablevel));
            int stateStart = lineBuffer.getJavaLine();
            for (Iterator<MethodBlockLineBuffer> it = methodBlockLineBufferMap.values().iterator(); it.hasNext();) {
                MethodBlockLineBuffer mblb = it.next();
                mblb.toIndentedString(tablevel, lineBuffer,adjust);
            }

            if (getChildStateBlocks().size() > 0) {
                for (Iterator<StateBlockLineBuffer> cit = getChildStateBlocks().values().iterator(); cit.hasNext();) {
                    StateBlockLineBuffer cblb = cit.next();
                    cblb.toIndentedString(tablevel + 1, lineBuffer, adjust);
                }
            }
            int stateEnd = lineBuffer.getJavaLine();
            if(adjust == true) {
                Block state_Block = setBlock(getName(),stateStart,stateEnd);
//                System.out.println(state_Block);
            }
            lineBuffer.append(BRK + indent("}", tablevel));
            return lineBuffer.toString();
        }

        public void setTimeoutPolicy(int timeoutPolicy) {
            this.timeoutPolicy = timeoutPolicy;
        }

        public void setTimeoutStateStr(String timeoutStateStr) {
            this.timeoutStateStr = timeoutStateStr;
        }
    }

    /**
     * class MethodBlockLineBuffer
     */
    public static class MethodBlockLineBuffer extends RuleBlockLineBuffer {
        protected RuleInfo ruleInfo;
        protected String methodName;
        private boolean adjusted = false;


        public MethodBlockLineBuffer(String name, String ruleSetPath) {
            super(name, ruleSetPath);
        }

        public static MethodBlockLineBuffer fromRule(Rule rul) {
            String path;
            if (rul.getRuleSet() != null) {
                path = rul.getRuleSet().getFullPath();
            } else {
                path = "";
            }

            MethodBlockLineBuffer rb = new MethodBlockLineBuffer(rul.getName(), ModelUtils.convertPackageToPath(rul.getFullPath()));
            rb.setMethodName((String) rul.getExtendedProperties().get("method"));
            //todo put the "priority" string constant somewhere else
            rb.addAttr("priority", String.valueOf(rul.getPriority()));
            rb.addAttr("$lastmod", '"' + rul.getLastModified() + '"');

            //have to cast to linked hash map because the state machine generation
            //won't work if the rule object doesn't return a LinkedHashMap
            for (Iterator it = rul.getDeclarations().values().iterator(); it.hasNext();) {
                final Symbol symbol = (Symbol) it.next();
                rb.addDecl(symbol.getType(), symbol.getName());
            }
            rb.setWhenBlock(rul.getConditionText());
            rb.setThenBlock(rul.getActionText());
            return rb;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public void setRuleInfo(RuleInfo ri) {
            this.ruleInfo = ri;
        }

        public RuleInfo getRuleInfo() {
            return ruleInfo;
        }

        @Override
        public LineBuffer toLineBuffer() {
            LineBuffer lineBuffer = new LineBuffer();
            toIndentedString(0, lineBuffer, false);
            return lineBuffer;
        }

        @Override
        public String toString() {
            return toIndentedString();
        }

        public String toIndentedString() {
            LineBuffer lineBuffer = new LineBuffer();
            return toIndentedString(0, lineBuffer, false);
        }

        @Override
        public int getThenOffset() {
            return thenOffset;
        }

        @Override
        public int getWhenOffset() {
            return -1;
        }

        public String toIndentedString(int tablevel, LineBuffer lineBuffer, boolean adjust) {
            String idstr;

            if(adjust == true && adjusted == false) {
                this.thenOffset = lineBuffer.getJavaLine() + 1;
                adjusted = true;
            }
            lineBuffer.append(indent(getMethodName() + "() {" + BRK, tablevel + 1));
            int start = lineBuffer.getJavaLine();
            idstr = indent(thenBlock, tablevel + 2);
            lineBuffer.append(idstr);
            int end = lineBuffer.getJavaLine();
            if (adjust == true) {
                Block method_Block = setBlock(getMethodName(), start, end);
//                System.out.println(method_Block);
            }
            lineBuffer.append(BRK + indent("}", tablevel + 1) + BRK);
            return lineBuffer.toString();
        }
    }

    /**
     * class TransitionBlockLineBuffer
     */
    public static class TransitionBlockLineBuffer extends RuleBlockLineBuffer {

        protected RuleInfo ruleInfo = new RuleInfo();
        protected String fromState;
        protected String toState;
        protected int priority;


        private TransitionBlockLineBuffer(String name, String ruleSetPath) {
            super(name, ruleSetPath);
        }

        public static TransitionBlockLineBuffer fromRule(Rule rul) {
            String path;
            if (rul.getRuleSet() != null) {
                path = rul.getRuleSet().getFullPath();
            } else {
                path = "";
            }

            TransitionBlockLineBuffer rb = new TransitionBlockLineBuffer(rul.getName(), ModelUtils.convertPackageToPath(rul.getFullPath()));
            //todo put the "priority" string constant somewhere else
            rb.addAttr("priority", String.valueOf(rul.getPriority()));
            rb.addAttr("$lastmod", '"' + rul.getLastModified() + '"');

            //have to cast to linked hash map because the state machine generation
            //won't work if the rule object doesn't return a LinkedHashMap
            for (Iterator it = rul.getDeclarations().values().iterator(); it.hasNext();) {
                final Symbol symbol = (Symbol) it.next();
                rb.addDecl(symbol.getType(), symbol.getName());
            }
            rb.setWhenBlock(rul.getConditionText());
            rb.setThenBlock(rul.getActionText());
            return rb;
        }

        public RuleInfo getRuleInfo() {
            return ruleInfo;
        }


        public void setRuleInfo(RuleInfo rinfo) {
            ruleInfo = rinfo;
        }

        public String getFromState() {
            return fromState;
        }

        public void setFromState(String fromState) {
            this.fromState = fromState;
        }

        public String getToState() {
            return toState;
        }

        public void setToState(String toState) {
            this.toState = toState;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        @Override
        public StringBuffer toStringBuffer() {
            return new StringBuffer(toIndentedString());
        }

        public String toIndentedString() {
            LineBuffer lineBuffer = new LineBuffer();
            return toIndentedString(0, lineBuffer,false);
        }

        public String toIndentedString(int tablevel, LineBuffer lineBuffer,boolean adjust) {
            String idstr;
            int start = 0;
            int end = 0;
            lineBuffer.append(indent(BRK + "/* fromState=\"" + getFromState() + "\" */", tablevel));
            lineBuffer.append(indent(BRK + "/* toState=\"" + getToState() + "\" */", tablevel));
            lineBuffer.append(indent(BRK + "/* priority=" + getPriority() + " */", tablevel));
            lineBuffer.append(indent(BRK + "transition " + getName() +"("+getFromState()+","+getToState()+")"+ " {" + BRK, tablevel));
            int trstart = lineBuffer.getJavaLine();
            
            if(adjust == true && this.adjustedDeclare == false ) {
                this.declareOffset = lineBuffer.getJavaLine()-this.declareOffset + 1;
                adjustedDeclare = true;
            }
            lineBuffer.append(indent("declare {", tablevel + 1) + BRK);
            start = lineBuffer.getJavaLine();
            idstr = indent(decls.toString(), tablevel + 2);
            lineBuffer.append(idstr);
            end = lineBuffer.getJavaLine();
            if (adjust == true) {
                Block declare_Block = setBlock(DECLARE_BLOCK, start, end);
//                System.out.println(declare_Block);
            }

            lineBuffer.append(indent("}", tablevel + 1) + BRK);
            if(adjust == true && this.adjustedWhen == false ) {
                this.whenOffset = lineBuffer.getJavaLine()-this.whenOffset + 1;
                adjustedWhen = true;
            }
            lineBuffer.append(indent("when {", tablevel + 1) + BRK);
            start = lineBuffer.getJavaLine();
            idstr = indent(whenBlock, 2);
            lineBuffer.append(idstr);
            end = lineBuffer.getJavaLine();
            if(adjust == true) {
                Block when_Block = setBlock(WHEN_BLOCK, start, end);
//                System.out.println(when_Block);
            }
            lineBuffer.append(BRK + indent("}", tablevel + 1) + BRK);
            if(adjust == true && this.adjustedThen == false ) {
                this.thenOffset = lineBuffer.getJavaLine()-this.thenOffset + 1;
                adjustedThen = true;
            }
            lineBuffer.append(indent("then {", tablevel + 1) + BRK);
            start = lineBuffer.getJavaLine();
            idstr = indent(thenBlock, tablevel + 2);
            lineBuffer.append(idstr);
            end = lineBuffer.getJavaLine();
            if (adjust == true) {
                Block then_Block = setBlock(THEN_BLOCK, start, end);
//                System.out.println(then_Block);
            }

            lineBuffer.append(BRK + indent("}", tablevel + 1) + BRK);

            int trend = lineBuffer.getJavaLine();
            if(adjust == true ) {
                Block transition_Block = setBlock(getName(),trstart,trend);
//                System.out.println(transition_Block);
            }

            lineBuffer.append(BRK + indent("}", tablevel) + BRK);
            return lineBuffer.toString();
        }

        @Override
        public int getThenOffset() {
            return this.thenOffset;
        }

        @Override
        public int getWhenOffset() {
            return this.whenOffset;
        }

        public int getDeclareOffset() {
            return super.getDeclareOffset();
        }

        public void setThenOffset(int thenOffset) {
            if(!adjustedThen) {
                this.thenOffset = thenOffset;
            }
        }

        public void setWhenOffset(int whenOffset) {
            if(!adjustedWhen) {
                this.whenOffset = whenOffset;
            }
        }

        public void setDeclareOffset(int declareOffset) {
            if(!adjustedDeclare) {
                this.declareOffset = declareOffset;
            }
        }
    }

    //////////////////////// contains //////////////////////////////////

    @Override
    public boolean contains(int lineno) {
        for (StateMachineBlockLineBuffer.StateBlockLineBuffer sblb : getStateMap().values()) {
            if (sblb.contains(lineno)) {
                return contains(sblb, lineno);
            }
        }
        for (StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb : getTransitionMap().values()) {
            if (tblb.contains(lineno)) {
                return contains(tblb, lineno);
            }
        }
        return false;
    }

    private boolean contains(StateMachineBlockLineBuffer.StateBlockLineBuffer sblb, int lineno) {

        for (StateMachineBlockLineBuffer.MethodBlockLineBuffer mblb : sblb.getMethodBlockLineBufferMap().values()) {
            if (mblb.contains(lineno)) {
                return contains(mblb, lineno);
            }
        }

        for (StateMachineBlockLineBuffer.StateBlockLineBuffer chsblb : sblb.getChildStateBlocks().values()) {
            if (chsblb.contains(lineno)) {
                return contains(chsblb, lineno);
            }
        }
        return false;
    }

    private boolean contains(StateMachineBlockLineBuffer.MethodBlockLineBuffer mblb, int lineno) {
        return mblb.contains(lineno);
    }

    private boolean contains(StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb, int lineno) {
        return tblb.contains(lineno);
    }

    /////////////////////// getEntityPath /////////////////////////////
    public String getEntityPath(int lineno) {
        for (StateMachineBlockLineBuffer.StateBlockLineBuffer sblb : getStateMap().values()) {
            if (sblb.contains(lineno)) {
                return getEntityPath(sblb, lineno);
            }
        }
        for (StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb : getTransitionMap().values()) {
            if (tblb.contains(lineno)) {
                return getEntityPath(tblb, lineno);
            }
        }
        return null;
    }

    private String getEntityPath(StateMachineBlockLineBuffer.StateBlockLineBuffer sblb, int lineno) {

        for (StateMachineBlockLineBuffer.MethodBlockLineBuffer mblb : sblb.getMethodBlockLineBufferMap().values()) {
            if (mblb.contains(lineno)) {
                return getEntityPath(mblb, lineno);
            }
        }

        for (StateMachineBlockLineBuffer.StateBlockLineBuffer chsblb : sblb.getChildStateBlocks().values()) {
            if (chsblb.contains(lineno)) {
                return getEntityPath(chsblb, lineno);
            }
        }
        return null;
    }

    private String getEntityPath(StateMachineBlockLineBuffer.MethodBlockLineBuffer mblb, int lineno) {
        if (mblb.contains(lineno)) {
            return mblb.getRuleSetPath();
        }
        return null;
    }

    private String getEntityPath(StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb, int lineno) {
        if (tblb.contains(lineno)) {
            return tblb.getRuleSetPath();
        }
        return null;
    }


    ///////////////////////////// containsEntity ///////////////////////////////////
     /////////////////////// getEntityPath /////////////////////////////
    public List<String> getEntityList() {
        List entityList = new ArrayList();
        entityList.add(getRuleSetPath());
        for (StateMachineBlockLineBuffer.StateBlockLineBuffer sblb : getStateMap().values()) {
            getEntityList(sblb, entityList);
        }
        for (StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb : getTransitionMap().values()) {
            getEntityList(tblb, entityList);
        }
        return entityList;
    }

    private void getEntityList(StateMachineBlockLineBuffer.StateBlockLineBuffer sblb, List<String> entityList) {
        entityList.add(getRuleSetPath());
        for (StateMachineBlockLineBuffer.MethodBlockLineBuffer mblb : sblb.getMethodBlockLineBufferMap().values()) {
            getEntityList(mblb, entityList);
        }

        for (StateMachineBlockLineBuffer.StateBlockLineBuffer chsblb : sblb.getChildStateBlocks().values()) {
            entityList.add(chsblb.getRuleSetPath());
            getEntityList(chsblb,entityList);
        }
        return;
    }

    private void getEntityList(StateMachineBlockLineBuffer.MethodBlockLineBuffer mblb, List<String> entityList) {
        entityList.add(mblb.getRuleSetPath());
        return;
    }

    private void getEntityList(StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb,List<String> entityList) {
        entityList.add(tblb.getRuleSetPath());
        return;
    }



}
