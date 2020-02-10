package com.tibco.cep.studio.tester.ui.editor.result;

import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.layer.stack.DefaultBodyLayerStack;

import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("unused")
public class WMResultsTableCellEditableRule extends EditableRule {

	private DefaultBodyLayerStack bodyLayer;
	private AbstractStudioResourceEditorPart editor;
	
	public WMResultsTableCellEditableRule(DefaultBodyLayerStack bodyLayer, AbstractStudioResourceEditorPart editor) {
		this.bodyLayer = bodyLayer;
		this.editor = editor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.nebula.widgets.nattable.config.EditableRule#isEditable(int, int)
	 */
	public boolean isEditable(int columnIndex, int rowIndex) {
		if (columnIndex == 0 || columnIndex == 1 ) {
			return false;
		}
		return true;
	}	


}