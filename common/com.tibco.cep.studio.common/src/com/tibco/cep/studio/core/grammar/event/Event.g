grammar Event;

options {
language=Java;
output=AST;
superClass=BaseRulesParser;
ASTLabelType=EventASTNode;
//backtrack=true;
}

import BaseElement, BaseLexer;

tokens { 
	EVENT_DEFINITION; PARENT; PROPERTY_DECLARATION; PROPERTIES_BLOCK; PROPERTY_BLOCK; ATTRIBUTE;
	ATTRIBUTES_BLOCK; DOMAIN_STATEMENT; DOMAIN_NAMES; RETRY_ON_EXCEPTION_STATEMENT; 
	DEFAULT_DESTINATION_STATEMENT; TTL_STATEMENT; UNITS; PAYLOAD_STRING_STATEMENT; LITERALS;
	NAMESPACE_STATEMENT; NAMESPACE; LOCATION; TTL; NAMESPACES_BLOCK; PAYLOAD_BLOCK; EXPIRY_ACTION_BLOCK;
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
		return ((EventLexer)getTokenStream().getTokenSource()).getHeaderNode();
	}

}

@header { 
package com.tibco.cep.studio.core.grammar.event; 

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
 * THE Business Event Event Grammar Begins here *
 ***********************************************/
startRule
	:	compilationUnit
	;
	
compilationUnit
	:	
		HeaderSection?
		eventDefinition
		EOF
	;

eventDefinition
	: 'event' nm=name[TYPE_RULE_DEF] ('extends' parent=name[TYPE_RULE_DEF])? LBRACE eventNT RBRACE
		{ setRuleElementType(ELEMENT_TYPES.SIMPLE_EVENT); }
		-> ^(EVENT_DEFINITION["event"] ^(NAME["name"] $nm)
			^(PARENT["parent"] $parent)? ^(BLOCKS eventNT))
	;

eventNT
	: (propertiesNT
	| attributesNT
	| expiryActionNT
	| payloadNT)*
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
	: defaultDestinationStatement
	| ttlStatement
	| retryOnExceptionStatement
	;
	
retryOnExceptionStatement
	: 'retryOnException' ASSIGN booleanLiteral SEMICOLON
		-> ^(RETRY_ON_EXCEPTION_STATEMENT ^(LITERAL booleanLiteral))
	;

defaultDestinationStatement
	: 'defaultDestination' ASSIGN name[DOMAIN_REF] SEMICOLON
		-> ^(DEFAULT_DESTINATION_STATEMENT ^(NAME name))
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
	: domainStatement
	;
	
domainStatement
	: 'domain' ASSIGN names+=name[DOMAIN_REF] (',' names+=name[DOMAIN_REF])* SEMICOLON
		-> ^(DOMAIN_STATEMENT ^(DOMAIN_NAMES $names+))
	;
	
ttlStatement
	: 'timeToLive' ASSIGN integerLiteral ttlUnits SEMICOLON
		-> ^(TTL_STATEMENT ^(TTL integerLiteral) ^(UNITS ttlUnits))
	;
	
ttlUnits
	: 'Milliseconds'
	| 'Seconds'
	| 'Minutes'
	| 'Hours'
	| 'Days'
	;
	
expiryActionNT
	: 'expiryAction' expiryActionNTBlock
	-> ^(EXPIRY_ACTION_BLOCK expiryActionNTBlock)
	;
	
expiryActionNTBlock
	: LBRACE { pushScope(ATTRIBUTE_SCOPE); }
				expiryActionStatement* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS expiryActionStatement*))
	;
				
expiryActionStatement
	: declareNT | thenNT
	;
	
payloadNT
	: 'payload' payloadNTBlock
	-> ^(PAYLOAD_BLOCK payloadNTBlock)
	;
	
payloadNTBlock
	: LBRACE { pushScope(ATTRIBUTE_SCOPE); }
				payloadStatement* RBRACE { popScope(); } 
	-> ^(BLOCK ^(STATEMENTS payloadStatement*))
	;
		
payloadStatement
	: namespaceNT | payloadStringStatement
	;
	
payloadStringStatement
	: 'payloadString' ASSIGN StringLiteral SEMICOLON
		-> ^(PAYLOAD_STRING_STATEMENT ^(LITERALS StringLiteral))
	;
	
namespaceNT
	: 'namespaces' namespaceNTBlock
	-> ^(NAMESPACES_BLOCK namespaceNTBlock)
	;

namespaceNTBlock
	: LBRACE { pushScope(ATTRIBUTE_SCOPE); }
				namespaceStatement* RBRACE { popScope(); } 
		-> ^(BLOCK ^(STATEMENTS namespaceStatement*))
	;
				
namespaceStatement
	: identifier ASSIGN ns=StringLiteral ('location' ASSIGN loc=StringLiteral)? SEMICOLON
		-> ^(NAMESPACE_STATEMENT ^(PREFIX identifier) ^(NAMESPACE $ns) ^(LOCATION $loc)?)
	;
	