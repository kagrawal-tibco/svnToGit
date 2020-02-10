package com.tibco.cep.webstudio.client.decisiontable;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * 
 * @author sasahoo
 *
 */
public class SelectColumnTypeDialog extends Window implements CloseClickHandler {

	protected IButton okButton, cancelButton;
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private String title;
	private CustomBooleanCallBack callback;
    private boolean condition = true;
    private String conditionVal = "Condition";
    private String actionVal = "Action";
	
	public SelectColumnTypeDialog(String title, CustomBooleanCallBack callback) {
		super();
		this.title = title;
		this.callback = callback;
		initialize();
	}

	public void initialize(){
		setWidth(280);
		setHeight(100);
		setTitle(title);		
		addCloseClickHandler(this);
		setAutoCenter(true);
		setAutoSize(true);
		setIsModal(true);
		VLayout layout = new VLayout(10);
		layout.setAutoHeight();
		layout.setWidth100();
		layout.setLayoutBottomMargin(10);
		layout.setLayoutLeftMargin(10);
		layout.setLayoutRightMargin(10);
		layout.setLayoutTopMargin(12);
		layout.addMember(createSelectColumnsSection(layout));
		layout.addMember(createButtons());
		addItem(layout);
	}

	private HLayout createButtons() {
		HLayout buttonContainer = new HLayout(10);
		buttonContainer.setWidth100();
		buttonContainer.setHeight100();
		buttonContainer.setAlign(Alignment.RIGHT);
		okButton = new IButton(globalMsgBundle.button_ok());
		okButton.setWidth(100);  
		okButton.setShowRollOver(true);  
		okButton.setShowDisabled(true);  
		okButton.setShowDown(true);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (callback != null) {
					callback.setCondition(condition);
					callback.execute(true);
				}
				markForDestroy();          
				hide();        
			}
		});
		buttonContainer.addMember(okButton);

		cancelButton = new IButton(globalMsgBundle.button_cancel());
		cancelButton.setWidth(100);  
		cancelButton.setShowRollOver(true);  
		cancelButton.setShowDisabled(true);  
		cancelButton.setShowDown(true);
		cancelButton.setAlign(Alignment.CENTER);
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
		buttonContainer.addMember(cancelButton);
		return buttonContainer;		
	}

	private DynamicForm createSelectColumnsSection(VLayout layout) {
		DynamicForm selectForm = new DynamicForm();
		selectForm.setWidth(250);
		selectForm.setIsGroup(true);   
		//selectForm.setShowShadow(true);
		selectForm.setGroupTitle("Select"); 
		final RadioGroupItem radioGroupItem = new RadioGroupItem();   
		radioGroupItem.setShowTitle(false);
		radioGroupItem.setValueMap(conditionVal, actionVal);   
		radioGroupItem.setVertical(false);
		radioGroupItem.setAlign(Alignment.CENTER);
		radioGroupItem.setDefaultValue(conditionVal);
		radioGroupItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				if (radioGroupItem.getValueAsString().equals(conditionVal)) {
					condition = true;
				}
				if (radioGroupItem.getValueAsString().equals(actionVal)) {
					condition = false;
				}
			}
		});
		selectForm.setFields(radioGroupItem);   
		return selectForm;
	}

	@Override
	public void onCloseClick(CloseClickEvent event) {
		destroy();
	}
	
	public boolean isCondition() {
		return condition;
	}

}