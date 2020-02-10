package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractDBFormEditor;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;

public class StateMachineComponentEditor extends AbstractDBFormEditor implements ITabbedPropertySheetPageContributor, IGraphDrawing, IGotoMarker {

	public static final String EDITOR_ID = StateMachineComponentEditor.class.getName();

	private StateMachineComponentPage page;

	private StateMachineComponentEditorPartListener stateMachineComponentEditorPartListener;
	
	
    public IDiagramModelAdapter getDiagramModelAdapter() {
    	return ((DiagramManager) this.getDiagramManager()).getDiagramModelAdapter();
    }

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		try {
			LocalECoreUtils.loadFully(getLocalElement(), true, true);
			getLocalElement().setInternalStatus(InternalStatusEnum.StatusExisting, true);

			if (stateMachineComponentEditorPartListener == null) {
				stateMachineComponentEditorPartListener = new StateMachineComponentEditorPartListener();
			}

			site.getPage().addPartListener(stateMachineComponentEditorPartListener);

		} catch (Exception e) {
			Status status = new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, "could not fully load " + getLocalElement(), e);
			throw new PartInitException(status);
		}
	}

	@Override
	protected void addPages() {
		try {
			page = new StateMachineComponentPage(this, getLocalElement());
			addPage(page);
		} catch (PartInitException e) {
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(
					new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, "could not initialize editor for " + getEditorInput().getName(), e));
			// TODO do something to prevent any changes to the editors
		}
	}

	@Override
	public String getContributorId() {
		return EDITOR_ID;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class) {
			return new TabbedPropertySheetPage(this);
		}
		return super.getAdapter(adapter);
	}

	@Override
	public IDiagramManager getDiagramManager() {
		if (page != null && page.getStateMachinePreviewer() != null) {
			return page.getStateMachinePreviewer().getDiagramManager();
		}
		return null;
	}

	@Override
	public TSSwingCanvas getDrawingCanvas() {
		if (page != null && page.getStateMachinePreviewer() != null) {
			return page.getStateMachinePreviewer().getDrawingCanvas();
		}
		return null;
	}

	@Override
	public TSEGraphManager getGraphManager() {
		if (page != null && page.getStateMachinePreviewer() != null) {
			return page.getStateMachinePreviewer().getGraphManager();
		}
		return null;

	}

	@Override
	public LayoutManager getLayoutManager() {
		if (page != null && page.getStateMachinePreviewer() != null) {
			return page.getStateMachinePreviewer().getLayoutManager();
		}
		return null;
	}

	@Override
	public TSEOverviewComponent getOverviewComponent() {
		if (page != null && page.getStateMachinePreviewer() != null) {
			return page.getStateMachinePreviewer().getOverviewComponent();
		}
		return null;
	}

	@Override
	public PALETTE getPalette() {
		return PALETTE.NONE;
	}

	@Override
	public void setLayoutManager(LayoutManager mgr) {
		if (page != null && page.getStateMachinePreviewer() != null) {
			page.getStateMachinePreviewer().setLayoutManager(mgr);
		}
	}

	@Override
	protected String[] getInterestingElementTypes() {
		return new String[] { BEViewsElementNames.METRIC, BEViewsElementNames.DATA_SOURCE, BEViewsElementNames.TEXT_COMPONENT, BEViewsElementNames.CHART_COMPONENT,
				StatesPackage.eINSTANCE.getStateMachine().getName() };
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.tibco.cep.studio.dashboard.ui.editors.AbstractDBFormEditor#dispose()
	 */
	public void dispose() {
		if (stateMachineComponentEditorPartListener != null) {
			getSite().getPage().removePartListener(stateMachineComponentEditorPartListener);
		}
		super.dispose();
	}

	public String getPerspectiveId() {
		return "com.tibco.cep.diagramming.diagram.perspective";
	}

	/**
	 * @param site
	 */
	public void openPerspective() {
		IPerspectiveDescriptor perspectiveDescriptor = getSite().getPage().getPerspective();
		if (getPerspectiveId() == null) {
			return;
		}
		if (perspectiveDescriptor.getId().equals("org.eclipse.debug.ui.DebugPerspective")) {
			return;
		}
		if (!perspectiveDescriptor.getId().equals(getPerspectiveId())) {
			try {
				if (true) {
					getSite().getWorkbenchWindow().getWorkbench().showPerspective(getPerspectiveId(), getSite().getWorkbenchWindow());
				}
			} catch (WorkbenchException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void gotoMarker(IMarker marker) {
		String location = marker.getAttribute(IMarker.LOCATION, null);
		if (location != null && location.trim().length() != 0) {
			if (marker.getAttribute(IResourceValidator.MARKER_STATE_GRAPH_PATH_ATTRIBUTE, null) == null) {
				StateMachine stateMachine = page.getStateMachinePreviewer().getStateMachine();
				location = stateMachine.getFullPath()+ "." + IndexUtils.getFileExtension(stateMachine) + location;
				try {
					marker.setAttribute(IResourceValidator.MARKER_STATE_GRAPH_PATH_ATTRIBUTE, location);
				} catch (CoreException e) {
					IStatus status = new MultiStatus(DashboardStateMachineComponentPlugin.PLUGIN_ID, IStatus.WARNING, new IStatus[] { e.getStatus() }, "could not add navigation information", e);
					DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				}
			}
			page.getStateMachinePreviewer().gotoMarker(marker);
		}
	}
	
	public boolean isDisplayFullName() {
		return false;
	}
	
	public boolean isfillTaskIcons() {
		return false;
	}
}