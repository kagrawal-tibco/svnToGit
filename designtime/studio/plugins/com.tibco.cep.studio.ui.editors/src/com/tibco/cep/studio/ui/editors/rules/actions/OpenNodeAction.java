package com.tibco.cep.studio.ui.editors.rules.actions;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openEditor;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.openElement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.source.ISourceViewer;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;


public class OpenNodeAction extends Action {

	private RulesASTNode fNode;
	private String fProjectName;
	private IResolutionContextProvider fContextProvider;
	private ISourceViewer fSourceViewer;
	private int fSourceType;

	public OpenNodeAction(ISourceViewer sourceViewer, int sourceType, RulesASTNode node, String projectName, IResolutionContextProvider contextProvider) {
		super("Open Node");
		this.fSourceViewer = sourceViewer;
		this.fSourceType = sourceType;
		this.fNode = node;
		this.fProjectName = projectName;
		this.fContextProvider = contextProvider;
	}

	@Override
	public void run() {
		resolveAndOpen(fNode);
	}

	private void resolveAndOpen(RulesASTNode node) {
		if (openFromRuleElement(node)) {
			return;
		}
		openFromASTNode(node);
	}

	private boolean openFromRuleElement(RulesASTNode node) {
		EObject reference = RuleGrammarUtils.getElementReference(node);
		if (reference == null) {
			return false;
		}
		if (reference instanceof VariableDefinition) {
			return openVariableDefinition(reference);
		}
		if (reference instanceof ElementReference) {
			Object element = ElementReferenceResolver.resolveElement((ElementReference) reference, fContextProvider.getResolutionContext((ElementReference) reference));
			if (element instanceof VariableDefinition) {
				return openVariableDefinition(element);
			}
			return openElement(element) != null;
		}
		return false;
	}

	private boolean openVariableDefinition(Object element) {
		if (element instanceof GlobalVariableDef && fSourceType == IRulesSourceTypes.FULL_SOURCE) {
			int offset = ((VariableDefinition) element).getOffset();
			int length = ((VariableDefinition) element).getLength();
			fSourceViewer.setRangeIndication(offset, length, true);
			fSourceViewer.setSelectedRange(offset, length);
			return true;
		}
		if (element instanceof LocalVariableDef) {
			int offset = ((VariableDefinition) element).getOffset();
			int length = ((VariableDefinition) element).getLength();
			fSourceViewer.setRangeIndication(offset, length, true);
			fSourceViewer.setSelectedRange(offset, length);
			return true;
		}
		return true;
	}

	protected boolean isXsltRef(ElementReference reference) {
		if ("createInstance".equals(reference.getName())) {
			ElementReference qualifier = reference.getQualifier();
			if (qualifier != null 
					&& "Instance".equals(qualifier.getName())
					&& qualifier.getQualifier() == null) {
				return true;
			}
			return false;
		}
		if ("createEvent".equals(reference.getName())) {
			ElementReference qualifier = reference.getQualifier();
			if (qualifier != null 
					&& "Event".equals(qualifier.getName())
					&& qualifier.getQualifier() == null) {
				return true;
			}
			return false;
		}
		return false;
	}

	private void openFromASTNode(RulesASTNode node) {
		System.out.println("Found node is "+node);
		// navigate up the AST until we get a NAME node,
		// then collect all names and resolve
		
		while (node.getType() != RulesParser.SIMPLE_NAME && node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		
		if (node.getType() != RulesParser.SIMPLE_NAME) {
			System.out.println("node is not part of a name node"+node.getText());
			return;
		}
		String fullName = RuleGrammarUtils.getPartialNameFromNode(node, RuleGrammarUtils.FOLDER_FORMAT);
		DesignerElement element = IndexUtils.getElement(fProjectName, fullName);
		if (element != null) {
			System.out.println("element resolved as "+element);
			if (element instanceof TypeElement) {
				openEditor((TypeElement) element);
			} else {
				System.out.println("Element was resolved, but is not a TypeElement: "+element);
			}
		} else {
			System.out.println("could not resolve element "+fullName);
		}
	}
}
