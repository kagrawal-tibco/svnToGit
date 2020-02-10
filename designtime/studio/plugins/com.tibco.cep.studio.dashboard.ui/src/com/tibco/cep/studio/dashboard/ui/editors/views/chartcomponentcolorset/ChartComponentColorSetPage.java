package com.tibco.cep.studio.dashboard.ui.editors.views.chartcomponentcolorset;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSeriesColor;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionTable;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.TableColumnInfo;

/**
 * 
 * @author rgupta
 */
public class ChartComponentColorSetPage extends AbstractEntityEditorPage {

	private ElementCheckBoxSelectionViewer seriesColorViewer;

	public ChartComponentColorSetPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
	}
	
	@Override
	protected void createFieldSection(IManagedForm mform, Composite parent) throws Exception {
		LocalElement localElement = getLocalElement();
		ElementCheckBoxSelectionTable table = new ElementCheckBoxSelectionTable(parent, TableColumnInfo.get(false, false));
		seriesColorViewer = new ElementCheckBoxSelectionViewer(parent, localElement, "SeriesColor", table);
	}
	
	@Override
	protected void doPopulateControl(LocalElement localElement) throws Exception {
		super.doPopulateControl(localElement);
		try {
			//TODO move getLocalElement().getChildren("SeriesColor") to handleOutsideElementChange
			//load series color 
			getLocalElement().getChildren("SeriesColor");
			//update series color 
			seriesColorViewer.setElementChoices(localElement.getRoot().getChildren("SeriesColor"));
			seriesColorViewer.setSelectedElements(localElement.getChildren("SeriesColor"));			
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not initialize "+getEditorInput().getName(),e));
		}		
	}
	
	@Override
	protected void handleOutsideElementChange(int change, LocalElement element) {
		try {
			//we will only process series color add & delete changes
			if (element instanceof LocalSeriesColor && change != IResourceDelta.CHANGED){
				if (change == IResourceDelta.REMOVED){
					getLocalElement().refresh("SeriesColor");
				}				
				seriesColorViewer.setElementChoices(getLocalElement().getRoot().getChildren("SeriesColor"));
				seriesColorViewer.setSelectedElements(getLocalElement().getChildren("SeriesColor"));	
			}
			//we will only be getting be-view updates due to a refactoring on skin/page
			if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true){
				//reset all the properties 
				getLocalElement().refresh(element.getEObject());
				//reset all referenced children
				getLocalElement().refresh("SeriesColor");
				populateControl(getLocalElement());
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
