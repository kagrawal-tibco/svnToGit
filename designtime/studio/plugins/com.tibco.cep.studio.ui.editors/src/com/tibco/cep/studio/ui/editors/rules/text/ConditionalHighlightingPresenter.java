package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextInputListener;
import org.eclipse.jface.text.ITextPresentationListener;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyleRange;

/**
 * This class is responsible for updating the conditionally
 * highlighted positions in the source viewer, based on
 * text input.  If the user modifies any position that has
 * a conditionally highlighted range, we need to update it
 * with a new styled range
 * @author rhollom
 *
 */
public class ConditionalHighlightingPresenter implements
		ITextPresentationListener, IDocumentListener, ITextInputListener {

	private ISourceViewer fSourceViewer;
	private RulesPresentationReconciler fPresentationReconciler;
	private List<HighlightedPosition> fHighlightedPositions;

	public void install(ISourceViewer viewer, RulesPresentationReconciler reconciler) {
		this.fSourceViewer = viewer;
		this.fPresentationReconciler = reconciler;
		((TextViewer)fSourceViewer).addTextPresentationListener(this);
		fSourceViewer.addTextInputListener(this);
		IDocument document = fSourceViewer.getDocument();
		initDocument(document);
	}
	
	private void initDocument(IDocument document) {
		if (document != null) {
			document.addPositionCategory(toString());
			document.addPositionUpdater(new InclusivePositionUpdater(toString()));
		}
	}

	private int computeIndexAtOffset(List positions, int offset) {
		int i= -1;
		int j= positions.size();
		while (j - i > 1) {
			int k= (i + j) >> 1;
			Position position= (Position) positions.get(k);
			if (position.getOffset() >= offset)
				j= k;
			else
				i= k;
		}
		return j;
	}
	
	public void applyTextPresentation(TextPresentation textPresentation) {
		if (fHighlightedPositions == null) {
			return;
		}
		IDocument document= fSourceViewer.getDocument();
		if (document == null) {
			return;
		}

		IRegion region= textPresentation.getExtent();
		int i= computeIndexAtOffset(fHighlightedPositions, region.getOffset()), n= computeIndexAtOffset(fHighlightedPositions, region.getOffset() + region.getLength());
		if (n - i > 2) {
			List ranges= new ArrayList(n - i);
			outer:for (; i < n; i++) {
				HighlightedPosition position= (HighlightedPosition) fHighlightedPositions.get(i);
				try {
					ITypedRegion[] partitioning = document.computePartitioning(position.offset, position.length);
					for (ITypedRegion typedRegion : partitioning) {
						if (IRulesEditorPartitionTypes.COMMENT.equals(typedRegion.getType())) {
							continue outer;
						}
					}
				} catch (BadLocationException e) {
				}
				if (!position.isDeleted()) {
					ranges.add(position.createStyleRange());
				}
			}
			StyleRange[] array= new StyleRange[ranges.size()];
			array= (StyleRange[]) ranges.toArray(array);
			textPresentation.replaceStyleRanges(array);
		} else {
			outer:for (; i < n; i++) {
				HighlightedPosition position= (HighlightedPosition) fHighlightedPositions.get(i);
				try {
					ITypedRegion[] partitioning = document.computePartitioning(position.offset, position.length);
					for (ITypedRegion typedRegion : partitioning) {
						if (IRulesEditorPartitionTypes.COMMENT.equals(typedRegion.getType())) {
							break outer;
						}
					}
				} catch (BadLocationException e) {
				}
				if (!position.isDeleted()) {
					textPresentation.replaceStyleRange(position.createStyleRange());
				}
			}
		}
	}
	
	public void documentAboutToBeChanged(DocumentEvent event) {
	}

	public void documentChanged(DocumentEvent event) {
	}

	public void inputDocumentAboutToBeChanged(IDocument oldInput,
			IDocument newInput) {
	}

	public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
		initDocument(newInput);
	}

	/**
	 * Creates the default presentation for the range spanned
	 * by the added/removed positions, WITHOUT the conditionally
	 * added styles (that is done later from the highlighting
	 * reconciler)
	 * @param addedPositions
	 * @param removedPositions
	 * @return
	 */
	public TextPresentation createPresentation(
			List<HighlightedPosition> addedPositions, List<HighlightedPosition> removedPositions) {

		if (fSourceViewer == null || fPresentationReconciler == null) {
			return null;
		}
		
		int start = Integer.MAX_VALUE;
		int end = Integer.MIN_VALUE;
		
		for (HighlightedPosition highlightedPosition : removedPositions) {
			start = Math.min(start, highlightedPosition.getOffset());
			end = Math.max(end, highlightedPosition.getOffset()+highlightedPosition.getLength());
		}
		
		for (HighlightedPosition highlightedPosition : addedPositions) {
			start = Math.min(start, highlightedPosition.getOffset());
			end = Math.max(end, highlightedPosition.getOffset()+highlightedPosition.getLength());
		}

		if (start >= end) {
			return null;
		}
		return fPresentationReconciler.createPresentation(new Region(start, end-start), fSourceViewer.getDocument());
	}

	public Runnable createUpdateJob(final TextPresentation presentation,
			final List<HighlightedPosition> addedPositions,
			final List<HighlightedPosition> removedPositions) {

		return new Runnable() {
		
			public void run() {
				updatePresentation(presentation, addedPositions, removedPositions);
			}
		};
	}

	protected void updatePresentation(TextPresentation presentation,
			List<HighlightedPosition> addedPositions,
			List<HighlightedPosition> removedPositions) {

		IDocument document= fSourceViewer.getDocument();
		if (document == null) {
			return;
		}

		for (HighlightedPosition highlightedPosition : removedPositions) {
			presentation.replaceStyleRange(highlightedPosition.createStyleRange());
//			highlightedPosition.createStyleRange();
		}
		
		for (HighlightedPosition highlightedPosition : addedPositions) {
			presentation.replaceStyleRange(highlightedPosition.createStyleRange());
			try {
				document.addPosition(toString(), highlightedPosition);
			} catch (BadLocationException e) {
				e.printStackTrace();
			} catch (BadPositionCategoryException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(addedPositions);
		fHighlightedPositions = addedPositions;
		fSourceViewer.changeTextPresentation(presentation, false);
	}

	public void uninstall() {
		if (fPresentationReconciler != null) {
			// fViewer is null, causing an NPE here...
//			fPresentationReconciler.uninstall();
			fPresentationReconciler = null;
		}
	}

}
