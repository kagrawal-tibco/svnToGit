package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.MarkerAnnotation;

public class RulesAnnotationHover implements IAnnotationHover {

	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
		IAnnotationModel annotationModel = sourceViewer.getAnnotationModel();
		List<String> messages = new ArrayList<String>();
		Iterator iter = annotationModel.getAnnotationIterator();
		try {
			while (iter.hasNext()) {
				Annotation annotation = (Annotation) iter.next();
	            if (annotation instanceof MarkerAnnotation) {
	                IMarker marker = ((MarkerAnnotation) annotation).getMarker();
	                if (marker.getAttribute(IMarker.LINE_NUMBER) != null && 
	                		((Integer) marker.getAttribute(IMarker.LINE_NUMBER))
	                        .intValue() == lineNumber + 1) {
	                    String s = (String) marker.getAttribute(IMarker.MESSAGE);
						if (!messages.contains(s))
	                        messages.add(s);
	                }
	            } else if (annotation instanceof RulesProblemAnnotation) {
	            	Position position = annotationModel.getPosition(annotation);
					((RulesProblemAnnotation)annotation).getText();
				}
			}
		} catch (CoreException e) {
			// TODO: handle exception
		}
		if (messages.size() == 1) {
            return (String) messages.get(0);
        } else if (messages.size() > 1) {
            String display = "There are multiple markers at this line:\n";
            for (int i = 0; i < messages.size(); i++) {
                display += "\t- " + messages.get(i)
                        + "\n";
            }
            return display;
        }
		return null;
	}

}
