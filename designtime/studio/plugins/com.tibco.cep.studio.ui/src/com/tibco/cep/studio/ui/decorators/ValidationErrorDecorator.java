package com.tibco.cep.studio.ui.decorators;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class ValidationErrorDecorator implements ILightweightLabelDecorator {

	private String errorIconPath = "icons/error_overlay.gif"; //NON-NLS-1
	private String warningIconPath = "icons/warning_overlay.gif"; //NON-NLS-1

	private ImageDescriptor descriptor;
	
	public void decorate(Object element, IDecoration decoration) {
		IResource resource = (IResource) element;
		try {
			if (!resource.isAccessible()) {
				return;
			}
			IMarker[] markers = resource.findMarkers(IResourceValidator.VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE);
			boolean isError = false;
			// determine whether to overlay the error or warning icon
			for (int i=0; i<markers.length; i++) {
				if( markers[i] != null && markers[i].getAttribute(IMarker.SEVERITY) != null){
					if ((Integer) markers[i].getAttribute(IMarker.SEVERITY) == IMarker.SEVERITY_ERROR) {
						isError = true;
						break;
					}
				}
			}
			if (markers.length > 0) {
				if (isError) {
					descriptor = StudioUIPlugin.getImageDescriptor(errorIconPath);			
				} else {
					descriptor = StudioUIPlugin.getImageDescriptor(warningIconPath);			
				}
				decoration.addOverlay(descriptor, IDecoration.BOTTOM_LEFT);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}
	
}