package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleFunctionLabelDecorator implements ILightweightLabelDecorator 
{

	public void decorate(Object element, IDecoration decoration) {
		if(element != null && element instanceof IFile) {
			IFile file = (IFile) element;
			if(file != null && file.isAccessible() && StudioResourceUtils.isVirtual(file)) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/virtualrulefunction_overlay.gif"), IDecoration.TOP_LEFT);
			}
		}
	}

	public Image decorateImage(Image image, Object element) {
		if(element != null && element instanceof IFile) {
			IFile file = (IFile)element;
			if(file != null && file.isAccessible() && StudioResourceUtils.isVirtual(file)) {
				return StudioUIPlugin.getDefault().getImage("icons/virtualrulefunction.gif");
			}
		}
		return null;
	}

	public String decorateText(String text, Object element) {
		return text;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

}
