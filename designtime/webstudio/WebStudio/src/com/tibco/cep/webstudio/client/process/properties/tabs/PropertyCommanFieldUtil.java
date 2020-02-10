package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.properties.BusinessTaskProperty;
import com.tibco.cep.webstudio.client.process.properties.CallActivityProperty;
import com.tibco.cep.webstudio.client.process.properties.ErrorEndEventProperty;
import com.tibco.cep.webstudio.client.process.properties.ExclusiveGatewayProperty;
import com.tibco.cep.webstudio.client.process.properties.InferenceTaskProperty;
import com.tibco.cep.webstudio.client.process.properties.MessageEndEventProperty;
import com.tibco.cep.webstudio.client.process.properties.MessageStartEventProperty;
import com.tibco.cep.webstudio.client.process.properties.ParallelGatewayProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.ReceiveTaskProperty;
import com.tibco.cep.webstudio.client.process.properties.RuleFunctionTaskProperty;
import com.tibco.cep.webstudio.client.process.properties.SendTaskProperty;
import com.tibco.cep.webstudio.client.process.properties.SignalEndEventProperty;
import com.tibco.cep.webstudio.client.process.properties.SignalStartEventProperty;
import com.tibco.cep.webstudio.client.process.properties.TimerStartEventProperty;
import com.tibco.cep.webstudio.client.process.properties.WebserviceProperty;

/**
 * This class is sued to create the property field common in most of the
 * node/edge.
 * 
 * @author dijadhav
 * 
 */
public class PropertyCommanFieldUtil {
	/**
	 * This method is use to create the resource text item with blur handler.
	 * 
	 * @param value
	 *            - Value of the field
	 * @param title
	 *            - Title of the field
	 * @param blurHandler
	 *            - Field blur handler.
	 * @param name
	 *            Name of the TextItem
	 * @return - TextItem.
	 */
	public static TextItem getResourceItem(String value, String title,
			BlurHandler blurHandler, String name) {
		TextItem resourceTextItem = getTextItem(value, title, name);
		resourceTextItem.addBlurHandler(blurHandler);
		resourceTextItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
						.get().getEditorPanel().getSelectedTab();
				if (!processEditor.isDirty()) {
					processEditor.makeDirty();
				}
			}
		});
		return resourceTextItem;
	}

	private static TextItem getTextItem(String value, String title, String name) {
		TextItem resourceTextItem = new TextItem(name, title);
		resourceTextItem.setWidth(400);
		resourceTextItem.setVisible(true);
		resourceTextItem.setTitle(title);
		resourceTextItem.setCanEdit(true);
		resourceTextItem.setValue(value);
		return resourceTextItem;
	}

	/**
	 * This method is used to get the checkpoint item.
	 * 
	 * @param value
	 *            - Value of the field
	 * @param title
	 *            - Title of the field
	 * @param name
	 *            - Name of the checkpoint item.
	 * @return - CheckboxItem.
	 */
	public static CheckboxItem getCheckpointItem(boolean value, String title,
			ChangedHandler changeHandler) {
		CheckboxItem checkpointItem = new CheckboxItem(ProcessConstants.CHECKPOINT, title);
		checkpointItem.setCanEdit(true);
		checkpointItem.setWidth(150);
		checkpointItem.setVisible(true);
		checkpointItem.setLabelAsTitle(true);
		checkpointItem.setShowTitle(true);
		checkpointItem.setValue(value);
		checkpointItem.addChangedHandler(changeHandler);
		return checkpointItem;
	}

	/**
	 * This method is use to create the resource text item with change handler.
	 * 
	 * @param value
	 *            - Value of the field
	 * @param title
	 *            - Title of the field
	 * @param changeHandler
	 *            - Field value change handler.
	 * @param name
	 *            Name of the TextItem
	 * @return - TextItem.
	 */
	public static TextItem getResourceTextItem(String value, String title,
			ChangedHandler changedHandler, String name) {

		TextItem resourceTextItem = getTextItem(value, title, name);
		resourceTextItem.addChangedHandler(changedHandler);
		return resourceTextItem;
	}

	public static SectionStack getHelpContainer(Property property,
			ProcessMessages processMessages) {
		SectionStack sectionStack = new SectionStack();
		sectionStack.setHeight100();
		sectionStack.setBackgroundColor("white");
		DynamicForm helpForm = new DynamicForm();
		helpForm.setTop(20);
		helpForm.setLeft(0);
		helpForm.setPosition(Positioning.ABSOLUTE);
		helpForm.setWidth("55%");
		helpForm.setHeight100();
		sectionStack.setOverflow(Overflow.SCROLL);
		SectionStackSection section = new SectionStackSection();
		section.setTitle("<b>" + processMessages.processHelpTitle() + "</b>");
		section.setCanCollapse(false);		
		StaticTextItem item = new StaticTextItem("");
		item.setWidth(100);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			helpForm.setLeft(200);
		}
		item.setWrap(true);
		item.setValue(getHelpContent(property, processMessages));
		helpForm.setFields(item);
		sectionStack.addChild(helpForm);
		section.addItem(helpForm);
		sectionStack.addSection(section);
		return sectionStack;
	}

	private static String getHelpContent(Property property,
			ProcessMessages processMessages) {
		StringBuilder helpContentBuilder = new StringBuilder(
				"<ul style=\"color:blue;width:100%; left: 0;top:0;position: absolute;\">");
		helpContentBuilder.append("<li><b>");
		helpContentBuilder.append(processMessages
				.processPropertyTabGeneralName());
		helpContentBuilder.append("</b><p>");
		helpContentBuilder.append(processMessages.processComponentNameHelp());
		helpContentBuilder.append("</p></li>");
		helpContentBuilder.append("<li><b>");
		helpContentBuilder.append(processMessages
				.processPropertyTabGeneralLabel());
		helpContentBuilder.append("</b><p>");
		helpContentBuilder.append(processMessages.processComponentLabelHelp());
		helpContentBuilder.append("</p></li>");
		if (property instanceof RuleFunctionTaskProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralCheckPoint());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processComponentCheckPointHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processScriptTaskResourceHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof BusinessTaskProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralCheckPoint());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processComponentCheckPointHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processBusinessRuleResourceHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralImplURI());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processBusinessRuleImplURIHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof SendTaskProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralCheckPoint());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processComponentCheckPointHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processSendMessageResourceHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof ReceiveTaskProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralCheckPoint());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processComponentCheckPointHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processReceiveMessageResourceHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralKeyExpression());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processReceiveMessageKeyExpressionHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof WebserviceProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralCheckPoint());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processComponentCheckPointHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processWebServiceResourceHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralService());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processWebServiceServiceHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralPort());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processWebServicePortHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralOperation());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processWebServiceOperationHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralSoapAction());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processWebServiceSoapActionHelp());
			helpContentBuilder.append("</p></li>");

			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralTimeOut());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processWebServiceTimeoutHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof InferenceTaskProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralCheckPoint());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processComponentCheckPointHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processInferenceTaskResourceHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof CallActivityProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralCheckPoint());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processComponentCheckPointHelp());
			helpContentBuilder.append("</p></li>");
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processCallActivityResourceHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof ExclusiveGatewayProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralDefaultSequence());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processExclusiveGatewayDefSqeHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof ParallelGatewayProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralJoinFunction());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processParallelGatewayJoinFunHelp());
			helpContentBuilder.append("</p></li>");

			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralForkFunction());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processParallelGatewayForkFunHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof MessageStartEventProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processMessageStartEventResourceRHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof SignalStartEventProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processSignalStartEventResourceHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof TimerStartEventProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processTimerStartEventResourceHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof MessageEndEventProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processMessageEndEventResourceRHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof SignalEndEventProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processSignalEndEventResourceHelp());
			helpContentBuilder.append("</p></li>");
		} else if (property instanceof ErrorEndEventProperty) {
			helpContentBuilder.append("<li><b>");
			helpContentBuilder.append(processMessages
					.processPropertyTabGeneralResource());
			helpContentBuilder.append("</b><p>");
			helpContentBuilder.append(processMessages
					.processErrorEndEventResourceHelp());
			helpContentBuilder.append("</p></li>");
		}
		helpContentBuilder.append("</ul>");
		return helpContentBuilder.toString();
	}
}
