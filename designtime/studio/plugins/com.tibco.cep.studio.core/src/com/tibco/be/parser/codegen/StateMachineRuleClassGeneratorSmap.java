package com.tibco.be.parser.codegen;


import static com.tibco.be.parser.codegen.CGConstants.BRK;
import static com.tibco.be.parser.codegen.CGConstants.actionImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.semantic.RLSymbolTable;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 11, 2006
 * Time: 12:31:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateMachineRuleClassGeneratorSmap extends RuleClassGeneratorSmap {

    StateTransition transition;
    Concept cept;
    int transitionIndex;
    int argIndex;


    public StateMachineRuleClassGeneratorSmap(JavaClassWriter cc, RuleInfo rinfo, StateTransition trans, Concept cept, String superClass
    		, RLSymbolTable rlSymbolTable, Properties oversizeStringConstants, Map ruleFnUsage, Ontology ontology,
    		Map<String, Map<String, int[]>> propInfoCache) 
    {
        //(JavaClassWriter, RuleInfo, RLSymbolTable,Properties, Ontology ontology, boolean nested) {
        super(cc,rinfo, rlSymbolTable, oversizeStringConstants, ruleFnUsage, ontology, propInfoCache);
        //rinfo.setName(SMClassGenerator.getTransitionRuleClassName(trans, index));
        ruleClass.name = shortName; //Overwrite the class name
        ruleClass.setAccess("public static");
        ruleClass.setSuperClass(superClass);
        ruleClass.addInterface("com.tibco.cep.runtime.service.debug.SmartStepInto");
        transition = trans;
        this.cept = cept;
    }

    public void setArgumentIndex(int index) {
        argIndex = index;
    }

    public void setTransitionIndex(int index) {
        transitionIndex = index;
    }

//    private static boolean extendsActionImpl(JavaClass jc) {
//        return actionImpl.equals(jc.superclass);
//    }
//
//    private static MethodRec getExecuteMethod(JavaClass actionClass) {
//        Iterator methods = actionClass.methods.iterator();
//        while(methods.hasNext()) {
//            MethodRec mr = (MethodRec) methods.next();
//            // todo-- check signature as well
//            if("execute".equals(mr.name)) {
//                return mr;
//            }
//        }
//
//        return null;
//    }

    public static String getTransitionRuleClassName(Concept concept, StateTransition trans, int index) {
        return getTransitionVariableName(concept, trans, index) + "_Rule";
    }


    public static String getTransitionGuardConditionName(Concept concept, StateTransition trans, int index) {
        return getTransitionVariableName(concept, trans, index) + "_gc";
    }


    public static String getTransitionGuardActionName(Concept concept, StateTransition trans, int index) {
        return getTransitionVariableName(concept, trans, index) + "_ga";
    }
    
    public static String getTransitionVariableName(Concept concept, StateTransition trans, int index) {
        //return "$" + index + escapePackage(concept) + '_' + concept.getName() + "Transition";
        //StateMachine sm = trans.getOwnerStateMachine();

        //The index may not be strictly necessary.
        //However states can have the same name as a transition,
        //and states already have INNER_CLASS_PREFIX as a prefix,
        //so adding the index after makes this name unique from a state.
        //No state name will accidentally conflict with this because
        //a state name (like all identifiers) cannot start with a number.
        return ModelNameUtil.INNER_CLASS_PREFIX + index + trans.getName();
    }

    public static JavaClassWriter makeRuleClass(JavaClassWriter cc, RuleInfo rinfo, StateTransition trans, int index, Concept cept, StateMachine sm
    		, String superClass, RLSymbolTable rlSymbolTable, Properties oversizeStringConstants, Map ruleFnUsage, Ontology o,
    		Map<String, Map<String, int[]>> propInfoCache) 
    {
        String oldPath = rinfo.getFullName();
        String newPath = cept.getFullPath() + "/" + ModelNameUtil.generatedStateMachineClassName(cept, sm) + "/" + getTransitionRuleClassName(cept, trans, index);
        rinfo.setPath(newPath);
        Rule guardRule = trans.getGuardRule(true);

        final Symbols decls = guardRule.getDeclarations();

        String guardCondition = "new " + getTransitionGuardConditionName(cept, trans, index) + "(this),";

        int argIndex = 0;

        StateMachineRuleClassGeneratorSmap rcg = new StateMachineRuleClassGeneratorSmap(cc,rinfo, trans, cept, superClass
        		, rlSymbolTable, oversizeStringConstants, ruleFnUsage, o, propInfoCache);
        rcg.setArgumentIndex(argIndex);
        rcg.setTransitionIndex(index);
        List<String> condClassNames = rcg.addConditionClasses();
        MethodRec mr = rcg.getActionExecMethod("action");

        StringBuilder sourceText = new StringBuilder();
        for(Iterator it2 = rinfo.getThenTrees(); it2.hasNext();) {
            sourceText.append(((RootNode)it2.next()).getSourceText());
        }
        rcg.ruleClass.setSourceText(sourceText.toString().trim());
        //rcg.addActionClass();

        rcg.addRuleConstructor(condClassNames, guardCondition);
        rcg.addGetDescription();
        JavaClassWriter jc= rcg.ruleClass;

        addTransitionMethod(jc,cept,trans);
        // Add the method to transition
        rcg.addThisClass();
        rinfo.setPath(oldPath);
        return jc;
    }

    private static void addTransitionMethod(JavaClassWriter cc, Concept cept, StateTransition t) {
        MethodRec transitionMethod = cc.createMethod("protected ", "void", "transition");
        transitionMethod.addArg("java.lang.Object []", "objects");

        String ceptClass= ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath());
        String smClass= ModelNameUtil.generatedStateMachineClassName(cept,t.getOwnerStateMachine());

        StringBuilder sb = new StringBuilder();

        sb.append(ceptClass + " obj = " + "(" + ceptClass + ") objects[0];" + BRK);
        sb.append(smClass   + " var = " + "(" + smClass   + ") obj.get" + ModelNameUtil.generatedStateMachinePropertyMemberName(cept,t.getOwnerStateMachine()) +  "().getMachine();" + BRK);
        sb.append("var." + ModelNameUtil.generatedMemberName(t.getName()) + "(objects);" + BRK);

        transitionMethod.setBody(sb);
//        cc.addMethod(transitionMethod);
    }
    public void addGetDescription() {
        MethodRec getDescription = ruleClass.createMethod("public", "java.lang.String", "getDescription");
        StringBuilder sb = new StringBuilder("return \"");
        StateMachine sm = transition.getOwnerStateMachine();
        Concept concept = sm.getOwnerConcept();
        State from = transition.getFromState();
        State to = transition.getToState();

        sb.append(concept.getFullPath()).
                append(".concept").
                append(Folder.FOLDER_SEPARATOR_CHAR).
                append(sm.getName()).
                append(Folder.FOLDER_SEPARATOR_CHAR).
                append(transition.getName()).
                append(": ").
                append(from.getName()).
                append("---->").
                append(to.getName()).
                append("\";");

        System.out.println(sb);

        getDescription.setBody(sb);
//        ruleClass.addMethod(getDescription);
    }

    private static int getConceptArgIndex(RuleInfo rinfo, String varName) {
        int cnt = 0;
        for (Iterator it = rinfo.getDeclarations().keySet().iterator(); it.hasNext(); ) {
            final String name = (String) it.next();
            if (name.equals(varName)) {
                return cnt;
            }
            ++cnt;
        }
        throw new RuntimeException("Concept variable " + varName + "does not exist in the rule. Please check the rule again");
    }

    protected void addRuleConstructor(List<String> condClassNames, String initialCondition)
    {
        MethodRec constructor = ruleClass.createMethod(shortName);
        constructor.setAccess("public");
        constructor.setReturnType("");
        //save currBody
        StringBuilder oldBody = currBody;
        LineBuffer oldBuffer = currentLinebuffer;
        //Process attributes
        currBody = new StringBuilder();
        currentLinebuffer = new LineBuffer();
//        String ruleName = ModelNameUtil.modelPathToGeneratedClassName(rinfo.getFullName());
        String ruleName = ModelNameUtil.modelPathToGeneratedClassName(rinfo.getFullName());
        this.currBody.append("super(\"" + ruleName + "\");" + BRK);
        this.currentLinebuffer.append("super(\"" + ruleName + "\");" + BRK);

        appendAttributeSetters(rinfo.getAttributes());

        removeUnusedScorecardsFromIds();
        currBody.append("m_identifiers = ");
        currentLinebuffer.append("m_identifiers = ");
        appendNewRuleIdentifierArray();
        currBody.append(";" + BRK);
        currentLinebuffer.append(";" + BRK);

        currBody.append("m_conditions = ");
        currentLinebuffer.append("m_conditions = ");
        appendNewConditionArray(condClassNames, initialCondition);
        currBody.append(";" + BRK);
        currentLinebuffer.append(";" + BRK);

        currBody.append(dependencyIndex + "[][] dependencyIndices = ");
        currentLinebuffer.append(dependencyIndex + "[][] dependencyIndices = ");
        appendNewRuleDependsOnArray();
        currBody.append(";" + BRK);
        currentLinebuffer.append(";" + BRK);

        currBody.append("setIdentifierDependencyBitArray(dependencyIndices);");
        currentLinebuffer.append("setIdentifierDependencyBitArray(dependencyIndices);");


        /*
        currBody.append("m_actions = ");
        appendNewActionArray(rinfo.getThenTrees(), guardAction);
        currBody.append(";" + BRK);
        */

        if(!useLineBuffer) {
            constructor.setBody(currBody);
        } else {
            constructor.setBody(currentLinebuffer.toString());
        }


//        ruleClass.addMethod(constructor);

        //restore currBody
        currBody = oldBody;
        currentLinebuffer = oldBuffer;
    }

    private void appendTransitionDependenciesForVirtualInvocations(Concept currentConcept, StateMachine refMachine) {
        Concept refConcept = refMachine.getOwnerConcept();
        String className = ModelNameUtil.modelPathToGeneratedClassName(refConcept.getFullPath());
        //getPropertyIndex_static uses this for the SM property name: sm.getOwnerConceptPath() + "." + sm.getName()
        String propName = refMachine.getOwnerConceptPath() + "." + refMachine.getName();
        appendNewDependencyIndex(className, propName);

        Collection allSubConcepts = currentConcept.getSubConceptPaths();
        if (allSubConcepts != null) {
            for (String subConceptPath : (Collection<String>)allSubConcepts) {
                Concept subConcept = ontology.getConcept(subConceptPath);
                List allStateMachines = subConcept.getStateMachines();
                if (allStateMachines != null) {
                    for (StateMachine sm : (Collection<StateMachine>)allStateMachines) {
                        if (sm.getName().equals(refMachine.getName())) {
                            className = ModelNameUtil.modelPathToGeneratedClassName(subConcept.getFullPath());
                            //getPropertyIndex_static uses this for the SM property name: sm.getOwnerConceptPath() + "." + sm.getName()
                            propName = sm.getOwnerConceptPath() + "." + sm.getName();
                            appendNewDependencyIndex(className, propName);
                        }
                    }
                }
                appendTransitionDependenciesForVirtualInvocations(subConcept, refMachine);
            }
        }
    }

    private void appendTransitionDependencies() {
        //boolean noGuardCondition=false;
        //if (StateMachineClassGeneratorSmap.isEmptyRule(transition) || StateMachineClassGeneratorSmap.isEmptyRule(transition)) {
        //    noGuardCondition=true;
        //}

        String className = ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath());
        StateMachine sm = transition.getOwnerStateMachine();
        //getPropertyIndex_static uses this for the SM property name: sm.getOwnerConceptPath() + "." + sm.getName()
        String propName = sm.getOwnerConceptPath() + "." + sm.getName();
        appendNewDependencyIndex(className, propName, smdependencyIndex);

        if (StateMachineClassGeneratorSmap.isReferenced(transition.getFromState())) {
            if (!StateMachineClassGeneratorSmap.isReferencedVirtual(transition.getFromState())) {
                StateMachine refMachine= StateMachineClassGeneratorSmap.getReferencedStateMachine(transition.getFromState());
                Concept refConcept = refMachine.getOwnerConcept();
                className= ModelNameUtil.modelPathToGeneratedClassName(refConcept.getFullPath());
                sm = refMachine;
                //getPropertyIndex_static uses this for the SM property name: sm.getOwnerConceptPath() + "." + sm.getName()
                propName = sm.getOwnerConceptPath() + "." + sm.getName();
                appendNewDependencyIndex(className, propName, smdependencyIndex);
            } else {
                StateMachine refMachine= StateMachineClassGeneratorSmap.getReferencedStateMachine(transition.getFromState());
                Concept currentConcept= transition.getOwnerStateMachine().getOwnerConcept();
                appendTransitionDependenciesForVirtualInvocations(currentConcept, refMachine);
            }
        }
    }

    protected void appendNewRuleDependsOnArray() {
//        currBody.append("new " + dependencyIndex + "[][] { ");
        currBody.append("{ ");
        currentLinebuffer.append("{ ");
        boolean firstOuter = true;
        boolean isSMVar=false;

        Rule guard = transition.getGuardRule(false);
        Symbols symbols = guard.getDeclarations();
        Set entries = symbols.entrySet();
        Iterator entriesIt = entries.iterator();
        Map.Entry e = (Map.Entry) entriesIt.next();
        Symbol symbol = (Symbol) e.getValue();
        String smVar = symbol.getName();

        for(Iterator ids = skipNonDependentScoreCardsIdsIterator(); ids.hasNext();) {
            if(!firstOuter || (firstOuter = false)) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
            String id = (String)ids.next();
            Object propertyDependencies = dependentProperties.get(id);
            String modelPath = identifierMap.getType(id);
//            isSMVar = id.equalsIgnoreCase(ModelNameUtil.getSMContextName((cept)));
            isSMVar = id.equals(smVar);

            //empty array _in the generated rule class_ means the identifier wasn't used in the conditino
            if(propertyDependencies == null) {
                if (isSMVar) {
                    currBody.append("new " + dependencyIndex + "[] { ");
                    currentLinebuffer.append("new " + dependencyIndex + "[] { ");
                    appendTransitionDependencies();
                    currBody.append("}" + BRK);
                    currentLinebuffer.append("}" + BRK);
                } else {
                    currBody.append("new " + dependencyIndex + "[] {}");
                    currentLinebuffer.append("new " + dependencyIndex + "[] {}");
                }
            }
            //null array _in the generated rule class_ means the usage was indefinite
            else if(propertyDependencies == INDEFINITE_USAGE) {
                currBody.append("null");
                currentLinebuffer.append("null");
            } else {
                currBody.append("new " + dependencyIndex + "[] { ");
                currentLinebuffer.append("new " + dependencyIndex + "[] { ");
                for(String propName : (Set<String>)propertyDependencies) {
                    //for scorecards, the id is the model path
                    //todo replace this w/ custom iterator?
                    if(modelPath == null && additionalDependentScorecards.contains(id)) modelPath = id;
                    String className = ModelNameUtil.modelPathToGeneratedClassName(modelPath);
                    appendNewDependencyIndex(className, propName);
                }
                if (isSMVar) {
                    appendTransitionDependencies();
                }
                currBody.append(" }");
                currentLinebuffer.append(" }");
            }
        }
        currBody.append(" }");
        currentLinebuffer.append(" }");
    }

    protected static StateComposite findLCA (StateTransition t) {
        StateComposite ta=(StateComposite)  t.getToState().getParent();
        StateComposite sa=(StateComposite)  t.getFromState().getParent();

        StateComposite LCA=null;
        if (ta.equals(sa)) {
            LCA=ta;
        } else {
            ta= (StateComposite) t.getToState().getParent();
            while (ta != null) {
                sa= (StateComposite) t.getFromState().getParent();

                while (sa != null) {
                    if (sa.equals(ta)){
                        LCA=ta;
                        break;
                    }
                    sa=(StateComposite) sa.getParent();
                }
                ta= (StateComposite) ta.getParent();
            }

        }
        return LCA;
    }

}