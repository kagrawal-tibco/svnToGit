package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.RGB;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.diagramming.ui.ExpandedSubprocessNodeUI;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
@SuppressWarnings("serial")
public class BPMNExpandedSubprocessNodeUI extends ExpandedSubprocessNodeUI {
	public RGB color= new RGB(255, 204, 100);
	public void draw(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();
		if (owner != null && owner.getUserObject() != null && owner.getUserObject() instanceof EObject) {
			final EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap((EObject) owner.getUserObject());
			if ((Boolean) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT)) {
				this.setBorderWidth(2);
				this.setDashedBorder(true);
			}else{
				this.setDashedBorder(false);
			}
		}
		
		super.draw(graphics);
	}	
	
	public TSEColor getColor(){
		return new TSEColor(color.red,color.green,color.blue);
		
	}
	public void setNodeColor(){
		this.setFillColor(getColor());
		this.setBorderColor(getColor());
	}
}
