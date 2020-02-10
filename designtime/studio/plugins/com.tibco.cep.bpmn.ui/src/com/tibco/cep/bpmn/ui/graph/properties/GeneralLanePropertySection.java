package com.tibco.cep.bpmn.ui.graph.properties;



/**
 * 
 * @author majha
 *
 */
public class GeneralLanePropertySection extends GeneralGraphPropertySection {


	public GeneralLanePropertySection() {
		super();
	}
	
	@Override
	protected com.tibco.cep.bpmn.ui.graph.properties.GeneralGraphPropertySection.WidgetListener getWidgetListener() {
		// TODO Auto-generated method stub
		return new WidgetListener();
	}

}