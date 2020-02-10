package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Item;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;

public class VariableTableCellModifier implements ICellModifier {

	private Viewer viewer;	
	//private String projName;
	private ModelController controller;
	VariableMappingPropertySection varMappingPropertySection;
	VariableTableCellModifier(Viewer viewer,VariableMappingPropertySection varMappingPropertySection){
		this.viewer=viewer;
		this.varMappingPropertySection= varMappingPropertySection;
		//this.projName=projName;
		this.controller=varMappingPropertySection.controller;
	}
	@Override
	public boolean canModify(Object element, String property) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getValue(Object element, String property) {
		ProcessVariables prvar= (ProcessVariables)element;
		if(VariableMappingPropertySection.NAME.equals(property))
			return prvar.getName();
		else if(VariableMappingPropertySection.TYPE.equals(property))
			return prvar.getPropTypeValue();
		else if(VariableMappingPropertySection.MULTIPLE.equals(property))
			return prvar.getMultiple().booleanValue();
		else
			return null;
	}

	@Override
	public void modify(Object element, String property, Object value) {
		 if (element instanceof Item)
		      element = ((Item) element).getData();
		
		ProcessVariables prvar= (ProcessVariables)element;
		 ProcessVariables prVarTemp=new ProcessVariables(prvar.getName(),prvar.getType(),prvar.getPropTypeValue(),prvar.getMultiple());
	
		if(VariableMappingPropertySection.NAME.equals(property)){
			VariablePropertyTableModelListener varTblListener=new VariablePropertyTableModelListener(varMappingPropertySection.fEditor.getProject(),controller,varMappingPropertySection);
			boolean validDefname = varTblListener.updatePropertyDefinitionName((List<EObjectWrapper<EClass, EObject>>)controller.getPropertyDefinitions(varMappingPropertySection.getUserObject()),prvar.getPropDef(), value.toString());
			if(!validDefname)
				return;
			prvar.setName(value.toString());
		}
		else if(VariableMappingPropertySection.TYPE.equals(property)){
			
			String[] arr=value.toString().split("-");
			if(arr.length==1 && ProcessVariables.getImageIconString(arr[0])==null)
				return; 
			prvar.setPropTypeValue(arr[0]);
			if(arr.length==2)
				prvar.setType(arr[1]);
			else
				prvar.setType(arr[0]);
		}
		else if(VariableMappingPropertySection.MULTIPLE.equals(property)){
			prvar.setMultiple(((Boolean) value));
		}
		if(prVarTemp.isNotEqualObj(prvar)){
		/*varMappingPropertySection.removeRowfromTable(prVarTemp.getName());
		varMappingPropertySection.addRowToTable(prvar);*/
			varMappingPropertySection.updateRowTable(prVarTemp.getName(), prvar, property);
		}
		viewer.refresh();

	}
	public Viewer getViewer() {
		return viewer;
	}
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}

	
}
