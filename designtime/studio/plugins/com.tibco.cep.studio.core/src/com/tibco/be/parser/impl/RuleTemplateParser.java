package com.tibco.be.parser.impl;

import java.io.Reader;
import java.io.StringReader;

import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.RuleGrammar;
import com.tibco.be.parser.semantic.BuiltinLookup;
import com.tibco.be.parser.semantic.CompositeModelLookup;
import com.tibco.be.parser.semantic.FunctionLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.ModelLookup;
import com.tibco.be.parser.semantic.NodeTypeVisitor;
import com.tibco.be.parser.semantic.OntologyModelLookup;
import com.tibco.be.parser.semantic.RuleTemplateSymbolTable;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleTemplate;


/**
 * User: nprade
 * Date: 1/30/12
 * Time: 3:52 PM
 */
public class RuleTemplateParser
        extends RuleParser {


    public void addBindingTree(
            RootNode tree) {
        ((RuleTemplateInfo) this.rInfo).addBindingTree(tree);
    }


    public void addError(
            ParseException error,
            String message) {

        if (null == this.rInfo) {
            this.newRule();
        }
        this.rInfo.addError(RuleError.makeSyntaxError(error, message));
    }


    public void addViewTree(
            RootNode tree) {
        ((RuleTemplateInfo) this.rInfo).addViewTree(tree);
    }


    public void newRule() {
        this.rInfo = new RuleTemplateInfo();
        //noinspection unchecked
        this.rInfoList.add(rInfo);
    }


    public static RuleTemplateInfo parseRuleTemplate(
            RuleTemplate rt,
            Ontology ontology)
            throws ParseException {

        return parseRuleTemplate(new StringReader(rt.getSource()), ontology);
    }


    public static RuleTemplateInfo parseRuleTemplate(
            Reader ruleTemplateReader,
            Ontology ontology)
            throws ParseException {

        final RuleTemplateParser ruleTemplateParser = new RuleTemplateParser();

        final RuleGrammar grammar = new RuleGrammar(ruleTemplateReader);
        grammar.setParserClient(ruleTemplateParser);
        grammar.RTDeclaration();

        final FunctionLookup functionLookup = new FunctionsCatalogLookup(ontology.getName());
        final ModelLookup modelLookup = new CompositeModelLookup(
                new ModelLookup[]{new BuiltinLookup(), new OntologyModelLookup(ontology)});

            final RuleTemplateInfo ruleInfo = (RuleTemplateInfo) ruleTemplateParser.rInfoList.get(0);

            final RuleTemplateSymbolTable symbolTable = new RuleTemplateSymbolTable(ruleInfo, ontology);
            final NodeTypeVisitor visitor = new NodeTypeVisitor(symbolTable, modelLookup, functionLookup);

            visitor.populateNodeTypes(ruleInfo.getViewTrees());
            ruleInfo.addAllErrors(visitor.getErrors());

            visitor.populateNodeTypes(ruleInfo.getBindingTrees());
            ruleInfo.addAllErrors(visitor.getErrors());

            visitor.populateNodeTypes(ruleInfo.getWhenTrees());
            ruleInfo.addAllErrors(visitor.getErrors());

            visitor.populateNodeTypes(ruleInfo.getThenTrees());
            ruleInfo.addAllErrors(visitor.getErrors());

        return ruleInfo;
    }

}
