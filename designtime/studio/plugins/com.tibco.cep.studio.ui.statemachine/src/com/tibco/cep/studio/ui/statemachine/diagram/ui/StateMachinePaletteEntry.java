package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tibco.cep.diagramming.ui.SubStateNodeUI;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.palette.PaletteEntryUI;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.PalettePresentationUpdater;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.parts.SeparatorPaletteEntry;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineImages;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;

public class StateMachinePaletteEntry extends PaletteEntryUI {

	private IGraphDrawing editor;
	private LayoutManager layoutManager; 

	//	public enum Tool {
	//		START_TOOL, END_TOOL, SIMPLE_STATE_TOOL, COMPOSITE_STATE_TOOL, CONCURRENT_STATE_TOOL, SUB_MACHINE_STATE_TOOL, EDGE_TOOL, NOTE_TOOL
	//	}

//	/**
//	 * 
//	 * @param page
//	 * @param palette
//	 * @return
//	 */
//	public PaletteDrawer createGeneralGroup(final IWorkbenchPage page,
//			Palette palette) {
//
//		PaletteDrawer projectNodesDrawer = new PaletteDrawer(
//				Messages.getString("Palette_General"),
//				Messages.getString("Palette_General"),
//				StateMachineImages.SM_PALETTE_NODES, palette);
//		projectNodesDrawer.setGlobal(false);
//
//		projectNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				projectNodesDrawer, null,
//				Messages.getString("PALLETTE_ENTRY_TOOL_NOTE_TITLE"),
//				Messages.getString("PALLETTE_ENTRY_TOOL_NOTE_TOOLTIP"),
//				StateMachineImages.SM_PALETTE_NOTE, new NoteNodeCreator(),
//				new NoteNodeUI(), false, Tool.NONE));
//		return projectNodesDrawer;
//	}

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createNodeGroup(final IWorkbenchPage page,
			Palette palette) {

		IEditorPart editorPart = page.getActiveEditor();
		if(editorPart instanceof IGraphDrawing){
			editor = (IGraphDrawing)editorPart;
			Object diagramManager = editor.getDiagramManager();
			if((diagramManager != null) && (diagramManager instanceof StateMachineDiagramManager)){
				layoutManager = ((StateMachineDiagramManager) diagramManager).getLayoutManager();
			}
		}
		PaletteDrawer stateMachineNodesDrawer = new PaletteDrawer(
				Messages.getString("SM_PALETTE_DRAWER_NODES_TITLE"),
				Messages.getString("SM_PALETTE_DRAWER_NODES_TOOLTIP"),
				StateMachineImages.SM_PALETTE_NODES, palette, false);
		stateMachineNodesDrawer.setGlobal(false);

		//		stateMachineNodesDrawer.addPaletteEntry(createPaletteEntry(page,
		//				stateMachineNodesDrawer, null,
		//				Messages.SM_PALETTE_START_STATE_TITLE,
		//				Messages.SM_PALETTE_START_STATE_TOOLTIP,
		//				StateMachineImages.SM_PALETTE_START_STATE, new StartStateNodeCreator(),
		//				new InitialNodeUI(), false, Tool.START_TOOL));

		stateMachineNodesDrawer.addPaletteEntry(createPaletteEntry(page,
				stateMachineNodesDrawer, null,
				Messages.getString("SM_PALETTE_END_STATE_TITLE"),
				Messages.getString("SM_PALETTE_END_STATE_TOOLTIP"),
				StateMachineImages.SM_PALETTE_END_STATE, new FinalStateNodeCreator(layoutManager),
				new FinalStateNodeUI(), false, Tool.NONE, false));
		stateMachineNodesDrawer.addPaletteEntry(new SeparatorPaletteEntry(
				stateMachineNodesDrawer));
		stateMachineNodesDrawer.addPaletteEntry(createPaletteEntry(page,
				stateMachineNodesDrawer, null,
				Messages.getString("SM_PALETTE_SIMPLE_STATE_TITLE"),
				Messages.getString("SM_PALETTE_SIMPLE_STATE_TOOLTIP"),
				StateMachineImages.SM_PALETTE_SIMPLE_STATE, new SimpleStateNodeCreator(layoutManager),
				new RoundRectNodeUI(), false, Tool.NONE, false));
		stateMachineNodesDrawer.addPaletteEntry(createPaletteEntry(page,
				stateMachineNodesDrawer, null,
				Messages.getString("SM_PALETTE_COMPOSITE_STATE_TITLE"),
				Messages.getString("SM_PALETTE_COMPOSITE_STATE_TOOLTIP"),
				StateMachineImages.SM_PALETTE_COMPOSITE_STATE,
				new CompositeStateNodeCreator(layoutManager), new CompositeStateNodeGraphUI(), false, Tool.NONE, false));
		stateMachineNodesDrawer.addPaletteEntry(createPaletteEntry(page,
				stateMachineNodesDrawer, null,
				Messages.getString("SM_PALETTE_CONCURRENT_STATE_TITLE"),
				Messages.getString("SM_PALETTE_CONCURRENT_STATE_TOOLTIP"),
				StateMachineImages.SM_PALETTE_CONCURRENT_STATE,
				new ConcurrentStateNodeCreator(layoutManager), new ConcurrentStateNodeUI(), false, Tool.NONE, false));
		stateMachineNodesDrawer.addPaletteEntry(createPaletteEntry(page,
				stateMachineNodesDrawer, null,
				Messages.getString("SM_PALETTE_REGION_TITLE"),
				Messages.getString("SM_PALETTE_REGION_TOOLTIP"),
				StateMachineImages.SM_PALETTE_REGION, 
				new RegionNodeCreator(layoutManager), new RegionGraphNodeUI(), false, Tool.NONE, false));
		stateMachineNodesDrawer.addPaletteEntry(new SeparatorPaletteEntry(
				stateMachineNodesDrawer));
		stateMachineNodesDrawer.addPaletteEntry(createPaletteEntry(page,
				stateMachineNodesDrawer, null,
				Messages.getString("SM_PALETTE_CALL_STATE_TITLE"),
				Messages.getString("SM_PALETTE_CALL_STATE_TOOLTIP"),
				StateMachineImages.SM_PALETTE_CALL_STATE, new SubStateMachineCreator(layoutManager),
				new SubStateNodeUI(), false, Tool.NONE, false));
		stateMachineNodesDrawer.addPaletteEntry(new SeparatorPaletteEntry(
				stateMachineNodesDrawer));
		//		stateMachineNodesDrawer.addPaletteEntry(createPaletteEntry(page,
		//				stateMachineNodesDrawer, null,
		//				Messages.PALLETTE_ENTRY_TOOL_NOTE_TITLE,
		//				Messages.PALLETTE_ENTRY_TOOL_NOTE_TOOLTIP,
		//				StateMachineImages.SM_PALETTE_NOTE, new NoteNodeCreator(),
		//				new NoteNodeUI(), false, Tool.NOTE_TOOL));
		return stateMachineNodesDrawer;
	}

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createLinkGroup(final IWorkbenchPage page,
			Palette palette) {
		PaletteDrawer stateMachineLinkDrawer = new PaletteDrawer(
				Messages.getString("SM_PALETTE_DRAWER_LINK_TITLE"),
				Messages.getString("SM_PALETTE_DRAWER_LINK_TOOLTIP"),
				StateMachineImages.SM_PALETTE_LINK, palette, false);
		stateMachineLinkDrawer.setGlobal(false);
//		stateMachineLinkDrawer.addPaletteEntry(createPaletteEntry(page,
//				stateMachineLinkDrawer, null,
//				Messages.getString("SM_PALETTE_SELECTION_TITLE"),
//				Messages.getString("SM_PALETTE_SELECTION_TOOLTIP"),
//				StateMachineImages.SM_PALETTE_SELECTION_STATE, null, null, false, Tool.SELECTION, false));
		stateMachineLinkDrawer.addPaletteEntry(createPaletteEntry(page,
				stateMachineLinkDrawer, null,
				Messages.getString("SM_PALETTE_TRANSITION_TITLE"),
				Messages.getString("SM_PALETTE_TRANSITION_TOOLTIP"),
				StateMachineImages.SM_PALETTE_TRANSITION_STATE, new TransitionStateEdgeCreator(), new TSECurvedEdgeUI(), true, Tool.NONE, false));
		return stateMachineLinkDrawer;
	}

	/**
	 * 
	 * @param window
	 * @param paletteView
	 * @param rootExpandBar
	 * @param palette
	 * @param listener
	 */
	public void expandBar(final IWorkbenchWindow window,
			PaletteView paletteView, ExpandBar rootExpandBar, Palette palette,
			PalettePresentationUpdater listener) {
		for (PaletteDrawer drawer : palette.getPaletteDrawers()) {
			createDrawer(window, rootExpandBar, drawer, listener);
		}
	}

	/**
	 * 
	 * @param window
	 * @param rootExpandBar
	 * @param drawer
	 * @param listener
	 */
	public void createDrawer(final IWorkbenchWindow window,
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
			createPaletteEntry(window, drawer.isGlobal(), paletteEntry, parent, listener);
		}
		ExpandItem graphItem = new ExpandItem(rootExpandBar, SWT.NONE);
		graphItem.setText(drawer.getTitle());
		if (drawer.getImage() != null) {
			if (drawer.isGlobal())
				graphItem.setImage(StudioUIPlugin.getDefault().getImage(drawer.getImage()));
			else
				graphItem.setImage(StateMachineImages.getImage(drawer.getImage()));
		}
		graphItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		graphItem.setControl(parent);
		graphItem.setExpanded(true);
		graphItem.setData(drawer);

	}

	/**
	 * 
	 * @param window
	 * @param isGlobalPalette
	 * @param paletteEntry
	 * @param parent
	 * @param listener
	 */
	public void createPaletteEntry(final IWorkbenchWindow window,
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
	public CLabel createRectangleButton(final IWorkbenchWindow window,
			boolean isGlobalPalette, Composite buttonParent, String label,
			String toolTipText, String imageName,
			PalettePresentationUpdater listener, boolean custom) {
		Image image = null;
		if (isGlobalPalette) {
			if (imageName != null && StudioUIPlugin.getImageDescriptor(imageName) != null) {
				image = (StudioUIPlugin.getDefault().getImage(imageName));
			}
		} else {
			image = StateMachineImages.getImage(imageName);
		}
		return StudioPaletteUI.createRectangleButton(window, isGlobalPalette,
				buttonParent, label, toolTipText, imageName, listener, image);
	}

	/**
	 * 
	 * @param window
	 * @param paletteView
	 * @param palette
	 * @param rootExpandBar
	 * @param listener
	 */
	public void updateExpandBar(final IWorkbenchWindow window,
			PaletteView paletteView, Palette palette, ExpandBar rootExpandBar,
			PalettePresentationUpdater listener) {
		if (palette == null) {
			return;
		}
		ExpandItem[] items = rootExpandBar.getItems();
		for (ExpandItem item : items) {
			item.dispose();
		}
		Control[] children = rootExpandBar.getChildren();
		for (Control control : children) {
			control.dispose();
		}
		expandBar(window, paletteView, rootExpandBar, palette, listener);
	}
}
