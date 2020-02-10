package com.tibco.cep.bpmn.ui.editor;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.diagramming.actions.DiagramLayoutActions;
import com.tibco.cep.diagramming.actions.DiagramSearchAction;
import com.tibco.cep.diagramming.actions.DiagramZoomComboContributionItem;
import com.tibco.cep.diagramming.actions.ExportToImageAction;
import com.tibco.cep.diagramming.actions.FitWindowAction;
import com.tibco.cep.diagramming.actions.IncrementalLayoutAction;
import com.tibco.cep.diagramming.actions.InteractiveZoomAction;
import com.tibco.cep.diagramming.actions.LabelingAction;
import com.tibco.cep.diagramming.actions.LinkNavigatorAction;
import com.tibco.cep.diagramming.actions.MagnifyAction;
import com.tibco.cep.diagramming.actions.PanAction;
import com.tibco.cep.diagramming.actions.PrintAction;
import com.tibco.cep.diagramming.actions.PrintPreviewAction;
import com.tibco.cep.diagramming.actions.PrintSetupAction;
import com.tibco.cep.diagramming.actions.RoutingAction;
import com.tibco.cep.diagramming.actions.SelectAction;
import com.tibco.cep.diagramming.actions.ZoomAction;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;

public class BpmnEditorContributor extends MultiPageEditorActionBarContributor {
	
	private IWorkbenchPage workbenchPage;
	private IEditorPart editor;

	public BpmnEditorContributor() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setActivePage(IEditorPart activeEditor) {
		 workbenchPage = getPage();
		
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		
		if (editorPart != null && editorPart instanceof BpmnEditor) {
//			((DecisionGraphEditor) editorPart).setSaveAction(saveAction);
		}
		if (editor == activeEditor)
			return;

		editor = activeEditor;

		IActionBars actionBars = getActionBars();
		if (actionBars != null) {}	
	}
	
	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(new PrintSetupAction(getPage()));
		toolBarManager.add(new PrintPreviewAction(getPage()));
		toolBarManager.add(new PrintAction(getPage()));
		toolBarManager.add(new ExportToImageAction(getPage()));
		toolBarManager.add(new Separator());
		toolBarManager.add(new SelectAction(getPage()));
		toolBarManager.add(new PanAction(getPage()));
		toolBarManager.add(new ZoomAction(getPage()));
		toolBarManager.add(new MagnifyAction(getPage()));
		toolBarManager.add(new InteractiveZoomAction(getPage()));
		toolBarManager.add(new LinkNavigatorAction(getPage()));
		toolBarManager.add(new FitWindowAction(getPage()));
		toolBarManager.add(new DiagramLayoutActions(getPage()));
		toolBarManager.add(new IncrementalLayoutAction(getPage()));
		toolBarManager.add(new LabelingAction(getPage()));
		toolBarManager.add(new RoutingAction(getPage()));
		toolBarManager.add(new Separator());
		toolBarManager.add(new DiagramZoomComboContributionItem(getPage()));
		toolBarManager.add(new Separator());
		toolBarManager.add(new DiagramSearchAction(getPage()));
		toolBarManager.add(new Separator());
	}
	
	@Override
	public void setActiveEditor(IEditorPart editorPart) {
		super.setActiveEditor(editorPart);

		if (editor == editorPart)
			return;

		editor = editorPart;
		
		//getting all the editor artifacts.....
		try{
			if(editor instanceof BpmnEditor) {
				BpmnEditor gEditor = (BpmnEditor) editor;
				BpmnDiagramManager diagramManager =  gEditor.getBpmnGraphDiagramManager();
				diagramManager.waitForInitComplete();
				if (!diagramManager.isInitialized()) {
					return;
				}
				TSEGraphManager manager =diagramManager.getGraphManager();
				DrawingCanvas canvas = diagramManager.getDrawingCanvas();
				BpmnLayoutManager layoutManager = (BpmnLayoutManager) diagramManager.getLayoutManager();
				IActionBars actionBars = getActionBars();
				if (actionBars != null) {
					actionBars.setGlobalActionHandler(ActionFactory.SAVE.getId(),gEditor.getActionHandler(ActionFactory.SAVE.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.SAVE_AS.getId(),gEditor.getActionHandler(ActionFactory.SAVE_AS.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.SAVE_ALL.getId(),gEditor.getActionHandler(ActionFactory.SAVE_ALL.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),gEditor.getActionHandler(ActionFactory.COPY.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),gEditor.getActionHandler(ActionFactory.PASTE.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),gEditor.getActionHandler(ActionFactory.SELECT_ALL.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),gEditor.getActionHandler(ActionFactory.CUT.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),gEditor.getActionHandler(ActionFactory.FIND.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),gEditor.getActionHandler(ActionFactory.DELETE.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),gEditor.getActionHandler(ActionFactory.UNDO.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),gEditor.getActionHandler(ActionFactory.REDO.getId(),diagramManager,manager,canvas,layoutManager));
					actionBars.updateActionBars();
				}
			}
			
		}catch(Exception e){
			BpmnUIPlugin.log(e);
		}
	}
	
	

}
