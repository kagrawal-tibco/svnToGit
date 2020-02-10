package com.tibco.cep.decision.tree.ui.nodes;

import java.io.IOException;

import com.tomsawyer.graphicaldefinition.TSGraphicalDefinition;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.composite.TSUIHelper;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSMouseDoubleClickedActionUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSShapeUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSTextUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;
import com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool;

/*
@author ssailapp
@date Oct 21, 2011
 */

@SuppressWarnings("serial")
public abstract class AbstractNodeUI extends TSCompositeNodeUI {
	
	public AbstractNodeUI() {
		super();

		TSUIStyle style = this.getStyle();
		TSShapeUIElement background = new TSShapeUIElement(
			"Background",
			new TSUIElementPoint(-0.5, 0, 0.5, 0),
			new TSUIElementPoint(0.5, 0, -0.5, 0));
		style.setProperty(background,
			TSUIStyleConstants.LINE_COLOR,
			null  // TODO - TSV 9.2							
			/*
			String.format("$%s(<%s>,<%s>,%s,%s,%s)",
				TSDecisionEvaluator.ACTION_BORDER_COLOR_FUNCTION,
				TSDecisionConstants.START_NODE_ATTRIBUTE,
				TSDecisionConstants.END_NODE_ATTRIBUTE,	
				TSDecisionUIConstants.START_NODE_BORDER_COLOR,
				TSDecisionUIConstants.END_NODE_BORDER_COLOR,
				TSDecisionUIConstants.ACTION_NODE_BORDER_COLOR)
				*/
				);
		style.setProperty(background, TSUIStyleConstants.GRADIENT_COLOR1, getGradientStartColor());
		style.setProperty(background, TSUIStyleConstants.GRADIENT_COLOR2, getGradientEndColor());
		style.setProperty(background, TSUIStyleConstants.GRADIENT_DIRECTION, TSUIStyleConstants.VERTICAL_JUSTIFICATION);
		style.setProperty(background, TSUIStyleConstants.LINE_WIDTH, 1);
		style.setProperty(background, TSUIStyleConstants.FILL_GRADIENT_ENABLED, true);

		TSTextUIElement text = this.getTextUIElement();

		TSMouseDoubleClickedActionUIElement editTextAction =
			new TSMouseDoubleClickedActionUIElement("EditTextAction",
				TSESelectTool.EDIT_TEXT_ACTION);
		editTextAction.setChild(text);

		TSGroupUIElement root = new TSGroupUIElement("ActionRoot");
		root.addElement(background);
		root.addElement(editTextAction);

		try {
			root.addElement(TSUIHelper.loadUIElementTree(
					TSGraphicalDefinition.class.getResource(
							"resources/partial/selection.xml"),
					style,
					null,
					TSGraphicalDefinition.class));
			root.addElement(TSUIHelper.loadUIElementTree(
				TSGraphicalDefinition.class.getResource(
					"resources/partial/highlighting.xml"),
				style,
				null,
				TSGraphicalDefinition.class));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		/* // TODO - TSV 9.2
		root.addElement(TSFilteringUISubtreeBuilder.buildSubTree(style));
		*/

		this.setRootElement(root);
	}

	protected TSTextUIElement getTextUIElement() {
		return new TSTextUIElement("ActionText",
			"$name()",
			new TSUIElementPoint(-0.5, 0, 0.5, 0),
			new TSUIElementPoint(0.5, 0, -0.5, 0));
	}

	protected abstract TSEColor getGradientStartColor();

	protected abstract TSEColor getGradientEndColor();
	
	protected TSEColor getBackgroundColor() {
		return TSEColor.white;
	}

}
