package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.PlatformUI;

import com.tibco.be.model.functions.Predicate;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;

public class XsltHyperlink implements IHyperlink {

	private static final String XPATH_PREFIX = "XPath";
	
	private RulesASTNode fNode;
	private String fProjectName;
	private IDocument fDocument; // needed to insert code into from XMLMapper
	private IResolutionContextProvider fProvider;
	private boolean fMapperEditable;

	public XsltHyperlink(RulesASTNode node, String projectName, IDocument document, IResolutionContextProvider provider, boolean mapperEditable) {
		this.fNode = node;
		this.fProjectName = projectName;
		this.fDocument = document;
		this.fProvider = provider;
		this.fMapperEditable = mapperEditable;
	}

	public IRegion getHyperlinkRegion() {
		return new Region(fNode.getOffset(), fNode.getLength());
	}

	public String getHyperlinkText() {
		return null;
	}

	public String getTypeLabel() {
		return null;
	}

	public void open() {
		try {
			if (fNode == null || fNode.getType() != RulesParser.StringLiteral) {
				return;
			}
			String xsltText = fNode.getText();
			RuleElement element = (RuleElement) getRuleElement(fNode);
			List<VariableDefinition> variableDefinitions = getVariableDefinitions(element, fNode.getOffset());
			Predicate function = getFunction(fNode);
			MapperInvocationContext ctx = new MapperInvocationContext(fProjectName, variableDefinitions, xsltText, function, fDocument, fNode);
			ctx.setMapperEditable(fMapperEditable);
			if (function.getName().namespaceURI.equals(XPATH_PREFIX)) {
				MapperUtils.invokeXPathBuilder(ctx, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			} else {
				MapperUtils.invokeMapper(ctx, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Predicate getFunction(RulesASTNode xmlStringNode) {
		RulesASTNode node = getMethodNameNode(xmlStringNode);
		EObject reference = RuleGrammarUtils.getElementReference(node);
		if (reference == null) {
			return null;
		}
		if (reference instanceof ElementReference) {
			Object element = ElementReferenceResolver.resolveElement((ElementReference) reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope((ElementReference) reference)));
			if (element instanceof Predicate) {
				return (Predicate) element;
			}
		}
		return null;
	}

	private RulesASTNode getMethodNameNode(RulesASTNode xmlStringNode) {
		RulesASTNode node = (RulesASTNode) xmlStringNode.getParent();
		while (node != null) {
			if (node.getType() == RulesParser.METHOD_CALL) {
				RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
				if (nameNode.getType() == RulesParser.QUALIFIED_NAME) {
					return nameNode.getFirstChildWithType(RulesParser.SIMPLE_NAME);
				}
				return nameNode;
			}
			node = (RulesASTNode) node.getParent();
		}
		return null;
	}

	private RuleElement getRuleElement(RulesASTNode node) {
		if (node == null) {
			return null;
		}
		while (node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		return (RuleElement) node.getData("element");
	}

	private List<VariableDefinition> getVariableDefinitions(
			RuleElement ruleElement, int offset) {
		EObject elementReference = RuleGrammarUtils.getElementReference(getMethodCallNode());
		List<VariableDefinition> vars = new ArrayList<VariableDefinition>();
		if (elementReference instanceof ElementReference) {
			IResolutionContext resolutionContext = fProvider.getResolutionContext((ElementReference) elementReference);
			for (GlobalVariableDef globalVariableDef : resolutionContext.getGlobalVariables()) {
				if (globalVariableDef.getOffset() < offset) {
					vars.add(globalVariableDef);
				}
			}
		} else {
			if (ruleElement == null) {
				return vars;
			}
			for (GlobalVariableDef globalVariableDef : ruleElement.getGlobalVariables()) {
				if (globalVariableDef.getOffset() < offset) {
					vars.add(globalVariableDef);
				}
			}
		}

		if (elementReference.eContainer() instanceof ScopeBlock) {
			ScopeBlock scope = (ScopeBlock) elementReference.eContainer();
			processScope(scope, vars, offset);
		} else {
			// does this ever happen?  Leaving here for now
			ScopeBlock scope = ruleElement.getScope();
			processAllScopes(scope, vars, offset);
		}
		Collections.sort(vars, new Comparator<VariableDefinition>() {

			@Override
			public int compare(VariableDefinition o1, VariableDefinition o2) {
				return o1.getOffset() > o2.getOffset() ? 1 : -1;
			}
		});
		return vars;
	}

	private List<VariableDefinition> processScope(ScopeBlock scope, List<VariableDefinition> vars,
			int offset) {

		if (scope == null || scope.getOffset() > offset) {
			return vars;
		}
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			if (localVariableDef.getOffset() < offset) {
				vars.add(localVariableDef);
			}
		}
		ScopeBlock parent = scope.getParentScopeDef();
		processScope(parent, vars, offset);
		return vars;

	}

	private RulesASTNode getMethodCallNode() {
		RulesASTNode methodNode = fNode;
		while (methodNode.getParent() != null) {
			methodNode = (RulesASTNode) methodNode.getParent();
			if (methodNode.getType() == RulesParser.METHOD_CALL) {
				return methodNode.getChildByFeatureId(RulesParser.NAME);
			}
		}
		return methodNode;
	}

	private List<VariableDefinition> processAllScopes(ScopeBlock scope,
			List<VariableDefinition> vars, int offset) {
		if (scope == null || scope.getOffset() > offset) {
			return vars;
		}
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			if (localVariableDef.getOffset() < offset) {
				vars.add(localVariableDef);
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			processAllScopes(scopeBlock, vars, offset);
		}
		return vars;
	}

	
	
}
