package com.tibco.cep.bpmn.debug;

import org.eclipse.core.resources.IMarker;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.texteditor.IAnnotationImageProvider;
import org.eclipse.ui.texteditor.MarkerAnnotation;

public class ProcessBreakpointImageProvider implements IAnnotationImageProvider {

	public ProcessBreakpointImageProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.IAnnotationImageProvider#getManagedImage(org.eclipse.jface.text.source.Annotation)
	 */
	public Image getManagedImage(Annotation annotation) {
		if (annotation instanceof MarkerAnnotation) {
			IMarker marker = ((MarkerAnnotation)annotation).getMarker();
			if (marker != null) {
				IBreakpoint breakpoint = DebugPlugin.getDefault().getBreakpointManager().getBreakpoint(marker);
				if (breakpoint != null) {
//					return BpmnUIPlugin.getModelPresentation().getImage(breakpoint);
					return getImage(breakpoint);
				}
			}
		}
		return null;
	}
	private Image getImage(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.IAnnotationImageProvider#getImageDescriptorId(org.eclipse.jface.text.source.Annotation)
	 */
	public String getImageDescriptorId(Annotation annotation) {
		return null;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.IAnnotationImageProvider#getImageDescriptor(java.lang.String)
	 */
	public ImageDescriptor getImageDescriptor(String imageDescritporId) {
		return null;
	}

}
