package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.AbstractActivityNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public abstract class AbstractTaskNodeUIFactory extends AbstractActivityNodeUIFactory {

	public AbstractTaskNodeUIFactory(String name,String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager, EClass nodeType) {
		super(name, referredBEResource, toolId, layoutManager, nodeType);
		
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		TaskNodeUI ui = new TaskNodeUI();
		
		//TODO: remove below commented code
//		BpmnUIPlugin plugInstance = BpmnUIPlugin.getDefault();
//		if(plugInstance != null){
//			IPreferenceStore store = BpmnUIPlugin.getDefault().getPreferenceStore();
//			ui.setDrawTag(!store.getBoolean(BpmnPreferenceConstants.PREF_DISPLAY_FULL_NAMES));
//		}
		
		ui.setDrawTag(!getLayoutManager().getDiagramManager().isDisplayFullName());
		ui.setFillTaskIcons(getLayoutManager().getDiagramManager().isfillTaskIcons());
		
		return ui;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 551667850488114002L;

	@Override
	public void decorateNode(TSENode node) {
		// TODO Auto-generated method stub
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		if(getNodeUI() instanceof TaskNodeUI) {
		//	((TaskNodeUI)getNodeUI()).setBadgeImage(getTaskImage());
			// Temp hack to show glowing nodes (just for rule nodes)
			/*
			if (getTaskImage() == TaskNodeUI.RULE_IMAGE) {
				((TaskNodeUI)getNodeUI()).setDrawGlow(true);
				((TaskNodeUI)getNodeUI()).setAnimated(true);
			}
			*/
		}
		node.setSize(70, 56);
		node.setName(getNodeName());
		node.setUI(getNodeUI());
	}

	protected abstract TSEImage getTaskImage();

	@Override
	public void layoutNode(TSENode node) {

	}

}
