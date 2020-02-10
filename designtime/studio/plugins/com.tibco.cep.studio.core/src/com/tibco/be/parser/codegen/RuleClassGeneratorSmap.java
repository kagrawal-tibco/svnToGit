package com.tibco.be.parser.codegen;


import static com.tibco.be.parser.RuleGrammarConstants.ARRAY_ACCESS;
import static com.tibco.be.parser.RuleGrammarConstants.ARRAY_ALLOCATOR;
import static com.tibco.be.parser.RuleGrammarConstants.ARRAY_LITERAL;
import static com.tibco.be.parser.RuleGrammarConstants.BANG;
import static com.tibco.be.parser.RuleGrammarConstants.BLOCK_STATEMENT;
import static com.tibco.be.parser.RuleGrammarConstants.CATCH;
import static com.tibco.be.parser.RuleGrammarConstants.DECR;
import static com.tibco.be.parser.RuleGrammarConstants.EQ;
import static com.tibco.be.parser.RuleGrammarConstants.EXPRESSION_NAME;
import static com.tibco.be.parser.RuleGrammarConstants.FINALLY;
import static com.tibco.be.parser.RuleGrammarConstants.FOR;
import static com.tibco.be.parser.RuleGrammarConstants.GE;
import static com.tibco.be.parser.RuleGrammarConstants.GT;
import static com.tibco.be.parser.RuleGrammarConstants.IDENTIFIER;
import static com.tibco.be.parser.RuleGrammarConstants.IF;
import static com.tibco.be.parser.RuleGrammarConstants.INCR;
import static com.tibco.be.parser.RuleGrammarConstants.INSTANCEOF;
import static com.tibco.be.parser.RuleGrammarConstants.LASTMOD;
import static com.tibco.be.parser.RuleGrammarConstants.LE;
import static com.tibco.be.parser.RuleGrammarConstants.LINE_STATEMENT;
import static com.tibco.be.parser.RuleGrammarConstants.LT;
import static com.tibco.be.parser.RuleGrammarConstants.MINUS;
import static com.tibco.be.parser.RuleGrammarConstants.NE;
import static com.tibco.be.parser.RuleGrammarConstants.PLUS;
import static com.tibco.be.parser.RuleGrammarConstants.POST_DECR;
import static com.tibco.be.parser.RuleGrammarConstants.POST_INCR;
import static com.tibco.be.parser.RuleGrammarConstants.RETURN;
import static com.tibco.be.parser.RuleGrammarConstants.THROW;
import static com.tibco.be.parser.RuleGrammarConstants.TRY;
import static com.tibco.be.parser.RuleGrammarConstants.WHILE;
import static com.tibco.be.parser.codegen.CGConstants.BRK;
import static com.tibco.be.parser.codegen.CGConstants.GET_PREFIX;
import static com.tibco.be.parser.codegen.CGConstants.SET_PREFIX;
import static com.tibco.be.parser.codegen.CGConstants.actionImpl;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventCategoryAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventCategoryAttrName;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventMessageAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventMessageAttrName;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventTypeAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventTypeAttrName;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventRuleUriAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventRuleUriAttrName;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventRuleScopeAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.advisoryEventRuleScopeAttrName;
import static com.tibco.be.parser.codegen.CGConstants.beExceptionCauseAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.beExceptionCauseAttrName;
import static com.tibco.be.parser.codegen.CGConstants.beExceptionErrorTypeAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.beExceptionErrorTypeAttrName;
import static com.tibco.be.parser.codegen.CGConstants.beExceptionMessageAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.beExceptionMessageAttrName;
import static com.tibco.be.parser.codegen.CGConstants.beExceptionStackTraceAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.beExceptionStackTraceAttrName;
import static com.tibco.be.parser.codegen.CGConstants.containedConceptParentAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.containedConceptParentAttrName;
import static com.tibco.be.parser.codegen.CGConstants.engineExceptionInterface;
import static com.tibco.be.parser.codegen.CGConstants.entityExtIdAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.entityExtIdAttrName;
import static com.tibco.be.parser.codegen.CGConstants.entityIdAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.entityIdAttrName;
import static com.tibco.be.parser.codegen.CGConstants.eventTtlAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.eventTtlAttrName;
import static com.tibco.be.parser.codegen.CGConstants.lengthAttrName;
import static com.tibco.be.parser.codegen.CGConstants.oversizeStringConstantGetter;
import static com.tibco.be.parser.codegen.CGConstants.primitiveArrayLengthAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.propertyArrayElementGetter;
import static com.tibco.be.parser.codegen.CGConstants.propertyArrayLengthAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.propertyAtomInterfaceFSClassNames;
import static com.tibco.be.parser.codegen.CGConstants.propertyAtomIsSetAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.propertyAtomIsSetAttrName;
import static com.tibco.be.parser.codegen.CGConstants.propertyHistorySizeGetter;
import static com.tibco.be.parser.codegen.CGConstants.propertyHistorySizeName;
import static com.tibco.be.parser.codegen.CGConstants.propertySubjectAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.propertySubjectAttrName;
import static com.tibco.be.parser.codegen.CGConstants.setArgumentTypes;
import static com.tibco.be.parser.codegen.CGConstants.simpleEventPayloadAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.simpleEventPayloadAttrName;
import static com.tibco.be.parser.codegen.CGConstants.simpleEventSOAPActionAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.simpleEventSOAPActionAttrName;
import static com.tibco.be.parser.codegen.CGConstants.stringLengthAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.timeEventClosureAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.timeEventClosureAttrName;
import static com.tibco.be.parser.codegen.CGConstants.timeEventIntervalAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.timeEventIntervalAttrName;
import static com.tibco.be.parser.codegen.CGConstants.timeEventScheduledTimeAttrGetter;
import static com.tibco.be.parser.codegen.CGConstants.timeEventScheduledTimeAttrName;
import static com.tibco.be.parser.codegen.CoreCGConstants.boxUnboxed;
import static com.tibco.be.parser.codegen.CoreCGConstants.dateTimeEQ;
import static com.tibco.be.parser.codegen.CoreCGConstants.dateTimeGE;
import static com.tibco.be.parser.codegen.CoreCGConstants.dateTimeGT;
import static com.tibco.be.parser.codegen.CoreCGConstants.dateTimeLE;
import static com.tibco.be.parser.codegen.CoreCGConstants.dateTimeLT;
import static com.tibco.be.parser.codegen.CoreCGConstants.entityEQ;
import static com.tibco.be.parser.codegen.CoreCGConstants.numberToDouble;
import static com.tibco.be.parser.codegen.CoreCGConstants.numberToInteger;
import static com.tibco.be.parser.codegen.CoreCGConstants.numberToLong;
import static com.tibco.be.parser.codegen.CoreCGConstants.propertyEqualsNull;
import static com.tibco.be.parser.codegen.CoreCGConstants.stringEQ;
import static com.tibco.be.parser.codegen.CoreCGConstants.stringGE;
import static com.tibco.be.parser.codegen.CoreCGConstants.stringGT;
import static com.tibco.be.parser.codegen.CoreCGConstants.stringLE;
import static com.tibco.be.parser.codegen.CoreCGConstants.stringLT;
import static com.tibco.be.parser.codegen.CoreCGConstants.toString;
import static com.tibco.be.parser.codegen.CoreCGConstants.unboxBoxed;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.ConceptModelFunction;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.be.model.functions.impl.ModelRuleFunction;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.Token;
import com.tibco.be.parser.TokenUtils;
import com.tibco.be.parser.impl.RuleTemplateInfo;
import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.RLSymbolTable;
import com.tibco.be.parser.semantic.SmapStratum;
import com.tibco.be.parser.tree.DeclarationNode;
import com.tibco.be.parser.tree.FunctionNode;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeType.NodeTypeFlag;
import com.tibco.be.parser.tree.NodeType.TypeContext;
import com.tibco.be.parser.tree.NodeVisitor;
import com.tibco.be.parser.tree.ProductionNode;
import com.tibco.be.parser.tree.ProductionNodeListNode;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.SourceType;
import com.tibco.be.parser.tree.TemplatedDeclarationNode;
import com.tibco.be.parser.tree.TemplatedNode;
import com.tibco.be.parser.tree.TemplatedProductionNode;
import com.tibco.be.parser.tree.TypeNode;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.event.impl.EventImpl;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.mapper.codegen.UnsupportedXsltMappingException;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.exception.impl.BEExceptionImpl;
import com.tibco.cep.studio.core.functions.model.EMFEventModelFunction_Create;
import com.tibco.cep.util.CodegenFunctions;
import com.tibco.cep.util.ExcludeScorecardsCondition;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 6, 2004
 * Time: 4:25:03 PM
 * To change this template use File | Settings | File Templates.
 */

public class RuleClassGeneratorSmap implements NodeVisitor {
    protected static final String ruleSuperClassImpl = com.tibco.cep.kernel.model.rule.impl.RuleImpl.class.getName();
    protected static final String rtSuperClassImpl = "com.tibco.cep.interpreter.template.TemplatedRule";
    protected static final String ruleInterface = com.tibco.cep.kernel.model.rule.Rule.class.getName();
    protected static final String identifierImpl = com.tibco.cep.kernel.model.rule.impl.IdentifierImpl.class.getName();
    protected static final String smidentifierImpl = com.tibco.cep.kernel.model.rule.impl.StateIdentifierImpl.class.getName();
    protected static final String conditionImpl = com.tibco.cep.kernel.model.rule.impl.ConditionImpl.class.getName();
    protected static final String equivConditionImpl = com.tibco.cep.kernel.model.rule.impl.EquivalentCondition.class.getName();
    protected static final String negatedEquivConditionImpl = com.tibco.cep.kernel.model.rule.impl.NegatedEquivalentCondition.class.getName();
    protected static final String identifierInterface =com.tibco.cep.kernel.model.rule.Identifier.class.getName();
    protected static final String conditionInterface = com.tibco.cep.kernel.model.rule.Condition.class.getName();
    protected static final String actionInterface = com.tibco.cep.kernel.model.rule.Action.class.getName();
    //protected static final String dependencyIndex = com.tibco.cep.kernel.model.rule.impl.RuleImpl.DependencyIndex.class.getSimpleName();
    protected static final String dependencyIndex = "com.tibco.cep.kernel.model.rule.impl.RuleImpl.DependencyIndex";
    public static final String smdependencyIndex = com.tibco.cep.kernel.model.rule.impl.RuleImpl.ChildDependencyIndex.class.getCanonicalName();

    protected static final String hashCodeFunctionName = CodegenFunctions.class.getName()+ ".hashcode";

    public static final String conditionSuffix = "_c";
    public static final String equivConditionSuffix = "_eq_c";
    public static final String negEquivConditionSuffix = "_neq_c";
    protected static final String actionSuffix = "_a";
    protected static final String scorecardIdPrefix = "$1z";

    protected static final String variableListImpl = com.tibco.be.model.functions.VariableListImpl.class.getName();
    protected static final String variableListImplFactory = variableListImpl + "." + "newVariableListImpl";

    protected RuleInfo rinfo;
    protected String shortName;
    protected JavaClassWriter ruleClass;
    protected Symbols identifierMap;
    protected HashSet additionalDependentScorecards;
    //use this as the value in an identifier map entry to indicate that the entry is for a scorecard
    protected RLSymbolTable symbolTable;

    //Used to determine what individual properties of the declared concepts and events
    //are considered in the condition(s) of the entire rule.
    //Keys are declared identifiers.  Values are HashSets of property names.
    //If a HashSet of propery names is empty, none of that identifier's properties
    //are used in the condition, but the identifier itself is used.
    //If an identifier is not present, that means that it is not used in the condition.
    //If the value for an identifier in dependentProperties is INDEFINITE_USAGE
    //it means that the identifier was used in a way where its accesses are unknown
    //(for example it was passed to a Java function)
    protected static final String INDEFINITE_USAGE = "INDEFINITE_USAGE";
    //dependencies will only be added to dependentProperties when recordDependencies is true
    protected boolean recordDependencies = false;
    protected HashMap dependentProperties = new HashMap();

    //variables used to represent the class or
    //method body currently being constructed
    protected StringBuilder currBody = null;
    protected JavaClassWriter currClass = null;
    protected Properties oversizeStringConstants;
    protected Map ruleFnUsage;
    protected Node currNode = null;
    protected Node prevNode = null;
    //last root node visited
    protected SourceType currentSourceType = null;
    protected boolean generateNullContainedConcept = Boolean.getBoolean(CGConstants.GENERATE_NULL_CONTAINED_CONCEPT);
    protected Ontology ontology;
    protected LineBuffer currentLinebuffer;
    protected boolean useLineBuffer;
    protected List<Map<String, String>> rtScopes = new ArrayList<Map<String, String>>();
    protected boolean isTemplated;
    protected Map<String, String> rtBindingNameToClass = new LinkedHashMap<String, String>();

    protected Map<String, Map<String, int[]>> propInfoCache = new HashMap<>();
    //hyphen is illegal char in prop names
    protected final String NUM_PROPS = "num-props";


    //only OK to use this constructor when not generating conditions
    /**
     * @param linebuffer
     * @param rinfo
     * @param rlSymbolTable
     * @param oversizeStringConstants
     * @param ontology
     */
    protected RuleClassGeneratorSmap(LineBuffer linebuffer, RuleInfo rinfo, RLSymbolTable rlSymbolTable
            , Properties oversizeStringConstants, Ontology ontology, Map<String, Map<String, int[]>> propInfoCache)
    {
        this(rinfo, rlSymbolTable, oversizeStringConstants, null,  ontology, propInfoCache);
        currentLinebuffer = linebuffer;
    }

    /**
     * @param jClassWriter
     * @param rinfo
     * @param rlSymbolTable
     * @param oversizeStringConstants
     * @param ontology
     */
    protected RuleClassGeneratorSmap(JavaClassWriter jClassWriter, RuleInfo rinfo, RLSymbolTable rlSymbolTable
            , Properties oversizeStringConstants, Ontology ontology, Map<String, Map<String, int[]>> propInfoCache)
    {
       this(rinfo, rlSymbolTable, oversizeStringConstants, null,  ontology, propInfoCache);
       currClass = jClassWriter;
    }


    /**
     * @param rinfo
     * @param rlSymbolTable
     * @param oversizeStringConstants
     * @param ruleFnUsage
     * @param ontology
     */
    protected RuleClassGeneratorSmap(RuleInfo rinfo, RLSymbolTable rlSymbolTable, Properties oversizeStringConstants
            , Map ruleFnUsage, Ontology ontology, Map<String, Map<String, int[]>> propInfoCache)
    {
        this.rinfo = rinfo;
        this.isTemplated = (rinfo instanceof RuleTemplateInfo);
        shortName = ModelNameUtil.escapeIdentifier(ModelNameUtil.getShortNameFromPath(rinfo.getFullName()));
        ruleClass = new JavaClassWriter(shortName,null);
        ruleClass.setAccess("public");
        ruleClass.setSuperClass(isTemplated ? rtSuperClassImpl : ruleSuperClassImpl);
        currClass = ruleClass;
        currentLinebuffer = ruleClass.getLineBuffer();
        identifierMap = rinfo.getDeclarations();
        additionalDependentScorecards = new HashSet();
        symbolTable = rlSymbolTable;
        this.oversizeStringConstants = oversizeStringConstants;
        this.ruleFnUsage = ruleFnUsage;
        this.ontology = ontology;
        this.useLineBuffer = true;
        this.propInfoCache = propInfoCache;
    }

    /**
     * @param cc
     * @param rinfo
     * @param rlSymbolTable
     * @param oversizeStringConstants
     * @param ruleFnUsage
     * @param ontology
     */
    protected RuleClassGeneratorSmap(JavaClassWriter cc,RuleInfo rinfo, RLSymbolTable rlSymbolTable, Properties oversizeStringConstants
            , Map ruleFnUsage, Ontology ontology, Map<String, Map<String, int[]>> propInfoCache)
    {
        this.rinfo = rinfo;
        this.isTemplated = (rinfo instanceof RuleTemplateInfo);
        shortName = ModelNameUtil.escapeIdentifier(ModelNameUtil.getShortNameFromPath(rinfo.getFullName()));
        ruleClass = cc.createNestedClassWriter(shortName,null);
        ruleClass.setAccess("public");
        ruleClass.setSuperClass(isTemplated ? rtSuperClassImpl : ruleSuperClassImpl);
        currClass = ruleClass;
        currentLinebuffer = ruleClass.getLineBuffer();
        identifierMap = rinfo.getDeclarations();
        additionalDependentScorecards = new HashSet();
        symbolTable = rlSymbolTable;
        this.oversizeStringConstants = oversizeStringConstants;
        this.ruleFnUsage = ruleFnUsage;
        this.ontology = ontology;
        this.useLineBuffer = true;
        this.propInfoCache = propInfoCache;
    }



    /**
     * @param rinfo
     * @param rlSymbolTable
     * @param oversizeStringConstants
     * @param ruleFnUsage
     * @param ontology
     * @return
     */
    public static JavaClassWriter makeRuleClassNew(RuleInfo rinfo, RLSymbolTable rlSymbolTable, Properties oversizeStringConstants
            , Map ruleFnUsage, Ontology ontology, Map<String, Map<String, int[]>> propInfoCache)
    {
        RuleClassGeneratorSmap rcg = new RuleClassGeneratorSmap(rinfo, rlSymbolTable, oversizeStringConstants, ruleFnUsage, ontology, propInfoCache);
        rcg.addConfigureBindingScopeMethod(rinfo);
        List<String> condClassNames = rcg.addConditionClasses();
        rcg.addActionClass();
        rcg.addRuleConstructor(rinfo.usesForwardChain(),"", "", condClassNames);
        rcg.addThisClass();
        rcg.addGetActionScopesMethod();
        return rcg.ruleClass;
    }





    /** ++ Suresh Added for StateTransition. There is a better way, but need to wait till the array operation[] is provided */
    /*public static JavaClass makeRuleClass(RuleInfo rinfo, StateTransition trans, Concept cept, RLSymbolTable rlSymbolTable, Properties oversizeStringConstants) {
        Rule guardRule = trans.getGuardRule(true);
        Map decls = guardRule.getDeclarations();
        Set keySet = decls.keySet();
        Object[] keys = keySet.toArray();
        String varName = ModelNameUtil.escapeIdentifier(String.valueOf(keys[0]));

        HashSet set = new HashSet();
        set.add(ConceptClassGenerator.TRANSITION_STATUSES);


        String guardCondition = "new " + ModelNameUtil.escapeIdentifier(trans.getName()) + "_GuardCondition(this),";
        String guardAction = "new " + ModelNameUtil.escapeIdentifier(trans.getName()) + "_GuardAction(this),";
        RuleClassGenerator rcg = new RuleClassGenerator(rinfo, rlSymbolTable, oversizeStringConstants);
        rcg.dependentProperties.put(varName, set);
        rcg.addConditionClasses();
        rcg.addActionClass();
        rcg.addRuleConstructor(guardCondition, guardAction);
        return rcg.ruleClass;
    }*/

    public static MethodRecWriter makeActionMethod(JavaClassWriter cc, String methodName, RuleInfo rinfo, RLSymbolTable rlSymbolTable
            , Properties oversizeStringConstants, Ontology ontology, Map<String, Map<String, int[]>> propInfoCache)
    {
        return new RuleClassGeneratorSmap(cc,rinfo, rlSymbolTable, oversizeStringConstants, ontology, propInfoCache).getActionExecMethod(methodName);
    }

    public static LineBuffer getActionBodyText(RuleInfo rinfo, RLSymbolTable rlSymbolTable, Properties oversizeStringConstants
            , Ontology ontology, Map<String, Map<String, int[]>> propInfoCache)
    {
        LineBuffer linebuffer = new LineBuffer(null);
        return new RuleClassGeneratorSmap(linebuffer, rinfo, rlSymbolTable, oversizeStringConstants, ontology, propInfoCache).getActionBodyText();
    }
    public static LineBuffer getActionBodyText(JavaClassWriter jClassWriter, LineBuffer linebuffer, RuleInfo rinfo, RLSymbolTable rlSymbolTable
            , Properties oversizeStringConstants, Ontology ontology, Map<String, Map<String, int[]>> propInfoCache)
    {
        return new RuleClassGeneratorSmap(linebuffer, rinfo, rlSymbolTable, oversizeStringConstants, ontology, propInfoCache).getActionBodyText(jClassWriter);
    }

    protected void appendUsedRuleFunctions(Set conditionRuleFnUsage) {
        //have to store up the changes to be made separately.
        //adding to conditionRuleFnUsage while iterating over it
        //will cause a ConcurrentModificationException
        ArrayList tempList = new ArrayList();
        for(Iterator it = conditionRuleFnUsage.iterator(); it.hasNext();) {
            String ruleFnClass = (String)it.next();
            Set fnUsages = (Set)ruleFnUsage.get(ruleFnClass);
            tempList.add(fnUsages);
        }
        //add usages of all ruleFunctions used by this function to the condition's usages
        for(Iterator it = tempList.iterator(); it.hasNext();) {
            conditionRuleFnUsage.addAll((Set)it.next());
        }

        currBody.append("new java.lang.String[] {");
        currentLinebuffer.append("new java.lang.String[] {");
        for(Iterator it = conditionRuleFnUsage.iterator(); it.hasNext();) {
            String ruleFnClass = (String)it.next();
            currBody.append("\"");
            currBody.append(ruleFnClass);
            currBody.append("\"");
            currentLinebuffer.append("\"");
            currentLinebuffer.append(ruleFnClass);
            currentLinebuffer.append("\"");
            if(it.hasNext()) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
        }
        currBody.append(" }");
        currentLinebuffer.append(" }");

    }


    protected void appendNewIdentifierArray(Iterator ids) {
        currBody.append("new " + identifierInterface + "[] { ");
        currentLinebuffer.append("new " + identifierInterface + "[] { ");
        while(ids.hasNext()) {
            String id = (String)ids.next();
            String typeName = identifierMap.getType(id);
            NodeType type = symbolTable.getDeclaredIdentifierType(id);
            //if the id was a scorecard path, then use that path
            if(typeName == null && additionalDependentScorecards.contains(id)) {
                typeName = id;
            }
            currBody.append(" new " + identifierImpl + "(");
            currBody.append(CodegenFunctions.class.getName() + ".classForName(thisClass.getClassLoader(),\"");
            currentLinebuffer.append(" new " + identifierImpl + "(");
            currentLinebuffer.append(CodegenFunctions.class.getName() + ".classForName(thisClass.getClassLoader(),\"");

            //type will be null if typeName was null before the above if statement
            //due to id being looked up in additionalDependentScorecards
            if(type == null || !type.isGenericInCodegen()) {
                currBody.append(ModelNameUtil.modelPathToGeneratedClassName(typeName));
                currentLinebuffer.append(ModelNameUtil.modelPathToGeneratedClassName(typeName));
            } else {
                if(type.isGenericConcept()) {
                    currBody.append(CGConstants.engineConceptImpl);
                    currentLinebuffer.append(CGConstants.engineConceptImpl);
                }
                else if(type.isGenericSimpleEvent()) {
                    currBody.append(CGConstants.engineSimpleEventImpl);
                    currentLinebuffer.append(CGConstants.engineSimpleEventImpl);
                }
                else if(type.isGenericTimeEvent()) {
                    currBody.append(CGConstants.engineTimeEventImpl);
                    currentLinebuffer.append(CGConstants.engineTimeEventImpl);
                }
                else if(type.isGenericAdvisoryEvent()){
                    currBody.append(CGConstants.engineAdvisoryEventImpl);
                    currentLinebuffer.append(CGConstants.engineAdvisoryEventImpl);
                }
                else if(type.isProcess()) {
                    currBody.append(CGConstants.engineProcessBaseClass);
                    currentLinebuffer.append(CGConstants.engineProcessBaseClass);
                }
                else assert false;
            }
            currBody.append("\") , " + '"' + id + "\")");
            currentLinebuffer.append("\") , " + '"' + id + "\")");
            if(ids.hasNext()) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
        }
        currBody.append(" }");
        currentLinebuffer.append(" }");
    }

    protected void appendNewRuleIdentifierArray() {
        Iterator it = skipNonDependentScoreCardsIdsIterator();
        //if all the identifiers were non-dependent, then just use all of them.
        if(!it.hasNext()) it = identifierMap.keySet().iterator();
        appendNewIdentifierArray(it);
    }

//    DependencyIndex[][] propertyDependencyIndices = { new DependencyIndex[]{
//         new DependencyIndex(Contained1.$1zContained1_property_1.getLevel(), Contained1.$1zContained1_property_1.getIndex())} };

    protected void appendNewRuleDependsOnArray() {
//        currBody.append("new " + dependencyIndex + "[][] { ");
        currBody.append("{ ");
        currentLinebuffer.append("{ ");
        boolean firstOuter = true;
        for(Iterator ids = skipNonDependentScoreCardsIdsIterator(); ids.hasNext();) {
            if(!firstOuter || (firstOuter = false)) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
            String id = (String)ids.next();
            Object propertyDependencies = dependentProperties.get(id);

            //empty array _in the generated rule class_ means the identifier wasn't used in the conditino
            if(propertyDependencies == null) {
                currBody.append("new " + dependencyIndex + "[] {}");
                currentLinebuffer.append("new " + dependencyIndex + "[] {}");
            }
            //null array _in the generated rule class_ means the usage was indefinite
            else if(propertyDependencies == INDEFINITE_USAGE) {
                currBody.append("null");
                currentLinebuffer.append("null");
            } else {
                currBody.append("new " + dependencyIndex + "[] { ");
                currentLinebuffer.append("new " + dependencyIndex + "[] { ");
                for(String propName : (Set<String>)propertyDependencies) {
                    String modelPath = identifierMap.getType(id);
                    //for scorecards, the id is the model path
                    //todo replace this w/ custom iterator?
                    if(modelPath == null && additionalDependentScorecards.contains(id)) modelPath = id;
                    String className = ModelNameUtil.modelPathToGeneratedClassName(modelPath);
                    appendNewDependencyIndex(className, propName);
                }
                currBody.append(" }");
                currentLinebuffer.append(" }");
            }
        }
        currBody.append(" }");
        currentLinebuffer.append(" }");
    }

    protected void appendNewDependencyIndex(String className, String propName) {
        appendNewDependencyIndex(className, propName, dependencyIndex);
    }
    protected void appendNewDependencyIndex(String className, String propName, String dependencyClass) {
        String s = String.format("new %s(%s(%s.%s(\"%s\"))), ", dependencyIndex, PropertyImpl.DIRTY_INDEX_STATIC
                , className, ConceptClassGeneratorSmap.GET_PROP_INDEX_STATIC, propName);
        currBody.append(s);
        currentLinebuffer.append(s);
    }

    protected void appendNewConditionArray(List<String> condClassNames, String initCondition) {
        currBody.append("new " + conditionInterface + "[] { " + initCondition);
        currentLinebuffer.append("new " + conditionInterface + "[] { " + initCondition);

        if(rinfo.getDeclarations().getTypes().contains(RDFTypes.BASE_CONCEPT.getName())) {
            initCondition = initCondition == null ? null : initCondition.trim();
            if(initCondition != null && initCondition.length() > 0 && !initCondition.endsWith(",")) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
            currBody.append("new " + ExcludeScorecardsCondition.class.getName() + "(this)");
            currentLinebuffer.append("new " + ExcludeScorecardsCondition.class.getName() + "(this)");
            if(condClassNames != null && condClassNames.size() > 0) {
                currBody.append( ", ");
                currentLinebuffer.append(", ");
            }
        }
        if(condClassNames != null) {
            Iterator<String> names = condClassNames.iterator();
            for(int ii = 1; names.hasNext(); ii++) {
                String condClassName = names.next();
                if(ii != 1) {
                    currBody.append(", ");
                    currentLinebuffer.append(", ");
                }
                currBody.append("new " + condClassName + "(this)");
                currentLinebuffer.append("new " + condClassName + "(this)");
            }
        }
        currBody.append("}");
        currentLinebuffer.append("}");
    }

    protected void appendNewActionArray(Iterator thenTrees, String guardAction) {
        currBody.append("new " + actionInterface + "[] { ");
        currentLinebuffer.append("new " + actionInterface + "[] { ");
        currBody.append("new " + shortName + actionSuffix + "(this)");
        currentLinebuffer.append("new " + shortName + actionSuffix + "(this)");
        if(guardAction != null && guardAction.length() > 0) {
            currBody.append(",");
            currentLinebuffer.append(", ");
        }
        currBody.append(guardAction);
        currentLinebuffer.append(guardAction);

        currBody.append("}");
        currentLinebuffer.append("}");
    }

    protected void appendAttributeSetters(Iterator attrs) {
        for(Iterator it = rinfo.getAttributes(); it.hasNext();) {
            RuleInfo.AttrRecord rec = (RuleInfo.AttrRecord)it.next();
            if(rec.attr.kind == LASTMOD) {
//                currClass.addMember("static public", "String", "LAST_MODIFIED" , rec.value);
//                currBody.append("m_lastMod = ");
//                currBody.append("LAST_MODIFIED");
//                currBody.append(";" + BRK);
            } else {
                currBody.append("set");
                currBody.append(CGUtil.firstCap(rec.attr.image));
                currBody.append("(" + rec.value + ");" + BRK);
                currentLinebuffer.append("set");
                currentLinebuffer.append(CGUtil.firstCap(rec.attr.image));
                currentLinebuffer.append("(" + rec.value + ");"+BRK);
            }
        }


        if (rinfo.getRank() != null && !rinfo.getRank().equals("")) {
        	String path = null;
        	if(rinfo.getRank().endsWith(".rulefunction")) {
        		path = rinfo.getRank().substring(0, rinfo.getRank().lastIndexOf(".rulefunction"));
        	} else {
        		path = rinfo.getRank();
        	}
            currBody.append("setRank(((com.tibco.cep.runtime.service.loader.BEClassLoader)this.getClass().getClassLoader()).getRuleFunctionInstance(\"" + path + "\"));" + BRK);
            currentLinebuffer.append("setRank(((com.tibco.cep.runtime.service.loader.BEClassLoader)this.getClass().getClassLoader()).getRuleFunctionInstance(\"" + path + "\"));" + BRK);
        }
        else {
        	currBody.append("setRank(null);" + BRK);
        	currentLinebuffer.append("setRank(null);" + BRK);
        }
    }

    protected void addThisClass() {
        this.ruleClass.addMember("static final", Class.class.getName(), "thisClass", ruleClass.getName() + ".class");
    }

    protected void addRuleConstructor(boolean usesForwardChain, String initialCondition, String guardAction, List<String> classNames) {
        //save currBody
        StringBuilder oldBody = currBody;
        LineBuffer oldLineBuffer = currentLinebuffer;
        //Process attributes
        currBody = new StringBuilder();
        MethodRecWriter constructor = ruleClass.createMethod("public", "", shortName);
        currentLinebuffer = constructor.getLineBuffer();




        String ruleName = ModelNameUtil.modelPathToGeneratedClassName(rinfo.getFullName());
        this.currBody.append("super(\"" + ruleName + "\", \"" + rinfo.getFullName() + "\");" + BRK);
        this.currentLinebuffer.append("super(\"" + ruleName + "\", \"" + rinfo.getFullName() + "\");" + BRK);

        appendAttributeSetters(rinfo.getAttributes());


        currBody.append("m_identifiers = ");
        currentLinebuffer.append("m_identifiers = ");
        appendNewRuleIdentifierArray();
        currBody.append(";" + BRK);
        currentLinebuffer.append(";"+BRK);

        currBody.append("m_conditions = ");
        currentLinebuffer.append("m_conditions = ");
        appendNewConditionArray(classNames, initialCondition);
        currBody.append(";" + BRK);
        currentLinebuffer.append(";"+BRK);

        currBody.append("m_actions = ");
        currentLinebuffer.append("m_actions = ");
        appendNewActionArray(rinfo.getThenTrees(), guardAction);
        currBody.append(";" + BRK);
        currentLinebuffer.append(";"+BRK);

        currBody.append(dependencyIndex + "[][] dependencyIndices = ");
        currentLinebuffer.append(dependencyIndex + "[][] dependencyIndices = ");
        appendNewRuleDependsOnArray();
        currBody.append(";" + BRK);
        currentLinebuffer.append(";"+BRK);

        currBody.append("setIdentifierDependencyBitArray(dependencyIndices);");
        currentLinebuffer.append("setIdentifierDependencyBitArray(dependencyIndices);");

        if (isTemplated) {
            currBody.append("rtBaseDependencyIndices = dependencyIndices;" + BRK
                    + "rtBaseConditions = m_conditions;");
            currentLinebuffer.append("rtBaseDependencyIndices = dependencyIndices;"
                    + BRK + "rtBaseConditions = m_conditions;");
        }

//        MethodRec constructor = new MethodRec(shortName);
//        constructor.setAccess("public");
//        constructor.setReturnType("");
        if(!useLineBuffer) {
            constructor.setBody(currBody);
        }

        //ruleClass.addMethod(constructor);

        //restore currBody
        currBody = oldBody;
        currentLinebuffer = oldLineBuffer;

        MethodRec fc = ruleClass.createMethod("public","boolean","forwardChain");
//        fc.setAccess("public");
//        fc.setReturnType("boolean");
        fc.setBody("return " + usesForwardChain + " ; " + BRK);
//        ruleClass.addMethod(fc);

    }

    protected Iterator skipNonDependentScoreCardsIdsIterator() {
        return new Iterator() {
            Iterator idIt = identifierMap.keySet().iterator();
            Iterator ADSit = additionalDependentScorecards.iterator();
            Object next = null;
            {
                if(idIt.hasNext()) next = idIt.next();
                else if(ADSit.hasNext()) next = ADSit.next();
            }

            public boolean hasNext() {
                return next != null;
            }

            public Object next() {
                if(next == null) throw new NoSuchElementException();
                Object ret = next;
                while(idIt.hasNext()) {
                    String id = (String)idIt.next();
                    if(!(symbolTable.getDeclaredIdentifierType(id).isScorecard() && !dependentProperties.containsKey(id))) {
                        next = id;
                        return ret;
                    }
                }
                if(ADSit.hasNext()) {
                    next = ADSit.next();
                } else {
                    next = null;
                }
                return ret;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    //used to record what identifiers are used during evaluation of a condition
    protected LinkedHashSet usedIdentifiers = null;
    protected LinkedHashSet leftIdentifiers = null;
    protected LinkedHashSet rightIdentifiers = null;
    protected StringBuilder leftBody = null;
    protected StringBuilder rightBody = null;
    protected boolean alwaysEvaluate = false;
    protected HashSet usedRuleFunctions = null;
    protected LineBuffer leftBuffer = null;
    protected LineBuffer rightBuffer = null;

    //returns list of local condition class names for use when calling the constructors of those classes
    protected List<String> addConditionClasses() {
        ArrayList<String> localClassNames = new ArrayList();
        Iterator it = rinfo.getWhenTrees();
        JavaClassWriter oldClass = currClass;
        StringBuilder oldBody = currBody;
        LineBuffer oldLineBuffer = currentLinebuffer;

        recordDependencies = true;
        for(int ii = 1; it.hasNext(); ii++) {
            RootNode rootNode = (RootNode)it.next();
            localClassNames.add(addConditionClass(rootNode, oldClass, ii));
        }
        recordDependencies = false;

        currClass = oldClass;
        currBody = oldBody;
        currentLinebuffer = oldLineBuffer;
        return localClassNames;
    }

    protected boolean isEquivalence(Node node) {
        if(node instanceof ProductionNode) {
            ProductionNode pn = (ProductionNode)node;
            // check for exp1 == exp2
            if(pn.getToken().kind == EQ) {
                return true;
            }

        }
        return false;
    }

    protected boolean isNegatedEquivalence(Node node) {
        if(node instanceof ProductionNode) {
            ProductionNode pn = (ProductionNode)node;
            //check for !(exp1 == exp2)
            if(pn.getToken().kind == BANG){
                if(pn.getChildCount() > 0 && pn.getChild(0) instanceof ProductionNode) {
                    ProductionNode pn2 = (ProductionNode)pn.getChild(0);
                    if(pn2.getToken().kind == EQ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //returns local condition class name for use when calling the constructor of that class
    protected String addConditionClass(RootNode rootNode, JavaClassWriter oldClass, int ii)  {
        //used for checking for equivalent condition
        Node firstNode = rootNode.getChild(0);

        //first do the actual evaluation part of the eval method.
        //declaring the local variables comes later

        boolean isNegatedEquivalence = isNegatedEquivalence(firstNode);
        boolean equivalence = isEquivalence(firstNode) || isNegatedEquivalence;

        //reset this for every condition
        alwaysEvaluate = false;
        usedRuleFunctions = new HashSet();
        if(equivalence) {
            Node oldFirstNode = firstNode;
            if(isNegatedEquivalence) {
                isNegatedEquivalence = true;
                firstNode = firstNode.getChild(0);
            }
            leftIdentifiers = new LinkedHashSet();
            rightIdentifiers = new LinkedHashSet();
            leftBody = new StringBuilder();
            rightBody = new StringBuilder();
            leftBuffer = new LineBuffer(firstNode.getChild(0));
            rightBuffer = new LineBuffer(firstNode.getChild(1));

            //append left side of condition
            usedIdentifiers = leftIdentifiers;
            currBody = leftBody;
            currentLinebuffer = leftBuffer;
            currentSourceType = SourceType.RULE_CONDITION;
            accept(firstNode.getChild(0));
            //append right side of condition
            usedIdentifiers = rightIdentifiers;
            currBody = rightBody;
            currentLinebuffer = rightBuffer;
            accept(firstNode.getChild(1));

            //check if the intersection of the left and right identifiers is empty
            int leftSize = leftIdentifiers.size();
            leftIdentifiers.removeAll(rightIdentifiers);
            //if leftIdentifiers.size() got smaller then one or more identifiers appeared on both
            //sides of the equivalence and the optimization cannot be performed
            if(leftSize == 0 || rightIdentifiers.size() == 0 || leftSize != leftIdentifiers.size()) {
                equivalence = false;
            }

            usedIdentifiers = new LinkedHashSet();
            currBody = new StringBuilder();
            currentLinebuffer = new LineBuffer(firstNode);
            if(isNegatedEquivalence) {
//                currBody.append("return !(");
                currBody.append("boolean $rv = !(");
                currentLinebuffer.append("boolean $rv = !(");
            } else {
//                currBody.append("return (");
                currBody.append("boolean $rv = (");
                currentLinebuffer.append("boolean $rv = (");
            }
            appendComparisonOp((ProductionNode)firstNode);
            currBody.append(");");
            currBody.append("\nreturn $rv;"); //++ ISS August 2, 2006
            currentLinebuffer.append(");");
            currentLinebuffer.append("return $rv;");
            currentSourceType = null;
            firstNode = oldFirstNode;
        } else {
            usedIdentifiers = new LinkedHashSet();
            currBody = new StringBuilder();
            currentLinebuffer = new LineBuffer(rootNode);
            //need to return the result of the condition expression
//            currBody.append("return ");
            currBody.append("boolean $rv = ");
            currentLinebuffer.append("boolean $rv = ");
            accept(rootNode);
            currBody.append(";"+BRK);
            currBody.append("return $rv;"); //++ ISS August 2, 2006
            currentLinebuffer.append(";"+BRK);
            currentLinebuffer.append("return $rv;");

        }
        //currClass = new JavaClass(getLineBuffer(),shortName + conditionSuffix + ii,rootNode);
        //currClass = oldClass.createNestedClassWriter(shortName + conditionSuffix + ii,null);

        if(equivalence) {
            if(isNegatedEquivalence) {
//                currClass = new JavaClass(shortName + negEquivConditionSuffix + ii);
                currClass = oldClass.createNestedClassWriter(shortName + negEquivConditionSuffix + ii,null);
                currClass.setSuperClass(negatedEquivConditionImpl);
            } else {
//                currClass = new JavaClass(shortName + equivConditionSuffix + ii);
                currClass = oldClass.createNestedClassWriter(shortName + equivConditionSuffix + ii,null);
                currClass.setSuperClass(equivConditionImpl);

            }
        } else {
//            currClass = new JavaClass(shortName + conditionSuffix + ii);
            currClass = oldClass.createNestedClassWriter(shortName + conditionSuffix + ii,null);
            currClass.setSuperClass(conditionImpl);
        }
        if(!rinfo.getConditionStratumMap().containsKey(currClass.getClassName())) {
            rinfo.getConditionStratumMap().put(currClass.getClassName(),new SmapStratum("RSP"));
            rootNode.setUserContext(currClass.getClassName());
        }


        StringBuilder evalCondition = currBody;
        LineBuffer evalConditionBuffer = currentLinebuffer;
        //the local variable declarations will be appended to this new StringBuilder
        currBody = new StringBuilder();

        MethodRecWriter mr = currClass.createMethod("public", "boolean", "eval");
        currentLinebuffer = mr.getLineBuffer();
        appendLocalVariables(usedIdentifiers.iterator());
//        MethodRec mr = new MethodRec("eval");
//        mr.setAccess("public");
//        mr.setReturnType("boolean");
        mr.addArg("java.lang.Object[]", "objects");
        //add the condition to the end of the local variable declarations
        currBody.append(evalCondition);
        currentLinebuffer.registerNode(rootNode);
        currentLinebuffer.append(evalConditionBuffer);
        if(!useLineBuffer) {
            mr.setBody(currBody);
        }
        //currClass.addMethod(mr);

        //constructor
        mr = currClass.createMethod("public", "", currClass.getLocalName());
//        mr = new MethodRec(shortName + conditionSuffix + ii);
//        mr.setAccess("public");
//        mr.setReturnType("");
        mr.addArg(ruleInterface, "rule");
        //constructor body
        currBody = new StringBuilder();
        currentLinebuffer = mr.getLineBuffer();
        currBody.append("super(rule);" + BRK);
        currBody.append("m_identifiers = ");
        currentLinebuffer.append("super(rule);"+BRK);
        currentLinebuffer.append("m_identifiers = ");
        appendNewIdentifierArray(usedIdentifiers.iterator());
        currBody.append(";" + BRK);
        currentLinebuffer.append(";"+BRK);
        if(!useLineBuffer) {
            mr.setBody(currBody);
        }
        //currClass.addMethod(mr);

        //add methods for equivalent condition
        if(equivalence) {
            Node oldFirstNode = firstNode;
            if(isNegatedEquivalence) {
                isNegatedEquivalence = true;
                firstNode = firstNode.getChild(0);
            }
            mr = currClass.createMethod("public", com.tibco.cep.kernel.model.rule.Identifier.class.getName() + "[]", "_getLeftIdentifiers");
            //mr = new MethodRec("public", com.tibco.cep.kernel.model.rule.Identifier.class.getName() + "[]", "getLeftIdentifiers");
            currBody = new StringBuilder();
            currentLinebuffer = mr.getLineBuffer();
            currBody.append("return ");
            currentLinebuffer.append("return ");
            appendNewIdentifierArray(leftIdentifiers.iterator());
            currBody.append(";" + BRK);
            currentLinebuffer.append(";"+BRK);
            mr.setBody(currBody);

            //currClass.addMethod(mr);
            mr = currClass.createMethod("public", com.tibco.cep.kernel.model.rule.Identifier.class.getName() + "[]", "_getRightIdentifiers");
//            mr = new MethodRec("public", com.tibco.cep.kernel.model.rule.Identifier.class.getName() + "[]", "getRightIdentifiers");
            currBody = new StringBuilder();
            currentLinebuffer = mr.getLineBuffer();
            currBody.append("return ");
            currentLinebuffer.append("return ");
            appendNewIdentifierArray(rightIdentifiers.iterator());
            currBody.append(";" + BRK);
            currentLinebuffer.append(";"+BRK);
            mr.setBody(currBody);
//            currClass.addMethod(mr);

            NodeType lhsType = firstNode.getChild(0).getType();
            NodeType rhsType = firstNode.getChild(1).getType();

            mr = currClass.createMethod("public", "int", "leftExpHashcode");
//            mr = new MethodRec("public", "int", "leftExpHashcode");
            mr.addArg("java.lang.Object[]", "objects");
            currBody = new StringBuilder();
            currentLinebuffer = mr.getLineBuffer();
            appendLocalVariables(usedIdentifiers.iterator());
            currBody.append("return ");
            currentLinebuffer.append("return ");
            currBody.append(hashCodeFunctionName + "(");
            currentLinebuffer.append(hashCodeFunctionName + "(");
            //hashcode algorithm is different for certain types
            //so must be sure that both sides use the same algorithm
            if(rhsType.isDouble() && (lhsType.isInt() || lhsType.isLong()) && !(lhsType.isArray() || rhsType.isArray())) {
                leftBody.insert(0, "(double)");
                leftBuffer.insert(0, "(double)");
            }
            if(rhsType.isLong() && lhsType.isInt() && !(lhsType.isArray() || rhsType.isArray())) {
                leftBody.insert(0, "(long)");
                leftBuffer.insert(0, "(long)");
            }
            currBody.append(leftBody);
            currentLinebuffer.append(leftBuffer.toString());
            currBody.append(");");
            currentLinebuffer.append(");");
            mr.setBody(currBody);
            //currClass.addMethod(mr);

            mr = currClass.createMethod("public", "int", "rightExpHashcode");
//            mr = new MethodRec("public", "int", "rightExpHashcode");
            mr.addArg("java.lang.Object[]", "objects");
            currBody = new StringBuilder();
            currentLinebuffer = mr.getLineBuffer();
            appendLocalVariables(usedIdentifiers.iterator());
            currBody.append("return ");
            currentLinebuffer.append("return ");
            currBody.append(hashCodeFunctionName + "(");
            currentLinebuffer.append(hashCodeFunctionName + "(");
            //hashcode algorithm is different for certain types
            //so must be sure that both sides use the same algorithm
            if(lhsType.isDouble() && (rhsType.isInt() || rhsType.isLong()) && !(rhsType.isArray() || lhsType.isArray())) {
                rightBody.insert(0,"(double)");
                rightBuffer.insert(0,"(double)");
            }
            if(lhsType.isLong() && rhsType.isInt() && !(rhsType.isArray() || lhsType.isArray())) {
                rightBody.insert(0,"(long)");
                rightBuffer.insert(0,"(long)");
            }
            currBody.append(rightBody);
            currentLinebuffer.append(rightBuffer.toString());
            currBody.append(");");
            currentLinebuffer.append(");");
            mr.setBody(currBody);
            //currClass.addMethod(mr);

            if(isNegatedEquivalence) {
                mr = currClass.createMethod("public", "java.lang.Object", "leftExp");
//                mr = new MethodRec("public", "java.lang.Object", "leftExp");
                mr.addArg("java.lang.Object[]", "objects");
                currBody = new StringBuilder();
                currentLinebuffer = mr.getLineBuffer();
                appendLocalVariables(usedIdentifiers.iterator());
                currBody.append("return ");
                currentLinebuffer.append("return ");
                currBody.append(boxUnboxed + "(");
                currentLinebuffer.append(boxUnboxed + "(");
                currBody.append(leftBody);
                currentLinebuffer.append(leftBuffer);
                currBody.append(");");
                currentLinebuffer.append(");");
                mr.setBody(currBody);
//                currClass.addMethod(mr);

                mr = currClass.createMethod("public", "java.lang.Object", "rightExp");
//                mr = new MethodRec("public", "java.lang.Object", "rightExp");
                mr.addArg("java.lang.Object[]", "objects");
                currBody = new StringBuilder();
                currentLinebuffer = mr.getLineBuffer();
                appendLocalVariables(usedIdentifiers.iterator());
                currBody.append("return ");
                currentLinebuffer.append("return ");
                currBody.append(boxUnboxed + "(");
                currentLinebuffer.append(boxUnboxed + "(");
                currBody.append(rightBody);
                currentLinebuffer.append(leftBuffer);
                currBody.append(");");
                currentLinebuffer.append(");");
                mr.setBody(currBody);
//                currClass.addMethod(mr);
            }
            firstNode = oldFirstNode;
        }

        //add depends on
        mr = currClass.createMethod("public", "java.lang.String[]", "getDependsOn");
//        mr = new MethodRec("public", "java.lang.String[]", "getDependsOn");
        currBody = new StringBuilder();
        currentLinebuffer = mr.getLineBuffer();
        currBody.append("return ");
        currentLinebuffer.append("return ");
        appendUsedRuleFunctions(usedRuleFunctions);
        currBody.append(";" + BRK);
        currentLinebuffer.append("; "+BRK);
        if(!useLineBuffer) {
            mr.setBody(currBody);
        }
//        currClass.addMethod(mr);

        if(alwaysEvaluate) {
            mr = currClass.createMethod("public", "boolean", "alwaysEvaluate");
//            mr = new MethodRec("public", "boolean", "alwaysEvaluate");
            mr.setBody("return true;");
            currClass.getLineBuffer().append("return true;");
//            currClass.addMethod(mr);
        }

        //currClass.setSourceText(((RootNode)rootNode).getSourceText().trim());
        addSourceTextMethod(((RootNode)rootNode).getSourceText().trim());
        addToStringMethod();



        equivalence = false;
        usedIdentifiers = null;
        leftIdentifiers = null;
        rightIdentifiers = null;
        leftBody = null;
        rightBody = null;
        usedRuleFunctions = null;

        return currClass.getLocalName();
    }

    protected void addActionClass()  {
        JavaClassWriter oldClass = currClass;
        StringBuilder oldBody = currBody;
        LineBuffer oldLineBuffer = currentLinebuffer;
        StringBuilder sourceText = new StringBuilder();

        currClass = oldClass.createNestedClassWriter(shortName + actionSuffix, null);
        rinfo.getActionStratumMap().put(currClass.getClassName(),new SmapStratum("RSP"));
//        currClass = new JavaClassWriter(shortName + actionSuffix);
        currClass.setSuperClass(actionImpl);
        currClass.addInterface("com.tibco.cep.runtime.service.debug.SmartStepInto");
        //constructor
        MethodRecWriter mr = currClass.createMethod("public","",shortName + actionSuffix);
//        MethodRec mr = new MethodRec(shortName + actionSuffix);
//        mr.setAccess("public");
//        mr.setReturnType("");
        mr.addArg(ruleInterface, "rule");
        //constructor body
        currBody = new StringBuilder();
        currentLinebuffer = mr.getLineBuffer();
        currBody.append("super(rule);" + BRK);
        currentLinebuffer.append("super(rule);"+BRK);
        if(!useLineBuffer) {
            mr.setBody(currBody);
        }
//        currClass.addMethod(mr);

        //execute

//        currClass.addMethod(getActionExecMethod("execute"));
        MethodRecWriter actionMethod = getActionExecMethod("execute");

        for(Iterator it2 = rinfo.getThenTrees(); it2.hasNext();) {
            sourceText.append(((RootNode)it2.next()).getSourceText());
        }
        //currClass.setSourceText(sourceText.toString().trim());
        addSourceTextMethod(sourceText.toString().trim());

//        oldClass.addClass(currClass);

        currClass = oldClass;
        currentLinebuffer = oldLineBuffer;
        currBody = oldBody;
    }

    protected void addToStringMethod() {
        MethodRecWriter toString = currClass.createMethod("public",String.class.getName(),"toString");
//        MethodRec toString = new MethodRec("toString");
//        toString.setAccess("public");
//        toString.setReturnType(String.class.getName());
        if(!useLineBuffer) {
          toString.setBody("return getSourceText() + \"(\" + getClass().getName() + \")\" ;");
        } else {
            toString.getLineBuffer().append("return getSourceText() + \"(\" + getClass().getName() + \")\" ;");
        }
//        currClass.addMethod(toString);
    }

    protected void addSourceTextMethod(String srcTxt) {
        int lengthLimit = 50;
        StringBuilder tempStr = new StringBuilder();
        boolean appendDot = false;
        for(int ii = 0; ii < srcTxt.length() && ii < lengthLimit; ii++) {
            if(srcTxt.charAt(ii) == '\n') {
                appendDot = true;
                break;
            }
            tempStr.append(srcTxt.charAt(ii));
        }
        if(srcTxt.length() > lengthLimit) {
            appendDot = true;
        }
        String text = appendDot? (tempStr.toString().trim() + "...") : tempStr.toString().trim();

        byte[] srcTxtAsBytes = null;
        try {
            srcTxtAsBytes = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //impossible
        }

        StringBuilder byteArrBuffer = new StringBuilder("try { return new " + String.class.getName() + "(new byte[]{");

        for(int jj=0; jj < srcTxtAsBytes.length; jj++) {
            if(jj > 0) byteArrBuffer.append(", ");
            byteArrBuffer.append((new Byte(srcTxtAsBytes[jj])).intValue());
        }
        byteArrBuffer.append("}, \"UTF-8\"); } catch(" + Exception.class.getName() + " ex) { return \"\"; }");

        MethodRecWriter m = currClass.createMethod("public", String.class.getName(), "getSourceText");
//        MethodRec m = new MethodRec("public", String.class.getName(), "getSourceText");
        if(!useLineBuffer) {
        m.setBody(byteArrBuffer.toString());
        } else {
            m.getLineBuffer().append(byteArrBuffer.toString());
        }
        //currClass.addMethod(m);
    }


    protected void addGetActionScopesMethod() {
        if (!isTemplated) {
            return;
        }

        final StringBuilder sb = new StringBuilder("final java.util.List<")
                .append("com.tibco.cep.interpreter.ExpressionScope")
                .append("> scopes = new java.util.ArrayList<")
                .append("com.tibco.cep.interpreter.ExpressionScope")
                .append(">();")
                .append(BRK)
                .append("com.tibco.cep.interpreter.ExpressionScope")
                .append(" scope;")
                .append(BRK);

        for (final Map<String, String> scope : this.rtScopes) {
            sb.append("scope = new ")
                    .append("com.tibco.cep.interpreter.ExpressionScope")
                    .append("();")
                    .append(BRK)

                    .append("scope.putAll(rtBindingScope);")
                    .append(BRK)
            ;
            for (final Map.Entry<String, String> entry : scope.entrySet()) {
                sb.append("scope.put(\"")
                        .append(entry.getKey())
                        .append("\", ")
                        .append(entry.getValue())
                        .append(".class);")
                        .append(BRK);

            }
            sb.append("scopes.add(scope);").append(BRK);
        }

        sb.append("return scopes;");

        final MethodRecWriter writer = currClass.createMethod(
                "protected",
                "java.util.List<com.tibco.cep.interpreter.ExpressionScope>",
                "getActionScopes");
        if (!useLineBuffer) {
            writer.setBody(sb.toString());
        } else {
            writer.getLineBuffer().append(sb.toString());
        }
    }


    protected void addConfigureBindingScopeMethod(
            RuleInfo rinfo) {

        if (!(rinfo instanceof RuleTemplateInfo)) {
            return;
        }

        final StringBuilder sb = new StringBuilder();

        for (final Iterator<RootNode> i = ((RuleTemplateInfo) rinfo).getBindingTrees(); i.hasNext(); ) {
            final RootNode rootNode = i.next();
            final String name = rootNode.getChild(0).getChild(1).getFirstToken().image;
            String className = CGUtil.convertNonboxedToBoxed(
                    ((TypeNode) rootNode.getChild(0).getChild(0)).getTypeName().toName());
            if ("DateTime".equals(className)) {
            	className = java.util.Calendar.class.getName();
            }
            rtBindingNameToClass.put(name, className);
            sb.append("rtBindingScope.put(\"").append(name).append("\", ").append(className).append(".class);")
                    .append(BRK);
        }

        final MethodRecWriter writer = currClass.createMethod("protected", "void", "configureBindingScope");

        if (!useLineBuffer) {
            writer.setBody(sb.toString());
        } else {
            writer.getLineBuffer().append(sb.toString());
        }
    }


    protected MethodRecWriter getActionExecMethod(String methodName) {
        MethodRecWriter mr = currClass.createMethod("public","void",methodName);
//        MethodRec mr = new MethodRec(methodName);
//        mr.setAccess("public");
//        mr.setReturnType("void");
        mr.addArg("java.lang.Object[]", "objects");
        //execute body
        currBody = new StringBuilder();
        currentLinebuffer = mr.getLineBuffer();
        //need to do this so that the local variables appended
        //match up with what is declared in the identifier array
        removeUnusedScorecardsFromIds();
        appendActionLocalVariables();
        LineBuffer actionBodyText = getActionBodyText();
        if(!useLineBuffer) {
            currBody.append(actionBodyText.toString());
        }
//        currentLinebuffer.append(actionBodyText);

        //append lines of the excute method corresponding
        //to lines in the action block of a rule
        if(!useLineBuffer) {
            mr.setBody(currBody);
        }

        return mr;
    }

    //todo remove
    protected void removeUnusedScorecardsFromIds() {}

    protected LineBuffer getActionBodyText() {
    	return getActionBodyText(null);
    }

    protected LineBuffer getActionBodyText(JavaClassWriter parentWriter) {
    	JavaClassWriter oldClass = this.currClass;
    	if (parentWriter != null) {
    		this.currClass = parentWriter;
    	}
        StringBuilder oldBody = currBody;
        LineBuffer oldLineBuffer = this.currentLinebuffer;
        currBody = new StringBuilder();
        //currentLinebuffer = new LineBuffer();
        //append lines of the excute method corresponding
        //to lines in the action block of a rule
        Iterator it = rinfo.getThenTrees();
        while(it.hasNext()) {
            RootNode rootNode = (RootNode)it.next();
            this.currentLinebuffer.registerNode(rootNode);
            acceptUnenclosed(rootNode);
            currBody.append(BRK);
            this.currentLinebuffer.append(BRK);
        }

//        StringBuilder ret = currBody;
        LineBuffer ret = oldLineBuffer;
        currBody = oldBody;
        this.currentLinebuffer = oldLineBuffer;
        this.currClass = oldClass;
        return ret;
    }

    protected void appendLocalVariables(Iterator ids) {
        for(int ii = 0; ids.hasNext(); ii++) {
            String id = (String)ids.next();
            final String modelPathName = identifierMap.getType(id);
            NodeType type = symbolTable.getDeclaredIdentifierType(id);
            if(!(modelPathName == null && additionalDependentScorecards.contains(id))) {
                String typeClassName;
                if(type != null && (type.isGenericInCodegen() || type.isPrimitiveType())) {
                    typeClassName = CGUtil.genericJavaTypeName(type, true, false, true);
                } else {
                    typeClassName = ModelNameUtil.modelPathToGeneratedClassName(modelPathName);
                }
                currBody.append(typeClassName + " " + ModelNameUtil.generatedScopeVariableName(id) + " = " + "(" + typeClassName + ")objects[" + ii + "];" + BRK);
                currentLinebuffer.append(typeClassName + " " + ModelNameUtil.generatedScopeVariableName(id) + " = " + "(" + typeClassName + ")objects[" + ii + "];" + BRK);
            }
        }
    }

    protected void appendActionLocalVariables() {
        appendLocalVariables(skipNonDependentScoreCardsIdsIterator());
        //append local variables for scorecards in the scope that were non dependent,
        //since they will be skipped in the above step
        Iterator skipIt = skipNonDependentScoreCardsIdsIterator();
        Iterator fullIt = identifierMap.keySet().iterator();
        while(fullIt.hasNext()) {
            String skipId = skipIt.hasNext() ? (String)skipIt.next() : null;
            String fullId = (String)fullIt.next();
            while(!fullId.equals(skipId)) {
                final String modelPathName = identifierMap.getType(fullId);
                String typeClassName = ModelNameUtil.modelPathToGeneratedClassName(modelPathName);
                currBody.append(typeClassName + " " + ModelNameUtil.generatedScopeVariableName(fullId) + " = " + typeClassName + "." + CGConstants.scorecardInstanceGetter + "();" + BRK);
                currentLinebuffer.append(typeClassName + " " + ModelNameUtil.generatedScopeVariableName(fullId) + " = " + typeClassName + "." + CGConstants.scorecardInstanceGetter + "();" + BRK);
                if(fullIt.hasNext()) {
                    fullId = (String)fullIt.next();
                } else {
                    break;
                }
            }
        }
    }

    protected void appendUnaryRelation(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Node nextNode = node.getChild(0);
        switch(node.getToken().kind) {
            case PLUS:
            case MINUS:
            case BANG:
                currBody.append(node.getToken().image);
                currentLinebuffer.append(node.getToken().image);
                accept(nextNode);
                break;
            case RETURN:
                currBody.append(node.getToken().image);
                currentLinebuffer.append(node.getToken().image);
                currBody.append(" ");
                currentLinebuffer.append(" ");
                appendFunctionArg(nextNode, symbolTable.getReturnType(), nextNode.getType());
                break;
            case EXPRESSION_NAME:
                appendExpressionName(node);
                break;
            case THROW:
                appendThrow(node);
                break;
            case INCR:
            case DECR:
            case POST_INCR:
            case POST_DECR:
                appendCompoundAssignment(node);
                break;
            default:
                assert(false);
        }
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendExpressionName(ProductionNode node) {
        //expression names are either variables
        //fully specified names of scorecards, or
        //shortcut names (only one token) to scorecards
        this.appendExpressionName(node, ((NameNode)node.getChild(0)).join("."));
    }

    protected void appendExpressionName(
            Node node,
            String name) {
        NodeType type = node.getType();

        //only do this for fully specified names of scorecards or shortcut names for scorecards
        if(type.isScorecard() && !identifierMap.containsKey(name) && symbolTable.getDeclaredIdentifierType(name).isUnknown()) {
            currBody.append(scorecardGetterFromType(type));
            currentLinebuffer.append(scorecardGetterFromType(type));
        }
        //otherwise it's a variable (scope or local)
        else if(!symbolTable.getDeclaredIdentifierType(name).isUnknown()) {
            currBody.append(ModelNameUtil.generatedScopeVariableName(name));
            currentLinebuffer.append(ModelNameUtil.generatedScopeVariableName(name));
        // or a binding
        } else {
            final String bindingClassName = rtBindingNameToClass.get(name);
            if (null != bindingClassName) {
                currBody.append("((")
                        .append(bindingClassName)
                        .append(") rtBindings.get(\"")
                        .append(name)
                        .append("\").getColumn(0))");
                currentLinebuffer.append("((")
                        .append(bindingClassName)
                        .append(") rtBindings.get(\"")
                        .append(name)
                        .append("\").getColumn(0))");
            }
            else {
                //exp name should either be a scorecard or a variable
                assert (false);
            }
        }
        updateExpressionNameDependency(node);
    }

    protected String scorecardGetterFromType(NodeType type) {
        String typeClassName = ModelNameUtil.modelPathToGeneratedClassName(type.getName());
        return typeClassName + "." + CGConstants.scorecardInstanceGetter + "()";
    }

    protected void updateExpressionNameDependency(Node node) {
        updateExpressionNameUsage(node, false);
    }
    protected void updateExpressionNameUsage(Node node, boolean indefiniteUsage) {
        // todo templated cases
        if(recordDependencies && node instanceof ProductionNode) {
            ProductionNode pNode = (ProductionNode)node;
            if(pNode.getToken().kind == EXPRESSION_NAME) {
                //where the expName is used below,
                //it will be a single identifier without "." characters
                String expName;

                if(pNode.getType().isScorecard()) {
                    //If expName is a static name of a scorecard that is in the scope, this will
                    //change expName to the scope identifier so that the dependency will be added
                    //to the scope variable
                    expName = getDependencyIdForScorecardExpName(pNode.getType());
                    if(!identifierMap.containsKey(expName)) {
                        additionalDependentScorecards.add(pNode.getType().getName());
                    }
                } else {
                    expName = ((NameNode)pNode.getChild(0)).join(".");
                }

                if (identifierMap.containsKey(expName) || additionalDependentScorecards.contains(expName)) {
                    if((usedIdentifiers != null)) {
                        usedIdentifiers.add(expName);
                    }
                    if(indefiniteUsage) {
                        dependentProperties.put(expName, INDEFINITE_USAGE);
                    } else if(dependentProperties.get(expName) == null) {
                        dependentProperties.put(expName, new HashSet());
                    }
                }
            }
        }
    }

    protected String getDependencyIdForScorecardExpName(NodeType scType) {
        assert(!scType.isArray());
        String fullPath = scType.getName();
        for(Iterator it = identifierMap.values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
            if(symbol.getType().equals(fullPath)) {
                return symbol.getName();
            }
        }
        return scType.getName();
    }

    protected void appendBinaryRelation(ProductionNode node) {
        Token token = node.getToken();
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);
        NodeType lhsType = lhs.getType();
        NodeType rhsType = rhs.getType();

        if(TokenUtils.isComparisonOp(token)) {
            appendComparisonOp(node);
        } else if(TokenUtils.isPropertyAccess(token)) {
            appendPropertyAccess(node); //, true);
        } else if(TokenUtils.isAttributeAccess(token)) {
            appendAttributeAccess(node);
        } else if(TokenUtils.isAssignOp(token)) {
            appendAssignment(node);
        } else if(token.kind == ARRAY_ACCESS) {
            appendArrayAccess(node);
        } else if(token.kind == IF || token.kind == WHILE) {
            appendIfOrWhile(node);
        } else if(TokenUtils.isCompoundAssignment(token)) {
            appendCompoundAssignment(node);
        }
        //when adding a boxed type to a string, don't unbox so that "null" is added to the string
        else if(token.kind == PLUS && lhsType.isString() && rhsType.hasBoxedContext()) {
            accept(lhs);
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            accept(rhs, false);
        } else if(token.kind == PLUS && rhsType.isString() && lhsType.hasBoxedContext()) {
            accept(lhs, false );
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            accept(rhs);
        //allow math between an object and a number
        } else if(TokenUtils.isBinaryArithmeticOp(token) && lhsType.isNumber() && rhsType.isObject()) {
            accept(lhs);
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            currBody.append(objectToPrimitive(lhsType));
            currentLinebuffer.append(objectToPrimitive(lhsType));
            accept(rhs);
            currBody.append(")");
            currentLinebuffer.append(")");
        } else if(TokenUtils.isBinaryArithmeticOp(token) && rhsType.isNumber() && lhsType.isObject()) {
            currBody.append(objectToPrimitive(rhsType));
            currentLinebuffer.append(objectToPrimitive(rhsType));
            accept(lhs);
            currBody.append(")");
            currentLinebuffer.append(")");
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            accept(rhs);
        //support logical ops between boolean and Object
        } else if(TokenUtils.isBinaryLogicalOp(token) && lhsType.isBoolean() && rhsType.isObject()) {
            accept(lhs);
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            currBody.append(objectToPrimitive(lhsType));
            currentLinebuffer.append(objectToPrimitive(lhsType));
            accept(rhs);
            currBody.append(")");
            currentLinebuffer.append(")");
        } else if(TokenUtils.isBinaryLogicalOp(token) && rhsType.isBoolean() && lhsType.isObject()) {
            currBody.append(objectToPrimitive(rhsType));
            currentLinebuffer.append(objectToPrimitive(rhsType));
            accept(lhs);
            currBody.append(")");
            currentLinebuffer.append(")");
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            accept(rhs);
        } else if(token.kind == ARRAY_LITERAL) {
            appendArrayLiteral(node);
        } else if(token.kind == ARRAY_ALLOCATOR) {
            appendArrayAllocator(node);
        } else if(token.kind == INSTANCEOF) {
            accept(lhs);
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            currBody.append(" ");
            currentLinebuffer.append(" ");
            if(!rhsType.isGeneric() && rhsType.isEntity()) {
                currBody.append(ModelNameUtil.modelPathToGeneratedClassName(rhsType.getName()));
                currentLinebuffer.append(ModelNameUtil.modelPathToGeneratedClassName(rhsType.getName()));
                if(rhsType.isArray()) {
                    currBody.append("[]");
                    currentLinebuffer.append("[]");
                }
            } else {
                currBody.append(CGUtil.genericJavaTypeName(rhsType, false, true, false));
                currentLinebuffer.append(CGUtil.genericJavaTypeName(rhsType, false, true, false));
            }
        } else {
            accept(lhs);
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            accept(rhs);
        }
    }

    protected void appendGreaterThan2Relation(ProductionNode node) {
        Token token = node.getToken();
        if(token.kind == IF) {
            appendIfOrWhile(node);
        } else if(token.kind == FOR) {
            appendForLoop(node);
        }
    }

    protected boolean isPropertyAttr(String name, NodeType type) {
        if(name.equals(propertySubjectAttrName)) return true;
        if(name.equals(propertyHistorySizeName)) return true;
        if(name.equals(propertyAtomIsSetAttrName)) return true;
        if(name.equals(lengthAttrName) && type.hasPropertyContext() && !(type.isString() && !type.isArray())) return true;
        return false;
    }

    protected String getAttrGetterPrefix(String name, NodeType type) {
    	if(name.equals(advisoryEventRuleUriAttrName)) {
    		return advisoryEventRuleUriAttrGetter + "(";
    	}
    	if(name.equals(advisoryEventRuleScopeAttrName)) {
    		return advisoryEventRuleScopeAttrGetter + "(";
    	}
    	
    	return "";
    }
    	
	protected String getAttrGetterSuffix(String name, NodeType type) {
    	if(name.equals(advisoryEventRuleUriAttrName)) {
            return ")";
        }
        if(name.equals(advisoryEventRuleScopeAttrName)) {
        	return ")";
        }
    	
    	if(name.equals(lengthAttrName)) {
            if(type.isString() && !type.isArray()) {
                return "." + stringLengthAttrGetter;
            } else if(type.hasPropertyContext()) {
                return "." + propertyArrayLengthAttrGetter;
            } else {
                return "." + primitiveArrayLengthAttrGetter;
            }
        }

        if(name.equals(entityIdAttrName)) {
            return "." + entityIdAttrGetter;
        }
        if(name.equals(entityExtIdAttrName)) {
            return "." + entityExtIdAttrGetter;
        }
        if(type.isContainedConcept() && name.equals(containedConceptParentAttrName)) {
            return "." + containedConceptParentAttrGetter;
        }
        if(name.equals(eventTtlAttrName)) {
            return "." + eventTtlAttrGetter;
        }
        if(name.equals(simpleEventPayloadAttrName)) {
            return "." + simpleEventPayloadAttrGetter;
        }
        if(name.equals(simpleEventSOAPActionAttrName)) {
            return "." + simpleEventSOAPActionAttrGetter;
        }
        if(name.equals(timeEventClosureAttrName)) {
            return "." + timeEventClosureAttrGetter;
        }
        if(name.equals(timeEventScheduledTimeAttrName)) {
            return "." + timeEventScheduledTimeAttrGetter;
        }
        if(name.equals(timeEventIntervalAttrName)) {
            return "." + timeEventIntervalAttrGetter;
        }
        if(name.equals(propertySubjectAttrName)) {
            return "." + propertySubjectAttrGetter;
        }
        if(name.equals(propertyHistorySizeName)) {
            return "." + propertyHistorySizeGetter;
        }
        if(name.equals(propertyAtomIsSetAttrName)) {
            return "." + propertyAtomIsSetAttrGetter;
        }
        if(name.equals(beExceptionMessageAttrName)) {
            return "." + beExceptionMessageAttrGetter;
        }
        if(name.equals(beExceptionErrorTypeAttrName)) {
            return "." + beExceptionErrorTypeAttrGetter;
        }
        if(name.equals(beExceptionCauseAttrName)) {
            return "." + beExceptionCauseAttrGetter;
        }
        if(name.equals(beExceptionStackTraceAttrName)) {
            return "." + beExceptionStackTraceAttrGetter;
        }
        if(name.equals(advisoryEventCategoryAttrName)) {
            return "." + advisoryEventCategoryAttrGetter;
        }
        if(name.equals(advisoryEventMessageAttrName)) {
            return "." + advisoryEventMessageAttrGetter;
        }
        if(name.equals(advisoryEventTypeAttrName)) {
            return "." + advisoryEventTypeAttrGetter;
        }
        if(type.isProcess() && ProcessModel.BASE_ATTRIBUTES.valueOf(name) != null) {
            return "." + ProcessModel.BASE_ATTRIBUTES.valueOf(name).getGetter();
        }
        assert(false);
        return ".getInvalidAttributeName()";
    }

    protected String propAtomValueGetterName(NodeType type) {
        if(type.isBoolean()) return "getBoolean()";
        if(type.isDouble()) return "getDouble()";
        if(type.isDateTime()) return "getDateTime()";
        if(type.isInt()) return "getInt()";
        if(type.isLong()) return "getLong()";
        if(type.isString()) return "getString()";
        if(type.isContainedConcept() && !type.isConceptReference()) {
            assert(currentSourceType != null);
            if(currentSourceType != null &&  generateNullContainedConcept) {
                if(currentSourceType == SourceType.RULE_CONDITION) {
                    return "getContainedConcept(true)";
                } else if(currentSourceType == SourceType.RULE_FUNCTION_CONDITION_OK) {
                    return "getContainedConcept(" + CGConstants.calledFromCondition + ")";
                }
            }
            return "getContainedConcept()";
        }
        if(type.isConcept()) return "getConcept()";
        assert(false);
        return "getInvalidPropertyAtomType()";
    }

    protected String propAtomValueSetterName(NodeType type) {
        if(type.isBoolean()) return "setBoolean";
        if(type.isDouble()) return "setDouble";
        if(type.isDateTime()) return "setDateTime";
        if(type.isInt()) return "setInt";
        if(type.isLong()) return "setLong";
        if(type.isString()) return "setString";
        if(type.isContainedConcept() && !type.isConceptReference()) return "setContainedConcept";
        if(type.isConcept()) return "setConcept";
        assert(false);
        return "setInvalidPropertyAtomType";
    }

    protected String propertyArrayElementCast(NodeType type) {
        if(type.isBoolean()) return propertyAtomInterfaceFSClassNames[RDFTypes.BOOLEAN_TYPEID];
        if(type.isDouble()) return propertyAtomInterfaceFSClassNames[RDFTypes.DOUBLE_TYPEID];
        if(type.isDateTime()) return propertyAtomInterfaceFSClassNames[RDFTypes.DATETIME_TYPEID];
        if(type.isInt()) return propertyAtomInterfaceFSClassNames[RDFTypes.INTEGER_TYPEID];
        if(type.isLong()) return propertyAtomInterfaceFSClassNames[RDFTypes.LONG_TYPEID];
        if(type.isString()) return propertyAtomInterfaceFSClassNames[RDFTypes.STRING_TYPEID];
        //use this so that getContainedConcept(boolean calledFromCondition) method is exposed
        if(type.isContainedConcept() && !type.isConceptReference()) {
            if(generateNullContainedConcept) {
                return CGConstants.propertyAtomContainedConcept_CalledFromCondition;
            } else {
                return propertyAtomInterfaceFSClassNames[RDFTypes.CONCEPT_TYPEID];
            }
        }
        if(type.isConcept()) return propertyAtomInterfaceFSClassNames[RDFTypes.CONCEPT_REFERENCE_TYPEID];
        assert(false);
        return "invalidPropertyArrayElementType";
    }

    protected String entityUnsafeCast(NodeType castTo, NodeType castFrom) {
        if(castTo.isGenericInCodegen()) {
            return CGUtil.genericJavaTypeName(castTo, true, true, true);
        } else {
            if(castTo.isArray()) {
                return ModelNameUtil.modelPathToGeneratedClassName(castTo.getComponentType(false).getName()) + "[]";
            } else {
                return ModelNameUtil.modelPathToGeneratedClassName(castTo.getName());
            }
        }
    }

    protected String numericConversionCast(NodeType castTo) {
        return CGUtil.genericJavaTypeName(castTo, true, false, true);
    }

    protected String boxedNumberConversion(NodeType convertTo) {
        if(convertTo.isDouble()) {
            return numberToDouble;
        } else if(convertTo.isInt()) {
            return numberToInteger;
        } else if(convertTo.isLong()) {
            return numberToLong;
        } else {
            return "invalidBoxedType";
        }
    }

    //unboxAtom == true means that if this access results in a PropertyAtom object,
    //the object will be unboxed into its primitive form
    protected void appendPropertyAccess(ProductionNode node) {//, boolean unboxAtom) {
        //this could be an identifier or a FunctionNode
        Node lhs = node.getChild(0);
        ProductionNode rhs = (ProductionNode)node.getChild(1);

        NodeType lhsType = lhs.getType();
        String propName = rhs.getToken().image;

        //append the expression whose property is accessed
        accept(lhs);

        if(!lhsType.isConcept()) {
            //get the Property object for this property
            currBody.append("." + GET_PREFIX + ModelNameUtil.generatedMemberName(propName) + "()");
            currentLinebuffer.append("." + GET_PREFIX + ModelNameUtil.generatedMemberName(propName) + "()");
        } else {
            appendConceptPropertyAccess(lhsType, propName);
        }
        /*
        if(unboxAtom) {
        //get the value of this property from the PropertyAtom
        appendPropertyAtomValueGetter(nodeType);
        if(addUnboxTypeCast) currBody.append(")");
        }
        */
        //add an entry to dependentProperties if necessary
        if(recordDependencies) {
            //only record dependency on properties of scope variables
            //don't record event properties
            if(lhs instanceof ProductionNode) {
                ProductionNode pLhs = (ProductionNode)lhs;
                if(pLhs.getToken().kind == EXPRESSION_NAME) {
                    NameNode expNameNode = (NameNode)pLhs.getChild(0);
                    String expName;
                    if(lhsType.isScorecard()) {
                        expName = getDependencyIdForScorecardExpName(lhsType);
                    } else {
                        expName = expNameNode.join(".");
                    }

                    if((identifierMap.containsKey(expName) && !lhsType.isEvent()) || additionalDependentScorecards.contains(expName)) {
                        Object props = dependentProperties.get(expName);
                        assert(props != null);
                        //if an identifier is flagged with INDEFINITE_USAGE
                        //it is treated as though all its properties may be accessed.
                        if(props != INDEFINITE_USAGE) {
                            ((HashSet)props).add(propName);
                        }
                    }
                }
            }
        }
    }

    protected void appendConceptPropertyAccess(NodeType lhsType, String propName) {
        int[] propInfo = new int[]{0,Integer.MIN_VALUE};
        getConceptPropInfo(getConcept(lhsType.getName()), lhsType.getName(), propName, propInfo);
        //negative means array
        String accessExp;
        if(propInfo[1] <0) {
            int typeIdx = -propInfo[1] - 1;
            accessExp = CGUtil.conceptPropertyArrayAccessExp(propInfo[0], typeIdx, false);
            
        } else {
        	accessExp = CGUtil.conceptPropertyAtomAccessExp(propInfo[0], propInfo[1], generateNullContainedConcept, false);
        }

        currBody.append(".").append(accessExp);
        currentLinebuffer.append(".").append(accessExp);
    }
    
    protected Concept getConcept(String name) {
    	Concept c = ontology.getConcept(name);
    	if(c == null) {
    		Entity e = ontology.getEntity(name);
            if(e instanceof Concept) {
                c = (Concept)e;
            } else if(e instanceof ProcessModel) {
                c = ((ProcessModel)e).cast(Concept.class);
            }
    	}
    	return c;
    }

    //a cache miss will build cache entries for the top of the inheritance hierarchy down to the level
    //with the requested property
    protected void getConceptPropInfo(Concept c, String conceptName, String propName, int[] info) {
        Map<String, int[]> cachedProps = propInfoCache.get(conceptName);
        if(cachedProps != null) {
            int[] cachedInfo = cachedProps.get(propName);
            if(cachedInfo != null) {
                for(int ii = 0; ii < info.length && ii < cachedInfo.length; info[ii] = cachedInfo[ii++]);
                return;
            }
        }

        {
        	Concept superC = c.getSuperConcept();
        	if(superC != null) getConceptPropInfo(superC, superC.getFullPath(), propName, info);
        }
        
        //cached entry was not found
        if(info[1] == Integer.MIN_VALUE) {
	        if(cachedProps != null) {
			    //sum up the size of all the ancestor levels in the hierarchy for the below code that will
			    //create the cached info for lower levels of the hierarchy
			    info[0] += cachedProps.get(NUM_PROPS)[0];
	        } else {
		    	int index = info[0];
		        List<PropertyDefinition> pds = c.getLocalPropertyDefinitions();
		        cachedProps = new HashMap(pds.size()+1);
		        for(PropertyDefinition pd : pds) {
		            int[] cachedInfo;
		            if(propName.equals(pd.getName())) {
		                cachedInfo = info;
		            } else {
		                cachedInfo = new int[2];
		            }
		            if(pd.isArray()) cachedInfo[1] = -pd.getType() - 1;
		            else cachedInfo[1] = pd.getType();
		            cachedInfo[0] = index;
		
		            cachedProps.put(pd.getName(), cachedInfo);
		
		            index++;
		        }
		        int numProps = pds.size() + c.getStateMachines().size();
		        //update index for next level if no match was found
		        if(info[1] == Integer.MIN_VALUE) info[0]+= numProps;
		        cachedProps.put(NUM_PROPS, new int[]{numProps});
		        propInfoCache.put(conceptName, cachedProps);
	        }
        }
    }

    protected void appendAttributeAccess(ProductionNode node) {
        //this could be an identifier or a FunctionNode
        Node lhs = node.getChild(0);
        ProductionNode rhs = (ProductionNode)node.getChild(1);
        NodeType nodeType = node.getType();

        //typecast for generated concepts
        if(nodeType.isConcept() && !nodeType.isGenericInCodegen()) {
            currBody.append("(");
            currentLinebuffer.append("(");
            currBody.append(ModelNameUtil.modelPathToGeneratedClassName(nodeType.getName()));
            currentLinebuffer.append(ModelNameUtil.modelPathToGeneratedClassName(nodeType.getName()));
            currBody.append(")");
            currentLinebuffer.append(")");
        }
        
        String attrName = rhs.getToken().image;
        
        currBody.append(getAttrGetterPrefix(attrName, lhs.getType()));
        currentLinebuffer.append(getAttrGetterPrefix(attrName, lhs.getType()));
        
        if(isPropertyAttr(attrName, lhs.getType()) && lhs.getType().hasPropertyContext()) {
            accept(lhs, false);
        } else {
            //append the expression whose attribute is accessed
            accept(lhs);
        }

        currBody.append(getAttrGetterSuffix(attrName, lhs.getType()));
        currentLinebuffer.append(getAttrGetterSuffix(attrName, lhs.getType()));
    }

    protected void appendAssignment(ProductionNode node) {
        appendAssignment(node, false);
    }
    protected void appendAssignment(ProductionNode node, boolean omitLHS) {
        ProductionNode lhs = (ProductionNode)node.getChild(0);
        Node rhs = node.getChild(1);

        NodeType lhsType = lhs.getType();

        boolean isPojoAtomProperty = TokenUtils.isPropertyAccess(lhs.getToken()) && isPOJOConcept(lhs.getChild(0).getType());
        boolean isPojoArrayProperty = isPojoArrayProperty(lhs);
        boolean isEventProperty = TokenUtils.isPropertyAccess(lhs.getToken()) && lhs.getChild(0).getType().isEvent();
        boolean isPropArrayAccess = TokenUtils.isArrayAccess(lhs.getToken()) && lhs.getChild(0).getType().hasPropertyContext();
        if(!omitLHS) {
            if (isPojoArrayProperty) {
                accept(lhs.getChild(0).getChild(0), false);
            }
            else if(isPropArrayAccess || isPojoAtomProperty) {
                //don't append the array access since the expression
                //in the brackets will go into the index argument of the set method

                accept(lhs.getChild(0), false);
            }
            else if(isEventProperty) {
                //for event.prop = 10; need to append event here,
                //then set$2zProp(10) later
                accept(lhs.getChild(0), false);
            }
            else {
                //don't unbox, need property object to call .set() on
                accept(lhs, false);
            }
        }

        if(isPropArrayAccess) {
            currBody.append("." + SET_PREFIX + "(");
            currentLinebuffer.append("." + SET_PREFIX + "(");
            //append the expression in the brackets for the index argument
            Node indexNode = lhs.getChild(1);
            appendArrayIndexExpression(indexNode);
            currBody.append(", ");
            currentLinebuffer.append(", ");
            appendAssignmentRHS(lhsType, rhs);
            currBody.append(")");
            currentLinebuffer.append(")");

        }
        else if(lhsType.hasPropertyContext()) {
            currBody.append("." + propAtomValueSetterName(lhsType) + "(");
            currentLinebuffer.append("." + propAtomValueSetterName(lhsType) + "(");
            appendAssignmentRHS(lhsType, rhs);
            currBody.append(")");
            currentLinebuffer.append(")");
        }
        else if (isPojoAtomProperty || isEventProperty) {
            ProductionNode rhs1 = (ProductionNode) lhs.getChild(1);
            String propName = rhs1.getToken().image;
            currBody.append("." + SET_PREFIX + ModelNameUtil.generatedMemberName(propName) + "(");
            currentLinebuffer.append("." + SET_PREFIX + ModelNameUtil.generatedMemberName(propName) + "(");

            appendAssignmentRHS(lhsType, rhs);
            currBody.append(")");
            currentLinebuffer.append(")");
        }
        else if (isPojoArrayProperty) {
            ProductionNode propertyNode = (ProductionNode) lhs.getChild(0).getChild(1);
            String propName = propertyNode.getToken().image;
            currBody.append("." + SET_PREFIX + ModelNameUtil.generatedMemberName(propName) + "(");
            currentLinebuffer.append("." + SET_PREFIX + ModelNameUtil.generatedMemberName(propName) + "(");
            Node indexNode = lhs.getChild(1);
            appendArrayIndexExpressionIntOrString(indexNode);
            currBody.append(", ");
            currentLinebuffer.append(", ");
            appendAssignmentRHS(lhsType, rhs);
            currBody.append(")");
            currentLinebuffer.append(")");

        }
        else if(lhsType.hasBoxedContext()) {
            currBody.append(" = ");
            currentLinebuffer.append(" = ");
            appendAssignmentRHS(lhsType, rhs);
        }
        else {
            currBody.append(" = (");
            currentLinebuffer.append(" = (");
            appendAssignmentRHS(lhsType, rhs);
            currBody.append(")");
            currentLinebuffer.append(")");
        }
    }


    //TODO handle pojos.  Make += append to arrays.
    //add ^=, &=, |= for boolean
    private void appendCompoundAssignment(ProductionNode node) {
        Token token = node.getToken();
        Node lhs = node.getChild(0);
        NodeType lhsType = lhs.getType();
        boolean isEventProp = lhs instanceof ProductionNode
                &&  TokenUtils.isPropertyAccess(((ProductionNode)lhs).getToken())
                && lhs.getChild(0).getType().isSimpleEvent();

        //conditions on type eliminate generic property atom
        if(lhsType.hasPropertyContext() && (lhsType.isString() || lhsType.isNumber())) {
            String method = CompoundAssignmentHelper.fromToken(token).getMethodName();

            //don't unbox, need property object to call .set() on
            accept(lhs, false);
            currBody.append(".").append(method).append("(");
            currentLinebuffer.append(".").append(method).append("(");

            if(node.getRelationKind() >= 2) {
                appendAssignmentRHS(lhsType, node.getChild(1));
            }
            currBody.append(")");
            currentLinebuffer.append(")");
        }
        //handle generic property atom here
        //validation will only let assignable lhs's get to here.
        //objectFn() += "" will not validate
        //so generate obj = plusEquals(obj, "")
        else if(lhsType.isObject() || lhsType.hasPropertyContext()) {
            accept(lhs);
            currBody.append(" = ");
            currentLinebuffer.append(" = ");
            String method = CompoundAssignmentHelper.fromToken(token).getMethodName();
            currBody.append(CodegenFunctions.class.getName()).append(".").append(method).append("(");
            currentLinebuffer.append(CodegenFunctions.class.getName()).append(".").append(method).append("(");
            accept(lhs);

            if(node.getRelationKind() >= 2) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
                appendAssignmentRHS(lhsType, node.getChild(1));
            }
            currBody.append(")");
            currentLinebuffer.append(")");
        }
        //event property
        //could have something like eventFn().prop ++;
        //eventFn() could have side effects so can't generate eventFn().set$2zProp(eventFn().get$2zProp + 1)
        //so need to call a function incr(eventFn(), "prop")
        else if(isEventProp) {
            ProductionNode lhsPn = (ProductionNode)lhs;
            Node event = lhsPn.getChild(0);
            Node propNameNode = lhsPn.getChild(1);
            assert(propNameNode instanceof ProductionNode && ((ProductionNode)propNameNode).getToken().kind == IDENTIFIER);
            String propName = ((ProductionNode)propNameNode).getToken().image;

            String method = CompoundAssignmentHelper.fromToken(token).getMethodName();
            currBody.append(CodegenFunctions.class.getName()).append(".").append(method).append("(");
            currentLinebuffer.append(CodegenFunctions.class.getName()).append(".").append(method).append("(");

            accept(event);
            currBody.append(", \"").append(propName).append('"');
            currentLinebuffer.append(", \"").append(propName).append("\"");

            if(node.getRelationKind() >= 2) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
                appendAssignmentRHS(lhsType, node.getChild(1));
            }
            currBody.append(")");
            currentLinebuffer.append(")");
        }
        else {
            /*  will apply ++ operation directly to Integer, Long, etc, but now that we require java 5 this should compile */
            boolean prefixOp = token.kind == INCR || token.kind == DECR;
            String image = token.image;
            if(token.kind == POST_INCR) image = "++";
            else if(token.kind == POST_DECR) image = "--";

            if(prefixOp) {
                currBody.append(image);
                currentLinebuffer.append(image);
            }

            accept(lhs);

            if(!prefixOp) {
                currBody.append(image);
                currentLinebuffer.append(image);
                if(node.getRelationKind() >= 2) {
                    Node rhs = node.getChild(1);
                    //parens copied from appendAssignment
                    if(!lhsType.hasBoxedContext()) {
                        currBody.append("(");
                        currentLinebuffer.append("(");
                    }
                    appendAssignmentRHS(lhsType, rhs);
                    if(!lhsType.hasBoxedContext()) {
                        currBody.append(")");
                        currentLinebuffer.append(")");
                    }
                }
            }
        }
    }

    //for this to be true the expression must have the form
    // <expression that results in a pojo concept>.<property name>[<index expression>]
    private boolean isPojoArrayProperty(ProductionNode lhs) {
        //check for brackets
        if(!TokenUtils.isArrayAccess(lhs.getToken())) return false;
        Node n1 =  lhs.getChild(0);
        //check for dot (property access)
        if (!(n1 instanceof ProductionNode)) return false;
        ProductionNode pn = (ProductionNode) n1;
        if(!TokenUtils.isPropertyAccess(pn.getToken())) return false;
        //check if the property access is applied to a pojo concept
        return isPOJOConcept(pn.getChild(0).getType());
    }

    private boolean isPOJOConcept(NodeType type) {
        if(type == null || !type.isConcept()) return false;
        Concept cept = getConcept(type.getName());
        if (cept == null) return false;
        return cept.isPOJO();
    }

    protected void appendArrayIndexExpression(Node indexNode) {
        //numeric conversion of index value
        if(!indexNode.getType().isInt()) {
            currBody.append("(int)");
            currentLinebuffer.append("(int)");
        }
        accept(indexNode);
    }

    protected void appendArrayIndexExpressionIntOrString(Node indexNode) {
        //numeric conversion of index value
        if(!(indexNode.getType().isInt() || (indexNode.getType().isString()))) {
        currBody.append("(int)");
        currentLinebuffer.append("(int)");
    }
        accept(indexNode);
    }

    protected void appendAssignmentRHS(NodeType lhsType, Node rhs) {
        NodeType rhsType = rhs.getType();
        String typeCast = "";
        //java doesn't automatically do numerical conversions on function arguments
        if(!lhsType.isTypeEqual(rhsType)) {
            if(lhsType.isEntity()) {
                typeCast = "(" + entityUnsafeCast(lhsType, rhsType) + ")";
            } else if(rhsType.isObject()) {
                    typeCast = "(" + CGUtil.genericJavaTypeName(lhsType, true, true, false) + ")";
            } else  if(!lhsType.isArray() && lhsType.isNumber()) {
                typeCast = "(" + numericConversionCast(lhsType) + ")";
            }
        }

        if(!lhsType.isTypeEqual(rhsType) && !lhsType.isArray() && lhsType.isNumber() && rhsType.hasBoxedContext()) {
        	if(!lhsType.hasBoxedContext()) {
        		currBody.append(unboxBoxed).append("(");
            	currentLinebuffer.append(unboxBoxed).append("(");
            }
        	currBody.append(boxedNumberConversion(lhsType) + "(");
        	currentLinebuffer.append(boxedNumberConversion(lhsType) + "(");
        	accept(rhs, false);
        	currBody.append(")");
        	currentLinebuffer.append(")");
        	if(!lhsType.hasBoxedContext()) {
        		currBody.append(")");
        		currentLinebuffer.append(")");
        	}
        } else if(lhsType.hasBoxedContext() && !lhsType.isArray()) {
        	if(rhsType.hasBoxedContext() || rhsType.isNull()) {
        		accept(rhs, false);

        	} else if(rhsType.isObject()) {
        		currBody.append(typeCast);
        		currentLinebuffer.append(typeCast);
        		accept(rhs, false);
        	} else {
        		currBody.append(boxUnboxed + "(" + typeCast);
        		currentLinebuffer.append(boxUnboxed + "(" + typeCast);
        		accept(rhs);
        		currBody.append(")");
        		currentLinebuffer.append(")");
        	}
        } else if(rhsType.isObject() && !lhsType.hasBoxedContext() && !lhsType.isArray() && (lhsType.isNumber() || lhsType.isBoolean())) {
        	currBody.append(typeCast);
        	currentLinebuffer.append(typeCast);
        	currBody.append(objectToPrimitive(lhsType));
        	currentLinebuffer.append(objectToPrimitive(lhsType));
        	accept(rhs);
        	currBody.append(")");
        	currentLinebuffer.append(")");
        } else if(lhsType.isObject() && !rhsType.hasBoxedContext() && !rhsType.isArray() && (rhsType.isBoolean() || rhsType.isNumber())) {
        	currBody.append(typeCast);
            currentLinebuffer.append(typeCast);
            currBody.append(boxUnboxed + "(");
            currentLinebuffer.append(boxUnboxed + "(");
            accept(rhs);
            currBody.append(")");
            currentLinebuffer.append(")");
        }
        //can't do String s = 1; in java
        else if(lhsType.isString() && !lhsType.isArray() && !rhsType.isString() && !rhsType.isNull()) {
            currBody.append(toString).append("(");
            currentLinebuffer.append(toString).append("(");
            accept(rhs);
            currBody.append(")");
            currentLinebuffer.append(")");
        } else {
            currBody.append(typeCast);
            currentLinebuffer.append(typeCast);
            accept(rhs);
        }
    }

    protected String objectToPrimitive(NodeType primitiveType) {
        String intermediateCast = "";
        if(primitiveType.isBoolean())  intermediateCast = "(java.lang.Boolean)";
        else if(primitiveType.isLong()) intermediateCast = "(java.lang.Long)";
        else if(primitiveType.isInt()) intermediateCast = "(java.lang.Integer)";
        else if(primitiveType.isDouble()) intermediateCast = "(java.lang.Double)";
        return unboxBoxed + "(" + intermediateCast;
    }

    protected void appendComparisonOp(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Token token = node.getToken();
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);
        NodeType lhsType = lhs.getType();
        NodeType rhsType = rhs.getType();

        if(lhsType.isNull()) {
            //by the rules of the type checker, a comparison to null will always be an equality comparison
            appendNullEquality(lhs, rhs, token);
        } else if(rhsType.isNull()) {
            appendNullEquality(rhs, lhs, token);
        } else if((lhsType.isString() || rhsType.isString()) && !(lhsType.isArray() || rhsType.isArray())) {
            appendStringComparisonOp(node);
        } else if((lhsType.isDateTime() || rhsType.isDateTime()) && !(lhsType.isArray() || rhsType.isArray())) {
            appendDateTimeComparisonOp(node);
            //an array may have an entity type, but the comparison is on an array object, not an entity
        } else if(((lhsType.isEntity() || rhsType.isEntity())) && !(lhsType.isArray() || rhsType.isArray())) {
            appendEntityComparisonOp(node);
        } else if(rhsType.isObject() && !lhsType.isArray()) {
            accept(lhs);
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            currBody.append(objectToPrimitive(lhsType));
            currentLinebuffer.append(objectToPrimitive(lhsType));
            accept(rhs);
            currBody.append(")");
            currentLinebuffer.append(")");
        } else if(lhsType.isObject() && !rhsType.isArray()) {
            currBody.append(objectToPrimitive(rhsType));
            currentLinebuffer.append(objectToPrimitive(rhsType));
            accept(lhs);
            currBody.append(")");
            currentLinebuffer.append(")");
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            accept(rhs);
        } else {
            accept(lhs);
            currBody.append(token.image);
            currentLinebuffer.append(token.image);
            accept(rhs);
        }
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    //op will be either '==' or '!='
    protected void appendNullEquality(Node nullNode, Node nonNullNode, Token op) {
        //check if this property object isn't a property access from a concept
        //or an element of a PropertyArray
        if(nonNullNode.getType().hasPropertyContext()
                && !(nonNullNode instanceof ProductionNode
                && (TokenUtils.isPropertyAccess(((ProductionNode)nonNullNode).getToken())
                || ((ProductionNode)nonNullNode).getToken().kind == ARRAY_ACCESS)))
        {
            nonNullNode.setBeginJavaLine(currentLinebuffer.getJavaLine());
            currBody.append(propertyEqualsNull + "(");
            currentLinebuffer.append(propertyEqualsNull + "(");
            acceptUnenclosed(nonNullNode, false);
            currBody.append(")");
            currentLinebuffer.append(")");
            nonNullNode.setEndJavaLine(currentLinebuffer.getJavaLine());
        } else if(nonNullNode.getType().hasBoxedContext()) {
            nonNullNode.setBeginJavaLine(currentLinebuffer.getJavaLine());
            accept(nonNullNode, false);
            currBody.append(op.image);
            currentLinebuffer.append(op.image);
            nullNode.setBeginJavaLine(currentLinebuffer.getJavaLine());
            accept(nullNode);
            nullNode.setEndJavaLine(currentLinebuffer.getJavaLine());
        } else {
            nonNullNode.setBeginJavaLine(currentLinebuffer.getJavaLine());
            accept(nonNullNode);
            nonNullNode.setEndJavaLine(currentLinebuffer.getJavaLine());
            currBody.append(op.image);
            currentLinebuffer.append(op.image);
            nullNode.setBeginJavaLine(currentLinebuffer.getJavaLine());
            accept(nullNode);
            nullNode.setEndJavaLine(currentLinebuffer.getJavaLine());
        }
    }

    protected void appendStringComparisonOp(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Token token = node.getToken();
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);

        switch(token.kind) {
            case NE:
                //negate the result of stringEQ
                currBody.append("!");
                currentLinebuffer.append("!");
            case EQ:
                currBody.append(stringEQ);
                currentLinebuffer.append(stringEQ);
                break;
            case GT:
                currBody.append(stringGT);
                currentLinebuffer.append(stringGT);
                break;
            case GE:
                currBody.append(stringGE);
                currentLinebuffer.append(stringGE);
                break;
            case LT:
                currBody.append(stringLT);
                currentLinebuffer.append(stringLT);
                break;
            case LE:
                currBody.append(stringLE);
                currentLinebuffer.append(stringLE);
                break;
        }
        currBody.append("(");
        currentLinebuffer.append("(");
        String paren = "";
        if(lhs.getType().isObject()) {
            currBody.append("((java.lang.String)");
            currentLinebuffer.append("((java.lang.String)");
            paren = ")";
        }
        accept(lhs);
        currBody.append(paren);
        currentLinebuffer.append(paren);
        currBody.append(", ");
        currentLinebuffer.append(", ");
        paren = "";
        if(rhs.getType().isObject()) {
            currBody.append("((java.lang.String)");
            currentLinebuffer.append("((java.lang.String)");
            paren = ")";
        }
        accept(rhs);
        currBody.append(paren);
        currentLinebuffer.append(paren);
        currBody.append(")");
        currentLinebuffer.append(")");
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendDateTimeComparisonOp(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Token token = node.getToken();
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);

        switch(token.kind) {
            case NE:
                //negate the result of dateTimeEQ
                currBody.append("!");
                currentLinebuffer.append("!");
            case EQ:
                currBody.append(dateTimeEQ);
                currentLinebuffer.append(dateTimeEQ);
                break;
            case GT:
                currBody.append(dateTimeGT);
                currentLinebuffer.append(dateTimeGT);
                break;
            case GE:
                currBody.append(dateTimeGE);
                currentLinebuffer.append(dateTimeGE);
                break;
            case LT:
                currBody.append(dateTimeLT);
                currentLinebuffer.append(dateTimeLT);
                break;
            case LE:
                currBody.append(dateTimeLE);
                currentLinebuffer.append(dateTimeLE);
                break;
        }
        currBody.append("(");
        currentLinebuffer.append("(");
        String paren = "";
        String calendar = setArgumentTypes[RDFTypes.DATETIME_TYPEID];
        String cast = "((" + calendar + ")";
        if(lhs.getType().isObject()) {
            currBody.append(cast);
            currentLinebuffer.append(cast);
            paren = ")";
        }
        accept(lhs);
        currBody.append(paren);
        currentLinebuffer.append(paren);
        currBody.append(", ");
        currentLinebuffer.append(", ");
        paren = "";
        if(rhs.getType().isObject()) {
            currBody.append(cast);
            currentLinebuffer.append(cast);
            paren = ")";
        }
        accept(rhs);
        currBody.append(paren);
        currentLinebuffer.append(paren);
        currBody.append(")");
        currentLinebuffer.append(")");
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendEntityComparisonOp(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Token token = node.getToken();
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);

        //could only be an EQ or NE comparison
        if(token.kind == NE) {
            //negate the result of entityEQ
            currBody.append("!");
            currentLinebuffer.append("!");
        }
        currBody.append(entityEQ);
        currentLinebuffer.append(entityEQ);
        currBody.append("(");
        currentLinebuffer.append("(");
        accept(lhs);
        currBody.append(", ");
        currentLinebuffer.append(", ");
        accept(rhs);
        currBody.append(")");
        currentLinebuffer.append(")");
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendArrayAccess(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);
        NodeType lhsType = lhs.getType();

        if(lhsType.hasPropertyContext()) {
            currBody.append("((");
            currBody.append(propertyArrayElementCast(lhsType));
            currBody.append(")");
            currentLinebuffer.append("(("+propertyArrayElementCast(lhsType)+")");
            accept(lhs);
            currBody.append(".");
            currBody.append(propertyArrayElementGetter);
            currBody.append("(");
            currentLinebuffer.append("."+propertyArrayElementGetter+"(");
            appendArrayIndexExpression(rhs);
            currBody.append("))");
            currentLinebuffer.append("))");
        } else {
            accept(lhs);
            currBody.append("[");
            currentLinebuffer.append("[");
            appendArrayIndexExpression(rhs);
            currBody.append("]");
            currentLinebuffer.append("]");
        }
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendIfOrWhile(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        currBody.append(node.getToken().image);
        currBody.append("(");
        currentLinebuffer.append(node.getToken().image+"(");
        //append condition
        accept(node.getChild(0));
        currBody.append(")");
        currentLinebuffer.append(")");
        //append body
        acceptUnenclosed(node.getChild(1));
        //has an else clause
        if(node.getToken().kind == IF && node.getRelationKind() == 3) {
            currentLinebuffer.append(BRK);//BRK
            currBody.append("else ");
            currentLinebuffer.append("else ");
            currentLinebuffer.append(BRK);//BRK
            acceptUnenclosed(node.getChild(2));
        }
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    //the for loop has three or four children:
    //initialization, condition (optional), increment, body
    protected void appendForLoop(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        currBody.append(node.getToken().image);
        currBody.append("(");
        currentLinebuffer.append(node.getToken().image+"(");
        //the scope of the declaration part of the for (for(int i ...)
        symbolTable.pushScope();

        for(Iterator children = node.getChildren(); children.hasNext();) {
            Node child = (Node)children.next();
            if(!children.hasNext()) {
                //this is the last node (the body) so before
                //appending it, append a closing paren
                currBody.append(")");
                currentLinebuffer.append(")");
            }
            acceptUnenclosed(child);
            if(child instanceof DeclarationNode) {
                //since accepting the declaration node will append a semicolon
                //discard the semicolon node that follows
                children.next();
            }
        }
        symbolTable.popScope();
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendStatement(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Iterator it = node.getChildren();
        Token token = node.getToken();
        if(it.hasNext() && token != null && token.kind == BLOCK_STATEMENT) {
            Node nextNode = node.getChild(0);
            if(nextNode instanceof ProductionNode) {
                Token nextToken = ((ProductionNode)nextNode).getToken();
                if(nextToken != null && nextToken.kind == TRY) {
                    appendTryCatchFinally(node);
                    node.setEndJavaLine(currentLinebuffer.getJavaLine());
                    return;
                }
            }
        }
        while(it.hasNext()) {
            Node child = (Node)it.next();
            acceptUnenclosed(child, false);
        }

        if(node.getToken().kind == LINE_STATEMENT) {
            currBody.append(";" + BRK);
            currentLinebuffer.append(";" + BRK);
        }
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }
    protected static String beExceptionGetException = CodegenFunctions.class.getName() + ".BEExceptionAsRuntimeException";
    protected void appendThrow(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        currBody.append("throw " + " " + beExceptionGetException + "(");
        currentLinebuffer.append("throw " + " " + beExceptionGetException + "(");
        acceptUnenclosed(node.getChild(0));
        currBody.append(")" + BRK);
        currentLinebuffer.append(")"+BRK);
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected static String wrapThrowableAsBEException = BEExceptionImpl.class.getName() + ".wrapThrowable";
    private int throwableCounter = 0;
    //currrently only a single catch block is allowed, so the node will have either 2 or 3 children
    protected void appendTryCatchFinally(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Iterator childIt = node.getChildren();
        ProductionNode child;
        //append try
        child = (ProductionNode)childIt.next();
        currBody.append("try");
        currentLinebuffer.append("try");
        acceptUnenclosed(child.getChild(0));
        currBody.append(BRK);
        currentLinebuffer.append(BRK);

        child = (ProductionNode)childIt.next();
        if(child.getToken().kind == CATCH) {
            symbolTable.pushScope();
            NodeType type = child.getChild(0).getType();
            String thrw = "$thrw";
            if (throwableCounter > 0) {
            	thrw+=throwableCounter;
            }
            throwableCounter++;
            currBody.append("catch (java.lang.Throwable "+thrw+") {" + BRK);
            currentLinebuffer.append("catch (java.lang.Throwable "+thrw+") {"+BRK);
            currBody.append(engineExceptionInterface + " ");
            currentLinebuffer.append(engineExceptionInterface + " ");
            //local variable name for the declared exception
            //the node is an Identifier ProductionNode
            ProductionNode idNode = (ProductionNode)child.getChild(1);
            symbolTable.addLocalDeclaration(idNode.getToken().image, type);
            currBody.append(ModelNameUtil.generatedScopeVariableName(idNode.getToken().image));
            currentLinebuffer.append(ModelNameUtil.generatedScopeVariableName(idNode.getToken().image));
            currBody.append(" = " + wrapThrowableAsBEException + "("+thrw+");" + BRK);
            currentLinebuffer.append(" = " + wrapThrowableAsBEException + "("+thrw+");"+BRK);
            //catch body
            acceptUnenclosed(child.getChild(2));
            currBody.append("}" + BRK);
            currentLinebuffer.append(" }"+BRK);
            symbolTable.popScope();
            if(childIt.hasNext()) child = (ProductionNode)childIt.next();
        }

        if(child.getToken().kind == FINALLY) {
            currBody.append("finally {" + BRK);
            currentLinebuffer.append("finally {"+BRK);
            acceptUnenclosed(child.getChild(0));
            currBody.append(BRK + "}" + BRK);
            currentLinebuffer.append(BRK+"}"+BRK);
        }
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected Node startVisitNode(Node visitingNode) {
        Node oldPrevNode = prevNode;
        prevNode = currNode;
        currNode = visitingNode;
        return oldPrevNode;
    }

    protected void endVisitNode(Node oldPrevNode) {
        currNode = prevNode;
        prevNode = oldPrevNode;
    }

    public Object visitProductionNode(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Node oldPrevNode = startVisitNode(node);
        if(TokenUtils.isStatement(node.getToken())) {
            appendStatement(node);
        } else if(node.getRelationKind() == Node.NODE_NULL_RELATION) {
            String image = node.getToken().image;
            currBody.append(image);
            currentLinebuffer.append(image);
            if(node.getType().isPromotedLong()) {
            	currBody.append('L');
            	currentLinebuffer.append('L');
            }
        }
        else if (node.getRelationKind() == Node.NODE_UNARY_RELATION) {
            appendUnaryRelation(node);
        }
        else if (node.getRelationKind() == Node.NODE_BINARY_RELATION) {
            appendBinaryRelation(node);
        } else if(node.getRelationKind() > Node.NODE_BINARY_RELATION) {
            appendGreaterThan2Relation(node);
        }
        endVisitNode(oldPrevNode);
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
        return null;
    }


    public Object visitTemplatedProductionNode(
            TemplatedProductionNode node) {
        switch (node.getMode()) {

            case MODIFY:
                node.setBeginJavaLine(currentLinebuffer.getJavaLine());
                final Node oldPrevNode = startVisitNode(node);

                this.appendRtUseOfEvaluator(node, null);

                endVisitNode(oldPrevNode);
                node.setEndJavaLine(currentLinebuffer.getJavaLine());

                return null;

            default:
                return this.visitProductionNode(node);
        }

    }


    private Map<String, String> appendNewRtScope(
            String scopeName,
            TemplatedNode node) {

        final List<String> accessibleNames = node.getAccessibleSymbolNames();

        currBody.append("final com.tibco.cep.query.stream.util.FixedKeyHashMap<String, " +
                "com.tibco.cep.query.stream.tuple.Tuple> ")
                .append(scopeName).append(" = new com.tibco.cep.query.stream.util.FixedKeyHashMap<String, " +
                "com.tibco.cep.query.stream.tuple.Tuple>(");
        currentLinebuffer.append("final com.tibco.cep.query.stream.util.FixedKeyHashMap<String, " +
                "com.tibco.cep.query.stream.tuple.Tuple> ")
                .append(scopeName).append(" = new com.tibco.cep.query.stream.util.FixedKeyHashMap<String, " +
                "com.tibco.cep.query.stream.tuple.Tuple>(");

        final Map<String, String> scope = new LinkedHashMap<String, String>();
        boolean addComma = false;
        for (final String name : accessibleNames) {
            if (addComma) {
                currBody.append(",");
                currentLinebuffer.append(",");
            } else {
                addComma = true;
            }
            currBody.append("\"").append(name).append("\"");
            currentLinebuffer.append("\"").append(name).append("\"");

            final NodeType nodeType = symbolTable.getDeclaredIdentifierType(name);
            if (!(nodeType.isVoid() || nodeType.isUnknown())) {
            	
            	final StringBuilder typeName = new StringBuilder();
            	
                if (nodeType.isGenericInCodegen() || nodeType.isPrimitiveType()) {
                	typeName.append(CGUtil.genericJavaTypeName(nodeType, false, false, false));
                }
                else {
                	typeName.append(ModelNameUtil.modelPathToGeneratedClassName(nodeType.getName(false)));
                }
                
                if (nodeType.isArray()) {
                	typeName.append("[]");
                }//TODO find actual dimension 
            	
                scope.put(name, typeName.toString());
            }
        }

        currBody.append(");" + BRK);
        currentLinebuffer.append(");" + BRK);

        for (final String name : scope.keySet()) {
            currBody.append(scopeName)
                    .append(".put(\"")
                    .append(name)
                    .append("\", new com.tibco.cep.interpreter.SimpleTuple(")
                    .append(ModelNameUtil.generatedScopeVariableName(name))
                    .append("));" + BRK);
            currentLinebuffer.append(scopeName)
                    .append(".put(\"")
                    .append(name)
                    .append("\", new com.tibco.cep.interpreter.SimpleTuple(")
                    .append(ModelNameUtil.generatedScopeVariableName(name))
                    .append("));" + BRK);
        }

        return scope;
    }


    public Object visitProductionNodeListNode(ProductionNodeListNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Node oldPrevNode = startVisitNode(node);
        if(node.getListType() == ProductionNodeListNode.BLOCK_TYPE) {
            symbolTable.pushScope();
            currBody.append("{" + BRK);
            currentLinebuffer.append("{"+BRK);
        } else if(node.getListType() == ProductionNodeListNode.ARRAY_LITERAL_TYPE) {
            currBody.append("{ ");
            currentLinebuffer.append("{");
        }
        for(Iterator it = node.getChildren(); it.hasNext();) {
            acceptUnenclosed((Node)it.next());
            if((node.getListType() == ProductionNodeListNode.STATEMENT_EXPRESSION_LIST_TYPE || node.getListType() == ProductionNodeListNode.ARRAY_LITERAL_TYPE)&& it.hasNext()) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
        }
        if(node.getListType() == ProductionNodeListNode.BLOCK_TYPE) {
            symbolTable.popScope();
            currBody.append("}");
            currentLinebuffer.append("}");
        } else if(node.getListType() == ProductionNodeListNode.ARRAY_LITERAL_TYPE) {
            currBody.append("}");
            currentLinebuffer.append("}");
        }
        endVisitNode(oldPrevNode);
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
        return null;
    }

    public Object visitDeclarationNode(DeclarationNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        this.visitDeclarationNodeInner(node);
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
        return null;
    }

    private List<String> visitDeclarationNodeInner(
            DeclarationNode node) {

        Node oldPrevNode = startVisitNode(node);

        final NodeType type = this.appendType(node);

        currBody.append(" ");
        currentLinebuffer.append(" ");

        final List<String> variableNames = new LinkedList<String>();
        for(Iterator it = node.getDeclarations(); it.hasNext();) {
            ProductionNode decl = (ProductionNode)it.next();
            String id;

            if(TokenUtils.isAssignOp(decl.getToken())) {
                id = ((ProductionNode)decl.getChild(0)).getToken().image;
            } else {
                id = decl.getToken().image;
            }

            variableNames.add(id);

            currBody.append(ModelNameUtil.generatedScopeVariableName(id));
            currentLinebuffer.append(ModelNameUtil.generatedScopeVariableName(id));

            if(TokenUtils.isAssignOp(decl.getToken())) {
                Node rhs = decl.getChild(1);
                if(rhs instanceof ProductionNodeListNode) {
                    currBody.append(" = ");
                    currentLinebuffer.append(" = ");
                    appendArrayLiteralInitializer(type.getComponentType(true), (ProductionNodeListNode)rhs);
                } else {
                    appendAssignment(decl, true);
                }
            } else {
                //Add default initialization to prevent
                //"id may not be initialized" errors from javac.
                currBody.append(" = " + defaultLocalInitialization(type));
                currentLinebuffer.append(" = " + defaultLocalInitialization(type));
            }
            if(it.hasNext()) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }

            //don't update the symbol table until after the entire declaration
            //for this id has been processed
            symbolTable.addLocalDeclaration(id, type);
        }
        currBody.append(";" + BRK);
        currentLinebuffer.append(";"+BRK);
        endVisitNode(oldPrevNode);

        return variableNames;
    }


    private NodeType appendType(
            Node node) {

        final NodeType type = node.getType();
        if (type.isGenericInCodegen() || type.isPrimitiveType()) {
            currBody.append(CGUtil.genericJavaTypeName(type, false, false, false));
            currentLinebuffer.append(CGUtil.genericJavaTypeName(type, false, false, false));
        }
        else {
            currBody.append(ModelNameUtil.modelPathToGeneratedClassName(type.getName(false)));
            currentLinebuffer.append(ModelNameUtil.modelPathToGeneratedClassName(type.getName(false)));
        }
        if (node instanceof DeclarationNode) {
            final TypeNode typeNode = ((DeclarationNode) node).getDeclarationType();
            if (null != typeNode) {
                for (int ii = 0; ii < typeNode.getArrayDimension(); ii++) {
                    currBody.append("[]");
                    currentLinebuffer.append("[]");
                }
            }
        } //todo: else...
        return type;
    }


    protected void appendArrayLiteralInitializer(NodeType unitType, ProductionNodeListNode rhs) {
        rhs.setBeginJavaLine(currentLinebuffer.getJavaLine());
        currBody.append("{ ");
        currentLinebuffer.append("{ ");
        for(Iterator it = rhs.getChildren(); it.hasNext();) {
            Node child = (Node)it.next();
            appendAssignmentRHS(unitType, child);
            if(it.hasNext()) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
        }
        currBody.append(" }");
        currentLinebuffer.append(" }");
        rhs.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendArrayLiteral(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        currBody.append("new ");
        currentLinebuffer.append("new ");
        NodeType type = node.getType();
        if(type.isGenericInCodegen() || type.isPrimitiveType()) {
            currBody.append(CGUtil.genericJavaTypeName(type, false, false, false));
            currentLinebuffer.append(CGUtil.genericJavaTypeName(type, false, false, false));
        } else {
            currBody.append(ModelNameUtil.modelPathToGeneratedClassName(type.getName(false)));
            currentLinebuffer.append(ModelNameUtil.modelPathToGeneratedClassName(type.getName(false)));
        }

        //first node is a TypeNode for the type
        Node secondNode = node.getChild(1);
        currBody.append("[]");
        currentLinebuffer.append("[]");
        appendArrayLiteralInitializer(node.getType().getComponentType(true), (ProductionNodeListNode)secondNode);
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendArrayAllocator(ProductionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        currBody.append("new ");
        currentLinebuffer.append("new ");
        NodeType type = node.getType();
        if(type.isGenericInCodegen() || type.isPrimitiveType()) {
            currBody.append(CGUtil.genericJavaTypeName(type, false, false, false));
            currentLinebuffer.append(CGUtil.genericJavaTypeName(type, false, false, false));
        } else {
            currBody.append(ModelNameUtil.modelPathToGeneratedClassName(type.getName(false)));
            currentLinebuffer.append(ModelNameUtil.modelPathToGeneratedClassName(type.getName(false)));
        }

        //first node is a TypeNode for the type
        Node secondNode = node.getChild(1);
        currBody.append("[");
        currentLinebuffer.append("[");
        appendArrayIndexExpression(secondNode);
        currBody.append("]");
        currentLinebuffer.append("]");
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    public String defaultLocalInitialization(NodeType type) {
        if(type.isPrimitiveType() && type.hasPrimitiveContext() && !type.isArray()) {
            if(type.isInt()) {
                return "0";
                //return String.valueOf(PropertyAtomIntImpl.DEFAULT_VALUE);
            } else if(type.isLong()) {
                return "0L";
                //return String.valueOf(PropertyAtomLongImpl.DEFAULT_VALUE);
            } else if(type.isDouble()) {
                return "0.0";
                //return String.valueOf(PropertyAtomDoubleImpl.DEFAULT_VALUE);
            } else if(type.isBoolean()) {
                return "false";
                //return String.valueOf(PropertyAtomBooleanImpl.DEFAULT_VALUE);
            } else if(type.isString()) {
                return "null";
                //return PropertyAtomStringImpl.class.getName() + ".DEFAULT_VALUE";
            } else if(type.isDateTime()) {
                return com.tibco.cep.runtime.model.element.PropertyAtomDateTime.class.getName() + ".DEFAULT_VALUE";
                //return PropertyAtomDateTimeImpl.class.getName() + ".DEFAULT_VALUE";
            } else {
                return "null";
            }

        } else {
            return "null";
        }
    }

    public Object visitRootNode(RootNode node) {
        prevNode = null;
        currNode = node;
        currentSourceType = node.getSourceType();
        acceptUnenclosed(node.getChild(0));
        currentSourceType = null;
        prevNode = null;
        currNode = null;
        return null;
    }

    public Object visitTypeNode(TypeNode node) {
        assert(false);
        Node oldPrevNode = startVisitNode(node);
        endVisitNode(oldPrevNode);
        return null;
    }


    @Override
    public Object visitTemplatedDeclarationNode(
            TemplatedDeclarationNode node) {

        node.setBeginJavaLine(currentLinebuffer.getJavaLine());

        // Basic declaration
        final String name = node.getType() == null || node.getType().isVoid()
        		? null : this.visitDeclarationNodeInner(node).get(0);

        // Initializer
        this.appendRtUseOfEvaluator(node, name);

        node.setEndJavaLine(currentLinebuffer.getJavaLine());
        return null;
    }


    private void appendRtUseOfEvaluator(
            TemplatedNode node,
            String name) {

        final int index = this.rtScopes.size();

        // Starts the block
        currBody.append("if (null != rtThenEvaluators[").append(index).append("]) {" + BRK);
        currentLinebuffer.append("if (null != rtThenEvaluators[").append(index).append("]) {" + BRK);

        // Defines the local variables for the evaluator
        this.rtScopes.add(this.appendNewRtScope("rtScope", node));

        // If a variable name is provided, sets it to the templated initializer
        if (null != name) {
            this.appendExpressionName(node, name);

            currBody.append(" = (");
            currentLinebuffer.append(" = (");

            this.appendType(node);

            currBody.append(") ");
            currentLinebuffer.append(") ");
        }

        // Calls the evaluator
        currBody.append("rtThenEvaluators[").append(index).append("].evaluate(null, null, rtScope);" + BRK);
        currentLinebuffer.append("rtThenEvaluators[").append(index).append("].evaluate(null, null, rtScope);" + BRK);

        // Ends the block
        currBody.append("}" + BRK);
        currentLinebuffer.append("}" + BRK);
    }


    public Object visitNameNode(NameNode node) {
        assert(false);
        Node oldPrevNode = startVisitNode(node);
        endVisitNode(oldPrevNode);
        return null;
    }

    private String mapperFnSuffix = "2";
    public Object visitFunctionNode(FunctionNode node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        Node oldPrevNode = startVisitNode(node);
        FunctionRec fr = node.getFunctionRec();
        if(fr == null) {
            String name = "";
            if(node.getName() != null) name = node.getName().toName();
            throw new RuntimeException("Function " + name + " not found.  Try validating project before building.");
        }
        Predicate p = fr.function;
        //generated class name (with returnTypeFnNameArg1Arg2...)
        //so put all args into an Object[] so that signature changes aren't a schema change for hot deploy
        boolean passArgsAsArray = false;
        boolean isOversizeName = false;
        if(p instanceof ModelRuleFunction) {
            ModelRuleFunction rulefn = (ModelRuleFunction)p;
            passArgsAsArray = isOversizeName = ModelNameUtil.endsWithOversizeRuleFnClassNameSuffix(rulefn.getModelClass());
        } else if(p instanceof EMFEventModelFunction_Create) {
            passArgsAsArray = CGUtil.areEventConstructorArgsOversize((EventImpl)((EMFEventModelFunction_Create) p).getModel());
        }

        //the return type for rule functions with oversize names may be plain Object so insert a cast
        //to the appropriate return type
        if(isOversizeName) {
            if(fr.returnType != null && !fr.returnType.isVoid()) {
                String typeName;
                if(fr.returnType.isGenericInCodegen() || fr.returnType.isPrimitiveType()) typeName = CGUtil.genericJavaTypeName(fr.returnType, true, fr.returnType.isArray(), true);
                else typeName = ModelNameUtil.modelPathToGeneratedClassName(fr.returnType.getName());
                //prevNode should never be null since RootNode is always the outermost node
                if(prevNode != null) {
                    boolean statementExp = prevNode instanceof ProductionNode && ((ProductionNode)prevNode).getToken().kind == LINE_STATEMENT;
                    boolean statementExpList = prevNode instanceof ProductionNodeListNode && ((ProductionNodeListNode)prevNode).getListType() == ProductionNodeListNode.STATEMENT_EXPRESSION_LIST_TYPE;
                    //don't put this cast in when the function is part of an expression statement (return value is ignored)
                    //or else javac will complain.
                    if(!statementExp && !statementExpList) {
                        currBody.append("(" + typeName + ")");
                        currentLinebuffer.append("(" + typeName + ")");
                    }
                }
            }
        }

//        if(p.requiresAssert()) currBody.append("assertObject(");  //todo - how to assert?

        // Generate java code directly for xslt mappings
        boolean transformXSLT = false; // TODO : extract this to a preference/system property
        if (p instanceof JavaStaticFunctionWithXSLT && ((JavaStaticFunctionWithXSLT)p).isXsltFunction() && transformXSLT) {
        	try {
        		Object o = new XsltCodeGenerator(ontology, symbolTable).transformXSLTToJava(currentLinebuffer,
        				fr, node, oldPrevNode, currentSourceType.isFunction(), currClass, identifierMap, additionalDependentScorecards);
        		endVisitNode(oldPrevNode);
        		node.setEndJavaLine(currentLinebuffer.getJavaLine());
        		return o;
        	} catch (UnsupportedXsltMappingException e) {
        		// fall through to normal processing
        		e.printStackTrace();
        	}
        }

        currBody.append(p.code());
        currentLinebuffer.append(p.code());
        if(fr.isMapper) {
            currBody.append(mapperFnSuffix);
            currentLinebuffer.append(mapperFnSuffix);
        }
        currBody.append("(");
        currentLinebuffer.append("(");

        if(p.reevaluate()) {
            alwaysEvaluate = true;
        }

        if(usedRuleFunctions != null && p instanceof ModelRuleFunction) {
            usedRuleFunctions.add(((ModelRuleFunction)p).getModelClass());
        }

        boolean leadingArg = false;
        //add the calledFromCondition argument to non-action-only rule functions
        if(generateNullContainedConcept && !fr.isMapper && fr.function != null && fr.function instanceof ModelRuleFunction && ((ModelRuleFunction)fr.function).isValidInCondition()) {
            assert(currentSourceType != null);
            if(currentSourceType != null) {
                if(currentSourceType == SourceType.RULE_CONDITION) {
                    currBody.append("true");
                    currentLinebuffer.append("true");
                } else if(currentSourceType == SourceType.RULE_FUNCTION_CONDITION_OK) {
                    //pass along the calledFromCondition argument
                    currBody.append(CGConstants.calledFromCondition);
                    currentLinebuffer.append(CGConstants.calledFromCondition);
                } else {
                    currBody.append("false");
                    currentLinebuffer.append("false");
                }
            } else {
                currBody.append("false");
                currentLinebuffer.append("false");
            }
            leadingArg = true;
        }

        if(passArgsAsArray) {
            if(leadingArg) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
            leadingArg = false;
            currBody.append("new java.lang.Object[]{");
            currentLinebuffer.append("new java.lang.Object[]{");
        }

        if(fr.isMapper) {
            if(leadingArg) {
                currBody.append(", ");
                currentLinebuffer.append(", ");
            }
            leadingArg = false;
            appendMapperArgs(node);
        } else {
            int varargsStart = Integer.MAX_VALUE;
            //studio and user do not know this is a varargs method
            boolean isHiddenVarargs = !fr.function.isVarargs() && fr.function.isVarargsCodegen();
            if(fr.function.isVarargsCodegen()) varargsStart = fr.argTypes.length - 1;
            
            {
	            Iterator children = node.getArgs();
	            NodeType fnArgType = null;
	            for(int ii = 0; children.hasNext(); ii++) {
	                if(leadingArg) {
	                    currBody.append(", ");
	                    currentLinebuffer.append(", ");
	                    leadingArg = false;
	                }
	                Node child = (Node)children.next();
	                NodeType userArgType = child.getType();
	                
	                //args passed by user to hidden varargs need to be casted / unboxed, etc 
	                //as if they were passed to individual function arguments as is presented to the user
	                if(ii < varargsStart || isHiddenVarargs) {
	                	fnArgType = fr.argTypes[ii];
	                }
	                //for passing more than one arg to a varargs function there are two scenarios
	                //The first is the user and studio know it's a varargs method (.isVarargs() == .isVarargsCodegen() == true)
	                //in this case proceed with fnArgType set below for all arguments after varargsStart
	                //The other is the user and studio think it is not varargs.
	                //In this case, proceed with fnArgType = fr.argTypes[ii]
	                //but use fr.varargsCodegenArgType to decide about the following special case.
	                //In either case, if there is only one argument and it is an array, it may be necessary to
	                //cast it to Object to force it to be wrapped in another Object array
	                if(ii == varargsStart) {
	                    fnArgType = fr.argTypes[ii];
	                    NodeType _fnArgType = fnArgType;
	                    if(isHiddenVarargs) _fnArgType = fr.varargsCodegenArgType;
	                    //if more than 1 args passed to varargs, always use component type for fnArgType
	                	//for single non-array arguments, use the component type
	                	//use original type (no change) for null arg and for arrays of compatible types
	                    //use Object for arrays passed to an Object... parameter for consistency 
	                	//between passing ..., ObjArray1) and ..., ObjArray1, ObjArray2), 
	                	//otherwise adding the second arg would change how the first arg was passed
	                    if(children.hasNext() || _fnArgType.isObject() || (!children.hasNext() && !userArgType.isArray())) {
	                        _fnArgType = _fnArgType.getComponentType(false);
	                    }
	                    //for single array arguments passed to Object... always cast them to Object so that passing Object[] 
	                    //passes a single argument the same as passing Object[], Object[] passes two arguments
	                    if(userArgType.isArray() && !children.hasNext() && _fnArgType.isObject()) {
	                    	currBody.append("(").append(Object.class.getName()).append(")");
	                    	currentLinebuffer.append("(").append(Object.class.getName()).append(")");
	                    }
	                    if(!isHiddenVarargs) fnArgType = _fnArgType;
	                }
	
	                if(passArgsAsArray && fnArgType.isValueType() && !fnArgType.hasBoxedContext()) {
	                    //do the type conversion without unboxing
	                    NodeType fixedFnArgType = new NodeType(fnArgType, null, TypeContext.BOXED_CONTEXT, null, null, null, null);
	                    appendFunctionArg(child, fixedFnArgType, userArgType, p instanceof ConceptModelFunction);
	
	                } else {
	                    appendFunctionArg(child, fnArgType, userArgType, p instanceof ConceptModelFunction);
	                }
	                if(children.hasNext()) {
	                    currBody.append(", ");
	                    currentLinebuffer.append(", ");
	                }
	                //if a declared object is passed as a function argument
	                //flag it with INDEFINITE_ACCESS
	                updateExpressionNameUsage(child, true);
	            }
	        }
        }

        if(passArgsAsArray) {
            currBody.append("}");
            currentLinebuffer.append("}");
        }
        currBody.append(")");
        currentLinebuffer.append(")");
//        if(p.requiresAssert()) currBody.append(")");  //todo - how to assert?

        assert(fr.returnType != null && fr.returnType.equals(node.getType()));
        //System.out.println("ReturnType is " + retType.getName() + " NodeType is " + node.getType().getName());
        endVisitNode(oldPrevNode);
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
        return null;
    }

    protected void appendFunctionArg(Node node, NodeType fnArgType, NodeType userArgType) {
        appendFunctionArg(node, fnArgType, userArgType, false);
    }
    protected void appendFunctionArg(Node node, NodeType fnArgType, NodeType userArgType, boolean isConceptModelFunction) {
        int closingParens = 0;
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());

        if(fnArgType.hasBoxedContext() && !userArgType.hasBoxedContext() && !userArgType.isNull()) {
            currBody.append(boxUnboxed + "(");
            currentLinebuffer.append(boxUnboxed + "(");
            closingParens++;
        } else if(fnArgType.isObject() && !userArgType.isArray() && !userArgType.hasBoxedContext() && (userArgType.isNumber() || userArgType.isBoolean())) {
            currBody.append(boxUnboxed + "(");
            currentLinebuffer.append(boxUnboxed + "(");
            closingParens++;
        } else if (userArgType.isObject() && !fnArgType.isArray() && fnArgType.hasPrimitiveContext() && (fnArgType.isNumber() || fnArgType.isBoolean())){
            currBody.append(objectToPrimitive(fnArgType));
            currentLinebuffer.append(objectToPrimitive(fnArgType));
            closingParens++;
        }

        //append the typecast needed to pass a more general entity type in place of a more specific type (unsafe cast)
        //the cast will be appended whenever the user type and function type differ

        if(isConceptModelFunction && fnArgType.isConcept()) {
            if(fnArgType.isContainedConcept()) {
                fnArgType = new NodeType(NodeTypeFlag.GENERIC_CONTAINED_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, fnArgType.isArray());
            } else if(fnArgType.isConcept()) {
                fnArgType = new NodeType(NodeTypeFlag.GENERIC_CONCEPT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false, fnArgType.isArray());
            }
        }

        boolean noUnboxOverride = false;
        if(!fnArgType.isTypeEqual(userArgType)) {
            //given the type checking rules, only generic properties will need typecasting when passed to a property argument
        	//generic Property doesn't have property context
            if(fnArgType.hasPropertyContext()) {
                currBody.append("(" + CGUtil.genericJavaTypeName(fnArgType, false, false, true) + ")");
                currentLinebuffer.append("(" + CGUtil.genericJavaTypeName(fnArgType, false, false, true) + ")");
            } else if(fnArgType.isGenericProperty()) {
            	currBody.append("(" + CGUtil.genericJavaTypeName(fnArgType, true, true, true) + ")");
                currentLinebuffer.append("(" + CGUtil.genericJavaTypeName(fnArgType, true, true, true) + ")");
            } else if(fnArgType.isEntity()) {
                currBody.append("(" + entityUnsafeCast(fnArgType, userArgType) + ")");
                currentLinebuffer.append("(" + entityUnsafeCast(fnArgType, userArgType) + ")");
            } //append typecast for numerical conversion if necessary
            else if(!fnArgType.hasPropertyContext() && !fnArgType.isArray() && fnArgType.isNumber() && userArgType.isNumber()) {
                if(!userArgType.hasBoxedContext()) {
                	currBody.append("(" + numericConversionCast(fnArgType) + ")");
                	currentLinebuffer.append("(" + numericConversionCast(fnArgType) + ")");
                } else {
                    //the boxedNumberConversion function takes a boxed type argument
                    //so don't unbox yet, even if the fnArg is not a boxed type
                    noUnboxOverride = true;
                    //do this manually since it is overriden by the above
                    if(!fnArgType.hasBoxedContext()) {
                        currBody.append(unboxBoxed).append("(");
                        currentLinebuffer.append(unboxBoxed).append("(");
                        ++closingParens;
                    }
                    currBody.append(boxedNumberConversion(fnArgType)).append("(");
                    currentLinebuffer.append(boxedNumberConversion(fnArgType)).append("(");
                    ++closingParens;
                }
            } else if(userArgType.isObject()) {
                currBody.append("(" + CGUtil.genericJavaTypeName(fnArgType, false, true, false) + ")");
                currentLinebuffer.append("(" + CGUtil.genericJavaTypeName(fnArgType, false, true, false) + ")");
            }
        }

        //if the function argument expects a Property type, and the argument is a property of a concept,
        //the unbox condition will keep the concept from being unboxed
        boolean unbox = !noUnboxOverride && ((userArgType.hasPropertyContext() && !(fnArgType.hasPropertyContext() || fnArgType.isGenericProperty()))
        			|| (!fnArgType.hasBoxedContext() && userArgType.hasBoxedContext()));

        accept(node, unbox);
        for(; closingParens > 0; closingParens--)  {
            currBody.append(")");
            currentLinebuffer.append(")");
        }

        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected void appendMapperArgs(FunctionNode node) {
        //only arg in rules language is the xml string
        //(surrounding double quotes are included)
        String xmlStringLiteral = ((ProductionNode)node.getChild(0)).getToken().image;
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());

        //currBody.append('"' + mapperFnUID() + '"');
        //currBody.append(", ");
        if(xmlStringLiteral == null || xmlStringLiteral.length() <= 2) {
            currBody.append("\"\"");
            currentLinebuffer.append("\"\"");
        } else {
            //remove quote marks from the xml string literal
            String xmlString = xmlStringLiteral.substring(1, xmlStringLiteral.length() -1);
            if(CGUtil.isStringConstantOversize(xmlString)) {
                appendOversizeXMLArg(xmlString);
            } else {
                currBody.append(xmlStringLiteral);
                currentLinebuffer.append(xmlStringLiteral);
            }
        }
        currBody.append(", ");
        currentLinebuffer.append(", ");

        if(((JavaStaticFunctionWithXSLT)node.getFunctionRec().function).isXPathFunction()) {
            appendXPathVars(xmlStringLiteral);
        } else {
            appendMapperVars(symbolTable.visibleIds());
        }
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    protected static int oversizeXMLArgCount = 0;
    protected void appendOversizeXMLArg(String xml) {
        String argKey = "OversizeXMLArg" + oversizeXMLArgCount;
        oversizeXMLArgCount++;
        oversizeStringConstants.setProperty(argKey, xml);

        currBody.append(oversizeStringConstantGetter);
        currBody.append("(\"");
        currBody.append(argKey);
        currBody.append("\")");
        currentLinebuffer.append(oversizeStringConstantGetter);
        currentLinebuffer.append("(\"");
        currentLinebuffer.append(argKey);
        currentLinebuffer.append("\")");
    }

    protected void appendMapperVars(Iterator ids) {
        StringBuilder idArrayBody = new StringBuilder();
        StringBuilder objArrayBody = new StringBuilder();



        while(ids.hasNext()) {
            String id = (String)ids.next();

            NodeType idType = symbolTable.getDeclaredIdentifierType(id);
            //skip unknown identifiers that may come from global variables or from incorrect parsing of an xpath expression
            if(!idType.isUnknown()) {
                appendMapperVar(idArrayBody, objArrayBody, id, ids.hasNext(), idType.isArray());
            }
        }

        currBody.append(variableListImplFactory + "(new java.lang.String[] { ");
        currBody.append(idArrayBody);
        currBody.append(" }, new java.lang.Object[] { ");
        currBody.append(objArrayBody);
        currBody.append(" })");
        currentLinebuffer.append(variableListImplFactory + "(new java.lang.String[] { ");
        currentLinebuffer.append(idArrayBody.toString());
        currentLinebuffer.append(" }, new java.lang.Object[] { ");
        currentLinebuffer.append(objArrayBody.toString());
        currentLinebuffer.append(" })");
    }

    protected void appendMapperVar(StringBuilder idArrayBody, StringBuilder objArrayBody, String id, boolean addComma, boolean isArray) {
        NodeType idType = symbolTable.getDeclaredIdentifierType(id);
        //skip unknown identifiers that may come from global variables or from incorrect parsing of an xpath expression
        if(!idType.isUnknown()) {
            idArrayBody.append('"' + id + (isArray ? "*" : "") + '"');

            objArrayBody.append(wrapForMapperArgs(id));

            if(addComma) {
                idArrayBody.append(", ");
                objArrayBody.append(", ");
            }
        }
    }

    protected void appendXPathVars(String xpathString) {
        //an empty string in the rules language ("") will have length 2 here due to the quote marks
        if(xpathString == null || xpathString.length() <= 2) return;

        //calculate dependencies
        if(recordDependencies) {
            List vars = XSTemplateSerializer.getVariablesinXPath(xpathString.substring(1,xpathString.length()-1));
            for(Iterator varsIt = vars.iterator(); varsIt.hasNext();) {
                String idName = (String)varsIt.next();
                NodeType idType = symbolTable.getDeclaredIdentifierType(idName);
                if(!idType.isUnknown()) {
                    if(identifierMap.containsKey(idName)) {
                        dependentProperties.put(idName, INDEFINITE_USAGE );
                        if(usedIdentifiers != null) {
                            usedIdentifiers.add(idName);
                        }
                    }
                }
            }
            appendMapperVars(vars.iterator());
        } else {
            //if the XPath function is used in a non-condition context, do this so that the local variables are included.
            appendMapperVars(symbolTable.visibleIds());
        }

    }

    protected String wrapForMapperArgs(String id) {
        return wrapForMapperArgs(id, symbolTable.getDeclaredIdentifierType(id));
    }
    protected String wrapForMapperArgs(String value, NodeType type) {
        assert(type != null && !type.hasPropertyContext());

        value = ModelNameUtil.generatedScopeVariableName(value);
        if(type.hasPrimitiveContext() && !type.isArray() && (type.isInt() || type.isLong() || type.isDouble() || type.isBoolean())) {
            return boxUnboxed + "(" + value + ")";
        } else {
            return value;
        }
    }

    //protected static int mapperIDIndex = 0;
    //protected String mapperFnUID() {
    //    return String.valueOf(mapperIDIndex++);
    //    return ModelNameUtil.modelPathToGeneratedClassName(rinfo.getFullName()) + mapperIDIndex++;
    //}

    protected void appendPropertyAtomValueGetter(NodeType propType) {
        String getterName = propAtomValueGetterName(propType);
        if(getterName != null) {
            currBody.append("." + getterName);
            currentLinebuffer.append("." + getterName);
        }
    }

    private void accept(Node node) {
        accept(node, true);
    }
    private void accept(Node node, boolean unbox) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        currBody.append("(");
        currentLinebuffer.append("(");
        acceptUnenclosed(node, unbox);
        currBody.append(")");
        currentLinebuffer.append(")");
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }
    private void acceptUnenclosed(Node node) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        acceptUnenclosed(node, true);
        node.setEndJavaLine(currentLinebuffer.getJavaLine());
    }

    private void acceptUnenclosed(Node node, boolean unbox) {
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        NodeType nodeType = node.getType();
        //the unboxing operation only needs to happen
        //for non-array properties of Concepts
        unbox = unbox && (nodeType != null)
                && (nodeType.hasPropertyContext() || nodeType.hasBoxedContext())
                && !nodeType.isArray() && !(node instanceof DeclarationNode);
        //for PropertyAtoms of Concepts,
        //<expression>.property needs to become
        //((concept type of property)<expression>.getPropety().getValue())
        //typecast is only strictly necessary when getValue() returns a generated class
        //so if a generic type is unboxed, the typecast is omitted
        boolean addUnboxTypeCast = unbox && nodeType.isConcept() && !nodeType.isGenericInCodegen() && nodeType.hasPropertyContext();

        int closingParens = 0;
        if(unbox) {
            if(nodeType.hasPropertyContext()) {
                if(addUnboxTypeCast) {

                    currBody.append("((");
                    currBody.append(ModelNameUtil.modelPathToGeneratedClassName(nodeType.getName()));
                    currBody.append(")");

                    currentLinebuffer.append("((");
                    currentLinebuffer.append(ModelNameUtil.modelPathToGeneratedClassName(nodeType.getName()));
                    currentLinebuffer.append(")");
                    closingParens++;
                }
            } else if(nodeType.hasBoxedContext()) {
                currBody.append("("+ unboxBoxed + "(");
                currentLinebuffer.append("("+ unboxBoxed + "(");
                closingParens+=2;
            }
        }
        node.accept(this);
        if(unbox && nodeType.hasPropertyContext()) {
            //get the value of this property from the PropertyAtom
            appendPropertyAtomValueGetter(nodeType);
            //if(addUnboxTypeCast) currBody.append(")");
        }

        for(; closingParens > 0; closingParens--) {
            currentLinebuffer.append(")");
            currBody.append(")");
        }
    }


}
////Rules can be redeployed by the BUI, so their keys for oversizeXMLArgs need to be the same
////across many builds.  Therefore the key will be rule_name + number, where number starts
////at zero for each new rule.  oversieXMLArgCountInstance is used for this.
////Because of this rinfo.getFullName() must always return the
////full path of the resource when a rule is being generated.
//protected int oversizeXMLArgCountInstance = 0;
////When a method is generated instead of a Rule class (ex. RuleFunctions, expiry action)
////rinfo.getFullPath() usually returns "".  In this case the key for an oversized XML argument
////will be oversizeXMLArg + number where number is unique across all keys generated in this manner/
////oversizeXMLArgCountStatic is used for this purpose, and if these types of resources may be
////regenerated by the BUI, then this system will need to change, since this scheme only works
////if the entire project is generated using the same instance of oversizeXMLArgCountStatic.
//protected static int oversizeXMLArgCountStatic = 0;
//protected void appendOversizeXMLArg(String xml) {
//        String argKey;
//if(rinfo.getFullName() != null && rinfo.getFullName().length() > 0) {
//argKey = rinfo.getFullName() + "_OversizeXMLArg_" + oversizeXMLArgCountInstance;
//oversizeXMLArgCountInstance++;
//} else {
//argKey = "OversizeXMLArg_" + oversizeXMLArgCountStatic;
//oversizeXMLArgCountStatic++;
//}
//oversizeStringConstants.setProperty(argKey, xml);
//
//currBody.append(CGConstants.oversizeStringConstantGetter);
//currBody.append("(\"");
//currBody.append(argKey);
//currBody.append("\")");
//}
