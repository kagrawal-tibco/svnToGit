package com.tibco.cep.studio.ui.forms;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getEmptyDefaultEntityPropertiesMap;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.updateProperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.EntityCellModifier;
import com.tibco.cep.studio.ui.editors.utils.PropertyTableConstants;
import com.tibco.cep.studio.ui.editors.wizards.NewMetaDataDialog;
import com.tibco.cep.studio.ui.forms.components.EntityContentProvider;
import com.tibco.cep.studio.ui.forms.components.EntityLabelProvider;
import com.tibco.cep.studio.ui.forms.components.TextAndDialogCellEditor;
import com.tibco.cep.studio.ui.forms.components.UneditableComboBoxCellEditor;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

//import com.tibco.cep.utilities.EntityPropertiesTable.MetaDataExtTree;

/**
 * 
 * @author sasahoo
 * 
 */

public abstract class AbstractEntityFormViewer extends AbstractFormViewer {

	protected AbstractSaveableEntityEditorPart editor;
//	protected ExtendedPropertiesPanel exPanel;
	protected boolean isPropertyExtendedCheck = false;

	protected Section propertiesSection;
	protected Section extPropSection;

	protected org.eclipse.jface.action.Action diagramAction;
	protected org.eclipse.jface.action.Action dependencyDiagramAction;
	protected org.eclipse.jface.action.Action sequenceDiagramAction;

	protected TableViewer tableViewer;
	protected Table tableReference;

	protected List<String> columnNames = new ArrayList<String>();;
	protected EList<PropertyDefinition> entityList;

	private String[] PROPS;
	private int NO_OF_COLUMNS;

	public ToolBarProvider toolBarProvider;

	protected static final int[] defaultWeightPropertySections = new int[] {
			195, 15 };

	/**
	 * Default extended Properties
	 * 
	 * @param entity
	 * @param section
	 */
	public void setDefaultExtendedProperties(Entity entity, Section section) {
		try {
			if (entity.getExtendedProperties() != null) {
//				exPanel.setEntity(entity);
				if (updateProperties(entity.getExtendedProperties())) {
					ModelUtils.saveEObject(entity);
				}
//				setExtendedPropertiesPanelData(entity.getExtendedProperties(),
//						exPanel);
				// section.setExpanded(true);
			} else {
				// Setting default Extended Properties, entity save and expand
				// Extended Properties Section
				entity.setExtendedProperties(getEmptyDefaultEntityPropertiesMap());
//				exPanel.setEntity(entity);
				ModelUtils.saveEObject(entity);
				// entity.eResource().save(ModelUtils.getPersistenceOptions());
//				setExtendedPropertiesPanelData(entity.getExtendedProperties(),
//						exPanel);
				// section.setExpanded(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param e
	 * @param isPropertySection
	 */
	protected void adjustPropertySections(ExpansionEvent e,
			boolean isPropertySection) {
		if (isPropertySection) {
			if (e.getState() == true
					&& (enableMetadataConfigurationEnabled() && extPropSection
							.isExpanded())) {
				sashForm.setWeights(new int[] { 105, 105 });// When both
				// expanded
			} else if (e.getState() == true
					&& (enableMetadataConfigurationEnabled() && !extPropSection
							.isExpanded())) {
				sashForm.setWeights(new int[] { 195, 15 });
			} else {
				if(editor.getEntity() instanceof Event || editor.getEntity() instanceof Scorecard)
					sashForm.setWeights(new int[] { 15 });
				else if(editor.getEntity() instanceof Concept)
					sashForm.setWeights(new int[] { 15,195});	
			}
		} else {
			if (e.getState() == true && propertiesSection.isExpanded()) {
				sashForm.setWeights(new int[] { 105, 105 });// When both
				// expanded
			} else if (e.getState() == true && !propertiesSection.isExpanded()) {
				sashForm.setWeights(new int[] { 15, 195 });

			} else if (e.getState() == false && !propertiesSection.isExpanded()) {
				sashForm.setWeights(new int[] { 15, 195 });
			} else {
				sashForm.setWeights(new int[] { 195, 15 });
			}
		}
		getForm().reflow(true);
	}

	/**
	 * @return
	 */
	public Section getPropertiesSection() {
		return propertiesSection;
	}

	protected boolean enableMetadataConfigurationEnabled() {
		String metadataEnabled = System
				.getProperty("TIBCO.BE.Entity.Metadata.Config.Enable");
		if (new Boolean(metadataEnabled).booleanValue())
			return true;
		return false;
	}

	protected void createPropertiesTable(Composite parent) {
		createPropertiesColumnList();
		NO_OF_COLUMNS = columnNames.size();
		PROPS = new String[NO_OF_COLUMNS];
		int i = 0;
		for (String prop : columnNames) {
			PROPS[i++] = prop;
		}
		createTable(parent, editor.getEntity());
		if (getEditor().isEnabled()) {
			createCellEditor(columnNames);
		}
	}

	public void refreshTable() {
		tableViewer.setInput(null);
		tableViewer.refresh();
		populateData();
	}

	private EList<PropertyDefinition> getList(Entity entity) {
		EList<PropertyDefinition> list = null;

		if (entity instanceof Scorecard) {
			Scorecard scorecard = (Scorecard) entity;
			list = scorecard.getProperties();

		} else if (entity instanceof Event) {
			Event event = (Event) entity;
			list = event.getProperties();

		} else if (entity instanceof Concept) {
			Concept concept = (Concept) entity;
			list = concept.getProperties();

		}

		return list;
	}

	private void createToolBar(Composite parent, final Entity entity) {
		toolBarProvider = new ToolBarProvider(parent);
		toolBarProvider.setShowBackgroundColor(false);
		toolBarProvider.setShowButtonText(true);
		toolBarProvider.createToolbar();

		Listener listener = new Listener() {

			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				// TODO Auto-generated method stub
				ToolItem toolItem = (ToolItem) arg0.widget;
				if (toolItem.getText().equalsIgnoreCase(
						toolBarProvider.getAddItem().getText())) {
					addAction(entity);
				} else if (toolItem.getText().equalsIgnoreCase(
						toolBarProvider.getDeleteItem().getText())) {
					removeAction(entity);
				} else if(toolItem.getText().equalsIgnoreCase(
						toolBarProvider.getFitcontentItem().getText())){
					fitContentAction();
				}

			}
		};

		toolBarProvider.setAllItemSelectionListener(listener);

	}

	public void addAction(Entity entity) {

		EList<PropertyDefinition> list = getList(entity); // temp.getProperties();
		PropertyDefinition propertyDef = ElementFactory.eINSTANCE
				.createPropertyDefinition();
		propertyDef.setName(PropertyTableConstants.getPropertyName(
				entity.getName(), tableReference));
		propertyDef.setOwnerPath(entity.getFullPath());
		propertyDef.setOwnerProjectName(entity.getOwnerProjectName());
		propertyDef.setType(PROPERTY_TYPES.STRING);
		propertyDef.setHistorySize(0);
		propertyDef.setHistoryPolicy(0); // ///???
		propertyDef.setArray(false);
		AddCommand command = new AddCommand(editor.getEditingDomain(), list,
				propertyDef);
		boolean executed = EditorUtils.executeCommand(editor, command);
		editor.modified();
		getProperties(entity).add(propertyDef);
		tableViewer.refresh();

	}

	public void removeAction(Entity entity) {

		StructuredSelection strSel = (StructuredSelection) tableViewer
				.getSelection();
		Iterator iter = strSel.iterator();
		while (iter.hasNext()) {
			PropertyDefinition propName = (PropertyDefinition) iter.next(); // (PropertyDefinition)strSel.getFirstElement();
			EList<PropertyDefinition> list = getList(entity);// scorecard.getProperties();
			list.remove(propName);
			editor.modified();
			getProperties(entity).remove(propName);
		}
		tableViewer.refresh();
		toolBarProvider.getDeleteItem().setEnabled(false);
}

	public void fitContentAction() {
		int width = 0;
		TableColumn[] tableColumns = tableViewer.getTable().getColumns();
		for (TableColumn tc : tableColumns) {
			width = width + tc.getWidth();
		}
		for (TableColumn tc : tableColumns) {
			tc.setWidth(width / tableColumns.length);
		}
		for (TableColumn tc : tableColumns) {
			tc.pack();
		}
	}
	
	private void paintCheckBox(final TableViewer tableViewer,
			final int CheckBoxColNo) {
		tableViewer.getTable().addListener(SWT.PaintItem, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {

				String ICON_TOOLBAR_CHECK = "/icons/checked.png";
				String ICON_TOOLBAR_UNCHECK = "/icons/unchecked.png";

				Image tmpImage = null;

				if (event.index == CheckBoxColNo) {

					TableItem item = (TableItem) event.item;
					PropertyDefinition propDef = (PropertyDefinition) item
							.getData();
					if (propDef != null) {
						if (propDef.isArray())
							tmpImage = StudioUIPlugin.getDefault()
									.getImage(ICON_TOOLBAR_CHECK);
						else
							tmpImage = StudioUIPlugin.getDefault()
									.getImage(ICON_TOOLBAR_UNCHECK);
					}
					if (tmpImage != null) {
						int tmpWidth = 0;
						int tmpHeight = 0;
						int tmpX = 0;
						int tmpY = 0;

						tmpWidth = tableViewer.getTable()
								.getColumn(event.index).getWidth();
						tmpHeight = ((TableItem) event.item).getBounds().height;

						tmpX = tmpImage.getBounds().width;
						tmpX = (tmpWidth / 2 - tmpX / 2);
						tmpY = tmpImage.getBounds().height;
						tmpY = (tmpHeight / 2 - tmpY / 2);
						if (tmpX <= 0)
							tmpX = event.x;
						else
							tmpX += event.x;
						if (tmpY <= 0)
							tmpY = event.y;
						else
							tmpY += event.y;
						event.gc.drawImage(tmpImage, tmpX, tmpY);
					}

				}
			}
		});
	}

	protected Table createTable(Composite composite, final Entity entity) {
		int checkBoxColNo;
		EntityContentProvider ContentProvider = new EntityContentProvider();
		EntityLabelProvider labelProvider = new EntityLabelProvider(entity);
		createToolBar(composite, entity);

		Composite tableComp = new Composite(composite, GridData.BEGINNING);
		GridData gdtable = new GridData(GridData.FILL_BOTH);
		tableComp.setLayoutData(gdtable);
		GridLayout gridtablelayout = new GridLayout(1, false);
		tableComp.setLayout(gridtablelayout);

		tableViewer = new TableViewer(tableComp, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);
		tableViewer.setContentProvider(ContentProvider);
		tableViewer.setLabelProvider(labelProvider.getLabel());
		entityList = getProperties(entity);
		toolBarProvider.getDeleteItem().setEnabled(false);

		if (entity instanceof Concept || entity instanceof Scorecard) {
			checkBoxColNo = 2;
			paintCheckBox(tableViewer, checkBoxColNo);
		}

		// Set up the table
		Table table = tableViewer.getTable();
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 500;
		table.setLayoutData(gd);
		for (int i = 0; i < NO_OF_COLUMNS; i++) {
			new TableColumn(table, SWT.NONE).setText(columnNames.get(i));
		}
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(i).pack();
		}
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setColumnProperties(PROPS);
		tableReference = table;
		autoTableLayout(table);

		MenuManager popupMenu = new MenuManager();
		popupMenu.add(new RenamePropertyAction());
		popupMenu.add(new MetaDataExtTree());
		Menu menu = popupMenu.createContextMenu(tableReference);
		tableReference.setMenu(menu);
		 toolBarProvider.getDeleteItem().setEnabled(false);
		tableViewer.getTable().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableViewer.getTable().getItemCount() > 0) {
					if (getEditor().isEnabled()) {
						toolBarProvider.getDeleteItem().setEnabled(true);
					}
				}
			}
		});
		tableViewer.setInput(entityList);
		tableViewer.refresh();
		table.pack();

		return table;
	}

	private EList<PropertyDefinition> getProperties(Entity entity) {
		if (entity instanceof Event) {
			EList<PropertyDefinition> lst = ((Event) editor.getEntity())
					.getProperties();
			return lst;
		} else if (entity instanceof Scorecard) {
			EList<PropertyDefinition> lst = ((Scorecard) editor.getEntity())
					.getProperties();
			return lst;
		} else if (entity instanceof Concept) {
			EList<PropertyDefinition> lst = ((Concept) editor.getEntity())
					.getProperties();
			return lst;
		} else
			return null;
	}

	public void populateData() {
		tableViewer.setInput(getProperties(editor.getEntity()));
		tableViewer.refresh();
	}

	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		table.setLayout(autoTableLayout);
	}

	public void createCellEditor(List<String> tableColumnsWithType) {
		int editorCount = 0;
		CellEditor[] editors = new CellEditor[NO_OF_COLUMNS];
		int iterator = 0;
		while (iterator < tableColumnsWithType.size()) {
			String columnName = tableColumnsWithType.get(iterator);
			if (columnName.equals(PropertyTableConstants.NAME)) {
				editors[editorCount++] = new TextCellEditor(tableReference);
			}
			if (columnName.equals(PropertyTableConstants.TYPE)) {
				if (editor.getEntity() instanceof Scorecard) {
					
				 editors[editorCount++] = new UneditableComboBoxCellEditor(
							tableReference, PropertyTableConstants.typeItems);
				} else if (editor.getEntity() instanceof Event) {
					editors[editorCount++] = new UneditableComboBoxCellEditor(
							tableReference, PropertyTableConstants.typeItems);
				} else {
					editors[editorCount++] = new TextAndDialogCellEditor(
							tableReference, columnName, editor);
				}
			}
			if (columnName.equals(PropertyTableConstants.MULTIPLE)) {
				editors[editorCount++] = new CheckboxCellEditor(tableReference);
			}
			if (columnName.equals(PropertyTableConstants.POLICY)) {
				editors[editorCount++] = new UneditableComboBoxCellEditor(tableReference,
						PropertyTableConstants.policyItems);
			}
			if (columnName.equals(PropertyTableConstants.HISTORY)) {
				editors[editorCount++] = new TextCellEditor(tableReference);
			}
			if (columnName.equals(PropertyTableConstants.DOMAIN)) {
				editors[editorCount++] = new TextAndDialogCellEditor(
						tableReference, columnName, editor);
			}
			iterator++;
		}
		tableViewer
				.setCellModifier(new EntityCellModifier(tableViewer, editor));
		tableViewer.setCellEditors(editors);
	}

	@Override
	public void dispose() {
		super.dispose();
		editor = null;
//		exPanel = null;
		propertiesSection = null;
		extPropSection = null;
		diagramAction = null;
		dependencyDiagramAction = null;
		sequenceDiagramAction = null;
	}

	protected AbstractSaveableEntityEditorPart getEditor() {
		return editor;
	}

	protected void setEditor(AbstractSaveableEntityEditorPart editor) {
		this.editor = editor;
	}

	protected EditingDomain getEditingDomain() {
		if (editor instanceof IEditingDomainProvider) {
			IEditingDomainProvider editingDomainProvider = (IEditingDomainProvider) editor;
			return editingDomainProvider.getEditingDomain();
		}
		return null;
	}

	/**
	 * 
	 */
	protected abstract void createPropertiesColumnList();

	/**
	 * 
	 * @param propertyDefinition
	 */
	protected void renameElement(PropertyDefinition propertyDefinition) {

	}

	protected PropertyDefinition getPropertyDefinition(
			EList<PropertyDefinition> list, String name) {
		for (PropertyDefinition propertyDefinition : list) {
			if (propertyDefinition.getName().equalsIgnoreCase(name)) {
				return propertyDefinition;
			}
		}
		return null;
	}

	protected class MetaDataExtTree extends org.eclipse.jface.action.Action {
		public MetaDataExtTree() {
			super("MetaDataExt");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			if (!tableViewer.getSelection().isEmpty()) {
				PropertyDefinition propDefSel = (PropertyDefinition) ((IStructuredSelection) tableViewer
						.getSelection()).getFirstElement();
				final PropertyDefinition propDef = getPropertyDefinition(
						getProperties(editor.getEntity()), propDefSel.getName());
				NewMetaDataDialog newMetaDataDialog = new NewMetaDataDialog(
						editor.getSite().getShell(), "Metadata", editor,
						propDef);
				newMetaDataDialog.open();
			}
		}
	}

	protected class RenamePropertyAction extends
			org.eclipse.jface.action.Action {

		public RenamePropertyAction() {
			super("Rename");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		public void run() {
			if (!tableViewer.getSelection().isEmpty()) {
				PropertyDefinition propertyDefinition = (PropertyDefinition) ((IStructuredSelection) tableViewer
						.getSelection()).getFirstElement();
				renameElement(propertyDefinition);
			}
		}
	}

}