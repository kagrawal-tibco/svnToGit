package com.tibco.cep.studio.dashboard.ui.wizards.metric;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.metric.viewers.DataTypeSelector;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AbstractLockingCellModifier;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.LocalSorterByPosition;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class GroupByFieldViewer extends AttributeViewer {

	public GroupByFieldViewer(FormToolkit toolKit, Composite parent, boolean editToolbarSupported, String childType) {
		super(toolKit, parent, editToolbarSupported, childType);
	}

	@Override
	public void createTable(Composite parent) {

		setColumnNames(new String[] { NAME_COLUMN, DATATYPE_COLUMN });

		Table table = toolKit.createTable(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
		setTable(table);

		GridData gridData = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(gridData);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableAutoResizeLayout tableLayout = new TableAutoResizeLayout(table);

		int colIdx = 0;
		// 2nd column with task Description
		TableColumn column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(NAME_COLUMN);
		column.setToolTipText(NAME_COLUMN);
		column.setResizable(true);
		tableLayout.addColumnData(new ColumnWeightData(1, true));

		column = new TableColumn(table, SWT.LEFT, colIdx++);
		column.setText(DATATYPE_COLUMN);
		column.setToolTipText(DATATYPE_COLUMN);
		column.setResizable(false);
		tableLayout.addColumnData(new ColumnWeightData(1, false));
	}

	@Override
	public void createTableViewer() {
		TableViewer tableViewer = new TableViewer(getTable());
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(getColumnNames());

		int idx = 0;
		CellEditor[] editors = new CellEditor[getColumnNames().length];

		//name editor
		TextCellEditor textEditor = new TextCellEditor(getTable());
		((Text) textEditor.getControl()).addVerifyListener(nameVerifier);
		editors[idx++] = textEditor;

		//data type editor
		editors[idx++] = new DataTypeCellEditor(getTable());

		// Assign the cell editors to the viewer
		tableViewer.setCellEditors(editors);
		tableViewer.setCellModifier(new SimpleAttributeCellModifier(this));
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new SimpleAttributeLabelProvider(this));
		tableViewer.setSorter(new LocalSorterByPosition());
		setTableViewer(tableViewer);
	}



	class SimpleAttributeLabelProvider extends LabelProvider implements ITableLabelProvider {

		private Map<PROPERTY_TYPES, Image> propertyTypeImages;

		SimpleAttributeLabelProvider(AttributeViewer attributeViewer) {
			propertyTypeImages = new HashMap<PROPERTY_TYPES, Image>();
			for (PROPERTY_TYPES propertyType : PROPERTY_TYPES.VALUES) {
				ImageDescriptor imageDescriptor = EditorUtils.getImageDescriptor(propertyType);
				if (imageDescriptor != null) {
					propertyTypeImages.put(propertyType, imageDescriptor.createImage());
				}
			}
			attributeViewer.getTable().addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					for (Image image : propertyTypeImages.values()) {
						image.dispose();
					}
				}

			});
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof LocalAttribute) {
				LocalAttribute localAttribute = (LocalAttribute) element;
				switch (columnIndex) {
					case 0:
						return localAttribute.getName();
					case 1:
						return localAttribute.getDataType();
					default:
						break;
				}
			}
			return "";
		}

		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof LocalAttribute) {
				LocalAttribute localAttribute = (LocalAttribute) element;
				switch (columnIndex) {
					case 0:
						return ((ContributableImageRegistry)DashboardUIPlugin.getInstance().getImageRegistry()).get(localAttribute);
					case 1:
						PROPERTY_TYPES propType = PROPERTY_TYPES.getByName(localAttribute.getDataType());
						return propertyTypeImages.get(propType);
					default:
						break;
				}
			}
			return null;
		}

	}



	class SimpleAttributeCellModifier extends AbstractLockingCellModifier {

		public SimpleAttributeCellModifier(AttributeViewer tableViewer) {
			super(tableViewer);
		}

		@Override
		public boolean internalCanModify(Object element, String property) {
			return true;
		}

		public Object getValue(Object element, String property) {
			// Find the index of the column
			int columnIndex = attributeViewer.getColumnNamesList().indexOf(property);
			Object result = "";
			if (element instanceof LocalAttribute) {
				try {
					LocalAttribute localAttribute = (LocalAttribute) element;
					switch (columnIndex) {
						case 0:
							return localAttribute.getName();
						case 1:
							return localAttribute;
						default:
							break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		@Override
		public void internalModify(Object element, String property, Object value) {
			// Find the index of the column
			int columnIndex = attributeViewer.getColumnNamesList().indexOf(property);
			if (element instanceof Item) {
				element = ((Item) element).getData();
			}
			if (element instanceof LocalAttribute) {
				try {
					LocalAttribute localAttribute = (LocalAttribute) element;
					switch (columnIndex) {
						case 0:
							localAttribute.setName(value.toString());
							break;
						case 1:
							//do nothing the DataTypeCellEditor will take care of this
							break;
						default:
							break;
					}

					/*
					 * Request the viewer to validate all fields
					 */
					getAttributeViewer().validate();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			/*
			 * Refresh to update with new values
			 */
			getAttributeViewer().getTableViewer().refresh(element);
		}

		public AttributeViewer getAttributeViewer() {
			return attributeViewer;
		}

		public void setAttributeViewer(AttributeViewer attributeViewer) {
			this.attributeViewer = attributeViewer;
		}

	}

	class DataTypeCellEditor extends DialogCellEditor {

		DataTypeCellEditor(Composite parent) {
			super(parent);
		}

		@Override
		protected void updateContents(Object value) {
			String text = "";
			if (value != null) {
				LocalAttribute attribute = (LocalAttribute) getValue();
				text = attribute.getDataType();
			}
			getDefaultLabel().setText(text);
		}

		@Override
		protected Object openDialogBox(Control cellEditorWindow) {
			LocalAttribute attribute = (LocalAttribute) getValue();
			DataTypeSelector dlg = new DataTypeSelector(cellEditorWindow.getShell(), attribute.getSupportedDataTypes(), attribute.getDataType());
			dlg.open();
			final String newDataType = (String) dlg.getFirstResult();
			if (newDataType != null && newDataType.equals(attribute.getDataType()) == false) {
				attribute.setDataType(newDataType);
			}
			return attribute;
		}

	}

}
