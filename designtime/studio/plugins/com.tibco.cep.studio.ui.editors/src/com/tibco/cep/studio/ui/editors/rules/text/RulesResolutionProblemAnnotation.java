package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.jface.text.quickassist.IQuickFixableAnnotation;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorQuickFixProcessor;

public class RulesResolutionProblemAnnotation extends Annotation implements IRulesAnnotation, IQuickFixableAnnotation {//, IAnnotationPresentation {

	public static final String TYPE_WARNING 	= "com.tibco.cep.studio.ui.editors.warning.resolution";
	public static final String TYPE_ERROR 		= "com.tibco.cep.studio.ui.editors.error.resolution";
	private ResolutionProblemContext fProblemContext;

	public RulesResolutionProblemAnnotation(int problemCode, ElementReference problem, boolean persistent, String type) {
		super(type, persistent, getErrorMessage(problem));
		this.fProblemContext = new ResolutionProblemContext(problemCode, problem);
	}
	
	private static String getErrorMessage(ElementReference problem) {
		StringBuilder builder = new StringBuilder();
		builder.append("Unable to resolve ");
		builder.append(problem.getName());
		builder.append(". ");
		if (problem.getQualifier() != null && problem.getQualifier().getBinding() != null) {
			builder.append("\n'");
			builder.append(problem.getName());
			builder.append("' is not a valid member of ");
			builder.append(getInfo(problem.getQualifier().getBinding()));
			if (!(problem.getQualifier().getBinding() instanceof VariableDefinition)) {
				builder.append(" '");
				builder.append(problem.getQualifier().getName());
				builder.append("'");
			}
		}

		return builder.toString();
	}

	private static String getInfo(Object binding) {
		return RulesTextLabelDecorator.getMetaInfo(binding);
	}

	public RulesResolutionProblemAnnotation(int problemCode, ElementReference problem, boolean persistent) {
		this(problemCode, problem, persistent, TYPE_ERROR);
	}

	public int getLayer() {
		return 0;
	}

	public void paint(GC gc, Canvas canvas, Rectangle bounds) {
		// this could be useful if we need to present the source viewer without
		// a corresponding editor, so we can draw error markers in the vertical ruler
		if (EditorsUIPlugin.getDefault() != null) {
			// need a better error image
			ImageUtilities.drawImage(EditorsUIPlugin.getDefault().getImage("icons/delete.gif"), gc, canvas, bounds, SWT.CENTER, SWT.TOP);
		} else {
			gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			gc.fillOval(bounds.x, bounds.y+bounds.width/2, bounds.width, bounds.height/2);
		}

	}

	@Override
	public boolean isQuickFixable() throws AssertionFailedException {
		return RulesEditorQuickFixProcessor.hasCorrections(this);
	}

	@Override
	public boolean isQuickFixableStateSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setQuickFixable(boolean state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getProblemCode() {
		return fProblemContext.getProblemCode();
	}

	@Override
	public IProblemContext getProblemContext() {
		return fProblemContext;
	}

}
