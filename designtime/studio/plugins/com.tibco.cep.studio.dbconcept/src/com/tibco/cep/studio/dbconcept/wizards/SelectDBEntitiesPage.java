package com.tibco.cep.studio.dbconcept.wizards;

import java.util.Map;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.DBSchema;
import com.tibco.cep.studio.dbconcept.palettes.tools.DBCeptGenHelper;
import com.tibco.cep.studio.dbconcept.palettes.tools.SelectDBELabelProvider;
import com.tibco.cep.studio.dbconcept.palettes.tools.SelectDBETreeContentProvider;
import com.tibco.cep.studio.dbconcept.palettes.tools.SelectDBETreeModel;
import com.tibco.cep.studio.dbconcept.utils.Messages;

/**
 * 
 * @author majha
 * 
 */
public class SelectDBEntitiesPage extends WizardPage implements
		IPageChangingListener, IPageChangedListener {

	DBCeptGenHelper helper;

	Composite parentComposite = null;

	private Button btnSelectAll;
	private Button btnClearAll;
	private Button btnGenerate;

	private CheckboxTreeViewer dbCheckBoxTree;
	private SelectDBETreeContentProvider treeContentProvider;
//	private String projectName;

	protected SelectDBEntitiesPage(String projectName, DBCeptGenHelper helper) {
		super(Messages
				.getString("Select.Database.Entities.Page.Description"));
		this.helper = helper;
//		this.projectName = projectName;
		setTitle(getName());
	}

	@Override
	public void createControl(Composite parent) {
		parentComposite = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 5;
		gridLayout.makeColumnsEqualWidth = true;
		parentComposite.setLayout(gridLayout);

		Composite leftComposite = new Composite(parentComposite, SWT.NONE);
		gridLayout = new GridLayout();
		leftComposite.setLayout(gridLayout);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 4;
		gd.verticalSpan = 10;
		leftComposite.setLayoutData(gd);

		Tree tree = createEntitiesTree(leftComposite);
		tree.setLayoutData(gd);
		tree.setLayout(gridLayout);

		Composite rightComposite = new Composite(parentComposite, SWT.NONE);
		gridLayout = new GridLayout();
		rightComposite.setLayout(gridLayout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 1;
		gd.verticalSpan = 10;
		rightComposite.setLayoutData(gd);

		btnSelectAll = new Button(rightComposite, SWT.PUSH);
		btnSelectAll.setText(Messages
				.getString("Select.Database.Entities.SelectAllBut"));
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		btnSelectAll.setLayoutData(gd);

		btnClearAll = new Button(rightComposite, SWT.PUSH);
		btnClearAll.setText(Messages
				.getString("Select.Database.Entities.ClearAllBut"));
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		btnClearAll.setLayoutData(gd);

		btnGenerate = new Button(parentComposite, SWT.CHECK);
		btnGenerate.setText(Messages
				.getString("Select.Database.Entities.GeneratConceptBut"));
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		btnGenerate.setLayoutData(gd);

		btnSelectAll.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
				widgetSelected(event);
			}

			public void widgetSelected(SelectionEvent event) {
				for (TreeItem treeItem : dbCheckBoxTree.getTree().getItems()) {
					changeCheckState(treeItem, true);
				}
				setPageComplete(validatePage());
			}
		});

		btnClearAll.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
				widgetSelected(event);
			}

			public void widgetSelected(SelectionEvent event) {
				dbCheckBoxTree.setCheckedElements(new Object[]{});
				dbCheckBoxTree.setGrayedElements(new Object[]{});
				setPageComplete(validatePage());
			}

		});

		btnGenerate.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
				widgetSelected(event);
			}

			public void widgetSelected(SelectionEvent event) {
				Button widget = (Button) event.widget;
				helper.setGenWithRel(widget.getSelection());
			}
		});

		WizardDialog dialog = (WizardDialog) getContainer();
		dialog.addPageChangingListener(this);
		dialog.addPageChangedListener(this);

		setControl(parentComposite);
	}

	private void createModel() {
		if (this.helper.getDbEntityCatalog() == null)
			return;
		SelectDBETreeModel selectDbeTreeModel = new SelectDBETreeModel(this.helper.getDbEntityCatalog());
		this.treeContentProvider = new SelectDBETreeContentProvider(selectDbeTreeModel);
		
		this.dbCheckBoxTree.setContentProvider(this.treeContentProvider);
		this.dbCheckBoxTree.setLabelProvider(new SelectDBELabelProvider());
		this.dbCheckBoxTree.setInput(selectDbeTreeModel);
		this.dbCheckBoxTree.expandAll();
		this.dbCheckBoxTree.setAllChecked(true);
//		this.dbCheckBoxTree.setCheckedElements(helper.getSelectedEntities()
//				.values().toArray());
//		for (Object element : this.dbCheckBoxTree.getCheckedElements()) {
//			updateParentHierarchyState(element);
//		}
		setPageComplete(validatePage());
	}

	private Tree createEntitiesTree(Composite composite) {
		this.dbCheckBoxTree = new CheckboxTreeViewer(composite, SWT.BORDER);
		this.dbCheckBoxTree.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				dbCheckBoxTree.setGrayed(event.getElement(), false);
				dbCheckBoxTree.setSubtreeChecked(event.getElement(),
						event.getChecked());
				updateParentHierarchyState(event.getElement());
				setPageComplete(validatePage());
			}
		});
		return (Tree) this.dbCheckBoxTree.getControl();
	}

	private boolean validatePage() {
		return dbCheckBoxTree.getCheckedElements().length > 0;
	}

	public void handlePageChanging(PageChangingEvent event) {
		IWizardPage page = (IWizardPage) event.getCurrentPage();
		if (!page.getName().equals(getName()))
			return;

		helper.getSelectedEntities().clear();

		Object[] checked = dbCheckBoxTree.getCheckedElements();
		if (checked != null) {
			for (Object nodeValue : checked) {
				if (dbCheckBoxTree.getGrayed(nodeValue)) {
					continue;
				}
				if (nodeValue instanceof DBEntity) {
					DBEntity dbe = (DBEntity) nodeValue;
					helper.getSelectedEntities().put(dbe.getFullName(), dbe);
				}
				if (nodeValue instanceof DBSchema) {
					DBSchema dbs = (DBSchema) nodeValue;
					helper.getSelectedEntities().putAll(dbs.getEntities());
				}
				if (nodeValue instanceof DBEntityCatalog) {
					DBEntityCatalog dbeCatalog = (DBEntityCatalog) nodeValue;
					helper.getSelectedEntities().putAll(
							dbeCatalog.getEntities());
				}
			}
		}

		IWizardPage targetPage = (IWizardPage) event.getTargetPage();
		IWizardPage previousPage = getPreviousPage();

		if (!(targetPage.getName().equalsIgnoreCase(previousPage.getName()))) {
			if (this.helper.isGenWithRel()) {
				Map<String, DBEntity> additionalEntities = this.helper
						.getAdditionalEntities();

				if (additionalEntities.size() > 0) {
					MessageBox mb = new MessageBox(parentComposite.getShell(),
							SWT.YES | SWT.NO);
					mb.setText("Confirm Import Of Additional Entities");
					mb.setMessage(additionalEntities.size()
							+ " other related entities would be imported in addition to selected entities");
					int open = mb.open();
					if (open == SWT.NO) {
						event.doit = false;
					} else {
						// When Generate relation is enabled and user wish to
						// import other additional entities,
						// then BE should display other additional entities too
						// in the Tree...
						// code is add to resolve the issue - AUOMP5
						helper.getSelectedEntities().putAll(additionalEntities);
						event.doit = true;
					}
				}
			}
		}
	}

	public void pageChanged(PageChangedEvent event) {
		IWizardPage selectedPage = (IWizardPage) event.getSelectedPage();
		if (selectedPage.getName().equalsIgnoreCase(getName())) {
			// allow to re-create the model....
			// if (dbTreeModel == null)
			createModel();
		}
	}

	/**
	 * Handles the checkAll, unCheckAll, partial checked conditions for the checkbox tree.
	 * @param element
	 */
	private void updateParentHierarchyState(Object element) {
		if (treeContentProvider == null || treeContentProvider.getParent(element) == null)
			return;
		Object parent = treeContentProvider.getParent(element);
		boolean allChecked = true;
		boolean allUnchecked = true;
		for (Object sibling : treeContentProvider.getChildren(parent)) {
			if (dbCheckBoxTree.getGrayed(sibling)) {
				allChecked = allUnchecked = false;
				break;
			}
			if (dbCheckBoxTree.getChecked(sibling))
				allUnchecked = false;
			else
				allChecked = false;
		}
		if (allChecked) {
			dbCheckBoxTree.setGrayChecked(parent, false);
			dbCheckBoxTree.setChecked(parent, true);
		} else if (allUnchecked) {
			dbCheckBoxTree.setGrayChecked(parent, false);
			dbCheckBoxTree.setChecked(parent, false);
		} else {
			dbCheckBoxTree.setGrayChecked(parent, true);
		}
		updateParentHierarchyState(parent);
	}

	private void changeCheckState(TreeItem treeItem, boolean check) {
		treeItem.setChecked(check);
		treeItem.setGrayed(false);
		if (treeItem.getItemCount() <= 0) {
			return;
		}
		for (TreeItem item : treeItem.getItems()) {
			changeCheckState(item, check);
		}
	}
}
