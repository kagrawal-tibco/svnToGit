package com.tibco.cep.studio.dbconcept.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.studio.dbconcept.ModulePlugin;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseProperty;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseRelationship;
import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBProperty;
import com.tibco.cep.studio.dbconcept.conceptgen.RelationshipKey;
import com.tibco.cep.studio.dbconcept.conceptgen.impl.DBEntityImpl;
import com.tibco.cep.studio.dbconcept.conceptgen.impl.DBPropertyImpl;
import com.tibco.cep.studio.dbconcept.palettes.tools.ConceptDBETableContentProvider;
import com.tibco.cep.studio.dbconcept.palettes.tools.ConceptDBETableLabelProvider;
import com.tibco.cep.studio.dbconcept.palettes.tools.ConceptDBETableModel;
import com.tibco.cep.studio.dbconcept.palettes.tools.DBCeptGenHelper;
import com.tibco.cep.studio.dbconcept.utils.Messages;

/**
 * 
 * @author majha
 * 
 */
public class ConceptDBEntitiesPage extends WizardPage implements
		IPageChangedListener {

	DBCeptGenHelper helper;
	
	Composite parentComposite = null;
	
	private Label alertLabel;
	private ICheckStateListener checkStateListener;

	private CheckboxTreeViewer conceptCheckboxTree;
	private ConceptDBETableContentProvider conceptDBETableContentProvider;
	private static final String[] COLUMN_NAMES = new String[] { "Database Schema", "BusinessEvents Concept" };
	private static final String[] COLUMN_PROPERTIES = new String[]{"SchemaColumn", "ConceptColumn"};
	//	private String projectName;

	protected ConceptDBEntitiesPage(String projectName, DBCeptGenHelper helper) {
		super(Messages
				.getString("Concept.Name.DBEntities.Page.Description"));
		this.helper = helper;
//		this.projectName = projectName;
		setTitle(getName());
	}

	@Override
	public void createControl(Composite parent) {
		parentComposite = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		parentComposite.setLayout(gridLayout);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		parentComposite.setLayoutData(gd);
		
		Tree tree = createTreeTable(parentComposite);
		tree.setLayout(gridLayout);
		tree.setLayoutData(gd);
		tree.pack();
		
		alertLabel = new Label(parentComposite, SWT.LEFT);
		Display display = Display.getCurrent();
		if (display != null || (display = Display.getDefault()) != null) {
			alertLabel.setForeground(new Color(display, 255, 0, 0));
		}
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		alertLabel.setLayoutData(gd);

		WizardDialog dialog = (WizardDialog) getContainer();
		dialog.addPageChangedListener(this);

		setControl(parentComposite);
	}

	private void createModel() {
		if (this.helper.getDbEntityCatalog() == null)
			return;
		
		ConceptDBETableModel dbeTableModel = new ConceptDBETableModel(this.helper.getDbEntityCatalog(), this.helper.getSelectedEntities());
		this.conceptDBETableContentProvider = new ConceptDBETableContentProvider(dbeTableModel);

	    this.conceptCheckboxTree.setContentProvider(conceptDBETableContentProvider);
		this.conceptCheckboxTree.setLabelProvider(new ConceptDBETableLabelProvider());
		
		this.conceptCheckboxTree.setInput(dbeTableModel);
		this.conceptCheckboxTree.expandToLevel(DBDataSource.MSSQL.equals(this.helper.getDbEntityCatalog().getDatabaseType()) ? 3 : 2);
		
		final List<Object> alwaysCheckedElements = getAlwaysCheckedElements();
		this.conceptCheckboxTree.setCheckedElements(conceptDBETableContentProvider.getAllSubElements(null).toArray());
		if (this.checkStateListener != null) {
			this.conceptCheckboxTree.removeCheckStateListener(checkStateListener);
		}
		this.checkStateListener = new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object element = event.getElement();
				if (alwaysCheckedElements.contains(element)) {
					conceptCheckboxTree.setChecked(element, true);
					alertLabel.setText("Schemas, Tables, Primary keys cannot be unchecked. Please uncheck other unwanted columns.");
				}
				else {
					alertLabel.setText("");
				}
			}
		};
		this.conceptCheckboxTree.addCheckStateListener(checkStateListener);
		this.alertLabel.setText("");
		
		packLastColumn(this.conceptCheckboxTree.getTree());
	}
	
	/**
	 * Returns a list of elements that has to be checked and cannot be unchecked by user.<br/>
	 * Such as PK attributes, tables (Tables can be checked/unchecked on previous screen only).
	 * @return
	 */
	private List<Object> getAlwaysCheckedElements() {
		List<Object> checkedElements = new ArrayList<Object>();
		Collection<Object> all = conceptDBETableContentProvider.getAllSubElements(null);
		for (Object element : all) {
			//Always checked IF (Not a column element OR PK column) 
			if (!(element instanceof DBProperty) || ((DBProperty)element).isPK()) {
				checkedElements.add(element);
			}
		}
		if (this.helper.isGenWithRel()) {
			//Disabling uncheck for FK columns as well
			Collection<DBEntity> selectedEntities = this.helper.getSelectedEntities().values();
			for (DBEntity dbe : selectedEntities) {
				if (dbe instanceof DBEntityImpl) {
					Map<String, BaseRelationship> relations = ((DBEntityImpl) dbe).getRelations();
					for (Entry<String, BaseRelationship> relationEntry : relations.entrySet()) {
						BaseRelationship relation = relationEntry.getValue();
						for (Object relationshipKey : relation.getRelationshipKeySet()) {
							if(relationshipKey instanceof RelationshipKey) {
								BaseProperty fkProperty = dbe.getProperty(((RelationshipKey)relationshipKey).getParentKey());
								checkedElements.add(fkProperty);
							}
						}
					}
				}
			}
		}
		
		return checkedElements;
	}
	
	private Tree createTreeTable(Composite composite) {
		Tree tree = new Tree(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		for (String columnName : COLUMN_NAMES) {
			TreeColumn column = new TreeColumn(tree, SWT.LEFT);
			column.setAlignment(SWT.LEFT);
			column.setText(columnName);
			column.setMoveable(false);
			column.setWidth(250);
		}
		this.conceptCheckboxTree = new CheckboxTreeViewer(tree);
		this.conceptCheckboxTree.setCellEditors(new CellEditor[] { null, new TextCellEditor(tree) });
		this.conceptCheckboxTree.setColumnProperties(COLUMN_PROPERTIES);
		this.conceptCheckboxTree.setCellModifier(new ConceptTableCellModifier(conceptCheckboxTree));
		
		return (Tree) this.conceptCheckboxTree.getControl();
	}
	
	@Override
	public boolean isPageComplete() {
		boolean pageComplete = super.isPageComplete();

		boolean lastpage = getContainer().getCurrentPage().getName().equals(getName());

		return pageComplete && lastpage;
	}

	public boolean performFinish(final IProject project) throws Exception {
		try {
			if (helper.conceptExistAtLocationWithAlias()) {
				MessageBox mb = new MessageBox(parentComposite.getShell(),
						SWT.YES | SWT.NO | SWT.CANCEL);
				mb.setText("Confirm Overwrite");
				mb
						.setMessage("One or more database concept exist at the given folder location. Do you want to overwrite?");
				int open = mb.open();
				if (open == SWT.NO) {
					helper.setOverwriteConcepts(false);
				} else if (open == SWT.YES) {
					helper.setOverwriteConcepts(true);
				} else {
					return false;
				}
			}
			//Remove unchecked properties from helper
			Collection<DBEntity> selectedEntities = this.helper.getSelectedEntities().values();
			for (DBEntity dbe : selectedEntities) {
				if (dbe instanceof DBEntityImpl) {
					DBEntityImpl dbeImpl = (DBEntityImpl)dbe;
					Iterator<Entry<String, BaseProperty>> propsIter = dbeImpl.getAllProperties().entrySet().iterator();
					while(propsIter.hasNext()) {
						Entry<String, BaseProperty> propEntry = propsIter.next();
						if (propEntry.getValue() instanceof DBProperty && !this.conceptCheckboxTree.getChecked(propEntry.getValue())) {
							propsIter.remove();
						}
					}
				}
			}
			helper.filterDBEntityCatalog();

			if (!this.helper.isGenWithRel()) {
				helper.getRidOfRelations();
			}
			getWizard().getContainer().run(true, true, new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor monitor) throws CoreException,
				InvocationTargetException, InterruptedException {
					try {
						monitor.beginTask("", IProgressMonitor.UNKNOWN);
						monitor.setTaskName("Generating Concepts");
						helper.generateConcepts();
						if (helper.isGenerateEvents()) {
							// Refresh project , once the concepts are generated
							// to update the concept related info in IndexUtils class.
							monitor.subTask("Refreshing project");
							if (project != null) {
								project.refreshLocal(IProject.DEPTH_INFINITE, null);
							}
							monitor.setTaskName("Generating events");
							helper.generateEvents();
							monitor.subTask("Refreshing project");
						}
						if (project != null) {
							project.refreshLocal(IProject.DEPTH_INFINITE, null);
						}
					} catch (InterruptedException e) {
						throw e;
					} catch (Exception e) {
						throw new CoreException(new Status(IStatus.ERROR, ModulePlugin.PLUGIN_ID, "Error generating concepts", e));
					}
				}
			});

		} catch (Exception ex) {
			throw ex;
		}

		return true;
	}

	public void pageChanged(PageChangedEvent event) {
		IWizardPage selectedPage = (IWizardPage) event.getSelectedPage();
		if (selectedPage.getName().equalsIgnoreCase(getName())) {
			createModel();
		}
	}

	private static class ConceptTableCellModifier implements ICellModifier {
		private TreeViewer treeViewer;
		
		public ConceptTableCellModifier(TreeViewer treeViewer) {
			this.treeViewer = treeViewer;
		}
		
		@Override
		public boolean canModify(Object element, String property) {
			if (COLUMN_PROPERTIES[1].equals(property) && (element instanceof DBProperty || element instanceof DBEntity)) {
				return true;
			}
			return false;
		}

		@Override
		public Object getValue(Object element, String property) {
			if (element instanceof DBEntity) {
				DBEntity dbe = (DBEntity)element;
				if(COLUMN_PROPERTIES[1].equals(property)) {
					return dbe.getAlias();
				}
			}
			else if (element instanceof DBProperty) {
				DBProperty dbp = (DBProperty)element;
				if(COLUMN_PROPERTIES[1].equals(property)) {
					return dbp.getAlias();
				}
			}
			return "";
		}

		@Override
		public void modify(Object element, String property, Object value) {
			if (element instanceof TreeItem) {
				TreeItem ti = (TreeItem) element;
				Object data = ti.getData();
				if (data instanceof DBEntityImpl) {
					DBEntityImpl dbeImpl = (DBEntityImpl)data;
					dbeImpl.setAliasName(value.toString());
				}
				else if (data instanceof DBPropertyImpl) {
					DBPropertyImpl dbpImpl = (DBPropertyImpl)data;
					dbpImpl.setAlias(value.toString());
				}
				treeViewer.refresh();
			}
		}
	}
	
	/**
	 * Shrink the last(extra) column generated in SWT tables.
	 * @param tree
	 */
	private void packLastColumn(Tree tree) {
		int sumColumnWidth = 0;
		for (int i = 0; i < tree.getColumnCount() - 1; i++) {
			sumColumnWidth += tree.getColumn(i).getWidth();
		}
		TreeColumn lastColumn = tree.getColumn(tree.getColumnCount() - 1);
		lastColumn.pack();

		Rectangle area = tree.getClientArea();
		Point preferredSize = tree.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int width = area.width - 2 * tree.getBorderWidth();

		if (preferredSize.y > area.height + tree.getHeaderHeight()) {
			Point verticalScrollBarSize = tree.getVerticalBar().getSize();
			width -= verticalScrollBarSize.x;
		}
		if (lastColumn.getWidth() < width - sumColumnWidth) {
			lastColumn.setWidth(width - sumColumnWidth);
		}
	}
}
