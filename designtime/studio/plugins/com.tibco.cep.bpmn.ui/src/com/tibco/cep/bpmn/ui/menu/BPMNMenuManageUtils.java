package com.tibco.cep.bpmn.ui.menu;

import java.awt.event.KeyEvent;
import java.io.IOException;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.diagramming.menu.Menu;
import com.tibco.cep.diagramming.menu.MenuFactory;
import com.tibco.cep.diagramming.menu.MenuItem;
import com.tibco.cep.diagramming.menu.Pattern;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class BPMNMenuManageUtils {
	
	public static void generateMenu() {
		Menu menu = MenuFactory.eINSTANCE.createMenu();
		menu.setName(BpmnMessages.getString("bpmnMenuManager_menu_Label"));
		menu.setId("com.tibco.cep.bpmn.menu");
		menu.setCommand("com.tibco.cep.bpmn.menu");
		menu.setIsSubMenu(false);
		menu.setVisible(true);
		
		Pattern pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		menu.getPattern().add(pattern);
		
		//Cut Menu Item
		MenuItem item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_cut_Label"));
		item.setId("com.tibco.cep.bpmn.edit.cut");
		item.setIcon("/icons/cut_edit.png");
		item.setCommand("com.tibco.cep.bpmn.edit.cut");
		item.setKeycode(KeyEvent.VK_X);
		item.setMnemonic("t");
		item.setModifiers(KeyEvent.CTRL_MASK);
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);

		menu.getItems().add(item);

		//Copy Menu Item
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_copy_Label"));
		item.setId("com.tibco.cep.bpmn.edit.copy");
		item.setIcon("/icons/copy_edit.png");
		item.setCommand("com.tibco.cep.bpmn.edit.copy");
		item.setKeycode(KeyEvent.VK_C);
		item.setMnemonic("C");
		item.setModifiers(KeyEvent.CTRL_MASK);
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);

		menu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setSeparator(true);
		menu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_delete_Label"));
		item.setId("com.tibco.cep.bpmn.edit.delete");
		item.setIcon("/icons/delete.png");
		item.setCommand("com.tibco.cep.bpmn.edit.delete");
		item.setKeycode(KeyEvent.VK_DELETE);
		item.setMnemonic("D");
		item.setModifiers(0);
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);

		menu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setSeparator(true);
		menu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_paste_Label"));
		item.setId("com.tibco.cep.bpmn.edit.paste");
		item.setIcon("/icons/paste_edit.png");
		item.setCommand("com.tibco.cep.bpmn.edit.paste");
		item.setKeycode(KeyEvent.VK_V);
		item.setMnemonic("P");
		item.setModifiers(KeyEvent.CTRL_MASK);
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);
		
		menu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setSeparator(true);
		menu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_exportToImage_label"));
		item.setId("com.tibco.cep.bpmn.graph.exportImage");
		item.setCommand("com.tibco.cep.bpmn.graph.exportImage");
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);
		
		menu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_printSetup_label"));
		item.setId("com.tibco.cep.bpmn.graph.printSetup");
		item.setCommand("com.tibco.cep.bpmn.graph.printSetup");
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);
		
		menu.getItems().add(item);

		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_printPreview_label"));
		item.setId("com.tibco.cep.bpmn.graph.printPreview");
		item.setCommand("com.tibco.cep.bpmn.graph.printPreview");
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);
		
		menu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setSeparator(true);
		menu.getItems().add(item);
		
//		item = MenuFactory.eINSTANCE.createMenuItem();
//		item.setName("Add Boundary Event");
//		item.setId("com.tibco.cep.bpmn.graph.addBoundaryEvent");
//		item.setCommand("com.tibco.cep.bpmn.graph.addBoundaryEvent");
//		item.setVisible(true);
//		
//		pattern = MenuFactory.eINSTANCE.createPattern();
//		pattern.setPattern(BpmnMetaModelConstants.ACTIVITY.getExpandedForm());
//		item.getPattern().add(pattern);
//		
//		menu.getItems().add(item);

		Menu subMenu = MenuFactory.eINSTANCE.createMenu();
		subMenu.setName(BpmnMessages.getString("bpmnMenuManager_subMenu_label"));
		subMenu.setId("com.tibco.cep.bpmn.graph.addBoundaryEvent");
		subMenu.setCommand("com.tibco.cep.bpmn.graph.addBoundaryEvent");
		subMenu.setIsSubMenu(true);
		subMenu.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern(BpmnMetaModelConstants.ACTIVITY.getExpandedForm());
		subMenu.getPattern().add(pattern);
		
		menu.getItems().add(subMenu);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_item_catchMessage_label"));
		item.setId("event.catch.message.intermediate");
		item.setCommand("event.catch.message.intermediate");
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);
		
		subMenu.getItems().add(item);
		
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_item_catchError_label"));
		item.setId("event.catch.error.intermediate");
		item.setCommand("event.catch.error.intermediate");
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);
		
		subMenu.getItems().add(item);
		
		item = MenuFactory.eINSTANCE.createMenuItem();
		item.setName(BpmnMessages.getString("bpmnMenuManager_item_catchSignal_label"));
		item.setId("event.catch.signal.intermediate");
		item.setCommand("event.catch.signal.intermediate");
		item.setVisible(true);
		
		pattern = MenuFactory.eINSTANCE.createPattern();
		pattern.setPattern("*");
		item.getPattern().add(pattern);
		
		subMenu.getItems().add(item);

//		Menu subMenu = MenuFactory.eINSTANCE.createMenu();
//		subMenu.setName("Add Break Point");
//		subMenu.setId("com.tibco.cep.bpmn.breakpoint");
//		subMenu.setCommand("com.tibco.cep.bpmn.breakpoint");
//		subMenu.setIsSubMenu(true);
//		subMenu.setVisible(true);
//		
//		pattern = MenuFactory.eINSTANCE.createPattern();
//		pattern.setPattern(BpmnMetaModelConstants.FLOW_ELEMENT.getExpandedForm());
//		subMenu.getPattern().add(pattern);
//		
//		menu.getItems().add(subMenu);
//		
//		item = MenuFactory.eINSTANCE.createMenuItem();
//		item.setName("Input");
//		item.setId("com.tibco.cep.bpmn.breakpoint.input");
//		item.setIcon("/icons/openCond.gif");
//		item.setCommand("com.tibco.cep.bpmn.breakpoint.input");
//		item.setChecked(false);
//		item.setVisible(true);
//		
//		pattern = MenuFactory.eINSTANCE.createPattern();
//		pattern.setPattern(BpmnMetaModelConstants.FLOW_ELEMENT.getExpandedForm());
//		item.getPattern().add(pattern);
//		
//		subMenu.getItems().add(item);
//		
//		item = MenuFactory.eINSTANCE.createMenuItem();
//		item.setName("Ouput");
//		item.setId("com.tibco.cep.bpmn.breakpoint.output");
//		item.setIcon("/icons/success.gif");
//		item.setCommand("com.tibco.cep.bpmn.breakpoint.output");
//		item.setChecked(false);
//		item.setVisible(true);
//		
//		pattern = MenuFactory.eINSTANCE.createPattern();
//		pattern.setPattern(BpmnMetaModelConstants.FLOW_ELEMENT.getExpandedForm());
//		item.getPattern().add(pattern);
//		
//		subMenu.getItems().add(item);
		
		//For generating sample default beprocessmenu
		try {
			StudioResourceUtils.persistEntity(menu, "default", "/", "beprocessmenu", "D:/SVN/be", "schema", null);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}

