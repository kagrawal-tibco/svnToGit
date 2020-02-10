package com.tibco.cep.bpmn.ui.menu;

import static com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils.editGraph;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BOUNDARY_EVENT;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BOUNDARY_EVENT_ERROR;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BOUNDARY_EVENT_MESSAGE;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BOUNDARY_EVENT_SIGNAL;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BREAKPOINT_END;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BREAKPOINT_PROPERTIES;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BREAKPOINT_START;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BREAKPOINT_START_ONLY;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_COPY;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_CREATE_SUBPROCESS;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_CUT;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_DELETE;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_EXPORT_IMAGE;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_PASTE;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_PRINT_PREVIEW;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_PRINT_SETUP;
import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_SHOW_PROPERTIES;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.bpmn.debug.ProcessBreakpointPropertiesAction;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractNodeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.FlowElementInsertCommand;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.SubProcessNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnCreateNodeTool;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnSelectTool;
import com.tibco.cep.bpmn.ui.wizards.SubProcessWizard;
import com.tibco.cep.diagramming.dialog.SaveAsImageDialog;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.menu.popup.AbstractDiagramMenuHandler;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.application.swing.export.TSEPrintPreviewWindow;
import com.tomsawyer.application.swing.export.TSEPrintSetupDialog;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.interactive.command.editing.TSEInsertNodeCommand;
import com.tomsawyer.interactive.swing.TSSwingCanvas;

/**
 * 
 * @author sasahoo
 *
 */
public class BPMNDiagramMenuHandler extends AbstractDiagramMenuHandler {
	
	private BpmnDiagramManager bpmnGraphDiagramManager = null;

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String comandId = e.getActionCommand();
		
		if (getManager() instanceof BpmnDiagramManager) {
			bpmnGraphDiagramManager = (BpmnDiagramManager)getManager();
		}
		if (comandId.equals(PROCESS_CUT)) {
			bpmnGraphDiagramManager.setCutGraph(true);
			editGraph(EDIT_TYPES.CUT, bpmnGraphDiagramManager.getEditor().getEditorSite(), bpmnGraphDiagramManager);
		} else  if (comandId.equals(PROCESS_COPY)) {
			bpmnGraphDiagramManager.setCopyGraph(true);
			editGraph(EDIT_TYPES.COPY, bpmnGraphDiagramManager.getEditor().getEditorSite(), bpmnGraphDiagramManager);
		} else  if (comandId.equals(PROCESS_PASTE)) {
			bpmnGraphDiagramManager.setPasteGraph(true);
			editGraph(EDIT_TYPES.PASTE, bpmnGraphDiagramManager.getEditor().getEditorSite(), bpmnGraphDiagramManager);
		} else  if (comandId.equals(PROCESS_DELETE)) {
			editGraph(EDIT_TYPES.DELETE, bpmnGraphDiagramManager.getEditor().getEditorSite(), bpmnGraphDiagramManager);
		} else  if (comandId.equals(PROCESS_EXPORT_IMAGE)) {
			if (getSwingCanvas() != null) {
				SaveAsImageDialog saveAsImageDialog = new SaveAsImageDialog(
						new JFrame("ts"),BpmnMessages.getString("bpmnDiagramMenuHandler_saveAsImageDialog_label"), getSwingCanvas());
				saveAsImageDialog.setGraphPath(System.getProperty("user.home") +
						File.separator +"Untitled1");
				saveAsImageDialog.setVisible(true);
			}
		} else  if (comandId.equals(PROCESS_PRINT_SETUP)) {
			if (getSwingCanvas() != null) {
				TSEPrintSetupDialog printSetup = new TSEPrintSetupDialog(
						new JFrame("ts"), BpmnMessages.getString("bpmnDiagramMenuHandler_printSetup_label"), getSwingCanvas());
				printSetup.setVisible(true);
			}
		} else  if (comandId.equals(PROCESS_PRINT_PREVIEW)) {
			if (getSwingCanvas() != null) {
				JDialog printPreviewWindow = new TSEPrintPreviewWindow(
						new JFrame("ts"), BpmnMessages.getString("bpmnDiagramMenuHandler_printPreview_label"), getSwingCanvas());
				printPreviewWindow.setSize(800, 600);
				printPreviewWindow.setVisible(true);
			}
		} else  if (comandId.equals(PROCESS_BREAKPOINT_PROPERTIES)) {
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				@Override
				public void run() {
					ProcessBreakpointPropertiesAction prBrkAction =  new ProcessBreakpointPropertiesAction(bpmnGraphDiagramManager.getEditor());
					prBrkAction.run();
				}
			}, false);
			
		} else  if (comandId.equals(PROCESS_CREATE_SUBPROCESS)) {
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				@Override
				public void run() {
					SubProcessWizard wizard = new SubProcessWizard(bpmnGraphDiagramManager.getProject(), null);
					WizardDialog dialog = new WizardDialog(bpmnGraphDiagramManager.getEditor().getSite().getShell(), wizard) {
						@Override
						protected void createButtonsForButtonBar(
								Composite parent) {
							super.createButtonsForButtonBar(parent);
							Button finishButton = getButton(IDialogConstants.FINISH_ID);
							finishButton.setText(IDialogConstants.OK_LABEL);
						}
					};
					dialog.setMinimumPageSize(700, 500);
					try {
						dialog.create();
					} catch (RuntimeException e) {
						if (e.getCause() instanceof InterruptedException) {
							return;
						}
					}
					int status = dialog.open();
					if(status == WizardDialog.OK) {
						//Replace with value from wizard
						createSubProcessNode(wizard.getSubProcessName());
					}
				}
			}, false);
		} else  if (comandId.equals(PROCESS_BOUNDARY_EVENT)) {
			
			//TODO
		}  else  if (comandId.equals(PROCESS_BOUNDARY_EVENT_MESSAGE)) {
			createBoundaryEvent(BpmnMetaModelConstants.MESSAGE_EVENT_DEFINITION);
		}  else  if (comandId.equals(PROCESS_BOUNDARY_EVENT_ERROR)) {
			createBoundaryEvent(BpmnMetaModelConstants.ERROR_EVENT_DEFINITION);
		}  else  if (comandId.equals(PROCESS_BOUNDARY_EVENT_SIGNAL)) {
			createBoundaryEvent(BpmnMetaModelConstants.SIGNAL_EVENT_DEFINITION);
		} else  if (comandId.equals(PROCESS_SHOW_PROPERTIES)) {
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				@Override
				public void run() {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(IPageLayout.ID_PROP_SHEET);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}, false);
		} else  if (comandId.equals(PROCESS_BREAKPOINT_START) || comandId.equals(PROCESS_BREAKPOINT_START_ONLY)) {
			BpmnSelectTool selectTool = (BpmnSelectTool)bpmnGraphDiagramManager.getSelectTool();
			if (hitObject instanceof TSENode) {
				TSENode node = (TSENode)hitObject;
				if (hitObject != null) {
					EObject userObject = (EObject) node.getUserObject();
					EObjectWrapper<EClass, EObject> userObjectWrap = EObjectWrapper.wrap(userObject);
					selectTool.onAddBreakpoint(hitObject, userObjectWrap, true, false);
				}
			}
		} else  if (comandId.equals(PROCESS_BREAKPOINT_END)) {
			BpmnSelectTool selectTool = (BpmnSelectTool)bpmnGraphDiagramManager.getSelectTool();
			if (hitObject instanceof TSENode) {
				TSENode node = (TSENode)hitObject;
				if (node != null) {
					EObject userObject = (EObject) node.getUserObject();
					EObjectWrapper<EClass, EObject> userObjectWrap = EObjectWrapper.wrap(userObject);
					selectTool.onAddBreakpoint(hitObject, userObjectWrap, false, false);
				}
			}
		}
	}
	
	private void createBoundaryEvent(ExpandedName bEventType){
		List<TSENode> selectedNodes = bpmnGraphDiagramManager.getSelectedNodes();
		if(selectedNodes.size() == 1 ){
			TSENode tseNode = selectedNodes.get(0);
			if(tseNode != null){
				Map<String, Object> updateList = new HashMap<String, Object>();
				EClass boundaryEventType = BpmnMetaModel.getInstance().getEClass(bEventType);
				updateList.put(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS, boundaryEventType);
				BpmnGraphUtils.fireUpdate(updateList,tseNode ,bpmnGraphDiagramManager);
				bpmnGraphDiagramManager.refreshNode(tseNode);
			}
		}
		
	}

	/**
	 * Create SubProcess Node
	 * @param nodeName
	 */
	private void createSubProcessNode(final String nodeName) {
		SwingUtilities.invokeLater(new Runnable() {

			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				//Setting up Subprocess Node Builder for the Graph 
				TSEGraphManager graphManager = ((DiagramManager) bpmnGraphDiagramManager).getGraphManager();
				BpmnGraphUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager)bpmnGraphDiagramManager.getLayoutManager());
				//EClass modelType = BpmnMetaModel.getInstance().getEClass(BpmnMetaModelConstants.SUB_PROCESS);
				SubProcessNodeUIFactory graphObjFactory = (SubProcessNodeUIFactory) uiFactory.getNodeUIFactory(nodeName, "", "activity.subProcess", BpmnMetaModelConstants.SUB_PROCESS, false);
				TSNodeBuilder nodeBuilder = (TSNodeBuilder) graphObjFactory;
				graphManager.setNodeBuilder(nodeBuilder);
				graphManager.getNodeBuilder().setNodeUI((TSNodeUI) nodeBuilder.getNodeUI());	
				
				//Getting corresponding CommandFactory i.e. SubProcessCommandFactory
				AbstractNodeCommandFactory cf = (AbstractNodeCommandFactory) bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(BpmnModelClass.SUB_PROCESS, null);
				TSEGraph graph = getGraphAtHitPoint();
				
				//Creating Node using BPMNCreateNode Tool at the hit point 
				BpmnCreateNodeTool createNodeTool = (BpmnCreateNodeTool)bpmnGraphDiagramManager.getNodeTool();
				TSENode node = createNodeTool.createNode(graph, point);
				node.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, BpmnModelClass.SUB_PROCESS);
				node.setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, null);
				
				//Use corresponding Insert Command
				IGraphCommand<TSENode> cmd = cf.getCommand(IGraphCommand.COMMAND_INSERT, graph, node);
				if(cmd instanceof FlowElementInsertCommand)
					((FlowElementInsertCommand)cmd).setInsertEmfModelOnly(true);
				bpmnGraphDiagramManager.executeCommand((TSEInsertNodeCommand) cmd.getAdapter(TSEInsertNodeCommand.class));
				bpmnGraphDiagramManager.getModelGraphFactory().buildNodeRegistry();
				bpmnGraphDiagramManager.refreshNode(node);
			}
		});
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.menu.popup.AbstractDiagramMenuHandler#getManager()
	 */
	@Override
	public DiagramManager getManager() {
		return manager;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.menu.popup.AbstractDiagramMenuHandler#setManager(com.tibco.cep.diagramming.drawing.DiagramManager)
	 */
	@Override
	public void setManager(DiagramManager manager) {
		this.manager = manager;
	}

	@Override
	public TSSwingCanvas getSwingCanvas() {
		return canvas;
	}

	@Override
	public void setSwingCanvas(TSSwingCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void setHitPoint(TSConstPoint point) {
		this.point = point;
	}

	@Override
	public TSConstPoint getHitPoint() {
		return point;
	}

	@Override
	public void setGraphAtHitPoint(TSEGraph graph) {
		this.graphAtHitPoint = graph;
		
	}

	@Override
	public TSEGraph getGraphAtHitPoint() {
		return graphAtHitPoint;
	}

	@Override
	public void setHitObject(TSEObject object) {
		this.hitObject = object;
	}
	
}
