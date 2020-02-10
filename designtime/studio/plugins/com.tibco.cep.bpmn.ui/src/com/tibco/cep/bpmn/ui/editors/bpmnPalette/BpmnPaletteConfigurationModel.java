package com.tibco.cep.bpmn.ui.editors.bpmnPalette;

import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;

/**
 * 
 * @author majha
 *
 */

public class BpmnPaletteConfigurationModel {
	BpmnPaletteModel paletteModel;
	
	public BpmnPaletteConfigurationModel(BpmnPaletteModel model) {
		paletteModel = model;
	}

	public  BpmnPaletteModel getBpmnPaletteModel() {
		return paletteModel;
	}
	
	public  void setBpmnPaletteModel(BpmnPaletteModel model) {
		this.paletteModel = model;
	}
}
