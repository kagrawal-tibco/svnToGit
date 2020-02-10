/**
 * 
 */
package com.tibco.cep.webstudio.server.ui.custom;

import java.util.Arrays;
import java.util.List;

import com.tomsawyer.drawingtemplate.TSDrawingObjectPropertyHelper;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.builder.attribute.TSAttributeDependencyManager;
import com.tomsawyer.graphicaldrawing.builder.attribute.TSAttributeGroupManager;
import com.tomsawyer.graphicaldrawing.builder.attribute.TSTemplateAttribute;
import com.tomsawyer.graphicaldrawing.ui.TSAttributeEnumHelper;
import com.tomsawyer.graphicaldrawing.ui.predefined.node.basic.TSEllipseNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.shared.TSUIConstants;
import com.tomsawyer.util.shared.TSAttributedObject;


public abstract class BpmnBaseEndEventNodeBuilder extends TSEllipseNodeBuilder
{
	/* 
	 * @inheritDoc
	 */
	@Override
	protected void initDefaultProperties()
	{
		super.initDefaultProperties();
		//this.setName("");
	}


	/* 
	 * @inheritDoc
	 */
	protected void initDefaultAttributes(TSAttributedObject o)
	{
		this.initEnums();
		
		o.setAttribute(BpmnNodeConstants.BACKGROUND_COLOR, new TSEColor(255, 255, 255));
		o.setAttribute(BpmnNodeConstants.TRANSPARENT_COLOR, new TSEColor(255, 255, 255));
		
		o.setAttribute(BpmnNodeConstants.GRADIENT_COLOR_ENABLED, Boolean.valueOf(true));
		o.setAttribute(BpmnNodeConstants.GRADIENT_START_COLOR, new TSEColor(255, 153, 0));
		o.setAttribute(BpmnNodeConstants.GRADIENT_FINISH_COLOR, new TSEColor(255, 153, 0));
		o.setAttribute(BpmnNodeConstants.GRADIENT_DIRECTION, Integer.valueOf(TSUIConstants.TOP_TO_BOTTOM));
		
		o.setAttribute(BpmnNodeConstants.BORDER_COLOR, new TSEColor(255, 255,255));
		o.setAttribute(BpmnNodeConstants.BORDER_STYLE, Integer.valueOf(0));
		o.setAttribute(BpmnNodeConstants.BORDER_WIDTH, Integer.valueOf(1));
	}

	private void initEnums()
	{
		
		this.setEnumValuesForAttribute(
				BpmnNodeConstants.GRADIENT_DIRECTION,
				TSAttributeEnumHelper.getGradientDirectionEnums());

		this.setEnumRendererImagesForAttribute(
				BpmnNodeConstants.BORDER_STYLE,
				TSAttributeEnumHelper.getLineStyleImageMap());
	}
	
	@Override
	public void defineAttributeGroups(TSAttributeGroupManager groupManager)
	{
		groupManager.setGroupNames(
			Arrays.asList(
				GROUP_TEXT,
				GROUP_BACKGROUND_AND_BORDER,
				GROUP_SIZE_AND_POSITION));

		/*groupManager.setGroupAttributes(GROUP_TEXT,
			Arrays.asList(
				new TSTemplateAttribute(
					TSDrawingObjectPropertyHelper.PROPERTY_TOOLTIP,
					true),
				new TSTemplateAttribute(
					TSDrawingObjectPropertyHelper.PROPERTY_URL,
					true)));


		groupManager.setGroupAttributes(GROUP_BACKGROUND_AND_BORDER,
			Arrays.asList(
				new TSTemplateAttribute(
						BpmnNodeConstants.GRADIENT_COLOR_ENABLED),
				new TSTemplateAttribute(
						BpmnNodeConstants.BACKGROUND_COLOR),
				new TSTemplateAttribute(
						BpmnNodeConstants.GRADIENT_START_COLOR),
				new TSTemplateAttribute(
						BpmnNodeConstants.GRADIENT_FINISH_COLOR),
				new TSTemplateAttribute(
						BpmnNodeConstants.GRADIENT_DIRECTION),
				new TSTemplateAttribute(
						BpmnNodeConstants.BORDER_STYLE),
				new TSTemplateAttribute(
						BpmnNodeConstants.BORDER_COLOR),
				new TSTemplateAttribute(
						BpmnNodeConstants.BORDER_WIDTH)));*/
		
		groupManager.setGroupAttributes(GROUP_TEXT,
			getTextGroupAttributes());


		groupManager.setGroupAttributes(GROUP_BACKGROUND_AND_BORDER,
			getBackgroundAndBorderGroupAttributes());


		groupManager.setGroupAttributes(GROUP_SIZE_AND_POSITION,
			this.getSizeAndPositionGroupAttributes());

		groupManager.setShowUngroupedAttributes(false);
		groupManager.setShowUngroupedProperties(false);
	}	
	
	protected List<TSTemplateAttribute> getTextGroupAttributes()
	{
		return Arrays.asList(
			new TSTemplateAttribute(
				TSDrawingObjectPropertyHelper.PROPERTY_TOOLTIP,
				true),
			new TSTemplateAttribute(
				TSDrawingObjectPropertyHelper.PROPERTY_URL,
				true));
	}
	
	protected List<TSTemplateAttribute> getBackgroundAndBorderGroupAttributes()
	{
		return Arrays.asList(
				new TSTemplateAttribute(
						BpmnNodeConstants.GRADIENT_COLOR_ENABLED),
				new TSTemplateAttribute(
						BpmnNodeConstants.BACKGROUND_COLOR),
				new TSTemplateAttribute(
						BpmnNodeConstants.GRADIENT_START_COLOR),
				new TSTemplateAttribute(
						BpmnNodeConstants.GRADIENT_FINISH_COLOR),
				new TSTemplateAttribute(
						BpmnNodeConstants.GRADIENT_DIRECTION),
				new TSTemplateAttribute(
						BpmnNodeConstants.BORDER_STYLE),
				new TSTemplateAttribute(
						BpmnNodeConstants.BORDER_COLOR),
				new TSTemplateAttribute(
						BpmnNodeConstants.BORDER_WIDTH));
	}
	
	@Override
	public void defineAttributeDependencies(TSAttributeDependencyManager dependencyManager)
	{
		defineBackgroundAttributeDependencies(dependencyManager);
	}
	
	protected static void defineBackgroundAttributeDependencies(TSAttributeDependencyManager dependencyManager)
	{
		// gradient related

		TSTemplateAttribute gradientEnabledAttribute =
			new TSTemplateAttribute(
				BpmnNodeConstants.GRADIENT_COLOR_ENABLED);

		dependencyManager.setAttributeDependency(
			new TSTemplateAttribute(
				BpmnNodeConstants.BACKGROUND_COLOR),
			gradientEnabledAttribute,
			Boolean.FALSE);

		dependencyManager.setAttributeDependency(
			new TSTemplateAttribute(
				BpmnNodeConstants.GRADIENT_START_COLOR),
			gradientEnabledAttribute,
			Boolean.TRUE);

		dependencyManager.setAttributeDependency(
			new TSTemplateAttribute(
				BpmnNodeConstants.GRADIENT_FINISH_COLOR),
			gradientEnabledAttribute,
			Boolean.TRUE);

		dependencyManager.setAttributeDependency(
			new TSTemplateAttribute(
				BpmnNodeConstants.GRADIENT_DIRECTION),
			gradientEnabledAttribute,
			Boolean.TRUE);

	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
	
	//private String type;
}
