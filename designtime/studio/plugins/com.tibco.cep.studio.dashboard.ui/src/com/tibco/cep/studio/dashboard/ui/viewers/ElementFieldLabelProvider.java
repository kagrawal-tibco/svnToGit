package com.tibco.cep.studio.dashboard.ui.viewers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;

/**
 * @
 *  
 */
public class ElementFieldLabelProvider implements ITableLabelProvider {

    /**
     *  
     */
    public ElementFieldLabelProvider() {
        super();
    }

    public Image getColumnImage(Object element, int columnIndex) {
        if (0 == columnIndex) {
            return ((ContributableImageRegistry)DashboardUIPlugin.getInstance().getImageRegistry()).get((LocalAttribute)element);
        }
        return null;
    }

    public String getColumnText(Object element, int columnIndex) {

        if (element instanceof LocalAttribute) {
            LocalAttribute localElement = (LocalAttribute) element;
            try {
                switch (columnIndex) {
                case 0:
                    return localElement.getName();
                case 1:
                    return localElement.getDataType();
                case 2:
                    return localElement.getDescription();
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

}
