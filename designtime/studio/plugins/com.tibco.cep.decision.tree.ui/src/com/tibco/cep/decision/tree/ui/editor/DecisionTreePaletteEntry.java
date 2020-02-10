package com.tibco.cep.decision.tree.ui.editor;

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

import com.tibco.cep.decision.tree.ui.nodeactions.AssignmentNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.BooleanConditionNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.BreakNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.CallRFNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.CallTableNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.CallTreeNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.DescriptionNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.EndNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.StartNodeCreator;
import com.tibco.cep.decision.tree.ui.nodeactions.TransitionEdgeCreator;
import com.tibco.cep.decision.tree.ui.nodes.AssignmentNodeUI;
import com.tibco.cep.decision.tree.ui.nodes.BooleanConditionNodeUI;
import com.tibco.cep.decision.tree.ui.nodes.BreakNodeUI;
import com.tibco.cep.decision.tree.ui.nodes.CallRFNodeUI;
import com.tibco.cep.decision.tree.ui.nodes.CallTableNodeUI;
import com.tibco.cep.decision.tree.ui.nodes.CallTreeNodeUI;
import com.tibco.cep.decision.tree.ui.nodes.DescriptionNodeUI;
import com.tibco.cep.decision.tree.ui.nodes.EndNodeUI;
import com.tibco.cep.decision.tree.ui.nodes.StartNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeImages;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.palette.PaletteEntryUI;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.PalettePresentationUpdater;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.parts.SeparatorPaletteEntry;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreePaletteEntry extends PaletteEntryUI {

	private IGraphDrawing editor;
	private LayoutManager layoutManager = null;
	
	public DecisionTreePaletteEntry(final IWorkbenchPage page) { 
		IEditorPart editorPart = page.getActiveEditor();
		if (editorPart instanceof IGraphDrawing) {
			editor = (IGraphDrawing)editorPart;
			Object diagramManager = editor.getDiagramManager();
			if ((diagramManager != null) && (diagramManager instanceof DecisionTreeDiagramManager)) {
				layoutManager = ((DecisionTreeDiagramManager) diagramManager).getLayoutManager();
			}
		}
	}

	public PaletteDrawer createNodeGroup(final IWorkbenchPage page, Palette palette) {
		return null;
	}
	
	public PaletteDrawer createConditionGroup(final IWorkbenchPage page, Palette palette) {
		PaletteDrawer conditionDrawer = new PaletteDrawer(
				"Conditions",
				"",
				DecisionTreeImages.NODES, palette, false);
		conditionDrawer.setGlobal(false);

		conditionDrawer.addPaletteEntry(createPaletteEntry(page,
				conditionDrawer, null,
				"Boolean Condition",
				"",
				DecisionTreeImages.BOOLEAN_CONDITION, new BooleanConditionNodeCreator(layoutManager, null),
				new BooleanConditionNodeUI(), false, Tool.NONE, false));
		//decisionTreeNodesDrawer.addPaletteEntry(new SeparatorPaletteEntry(decisionTreeNodesDrawer));
		/* // TODO - TSV 9.2
		conditionDrawer.addPaletteEntry(createPaletteEntry(page,
				conditionDrawer, null,
				"Switch Condition",
				"",
				DecisionTreeImages.SWITCH_CONDITION, new SwitchConditionNodeCreator(layoutManager, null),
				new SwitchConditionNodeUI(), false, Tool.NONE, false));
				*/
		return conditionDrawer;
	}

	public PaletteDrawer createActionGroup(final IWorkbenchPage page, Palette palette) {
		PaletteDrawer actionDrawer = new PaletteDrawer(
				"Actions",
				"",
				DecisionTreeImages.NODES, palette, false);
		actionDrawer.setGlobal(false);

		actionDrawer.addPaletteEntry(createPaletteEntry(page,
				actionDrawer, null,
				"Assignment",
				"",
				DecisionTreeImages.ASSIGNMENT, new AssignmentNodeCreator(layoutManager, null),
				new AssignmentNodeUI(), false, Tool.NONE, false));
		//decisionTreeNodesDrawer.addPaletteEntry(new SeparatorPaletteEntry(decisionTreeNodesDrawer));
		actionDrawer.addPaletteEntry(createPaletteEntry(page,
				actionDrawer, null,
				"Invoke Rule Function",
				"",
				DecisionTreeImages.CALL_RF, new CallRFNodeCreator(layoutManager, null),
				new CallRFNodeUI(), false, Tool.NONE, false));
		actionDrawer.addPaletteEntry(createPaletteEntry(page,
				actionDrawer, null,
				"Invoke Decision Table",
				"",
				DecisionTreeImages.CALL_TABLE, new CallTableNodeCreator(layoutManager, null),
				new CallTableNodeUI(), false, Tool.NONE, false));
		actionDrawer.addPaletteEntry(createPaletteEntry(page,
				actionDrawer, null,
				"Invoke Decision Tree",
				"",
				DecisionTreeImages.CALL_TREE, new CallTreeNodeCreator(layoutManager, null),
				new CallTreeNodeUI(), false, Tool.NONE, false));
		return actionDrawer;
	}

	public PaletteDrawer createTerminalGroup(final IWorkbenchPage page, Palette palette) {
		PaletteDrawer terminalDrawer = new PaletteDrawer(
				"Terminals",
				"",
				DecisionTreeImages.NODES, palette, false);
		terminalDrawer.setGlobal(false);

		terminalDrawer.addPaletteEntry(createPaletteEntry(page,
				terminalDrawer, null,
				"Start",
				"",
				DecisionTreeImages.TERMINAL_START, new StartNodeCreator(layoutManager, null),
				new StartNodeUI(), false, Tool.NONE, false));
		//decisionTreeNodesDrawer.addPaletteEntry(new SeparatorPaletteEntry(decisionTreeNodesDrawer));
		terminalDrawer.addPaletteEntry(createPaletteEntry(page,
				terminalDrawer, null,
				"End",
				"",
				DecisionTreeImages.TERMINAL_END, new EndNodeCreator(layoutManager, null),
				new EndNodeUI(), false, Tool.NONE, false));
		terminalDrawer.addPaletteEntry(createPaletteEntry(page,
				terminalDrawer, null,
				"Break",
				"",
				DecisionTreeImages.TERMINAL_BREAK, new BreakNodeCreator(layoutManager, null),
				new BreakNodeUI(), false, Tool.NONE, false));
		return terminalDrawer;
	}

	public PaletteDrawer createOtherGroup(final IWorkbenchPage page, Palette palette) {
		PaletteDrawer otherDrawer = new PaletteDrawer(
				"Other",
				"",
				DecisionTreeImages.NODES, palette, false);
		otherDrawer.setGlobal(false);

		otherDrawer.addPaletteEntry(createPaletteEntry(page,
				otherDrawer, null,
				"Description",
				"",
				DecisionTreeImages.DESCRIPTION, new DescriptionNodeCreator(layoutManager, null),
				new DescriptionNodeUI(), false, Tool.NONE, false));

		/* // TODO - TSV 9.2
		otherDrawer.addPaletteEntry(createPaletteEntry(page,
				otherDrawer, null,
				"Loop",
				"",
				DecisionTreeImages.LOOP, new LoopNodeCreator(layoutManager, null),
				new LoopNodeUI(), false, Tool.NONE, false));
				*/
		return otherDrawer;
	}

	public PaletteDrawer createLinkGroup(final IWorkbenchPage page,
			Palette palette) {
		PaletteDrawer linkDrawer = new PaletteDrawer(
				"Edges",
				"",
				DecisionTreeImages.LINK, palette, false);
		linkDrawer.setGlobal(false);
		linkDrawer.addPaletteEntry(createPaletteEntry(page,
				linkDrawer, null,
				"Transition",
				"",
				DecisionTreeImages.TRANSITION, new TransitionEdgeCreator(), new TSEPolylineEdgeUI(), true, Tool.NONE, false));
		linkDrawer.addPaletteEntry(createPaletteEntry(page,
				linkDrawer, null,
				"Association",
				"",
				DecisionTreeImages.ASSOCIATION, new TransitionEdgeCreator(), new TSEPolylineEdgeUI(), true, Tool.NONE, false));
		return linkDrawer;
	}

	public void expandBar(final IWorkbenchWindow window, PaletteView paletteView, ExpandBar rootExpandBar, Palette palette, PalettePresentationUpdater listener) {
		for (PaletteDrawer drawer : palette.getPaletteDrawers()) {
			createDrawer(window, rootExpandBar, drawer, listener);
		}
	}

	public void createDrawer(final IWorkbenchWindow window, ExpandBar rootExpandBar, PaletteDrawer drawer, PalettePresentationUpdater listener) {
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
				graphItem.setImage(DecisionTreeImages.getImage(drawer.getImage()));
		}
		graphItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		graphItem.setControl(parent);
		graphItem.setExpanded(true);
		graphItem.setData(drawer);
	}

	public void createPaletteEntry(final IWorkbenchWindow window, boolean isGlobalPalette, PaletteEntry paletteEntry, Composite parent, PalettePresentationUpdater listener) {
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
			image = DecisionTreeImages.getImage(imageName);
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
