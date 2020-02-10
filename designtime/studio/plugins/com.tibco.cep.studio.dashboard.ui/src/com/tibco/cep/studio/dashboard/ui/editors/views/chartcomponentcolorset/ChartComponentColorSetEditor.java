package com.tibco.cep.studio.dashboard.ui.editors.views.chartcomponentcolorset;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractDBFormEditor;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 *
 * @author rgupta
 */
public class ChartComponentColorSetEditor extends AbstractDBFormEditor {

    @Override
	protected void addPages() {
        try {
            addPage(new ChartComponentColorSetPage(this, getLocalElement()));
        } catch (PartInitException e) {
			Status status = new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not initialize editor for " + getEditorInput().getName(), e);
			DashboardUIPlugin.getInstance().getLog().log(status);
			// TODO do something to prevent any changes to the editors
        }
    }
    
    @Override
    protected String[] getInterestingElementTypes() {
    	return new String[]{BEViewsElementNames.SERIES_COLOR};
    }

}