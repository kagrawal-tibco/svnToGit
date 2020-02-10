package com.tibco.be.parser.codegen;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.RuleGrammar;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.impl.RuleFileParser;
import com.tibco.be.parser.semantic.CompilableDeclSymbolTable;
import com.tibco.be.parser.semantic.CompositeModelLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.NodeTypeVisitor;
import com.tibco.be.parser.semantic.RuleInfoSymbolTable;
import com.tibco.be.parser.semantic.SmapStratum;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateEnd;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateStart;
import com.tibco.cep.designtime.model.element.stategraph.StateSubMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.kernel.model.entity.Mutable;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.impl.IdentifierImpl;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineCompositeState;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineReferencedState;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyStateMachineCompositeStateBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyStateMachineReferencedStateBigIndex;
import com.tibco.cep.runtime.model.element.impl.property.simple.bigindex.PropertyStateMachineStateBigIndex;
import com.tibco.cep.studio.core.adapters.SymbolAdapter;
import com.tibco.cep.studio.core.adapters.mutable.MutableRuleAdapter;
import com.tibco.cep.studio.core.adapters.mutable.MutableSymbolAdapter;
import com.tibco.cep.util.CodegenFunctions;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 8, 2006
 * Time: 4:46:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateMachineClassGeneratorSmap extends BaseConceptClassGeneratorSmap {
	public static String STATE_MODEL_IF_PACKAGE = "com.tibco.cep.runtime.model.element.stategraph.";
	public static final String[] TIMEOUT_POLICY_STRINGS = {STATE_MODEL_IF_PACKAGE + "StateVertex.NO_ACTION_TIMEOUT_POLICY", STATE_MODEL_IF_PACKAGE + "StateVertex.NON_DETERMINISTIC_STATE_TIMEOUT_POLICY", STATE_MODEL_IF_PACKAGE + "StateVertex.DETERMINISTIC_STATE_POLICY"};
    public static final String ALLOW_FWD_CORRELATE = "TIBCO.BE.allowForwardCorrelation";
    private static final String BRK = CGConstants.BRK;
    private static final String MEMBER_ACCESS = "protected";
    private static final String TRANSITION_RULE_BASECLASS = "com.tibco.cep.runtime.model.element.stategraph.impl.GeneratedStateTransitionRule";
    public static final String TRANSITION_STATUSES = ModelNameUtil.escapeIdentifier("TransitionStatuses");

    protected final StateMachine sm;
    protected final StateComposite root;
    protected final StateMachineBlockLineBuffer smblb;
    protected final HashMap<StateEntity, String> memberNames;
    protected final int numStates;
    protected final Map<String, Map<String, int[]>> propInfoCache;
    protected final Concept parent;

    public StateMachineClassGeneratorSmap(StateMachine sm, Concept owner, JavaClassWriter cc, Properties oversizeStringConstants
            , Map ruleFnUsage, StateMachineBlockLineBuffer smblb, Ontology o, Map<String, Map<String, int[]>> propInfoCache)
    {
        super(oversizeStringConstants, ruleFnUsage, cc, o);
        this.sm = sm;
        parent = owner;
        root = sm.getMachineRoot();
        this.smblb = smblb;
        numStates = countStates(root);
        memberNames = new HashMap(numStates);
        propIds = new HashMap(numStates);
        this.propInfoCache = propInfoCache;
    }

    public static String propClassName(Concept cept, StateMachine sm) {
        return ModelNameUtil.generatedStateMachinePropertyName(cept, sm);
    }

    public static void makeStateMachineFile(JavaClassWriter parentCC, StateMachine sm, Concept owner, Properties oversizeStringConstants
            , Map ruleFnUsage, StateMachineBlockLineBuffer smblb, ConceptClassGeneratorSmap parentConceptGen
            , Ontology o, Map<String, Map<String, int[]>> propInfoCache) throws ModelException
    {
        JavaClassWriter cc = parentCC.createNestedClassWriter(propClassName(owner, sm),null);
        new StateMachineClassGeneratorSmap(sm, owner, cc, oversizeStringConstants, ruleFnUsage, smblb, o, propInfoCache).makeStateMachineFile();
    }

    protected void makeStateMachineFile() throws ModelException {
        if (sm.getTransitions() == null || sm.getTransitions().size() == 0) {
            throw new RuntimeException("StateMachine " + sm.getName() + " in " + parent.getFullPath() + " is incomplete");
        }

        smblb.setClassName(cc.getName());
        cc.setAccess("static public ");

        cc.setSuperClass(StateMachineConceptImpl.class.getName());
        createStateMachinePropertyIndex();
        addMetaProperties();
        addMembers();
        addGetMachineRoot();
        addGetProperties();
        //addSerializerDeserializer(cept,cc);

        addGetParentPropertyMethods();

        addGetType(RDFTnsFlavor.BE_NAMESPACE + parent.getNamespace() + parent.getName() + "/" + sm.getName() );
        addGetExpandedName();
        addConstructors(propClassName(parent, sm));
        addNewInstanceMethod();
        addGetSet();
        addGetPropertyWithHashCode();

        addGetNumProperties(numStates);
        addGetNumDirtyBits(numStates);

        addTransitions();
    }

    @Override
    protected String className() {
    	return genSMClassName();
    }
    
    private void createStateMachinePropertyIndex() {
        createStateMachinePropertyIndex(root, 0);
    }

    private int createStateMachinePropertyIndex(State state, int index) {
        String interfaceName = ModelNameUtil.generatedMemberClassName(getCachedStatePath(state));
        if (!propIds.containsKey(interfaceName)) {
            propIds.put(interfaceName, index++);
        }

        if (hasChildren(state)) {
            StateComposite group = (StateComposite) state;
            for(State childState : StateMachineClassGeneratorSmap.getChildren(group)) {
                index = createStateMachinePropertyIndex(childState, index);
            }
        }
        return index;
    }

    protected String getStateFromArray(State state) {
        return String.format(PROPS_ARRAY + "[%s]", getPropertyId(state));
    }

    protected Integer getPropertyId(State state) {
        String interfaceName = ModelNameUtil.generatedMemberClassName(getCachedStatePath(state));
        return propIds.get(interfaceName);
    }

    private String getPropertyInitializer(State state) {
        return getPropertyInitializer(state, "this");
    }

    private String getPropertyInitializer(State state, String subject) {
        return String.format("new %s(%s)", ModelNameUtil.generatedMemberClassName(getCachedStatePath(state)), subject);
    }

    //todo parse the default value with the parser to see if it is constant or an expression
    private static String getDefaultValue(PropertyDefinition pd) {
        String defVal = pd.getDefaultValue();
        if("".equals(defVal) || defVal == null) return null;
        switch(pd.getType()) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return Boolean.valueOf(defVal).toString();
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                try {
                    return Integer.toString(Integer.parseInt(defVal));
                } catch(NumberFormatException nfe) {
                    //todo debug code (enforce this in the gui?)
                    throw new IllegalArgumentException("NumberFormat exception while parsing integer default value for property definition: " + pd.getName());
                }
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                try {
                    return Double.toString(Double.parseDouble(defVal));
                } catch(NumberFormatException nfe) {
                    //todo debug code
                    throw new IllegalArgumentException("NumberFormat exception while parsing double default value for property definition: " + pd.getName());
                }
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return "\"" + defVal + "\"";
            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                //todo implement default values for concept properties
                return null;
            default:
                return null;
        }

    }

    private static void setSMRuleConcept(Rule rule, Concept concept) {
        String conceptPath = concept.getFullPath();

        final Symbols decls = rule.getDeclarations();
        if (decls.size() > 0) {
        	final SymbolAdapter symbol =  (SymbolAdapter) decls.values().iterator().next();
            MutableSymbolAdapter mutableSymbol = new MutableSymbolAdapter(symbol);
            mutableSymbol.setTypeInternal(conceptPath);
        }
    }

    private static MethodRecWriter genActionBody(JavaClassWriter propClass, String methodName, Concept concept, Rule rule, Properties oversizeStringConstants
    		, StateMachineBlockLineBuffer.StateBlockLineBuffer sblb, Ontology o
    		, Map<String, Map<String, int[]>> propInfoCache) 
    {
        StringReader reader = new StringReader(rule.getActionText());
        RuleGrammar parser = new RuleGrammar(reader);
        ActionOnlyParserClient client = new ActionOnlyParserClient();
        parser.setParserClient(client);

        try {
            parser.ActionBlock();
        } catch (ParseException error) {
            client.errors.add(RuleError.makeSyntaxError(error));
        }
        //todo report compile errors if client.errors.size() > 0;

        MutableRuleAdapter r = new MutableRuleAdapter(o, null, rule.getName(), false);
        r.setDeclarations(rule.getDeclarations());
        r.setConditionText(rule.getConditionText());
        r.setActionText(rule.getActionText());
        r.setPriority(rule.getPriority());
        setSMRuleConcept(r, concept);

        //do type checking / annotation
        NodeTypeVisitor ntv = new NodeTypeVisitor(new CompilableDeclSymbolTable(r, o), CompositeModelLookup.getDefaultLookupSet(o), new FunctionsCatalogLookup(rule.getOntology().getName()));
        ntv.populateNodeTypes(client.thenTrees.iterator());



        StateMachineBlockLineBuffer.MethodBlockLineBuffer mblb = sblb.getMethodBlockLineBufferMap().get(methodName);
        RuleInfo rinfo = new RuleInfo();
        rinfo.setPath(ModelUtils.convertPackageToPath(rule.getFullPath()));
        rinfo.setDeclarations(r.getDeclarations());
        rinfo.setThenTrees(client.thenTrees);
        if(mblb != null) {
            rinfo.getActionStratumMap().put(propClass.getClassName(),new SmapStratum("RSP"));
            rinfo.setRuleBlockBuffer(mblb);
            mblb.setRuleInfo(rinfo);
        }



        return RuleClassGeneratorSmap.makeActionMethod(propClass,methodName, rinfo, 
        		new RuleInfoSymbolTable(rinfo, rule.getOntology()), oversizeStringConstants,
        		rule.getRuleSet().getOntology(), propInfoCache);
    }

    private void generateTimeoutFunction(JavaClassWriter stateClass, State s) {
        RuleFunction rf = s.getTimeoutExpression();

        final Symbols symbols = rf.getArguments();
        final String identifier = (String) symbols.keySet().iterator().next();

        String args = '$' + identifier + "_args$";

        ArrayList errList = new ArrayList();
        MethodRecWriter timeoutExpr = RuleFunctionCodeGeneratorSmap.generateStaticMethodFromRuleFn(stateClass,rf, errList, oversizeStringConstants, ontology, propInfoCache);

        timeoutExpr.name = "getTimeout";
        timeoutExpr.setAccess("public");

        timeoutExpr.args.clear();
        timeoutExpr.addArg("java.lang.Object[]", args);
        timeoutExpr.setReturnType(long.class.getName());


        String cls = ModelNameUtil.modelPathToGeneratedClassName(parent.getFullPath());
        StringBuilder initializer = newStringBuilder();
        initializer.append(cls);
        initializer.append(' ');
        initializer.append(ModelNameUtil.generatedScopeVariableName(identifier));
        initializer.append(" =  (");
        initializer.append(cls);
        initializer.append(')');
        initializer.append(' ');
        initializer.append(args);
        initializer.append("[0];\n");
        initializer.append(timeoutExpr.body);

        timeoutExpr.setBody(initializer);
    }

    private static void appendTimeoutInitializers(State s, JavaClassWriter cc) {
        int units;
        if (s.getParent() == null) { // root state-- so use timeout units
            units = s.getOwnerStateMachine().getTimeoutUnits();
        } else {
            units = s.getTimeoutUnits();
        }

        // multiply out ttl units
        long multiplier = 1L;
        switch (units) {
            case Event.MILLISECONDS_UNITS:
                multiplier = 1L;
                break;
            case Event.SECONDS_UNITS:
                multiplier = 1000L;
                break;
            case Event.MINUTES_UNITS:
                multiplier = 60 * 1000L;
                break;
            case Event.HOURS_UNITS:
                multiplier = 60 * 60 * 1000L;
                break;
            case Event.DAYS_UNITS:
                multiplier = 24 * 60 * 60 * 1000L;
                break;
            default:
                multiplier = 1L;
        }

        MethodRecWriter m1 = cc.createMethod("public", "byte", "getTimeoutPolicy");
        m1.setBody("return " + TIMEOUT_POLICY_STRINGS[s.getTimeoutPolicy()] + ";" + BRK);

        MethodRecWriter m2 = cc.createMethod("public", "long", "getTimeoutMultiplier");
        m2.setBody("return (long)" + multiplier + ";" + BRK);
    }

    public static List<State> getChildren(StateComposite state) {
        if (state.isConcurrentState()) {
            return state.getRegions();
        } else {
            return state.getEntities();
        }
    }

    private void addStatePropertyClass(State state, StateMachineBlockLineBuffer.StateBlockLineBuffer sblb) throws ModelException {
        String propName = ModelNameUtil.generatedMemberClassName(getCachedStatePath(state));
        JavaClassWriter propClass = cc.createNestedClassWriter(propName,null);
        propClass.setAccess("static public");
        propClass.addInterface("com.tibco.cep.runtime.service.debug.SmartStepInto");
        int propIdx = getPropertyId(state);
        if(propIdx > MetaProperty.INDEX_LIMIT) {
        	if (hasChildren(state)) {
	            propClass.setSuperClass(PropertyStateMachineCompositeStateBigIndex.class.getName());
	        } else if (isReferenced(state)) {
	            propClass.setSuperClass(PropertyStateMachineReferencedStateBigIndex.class.getName());
	        } else {
	            propClass.setSuperClass(PropertyStateMachineStateBigIndex.class.getName());
	        }
        } else {
        	if (hasChildren(state)) {
	            propClass.setSuperClass(PropertyStateMachineCompositeState.class.getName());
	        } else if (isReferenced(state)) {
	            propClass.setSuperClass(PropertyStateMachineReferencedState.class.getName());
	        } else {
	            propClass.setSuperClass(PropertyStateMachineState.class.getName());
	        }
    	}

        //constructor w/o default value
        MethodRecWriter cons = propClass.createMethod(propName);
        cons.setAccess("public");
        cons.setReturnType("");
        cons.setBody(String.format("super(subject);%nsetPropertyIndex(%s);", getPropertyId(state)));

        cons.addArg(CGConstants.engineConceptInterface, "subject");

        MethodRecWriter getNameMethod = propClass.createMethod("public", "java.lang.String", "getName");
        getNameMethod.setBody("return \"" +  getCachedStatePath(state) + "\";");

        getNameMethod = propClass.createMethod("public", "java.lang.String", "getStateMachineName");
        getNameMethod.setBody("return \"" +  parent.getName() + "." + sm.getName() + "\";");

        MethodRec hasCompletion = propClass.createMethod("public", "boolean", "hasCompletion");
        if (hasCompletionTransition(parent,sm,state)) {
            hasCompletion.setBody("return true;");
        } else {
            hasCompletion.setBody("return false;");
        }

        MethodRec isEndState = propClass.createMethod("public", "boolean", "isEndState");
        if (!hasFromTransition(parent,sm,state)) {
            isEndState.setBody("return true;");
        } else {
            isEndState.setBody("return false;");
        }

        StringBuilder sourceText = newStringBuilder();
        /** Add action stuff */
        Rule r = state.getEntryAction(false);
        if (r != null && !ModelUtils.IsEmptyString(r.getActionText())) {
            genActionBody(propClass,"onEntry", parent, r, oversizeStringConstants, sblb, ontology, propInfoCache);
            sourceText.append("==== Entry Action ====\n");
            sourceText.append(r.getActionText()).append(BRK);
        }

        r = state.getExitAction(false);
        if (r != null && !ModelUtils.IsEmptyString(r.getActionText())) {
            genActionBody(propClass, "onExit", parent, r, oversizeStringConstants, sblb, ontology, propInfoCache);
            sourceText.append("==== Exit Action ====\n");
            sourceText.append(r.getActionText()).append(BRK);
        }

        r = state.getTimeoutAction(false);
        if (r != null && !ModelUtils.IsEmptyString(r.getActionText())) {
            genActionBody(propClass, "onTimeout", parent, r, oversizeStringConstants, sblb, ontology, propInfoCache);
            sourceText.append("==== Timeout Action ====\n");
            sourceText.append(r.getActionText()).append(BRK);
        }

        /**
         * Deterministic state policy
         */
        if (state.getTimeoutPolicy() == State.DETERMINISTIC_STATE_POLICY) {
            State go2 = state.getTimeoutState();
            if (go2 != null ) {
                MethodRecWriter goTo = propClass.createMethod("public", "void", "jumpToState");
                goTo.addArg("java.lang.Object []", "objects");
                String smClass= modelNameToSMClassName();

                StringBuilder sb = newStringBuilder();
                sb.append(smClass + " obj = " + "(" + smClass + ") getOwner();").append(BRK);

                sb.append("obj.get" + stateMember(go2) + "().enter(objects);").append(BRK);
                goTo.setBody(sb);
            }
        } else if (state.getTimeoutPolicy() == State.NON_DETERMINISTIC_STATE_TIMEOUT_POLICY) {
            MethodRecWriter goTo = propClass.createMethod("public", "void", "jumpToState");

            ModelNameUtil.modelPathToGeneratedClassName(parent.getFullPath());
            String smClass= modelNameToSMClassName();
            goTo.addArg("java.lang.Object []", "objects");

            StringBuilder sb = newStringBuilder();
            sb.append(smClass + " obj = " + "(" + smClass + ") getOwner();").append(BRK);

            for (Object o : getNextStates(parent, sm, state)) {
                State s = (State) o;
                sb.append("obj.get" + stateMember(s) + "().enter(objects,false);").append(BRK);
            }
            goTo.setBody(sb);
        }

        {
            String parentClass = ModelNameUtil.modelPathToGeneratedClassName(parent.getFullPath());
            String smClass = ModelNameUtil.modelNametoStateMachineClassName(parent, sm);

            MethodRec retract = propClass.createMethod("public", "void", "retractReferenced");
            StringBuilder buf = newStringBuilder();
            if (isReferenced(state)) {
                String c1 = ModelNameUtil.modelPathToGeneratedClassName(parent.getFullPath());
                String s1 = ModelNameUtil.modelNametoStateMachineClassName(parent, sm);

                buf.append(smClass + " obj = " + "(" + s1 + ") getOwner();").append(BRK);
                buf.append(parentClass + " var = " + "(" + c1 + ") obj.getParent();").append(BRK);

                StateMachine refMachine = getReferencedStateMachine(state);
                if (isReferencedVirtual(state)) {
                    buf.append("if (var." + ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine) + "() != null) ").append(BRK);
                    buf.append("if (var." + ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine) + "().getStateMachineConcept() != null) ").append(BRK);
                    buf.append("com.tibco.cep.runtime.session.RuleSessionManager.getCurrentRuleSession().retractObject(var." + ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine) + "().getStateMachineConcept(), true);").append(BRK);
                } else {
                    buf.append("if (var.get" + ModelNameUtil.generatedStateMachinePropertyMemberName(refMachine.getOwnerConcept(), refMachine) + "() != null) ").append(BRK);
                    buf.append("if (var.get" + ModelNameUtil.generatedStateMachinePropertyMemberName(refMachine.getOwnerConcept(), refMachine) + "().getStateMachineConcept() != null) ").append(BRK);
                    buf.append("com.tibco.cep.runtime.session.RuleSessionManager.getCurrentRuleSession().retractObject(var.get" + ModelNameUtil.generatedStateMachinePropertyMemberName(refMachine.getOwnerConcept(), refMachine) + "().getStateMachineConcept(), true);").append(BRK);
                }
            }
            buf.append("return;");
            retract.setBody(buf);

            MethodRecWriter exit = propClass.createMethod("public", "void", "exit");
            exit.addArg("java.lang.Object []", "objects");
            exit.addArg("boolean", "executeExitFunctions");
            exit.addArg("boolean", "notifyParent");

            {
                StateComposite parentState = (StateComposite) state.getParent();
                StringBuilder sb = newStringBuilder();
                sb.append(smClass + " obj = " + "(" + smClass + ") getOwner();").append(BRK);
                if ((state instanceof StateComposite) && ((StateComposite) state).isConcurrentState()) {
                    Iterator regions = getChildren((StateComposite) state).iterator();
                    boolean first = true;

                    while (regions.hasNext()) {
                        if (first) {
                            sb.append(" if (");
                            first = false;
                        } else {
                            sb.append(" && ");
                        }
                        State child = (State) regions.next();
                        sb.append("(obj.get" + stateMember(child) + "().isCompleteOrExited()) ");
                    }
                    sb.append("){").append(BRK);
                    sb.append("super.exit(objects,executeExitFunctions, notifyParent);").append(BRK);
                    if ((parentState != null) && !hasFromTransition(parent,sm,state)) {
                        sb.append("if (notifyParent) {").append(BRK);
                        sb.append("obj.get").append(stateMember(parentState)).append("().exit(objects,executeExitFunctions,notifyParent);").append(BRK);
                        sb.append("}").append(BRK);
                    }
                    sb.append("}").append(BRK);
                } else {
                    sb.append("super.exit(objects,executeExitFunctions, notifyParent);").append(BRK);
                    if ((parentState != null) && !hasFromTransition(parent, sm, state)) {
                        sb.append("if (notifyParent) {").append(BRK);
                        sb.append("obj.get").append(stateMember(parentState)).append("().exit(objects,executeExitFunctions,notifyParent);").append(BRK);
                        sb.append("}").append(BRK);
                    }
                }
                exit.setBody(sb);
            }

            if (!isReferenced(state)) {
                MethodRecWriter enter = propClass.createMethod("public", "void", "enter");
                enter.addArg("java.lang.Object []", "objects");
                StringBuilder sb = newStringBuilder();
                sb.append("super.enter(objects);").append(BRK);
                sb.append(smClass + " obj = " + "(" + smClass + ") getOwner();").append(BRK);

                if (hasChildren(state)) {
                    StateComposite sc = (StateComposite) state;
                    if (sc.isConcurrentState() || (sc.equals(root)) || sc.isRegion()) {
                        for (State startState : getStartStates(sc)) {
                            sb.append("obj.get").append(stateMember(startState)).append("().enter(objects);").append(BRK);
                        }
                    }
                }

                if (!hasFromTransition(parent, sm, state) && !hasChildren(state)) {
                    sb.append("this.exit(objects,true,true);").append(BRK);
                }

                enter.setBody(sb);

                enter = propClass.createMethod("public", "void", "enter");
                enter.addArg("java.lang.Object []", "objects");
                enter.addArg("boolean", "executeMethodsOnEntry");
                sb = newStringBuilder();
                sb.append("super.enter(objects,executeMethodsOnEntry);").append(BRK);
                sb.append(smClass + " obj = " + "(" + smClass + ") getOwner();").append(BRK);

                if (hasChildren(state)) {
                    StateComposite sc = (StateComposite) state;
                    sb.append("if (executeMethodsOnEntry) {").append(BRK);
                    if (sc.isConcurrentState() || (sc.equals(root)) || sc.isRegion()) {
                        for (State startState : getStartStates(sc)) {
                            sb.append("obj.get").append(stateMember(startState)).append("().enter(objects,executeMethodsOnEntry);").append(BRK);
                        }
                    }
                    sb.append("}").append(BRK);
                    sb.append("else {").append(BRK);
                    for (State startState : getStartStates(sc)) {
                        sb.append("obj.get").append(stateMember(startState)).append("().enter(objects,executeMethodsOnEntry);").append(BRK);
                    }
                    sb.append("}").append(BRK);
                }

                if (!hasFromTransition(parent, sm, state) && !hasChildren(state)) {
                    sb.append("this.exit(objects,executeMethodsOnEntry,true);").append(BRK);
                }
                enter.setBody(sb);
            }
        }

        if (hasChildren(state)) {
            String smClass= modelNameToSMClassName();

            StringBuilder sb = newStringBuilder();
            sb.append(smClass).append(" obj = ").append("(").append(smClass).append(") getOwner();").append(BRK);

            MethodRecWriter exitChildren = propClass.createMethod("public", "void", "exitChildren");
            exitChildren.addArg("java.lang.Object []", "objects");
            for (State s : getChildren((StateComposite) state)) {
                sb.append("if (obj.get").append(stateMember(s)).append("().isReady()) {").append(BRK);
                if (s instanceof StateComposite) {
                    sb.append("obj.get").append(stateMember(s)).append("().exitChildren(objects);").append(BRK);
                    sb.append("obj.get").append(stateMember(s)).append("().exit(objects,true,false);").append(BRK);
                } else {
                    sb.append("obj.get").append(stateMember(s)).append("().exit(objects,true,false);").append(BRK);
                }
                sb.append("obj.get").append(stateMember(s)).append("().complete(objects, false);").append(BRK);
                sb.append("}").append(BRK);
            }
            exitChildren.setBody(sb);

            MethodRecWriter rollbackChildren = propClass.createMethod("public", "void", "rollbackChildren");
            sb = newStringBuilder();
            sb.append(smClass).append(" obj = ").append("(").append(smClass).append(") getOwner();").append(BRK);
            for(State s : getChildren((StateComposite) state)) {
                sb.append("obj.get").append(stateMember(s)).append("().rollback();").append(BRK);
            }
            rollbackChildren.setBody(sb);

            MethodRecWriter timeoutChildren = propClass.createMethod("public", "void", "timeoutChildren");
            timeoutChildren.addArg("java.lang.Object[]", "objs");
            timeoutChildren.addArg("boolean", "reEnter");
            timeoutChildren.addArg(com.tibco.cep.runtime.session.RuleSession.class.getName(), "rs");

            sb = newStringBuilder();
            sb.append(smClass).append(" obj = ").append("(").append(smClass).append(") getOwner();").append(BRK);
            sb.append("if(!reEnter) {").append(BRK);
            sb.append("exitChildren(objs);").append(BRK);
            sb.append("} else {").append(BRK);

            for(State s : getChildren((StateComposite) state)) {
                String pfx = "obj.get" + stateMember(s) + "().";
                sb.append("if (").append(pfx).append("isReady()) {").append(BRK);
                if(hasChildren(s)) sb.append(pfx).append("timeoutChildren(objs, reEnter, rs);").append(BRK);
                sb.append(pfx).append("parentTimedOut(rs, objs);").append(BRK);
                sb.append("}").append(BRK);
            }
            sb.append("}").append(BRK);
            timeoutChildren.setBody(sb);
        }

        if (isReferenced(state)) {
            String parentClass= ModelNameUtil.modelPathToGeneratedClassName(parent.getFullPath());
            String smClass= modelNameToSMClassName();

            StringBuilder sb = newStringBuilder();


            sb.append(smClass).append(" obj = ").append("(").append(smClass).append(") getOwner();").append(BRK);
            sb.append(parentClass).append(" var = ").append("(").append(parentClass).append(") obj.getParent();").append(BRK);

            MethodRecWriter exitChildren = propClass.createMethod("public", "void", "exitChildren");
            exitChildren.addArg("java.lang.Object []", "objects");

            StateComposite refRoot= getReferencedRootState(state);
            StateMachine refMachine = getReferencedStateMachine(state);

            if (isReferencedVirtual(state)) {
                sb.append("var.").append(ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine)).append("().getStateMachineConcept().getMachineRoot().exitChildren(objects);").append(BRK);
                sb.append("var.").append(ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine)).append("().getStateMachineConcept().getMachineRoot().exit(objects, true, false);").append(BRK);
            }
            exitChildren.setBody(sb);

            MethodRecWriter enter = propClass.createMethod("public", "void", "enter");
            enter.addArg("java.lang.Object []", "objects");
            sb = newStringBuilder();
            sb.append("super.enter(objects);").append(BRK);
            sb.append(smClass).append(" obj = ").append("(").append(smClass).append(") getOwner();").append(BRK);
            sb.append(parentClass).append(" var = ").append("(").append(parentClass).append(") obj.getParent();").append(BRK);

            // Start the submachine
            if (isReferencedVirtual(state)) {
                sb.append("var.").append(ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine)).append("().startMachine();").append(BRK);
            } else {
                sb.append("var.get").append(ModelNameUtil.generatedStateMachinePropertyMemberName(refMachine.getOwnerConcept(),refMachine)).append("().startMachine();").append(BRK);
            }

            enter.setBody(sb);

            enter = propClass.createMethod("public", "void", "enter");
            enter.addArg("java.lang.Object []", "objects");
            enter.addArg("boolean", "executeEntryMethods");
            sb = newStringBuilder();
            sb.append("super.enter(objects,executeEntryMethods);").append(BRK);
            sb.append(smClass).append(" obj = ").append("(").append(smClass).append(") getOwner();").append(BRK);
            sb.append(parentClass).append(" var = ").append("(").append(parentClass).append(") obj.getParent();").append(BRK);

            // Start the submachine
            if (isReferencedVirtual(state)) {
                sb.append("var.").append(ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine)).append("().startMachine();").append(BRK);
            } else {
                sb.append("var.get").append(ModelNameUtil.generatedStateMachinePropertyMemberName(refMachine.getOwnerConcept(),refMachine)+ "().startMachine();").append(BRK);
            }
            enter.setBody(sb);
//            propClass.addMethod(enter);

            MethodRecWriter rollbackChildren = propClass.createMethod("public", "void", "rollbackChildren");
            sb = newStringBuilder();
            sb.append(smClass).append(" obj = ").append("(").append(smClass).append(") getOwner();").append(BRK);
            sb.append(parentClass).append(" var = ").append("(").append(parentClass).append(") obj.getParent();").append(BRK);

            if (isReferencedVirtual(state)) {
                sb.append("var.").append(ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine)).append("().getStateMachineConcept().getMachineRoot().rollbackChildren();").append(BRK);
            } else {
                sb.append("var.get").append(ModelNameUtil.generatedStateMachinePropertyMemberName(refMachine.getOwnerConcept(),refMachine)).append("().getMachine().get").append(ModelNameUtil.generatedMemberName(refRoot.getName())).append("().rollbackChildren();").append(BRK);
            }
            rollbackChildren.setBody(sb);
//            propClass.addMethod(rollbackChildren);

        }

        generateTimeoutFunction(propClass, state);

        appendTimeoutInitializers(state,propClass);

        /** Add the rollback states */
        MethodRecWriter rollbackMethod = propClass.createMethod("public", "void", "rollbackExclusiveStates");
        StringBuilder body = newStringBuilder();
        String smClass= modelNameToSMClassName();
        body.append(smClass + " obj = " + "(" + smClass + ") getOwner();").append(BRK);

        for (State exclusiveState : getExclusiveStates(parent, sm, state, state)) {
            if (!exclusiveState.equals(state)) {
                body.append("obj.get").append(stateMember(exclusiveState)).append("().rollback();").append(BRK);
            }
        }
        body.append("return;").append(BRK);
        rollbackMethod.setBody(body);

        if (sourceText.length() > 0) {
            propClass.setSourceText(sourceText.toString().trim());
        }
    }

    public static List<State> getStartStates(StateComposite state) {
        ArrayList<State> startStates = new ArrayList();
        if (state.isConcurrentState()) {
            startStates.addAll(getChildren(state));
        } else {
            for (State s : getChildren(state)) {
                if (s instanceof StateStart) {
                    startStates.add(s);
                }
            }
        }
        return startStates;
    }


    public final static boolean hasChildren(State state) {
        return ((state instanceof StateComposite) && !(state instanceof StateSubMachine));
    }


    public final static boolean isReferenced(State state) {
        return (state instanceof StateSubMachine);
    }


    public final static boolean isReferencedVirtual(State state) {
        if (state instanceof StateSubMachine) {
            return !((StateSubMachine) state).callExplicitly();
        } else {
            return false;
        }
    }


    public final static StateComposite getReferencedRootState(State state) {
        StateSubMachine refMachine = (StateSubMachine) state;
        return refMachine.getReferencedStateMachine().getMachineRoot();
    }


    public final static StateMachine getReferencedStateMachine(State state) {
        StateSubMachine refMachine = (StateSubMachine) state;
        return refMachine.getReferencedStateMachine();
    }


    private void addStateMember(State state, StateMachineBlockLineBuffer.StateBlockLineBuffer sblb) throws ModelException {
        addStatePropertyClass(state, sblb);

        String interfaceName= ModelNameUtil.generatedMemberClassName(getCachedStatePath(state));
        MethodRecWriter mr = cc.createMethod("public", interfaceName, nullOkMethodName(state));
        mr.setBody(String.format("return (%s) %s;%n", interfaceName, getStateFromArray(state)));

        if (hasChildren(state)) {
            StateComposite group = (StateComposite) state;
            for(State childState : getChildren(group)) {
                StateMachineBlockLineBuffer.StateBlockLineBuffer childStateLineBuffer = sblb.getChildStateBlocks().get(childState.getName());
                addStateMember(childState, childStateLineBuffer);
            }
        }
    }


    private void addGetMachineRoot() {
        MethodRecWriter mr= cc.createMethod("public", PropertyStateMachineCompositeState.class.getName(), "getMachineRoot");
        mr.setBody("return get" + stateMember(root) + "();" + BRK);
    }

    private void addMembers() throws ModelException {
        StateMachineBlockLineBuffer.StateBlockLineBuffer sblb = smblb.getStateMap().get(root.getName());
        addStateMember(root, sblb);
    }

    protected void addGetParentPropertyMethods() {
        addGetParentProperty(propClassName(parent, sm));
    }

    protected void addGetExpandedName() {
        String nameSpaceURI = RDFTnsFlavor.BE_NAMESPACE + parent.getNamespace() + parent.getName() + "/" + sm.getName();
        String localName    = sm.getName();

        addGetExpandedName(nameSpaceURI, localName);
    }

    // Puneet
    private void addGetSetState(State state, String[] initializers) {

        String name = stateMember(state);
        String className = ModelNameUtil.generatedMemberClassName(getCachedStatePath(state));

        // Add the Get Method
        String getterName = getGetMethodName(state);
        MethodRecWriter mr = cc.createMethod(getterName);
        mr.setAccess("public");
        mr.setReturnType(className);

        int propId = getPropertyId(state);
        mr.setBody(String.format("return (%s)getProperty(%s);", className, propId));

        mr = cc.createMethod(CGConstants.SET_PREFIX + name);
        mr.setAccess("public");
        mr.addArg(CGConstants.setArgumentTypes[RDFTypes.INTEGER_TYPEID], "value");
        mr.setBody(String.format("%s().%s(value);"
                , getterName, CGConstants.propAtomSetterNames[RDFTypes.INTEGER_TYPEID]));

        initializers[propId] = getPropertyInitializer(state);

        if (hasChildren(state)) {
            StateComposite group = (StateComposite) state;
            //if (!group.isConcurrentState()) {
            for (State childState : getChildren(group)) {
                addGetSetState(childState, initializers);
            }
            //}
        }
    }

    private void addGetSet() {
        String[] initializers = new String[numStates];
        addGetSetState(root, initializers);

        MethodRecWriter mr = cc.createMethod("public", PropertyStateMachineState.class.getName(), "newStateWithIndex");
        mr.addArg("int", "index");

        StringBuilder body = newStringBuilder();
        body.append("switch(index) {").append(BRK);
        for(int ii = 0; ii < initializers.length; ii++) {
            String init = initializers[ii];
            if(init != null) {
                body.append("case ").append(ii).append(":").append(BRK);
                body.append("return ").append(init).append(";");
                body.append(BRK);
            }
        }
        body.append("}").append(BRK);
        body.append("return null;");
        mr.setBody(body);
    }

    protected String stateMember(StateEntity state) {
        String ret = memberNames.get(state);
        if(ret == null) {
            ret = ModelNameUtil.generatedMemberName(getStatePath(state));
            memberNames.put(state, ret);
        }
        return ret;
    }

    //todo hacky
    private static int pfxSize = ModelNameUtil.generatedMemberName("").length();
    protected String getCachedStatePath(StateEntity state) {
        return stateMember(state).substring(pfxSize);
    }

    public static String getStatePath(StateEntity state) {
        ArrayList parents = new ArrayList();
        StringBuilder fullName= newStringBuilder();
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

    protected void addGetPropertyWithHashCode() {
        HashMap<Integer, Object[]> map = new HashMap(numStates);
        addGetPropertyWithHashCode(root, map);
        addGetPropertyWithHashCode(map);
    }

    protected void addGetPropertyWithHashCode(State state, HashMap<Integer, Object[]> map) {
        String externalName = getCachedStatePath(state);
        Integer hashCode = externalName.hashCode();
        //last element implements linked list of hashcode collisions
        Object[] arr = new Object[]{externalName, getPropertyId(state), map.get(hashCode)};
        map.put(hashCode, arr);

        if (hasChildren(state)) {
            StateComposite group = (StateComposite) state;
            for (State childState : getChildren(group)) {
                addGetPropertyWithHashCode(childState, map);
            }
        }
    }

    // Puneet
    private void addGetStateProperties(State state, StringBuilder body) {
        body.append(getGetMethodName(state)).append("()");
        body.append(" , ");

        if (hasChildren(state)) {
            StateComposite group = (StateComposite) state;
            //if (!group.isConcurrentState()) {
            for (State childState : getChildren(group)) {
                addGetStateProperties(childState, body);
            }
            //}
        }
    }

    private void addGetProperties() {
        StringBuilder body = new StringBuilder("return new " + CGConstants.propertyGenericInterface + "[] {");
        addGetStateProperties(root, body);
        body.append("};");

        MethodRecWriter mr = cc.createMethod("getProperties");
        mr.setAccess("public");
        mr.setReturnType(CGConstants.propertyGenericInterface + "[]");
        mr.setBody(body);
    }

    private static int countStates(State state) {
        int count = 1;
        if (hasChildren(state)) {
            StateComposite group = (StateComposite) state;
            for (State childState : getChildren(group)) {
                count += countStates(childState);
            }
        }
        return count;
    }

    private String getGetMethodName(State state) {
        return getGetMethodName(stateMember(state));
    }

    private void addTransitions() throws ModelException {
        LinkedHashSet rulesList = new LinkedHashSet();
        int idx = 0;
        for(StateTransition trans : (List<StateTransition>)sm.getTransitions()) {
            addTransitionMethod(trans);
            addRule(trans, idx, rulesList);
            ++idx;
        }
    }

    private void addRule(StateTransition trans, int transitionIndex, Set rules) {
        Rule r = trans.getGuardRule(false);
        boolean noGuardCondition=false;

        if (trans.isLambda()) {
            noGuardCondition=true;
        }
        if (isEmptyRule(trans)) {
            noGuardCondition=true;
        }

        MutableRuleAdapter rule = new MutableRuleAdapter(ontology, null, r.getName(), false);
        rule.setDefaultRuleSet(r.getRuleSet());
        if (!(trans.isLambda() || isEmptyRule(trans))) {
            rule.setDeclarations(r.getDeclarations());
            rule.setConditionText(r.getConditionText());
            rule.setActionText(r.getActionText());
            rule.setPriority(r.getPriority());
            rule.setRank(r.getRankPath());
        } else {
            Symbols decls= r.getDeclarations();
            if (decls.size() > 0) {
                // Ryan
                MutableSymbolAdapter mutableSymbol = new MutableSymbolAdapter((SymbolAdapter) decls.values().iterator().next());
                mutableSymbol.setTypeInternal(parent.getFullPath());
                rule.addDeclaration(mutableSymbol.getName(), mutableSymbol.getType());
            }
        }
        setSMRuleConcept(rule, parent);

        boolean genAsFwdCorr = false;
        // only fwd corr if the mainMachine allows it

        String oldcond = rule.getConditionText();
        StringReader sr = new StringReader(RuleBlockLineBuffer.fromRule(rule).toString());
        try {
            ArrayList rinfos = RuleFileParser.parseRuleFile(sr, ontology);
            for (int ii = 0; ii < rinfos.size(); ++ii) {

                RuleInfo rinfo = (RuleInfo) rinfos.get(ii);
                assert rinfo.getErrors() == null || rinfo.getErrors().size() <= 0 : assertionMsg(rinfo, rule);

                rinfo.setRuleBlockBuffer(RuleBlockLineBuffer.fromRule(rule));
                JavaClassWriter jClass = StateMachineRuleClassGeneratorSmap.makeRuleClass(cc, rinfo, trans,  transitionIndex, parent, sm, TRANSITION_RULE_BASECLASS, new RuleInfoSymbolTable(rinfo, ontology), oversizeStringConstants,ruleFnUsage, ontology, propInfoCache);
                if(!rinfo.getActionStratumMap().containsKey(jClass.getClassName()) && rinfo.getThenTrees().hasNext()){
                    rinfo.getActionStratumMap().put(jClass.getClassName(),new SmapStratum("RSP"));
                }

                StateMachineBlockLineBuffer.TransitionBlockLineBuffer tblb = smblb.getTransitionMap().get(rule.getName());
                tblb.setRuleInfo(rinfo);
                RuleBlockLineBuffer rblb =RuleBlockLineBuffer.fromRule(rule);
                tblb.setDeclareOffset(rblb.getDeclareOffset()-1);
                tblb.setWhenOffset(rblb.getWhenOffset()-1);
                tblb.setThenOffset(rblb.getThenOffset()-1);
                rinfo.setRuleBlockBuffer(tblb);

                if (!genAsFwdCorr) {
                    genGuardConditionClass(jClass, trans, transitionIndex, noGuardCondition);
                }
                addTransitionDependencies(jClass, trans);
                rules.add(jClass.name + ".class");
            }
        }
        catch (ParseException pe) {
            assert false : "ParseException while building state machine transition rules " +  pe.getMessage();
        }
        finally {
            rule.setConditionText(oldcond);
        }
    }

    private static String assertionMsg(RuleInfo rinfo, MutableRuleAdapter rule) {
	   	 StringBuilder msg = new StringBuilder("Errors in Transition Rule ").append(rinfo.getFullName());
		 for(RuleError err : rinfo.getErrors()) {
			 msg.append("\n");
			 msg.append(err.toString());
		 }
		 msg.append("\n").append(RuleBlockLineBuffer.fromRule(rule).toString());
		 return msg.toString();
    }

    protected void addTransitionDependencies(JavaClassWriter ruleClass, StateTransition trans) {
        MethodRecWriter getTarget = ruleClass.createMethod("public",Identifier.class.getName(),"getTarget");
        StringBuilder currBody = newStringBuilder();
        currBody.append("return new " + IdentifierImpl.class.getName() + "( ");
        currBody.append(CodegenFunctions.class.getName() + ".classForName(thisClass.getClassLoader(),\"");
        currBody.append(ModelNameUtil.modelPathToGeneratedClassName(parent.getFullPath()));
        currBody.append("\") , " + '"' + 1 + "\");").append(BRK);

        getTarget.setBody(currBody);

        State s = trans.getFromState();
        MethodRecWriter dr = ruleClass.createMethod("public","boolean","checkDependency");
        dr.addArg(Mutable.class.getName(), "obj");
        dr.addArg(" int [] ", "bitMap");
        currBody = newStringBuilder();
        currBody.append("boolean ret=false;").append(BRK);

        appendStateCheckDependency(currBody, parent, sm, s, true);

        if (isReferenced(s)) {
            StateMachine refMachine = getReferencedStateMachine(s);
            StateComposite refRoot = getReferencedRootState(s);
            Concept refParent = refMachine.getOwnerConcept();

            appendStateCheckDependency(currBody, refParent, refMachine, refRoot, false);

            if (isReferencedVirtual(s)) {
                ArrayList allVirtuals = new ArrayList();
                getVirtualStateMachines(parent, refMachine, allVirtuals, ontology);

                for (StateMachine vsm : (Collection<StateMachine>)allVirtuals) {
                    refRoot = vsm.getMachineRoot();
                    refParent = vsm.getOwnerConcept();
                    appendStateCheckDependency(currBody, refParent, vsm, refRoot, false);
                }
            }
        }

        currBody.append("return ret;").append(BRK);
        dr.setBody(currBody);
    }


    public JavaClassWriter genGuardConditionClass(JavaClassWriter jClass, StateTransition trans, int idx, boolean noGuardCondition) {
        Rule r = trans.getGuardRule(true);
        final Symbols decls = r.getDeclarations();
        final String identifier = (String) decls.keySet().iterator().next();
        String varName = ModelNameUtil.escapeIdentifier(identifier);
        String conceptClass = CGUtil.getFSName(parent) + ".class";

        String shortName = StateMachineRuleClassGeneratorSmap.getTransitionGuardConditionName(parent, trans, idx);

        JavaClassWriter jc = jClass.createNestedClassWriter(shortName,null);
        jc.setSuperClass(RuleClassGeneratorSmap.conditionImpl);
        jc.addInterface(com.tibco.cep.kernel.model.rule.StateGuardCondition.class.getName());
        jc.addInterface("com.tibco.cep.runtime.service.debug.SmartStepInto");

        MethodRecWriter mr = jc.createMethod(shortName);
        mr.setAccess("public");
        mr.setReturnType("");
        mr.addArg(RuleClassGeneratorSmap.ruleInterface, "rule");
        StringBuilder buf = newStringBuilder();
        buf.append("super(rule);").append(BRK);
        buf.append("m_identifiers = new " + RuleClassGeneratorSmap.identifierInterface + "[] { new " + RuleClassGeneratorSmap.smidentifierImpl +
                "(" + conceptClass + ", \"" + varName + "\")};");
        mr.setBody(buf);
        getGuardCondition(jc, r, trans,noGuardCondition);
        return jc;
    }

    private final static boolean hasFromTransition (Concept cept, StateMachine sm, State state) {
        Iterator allTransitions= sm.getTransitions().iterator();
        while (allTransitions.hasNext()) {
            StateTransition t= (StateTransition) allTransitions.next();
            if (t.getFromState().equals(state)) {
                return true;
            }
        }
        return false;
    }

    private final static boolean hasCompletionTransition (Concept cept, StateMachine sm, State state) {
        try {
            Iterator allTransitions= sm.getTransitions().iterator();
            while (allTransitions.hasNext()) {
                StateTransition t= (StateTransition) allTransitions.next();
                if (t.getFromState().equals(state)) {
                    if (isEmptyRule(t) || t.isLambda()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception .. " + cept.getName() + "," + sm.getName() + "," + state.getName());
            throw new RuntimeException(e);
        }
        return false;
    }

    private final static List getNextStates (Concept cept, StateMachine sm, State state) {
        ArrayList nextStates = new ArrayList();
        Iterator allTransitions= sm.getTransitions().iterator();
        while (allTransitions.hasNext()) {
            StateTransition t= (StateTransition) allTransitions.next();
            if (t.getFromState().equals(state)) {
                if (!nextStates.contains(t.getToState())) {
                    nextStates.add(t.getToState());
                }
            }
        }
        return nextStates;
    }

    private static boolean _isThereAPath(Concept cept, StateMachine sm, State from, State to, List transitions) {
        Iterator allTransitions= sm.getTransitions().iterator();
        while (allTransitions.hasNext()) {
            StateTransition t= (StateTransition) allTransitions.next();
            if (!transitions.contains(t)) {
                if (!t.getFromState().equals(t.getToState())) {
                    if (t.getFromState().equals(from)) {
                        transitions.add(t);
                        if (t.getToState().equals(to)) {
                            return true;
                        }
                        if (_isThereAPath(cept, sm, t.getToState(),to, transitions)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isThereAPath(Concept cept, StateMachine sm, State from, State to) {
        return _isThereAPath(cept, sm, from,to, new ArrayList());
    }
    private final static List _getExclusiveStates(Concept cept, StateMachine sm, State state, State targetState, List statesTraversed) {
        ArrayList exclusiveStates = new ArrayList();
        Iterator allTransitions= sm.getTransitions().iterator();
        while (allTransitions.hasNext()) {
            StateTransition t= (StateTransition) allTransitions.next();
            if (!t.getFromState().equals(t.getToState())) {
                if (t.getToState().equals(state)){
                    State fromState= t.getFromState();
                    if (!statesTraversed.contains(fromState)) {
                        Iterator nextStates= getNextStates(cept,sm,fromState).iterator();
                        while (nextStates.hasNext()) {
                            State s= (State)nextStates.next();
                            if (!s.equals(state)) {
                                if (!(isThereAPath(cept,sm,s,targetState) || isThereAPath(cept,sm,targetState,s))) {
                                    exclusiveStates.add(s);
                                }
                            }
                        }
                        statesTraversed.add(fromState);
                        exclusiveStates.addAll(_getExclusiveStates(cept,sm,fromState,targetState, statesTraversed));
                    }
                }
            }
        }
        return exclusiveStates;
    }

    private static List<State> getExclusiveStates(Concept cept, StateMachine sm, State state, State targetState) {
        ArrayList traversedStates = new ArrayList();
        return _getExclusiveStates(cept, sm, state, targetState,traversedStates);
    }

    public final static List getEndStates(Concept cept, StateMachine sm, StateComposite state) {
        ArrayList endStates = new ArrayList();
        if (!state.isConcurrentState()) {
            Iterator children= getChildren(state).iterator();
            while (children.hasNext()) {
                State child = (State) children.next();
                if (child instanceof StateEnd) {
                    endStates.add(child);
                } else {
                    if (!hasFromTransition(cept, sm, child)) {
                        endStates.add(child);
                    }
                }
            }
        } else {
            Iterator children= getChildren(state).iterator();
            while (children.hasNext()) {
                StateComposite child = (StateComposite) children.next();
                endStates.addAll(getEndStates(cept, sm, child));
            }
        }
        return endStates;
    }
    public final static String generatedRootForReferencedMachine(Concept cept, StateMachine refMachine) {
        return ModelNameUtil.generatedStateMachinePropertyMemberName(cept,refMachine) + "().getMachine().get" + ModelNameUtil.generatedMemberName(refMachine.getMachineRoot().getName()) + "()";
    }

    public final static String generatedPathForReferencedMachine(Concept cept, StateMachine refMachine) {
        return ModelNameUtil.generatedStateMachinePropertyMemberName(cept,refMachine) + "().getMachine()";
    }

    private final static void getVirtualStateMachines(Concept cept, StateMachine refMachine, ArrayList virtualSubs, Ontology o) {
        Collection allSubConcepts = cept.getSubConceptPaths();
        if (allSubConcepts != null) {
            Iterator allSubs = allSubConcepts.iterator();
            while (allSubs.hasNext()) {
                String subConceptPath= (String) allSubs.next();
                Concept subConcept= o.getConcept(subConceptPath);
                List allStateMachines = subConcept.getStateMachines();
                if (allStateMachines != null) {
                    for (int i=0; i < allStateMachines.size();i++) {
                        StateMachine sm= (StateMachine) allStateMachines.get(i);
                        if (sm.getName().equals(refMachine.getName())) {
                            virtualSubs.add(sm);
                        }
                    }
                }
                getVirtualStateMachines(subConcept,refMachine,virtualSubs, o);
            }
        }
    }

    public MethodRecWriter getGuardCondition(JavaClassWriter jc, Rule rule, StateTransition transition, boolean noGuardCondition) {
        final Symbols decls = rule.getDeclarations();
        final String varName = (String) decls.keySet().iterator().next();

        String transStatus = TRANSITION_STATUSES;

        MethodRecWriter mr = jc.createMethod("eval");
        mr.setAccess("public");
        mr.setReturnType("boolean");
        mr.addArg("java.lang.Object[]", "objects");

        StringBuilder buf = newStringBuilder();
        String ceptClass = CGUtil.getFSName(parent);
        buf.append(ceptClass + " " + varName + "= (" + ceptClass + ")objects[0];").append(BRK);

        String condition="";

        //condition +=
        
        // todo remove this when trying to allow dynamic dispatch / inheritance of State Machines
        if ((hasChildren(transition.getFromState()) && noGuardCondition)) {
            condition += StateMachineConceptImpl.class.getName() + " $sm=" + varName + ".get" + ModelNameUtil.generatedStateMachinePropertyMemberName(parent, sm) + "().getMachine();" + BRK;
            condition += "if ($sm instanceof " + ModelNameUtil.generatedStateMachineClassName(parent, sm) + ") {" + BRK;
            condition += PropertyStateMachineState.class.getName() + " $state=" + "((" + ModelNameUtil.generatedStateMachineClassName(parent, sm) + ")$sm).get_NullOK_" + stateMember(transition.getFromState()) + "();" + BRK;
            condition += "return $sm.isComplete($state);" + BRK;
            condition += "} " + BRK;
            //condition = "((" + smClass + ")" +  varName + ".get" + ModelNameUtil.generatedStateMachinePropertyMemberName(cept,sm) + "().getMachine()).get" + ModelNameUtil.generatedMemberName( getStatePath(transition.getFromState())) + "()." + "isComplete()";
        } else if ((isReferenced(transition.getFromState()))) {
            StateComposite sc = (StateComposite) transition.getFromState();
            if (isReferencedVirtual(transition.getFromState())) {
                StateComposite refRoot= getReferencedRootState(sc);
                StateMachine refMachine = getReferencedStateMachine(sc);
                condition += StateMachineConcept.class.getName() + " $childSM=" +   varName + "." + ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine) + "().getStateMachineConcept();" + BRK;
                condition += "if ($childSM != null) {" + BRK;

                if (noGuardCondition) {
                    condition += PropertyStateMachineState.class.getName() + " $childState=$childSM.getMachineRoot();" + BRK;
                    condition += "if ($childSM.isExited($childState)) {" + BRK;
                    condition += StateMachineConcept.class.getName() + " $sm=" +  varName + ".get" + genSMPropMemberName() + "().getMachine();" + BRK;
                    condition += "if ($sm instanceof " + genSMClassName() + ") {" + BRK;
                    condition += PropertyStateMachineState.class.getName() + " $state=" + "((" + genSMClassName() + ")$sm).get_NullOK_"  + ModelNameUtil.generatedMemberName( getCachedStatePath(transition.getFromState())) + "();" + BRK;
                    condition += "return $sm.isReady($state);" + BRK;
                    condition += "}" + BRK;
                    condition += "}" + BRK;
                } else {
                    condition +=StateMachineConcept.class.getName() + " $sm=" + varName + ".get" + genSMPropMemberName() + "().getMachine();" + BRK;
                    condition += "if ($sm instanceof " + genSMClassName() + ") {" + BRK;
                    condition += PropertyStateMachineState.class.getName() + " $state=" + "((" + genSMClassName() + ")$sm).get_NullOK_"  + ModelNameUtil.generatedMemberName( getCachedStatePath(transition.getFromState())) + "();" + BRK;
                    condition += "return $sm.isReady($state);" + BRK;
                    condition += "} " + BRK;
                }
                condition += "}" + BRK;
//                if (noGuardCondition) {
//                    condition = condition + " ( " +  varName +  "." + ModelNameUtil.modelNameToStateMachineVirtualMethod(refMachine) + "().getStateMachineConcept().getMachineRoot().isExited() ) ";
//                    condition = condition + "&& ((" + smClass + ")" +  varName + ".get" + ModelNameUtil.generatedStateMachinePropertyMemberName(cept,sm) + "().getMachine()).get" + ModelNameUtil.generatedMemberName( getStatePath(transition.getFromState())) + "()." + "isReady()";
//                } else {
//                    condition = condition + "((" + smClass + ")" +  varName + ".get" + ModelNameUtil.generatedStateMachinePropertyMemberName(cept,sm) + "().getMachine()).get" + ModelNameUtil.generatedMemberName( getStatePath(transition.getFromState())) + "()." + "isReady()";
//                }
            } else {
                StateComposite refRoot= getReferencedRootState(sc);
                StateMachine refMachine = getReferencedStateMachine(sc);

                condition += StateMachineConcept.class.getName() + " $childSM=" +   varName + ".get" + generatedPathForReferencedMachine(refMachine.getOwnerConcept(), refMachine) + ";" + BRK;
                condition += "if ($childSM != null) {" + BRK;

                if (noGuardCondition) {
                    condition += PropertyStateMachineState.class.getName() + " $childState=$childSM.getMachineRoot();" + BRK;
                    condition += "if ($childSM.isExited($childState)) {" + BRK;
                    condition += StateMachineConcept.class.getName() + " $sm=" +   varName + ".get" + genSMPropMemberName() + "().getMachine();" + BRK;
                    condition += "if ($sm instanceof " + genSMClassName() + ") {" + BRK;
                    condition += PropertyStateMachineState.class.getName() + " $state=" + "((" + genSMClassName() + ")$sm).get_NullOK_"  + ModelNameUtil.generatedMemberName( getCachedStatePath(transition.getFromState())) + "();" + BRK;
                    condition += "return $sm.isReady($state);" + BRK;
                    condition += "}" + BRK;
                    condition += "}" + BRK;
                } else {
                    condition +=StateMachineConcept.class.getName() + " $sm=" +  varName + ".get" + genSMPropMemberName() + "().getMachine();" + BRK;
                    condition += "if ($sm instanceof " + genSMClassName() + ") {" + BRK;
                    condition += PropertyStateMachineState.class.getName() + " $state=" + "((" + genSMClassName() + ")$sm).get_NullOK_"  + ModelNameUtil.generatedMemberName( getCachedStatePath(transition.getFromState())) + "();" + BRK;
                    condition += "return $sm.isReady($state);" + BRK;
                    condition += "} " + BRK;
                }
                condition += "}" + BRK;

//                condition = "( " + varName + ".get" + generatedPathForReferencedMachine(parent, refMachine) + " != null) && ";
//                if (noGuardCondition) {
//                    condition = condition + " ( " +  varName +  ".get" + generatedRootForReferencedMachine(parent, refMachine) + ".isExited() ) ";
//                    condition = condition + "&& ((" + smClass + ")" +  varName + ".get" + genSMPropMemberName() + "().getMachine()).get" + ModelNameUtil.generatedMemberName( getStatePath(transition.getFromState())) + "()." + "isReady()";
//                } else {
//                    condition = condition + "((" + smClass + ")" +  varName + ".get" + genSMPropMemberName() + "().getMachine()).get" + ModelNameUtil.generatedMemberName( getStatePath(transition.getFromState())) + "()." + "isReady()";
//                }
            }
        } else {
            condition += StateMachineConcept.class.getName() + " $sm=" + varName + ".get" + genSMPropMemberName() + "().getMachine();" + BRK;
            condition += "if ($sm instanceof " + genSMClassName() + ") {" + BRK;
            condition += PropertyStateMachineState.class.getName() + " $state=" + "((" + genSMClassName() + ")$sm).get_NullOK_"  + ModelNameUtil.generatedMemberName( getCachedStatePath(transition.getFromState())) + "();" + BRK;
            condition += "return $sm.isReady($state);" + BRK;
            condition += "} " + BRK;
//            condition = "((" + smClass + ")" +  varName + ".get" + genSMPropMemberName() + "().getMachine()).get" + ModelNameUtil.generatedMemberName( getStatePath(transition.getFromState())) + "()." + "isReady()";
        }

        condition += "return false;" + BRK;
        //buf.append("(" +  varName + ".get" + genSMPropMemberName() + "().getMachine() instanceof " + genSMClassName() + ") && ");
        buf.append(condition);
        //buf.append(");");
        mr.setBody(buf);

//        buf.append("return (");
//        buf.append("(" +  varName + ".get" + genSMPropMemberName() + "().getMachine() instanceof " + genSMClassName() + ") && ");
//        buf.append(condition);
//        buf.append(");");
//        mr.setBody(buf);
        return mr;
    }

    private void addTransitionMethod(StateTransition t) {
        boolean noGuardCondition=false;

        if (t.isLambda()) {
            noGuardCondition=true;
        }
        if (isEmptyRule(t)) {
            noGuardCondition=true;
        }

        MethodRecWriter transitionMethod = cc.createMethod("protected ", "void", ModelNameUtil.generatedMemberName(t.getName()));
        transitionMethod.addArg("java.lang.Object []", "objects");

        String ceptClass= ModelNameUtil.modelPathToGeneratedClassName(parent.getFullPath());

        StringBuilder sb = newStringBuilder();
        sb.append(ceptClass + " obj = " + "(" + ceptClass + ") objects[0];").append(BRK);

        StateComposite LCA = StateMachineRuleClassGeneratorSmap.findLCA(t);
        State baseFromState = t.getFromState();
        State baseToState = t.getToState();
        StateEntity fromState = baseFromState;
        StateEntity toState = baseToState;
        boolean self = baseFromState.equals(baseToState);

        //this could still work if .self() reset the state timeout and handled all the composite state cases but did not change the state status for performance reasons
        //        if (fromState == toState) {
        //            sb.append("this.get" + ModelNameUtil.generatedMemberName(StateMachineClassGenerator.getStatePath((State) fromState) + "().self(objects);").append(BRK));
        //        } else {

        while (true) {
            if ((fromState instanceof StateComposite) && (fromState.equals(baseFromState)) && !noGuardCondition ) {
                sb.append("this.get").append(ModelNameUtil.generatedMemberName(getCachedStatePath(fromState) + "().exitChildren(objects);")).append(BRK);
            }

            if (noGuardCondition && (fromState.equals(baseFromState))) {
                sb.append("this.get").append(ModelNameUtil.generatedMemberName(getCachedStatePath(fromState)))
                .append("().complete(objects,").append(self).append(");").append(BRK);
            } else {
                sb.append("this.get").append(ModelNameUtil.generatedMemberName(getCachedStatePath(fromState)))
                .append("().exit(objects,true,false,false,").append(self).append(");").append(BRK);
                sb.append("this.get").append(ModelNameUtil.generatedMemberName(getCachedStatePath(fromState)))
                .append("().complete(objects,").append(self).append(");").append(BRK);
            }
            fromState= fromState.getParent();
            if (fromState.equals(LCA)){
                break;
            }
        }
        ArrayList trailPath = new ArrayList();
        while (true) {
            trailPath.add(toState);
            toState=toState.getParent();
            if (toState.equals(LCA)) {
                break;
            }
        }
        for (int i = 0; i < trailPath.size(); i++) {
            toState=(StateEntity) trailPath.get(trailPath.size()-1-i);
            String selfStr = "";
            if(self && toState == baseToState) selfStr = ",true,true"; 
            sb.append("this.get").append(ModelNameUtil.generatedMemberName(getCachedStatePath(toState)))
                    .append("().enter(objects").append(selfStr).append(");").append(BRK);
        }
        if (hasChildren(t.getToState())) {
            StateComposite sc = (StateComposite) t.getToState();
            if (!sc.isConcurrentState()) {
                ArrayList startStates= (ArrayList) getStartStates(sc);
                State firstState=(State) startStates.get(0);
                sb.append("this.get").append(ModelNameUtil.generatedMemberName(getCachedStatePath(firstState) + "().enter(objects);")).append(BRK);
            }
        }
        //        }

        transitionMethod.setBody(sb);
        //        cc.addMethod(transitionMethod);
    }



    public static boolean isEmptyCondition(StateTransition st) {
        Rule rule = st.getGuardRule(false);
        if (rule == null) {
            return true;
        }

        String condition = rule.getConditionText();
        return ModelUtils.IsEmptyString(condition);
    }


    public static boolean isEmptyRule(StateTransition st) {
        boolean isEmpty = true;

        if (st.isLambda()) {
            return true;
        }
        Rule guardRule = st.getGuardRule(false);
        if (guardRule != null) {
            return guardRule.isEmptyRule();
        }

        return isEmpty;
    }

    private String modelNameToSMClassName() {
        return ModelNameUtil.modelNametoStateMachineClassName(parent,sm);
    }

    private String genSMClassName() {
        return ModelNameUtil.generatedStateMachineClassName(parent,sm);
    }

    private String genSMPropMemberName() {
        return ModelNameUtil.generatedStateMachinePropertyMemberName(parent,sm);
    }

    @Override
    protected void appendMetaPropsInitBody(StringBuilder bld) {
        String[] mprops = new String[numStates];
        int len = makeMetaPropsInitBody(root, mprops);
        appendMetaPropsInitBody(mprops, len, false, bld);
    }

    protected int makeMetaPropsInitBody(State state, String[] mprops) {
        String mprop;
        if(METAPROP_BODY_CONSTRUCTORS) {
            mprop = MetaPropertyEncoder.encodeAsConstructor(getCachedStatePath(state)
                    , PropertyStateMachineState.getHistoryPolicy_static(), 0, PropertyDefinition.PROPERTY_TYPE_INTEGER, false, null, false);
        } else {
            mprop = MetaPropertyEncoder.encode(getCachedStatePath(state)
                    , (byte)PropertyStateMachineState.getHistoryPolicy_static(), 0, MetaProperty.SM_STATE, false, null);
        }

        int len = mprop.length();
        mprops[getPropertyId(state)] = mprop;

        if (hasChildren(state)) {
            StateComposite group = (StateComposite) state;
            for(State childState : getChildren(group)) {
                len += makeMetaPropsInitBody(childState, mprops);
            }
        }
        return len;
    }

    protected void addNewInstanceMethod() {
        MethodRecWriter mr = cc.createMethod("newInstance");
        mr.setAccess("public");
        mr.setReturnType(StateMachineConceptImpl.class.getName());
        mr.setBody(String.format("return new %s();", genSMClassName()));
    }

    protected void appendStateCheckDependency(StringBuilder body, Concept parent, StateMachine sm, State state, boolean first) {
        String ceptClassName = ModelNameUtil.modelPathToGeneratedClassName(parent.getFullPath());
        body.append(" if (");
        if(!first) body.append("!ret && ");
        body.append("obj instanceof ").append(ceptClassName).append(") {").append(BRK);
        body.append(smPropType(sm)).append(" $var = ");
        body.append("((").append(ceptClassName).append(")obj).");
        body.append(nullOkMethodName(sm)).append("();").append(BRK);
        body.append("if ($var != null) {").append(BRK);
        body.append(ModelNameUtil.modelNametoStateMachineClassName(sm.getOwnerConcept(), sm));
        body.append(" $sm = $var.getMachine();").append(BRK);
        body.append("if($sm != null) {").append(BRK);
        if(first) body.append("ret = ");
        else body.append("return ");
        body.append("$var.isStateChanged(");
        body.append("$sm.").append(nullOkMethodName(state)).append("());").append(BRK);
        body.append("}").append(BRK);
        body.append("}").append(BRK);
        body.append("}").append(BRK);
    }

    protected String nullOkMethodName(State state) {
        return "get_NullOK_" + stateMember(state);
    }
}