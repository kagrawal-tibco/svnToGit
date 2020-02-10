package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.dialogs.FilteredList.FilterMatcher;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalComponentGalleryFolder;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.actions.SeparatorAction;
import com.tibco.cep.studio.dashboard.ui.actions.UpdateableAction;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.editors.views.DeleteOrRemoveAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.LocalElementTreeContentProvider;
import com.tibco.cep.studio.dashboard.ui.editors.views.MoveDownAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.MoveUpAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.RenameAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNode;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNodeLabelProvider;
import com.tibco.cep.studio.dashboard.ui.editors.views.ViewsTreeViewerAction;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.forms.LocalElementLabelProvider;
import com.tibco.cep.studio.dashboard.ui.search.LocalElementPropertyMatcher;
import com.tibco.cep.studio.dashboard.ui.utils.NameValidator;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 *
 */
public class ExperimentalRolePreferencePage extends AbstractEntityEditorPage {

	private RolePreferenceChangeListener changeListener;

	private ExceptionHandler exceptionHandler;

	private Composite switchingComposite;
	private StackLayout switchingCompositeLayout;

	private Composite emptyGalleryComposite;

	private Composite galleryComposite;

	//charts list
	private CustomFilteredList chartsFilteredList;
	//all actions associated with chartsFilteredList
	private List<UpdateableAction> chartsFilteredListActions;
	//tool bar for chartsFilteredActions
	private ToolBar chartsFilteredListToolBar;

	// gallery as tree
	private GalleryFoldersAndChartsTreeViewer galleryFoldersAndChartsViewer;
	// gallery actions
	private List<UpdateableAction> galleryFoldersAndChartsActions;
	// gallery tool bar
	private ToolBar galleryFoldersAndChartsToolBar;

	public ExperimentalRolePreferencePage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
		exceptionHandler = DashboardUIPlugin.getInstance().getExceptionHandler();
		changeListener = new RolePreferenceChangeListener();
		localElement.subscribeToAll(changeListener);
		localElement.subscribe(changeListener, BEViewsElementNames.COMPONENT_GALLERY_FOLDER);
	}

	@Override
	protected void createExtendedProperties(IManagedForm mform, Composite propertiesPanel) throws Exception {
		super.createExtendedProperties(mform, propertiesPanel);
		createComboField(mform, propertiesPanel, getLocalElement().getParticle(LocalRolePreference.PROP_KEY_VIEW));
	}

	@Override
	protected void createFieldSection(IManagedForm mform, Composite parent) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		Section section = createSection(mform, parent, "Gallery");

		switchingComposite = toolkit.createComposite(section, SWT.NONE);
		switchingCompositeLayout = new StackLayout();
		switchingComposite.setLayout(switchingCompositeLayout);

		// create empty gallery composite
		emptyGalleryComposite = toolkit.createComposite(switchingComposite, SWT.NONE);
		emptyGalleryComposite.setLayout(new GridLayout(1, true));
		// label prompt
		Label lbl_prompt = toolkit.createLabel(emptyGalleryComposite, "Click To Add New Gallery Folder", SWT.CENTER);
		lbl_prompt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		lbl_prompt.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				addRootFolder();
			}

		});

		emptyGalleryComposite.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				addRootFolder();
			}

		});

		// create the main component
		galleryComposite = toolkit.createComposite(switchingComposite, SWT.NONE);
		galleryComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		galleryComposite.setLayout(new GridLayout(2, true));

		Group chartsGroup = new Group(galleryComposite, SWT.NONE);
		toolkit.adapt(chartsGroup);
		chartsGroup.setText("Charts");
		chartsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout chartsCompositeLayout = new GridLayout(2, false);
		chartsGroup.setLayout(chartsCompositeLayout);

		/*Label lbl_SearchPrompt = */toolkit.createLabel(chartsGroup, "Enter a name or pattern:");

		chartsFilteredListToolBar = new ToolBar(chartsGroup, SWT.VERTICAL);
		toolkit.adapt(chartsFilteredListToolBar);
		chartsFilteredListToolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 3));

		final Text txt_Search = toolkit.createText(chartsGroup, "", SWT.SINGLE);
		txt_Search.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		txt_Search.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent e) {
				//clear selection
				chartsFilteredList.setSelection(new int[0]);
				//set the text for filter
				chartsFilteredList.setFilter(txt_Search.getText());
			}

		});


		chartsFilteredList = new CustomFilteredList(chartsGroup, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION, new LocalElementLabelProvider(true,true), true, false, true);
		chartsFilteredList.setFilterMatcher(new CustomFilterMatch());
		chartsFilteredList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//by default the input is all the charts in the project
		chartsFilteredList.setInput(getLocalElement().getRoot().getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT));

		Group galleryTreeGroup = new Group(galleryComposite, SWT.NONE);
		toolkit.adapt(chartsGroup);
		galleryTreeGroup.setText("Folders And Charts");
		galleryTreeGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout treeCompositeLayout = new GridLayout(2, false);
		galleryTreeGroup.setLayout(treeCompositeLayout);

		// tree viewer
		galleryFoldersAndChartsViewer = new GalleryFoldersAndChartsTreeViewer(galleryTreeGroup, SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER);
		galleryFoldersAndChartsViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolkit.adapt(galleryFoldersAndChartsViewer.getControl(), true, true);

		galleryFoldersAndChartsViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				try {
					//get selected folders
					List<LocalElement> selectedFolders = galleryFoldersAndChartsViewer.getSelectedFolders();
					//get all the charts
					List<LocalElement> allCharts = getLocalElement().getRoot().getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
					//check if the user has selected a single folder
					if (selectedFolders.size() == 1) {
						//get the components in the folder
						LocalComponentGalleryFolder folder = (LocalComponentGalleryFolder) selectedFolders.get(0);
						//remove all the existing components from the folder
						allCharts.removeAll(folder.getChildren(BEViewsElementNames.COMPONENT));
					}
					//set the input on the list
					chartsFilteredList.setInput(allCharts);
				} catch (Exception e) {
					exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not filter chart listing based on charts in "+getLocalElement().getDisplayableName(), e));
				}
			}

		});

		// create all the actions
		createActions();

		// create gallery tool bar
		galleryFoldersAndChartsToolBar = new ToolBar(galleryTreeGroup, SWT.VERTICAL);
		toolkit.adapt(galleryFoldersAndChartsToolBar);

		// add all the gallery actions
		int i = 0;
		for (IAction action : galleryFoldersAndChartsActions) {
			IContributionItem aci = null;
			if (action instanceof SeparatorAction) {
				aci = new Separator();
			} else {
				aci = new ActionContributionItem(action);
			}
			aci.fill(galleryFoldersAndChartsToolBar, i);
			i++;
		}

		// add all the charts actions
		i = 0;
		for (IAction action : chartsFilteredListActions) {
			IContributionItem aci = null;
			if (action instanceof SeparatorAction) {
				aci = new Separator();
			} else {
				aci = new ActionContributionItem(action);
			}
			aci.fill(chartsFilteredListToolBar, i);
			i++;
		}

		// add pop up menu
		MenuManager menuManager = new MenuManager("#PopupMenu");
		// add all the actions
		for (IAction action : galleryFoldersAndChartsActions) {
			if (action instanceof SeparatorAction) {
				menuManager.add(new Separator());
			} else {
				menuManager.add(action);
			}
		}
		galleryFoldersAndChartsViewer.getTree().setMenu(menuManager.createContextMenu(galleryFoldersAndChartsViewer.getTree()));

		// update the actions
		updateActions();

		// by default set the top control as gallery composite
		switchingCompositeLayout.topControl = galleryComposite;

		section.setClient(switchingComposite);
	}

	private void createActions() {
		galleryFoldersAndChartsActions = new LinkedList<UpdateableAction>();
		// add new folder action
		galleryFoldersAndChartsActions.add(new AddNewGalleryFolderAction(exceptionHandler, galleryFoldersAndChartsViewer));
		// add a separator
		galleryFoldersAndChartsActions.add(new SeparatorAction());
		// rename action
		galleryFoldersAndChartsActions.add(new RenameAction(exceptionHandler, galleryFoldersAndChartsViewer));
		// move up action
		galleryFoldersAndChartsActions.add(new MoveUpAction(exceptionHandler, galleryFoldersAndChartsViewer));
		// move down action
		galleryFoldersAndChartsActions.add(new MoveDownAction(exceptionHandler, galleryFoldersAndChartsViewer));
		// add a separator
		galleryFoldersAndChartsActions.add(new SeparatorAction());
		// delete or remove action
		galleryFoldersAndChartsActions.add(new DeleteOrRemoveAction(exceptionHandler, galleryFoldersAndChartsViewer));

		chartsFilteredListActions = new LinkedList<UpdateableAction>();
		//show charts in view action
		chartsFilteredListActions.add(new ShowChartsInView(exceptionHandler, chartsFilteredList));
		//add a separator
		chartsFilteredListActions.add(new SeparatorAction());
		//'>>'
		chartsFilteredListActions.add(new AddManyToGallery(exceptionHandler, chartsFilteredList, galleryFoldersAndChartsViewer));
	}

	private void updateActions() {
		for (UpdateableAction action : galleryFoldersAndChartsActions) {
			action.setEnabled(false);
			action.update();
		}
		for (UpdateableAction action : chartsFilteredListActions) {
			action.setEnabled(false);
			action.update();
		}
	}

	@Override
	protected void doPopulateControl(LocalElement localElement) throws Exception {
		// load all elements in the localelement
		LocalECoreUtils.loadFully(localElement, true, true);
		super.doPopulateControl(localElement);
		doPopulateGalleryControl(localElement);
	}

	protected void doPopulateGalleryControl(LocalElement localElement) {
		LocalElement rootGalleryFolder = localElement.getElement("Gallery");
		galleryFoldersAndChartsViewer.setInput(localElement);
		if (rootGalleryFolder == null) {
			// we have no root folder, switch to empty gallery component
			switchingCompositeLayout.topControl = emptyGalleryComposite;
			switchingComposite.layout();
		}
		else {
			//select root element of the tree
			Object[] rootElements = ((IStructuredContentProvider)galleryFoldersAndChartsViewer.getContentProvider()).getElements(galleryFoldersAndChartsViewer.getInput());
			galleryFoldersAndChartsViewer.setSelection(new StructuredSelection(rootElements));
			//expand the root node
			galleryFoldersAndChartsViewer.expandToLevel(2);
			// update the actions
			updateActions();
		}
	}

	private void addRootFolder() {
		NameValidator validator = new NameValidator(getLocalElement(), BEViewsElementNames.COMPONENT_GALLERY_FOLDER, "root");
		InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), "New Folder", "Enter the name for a new folder", "root", validator);

		if (dialog.open() == Window.OK) {
			try {
				// get the new name
				String newName = dialog.getValue();
				// create a partition
				LocalElement newFolder = getLocalElement().createLocalElement("Gallery");
				// set the new name on the newly create partition
				newFolder.setName(newName);
				// refresh the tree viewer
				galleryFoldersAndChartsViewer.refresh();
				switchingCompositeLayout.topControl = galleryComposite;
				switchingComposite.layout();
				// select the newly created folder
				galleryFoldersAndChartsViewer.setSelection(new StructuredSelection(new TreeContentNode(null, newFolder)));
				// expand the newly created folder
				galleryFoldersAndChartsViewer.expandToLevel(new TreeContentNode(null, newFolder), TreeViewer.ALL_LEVELS);
			} catch (Exception e) {
				exceptionHandler.logAndAlert("Add New Partition", exceptionHandler.createStatus(IStatus.ERROR, "could not add root gallery folder", e));
			}
		}
	}

	@Override
	protected void handleOutsideElementChange(int change, LocalElement element) {
		try {
			//we will only process view add & delete changes, re-factoring changes are handled by role preference change
			if (element instanceof LocalView && change != IResourceDelta.CHANGED){
				if (change == IResourceDelta.REMOVED){
					//if we are removing
					getLocalElement().refresh(LocalRolePreference.PROP_KEY_VIEW);
				}
				updateComboField((CCombo)propertyWidgets.get(LocalRolePreference.PROP_KEY_VIEW), getLocalElement().getParticle(LocalRolePreference.PROP_KEY_VIEW));
			} else {
				if (BEViewsElementNames.getChartOrTextComponentTypes().contains(element.getElementType())/* && change != IResourceDelta.CHANGED*/){
					doPopulateGalleryControl(getLocalElement());
				}
				//we will only be getting role preference updates due to a refactoring on view/components
				else if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true){
					//reset all the properties
					getLocalElement().refresh(element.getEObject());
					populateControl(getLocalElement());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void dispose() {
		getLocalElement().unsubscribeAll(changeListener);
		changeListener = null;
	}



	class RolePreferenceChangeListener implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement)  {
			// do nothing
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement)  {
			// do nothing
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement)  {
			// check if the top level gallery folder has been removed
			if (removedElement instanceof LocalComponentGalleryFolder) {
				if (((LocalComponentGalleryFolder) removedElement).getParent() == getLocalElement()) {
					// yes, the root folder has been remvoed , switch the composites
					switchingCompositeLayout.topControl = emptyGalleryComposite;
					switchingComposite.layout();
				}
			}
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status)  {
			// do nothing
		}

		@Override
		public String getName()  {
			return "RolePreferenceChangeListener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue)  {
			if (LocalElement.PROP_KEY_NAME.equals(property.getName()) == true) {
				galleryFoldersAndChartsViewer.refresh();
			}
		}

	}

	class ShowChartsInView extends UpdateableAction {

		private CustomFilteredList chartsList;

		private boolean showingChartsInView;

		public ShowChartsInView(ExceptionHandler exHandler, CustomFilteredList chartsList) {
			super(null, ShowChartsInView.class.getName(), "Show Charts In View", exHandler, AS_CHECK_BOX);
			setId(ShowChartsInView.class.getName());
			setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("showchartsinview_16x16.gif"));
			this.chartsList = chartsList;
			this.showingChartsInView = false;
		}

		@Override
		public void update() {
			setEnabled(true);
		}

		@Override
		public void run() {
			if (showingChartsInView == false) {
				try {
					LocalView view = (LocalView) getLocalElement().getElement(BEViewsElementNames.VIEW);
					if (view != null) {
						List<LocalElement> components = view.getComponents(BEViewsElementNames.getChartOrTextComponentTypes());
						//filter the components with current tree viewer selection components
						if (components.isEmpty() == false) {
							//get the current selection
							List<Object> currentSelection = Arrays.asList(chartsList.getSelection());
							//apply the filter
							chartsList.applyRetainer(components);
							currentSelection.retainAll(components);
							chartsList.setSelection(currentSelection.toArray());
							showingChartsInView = true;
						}
					}
				} catch (Exception ex) {
					exHandler.logAndAlert(getText(), exHandler.createStatus(IStatus.ERROR, "could not show charts in view", ex));
				}
			}
			else {
				chartsList.applyRetainer(null);
				showingChartsInView = false;
			}
		}

	}

	class AddManyToGallery extends ViewsTreeViewerAction {

		private CustomFilteredList chartsList;

		public AddManyToGallery(ExceptionHandler exHandler, CustomFilteredList chartsList, TreeViewer treeViewer) {
			super(treeViewer, AddManyToGallery.class.getName(), " >> ", exHandler);
			setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("RightMany.gif"));
			setToolTipText("Add selected charts to gallery");
			this.chartsList = chartsList;
			this.chartsList.addSelectionListener(new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					setEnabled(false);
					update();
				}

			});
		}

		@Override
		public void update() {
			if (chartsList.getSelection() != null && chartsList.getSelection().length > 0) {
				IStructuredSelection treeViewerSelection = (IStructuredSelection) viewer.getSelection();
				if (treeViewerSelection.size() == 1) {
					setEnabled(true);
				}
			}
		}

		@Override
		public void run() {
			LocalElement nextSelection = null;
			try {
				IStructuredSelection treeViewerSelection = (IStructuredSelection) viewer.getSelection();
				if (treeViewerSelection.size() == 1) {
					TreeContentNode selectedNode = (TreeContentNode) treeViewerSelection.getFirstElement();
					LocalElement folder = null;
					if (BEViewsElementNames.COMPONENT_GALLERY_FOLDER.equals(selectedNode.getType()) == true) {
						folder = (LocalElement) selectedNode.getData();
					}
					else if (BEViewsElementNames.getChartOrTextComponentTypes().contains(selectedNode.getType()) == true) {
						folder = (LocalElement) selectedNode.getParent().getData();
					}
					if (folder != null) {
						Object[] selectedCharts = chartsList.getSelection();
						if (selectedCharts.length == chartsList.getInput().size()) {
							String msg = "Are you sure you want to add all the charts?";
							MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(), "Add Charts...", null, msg, MessageDialog.QUESTION, new String[] { "Yes", "No" }, 0);
							int userResponse = dialog.open();
							if (userResponse == MessageDialog.CANCEL) {
								return;
							}
						}
						boolean refresh = false;
						for (Object selectedChartObj : selectedCharts) {
							LocalElement selectedChart = (LocalElement) selectedChartObj;
							if (folder.getElementByID(BEViewsElementNames.COMPONENT, selectedChart.getID()) == null) {
								folder.addElement(BEViewsElementNames.COMPONENT, selectedChart);
								List<LocalElement> chartsListInput = new LinkedList<LocalElement>(chartsList.getInput());
								int index = chartsListInput.indexOf(selectedChart);
								boolean removed = chartsListInput.remove(selectedChart);
								if (removed == true) {
									chartsList.setInput(chartsListInput);
									if (index >= chartsListInput.size()) {
										index--;
									}
									nextSelection = index < 0 ? null : chartsListInput.get(index);
								}
								refresh = true;
							}
						}
						if (refresh == true) {
							viewer.refresh();
							((AbstractTreeViewer) viewer).expandToLevel(selectedNode, 1);
						}

					}
				}
			} catch (Exception e) {
				exHandler.logAndAlert(getText(), exHandler.createStatus(IStatus.ERROR, "could not add selected charts to gallery", e));
			} finally {
				if (nextSelection != null) {
					chartsList.setSelection(new Object[]{nextSelection});
				}
			}
		}

	}

	class RemoveManyFromGallery extends DeleteOrRemoveAction {

		public RemoveManyFromGallery(ExceptionHandler exHandler, TreeViewer treeViewer) {
			super(exHandler, treeViewer);
			setId(RemoveManyFromGallery.class.getName());
			setText(" << ");
			setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("RightMany.gif"));
			setToolTipText("Remove selected charts from gallery");
		}

		@Override
		public void update() {
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			for (Object selectionObj : selection.toList()) {
				TreeContentNode node = (TreeContentNode) selectionObj;
				if (BEViewsElementNames.getChartOrTextComponentTypes().contains(node.getType()) == false) {
					return;
				}
			}
			setEnabled(true);
		}

	}

	class RemoveFolder extends DeleteOrRemoveAction {

		public RemoveFolder(ExceptionHandler exHandler, TreeViewer treeViewer) {
			super(exHandler, treeViewer);
			setId(RemoveFolder.class.getName());
			setText(" << ");
			setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("deletefolder.gif"));
			setToolTipText("Remove selected folder(s) from gallery");
		}

		@Override
		public void update() {
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			for (Object selectionObj : selection.toList()) {
				TreeContentNode node = (TreeContentNode) selectionObj;
				if (BEViewsElementNames.COMPONENT_GALLERY_FOLDER.equals(node.getType()) == false) {
					return;
				}
			}
			setEnabled(true);
		}

	}

	private class CustomFilteredList extends FilteredList  {

		private List<LocalElement> input;

		private List<LocalElement> retain;

		public CustomFilteredList(Composite parent, int style, ILabelProvider labelProvider, boolean ignoreCase, boolean allowDuplicates, boolean matchEmptyString) {
			super(parent, style, labelProvider, ignoreCase, allowDuplicates, matchEmptyString);
//			setComparator(new LocalElementComparator());
		}

		public void setInput(List<LocalElement> input) {
			this.input = input;
			Collections.sort(this.input, new LocalElementComparator());
			List<LocalElement> elements = this.input;
			if (this.retain != null && this.retain.isEmpty() == false) {
				elements = new LinkedList<LocalElement>(this.input);
				elements.retainAll(retain);
			}
			setElements(elements.toArray());
		}

		public List<LocalElement> getInput() {
			return input;
		}

		public void applyRetainer(List<LocalElement> retain) {
			this.retain = retain;
			if (this.input != null) {
				//reset the input to force the retain using 'retain'
				setInput(this.input);
			}
		}

		class LocalElementComparator implements Comparator<LocalElement> {

			@Override
			public int compare(LocalElement o1, LocalElement o2) {
				String o1Text = getLabelProvider().getText(o1);
				String o2Text = getLabelProvider().getText(o2);
				return getIgnoreCase() == true ? o1Text.compareToIgnoreCase(o2Text) : o1Text.compareTo(o2Text);
			}

		}

	}

	private class CustomFilterMatch implements FilterMatcher {

		private LocalElementPropertyMatcher propertyMatcher;

		@Override
		public void setFilter(String pattern, boolean ignoreCase, boolean ignoreWildCards) {
			propertyMatcher = new LocalElementPropertyMatcher(LocalElement.PROP_KEY_NAME, "*"+pattern, ignoreCase);
		}

		@Override
		public boolean match(Object element) {
			return propertyMatcher.match((LocalElement) element);
		}

	}

	private class GalleryFoldersAndChartsTreeViewer extends TreeViewer {

		public GalleryFoldersAndChartsTreeViewer(Composite parent, int style) {
			super(parent, style);
			// set tree content provider
			setContentProvider(new LocalElementTreeContentProvider(exceptionHandler, BEViewsElementNames.ROLE_PREFERENCE, Arrays.asList("Gallery"), true));
			// set tree label provider
			setLabelProvider(new TreeContentNodeLabelProvider());
		}

		public List<LocalElement> getSelectedFolders() {
			IStructuredSelection selection = (IStructuredSelection) getSelection();
			List<LocalElement> folders = new LinkedList<LocalElement>();
			for (int i = 0; i < selection.toArray().length; i++) {
				TreeContentNode selectedNode = (TreeContentNode) selection.toArray()[i];
				if (BEViewsElementNames.COMPONENT_GALLERY_FOLDER.equals(selectedNode.getType()) == false){
					selectedNode = selectedNode.getParent();
				}
				folders.add((LocalElement) selectedNode.getData());
			}
			return folders;
		}



	}
}