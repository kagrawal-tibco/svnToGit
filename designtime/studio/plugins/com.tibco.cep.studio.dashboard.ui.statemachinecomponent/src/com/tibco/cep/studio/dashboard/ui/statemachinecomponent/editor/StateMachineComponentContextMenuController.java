package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.AllSettingsCopyFromSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.AllSettingsDeleteFromAll;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.AllSettingsDeleteFromSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.AllSettingsDeleteFromSelections;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.AllSettingsPasteToSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.AllSettingsPasteToSelections;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.ContentSettingsCopyFromSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.ContentSettingsCreateInSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.ContentSettingsDeleteFromAll;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.ContentSettingsDeleteFromSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.ContentSettingsDeleteFromSelections;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.ContentSettingsPasteToSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.ContentSettingsPasteToSelections;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.IndicatorSettingsCopyFromSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.IndicatorSettingsCreateInSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.IndicatorSettingsDeleteFromAll;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.IndicatorSettingsDeleteFromSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.IndicatorSettingsDeleteFromSelections;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.IndicatorSettingsPasteToSelected;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.IndicatorSettingsPasteToSelections;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions.LocalElementAbstractAction;

public class StateMachineComponentContextMenuController extends ContextMenuController {

	private Map<String, JPopupMenu> menus;

	public StateMachineComponentContextMenuController() {
		menus = new HashMap<String, JPopupMenu>();
//		//graph level menus
//		JPopupMenu graphLvlPopupMenu = new JPopupMenu();
//		//delete content,indicator,settings
//		graphLvlPopupMenu.add(new IndicatorSettingsDeleteFromSelections());
//		graphLvlPopupMenu.add(new ContentSettingsDeleteFromSelections());
//		graphLvlPopupMenu.add(new AllSettingsDeleteFromSelections());
//		graphLvlPopupMenu.addSeparator();
//
//		graphLvlPopupMenu.add(new IndicatorSettingsDeleteFromAll());
//		graphLvlPopupMenu.add(new ContentSettingsDeleteFromAll());
//		graphLvlPopupMenu.add(new AllSettingsDeleteFromAll());
//		graphLvlPopupMenu.addSeparator();
//
//		graphLvlPopupMenu.add(new IndicatorSettingsPasteToSelections());
//		graphLvlPopupMenu.add(new ContentSettingsPasteToSelections());
//		graphLvlPopupMenu.add(new AllSettingsPasteToSelections());
//
//		menus.put(EntityResourceConstants.GRAPH_POPUP, graphLvlPopupMenu);
//
//		//node level menus
//		JPopupMenu nodeLvlPopupMenu = new JPopupMenu();
//		nodeLvlPopupMenu.add(new IndicatorSettingsCreateInSelected());
//		nodeLvlPopupMenu.add(new ContentSettingsCreateInSelected());
//		nodeLvlPopupMenu.addSeparator();
//
//		nodeLvlPopupMenu.add(new IndicatorSettingsCopyFromSelected());
//		nodeLvlPopupMenu.add(new ContentSettingsCopyFromSelected());
//		nodeLvlPopupMenu.add(new AllSettingsCopyFromSelected());
//		nodeLvlPopupMenu.addSeparator();
//
//		nodeLvlPopupMenu.add(new IndicatorSettingsPasteToSelected());
//		nodeLvlPopupMenu.add(new ContentSettingsPasteToSelected());
//		nodeLvlPopupMenu.add(new AllSettingsPasteToSelected());
//		nodeLvlPopupMenu.addSeparator();
//
//		nodeLvlPopupMenu.add(new IndicatorSettingsDeleteFromSelected());
//		nodeLvlPopupMenu.add(new ContentSettingsDeleteFromSelected());
//		nodeLvlPopupMenu.add(new AllSettingsDeleteFromSelected());
//
//		menus.put(EntityResourceConstants.NODE_POPUP, nodeLvlPopupMenu);

		//graph level menus
		JPopupMenu graphLvlPopupMenu = new JPopupMenu();

		JMenu graphLvlDeleteMenus = new JMenu("Delete");
		graphLvlDeleteMenus.add(new IndicatorSettingsDeleteFromSelections());
		graphLvlDeleteMenus.add(new ContentSettingsDeleteFromSelections());
		graphLvlDeleteMenus.add(new AllSettingsDeleteFromSelections());
		graphLvlPopupMenu.add(graphLvlDeleteMenus);

		graphLvlPopupMenu.addSeparator();

		JMenu graphLvldeleteAllMenus = new JMenu("Delete All");
		graphLvldeleteAllMenus.add(new IndicatorSettingsDeleteFromAll());
		graphLvldeleteAllMenus.add(new ContentSettingsDeleteFromAll());
		graphLvldeleteAllMenus.add(new AllSettingsDeleteFromAll());
		graphLvlPopupMenu.add(graphLvldeleteAllMenus);

		graphLvlPopupMenu.addSeparator();

		JMenu graphLvlPasteMenus = new JMenu("Paste");
		graphLvlPasteMenus.add(new IndicatorSettingsPasteToSelections());
		graphLvlPasteMenus.add(new ContentSettingsPasteToSelections());
		graphLvlPasteMenus.add(new AllSettingsPasteToSelections());
		graphLvlPopupMenu.add(graphLvlPasteMenus);

		menus.put(EntityResourceConstants.GRAPH_POPUP, graphLvlPopupMenu);

		//node level menus
		JPopupMenu nodeLvlPopupMenu = new JPopupMenu();
		JMenu nodeLvlCreateMenus = new JMenu("Create");
		nodeLvlCreateMenus.add(new IndicatorSettingsCreateInSelected());
		nodeLvlCreateMenus.add(new ContentSettingsCreateInSelected());
		nodeLvlPopupMenu.add(nodeLvlCreateMenus);

		nodeLvlPopupMenu.addSeparator();

		JMenu copyMenus = new JMenu("Copy");
		copyMenus.add(new IndicatorSettingsCopyFromSelected());
		copyMenus.add(new ContentSettingsCopyFromSelected());
		copyMenus.add(new AllSettingsCopyFromSelected());
		nodeLvlPopupMenu.add(copyMenus);

		nodeLvlPopupMenu.addSeparator();

		JMenu nodeLvlPasteMenus = new JMenu("Paste");
		nodeLvlPasteMenus.add(new IndicatorSettingsPasteToSelected());
		nodeLvlPasteMenus.add(new ContentSettingsPasteToSelected());
		nodeLvlPasteMenus.add(new AllSettingsPasteToSelected());
		nodeLvlPopupMenu.add(nodeLvlPasteMenus);

		nodeLvlPopupMenu.addSeparator();

		JMenu nodeLvlDeleteMenus = new JMenu("Delete");
		nodeLvlDeleteMenus.add(new IndicatorSettingsDeleteFromSelected());
		nodeLvlDeleteMenus.add(new ContentSettingsDeleteFromSelected());
		nodeLvlDeleteMenus.add(new AllSettingsDeleteFromSelected());
		nodeLvlPopupMenu.add(nodeLvlDeleteMenus);

		menus.put(EntityResourceConstants.NODE_POPUP, nodeLvlPopupMenu);
	}


	@Override
	public void setPopupMenuListening() {
		//do nothing
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//do nothing
	}

	public void showMenu(Container container, Point point, String key, LocalElement targetElement) {
		JPopupMenu popupMenu = menus.get(key);
		if (popupMenu != null){
			Component[] children = popupMenu.getComponents();
			updatePopupChildren(children, targetElement);
			popupMenu.show(container, point.x, point.y);
		}
	}

	private void updatePopupChildren(Component[] popupChildren, LocalElement targetElement) {
		for (Component component : popupChildren) {
			if (component instanceof JMenu){
				boolean enable = false;
				Component[] children = ((JMenu) component).getMenuComponents();
				if (children != null && children.length != 0) {
					updatePopupChildren(children, targetElement);
					for (Component child : children) {
						if (child.isEnabled() == true){
							enable = true;
							break;
						}
					}
				}
			}
			else if (component instanceof AbstractButton) {
				AbstractButton abstractButton = (AbstractButton) component;
				Action action = abstractButton.getAction();
				if (action instanceof LocalElementAbstractAction) {
					//((LocalElementAbstractAction) action).setTargetElement(targetElement);
					component.setEnabled(action.isEnabled());
				}

			}
		}
	}
}
