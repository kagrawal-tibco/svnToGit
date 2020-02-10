package com.tibco.cep.studio.dashboard.ui.editors.metric.viewers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.eclipse.core.runtime.IStatus;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;

/**
 * @deprecated
 * @author anpatil
 *
 */
public class LocalMetricFieldTableModel extends DefaultTableModel {

	private static final long serialVersionUID = -6240004351271435773L;

    static final LinkedHashMap<Integer, String[]> PROPERTIES = new LinkedHashMap<Integer, String[]>();

	static {
		PROPERTIES.put(FieldPositions.FIELD_NAME_POS, new String[] { LocalMetricField.PROP_KEY_NAME, Messages.getString("metric.property.Name") });

		PROPERTIES.put(FieldPositions.FIELD_GROUP_BY_POS, new String[] { LocalMetricField.PROP_KEY_IS_GROUP_BY, Messages.getString("metric.property.GroupBy") });

		PROPERTIES.put(FieldPositions.FIELD_AGGR_FUNCTION_POS, new String[] { LocalMetricField.PROP_KEY_AGG_FUNCTION, Messages.getString("metric.property.AggrType") });

		PROPERTIES.put(FieldPositions.FIELD_DEPENDING_FIELDS_POS, new String[] { LocalMetricField.PROP_KEY_DEP_FIELD, Messages.getString("metric.property.DependingFields") });

		PROPERTIES.put(FieldPositions.FIELD_DATA_TYPE_POS, new String[] { LocalMetricField.PROP_KEY_DATA_TYPE, Messages.getString("metric.property.DataType") });

		PROPERTIES.put(FieldPositions.FIELD_URL_NAME_POS, new String[] { LocalMetricField.PROP_KEY_URL_NAME, Messages.getString("metric.property.URLName") });

		PROPERTIES.put(FieldPositions.FIELD_URL_LINK_POS, new String[] { LocalMetricField.PROP_KEY_URL_LINK, Messages.getString("metric.property.URLLink") });

		PROPERTIES.put(FieldPositions.FIELD_DESCRIPTION_POS, new String[] { LocalMetricField.PROP_KEY_DESCRIPTION, Messages.getString("metric.property.Description") });
	}

	private ExceptionHandler exceptionHandler;

	private LocalMetric localMetric;

	private ISynElementChangeListener listener;

	public LocalMetricFieldTableModel(LocalMetric localMetric) {
		this.localMetric = localMetric;
		this.listener = new LocalMetricFieldChangeListener();
		this.localMetric.subscribe(listener, LocalMetric.ELEMENT_KEY_FIELD);
		for (String[] propertyArray : PROPERTIES.values()) {
			this.localMetric.subscribeForPropertyChange(listener, propertyArray[0]);
		}
		this.exceptionHandler = DashboardUIPlugin.getInstance().getExceptionHandler();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		String propertyName = PROPERTIES.get(columnIndex)[0];
		if (LocalMetricField.PROP_KEY_IS_GROUP_BY.equals(propertyName) == true) {
			return Boolean.class;
		}
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return PROPERTIES.size();
	}

	@Override
	public String getColumnName(int column) {
		return PROPERTIES.get(column)[1];
	}

	@Override
	public int getRowCount() {
		if (localMetric == null) {
			return 0;
		}
		try {
			return localMetric.getFields(false).size();
		} catch (Exception e) {
			exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read fields in " + localMetric, e));
			return 0;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String propertyName = PROPERTIES.get(columnIndex)[0];
		try {
			LocalMetricField field = getField(rowIndex);
			String propertyValue = field.getPropertyValue(propertyName);
			if (LocalMetricField.PROP_KEY_IS_GROUP_BY.equals(propertyName) == true) {
				return Boolean.valueOf(propertyValue);
			}
			if (LocalMetricField.PROP_KEY_AGG_FUNCTION.equals(propertyName) == true) {
				if (field.isGroupBy() == true) {
					return null;
				}
				return propertyValue;
			}
			return propertyValue;
		} catch (Exception e) {
			exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read " + propertyName + " in fields in " + localMetric, e));
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		String propertyName = PROPERTIES.get(columnIndex)[0];
		try {
			LocalMetricField metricField = getField(rowIndex);
			if (LocalMetricField.PROP_KEY_AGG_FUNCTION.equals(propertyName) == true) {
				// allow aggregation function editing for non group by fields only
				try {
					return metricField.isGroupBy() == false;
				} catch (Exception e) {
					exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read " + LocalMetricField.PROP_KEY_IS_GROUP_BY + " in fields in " + localMetric, e));
					return false;
				}
			}
			if (LocalMetricField.PROP_KEY_URL_NAME.equals(propertyName) == true) {
				return false;
			}
			if (LocalMetricField.PROP_KEY_DEP_FIELD.equals(propertyName) == true) {
				return metricField.requiresDependingFields();
			}
			return true;
		} catch (Exception e) {
			exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read " + propertyName + " in fields in " + localMetric, e));
			return false;
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		String propertyName = PROPERTIES.get(columnIndex)[0];
		try {
			LocalMetricField metricField = getField(rowIndex);
			metricField.setPropertyValue(propertyName, value == null ? null : value.toString());
			// don't fire event telling everyone listening that a cell has updated, LocalMetricUserDefinedFieldChangeListener will do that
			// fireTableCellUpdated(rowIndex, columnIndex);
		} catch (Exception e) {
			exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not set " + propertyName + " in fields in " + localMetric, e));
		}
	}

	private LocalMetricField getField(int rowIndex) throws Exception {
		return (LocalMetricField) localMetric.getFields(false).get(rowIndex);
	}

	class LocalMetricFieldChangeListener implements ISynElementChangeListener {

		private List<LocalElement> fields;

		private List<String> properties;

		private LocalMetricFieldChangeListener() {
			try {
				properties = new ArrayList<String>();
				for (String[] propertyArray : PROPERTIES.values()) {
					properties.add(propertyArray[0]);
				}
				fields = localMetric.getChildren(LocalMetric.ELEMENT_KEY_FIELD);
			} catch (Exception e) {
				exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read fields in " + localMetric, e));
			}
		}

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			final int idx = fields.size();
			fields = localMetric.getChildren(LocalMetric.ELEMENT_KEY_FIELD);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					fireTableRowsInserted(idx, idx);
				}

			});

		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
			final int idx = fields.indexOf(removedElement);
			if (idx != -1) {
				fields = localMetric.getChildren(LocalMetric.ELEMENT_KEY_FIELD);
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						fireTableRowsDeleted(idx, idx);
					}

				});
			}
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
		}

		@Override
		public String getName() {
			return LocalMetricFieldTableModel.class.getSimpleName() + "Listener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			final int row = fields.indexOf(property.getParent());
			final int col = properties.indexOf(property.getName());
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					fireTableCellUpdated(row, col);
				}

			});
		}

	}
}