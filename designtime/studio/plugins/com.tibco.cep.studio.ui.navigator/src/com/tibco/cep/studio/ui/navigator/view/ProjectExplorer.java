package com.tibco.cep.studio.ui.navigator.view;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openEditor;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.help.IContextProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.core.index.update.StudioModelChangedEvent;
import com.tibco.cep.studio.core.index.update.StudioProjectDelta;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.AbstractNavigatorNode;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.navigator.INavigatorHelpContextIds;
import com.tibco.cep.studio.ui.navigator.NavigatorContextProvider;
import com.tibco.cep.studio.ui.navigator.model.ArgumentTransfer;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;
import com.tibco.cep.studio.ui.navigator.model.VirtualRuleFunctionImplementationNavigatorNode;
import com.tibco.cep.studio.ui.navigator.util.ProjectExplorerUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ProjectExplorer extends CommonNavigator 
	implements ITabbedPropertySheetPageContributor, IStudioModelChangedListener {

	public static final String ID = "com.tibco.cep.studio.projectexplorer.view";

	private IContextProvider fContextProvider;
	
	public String getContributorId() {
		return "com.tibco.cep.studio.projectexplorer.view";
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, INavigatorHelpContextIds.DESIGNER_NAVIGATOR);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, INavigatorHelpContextIds.DESIGNER_CHANNEL);
		StudioCorePlugin.getDefault().addDesignerModelChangedListener(this);
		getCommonViewer().getControl().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IWorkbenchPartReference partReference = activePage.getActivePartReference();
					if(partReference != null && !ID.equals(partReference.getId())) {
						activePage.activate(ProjectExplorer.this);
					}
				} catch (Exception ex) {
					// ignore
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		//Cache the selection from Project Explorer, for Argument Drag n Drop On Decision Table 
		getCommonViewer().addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(getCommonViewer().getSelection() instanceof IStructuredSelection){
					ArgumentTransfer.getInstance().setSelection((IStructuredSelection)getCommonViewer().getSelection());
				}
			}});
		ViewerFilter[] projectViewerFilters = ProjectExplorerUtils.getProjectViewerFilters();
		for (ViewerFilter viewerFilter : projectViewerFilters) {
			getCommonViewer().addFilter(viewerFilter);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		if (IContextProvider.class.equals(key)) {
			if (fContextProvider == null) {
				fContextProvider = new NavigatorContextProvider(this);
			}
			return fContextProvider;
		}
		return super.getAdapter(key);
	}

	@Override
	public void dispose() {
		ViewerFilter[] projectViewerFilters = ProjectExplorerUtils.getProjectViewerFilters();
		for (ViewerFilter viewerFilter : projectViewerFilters) {
			getCommonViewer().removeFilter(viewerFilter);
		}

		super.dispose();
		StudioCorePlugin.getDefault().removeDesignerModelChangedListener(this);
	}

	public void modelChanged(final StudioModelChangedEvent event) {
		Display.getDefault().asyncExec(new Runnable() {
		
			public void run() {
				if (!event.getDelta().hasReferenceChanges()) {
					return;
				}
				// wait until all update operations are finished
				IndexUtils.waitForUpdateAll();
				// the project libraries have changed, perform the refresh
				List<StudioProjectDelta> changedProjects = event.getDelta().getChangedProjects();
				for (StudioProjectDelta delta : changedProjects) {
					if(delta.getChangedProject() == null) continue;
					String name = delta.getChangedProject().getName();
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
					getNavigatorActionService();
					Object adapter = getAdapter(CommonViewer.class);
					if (adapter != null) {
						((CommonViewer)adapter).refresh(project, true);
					}
				}
			}
		});
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonNavigator#handleDoubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	@Override
	protected void handleDoubleClick(DoubleClickEvent anEvent) {
		IAction openHandler = getViewSite().getActionBars().getGlobalActionHandler(ICommonActionConstants.OPEN);
		if(openHandler == null) {
			IStructuredSelection selection = (IStructuredSelection) anEvent.getSelection();
			Object element = selection.getFirstElement();
			openElement(element); 
		}
	}

	private void openElement(Object object) {
		if (object instanceof AbstractNavigatorNode) {
			openEditorNavigationItem((AbstractNavigatorNode)object);
		} else {
			StudioUIUtils.openElement(object);
		}
		if (object instanceof SharedElementRootNode 
				|| object instanceof DesignerProject 
				|| object instanceof Folder 
				|| object instanceof IFolder 
				|| object instanceof IProject) {
			
			if(getCommonViewer().getExpandedState(object)){
				getCommonViewer().setExpandedState(object, false);	
			}else{
				getCommonViewer().setExpandedState(object, true);
			}
		}
	}
	
	/**
	 * Opening Editor for selection of Destination {@linkplain ChannelDestinationNode}
	 * and Decision Table {@linkplain VirtualRuleFunctionImplementationNavigatorNode}
	 * @param element {@link AbstractNavigatorNode}}
	 */
	public static void openEditorNavigationItem(AbstractNavigatorNode element) {
		if (element instanceof ChannelDestinationNode) {
			ChannelDestinationNode  channelDestinationNode = (ChannelDestinationNode)element;
			Destination destination = (Destination)channelDestinationNode.getEntity();
			Channel  channel = destination.getDriverConfig().getChannel();
			StudioUIPlugin.getDefault().setSelectedEntity(destination);
			openEditor(channel, destination);
		} else if (element instanceof VirtualRuleFunctionImplementationNavigatorNode) {
			VirtualRuleFunctionImplementationNavigatorNode node = (VirtualRuleFunctionImplementationNavigatorNode)element;
			DecisionTableElement decisionTableElement = (DecisionTableElement)node.getDecisionTableElement();
			openEditor(decisionTableElement);
		}
	}
}