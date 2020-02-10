package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalComponentGalleryFolder;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.utils.NameValidator;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 *
 * @author rgupta
 */
public class OldRolePreferencePage extends AbstractEntityEditorPage {

	private Tree galleryFoldersTree;
	private Table componentsTable;
	private Button selectAllCheckBox;
	private TreeItem rootItem;
	private Menu menu;

	public OldRolePreferencePage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
	}

	@Override
	protected void createExtendedProperties(IManagedForm mform, Composite propertiesPanel) throws Exception {
		super.createExtendedProperties(mform, propertiesPanel);
		createComboField(mform, propertiesPanel, getLocalElement().getParticle(LocalRolePreference.PROP_KEY_VIEW));
	}

	@Override
	protected void createFieldSection(IManagedForm managedForm, Composite parent) throws Exception {
		FormToolkit toolkit = managedForm.getToolkit();
		// Create Composite Panel to have section for Galley
		Composite galleryPanel = managedForm.getToolkit().createComposite(parent);
		// Attach the layout data to place the galleryPanel in the parent composite
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = gd.widthHint = 200;
		galleryPanel.setLayoutData(gd);

		// Set the layout as FillLayout of the galleryPanel. We will have one
		// section under this Panel
		galleryPanel.setLayout(new GridLayout());

		// Create section for Galley in Gallery Panel
		Composite gallerySection = createSection(managedForm, galleryPanel, "Gallery");
		gallerySection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		// Set the layout of the section as GridLayout which will contain two
		// columns
		gallerySection.setLayout(new GridLayout(2, false));

		Composite gridComposite = createGridColumnComposite(managedForm, gallerySection, 2);
		((Section) gallerySection).setClient(gridComposite);

		// Create the Folder Tree
		Composite folderTreeComposite = createGalleryFolderTree(managedForm, gridComposite);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = gd.widthHint = 100;
		folderTreeComposite.setLayoutData(gd);
		toolkit.paintBordersFor(folderTreeComposite);
		// Create the Component List
		Composite componentListComposite = createComponentCheckedList(managedForm, gridComposite);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = gd.widthHint = 100;

		componentListComposite.setLayoutData(gd);

	}

	private Composite createGalleryFolderTree(IManagedForm managedForm, Composite parent) throws Exception {
		FormToolkit toolkit = managedForm.getToolkit();
		Composite composite = createGridColumnComposite(managedForm, parent, 1);
		galleryFoldersTree = toolkit.createTree(composite, SWT.SINGLE);
		galleryFoldersTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		menu = new GalleryTreeContextMenu().getMenu();
		galleryFoldersTree.setMenu(menu);
		galleryFoldersTree.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				try {
					galleryItemSelected((TreeItem) e.item);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				try {
					galleryItemSelected((TreeItem) e.item);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		return composite;
	}

	private Composite createComponentCheckedList(IManagedForm managedForm, Composite parent) throws Exception {
		FormToolkit toolkit = managedForm.getToolkit();
		Composite composite = createGridColumnComposite(managedForm, parent, 1);
		/*final Label label = */toolkit.createLabel(composite, "Select Components");

		// Add Select all check Box

		selectAllCheckBox = new Button(composite, SWT.CHECK);
		selectAllCheckBox.setText("Select All");
		selectAllCheckBox.setBackground(new Color(null, 255, 255, 255));
		selectAllCheckBox.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				if (selectAllCheckBox.getSelection()) {
					try {
						selectAll(componentsTable);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						deselectAll(componentsTable);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		componentsTable = toolkit.createTable(composite, SWT.CHECK | SWT.FULL_SELECTION);
		TableColumn firstColumn = new TableColumn(componentsTable, SWT.CENTER);
		firstColumn.setText("Select");
		firstColumn.setWidth(45);

		TableColumn secondColumn = new TableColumn(componentsTable, SWT.LEFT);
		secondColumn.setText("Name");
		secondColumn.setWidth(100);

		TableColumn thirdColumn = new TableColumn(componentsTable, SWT.LEFT);
		thirdColumn.setText("Folder");
		thirdColumn.setWidth(150);

		TableColumn fourthColumn = new TableColumn(componentsTable, SWT.LEFT);
		fourthColumn.setText(LocalRolePreference.PROP_KEY_DISPLAY_NAME);
		fourthColumn.setWidth(150);

		TableColumn fifthColumn = new TableColumn(componentsTable, SWT.LEFT);
		fifthColumn.setText("Description");
		fifthColumn.setWidth(200);



		componentsTable.setHeaderVisible(true);
		componentsTable.setLinesVisible(true);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		componentsTable.setLayoutData(gd);

		componentsTable.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				try {
					componentSelected(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// Suppress it. Of no use
				// componentSelected(e);
			}

		});

		return composite;
	}

	@Override
	protected void doPopulateControl(LocalElement localElement) throws Exception {
		try {
			localElement.getChildren(LocalRolePreference.PROP_KEY_VIEW);
			super.doPopulateControl(localElement);
			galleryFoldersTree.removeAll();
			//update gallery tree
			LocalComponentGalleryFolder rootCategory = ((LocalRolePreference) localElement).getComponentGallery();
			if (rootCategory != null) {
				loadGallery(rootCategory);
				rootItem = new TreeItem(galleryFoldersTree, SWT.NONE);
				rootItem.setText(rootCategory.getName());
				rootItem.setData(rootCategory);
				createSubTreeItems(rootCategory, rootItem);
			} else {
				rootItem = new TreeItem(galleryFoldersTree, SWT.NONE);
				rootItem.setText("No Galleries defined");
				rootItem.setData(null);
			}
			//update component selection table
			populateComponentsTable(localElement);
			// Make the root node of the gallery tree selected by default
			galleryFoldersTree.setSelection(new TreeItem[] { rootItem });
			galleryItemSelected(rootItem);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateComponentsTable(LocalElement localElement) throws Exception {
		List<LocalElement> dashboardComponents = localElement.getRoot().getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
		Collections.sort(dashboardComponents);
		componentsTable.removeAll();
		for (Iterator<LocalElement> itComponents = dashboardComponents.iterator(); itComponents.hasNext();) {
			LocalConfig localConfig = (LocalConfig) itComponents.next();
			TableItem tableItem = new TableItem(componentsTable, 0);
			tableItem.setText(new String[] { "", localConfig.getName(), localConfig.getFolder(), localConfig.getDisplayName(), localConfig.getDescription() });
			tableItem.setData(localConfig);
		}
	}

	private void loadGallery(LocalComponentGalleryFolder gallery) throws Exception {
		gallery.getComponents();
		for (LocalElement galleryFolder : gallery.getSubFolders()) {
			loadGallery((LocalComponentGalleryFolder) galleryFolder);
		}
	}

	private void componentSelected(SelectionEvent e) throws Exception {
		TableItem tableItem = (TableItem) e.item;
		int detail = e.detail;
		if (detail == SWT.CHECK) {
			if (galleryFoldersTree.getSelectionCount() == 0) {
				// Something wrong, user does not have any item selected in tree
				// and updating the table.
				return;
			}
			LocalComponentGalleryFolder selectedElementCategory = (LocalComponentGalleryFolder) galleryFoldersTree.getSelection()[0].getData();
			LocalConfig selectedConfig = (LocalConfig) tableItem.getData();
			// Check state has changed
			try {
				if (tableItem.getChecked()) {
					selectedElementCategory.addComponent(selectedConfig);
				} else {
					selectAllCheckBox.setSelection(false);
					selectedElementCategory.removeComponent(selectedConfig);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void galleryItemSelected(TreeItem treeItem) throws Exception {
		LocalComponentGalleryFolder selectedElementCategory = (LocalComponentGalleryFolder) treeItem.getData();
		uncheckAll(componentsTable);
		selectAllCheckBox.setSelection(false);
		if (selectedElementCategory == null) {
			selectAllCheckBox.setEnabled(false);
			componentsTable.setEnabled(false);
			return;
		}
		selectAllCheckBox.setEnabled(true);
		componentsTable.setEnabled(true);
		List<LocalElement> lstComponentConfigs = selectedElementCategory.getComponents();
		for (Iterator<LocalElement> itComponentConfigs = lstComponentConfigs.iterator(); itComponentConfigs.hasNext();) {
			LocalConfig componentConfig = (LocalConfig) itComponentConfigs.next();
			TableItem[] tableItems = componentsTable.getItems();
			for (int itemIndex = 0; itemIndex < tableItems.length; itemIndex++) {
				TableItem tableItem = tableItems[itemIndex];
				LocalConfig tableItemConfig = (LocalConfig) tableItem.getData();
				if (tableItemConfig.getScopeName().equals(componentConfig.getScopeName())) {
					tableItem.setChecked(true);
				}
			}
		}

		// Select the selectAll check box if all the table items are selected.

		boolean allChecked = true;
		TableItem[] tableItems = componentsTable.getItems();
		for (int itemIndex = 0; itemIndex < tableItems.length; itemIndex++) {
			TableItem tableItem = tableItems[itemIndex];
			if (!tableItem.getChecked()) {
				allChecked = false;
				break;
			}
		}
		if (allChecked) {
			selectAllCheckBox.setSelection(true);
		}
	}

	private void uncheckAll(Table table2) {
		TableItem[] tableItems = componentsTable.getItems();
		for (int itemIndex = 0; itemIndex < tableItems.length; itemIndex++) {
			tableItems[itemIndex].setChecked(false);
		}
	}

	private void selectAll(Table table2) throws Exception {
		TableItem[] tableItems = componentsTable.getItems();
		for (int itemIndex = 0; itemIndex < tableItems.length; itemIndex++) {
			TableItem tableItem = tableItems[itemIndex];
			LocalComponentGalleryFolder selectedElementCategory = (LocalComponentGalleryFolder) galleryFoldersTree.getSelection()[0].getData();
			LocalConfig selectedConfig = (LocalConfig) tableItem.getData();
			if (null != selectedElementCategory.getComponentByName(selectedConfig.getName(), selectedConfig.getFolder())) {
				selectedElementCategory.removeComponent(selectedConfig);
			}
			tableItem.setChecked(true);
			selectedElementCategory.addComponent(selectedConfig);
		}
	}

	private void deselectAll(Table table2) throws Exception {
		TableItem[] tableItems = componentsTable.getItems();
		for (int itemIndex = 0; itemIndex < tableItems.length; itemIndex++) {
			TableItem tableItem = tableItems[itemIndex];
			tableItem.setChecked(false);
			LocalComponentGalleryFolder selectedElementCategory = (LocalComponentGalleryFolder) galleryFoldersTree.getSelection()[0].getData();
			LocalConfig selectedConfig = (LocalConfig) tableItem.getData();
			selectedElementCategory.removeComponent(selectedConfig);
		}

	}

	private void createSubTreeItems(LocalComponentGalleryFolder parentCategory, TreeItem parentTreeItem) throws Exception {
		List<LocalElement> subCategories = parentCategory.getSubFolders();
		if (subCategories == null) {
			return;
		}
		for (Iterator<LocalElement> itSubCategories = subCategories.iterator(); itSubCategories.hasNext();) {
			LocalComponentGalleryFolder childCategory = (LocalComponentGalleryFolder) itSubCategories.next();
			TreeItem treeChildItem = createTreeItem(parentTreeItem, childCategory);
			createSubTreeItems(childCategory, treeChildItem);
		}
		parentTreeItem.setExpanded(true);
	}

	private TreeItem createTreeItem(TreeItem parentTreeItem, LocalComponentGalleryFolder childCategory) throws Exception {
		TreeItem treeChildItem = new TreeItem(parentTreeItem, SWT.NONE);
		treeChildItem.setText(childCategory.getName());
		treeChildItem.setData(childCategory);
		return treeChildItem;
	}

	/**
	 * @param mform
	 * @param galleryPanel
	 * @param string
	 * @return
	 */
	private Composite createGridColumnComposite(IManagedForm mform, Composite parent, int numColumns) {
		FormToolkit toolkit = mform.getToolkit();
		Composite gridComposite = toolkit.createComposite(parent);
		gridComposite.setLayout(new GridLayout(numColumns, false));
		return gridComposite;
	}

	class GalleryTreeContextMenu {
		private IAction add;
		private IAction remove;
		private IAction edit;

		/**
		 *
		 */
		public GalleryTreeContextMenu() {
		}

		private Menu getMenu() {
			makeActions();
			return hookContextMenu();
		}

		private Menu hookContextMenu() {

			MenuManager menuMgr = new MenuManager("#PopupMenu");
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					fillContextMenu(manager);
				}
			});

			return menuMgr.createContextMenu(galleryFoldersTree);
		}

		private void fillContextMenu(IMenuManager manager) {
			Object data = getSelectedItem().getData();
			if (data == null) {
				manager.add(add);
			} else {
				manager.add(add);
				manager.add(edit);
				manager.add(remove);
			}
		}

		private void makeActions() {
			add = new Action() {
				@Override
				public void run() {
					try {
						addTreeMenuSelected();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			add.setText("New");

			edit = new Action() {
				@Override
				public void run() {
					try {
						editTreeMenuSelected();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			edit.setText("Rename");

			remove = new Action() {
				@Override
				public void run() {
					try {
						deleteTreeMenuSelected();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			remove.setText("Delete");
		}
	}

	private void deleteTreeMenuSelected() throws Exception {
		LocalComponentGalleryFolder selectedElementCategory = (LocalComponentGalleryFolder) getSelectedItem().getData();
		LocalElement folderParent = selectedElementCategory.getParent();
		if (folderParent instanceof LocalRolePreference) {
			((LocalRolePreference) folderParent).removeComponentGallery(selectedElementCategory);
		} else {
			((LocalComponentGalleryFolder) folderParent).removeSubFolder(selectedElementCategory);
		}

		if (getSelectedItem() == rootItem) {
			rootItem.dispose();
			rootItem = new TreeItem(galleryFoldersTree, SWT.NONE);
			rootItem.setText("No Galleries defined");
			rootItem.setData(null);
			// Make the root node of the gallery tree selected by default
			galleryFoldersTree.setSelection(new TreeItem[] { rootItem });
			galleryItemSelected(rootItem);
		} else {
			// Make the parent node of the deleted item selected
			TreeItem newSelectedItem = getSelectedItem().getParentItem();
			getSelectedItem().dispose();
			galleryFoldersTree.setSelection(new TreeItem[] { newSelectedItem });
			galleryItemSelected(newSelectedItem);

		}
	}

	private void editTreeMenuSelected() throws Exception {
		TreeItem selectedItem = getSelectedItem();
		LocalComponentGalleryFolder selectedElementCategory = (LocalComponentGalleryFolder) selectedItem.getData();

		NameValidator nameValidator = null;
		if (selectedItem.getParentItem() == null) {
			// Root i.e. RolePreference
			nameValidator = new NameValidator(getLocalElement(), "Gallery", selectedElementCategory.getName());
		} else {
			// Sub Folder i.e. ComponentGalleryFolder
			nameValidator = new NameValidator((LocalComponentGalleryFolder) selectedItem.getParentItem().getData(), "SubFolder", selectedElementCategory.getName());
		}

		InputDialog dialog = new InputDialog(menu.getShell(), "Rename Gallery Folder", "Enter the new name for the folder", selectedElementCategory.getName(), nameValidator);
		if (dialog.open() == Window.OK) {
			String newValue = dialog.getValue();
			selectedElementCategory.setName(newValue);
			selectedElementCategory.setDisplayName(newValue);
			selectedItem.setText(newValue);
		}
	}

	/**
	 * @param menu
	 * @throws Exception
	 */
	private void addTreeMenuSelected() throws Exception {
		LocalComponentGalleryFolder selectedElementCategory = (LocalComponentGalleryFolder) getSelectedItem().getData();

		NameValidator nameValidator = null;
		if (selectedElementCategory == null) {
			// Root i.e. RolePreference
			nameValidator = new NameValidator(getLocalElement(), "Gallery");
		} else {
			// Sub Folder i.e. ComponentGalleryFolder
			nameValidator = new NameValidator(selectedElementCategory, "SubFolder");
		}

		InputDialog dialog = new InputDialog(menu.getShell(), "New Gallery Folder", "Enter the name for the folder", "", nameValidator);

		if (dialog.open() == Window.OK) {
			if (selectedElementCategory != null) {
				LocalComponentGalleryFolder subFolder = selectedElementCategory.createSubFolder(dialog.getValue());
				TreeItem treeItem = createTreeItem(getSelectedItem(), subFolder);
				getSelectedItem().setExpanded(true);
				galleryFoldersTree.setSelection(new TreeItem[] { treeItem });
				galleryItemSelected(treeItem);
			} else {
				// First Gallery to add here
				LocalRolePreference role = (LocalRolePreference) getLocalElement();
				LocalComponentGalleryFolder rootCategory = role.createComponentGallery(dialog.getValue());
				rootItem.setText(rootCategory.getName());
				rootItem.setData(rootCategory);
				galleryItemSelected(rootItem);
			}
		}
	}

	private TreeItem getSelectedItem() {
		if (galleryFoldersTree.getSelectionCount() > 0) {
			return galleryFoldersTree.getSelection()[0];
		}
		return null;
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
				List<String> selectionPath = getSelectionPath(galleryFoldersTree);
				LocalComponentGalleryFolder gallery = ((LocalRolePreference) getLocalElement()).getComponentGallery();
				if (BEViewsElementNames.getChartOrTextComponentTypes().contains(element.getElementType())/* && change != IResourceDelta.CHANGED*/){
					if (change == IResourceDelta.REMOVED && gallery != null){
						Stack<LocalElement> folders = new Stack<LocalElement>();
						folders.add(gallery);
						while (folders.isEmpty() == false){
							LocalComponentGalleryFolder folder = (LocalComponentGalleryFolder) folders.pop();
							folder.refreshComponents();
							folders.addAll(folder.getSubFolders());
						}
					}
					populateComponentsTable(getLocalElement());
					setSelection(galleryFoldersTree, selectionPath);
					galleryItemSelected(galleryFoldersTree.getSelection()[0]);
					componentsTable.setEnabled(componentsTable.getItemCount() != 0);
					selectAllCheckBox.setEnabled(componentsTable.getEnabled());
				}
				//we will only be getting role preference updates due to a refactoring on view/components
				else if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true){
					//reset all the properties
					getLocalElement().refresh(element.getEObject());
					if (gallery != null) {
						//reset all referenced components
						Stack<LocalElement> folders = new Stack<LocalElement>();
						folders.add(gallery);
						while (folders.isEmpty() == false){
							LocalComponentGalleryFolder folder = (LocalComponentGalleryFolder) folders.pop();
							folder.refreshComponents();
							folders.addAll(folder.getSubFolders());
						}
					}
					populateControl(getLocalElement());
					setSelection(galleryFoldersTree, selectionPath);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<String> getSelectionPath(Tree tree){
		TreeItem[] selection = this.galleryFoldersTree.getSelection();
		if (selection != null && selection.length == 1){
			List<String> elements = new ArrayList<String>();
			TreeItem treeItem = selection[0];
			do {
				elements.add(treeItem.getText());
				treeItem = treeItem.getParentItem();
			} while (treeItem != null);
			Collections.reverse(elements);
			return elements;
		}
		return Collections.emptyList();
	}

	private void setSelection(Tree tree, List<String> elements){
		TreeItem selection = null;
		TreeItem[] items = tree.getItems();
		for (String element : elements) {
			for (TreeItem treeItem : items) {
				if (element.equals(treeItem.getText()) == true){
					selection = treeItem;
					break;
				}
			}
			if (selection == null){
				return;
			}
			items = selection.getItems();
		}
		tree.setSelection(selection);
	}

}