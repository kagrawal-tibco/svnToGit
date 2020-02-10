/**
 * 
 */
package com.tibco.cep.studio.cluster.topology.ui;

import java.awt.Color;

import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;

/**
 * @author hitesh
 *
 */
@SuppressWarnings("serial")
public class DeploymentUnitNodeUI extends ChildGraphNodeUI {

	private static Color GROUPNODE_START_COLOR = new Color(17, 100, 255);
	private static Color GROUPNODE_END_COLOR = new Color(204,222,255);
	private static Color GROUPNODE_MARK_COLOR = new Color(0, 0, 255);
	
	 public DeploymentUnitNodeUI() {
		 	this.setOuterRoundRect(true);
		 	this.setStartColor(GROUPNODE_START_COLOR);
		 	this.setEndColor(GROUPNODE_END_COLOR);
		 	this.setMarkColor(GROUPNODE_MARK_COLOR);
		 	this.setBorderDrawn(true);
		 	this.setDrawChildGraphMark(false);
    }
}
