package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.PresentationReconciler;

public class RulesPresentationReconciler extends PresentationReconciler {

	private IDocument fLastDocument;

	@Override
	public TextPresentation createPresentation(IRegion damage,
			IDocument document) {
		if (document != fLastDocument) {
			setDocumentToDamagers(document);
			setDocumentToRepairers(document);
			fLastDocument= document;
		}
		if (document == null) {
			return null;
		}
		return super.createPresentation(damage, document);
	}

}
