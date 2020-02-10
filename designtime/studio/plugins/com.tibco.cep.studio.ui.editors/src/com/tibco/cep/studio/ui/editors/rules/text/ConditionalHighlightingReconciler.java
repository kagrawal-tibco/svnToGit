package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.ui.util.ColorConstants;

/**
 * This class is responsible for taking the reconciled object
 * and creating positions that should be conditionally highlighted
 * in the source code (i.e. Catalog Function calls)
 * 
 * @author rhollom
 *
 */
public class ConditionalHighlightingReconciler implements IReconcilingListener {

	private List<HighlightedPosition> fAddedPositions = new ArrayList<HighlightedPosition>();
	private List<HighlightedPosition> fRemovedPositions = new ArrayList<HighlightedPosition>();
	private ConditionalHighlightingPresenter fPresenter;
	private ISourceViewer fSourceViewer;
	private IResolutionContextProvider fContextProvider;
	
	public ConditionalHighlightingReconciler(ConditionalHighlightingPresenter presenter, ISourceViewer viewer, IResolutionContextProvider contextProvider) {
		super();
		this.fPresenter = presenter;
		this.fSourceViewer = viewer;
		this.fContextProvider = contextProvider;
	}

	public void reconciled(Object result) {
//		add semantically highlighted ranges
		if (!(result instanceof RulesASTNode)) {
			return;
		}
		RulesASTNode node = (RulesASTNode) result;
		RuleElement element = (RuleElement) node.getData("element");
		if (element == null) {
			return;
		}
		fAddedPositions.clear();
		fRemovedPositions.clear();
		RootScopeBlock scope = element.getScope();
		EList<GlobalVariableDef> defs = element.getGlobalVariables();
		for (GlobalVariableDef variableDef : defs) {
			fAddedPositions.add(new HighlightedPosition(variableDef.getOffset(), 
					variableDef.getLength(), createTextAttribute(variableDef)));
		}

		collectPositions(scope);
		
		// create the presentation and apply it
		TextPresentation presentation = fPresenter.createPresentation(fAddedPositions, fRemovedPositions);
		
		updateConditionalPresentation(presentation);
		
	}

	private void updateConditionalPresentation(TextPresentation presentation) {
		if (presentation == null) {
			return;
		}
		Runnable runnable = fPresenter.createUpdateJob(presentation, fAddedPositions, fRemovedPositions);
		Display.getDefault().asyncExec(runnable);
	}

	private void collectPositions(ScopeBlock scope) {
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			fAddedPositions.add(new HighlightedPosition(localVariableDef.getOffset(), 
					localVariableDef.getLength(), createTextAttribute(localVariableDef)));
		}
		EList<ElementReference> refs = scope.getRefs();
		for (ElementReference reference : refs) {
			collectReferencePositions(reference, false);
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			collectPositions(scopeBlock);
		}
	}

	private void collectReferencePositions(ElementReference reference, boolean isMethod) {
		TextAttribute attribute = createTextAttribute(reference, isMethod);
		if (attribute != null) {
			fAddedPositions.add(new HighlightedPosition(reference.getOffset(), 
					reference.getLength(), attribute));
		}
		if (reference.getQualifier() != null) {
			boolean method = isMethod || reference.isMethod();
			collectReferencePositions(reference.getQualifier(), method);
		}
	}

	public static final Color fVarColor = new Color(null, 0, 0, 185);
	
	private TextAttribute createTextAttribute(VariableDefinition definition) {
//		Color fgColor = ColorConstants.darkBlue;
		int style = definition instanceof LocalVariableDef ? 0 : SWT.ITALIC;
		TextAttribute att = new TextAttribute(fVarColor, null, style);
		return att;
	}

//	private TextAttribute createTextAttribute(VariableDefinition definition) {
//		IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
//		int style = definition instanceof LocalVariableDef ? 0 : SWT.ITALIC;
//		if (annotationModel != null) {
//			Iterator iter = annotationModel.getAnnotationIterator();
//			while (iter.hasNext()) {
//				Annotation annotation = (Annotation) iter.next();
//				if (!(annotation instanceof RulesProblemAnnotation)) {
//					continue;
//				}
//				Position position = annotationModel.getPosition(annotation);
//				if (position.getOffset() <= definition.getOffset() && (definition.getOffset() + definition.getLength() <= position.getOffset() + position.getLength())) {
//					style |= TextAttribute.UNDERLINE;
//				}
//			}
//		}
//		Color fgColor = ColorConstants.blue;
//		TextAttribute att = new TextAttribute(fgColor, null, style);
//		return att;
//	}
	
	private TextAttribute createTextAttribute(ElementReference reference, boolean isMethod) {
		Object binding = reference.getBinding();
		if (binding == null) {
			binding = ElementReferenceResolver.resolveElement(reference, fContextProvider.getResolutionContext(reference));
			if (binding == null) {
				return null;
			}
		}
		if (binding instanceof VariableDefinition) {
			return createTextAttribute((VariableDefinition) binding);
		}
		if (binding instanceof FunctionsCategory) {
			return createTextAttribute((FunctionsCategory) binding);
		}
		if (binding instanceof ElementContainer) {
			return createTextAttribute((ElementContainer) binding);
		}
		if (binding instanceof EntityElement && !reference.isMethod()) {
			return createTextAttribute((EntityElement) binding);
		}
		return null;
	}

	private TextAttribute createTextAttribute(EntityElement binding) {
		Color fgColor = ColorConstants.darkBlue;
		int style = SWT.BOLD;
		TextAttribute att = new TextAttribute(fgColor, null, style);
		return att;
	}

	private TextAttribute createTextAttribute(ElementContainer binding) {
		Color fgColor = ColorConstants.darkBlue;
		int style = SWT.BOLD;
		TextAttribute att = new TextAttribute(fgColor, null, style);
		return att;
	}

	private TextAttribute createTextAttribute(FunctionsCategory binding) {
		Color fgColor = ColorConstants.darkGray;
		int style = SWT.BOLD;
		TextAttribute att = new TextAttribute(fgColor, null, style);
		return att;
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

}
