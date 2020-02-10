package com.tibco.cep.studio.ui.editors.rules.actions;

import java.util.Iterator;
import java.util.ResourceBundle;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.quickassist.IQuickFixableAnnotation;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationAccessExtension;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorExtension;
import org.eclipse.ui.texteditor.SelectMarkerRulerAction;

import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorQuickFixProcessor;
import com.tibco.cep.studio.ui.editors.rules.text.RulesProblemAnnotation;

public class RuleSelectAnnotationRulerAction extends SelectMarkerRulerAction {

	private ResourceBundle fResourceBundle;
	private ITextEditor fEditor;
	private Position fAnnotationPosition;
	private Object fAnnotation;
	private boolean fHasCorrection;
	private ResourceBundle fBundle;

	public RuleSelectAnnotationRulerAction(ResourceBundle bundle,
			String prefix, ITextEditor editor, IVerticalRulerInfo ruler) {
		super(bundle, prefix, editor, ruler);
		this.fResourceBundle = bundle;
		this.fEditor = editor;
		this.fBundle = bundle;
	}

	@Override
	public void run() {
		runWithEvent(null);
	}

	@Override
	public void runWithEvent(Event event) {
		if (fHasCorrection) {
			ITextOperationTarget operation= (ITextOperationTarget) fEditor.getAdapter(ITextOperationTarget.class);
			final int opCode= ISourceViewer.QUICK_ASSIST;
			if (operation != null && operation.canDoOperation(opCode)) {
				fEditor.selectAndReveal(fAnnotationPosition.getOffset(), fAnnotationPosition.getLength());
				operation.doOperation(opCode);
			}
			return;
		}
		
		super.run();
	}

	@Override
	public void update() {
		findCurrentRulesAnnotation();
		super.update();
		
		if (fHasCorrection) {
			if (fAnnotation instanceof RulesProblemAnnotation)
				initialize(fBundle, "RulesSelectAnnotationRulerAction.QuickFix."); //$NON-NLS-1$
			else
				initialize(fBundle, "RulesSelectAnnotationRulerAction.QuickFix."); //$NON-NLS-1$
			setEnabled(true);
			return;
		}
	}

	private void findCurrentRulesAnnotation() {
		fAnnotationPosition= null;
		fAnnotation= null;
		fHasCorrection= false;

		AbstractMarkerAnnotationModel model= getAnnotationModel();
		IAnnotationAccessExtension annotationAccess= getAnnotationAccessExtension();

		IDocument document= getDocument();
		if (model == null)
			return ;

//		boolean hasAssistLightbulb= fStore.getBoolean(PreferenceConstants.EDITOR_QUICKASSIST_LIGHTBULB);

		Iterator iter= model.getAnnotationIterator();
		int layer= Integer.MIN_VALUE;

		while (iter.hasNext()) {
			Annotation annotation= (Annotation) iter.next();
			if (annotation.isMarkedDeleted()) {
				continue;
			}

			if (!(annotation instanceof IQuickFixableAnnotation)) {
				continue;
			}
			
			int annotationLayer= layer;
			if (annotationAccess != null) {
				annotationLayer= annotationAccess.getLayer(annotation);
				if (annotationLayer < layer)
					continue;
			}

			Position position= model.getPosition(annotation);
			if (!includesRulerLine(position, document)) {
				continue;
			}

			boolean isReadOnly= fEditor instanceof ITextEditorExtension && ((ITextEditorExtension)fEditor).isEditorInputReadOnly();
			if (!isReadOnly && RulesEditorQuickFixProcessor.hasCorrections(annotation)) {
				fAnnotationPosition= position;
				fAnnotation= annotation;
				fHasCorrection= true;
				layer= annotationLayer;
				continue;
			} 
		}
	}

}
