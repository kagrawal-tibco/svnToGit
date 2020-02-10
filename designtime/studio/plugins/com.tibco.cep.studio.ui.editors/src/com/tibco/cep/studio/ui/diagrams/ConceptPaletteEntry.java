package com.tibco.cep.studio.ui.diagrams;

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
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.diagramming.model.ConceptNodeCreator;
import com.tibco.cep.diagramming.ui.SimpleConceptNodeUI;
import com.tibco.cep.diagramming.ui.UMLCurvedEdgeUI;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EntityImages;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.palette.PaletteEntryUI;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.PalettePresentationUpdater;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.parts.SeparatorPaletteEntry;
import com.tibco.cep.studio.ui.palette.views.PaletteView;

/**
 * 
 * @author sasahoo
 * 
 */
public class ConceptPaletteEntry extends PaletteEntryUI {

//	public enum Tool {
//		CONCEPT_TOOL, INHERITANCE_LINK_TOOL, CONTAINMENT_LINK_TOOL, REFERENCE_LINK_TOOL, NOTE_TOOL
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
//				Messages.getString("palette.general"),
//				Messages.getString("palette.general"),
//				EntityImages.PALETTE_NODES, palette);
//		projectNodesDrawer.setGlobal(false);
//
//		projectNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				projectNodesDrawer, null,
//				Messages.getString("PALETTE_ENTRY_TOOL_NOTE_TITLE"),
//				Messages.getString("PALETTE_ENTRY_TOOL_NOTE_TOOLTIP"),
//				EntityImages.PALETTE_NOTE, new NoteNodeCreator(),
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

		PaletteDrawer conceptDiagramNodesDrawer = new PaletteDrawer(
				Messages.getString("PALETTE_DRAWER_NODES_TITLE"),
				Messages.getString("PALETTE_DRAWER_NODES_TOOLTIP"),
				EntityImages.PALETTE_NODES, palette, false);
		conceptDiagramNodesDrawer.setGlobal(false);

		conceptDiagramNodesDrawer.addPaletteEntry(createPaletteEntry(page,
				conceptDiagramNodesDrawer, null,
				Messages.getString("CONCEPT_PALETTE_CONCEPT_TITLE"),
				Messages.getString("CONCEPT_PALETTE_CONCEPT_TOOLTIP"),
				EntityImages.CONCEPT_PALETTE_CONCEPT, new ConceptNodeCreator(),
				new SimpleConceptNodeUI(), false, Tool.NONE, false));
		conceptDiagramNodesDrawer.addPaletteEntry(new SeparatorPaletteEntry(
				conceptDiagramNodesDrawer));
//		conceptDiagramNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				conceptDiagramNodesDrawer, null,
//				Messages.PALLETTE_ENTRY_TOOL_NOTE_TITLE,
//				Messages.PALLETTE_ENTRY_TOOL_NOTE_TOOLTIP,
//				EntityImages.PALETTE_NOTE, new NoteNodeCreator(),
//				new NoteNodeUI(), false, Tool.NOTE_TOOL));
		return conceptDiagramNodesDrawer;
	}

	/**
	 * 
	 * @param page
	 * @param palette
	 * @return
	 */
	public PaletteDrawer createLinkGroup(final IWorkbenchPage page,
			Palette palette) {

		PaletteDrawer conceptDiagramLinkDrawer = new PaletteDrawer(
				Messages.getString("CONCEPT_PALETTE_DRAWER_LINK_TITLE"),
				Messages.getString("CONCEPT_PALETTE_DRAWER_LINK_TOOLTIP"),
				EntityImages.CONCEPT_PALETTE_LINK, palette, false);
		conceptDiagramLinkDrawer.setGlobal(false);
		conceptDiagramLinkDrawer.addPaletteEntry(createPaletteEntry(page,
				conceptDiagramLinkDrawer, null,
				Messages.getString("CONCEPT_PALETTE_INHERITANCE_TITLE"),
				Messages.getString("CONCEPT_PALETTE_INHERITANCE_TOOLTIP"),
				EntityImages.CONCEPT_PALETTE_INHERITANCE, new InheritanceStateEdgeCreator(), new UMLCurvedEdgeUI(UMLCurvedEdgeUI.INHERITANCE), true,
				Tool.NONE, false));
		conceptDiagramLinkDrawer.addPaletteEntry(createPaletteEntry(page,
				conceptDiagramLinkDrawer, null,
				Messages.getString("CONCEPT_PALETTE_CONTAINMENT_TITLE"),
				Messages.getString("CONCEPT_PALETTE_CONTAINMENT_TOOLTIP"),
				EntityImages.CONCEPT_PALETTE_CONTAINMENT, new ContainmentStateEdgeCreator(), new UMLCurvedEdgeUI(UMLCurvedEdgeUI.CONTAINMENT), true, Tool.NONE, false));
		conceptDiagramLinkDrawer.addPaletteEntry(createPaletteEntry(page,
				conceptDiagramLinkDrawer, null,
				Messages.getString("CONCEPT_PALETTE_REFERENCE_TITLE"),
				Messages.getString("CONCEPT_PALETTE_REFERENCE_TOOLTIP"),
				EntityImages.CONCEPT_PALETTE_REFERENCE, new ReferenceStateEdgeCreator(), new UMLCurvedEdgeUI(UMLCurvedEdgeUI.REFERENCE), true, Tool.NONE, false));

		return conceptDiagramLinkDrawer;
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
			createPaletteEntry(window, drawer.isGlobal(), paletteEntry, parent,
					listener);
		}
		ExpandItem graphItem = new ExpandItem(rootExpandBar, SWT.NONE);
		graphItem.setText(drawer.getTitle());
		if (drawer.getImage() != null) {
			if (drawer.isGlobal())
				graphItem.setImage(StudioUIPlugin.getDefault().getImage(
						drawer.getImage()));
			else
				graphItem.setImage(EntityImages.getImage(drawer.getImage()));
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
			if (imageName != null
					&& StudioUIPlugin.getImageDescriptor(imageName) != null) {
				image = (StudioUIPlugin.getDefault().getImage(imageName));
			}
		} else {
			image = EntityImages.getImage(imageName);
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
