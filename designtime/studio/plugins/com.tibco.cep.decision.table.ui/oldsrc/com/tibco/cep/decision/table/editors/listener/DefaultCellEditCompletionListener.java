/**
 * 
 */
package com.tibco.cep.decision.table.editors.listener;

import javax.swing.JTextField;

import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * Update Function Text field here
 * @author aathalye
 *
 */
public class DefaultCellEditCompletionListener implements ICellEditCompletionListener {
	
	/**
	 * The text field to update
	 */
	private JTextField functionTextField;
	
	

	public DefaultCellEditCompletionListener(JTextField functionTextField) {
		this.functionTextField = functionTextField;
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.editors.listener.ICellEditCompletionListener#editingComplete(com.tibco.cep.decision.table.editors.listener.EditCompletionEvent)
	 */
	@Override
	public void editingComplete(EditCompletionEvent editCompletionEvent) {
		if (editCompletionEvent != null) {
			//Get data
			TableRuleVariable data = editCompletionEvent.getData();
			String expression = data.getExpr();
			if (expression != null) {
				functionTextField.setText(expression);
			}
		}
	}
}
