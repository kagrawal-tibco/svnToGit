//
//	TomahawkBundle.java
//

package com.tibco.cep.diagramming.tool.popup;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.tomsawyer.service.client.TSServiceProxy;
import com.tomsawyer.service.layout.TSLayoutConstants;


/**
 * This class holds locale-specific objects. These include menus,
 * toolbars, application title, icon, node and edge UIs, etc. Each
 * resource is defined by a pair: name and value. By changing values
 * one can create locale-specific versions of application resources.
 * The name of the resource bundle should reflect the locale for which
 * it is designed.
 */
public class SelectToolBundle extends EntityResourceBundle
{
	/**
	 * This method gives access to the resource table defined
	 * in this resource bundle.
	 */
	public Object[][] getContents()
	{
		return (contents);
	}

	/**
	 * This is the menu shortcut key mask for this platform
	 */
	private static int MENU_SHORTCUT_KEY_MASK =
		java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

// ---------------------------------------------------------------------
// Section: Resource table
// ---------------------------------------------------------------------
//
	static final Object[][] contents =
	{

// ---------------------------------------------------------------------
// Section: Popup menus
//
// Each popup menu starts with "popup" followed by a dot and the name of
// the popup menu. Next it defines a number of items. On top of it the
// popup menu has to define the command on which it is poped up.
// Currently the application recognizes three popup showing commands:
// EDGE_POPUP, NODE_POPUP, and LABEL_POPUP.
// ---------------------------------------------------------------------

	{"popup.node", "Node Menu"},
//		{"submenu", "popup.shared.navigate"},
//			{"item", "popup.shared.navigate.goToChildGraph"},
//			{"item", ""},
//			{"item", "popup.shared.navigate.expand"},
//			{"item", "popup.shared.navigate.collapse"},
//			{"item", ""},
//			{"item", "popup.shared.navigate.createChildGraph"},
//			{"item", "popup.shared.navigate.deleteChildGraph"},
//		{"submenu", ""},
//		{"item", ""},
//		{"submenu", "popup.node.fold"},
//			{"item", "popup.node.fold.foldSelected"},
//			{"item", ""},
//			{"submenu", "popup.node.fold.foldChildren"},
//				{"item", "popup.node.fold.foldChildren.oneLevel"},
//				{"item", "popup.node.fold.foldChildren.nLevels"},
//				{"item", "popup.node.fold.foldChildren.allLevels"},
//			{"submenu", ""},
//			{"submenu", "popup.node.fold.foldParents"},
//				{"item", "popup.node.fold.foldParents.oneLevel"},
//				{"item", "popup.node.fold.foldParents.nLevels"},
//				{"item", "popup.node.fold.foldParents.allLevels"},
//			{"submenu", ""},
//			{"submenu", "popup.node.fold.foldNeighbors"},
//				{"item", "popup.node.fold.foldNeighbors.oneLevel"},
//				{"item", "popup.node.fold.foldNeighbors.nLevels"},
//				{"item", "popup.node.fold.foldNeighbors.allLevels"},
//			{"submenu", ""},
//			{"item", ""},
//			{"item", "popup.node.fold.unfoldSelected"},
//			{"item", ""},
//			{"item", "popup.node.fold.unfold"},
//		{"submenu", ""},
//		{"item", ""},
//		{"submenu", "popup.shared.edit"},
//			{"item", "popup.shared.edit.cut"},
//			{"item", "popup.shared.edit.copy"},
//			{"item", "popup.shared.edit.paste"},
//			{"item", "popup.shared.edit.duplicate"},
//			{"item", ""},
//			{"item", "popup.shared.edit.delete"},
//		{"submenu", ""},
//		{"item", ""},
//		{"submenu", "popup.node.createLabel"},
//			{"item", "popup.node.createLabel.text"},
//			{"item", "popup.node.createLabel.button"},
//			{"item", "popup.node.createLabel.checkBox"},
//		{"submenu", ""},
		//////////////////// STARTS WORKING FROM HERE ///////////////////
			{"item", "popup.node.createLabel.text"},
		{"item", ""},
			{"item", "popup.shared.edit.cut"},
			{"item", "popup.shared.edit.copy"},
			{"item", "popup.shared.edit.paste"},
			{"item", "popup.shared.edit.duplicate"},
		{"item", ""},			
			{"item", "popup.shared.edit.delete"},
		{"item", ""},
			{"item", "popup.node.fold.foldChildren.oneLevel"},
			{"item", "popup.node.fold.foldChildren.nLevels"},
			{"item", "popup.node.fold.foldChildren.allLevels"},
		{"item", ""},
			{"item", "popup.node.fold.foldParents.oneLevel"},
			{"item", "popup.node.fold.foldParents.nLevels"},
			{"item", "popup.node.fold.foldParents.allLevels"},
//		{"item", ""},
//			{"item", "popup.node.fold.foldNeighbors.oneLevel"},
//			{"item", "popup.node.fold.foldNeighbors.nLevels"},
//			{"item", "popup.node.fold.foldNeighbors.allLevels"},
		{"item", ""},
		{"item", "popup.node.fold.unfoldSelected"},
		{"item", ""},
		{"item", "popup.node.fold.unfold"},
		{"item", ""},
		{"item", "popup.shared.objectProperties"},
	{"popup.node", ""},
	//////////////////// STARTS WORKING FROM HERE ///////////////////

	{"popup.node.state.machine", "StateMachine Node Menu"},
		{"item", "popup.shared.edit.cut"},
		{"item", "popup.shared.edit.copy"},
		{"item", ""},
		{"item", "popup.shared.edit.delete"},
	{"item", ""},
	{"popup.node.state.machine", ""},

// BPMN Related:
	
	{"popup.node.bpmn.task", "BPMN Node Menu"},
		{"item", "popup.shared.edit.cut"},
		{"item", "popup.shared.edit.copy"},
		{"item", ""},
		{"item", "popup.shared.edit.delete"},
		{"item", ""},
		{"submenu", "popup.bpmn.submenu.breakpoint"},
		{"item", ""},
		{"item", "popup.bpmn.node.addinputbreakpoint"}, 
		{"item", "popup.bpmn.node.addoutputbreakpoint"},
		{"submenu", ""},
		{"submenu", "popup.bpmn.submenu.breakpoint.remove"},
		{"item", ""},
		{"item", "popup.bpmn.node.removeinputbreakpoint"}, 
		{"item", "popup.bpmn.node.removeoutputbreakpoint"},
		{"submenu", ""},
		{"item", ""},
		{"item", "popup.bpmn.node.removeallbreakpoint"},
	{"item", ""},
	{"popup.node.state.machine", ""},
	//add
	{"popup.bpmn.submenu.breakpoint.text", "Hit Breakpoint"},
	{"popup.bpmn.submenu.breakpoint.checked", "false"},

	{"popup.bpmn.node.addinputbreakpoint.text", "Input"},
	{"popup.bpmn.node.addinputbreakpoint.checked", "false"},
	{"popup.bpmn.node.addinputbreakpoint.mnemonic", "t"},
	{"popup.bpmn.node.addinputbreakpoint.icon", "/icons/openCond.gif"},
	{"popup.bpmn.node.addinputbreakpoint.command", EntityResourceConstants.HIT_IN_BREAKPOINT},
	
	{"popup.bpmn.node.addoutputbreakpoint.text", "Output"},
	{"popup.bpmn.node.addoutputbreakpoint.checked", "false"},
	{"popup.bpmn.node.addoutputbreakpoint.mnemonic", "t"},
	{"popup.bpmn.node.addoutputbreakpoint.icon", "/icons/success.gif"},
	{"popup.bpmn.node.addoutputbreakpoint.command", EntityResourceConstants.HIT_OUT_BREAKPOINT},
	
	//remove
	{"popup.bpmn.submenu.breakpoint.remove.text", "Remove Breakpoint"},
	{"popup.bpmn.submenu.breakpoint.remove.checked", "false"},

	{"popup.bpmn.node.removeinputbreakpoint.text", "Input"},
	{"popup.bpmn.node.removeinputbreakpoint.checked", "false"},
	{"popup.bpmn.node.removeinputbreakpoint.mnemonic", "t"},
	{"popup.bpmn.node.removeinputbreakpoint.icon", "/icons/openCond.gif"},
	{"popup.bpmn.node.removeinputbreakpoint.command", EntityResourceConstants.REMOVE_IN_BREAKPOINT},
	
	{"popup.bpmn.node.removeoutputbreakpoint.text", "Output"},
	{"popup.bpmn.node.removeoutputbreakpoint.checked", "false"},
	{"popup.bpmn.node.removeoutputbreakpoint.mnemonic", "t"},
	{"popup.bpmn.node.removeoutputbreakpoint.icon", "/icons/success.gif"},
	{"popup.bpmn.node.removeoutputbreakpoint.command", EntityResourceConstants.REMOVE_OUT_BREAKPOINT},
	
	{"popup.bpmn.node.removeallbreakpoint.text", "Remove All Breakpoints"},
	{"popup.bpmn.node.removeallbreakpoint.command", EntityResourceConstants.REMOVE_ALL_BREAKPOINTS},
	
	
	{"popup.bpmn.node.breakpoint", "BPMN Breakpoint Menu"},
	{"item", "popup.bpmn.node.breakpoint.properties"},
	
	{"popup.bpmn.node.breakpoint.properties.text", "Process BreakPoint Properties..."},
	{"popup.bpmn.node.breakpoint.properties.command", EntityResourceConstants.PROCESS_BREAKPOINT_PROPERTIES},
	
	{"popup.graph.bpmn.task", "Bpmn Graph Menu"},
	{"item", "popup.shared.edit.paste"},
	{"item", ""},
	{"item", "popup.graph.fold.exportImage"},	
	{"item", "popup.graph.fold.printSetup"},	
	{"item", "popup.graph.fold.printPreview"},	
// END BPMN Related	
	
	{"popup.node.concept.view", "Concept Node Menu"},
	{"item", "popup.shared.edit.edit"},
	{"item", "popup.shared.edit.delete"},
	{"item", ""},
//	{"item", "popup.shared.edit.cut"},
//	{"item", "popup.shared.edit.copy"},
//	{"item", ""},
//	{"item", "popup.shared.edit.delete"},
//	{"item", ""},
	{"popup.node.concept.view", ""},
	
	{"popup.node.event.view", "Event Node Menu"},
	{"item", "popup.shared.edit.edit"},
	{"item", "popup.shared.edit.delete"},
	{"item", ""},
//	{"item", "popup.shared.edit.cut"},
//	{"item", "popup.shared.edit.copy"},
//	{"item", ""},
//	{"item", "popup.shared.edit.delete"},
//	{"item", ""},
	{"popup.node.event.view", ""},

	{"popup.node.dependency.view", "Dependency Node Menu"},
	{"item", "popup.shared.edit.edit"},
	{"item", ""},
	{"popup.node.dependency.view", ""},
	
	{"popup.node.cluster.view", "ClusterView Node Menu"},
	{"item", "popup.shared.edit.cut"},
	{"item", "popup.shared.edit.copy"},
	{"item", "popup.shared.edit.delete"},
	{"item", ""},
	{"popup.node.cluster.view", ""},
	
	{"popup.node.sequence.view", "Sequence Node Menu"},
	{"item", "popup.shared.edit.edit"},
	{"item", ""},
//	{"item", "popup.shared.edit.cut"},
//	{"item", "popup.shared.edit.copy"},
//	{"item", ""},
//	{"item", "popup.shared.edit.delete"},
//	{"item", ""},
	{"popup.node.sequence.view", ""},
	
	{"popup.node.project.view", "Project View Node Menu"},
	{"item", "popup.show.dependency.project.view"},
	{"item", ""},			
//	{"item", "popup.node.fold.foldChildren.oneLevel"},
//	{"item", "popup.node.fold.foldChildren.nLevels"},
//	{"item", "popup.node.fold.foldChildren.allLevels"},
//	{"item", ""},
	{"popup.node.project.view", ""},
	
	{"popup.nodeLabel", "Node Label Menu"},
		{"submenu", "popup.shared.edit"},
			{"item", "popup.shared.edit.cut"},
			{"item", "popup.shared.edit.copy"},
			{"item", "popup.shared.edit.paste"},
			{"item", "popup.shared.edit.duplicate"},
			{"item", ""},
			{"item", "popup.shared.edit.delete"},
		{"submenu", ""},
		{"item", ""},
		{"item", "popup.shared.openURL"},
		{"item", ""},
		{"item", "popup.shared.objectProperties"},
	{"popup.nodeLabel", ""},

	{"popup.edge", "Edge Menu"},
		{"submenu", "popup.shared.navigate"},
			{"item", "popup.shared.navigate.goToChildGraph"},
			{"item", ""},
			{"item", "popup.shared.navigate.createChildGraph"},
			 {"item", "popup.shared.navigate.deleteChildGraph"},
		{"submenu", ""},
		{"item", ""},
		{"submenu", "popup.shared.hide"},
			{"item", "popup.shared.hide.hideSelected"},
		{"submenu", ""},
		{"item", ""},
		{"submenu", "popup.shared.edit"},
			{"item", "popup.shared.edit.cut"},
			{"item", "popup.shared.edit.copy"},
			{"item", "popup.shared.edit.paste"},
			{"item", "popup.shared.edit.duplicate"},
			{"item", ""},
			{"item", "popup.shared.edit.delete"},
		{"submenu", ""},
		{"item", ""},
		{"submenu", "popup.edge.createLabel"},
			{"item", "popup.edge.createLabel.text"},
			{"item", "popup.edge.createLabel.button"},
			{"item", "popup.edge.createLabel.checkBox"},
		{"submenu", ""},
		{"item", ""},
		{"item", "popup.shared.openURL"},
		{"item", ""},
		{"item", "popup.shared.objectProperties"},
	{"popup.edge", ""},

	{"popup.edge.state.machine", "StateMachine Edge Menu"},
		{"item", "popup.shared.edit.cut"},
		{"item", "popup.shared.edit.copy"},
		{"item", "popup.shared.edit.delete"},
	{"popup.edge.state.machine", ""},
	
	{"popup.edge.concept.view", "Concept Edge Menu"},
		{"item", "popup.shared.edit.delete"},
	{"popup.edge.concept.view", ""},
	
	{"popup.edge.cluster.view", "ClusterView Edge Menu"},
	{"item", "popup.shared.edit.cut"},
	{"item", "popup.shared.edit.copy"},
	{"item", "popup.shared.edit.delete"},
	{"item", ""},
	{"popup.edge.cluster.view", ""},
	
	{"popup.edgeLabel", "Edge Label Menu"},
		{"submenu", "popup.shared.edit"},
			{"item", "popup.shared.edit.cut"},
			{"item", "popup.shared.edit.copy"},
			{"item", "popup.shared.edit.paste"},
			{"item", "popup.shared.edit.duplicate"},
			{"item", ""},
			{"item", "popup.shared.edit.delete"},
		{"submenu", ""},
		{"item", ""},
		{"item", "popup.shared.openURL"},
		{"item", ""},
		{"item", "popup.shared.objectProperties"},
	{"popup.edgeLabel", ""},

	{"popup.bend", "Bend Menu"},
		{"submenu", "popup.shared.edit"},
			{"item", "popup.shared.edit.delete"},
		{"submenu", ""},
		{"item", ""},
		{"item", "popup.shared.objectProperties"},
	{"popup.bend", ""},

	{"popup.connector", "Connector Menu"},
		{"submenu", "popup.shared.edit"},
			{"item", "popup.shared.edit.delete"},
		{"submenu", ""},
		{"item", ""},
        {"submenu", "popup.connector.createLabel"},
			{"item", "popup.connector.createLabel.text"},
			{"item", "popup.connector.createLabel.button"},
			{"item", "popup.connector.createLabel.checkBox"},
		{"submenu", ""},
		{"item", ""},
		{"item", "popup.shared.openURL"},
        {"item", ""},
		{"item", "popup.shared.objectProperties"},
	{"popup.connector", ""},

	{"popup.connectorLabel", "Connector Label Menu"},
		{"submenu", "popup.shared.edit"},
			{"item", "popup.shared.edit.cut"},
			{"item", "popup.shared.edit.copy"},
			{"item", "popup.shared.edit.paste"},
			{"item", "popup.shared.edit.duplicate"},
			{"item", ""},
			{"item", "popup.shared.edit.delete"},
		{"submenu", ""},
		{"item", ""},
		{"item", "popup.shared.openURL"},
		{"item", ""},
		{"item", "popup.shared.objectProperties"},
	{"popup.connectorLabel", ""},

	{"popup.graph", "Graph Menu"},
//		{"submenu", "popup.graph.navigate"},
//			{"item", "popup.shared.navigate.goToChildGraph"},
//			{"item", ""},
//			{"item", "popup.shared.navigate.collapse"},
//			{"item", ""},
//			{"item", "popup.graph.navigate.goToParentGraph"},
//			{"item", "popup.graph.navigate.goToRootGraph"},
//		{"submenu", ""},
//		{"item", ""},
//		{"submenu", "popup.graph.zoom"},
//			{"item", "popup.graph.zoom.fitInCanvas"},
//			{"item", ""},
//			{"item", "popup.graph.zoom.400"},
//			{"item", "popup.graph.zoom.200"},
//			{"item", "popup.graph.zoom.100"},
//			{"item", "popup.graph.zoom.50"},
//			{"item", "popup.graph.zoom.25"},
//			{"item", ""},
//			{"item", "popup.graph.zoom.customZoom"},
//		{"submenu", ""},
//		{"item", ""},
//		{"submenu", "popup.shared.edit"},
//			{"item", "popup.shared.edit.cut"},
//			{"item", "popup.shared.edit.copy"},
//			{"item", "popup.shared.edit.paste"},
//			{"item", "popup.shared.edit.duplicate"},
//			{"item", ""},
//			{"item", "popup.shared.edit.delete"},
//		{"submenu", ""},
//		{"item", ""},
//		{"item", "popup.graph.layoutConstraints"},
//		{"item", "popup.graph.layoutProperties"},
//		{"item", ""},
//		{"item", "popup.graph.drawingPreferences"},
//		{"item", ""},
//		{"item", "popup.shared.openURL"},
//		{"item", ""},
//		{"item", "popup.shared.objectProperties"},

		{"item", "popup.graph.fold.exportImage"},	
		{"item", ""},
		{"item", "popup.graph.fold.printSetup"},	
		{"item", "popup.graph.fold.printPreview"},	
		{"item", ""},
		{"item", "popup.graph.fold.customFold"},	
		{"item", "popup.graph.fold.unfoldall"},	
	{"item", ""},
		{"item", "popup.graph.zoom.fitInCanvas"},
	{"item", ""},
		{"item", "popup.graph.zoom.400"},
		{"item", "popup.graph.zoom.200"},
		{"item", "popup.graph.zoom.100"},
		{"item", "popup.graph.zoom.50"},
		{"item", "popup.graph.zoom.25"},
	{"item", ""},
		{"item", "popup.graph.zoom.customZoom"},
	{"item", ""},
		{"item", "popup.graph.layoutConstraints"},
		{"item", "popup.graph.layoutProperties"},
	{"item", ""},
	{"item", "popup.graph.drawingPreferences"},
	{"item", ""},
		{"item", "popup.shared.objectProperties"},
	
	{"popup.graph", ""},

	{"popup.graph.state.machine", "StateMachine Graph Menu"},
	{"item", "popup.shared.edit.paste"},
	{"item", ""},
	{"item", "popup.graph.fold.exportImage"},	
	{"item", "popup.graph.fold.printSetup"},	
	{"item", "popup.graph.fold.printPreview"},	
	{"item", ""},
	{"item", "popup.statemachine.createTransition"},
	{"item", ""},
	{"submenu", "popup.statemachine.newResource"},
	    {"item", "popup.statemachine.endState"},
	    {"item", ""},
		{"item", "popup.statemachine.simpleState"},
		{"item", "popup.statemachine.compositeState"},
		{"item", "popup.statemachine.concurrentState"},
		{"item", "popup.statemachine.region"},
		{"item", ""},
		{"item", "popup.statemachine.callStateModel"},
    {"submenu", ""},
	{"popup.graph.state.machine", ""},
	
	{"popup.graph.cluster.view", "ClusterView Graph Menu"},
	{"item", "popup.shared.edit.paste"},
	{"item", ""},
	{"popup.graph.cluster.view", ""},
	{"popup.graph.cluster.view", "Concept View Graph Menu"},
	{"item", "popup.graph.fold.exportImage"},	
	{"item", "popup.graph.fold.printSetup"},	
	{"item", "popup.graph.fold.printPreview"},	
	{"item", ""},
	{"item", "popup.graph.zoom.fitInCanvas"},
	{"popup.graph.cluster.view", ""},
	
	{"popup.graph.dependency.view", "Dependency Graph Menu"},
	{"item", "popup.graph.fold.exportImage"},	
	{"item", "popup.graph.fold.printSetup"},	
	{"item", "popup.graph.fold.printPreview"},	
	{"item", ""},
	{"item", "popup.graph.zoom.fitInCanvas"},
	{"popup.graph.dependency.view", ""},
	
	{"popup.graph.concept.view", "Concept View Graph Menu"},
	{"item", "popup.graph.fold.exportImage"},	
	{"item", "popup.graph.fold.printSetup"},	
	{"item", "popup.graph.fold.printPreview"},	
	{"item", ""},
	{"item", "popup.graph.zoom.fitInCanvas"},
	{"popup.graph.concept.view", ""},
	
	{"popup.graph.sequence.view", "Sequence Diagram Graph Menu"},
	{"item", "popup.graph.fold.exportImage"},	
	{"item", "popup.graph.fold.printSetup"},	
	{"item", "popup.graph.fold.printPreview"},	
	{"item", ""},
	{"item", "popup.graph.zoom.fitInCanvas"},
	{"popup.graph.sequence.view", ""},
	
	{"popup.graph.project.view", "Project View Graph Menu"},
	{"item", "popup.node.diagram.element"},
	{"item", ""},
	{"item", "popup.graph.fold.exportImage"},	
	{"item", "popup.graph.fold.printSetup"},	
	{"item", "popup.graph.fold.printPreview"},	
	{"item", ""},
	{"item", "popup.graph.zoom.fitInCanvas"},
	{"popup.graph.project.view", ""},
	
	{"popup.graph", ""},

	{"popup.graph.decision.tree", "Decision Tree Menu"},
	{"item", "popup.graph.fold.exportImage"},	
	{"item", "popup.graph.fold.printSetup"},	
	{"item", "popup.graph.fold.printPreview"},	
	{"item", ""},
	{"item", "popup.graph.zoom.fitInCanvas"},
	{"popup.graph.decision.tree", ""},
	
// ----------------------------------------------------------------------
// Section: Key binding
//
// This section defines the key bindings. The key binding
// starts with the keyword "key". The number that follows
// is the index number which must be increased when you add
// new key bindings.
// Each key event has a key code, modifier value, command and
// an focus value.
// ----------------------------------------------------------------------

		{"key.1.keyCode", String.valueOf(KeyEvent.VK_PLUS)},
		{"key.1.modifiers", String.valueOf(0)},
		{"key.1.command", EntityResourceConstants.ZOOM_IN},
		{"key.1.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.2.keyCode", String.valueOf(KeyEvent.VK_ADD)},
		{"key.2.modifiers", String.valueOf(0)},
		{"key.2.command", EntityResourceConstants.ZOOM_IN},
		{"key.2.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.3.keyCode", String.valueOf(KeyEvent.VK_EQUALS)},
		{"key.3.modifiers", String.valueOf(0)},
		{"key.3.command", EntityResourceConstants.ZOOM_IN},
		{"key.3.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.4.keyCode", String.valueOf(KeyEvent.VK_MINUS)},
		{"key.4.modifiers", String.valueOf(0)},
		{"key.4.command", EntityResourceConstants.ZOOM_OUT},
		{"key.4.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.5.keyCode", String.valueOf(KeyEvent.VK_SUBTRACT)},
		{"key.5.modifiers", String.valueOf(0)},
		{"key.5.command", EntityResourceConstants.ZOOM_OUT},
		{"key.5.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.6.keyCode", String.valueOf(KeyEvent.VK_PLUS)},
		{"key.6.modifiers", String.valueOf(KeyEvent.SHIFT_MASK)},
		{"key.6.command", EntityResourceConstants.ZOOM_IN},
		{"key.6.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.7.keyCode", String.valueOf(KeyEvent.VK_ADD)},
		{"key.7.modifiers", String.valueOf(KeyEvent.SHIFT_MASK)},
		{"key.7.command", EntityResourceConstants.ZOOM_IN},
		{"key.7.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.8.keyCode", String.valueOf(KeyEvent.VK_EQUALS)},
		{"key.8.modifiers", String.valueOf(KeyEvent.SHIFT_MASK)},
		{"key.8.command", EntityResourceConstants.ZOOM_IN},
		{"key.8.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.9.keyCode", String.valueOf(KeyEvent.VK_MINUS)},
		{"key.9.modifiers", String.valueOf(KeyEvent.SHIFT_MASK)},
		{"key.9.command", EntityResourceConstants.ZOOM_OUT},
		{"key.9.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.10.keyCode", String.valueOf(KeyEvent.VK_SUBTRACT)},
		{"key.10.modifiers", String.valueOf(KeyEvent.SHIFT_MASK)},
		{"key.10.command", EntityResourceConstants.ZOOM_OUT},
		{"key.10.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.11.keyCode", String.valueOf(KeyEvent.VK_PLUS)},
		{"key.11.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.11.command", EntityResourceConstants.ZOOM_IN},
		{"key.11.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.12.keyCode", String.valueOf(KeyEvent.VK_ADD)},
		{"key.12.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.12.command", EntityResourceConstants.ZOOM_IN},
		{"key.12.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.13.keyCode", String.valueOf(KeyEvent.VK_EQUALS)},
		{"key.13.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.13.command", EntityResourceConstants.ZOOM_IN},
		{"key.13.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.14.keyCode", String.valueOf(KeyEvent.VK_MINUS)},
		{"key.14.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.14.command", EntityResourceConstants.ZOOM_OUT},
		{"key.14.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.15.keyCode", String.valueOf(KeyEvent.VK_SUBTRACT)},
		{"key.15.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.15.command", EntityResourceConstants.ZOOM_OUT},
		{"key.15.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.16.keyCode", String.valueOf(KeyEvent.VK_BACK_SPACE)},
		{"key.16.modifiers", String.valueOf(0)},
		{"key.16.command", EntityResourceConstants.DELETE_SELECTED},
		{"key.16.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.17.keyCode", String.valueOf(KeyEvent.VK_ESCAPE)},
		{"key.17.modifiers", String.valueOf(0)},
		{"key.17.command", EntityResourceConstants.ACTION_ABORT},
		{"key.17.focus", String.valueOf
			(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)},

		{"key.18.keyCode", String.valueOf(KeyEvent.VK_LEFT)},
		{"key.18.modifiers", String.valueOf(0)},
		{"key.18.command", EntityResourceConstants.SCROLL_LEFT},
		{"key.18.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.19.keyCode", String.valueOf(KeyEvent.VK_RIGHT)},
		{"key.19.modifiers", String.valueOf(0)},
		{"key.19.command", EntityResourceConstants.SCROLL_RIGHT},
		{"key.19.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.20.keyCode", String.valueOf(KeyEvent.VK_UP)},
		{"key.20.modifiers", String.valueOf(0)},
		{"key.20.command", EntityResourceConstants.SCROLL_UP},
		{"key.20.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.21.keyCode", String.valueOf(KeyEvent.VK_DOWN)},
		{"key.21.modifiers", String.valueOf(0)},
		{"key.21.command", EntityResourceConstants.SCROLL_DOWN},
		{"key.21.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.22.keyCode", String.valueOf(KeyEvent.VK_SPACE)},
		{"key.22.modifiers", String.valueOf(0)},
		{"key.22.command", EntityResourceConstants.NEXT_TOOL},
		{"key.22.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.23.keyCode", String.valueOf(KeyEvent.VK_SPACE)},
		{"key.23.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.23.command", EntityResourceConstants.NEXT_TOOL_SET},
		{"key.23.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.24.keyCode", String.valueOf(KeyEvent.VK_LEFT)},
		{"key.24.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.24.command", EntityResourceConstants.MOVE_LEFT},
		{"key.24.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.25.keyCode", String.valueOf(KeyEvent.VK_RIGHT)},
		{"key.25.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.25.command", EntityResourceConstants.MOVE_RIGHT},
		{"key.25.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.26.keyCode", String.valueOf(KeyEvent.VK_UP)},
		{"key.26.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.26.command", EntityResourceConstants.MOVE_UP},
		{"key.26.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.27.keyCode", String.valueOf(KeyEvent.VK_DOWN)},
		{"key.27.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.27.command", EntityResourceConstants.MOVE_DOWN},
		{"key.27.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.28.keyCode", String.valueOf(KeyEvent.VK_F6)},
		{"key.28.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.28.command", EntityResourceConstants.NEXT_CANVAS},
		{"key.28.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.29.keyCode", String.valueOf(KeyEvent.VK_TAB)},
		{"key.29.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.29.command", EntityResourceConstants.NEXT_CANVAS},
		{"key.29.focus", String.valueOf(JComponent.WHEN_FOCUSED)},

		{"key.30.keyCode", String.valueOf(KeyEvent.VK_LEFT)},
		{"key.30.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.30.command", EntityResourceConstants.MOVE_DONE},
		{"key.30.focus", String.valueOf(JComponent.WHEN_FOCUSED)},
		{"key.30.released", "true"},

		{"key.31.keyCode", String.valueOf(KeyEvent.VK_RIGHT)},
		{"key.31.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.31.command", EntityResourceConstants.MOVE_DONE},
		{"key.31.focus", String.valueOf(JComponent.WHEN_FOCUSED)},
		{"key.31.released", "true"},

		{"key.32.keyCode", String.valueOf(KeyEvent.VK_UP)},
		{"key.32.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.32.command", EntityResourceConstants.MOVE_DONE},
		{"key.32.focus", String.valueOf(JComponent.WHEN_FOCUSED)},
		{"key.32.released", "true"},

		{"key.33.keyCode", String.valueOf(KeyEvent.VK_DOWN)},
		{"key.33.modifiers", String.valueOf(KeyEvent.CTRL_MASK)},
		{"key.33.command", EntityResourceConstants.MOVE_DONE},
		{"key.33.focus", String.valueOf(JComponent.WHEN_FOCUSED)},
		{"key.33.released", "true"},

		{"key.34.keyCode", String.valueOf(KeyEvent.VK_LEFT)},
		{"key.34.modifiers", String.valueOf(0)},
		{"key.34.command", EntityResourceConstants.MOVE_DONE},
		{"key.34.focus", String.valueOf(JComponent.WHEN_FOCUSED)},
		{"key.34.released", "true"},

		{"key.35.keyCode", String.valueOf(KeyEvent.VK_RIGHT)},
		{"key.35.modifiers", String.valueOf(0)},
		{"key.35.command", EntityResourceConstants.MOVE_DONE},
		{"key.35.focus", String.valueOf(JComponent.WHEN_FOCUSED)},
		{"key.35.released", "true"},

		{"key.36.keyCode", String.valueOf(KeyEvent.VK_UP)},
		{"key.36.modifiers", String.valueOf(0)},
		{"key.36.command", EntityResourceConstants.MOVE_DONE},
		{"key.36.focus", String.valueOf(JComponent.WHEN_FOCUSED)},
		{"key.36.released", "true"},

		{"key.37.keyCode", String.valueOf(KeyEvent.VK_DOWN)},
		{"key.37.modifiers", String.valueOf(0)},
		{"key.37.command", EntityResourceConstants.MOVE_DONE},
		{"key.37.focus", String.valueOf(JComponent.WHEN_FOCUSED)},
		{"key.37.released", "true"},


// ----------------------------------------------------------------------
// Section: Items specifications
//
// Each may have a number of properties, defined here. The most obvious
// are:
// 	   text        - how the item is shown to the user,
//	   mnemonic    - which letter activates the item,
//	   command     - the action associated with the item,
// 	   icon        - the image that is shown with the item,
//	   tooltip     - the text shown when the mouse hovers above the item
//	   accelerator - the hot key that activates the item
// ----------------------------------------------------------------------

		// menu and toolbar items resources: specify texts, mnemonics,
		// commands, icons, tooltips, accelerators

		{"popup.graph.layoutProperties.text", "Layout Properties..."},
		{"popup.graph.layoutProperties.mnemonic", "p"},
		{"popup.graph.layoutProperties.command",
			EntityResourceConstants.LAYOUT_PROPERTIES},

		{"popup.graph.layoutConstraints.text", "Layout Constraints..."},
		{"popup.graph.layoutConstraints.mnemonic", "l"},
		{"popup.graph.layoutConstraints.command",
			EntityResourceConstants.CONSTRAINT_WINDOW},

		{"popup.graph.drawingPreferences.text", "Preferences..."},
		{"popup.graph.drawingPreferences.mnemonic", "r"},
		{"popup.graph.drawingPreferences.command",
			EntityResourceConstants.DRAWING_PREFERENCES},

		{"popup.shared.objectProperties.text", "Object Properties..."},
		{"popup.shared.objectProperties.mnemonic", "b"},
		{"popup.shared.objectProperties.command",
			EntityResourceConstants.INSPECTOR_WINDOW},

		{"popup.shared.navigate.text", "Navigate"},
		{"popup.shared.navigate.mnemonic", "n"},

		{"popup.shared.navigate.goToChildGraph.text", "Go To Child"},
		{"popup.shared.navigate.goToChildGraph.mnemonic", "g"},
		{"popup.shared.navigate.goToChildGraph.command",
			EntityResourceConstants.GOTO_CHILD},

		{"popup.shared.navigate.expand.text", "Expand"},
		{"popup.shared.navigate.expand.mnemonic", "e"},
		{"popup.shared.navigate.expand.command", EntityResourceConstants.EXPAND},

		{"popup.shared.navigate.collapse.text", "Collapse"},
		{"popup.shared.navigate.collapse.mnemonic", "c"},
		{"popup.shared.navigate.collapse.command", EntityResourceConstants.COLLAPSE},

		{"popup.shared.navigate.createChildGraph.text", "Create Child"},
		{"popup.shared.navigate.createChildGraph.mnemonic", "r"},
		{"popup.shared.navigate.createChildGraph.command",
			EntityResourceConstants.CREATE_CHILD_GRAPH},

		{"popup.shared.navigate.deleteChildGraph.text", "Delete Child"},
		{"popup.shared.navigate.deleteChildGraph.mnemonic", "d"},
		{"popup.shared.navigate.deleteChildGraph.command",
			EntityResourceConstants.DELETE_CHILD_GRAPH},

		{"popup.shared.hide.text", "Hide"},
		{"popup.shared.hide.mnemonic", "h"},

		{"popup.shared.hide.hideSelected.text", "Hide Selected"},
		{"popup.shared.hide.hideSelected.mnemonic", "s"},
		{"popup.shared.hide.hideSelected.command",
			EntityResourceConstants.HIDE_SELECTED},

		{"popup.shared.edit.text", "Edit"},
		{"popup.shared.edit.mnemonic", "e"},

		{"popup.shared.edit.edit.text", "Edit"},
		{"popup.shared.edit.edit.mnemonic", "e"},
		{"popup.shared.edit.edit.icon", "images/edit.gif"},
		{"popup.shared.edit.edit.command", EntityResourceConstants.EDIT_NODE},
		
		{"popup.shared.edit.cut.text", "Cut"},
		{"popup.shared.edit.cut.mnemonic", "t"},
		{"popup.shared.edit.cut.icon", "/icons/cut_edit.png"},
		{"popup.shared.edit.cut.accelerator",	KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK)},
		{"popup.shared.edit.cut.command", EntityResourceConstants.CUT_GRAPH},

		{"popup.show.dependency.project.view.text", "Create Dependency Diagram"},
		{"popup.show.dependency.project.view.mnemonic", "t"},
		{"popup.show.dependency.project.view.icon", "images/cut.gif"},
		{"popup.show.dependency.project.view.command", EntityResourceConstants.SHOW_DEPENDENCY_GRAPH},
		
		
		{"popup.shared.edit.copy.text", "Copy"},
		{"popup.shared.edit.copy.mnemonic", "c"},
		{"popup.shared.edit.copy.icon", "/icons/copy_edit.png"},
		{"popup.shared.edit.copy.accelerator",	KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK)},
		{"popup.shared.edit.copy.command", EntityResourceConstants.COPY_GRAPH},

		{"popup.shared.edit.paste.text", "Paste"},
		{"popup.shared.edit.paste.mnemonic", "p"},
		{"popup.shared.edit.paste.icon", "/icons/paste_edit.png"},
		{"popup.shared.edit.paste.accelerator",	KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK)},
		{"popup.shared.edit.paste.command", EntityResourceConstants.PASTE_GRAPH},

		{"popup.bpmn.node.addInBreakpoint.text", "Add Incoming Breakpoint"},
		{"popup.bpmn.node.addInBreakpoint.icon", "/icons/transition.png"},
		{"popup.bpmn.node.addInBreakpoint.accelerator",	KeyStroke.getKeyStroke(KeyEvent.VK_F3, KeyEvent.CTRL_MASK)},
		{"popup.bpmn.node.addInBreakpoint.command", EntityResourceConstants.SM_TRANSITION},

		{"popup.bpmn.node.addOutBreakpoint.text", "Add Outgoing Breakpoint"},
		{"popup.bpmn.node.addOutBreakpoint.icon", "/icons/transition.png"},
		{"popup.bpmn.node.addOutBreakpoint.accelerator",	KeyStroke.getKeyStroke(KeyEvent.VK_F3, KeyEvent.CTRL_MASK)},
		{"popup.bpmn.node.addOutBreakpoint.command", EntityResourceConstants.SM_TRANSITION},

		
		{"popup.statemachine.createTransition.text", "Make Transition"},
		{"popup.statemachine.createTransition.icon", "/icons/transition.png"},
		{"popup.statemachine.createTransition.accelerator",	KeyStroke.getKeyStroke(KeyEvent.VK_F3, KeyEvent.CTRL_MASK)},
		{"popup.statemachine.createTransition.command", EntityResourceConstants.SM_TRANSITION},
		
		{"popup.statemachine.newResource.text", "New Resource..."},
		
		{"popup.statemachine.simpleState.text", "Simple State"},
		{"popup.statemachine.simpleState.icon", "/icons/SimpleState.png"},
		{"popup.statemachine.simpleState.command", EntityResourceConstants.SM_SIMPLE_STATE},
		
		{"popup.statemachine.compositeState.text", "Composite State"},
		{"popup.statemachine.compositeState.icon", "/icons/composite.png"},
		{"popup.statemachine.compositeState.command", EntityResourceConstants.SM_COMPOSITE_STATE},
		
		{"popup.statemachine.concurrentState.text", "Concurrent State"},
		{"popup.statemachine.concurrentState.icon", "/icons/concurrent.png"},
		{"popup.statemachine.concurrentState.command", EntityResourceConstants.SM_CONCURRENT_STATE},
		
		{"popup.statemachine.region.text", "Region"},
		{"popup.statemachine.region.icon", "/icons/region.png"},
		{"popup.statemachine.region.command", EntityResourceConstants.SM_REGION_STATE},
		
		{"popup.statemachine.callStateModel.text", "Call State Model"},
		{"popup.statemachine.callStateModel.icon", "/icons/sub_machinestate.png"},
		{"popup.statemachine.callStateModel.command", EntityResourceConstants.SM_CALL_STATE_MODEL},
		
		{"popup.statemachine.endState.text", "End State"},
		{"popup.statemachine.endState.icon", "/icons/EndState.png"},
		{"popup.statemachine.endState.command", EntityResourceConstants.SM_END_STATE},
		
		{"popup.shared.edit.duplicate.text", "Duplicate"},
		{"popup.shared.edit.duplicate.mnemonic", "u"},
		{"popup.shared.edit.duplicate.command",
			EntityResourceConstants.DUPLICATE_GRAPH},

		{"popup.shared.edit.delete.text", "Delete"},
		{"popup.shared.edit.delete.mnemonic", "d"},
		{"popup.shared.edit.delete.icon", "/icons/delete.png"},
		{"popup.shared.edit.delete.accelerator", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)},
		{"popup.shared.edit.delete.command", EntityResourceConstants.DELETE_SELECTED},
			

		{"popup.shared.openURL.text", "Open URL"},
		{"popup.shared.openURL.mnemonic", "u"},
		{"popup.shared.openURL.command", EntityResourceConstants.OPEN_URL},

		{"popup.graph.navigate.text", "Navigate"},
		{"popup.graph.navigate.mnemonic", "n"},

		{"popup.graph.navigate.goToParentGraph.text", "Go To Parent"},
		{"popup.graph.navigate.goToParentGraph.mnemonic", "p"},
		{"popup.graph.navigate.goToParentGraph.command",
			EntityResourceConstants.GOTO_PARENT},

		{"popup.graph.navigate.goToRootGraph.text", "Go To Root"},
		{"popup.graph.navigate.goToRootGraph.mnemonic", "r"},
		{"popup.graph.navigate.goToRootGraph.command",
			EntityResourceConstants.GOTO_ROOT},

		{"popup.graph.zoom.text", "Zoom"},
		{"popup.graph.zoom.mnemonic", "z"},

		{"popup.graph.zoom.fitInCanvas.text", "Fit In Window"},
		{"popup.graph.zoom.fitInCanvas.mnemonic", "f"},
		{"popup.graph.zoom.fitInCanvas.icon", "images/fitInCanvas.gif"},
		{"popup.graph.zoom.fitInCanvas.group", "zoom"},
		{"popup.graph.zoom.fitInCanvas.command", EntityResourceConstants.FIT_IN_WINDOW},
//		{"popup.graph.zoom.fitInCanvas.accelerator",
//			KeyStroke.getKeyStroke(KeyEvent.VK_F, MENU_SHORTCUT_KEY_MASK)},

		{"popup.graph.zoom.25.text", "Zoom 25%"},
		{"popup.graph.zoom.25.mnemonic", "2"},
		{"popup.graph.zoom.25.group", "zoom"},
		{"popup.graph.zoom.25.command", EntityResourceConstants.ZOOM + ".25"},

		{"popup.graph.zoom.50.text", "Zoom 50%"},
		{"popup.graph.zoom.50.mnemonic", "5"},
		{"popup.graph.zoom.50.group", "zoom"},
		{"popup.graph.zoom.50.command", EntityResourceConstants.ZOOM + ".50"},

		{"popup.graph.zoom.100.text", "Zoom 100%"},
		{"popup.graph.zoom.100.mnemonic", "1"},
		{"popup.graph.zoom.100.group", "zoom"},
		{"popup.graph.zoom.100.command", EntityResourceConstants.ZOOM + ".100"},

		{"popup.graph.zoom.200.text", "Zoom 200%"},
		{"popup.graph.zoom.200.mnemonic", "0"},
		{"popup.graph.zoom.200.group", "zoom"},
		{"popup.graph.zoom.200.command", EntityResourceConstants.ZOOM + ".200"},

		{"popup.graph.zoom.400.text", "Zoom 400%"},
		{"popup.graph.zoom.400.mnemonic", "4"},
		{"popup.graph.zoom.400.group", "zoom"},
		{"popup.graph.zoom.400.command", EntityResourceConstants.ZOOM + ".400"},

		{"popup.graph.fold.customFold.text", "Simplify Tree..."},
		{"popup.graph.fold.customFold.mnemonic", "z"},
		{"popup.graph.fold.customFold.group", "zoom"},
		{"popup.graph.fold.customFold.command", EntityResourceConstants.FOLD_CUSTOM},
//		{"popup.graph.fold.customFold.accelerator",
//			KeyStroke.getKeyStroke(KeyEvent.VK_M, MENU_SHORTCUT_KEY_MASK)},

		{"popup.graph.fold.exportImage.text", "Export to Image..."},
		{"popup.graph.fold.exportImage.mnemonic", "z"},
		{"popup.graph.fold.exportImage.group", "zoom"},
		{"popup.graph.fold.exportImage.command", EntityResourceConstants.EXPORT_IMAGE},
//		{"popup.graph.fold.exportImage.accelerator",
//			KeyStroke.getKeyStroke(KeyEvent.VK_M, MENU_SHORTCUT_KEY_MASK)},

		{"popup.graph.fold.printSetup.text", "Print Setup..."},
		{"popup.graph.fold.printSetup.group", "zoom"},
		{"popup.graph.fold.printSetup.command", EntityResourceConstants.PRINT_SETUP},

		{"popup.graph.fold.printPreview.text", "Print Preview..."},
		{"popup.graph.fold.printPreview.group", "zoom"},
		{"popup.graph.fold.printPreview.command", EntityResourceConstants.PRINT_PREVIEW},
		
		{"popup.graph.fold.unfoldall.text", "Unfold Entire Tree"},
		{"popup.graph.fold.unfoldall.mnemonic", "u"},
		{"popup.graph.fold.unfoldall.command", EntityResourceConstants.UNFOLDALL},
			
		{"popup.graph.zoom.customZoom.text", "Custom Zoom..."},
		{"popup.graph.zoom.customZoom.mnemonic", "z"},
		{"popup.graph.zoom.customZoom.group", "zoom"},
		{"popup.graph.zoom.customZoom.command", EntityResourceConstants.ZOOM_CUSTOM},
//		{"popup.graph.zoom.customZoom.accelerator",
//			KeyStroke.getKeyStroke(KeyEvent.VK_M, MENU_SHORTCUT_KEY_MASK)},

		{"popup.node.hide.hideChildren.text", "Hide Children"},
		{"popup.node.hide.hideChildren.mnemonic", "h"},

		{"popup.node.hide.hideChildren.oneLevel.text", "One Level"},
		{"popup.node.hide.hideChildren.oneLevel.mnemonic", "o"},
		{"popup.node.hide.hideChildren.oneLevel.command",
			EntityResourceConstants.HIDE_CHILDREN_ONE_LEVEL},

		{"popup.node.hide.hideChildren.nLevels.text", "N Levels..."},
		{"popup.node.hide.hideChildren.nLevels.mnemonic", "n"},
		{"popup.node.hide.hideChildren.nLevels.command",
			EntityResourceConstants.HIDE_CHILDREN_N_LEVEL},

		{"popup.node.hide.hideChildren.allLevels.text", "All Levels"},
		{"popup.node.hide.hideChildren.allLevels.mnemonic", "a"},
		{"popup.node.hide.hideChildren.allLevels.command",
			EntityResourceConstants.HIDE_CHILDREN_ALL_LEVEL},

		{"popup.node.hide.hideParents.text", "Hide Parents"},
		{"popup.node.hide.hideParents.mnemonic", "p"},

		{"popup.node.hide.hideParents.oneLevel.text", "One Level"},
		{"popup.node.hide.hideParents.oneLevel.mnemonic", "o"},
		{"popup.node.hide.hideParents.oneLevel.command",
			EntityResourceConstants.HIDE_PARENTS_ONE_LEVEL},

		{"popup.node.hide.hideParents.nLevels.text", "N Levels..."},
		{"popup.node.hide.hideParents.nLevels.mnemonic", "n"},
		{"popup.node.hide.hideParents.nLevels.command",
			EntityResourceConstants.HIDE_PARENTS_N_LEVEL},

		{"popup.node.hide.hideParents.allLevels.text", "All Levels"},
		{"popup.node.hide.hideParents.allLevels.mnemonic", "a"},
		{"popup.node.hide.hideParents.allLevels.command",
			EntityResourceConstants.HIDE_PARENTS_ALL_LEVEL},

		{"popup.node.hide.hideNeighbors.text", "Hide Neighbors"},
		{"popup.node.hide.hideNeighbors.mnemonic", "n"},

		{"popup.node.hide.hideNeighbors.oneLevel.text", "One Level"},
		{"popup.node.hide.hideNeighbors.oneLevel.mnemonic", "o"},
		{"popup.node.hide.hideNeighbors.oneLevel.command",
			EntityResourceConstants.HIDE_NEIGHBORS_ONE_LEVEL},

		{"popup.node.hide.hideNeighbors.nLevels.text", "N Levels..."},
		{"popup.node.hide.hideNeighbors.nLevels.mnemonic", "n"},
		{"popup.node.hide.hideNeighbors.nLevels.command",
			EntityResourceConstants.HIDE_NEIGHBORS_N_LEVEL},

		{"popup.node.hide.hideNeighbors.allLevels.text", "All Levels"},
		{"popup.node.hide.hideNeighbors.allLevels.mnemonic", "a"},
		{"popup.node.hide.hideNeighbors.allLevels.command",
			EntityResourceConstants.HIDE_NEIGHBORS_ALL_LEVEL},

		{"popup.node.hide.hideIncidentEdges.text", "Hide Incident Edges"},
		{"popup.node.hide.hideIncidentEdges.mnemonic", "c"},
		{"popup.node.hide.hideIncidentEdges.command",
			EntityResourceConstants.HIDE_INCIDENT_EDGES},
		
		{"popup.node.hide.unhideChildren.text", "Unhide Children"},
		{"popup.node.hide.unhideChildren.mnemonic", "u"},

		{"popup.node.hide.unhideChildren.oneLevel.text", "One Level"},
		{"popup.node.hide.unhideChildren.oneLevel.mnemonic", "o"},
		{"popup.node.hide.unhideChildren.oneLevel.command",
			EntityResourceConstants.UNHIDE_CHILDREN_ONE_LEVEL},

		{"popup.node.hide.unhideChildren.nLevels.text", "N Levels..."},
		{"popup.node.hide.unhideChildren.nLevels.mnemonic", "n"},
		{"popup.node.hide.unhideChildren.nLevels.command",
			EntityResourceConstants.UNHIDE_CHILDREN_N_LEVEL},

		{"popup.node.hide.unhideChildren.allLevels.text", "All Levels"},
		{"popup.node.hide.unhideChildren.allLevels.mnemonic", "a"},
		{"popup.node.hide.unhideChildren.allLevels.command",
			EntityResourceConstants.UNHIDE_CHILDREN_ALL_LEVEL},

		{"popup.node.hide.unhideParents.text", "Unhide Parents"},
		{"popup.node.hide.unhideParents.mnemonic", "a"},

		{"popup.node.hide.unhideParents.oneLevel.text", "One Level"},
		{"popup.node.hide.unhideParents.oneLevel.mnemonic", "o"},
		{"popup.node.hide.unhideParents.oneLevel.command",
			EntityResourceConstants.UNHIDE_PARENTS_ONE_LEVEL},

		{"popup.node.hide.unhideParents.nLevels.text", "N Levels..."},
		{"popup.node.hide.unhideParents.nLevels.mnemonic", "n"},
		{"popup.node.hide.unhideParents.nLevels.command",
			EntityResourceConstants.UNHIDE_PARENTS_N_LEVEL},

		{"popup.node.hide.unhideParents.allLevels.text", "All Levels"},
		{"popup.node.hide.unhideParents.allLevels.mnemonic", "a"},
		{"popup.node.hide.unhideParents.allLevels.command",
			EntityResourceConstants.UNHIDE_PARENTS_ALL_LEVEL},

		{"popup.node.hide.unhideNeighbors.text", "Unhide Neighbors"},
		{"popup.node.hide.unhideNeighbors.mnemonic", "e"},

		{"popup.node.hide.unhideNeighbors.oneLevel.text", "One Level"},
		{"popup.node.hide.unhideNeighbors.oneLevel.mnemonic", "o"},
		{"popup.node.hide.unhideNeighbors.oneLevel.command",
			EntityResourceConstants.UNHIDE_NEIGHBORS_ONE_LEVEL},

		{"popup.node.hide.unhideNeighbors.nLevels.text", "N Levels..."},
		{"popup.node.hide.unhideNeighbors.nLevels.mnemonic", "n"},
		{"popup.node.hide.unhideNeighbors.nLevels.command",
			EntityResourceConstants.UNHIDE_NEIGHBORS_N_LEVEL},

		{"popup.node.hide.unhideNeighbors.allLevels.text", "All Levels"},
		{"popup.node.hide.unhideNeighbors.allLevels.mnemonic", "a"},
		{"popup.node.hide.unhideNeighbors.allLevels.command",
			EntityResourceConstants.UNHIDE_NEIGHBORS_ALL_LEVEL},

		{"popup.node.hide.unhideIncidentEdges.text", "Unhide Incident Edges"},
		{"popup.node.hide.unhideIncidentEdges.mnemonic", "i"},
		{"popup.node.hide.unhideIncidentEdges.command",
			EntityResourceConstants.UNHIDE_INCIDENT_EDGES},

		{"popup.node.fold.text", "Fold"},
		{"popup.node.fold.mnemonic", "f"},

		{"popup.node.fold.foldSelected.text", "Fold Selected"},
		{"popup.node.fold.foldSelected.mnemonic", "s"},
		{"popup.node.fold.foldSelected.command",
			EntityResourceConstants.FOLD_SELECTED},

		{"popup.node.fold.foldChildren.text", "Fold Children"},
		{"popup.node.fold.foldChildren.mnemonic", "f"},

		{"popup.node.fold.foldChildren.oneLevel.text", "Fold One Level"},
		{"popup.node.fold.foldChildren.oneLevel.mnemonic", "o"},
		{"popup.node.fold.foldChildren.oneLevel.command",
			EntityResourceConstants.FOLD_CHILDREN_ONE_LEVEL},

		{"popup.node.fold.foldChildren.nLevels.text", "Fold N Levels..."},
		{"popup.node.fold.foldChildren.nLevels.mnemonic", "n"},
		{"popup.node.fold.foldChildren.nLevels.command",
			EntityResourceConstants.FOLD_CHILDREN_N_LEVEL},

		{"popup.node.fold.foldChildren.allLevels.text", "Fold All Levels"},
		{"popup.node.fold.foldChildren.allLevels.mnemonic", "a"},
		{"popup.node.fold.foldChildren.allLevels.command",
			EntityResourceConstants.FOLD_CHILDREN_ALL_LEVEL},

		{"popup.node.fold.foldParents.text", "Fold Parents"},
		{"popup.node.fold.foldParents.mnemonic", "p"},

		{"popup.node.fold.foldParents.oneLevel.text", "Fold One Parent Level"},
		{"popup.node.fold.foldParents.oneLevel.mnemonic", "o"},
		{"popup.node.fold.foldParents.oneLevel.command",
			EntityResourceConstants.FOLD_PARENTS_ONE_LEVEL},

		{"popup.node.fold.foldParents.nLevels.text", "Fold N Parent Levels..."},
		{"popup.node.fold.foldParents.nLevels.mnemonic", "n"},
		{"popup.node.fold.foldParents.nLevels.command",
			EntityResourceConstants.FOLD_PARENTS_N_LEVEL},

		{"popup.node.fold.foldParents.allLevels.text", "Fold All Parent Levels"},
		{"popup.node.fold.foldParents.allLevels.mnemonic", "a"},
		{"popup.node.fold.foldParents.allLevels.command",
			EntityResourceConstants.FOLD_PARENTS_ALL_LEVEL},

		{"popup.node.fold.foldNeighbors.text", "Fold Neighbors"},
		{"popup.node.fold.foldNeighbors.mnemonic", "g"},

		{"popup.node.fold.foldNeighbors.oneLevel.text", "Fold One Level (neighbor)"},
		{"popup.node.fold.foldNeighbors.oneLevel.mnemonic", "o"},
		{"popup.node.fold.foldNeighbors.oneLevel.command",
			EntityResourceConstants.FOLD_NEIGHBORS_ONE_LEVEL},

		{"popup.node.fold.foldNeighbors.nLevels.text", "Fold N Levels... (neighbor)"},
		{"popup.node.fold.foldNeighbors.nLevels.mnemonic", "n"},
		{"popup.node.fold.foldNeighbors.nLevels.command",
			EntityResourceConstants.FOLD_NEIGHBORS_N_LEVEL},

		{"popup.node.fold.foldNeighbors.allLevels.text", "Fold All Levels (neighbor)"},
		{"popup.node.fold.foldNeighbors.allLevels.mnemonic", "a"},
		{"popup.node.fold.foldNeighbors.allLevels.command",
			EntityResourceConstants.FOLD_NEIGHBORS_ALL_LEVEL},

		{"popup.node.diagram.element.text", "Selected Entity Project Diagram"},
		{"popup.node.diagram.element.mnemonic", "e"},
		{"popup.node.diagram.element.command", EntityResourceConstants.ELEMENTDIAGRAM},
			
		{"popup.node.fold.unfold.text", "Unfold"},
		{"popup.node.fold.unfold.mnemonic", "u"},
		{"popup.node.fold.unfold.command", EntityResourceConstants.UNFOLD},

		{"popup.node.fold.unfoldSelected.text", "Unfold Selected"},
		{"popup.node.fold.unfoldSelected.mnemonic", "n"},
		{"popup.node.fold.unfoldSelected.command",
			EntityResourceConstants.UNFOLD_SELECTED},

		{"popup.node.createLabel.text", ""},
		{"popup.node.createLabel.mnemonic", "l"},

		{"popup.node.createLabel.text.text", "Create Label"},
		{"popup.node.createLabel.text.mnemonic", "t"},
		{"popup.node.createLabel.text.command",
			EntityResourceConstants.ADD_NODE_LABEL},

		{"popup.node.createLabel.button.text", "Button"},
		{"popup.node.createLabel.button.mnemonic", "b"},
		{"popup.node.createLabel.button.command",
			EntityResourceConstants.ADD_NODE_LABEL + "|labelUI.button"},

		{"popup.node.createLabel.checkBox.text", "CheckBox"},
		{"popup.node.createLabel.checkBox.mnemonic", "c"},
		{"popup.node.createLabel.checkBox.command",
			EntityResourceConstants.ADD_NODE_LABEL + "|labelUI.checkBox"},

		{"popup.node.createConnector.text", "Create Connector"},
		{"popup.node.createConnector.mnemonic", "c"},
		{"popup.node.createConnector.command",
			EntityResourceConstants.ADD_NODE_CONNECTOR},

		{"popup.node.deleteAllConnectors.text", "Delete All Connectors"},
		{"popup.node.deleteAllConnectors.mnemonic", "d"},
		{"popup.node.deleteAllConnectors.command",
                        EntityResourceConstants.DELETE_NODE_CONNECTORS},

		{"popup.connector.createLabel.text", "Create Label"},
		{"popup.connector.createLabel.mnemonic", "l"},

		{"popup.connector.createLabel.text.text", "Create Label"},
		{"popup.connector.createLabel.text.mnemonic", "t"},
		{"popup.connector.createLabel.text.command", EntityResourceConstants.ADD_CONNECTOR_LABEL + "|labelUI.text"},

		{"popup.connector.createLabel.button.text", "Button"},
		{"popup.connector.createLabel.button.mnemonic", "t"},
		{"popup.connector.createLabel.button.command", EntityResourceConstants.ADD_CONNECTOR_LABEL + "|labelUI.button"},

		{"popup.connector.createLabel.checkBox.text", "CheckBox"},
		{"popup.connector.createLabel.checkBox.mnemonic", "t"},
		{"popup.connector.createLabel.checkBox.command", EntityResourceConstants.ADD_CONNECTOR_LABEL + "|labelUI.checkBox"},

		{"popup.edge.createLabel.text", "Create Label"},
		{"popup.edge.createLabel.mnemonic", "l"},

		{"popup.edge.createLabel.text.text", "Create Label"},
		{"popup.edge.createLabel.text.mnemonic", "t"},
		{"popup.edge.createLabel.text.command",
			EntityResourceConstants.ADD_EDGE_LABEL + "|labelUI.text"},

		{"popup.edge.createLabel.button.text", "Button"},
		{"popup.edge.createLabel.button.mnemonic", "b"},
		{"popup.edge.createLabel.button.command",
			EntityResourceConstants.ADD_EDGE_LABEL + "|labelUI.button"},

		{"popup.edge.createLabel.checkBox.text", "CheckBox"},
		{"popup.edge.createLabel.checkBox.mnemonic", "c"},
		{"popup.edge.createLabel.checkBox.command",
			EntityResourceConstants.ADD_EDGE_LABEL + "|labelUI.checkBox"},

		{"popup.tab.close.text", "Close"},
		{"popup.tab.close.command", EntityResourceConstants.CLOSE_GRAPH},

		{"popup.tab.closeAll.text", "Close All"},
		{"popup.tab.closeAll.command", EntityResourceConstants.CLOSE_ALL_GRAPHS},
		{"popup.tab.closeAll.tooltip", "Close All"},

		{"popup.tab.closeAllButThis.text", "Close All But This"},
		{"popup.tab.closeAllButThis.command", EntityResourceConstants.CLOSE_ALL_GRAPHS_BUT_THIS},
		{"popup.tab.closeAllButThis.tooltip", "Close All But This"},

		{"popup.tab.revert.text", "Revert"},
		{"popup.tab.revert.mnemonic", "r"},
		{"popup.tab.revert.command", EntityResourceConstants.REVERT_GRAPH},

		{"popup.tab.save.text", "Save"},
		{"popup.tab.save.mnemonic", "s"},
		{"popup.tab.save.icon", "images/save.gif"},
		{"popup.tab.save.command", EntityResourceConstants.SAVE_GRAPH},

		{"popup.tab.saveAs.text", "Save As..."},
		{"popup.tab.saveAs.mnemonic", "a"},
		{"popup.tab.saveAs.command", EntityResourceConstants.SAVE_GRAPH_AS},

		{"popup.tab.print.text", "Print..."},
		{"popup.tab.print.mnemonic", "p"},
		{"popup.tab.print.command", EntityResourceConstants.PRINT_GRAPH},
		{"popup.tab.print.icon", "images/print.gif"},

		{"file.new.text", "New"},
		{"file.new.mnemonic", "n"},
		{"file.new.command", EntityResourceConstants.NEW_GRAPH},
		{"file.new.icon", "images/new.gif"},
		{"file.new.tooltip", "New"},
//		{"file.new.accelerator",
//			KeyStroke.getKeyStroke(KeyEvent.VK_N, MENU_SHORTCUT_KEY_MASK)},

		{"file.open.text", "Open..."},
		{"file.open.mnemonic", "o"},
		{"file.open.command", EntityResourceConstants.LOAD_GRAPH},
		{"file.open.icon", "images/open.gif"},
		{"file.open.tooltip", "Open"},
//		{"file.open.accelerator",
//			KeyStroke.getKeyStroke(KeyEvent.VK_O, MENU_SHORTCUT_KEY_MASK)},


		{"file.import.text", "Import..."},
		{"file.import.mnemonic", "i"},
		{"file.import.command", EntityResourceConstants.IMPORT_GRAPH},

		{"file.export.text", "Export..."},
		{"file.export.mnemonic", "e"},
		{"file.export.command", EntityResourceConstants.EXPORT_GRAPH},

		{"file.close.text", "Close"},
		{"file.close.command", EntityResourceConstants.CLOSE_GRAPH},
		{"file.close.mnemonic", "c"},
		{"file.close.tooltip", "Close"},

		{"file.closeAll.text", "Close All"},
		{"file.closeAll.command", EntityResourceConstants.CLOSE_ALL_GRAPHS},
		{"file.closeAll.mnemonic", "l"},
		{"file.closeAll.tooltip", "Close All Graphs"},

		{"file.closeAllButCurrent.text", "Close All But Current"},
		{"file.closeAllButCurrent.command", EntityResourceConstants.CLOSE_ALL_GRAPHS_BUT_THIS},
		{"file.closeAllButCurrent.tooltip", "Close All Graphs But Current"},

		{"file.revert.text", "Revert"},
		{"file.revert.mnemonic", "r"},
		{"file.revert.command", EntityResourceConstants.REVERT_GRAPH},

		{"file.save.text", "Save"},
		{"file.save.notInAppletToolbar", "true"},
		{"file.save.mnemonic", "s"},
		{"file.save.command", EntityResourceConstants.SAVE_GRAPH},
		{"file.save.icon", "images/save.gif"},
		{"file.save.tooltip", "Save"},
//		{"file.save.accelerator",
//			KeyStroke.getKeyStroke(KeyEvent.VK_S, MENU_SHORTCUT_KEY_MASK)},

		{"file.saveAs.text", "Save As..."},
		{"file.saveAs.notInAppletToolbar", "true"},
		{"file.saveAs.mnemonic", "a"},
		{"file.saveAs.command", EntityResourceConstants.SAVE_GRAPH_AS},

		{"file.saveAsImage.text", "Save As Image..."},
		{"file.saveAsImage.mnemonic", "g"},
		{"file.saveAsImage.command", EntityResourceConstants.SAVE_GRAPH_AS_IMAGE},

		{"file.printSetup.text", "Print Setup..."},
		{"file.printSetup.notInAppletToolbar", "true"},
		{"file.printSetup.mnemonic", "u"},
		{"file.printSetup.command", EntityResourceConstants.PRINT_SETUP},

		{"file.printPreview.text", "Print Preview"},
		{"file.printPreview.notInAppletToolbar", "true"},
		{"file.printPreview.mnemonic", "v"},
		{"file.printPreview.command", EntityResourceConstants.PRINT_PREVIEW},
		{"file.printPreview.icon", "images/printPreview.gif"},
		{"file.printPreview.tooltip", "Print Preview"},

		{"file.print.text", "Print..."},
		{"file.print.notInAppletToolbar", "true"},
		{"file.print.mnemonic", "p"},
		{"file.print.command", EntityResourceConstants.PRINT_GRAPH},
		{"file.print.icon", "images/print.gif"},
		{"file.print.tooltip", "Print"},
//		{"file.print.accelerator",
//			KeyStroke.getKeyStroke(KeyEvent.VK_P, MENU_SHORTCUT_KEY_MASK)},

		{"file.exit.text", "Exit"},
		{"file.exit.notInAppletToolbar", "true"},
		{"file.exit.mnemonic", "x"},
		{"file.exit.command", EntityResourceConstants.APP_EXIT},

		{"edit.undo.text", "Undo"},
		{"edit.undo.mnemonic", "u"},
		{"edit.undo.icon", "images/undo.gif"},
		{"edit.undo.tooltip", "Undo"},
		{"edit.undo.command", EntityResourceConstants.UNDO},
		{"edit.undo.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_Z, MENU_SHORTCUT_KEY_MASK)},

		{"edit.redo.text", "Redo"},
		{"edit.redo.mnemonic", "r"},
		{"edit.redo.icon", "images/redo.gif"},
		{"edit.redo.tooltip", "Redo"},
		{"edit.redo.command", EntityResourceConstants.REDO},
		{"edit.redo.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_Y, MENU_SHORTCUT_KEY_MASK)},

		{"edit.cut.text", "Cut"},
		{"edit.cut.mnemonic", "t"},
		{"edit.cut.icon", "images/cut.gif"},
		{"edit.cut.tooltip", "Cut"},
		{"edit.cut.command", EntityResourceConstants.CUT_GRAPH},
		{"edit.cut.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_X, MENU_SHORTCUT_KEY_MASK)},

		{"edit.copy.text", "Copy"},
		{"edit.copy.mnemonic", "c"},
		{"edit.copy.icon", "images/copy.gif"},
		{"edit.copy.tooltip", "Copy"},
		{"edit.copy.command", EntityResourceConstants.COPY_GRAPH},
		{"edit.copy.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_C, MENU_SHORTCUT_KEY_MASK)},

		{"edit.paste.text", "Paste"},
		{"edit.paste.mnemonic", "p"},
		{"edit.paste.icon", "images/paste.gif"},
		{"edit.paste.tooltip", "Paste"},
		{"edit.paste.command", EntityResourceConstants.PASTE_GRAPH},
		{"edit.paste.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_V, MENU_SHORTCUT_KEY_MASK)},

		{"edit.duplicate.text", "Duplicate"},
		{"edit.duplicate.mnemonic", "i"},
		{"edit.duplicate.command", EntityResourceConstants.DUPLICATE_GRAPH},
		{"edit.duplicate.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_D, MENU_SHORTCUT_KEY_MASK)},

		{"edit.clearall.text", "Clear All"},
		{"edit.clearall.command", EntityResourceConstants.CLEAR_ALL},
		{"edit.clearall.mnemonic", "A"},
		{"edit.clearall.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, MENU_SHORTCUT_KEY_MASK)},

		{"edit.delete.text", "Delete"},
		{"edit.delete.mnemonic", "d"},
		{"edit.delete.icon", "images/delete.gif"},
		{"edit.delete.command", EntityResourceConstants.DELETE_SELECTED},
		{"edit.delete.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)},

		{"edit.selectAll.text", "Select All"},
		{"edit.selectAll.mnemonic", "s"},
		{"edit.selectAll.command", EntityResourceConstants.SELECT_ALL},
		{"edit.selectAll.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_A, MENU_SHORTCUT_KEY_MASK)},

		{"edit.selectAllNodes.text", "Select All Nodes"},
		{"edit.selectAllNodes.mnemonic", "N"},
		{"edit.selectAllNodes.command", EntityResourceConstants.SELECT_NODES},

		{"edit.selectAllEdges.text", "Select All Edges"},
		{"edit.selectAllEdges.mnemonic", "E"},
		{"edit.selectAllEdges.command", EntityResourceConstants.SELECT_EDGES},

		{"edit.selectAllLabels.text", "Select All Labels"},
		{"edit.selectAllLabels.mnemonic", "L"},
		{"edit.selectAllLabels.command", EntityResourceConstants.SELECT_LABELS},

		{"edit.clearHistory.text", "Clear History"},
		{"edit.clearHistory.mnemonic", "h"},
		{"edit.clearHistory.command", EntityResourceConstants.CLEAR_HISTORY},

		{"view.overviewWindow.text", "Overview"},
		{"view.overviewWindow.mnemonic", "o"},
		{"view.overviewWindow.checked", "true"},
		{"view.overviewWindow.command", EntityResourceConstants.OVERVIEW_WINDOW},

		{"view.zoom.text", "Zoom"},
		{"view.zoom.mnemonic", "z"},

		{"view.zoom.fitInCanvas.text", "Fit In Canvas"},
		{"view.zoom.fitInCanvas.mnemonic", "f"},
		{"view.zoom.fitInCanvas.command", EntityResourceConstants.FIT_IN_WINDOW},
		{"view.zoom.fitInCanvas.icon", "images/fitInCanvas.gif"},
		{"view.zoom.fitInCanvas.tooltip", "Fit In Canvas"},
		{"view.zoom.fitInCanvas.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_F, MENU_SHORTCUT_KEY_MASK)},

		{"view.zoom.autoFitInCanvas.text", "Auto Fit In Canvas"},
		{"view.zoom.autoFitInCanvas.mnemonic", "u"},
		{"view.zoom.autoFitInCanvas.checked", "false"},
		{"view.zoom.autoFitInCanvas.command", EntityResourceConstants.ZOOM_AUTO_FIT},

		{"view.zoom.400.text", "400%"},
		{"view.zoom.400.mnemonic", "4"},
		{"view.zoom.400.group", "zoom"},
		{"view.zoom.400.command", EntityResourceConstants.ZOOM + ".400"},

		{"view.zoom.200.text", "200%"},
		{"view.zoom.200.mnemonic", "0"},
		{"view.zoom.200.group", "zoom"},
		{"view.zoom.200.command", EntityResourceConstants.ZOOM + ".200"},

		{"view.zoom.100.text", "100%"},
		{"view.zoom.100.mnemonic", "1"},
		{"view.zoom.100.group", "zoom"},
		{"view.zoom.100.command", EntityResourceConstants.ZOOM + ".100"},

		{"view.zoom.50.text", "50%"},
		{"view.zoom.50.mnemonic", "5"},
		{"view.zoom.50.group", "zoom"},
		{"view.zoom.50.command", EntityResourceConstants.ZOOM + ".50"},

		{"view.zoom.25.text", "25%"},
		{"view.zoom.25.mnemonic", "2"},
		{"view.zoom.25.group", "zoom"},
		{"view.zoom.25.command", EntityResourceConstants.ZOOM + ".25"},

		{"view.zoom.customZoom.text", "Custom Zoom..."},
		{"view.zoom.customZoom.mnemonic", "z"},
		{"view.zoom.customZoom.tooltip", "Zoom"},
		{"view.zoom.customZoom.group", "zoom"},
		{"view.zoom.customZoom.command", EntityResourceConstants.ZOOM_CUSTOM},
		{"view.zoom.customZoom.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_M, MENU_SHORTCUT_KEY_MASK)},

		{"view.zoom.in.mnemonic", "i"},
		{"view.zoom.in.text", "Zoom In"},
		{"view.zoom.in.command", EntityResourceConstants.ZOOM_IN},
		{"view.zoom.in.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, MENU_SHORTCUT_KEY_MASK)},

		{"view.zoom.out.mnemonic", "o"},
		{"view.zoom.out.text", "Zoom Out"},
		{"view.zoom.out.command", EntityResourceConstants.ZOOM_OUT},
		{"view.zoom.out.accelerator",
				KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, MENU_SHORTCUT_KEY_MASK)},

		{"drawing.grid.text", "Grid"},
		{"drawing.grid.mnemonic", "g"},

		{"drawing.grid.snapToGrid.text", "Snap To Grid"},
		{"drawing.grid.snapToGrid.mnemonic", "n"},
		{"drawing.grid.snapToGrid.command", EntityResourceConstants.SNAP_TO_GRID},
		
		{"drawing.grid.gridType.text", "Grid Type"},
		{"drawing.grid.gridType.mnemonic", "t"},

		{"drawing.grid.gridType.none.text", "None"},
		{"drawing.grid.gridType.none.notInAppletToolbar", "true"},
		{"drawing.grid.gridType.none.mnemonic", "n"},
		{"drawing.grid.gridType.none.command", EntityResourceConstants.GRID_TYPE +
			".none"},
		{"drawing.grid.gridType.none.icon", "images/noGrid.gif"},
		{"drawing.grid.gridType.none.tooltip", "No Grid"},
		{"drawing.grid.gridType.none.group", "drawing.grid.gridType"},

		{"drawing.grid.gridType.lines.text", "Lines"},
		{"drawing.grid.gridType.lines.notInAppletToolbar", "true"},
		{"drawing.grid.gridType.lines.mnemonic", "l"},
		{"drawing.grid.gridType.lines.command", EntityResourceConstants.GRID_TYPE +
			".line"},
		{"drawing.grid.gridType.lines.icon", "images/lineGrid.gif"},
		{"drawing.grid.gridType.lines.tooltip", "Lines Grid"},
		{"drawing.grid.gridType.lines.group", "drawing.grid.gridType"},

		{"drawing.grid.gridType.points.text", "Points"},
		{"drawing.grid.gridType.points.notInAppletToolbar", "true"},
		{"drawing.grid.gridType.points.mnemonic", "p"},
		{"drawing.grid.gridType.points.command", EntityResourceConstants.GRID_TYPE +
			".point"},
		{"drawing.grid.gridType.points.icon", "images/tileGrid.gif"},
		{"drawing.grid.gridType.points.tooltip", "Points Grid"},
		{"drawing.grid.gridType.points.group", "drawing.grid.gridType"},

		{"drawing.grid.gridSize.text", "Grid Size"},
		{"drawing.grid.gridSize.mnemonic", "s"},

		{"drawing.grid.gridSize.10Units.text", "10 Units"},
		{"drawing.grid.gridSize.10Units.mnemonic", "1"},
		{"drawing.grid.gridSize.10Units.command",
			EntityResourceConstants.GRID_SIZE + ".10"},
		{"drawing.grid.gridSize.10Units.group", "drawing.grid.gridSize"},

		{"drawing.grid.gridSize.20Units.text", "20 Units"},
		{"drawing.grid.gridSize.20Units.mnemonic", "2"},
		{"drawing.grid.gridSize.20Units.command",
			EntityResourceConstants.GRID_SIZE + ".20"},
		{"drawing.grid.gridSize.20Units.group", "drawing.grid.gridSize"},

		{"drawing.grid.gridSize.30Units.text", "30 Units"},
		{"drawing.grid.gridSize.30Units.mnemonic", "3"},
		{"drawing.grid.gridSize.30Units.command",
			EntityResourceConstants.GRID_SIZE + ".30"},
		{"drawing.grid.gridSize.30Units.group", "drawing.grid.gridSize"},

		{"drawing.grid.gridSize.customSize.text", "Custom Size..."},
		{"drawing.grid.gridSize.customSize.mnemonic", "c"},
		{"drawing.grid.gridSize.customSize.command",
			EntityResourceConstants.GRID_SIZE_CUSTOM},
		{"drawing.grid.gridSize.customSize.group", "drawing.grid.gridSize"},

		{"drawing.dockingWindows.text", "Docking Windows"},
		{"drawing.dockingWindows.mnemonic", "d"},
		{"drawing.dockingWindows.checked", "true"},
		{"drawing.dockingWindows.command", EntityResourceConstants.DOCKABLE_WINDOWS},

		{"drawing.tabbedWindows.text", "Tabbed Windows"},
		{"drawing.tabbedWindows.checked", "true"},
		{"drawing.tabbedWindows.command", EntityResourceConstants.TABBED_WINDOWS},

		{"drawing.preferences.text", "Preferences"},
		{"drawing.preferences.mnemonic", "p"},
		{"drawing.preferences.checked", "true"},
		{"drawing.preferences.command", EntityResourceConstants.DRAWING_PREFERENCES},

		{"goToParent.text", "Go To Parent"},
		{"goToParent.mnemonic", "p"},
		{"goToParent.command", EntityResourceConstants.GOTO_PARENT},
		{"goToParent.icon", "images/goToParent.gif"},
		{"goToParent.tooltip", "Go To Parent"},

		{"runMode.text", "Run Mode"},
		{"runMode.mnemonic", "r"},
		{"runMode.checked", "true"},
		{"runMode.command", EntityResourceConstants.RUN_MODE},
		{"runMode.icon", "images/startRun.gif"},
		{"runMode.tooltip", "JComponent Run Mode"},

		{"view.objectProperties.text", "Object Properties..."},
		{"view.objectProperties.mnemonic", "b"},
		{"view.objectProperties.checked", "true"},
		{"view.objectProperties.command", EntityResourceConstants.INSPECTOR_WINDOW},

		{"tools.select.text", "Select"},
		{"tools.select.mnemonic", "s"},
		{"tools.select.group", "tools"},
		{"tools.select.icon", "images/select.gif"},
		{"tools.select.tooltip", "Select"},
		{"tools.select.command", EntityResourceConstants.SELECT_TOOL},
		{"tools.select.status", "Selecting"},

		{"tools.pan.text", "Pan"},
		{"tools.pan.mnemonic", "p"},
		{"tools.pan.group", "tools"},
		{"tools.pan.icon", "images/pan.gif"},
		{"tools.pan.tooltip", "Pan"},
		{"tools.pan.command", EntityResourceConstants.PAN_TOOL},
		{"tools.pan.status", "Panning"},

		{"tools.marqueeZoom.text", "Marquee Zoom"},
		{"tools.marqueeZoom.mnemonic", "z"},
		{"tools.marqueeZoom.command", EntityResourceConstants.ZOOM_TOOL},
		{"tools.marqueeZoom.group", "tools"},
		{"tools.marqueeZoom.icon", "images/zoom.gif"},
		{"tools.marqueeZoom.tooltip", "Marquee Zoom"},
		{"tools.marqueeZoom.status", "Zooming with marquee"},

		{"tools.interactiveZoom.text", "Interactive Zoom"},
		{"tools.interactiveZoom.mnemonic", "i"},
		{"tools.interactiveZoom.group", "tools"},
		{"tools.interactiveZoom.icon", "images/interactiveZoom.gif"},
		{"tools.interactiveZoom.tooltip", "Interactive Zoom"},
		{"tools.interactiveZoom.command", EntityResourceConstants.INTERACTIVE_ZOOM_TOOL},
		{"tools.interactiveZoom.status", "Zooming interactively"},

		{"tools.createNodes.text", "Create Nodes"},
		{"tools.createNodes.mnemonic","n"},
		{"tools.createNodes.group", "tools"},
		{"tools.createNodes.icon", "images/addNode.gif"},
		{"tools.createNodes.tooltip", "Create Nodes"},
		{"tools.createNodes.command", EntityResourceConstants.CREATE_NODE_TOOL},
		{"tools.createNodes.status", "Creating nodes"},

		{"tools.createEdges.text", "Create Edges"},
		{"tools.createEdges.mnemonic", "e"},
		{"tools.createEdges.group", "tools"},
		{"tools.createEdges.icon", "images/addEdge.gif"},
		{"tools.createEdges.tooltip", "Create Edges"},
		{"tools.createEdges.command", EntityResourceConstants.CREATE_EDGE_TOOL},
		{"tools.createEdges.status", "Creating edges"},

		{"tools.navigateEdges.text", "Navigate Edges"},
		{"tools.navigateEdges.mnemonic", "g"},
		{"tools.navigateEdges.group", "tools"},
		{"tools.navigateEdges.icon", "images/linkNavigation.gif"},
		{"tools.navigateEdges.tooltip", "Navigate Edges"},
		{"tools.navigateEdges.command",
			EntityResourceConstants.EDGE_NAVIGATION_TOOL},
		{"tools.navigateEdges.status", "Navigating edges"},
		{"layout.globalLayout.text", "Global Layout"},
		{"layout.globalLayout.mnemonic", "g"},
		{"layout.globalLayout.icon", "images/globalLayout.gif"},
		{"layout.globalLayout.command", EntityResourceConstants.APPLY_OPERATION +
			"." + TSLayoutConstants.OPERATION_LAYOUT},
		{"layout.globalLayout.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_L, MENU_SHORTCUT_KEY_MASK)},

		{"layout.incrementalLayout.text", "Incremental Layout"},
		{"layout.incrementalLayout.mnemonic", "i"},
		{"layout.incrementalLayout.icon", "images/incrementalLayout.gif"},
		{"layout.incrementalLayout.tooltip", "Incremental Layout"},
		{"layout.incrementalLayout.command",
			EntityResourceConstants.INCREMENTAL_LAYOUT},
		{"layout.incrementalLayout.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_I, MENU_SHORTCUT_KEY_MASK)},

		{"layout.circularLayout.text", "Circular Layout"},
		{"layout.circularLayout.mnemonic", "c"},
		{"layout.circularLayout.group", "layout"},
		{"layout.circularLayout.icon", "images/circular.gif"},
		{"layout.circularLayout.tooltip", "Circular Layout"},
		{"layout.circularLayout.command", EntityResourceConstants.APPLY_OPERATION +
			"." + TSLayoutConstants.OPERATION_LAYOUT +
			"." + TSLayoutConstants.LAYOUT_STYLE_CIRCULAR},

		{"layout.hierarchicalLayout.text", "Hierarchical Layout"},
		{"layout.hierarchicalLayout.mnemonic", "h"},
		{"layout.hierarchicalLayout.group", "layout"},
		{"layout.hierarchicalLayout.icon", "images/hierarchical.gif"},
		{"layout.hierarchicalLayout.tooltip", "Hierarchical Layout"},
		{"layout.hierarchicalLayout.command",
		 	EntityResourceConstants.APPLY_OPERATION +
			"." + TSLayoutConstants.OPERATION_LAYOUT +
			"." + TSLayoutConstants.LAYOUT_STYLE_HIERARCHICAL},

		{"layout.orthogonalLayout.text", "Orthogonal Layout"},
		{"layout.orthogonalLayout.mnemonic", "o"},
		{"layout.orthogonalLayout.group", "layout"},
		{"layout.orthogonalLayout.icon", "images/orthogonal.gif"},
		{"layout.orthogonalLayout.tooltip", "Orthogonal Layout"},
		{"layout.orthogonalLayout.command", EntityResourceConstants.APPLY_OPERATION +
			"." + TSLayoutConstants.OPERATION_LAYOUT +
            "." + TSLayoutConstants.LAYOUT_STYLE_ORTHOGONAL},

		{"layout.symmetricLayout.text", "Symmetric Layout"},
		{"layout.symmetricLayout.mnemonic", "s"},
		{"layout.symmetricLayout.group", "layout"},
		{"layout.symmetricLayout.icon", "images/symmetric.gif"},
		{"layout.symmetricLayout.tooltip", "Symmetric Layout"},
		{"layout.symmetricLayout.command", EntityResourceConstants.APPLY_OPERATION +
			"." + TSLayoutConstants.OPERATION_LAYOUT +
			"." + TSLayoutConstants.LAYOUT_STYLE_SYMMETRIC},


		{"layout.labeling.text", "Label Layout"},
		{"layout.labeling.mnemonic", "l"},
		{"layout.labeling.icon", "images/labeling.gif"},
		{"layout.labeling.tooltip", "Label Layout"},
		{"layout.labeling.command", EntityResourceConstants.APPLY_OPERATION +
			"." + TSLayoutConstants.OPERATION_LABELING},

		{"layout.allEdgeRouting.text", "All Edge Routing"},
		{"layout.allEdgeRouting.mnemonic", "u"},
		{"layout.allEdgeRouting.icon", "images/routing.gif"},
		{"layout.allEdgeRouting.tooltip", "All Edge Routing"},
		{"layout.allEdgeRouting.command", EntityResourceConstants.APPLY_OPERATION +
			"." + TSLayoutConstants.OPERATION_ROUTING +
			"." + EntityResourceConstants.ALL_EDGES},
		{"layout.allEdgeRouting.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_R, MENU_SHORTCUT_KEY_MASK)},

		{"layout.selectedEdgeRouting.text", "Selected Edge Routing"},
		{"layout.selectedEdgeRouting.mnemonic", "e"},
		{"layout.selectedEdgeRouting.icon", "images/selectedRouting.gif"},
		{"layout.selectedEdgeRouting.tooltip", "Selected Edge Routing"},
		{"layout.selectedEdgeRouting.command", EntityResourceConstants.APPLY_OPERATION +
			"." + TSLayoutConstants.OPERATION_ROUTING +
			"." + EntityResourceConstants.SELECTED_EDGES},
			
		{"layout.randomLayout.text", "Random Layout"},
		{"layout.randomLayout.mnemonic", "r"},
		{"layout.randomLayout.command", EntityResourceConstants.RANDOM_LAYOUT},

		{"layout.constraints.text", "Constraints..."},
		{"layout.constraints.mnemonic", "n"},
		{"layout.constraints.checked", "true"},
		{"layout.constraints.command", EntityResourceConstants.CONSTRAINT_WINDOW},
		{"layout.constraints.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_C, MENU_SHORTCUT_KEY_MASK +
				ActionEvent.SHIFT_MASK)},

		{"layout.properties.text", "Properties..."},
		{"layout.properties.mnemonic", "p"},
		{"layout.properties.command", EntityResourceConstants.LAYOUT_PROPERTIES},
		{"layout.properties.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_L, MENU_SHORTCUT_KEY_MASK +
				ActionEvent.SHIFT_MASK)},
		{"layout.properties.checked", "true"},

		{"complexity.expandSelected.text", "Expand Selected"},
		{"complexity.expandSelected.mnemonic", "x"},
		{"complexity.expandSelected.command", EntityResourceConstants.EXPAND_SELECTED},

		{"complexity.collapseSelected.text", "Collapse Selected"},
		{"complexity.collapseSelected.mnemonic", "o"},
		{"complexity.collapseSelected.command",
			EntityResourceConstants.COLLAPSE_SELECTED},

		{"complexity.expandAll.text", "Expand All"},
		{"complexity.expandAll.mnemonic", "e"},
		{"complexity.expandAll.command", EntityResourceConstants.EXPAND_ALL},

		{"complexity.collapseAll.text", "Collapse All"},
		{"complexity.collapseAll.mnemonic", "c"},
		{"complexity.collapseAll.command", EntityResourceConstants.COLLAPSE_ALL},

		{"complexity.hideSelected.text", "Hide Selected"},
		{"complexity.hideSelected.mnemonic", "h"},
		{"complexity.hideSelected.tooltip", "Hide Selected"},
		{"complexity.hideSelected.command", EntityResourceConstants.HIDE_SELECTED},
		{"complexity.hideSelected.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_H, MENU_SHORTCUT_KEY_MASK)},

		{"complexity.unhideAll.text", "Unhide All"},
		{"complexity.unhideAll.mnemonic", "u"},
		{"complexity.unhideAll.tooltip", "Unhide All"},
		{"complexity.unhideAll.command", EntityResourceConstants.UNHIDE_ALL},
		{"complexity.unhideAll.accelerator",
			KeyStroke.getKeyStroke(KeyEvent.VK_U, MENU_SHORTCUT_KEY_MASK)},

		{"complexity.foldSelected.text", "Fold Selected"},
		{"complexity.foldSelected.mnemonic", "f"},
		{"complexity.foldSelected.tooltip", "Fold Selected"},
		{"complexity.foldSelected.command", EntityResourceConstants.FOLD_SELECTED},

		{"complexity.unfoldSelected.text", "Unfold Selected"},
		{"complexity.unfoldSelected.mnemonic", "n"},
		{"complexity.unfoldSelected.tooltip", "Unfold Selected"},
		{"complexity.unfoldSelected.command", EntityResourceConstants.UNFOLD_SELECTED},

		{"complexity.unfoldAll.text", "Unfold All"},
		{"complexity.unfoldAll.mnemonic", "a"},
		{"complexity.unfoldAll.tooltip", "Unfold All"},
		{"complexity.unfoldAll.command", EntityResourceConstants.UNFOLD_ALL},

// ----------------------------------------------------------------------
// Section: Application resources
//
// The look and feel, the title of the frame, application icon and
// the text of the about box
// ---------------------------------------------------------------------

		// Change this to start a different type of layout server

		{"app.title", "Tom Sawyer Visualization Application"
        },
		{"app.lookAndFeel", EntityResourceConstants.SYSTEM},
		{"app.window.width", "900"},
		{"app.window.height", "710"},

		{"canvas.default.width", "700"},
		{"canvas.default.height", "500"},

		{"app.undo.limit", "50"},
		{"app.icon", "images/appicon16x16.gif"},
		{"app.dialogIcon", "images/dialogIcon.gif"},
		{"app.doc.icon", "images/document.gif"},
		{"applet.window.width", "800"},
		{"applet.window.height", "530"},
		{"applet.info", "Tom Sawyer Visualization"
		    + " for Java\n" +
			"Version " + EntityResourceConstants.TSVJ_VERSION_PLACEHOLDER + "\n" +
			"Copyright \u00A9 1992 - 2006\n" +
			"Tom Sawyer Software\n" +
			"All Rights Reserved."},

		{"app.mdi.enabled", "true"},
		{"app.mdi.style.tabbed", "true"},

        {"service.server.type", TSServiceProxy.DIRECT},
        {"service.server.soapLocation", "http://localhost:8080/soap/servlet/rpcrouter"},
        {"service.server.servletLocation",  "http://localhost:8080/tomsawyer/servlet/ServiceServer"},
		{"service.server.rmiLocation",  "//localhost:1099/ServiceServer"},

		{"dialog.zoom.title", "Custom Zoom"},
		{"dialog.zoom.message", "Zoom Level:"},

		{"dialog.zoomError.title", "Custom Zoom Error"},
		{"dialog.zoomError.message",
			"Please enter a number between " +
				EntityResourceConstants.X_PLACEHOLDER +
			                " and " + EntityResourceConstants.Y_PLACEHOLDER + "."},

		{"dialog.pasteError.title", "Paste Error"},
		{"dialog.pasteError.message",
			"Clipboard does not contain valid graph data."},

		{"dialog.gridSize.title", "Custom Grid Size"},
		{"dialog.gridSize.message", "Grid Size:"},

		{"dialog.gridSizeError.title", "Custom Grid Size Error"},
		{"dialog.gridSizeError.message",
			"Please enter an number greater than zero."},

		{"dialog.open.title", "Open"},
		{"dialog.open.message", "Specify the URL of the diagram:"},
		{"dialog.open.icon", "images/open.gif"},

        {"dialog.import.title", "Import"},
        {"dialog.export.title", "Export"},

        {"dialog.import.title", "Import"},
        {"dialog.import.message", "Specify the URL of the diagram:"},
        {"dialog.import.icon", "images/open.gif"},

		{"dialog.openError.fileNotFound.title", "File not Found"},
		{"dialog.openError.fileNotFound.message",
			"The file " + EntityResourceConstants.FILENAME_PLACEHOLDER +
				" could not be found.\n" +
			"Please verify that the correct path was given."},

		{"dialog.openError.general.title", "File Open Error"},
		{"dialog.openError.general.message",
			"The file " + EntityResourceConstants.FILENAME_PLACEHOLDER +
				" could not be opened.\n" +
			"Please verify that the file is compatible " +
			"with this version of Tom Sawyer Visualization, Java Edition."},

		{"dialog.saveAs.title", "Save As"},

		{"dialog.saveAsImage.title", "Save As Image"},

		{"dialog.saveConfirm.title", "Save Confirmation"},
		{"dialog.saveConfirm.message",
			"Save changes to " + EntityResourceConstants.FILENAME_PLACEHOLDER + "?"},
		{"dialog.saveConfirm.yesToAll", "Yes To All"},
		{"dialog.saveConfirm.yes", "Yes"},
		{"dialog.saveConfirm.noToAll", "No To All"},
		{"dialog.saveConfirm.no", "No"},
		{"dialog.saveConfirm.cancel", "Cancel"},

		{"dialog.discardConfirm.title", "Discard Confirmation"},
		{"dialog.discardConfirm.message",
			"Discard changes to " +
				EntityResourceConstants.FILENAME_PLACEHOLDER + "?"},

		{"dialog.overWriteConfirm.title", "Overwrite Confirmation"},
		{"dialog.overWriteConfirm.message",
			EntityResourceConstants.FILENAME_PLACEHOLDER + " already exists.\n" +
			"Do you want to replace it?"},

		{"dialog.saveError.pathNotFound.title", "File Save Error"},
		{"dialog.saveError.pathNotFound.message",
			"The file " + EntityResourceConstants.FILENAME_PLACEHOLDER +
				" could not be saved.\n" +
			"Please verify that the correct path was given."},

		{"dialog.saveError.title", "File Save Error"},
		{"dialog.saveError.message",
			"The file " + EntityResourceConstants.FILENAME_PLACEHOLDER +
				" could not be saved."},

		{"dialog.revert.title", "Revert Confirmation"},
		{"dialog.revert.message",
			"Do you really want to revert to the previously\nsaved " +
				"version of this drawing?"},

		{"dialog.undoError.title", "Undo Error"},
		{"dialog.undoError.message", "Undo operation not available."},

		{"dialog.redoError.title", "Redo Error"},
		{"dialog.redoError.message", "Redo operation not available."},

		{"dialog.clearHistory.title", "Clear History Confirmation"},
		{"dialog.clearHistory.message",
			"Are you sure you want to clear the undo/redo history?"},

		{"dialog.layoutError.title", "Layout Error"},
		{"dialog.layoutError.message", "Error occurred during layout."},

		{"dialog.objectProperties.title", "Object Properties"},

		{"dialog.overviewWindow.title", "Overview"},

		{"dialog.nodePalette.title", "Node Palette"},

		{"dialog.printSetup.title", "Print Setup"},

		{"dialog.printPreview.title", "Print Preview"},

		{"dialog.layoutProperties.title", "Layout Properties"},
		{"dialog.layoutProperties.defaultFile", "untitled"},
		{"dialog.constraints.title", "Layout Constraints"},
		{"dialog.drawingPreferences.title", "Display and Drawing Preferences"},
		{"dialog.about.title", "About Tom Sawyer Visualization"
            + " Application"},
		{"dialog.about.icon", "images/tomicon.gif"},
		{"dialog.about.message",
			"This demonstration illustrates the\n" +
			"component technology of\n" +
			" \n" +
			"Tom Sawyer Visualization\n" +
			"Version " + EntityResourceConstants.TSVJ_MAJOR_VERSION_PLACEHOLDER + "\n" +
			"Java Edition\n" +
			" \n" +
			"Build " + EntityResourceConstants.TSVJ_VERSION_PLACEHOLDER + "\n" +
			" \n" +

			EntityResourceConstants.TSVJ_TRACK_PLACEHOLDER + " Track, " +
			EntityResourceConstants.TSVJ_TIER_PLACEHOLDER + " Tier" + "\n" +

			// You may choose to display more information about your
			// company or available features

			// TreeResourceConstants.LAYOUT_SERVER_TYPE_PLACEHOLDER + "\n" +
			// "Installed Layout Styles: " +
			// 	TreeResourceConstants.LAYOUT_STYLES_PLACEHOLDER + "\n" +
			"Java VM Version " + EntityResourceConstants.JAVA_VERSION_PLACEHOLDER +
			" \n" +
			" \n" +
			"Copyright \u00A9 1992 - 2006\n" +
			"Tom Sawyer Software\n" +
			"All rights reserved.\n" +
			" \n" +
			"www.tomsawyer.com"},

		{"dialog.licensebaseerror.title", "License Check Failed"},
		{"dialog.licensebaseerror.message",
			"Unable to validate license from Java. " +
			"The application will terminate.\n" +
			"For assistance, please contact Tom Sawyer Software " +
			"by phone\n" + "at (510) 208-4379 or by e-mail " +
			"at support@tomsawyer.com."},

		{"dialog.licenseerror.title", "License Feature Unavailable"},
		{"dialog.licenseerror.message",
			"The feature you are attempting to use is not" +
			"\n" + "available with the currently installed licenses. " + "\n" +
			"To upgrade your license and access additional " + "\n" +
			"product features, visit www.tomsawyer.com."},

		{"file.all.extension", "*"},
		{"file.xml.extension", "tsv"},
		{"file.xmlCompressed.extension", "tsvz"},

        {"file.analysis.extension", "tsa"},
        {"file.analysisCompressed.extension", "tsaz"},
        {"file.layout.extension", "tsl"},
        {"file.layoutCompressed.extension", "tslz"},
        {"file.old.extension", "gmf"},
        {"file.oldCompressed.extension", "gmz"},

		{"file.all.description", "All Files (*.*)"},
		{"file.xml.description", "Tom Sawyer Visualization File (*.tsv)"},
		{"file.xmlCompressed.description",
			"Tom Sawyer Visualization Compressed File (*.tsvz)"},

	 	{"file.analysis.description", "Tom Sawyer Analysis File (*.tsa)"},
        {"file.analysisCompressed.description",
        	"Tom Sawyer Analysis Compressed File (*.tsaz)"},
        {"file.layout.description", "Tom Sawyer Layout File (*.tsl)"},
        {"file.layoutCompressed.description",
        	"Tom Sawyer Layout Compressed File (*.tslz)"},
        {"file.old.description", "Tom Sawyer Visualization File (*.gmf)"},
        {"file.oldCompressed.description",
        	"Tom Sawyer Visualization Compressed File (*.gmz)"},
        {"file.noname", "untitled"},


// ---------------------------------------------------------------------
// Section: Strings
//
// This section specifies string resources used within the example
// comprehensive.  Replace the 'value' strings with translations for
// internationalization.
// ---------------------------------------------------------------------

// generic strings
		{"string.OK", "OK"},
		{"string.Cancel", "Cancel"},
		{"string.Help", "Help"},

		{"string.Edge", "Edge"},

		{"string.Above", "Above"},
		{"string.Below", "Below"},
		{"string.Top", "Top"},
		{"string.Bottom", "Bottom"},
		{"string.Left", "Left"},
		{"string.Right", "Right"},
		{"string.Center", "Center"},
		{"string.Top_Or_Bottom", "Top Or Bottom"},
		{"string.Left_Or_Right", "Left Or Right"},
		{"string.Over", "Over"},
		{"string.Dont_Care", "Don\'t Care"},
		{"string.Any", "Any"},

		{"string.Untitled", "untitled"},

		{"string.Off", "Off"},

// TSENode strings
		{"string.Embedded", "Embedded"},
		{"string.Attachment_Side", "Attachment Side"},
		{"string.Fix_Size_When_Routing", "Fix Size When Routing"},
		{"string.Fix_Position_When_Routing", "Fix Position When Routing"},
		{"string.End_Color", "End Color"},
		{"string.Desired_Group_ID", "Desired Group ID"},
		{"string.Actual_Group_ID", "Actual Group ID"},
		{"string.Left_Ports", "Left Ports"},
		{"string.Right_Ports", "Right Ports"},
		{"string.Top_Ports", "Top Ports"},
		{"string.Bottom_Ports", "Bottom Ports"},
		{"string.Invertible", "Invertible"},
		{"string.Stretchable", "Stretchable"},
		{"string.Resize_Style", "Resize Style"},
		{"string.Desired_Root_Node", "Desired Root Node"},
		{"string.Is_Root_Node", "Is Root Node"},
		{"string.Horizontally_Stretchable", "Horizontally Stretchable"},
		{"string.Vertically_Stretchable", "Vertically Stretchable"},
		{"string.Preserve_Aspect_Ratio", "Preserve Aspect Ratio"},
		{"string.Force_Preserve_Aspect", "Force Preserve Aspect"},
		{"string.Desired_Level_Number", "Desired Level Number"},
		{"string.Actual_Level_Number", "Actual Level Number"},

// TSEEdge strings
		{"string.Source_Attachment_Side", "Source Attachment Side"},
		{"string.Target_Attachment_Side", "Target Attachment Side"},
		{"string.Source_Port_Number", "Source Port Number"},
		{"string.Source_Port_Style", "Source Port Style"},
		{"string.Target_Port_Number", "Target Port Number"},
		{"string.Target_Port_Style", "Target Port Style"},
		{"string.Non_Leveling", "Non Leveling"},
		{"string.Desired_Tree_Edge", "Desired Tree Edge"},
		{"string.Is_Tree_Edge", "Is Tree Edge"},
		{"string.None", "None"},
		{"string.Top_or_Bottom", "Top or Bottom"},
		{"string.Left_or_Right", "Left or Right"},
        {"string.Desired_Length", "Desired Length"},
        {"string.Node_Weight", "Node Weight"},
        {"string.Edge_Cost", "Edge Cost"},
        {"string.Source", "Source"},
        {"string.Target", "Target"},
        {"string.Association", "Association"},

// ExLabel strings
		{"string.Inside", "Inside"},
		{"string.Outside", "Outside"},
		{"string.Global", "Global"},
		{"string.Orientation", "Orientation"},
		{"string.Region", "Region"},
		{"string.Location", "Location"},
		{"string.Fixed", "Fixed"},

//ExJTableNodeUI strings
		{"string.Auto_Resize_Mode", "Auto Resize Mode"},
		{"string.Next_Column", "Next Column"},
		{"string.Subsequent_Columns", "Subsequent Columns"},
		{"string.Last_Column", "Last Column"},
		{"string.All_Columns", "All Columns"},
		{"string.Scrollbar_Size", "Scrollbar Size"},
		{"string.Number_of_Rows", "Number of Rows"},
		{"string.Number_of_Columns", "Number of Columns"},
		{"string.Column_" + EntityResourceConstants.X_PLACEHOLDER + "_Name",
			"Column " + EntityResourceConstants.X_PLACEHOLDER + " Name"},

//ExJComboBoxNodeUI strings
		{"string.Number_of_Options", "Number of Options"},
		{"string.Option_" + EntityResourceConstants.X_PLACEHOLDER,
			"Option " + EntityResourceConstants.X_PLACEHOLDER},
	};
}
