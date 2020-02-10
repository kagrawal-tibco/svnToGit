package com.tibco.cep.webstudio.client.decisiontable;

import com.smartgwt.client.widgets.events.DragStartEvent;
import com.smartgwt.client.widgets.events.DragStartHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentDataTransfer;
import com.tibco.cep.webstudio.client.decisiontable.model.ParentArgumentResource;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;

/**
 * 
 * @author sasahoo
 *
 */
public class NavigatorTreeDragHandler implements DragStartHandler {
	
	private String argumentPath;

	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.events.DragStartHandler#onDragStart(com.smartgwt.client.widgets.events.DragStartEvent)
	 */
	@Override
	public void onDragStart(DragStartEvent event) {
		if (WebStudio.get().getEditorPanel().getSelectedTab() instanceof RuleTemplateInstanceEditor) {
			event.cancel();
			return;
		}
		if (WebStudio.get().getEditorPanel().getSelectedTab() instanceof DecisionTableEditor) {
			DecisionTableEditor editor = (DecisionTableEditor)WebStudio.get().getEditorPanel().getSelectedTab();
			if (editor.isReadOnly()) {
				event.cancel();
				return;
			}
		}
		
		WebStudioNavigatorGrid navigatorGrid = (WebStudioNavigatorGrid) event.getSource();
		if (navigatorGrid.getSelectedRecords().length > 1 ) {
			event.cancel();
			return;
		}
		
		NavigatorResource selectedResource = (NavigatorResource) navigatorGrid.getSelectedRecord();
		if (selectedResource instanceof ArgumentNavigatorResource) {
			ArgumentNavigatorResource argNavResource = (ArgumentNavigatorResource)selectedResource;
			//Don't allow Parent Navigator Resource record
			if (argNavResource.isParent()) {
				event.cancel();
				return;
			}
			
			argumentPath = null;
			
			DecisionTableNavigatorResource decisionTableNavigatorRes = getDecisionTableParentResource(navigatorGrid, argNavResource);
			String uri = (decisionTableNavigatorRes.parseParentId() + "$" + decisionTableNavigatorRes.getName()).replace("$", "/");

			ArgumentDataTransfer.getInstance().setTransfer(argNavResource.getResource(), uri, argumentPath);
		} else {
			event.cancel();
			return;
		}
	}
	
	/**
	 * @param navigatorGrid
	 * @param argResource
	 * @return
	 */
	private DecisionTableNavigatorResource getDecisionTableParentResource(WebStudioNavigatorGrid navigatorGrid, NavigatorResource argResource) {
		NavigatorResource parentNode = (NavigatorResource) navigatorGrid.getTree().findById(argResource.getParent());
		if (parentNode instanceof DecisionTableNavigatorResource ) {
			ArgumentNavigatorResource argNavRes = (ArgumentNavigatorResource)argResource;
			if (argNavRes.isParent()) {
				ParentArgumentResource parentArgRes = (ParentArgumentResource)argNavRes.getResource();
				argumentPath = parentArgRes.getPath() + parentArgRes.getName();
			}
			return (DecisionTableNavigatorResource)parentNode;
		} else {
			return getDecisionTableParentResource(navigatorGrid, parentNode);
		}
	}
	
	


}
