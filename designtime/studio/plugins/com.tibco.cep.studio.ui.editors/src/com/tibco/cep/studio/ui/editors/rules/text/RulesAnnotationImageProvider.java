package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.texteditor.IAnnotationImageProvider;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

public class RulesAnnotationImageProvider implements IAnnotationImageProvider {

	public RulesAnnotationImageProvider() {
	}
	
	@Override
	public Image getManagedImage(Annotation annotation) {
		if (annotation.isMarkedDeleted()) {
			Image disabledImg = EditorsUIPlugin.getDefault().getImage("icons/error_mark_gray.png");
			if (disabledImg == null) {
				Image image = EditorsUIPlugin.getDefault().getImage("icons/error_mark.png");
				disabledImg = new Image(null, image, SWT.IMAGE_GRAY);
				EditorsUIPlugin.getDefault().getImageRegistry().put("icons/error_mark_gray.png", disabledImg);
			}
			return disabledImg;
		}
		return EditorsUIPlugin.getDefault().getImage("icons/error_mark.png");
	}

	@Override
	public String getImageDescriptorId(Annotation annotation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageDescriptor getImageDescriptor(String imageDescritporId) {
		// TODO Auto-generated method stub
		return null;
	}

}
