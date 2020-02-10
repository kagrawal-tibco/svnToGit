package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.RecognitionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.IProblemHandler;
import com.tibco.cep.studio.core.rules.IProblemTypes;
import com.tibco.cep.studio.core.rules.IResolutionContextProviderExtension;
import com.tibco.cep.studio.core.rules.IRulesProblem;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.IRulesSyntaxProblem;
import com.tibco.cep.studio.core.rules.ISemanticValidatorCallback;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.RulesSemanticASTVisitor;
import com.tibco.cep.studio.core.rules.RulesSemanticError;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

public class RulesReconcilingStrategy implements IReconcilingStrategy,
		IReconcilingStrategyExtension {

	/*
	 * This class is responsible for handling semantic errors that are detected from
	 * the AWT Thread (that is, an error from a BW5 XPath/XSLT string, which
	 * validates on the AWT Thread)
	 */
	private ISemanticValidatorCallback callback = new ISemanticValidatorCallback() {
		
		@Override
		public void errorReported(RulesSemanticError error) {
			if (!fEnabled) {
				return; // do not report errors for read only source viewer
			}
			IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
			if (annotationModel == null) {
				return;
			}
			addProblemAnnotation(annotationModel, error);
		}
	};
	
	class UnresolvedReferenceProblem implements IRulesProblem {

		private ElementReference reference;

		public UnresolvedReferenceProblem(ElementReference reference) {
			this.reference = reference;
		}
		
		public ElementReference getReference() {
			return reference;
		}
		
		@Override
		public int getLine() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getOffset() {
			return reference.getOffset();
		}

		@Override
		public int getLength() {
			return reference.getLength();
		}

		@Override
		public int getSeverity() {
			return IMarker.SEVERITY_ERROR;
		}

		@Override
		public int getProblemCode() {
			return 0;
		}

		@Override
		public String getErrorMessage() {
			return "Unresolved reference";
		}
		
	}

	private class ReconcilingProblemHandler implements IProblemHandler {

		private List<IRulesProblem> fCollectedProblems = new ArrayList<IRulesProblem>();
		
		public void handleProblem(IRulesProblem problem) {
			// errors can be reported multiple times due to the recovery mechanism
			// perform a (slow) check to make sure we don't report the same error
			// multiple times
			if (!collected(problem)) {
				fCollectedProblems.add(problem);
			}
		}

		private boolean collected(IRulesProblem problem) {
			for (IRulesProblem prob : fCollectedProblems) {
				if (problem instanceof IRulesSyntaxProblem && prob instanceof IRulesSyntaxProblem) {
					RecognitionException recognitionException = ((IRulesSyntaxProblem) prob).getRecognitionException();
					RecognitionException recognitionException2 = ((IRulesSyntaxProblem) problem).getRecognitionException();
					if (areEqual(recognitionException, recognitionException2)) {
						return true;
					}
				}
			}
			return false;
		}

		private boolean areEqual(RecognitionException r1,
				RecognitionException r2) {
			return (r1.approximateLineInfo == r2.approximateLineInfo
					&& r1.c == r2.c
					&& r1.charPositionInLine == r2.charPositionInLine
					&& r1.index == r2.index
					&& r1.line == r2.line);
		}

		@Override
		public List<IRulesProblem> getHandledProblems() {
			return fCollectedProblems;
		}

		public void clearCollectedProblems() {
			fCollectedProblems.clear();
		}

		@Override
		public boolean hasProblems() {
			return fCollectedProblems.size() > 0;
		}
		
	}

	private static boolean fDebug = false;
	
	private IDocument fDocument;
	private IProgressMonitor fProgressMonitor;
	private ReconcilingProblemHandler fProblemHandler = new ReconcilingProblemHandler();
	private Map<Annotation, Position> fProblemAnnotations = new HashMap<Annotation, Position>();
	private List<IReconcilingListener> fReconcileListeners = new ArrayList<IReconcilingListener>();
	private ISourceViewer fSourceViewer;
	private IResolutionContextProvider fResolutionContextProvider;

	private int fReconcileType = IRulesSourceTypes.FULL_SOURCE;
	private String fProjectName;
	
	// whether to resolve all element references and mark errors for unresolved elements
	// TODO : make this a preference
	private boolean fResolveReferences = true;

//	private Ontology fOntology;

	private boolean fCurrentlyReconciling;
	private boolean fEnabled;
	
	public RulesReconcilingStrategy(IResolutionContextProvider resolutionContextProvider, String projectName, ISourceViewer sourceViewer, int reconcileType) {
		this.fResolutionContextProvider = resolutionContextProvider;
		this.fProjectName = projectName;
		this.fSourceViewer = sourceViewer;
		this.fReconcileType = reconcileType;
//		this.fOntology = new OntologyEMFAdapter(fProjectName);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				fEnabled = fSourceViewer.isEditable();		
			}
		});

	}

	public void reconcile(IRegion partition) {
		try {
			reconcile();
		} catch (Exception e) {
			EditorsUIPlugin.log(e);
		}
	}

	private void reconcile() {
		if (!fEnabled) {
			return;
		}
		if (fCurrentlyReconciling) {
			return;
		}
		fCurrentlyReconciling = true;
		
		Annotation[] removedAnnotations = getClearedAnnotations();
		RulesASTNode rulesAST = null;
		rulesAST = RulesParserManager.getTree(fDocument, fReconcileType, fProjectName, fProblemHandler);
		
		if (rulesAST == null) {
			if (fResolutionContextProvider instanceof IResolutionContextProviderExtension) {
				NodeType retType = ((IResolutionContextProviderExtension) fResolutionContextProvider).getReturnType();
				if (!retType.isVoid()) {
					// must return a value, mark as error
					fProblemHandler.handleProblem(new RulesSemanticError(IProblemTypes.PROBLEM_INVALID_RETURN, "Rule must return a value of type '"+retType.getDisplayName()+"'", 0, 0, 0));
//					List<IRulesProblem> semanticErrors = new ArrayList<IRulesProblem>();
//					semanticErrors.add(new RulesSemanticError(IProblemTypes.PROBLEM_INVALID_RETURN, "Rule must return a value of type '"+retType.getDisplayName()+"'", 0, 0, 0));
//					reportSemanticErrors(semanticErrors);
				}
			}
			reportErrors(fProblemHandler.getHandledProblems(), removedAnnotations);
			postReconcile(rulesAST);
			fCurrentlyReconciling = false;
			return;
		}
		Object data = rulesAST.getData("element");
		if (data instanceof RuleElement && fResolveReferences ) {
			checkRuleSemantics(rulesAST);
			RuleElement element = (RuleElement) data;
			if (element.getIndexName() == null) {
				printDebug("Project name was null");
				reportErrors(fProblemHandler.getHandledProblems(), removedAnnotations);
				fCurrentlyReconciling = false;
				return;
			}
			resolveElementReferences((RuleElement) data);
//			checkRuleSemanticsFromCompilable(rulesAST);
		}
		reportErrors(fProblemHandler.getHandledProblems(), removedAnnotations);
		postReconcile(rulesAST);
		fCurrentlyReconciling = false;
	}

	private void checkRuleSemantics(RulesASTNode rulesAST) {
		Date start = new Date();
		try {
			RulesSemanticASTVisitor visitor = new RulesSemanticASTVisitor(fResolutionContextProvider, fProjectName, fReconcileType, callback);
			rulesAST.accept(visitor);
			List<IRulesProblem> semanticErrors = visitor.getSemanticErrors();
			collectSemanticErrorAnnotations(semanticErrors);
		} catch (Exception e) {
			EditorsUIPlugin.log(e);
		}
		Date end = new Date();
		printDebug("semantic check took "+(end.getTime() - start.getTime()));
	}

	private void collectSemanticErrorAnnotations(List<IRulesProblem> collectedProblems) {
		if (!fEnabled) {
			return; // do not report errors for read only source viewer
		}
		IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
		if (annotationModel == null) {
			return;
		}
		for (IRulesProblem problem : collectedProblems) {
			addProblemAnnotation(annotationModel, problem);
		}
	}

	private void removeFixedProblemAnnotations(
			IAnnotationModel annotationModel,
			List<IRulesProblem> collectedProblems) {
		Iterator iter = annotationModel.getAnnotationIterator();
		while (iter.hasNext()) {
			Annotation annotation = (Annotation) iter.next();
			if (!(annotation instanceof MarkerAnnotation)) {
				continue;
			}
			MarkerAnnotation markerAnn = (MarkerAnnotation) annotation;
			try {
				String markerType = markerAnn.getMarker().getType();
				if (IResourceValidator.VALIDATION_MARKER_TYPE.equals(markerType)) {
					// check to see if this marker has a corresponding IRulesProblem, if not, assume that this issue has been fixed
					Position position = annotationModel.getPosition(annotation);
					IRulesProblem matchingProb = getMatchingProblemFromPosition(position, collectedProblems);
					if (matchingProb == null) {
						markerAnn.markDeleted(true);
					} else {
						markerAnn.markDeleted(false);
					}
				}
			} catch (CoreException e) {
			}
		}
	}		

	private IRulesProblem getMatchingProblemFromPosition(Position position,
			List<IRulesProblem> collectedProblems) {
		for (IRulesProblem prob : collectedProblems) {
			if (position.getOffset() == prob.getOffset() && position.getLength() == prob.getLength()) {
				return prob;
			}
		}
		return null;
	}

	private void addProblemAnnotation(IAnnotationModel annotationModel,
			IRulesProblem problem) {
		String type = problem.getSeverity() == IMarker.SEVERITY_WARNING ? RulesResolutionProblemAnnotation.TYPE_WARNING : RulesProblemAnnotation.TYPE_SYNTAX_ERROR;
		RulesProblemAnnotation annotation = new RulesProblemAnnotation(problem, type, false);
		Position position = new Position(problem.getOffset(), problem.getLength());
		fProblemAnnotations.put(annotation, position);
//		annotationModel.addAnnotation(annotation, position);
	}

	/**
	 * Uses the old mechanism of semantic checks, however this
	 * depends on the ontology adapter framework and is more
	 * heavyweight.  Furthermore, it does not work well with
	 * partially defined rules.
	 * 
	 * @param rulesAST
	 */
//	private void checkRuleSemanticsFromCompilable(RulesASTNode rulesAST) {
//		Date start = new Date();
//		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(true);
//		rulesAST.accept(visitor);
//		Compilable rule = visitor.getRule();
//		if (rule instanceof RuleFunction) {
//			RuleCompiler compiler = new RuleCompiler(new RuleFunctionAdapter<RuleFunction>((RuleFunction) rule, fOntology));
//			List list = compiler.checkRuleFunctionBlock(new StringReader(rule.getActionText()), Validity.valueOf(((RuleFunction) rule).getValidity().getLiteral()));
//			int offset = getOffset(rulesAST, RulesParser.BODY_BLOCK);
//			reportSemanticErrors(offset, list);
//		} else {
//			RuleCompiler compiler = new RuleCompiler(new RuleAdapter<Rule>((Rule) rule, fOntology));
//			List list = compiler.checkConditionBlock(new StringReader(rule.getConditionText()));
//			int offset = getOffset(rulesAST, RulesParser.WHEN_BLOCK);
//			reportSemanticErrors(offset, list);
//			list = compiler.checkActionBlock(new StringReader(rule.getActionText()));
//			offset = getOffset(rulesAST, RulesParser.THEN_BLOCK);
//			reportSemanticErrors(offset, list);
//		}
//		Date end = new Date();
//		System.out.println("semantic check took "+(end.getTime() - start.getTime()));
//	}

//	private int getOffset(RulesASTNode rulesAST, int blockType) {
//		List<RulesASTNode> children = rulesAST.getFirstChild().getChildrenByFeatureId(RulesParser.BLOCKS);
//		for (RulesASTNode node : children) {
//			if (node.getType() == blockType) {
//				List<RulesASTNode> list = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
//				if (list.size() > 0) {
//					return list.get(0).getOffset();
//				}
//				return node.getOffset();
//			}
//		}
//		return 0;
//	}

	private void resolveElementReferences(RuleElement ruleElement) {
		Date start = new Date();
		ScopeBlock scope = ruleElement.getScope();
		List<ElementReference> unresolvedReferences = new ArrayList<ElementReference>();
		resolveElementReferencesInScope(scope, unresolvedReferences);
		Date end = new Date();
		printDebug("Element resolution took "+(end.getTime()-start.getTime()));
		collectUnresolvedReferenceErrors(unresolvedReferences);
	}

	private void resolveElementReferencesInScope(ScopeBlock scope,
			List<ElementReference> unresolvedReferences) {
		EList<ElementReference> refs = scope.getRefs();
		for (ElementReference elementReference : refs) {
			Object resolveElement = ElementReferenceResolver.resolveElement(elementReference, fResolutionContextProvider.getResolutionContext(elementReference));
			if (resolveElement == null) {
				// determine what link in the chain could not be resolved
				ElementReference qualifier = elementReference;
				while (qualifier.getQualifier() != null) {
					Object resolved = qualifier.getQualifier().getBinding();
					if (resolved == null) {
						// could not resolve this link, try the qualifier
						qualifier = qualifier.getQualifier();
					} else {
						// qualifier is the issue
						break;
					}
				}
				unresolvedReferences.add(qualifier);
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			resolveElementReferencesInScope(scopeBlock, unresolvedReferences);
		}
	}

	private void collectUnresolvedReferenceErrors(
			List<ElementReference> unresolvedReferences) {
		if (!fEnabled) {
			return; // do not report errors for read only source viewer
		}

		IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
		if (annotationModel == null) {
			return; // possibly a read-only case
		}
		for (ElementReference elementReference : unresolvedReferences) {
			fProblemHandler.handleProblem(new UnresolvedReferenceProblem(elementReference));
//			RulesResolutionProblemAnnotation annotation = new RulesResolutionProblemAnnotation(IProblemTypes.PROBLEM_RESOLUTION, elementReference, false);
//			fProblemAnnotations.add(annotation);
//			annotationModel.addAnnotation(annotation, 
//					new Position(elementReference.getOffset(), elementReference.getLength()));
		}
	}

	private void postReconcile(RulesASTNode rulesAST) {
		firePostReconcile(rulesAST);
	}

	private void firePostReconcile(final RulesASTNode rulesAST) {
		Display.getDefault().asyncExec(new Runnable() {
		
			public void run() {
				if (fReconcileListeners != null && fReconcileListeners.size() > 0) {
					for (IReconcilingListener listener : fReconcileListeners) {
						listener.reconciled(rulesAST);
					}
				}
			}
		});
	}

	private Annotation[] getClearedAnnotations() {
		fProblemHandler.clearCollectedProblems();
//		IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
		Set<Annotation> keySet = fProblemAnnotations.keySet();
		Annotation[] arr = (Annotation[]) keySet.toArray(new Annotation[keySet.size()]);
		fProblemAnnotations.clear();
		return arr;
//		if (annotationModel instanceof IAnnotationModelExtension) {
//			((IAnnotationModelExtension)annotationModel).replaceAnnotations(arr, null);
//		} else {
//			for (Iterator<Annotation> iterator = keySet.iterator(); iterator.hasNext();) {
//				Annotation annotation = (Annotation) iterator.next();
//				annotationModel.removeAnnotation(annotation);
//			}
//		}
//		for (Annotation annotation : fProblemAnnotations) {
//			annotationModel.removeAnnotation(annotation);
//		}
//		fProblemAnnotations.clear();
//		return null;

	}

	/*
	 * Create annotations in the source based on the collected syntax/resolution errors
	 * @param collectedProblems
	 */
	private void reportErrors(List<IRulesProblem> collectedProblems, Annotation[] removedAnnotations) {
		if (!fEnabled) {
			return; // do not report errors for read only source viewer
		}

		IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
		for (IRulesProblem problem : collectedProblems) {
			if (problem instanceof IRulesSyntaxProblem) {
				collectRulesSyntaxProblemAnnotations(problem, annotationModel);
			} else if (problem instanceof UnresolvedReferenceProblem) {
				ElementReference elementReference = ((UnresolvedReferenceProblem) problem).getReference();
				RulesResolutionProblemAnnotation annotation = new RulesResolutionProblemAnnotation(IProblemTypes.PROBLEM_RESOLUTION, elementReference, false);
				Position position = new Position(elementReference.getOffset(), elementReference.getLength());
				fProblemAnnotations.put(annotation, position);
//				annotationModel.addAnnotation(annotation, position);
			} else {
				addProblemAnnotation(annotationModel, problem);
			}
		}
		((IAnnotationModelExtension)annotationModel).replaceAnnotations(removedAnnotations, fProblemAnnotations);

		// now remove any 'fixed' marker errors
		removeFixedProblemAnnotations(annotationModel, collectedProblems);
	}

	private void collectRulesSyntaxProblemAnnotations(IRulesProblem problem,
			IAnnotationModel annotationModel) {
		String type = problem.getSeverity() == IMarker.SEVERITY_WARNING ? RulesResolutionProblemAnnotation.TYPE_WARNING : RulesProblemAnnotation.TYPE_SYNTAX_ERROR;
		RulesProblemAnnotation annotation = new RulesProblemAnnotation(problem, type, false);
		int offset = problem.getOffset();
		int length = problem.getLength();
		if (offset == -1) {
			offset = getEndOffset();
			length = getEndLength();
			if (length == 0) {
				if (offset > 0) {
					offset--;
				}
				length++;
			}
		}
		try {
			// CR 1-ADS99F - In form editors, if the annotation is at the end of the document, the red 'X' is not drawn properly
			if (length == 1 && offset+length >= fDocument.getLength()) {
				IRegion reg = fDocument.getLineInformationOfOffset(offset);
				offset = reg.getOffset();
				length = reg.getLength();
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		Position position = new Position(offset, length);
		fProblemAnnotations.put(annotation, position);
//		annotationModel.addAnnotation(annotation, position);
	}

	private int getEndLength() {
		try {
			return getLastNonWSLength(getLastLine());//fDocument.getLineLength(getLastLine()-1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int getEndOffset() {
		int lastLine = getLastLine();
		int endOffset = -1;
		try {
			endOffset = getLastNonWSOffset(lastLine);//fDocument.getLineOffset(lastLine-1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return endOffset;
	}
	
	private int getLastNonWSLength(int lastLine) throws BadLocationException {
		while (lastLine > 0) {
			IRegion lineInformation = fDocument.getLineInformation(lastLine-1);
			if (fDocument.get(lineInformation.getOffset(), lineInformation.getLength()).trim().length() > 0) {
				return lineInformation.getLength();
			}
			lastLine--;
		}
		return 0;
	}
	
	private int getLastNonWSOffset(int lastLine) throws BadLocationException {
		while (lastLine > 0) {
			IRegion lineInformation = fDocument.getLineInformation(lastLine-1);
			if (fDocument.get(lineInformation.getOffset(), lineInformation.getLength()).trim().length() > 0) {
				return lineInformation.getOffset();
			}
			lastLine--;
		}
		return 0;
	}

	private int getLastLine() {
		if (fDocument == null) {
			return 0;
		}
		return fDocument.getNumberOfLines();
	}

//	private void reportSemanticErrors(List<IRulesProblem> collectedProblems) {
//		IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
//		for (IRulesProblem problem : collectedProblems) {
//			RulesProblemAnnotation annotation = new RulesProblemAnnotation(problem, false);
//			fProblemAnnotations.add(annotation);
//			annotationModel.addAnnotation(annotation, 
//					new Position(problem.getOffset(), problem.getLength()));
//		}
//	}
	
//	private void reportSemanticErrors(int offset, List collectedProblems) {
//		if (!fSourceViewerEditable) {
//			return; // do not report errors for read only source viewer
//		}
//
//		IAnnotationModel annotationModel = fSourceViewer.getAnnotationModel();
//		for (Object problem : collectedProblems) {
//			RuleError error = (RuleError) problem;
//			RulesSemanticError semanticError = createSemanticError(offset, error);
//			String type = semanticError.getSeverity() == IMarker.SEVERITY_WARNING ? RulesResolutionProblemAnnotation.TYPE_WARNING : RulesProblemAnnotation.TYPE_SYNTAX_ERROR;
//			RulesProblemAnnotation annotation = new RulesProblemAnnotation(semanticError, type, false);
//			fProblemAnnotations.add(annotation);
//			annotationModel.addAnnotation(annotation, 
//					new Position(semanticError.getOffset(), semanticError.getLength()));
//		}
//	}
//	
//	private RulesSemanticError createSemanticError(int offsetDelta, RuleError error) {
//		IDocument doc = fSourceViewer.getDocument();
//		Token beginExtent = error.getBeginExtent();
//		Token endExtent = error.getEndExtent();
//		
//		try {
//			int lineDelta = doc.getLineOfOffset(offsetDelta);
//			int indentDelta = offsetDelta - doc.getLineOffset(lineDelta);
//			int startOffset = doc.getLineOffset(beginExtent.beginLine-1+lineDelta);
//			startOffset += beginExtent.beginColumn - 1;
//			if (beginExtent.beginLine == 1) {
//				startOffset += indentDelta;
//			}
//			int endOffset = doc.getLineOffset(endExtent.endLine-1+lineDelta);
//			endOffset += endExtent.endColumn;
//			if (beginExtent.beginLine == 1) {
//				endOffset += indentDelta;
//			}
//			return new RulesSemanticError(error.getMessage(), startOffset, endOffset-startOffset, doc.getLineOfOffset(startOffset));
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		reconcile();
	}

	public void setDocument(IDocument document) {
		this.fDocument = document;
	}

	public void initialReconcile() {
		reconcile();
	}

	public void setProgressMonitor(IProgressMonitor monitor) {
		this.fProgressMonitor = monitor;
	}

	public void addReconcileListener(IReconcilingListener listener) {
		if (!fReconcileListeners.contains(listener)) {
			fReconcileListeners.add(listener);
		}
	}
	
	public void removeReconcileListener(IReconcilingListener listener) {
		if (fReconcileListeners.contains(listener)) {
			fReconcileListeners.remove(listener);
		}
	}
	
	private static void printDebug(String message) {
		if (fDebug) {
			System.out.println(message);
		}
	}

	public void uninstall() {
		if (fReconcileListeners != null) {
			fReconcileListeners.clear();
			fReconcileListeners = null;
		}
		if (fProblemAnnotations != null) {
			fProblemAnnotations.clear();
			fProblemAnnotations = null;
		}
		fSourceViewer = null;
		fResolutionContextProvider = null;
		fProblemHandler = null;
	}

	public void setEnablement(boolean enabled) {
		this.fEnabled = enabled;
	}

}
