package com.tibco.cep.studio.ui.editors.rules.assist;

import java.util.Iterator;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.quickassist.IQuickFixableAnnotation;
import org.eclipse.jface.text.quickassist.QuickAssistAssistant;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;

public class RulesEditorQuickFixAssistant extends QuickAssistAssistant {

	private ISourceViewer fSourceViewer;
	private Annotation fCurrentAnnotation;

	public RulesEditorQuickFixAssistant() {
		super();
	}

	@Override
	public String showPossibleQuickAssists() {
		this.fCurrentAnnotation = null;
		int offset = fSourceViewer.getSelectedRange().x;
		try {
			IRegion lineInfo = fSourceViewer.getDocument().getLineInformationOfOffset(offset);
			int start = lineInfo.getOffset();
			int end = start + lineInfo.getLength();
			IAnnotationModel model = fSourceViewer.getAnnotationModel();
			Iterator iter = model.getAnnotationIterator();
			while (iter.hasNext()) {
				Annotation annotation= (Annotation) iter.next();
				if (annotation.isMarkedDeleted()) {
					continue;
				}
				if (!(annotation instanceof IQuickFixableAnnotation)) {
					continue;
				}
				IQuickFixableAnnotation ann = (IQuickFixableAnnotation) annotation;
				
				if (!ann.isQuickFixableStateSet() || !ann.isQuickFixable()) {
					continue;
				}
				Position pos = model.getPosition(annotation);
				int annotationStart = pos.offset;
				if (annotationStart >= start && annotationStart <= end) {
					this.fCurrentAnnotation = annotation;
					fSourceViewer.setSelectedRange(annotationStart, pos.length);
					break;
				}
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return super.showPossibleQuickAssists();
	}

	@Override
	public void install(ISourceViewer sourceViewer) {
		super.install(sourceViewer);
		this.fSourceViewer = sourceViewer;
	}

	public Annotation getCurrentAnnotation() {
		return fCurrentAnnotation;
	}

}
