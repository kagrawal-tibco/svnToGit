package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.projection.IProjectionListener;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;

public class RulesProjectionUpdater implements IProjectionListener, IReconcilingListener {

    private ITextEditor fEditor;
	private IPreferenceStore fPreferenceStore;
	private IDocument fCachedDocument;
	private ProjectionViewer fViewer;
	private RulesASTNode fInput;

	public void install(ITextEditor editor, ProjectionViewer viewer,
            IPreferenceStore store) {
        if (editor instanceof RulesEditor) {
            this.fEditor = editor;
            this.fViewer = viewer;
            this.fPreferenceStore = store;
            viewer.addProjectionListener(this);
        }
    }

    public void uninstall() {
    }

    public void initialize() {
        if (!isInstalled()) {
        	return;
        }

        IDocumentProvider provider = fEditor.getDocumentProvider();
        fCachedDocument = provider.getDocument(fEditor.getEditorInput());

        ProjectionAnnotationModel model = (ProjectionAnnotationModel) fEditor
                .getAdapter(ProjectionAnnotationModel.class);
        if (model == null)
            return;
        fInput = (RulesASTNode) fEditor.getAdapter(RulesASTNode.class);
        update(fInput);
    }

	private boolean isInstalled() {
		// TODO : check the preference store to see whether folding should take place
		return true;
	}

	private List<RulesASTNode> findProjectionNodes(RulesASTNode rulesAST) {
		if (rulesAST == null) {
			return new ArrayList<RulesASTNode>();
		}
		ProjectionNodeFinder finder = new ProjectionNodeFinder();
		rulesAST.accept(finder);
		return finder.getProjectionNodes();
	} 

	public void projectionDisabled() {
		
	}

	public void projectionEnabled() {
		projectionDisabled();
		
		if (fEditor instanceof RulesEditor) {
			initialize();
		}
	}

	public void reconciled(Object result) {
		if (!(result instanceof RulesASTNode)) {
			return;
		}
		RulesASTNode node = (RulesASTNode) result;
		update(node);
	}

	private void update(RulesASTNode node) {
//		fEditor.showHighlightRangeOnly(true);
//		fEditor.setHighlightRange(0, node.getOffset() + node.getLength(), true);

		ProjectionAnnotationModel annotationModel = fViewer.getProjectionAnnotationModel();
		if (annotationModel == null) {
			return;
		}
		
		// find the new projection nodes
		List<RulesASTNode> projectionNodes = findProjectionNodes(node);
		
		// now, find the projections that have been deleted
		List<Annotation> deletions = computeDeletions(annotationModel, projectionNodes);
		
		// next, find the added projections
		Map<ProjectionAnnotation, Position> additions = computeAdditions(annotationModel, projectionNodes);
		
		Annotation[] dels = new Annotation[deletions.size()];
		deletions.toArray(dels);
		
		// apply the delta to the annotation model
		annotationModel.modifyAnnotations(dels, additions, new Annotation[] {});
		
	}

	private Map<ProjectionAnnotation, Position> computeAdditions(IAnnotationModel annotationModel,
			List<RulesASTNode> projectionNodes) {
		Iterator annotationIterator;
		Map<ProjectionAnnotation, Position> additions = new HashMap<ProjectionAnnotation, Position>();
		for (RulesASTNode rulesASTNode : projectionNodes) {
			boolean isAdded = true;
			annotationIterator = annotationModel.getAnnotationIterator();
			while (annotationIterator.hasNext()) {
				ProjectionAnnotation ann = (ProjectionAnnotation) annotationIterator.next();
				Position position = annotationModel.getPosition(ann);
				if (positionsAreEqual(position, rulesASTNode)) {
					isAdded = false;
					break;
				}
			}
			if (isAdded) {
				ProjectionAnnotation annotation = null;
				if (rulesASTNode.getType() == RulesParser.MAPPING_BLOCK) {
					if (rulesASTNode.getChildCount() == 0) {
						continue;
					}
					annotation = new MappingProjectionAnnotation();
					annotation.setRangeIndication(false);
					annotation.markCollapsed();
				} else {
					annotation = new ProjectionAnnotation();
				}
				additions.put(annotation, new Position(rulesASTNode.getOffset(), rulesASTNode.getLength()));
			}
		}
		return additions;
	}

	private List<Annotation> computeDeletions(IAnnotationModel annotationModel, List<RulesASTNode> projectionNodes) {
		List<Annotation> deletions = new ArrayList<Annotation>();
		List<Annotation> modifications = new ArrayList<Annotation>();
		Iterator annotationIterator = annotationModel.getAnnotationIterator();
		while (annotationIterator.hasNext()) {
			Annotation ann = (Annotation) annotationIterator.next();
			Position position = annotationModel.getPosition(ann);
			boolean isDeleted = true;
			for (RulesASTNode rulesASTNode : projectionNodes) {
				if (positionsAreEqual(position, rulesASTNode)) {
					// no change to this annotation
					isDeleted = false;
					modifications.add(ann);
					break;
				}
			}
			if (isDeleted) {
				deletions.add(ann);
			}
		}
		return deletions;
	}

	private boolean positionsAreEqual(Position position,
			RulesASTNode rulesASTNode) {
		return position.getOffset() == rulesASTNode.getOffset()
					&& position.getLength() == rulesASTNode.getLength();
	}

}
