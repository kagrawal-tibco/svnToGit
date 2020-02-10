package com.tibco.cep.studio.dashboard.ui.editors.views.page;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.actions.SeparatorAction;
import com.tibco.cep.studio.dashboard.ui.actions.UpdateableAction;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.editors.views.DeleteOrRemoveAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.LocalElementTreeContentProvider;
import com.tibco.cep.studio.dashboard.ui.editors.views.RenameAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNode;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNodeLabelProvider;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.DefaultExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.forms.ReadOnlyPropertyForm;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class PagePage extends AbstractEntityEditorPage {

	private ExceptionHandler exceptionHandler;

	// page as tree
	private TreeViewer treeViewer;
	private LocalElementTreeContentProvider treeViewerContentProvider;
	private ISelectionChangedListener treeViewerSelectionChangedListener;

	// actions
	private List<UpdateableAction> actions;
	private ToolBar toolBar;

	// property sheet
	private Composite cmp_PropertySheet;
	private StackLayout propertySheetLayout;
	private SimplePropertyForm emptyPropertyForm;
	private SimplePropertyForm partitionPropertyForm;
	private SimplePropertyForm panelPropertyForm;
	private SimplePropertyForm chartPropertyForm;
	private SimplePropertyForm nonChartPropertyForm;


	public PagePage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
		exceptionHandler = new DefaultExceptionHandler(DashboardUIPlugin.PLUGIN_ID);
	}

	@Override
	protected String getElementTypeName() {
		return "Dashboard Page";
	}

	@Override
	protected void createFieldSection(IManagedForm mform, Composite parent) throws Exception {
		FormToolkit toolkit = mform.getToolkit();

		Section section = createSection(mform, parent, "Layout");

		// create the main component
		Composite pageComposite = toolkit.createComposite(section, SWT.NONE);
		pageComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		pageComposite.setLayout(new GridLayout(2, true));

		Composite treeComposite = toolkit.createComposite(pageComposite, SWT.NONE);
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
		for (Action action : actions) {
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
		cmp_PropertySheet = toolkit.createComposite(pageComposite, SWT.NONE);
		cmp_PropertySheet.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		cmp_PropertySheet.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		propertySheetLayout = new StackLayout();
		cmp_PropertySheet.setLayout(propertySheetLayout);

		// create various property sheets
		createPropertySheets(toolkit);

		// create tree content provider
		treeViewerContentProvider = new LocalElementTreeContentProvider(exceptionHandler, BEViewsElementNames.DASHBOARD_PAGE, null, true);
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

		// create a page editor action updater
		//pageEditorActionsUpdater = new ElementsOnlyPageEditorActionsUpdater(exceptionHandler, getEditorInput(), actions);

		// add pop up menu
		MenuManager menuManager = new MenuManager("#PopupMenu");
		// add all the actions
		for (Action action : actions) {
			if (action instanceof SeparatorAction) {
				menuManager.add(new Separator());
			} else {
				menuManager.add(action);
			}
		}
		treeViewer.getTree().setMenu(menuManager.createContextMenu(treeViewer.getTree()));

		//trigger tree selection changed to update the actions and property sheets
		treeViewerSelectionChanged();
		//set the page composite as the client
		section.setClient(pageComposite);
	}



	private void createPropertySheets(FormToolkit toolkit) throws Exception {
		// empty property sheet
		emptyPropertyForm = new SimplePropertyForm("", toolkit, cmp_PropertySheet, false);
		emptyPropertyForm.setExceptionHandler(exceptionHandler);
		emptyPropertyForm.init();

		// partition element
		partitionPropertyForm = new SimplePropertyForm("Partition Properties", toolkit, cmp_PropertySheet, true, true);
		partitionPropertyForm.addProperty(new SpanSpinnerControl(partitionPropertyForm, "Width (%)", ViewsConfigReader.getInstance().getProperty("Partition", "SpanPercentage")));
		partitionPropertyForm.setExceptionHandler(exceptionHandler);
		partitionPropertyForm.init();

		// panel element
		panelPropertyForm = new SimplePropertyForm("Panel Properties", toolkit, cmp_PropertySheet, true, true);
		panelPropertyForm.addProperty(new SpanSpinnerControl(panelPropertyForm, "Height (%)", ViewsConfigReader.getInstance().getProperty("Panel", "SpanPercentage")));
		panelPropertyForm.setExceptionHandler(exceptionHandler);
		panelPropertyForm.init();

		// chart element
		chartPropertyForm = new ReadOnlyPropertyForm("Chart Properties", toolkit, cmp_PropertySheet, true, true);
		// display name
		chartPropertyForm.addProperty("Display Name", ViewsConfigReader.getInstance().getProperty("Component", LocalConfig.PROP_KEY_DISPLAY_NAME));
		// description
		chartPropertyForm.addPropertyAsText("Description", (SynProperty) getLocalElement().getProperty(LocalElement.PROP_KEY_DESCRIPTION), true);
		// colspan
		chartPropertyForm.addPropertyAsText("Width", ViewsConfigReader.getInstance().getProperty("ChartComponent", "ColSpan"), false);
		// rowspan
		chartPropertyForm.addPropertyAsText("Height", ViewsConfigReader.getInstance().getProperty("ChartComponent", "RowSpan"), false);
		chartPropertyForm.setExceptionHandler(exceptionHandler);
		chartPropertyForm.init();

		// non chart element
		nonChartPropertyForm = new ReadOnlyPropertyForm("Component Properties", toolkit, cmp_PropertySheet, true, true);
		// display name
		nonChartPropertyForm.addProperty("Display Name", ViewsConfigReader.getInstance().getProperty("Component", LocalConfig.PROP_KEY_DISPLAY_NAME));
		// description
		nonChartPropertyForm.addPropertyAsText("Description", (SynProperty) getLocalElement().getProperty(LocalElement.PROP_KEY_DESCRIPTION), true);
		nonChartPropertyForm.setExceptionHandler(exceptionHandler);
		nonChartPropertyForm.init();
	}

	private void createActions() {
		actions = new LinkedList<UpdateableAction>();
		//add child action
		actions.add(new AddChildToPageAction(exceptionHandler, treeViewer));
		// add a separator
		actions.add(new SeparatorAction());
		// add rename action
		actions.add(new RenameAction(exceptionHandler, treeViewer));
		// add move up action
		actions.add(new com.tibco.cep.studio.dashboard.ui.editors.views.MoveUpAction(exceptionHandler, treeViewer));
		// add move down action
		actions.add(new com.tibco.cep.studio.dashboard.ui.editors.views.MoveDownAction(exceptionHandler, treeViewer));
		// add separator
		actions.add(new SeparatorAction());
		//add delete or remove action
		actions.add(new DeleteOrRemoveAction(exceptionHandler, treeViewer));
		//add delete or remove children action
		actions.add(new com.tibco.cep.studio.dashboard.ui.editors.views.DeleteOrRemoveChildrenAction(exceptionHandler, treeViewer));
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
		// set input on the tree viewer
		treeViewer.setInput(localElement);
		treeViewer.expandAll();
	}

	private void updatePropertySheet() {
		BaseForm formToShow = emptyPropertyForm;
		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		if (selection.size() == 1) {
			TreeContentNode selectedNode = (TreeContentNode) selection.getFirstElement();
			if (selectedNode.isElement() == true) {
				if (BEViewsElementNames.PARTITION.equals(selectedNode.getType()) == true) {
					formToShow = partitionPropertyForm;
				}
				else if (BEViewsElementNames.PANEL.equals(selectedNode.getType()) == true) {
					formToShow = panelPropertyForm;
				}
				else if (BEViewsElementNames.TEXT_OR_CHART_COMPONENT.contains(selectedNode.getType()) == true) {
					formToShow = chartPropertyForm;
				}
				else if (selectedNode.getData() instanceof LocalComponent) {
					formToShow = nonChartPropertyForm;
				}
			}
			try {
				formToShow.setInput((LocalElement) selectedNode.getData());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (propertySheetLayout.topControl != formToShow.getControl()) {
			propertySheetLayout.topControl = formToShow.getControl();
			cmp_PropertySheet.layout(true);
		}
	}

	@Override
	public void dispose() {
		treeViewer.removeSelectionChangedListener(treeViewerSelectionChangedListener);
		treeViewerSelectionChangedListener = null;
		emptyPropertyForm.dispose();
		partitionPropertyForm.dispose();
		panelPropertyForm.dispose();
		chartPropertyForm.dispose();
		nonChartPropertyForm.dispose();
	}

	@Override
	protected void handleOutsideElementChange(int change, LocalElement element) {
		try {
			if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true) {
				ISelection selection = treeViewer.getSelection();
				// reset all the properties
				getLocalElement().refresh(element.getEObject());
				// updateComboField((CCombo)propertyWidgets.get("DisplayMode"), (SynProperty) getLocalElement().getProperty("DisplayMode"));
				// reset all referenced children
				for (LocalElement partition : getLocalElement().getChildren(BEViewsElementNames.PARTITION)) {
					for (LocalElement panel : partition.getChildren(BEViewsElementNames.PANEL)) {
						panel.refresh(BEViewsElementNames.COMPONENT);
					}
				}
				populateControl(getLocalElement());
				treeViewer.setSelection(selection);
			}
		} catch (Exception e) {
			exceptionHandler.log(new Status(IStatus.ERROR, exceptionHandler.getPluginId(), "could not refresh " + getEditorInput().getName(), e));
		}
	}

	private void treeViewerSelectionChanged() {
		// update the property sheet
		updatePropertySheet();
		// update all the actions
		updateActions();
	}
}
