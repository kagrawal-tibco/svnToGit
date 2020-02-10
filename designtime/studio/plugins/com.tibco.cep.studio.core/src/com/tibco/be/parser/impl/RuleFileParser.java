package com.tibco.be.parser.impl;

import java.io.Reader;
import java.util.ArrayList;

import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.RuleGrammar;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.semantic.BuiltinLookup;
import com.tibco.be.parser.semantic.CompositeModelLookup;
import com.tibco.be.parser.semantic.FunctionLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.ModelLookup;
import com.tibco.be.parser.semantic.NodeTypeVisitor;
import com.tibco.be.parser.semantic.OntologyModelLookup;
import com.tibco.be.parser.semantic.RLSymbolTable;
import com.tibco.be.parser.semantic.RuleInfoSymbolTable;
import com.tibco.cep.designtime.model.Ontology;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jun 15, 2004
 * Time: 12:37:42 PM
 */
public class RuleFileParser extends RuleParser {

    /**
     * Parses and validates the rules in the stream ruleFile
     * @param ruleFile generally backed by a file containing one or more Rule blocks
     * @param o ontology containing the model objects referenced by the rule
     * @return a list of RuleInfo objects, one for each rule block
     * @throws ParseException
     */
    public static ArrayList parseRuleFile(Reader ruleFile, Ontology o) throws ParseException {
        RuleFileParser rfp = new RuleFileParser();

        RuleGrammar grammer = new RuleGrammar(ruleFile);
        grammer.setParserClient(rfp);
        grammer.CompilationUnit();

        for(int ii = 0; ii < rfp.rInfoList.size(); ii++) {
            RuleInfo ri = (RuleInfo)rfp.rInfoList.get(ii);

            //println("\nRule: " + ri.getName());

            RLSymbolTable symbolTable = new RuleInfoSymbolTable(ri, o);
            ModelLookup modelLookup = new CompositeModelLookup(new ModelLookup[] {new BuiltinLookup(), new OntologyModelLookup(o)});
            FunctionLookup functionLookup = new FunctionsCatalogLookup(o.getName());

            //println("\nPredicates");
            NodeTypeVisitor visitor = new NodeTypeVisitor(symbolTable, modelLookup, functionLookup);
            visitor.populateNodeTypes(ri.getWhenTrees());
            ri.addAllErrors(visitor.getErrors());

            visitor.populateNodeTypes(ri.getThenTrees());
            ri.addAllErrors(visitor.getErrors());

            //println("\nErrors");
            //for(Iterator it = ri.getErrors().iterator(); it.hasNext();) {
                //println(((RuleError)it.next()).toString());
            //}
        }
        return rfp.rInfoList;
    }
}
/*
for(Iterator it = ri.getImports(); it.hasNext();) {
String type = ((NamedNode)it.next()).join(".");
boolean result = sl.addModelImport(type); 
println("    import " + type + "; " + result);
}

println("\nAttributes");
for(Iterator it = ri.getAttributes(); it.hasNext();) {
RuleInfo.AttrRecord ar = (RuleInfo.AttrRecord)it.next();
println("    " + ar.attr.image + " : " + ar.value + ";");
}

println("\nDeclarators");
for(Iterator it = ri.getModelDeclarations(); it.hasNext();) {
DeclRecord dr = (DeclRecord)it.next();
println("    " + dr.declType + " " + dr.declName + "; " + result);

}
*/
