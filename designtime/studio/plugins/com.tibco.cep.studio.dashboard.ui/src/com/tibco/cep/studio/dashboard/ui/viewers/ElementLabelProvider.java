package com.tibco.cep.studio.dashboard.ui.viewers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;

/**
 * @
 *  
 */
public class ElementLabelProvider implements ITableLabelProvider {

    /**
     *  
     */
    public ElementLabelProvider() {
        super();
    }

    public Image getColumnImage(Object element, int columnIndex) {

        if (element instanceof LocalElement) {
            LocalElement localElement = (LocalElement) element;
            try {
                switch (columnIndex) {
                case 0:
                    return ((ContributableImageRegistry)DashboardUIPlugin.getInstance().getImageRegistry()).get(localElement);
                case 1:
                case 2:
                case 3:
                    break;
                case 4:
                    return ((ContributableImageRegistry)DashboardUIPlugin.getInstance().getImageRegistry()).get(localElement.getParent());
                default:
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getColumnText(Object element, int columnIndex) {

        if (element instanceof LocalElement) {
            LocalElement localElement = (LocalElement) element;
            try {
                switch (columnIndex) {
                case 0:
                    return localElement.getName();
                case 1:
                    return localElement.getFolder();
                case 2:
                	if (localElement.hasProperty(LocalConfig.PROP_KEY_DISPLAY_NAME)){
                		return localElement.getPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME);
                	}
                    return localElement.getName();
                case 3:
                	String sDescription= removeSpecialCharacters(localElement.getDescription());
                    return  sDescription;
                case 4:
                    if (localElement.getParent() != null) {
                        return localElement.getParent().getName();
                    }
                default:
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "";
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
    /**
     * User       : vijay.n
     * Description : This method is added remove special characters line '\n' '\t'
     * 				 '\r' as some text contains newline or tabs
     * 				 and table viewer shows junk characters
     * @param string string from which special characters need to be removed
     * @return
     */
    private String removeSpecialCharacters(String string){
    	string = string.trim();
    	string = string.replace('\n',' ');    	
    	string = string.replace('\t',' ');
    	string = string.replace('\r',' ');
    	return string;
    }
}
