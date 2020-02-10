package com.tibco.cep.studio.ui.diagrams;

import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_DEPENDENCY_LEVEL_ALL_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_DEPENDENCY_LEVEL_ALL_TOOLTIP;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_DEPENDENCY_LEVEL_ONE_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_DEPENDENCY_LEVEL_ONE_TOOLTIP;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_DEPENDENCY_LEVEL_TWO_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_DEPENDENCY_LEVEL_TWO_TOOLTIP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditor;
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
import com.tomsawyer.graph.TSFindChildParent;

public class DependencyDiagramPaletteEntry extends PaletteEntryUI {

	private static Font unselectedFont = new Font(null, "Tahoma", 8, SWT.NONE);
	private static Color textColor = new Color(null, 33, 93, 198);
	private static Color defaultColor = new Color(null, 215, 222, 248);
	private  IWorkbenchPage page;
	DependencyDiagramManager dependencyDiagramManager;
//	public enum Tool {
//		EVENT_TOOL, NOTE_TOOL, CONCEPT_TOOL, STATEMACHINE_TOOL, DECISIONTABLE_TOOL,
//		ARCHIVE_TOOL, RULES_TOOL, CHANNEL_TOOL, SCORECARD_TOOL, SCOPE_LINKS_TOOL,
//		RULES_IN_FOLDER_TOOL, TOOLTIP_TOOL, GROUP_CONCEPTS_TOOL, GROUP_EVENTS_TOOL,
//		GROUP_RULE_TOOL, GROUP_RULE_FUNCTIONS_TOOL, ARCHIVE_DEST_LINKS_TOOL,
//		ARCHIVE_RULES_LINKS_TOOL, ARCHIVE_ALLRULES_LINKS_TOOL
//	}
	private Map<String, Button> dependencyLevelButtonMap = new HashMap<String,Button>();

	public PaletteDrawer createNodeGroup(final IWorkbenchPage page,
			Palette palette) {

//		PaletteDrawer dependencyNodesDrawer = new PaletteDrawer(
//				Messages.getString("palette.general"),
//				Messages.getString("palette.general"),
//				EntityImages.PALETTE_NODES, palette);
//		dependencyNodesDrawer.setGlobal(false);
//
//		dependencyNodesDrawer.addPaletteEntry(createPaletteEntry(page,
//				dependencyNodesDrawer, null,
//				Messages.getString("PALETTE_ENTRY_TOOL_NOTE_TITLE"),
//				Messages.getString("PALETTE_ENTRY_TOOL_NOTE_TOOLTIP"),
//				EntityImages.PALETTE_NOTE, new NoteNodeCreator(),
//				new NoteNodeUI(), false, Tool.NONE));
//		
//		return dependencyNodesDrawer;
		return null;
	}

	public PaletteDrawer createFilterGroup(final IWorkbenchPage page,
			Palette palette) {
		dependencyDiagramManager = 
				(DependencyDiagramManager)((DependencyDiagramEditor) page.getActiveEditor()).getDiagramManager();
	
		this.page = page;
		
		PaletteDrawer dependencyNodesDrawer = new PaletteDrawer(
				Messages.getString("PALETTE_ENTRY_VIEW_DEPENDENCY_TITLE"),
				Messages.getString("PALETTE_ENTRY_VIEW_DEPENDENCY_TOOLTIP"),
				EntityImages.PALETTE_NODES, palette, false);
		dependencyNodesDrawer.setGlobal(false);

		dependencyNodesDrawer.addPaletteEntry(createRadioButtonEntry(page,
				dependencyNodesDrawer, null, 
				PALETTE_DEPENDENCY_LEVEL_ONE_TITLE, PALETTE_DEPENDENCY_LEVEL_ONE_TOOLTIP,
				null, Tool.NONE));

		dependencyNodesDrawer.addPaletteEntry(createRadioButtonEntry(page,
				dependencyNodesDrawer, null,
				PALETTE_DEPENDENCY_LEVEL_TWO_TITLE, PALETTE_DEPENDENCY_LEVEL_TWO_TOOLTIP,
				null, Tool.NONE));

		dependencyNodesDrawer.addPaletteEntry(createRadioButtonEntry(page,
				dependencyNodesDrawer, null,
				PALETTE_DEPENDENCY_LEVEL_ALL_TITLE, PALETTE_DEPENDENCY_LEVEL_ALL_TOOLTIP,
				null, Tool.NONE));
		
		return dependencyNodesDrawer;
	}


	public PaletteEntry createRadioButtonEntry(final IWorkbenchPage page,
			PaletteDrawer drawer, String id, String title, String tooltip,
			String image, final Tool tool) {

		return new PaletteEntry(id, title, tooltip, image, drawer, false) {

			@Override
			protected void stateChanged() {
				if (getState() == STATE_NOT_SELECTED) {
					return;
				}
			}
		};
	}

	public void expandBar(final IWorkbenchWindow window,
			PaletteView paletteView, ExpandBar rootExpandBar, Palette palette,
			PalettePresentationUpdater listener) {
		for (PaletteDrawer drawer : palette.getPaletteDrawers()) {
			createDrawer(window, rootExpandBar, drawer, listener);
		}
	}


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

	public Button createRadioButtonEntry(final IWorkbenchWindow window,
			PaletteEntry paletteEntry, Composite parent,
			PalettePresentationUpdater listener) {
		if (paletteEntry instanceof SeparatorPaletteEntry) {
			StudioPaletteUI.createSeparator(parent);
			return null;
		}
		final Button button = createRadioButton(window, parent, paletteEntry.getTitle(), paletteEntry.getToolTip(), paletteEntry.getImage(), listener);
		button.setData(paletteEntry);
	/*	
		if(button.getText().intern() == PALETTE_DEPENDENCY_LEVEL_ONE_TITLE){
			button.setSelection(true);
			dependencyDiagramManager.setDependencyLevel(1);
		}
		*/
		button.addSelectionListener(new SelectionAdapter(){

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				if(page == null) return;
				if(!(page.getActiveEditor() instanceof DependencyDiagramEditor)) return;
				dependencySelection(button);
		     }
		});
		return button;
	}

	/**
	 * @param button
	 */
	private void dependencySelection(Button button){
		String title = button.getText().intern();
		boolean selection = button.getSelection();
		if(selection)
		{
			if(title == PALETTE_DEPENDENCY_LEVEL_ONE_TITLE){
				dependencyDiagramManager.setDependencyLevel(1);
			}
			if(title == PALETTE_DEPENDENCY_LEVEL_TWO_TITLE) {
				dependencyDiagramManager.setDependencyLevel(2);
			}
			if(title == PALETTE_DEPENDENCY_LEVEL_ALL_TITLE){
				dependencyDiagramManager.setDependencyLevel(TSFindChildParent.FIND_DEPTH_ALL);
			}
		}
	}
	
	public void createRadioDrawer(final IWorkbenchWindow window,
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
		
		dependencyLevelButtonMap.clear();
		List<PaletteEntry> entries = drawer.getPaletteEntries();
		for (PaletteEntry paletteEntry : entries) {
			Button button = createRadioButtonEntry(window, paletteEntry, parent, listener);
			dependencyLevelButtonMap.put(button.getText(),button);
		}
		
		Button button;
        switch(dependencyDiagramManager.getDependencyLevel()){
          case 1: 
        	  button =  dependencyLevelButtonMap.get("One");
         	 dependencyDiagramManager.setDependencyLevel(dependencyDiagramManager.getDependencyLevel());
         	button.setSelection(true);
         	 break;
          case 2:
        	  button =  dependencyLevelButtonMap.get("Two");
             dependencyDiagramManager.setDependencyLevel(dependencyDiagramManager.getDependencyLevel());
             button.setSelection(true);
              break;
          case TSFindChildParent.FIND_DEPTH_ALL:	 // this is the depth stored internally on selecting 'All' option
        	  button =  dependencyLevelButtonMap.get("All");
          	 dependencyDiagramManager.setDependencyLevel(dependencyDiagramManager.getDependencyLevel());
          	button.setSelection(true);
          } 
		
		
		
		ExpandItem graphItem = new ExpandItem(rootExpandBar, SWT.NONE);
		graphItem.setText(drawer.getTitle());
		if (drawer.getImage() != null) {
			graphItem.setImage(EntityImages.getImage(drawer.getImage()));
		}
		graphItem.setHeight(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		graphItem.setControl(parent);
		graphItem.setExpanded(true);
		graphItem.setData(drawer);

	}
	
	public Button createRadioButton(final IWorkbenchWindow window,
			Composite buttonParent, String label, String toolTipText,
			String imageName, PalettePresentationUpdater listener) {
		return StudioPaletteUI.createRadioButton(window, buttonParent, label,
				toolTipText, imageName, listener, EntityImages.getImage(imageName));
	}


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
	}

	public void createRadioButtonEntry(PaletteEntry paletteEntry,
			Composite parent, PalettePresentationUpdater listener) {
		if (paletteEntry instanceof SeparatorPaletteEntry) {
			StudioPaletteUI.createSeparator(parent);
			return;
		}
		Control entry = createRadioButton(parent, paletteEntry.getTitle(),
				paletteEntry.getToolTip());
		entry.setData(paletteEntry);
	}

	public Control createRadioButton(Composite buttonParent, String title,
			String toolTipText) {
		org.eclipse.swt.widgets.Button button = new org.eclipse.swt.widgets.Button(
				buttonParent, SWT.RADIO);
		button.setText(title);
		button.setFont(unselectedFont);
		button.setBackground(defaultColor);
		button.setForeground(textColor);
		button.setToolTipText(toolTipText);

		GridLayout layout = new GridLayout();
		layout.marginLeft = layout.marginRight = layout.marginWidth = 0;
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(data);

		return button;
	}


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

	protected PaletteDrawer createLinkGroup(IWorkbenchPage page, Palette palette) {
		// We are not using this group for this diagram  
		return null;
	}

	public Map<String, Button> getDependencyLevelButtonMap() {
		return dependencyLevelButtonMap;
	}

}
