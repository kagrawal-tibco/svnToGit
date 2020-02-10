package com.tibco.cep.studio.ui.diagrams;

import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_GROUP_CONCEPTS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_GROUP_EVENTS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_GROUP_RULES_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_GROUP_RULE_FUNCTIONS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_CONCEPTS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_EVENTS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_METRICS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_RULES_FUNCTIONS_TITLE;
import static com.tibco.cep.diagramming.utils.DiagramConstants.PALETTE_SHOW_RULES_TITLE;
import static com.tibco.cep.studio.ui.editors.utils.StudioEditorDiagramUIUtils.doApply;
import static com.tibco.cep.studio.ui.editors.utils.StudioEditorDiagramUIUtils.doDefault;
import static com.tibco.cep.studio.ui.editors.utils.StudioEditorDiagramUIUtils.handleEntry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.utils.DiagramConstants;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.project.ProjectDiagramEditor;
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
 * @author hnembhwa
 * 
 */
public class ProjectPaletteEntry extends PaletteEntryUI {

	private IGraphDrawing editor;
	private static Font unselectedFont = new Font(null, "Tahoma", 8, SWT.NONE);
	private static Color textColor = new Color(null, 33, 93, 198);
	private static Color defaultColor = new Color(null, 215, 222, 248);
    
	private ProjectDiagramManager projectDiagramManager;
	
	//Project Diagram Map used for Filter Palette Refresh on editor switch
	private Map<String, Boolean> projectDiagramMap;
	//Palette Map for the internal purpose.
	private Map<String, Boolean> paletteMap = new HashMap<String, Boolean>();
	
	private Map<String, Boolean> defaultMap;
	
	private Map<Button, Boolean> entryButtonValueMap = new HashMap<Button, Boolean>();
	private Map<String, Button> entryButtonMap = new HashMap<String,Button>();
	
	public PaletteDrawer createNodeGroup(final IWorkbenchPage page,
			Palette palette) {
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
//
//		return projectNodesDrawer;
		return null;
	}

	public PaletteDrawer createFilterGroup(final IWorkbenchPage page,
			Palette palette) {

		projectDiagramManager = (ProjectDiagramManager)((ProjectDiagramEditor) page.getActiveEditor()).getDiagramManager();
		projectDiagramMap = projectDiagramManager.getProjectDiagramMap();
		defaultMap = projectDiagramManager.getDefaultMap();
		
		PaletteDrawer projectNodesDrawer = new PaletteDrawer(
				Messages.getString("PALETTE_ENTRY_VIEW_FILTER_TITLE"),
				Messages.getString("PALETTE_DRAWER_NODES_TOOLTIP"),
				EntityImages.PROJECT_DIAGRAM_FILTER_DRAWER_GRP_TITLE, palette, false);
		projectNodesDrawer.setGlobal(false);

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				PALETTE_SHOW_CONCEPTS_TITLE,
				DiagramConstants.PALETTE_SHOW_CONCEPTS_TOOLTIP,
				EntityImages.CONCEPT_PALETTE_CONCEPT, Tool.NONE));
		if(AddonUtil.isViewsAddonInstalled()){
		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				PALETTE_SHOW_METRICS_TITLE,
				DiagramConstants.PALETTE_SHOW_METRICS_TOOLTIP,
				EntityImages.METRIC, Tool.NONE));
		}
		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null, PALETTE_SHOW_EVENTS_TITLE,
				DiagramConstants.PALETTE_SHOW_EVENTS_TOOLTIP, EntityImages.EVENT,
				Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_DECISIONTABLES_TITLE,
				DiagramConstants.PALETTE_SHOW_DECISIONTABLES_TOOLTIP,
				EntityImages.PALETTE_DECISIONTABLE, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_DOMAIN_MODEL_TITLE,
				DiagramConstants.PALETTE_SHOW_DOMAIN_MODEL_TOOLTIP,
				EntityImages.PALETTE_DOMAINMODEL, Tool.NONE));
		
		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_STATEMACHIES_TITLE,
				DiagramConstants.PALETTE_SHOW_STATEMACHIES_TOOLTIP,
				EntityImages.PALETTE_STATEMACHINE, Tool.NONE));
		
		/*projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_ARCHIVES_TITLE,
				DiagramConstants.PALETTE_SHOW_ARCHIVES_TOOLTIP,
				EntityImages.PALETTE_ARCHIVE, Tool.NONE));*/

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null, PALETTE_SHOW_RULES_TITLE,
				DiagramConstants.PALETTE_SHOW_RULES_TOOLTIP,
				EntityImages.PALETTE_RULE, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				PALETTE_SHOW_RULES_FUNCTIONS_TITLE,
				DiagramConstants.PALETTE_SHOW_RULES_FUNCTIONS_TOOLTIP,
				EntityImages.PALETTE_RULE_FUNCTION, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_SCORECARDS_TITLE,
				DiagramConstants.PALETTE_SHOW_SCORECARDS_TOOLTIP,
				EntityImages.SCORECARD, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_CHANNELS_TITLE,
				DiagramConstants.PALETTE_SHOW_CHANNELS_TOOLTIP,
				EntityImages.CHANNEL,
				Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_PROCESSES_TITLE,
				DiagramConstants.PALETTE_SHOW_PROCESSES_TOOLTIP,
				EntityImages.PROCESS,
				Tool.NONE));
		
		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_SCOPE_LINKS_TITLE,
				DiagramConstants.PALETTE_SHOW_SCOPE_LINKS_TOOLTIP,
				EntityImages.PALETTE_LINK, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_USAGE_LINKS_TITLE,
				DiagramConstants.PALETTE_SHOW_USAGE_LINKS_TOOLTIP,
				EntityImages.PALETTE_LINK, Tool.NONE));
		
		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_PROCESS_LINKS_TITLE,
				DiagramConstants.PALETTE_SHOW_PROCESS_LINKS_TOOLTIP,
				EntityImages.PALETTE_LINK, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_PROCESS_EXPANDED_TITLE,
				DiagramConstants.PALETTE_SHOW_PROCESS_EXPANDED_TOOLTIP,
				EntityImages.PROCESS, Tool.NONE));
		
		/*projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_ARCHIVE_DEST_LINKS_TITLE,
				DiagramConstants.PALETTE_SHOW_ARCHIVE_DEST_LINKS_TOOLTIP,
				EntityImages.PALETTE_ARCHIVE, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_ARCHIVE_RULES_LINKS_TITLE,
				DiagramConstants.PALETTE_SHOW_ARCHIVE_RULES_LINKS_TOOLTIP,
				EntityImages.PALETTE_ARCHIVE, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TITLE,
				DiagramConstants.PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TOOLTIP,
				EntityImages.PALETTE_ARCHIVE, Tool.NONE));
		
		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_SHOW_RULES_FOLDERS_TITLE,
				DiagramConstants.PALETTE_SHOW_RULES_FOLDERS_TOOLTIP,
				EntityImages.PALETTE_RULE, Tool.NONE));
*/
//		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
//				projectNodesDrawer, null,
//				DiagramConstants.PALETTE_SHOW_TOOLTIPS_TITLE,
//				DiagramConstants.PALETTE_SHOW_TOOLTIPS_TOOLTIP,
//				EntityImages.PALETTE_TOOLTIP, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				DiagramConstants.PALETTE_GROUP_CONCEPTS_TITLE,
				DiagramConstants.PALETTE_GROUP_CONCEPTS_TOOLTIP,
				EntityImages.PALETTE_NOTE, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null, PALETTE_GROUP_EVENTS_TITLE,
				DiagramConstants.PALETTE_GROUP_EVENTS_TOOLTIP,
				EntityImages.PALETTE_NOTE, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null, PALETTE_GROUP_RULES_TITLE,
				DiagramConstants.PALETTE_GROUP_RULES_TOOLTIP,
				EntityImages.PALETTE_NOTE, Tool.NONE));

		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				PALETTE_GROUP_RULE_FUNCTIONS_TITLE,
				DiagramConstants.PALETTE_GROUP_RULE_FUNCTIONS_TOOLTIP,
				EntityImages.PALETTE_NOTE, Tool.NONE));
	/*	
		projectNodesDrawer.addPaletteEntry(createCheckButtonEntry(page,
				projectNodesDrawer, null,
				PALETTE_SHOWALL,
				DiagramConstants.PALETTE_SHOWALL,
				EntityImages.PALETTE_NOTE, Tool.NONE));*/

		return projectNodesDrawer;
	}


	public PaletteEntry createCheckButtonEntry(final IWorkbenchPage page,
			PaletteDrawer drawer, String id, String title, String tooltip,
			String image, final Tool tool) {

		return new PaletteEntry(id, title, tooltip, image, drawer, false) {

			@Override
			protected void stateChanged() {
				if (getState() == STATE_NOT_SELECTED) {
					return;
				}
				IEditorPart activeEditorPart = page.getActiveEditor();
				if (activeEditorPart instanceof IGraphDrawing) {
					editor = (IGraphDrawing) activeEditorPart;
				}
				if (editor != null) {
				} else {
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
    
	public void createCheckButtonEntry(final IWorkbenchWindow window,
			PaletteEntry paletteEntry, Composite parent,
			PalettePresentationUpdater listener) {
		try{
		if (paletteEntry instanceof SeparatorPaletteEntry) {
			StudioPaletteUI.createSeparator(parent);
			return;
		}
		final Button entry = createCheckButton(window, parent, paletteEntry.getTitle(), paletteEntry.getToolTip(), paletteEntry.getImage(), listener);
		boolean defaultValue = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(entry.getText().intern());

		if(projectDiagramMap.containsKey(entry.getText().intern())){
			entry.setSelection(projectDiagramMap.get(entry.getText().intern()));
			paletteMap.put(entry.getText().intern(), projectDiagramMap.get(entry.getText().intern()));
		}else{
			entry.setSelection(defaultValue);
			if(!projectDiagramMap.containsKey(entry.getText().intern())){
				projectDiagramMap.put(entry.getText().intern(), defaultValue);
			}
			if(!paletteMap.containsKey(entry.getText().intern())){
				paletteMap.put(entry.getText().intern(), defaultValue);
			}
		}
		entryButtonMap.put(entry.getText().intern(), entry);
		entryButtonValueMap.put(entry, entry.getSelection());
		
		entry.addSelectionListener(new SelectionAdapter(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				paletteMap.put(entry.getText().intern(), entry.getSelection());
				entryButtonValueMap.put(entry, entry.getSelection());
				
				//Handling Selection of Group Palette Entries
				handleEntry(entry, entryButtonMap, paletteMap, PALETTE_SHOW_CONCEPTS_TITLE, PALETTE_GROUP_CONCEPTS_TITLE);
				handleEntry(entry, entryButtonMap, paletteMap, PALETTE_SHOW_EVENTS_TITLE, PALETTE_GROUP_EVENTS_TITLE);
				handleEntry(entry, entryButtonMap, paletteMap, PALETTE_SHOW_RULES_TITLE, PALETTE_GROUP_RULES_TITLE);
				handleEntry(entry, entryButtonMap, paletteMap, PALETTE_SHOW_RULES_FUNCTIONS_TITLE, PALETTE_GROUP_RULE_FUNCTIONS_TITLE);
				
			}});
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void createCheckDrawer(final IWorkbenchWindow window,
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
			createCheckButtonEntry(window, paletteEntry, parent, listener);
			/*if(paletteEntry.getTitle().equalsIgnoreCase("Showall")){
				if(paletteEntry.getState()==PaletteEntry.STATE_NOT_SELECTED){

				}
			}*/
		}
		
		Composite parentButtonComposite = new Composite(parent, SWT.BORDER);
		GridLayout layoutButton = new GridLayout(3, true);
		parentButtonComposite.setLayout(layoutButton);
		
		GridData dataButton = new GridData(GridData.FILL_BOTH);
		dataButton.grabExcessHorizontalSpace = true;
		dataButton.grabExcessVerticalSpace = true;
		parentButtonComposite.setLayoutData(dataButton);
		final Button button1= createCheckButton(window, parentButtonComposite,
				"Check All", "Check All", "Image", listener);
		
		
		button1.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				try{
					boolean select=button1.getSelection();
					Iterator<Entry<String, Boolean>> i= paletteMap.entrySet().iterator();
					for(Button entry:entryButtonValueMap.keySet()){
						entry.setEnabled(true);
						if(select==true){
						entry.setSelection(true);
						}else{
							entry.setSelection(false);
							//Handling Selection of Group Palette Entries
							entryButtonMap.get( PALETTE_GROUP_CONCEPTS_TITLE).setEnabled(false);
							entryButtonMap.get(  PALETTE_GROUP_EVENTS_TITLE).setEnabled(false);
							entryButtonMap.get( PALETTE_GROUP_RULES_TITLE).setEnabled(false);
							entryButtonMap.get(  PALETTE_GROUP_RULE_FUNCTIONS_TITLE).setEnabled(false);
						}
						
			}
			
					while(i.hasNext()){
						Entry<String, Boolean> pairs = i.next();
						
						
						if(select==true){
						paletteMap.put((String) pairs.getKey(), true);
						
					}else{
						paletteMap.put((String) pairs.getKey(), false);
					}
					}
					
					/*doApply(projectDiagramManager, projectDiagramMap, paletteMap);
					*/
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
	
		Button button = new Button(parentButtonComposite, SWT.NONE);
		button.setText(Messages.getString("Apply"));
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				try{
					updateProjectDiagramManager(window);
					doApply(projectDiagramManager, projectDiagramMap, paletteMap);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		button = new Button(parentButtonComposite, SWT.NONE);
		button.setText(Messages.getString("Defaults"));
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				try{
					updateProjectDiagramManager(window);
					paletteMap.clear();
					paletteMap.putAll(defaultMap);
					doDefault(projectDiagramManager, projectDiagramMap, defaultMap, entryButtonValueMap);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
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
	
	

	public Button createCheckButton(final IWorkbenchWindow window,
			Composite buttonParent, String label, String toolTipText,
			String imageName, PalettePresentationUpdater listener) {
		return StudioPaletteUI.createCheckButton(window, buttonParent, label,
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


	@Override
	public void createCheckButtonEntry(PaletteEntry paletteEntry,
			Composite parent, PalettePresentationUpdater listener) {
		if (paletteEntry instanceof SeparatorPaletteEntry) {
			StudioPaletteUI.createSeparator(parent);
			return;
		}
		Control entry = createCheckboxButton(parent, paletteEntry.getTitle(),
				paletteEntry.getToolTip());
		entry.setData(paletteEntry);
	}

	/**
	 * @param window
	 */
	private void updateProjectDiagramManager(final IWorkbenchWindow window){
		projectDiagramManager = (ProjectDiagramManager)((ProjectDiagramEditor) window.getActivePage().getActiveEditor()).getDiagramManager();
		projectDiagramMap = projectDiagramManager.getProjectDiagramMap();
		defaultMap = projectDiagramManager.getDefaultMap();
	}
	
	@Override
	public Control createCheckboxButton(Composite buttonParent, String title,
			String toolTipText) {
		org.eclipse.swt.widgets.Button button = new org.eclipse.swt.widgets.Button(
				buttonParent, SWT.CHECK);
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

	public Map<String, Boolean> getPaletteMap() {
		return paletteMap;
	}

	public Map<String, Button> getEntryButtonMap() {
		return entryButtonMap;
	}

	public Map<Button, Boolean> getEntryButtonValueMap() {
		return entryButtonValueMap;
	}
}