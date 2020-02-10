package com.tibco.cep.studio.core.grammar.concept;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.stringtemplate.AutoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class ConceptParserTester {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No file specified.  Exiting");
			return;
		}
		try {
			System.out.println("Attempting parse of "+args[0]);
			Date start = new Date();
			/*
			 * You can also use ANTLRStringStream rather than ANTLRFileStream here:
			 * ANTLRStringStream stream = new ANTLRStringStream("Here is my input");
			 */
			ConceptLexer lexer = new ConceptLexer(new ANTLRFileStream(args[0], ModelUtils.DEFAULT_ENCODING));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			ConceptParser parser = new ConceptParser(tokens);
			parser.setTreeAdaptor(new ConceptTreeAdaptor());
			ParserRuleReturnScope startRule = parser.startRule();
			Date end = new Date();
			RulesASTNode headerNode = parser.getHeaderNode();
			System.out.println("Parse finished in "+(end.getTime() - start.getTime()));
			if (startRule != null) {
				CommonTree tree = (CommonTree) startRule.getTree();
				System.out.println("Tree: "+tree.toStringTree());
				walkAST(0, tree, tokens);
				if (parser.getHeaderNode() != null) {
					((RulesASTNode)tree).setData("HEADER", parser.getHeaderNode());
				}

				new ConceptParserTester().convertConceptASTToString((RulesASTNode)tree);

			} else {
				System.out.println("Return value is null");
			}
			RuleElement element = parser.getRuleElement();
			EList<GlobalVariableDef> globalVariables = element.getGlobalVariables();
			ScopeBlock scope = element.getScope();
			System.out.println("");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
	}

	private static void walkAST(int tabs, CommonTree tree, CommonTokenStream tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("type: "+tree.getType());
		builder.append(" -- text: '"+tree.getText()+"'");
		int tokenStartIndex = tree.getTokenStartIndex(); // the first token that spans this node
		int tokenStopIndex = tree.getTokenStopIndex(); // the last token that spans this node
		if (tokenStartIndex == -1) {
			// this is an 'implicit' node that has no corresponding tokens (i.e. NAME)
			// the offset/length information can be obtained from its children
			int childCount = tree.getChildCount();
			if (childCount > 0) {
				CommonTree firstChild = (CommonTree) tree.getChild(0);
				CommonTree lastChild = (CommonTree) tree.getChild(childCount - 1);
				
				tokenStartIndex = firstChild.getTokenStartIndex();
				tokenStopIndex = lastChild.getTokenStopIndex();
			}
		} 
		if (tokenStartIndex >= 0) {
			CommonToken startToken = (CommonToken) tokens.get(tokenStartIndex);
			CommonToken stopToken = (CommonToken) tokens.get(tokenStopIndex);
			int offset = startToken.getStartIndex();
			int length = stopToken.getStopIndex() - offset + 1;
			builder.append(" -- node offset: "+offset);
			builder.append(" -- node length: "+length);
			builder.append(" -- line number: "+startToken.getLine());
			builder.append(" -- column number: "+startToken.getCharPositionInLine());
		}
		
		System.out.println(getTabs(tabs)+builder.toString());
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			CommonTree child = (CommonTree) tree.getChild(i);
			walkAST(tabs+1, child, tokens);
		}
	}
	
	private static String getTabs(int tabCount) {
		String tabs = "";
		for (int i = 0; i < tabCount; i++) {
			tabs += "\t";
		}
		return tabs;
	}
	
	public String convertConceptASTToString(RulesASTNode conceptNode) throws IOException {
		StringTemplateGroup stgroup = new StringTemplateGroup(new InputStreamReader(getClass().getResourceAsStream("/com/tibco/cep/studio/core/grammar/concept/ConceptTemplate.stg")));

		StringTemplate conceptTemplate = stgroup.getInstanceOf("Concept_Definition");
		conceptTemplate.reset();
		
		ConceptInfo conceptInfo = getConceptInfo(conceptNode);
    	conceptTemplate.setAttribute("elementInfo", conceptInfo);
    	System.out.println(conceptTemplate.toString());
    	
    	ByteArrayOutputStream mapperTransformStream = new ByteArrayOutputStream();
    	OutputStreamWriter mapperStreamWriter = new OutputStreamWriter(mapperTransformStream,"UTF-8");
    	conceptTemplate.write(new AutoIndentWriter(mapperStreamWriter));
    	mapperStreamWriter.flush();
    	mapperStreamWriter.close();
    	
    	mapperTransformStream.flush();
    	mapperTransformStream.close();

		return conceptTemplate.toString();
	}

	private ConceptInfo getConceptInfo(RulesASTNode conceptNode) {
		
    	ConceptCreatorASTVisitor visitor = new ConceptCreatorASTVisitor("Test");
    	conceptNode.accept(visitor);
    	Concept createdConcept = visitor.getConcept();
    	if (createdConcept != null) {
    		ConceptInfo info = new ConceptInfo(createdConcept);
    		return info;
    	}
    	
		Concept concept = ElementFactory.eINSTANCE.createConcept();
		concept.setName("MyConceptName");
		concept.setFolder("/Concepts/Stuff/");
		concept.setSuperConceptPath("/Heres/My/Parent");
		concept.setGUID("q345224509234u5023945");
		concept.setDescription("Here is a very eloquent description");
		concept.setAutoStartStateMachine(true);
		
		concept.getStateMachinePaths().add("/Concepts/SM1");
		concept.getStateMachinePaths().add("/Concepts/SM2");
		concept.getStateMachinePaths().add("/Concepts/SM3");
		
		PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
		propDef.setType(PROPERTY_TYPES.STRING);
		propDef.setName("MyProp");
		propDef.setArray(true);
		propDef.setHistorySize(5);
		concept.getProperties().add(propDef);
		
		SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
		sp.setName("SimpleProp1");
		sp.setValue("PropertyValue");
		
		ObjectProperty op = ModelFactory.eINSTANCE.createObjectProperty();
		op.setName("MyGroup");
		PropertyMap map = ModelFactory.eINSTANCE.createPropertyMap();
		op.setValue(map);
		
		SimpleProperty sp2 = ModelFactory.eINSTANCE.createSimpleProperty();
		sp2.setName("SimpleProp2");
		sp2.setValue("My Property Value");
		map.getProperties().add(sp2);
		
		concept.setExtendedProperties(ModelFactory.eINSTANCE.createPropertyMap());
		concept.getExtendedProperties().getProperties().add(sp);
		concept.getExtendedProperties().getProperties().add(op);
		
		ConceptInfo info = new ConceptInfo(concept);
		return info;
	}
}
