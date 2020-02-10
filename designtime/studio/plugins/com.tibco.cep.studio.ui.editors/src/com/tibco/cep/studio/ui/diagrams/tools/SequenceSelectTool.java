package com.tibco.cep.studio.ui.diagrams.tools;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.openEditor;

import java.awt.Point;

import javax.swing.JPopupMenu;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.SelectToolHandler;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.ChannelFormEditorInput;
import com.tibco.cep.studio.ui.diagrams.EntityDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EntityNodeData;
import com.tibco.cep.studio.ui.editors.RuleFormEditor;
import com.tibco.cep.studio.ui.editors.RuleFormEditorInput;
import com.tibco.cep.studio.ui.editors.RuleFunctionFormEditor;
import com.tibco.cep.studio.ui.editors.RuleFunctionFormEditorInput;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditorInput;
import com.tibco.cep.studio.ui.editors.events.EventFormEditor;
import com.tibco.cep.studio.ui.editors.events.EventFormEditorInput;
import com.tibco.cep.studio.ui.editors.events.TimeEventFormEditor;
import com.tibco.cep.studio.ui.editors.events.TimeEventFormEditorInput;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * @author hnembhwa
 *
 */

public class SequenceSelectTool extends EntitySelectTool {

	public static SequenceSelectTool tool;
	private Object userObject;
	private IProject project;
	
	protected SequenceDiagramPopupMenuController sequenceDiagramPopupMenuController;
	
	public SequenceSelectTool(DiagramManager diagramManager) {
		super(diagramManager);
		tool = this;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#showPopup(java.lang.String, java.awt.Point)
	 */
	public void showPopup(String menuName, Point point) {
		JPopupMenu menu = (JPopupMenu) SequenceSelectTool.menus.get(menuName);
		if (menu != null) {
			this.setPopupState(menu);
			menu.show(this.getSwingCanvas(), point.x, point.y);
		}
		this.setActiveMenu(menuName);
	}
	
	protected ContextMenuController getPopupMenuController() {
		if (this.sequenceDiagramPopupMenuController == null) {
			this.sequenceDiagramPopupMenuController = new SequenceDiagramPopupMenuController();
		}
		return (this.sequenceDiagramPopupMenuController);
	}
	
	protected void handleNodeDoubleClick(TSENode node) {
			openFormEditor(node);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#editNode()
	 */
	protected void editNode() {
		TSEObject object = SelectToolHandler.tseObject;
		if(object instanceof TSENode){
			TSENode selectedNode = (TSENode)object;
			openFormEditor(selectedNode);
		}
	}
	
	public void openFormEditor(TSENode node) {
		try {
			userObject = node.getUserObject();
			EntityDiagramManager entityDiagramManager = (EntityDiagramManager) diagramManager;
			project = entityDiagramManager.getProject();
				if (userObject instanceof RuleElement) {
					RuleElement ruleElement = (RuleElement)userObject;
					ELEMENT_TYPES elementType = ruleElement.getElementType();
					if(elementType.equals(ELEMENT_TYPES.RULE)){
						Rule rule = (Rule) ruleElement.getRule();
						openEditor(page, new RuleFormEditorInput(project.getFile(rule.getFullPath() + ".rule")), RuleFormEditor.ID);
					}else if(elementType.equals(ELEMENT_TYPES.RULE_FUNCTION)) {
						RuleFunction ruleFunction = (RuleFunction) ruleElement.getRule();
						openEditor(page, new RuleFunctionFormEditorInput(project.getFile(ruleFunction.getFullPath() + ".rulefunction")), RuleFunctionFormEditor.ID);
					}
				} else if(userObject instanceof EntityNodeData){
					EntityNodeData nodeData = (EntityNodeData) userObject;
					Entity entity = (Entity) nodeData.getEntity().getUserObject();
					String path = entity.getFullPath();
						if (entity instanceof Concept) {
							openEditor(page, new ConceptFormEditorInput(project.getFile(path + ".concept"), (Concept) entity), ConceptFormEditor.ID);
						} else if (entity instanceof SimpleEvent){
							openEditor(page, new EventFormEditorInput(project.getFile(path + ".event"), (SimpleEvent) entity), EventFormEditor.ID);
						} else if(entity instanceof TimeEvent){
							openEditor(page, new TimeEventFormEditorInput(project.getFile(path + "."+CommonIndexUtils.TIME_EXTENSION), (TimeEvent) entity), TimeEventFormEditor.ID);
						}
				} else if(userObject instanceof Rule) {
					Rule rule = (Rule) userObject;
					openEditor(page, new RuleFormEditorInput( project.getFile(rule.getFullPath() + ".rule")), RuleFormEditor.ID);
				} else if (userObject instanceof Channel) {
					Channel channelData = (Channel) userObject;
					openEditor(page, new ChannelFormEditorInput( project.getFile(channelData.getFolder()
							+ channelData.getName() + ".channel"), channelData), ChannelFormEditor.ID);
				} else if (userObject instanceof RuleFunction) {
					RuleFunction ruleFunction = (RuleFunction) userObject;
					openEditor(page, new RuleFunctionFormEditorInput( project.getFile(ruleFunction.getFullPath() + ".rulefunction")), RuleFunctionFormEditor.ID);
				} else {
					openErrorMessage();
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static SequenceSelectTool getTool() {
		return tool;
	}
	
	private void openErrorMessage() {
		Thread currentThread = Thread.currentThread();
		Display display = PlatformUI.getWorkbench().getDisplay();
		final Shell shell = page.getWorkbenchWindow().getShell();
		if(currentThread == display.getThread()){
			MessageDialog.openError(shell, Messages.getString("Sequence_Diagram_Error"), Messages.getString("Sequence_Diagram_Error_Message"));
		} else {
			display.asyncExec(new Runnable(){
				public void run() {
					MessageDialog.openError(shell, Messages.getString("Sequence_Diagram_Error"), Messages.getString("Sequence_Diagram_Error_Message"));
				}
			});
		}
	}
}
