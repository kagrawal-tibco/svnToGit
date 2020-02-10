package com.tibco.cep.studio.dashboard.ui.viewers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.dashboard.ui.images.KnownImageKeys;

/**
 * @
 *  
 */
public class ElementCheckBoxLabelProvider implements ITableLabelProvider {

    private TableColumnInfo[] columnInfos;

	/**
     * @param columnInfos 
     *  
     */
    public ElementCheckBoxLabelProvider(TableColumnInfo[] columnInfos) {
        super();
        this.columnInfos = columnInfos;
    }

    public Image getColumnImage(Object element, int columnIndex) {

        if (element instanceof ElementCheckBoxWrapper) {
            ElementCheckBoxWrapper localElementWrapper = (ElementCheckBoxWrapper) element;
            try {
            	TableColumnInfo columnInfo = columnInfos[columnIndex];
            	if (columnInfo.isImageRequired()) {
            		if (columnInfo.getId() == TableColumnInfo.DEFAULT_SELECT) {
                        if (true == localElementWrapper.isDefaultChecked()) {
                            return DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_RADIO_TRUE);
                        }
                        return DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_RADIO_FALSE);
            		}
            		else if (columnInfo.getId() == TableColumnInfo.SELECT) {
                        if (true == localElementWrapper.isChecked()) {
                            return DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_CHECKBOX_TRUE);
                        }
                        return DashboardUIPlugin.getInstance().getImageRegistry().get(KnownImageKeys.IMG_KEY_CHECKBOX_FALSE);
            		}
            		else if (columnInfo.getId() == TableColumnInfo.NAME) {
                        return ((ContributableImageRegistry)DashboardUIPlugin.getInstance().getImageRegistry()).get(localElementWrapper.getLocalElement());
            		}
            		else if (columnInfo.getId() == TableColumnInfo.PARENT_NAME) {
                        return ((ContributableImageRegistry)DashboardUIPlugin.getInstance().getImageRegistry()).get(localElementWrapper.getLocalElement().getParent());
            		}
            	}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getColumnText(Object element, int columnIndex) {

        if (element instanceof ElementCheckBoxWrapper) {
            ElementCheckBoxWrapper localElementWrapper = (ElementCheckBoxWrapper) element;
            try {
            	TableColumnInfo columnInfo = columnInfos[columnIndex];
        		if (columnInfo.getId() == TableColumnInfo.DEFAULT_SELECT) {
                    return "";
        		}
        		else if (columnInfo.getId() == TableColumnInfo.SELECT) {
                    return "";
        		}
        		else if (columnInfo.getId() == TableColumnInfo.NAME) {
                    return localElementWrapper.getName();
        		}
        		else if (columnInfo.getId() == TableColumnInfo.FOLDER) {
                    return localElementWrapper.getFolder();
        		}
        		else if (columnInfo.getId() == TableColumnInfo.DISPLAY_NAME) {
                    return localElementWrapper.getDisplayName();
        		}
        		else if (columnInfo.getId() == TableColumnInfo.DESCRIPTION) {
                    return localElementWrapper.getDescription();
        		}
        		else if (columnInfo.getId() == TableColumnInfo.PARENT_NAME) {
                    return localElementWrapper.getLocalElement().getParent().getName();
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
