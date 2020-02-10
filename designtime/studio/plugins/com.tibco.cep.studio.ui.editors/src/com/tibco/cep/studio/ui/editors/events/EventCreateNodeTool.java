package com.tibco.cep.studio.ui.editors.events;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.EventDiagramManager;
import com.tibco.cep.studio.ui.diagrams.tools.EntityCreateNodeTool;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;
import com.tibco.cep.studio.ui.wizards.IDiagramEntitySelection;
import com.tibco.cep.studio.ui.wizards.NewEventWizard;
import com.tibco.cep.studio.ui.wizards.NewTimeEventWizard;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class EventCreateNodeTool extends EntityCreateNodeTool implements IDiagramEntitySelection{
	
	private Event event;
	private EventDiagramManager eventDiagramManager;
	private String projectName;
	private int status = -1;
	
	/**
	 * @param projectName
	 * @param manager
	 * @param isTimeEvent
	 */
	public EventCreateNodeTool(String projectName, EventDiagramManager manager) {
		super(manager);
		this.eventDiagramManager =  manager;
		this.projectName = projectName;
	}
	
	private EventCreateNodeTool getNodeTool(){
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#supportEntityCreationDialogInvoke()
	 */
	protected boolean supportEntityCreationDialogInvoke(){
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#doEntityCreation()
	 */
	protected boolean doEntityCreation(){
		status = -1;
		try{
			Display.getDefault().syncExec(new Runnable(){
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					if(eventDiagramManager.getSelectedPaletteEntry() == null) return;
					AbstractNewEntityWizard newEntityWizard = null;
					if(eventDiagramManager.getSelectedPaletteEntry().equalsIgnoreCase("Simple Event")){
						newEntityWizard = new NewEventWizard(getNodeTool(), projectName);
					}else{
						newEntityWizard = new NewTimeEventWizard(getNodeTool(), projectName);
					}
					
					newEntityWizard.setOpenEditor(false);
					WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),newEntityWizard) {
						@Override
						protected void createButtonsForButtonBar(Composite parent) {
							super.createButtonsForButtonBar(parent);
							Button finishButton = getButton(IDialogConstants.FINISH_ID);
							finishButton.setText(IDialogConstants.OK_LABEL);
						}
					};
					dialog.create();
					status = dialog.open();
				}});
			if(status == WizardDialog.CANCEL){
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#afterNodeCreation(com.tomsawyer.drawing.geometry.shared.TSConstPoint)
	 */
	protected TSENode createNodeCommand(com.tomsawyer.drawing.geometry.shared.TSConstPoint point){
		if(eventDiagramManager.getSelectedPaletteEntry() == null) return null;
		if(eventDiagramManager.getSelectedPaletteEntry().equalsIgnoreCase("Simple Event")){
			event = IndexUtils.getSimpleEvent(file.getProject().getName(), IndexUtils.getFullPath(file));
		}else{
			event = IndexUtils.getTimeEvent(file.getProject().getName(), IndexUtils.getFullPath(file));
		}
		
		TSENode newNode = super.createNodeCommand(point);
		eventDiagramManager.populateTSNode(newNode, event);
		refresh(eventDiagramManager, eventDiagramManager.getEntityNodes(), eventDiagramManager.getDrawingCanvas());

		return null;
	}
}