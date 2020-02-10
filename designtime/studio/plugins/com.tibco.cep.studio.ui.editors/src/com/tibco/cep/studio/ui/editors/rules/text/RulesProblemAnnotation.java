package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.rules.IRulesProblem;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

public class RulesProblemAnnotation extends Annotation implements IRulesAnnotation { //, IAnnotationPresentation {
	
	public static final String TYPE_SYNTAX_ERROR = "com.tibco.cep.studio.ui.editors.error.syntax";
	private SyntaxProblemContext fProblemContext;

	public RulesProblemAnnotation(IRulesProblem problem, String type, boolean persistent) {
		super(type, persistent, problem.getErrorMessage());
		this.fProblemContext = new SyntaxProblemContext(problem);
	}

	public int getLayer() {
		return 5;
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
	public int getProblemCode() {
		return fProblemContext.getProblemCode();
	}

	@Override
	public IProblemContext getProblemContext() {
		return fProblemContext;
	}

}
