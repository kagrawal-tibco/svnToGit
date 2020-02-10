package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

public class MappingProjectionAnnotation extends ProjectionAnnotation {

	@Override
	public void markExpanded() {
		return; // disallow expansion
	}

	@Override
	public boolean isCollapsed() {
		return true; // always say that it is collapsed
	}

	@Override
	public void paint(GC gc, Canvas canvas, Rectangle rectangle) {
		return;
//		super.paint(gc, canvas, rectangle);
	}

}
