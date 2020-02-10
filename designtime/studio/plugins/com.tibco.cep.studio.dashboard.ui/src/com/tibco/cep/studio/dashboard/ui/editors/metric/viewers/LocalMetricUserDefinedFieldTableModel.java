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
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricUserDefinedField;
import com.tibco.cep.studio.dashboard.ui.editors.util.Messages;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;

/**
 * @deprecated
 * @author anpatil
 *
 */
public class LocalMetricUserDefinedFieldTableModel extends DefaultTableModel {

	private static final long serialVersionUID = -6240004351271435773L;

	public static final LinkedHashMap<Integer, String[]> PROPERTIES = new LinkedHashMap<Integer, String[]>();

	static {
		PROPERTIES.put(FieldPositions.UDFIELD_NAME_POS, new String[] { LocalMetricUserDefinedField.PROP_KEY_NAME, Messages.getString("metric.property.Name") });

		PROPERTIES.put(FieldPositions.UDFIELD_DATA_TYPE_POS, new String[] { LocalMetricUserDefinedField.PROP_KEY_DATA_TYPE, Messages.getString("metric.property.DataType") });

		PROPERTIES.put(FieldPositions.UDFIELD_URL_NAME_POS, new String[] { LocalMetricUserDefinedField.PROP_KEY_URL_NAME, Messages.getString("metric.property.URLName") });

		PROPERTIES.put(FieldPositions.UDFIELD_URL_LINK_POS, new String[] { LocalMetricUserDefinedField.PROP_KEY_URL_LINK, Messages.getString("metric.property.URLLink") });

		PROPERTIES.put(FieldPositions.UDFIELD_DESCRIPTION_POS, new String[] { LocalMetricUserDefinedField.PROP_KEY_DESCRIPTION, Messages.getString("metric.property.Description") });
	}

	private ExceptionHandler exceptionHandler;

	private LocalMetric localMetric;

	private ISynElementChangeListener listener;

	public LocalMetricUserDefinedFieldTableModel(ExceptionHandler exceptionHandler, LocalMetric localMetric) {
		this.exceptionHandler = exceptionHandler;
		this.localMetric = localMetric;
		this.listener = new LocalMetricUserDefinedFieldChangeListener();
		this.localMetric.subscribe(listener, LocalMetric.ELEMENT_KEY_USER_DEFINED_FIELD);
		for (String[] propertyArray : PROPERTIES.values()) {
			this.localMetric.subscribeForPropertyChange(listener, propertyArray[0]);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
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
			return localMetric.getUserDefinedFields().size();
		} catch (Exception e) {
			exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read user defined fields in " + localMetric, e));
			return 0;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String propertyName = PROPERTIES.get(columnIndex)[0];
		try {
			LocalMetricUserDefinedField field = getUserDefinedField(rowIndex);
			return field.getPropertyValue(propertyName);
		} catch (Exception e) {
			exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read " + propertyName + " in user defined fields in " + localMetric, e));
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		try {
			if (LocalMetricUserDefinedField.PROP_KEY_URL_NAME.equals(PROPERTIES.get(columnIndex)[0]) == true) {
				return false;
			}
			return true;
		} catch (Exception e) {
			exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read " + LocalMetricUserDefinedField.PROP_KEY_URL_NAME + " in user defined fields in " + localMetric, e));
			return false;
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		String propertyName = PROPERTIES.get(columnIndex)[0];
		try {
			LocalMetricUserDefinedField metricUserDefinedField = getUserDefinedField(rowIndex);
			metricUserDefinedField.setPropertyValue(propertyName, value == null ? null : value.toString());
			// don't fire event telling everyone listening that a cell has updated, LocalMetricUserDefinedFieldChangeListener will do that
		} catch (Exception e) {
			exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not set " + propertyName + " in user defined fields in " + localMetric, e));
		}
	}

	private LocalMetricUserDefinedField getUserDefinedField(int rowIndex) throws Exception {
		return (LocalMetricUserDefinedField) localMetric.getUserDefinedFields().get(rowIndex);
	}

	class LocalMetricUserDefinedFieldChangeListener implements ISynElementChangeListener {

		private List<LocalElement> userDefinedFields;

		private List<String> properties;

		private LocalMetricUserDefinedFieldChangeListener() {
			try {
				properties = new ArrayList<String>();
				for (String[] propertyArray : PROPERTIES.values()) {
					properties.add(propertyArray[0]);
				}
				userDefinedFields = localMetric.getChildren(LocalMetric.ELEMENT_KEY_USER_DEFINED_FIELD);
			} catch (Exception e) {
				exceptionHandler.log(exceptionHandler.createStatus(IStatus.WARNING, "could not read user defined fields in " + localMetric, e));
			}
		}

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			final int idx = userDefinedFields.size();
			userDefinedFields = localMetric.getChildren(LocalMetric.ELEMENT_KEY_USER_DEFINED_FIELD);
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
			final int idx = userDefinedFields.indexOf(removedElement);
			userDefinedFields = localMetric.getChildren(LocalMetric.ELEMENT_KEY_USER_DEFINED_FIELD);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					fireTableRowsDeleted(idx, idx);
				}

			});
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
		}

		@Override
		public String getName() {
			return LocalMetricUserDefinedFieldTableModel.class.getSimpleName() + "Listener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			final int row = userDefinedFields.indexOf(property.getParent());
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