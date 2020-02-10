package com.tibco.cep.studio.dashboard.ui.chartcomponent.preview;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.editor.ChartEditor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;

public class ChartPreviewView extends ViewPart {

	private ChartPreviewForm chartPreviewForm;

	private ChartPreviewController chartPreviewController;

	private ChartEditorSelectionListener chartEditorSelectionListener;

	private FullRefreshAction refreshAction;
	
	private IWorkbenchPart currPart;
	
	private ChartEditorPartListener chartEditorPartListener;

	@Override
	public void createPartControl(Composite parent) {
		// create chart preview widget
		chartPreviewForm = new ChartPreviewForm(null, parent);
		// create chart preview controller
		chartPreviewController = new ChartPreviewController(chartPreviewForm);
		// create selection listener
		chartEditorSelectionListener = new ChartEditorSelectionListener();
		// add it as a listener to site/page
		getSite().getPage().addSelectionListener(chartEditorSelectionListener);
		//create a part listener 
		chartEditorPartListener = new ChartEditorPartListener();
		// add it as a listener to site/activepage 
		getSite().getPage().addPartListener(chartEditorPartListener);
		// init chart preview async
		chartPreviewForm.init();
		//create view actions 
		createActions();
		//add the actions to the tool bar 
		createToolBar();
		//update the bars 
		getViewSite().getActionBars().updateActionBars();
		//find out which editor is active right now 
		IEditorPart activeEditor = getSite().getPage().getActiveEditor();
		if (activeEditor instanceof ChartEditor) {
			//we have a chart editor active 
			currPart = activeEditor;
			//update the view with that editor's preview
			setLocalUnifiedComponent((LocalUnifiedComponent) ((ChartEditor) activeEditor).getLocalElement());
		}		
	}

	private void createActions() {
		refreshAction = new FullRefreshAction();
		refreshAction.setEnabled(false);
	}

	private void createToolBar() {
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
		tbm.add(refreshAction);
	}

	@Override
	public void setFocus() {
		// do nothing
	}

	@Override
	public void dispose() {
		if (chartPreviewController != null) {
			chartPreviewController.dispose();
		}
		if (chartEditorSelectionListener != null) {
			getSite().getPage().removeSelectionListener(chartEditorSelectionListener);
		}
		if (chartEditorPartListener != null) {
			getSite().getPage().removePartListener(chartEditorPartListener);
		}
	}

	private void setLocalUnifiedComponent(LocalUnifiedComponent localUnifiedComponent) {
		if (localUnifiedComponent == null) {
			chartPreviewController.disablePreview(false, "");
		}
		else {
			chartPreviewController.setOriginalElement(localUnifiedComponent);
		}
		refreshAction.setEnabled(localUnifiedComponent != null);
	}

	class ChartEditorSelectionListener implements ISelectionListener {

		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			// optimization to check for selection changes to the same chart editor
			if (currPart == part) {
				return;
			}
			if (part instanceof ChartEditor) {
				// we have a new chart editor
				// update the preview based on the selection from the new chart editor
				Object element = ((IStructuredSelection) selection).getFirstElement();
				if (element instanceof LocalUnifiedComponent) {
					setLocalUnifiedComponent((LocalUnifiedComponent) element);
				}
				else {
					setLocalUnifiedComponent(null);
				}
				currPart = part;
			}
		}

	}
	
	class ChartEditorPartListener implements IPartListener {

		@Override
		public void partActivated(IWorkbenchPart part) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
		}

		@Override
		public void partClosed(IWorkbenchPart part) {
			if (part == currPart) {
				setLocalUnifiedComponent(null);
				currPart = null;
			}
		}

		@Override
		public void partDeactivated(IWorkbenchPart part) {
		}

		@Override
		public void partOpened(IWorkbenchPart part) {
		}

	}	
	
	class FullRefreshAction extends Action {
		
		FullRefreshAction() {
			setToolTipText("Refresh With New Data");
			setImageDescriptor(DashboardChartPlugin.getDefault().getImageRegistry().getDescriptor("refresh.gif"));			
		}
		
		@Override
		public void run() {
			chartPreviewController.refreshPreview(true);
		}
	}

}