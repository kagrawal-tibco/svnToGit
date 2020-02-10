package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricUserDefinedField;
import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.PropertyTableConstants;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

public class LocalMetricFieldsTable {

	private enum FIELD {

		NAME(Messages.getString("metric.property.Name"), true, LocalMetricField.PROP_KEY_NAME), GROUP_BY(Messages.getString("metric.property.GroupBy"), false, LocalMetricField.PROP_KEY_IS_GROUP_BY), AGGR_TYPE(Messages
				.getString("metric.property.AggrType"), false, LocalMetricField.PROP_KEY_AGG_FUNCTION), DEPENDING_FLDS(Messages.getString("metric.property.DependingFields"), false, LocalMetricField.PROP_KEY_DEP_FIELD), DATA_TYPE(
				Messages.getString("metric.property.DataType"), true, LocalMetricField.PROP_KEY_DATA_TYPE), URL_NAME(Messages.getString("metric.property.URLName"), true, LocalMetricField.PROP_KEY_URL_NAME), URL_LINK(Messages
				.getString("metric.property.URLLink"), true, LocalMetricField.PROP_KEY_URL_LINK), DESC(Messages.getString("metric.property.Description"), true, LocalMetricField.PROP_KEY_DESCRIPTION);

		private String name;
		private boolean userDefinedFieldViewable;
		private String propertyName;

		FIELD(String name, boolean userDefinedFieldViewable, String propertyName) {
			this.name = name;
			this.userDefinedFieldViewable = userDefinedFieldViewable;
			this.propertyName = propertyName;
		}

		public String getName() {
			return name;
		}

		public boolean isUserDefinedFieldViewable() {
			return userDefinedFieldViewable;
		}

		public String getPropertyName() {
			return propertyName;
		}

		public static FIELD getByName(String name) {
			for (FIELD field : values()) {
				if (field.getName().equals(name) == true) {
					return field;
				}
			}
			return null;
		}
	}

	private boolean showUserDefinedFields;

	private ArrayList<FIELD> fields;
	private TableViewer fieldsViewer;
	private AbstractSaveableEntityEditorPart editor;

	private String childParticleName;

	private LocalMetric localMetric;

	private ToolBarProvider toolBarProvider;

	public LocalMetricFieldsTable(AbstractSaveableEntityEditorPart editor, boolean showUserDefinedFields) {
		this.editor = editor;
		this.showUserDefinedFields = showUserDefinedFields;
		this.childParticleName = this.showUserDefinedFields == true ? LocalMetric.ELEMENT_KEY_USER_DEFINED_FIELD : LocalMetric.ELEMENT_KEY_FIELD;
		fields = new ArrayList<FIELD>();
		for (FIELD field : FIELD.values()) {
			if (this.showUserDefinedFields == true) {
				if (field.isUserDefinedFieldViewable() == true) {
					fields.add(field);
				}
			} else {
				fields.add(field);
			}
		}
	}

	public void createControl(Composite parent) {
		//create child composite
		Composite composite = new Composite(parent, SWT.NONE);
		//set composite layout
		//toolbar expects grid layout
		composite.setLayout(new GridLayout());
		//add tool bar
		addToolBar(composite);
		//add table viewer
		addTableViewer(composite);
	}

	public void setInput(LocalMetric localMetric) {
		this.localMetric = localMetric;
		if (showUserDefinedFields == true) {
			fieldsViewer.setInput(localMetric.getUserDefinedFields().toArray(new LocalMetricUserDefinedField[localMetric.getUserDefinedFields().size()]));
		} else {
			fieldsViewer.setInput(localMetric.getFields().toArray(new LocalMetricField[localMetric.getFields().size()]));
		}
		//fieldsViewer.getTable().pack();
	}

	private void addToolBar(Composite composite) {
		toolBarProvider = new ToolBarProvider(composite);
		toolBarProvider.setShowBackgroundColor(false);
		toolBarProvider.setShowButtonText(true);
		toolBarProvider.createToolbar();
		toolBarProvider.getAddItem().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//add element to metric
				LocalEntity newElement = localMetric.createLocalElement(childParticleName);
				//set input on the viewer
				List<LocalElement> elements = getChildren(localMetric, childParticleName);
				fieldsViewer.setInput(elements.toArray(new LocalElement[elements.size()]));
				//select the newly created element
				fieldsViewer.setSelection(new StructuredSelection(newElement), true);
			}

		});

		toolBarProvider.getDeleteItem().addSelectionListener(new SelectionAdapter() {

			@SuppressWarnings("rawtypes")
			@Override
			public void widgetSelected(SelectionEvent e) {
				//remove current select element from metric
				List selection = ((IStructuredSelection) fieldsViewer.getSelection()).toList();
				Iterator iterator = selection.iterator();
				int lastDeletedElementIdx = -1;
				while (iterator.hasNext()) {
					LocalElement element = (LocalElement) iterator.next();
					if (iterator.hasNext() == false) {
						lastDeletedElementIdx = getChildren(localMetric, childParticleName).indexOf(element);
					}
					localMetric.removeElementByID(childParticleName, element.getID(), LocalElement.FOLDER_NOT_APPLICABLE);
				}
				List<LocalElement> elements = getChildren(localMetric, childParticleName);
				fieldsViewer.setInput(elements.toArray(new LocalElement[elements.size()]));
				//change selection
				if (elements.isEmpty() == false) {
					//by default we will select first element
					int selectionIdx = 0;
					if (selection.size() == 1) {
						//we deleted a single element, choose first the element below the deleted one if not that then the one above it
						selectionIdx = elements.size() > lastDeletedElementIdx ? lastDeletedElementIdx : lastDeletedElementIdx - 1;
					}
					fieldsViewer.setSelection(new StructuredSelection(elements.get(selectionIdx)), true);
				}

			}

		});

		toolBarProvider.getFitcontentItem().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//INFO borrowed from com.tibco.cep.studio.ui.forms.AbstractEntityFormViewer.fitContentAction()
				int width = 0;
				TableColumn[] tableColumns = fieldsViewer.getTable().getColumns();
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
		});

		toolBarProvider.getDeleteItem().setEnabled(false);
	}

	private void addTableViewer(Composite composite) {
		fieldsViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);
		fieldsViewer.setContentProvider(new ArrayContentProvider());
		fieldsViewer.setLabelProvider(new LocalMetricFieldLabelProvider());
		fieldsViewer.setCellModifier(new LocalMetricCellModifier());
		Table table = fieldsViewer.getTable();
		table.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//add columns and editors
		TableAutoResizeLayout autoResizeLayout = new TableAutoResizeLayout(table);
		String[] viewerColumnProperties = new String[fields.size()];
		CellEditor[] editors = new CellEditor[fields.size()];
		int i = 0;
		for (FIELD field : fields) {
			//create basic column
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(field.getName());
			//set layout information on it
			autoResizeLayout.addColumnData(new ColumnWeightData(1));
			viewerColumnProperties[i] = field.getName();
			editors[i] = generateCellEditor(table, field);
			i++;
		}
		table.setLayout(autoResizeLayout);
		fieldsViewer.setColumnProperties(viewerColumnProperties);
		fieldsViewer.setCellEditors(editors);

		if (fields.contains(FIELD.GROUP_BY) == true) {
			//TODO we need to add this to center the check/uncheck image in the group by field cells. SWT does not support alignment for images in the cells
			fieldsViewer.getTable().addListener(SWT.PaintItem, new Listener() {

				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					Image image = null;
					if (event.index == 1) {
						TableItem item = (TableItem) event.item;
						LocalMetricField propDef = (LocalMetricField) item.getData();
						if (propDef != null) {
							if (propDef.isGroupBy() == true)
								image = StudioUIPlugin.getDefault().getImage(PropertyTableConstants.ICON_TOOLBAR_CHECK);
							else
								image = StudioUIPlugin.getDefault().getImage(PropertyTableConstants.ICON_TOOLBAR_UNCHECK);
						}
						if (image != null) {
							int tmpWidth = 0;
							int tmpHeight = 0;
							int tmpX = 0;
							int tmpY = 0;

							tmpWidth = fieldsViewer.getTable().getColumn(event.index).getWidth();
							tmpHeight = ((TableItem) event.item).getBounds().height;

							tmpX = image.getBounds().width;
							tmpX = (tmpWidth / 2 - tmpX / 2);
							tmpY = image.getBounds().height;
							tmpY = (tmpHeight / 2 - tmpY / 2);
							if (tmpX <= 0)
								tmpX = event.x;
							else
								tmpX += event.x;
							if (tmpY <= 0)
								tmpY = event.y;
							else
								tmpY += event.y;
							event.gc.drawImage(image, tmpX, tmpY);
						}
					}
				}
			});
		}

		fieldsViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent e) {
				toolBarProvider.getDeleteItem().setEnabled(e.getSelection().isEmpty() == false);
			}
		});
	}

	private CellEditor generateCellEditor(Table table, FIELD field) {
		switch (field) {
			case NAME:
				return new TextCellEditor(table);
			case AGGR_TYPE:
				return new AggregationTypeCellEditor(table, field.getPropertyName(), editor);
			case DATA_TYPE:
				return new LocalMetricDataTypeCellEditor(table, field.getPropertyName(), editor);
			case DEPENDING_FLDS:
				return new DependencyCellEditor(table, field.getPropertyName(), editor);
			case DESC:
				return new TextCellEditor(table);
			case GROUP_BY:
				return new CheckboxCellEditor(table);
			case URL_LINK:
				return new URLInfoCellEditor(table, field.getPropertyName(), editor);
			case URL_NAME:
				return null;
			default:
				return null;
		}
	}

	private List<LocalElement> getChildren(LocalMetric metric, String particleName) {
		if (particleName.equals(LocalMetric.ELEMENT_KEY_FIELD) == true) {
			return metric.getFields();
		}
		return metric.getUserDefinedFields();
	}

	private class LocalMetricFieldLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			FIELD fieldInfo = fields.get(columnIndex);
			if (fieldInfo == FIELD.DATA_TYPE) {
				LocalAttribute attribute = (LocalAttribute) element;
				if (attribute.getDataType().equals(PROPERTY_TYPES.BOOLEAN.getLiteral()) == true) {
					return StudioUIPlugin.getDefault().getImage(PropertyTableConstants.ICON_TOOLBAR_BOOLEAN);
				} else if (attribute.getDataType().equals(PROPERTY_TYPES.DATE_TIME.getLiteral()) == true) {
					return StudioUIPlugin.getDefault().getImage(PropertyTableConstants.ICON_TOOLBAR_DATE);
				} else if (attribute.getDataType().equals(PROPERTY_TYPES.DOUBLE.getLiteral()) == true) {
					return StudioUIPlugin.getDefault().getImage(PropertyTableConstants.ICON_TOOLBAR_DOUBLE);
				} else if (attribute.getDataType().equals(PROPERTY_TYPES.INTEGER.getLiteral()) == true) {
					return StudioUIPlugin.getDefault().getImage(PropertyTableConstants.ICON_TOOLBAR_INTEGER);
				} else if (attribute.getDataType().equals(PROPERTY_TYPES.LONG.getLiteral()) == true) {
					return StudioUIPlugin.getDefault().getImage(PropertyTableConstants.ICON_TOOLBAR_LONG);
				} else if (attribute.getDataType().equals(PROPERTY_TYPES.STRING.getLiteral()) == true) {
					return StudioUIPlugin.getDefault().getImage(PropertyTableConstants.ICON_TOOLBAR_STRING);
				}
				return null;
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			LocalAttribute field = (LocalAttribute) element;
			FIELD fieldInfo = fields.get(columnIndex);
			if (fieldInfo == FIELD.GROUP_BY) {
				return null;
			}
			return field.getPropertyValue(fieldInfo.getPropertyName());
		}

	}

	private class LocalMetricCellModifier implements ICellModifier {

		@Override
		public boolean canModify(Object element, String property) {
			if (property.equals(LocalMetricField.PROP_KEY_AGG_FUNCTION)) {
				LocalMetricField field = (LocalMetricField) element;
				// return false if user clicks AggregationType column and the
				// GroupBy field is checked in the corresponding row , otherwise
				// return true
				return (!field.isGroupBy());
			} else if (property.equals(LocalMetricField.PROP_KEY_URL_NAME) || property.equals(LocalMetricUserDefinedField.PROP_KEY_URL_NAME)) {
				return false;
			} else if (property.equals(LocalMetricField.PROP_KEY_DEP_FIELD)) {
				LocalMetricField field = (LocalMetricField) element;
				if (field.isGroupBy() == false) {
					if (field.getAggregationFunction().equalsIgnoreCase("Average") || field.getAggregationFunction().equalsIgnoreCase("StandardDeviation") || field.getAggregationFunction().equalsIgnoreCase("Variance")) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return true;
			}
		}

		@Override
		public Object getValue(Object element, String property) {
			LocalAttribute attribute = (LocalAttribute) element;
			FIELD field = FIELD.getByName(property);
			if (field == FIELD.GROUP_BY) {
				return attribute.getPropertyValue(field.getPropertyName()).equalsIgnoreCase("true");
			}
			return attribute.getPropertyValue(field.getPropertyName());
		}

		@Override
		public void modify(Object element, String property, Object value) {
			if (element instanceof Item) {
				element = ((Item) element).getData();
			}
			LocalAttribute attribute = (LocalAttribute) element;
			attribute.setPropertyValue(FIELD.getByName(property).getPropertyName(), value == null ? null : value.toString());
			fieldsViewer.update(element, new String[] { property });
		}

	}

}
