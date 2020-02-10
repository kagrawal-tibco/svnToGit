package com.tibco.cep.studio.decision.table.ui.properties;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

public class DecisionTablePropertiesLabelProvider extends LabelProvider{

	public String getText(Object element) {
		String text = null;
		
		IDecisionTableEditor editor = (IDecisionTableEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		Table table = editor.getTable();
		if(table != null){
			text =  "Decision Table: "+ table.getName();
		}
		return text != null ? text : "";
	}
	
	public Image getImage(Object element) {
		return DecisionTableUIPlugin.getDefault().getImage("icons/decisiontablerulefunctions_16x16.png");
	}
}
