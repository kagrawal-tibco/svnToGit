package com.tibco.cep.studio.cluster.topology.palette;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.model.NoteNodeCreator;
import com.tibco.cep.diagramming.ui.NoteNodeUI;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.ui.ClusterTopologyEdgeCreator;
import com.tibco.cep.studio.cluster.topology.ui.DeploymentUnitNodeCreator;
import com.tibco.cep.studio.cluster.topology.ui.DeploymentUnitNodeUI;
import com.tibco.cep.studio.cluster.topology.ui.HostNodeCreator;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyImages;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.palette.PaletteEntryUI;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.PalettePresentationUpdater;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.parts.SeparatorPaletteEntry;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEImageNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyPaletteEntry extends PaletteEntryUI {

	private IGraphDrawing editor;
	private LayoutManager layoutManager; 
	/**
	 * 
	 * @param window
	 * @param rootExpandBar
	 * @param drawer
	 * @param listener
	 */
	public void createDrawer(IWorkbenchWindow window,
			ExpandBar rootExpandBar, PaletteDrawer drawer,
			PalettePresentationUpdater listener) {
		Composite parent = new Composite(rootExpandBar, SWT.BORDER);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = layout.horizontalSpacing = layout.marginWidth = layout.marginHeight = 0;
		parent.setLayout(layout);

		GridData data = new GridData(GridData.FILL_BOTH);
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		parent.setLayoutData(data);

		List<PaletteEntry> entries = drawer.getPaletteEntries();
		for (PaletteEntry paletteEntry : entries) {
			createPaletteEntry(window, drawer.isGlobal(), paletteEntry, parent,
					listener);
		}
		ExpandItem graphItem = new ExpandItem(rootExpandBar, SWT.NONE);
		graphItem.setText(drawer.getTitle());
		if (drawer.getImage() != null) {
			if (drawer.isGlobal())
				graphItem.setImage(StudioUIPlugin.getDefault().getImage(drawer.getImage()));
			else
				graphItem.setImage(ClusterTopologyImages.getImage(drawer.getImage()));
		}
		graphItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		graphItem.setControl(parent);
		graphItem.setExpanded(true);
		graphItem.setData(drawer);
		
	}

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createLinkGroup(IWorkbenchPage page, Palette palette) {
		PaletteDrawer graphLinkDrawer = new PaletteDrawer(
				Messages.getString("GRAPH_PALETTE_DRAWER_LINK_TITLE"),
				Messages.getString("GRAPH_PALETTE_DRAWER_LINK_TOOLTIP"),
				ClusterTopologyImages.GRAPH_PALETTE_LINK, palette, false);
		graphLinkDrawer.setGlobal(false);
		graphLinkDrawer.addPaletteEntry(createPaletteEntry(page,
				graphLinkDrawer, null,
				Messages.getString("GRAPH_PALETTE_INHERITANCE_TITLE"),
				Messages.getString("GRAPH_PALETTE_INHERITANCE_TOOLTIP"),
				ClusterTopologyImages.GRAPH_PALETTE_INHERITANCE, new ClusterTopologyEdgeCreator(), new TSECurvedEdgeUI(), true,
				Tool.NONE, false));

		return graphLinkDrawer;
	}

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createNodeGroup(IWorkbenchPage page, Palette palette) {
		
		PaletteDrawer graphNodesDrawer = new PaletteDrawer(
				Messages.getString("GRAPH_PALETTE_DRAWER_NODES_TITLE"),
				Messages.getString("GRAPH_PALETTE_DRAWER_NODES_TOOLTIP"),
				ClusterTopologyImages.GRAPH_PALETTE_NODES, palette, false);
		graphNodesDrawer.setGlobal(false);
		
		graphNodesDrawer.addPaletteEntry(createPaletteEntry(page,
				graphNodesDrawer, null, Messages.getString("PALLETTE_ENTRY_TOOL_NOTE_TITLE"),
				Messages.getString("PALLETTE_ENTRY_TOOL_NOTE_TOOLTIP"),
				ClusterTopologyImages.GRAPH_PALETTE_NOTE, new NoteNodeCreator(),
				new NoteNodeUI(), false, Tool.NONE, false));
		
		return graphNodesDrawer;
	}

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createDeploymentGroup(IWorkbenchPage page, Palette palette) {
		
		IEditorPart editorPart = page.getActiveEditor();
		if(editorPart instanceof IGraphDrawing){
			editor = (IGraphDrawing)editorPart;
			Object diagramManager = editor.getDiagramManager();
			if((diagramManager != null) && (diagramManager instanceof ClusterTopologyDiagramManager)){
				layoutManager = ((ClusterTopologyDiagramManager) diagramManager).getLayoutManager();
			}
		}
		TSEImage.setLoaderClass(this.getClass());
		TSEImageNodeUI imageUI = new TSEImageNodeUI();
		
		PaletteDrawer graphBEDrawer = new PaletteDrawer(
				Messages.getString("GRAPH_PALETTE_DRAWER_BE_TITLE"),
				Messages.getString("GRAPH_PALETTE_DRAWER_BE_TOOLTIP"),
				ClusterTopologyImages.GRAPH_PALETTE_NODES, palette, false);
		graphBEDrawer.setGlobal(false);
		
		imageUI.setImage(new TSEImage(getClass(), "/icons/serverBig.gif"));
		graphBEDrawer.addPaletteEntry(createPaletteEntry(page,
				graphBEDrawer, null, Messages.getString("PALLETTE_ENTRY_TOOL_SERVER_TITLE"),
				Messages.getString("PALLETTE_ENTRY_TOOL_SERVER_TOOLTIP"),
				ClusterTopologyImages.GRAPH_PALETTE_HOST, new HostNodeCreator(),
				(TSENodeUI) imageUI.clone(), false, Tool.NONE, false));

		graphBEDrawer.addPaletteEntry(createPaletteEntry(page,
				graphBEDrawer, null, Messages.getString("PALLETTE_ENTRY_TOOL_DU_TITLE"),
				Messages.getString("PALLETTE_ENTRY_TOOL_DU_TOOLTIP"),
				ClusterTopologyImages.GRAPH_PALETTE_MU,new DeploymentUnitNodeCreator(layoutManager),
						new DeploymentUnitNodeUI(), false, Tool.NONE, false));		
		
//		graphBEDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphBEDrawer, null, Messages.getString("PALLETTE_ENTRY_TOOL_AGENT_TITLE"),
//				Messages.getString("PALLETTE_ENTRY_TOOL_AGENT_TOOLTIP"),
//				ClusterTopologyImages.GRAPH_PALETTE_AGENT, /*new ImageNodeCreator(
//					Messages.getString("PALLETTE_ENTRY_TOOL_AGENT_TITLE"), "agents_48x48.png")*/
//				    new ProcessingUnitNodeCreator(), new TSEImageNodeUI(), false, Tool.NONE));
//		
//		graphBEDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphBEDrawer, null, Messages.getString("PALLETTE_ENTRY_QUERY_AGENT_TITLE"),
//				Messages.getString("PALLETTE_ENTRY_QUERY_AGENT_TOOLTIP"),
//				ClusterTopologyImages.GRAPH_PALETTE_QUERY_AGENT, new QueryAgentNodeCreator()/*new ImageNodeCreator(
//					Messages.getString("PALLETTE_ENTRY_QUERY_AGENT_TITLE"), "queryagent_48x48.png")*/,
//					new TSEImageNodeUI(), false, Tool.NONE));
//		
//		graphBEDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphBEDrawer, null, Messages.getString("PALLETTE_ENTRY_INFERENCE_AGENT_TITLE"),
//				Messages.getString("PALLETTE_ENTRY_INFERENCE_AGENT_TOOLTIP"),
//				ClusterTopologyImages.GRAPH_PALETTE_INFERENCE_AGENT, new InferenceAgentNodeCreator()/* new ImageNodeCreator(
//					Messages.getString("PALLETTE_ENTRY_INFERENCE_AGENT_TITLE"), "interferenceagent_48x48.png")*/,
//					new TSEImageNodeUI(), false, Tool.NONE));
//		
//		graphBEDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphBEDrawer, null, Messages.getString("PALLETTE_ENTRY_DASHBOARD_AGENT_TITLE"),
//				Messages.getString("PALLETTE_ENTRY_DASHBOARD_AGENT_TOOLTIP"),
//				ClusterTopologyImages.GRAPH_PALETTE_DASHBOARD_AGENT, new DashboardAgentNodeCreator()/*new ImageNodeCreator(
//					Messages.getString("PALLETTE_ENTRY_DASHBOARD_AGENT_TITLE"), "dashboardagent_48x48.png")*/,
//					new TSEImageNodeUI(), false, Tool.NONE));
//		
//		graphBEDrawer.addPaletteEntry(new SeparatorPaletteEntry(graphBEDrawer));		
		
//		graphBEDrawer.addPaletteEntry(createPaletteEntry(page,
//				graphBEDrawer, null, Messages.PALLETTE_ENTRY_TOOL_CLUSTER_TITLE,
//				Messages.PALLETTE_ENTRY_TOOL_CLUSTER_TOOLTIP,
//				ClusterTopologyImages.GRAPH_PALETTE_CLUSTER, new BusNodeCreator(),
//					new BusNodeUI(), false, Tool.NONE));
		
		return graphBEDrawer;
	}

	
	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param paletteEntry
	 * @param parent
	 * @param listener
	 */
	public void createPaletteEntry(IWorkbenchWindow window,
			boolean isGlobalPalette, PaletteEntry paletteEntry,
			Composite parent, PalettePresentationUpdater listener) {
		if (paletteEntry instanceof SeparatorPaletteEntry) {
			StudioPaletteUI.createSeparator(parent);
			return;
		}
		CLabel entry = createRectangleButton(window, isGlobalPalette, parent,
				paletteEntry.getTitle(), paletteEntry.getToolTip(),
				paletteEntry.getImage(), listener, false);
		entry.setData(paletteEntry);
		paletteEntry.setControl(entry);
	}

	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param buttonParent
	 * @param label
	 * @param toolTipText
	 * @param imageName
	 * @param listener
	 * @return
	 */
	public CLabel createRectangleButton(IWorkbenchWindow window,
			boolean isGlobalPalette, Composite buttonParent, String label,
			String toolTipText, String imageName,
			PalettePresentationUpdater listener, boolean custom) {
			Image image = null;
		if (isGlobalPalette) {
			if (imageName != null && StudioUIPlugin.getImageDescriptor(imageName) != null) {
				image = (StudioUIPlugin.getDefault().getImage(imageName));
			}
		} else {
			image = ClusterTopologyImages.getImage(imageName);
		}
		return StudioPaletteUI.createRectangleButton(window, isGlobalPalette,
				buttonParent, label, toolTipText, imageName, listener, image);
	}

	/**
	 * 
	 * @param window
	 * @param paletteView
	 * @param rootExpandBar
	 * @param palette
	 * @param listener
	 */
	public void expandBar(IWorkbenchWindow window, PaletteView paletteView,
			ExpandBar rootExpandBar, Palette palette,
			PalettePresentationUpdater listener) {
		for (PaletteDrawer drawer : palette.getPaletteDrawers()) {
			createDrawer(window, rootExpandBar, drawer, listener);
		}	
	}
	
}
