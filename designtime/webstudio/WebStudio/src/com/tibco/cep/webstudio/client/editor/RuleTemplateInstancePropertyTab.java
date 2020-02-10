/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RTIMessages;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

/**
 * @author rkhera
 *
 */
public class RuleTemplateInstancePropertyTab extends HLayout implements ClickHandler, ChangedHandler {

	protected RTIMessages rtiMessages = (RTIMessages) I18nRegistry.getResourceBundle(I18nRegistry.RTI_MESSAGES);
	private static final String BOTTOM_PROPERIES_GROUP = "PROPERIES_GROUP";

	private TextItem nameItem;
	private TextAreaItem descriptionAreaItem;
	private SelectItem prioritySelectItem;
	private DynamicForm tableForm;
	private VStack buttonStack;
	private Button tableButton;

	private RuleTemplateInstanceEditor editor;
	private RuleTemplateInstance rti;

	public RuleTemplateInstancePropertyTab(RuleTemplateInstance rti, RuleTemplateInstanceEditor editor, boolean isReadOnly) {
		setWidth100();
		setHeight100();
		setMembersMargin(5);
		
		setRti(rti);
		setEditor(editor);

		createPropertiesTab(isReadOnly);
		setVisible(true);
	
		setData();
	}

	public void createPropertiesTab(boolean isReadOnly) {
		buttonStack = new VStack();
		buttonStack.setWidth(50);
		buttonStack.setHeight(200);
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			buttonStack.setAlign(Alignment.RIGHT);
		} else {
			buttonStack.setAlign(Alignment.LEFT);
		}

		tableButton = new Button(rtiMessages.rtiPropertyTab_General());
		tableButton.setActionType(SelectionType.RADIO);
		tableButton.setRadioGroup(BOTTOM_PROPERIES_GROUP);
		tableButton.addClickHandler(this);

		buttonStack.setMembers(tableButton);

		createTablePropertiesSection(isReadOnly);

		setMembers(buttonStack, tableForm);

		tableButton.setSelected(true);
		tableForm.setVisible(true);
	}

	private void createTablePropertiesSection(boolean isReadOnly) {
		tableForm = new DynamicForm();
		// tableForm.setAutoWidth();
		// tableForm.setAutoHeight();
		tableForm.setWidth(650);
		tableForm.setHeight(200);
		tableForm.setIsGroup(true);
		tableForm.setGroupTitle(rtiMessages.rtiPropertyTab_General());
		tableForm.setVisible(true);

		nameItem = new TextItem();
		nameItem.setTitle(rtiMessages.rtiPropertyTab_Name());
		nameItem.setCanEdit(false);
		nameItem.setWidth("100%");
		nameItem.setVisible(true);
		nameItem.setStartRow(true);
		nameItem.setEndRow(true);
		nameItem.setDisabled(isReadOnly);

		prioritySelectItem = new SelectItem();
		prioritySelectItem.setTitle(rtiMessages.rtiPropertyTab_Priority());
		prioritySelectItem.setVisible(true);
		LinkedHashMap<String, Integer> priorityValueMap = new LinkedHashMap<String, Integer>();
		priorityValueMap.put("1", 1);
		priorityValueMap.put("2", 2);
		priorityValueMap.put("3", 3);
		priorityValueMap.put("4", 4);
		priorityValueMap.put("5", 5);
		priorityValueMap.put("6", 6);
		priorityValueMap.put("7", 7);
		priorityValueMap.put("8", 8);
		priorityValueMap.put("9", 9);
		priorityValueMap.put("10", 10);
		prioritySelectItem.setValueMap(priorityValueMap);
		prioritySelectItem.setAllowEmptyValue(false);
		prioritySelectItem.addChangedHandler(this);
		prioritySelectItem.setEmptyDisplayValue("Set Priority");
		prioritySelectItem.setCanEdit(!isReadOnly);
		prioritySelectItem.setDisabled(isReadOnly);

		descriptionAreaItem = new TextAreaItem();
		descriptionAreaItem.setTitle(rtiMessages.rtiPropertyTab_Description());
		descriptionAreaItem.setTitleVAlign(VerticalAlignment.TOP);
		descriptionAreaItem.setCanEdit(true);
		descriptionAreaItem.setWidth("100%");
		descriptionAreaItem.setHeight("100%");
		descriptionAreaItem.setVisible(true);
		descriptionAreaItem.setStartRow(true);
		descriptionAreaItem.setEndRow(true);
		descriptionAreaItem.addChangedHandler(this);
		descriptionAreaItem.setCanEdit(!isReadOnly);
		descriptionAreaItem.setDisabled(isReadOnly);

		tableForm.setItems(nameItem, prioritySelectItem, descriptionAreaItem);
	}

	@Override
	public void onChanged(ChangedEvent event) {
		if (event.getItem() == descriptionAreaItem) {
			if (descriptionAreaItem.getValueAsString() != null
			|| !descriptionAreaItem.getValueAsString().equals("undefined")) {
				this.rti.setDescription(descriptionAreaItem.getValueAsString());
			} else {
				this.rti.setDescription("");
			}
		} else if (event.getItem() == prioritySelectItem) {
			this.rti.setPriority(Integer.parseInt(prioritySelectItem.getValueAsString()));
		}
		this.editor.makeDirty();
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() == tableButton) {
			tableForm.setVisible(true);
		}
	}

	public void setData() {
		String name = this.rti.getName();
		String description = this.rti.getDescription();
		int priority = this.rti.getPriority();
		
		this.nameItem.setValue(getRTIName(name));
		
		if (description != null) {
			this.descriptionAreaItem.setValue(description);
		} else {
			this.descriptionAreaItem.setValue("");
		}
		
		if (priority > 0 && priority <= 10) {
			this.prioritySelectItem.setValue(priority);
		}
	}

	public void showRTIPropertiesPane() {
		WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
		WebStudio.get().getEditorPanel().getPropertiesPane().fillProperties(this);
		WebStudio.get().getEditorPanel().getBottomContainer().selectTab(0);
	}
	
	private String getRTIName(String name) {
		if (name != null) {
			if (name.indexOf(".") != -1) {
				name = name.substring(0, name.indexOf("."));
			}
			if (name.lastIndexOf("/") > 0) {
				name = name.substring(name.lastIndexOf("/")+1, name.length());
			}
		}
		return name;
	}

	/**
	 * @return the editor
	 */
	public RuleTemplateInstanceEditor getEditor() {
		return editor;
	}

	/**
	 * @param editor
	 *            the editor to set
	 */
	public void setEditor(RuleTemplateInstanceEditor editor) {
		this.editor = editor;
	}

	/**
	 * @return the rti
	 */
	public RuleTemplateInstance getRti() {
		return rti;
	}

	/**
	 * @param rti
	 *            the rti to set
	 */
	public void setRti(RuleTemplateInstance rti) {
		this.rti = rti;
	}
}
