package com.tibco.cep.studio.dashboard.ui.editors.views.queryparams;

import org.eclipse.swt.widgets.Item;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalQueryParam;
import com.tibco.cep.studio.dashboard.ui.utils.BuiltInTypesHelper;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AbstractLockingCellModifier;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;

/**
 * This class implements an ICellModifier An ICellModifier is called when the
 * user modifes a cell in the tableViewer
 */
public class QueryParamCellModifier extends AbstractLockingCellModifier {

	/**
	 * Constructor
	 * 
	 * @param LocalMetricUserDefinedFieldView
	 *            an instance of a AttributeViewer
	 */
	public QueryParamCellModifier(AttributeViewer tableViewer) {
		super(tableViewer);
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean internalCanModify(Object element, String property) {
		int columnIndex = attributeViewer.getColumnNamesList().indexOf(property);
		if (element instanceof LocalQueryParam) {
			switch (columnIndex) {
				case 0:
				case 1:
				case 2:
					return false;
				case 3:
					return true;
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
		if (element instanceof LocalQueryParam) {
			try {
				LocalQueryParam localQueryParam = (LocalQueryParam) element;
				switch (columnIndex) {
					case 0:
						return "!";
					case 1:
						return localQueryParam.getName();
					case 2:
						return BuiltInTypesHelper.getFilteredTypeIndex(localQueryParam.getDataType());
					case 3:
						return localQueryParam.getValue();
					default:
						break;
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
		if (element instanceof LocalQueryParam) {
			try {
				LocalQueryParam localQueryParam = (LocalQueryParam) element;
				switch (columnIndex) {
					case 0:
						break;
					case 1:
						localQueryParam.setName(value.toString());
						break;
					case 2:
						/*
						 * Set the datatype
						 */
						localQueryParam.setDataType((BuiltInTypesHelper.getPrimitiveTypeNames()[((Integer) value).intValue()]));
						break;
					case 3:
						localQueryParam.setValue((String) value);
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