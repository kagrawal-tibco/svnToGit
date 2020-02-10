package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.studio.common.util.EntityNameHelper;

/**
 * 
 * @author sasahoo
 *
 */
public class VariablePropertyTableModelListener {

	//private VariableMappingPropertySection section;
	private ModelController controller;
	//private IProject project;

	public VariablePropertyTableModelListener(IProject project, ModelController controller, VariableMappingPropertySection section){
		//this.project = project;
		this.controller = controller;
		//this.section = section;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
/*	public void tableChanged(TableModelEvent e) {
		try{ 
			DefaultTableModel model = (DefaultTableModel) TableModelWrapperUtils.getActualTableModel(table.getModel());
			if(e.getType() == TableModelEvent.UPDATE){
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();

				if(row == - 1 || row >=  table.getRowCount()) {
					return;
				}

				List<EObjectWrapper<EClass, EObject>> propDefs = 
						(ArrayList<EObjectWrapper<EClass, EObject>>)controller.getPropertyDefinitions(section.getUserObject());

				Object obj = model.getValueAt(row, column);
				EObjectWrapper<EClass, EObject> propDef = propDefs.get(row);
				switch(column){
				case 0:
					//updatePropertyDefinitionName(propDefs, propDef, obj.toString(), row, column);
					break;
				case 1:
//					if (obj instanceof EObjectWrapper) {
//						EObjectWrapper<EClass, EObject> itenDef = (EObjectWrapper<EClass, EObject>) obj;
//						propDef.setAttribute(
//								BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
//								itenDef.getEInstance());
//					}
					break;
				case 2:
					EObjectWrapper<EClass, EObject> attribute = EObjectWrapper.wrap((EObject)propDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
					if (attribute
							.getAttribute(BpmnMetaModelConstants.E_ATTR_ID) != null) {
						if (controller.isArrayItemdef(attribute) != (Boolean) obj) {
							controller.switchItemdefinitionType(
									project.getName(), propDef, (Boolean) obj);
							
							section.updatePropertyList();
						}
					}
					break;
				}
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	*/
	/**
	 * @param list
	 * @param name
	 * @return
	 */
	public boolean isValidPropertyDefinitionName(String name){
		try{
			if(name.trim().equalsIgnoreCase("")) {
				return false;
			}
			if(!EntityNameHelper.isValidBEEntityIdentifier(name/*.trim()*/)){
				return false; // validating the character
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * @param list
	 * @param name
	 * @return
	 */
	public static boolean isDuplicatePropertyDefinitionName(List<EObjectWrapper<EClass, EObject>> list, String name){
		try{
			Iterator<EObjectWrapper<EClass, EObject>> iterator = list.iterator();
			while(iterator.hasNext()){
				String propName = iterator.next().getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
				if( propName!= null && (propName).equalsIgnoreCase(name)){
					return true;
				}
			}
		}catch(Exception e){
			BpmnUIPlugin.log(e);
		}
		return false;
	}
	
	/**
	 * @param list
	 * @param propDef
	 * @param name
	 * @param row
	 * @param column
	 */
	public Boolean updatePropertyDefinitionName(  List<EObjectWrapper<EClass, EObject>> list,
												final EObjectWrapper<EClass, EObject> propDef,
												String name
												) {
		if(controller.getPropertyName(propDef).equals(name)){
			return false;
		}
		boolean validDef = isValidPropertyDefinitionName(name);
		if(validDef && controller.getPropertyName(propDef).equalsIgnoreCase(name)){
			return  true;
		}
		boolean duplicateDef = isDuplicatePropertyDefinitionName(list, name);
		if(!validDef){
			//table.setValueAt(controller.getPropertyName(propDef), row, column);
			Display.getDefault().asyncExec(new Runnable(){
				public void run() {
					MessageDialog.openError(Display.getDefault().getActiveShell(), BpmnMessages.getString("invalid_property_title"), BpmnMessages.getString("invalid_property_message"));
				}});
			return false;
		}else if(duplicateDef){
			//.setValueAt(controller.getPropertyName(propDef), row, column);
			Display.getDefault().asyncExec(new Runnable(){
				public void run() {
					MessageDialog.openError(Display.getDefault().getActiveShell(), BpmnMessages.getString("duplicate_property_title"), BpmnMessages.getString("duplicate_property_message"));
				}});
			return false;
		}
		if (validDef && !duplicateDef) {
			propDef.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
			return true;
			//section.updatePropertyList();
		}
		return false;
	}
}