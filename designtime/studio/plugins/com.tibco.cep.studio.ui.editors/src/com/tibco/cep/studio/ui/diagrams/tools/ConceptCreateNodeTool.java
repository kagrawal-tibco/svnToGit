package com.tibco.cep.studio.ui.diagrams.tools;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.ConceptDiagramManager;
import com.tibco.cep.studio.ui.wizards.NewConceptWizard;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEInsertNodeCommand;

public class ConceptCreateNodeTool extends EntityCreateNodeTool {

	@Override
	protected TSEInsertNodeCommand newInsertNodeCommand(TSEGraph arg0,
			TSENode arg1) {
		// TODO Auto-generated method stub
		return super.newInsertNodeCommand(arg0, arg1);
	}

	private Concept concept;
	private ConceptDiagramManager conceptDiagramManager;
	private String projectName;
	private int status = -1;
	
	/**
	 * @param projectName
	 * @param manager
	 */
	public ConceptCreateNodeTool(String projectName, ConceptDiagramManager manager) {
		super(manager);
		this.conceptDiagramManager =  manager;
		this.projectName = projectName;
	}
	
	private ConceptCreateNodeTool getNodeTool(){
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
					NewConceptWizard conceptWizard = new NewConceptWizard(getNodeTool(), projectName);
					conceptWizard.setOpenEditor(false);
					WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),conceptWizard) {
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
		
		concept = IndexUtils.getConcept(file.getProject().getName(), IndexUtils.getFullPath(file));
		
		TSENode newNode = super.createNodeCommand(point);
		conceptDiagramManager.populateTSNode(newNode, concept);
		refresh(conceptDiagramManager, conceptDiagramManager.getEntityNodes(), conceptDiagramManager.getDrawingCanvas());

		return null;
	}

}