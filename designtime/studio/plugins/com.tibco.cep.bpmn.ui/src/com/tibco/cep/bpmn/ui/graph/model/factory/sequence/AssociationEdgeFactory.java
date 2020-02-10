package com.tibco.cep.bpmn.ui.graph.model.factory.sequence;

import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverviewAndDiagram;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractEdgeUIFactory;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;

public class AssociationEdgeFactory extends AbstractEdgeUIFactory {
	
	private EEnumLiteral arrowStyle = BpmnModelClass.ENUM_ASSOCIATION_DIRECTION_NONE;

	private static final long serialVersionUID = 2052351012021525236L;

	public AssociationEdgeFactory(String name, EClass type, BpmnLayoutManager layoutManager, EEnumLiteral arrowStyle) {
		super(name,type, layoutManager);
		this.arrowStyle = arrowStyle;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.ui.graph.model.AbstractEdgeUIFactory#decorateEdge(com.tomsawyer.graphicaldrawing.TSEEdge)
	 */
	protected void decorateEdge(TSEEdge edge) {
		TSECurvedEdgeUI edgeUI = (TSECurvedEdgeUI) edge.getEdgeUI();
		/*
		 * @see com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI
		 *  change following Line Style
		 */
//		TSECurvedEdgeUI.LINE_STYLE_DASH_DOT_DOT
//		TSECurvedEdgeUI.LINE_STYLE_DASH_DOT
//		TSECurvedEdgeUI.LINE_STYLE_LONG_DASH
//		TSECurvedEdgeUI.LINE_STYLE_SHORT_DASH
//		TSECurvedEdgeUI.LINE_STYLE_SOLID
		
		edgeUI.setLineStyle(TSECurvedEdgeUI.LINE_STYLE_DOT);
		if (arrowStyle == BpmnModelClass.ENUM_ASSOCIATION_DIRECTION_NONE) {
			edgeUI.setArrowType(TSECurvedEdgeUI.NO_ARROW);
		} else if (arrowStyle == BpmnModelClass.ENUM_ASSOCIATION_DIRECTION_ONE) {
			edgeUI.setArrowType(TSECurvedEdgeUI.TARGET_ARROW);
		} else if (arrowStyle == BpmnModelClass.ENUM_ASSOCIATION_DIRECTION_BOTH) {
			edgeUI.setArrowType(TSECurvedEdgeUI.SOURCE_AND_TARGET_ARROW);
		}
		super.decorateEdge(edge);	
	}
	
	/**
	 * Call this method to refresh EdgeUI
	 */
	public void refreshEdgeUI(TSEEdge edge, EEnumLiteral arrowStyle) {
		this.arrowStyle = arrowStyle;
		decorateEdge(edge);
		refreshOverviewAndDiagram(getLayoutManager().getDiagramManager().getDiagramEditorSite(), true, true,
				getLayoutManager().getDiagramManager(), true);

	}
}
