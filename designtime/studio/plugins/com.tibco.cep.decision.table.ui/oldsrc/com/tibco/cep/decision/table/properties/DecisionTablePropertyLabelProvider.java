package com.tibco.cep.decision.table.properties;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTablePropertyLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String text = null;
		Object selElement = null;
		if (element instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) element;
			selElement = selection.getFirstElement();
		}
		
		if(selElement instanceof TableRuleVariable){
//			TableRuleVariable tableRuleVariable = (TableRuleVariable) selElement;
			//TODO
		}
		
		DecisionTableEditor editor = (DecisionTableEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IDecisionTableEditorInput dtEditorInput = (IDecisionTableEditorInput)editor.getEditorInput();
		Table table = dtEditorInput.getTableEModel();
		if(table != null){
			text =  "Decision Table: "+ table.getName();
		}
		return text != null ? text : "";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		return DecisionTableUIPlugin.getDefault().getImage("icons/decisiontablerulefunctions_16x16.png");
	}
}
