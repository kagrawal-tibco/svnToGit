/**
 * 
 */
package com.tibco.cep.webstudio.server.ui.tools;

import com.google.gwt.user.client.Event;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tomsawyer.visualization.gwt.client.widget.canvas.TSCanvasWidget;
import com.tomsawyer.web.client.view.drawing.hittesting.TSGraphObjectRegion;
import com.tomsawyer.web.client.view.drawing.tool.predefinedtool.imagemap.TSImageMapSelectTool;

/**
 * This is class is used to extend the process element selection functionality.
 * 
 * @author dijadhav
 * 
 */

public class ProcessElementSelectTool extends TSImageMapSelectTool {
	/**
	 * Parameterized constructor.
	 * 
	 * @param canvasWidget
	 * @param toolId
	 */
	public ProcessElementSelectTool(TSCanvasWidget canvasWidget, String toolId) {
		super(canvasWidget, toolId);
	}

	protected boolean onMouseClickOnObject(Event event,
			TSGraphObjectRegion hitObject) {
		boolean rc = super.onMouseClickOnObject(event, hitObject);
		String id = "";
		if (null != hitObject) {
			id = hitObject.getId();
		} else {
			id = ProcessConstants.PROCESS_ID;
		}
		AbstractProcessEditor processEditor=(AbstractProcessEditor) WebStudio.get().getEditorPanel().getSelectedTab();
		processEditor.fetchProperties(id,ProcessConstants.GENERAL_PROPERTY);
		return rc;
	}
	
}