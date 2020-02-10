package com.tibco.be.parser.impl;

import java.io.StringReader;

import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.RuleGrammar;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.codegen.RuleBlockLineBuffer;
import com.tibco.be.parser.semantic.BuiltinLookup;
import com.tibco.be.parser.semantic.CompilableDeclSymbolTable;
import com.tibco.be.parser.semantic.CompositeModelLookup;
import com.tibco.be.parser.semantic.FunctionLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.ModelLookup;
import com.tibco.be.parser.semantic.NodeTypeVisitor;
import com.tibco.be.parser.semantic.OntologyModelLookup;
import com.tibco.be.parser.semantic.RLSymbolTable;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.core.adapters.mutable.MutableSymbolsAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 30, 2006
 * Time: 4:33:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleObjectParser extends RuleParser {
    public static RuleInfo parseRule(Rule rule, Ontology o) throws ParseException {
        RuleObjectParser rop = new RuleObjectParser();
        RuleInfo rinfo = rop.rInfo = new RuleInfo();
        rinfo.setPath(rule.getFullPath());
        rinfo.setPriority(rule.getPriority());
        rinfo.setForwardChain(rule.usesForwardChaining());
        rinfo.setRank(rule.getRankPath());
        Symbols ruleSymbols = rule.getDeclarations();
        Symbols mutSymbols;
        if(ruleSymbols instanceof Symbols) {
            mutSymbols = ruleSymbols;
        } else {
            mutSymbols = new MutableSymbolsAdapter(ruleSymbols,o);
        }
        rinfo.setDeclarations(mutSymbols);

        RuleGrammar grammar = new RuleGrammar(new StringReader(rule.getConditionText()));
        grammar.setParserClient(rop);
        grammar.ConditionBlock();
        grammar.re_init(new StringReader(rule.getActionText()));
        grammar.ActionBlock();
        
        RLSymbolTable symbolTable = new CompilableDeclSymbolTable(rule, o);
        ModelLookup modelLookup = new CompositeModelLookup(new ModelLookup[] {new BuiltinLookup(), new OntologyModelLookup(o)});
        FunctionLookup functionLookup = new FunctionsCatalogLookup(o.getName());

        //println("\nPredicates");
        NodeTypeVisitor visitor = new NodeTypeVisitor(symbolTable, modelLookup, functionLookup);
        visitor.populateNodeTypes(rinfo.getWhenTrees());
        rinfo.addAllErrors(visitor.getErrors());

        visitor.populateNodeTypes(rinfo.getThenTrees());
        rinfo.addAllErrors(visitor.getErrors());
        rinfo.setRuleBlockBuffer(RuleBlockLineBuffer.fromRule(rule));
        return rinfo;
    }
}
