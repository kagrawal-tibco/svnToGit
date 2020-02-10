package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;

public class LocalElementLabelProvider extends LabelProvider {
	
	private boolean showFullPath;
	
	private boolean showImage;
	
	public LocalElementLabelProvider(boolean showFullPath){
		this(showFullPath, false);
	}
	
	public LocalElementLabelProvider(boolean showFullPath, boolean showImage){
		this.showFullPath = showFullPath;
		this.showImage = showImage;
	}
	
	@Override
	public Image getImage(Object element) {
		if (showImage == false) {
			return null;
		}
		if (element instanceof LocalElement) {
			return ((ContributableImageRegistry) DashboardUIPlugin.getInstance().getImageRegistry()).get((LocalElement)element);
		}
		return null;
	}
	
	@Override
	public String getText(Object element) {
		if (element instanceof LocalElement){
			return getText((LocalElement)element);
		}
		return super.getText(element);
	}

	protected String getText(LocalElement localElement) {
		String str =  localElement.getName();
		if (showFullPath == true){
			String folder = localElement.getFolder();
			StringBuilder sb = new StringBuilder(folder);
			if (folder.endsWith("/") == false) {
				sb.append("/");
			}
			sb.append(str);
			str = sb.toString(); 
		}
		return str;
	}

}