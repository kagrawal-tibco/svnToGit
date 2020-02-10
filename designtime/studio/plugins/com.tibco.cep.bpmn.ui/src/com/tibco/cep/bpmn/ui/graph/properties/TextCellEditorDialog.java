package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.ui.forms.components.TextAndDialogCellEditor;

public class TextCellEditorDialog extends TextAndDialogCellEditor {

	
	private String projName;
	private VariableMappingPropertySection obj ;
	TextCellEditorDialog(String projName,Composite comp , VariableMappingPropertySection varMapObj){
		super(comp,null,null);
		//this.parent=comp;
		this.obj = varMapObj ;
		this.projName=projName;
	}
	
	

	@Override
	protected void updateContents(Object value) {
		String prop=null;
		try{
		String[] val=value.toString().split("-");
		 prop= val[0].toString();
		}catch(Exception e){
			prop=(String)value;
		}
		if (textField == null) {
			return;
		}

        String text = "";
        if (value != null) {
			text = prop.toString();
		}
        textField.setText(text);
       // textField.setImage(addImg);
	}
	
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		Shell shell = window.getShell();
		
		
		return invokePropertySelector(shell);
	}
	
	private Object invokePropertySelector(Shell shell){
		ProcessVariables propertyDefinition = (ProcessVariables) ((IStructuredSelection) obj.getTableViewer().getSelection()).getFirstElement();
		String propType = propertyDefinition.getType();
		String conceptType = "Concept";
		if (conceptType.equals(propType) ) {
			propType = PROPERTY_TYPES.CONCEPT.getName() ;
		}
		VariablePropertySelector conceptPropertySelector = new VariablePropertySelector(shell,
				projName,
				null,
				textField.getText(), 
				propType,
				false );
		if (conceptPropertySelector.open() == Dialog.OK) {
			String prop = conceptPropertySelector.getPropertyType();
			String val=conceptPropertySelector.getValue();
			return val+"-"+prop;
		}
		return null;
	}
	



}
