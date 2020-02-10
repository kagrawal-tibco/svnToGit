package com.tibco.cep.studio.ui.providers;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.LIBRARY_PATH_TYPE;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

public class LibraryLabelProvider extends LabelProvider {
	private static final String NATIVE_LIBRARY_LOCATION = "project.buildpath.tab.javalib.native.library";
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof DesignerProject){
			return StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
		} else if (element instanceof LIBRARY_PATH_TYPE) {
			return StudioUIPlugin.getDefault().getImage("icons/library_obj.gif");
		} else if (element instanceof BuildPathEntry){
			 BuildPathEntry entry = (BuildPathEntry) element;
			 Image image = StudioUIPlugin.getDefault().getImage("icons/archive_obj.gif");
			 if (entry.isVar()) {
				 image = new DecorationOverlayIcon(image, 
							StudioUIPlugin.getImageDescriptor("icons/vars/envvar_overlay.gif"),IDecoration.TOP_LEFT).createImage();
			 }
			return image;
		} else if (element instanceof NativeLibraryPath) {
			return StudioUIPlugin.getDefault().getImage("icons/native_lib_path_attrib.gif");
		}
		return null;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof DesignerProject){
			DesignerProject dp = (DesignerProject)element;
			String archivePath = dp.getArchivePath();				
			return archivePath;
		} else if (element instanceof BuildPathEntry) {
			
			if (element instanceof NativeLibraryPath) {
				NativeLibraryPath libPath = ((NativeLibraryPath)element);
				String location = libPath.getPath() == null || libPath.getPath().isEmpty()? "(None)": new Path(libPath.getPath()).toPortableString();
				String nativeLibraryStr = Messages.getString(NATIVE_LIBRARY_LOCATION,location);
				return nativeLibraryStr;
			} else {
				 BuildPathEntry entry = (BuildPathEntry) element;
				 if (entry.isVar()) {
					 return entry.getPath(false);
				 }
				return entry.getPath();
			}
		} else if ( element instanceof LIBRARY_PATH_TYPE) {
			LIBRARY_PATH_TYPE type = (LIBRARY_PATH_TYPE) element;
			return type.getLiteral();
		} 
		return null;
	}

}
