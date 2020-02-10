package com.tibco.cep.bpmn.ui.graph.properties;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ERROR_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SIGNAL_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TIMER_EVENT_DEFINITION;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * 
 * @author majha
 * 
 */
public class GeneralBoundaryEventPropertySection extends
		GeneralStartEventPropertySection {

	public GeneralBoundaryEventPropertySection() {
		super();
	}
	
	@Override
	protected boolean isCancelActivityCheckVisible() {
		// TODO Auto-generated method stub
		return true;
	}
	
	protected void handleForBoundaryEventChange(BpmnPaletteGroupItem item){
		EClass boundaryEventDefinitionType = null;
		if (item != null) {
			boundaryEventDefinitionType = getBoundaryEventDefinitionType(item);
			if (boundaryEventDefinitionType != null) {
				if (MESSAGE_EVENT_DEFINITION
						.isSuperTypeOf(boundaryEventDefinitionType)
						|| SIGNAL_EVENT_DEFINITION
								.isSuperTypeOf(boundaryEventDefinitionType)
						|| TIMER_EVENT_DEFINITION
								.isSuperTypeOf(boundaryEventDefinitionType)) {
					cancelActivityCheck.setEnabled(true);
					cancelActivityCheck.setSelection(true);
				} else if (ERROR_EVENT_DEFINITION
						.isSuperTypeOf(boundaryEventDefinitionType)) {
					cancelActivityCheck.setEnabled(false);
					cancelActivityCheck.setSelection(true);
				}
			} else {
				cancelActivityCheck.setEnabled(false);
				cancelActivityCheck.setSelection(false);
			}
		}else{
			cancelActivityCheck.setEnabled(false);
			cancelActivityCheck.setSelection(false);
		}
	}
	
	private EClass getBoundaryEventDefinitionType(BpmnPaletteGroupItem item) {
		BpmnCommonPaletteGroupItemType itemType = item.getItemType();
		EClass modelType = null;
		
		if (itemType.getType() ==  BpmnCommonPaletteGroupItemType.EMF_TYPE) {
			ExpandedName extClassSpec = ((BpmnCommonPaletteGroupEmfItemType)itemType).getExtendedType();
			modelType = BpmnMetaModel.getInstance().getEClass(extClassSpec);
		}
		return modelType;
	}

}