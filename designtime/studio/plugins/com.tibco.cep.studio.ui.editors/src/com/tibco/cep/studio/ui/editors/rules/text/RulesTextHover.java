package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.ResolutionUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNodeFinder;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;

public class RulesTextHover implements ITextHover, ITextHoverExtension {

	private ISourceViewer fSourceViewer;
	private int fSourceType;
	private String fProjectName;
	private IResolutionContextProvider fContextProvider;
	private boolean fHTML;
	private boolean fContainsNewline;
	
	public RulesTextHover(ISourceViewer sourceViewer, int sourceType, String projectName, IResolutionContextProvider contextProvider) {
		this.fSourceViewer = sourceViewer;
		this.fSourceType = sourceType;
		this.fProjectName = projectName;
		this.fContextProvider = contextProvider;
	}
	
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (!isEnabled()) {
			return null;
		}
		
		fHTML = false;
		fContainsNewline = false;
		IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
		List<String> messages = new ArrayList<String>();
		getAnnotationErrors(hoverRegion, annotationModel, messages);
		boolean hasAnnotationErrors = messages.size() > 0;
		RulesASTNode foundNode = getCurrentNode(hoverRegion);
		if (foundNode != null) {
			EObject reference = RuleGrammarUtils.getElementReference(foundNode);
			if (reference instanceof VariableDefinition && !hasAnnotationErrors) {
				String text = RulesTextLabelDecorator.getInfo(reference, fProjectName);
				this.fHTML = text.indexOf("<html>") != -1;
				return text;
			}
			if (reference instanceof ElementReference) {
				if (!((ElementReference) reference).isMethod() && messages.size() > 0) {
					return getDisplayString(messages);
				}
				Object element = ElementReferenceResolver.resolveElement((ElementReference) reference, fContextProvider.getResolutionContext((ElementReference) reference));
				if (element != null) {
					String text = RulesTextLabelDecorator.getInfo(element, (ElementReference) reference, fProjectName);
					IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
					if (text.isEmpty() || element instanceof Folder ) {
						for (IElementResolutionProvider provider : elementResolutionProviders) {
							text = provider.getElementInfo(element, fProjectName);
							if (text == null || !text.isEmpty()) {
								break;
							}
						}
					}
					if (text != null) {
						this.fHTML = text.indexOf("<html>") != -1;
						this.fContainsNewline = text.indexOf("\n") != -1;
						if (!hasAnnotationErrors || ((ElementReference) reference).isMethod()) {
							return text;
						}
					}
				}
			}
			if (foundNode.getType() == RulesParser.Identifier) {
				foundNode = (RulesASTNode) foundNode.getParent();
			}
			if (foundNode.getType() == RulesParser.StringLiteral && isMapperCall(foundNode)) {
				if (foundNode.getText().startsWith("\"xslt://")) {
					messages.add("Ctrl+click to open the XSLT mapper");
				} else if (foundNode.getText().startsWith("\"xpath://")) {
					messages.add("Ctrl+click to open the XPATH formula builder");
				}
			}
			if (hasAnnotationErrors) {
				return getDisplayString(messages);
			}
			if (foundNode.getType() == RulesParser.QUALIFIED_NAME || foundNode.getType() == RulesParser.SIMPLE_NAME) {
				String name = RuleGrammarUtils.getPartialNameFromNode(foundNode, RuleGrammarUtils.NAME_FORMAT);
				messages.add(name);
			} else if (foundNode.getParent() != null 
					&& (foundNode.getType() == RulesParser.QUALIFIED_NAME 
							|| foundNode.getType() == RulesParser.SIMPLE_NAME)) {
				String name = RuleGrammarUtils.getPartialNameFromNode(foundNode, RuleGrammarUtils.NAME_FORMAT);
				messages.add(name);
			} 
		}
		return getDisplayString(messages);
	}

	private boolean isEnabled() {
		return EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(StudioPreferenceConstants.RULE_EDITOR_SHOW_TOOLTIPS);
	}

	private boolean isMapperCall(RulesASTNode foundNode) {
		while (foundNode.getParent() != null) {
			foundNode = (RulesASTNode) foundNode.getParent();
			if (foundNode.getType() == RulesParser.METHOD_CALL) {
				EObject reference = RuleGrammarUtils.getElementReference(foundNode);
				if (reference instanceof ElementReference) {
					ElementReference ref = (ElementReference) reference;
					if (!ref.isMethod()) {
						return false;
					}
					Object element = ElementReferenceResolver.resolveElement(ref, fContextProvider.getResolutionContext(ref));
					if (element instanceof JavaStaticFunctionWithXSLT) {
						return true;
					}
				}
				break;
			}
		}
		return false;
	}

	private void getAnnotationErrors(IRegion hoverRegion,
			IAnnotationModel annotationModel, List<String> messages) {
		if (annotationModel != null) {
			Iterator iter = annotationModel.getAnnotationIterator();
			while (iter.hasNext()) {
				Annotation annotation = (Annotation) iter.next();
				if (!(annotation instanceof IRulesAnnotation)) {
					continue;
				}
				Position position = annotationModel.getPosition(annotation);
				if (position.getOffset() <= hoverRegion.getOffset() 
						&& position.getOffset() + position.getLength() >= hoverRegion.getOffset()) {
					messages.add(annotation.getText());
				}
			}
		}
	}

	private String getDisplayString(List<String> messages) {
		if (messages.size() == 1) {
			return (String) messages.get(0);
		} else if (messages.size() > 1) {
			String display = "There are multiple markers at this line:\n";
			fContainsNewline = true;
			for (int i = 0; i < messages.size(); i++) {
				display += "\t- " + messages.get(i)
				+ "\n";
			}
			return display;
		}
		return null;
	}

	private RulesASTNode getCurrentNode(IRegion hoverRegion) {
		RulesEditor editor = null;
		if (fSourceViewer instanceof IAdaptable) {
			editor = (RulesEditor) ((IAdaptable)fSourceViewer).getAdapter(ITextEditor.class);
		}
		RulesASTNode tree = null;
		if (editor != null) {
			tree = editor.getRulesAST();
			if (tree == null) {
				tree = editor.reparse();
			}
		} else {
			IDocument document = fSourceViewer.getDocument();
			tree = RulesParserManager.getTree(document, fSourceType, fProjectName);
		}
		if (tree == null && editor == null) {
			// see comments below for the reason that we're doing this
			IDocument document = fSourceViewer.getDocument();
			tree = RulesParserManager.getTree(new Document(document.get()+";"), fSourceType, fProjectName);
		}
		if (tree == null) {
			System.out.println("Could not build tree to calculate text hover");
			return null;
		}
		RulesASTNodeFinder finder = new RulesASTNodeFinder(hoverRegion.getOffset());
		tree.accept(finder);
		RulesASTNode foundNode = finder.getFoundNode();
		if (foundNode == null && editor == null) {
			// a hack for form editors, where if there is a syntax error, the ast would be incomplete.
			// For instance, while calling a method -- System.debugOut("Hi" -- and then hovering over debugOut,
			// nothing will show.  So, insert a token and reparse, hoping to get a pseudo tree
			IDocument document = fSourceViewer.getDocument();
			tree = RulesParserManager.getTree(new Document(document.get()+";"), fSourceType, fProjectName);
			if (tree != null) {
				finder = new RulesASTNodeFinder(hoverRegion.getOffset());
				tree.accept(finder);
				foundNode = finder.getFoundNode();
			}
		}
		return foundNode;
	}

	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return new Region(offset, 0);
	}

	@Override
	public IInformationControlCreator getHoverControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				if (fHTML) {
					return new BrowserInformationControl(parent, EditorsUI.getTooltipAffordanceString());
				} else if (fContainsNewline) {
					return new DefaultInformationControl(parent); // newlines are removed if we use other constructor, but there is no wrapping of text
				} else {
					return new DefaultInformationControl(parent, false); // this allows wrapping of text
				}
			}
		};
	}

}
