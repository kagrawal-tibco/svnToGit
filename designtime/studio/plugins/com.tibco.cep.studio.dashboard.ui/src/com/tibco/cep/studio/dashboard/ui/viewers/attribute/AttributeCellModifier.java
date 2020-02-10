package com.tibco.cep.studio.dashboard.ui.viewers.attribute;

import org.eclipse.swt.widgets.Item;

import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;

/**
 * This class implements an ICellModifier An ICellModifier is called when the
 * user modifies a cell in the tableViewer
 */

public class AttributeCellModifier extends AbstractLockingCellModifier {
	
	/**
	 * Constructor
	 * 
	 * @param LocalMetricUserDefinedFieldView
	 *            an instance of a AttributeViewer
	 */
	public AttributeCellModifier(AttributeViewer tableViewer) {
		super(tableViewer);
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean internalCanModify(Object element, String property) {
		int columnIndex = attributeViewer.getColumnNamesList().indexOf(property);
		if (element instanceof LocalAttribute) {
			switch (columnIndex) {
				case 0 /* STATUS_COLUMN */:
					return false;
				case 1 /* NAME_COLUMN */:
				case 2 /* DATATYPE_COLUMN */:
				case 3 /* DESCRIPTION_COLUMN */:
					return true;
				default:
					return false;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
	 *      java.lang.String)
	 */
	public Object getValue(Object element, String property) {
		// Find the index of the column
		int columnIndex = attributeViewer.getColumnNamesList().indexOf(property);
		Object result = "";

		if (element instanceof LocalAttribute) {
			try {
				LocalAttribute localAttribute = (LocalAttribute) element;
				switch (columnIndex) {
					case 0 /* STATUS_COLUMN */:
						return "!";
					case 1 /* NAME_COLUMN */:
						return localAttribute.getName();
					case 2 /* DATATYPE_COLUMN */:
						String[] supportedDataTypes = localAttribute.getSupportedDataTypes();
						for (int i = 0; i < supportedDataTypes.length; i++) {
							String datatype = localAttribute.getSupportedDataTypes()[i];
							if (datatype.equals(localAttribute.getDataType()) == true){
								return i;
							}
						}
						return -1;
					case 3 /* DESCRIPTION_COLUMN */:
						return localAttribute.getDescription();
					default:
						return "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
	 *      java.lang.String, java.lang.Object)
	 */
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
					case 0 /* STATUS_COLUMN */:
						break;
					case 1 /* NAME_COLUMN */:
						localAttribute.setName(value.toString());
						break;
					case 2 /* DATATYPE_COLUMN */:
						/*
						 * Set the datatype
						 */
						localAttribute.setDataType(localAttribute.getSupportedDataTypes()[(Integer)value]);
						break;
					case 3 /* DESCRIPTION_COLUMN */:
						localAttribute.setDescription(((String) value));
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
		getAttributeViewer().refresh();
	}

	public AttributeViewer getAttributeViewer() {
		return attributeViewer;
	}

	public void setAttributeViewer(AttributeViewer attributeViewer) {
		this.attributeViewer = attributeViewer;
	}

}