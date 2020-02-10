package com.tibco.cep.studio.core.rules.ast;

import java.util.List;

import org.antlr.runtime.CommonTokenStream;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.rule.ActionContext;
import com.tibco.cep.designtime.core.model.rule.ActionContextSymbol;
import com.tibco.cep.designtime.core.model.rule.ActionType;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;

public class RuleCreatorASTVisitor extends DefaultRulesASTNodeVisitor {

	private Compilable fRule;
	private RulesASTNode fRootNode;
	private boolean fIncludeBodyText;
	private boolean fIncludeFullRuleText;
	private String fProjectName;

	public RuleCreatorASTVisitor(String projectName) {
		this(false, projectName);
	}
	
	public RuleCreatorASTVisitor(boolean includeBodyText, String projectName) {
		this(includeBodyText, false, projectName);
	}

	public RuleCreatorASTVisitor(boolean includeBodyText,
			boolean includeFullRuleText, String projectName) {
		this.fIncludeBodyText = includeBodyText;
		this.fIncludeFullRuleText = includeFullRuleText;
		this.fProjectName = projectName;
	}

	public Compilable getRule() {
		return fRule;
	}

	@Override
	public boolean visitBodyBlockNode(RulesASTNode node) {
		if (!fIncludeBodyText) {
			return super.visitBodyBlockNode(node);
		}
		CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
		if (tokens != null) {
			RulesASTNode childNode = node.getFirstChildWithType(RulesParser.BLOCK);
			String nodeText = RuleGrammarUtils.getNodeText(tokens, childNode, false, false);
			// if node text is null then set it to blank
			if (nodeText == null){
				nodeText = "";
			}
			
			fRule.setActionText(nodeText);
		}
		return super.visitBodyBlockNode(node);
	}

	@Override
	public boolean visitThenBlockNode(RulesASTNode node) {
		if (!fIncludeBodyText) {
			return super.visitThenBlockNode(node);
		}
		CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
		if (tokens != null) {
			RulesASTNode blockNode = node.getFirstChildWithType(RulesParser.BLOCK);
			String nodeText = RuleGrammarUtils.getNodeText(tokens, blockNode, false, false);
			// if node text is null then set it to blank
			if (nodeText == null){
				nodeText = "";
			}
			fRule.setActionText(nodeText);
		}
		return super.visitThenBlockNode(node);
	}

	@Override
	public boolean visitWhenBlockNode(RulesASTNode blocksNode) {
		if (!fIncludeBodyText) {
			return super.visitWhenBlockNode(blocksNode);
		}
		CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
		if (tokens != null) {
			RulesASTNode node = blocksNode.getFirstChildWithType(RulesParser.BLOCK);
			String nodeText = RuleGrammarUtils.getNodeText(tokens, node, false, false);
			// if node text is null then set it to blank
			if (nodeText == null){
				nodeText = "";
			}
			fRule.setConditionText(nodeText);
		}
		return super.visitWhenBlockNode(blocksNode);
	}

	private void doVisitList(List<RulesASTNode> node) {
		if (node == null) {
			return;
		}
		for (RulesASTNode object : node) {
			visit(object);
		}
	}
	
	@Override
	public boolean visitForLoopBlock(RulesASTNode node) {
		return visitChildren(node);
	}

	@Override
	public boolean visitIfRuleBlock(RulesASTNode node) {
		return visitChildren(node);
	}
	
	@Override
	public boolean visitTryStatementNode(RulesASTNode node) {
		return visitChildren(node);
	}
	
	@Override
	public boolean visitCatchRuleNode(RulesASTNode node) {
		return visitChildren(node);
	}
	
	@Override
	public boolean visitFinallyRuleNode(RulesASTNode node) {
		return visitChildren(node);
	}

	@Override
	public boolean visitRuleNode(RulesASTNode node) {
		fRootNode = node;
		fRule = RuleFactory.eINSTANCE.createRule();
		fRule.setOwnerProjectName(fProjectName);
		if (fIncludeFullRuleText) {
			CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
			if (tokens != null) {
				String nodeText = tokens.toString(0, tokens.size()-1);
				// if node text is null then set it to blank
				if (nodeText == null){
					nodeText = "";
				}
				
				fRule.setFullSourceText(nodeText);
			}
		}
		if (node != null) {
			RulesASTNode decl = node.getChildOfType(RulesParser.RULE_DECL);
			visit(decl);
		}
		return false;
	}

	@Override
	public boolean visitRuleTemplateNode(RulesASTNode node) {
		fRootNode = node;
		fRule = RuleFactory.eINSTANCE.createRuleTemplate();
		fRule.setOwnerProjectName(fProjectName);
		if (fIncludeFullRuleText) {
			CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
			if (tokens != null) {
				String nodeText = tokens.toString(0, tokens.size()-1);
				// if node text is null then set it to blank
				if (nodeText == null){
					nodeText = "";
				}
				
				fRule.setFullSourceText(nodeText);
			}
		}
		if (node != null) {
			RulesASTNode decl = node.getChildOfType(RulesParser.RULE_TEMPLATE_DECL);
			visit(decl);
		}
		return false;
	}
	
	@Override
	public boolean visitRuleFunctionNode(RulesASTNode node) {
		fRootNode = node;
		fRule = RuleFactory.eINSTANCE.createRuleFunction();
		fRule.setOwnerProjectName(fProjectName);
		if (fIncludeFullRuleText) {
			CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
			if (tokens != null) {
				String nodeText = tokens.toString(0, tokens.size()-1);
				// if node text is null then set it to blank
				if (nodeText == null){
					nodeText = "";
				}
				
				fRule.setFullSourceText(nodeText);
			}
		}
		if (node != null) {
			RulesASTNode decl = node.getChildOfType(RulesParser.RULE_FUNC_DECL);
			visit(decl);
		}
		return false;
	}

	@Override
	public boolean visitRuleDeclarationNode(RulesASTNode node) {
		
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		if (nameNode == null) {
			System.out.println("could not find name from AST");
		}
		String folder = RuleGrammarUtils.getQualifierFromNode(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
		String name = RuleGrammarUtils.getSimpleNameFromNode(nameNode);
		if(folder == null || folder.equals("")) {
			folder = "/";
		}
		fRule.setFolder(folder);
		fRule.setName(name);
		
		List<RulesASTNode> blocks = node.getChildrenByFeatureId(RulesParser.BLOCKS);
		doVisitList(blocks);
		
		return false;
	}

	@Override
	public boolean visitRuleTemplateDeclarationNode(RulesASTNode node) {
		
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		if (nameNode == null) {
			System.out.println("could not find name from AST");
		}
		String folder = RuleGrammarUtils.getQualifierFromNode(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
		String name = RuleGrammarUtils.getSimpleNameFromNode(nameNode);
		if(folder == null || folder.equals("")) {
			folder = "/";
		}
		fRule.setFolder(folder);
		fRule.setName(name);
		
		List<RulesASTNode> blocks = node.getChildrenByFeatureId(RulesParser.BLOCKS);
		doVisitList(blocks);
		
		return false;
	}
	
	@Override
	public boolean visitRuleFunctionDeclarationNode(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		if (nameNode == null) {
			System.out.println("could not find name from AST");
		}
		String folder = RuleGrammarUtils.getQualifierFromNode(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
		String name = RuleGrammarUtils.getSimpleNameFromNode(nameNode);
		if(folder == null || folder.equals("")) {
			folder = "/";
		}
		fRule.setFolder(folder);
		fRule.setName(name);

		RulesASTNode returnType = node.getChildByFeatureId(RulesParser.RETURN_TYPE);
		String retType = null;
		if (returnType != null) {
			if (RuleGrammarUtils.isName(returnType)) {
				retType = RuleGrammarUtils.getFullNameFromNode(returnType, RuleGrammarUtils.FOLDER_FORMAT);
			} else if (returnType.getType() == RulesParser.PRIMITIVE_TYPE) {
				retType = returnType.getFirstChild().getText();
			} else if (returnType.getType() == RulesParser.VOID_LITERAL) {
				retType = null;
			} else {
				retType = returnType.getText();
			}
			if (returnType.isArray()) {
				retType += "[]";
			}
		}
		fRule.setReturnType(retType);
		
		List<RulesASTNode> blocks = node.getChildrenByFeatureId(RulesParser.BLOCKS);
		doVisitList(blocks);
		
		RulesASTNode modifiers = node.getChildOfType(RulesParser.MODIFIERS);
		if (modifiers != null) {
			RulesASTNode virtual = modifiers.getChildOfType(RulesParser.VIRTUAL);
			((RuleFunction)fRule).setVirtual(virtual != null);
		}
		
		return false;
	}

	@Override
	public boolean visitNameNode(RulesASTNode node) {
		return super.visitNameNode(node);
	}

	@Override
	public boolean visitRuleBlockNode(RulesASTNode node) {
		return super.visitRuleBlockNode(node);
	}

	@Override
	public boolean visitDeclareBlockNode(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitScopeBlockNode(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitAttributeBlockNode(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> attributes = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
		doVisitList(attributes);
		return false;
	}
	
	@Override
	public boolean visitScopeDeclaratorNode(RulesASTNode node) {
		Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
		
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		if (typeNode != null) {
			symbol.setArray(typeNode.isArray());
			if (RuleGrammarUtils.isName(typeNode)) {
				symbol.setType(RuleGrammarUtils.getFullNameFromNode(typeNode, RuleGrammarUtils.FOLDER_FORMAT));
			} else if (typeNode.getType() == RulesParser.PRIMITIVE_TYPE){
				symbol.setType(typeNode.getFirstChild().getText());
			}
		}
		
		
		RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
		if (name != null) {
			symbol.setIdName(RuleGrammarUtils.getSimpleNameFromNode(name));
		}
		if (fRule.getSymbols() == null) {
			fRule.setSymbols(RuleFactory.eINSTANCE.createSymbols());
		}
		fRule.getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
		return false;
	}
	
	
	
	@Override
	public boolean visitAnnotationBlockNode(RulesASTNode node) {
		return super.visitAnnotationBlockNode(node);
	}

	@Override
	public boolean visitDeclaratorNode(RulesASTNode node) {
		RuleFunctionSymbol symbol = RuleFactory.eINSTANCE.createRuleFunctionSymbol();
		
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		if (typeNode != null) {
			if (RuleGrammarUtils.isName(typeNode)) {
				symbol.setType(RuleGrammarUtils.getFullNameFromNode(typeNode, RuleGrammarUtils.FOLDER_FORMAT));
			} else if (typeNode.getType() == RulesParser.PRIMITIVE_TYPE){
				symbol.setType(typeNode.getFirstChild().getText());
			}
		}

		RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
		if (name != null) {
			symbol.setIdName(RuleGrammarUtils.getSimpleNameFromNode(name));
		}
		if (fRule != null) {
			if (fRule.getSymbols() == null) {
				Symbols symbols = RuleFactory.eINSTANCE.createSymbols();
				fRule.setSymbols(symbols);
			}
			fRule.getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
		}
		return false;
	}

	
	
	@Override
	public boolean visitActionContextNode(RulesASTNode node) {
		ActionContext actionContext = RuleFactory.eINSTANCE.createActionContext();
		RuleTemplate template = (RuleTemplate) fRule;
		if (template != null) {
			template.setActionContext(actionContext);
		}
		RulesASTNode blockNode = node.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> declarators = blockNode.getChildrenByFeatureId(RulesParser.STATEMENTS);
		doVisitList(declarators);
		if (!fIncludeBodyText) {
			return false;
		}
		CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
		if (tokens != null) {
			String nodeText = RuleGrammarUtils.getNodeText(tokens, blockNode, false, false);
			// if node text is null then set it to blank
			if (nodeText == null){
				nodeText = "";
			}
			fRule.setActionText(nodeText);
		}

		return false;
	}

	@Override
	public boolean visitActionContextStatementNode(RulesASTNode node) {
		RulesASTNode actionTypeNode = node.getChildByFeatureId(RulesParser.ACTION_TYPE);
		String type = actionTypeNode == null ? "" : actionTypeNode.getText();
		Symbol symbol = createActionContextSymbol(node, type);
		RuleTemplate template = (RuleTemplate) fRule;
		if (template != null) {
			ActionContext context = template.getActionContext();
			Symbols symbols = context.getActionContextSymbols();
			if (context.getActionContextSymbols() == null) {
				symbols = RuleFactory.eINSTANCE.createSymbols();
				context.setActionContextSymbols(symbols);
			}
			symbols.getSymbolMap().put(symbol.getIdName(),symbol);
		}

		return false;
	}

	private Symbol createActionContextSymbol(RulesASTNode node, String type) {

		ActionContextSymbol symbol = RuleFactory.eINSTANCE.createActionContextSymbol();
		symbol.setActionType(ActionType.get(type));
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		if (typeNode != null) {
			if (RuleGrammarUtils.isName(typeNode)) {
				symbol.setType(RuleGrammarUtils.getFullNameFromNode(typeNode, RuleGrammarUtils.FOLDER_FORMAT));
			} else if (typeNode.getType() == RulesParser.PRIMITIVE_TYPE){
				symbol.setType(typeNode.getFirstChild().getText());
			}
		}

		RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
		if (name != null) {
			symbol.setIdName(RuleGrammarUtils.getSimpleNameFromNode(name));
		}
		return symbol;
	}
	
	@Override
	public boolean visitTemplateDeclaratorNode(RulesASTNode node) {
		RuleTemplateSymbol symbol = RuleFactory.eINSTANCE.createRuleTemplateSymbol();
		
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		if (typeNode != null) {
			if (RuleGrammarUtils.isName(typeNode)) {
				symbol.setType(RuleGrammarUtils.getFullNameFromNode(typeNode, RuleGrammarUtils.FOLDER_FORMAT));
			} else if (typeNode.getType() == RulesParser.PRIMITIVE_TYPE){
				symbol.setType(typeNode.getFirstChild().getText());
			}
		}
		
		RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
		if (name != null) {
			symbol.setIdName(RuleGrammarUtils.getSimpleNameFromNode(name));
		}
		RulesASTNode expNode = node.getChildByFeatureId(RulesParser.EXPRESSION);
		if (expNode == null) {
			expNode = node.getChildByFeatureId(RulesParser.INITIALIZER);
		}
		if (expNode != null) {
			CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
			if (tokens != null) {
				String expText = RuleGrammarUtils.getNodeText(tokens, expNode, true, true);
				symbol.setExpression(expText);
			}
		}
		if (fRule != null) {
			if (fRule.getSymbols() == null) {
				Symbols symbols = RuleFactory.eINSTANCE.createSymbols();
				fRule.setSymbols(symbols);
			}
			fRule.getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
		}
		return false;
	}
	
	@Override
	public boolean visitPriorityStatementNode(RulesASTNode node) {
		RulesASTNode priority = node.getChildByFeatureId(RulesParser.PRIORITY);
		if (priority != null) {
			((Rule)fRule).setPriority(Integer.valueOf(priority.getText()));
		}
		return false;
	}

	@Override
	public boolean visitForwardChainStatementNode(RulesASTNode node) {
		RulesASTNode forwardChain = node.getChildByFeatureId(RulesParser.LITERAL);
		if (forwardChain != null) {
			((Rule)fRule).setForwardChain(Boolean.valueOf(forwardChain.getText()));
		}
		return false;
	}
	
	@Override
	public boolean visitBackwardChainStatementNode(RulesASTNode node) {
		RulesASTNode bwdChain = node.getChildByFeatureId(RulesParser.LITERAL);
		if (bwdChain != null) {
			((Rule)fRule).setForwardChain(Boolean.valueOf(bwdChain.getText()));
		}
		return false;
	}
	
	@Override
	public boolean visitValidityStatementNode(RulesASTNode node) {
		RulesASTNode validity = node.getChildByFeatureId(RulesParser.VALIDITY);
		if (validity != null) {
			((RuleFunction)fRule).setValidity(Validity.valueOf(validity.getText()));
		}
		return false;
	}

	@Override
	public boolean visitRankStatementNode(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		String rankPath = RuleGrammarUtils.getFullNameFromNode(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
		fRule.setRank(rankPath);
		
		return false;
	}

	@Override
	public boolean visitAliasStatementNode(RulesASTNode node) {
		RulesASTNode literalNode = node.getChildByFeatureId(RulesParser.LITERAL);
		String alias = literalNode.getText();
		if (alias != null && alias.length() > 0) {
			if (alias.charAt(0) == '\"') {
				alias = alias.substring(1, alias.length()-1);
			}
		}
		((RuleFunction)fRule).setAlias(alias);
		
		return false;
	}

	/*
	@Override
	public boolean visitNullConstraintNode(RulesASTNode node) {
		RuleTemplate template = (RuleTemplate) fRule;
		if (template != null && template.getBindings().size() > 0) {
			Binding binding = template.getBindings().get(template.getBindings().size()-1);
			NotNullConstraint constraint = RuleFactory.eINSTANCE.createNotNullConstraint();
			binding.getConstraints().add(constraint);
		}
		return false;
	}

	@Override
	public boolean visitRangeConstraintNode(RulesASTNode node) {
		RuleTemplate template = (RuleTemplate) fRule;
		if (template != null && template.getBindings().size() > 0) {
			Binding binding = template.getBindings().get(template.getBindings().size()-1);
			RangeConstraint constraint = RuleFactory.eINSTANCE.createRangeConstraint();
			RulesASTNode minNode = node.getChildByFeatureId(RulesParser.MIN);
			constraint.setMin(Integer.valueOf(minNode.getText()));
			RulesASTNode maxNode = node.getChildByFeatureId(RulesParser.MAX);
			constraint.setMax(Integer.valueOf(maxNode.getText()));
			binding.getConstraints().add(constraint);
		}
		return false;
	}
*/
	
	@Override
	public boolean visitBindingsBlock(RulesASTNode node) {
		RulesASTNode blockNode = node.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> declarators = blockNode.getChildrenByFeatureId(RulesParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitDomainStatementNode(RulesASTNode node) {
		RuleTemplate template = (RuleTemplate) fRule;
		if (template != null && template.getBindings().size() > 0) {
			Binding binding = template.getBindings().get(template.getBindings().size()-1);
			RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
			if (name != null) {
				binding.setDomainModelPath(RuleGrammarUtils.getFullNameFromNode(name, RuleGrammarUtils.FOLDER_FORMAT));
			}
		}
		return false;
	}

	@Override
	public boolean visitBindingStatementNode(RulesASTNode node) {
		RuleTemplate template = (RuleTemplate) fRule;
		if (template != null) {
			
			Binding symbol = RuleFactory.eINSTANCE.createBinding();
			RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
			if (typeNode != null) {
				symbol.setArray(typeNode.isArray());
				if (RuleGrammarUtils.isName(typeNode)) {
					symbol.setType(RuleGrammarUtils.getFullNameFromNode(typeNode, RuleGrammarUtils.FOLDER_FORMAT));
				} else if (typeNode.getType() == RulesParser.PRIMITIVE_TYPE){
					symbol.setType(typeNode.getFirstChild().getText());
				}
			}
			
			RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
			if (name != null) {
				symbol.setIdName(RuleGrammarUtils.getSimpleNameFromNode(name));
			}
			RulesASTNode expNode = node.getChildByFeatureId(RulesParser.EXPRESSION);
			if (expNode == null) {
				expNode = node.getChildByFeatureId(RulesParser.INITIALIZER);
			}
			if (expNode != null) {
				CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
				if (tokens != null) {
					String expText = RuleGrammarUtils.getNodeText(tokens, expNode, true, true);
					symbol.setExpression(expText);
				}
			}
			
			template.getBindings().add(symbol); // add the binding before visiting the constraints, so that they can be added properly
			RulesASTNode domainNode = node.getChildByFeatureId(RulesParser.DOMAIN_MODEL);
			if (domainNode != null) {
				visit(domainNode);
//				List<RulesASTNode> constraints = domainNode.getChildrenByFeatureId(RulesParser.STATEMENTS);
//				doVisitList(constraints);
			}
			
		}
		return false;
	}

	@Override
	public boolean visitBindingViewStatement(RulesASTNode node) {
		RuleTemplate template = (RuleTemplate) fRule;
		if (template != null) {
			RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
			if (name != null) {
				template.getViews().add(RuleGrammarUtils.getFullNameFromNode(name, RuleGrammarUtils.FOLDER_FORMAT));
			}
		}
		return false;
	}

	@Override
	public boolean visitViewsBlock(RulesASTNode node) {
		RulesASTNode blockNode = node.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> declarators = blockNode.getChildrenByFeatureId(RulesParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}
	
	@Override
	protected boolean visitDefault(RulesASTNode node) {
		return visitChildren(node);
	}

	@Override
	public boolean visitBlockNode(RulesASTNode node) {
		return visitChildren(node);
	}

	@Override
	public boolean visitDisplayBlock(RulesASTNode node) {
		RulesASTNode blockNode = node.getFirstChildWithType(RulesParser.BLOCK);
		List<RulesASTNode> declarators = blockNode.getChildrenByFeatureId(RulesParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitDisplayProperty(RulesASTNode node) {
		SimpleProperty displayProp = ModelFactory.eINSTANCE.createSimpleProperty();
		
		RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
		if (name != null) {
			displayProp.setName(RuleGrammarUtils.getFullNameFromNode(name, RuleGrammarUtils.NAME_FORMAT));
		}

		RulesASTNode literalNode = node.getChildByFeatureId(RulesParser.LITERAL);
		if (literalNode != null) {
			String value = literalNode.getText();
			if (value.length() > 1 && value.charAt(0) == '"') {
				displayProp.setValue(value.substring(1, value.length()-1));
			} else {
				displayProp.setValue(value);
			}
		}
		((RuleTemplate) fRule).getDisplayProperties().add(displayProp);
		return false;
	}
	
	
}
