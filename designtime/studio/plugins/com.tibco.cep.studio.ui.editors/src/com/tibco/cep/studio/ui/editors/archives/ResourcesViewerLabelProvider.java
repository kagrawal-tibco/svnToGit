package com.tibco.cep.studio.ui.editors.archives;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

/**
 * 
 * @author sasahoo
 *
 */
public class ResourcesViewerLabelProvider  extends LabelProvider implements	ITableLabelProvider {
	
	private ILabelProvider labelProvider;

	/**
	 * Constructor for CommonNavigatorTitleProvider.
	 */
	public ResourcesViewerLabelProvider(TableViewer viewer) {
		NavigatorContentServiceFactory fact = NavigatorContentServiceFactory.INSTANCE;
		INavigatorContentService navigatorService = fact.createContentService("com.tibco.cep.studio.projectexplorer.view", viewer);
		if (navigatorService != null) {
			labelProvider = navigatorService.createCommonLabelProvider();
		}
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object obj, int index) {
		if(obj instanceof IResource){
			return ((IResource)obj).getProjectRelativePath().toPortableString();
		}
		return obj.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object object, int index) {
		return labelProvider != null ? labelProvider.getImage(object) : null;
	}
}