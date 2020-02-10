package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.TSAttributeEnumHelper;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.graphicaldrawing.ui.TSPNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.shared.TSUIConstants;
import com.tomsawyer.util.shared.TSAttributedObject;


public class BpmnCallActivityNodeBuilder extends TSPNodeBuilder
{
	/* 
	 * @inheritDoc
	 */
	@Override
	protected void initDefaultAttributes(TSAttributedObject o)
	{

		this.setSize(20, 20);
		this.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		this.initEnums();

		o.setAttribute(BpmnNodeConstants.BACKGROUND_COLOR, new TSEColor(255, 204,102));
		o.setAttribute(BpmnNodeConstants.TRANSPARENT_COLOR, new TSEColor(255, 204,102));

		o.setAttribute(BpmnNodeConstants.GRADIENT_COLOR_ENABLED,
				Boolean.valueOf(true));
		o.setAttribute(BpmnNodeConstants.GRADIENT_START_COLOR, new TSEColor(255, 204,102));
		o.setAttribute(BpmnNodeConstants.GRADIENT_FINISH_COLOR, new TSEColor(255, 204,102));
		o.setAttribute(BpmnNodeConstants.GRADIENT_DIRECTION,
				Integer.valueOf(TSUIConstants.TOP_TO_BOTTOM));

		o.setAttribute(BpmnNodeConstants.BORDER_COLOR,
				new TSEColor(255, 153, 0));
		o.setAttribute(BpmnNodeConstants.BORDER_STYLE, Integer.valueOf(0));
		o.setAttribute(BpmnNodeConstants.BORDER_WIDTH, Integer.valueOf(1));
	}
	private void initEnums() {

		this.setEnumValuesForAttribute(BpmnNodeConstants.GRADIENT_DIRECTION,
				TSAttributeEnumHelper.getGradientDirectionEnums());

		this.setEnumRendererImagesForAttribute(BpmnNodeConstants.BORDER_STYLE,
				TSAttributeEnumHelper.getLineStyleImageMap());
	}

	/* 
	 * @inheritDoc
	 */
	@Override
	protected TSNodeUI createNodeUI()
	{
		return new BpmnCallActivityNodeUI();
	}
	
	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
}
