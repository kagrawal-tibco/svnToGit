package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.handler.ApplyButtonClickHandler;
import com.tibco.cep.webstudio.client.process.handler.ResetButtonClickHandler;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.properties.general.TextAnnotationGeneralProperty;

/**
 * This class is used to create the property tabs for text annotation.
 * 
 * @author dijadhav
 * 
 */
public class TextAnnotationPropertyTabSet extends PropertyTabSet {
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private static ProcessMessages processMsgBundle = (ProcessMessages)I18nRegistry.getResourceBundle(I18nRegistry.PROCESS_MESSAGES);
	public TextAnnotationPropertyTabSet(Property property) {
		super(property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.process.properties.tabs.PropertyTabSet
	 * #createTabSet ()
	 */
	@Override
	public void createTabSet() {
		if (null == getGeneraltab()) {
			super.createTabSet();
		}

		if (property instanceof TextAnnotationGeneralProperty) {
			createGeneralPropertyForm((TextAnnotationGeneralProperty) property);
		} else if (property instanceof DocumentationProperty) {
			createDocumentationPropertyForm((DocumentationProperty) property);
		}
	}

	/**
	 * This method is used to create the general property form.
	 * 
	 * @param annotationGeneralProperty
	 * 
	 */
	private void createGeneralPropertyForm(
			TextAnnotationGeneralProperty annotationGeneralProperty) {
		TextAreaItem textArea = new TextAreaItem(ProcessConstants.TEXT, processMsgBundle.processPropertyTabGeneralText());
		textArea.setValue(annotationGeneralProperty.getText());
		textArea.setHeight(100);
		textArea.setWidth(400);
		textArea.setAttribute("style", "resize:none");
		textArea.setTitleVAlign(VerticalAlignment.TOP);
		HLayout hLayout = new HLayout(5);
		hLayout.setAlign(Alignment.LEFT);
		hLayout.setLeft("0px");
		VLayout vLayout = new VLayout(5);
		
		final IButton applyButton = new IButton(globalMsgBundle.button_apply());
	
		applyButton.setDisabled(true);
		final IButton resetButton = new IButton(globalMsgBundle.button_reset());
		resetButton.setDisabled(true);
		resetButton.addClickHandler(new ResetButtonClickHandler(annotationGeneralProperty.getText(),applyButton,resetButton,textArea));
		applyButton.addClickHandler(new ApplyButtonClickHandler(textArea,applyButton,resetButton, annotationGeneralProperty.getText()));
		vLayout.setAlign(VerticalAlignment.BOTTOM);
		vLayout.setTop(20);
		vLayout.addMember(applyButton);
		vLayout.addMember(resetButton);

		textArea.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				resetButton.setDisabled(false);
				applyButton.setDisabled(false);
			}
		});
		DynamicForm generalForm = createGeneralPropertyForm();
		generalForm.setItems(getNameItem(annotationGeneralProperty.getName()),
				getLabelItem(annotationGeneralProperty.getLabel()), textArea);
		hLayout.addMember(generalForm);
		hLayout.addMember(vLayout);
		getGeneraltab().setPane(hLayout);

	}
}
