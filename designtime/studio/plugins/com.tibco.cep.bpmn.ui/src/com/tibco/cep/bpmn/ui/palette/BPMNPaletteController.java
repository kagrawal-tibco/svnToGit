package com.tibco.cep.bpmn.ui.palette;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.ui.palette.actions.PaletteController;

/**
 * 
 * @author sasahoo
 *
 */
public class BPMNPaletteController extends PaletteController {

	public BPMNPaletteController(IWorkbenchWindow window) {
		super(window);
	}

	@Override
	protected void updatePaletteItem(CLabel label) {
		BpmnPaletteResourceUtil.setPaletteFont(label);
	}
	
}
