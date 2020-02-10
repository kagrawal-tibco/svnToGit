package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import java.util.Arrays;
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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalComponentGalleryFolder;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
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
import com.tibco.cep.studio.dashboard.ui.editors.views.DeleteOrRemoveChildrenAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.LocalElementTreeContentProvider;
import com.tibco.cep.studio.dashboard.ui.editors.views.MoveDownAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.MoveUpAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.RenameAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNode;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNodeLabelProvider;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.forms.ReadOnlyPropertyForm;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.ui.utils.NameValidator;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 *
 */
public class RolePreferencePage extends AbstractEntityEditorPage {

	private RolePreferenceChangeListener changeListener;

	private ExceptionHandler exceptionHandler;

	private Composite switchingComposite;
	private StackLayout switchingCompositeLayout;

	private Composite emptyGalleryComposite;

	// gallery as tree
	private Composite galleryComposite;
	private TreeViewer treeViewer;
	private ITreeContentProvider treeViewerContentProvider;
	private ISelectionChangedListener treeViewerSelectionChangedListener;

	// actions
	private List<UpdateableAction> actions;
	private ToolBar toolBar;

	// property sheet
	private Composite cmp_PropertySheet;
	private StackLayout propertySheetLayout;

	private SimplePropertyForm emptyForm;

	private SimplePropertyForm folderForm;

	private SimplePropertyForm componentForm;

	public RolePreferencePage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
		exceptionHandler = DashboardUIPlugin.getInstance().getExceptionHandler();
		changeListener = new RolePreferenceChangeListener();
		localElement.subscribeToAll(changeListener);
		localElement.subscribe(changeListener, BEViewsElementNames.COMPONENT_GALLERY_FOLDER);
	}

	@Override
	protected String getElementTypeName() {
		return "Role Preference";
	}

	@Override
	protected void createExtendedProperties(IManagedForm mform, Composite propertiesPanel) throws Exception {
		super.createExtendedProperties(mform, propertiesPanel);
		createComboField(mform, propertiesPanel, getLocalElement().getParticle(LocalRolePreference.PROP_KEY_VIEW));
	}

	@Override
	protected void createFieldSection(IManagedForm mform, Composite parent) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		Section section = createSection(mform, parent, "Gallery Configuration");

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
				addNewFolder();
			}

		});

		emptyGalleryComposite.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				addNewFolder();
			}

		});

		// create the main component
		galleryComposite = toolkit.createComposite(switchingComposite, SWT.NONE);
		galleryComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		galleryComposite.setLayout(new GridLayout(2, true));

		Composite treeComposite = toolkit.createComposite(galleryComposite, SWT.NONE);
		treeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout treeCompositeLayout = new GridLayout(2, false);
		treeCompositeLayout.marginHeight = 0;
		treeCompositeLayout.marginWidth = 0;
		treeComposite.setLayout(treeCompositeLayout);

		// tree viewer
		treeViewer = new TreeViewer(treeComposite, SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER);
		treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		toolkit.adapt(treeViewer.getControl(), true, true);

		// create all the actions
		createActions();

		// create tool bar
		toolBar = new ToolBar(treeComposite, SWT.VERTICAL);
		toolkit.adapt(toolBar);

		// add all the actions
		int i = 0;
		for (IAction action : actions) {
			IContributionItem aci = null;
			if (action instanceof SeparatorAction) {
				aci = new Separator();
			} else {
				aci = new ActionContributionItem(action);
			}
			aci.fill(toolBar, i);
			i++;
		}

		// property sheet
		cmp_PropertySheet = toolkit.createComposite(galleryComposite, SWT.NONE);
		cmp_PropertySheet.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		propertySheetLayout = new StackLayout();

		cmp_PropertySheet.setLayout(propertySheetLayout);

		// create various property sheets
		createPropertySheets(toolkit);

		// create tree content provider
		treeViewerContentProvider = new LocalElementTreeContentProvider(exceptionHandler, BEViewsElementNames.ROLE_PREFERENCE, Arrays.asList("Gallery"), true);
		treeViewer.setContentProvider(treeViewerContentProvider);

		// create tree label provider
		treeViewer.setLabelProvider(new TreeContentNodeLabelProvider());

		// create treeViewerSelectionChangedListener
		treeViewerSelectionChangedListener = new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				treeViewerSelectionChanged();
			}

		};
		treeViewer.addSelectionChangedListener(treeViewerSelectionChangedListener);

		// add pop up menu
		MenuManager menuManager = new MenuManager("#PopupMenu");
		// add all the actions
		for (IAction action : actions) {
			if (action instanceof SeparatorAction) {
				menuManager.add(new Separator());
			} else {
				menuManager.add(action);
			}
		}
		treeViewer.getTree().setMenu(menuManager.createContextMenu(treeViewer.getTree()));

		//trigger tree selection changed to update the actions and property sheets
		treeViewerSelectionChanged();

		// by default set the top control as gallery composite
		switchingCompositeLayout.topControl = galleryComposite;

		section.setClient(switchingComposite);
	}

	private void createPropertySheets(FormToolkit toolkit) throws Exception {
		// empty property sheet
		emptyForm = new SimplePropertyForm("", toolkit, cmp_PropertySheet, false);
		emptyForm.init();
		emptyForm.setExceptionHandler(exceptionHandler);

		// folder property sheet
		folderForm = new SimplePropertyForm("Folder Properties", toolkit, cmp_PropertySheet, true, true);
		folderForm.addProperty("Name", (SynProperty) getLocalElement().getProperty(LocalElement.PROP_KEY_NAME));
		folderForm.init();
		folderForm.setExceptionHandler(exceptionHandler);

		// component property sheet
		componentForm = new ReadOnlyPropertyForm("Chart Properties", toolkit, cmp_PropertySheet, true, true);
		// display name
		componentForm.addProperty("Display Name", ViewsConfigReader.getInstance().getProperty("Component", LocalConfig.PROP_KEY_DISPLAY_NAME));
		// description
		componentForm.addPropertyAsText("Description", (SynProperty) getLocalElement().getProperty(LocalElement.PROP_KEY_DESCRIPTION), true);
		// colspan
		componentForm.addPropertyAsText("Width", ViewsConfigReader.getInstance().getProperty("ChartComponent", "ColSpan"), false);
		// rowspan
		componentForm.addPropertyAsText("Height", ViewsConfigReader.getInstance().getProperty("ChartComponent", "RowSpan"), false);
		componentForm.init();
		componentForm.setExceptionHandler(exceptionHandler);

	}

	private void createActions() {
		actions = new LinkedList<UpdateableAction>();
		// add new folder action
		actions.add(new AddNewGalleryFolderAction(exceptionHandler, treeViewer));
		// add charts action
		actions.add(new AddChartsToGalleryFolderAction(exceptionHandler, treeViewer));
//		// add charts from view action
//		actions.add(new AddChartsFromViewToGalleryFolderAction(exceptionHandler, treeViewer));
		// add a separator
		actions.add(new SeparatorAction());
		// rename action
		actions.add(new RenameAction(exceptionHandler, treeViewer));
		// move up action
		actions.add(new MoveUpAction(exceptionHandler, treeViewer));
		// move down action
		actions.add(new MoveDownAction(exceptionHandler, treeViewer));
		// add a separator
		actions.add(new SeparatorAction());
		// delete or remove action
		actions.add(new DeleteOrRemoveAction(exceptionHandler, treeViewer));
		// delete or remove children
		actions.add(new DeleteOrRemoveChildrenAction(exceptionHandler, treeViewer));
	}

	private void updateActions() {
		for (UpdateableAction action : actions) {
			action.setEnabled(false);
			action.update();
		}
	}

	@Override
	protected void doPopulateControl(LocalElement localElement) throws Exception {
		// load all elements in the localelement
		LocalECoreUtils.loadFully(localElement, true, true);
		super.doPopulateControl(localElement);
		LocalElement rootGalleryFolder = localElement.getElement("Gallery");
		treeViewer.setInput(localElement);
		if (rootGalleryFolder == null) {
			// we have no root folder, switch to empty gallery component
			switchingCompositeLayout.topControl = emptyGalleryComposite;
			switchingComposite.layout();
		}
		else {
			//if no selection, select root
			if (treeViewer.getSelection().isEmpty() == true) {
				// expand & select the newly created folder
				expandAndSelect(new TreeContentNode(null, rootGalleryFolder));
			}
		}
	}

	private void updatePropertySheet() {
		BaseForm form = emptyForm;
		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		if (selection.size() == 1) {
			TreeContentNode selectedNode = (TreeContentNode) selection.getFirstElement();
			if (selectedNode.getType().equals(BEViewsElementNames.COMPONENT_GALLERY_FOLDER) == true) {
				form = folderForm;
			} else if (BEViewsElementNames.getChartOrTextComponentTypes().contains(selectedNode.getType()) == true) {
				form = componentForm;
			}
			if (selectedNode.isElement() == true) {
				try {
					form.setInput((LocalElement) selectedNode.getData());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (propertySheetLayout.topControl != form.getControl()) {
			propertySheetLayout.topControl = form.getControl();
			cmp_PropertySheet.layout(true);
		}
	}

	private void addNewFolder() {
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
				//refresh the tree viewer
				treeViewer.refresh();
				switchingCompositeLayout.topControl = galleryComposite;
				switchingComposite.layout();
				// expand & select the newly created folder
				expandAndSelect(new TreeContentNode(null, newFolder));
			} catch (Exception e) {
				exceptionHandler.logAndAlert("Add New Partition", exceptionHandler.createStatus(IStatus.ERROR, "could not add root gallery folder", e));
			}
		}
	}

	protected void expandAndSelect(TreeContentNode treeContentNode) {
		treeViewer.expandToLevel(treeContentNode, TreeViewer.ALL_LEVELS);
		// select the newly create folder
		treeViewer.setSelection(new StructuredSelection(treeContentNode), true);
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
			} else if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true){
				//we will only be getting role preference updates due to a refactoring on view/components
				//reset all the properties
				//remember treeViewer selection
				ISelection selection = treeViewer.getSelection();
				getLocalElement().refresh(element.getEObject());
				populateControl(getLocalElement());
				//update the selection
				treeViewer.setSelection(selection, true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void treeViewerSelectionChanged() {
		// update the property sheet
		updatePropertySheet();
		// update all the actions
		updateActions();
	}


	@Override
	public void dispose() {
		getLocalElement().unsubscribeAll(changeListener);
		changeListener = null;
	}

	class RolePreferenceChangeListener implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			//do nothing
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
			//do nothing
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
			//check if the top level gallery folder has been removed
			if (removedElement instanceof LocalComponentGalleryFolder) {
				if (((LocalComponentGalleryFolder) removedElement).getParent() == getLocalElement()) {
					//yes, the root folder has been remvoed , switch the composites
					switchingCompositeLayout.topControl = emptyGalleryComposite;
					switchingComposite.layout();
				}
			}
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
			//do nothing
		}

		@Override
		public String getName() {
			return "RolePreferenceChangeListener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			if (LocalElement.PROP_KEY_NAME.equals(property.getName()) == true) {
				treeViewer.refresh();
			}
		}

	}
}