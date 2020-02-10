package com.tibco.cep.studio.ui.editors.wizardPages;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import com.tibco.cep.studio.core.util.ImportDomainUtils;
import com.tibco.cep.studio.ui.editors.domain.data.DBTreeColumns;
import com.tibco.cep.studio.ui.editors.domain.data.DBTreeContentProvider;
import com.tibco.cep.studio.ui.editors.domain.data.DBTreeLabelProvider;
import com.tibco.cep.studio.ui.editors.domain.data.DBTreeRoot;
import com.tibco.cep.studio.ui.editors.domain.data.DBTreeTables;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author smarathe
 * 
 */

public class ImportDatabaseTableSelectionDomainWizardPage extends WizardPage
implements IDomainSourceWizardPage, SelectionListener {

	Connection connection;
	private String projectName;
	Composite parentComposite;

	Label lblQuery;
	Label lblResult;
	Text txtQuery;
	Button  btnAdvanced;
	List<String> domainEntries;
	Map<String,Object> connectionMap;
	private Map checkedStateStore = new HashMap(9);
	private Collection expandedTreeNodes = new HashSet();
	private Collection whiteCheckedTreeItems = new HashSet();
	Group group, groupTable, groupAdvanced;
	HashMap<String, String> metaDataMap;
	CheckboxTreeViewer treeViewer;
	String tableQuery = "";
	private String domainDataType;
	private Button btnExecuteTreeQuery;
	private Button btnQuery;
	private boolean canFinish = false;

	public ImportDatabaseTableSelectionDomainWizardPage(String pageName, Connection connection, String domainDatatype) {
		super(pageName);
		setTitle(Messages.getString("ImportDomainWizard.page.Title"));
		setDescription(Messages.getString("import.domain.wizard.db.description"));
		this.connection = connection;
		this.domainDataType = domainDatatype;
	}

	@Override
	public <O> O getDataSource() {
		return (O) domainEntries;
	}

	@Override
	public String getProjectName() {
		return this.projectName;
	}

	@Override
	public void setProjectName(String projectName) {
		this.projectName = projectName;

	}

	@Override
	public void createControl(Composite parent) {
		parentComposite = new Composite(parent, SWT.NONE);
		//FormLayout formLayout = new FormLayout();
		//FormData formData = new FormData();

		GridLayout gridLayout = new GridLayout();
		parentComposite.setLayout(gridLayout);


		GridData gd = null;

		group = new Group(parentComposite, SWT.NULL);
		gridLayout = new GridLayout();
		gridLayout.makeColumnsEqualWidth = false;
		gridLayout.numColumns = 1;
		group.setLayout(gridLayout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.grabExcessHorizontalSpace = true;
		group.setLayoutData(gd);

		groupTable = new Group(group, SWT.NULL);
		groupTable.setText("");
		gridLayout = new GridLayout();
		gridLayout.numColumns = 6;

		gridLayout.makeColumnsEqualWidth = false;
		groupTable.setLayout(gridLayout);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.grabExcessHorizontalSpace = true;

		groupTable.setLayoutData(gd);

		IWizardPage previousPage = this.getPreviousPage();
		if(previousPage instanceof ImportDatabaseTableDomainWizardPage) {
			ImportDatabaseTableDomainWizardPage previousDatabaseTablePage = ((ImportDatabaseTableDomainWizardPage) previousPage);
			connectionMap = previousDatabaseTablePage.getConnectionMap();
			domainDataType = previousDatabaseTablePage.getDomainDataType();
		}

		try {
			treeViewer = new CheckboxTreeViewer(groupTable, SWT.BORDER | (false ? SWT.MULTI : SWT.SINGLE));
			treeViewer.setContentProvider(new DBTreeContentProvider(treeViewer, connectionMap, domainDataType));
			treeViewer.setLabelProvider(new DBTreeLabelProvider(treeViewer));
			treeViewer.addCheckStateListener(new ICheckStateListener() {

				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					setTreeChecked(event.getElement(), event.getChecked());

					Object parent = ((DBTreeContentProvider)((CheckboxTreeViewer)event.getSource()).getContentProvider()).getParent(event.getElement());
					if (parent == null) {
						return;
					}
					checkedStateStore.put(event.getElement(), new ArrayList());
					// now update upwards in the tree hierarchy 
					if (event.getChecked()) {
						whiteCheckedTreeItems.add(event.getElement());
						grayCheckHierarchy(parent, event.getElement());
					} else {
						checkedStateStore.remove(event.getElement());
						List checkList = (List) checkedStateStore.get(parent);
						checkList.remove(event.getElement());
						whiteCheckedTreeItems.remove(event.getElement());
						ungrayCheckHierarchy(parent);
					}

					//Update the hierarchy but do not white select the parent
					grayUpdateHierarchy(parent);
					updateHierarchy(parent);
				}

			});
			String databaseName = ImportDomainUtils.getDatabaseName(connectionMap);
			List<String> tableList = ImportDomainUtils.getTableList(connectionMap, databaseName);
			List<DBTreeTables> tableDBList = new ArrayList<DBTreeTables>();
			for(String tableName : tableList) {
				tableDBList.add(new DBTreeTables(tableName));
			}
			DBTreeRoot treeRoot = new DBTreeRoot(databaseName, tableDBList);
			treeViewer.setInput(treeRoot);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = 6;
			Tree treeWidget = treeViewer.getTree();
			treeWidget.setLayoutData(data);
			treeWidget.setFont(parent.getFont());

			setPageComplete(false);
		}catch(Exception e) {
			e.printStackTrace();
		}

		btnExecuteTreeQuery = new Button(groupTable, SWT.PUSH);
		btnExecuteTreeQuery.setText(Messages.getString("import.domain.DB.tableQuery"));
		gd = new GridData(SWT.LEFT, SWT.BEGINNING,false, false);
		gd.horizontalSpan = 2;
		btnExecuteTreeQuery.setLayoutData(gd);
		btnExecuteTreeQuery.addSelectionListener(this);


		btnAdvanced = new Button(group, SWT.PUSH);
		btnAdvanced.setText(Messages.getString("import.domain.DB.advanced"));
		gd = new GridData(SWT.LEFT, SWT.BEGINNING,false, false);
		gd.horizontalSpan = 2;
		btnAdvanced.setLayoutData(gd);
		btnAdvanced.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnAdvanced.getText().equals("Advanced>>")) {
					groupAdvanced.setVisible(true);
					btnAdvanced.setText("Advanced<<");
				} else {
					groupAdvanced.setVisible(false);
					btnAdvanced.setText("Advanced>>");
					txtQuery.setText("");
				}

			}
		});

		groupAdvanced = new Group(group, SWT.NULL);
		groupAdvanced.setVisible(false);
		groupAdvanced.setText("");

		gridLayout = new GridLayout();
		gridLayout.numColumns = 6;
		gridLayout.makeColumnsEqualWidth = false;
		groupAdvanced.setLayout(gridLayout);
		gd = new GridData(SWT.FILL, SWT.FILL, false, false);
		groupAdvanced.setLayoutData(gd);


		lblQuery = new Label(groupAdvanced, SWT.NONE);
		lblQuery.setText(Messages.getString("import.domain.DB.query.label"));
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 6;
		lblQuery.setLayoutData(gd);

		txtQuery = new Text(groupAdvanced, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 4;
		txtQuery.setLayoutData(gd);
		txtQuery.setToolTipText(Messages.getString("import.domain.DB.txtQuery.tooltip"));

		btnQuery = new Button(groupAdvanced, SWT.PUSH);
		btnQuery.setText(Messages.getString("import.domain.DB.query.execute"));
		gd = new GridData(SWT.RIGHT, SWT.BEGINNING,false, false);
		gd.horizontalSpan = 2;
		btnQuery.setLayoutData(gd);
		btnQuery.addSelectionListener(this);

		setControl(parentComposite);
		setPageComplete(false);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(((Button)e.getSource()).getText().equals("Execute Query")) {
			String query = txtQuery.getText();
			IWizardPage previousPage = this.getPreviousPage();
			if(previousPage instanceof ImportDatabaseTableDomainWizardPage) {
				ImportDatabaseTableDomainWizardPage previousDatabaseTablePage = ((ImportDatabaseTableDomainWizardPage) previousPage);
				connectionMap = previousDatabaseTablePage.getConnectionMap();
				connectionMap.put(Messages.getString("import.domain.databse.table.query"), query);
				connectionMap.put("jdbcResource", previousDatabaseTablePage.getJdbcURI());
				previousDatabaseTablePage.setDataSource(connectionMap);
				canFinish = true;
				setPageComplete(true);
			}
		} else if (((Button)e.getSource()).getText().equals("Create Domain for selected columns")){
			Iterator whiteCheckedListIterator = whiteCheckedTreeItems.iterator();
			if (!whiteCheckedTreeItems.isEmpty()) {
				while(whiteCheckedListIterator.hasNext()) {
					Object whiteCheckedItem = whiteCheckedListIterator.next();
					if(whiteCheckedItem instanceof DBTreeColumns) {
						DBTreeColumns column = (DBTreeColumns) whiteCheckedItem;
						if(tableQuery.equals("")) {
							tableQuery = "select " + column.getTableName() + "." + column.getColumnName() + " from " + column.getTableName();
	
						} else {
							tableQuery = tableQuery + " union " + "select " + column.getTableName() + "." + column.getColumnName() + " from " + column.getTableName();
						}
					}
				}
				IWizardPage previousPage = getWizard().getPreviousPage(this);
				if(previousPage instanceof ImportDatabaseTableDomainWizardPage) {
					ImportDatabaseTableDomainWizardPage previousDatabaseTablePage = ((ImportDatabaseTableDomainWizardPage) previousPage);
					connectionMap = previousDatabaseTablePage.getConnectionMap();
					connectionMap.put(Messages.getString("import.domain.databse.table.query"), tableQuery);
					connectionMap.put("jdbcResource", previousDatabaseTablePage.getJdbcURI());
					previousDatabaseTablePage.setDataSource(connectionMap);
					canFinish = true;
					setPageComplete(true);						
				}
			}	
		}

	}

	protected void grayCheckHierarchy(Object treeElement, Object child) {

		//expand the element first to make sure we have populated for it
		expandTreeElement(treeElement);

		// if this tree element is already gray then its ancestors all are as well
		if (checkedStateStore.containsKey(treeElement)) {
			return; // no need to proceed upwards from here
		}

		List childList = (List) checkedStateStore.get(treeElement);
		if(childList == null) {
			childList = new ArrayList();
		}
		if(child != null){
			childList.add(child);
		}


		checkedStateStore.put(treeElement, childList);
		Object parent = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getParent(treeElement);
		if (parent != null) {
			grayCheckHierarchy(parent, treeElement);
		}
	}

	protected boolean determineShouldBeAtLeastGrayChecked(Object treeElement) {
		// if any list items associated with treeElement are checked then it
		// retains its gray-checked status regardless of its children
		List checked = (List) checkedStateStore.get(treeElement);
		if (checked != null && (!checked.isEmpty())) {
			return true;
		}

		// if any children of treeElement are still gray-checked then treeElement
		// must remain gray-checked as well. Only ask expanded nodes
		if (expandedTreeNodes.contains(treeElement)) {
			Object[] children = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getChildren(treeElement);
			for (int i = 0; i < children.length; ++i) {
				if (checkedStateStore.containsKey(children[i])) {
					return true;
				}
			}
		}

		return false;
	}

	public Text getTxtQuery() {
		return txtQuery;
	}

	public Map<String, Object> getConnectionMap() {
		return connectionMap;
	}

	public Collection getWhiteCheckedTreeItems() {
		return whiteCheckedTreeItems;
	}

	public String getTableQuery() {
		return tableQuery;
	}   

	private void grayUpdateHierarchy(Object treeElement) {

		boolean shouldBeAtLeastGray = determineShouldBeAtLeastGrayChecked(treeElement);
		boolean shouldBeWhiteChecked = determineShouldBeWhiteChecked(treeElement);
		if(shouldBeWhiteChecked) {
			treeViewer.setChecked(treeElement, shouldBeWhiteChecked);
		} else {
			treeViewer.setGrayChecked(treeElement, shouldBeAtLeastGray);
		}


		if (whiteCheckedTreeItems.contains(treeElement)) {
			whiteCheckedTreeItems.remove(treeElement);
		}

		// proceed up the tree element hierarchy
		Object parent = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getParent(treeElement);
		if (parent != null) {
			grayUpdateHierarchy(parent);
		}
	}


	private void expandTreeElement(final Object item) {


		// First see if the children need to be given their checked state at all.  If they've
		// already been realized then this won't be necessary
		if (expandedTreeNodes.contains(item)) {
			checkNewTreeElements(((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider())
					.getChildren(item));
		} else {

			expandedTreeNodes.add(item);
			if (whiteCheckedTreeItems.contains(item)) {
				//If this is the first expansion and this is a white checked node then check the children
				Object[] children = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider())
				.getChildren(item);
				for (int i = 0; i < children.length; ++i) {
					if (!whiteCheckedTreeItems
							.contains(children[i])) {
						Object child = children[i];
						setWhiteChecked(child, true);
						treeViewer.setChecked(child, true);
						checkedStateStore.put(child,
								new ArrayList());
					}
				}

				//Now be sure to select the list of items too
				setListForWhiteSelection(item);
			}
		}

	}

	protected void checkNewTreeElements(Object[] elements) {
		for (int i = 0; i < elements.length; ++i) {
			Object currentElement = elements[i];
			boolean checked = checkedStateStore.containsKey(currentElement);
			treeViewer.setChecked(currentElement, checked);
			treeViewer.setGrayed(currentElement, checked
					&& !whiteCheckedTreeItems.contains(currentElement));
		}
	}

	protected void setTreeChecked(Object treeElement, boolean state) {

		//       if (treeElement.equals(currentTreeSelection)) {
		//           listViewer.setAllChecked(state);
		//       }

		if (state) {
			setListForWhiteSelection(treeElement);
		} else {
			checkedStateStore.remove(treeElement);
		}

		setWhiteChecked(treeElement, state);
		treeViewer.setChecked(treeElement, state);
		treeViewer.setGrayed(treeElement, false);

		// now logically check/uncheck all children as well if it has been expanded
		if (expandedTreeNodes.contains(treeElement)) {
			Object[] children = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getChildren(treeElement);
			for (int i = 0; i < children.length; ++i) {
				setTreeChecked(children[i], state);
			}
		}
	}


	private void setListForWhiteSelection(Object treeElement) {

		Object[] listItems = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getElements(treeElement);
		List listItemsChecked = new ArrayList();
		if(listItems != null) {
			for (int i = 0; i < listItems.length; ++i) {
				listItemsChecked.add(listItems[i]);
			}
			checkedStateStore.put(treeElement, listItemsChecked);
		}

	}

	protected void setWhiteChecked(Object treeElement, boolean isWhiteChecked) {
		if (isWhiteChecked) {
			if (!whiteCheckedTreeItems.contains(treeElement)) {
				whiteCheckedTreeItems.add(treeElement);
			}
		} else {
			whiteCheckedTreeItems.remove(treeElement);
		}
	}

	protected void treeItemChecked(Object treeElement, boolean state) {

		// recursively adjust all child tree elements appropriately
		setTreeChecked(treeElement, state);

		Object parent = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getParent(treeElement);
		if (parent == null) {
			return;
		}

		// now update upwards in the tree hierarchy 
		if (state) {
			grayCheckHierarchy(parent, treeElement);
		} else {
			ungrayCheckHierarchy(parent);
		}

		//Update the hierarchy but do not white select the parent
		grayUpdateHierarchy(parent);
		updateHierarchy(parent);
	}

	protected void ungrayCheckHierarchy(Object treeElement) {
		if (!determineShouldBeAtLeastGrayChecked(treeElement)) {
			checkedStateStore.remove(treeElement);
		}

		Object parent = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getParent(treeElement);
		if (parent != null) {
			ungrayCheckHierarchy(parent);
		}
	}


	protected void updateHierarchy(Object treeElement) {

		boolean whiteChecked = determineShouldBeWhiteChecked(treeElement);
		boolean shouldBeAtLeastGray = determineShouldBeAtLeastGrayChecked(treeElement);

		treeViewer.setChecked(treeElement, shouldBeAtLeastGray);
		setWhiteChecked(treeElement, whiteChecked);
		if (whiteChecked) {
			treeViewer.setGrayed(treeElement, false);
		} else {
			treeViewer.setGrayed(treeElement, shouldBeAtLeastGray);
		}

		// proceed up the tree element hierarchy but gray select all of them
		Object parent =  ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getParent(treeElement);
		if (parent != null) {
			grayUpdateHierarchy(parent);
		}
	}

	protected boolean determineShouldBeWhiteChecked(Object treeElement) {
		return areAllChildrenWhiteChecked(treeElement);
		// && areAllElementsChecked(treeElement);
	}


	protected boolean areAllElementsChecked(Object treeElement) {
		List checkedElements = (List) checkedStateStore.get(treeElement);
		if (checkedElements == null) {
			return false;
		}

		return getListItemsSize(treeElement) == checkedElements.size();
	}

	protected int getListItemsSize(Object treeElement) {
		Object[] elements = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getElements(treeElement);
		return elements.length;
	}

	protected boolean areAllChildrenWhiteChecked(Object treeElement) {
		Object[] children = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getChildren(treeElement);
		for (int i = 0; i < children.length; ++i) {
			if (!whiteCheckedTreeItems.contains(children[i])) {
				return false;
			}
		}

		return true;
	}

	public void updateSelections(Map items) {
		// We are replacing all selected items with the given selected items,
		// so reinitialize everything.
		//	        this.listViewer.setAllChecked(false);
		this.treeViewer.setCheckedElements(new Object[0]);
		this.whiteCheckedTreeItems = new HashSet();
		Set selectedNodes = new HashSet();
		checkedStateStore = new HashMap();

		//Update the store before the hierarchy to prevent updating parents before all of the children are done
		Iterator keyIterator = items.keySet().iterator();
		while (keyIterator.hasNext()) {
			Object key = keyIterator.next();
			List selections = (List) items.get(key);
			//Replace the items in the checked state store with those from the supplied items			
			checkedStateStore.put(key, selections);
			selectedNodes.add(key);
			// proceed up the tree element hierarchy
			Object parent = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getParent(key);
			if (parent != null) {
				// proceed up the tree element hierarchy and make sure everything is in the table		
				primeHierarchyForSelection(parent, selectedNodes);
			}
		}



		// Update the checked tree items.  Since each tree item has a selected
		// item, all the tree items will be gray checked.
		treeViewer.setCheckedElements(checkedStateStore.keySet().toArray());
		treeViewer.setGrayedElements(checkedStateStore.keySet().toArray());

		// Update the listView of the currently selected tree item.
		//	        if (currentTreeSelection != null) {
		//	            Object displayItems = items.get(currentTreeSelection);
		//	            if (displayItems != null) {
		//					listViewer.setCheckedElements(((List) displayItems).toArray());
		//				}
		//	        }
	}

	private void primeHierarchyForSelection(Object item, Set selectedNodes) {

		//Only prime it if we haven't visited yet
		if (selectedNodes.contains(item)) {
			return;
		}

		checkedStateStore.put(item, new ArrayList());

		//mark as expanded as we are going to populate it after this
		expandedTreeNodes.add(item);
		selectedNodes.add(item);

		Object parent = ((DBTreeContentProvider)((CheckboxTreeViewer)treeViewer).getContentProvider()).getParent(item);
		if (parent != null) {
			primeHierarchyForSelection(parent, selectedNodes);
		}
	}




	public IWizardPage getNextPage() {
		return null;
	}

	public boolean isPageComplete() {
		return validatePage();
	}

	private boolean validatePage() {
		return canFinish;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
