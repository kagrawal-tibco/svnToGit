package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNodeFinder;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.search.RuleVariableSearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;

/**
 * This class is responsible for taking the current editor
 * offset, resolving that node, and conditionally highlighting
 * variable usage
 * 
 * @author rhollom
 *
 */
public class SelectionHighlightingPresenter implements CaretListener, IReconcilingListener {

	private List<HighlightedPosition> fAddedPositions = new ArrayList<HighlightedPosition>();
	private List<HighlightedPosition> fRemovedPositions = new ArrayList<HighlightedPosition>();
	private ConditionalHighlightingPresenter fPresenter;
	private ISourceViewer fSourceViewer;
	private IResolutionContextProvider fContextProvider;
	
	public SelectionHighlightingPresenter(ConditionalHighlightingPresenter presenter, ISourceViewer viewer, IResolutionContextProvider contextProvider) {
		super();
		this.fPresenter = presenter;
		this.fSourceViewer = viewer;
		this.fContextProvider = contextProvider;
	}

	private void updateConditionalPresentation(TextPresentation presentation) {
		if (presentation == null) {
			return;
		}
		Runnable runnable = fPresenter.createUpdateJob(presentation, fAddedPositions, fRemovedPositions);
		Display.getDefault().asyncExec(runnable);
	}

	public static final Color fVarColor = ConditionalHighlightingReconciler.fVarColor;
	public static final Color fRefBgColor = new Color(null, 210, 210, 210);
	public static final Color fDefBgColor = new Color(null, 235, 200, 150);
	
	private TextAttribute createTextAttribute(VariableDefinition definition, boolean isDefinition) {
		StyleRange currStyle = fSourceViewer.getTextWidget().getStyleRangeAtOffset(definition.getOffset());
		
		int style = currStyle != null ? currStyle.fontStyle : 0;

		Color bgColor = isDefinition ? fDefBgColor : fRefBgColor;
		TextAttribute att = new TextAttribute(fVarColor, bgColor, style);
		return att;
	}

	private TextAttribute createTextAttribute(ElementReference reference, boolean isMethod) {
		Object binding = reference.getBinding();
		if (binding == null) {
			binding = ElementReferenceResolver.resolveElement(reference, fContextProvider.getResolutionContext(reference));
			if (binding == null) {
				return null;
			}
		}
		if (binding instanceof VariableDefinition) {
			if (((VariableDefinition) binding).getOffset() == reference.getOffset()) {
				return createTextAttribute((VariableDefinition) binding, true);
			} else {
				return createTextAttribute((VariableDefinition) binding, false);
			}
		}
		return null;
	}

	public void uninstall() {
		if (fPresenter != null) {
			fPresenter.uninstall();
			fPresenter = null;
		}
		fSourceViewer = null;
		fContextProvider = null;
		if (fVarColor != null) {
			fVarColor.dispose();
		}
	}

	@Override
	public void caretMoved(CaretEvent ev) {
		int offset = ev.caretOffset;
		updateHightlightPositions(offset);
	}

	private void updateHightlightPositions(int offset) {
		if (fSourceViewer instanceof IAdaptable) {
			Object adapter = ((IAdaptable) fSourceViewer).getAdapter(RulesEditor.class);
			if (adapter instanceof RulesEditor) {
				RulesASTNode currNode = ((RulesEditor) adapter).getRulesAST();
				if(currNode !=null){
					if (currNode.isDirty()) {
						return; // will be done post-reconcile
					}
				}
				RulesASTNodeFinder finder = new RulesASTNodeFinder(offset);
				if(currNode !=null){
					currNode.accept(finder);
				}
				RulesASTNode foundNode = finder.getFoundNode();
				if (foundNode != null && !foundNode.isDirty()) {
					if (foundNode.getType() == RulesParser.Identifier) {
						foundNode = (RulesASTNode) foundNode.getParent();
					}
				}
				resolveAndHighlightUsages(foundNode, ((RulesEditor) adapter).getProjectName());
			}
		}		
	}

	private boolean resolveAndHighlightUsages(RulesASTNode node, String projName) {
		fAddedPositions.clear();
		EObject reference = node == null ? null : RuleGrammarUtils.getElementReference(node);
		if (node == null || reference == null) {
			TextPresentation presentation = fPresenter.createPresentation(fAddedPositions, fRemovedPositions);
			
			updateConditionalPresentation(presentation);

			fRemovedPositions.clear();
			return false;
		}
		
		if (reference instanceof ElementReference) {
			Object element = ElementReferenceResolver.resolveElement((ElementReference) reference, fContextProvider.getResolutionContext((ElementReference) reference));
			if (element instanceof VariableDefinition) {
				reference = (EObject) element;
			}
		}
		if (reference instanceof VariableDefinition) {
			RuleVariableSearchParticipant part = new RuleVariableSearchParticipant();
			SearchResult search = part.search(reference, projName, ((VariableDefinition) reference).getName(), new NullProgressMonitor());
			List<EObject> exactMatches = search.getExactMatches();
			for (EObject eObject : exactMatches) {
				if (eObject instanceof VariableDefinition) {
					VariableDefinition variableDef = (VariableDefinition) eObject;
					fAddedPositions.add(new HighlightedPosition(variableDef.getOffset(), 
							variableDef.getLength(), createTextAttribute(variableDef, true)));
				} else if (eObject instanceof ElementReference) {
					ElementReference ref = (ElementReference) eObject;
					fAddedPositions.add(new HighlightedPosition(ref.getOffset(), 
							ref.getLength(), createTextAttribute(ref, false)));
				}

			}
		}
		// create the presentation and apply it
		TextPresentation presentation = fPresenter.createPresentation(fAddedPositions, fRemovedPositions);
		
		updateConditionalPresentation(presentation);

		fRemovedPositions.clear();
		fRemovedPositions.addAll(fAddedPositions);
		
		return false;
	}

	@Override
	public void reconciled(Object result) {
		if (!(result instanceof RulesASTNode)) {
			return;
		}
		RulesASTNode node = (RulesASTNode) result;
		RuleElement element = (RuleElement) node.getData("element");
		if (element == null) {
			return;
		}		
		int offset = fSourceViewer.getTextWidget().getCaretOffset();
		updateHightlightPositions(offset);
	}

}
