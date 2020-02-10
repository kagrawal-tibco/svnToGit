package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleSetLabelDecorator implements ILightweightLabelDecorator 
{
	
	public void decorate(Object element, IDecoration decoration) {
		if(element != null && element instanceof IFolder) {
		
			Object[] object = CommonUtil.getResources((IFolder)element);
			int count = 0;
			for(Object obj:object){
				if(obj instanceof IFolder){
					break;
				}
				if(obj instanceof IFile){
					if(((IFile)obj).getFileExtension()!=null){
						if(((IFile)obj).getFileExtension().equals("rule")){
							count++;
						}
					}
				}
			}
		   
			if(count > 0) {
				decoration.addOverlay(StudioUIPlugin.getImageDescriptor("icons/rules_folder_overlay.png"), IDecoration.TOP_RIGHT);
			}
			
		}
	}

	public Image decorateImage(Image image, Object element) {
		if(element != null && element instanceof IFolder) {
		
			Object[] object = CommonUtil.getResources((IFolder)element);
			int count = 0;
			for(Object obj:object){
				if(obj instanceof IFolder){
					break;
				}
				if(obj instanceof IFile){
					if(((IFile)obj).getFileExtension().equals("rule")){
						count++;
					}
				}
			}
		   
			if(count > 0) {
				return StudioUIPlugin.getDefault().getImage("icons/rules_folder.png");
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
