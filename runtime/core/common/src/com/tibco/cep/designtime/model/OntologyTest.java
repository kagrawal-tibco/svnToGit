package com.tibco.cep.designtime.model;


import java.util.Iterator;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.ModelFunctionsFactory;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.mutable.MutableEvent;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;


/**
 * @author ishaan
 * @version Apr 27, 2006, 4:28:42 PM
 */
public class OntologyTest {


    public static MutableOntology initOntology() throws Exception {
        MutableOntology o = new DefaultMutableOntology();
        initalizeOntology(o);
        return o;
    }


    public static void initalizeOntology(MutableOntology ontology) throws Exception {
        // initialize fn factories
        FunctionsCatalog.getINSTANCE();
        ModelFunctionsFactory mFnFactory = ModelFunctionsFactory.getINSTANCE();
        ontology.addEntityChangeListener(mFnFactory);
        ModelFunctionsFactory.loadOntology(ontology);
    }


    public static void testAssertEvent(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        MutableEvent e1 = ontology.createEvent("/", "e1", "0", Event.MILLISECONDS_UNITS, false);
        e1.addUserProperty("p1", (RDFPrimitiveTerm) RDFTypes.INTEGER);
        e1.addUserProperty("p2", (RDFPrimitiveTerm) RDFTypes.INTEGER);

        MutableRule assertEvent = rs.createRule("assertEvent", false, false);
        assertEvent.addDeclaration("e1", e1.getFullPath());
        assertEvent.setConditionText("");

        StringBuffer text = new StringBuffer();
        text.append("assertEvent(e1);");
        assertEvent.setActionText(text.toString());
        printRule(assertEvent);
    }


    public static void testNewEntityDependencyNoCondition(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        MutableEvent e1 = ontology.createEvent("/", "e1", "0", Event.MILLISECONDS_UNITS, false);
        e1.addUserProperty("p1", (RDFPrimitiveTerm) RDFTypes.INTEGER);
        e1.addUserProperty("p2", (RDFPrimitiveTerm) RDFTypes.INTEGER);

        MutableRule createConcept = rs.createRule("createConcept", false, false);
        createConcept.addDeclaration(e1.getName(), e1.getFullPath());
        createConcept.setConditionText("");

        MutableConcept c1 = ontology.createConcept("/", "c1", "", false);
        c1.createPropertyDefinition("p1", PropertyDefinition.PROPERTY_TYPE_INTEGER, "", PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, "3");
        StringBuffer body = new StringBuffer();
        body.append(c1.getName());
        body.append('.');
        body.append(c1.getName());
        body.append('(');
        body.append((String) null);
        body.append(',');
        body.append((String) null);
        body.append(')');
        body.append(';');
        createConcept.setActionText(body.toString());

        MutableRule newConcept = rs.createRule("newConcept", false, false);
        newConcept.addDeclaration(c1.getName(), c1.getFullPath());
        newConcept.setActionText("true;");
    }


    public static void testBasic(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        MutableConcept c1 = ontology.createConcept("/", "c1", "", false);
        c1.createPropertyDefinition("p1", PropertyDefinition.PROPERTY_TYPE_INTEGER, "", PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, "3");
        c1.createPropertyDefinition("p2", PropertyDefinition.PROPERTY_TYPE_INTEGER, "", PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, "5");

        MutableConcept c2 = ontology.createConcept("/", "c2", "", false);
        c2.createPropertyDefinition("p1", PropertyDefinition.PROPERTY_TYPE_INTEGER, "", PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, "1515");
        c2.createPropertyDefinition("p2", PropertyDefinition.PROPERTY_TYPE_INTEGER, "", PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, "234");

        MutableEvent e1 = ontology.createEvent("/", "e1", "0", Event.MILLISECONDS_UNITS, false);
        e1.addUserProperty("p1", (RDFPrimitiveTerm) RDFTypes.INTEGER);
        e1.addUserProperty("p2", (RDFPrimitiveTerm) RDFTypes.INTEGER);

        MutableEvent e2 = ontology.createEvent("/", "e2", "0", Event.MILLISECONDS_UNITS, false);
        e2.addUserProperty("p1", (RDFPrimitiveTerm) RDFTypes.INTEGER);
        e2.addUserProperty("p2", (RDFPrimitiveTerm) RDFTypes.INTEGER);

        MutableRule basicRule = rs.createRule("basicRule", false, false);
        basicRule.addDeclaration("c1", c1.getFullPath());
        basicRule.addDeclaration("e1", e1.getFullPath());
        basicRule.setConditionText("(c1.p1 > 2);\n//(c1.p1 > 2);");

        StringBuffer body = new StringBuffer("c1.p1 = 3;");
        body.append('\n');
        body.append("c2 newconcept = rf();\n");
        body.append("newconcept.p2 = -1;");
        basicRule.setActionText(body.toString());
        printRule(basicRule);

        MutableRule eventRule = rs.createRule("eventRule", false, false);
        eventRule.addDeclaration("c2", c2.getFullPath());
        eventRule.addDeclaration("e2", e2.getFullPath());
//        eventRule.setConditionText("(c2.p2 == e2.p1);\n(c2 != null);");
        printRule(eventRule);

        MutableRuleFunction rf = ontology.createRuleFunction("/", "rf", false);
        rf.setBody("return null;");
        rf.setReturnType(c2.getFullPath());
        rf.setValidity(RuleFunction.Validity.ACTION);
    }


    public static void testNoCondition(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        MutableConcept c1 = ontology.createConcept("/", "c1", "", false);
        PropertyDefinition p = c1.createPropertyDefinition("p", PropertyDefinition.PROPERTY_TYPE_STRING, null, PropertyDefinition.HISTORY_POLICY_ALL_VALUES, 0, false, "");
        Concept c2 = ontology.createConcept("/", "c2", "", false);
        Event e1 = ontology.createEvent("/", "e1", "0", Event.MILLISECONDS_UNITS, false);
        Event e2 = ontology.createEvent("/", "e2", "0", Event.MILLISECONDS_UNITS, false);

        MutableRule basicRule = rs.createRule("basicRule", false, false);
        basicRule.addDeclaration("c1", c1.getFullPath());
        basicRule.addDeclaration("c2", c2.getFullPath());
        basicRule.addDeclaration("e1", e1.getFullPath());
        basicRule.addDeclaration("e2", e2.getFullPath());
        basicRule.setConditionText("c1 != null;\ne1 != null;");
        basicRule.setActionText(c1.getName() + "." + p.getName() + " = null;");
        printRule(basicRule);

        MutableRule eventRule = rs.createRule("eventRule", false, false);
        eventRule.addDeclaration("c1", c1.getFullPath());
//        eventRule.setConditionText("(c2.p2 == e2.p1);\n(c2 != null);");
        printRule(eventRule);
    }


    public static void testScorecard(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        MutableConcept scorecard = ontology.createConcept("/globals/scorecards", "card", "", false, true);
        scorecard.createPropertyDefinition("prop", PropertyDefinition.PROPERTY_TYPE_INTEGER, "", PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, "234");
        Concept container = ontology.getConcept("/container");
        MutableRule scorecardRule = rs.createRule("r3", false, false);
        scorecardRule.addDeclaration("container", container.getFullPath());
        scorecardRule.setConditionText("container != null;");
        scorecardRule.setActionText("globals.scorecards.card.prop = 234;");
    }


    public static void testContained(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        MutableConcept container = ontology.createConcept("/", "container", "", false);
        MutableConcept contained1 = ontology.createConcept("/", "contained1", "", false);
        MutableConcept contained2 = ontology.createConcept("/", "contained2", "", false);
        MutableConcept contained3 = ontology.createConcept("/", "contained3", "", false);
        container.createPropertyDefinition(contained1.getName(), PropertyDefinition.PROPERTY_TYPE_CONCEPT, contained1.getFullPath(), PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, null);
        contained1.createPropertyDefinition(contained2.getName(), PropertyDefinition.PROPERTY_TYPE_CONCEPT, contained2.getFullPath(), PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, null);
        contained2.createPropertyDefinition(contained3.getName(), PropertyDefinition.PROPERTY_TYPE_CONCEPT, contained3.getFullPath(), PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, null);
        contained3.createPropertyDefinition("prop", PropertyDefinition.PROPERTY_TYPE_INTEGER, "", PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, "234");
        MutableRule containmentRule = rs.createRule("containmentRule", false, false);
        containmentRule.addDeclaration("container", container.getFullPath());
        containmentRule.setConditionText("container != null;");
        containmentRule.setActionText("container.contained1.contained2.contained3.prop = 34;");
    }


    public static void testFunctionArgs(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        Concept arg = ontology.getConcept("/c1");
        Concept container = ontology.getConcept("/container");
        MutableRuleFunction passConceptFn = ontology.createRuleFunction("/", "passConceptFn", false);
        passConceptFn.setArgumentType("arg", arg.getFullPath());
        passConceptFn.setReturnType(null);
        passConceptFn.setValidity(RuleFunction.Validity.ACTION);

        MutableRule passConceptRule = rs.createRule("passConceptFn", false, false);
        passConceptRule.addDeclaration(arg.getName(), arg.getFullPath());
        passConceptRule.setActionText("passConceptFn(" + arg.getName() + ");");

        PropertyDefinition argProp = container.getPropertyDefinition("contained1", false);
        MutableRuleFunction passConceptPropFn = ontology.createRuleFunction("/", "passConceptPropFn", false);
        passConceptPropFn.setArgumentType(argProp.getName(), argProp.getConceptTypePath());

        MutableRule passConceptPropRule = rs.createRule("passConceptRule", false, false);
        passConceptPropRule.addDeclaration(container.getName(), container.getFullPath());
        passConceptPropRule.setActionText("passConceptPropFn(" + container.getName() + ".contained1);");
    }


    public static void testSimpleLoop(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        Event event = ontology.createEvent("/", "event", "0", Event.SECONDS_UNITS, false);
        Concept concept = ontology.createConcept("/", "concept", null, false);

        MutableRule starterRule = rs.createRule("starterRule", false, false);
        starterRule.addDeclaration(event.getName(), event.getFullPath());
        StringBuffer body = new StringBuffer();
        body.append(concept.getName());
        body.append('.');
        body.append(concept.getName());
        body.append("(null);");
        starterRule.setActionText(body.toString());
        System.out.println(body);

        MutableRule conceptModifyRule = rs.createRule("conceptModifyRule", false, false);
        conceptModifyRule.addDeclaration(concept.getName(), concept.getFullPath());
        body = new StringBuffer();
        body.append(concept.getName());
        body.append(" != null;");
        conceptModifyRule.setConditionText(body.toString());
        System.out.println(body);

        body = new StringBuffer();
        body.append(event.getName());
        body.append('.');
        body.append(event.getName());
        body.append('(');
        body.append((String) null);
        body.append(',');
        body.append((String) null);
        body.append(')');
        body.append(';');
        conceptModifyRule.setActionText(body.toString());
        System.out.println(body);
    }


    public static void testPropertyLoop(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        Event event = ontology.createEvent("/", "event", "0", Event.SECONDS_UNITS, false);

        MutableConcept concept = ontology.createConcept("/", "concept", null, false);
        PropertyDefinition p1 = concept.createPropertyDefinition("p1", PropertyDefinition.PROPERTY_TYPE_STRING, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, false, null);

        MutableRule starterRule = rs.createRule("starterRule", false, false);
        starterRule.addDeclaration(event.getName(), event.getFullPath());
        StringBuffer body = new StringBuffer();
        body.append(concept.getName());
        body.append('.');
        body.append(concept.getName());
        body.append('(');
        body.append((String) null);
        body.append(',');
        body.append((String) null);
        body.append(')');
        body.append(';');
        starterRule.setActionText(body.toString());
        System.out.println(body);

        MutableRule conceptModifyRule = rs.createRule("conceptModifyRule", false, false);
        conceptModifyRule.addDeclaration(concept.getName(), concept.getFullPath());
        body = new StringBuffer();
        body.append(concept.getName());
        body.append('.');
        body.append(p1.getName());
        body.append(" != null;");
        conceptModifyRule.setConditionText(body.toString());
        System.out.println(body);

        body = new StringBuffer();
        body.append(concept.getName());
        body.append('.');
        body.append(p1.getName());
        body.append(" = ");
        body.append((String) null);
        body.append(';');
        conceptModifyRule.setActionText(body.toString());
        System.out.println(body);
    }


    public static void testConceptInheritance(MutableOntology ontology, MutableRuleSet rs) throws ModelException {
        MutableConcept base = ontology.createConcept("/", "base", null, false);
        PropertyDefinition pd = base.createPropertyDefinition("p1", PropertyDefinition.PROPERTY_TYPE_STRING, null, PropertyDefinition.HISTORY_POLICY_ALL_VALUES, 0, false, "");
        Concept sub1 = ontology.createConcept("/", "sub1", base.getFullPath(), false);

        Event e = ontology.createEvent("/", "starter", "0", Event.MINUTES_UNITS, false);

        Concept c = base;
        StringBuffer body = new StringBuffer();

        MutableRule r1 = rs.createRule("r1", false, false);
        r1.addDeclaration(e.getName(), e.getFullPath());
        r1.addDeclaration(c.getName(), c.getFullPath());
        body.append(c.getName());
        body.append('.');
        body.append(pd.getName());
        body.append(" = ");
        body.append((String) null);
        body.append(';');
        r1.setActionText(body.toString());
        printRule(r1);

        c = sub1;
        MutableRule r2 = rs.createRule("r2", false, false);
        r2.addDeclaration(c.getName(), c.getFullPath());
        body.setLength(0);
        body.append(c.getName());
        body.append('.');
        body.append(pd.getName());
        body.append(" == ");
        body.append((String) null);
        body.append(';');
        r2.setConditionText(body.toString());
        body.setLength(0);
        body.append(c.getName());
        body.append('.');
        body.append(pd.getName());
        body.append(" = ");
        body.append((String) null);
        body.append(';');
        r2.setActionText(body.toString());
        printRule(r2);
    }


    private static void printRule(Rule r) {
        System.out.println(r.getName());
        final Symbols decls = r.getDeclarations();
        for (Iterator it = decls.values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
            System.out.println(symbol.getType() + " " + symbol.getName());
        }
        System.out.println(r.getConditionText());
        String actionText = "";
        try {
            actionText = r.getActionText();
        } catch (Exception e) {
        }
        System.out.println(actionText);
        System.out.println();
    }


}
