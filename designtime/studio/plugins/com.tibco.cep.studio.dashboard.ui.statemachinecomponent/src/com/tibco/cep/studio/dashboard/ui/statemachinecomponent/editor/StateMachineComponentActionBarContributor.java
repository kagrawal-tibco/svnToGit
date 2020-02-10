package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.actions.DiagramEditorContributor;
import com.tibco.cep.diagramming.actions.DiagramLayoutActions;
import com.tibco.cep.diagramming.actions.DiagramSearchAction;
import com.tibco.cep.diagramming.actions.DiagramZoomComboContributionItem;
import com.tibco.cep.diagramming.actions.FitWindowAction;
import com.tibco.cep.diagramming.actions.IncrementalLayoutAction;
import com.tibco.cep.diagramming.actions.LabelingAction;
import com.tibco.cep.diagramming.actions.RoutingAction;

public class StateMachineComponentActionBarContributor extends DiagramEditorContributor {
	
	private static Action DISABLED_ACTION = new Action() {
		
		@Override
		public boolean isEnabled() {
			return false;
		}
		
	};

	public StateMachineComponentActionBarContributor() {
	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		contributeUtilitiesToolBar(toolBarManager);
		// toolBarManager.add(new PrintSetupAction(getPage()));
		// toolBarManager.add(new PrintPreviewAction(getPage()));
		// toolBarManager.add(new PrintAction(getPage()));
		// toolBarManager.add(new ExportToImageAction(getPage()));
		// toolBarManager.add(new Separator());
		contributeInteractiveToolBar(toolBarManager);
		toolBarManager.add(new Separator());
		toolBarManager.add(new DiagramLayoutActions(getPage()));
		toolBarManager.add(new LabelingAction(getPage()));
		toolBarManager.add(new RoutingAction(getPage()));
		toolBarManager.add(new IncrementalLayoutAction(getPage()));
		toolBarManager.add(new FitWindowAction(getPage()));
		toolBarManager.add(new Separator());
		toolBarManager.add(new DiagramZoomComboContributionItem(getPage()));
		toolBarManager.add(new Separator());
		toolBarManager.add(new DiagramSearchAction(getPage()));
		toolBarManager.add(new Separator());
	}

	@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {
			actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), DISABLED_ACTION);
			actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), DISABLED_ACTION);
			actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), DISABLED_ACTION);
			actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), DISABLED_ACTION);
			actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(), DISABLED_ACTION);
			actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), DISABLED_ACTION);
			actionBars.updateActionBars();
		}
	}

}