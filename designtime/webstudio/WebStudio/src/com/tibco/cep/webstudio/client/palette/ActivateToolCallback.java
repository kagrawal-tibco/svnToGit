package com.tibco.cep.webstudio.client.palette;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.command.TSServiceGroupCommand;
import com.tomsawyer.web.client.command.TSViewActivateToolActionItemCommand;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;
import com.tomsawyer.web.client.view.behavior.data.TSWebActivateToolActionItemData;

/**
 * 
 * @author sasahoo
 * 
 */
public class ActivateToolCallback implements
		com.smartgwt.client.widgets.events.ClickHandler {

	private String toolID;
	private String toolType;
	private String elementType;
	private String emfType;
	private String extnType = "";
	private String itemId;
	protected ProcessMessages processMessages = (ProcessMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.PROCESS_MESSAGES);
	/**
	 * @param toolID
	 * @param toolType
	 * @param deviceType
	 * @param emfType
	 * @param extnType
	 */
	public ActivateToolCallback(String toolID, String toolType,
			String deviceType, String emfType, String extnType) {
		this.toolID = toolID;
		this.toolType = toolType;
		this.elementType = deviceType;
		this.emfType = emfType;
		this.extnType = extnType;
	}

	@Override
	public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
		if (!("activity.javatask".equals(itemId) || "activity.manual".equals(itemId) || "activity.subProcess"
				.equals(itemId))) {
			AbstractEditor abstractEditor = (AbstractEditor) WebStudio.get()
					.getEditorPanel().getSelectedTab();
			if (abstractEditor instanceof AbstractProcessEditor) {

				String drawingviewId = ((AbstractProcessEditor) abstractEditor)
						.getDrawingViewID();

				TSCustomCommand setModelElementCommand = null;
				String modelId = ((AbstractProcessEditor) abstractEditor).getModelId();
				if (this.elementType != null & this.emfType != null) {
					setModelElementCommand = new TSCustomCommand();
					setModelElementCommand.setProjectID(projectID);
					setModelElementCommand
							.setServerClassName("com.tibco.cep.webstudio.server.command.SetActiveToolCommandImpl");
					setModelElementCommand.setModelID(modelId);
					setModelElementCommand.setModuleName(moduleName);
					setModelElementCommand.setViewID(drawingviewId);
					setModelElementCommand.setViewName(drawingViewName);
					List<String> list = new ArrayList<String>();
					list.add(this.emfType);
					list.add(this.extnType);
					list.add(this.elementType);
					list.add(((AbstractProcessEditor) abstractEditor).getProcessName());
					list.add(((AbstractProcessEditor) abstractEditor).getProcessArtifactPath());
					setModelElementCommand.setCustomArgs(list/*
															 * Collections.
															 * singletonList
															 * (this.deviceType)
															 */);
				}

				TSWebActivateToolActionItemData data = new TSWebActivateToolActionItemData();
				data.setActivateToolID(this.toolID);
				data.setActivateToolOnly(true);
				data.setInvokingLayout(false);
				data.setActivateToolType(this.toolType);
				data.setActionName("Tool.Activate Tool");
				data.setClientSideCommand(false);
				TSViewActivateToolActionItemCommand switchToolCommand = new TSViewActivateToolActionItemCommand();
				switchToolCommand.setProjectID(projectID);
				switchToolCommand.setModuleName(moduleName);
				switchToolCommand.setModelID(modelId);
				switchToolCommand.setViewID(drawingviewId);
				switchToolCommand.setViewName(drawingViewName);
				switchToolCommand.setActivateToolID(this.toolID);
				switchToolCommand.setActivateToolType(this.toolType);
				switchToolCommand.setActivateToolOnly(true);
				switchToolCommand.setActionItemData(data);

				TSServiceGroupCommand groupCommand = new TSServiceGroupCommand();

				if (setModelElementCommand != null) {
					groupCommand.add(setModelElementCommand);
				}

				groupCommand.add(switchToolCommand);

				TSWebModelViewCoordinators.get(modelId)
						.invokeCommandAndUpdateAll(groupCommand);
			}
		} else {
			CustomSC.say(processMessages.processUnderImpl());
		}
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}