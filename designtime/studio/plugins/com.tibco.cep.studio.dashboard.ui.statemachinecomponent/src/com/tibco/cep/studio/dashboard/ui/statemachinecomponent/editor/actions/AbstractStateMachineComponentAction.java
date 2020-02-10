package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.actions;

import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractDBFormEditor;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.StateMachineComponentEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSENode;

@SuppressWarnings("serial")
public abstract class AbstractStateMachineComponentAction extends LocalElementAbstractAction {

	protected static final Icon TRANSPARENT_ICON = new ImageIcon(DashboardStateMachineComponentPlugin.getInstance().getBundle().getEntry("/icons/transparent_box_16x16.png"));

	public AbstractStateMachineComponentAction() {
		super();
	}

	public AbstractStateMachineComponentAction(String name, Icon icon) {
		super(name, icon);
	}

	public AbstractStateMachineComponentAction(String name) {
		super(name);
	}

	@Override
	public LocalElement computeTargetElement(AbstractDBFormEditor editor) {
		if (editor instanceof StateMachineComponentEditor){
			StateMachineComponentEditor stateMachineComponentEditor = (StateMachineComponentEditor) editor;
			StateMachineDiagramManager diagramManager = (StateMachineDiagramManager) stateMachineComponentEditor.getDiagramManager();
			List<TSENode> selectedNodes = diagramManager.getSelectedNodes();
			if (selectedNodes.isEmpty() == false){
				Object userObject = selectedNodes.get(0).getUserObject();
				if (userObject instanceof State){
					try {
						return StateMachineComponentHelper.getStateVisualization((LocalStateMachineComponent) stateMachineComponentEditor.getLocalElement(), new LocalExternalReference((State)userObject));
					} catch (Exception e) {
						String message = "could not find state visualization of " + ((State)userObject).getFullPath() + " in " + editor.getLocalElement()+ " for "+ this.getValue(NAME) + " action";
						DashboardStateMachineComponentPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e));
					}
				}
				return null;
			}
			return editor.getLocalElement();
		}
		return null;
	}

	@Override
	public final boolean isEnabled() {
		LocalElement targetElement = getTargetElement();
		if (targetElement == null){
			return false;
		}
		try {
			if (targetElement.getParticleNames(true).contains(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE) == true){
				if (targetElement.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE) == null) {
					return false;
				}
			}
			else if (targetElement.getParticleNames(true).contains(LocalStateVisualization.ELEMENT_KEY_STATE) == true){
				if (targetElement.getElement(LocalStateVisualization.ELEMENT_KEY_STATE) == null) {
					return false;
				}
			}
			return doIsEnabled(targetElement);
		} catch (Exception e) {
			String message = "could not query " + targetElement + " for enabling/disabling " + this.getValue(NAME) + " action";
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e));
			return false;
		}
	}

	protected abstract boolean doIsEnabled(LocalElement targetElement) throws Exception;

	protected void logError(String message,Exception ex){
		DashboardStateMachineComponentPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, ex));
	}

	protected void logWarning(String message,Exception ex){
		DashboardStateMachineComponentPlugin.getInstance().getLog().log(new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, ex));
	}

	protected int openWarningDialog(String message){
		MessageDialog messageDialog = new MessageDialog(Display.getCurrent().getActiveShell(),(String)this.getValue(NAME),null,message,MessageDialog.WARNING,new String[]{"Yes","No"},1);
		return messageDialog.open();
	}

	protected void openErrorDialog(String message){
		MessageDialog.openError(Display.getCurrent().getActiveShell(),(String)this.getValue(NAME),message);
	}

	protected void logWarningAndAlert(String message, Exception ex){
		logWarning(message, ex);
		openErrorDialog(message);
	}

	protected void logErrorAndAlert(String message, Exception ex){
		logError(message, ex);
		openErrorDialog(message);
	}

	protected void refresh(LocalElement targetElement) {
		StudioUIUtils.setWorkbenchSelection(null, getActiveEditor());
		StudioUIUtils.setWorkbenchSelection(targetElement, getActiveEditor());
	}
}
