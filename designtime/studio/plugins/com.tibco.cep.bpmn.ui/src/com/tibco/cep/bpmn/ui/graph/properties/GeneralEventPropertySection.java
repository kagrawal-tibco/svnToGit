package com.tibco.cep.bpmn.ui.graph.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;



/**
 * 
 * @author majha
 *
 */
public class GeneralEventPropertySection extends GeneralNodePropertySection {

	@SuppressWarnings("unused")
	private boolean refresh;

	public GeneralEventPropertySection() {
		super();
	}
	
	@Override
	public void refresh() {
		try{
			this.refresh = true;
			if (fTSENode != null) { 
				super.refresh();
				@SuppressWarnings("unused")
				String description = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION);
				@SuppressWarnings("unused")
				String nodeName = (String) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
				@SuppressWarnings("unused")
				EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				@SuppressWarnings("unused")
				EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
				EObject userObject = (EObject) fTSENode.getUserObject();
				@SuppressWarnings("unused")
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);

			}if (fTSEConnector!= null) { 
				super.refresh();
			}
			if (fTSEEdge != null) {
			}
			if (fTSEGraph != null) {
			}
			
		}finally{
			this.refresh = false;
		}
		
	}

	@Override
	protected boolean isDestinationPropertyVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * priority is for Start Msg Event so it can be set for the start rule
	 * @return
	 */
	@Override
	protected boolean isPriorityPropertyVisible() {
		if (fTSENode != null) { 
			@SuppressWarnings("unused")
			EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			@SuppressWarnings("unused")
			EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
			if(userObjWrapper.isInstanceOf(BpmnModelClass.START_EVENT)){
				return true;
			}
			
		}
		return false;
	}

	
	protected ELEMENT_TYPES[] getElementsTypeSupportedForAction() {
		// TODO Auto-generated method stub
		return new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT };
	}
	
	protected PropertyNodeTypeGroup getNodeTypeData() {
		PropertyNodeTypeGroup events = null;
		if(fTSEConnector != null){
			PropertyNodeTypeGroup bevents = new PropertyNodeTypeGroup("Boundary Events",
					getBoundaryEventPaletteItems(),
					BpmnImages.EVENTS_PALETTE_GRAPH);
			 events = new PropertyNodeTypeGroup(
					"Events",
					Arrays.asList(new PropertyNodeTypeGroup[] { bevents }),
					BpmnImages.GATEWAY_PALETTE_GRAPH);
		}else {
			 PropertyNodeTypeGroup sevents = new PropertyNodeTypeGroup("Start",
						getStartEventPaletteItems(),
						BpmnImages.START_EVENT_PALETTE_GRAPH);
				PropertyNodeTypeGroup eevents = new PropertyNodeTypeGroup("End",
						getEndEventPaletteItems(),
						BpmnImages.END_EVENT_PALETTE_GRAPH);
//				PropertyNodeTypeGroup ievents = new PropertyNodeTypeGroup("Intermediate",
//						getIntermediateEventPaletteItems(),
//						BpmnImages.EVENTS_PALETTE_GRAPH);
				 events = new PropertyNodeTypeGroup(
						"Events",
						Arrays.asList(new PropertyNodeTypeGroup[] { sevents, eevents }),
						BpmnImages.GATEWAY_PALETTE_GRAPH);
		}
		
		
		PropertyNodeTypeGroup roots = new PropertyNodeTypeGroup("roots",
				Arrays.asList(new PropertyNodeTypeGroup[] {events }),
				BpmnImages.GATEWAY_PALETTE_GRAPH);
		
		return roots;
	}
	
	protected List<BpmnPaletteGroupItem> getStartEventPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items.addAll(toolDefs
				.getPaletteToolBySubType(BpmnModelClass.START_EVENT, false));
		return items;
	}
	
	protected List<BpmnPaletteGroupItem> getIntermediateEventPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items.addAll(toolDefs.getPaletteToolItemByType(
				BpmnModelClass.INTERMEDIATE_CATCH_EVENT,null, true));
		items.addAll(toolDefs.getPaletteToolItemByType(
				BpmnModelClass.INTERMEDIATE_THROW_EVENT, null, true));


		return items;
	}
	
	protected List<BpmnPaletteGroupItem> getBoundaryEventPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items.addAll(toolDefs.getPaletteToolItemByType(
				BpmnModelClass.INTERMEDIATE_CATCH_EVENT,null, true));


		return items;
	}
	
	protected List<BpmnPaletteGroupItem> getEndEventPaletteItems() {
		List<BpmnPaletteGroupItem> items = new ArrayList<BpmnPaletteGroupItem>();
		BpmnPaletteModel toolDefs = PaletteLoader.getBpmnPaletteModel(fProject);
		items
				.addAll(toolDefs
						.getPaletteToolBySubType(BpmnModelClass.END_EVENT, false));

		return items;
	}	
}