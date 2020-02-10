package com.tibco.be.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.parser.semantic.BuiltinLookup;
import com.tibco.be.parser.semantic.CompilableDeclSymbolTable;
import com.tibco.be.parser.semantic.CompositeModelLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.ModelLookup;
import com.tibco.be.parser.semantic.NodeTypeVisitor;
import com.tibco.be.parser.semantic.OntologyModelLookup;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.SourceType;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Compilable;
import com.tibco.cep.designtime.model.rule.RuleFunction;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 26, 2004
 * Time: 3:45:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleCompiler {
    protected RuleGrammar parser = null;
    protected ArrayList errors = null;
    protected CompilableDeclSymbolTable symbolTable;
    protected OntologyModelLookup oml;
    protected ArrayList parseTrees = new ArrayList();
    protected NodeTypeVisitor nodeTypeVisitor;
    protected int tabSize = -1;
    
    public RuleCompiler(Compilable compilable, Ontology o) {
        this(compilable, (MapperFunctionValidator)null, o);   
    }
    
    public RuleCompiler(Compilable compilable, MapperFunctionValidator mapperValidator, Ontology o) {
    	symbolTable = new CompilableDeclSymbolTable(compilable, o);
        oml = (null == compilable) ? null : new OntologyModelLookup(o);
        nodeTypeVisitor = new NodeTypeVisitor(symbolTable, new CompositeModelLookup(new ModelLookup[]{new BuiltinLookup(), oml}), new FunctionsCatalogLookup(o.getName()), mapperValidator);
    }

//    public RuleCompiler(Compilable compilable, MapperFunctionValidator mapperValidator, NodeTypeVisitor ntv) {
//        symbolTable = new CompilableDeclSymbolTable(compilable);
//        oml = (null == compilable) ? null : new OntologyModelLookup(compilable.getOntology());
//        nodeTypeVisitor = ntv;
//    }
    
    //support for .NET rule editor
//    public RuleCompiler(Compilable compilable, boolean useDotNetRuleEditorNTV) {
//        this(compilable, null, null);
//        if(useDotNetRuleEditorNTV) {
////            nodeTypeVisitor = new DotNetRuleEditorNTV(symbolTable, new CompositeModelLookup(new ModelLookup[]{new BuiltinLookup(), oml}), new FunctionsCatalogLookup());
//        } else {
//            nodeTypeVisitor = new NodeTypeVisitor(symbolTable, new CompositeModelLookup(new ModelLookup[]{new BuiltinLookup(), oml}), new FunctionsCatalogLookup(compilable.getOntology().getName()), null);
//        }
//    }
    
    public void reset(Compilable compilable, Ontology o) {
        symbolTable.reset(compilable, o);
        oml.reset(o);
    }
    
    protected void reInit(Reader reader) {
        clear();
        if(parser == null) {
            parser = new RuleGrammar(reader);
            parser.setParserClient(parserClient);
            if(tabSize >= 0) parser.setTabSize(tabSize);
        } else {
            parser.re_init(reader);
        }
    }

    private void clear() {
        errors = new ArrayList();
        parseTrees.clear();
        nodeTypeVisitor.clearErrors();
    }
    
    public void setTabSize(int tabSize) {
        this.tabSize = tabSize;
        if(parser != null && tabSize >= 0) parser.setTabSize(tabSize);
    }
    
    public List checkConditionBlock(Reader reader) {
        reInit(reader);
        return checkConditionBlock();
    }
    
    private List checkConditionBlock() {
        try {
            parser.ConditionBlock();
            doSemanticCheck(parseTrees.iterator());
        } catch (ParseException pe) {
            parserClient.addError(pe);
        }
        parseTrees.clear();
        return errors;
    }
    
    public RootNode compileStandaloneExpression(Reader reader, List errorList, SourceType srcType) {
        reInit(reader);
        return compileStandaloneExpression(errorList, srcType);
    }
    private RootNode compileStandaloneExpression(List errorList, SourceType srcType) {
        RootNode node = null;
        try {
            parser.StandaloneExpression(srcType);
            if(parseTrees.size() > 0) {
                node = ((RootNode)parseTrees.get(0));
                parseTrees.clear();
                parseTrees.add(node);
                nodeTypeVisitor.populateNodeTypes(parseTrees.iterator());
                errors.addAll(nodeTypeVisitor.getErrors());
                nodeTypeVisitor.clearErrors();
            }
        } catch (ParseException pe) {
            parserClient.addError(pe);
        }
        parseTrees.clear();
        if(errorList != null) errorList.addAll(errors);
        errors.clear();
        return node;
    }

    public RootNode compileStandaloneThenStatement(Reader reader, List errorList, SourceType srcType) {
        reInit(reader);
        return compileStandaloneThenStatement(errorList, srcType);
    }
    private RootNode compileStandaloneThenStatement(List errorList, SourceType srcType) {
        RootNode node = null;
        try {
            parser.StandaloneThenStatement(srcType);
            if(parseTrees.size() > 0) {
                node = ((RootNode)parseTrees.get(0));
                parseTrees.clear();
                parseTrees.add(node);
                nodeTypeVisitor.populateNodeTypes(parseTrees.iterator());
                errors.addAll(nodeTypeVisitor.getErrors());
                nodeTypeVisitor.clearErrors();
            }
        } catch (ParseException pe) {
            parserClient.addError(pe);
        }
        parseTrees.clear();
        if(errorList != null) errorList.addAll(errors);
        errors.clear();
        return node;
    }

    public List checkActionBlock(Reader reader) {
        return checkActionBlock(reader, false);
    }
    public List checkActionBlock(Reader reader, boolean isDTActionCell) {
        reInit(reader);
        ArrayList errorList = new ArrayList();
        compileActionBlock(errorList, isDTActionCell);
        return errorList;
    }

    public List checkRuleFunctionBlock(Reader reader, RuleFunction.Validity validity) {
        return checkRuleFunctionBlock(reader, validity, false);
    }
    private List checkRuleFunctionBlock(Reader reader, RuleFunction.Validity validity, boolean isDTRF) {
        reInit(reader);
        ArrayList errorList = new ArrayList();
        compileRuleFunctionBlock(errorList, validity, isDTRF);
        return errorList;
    }

    public List<RootNode> compileActionBlock(Reader reader, List errorList) {
        return compileActionBlock(reader, errorList, false);
    }
    public List<RootNode> compileActionBlock(Reader reader, List errorList, boolean isDTActionCell) {
        reInit(reader);
        return compileActionBlock(errorList, isDTActionCell);
    }

    private List<RootNode> compileActionBlock(List errorList, boolean isDTActionCell) {
        try {
            parser.ActionBlock();
            if(isDTActionCell) {
                for(RootNode rn : (List<RootNode>)parseTrees) {
                    rn.setSourceType(SourceType.DT_ACTION_CELL);
                }
            }
            doSemanticCheck(parseTrees.iterator());
        } catch(ParseException pe) {
            parserClient.addError(pe);
        }
        if(errorList != null) errorList.addAll(errors);
        
        final List temp = parseTrees;
        parseTrees = new ArrayList();
        return temp;
    }

    public List<RootNode> compileRuleFunctionBlock(Reader reader, List errorList, RuleFunction.Validity validity, boolean isDTRF) {
        reInit(reader);
        return compileRuleFunctionBlock(errorList, validity, isDTRF);
    }

    private List compileRuleFunctionBlock(List errorList, RuleFunction.Validity validity, boolean isDTRF) {
        try {
            switch (validity) {
                case CONDITION:
                    parser.RuleFunctionBody(SourceType.RULE_FUNCTION_CONDITION_OK);
                    break;
                case QUERY:
                    parser.RuleFunctionBody(SourceType.RULE_FUNCTION_QUERY_OK);
                    break;
                default:
                    SourceType srcType = SourceType.RULE_FUNCTION_ACTION_ONLY;
                    if(isDTRF) srcType = SourceType.DT_RF_ACTION_ONLY;
                    parser.RuleFunctionBody(srcType);
                    break;
            }
            doSemanticCheck(parseTrees.iterator());
        } catch(ParseException pe) {
            parserClient.addError(pe);
        }
        if(errorList != null) errorList.addAll(errors);
        
        final List temp = parseTrees;
        parseTrees = new ArrayList();
        return temp;
    }
    
    protected void doSemanticCheck(Iterator rootNodes) {
        nodeTypeVisitor.populateNodeTypes(rootNodes);
        errors.addAll(nodeTypeVisitor.getErrors());
        nodeTypeVisitor.clearErrors();
    }
    
    protected ParserClient parserClient = new ParserClient() {
        public void addWhenTree(RootNode whenTree) {
            parseTrees.add(whenTree);
        }
    
        public void addThenTree(RootNode thenTree) {
            parseTrees.add(thenTree);
        }
    
        public void addError(ParseException error) {
            errors.add(RuleError.makeSyntaxError(error));
        }

        public void addError(ParseException error, String message) {
            errors.add(RuleError.makeSyntaxError(error, message));
        }
    };
    
    /**
     * Compile Rule. It returns an arrayList of errors.
     * @param c
     * @return
     */
    public static int compile(Compilable c, Ontology o) {
        return compile(c, null, null, o);
    }
    public static int compile(Compilable c, ArrayList errorList, Ontology o) {
        return compile(c, null, errorList, o);
    }
    public static int compile(Compilable c, MapperFunctionValidator mapperValidator, Ontology o) {
        return compile(c, mapperValidator, null, o);
    }
    public static int compile(Compilable c, MapperFunctionValidator mapperValidator, ArrayList errorList, Ontology o) {
        return compile(c, mapperValidator, errorList, false, o);
    }
    public static int compile(Compilable c, MapperFunctionValidator mapperValidator, List errorList, boolean isDTRF, Ontology o) {
        if (c == null) return 0;
        
        RuleCompiler compiler;
        if(mapperValidator != null) {
            compiler = new RuleCompiler(c, mapperValidator, o);
        } else {
            compiler = new RuleCompiler(c, o);
        }
        
        boolean isRuleFn = (c instanceof RuleFunction);
        RuleFunction.Validity validity = RuleFunction.Validity.ACTION;
        String rfBody = null;
        if(!isRuleFn) {
            //if(c instanceof ProxyRule) {
            //    MutableProxyRule pr = (ProxyRule) c;
            //    isRuleFn = pr.isFunction();
            //    if(isRuleFn) {
            //        isActionOnly = !pr.isConditionFunction();
            //        rfBody = pr.getActionText();
            //    }
            //}
        } else {
            validity = ((RuleFunction)c).getValidity();
            rfBody = ((RuleFunction)c).getBody();
        }
        
        boolean errorFlag = false;
        List errList;
        
        if(!isRuleFn) {
            errList = compiler.checkConditionBlock(new StringReader(c.getConditionText()));
            errorFlag = errorFlag || errList.size() > 0;
            if(errorList != null) errorList.addAll(errList);
            
            errList = compiler.checkActionBlock(new StringReader(c.getActionText()));
            errorFlag = errorFlag || errList.size() > 0;
            if(errorList != null) errorList.addAll(errList);
        } else {
            errList = compiler.checkRuleFunctionBlock(new StringReader(rfBody), validity, isDTRF);
            errorFlag = errorFlag || errList.size() > 0;
            if(errorList != null) errorList.addAll(errList);
        }
        
        if (errorFlag) {
            return 1;
        }
        return 0;
    }
    
    //Returns a collection of ruleErrors or null if there were no errors
    public static Collection compileJNI(Compilable c, Ontology o) {
        ArrayList errorList = new ArrayList();
        int result = compile(c, errorList, o);
        if(result == 0 || errorList.size() <= 0) return null;
        return errorList;
    }
    
    //Following are for RuleFunctionCodeGenerator
    public static List parseAndTypeCheckRuleFunction(RuleFunction rf, List errorList, boolean isDTRF, Ontology o) {
        return parseAndTypeCheckRuleFunction(rf, null, errorList, isDTRF, o);
    }
    /**
     * @param rf
     * @param mapperValidator
     * @param errorList as errors arise, RuleErrors will be added to this list
     * @return if errors occurred, null is returned, otherwise a list of RootNodes for the rule function body is returned 
     */
    public static List parseAndTypeCheckRuleFunction(RuleFunction rf, MapperFunctionValidator mapperValidator, List errorList, boolean isDTRF, Ontology o) {
        RuleCompiler compiler;
        if(mapperValidator != null) {
            compiler = new RuleCompiler(rf, mapperValidator, o);
        } else {
            compiler = new RuleCompiler(rf, o);
        }
        return compiler.compileRuleFunctionBlock(new StringReader(rf.getBody()), errorList, rf.getValidity(), isDTRF);
    }
}
