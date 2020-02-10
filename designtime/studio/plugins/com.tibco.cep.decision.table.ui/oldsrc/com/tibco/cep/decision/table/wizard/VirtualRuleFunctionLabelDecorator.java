package com.tibco.cep.decision.table.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class VirtualRuleFunctionLabelDecorator implements ILabelDecorator{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse.swt.graphics.Image, java.lang.Object)
	 */
	@Override
	public Image decorateImage(Image image, Object element) {
		if(element != null && element instanceof IFile) {
			IFile file = (IFile)element;
			if(StudioResourceUtils.isVirtual(file)) {
				return new DecorationOverlayIcon(image, 
						StudioUIPlugin.getImageDescriptor("icons/virtualrulefunction_overlay.gif"),IDecoration.TOP_LEFT).createImage();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.String, java.lang.Object)
	 */
	@Override
	public String decorateText(String text, Object element) {
		return text;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

}
