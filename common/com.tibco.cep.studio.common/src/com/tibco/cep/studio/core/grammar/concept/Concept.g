grammar Concept;

options {
language=Java;
output=AST;
superClass=BaseRulesParser;
ASTLabelType=ConceptASTNode;
//backtrack=true;
}

import BaseElement, BaseLexer;

tokens { 
	CONCEPT_DEFINITION; PARENT; PROPERTY_DECLARATION; PROPERTIES_BLOCK; PROPERTY_BLOCK; ATTRIBUTE;
	ATTRIBUTES_BLOCK; STATE_MACHINES_BLOCK; METADATA_BLOCK; AUTO_START_STATEMENT; CONTAINED_STATEMENT;
	POLICY_STATEMENT; HISTORY_STATEMENT; DOMAIN_STATEMENT; DOMAIN_NAMES;
	STATE_MACHINE_STATEMENT; META_DATA_PROPERTY_GROUP; META_DATA_PROPERTY;
}

@rulecatch {
catch (RecognitionException re) {
    reportError(re);
    recover(input,re);
}
catch (RewriteEmptyStreamException e) {
	// do nothing for now
	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
	// e.printStackTrace();
}
}

@members {
protected boolean virtual = false;
protected RulesASTNode headerTree = null;

public void setRuleElementType(ELEMENT_TYPES type) {
	gBaseElement.setRuleElementType(type);
}

public void setTypeReference(boolean typeRef) {
	gBaseElement.setTypeReference(typeRef);
}
	
public void markAsArray(RulesASTNode node) {
	gBaseElement.markAsArray(node);
}

public void popScope() {
	gBaseElement.popScope();
}

public void pushScope(int type) {
	gBaseElement.pushScope(type);
}

public boolean isInActionContextBlock() {
	return gBaseElement.isInActionContextBlock();
}

public void setInActionContextBlock(boolean b) {
	gBaseElement.setInActionContextBlock(b);
}

public void pushGlobalScopeName(RulesASTNode node, RulesASTNode type) {
	gBaseElement.pushGlobalScopeName(node, type);
}
	
public void pushDefineName(RulesASTNode node, RulesASTNode type) {
	gBaseElement.pushDefineName(node, type);
}

public void addProblemCollector(IProblemHandler collector) {
	gBaseElement.addProblemCollector(collector);
}

public RuleElement getRuleElement() {
	return gBaseElement.getRuleElement();
}

public void setRuleElement(RuleElement element) {
	gBaseElement.setRuleElement(element);
}

public CommonToken getCurrentToken() {
	return gBaseElement.getCurrentToken();
}

public ScopeBlock getLastPoppedScope() {
	return gBaseElement.getLastPoppedScope();
}

public ScopeBlock getScope() {
	return gBaseElement.getScope();
}

public ScopeBlock getLocalScope() {
	return gBaseElement.getLocalScope();
}

	// THIS METHOD IS UNIQUE TO EACH GRAMMAR, AS THE LEXER CLASS IS UNIQUE
	public RulesASTNode getHeaderNode()
	{
		return ((ConceptLexer)getTokenStream().getTokenSource()).getHeaderNode();
	}

}

@header { 
package com.tibco.cep.studio.core.grammar.concept; 

import com.tibco.cep.studio.core.rules.*; 
import com.tibco.cep.studio.core.rules.ast.*; 
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
}

@lexer::members {

public void setProblemHandler(IProblemHandler handler) {
	gBaseLexer.setProblemHandler(handler);
}

public RulesASTNode getHeaderNode() {
	return gBaseLexer.getHeaderNode();
}
}
/***********************************************
 * THE Business Event Concept Grammar Begins here *
 ***********************************************/
startRule
	:	compilationUnit
	;
	
compilationUnit
	:	
		HeaderSection?
		conceptDefinition
		EOF
	;

conceptDefinition
	: 'concept' nm=name[TYPE_RULE_DEF] ('extends' parent=name[TYPE_RULE_DEF])? LBRACE conceptNT RBRACE
		{ setRuleElementType(ELEMENT_TYPES.CONCEPT); }
		-> ^(CONCEPT_DEFINITION["concept"] ^(NAME["name"] $nm)
			^(PARENT["parent"] $parent)? ^(BLOCKS conceptNT))
	;

conceptNT
	: (propertiesNT
	| attributesNT
	| statemachinesNT
	| metadataNT)*
	;

attributesNT
	: 'attributes' attributesNTBlock
		-> ^(ATTRIBUTES_BLOCK["attributes"] attributesNTBlock)
	;
	
attributesNTBlock : LBRACE { pushScope(ATTRIBUTE_SCOPE); }
					attributeStatement* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS attributeStatement*))
	;

attributeStatement
	: 'autoStartStateModel' ASSIGN booleanLiteral SEMICOLON
		-> ^(AUTO_START_STATEMENT ^(LITERAL booleanLiteral))
	;
	
propertiesNT
	: 'properties' propertiesNTBlock
	-> ^(PROPERTIES_BLOCK["properties"] propertiesNTBlock)
	;
	
propertiesNTBlock : LBRACE { pushScope(ATTRIBUTE_SCOPE); }
					propertiesBodyDeclaration* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS propertiesBodyDeclaration*))
	;

propertiesBodyDeclaration
	: { setTypeReference(true); } t=type { setTypeReference(false); } id=identifier
		{ pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); }
		(SEMICOLON | propertyBlock)
		-> ^(PROPERTY_DECLARATION ^(TYPE["type"] $t) ^(NAME["name"] $id) 
			^(PROPERTY_BLOCK["prop block"] propertyBlock)?)
	;

propertyBlock
	: 	LBRACE { pushScope(ATTRIBUTE_SCOPE); }
			propertyAttribute* RBRACE { popScope(); }
		-> ^(BLOCK ^(STATEMENTS propertyAttribute*))
	;
	
propertyAttribute
	: policyStatement
	| historyStatement
	| domainStatement
	| containedStatement
	;
	
containedStatement
	: 'contained' ASSIGN booleanLiteral SEMICOLON
		-> ^(CONTAINED_STATEMENT ^(LITERAL booleanLiteral))
	;
	
policyStatement
	: 'policy' ASSIGN (p='CHANGES_ONLY' | p='ALL_VALUES') SEMICOLON
		-> ^(POLICY_STATEMENT ^(LITERAL $p))
	;
	
historyStatement
	: 'history' ASSIGN integerLiteral SEMICOLON
		-> ^(HISTORY_STATEMENT ^(LITERAL integerLiteral))
	;
	
domainStatement
	: 'domain' ASSIGN names+=name[DOMAIN_REF] (',' names+=name[DOMAIN_REF])* SEMICOLON
		-> ^(DOMAIN_STATEMENT ^(DOMAIN_NAMES $names+))
	;
	
statemachinesNT
	: 'stateMachines' stateMachinesNTBlock
	-> ^(STATE_MACHINES_BLOCK["state machines"] stateMachinesNTBlock)
	;
	
stateMachinesNTBlock 
	: LBRACE { pushScope(ATTRIBUTE_SCOPE); }
			stateMachineStatement* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS stateMachineStatement*))
	;
	
stateMachineStatement
	: name[DOMAIN_REF] SEMICOLON
		-> ^(STATE_MACHINE_STATEMENT ^(NAME name))
	;
	
metadataNT
	: 'metadata' metadataNTBlock
	-> ^(METADATA_BLOCK["metadata"] metadataNTBlock)
	;
	
metadataNTBlock
	: LBRACE { pushScope(ATTRIBUTE_SCOPE); }
			metadataStatement* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS metadataStatement*))
	;
	
metadataStatement
	: metaDataGroup | metaDataProperty
	;
	
metaDataGroup
	: nm=StringLiteral LBRACE metadataStatement* RBRACE
		-> ^(META_DATA_PROPERTY_GROUP ^(NAME $nm) ^(STATEMENTS metadataStatement*))
	;
	
metaDataProperty
	: nm=StringLiteral ASSIGN lit=StringLiteral SEMICOLON
		-> ^(META_DATA_PROPERTY ^(NAME $nm) ^(LITERAL $lit))
	;
	
	