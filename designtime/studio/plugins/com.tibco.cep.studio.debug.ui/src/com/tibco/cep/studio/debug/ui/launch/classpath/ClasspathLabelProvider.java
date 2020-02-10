package com.tibco.cep.studio.debug.ui.launch.classpath;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class ClasspathLabelProvider extends LabelProvider {

	public ClasspathLabelProvider() {
		super();
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof DesignerProject){
			return StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
		} else if (element instanceof LIBRARY_PATH_TYPE) {
			return StudioUIPlugin.getDefault().getImage("icons/library_obj.gif");
		} else if (element instanceof JavaLibPath){
			return StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
		} else if (element instanceof BuildPathEntry){
			return StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof DesignerProject){
			DesignerProject dp = (DesignerProject)element;
			String archivePath = dp.getArchivePath();				
			return archivePath;
		} else if (element instanceof BuildPathEntry) {
			BuildPathEntry bpe = ((BuildPathEntry) element);
			String path = bpe.getPath(bpe.isVar());
			return path;
		} else if (element instanceof JavaLibPath) {
			return ((JavaLibPath) element).getLibPath();
		} else if ( element instanceof LIBRARY_PATH_TYPE) {
			LIBRARY_PATH_TYPE type = (LIBRARY_PATH_TYPE) element;
			return type.getLiteral();
		} 
		return null;
	}
}
