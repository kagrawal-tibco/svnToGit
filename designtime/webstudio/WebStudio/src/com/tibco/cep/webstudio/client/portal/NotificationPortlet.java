package com.tibco.cep.webstudio.client.portal;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RowEditorExitEvent;
import com.smartgwt.client.widgets.grid.events.RowEditorExitHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.preferences.NotificationPreferencesHelper;
import com.tibco.cep.webstudio.client.widgets.OperatorDisplayNameUtil;

public class NotificationPortlet {

	private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private static Label selectedProjectLabel = null;
	private static DynamicForm approvalActionsForm = null;
	private static Label selectedProjectNamesArrow;
	private static IButton applyNotifyButton;
	private HLayout gridsLayoutNotify;
	private ListGrid emailsGrid;
	private Record selectedEmailRecord;
	
	public Canvas getCanvas(){
		Canvas canvas = createConfigurationCanvas();
		return canvas;
	}

	/**
	 * Method to create the portlet canvas.
	 * 
	 * @return
	 */
	private Canvas createConfigurationCanvas() {
		VLayout stack = new VLayout();
		stack.setWidth100();
		stack.setHeight100();
		stack.setStyleName("ws-app-pref-portlet");
		stack.setOverflow(Overflow.AUTO);
		gridsLayoutNotify = new HLayout();
		gridsLayoutNotify.setHeight100();
		gridsLayoutNotify.setWidth100();
		gridsLayoutNotify.setStyleName("ws-app-pref-op-outerLayout");
		gridsLayoutNotify.setCanHover(true);
		gridsLayoutNotify.setHoverWidth(300);
		gridsLayoutNotify.setShowHover(true);
		
		createProjectsLayout(gridsLayoutNotify);
		createEmailConfigLayout(gridsLayoutNotify);

		HLayout buttonContainer = new HLayout();
		buttonContainer.setStyleName("ws-app-pref-button");
		buttonContainer.setWidth100();
		buttonContainer.setHeight("20%");
		buttonContainer.setAlign(Alignment.RIGHT);

		applyNotifyButton = new IButton(globalMsgBundle.button_apply());
		buttonContainer.addMember(applyNotifyButton);
		applyNotifyButton.disable();
		applyNotifyButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				NotificationPreferencesHelper.getInstance()
						.updateNotificationPreferences();
			}
		});
		
		
        
		stack.addMember(gridsLayoutNotify);
		stack.addMember(buttonContainer);

		return stack;
	}

	private void createEmailConfigLayout(HLayout parent) {
		HLayout gridsLayout = new HLayout();
		gridsLayout.setHeight100();
		gridsLayout.setWidth("70%");
		createActionsLayout(gridsLayout);
		createEmailIDsLayout(gridsLayout);
		parent.addMember(gridsLayout);
	}

	private void createActionsLayout(HLayout parent) {

		VLayout stack = new VLayout();
		parent.addMember(stack);
		stack.setBorder("1px solid #ABABAB");
		stack.setStyleName("ws-app-pref-commandop");
		stack.setHeight100();

		Label actionLabel = new Label();
		actionLabel.setContents(globalMsgBundle.notificationAction_list_String());
		actionLabel.setAlign(Alignment.CENTER);
		actionLabel.setBackgroundColor("#DEDEDE");
		actionLabel.setAutoHeight();
		actionLabel.setPadding(10);
		stack.addMember(actionLabel);
		
		approvalActionsForm = new DynamicForm();
		approvalActionsForm.setNumCols(1);
		approvalActionsForm.setWidth100();
		approvalActionsForm.setOverflow(Overflow.AUTO);
		
		Map<String, Boolean> actionsMap = NotificationPreferencesHelper.getInstance().getActionsMap(selectedProjectLabel.getContents());
		setActionsForm(approvalActionsForm, actionsMap, "action");
		
		stack.addMember(approvalActionsForm);
		
	}

	private void createEmailIDsLayout(HLayout parent) {

		VLayout stack = new VLayout();
		stack.setStyleName("ws-app-pref-commandop");
		parent.addMember(stack);
		stack.setBorder("1px solid #ABABAB");
		stack.setHeight100();

		Label emailsLabel = new Label();
		emailsLabel.setContents(globalMsgBundle.notificationEmails_list_String());
		emailsLabel.setAlign(Alignment.CENTER);
		emailsLabel.setBackgroundColor("#DEDEDE");
		emailsLabel.setAutoHeight();
		emailsLabel.setPadding(10);

		emailsGrid = new ListGrid();
		emailsGrid.setHeight("90%");
		emailsGrid.setAutoFitData(Autofit.VERTICAL);
		emailsGrid.setCanEdit(true);
		emailsGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
		emailsGrid.setAlternateRecordStyles(false);
		emailsGrid.setShowHeader(false);
		ListGridField userNameField = new ListGridField("EmailIDs");
		emailsGrid.setFields(userNameField);
		emailsGrid.setBorder("none");
		
		emailsGrid.addRowEditorExitHandler(new RowEditorExitHandler() {
			
			@Override
			public void onRowEditorExit(RowEditorExitEvent event) {
//				if(selectedEmailRecord!=null){
//					if(selectedEmailRecord.getAttribute("EmailIDs")!=null){
//						if(!((String) event.getNewValues().get("EmailIDs")).equalsIgnoreCase(selectedEmailRecord.getAttribute("EmailIDs"))){
//							NotificationPreferencesHelper.getInstance().addOperationProjectEmailsString(selectedProjectLabel.getContents(), (String) event.getNewValues().get("EmailIDs"),selectedEmailRecord.getAttribute("EmailIDs"));
//						}
//					}
//				}else if(event.getNewValues().get("EmailIDs")!=null){
//					NotificationPreferencesHelper.getInstance().addOperationProjectEmailsString(selectedProjectLabel.getContents(), (String) event.getNewValues().get("EmailIDs"),"@New_Value");
//				}
				
				String newValue = "";
				String oldValue = "";
				
				if(event.getNewValues().get("EmailIDs")!=null){
					 newValue = (String) event.getNewValues().get("EmailIDs");
				}else{
					 newValue = "@New_Null_Value";
				}
				
				if(selectedEmailRecord!=null){
					if(selectedEmailRecord.getAttribute("EmailIDs")!=null){
						oldValue = selectedEmailRecord.getAttribute("EmailIDs");
					}else{
						oldValue = "@Old_Null_Value";
					}
				}else{
					oldValue = "@Old_Null_Value";
				}
				
					NotificationPreferencesHelper.getInstance().addOperationProjectEmailsString(selectedProjectLabel.getContents(),newValue,oldValue);
				
				
				
				
				
			}
		});
		
		emailsGrid.addRecordClickHandler(new RecordClickHandler() {
			// @SuppressWarnings("deprecation")
			@Override
			public void onRecordClick(RecordClickEvent event) {
				selectedEmailRecord = event.getRecord();
			}
		});
		
        String[] list = NotificationPreferencesHelper.getInstance().getEmailIDsArray(selectedProjectLabel.getContents());	
        for(String a : list){
        	if(!a.equals("NO_EMAILS") && !a.equalsIgnoreCase(" ")){
        		ListGridRecord record = new ListGridRecord();
        		record.setAttribute("EmailIDs", a);
        		emailsGrid.addData(record);
        	}
        }
		
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth(50);
		toolStrip.setTop(2);
		toolStrip.setHeight(24);
		toolStrip.setStyleName("ws-role-config-button-toolbar");
		
		Canvas canvas = new Canvas();
		ImgButton addButton = new ImgButton();
		addButton.setSize(16);
		addButton.setShowRollOver(false);
		addButton.setShowDown(false);
		addButton.setSrc("[SKIN]actions/add.png");
		addButton.setActionType(SelectionType.BUTTON);
		toolStrip.addMember(addButton);
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				emailsGrid.startEditingNew();
				selectedEmailRecord=null;
				applyNotifyButton.enable();
			}
		});

		ImgButton removeButton = new ImgButton();
		removeButton.setSize(16);
		removeButton.setShowRollOver(false);
		removeButton.setShowDown(false);
		removeButton.setSrc("[SKIN]actions/remove.png");
		removeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				NotificationPreferencesHelper.getInstance().deleteOperationProjectEmailsString(selectedProjectLabel.getContents(), emailsGrid.getSelectedRecord().getAttribute("EmailIDs"));
                emailsGrid.removeSelectedData();
                applyNotifyButton.enable();
				
			}
		});
		
		toolStrip.addMember(removeButton);
		
		canvas.addChild(emailsLabel);
		canvas.addChild(toolStrip);

		stack.addMember(canvas);	
		stack.addMember(emailsGrid);
	}

	private void createProjectsLayout(HLayout parent) {
		VLayout stack = new VLayout();
		parent.addMember(stack);

		stack.setStyleName("ws-app-pref-fieldTypeStack");
		stack.setHeight100();
		stack.setWidth100();

		Label fieldTypeLabel = new Label();
		fieldTypeLabel.setContents(globalMsgBundle.notificationProject_list_String());
		fieldTypeLabel.setAlign(Alignment.CENTER);
		fieldTypeLabel.setBackgroundColor("#DEDEDE");
		fieldTypeLabel.setAutoHeight();
		fieldTypeLabel.setPadding(10);
		fieldTypeLabel.setWidth100();
		stack.addMember(fieldTypeLabel);

		List<String>  ProjectList = NotificationPreferencesHelper
				.getInstance().getProjectList();
		
		int count = 0;
		for (final String projectString : ProjectList) {
			final HLayout projectLayout = new HLayout();
			projectLayout.setStyleName("ws-role-config-hlayout");
			projectLayout.setAutoWidth();
			projectLayout.setAutoHeight();

			final Label projectLabel = new Label();
			projectLabel.setAutoHeight();
			projectLabel.setContents(projectString);
			projectLabel.setPadding(6);
			projectLabel.setAlign(Alignment.LEFT);

			final HLayout arrowLayout = new HLayout();
			arrowLayout.setAlign(Alignment.RIGHT);
			arrowLayout.setWidth100();
			final Label arrowLabel = new Label();
			arrowLabel.setAutoHeight();
			arrowLabel.setAutoWidth();
			arrowLabel.setContents("&#9654;");
			arrowLabel.hide();
			arrowLabel.setStyleName("ws-app-pref-arrow");
			arrowLabel.setAlign(Alignment.RIGHT);
			arrowLayout.addMember(arrowLabel);

			projectLayout.addMember(projectLabel);
			projectLayout.addMember(arrowLayout);

			if (count == 0) {
				projectLabel.setStyleName("ws-app-pref-fieldtype-onclick");
				selectedProjectNamesArrow = arrowLabel;
				selectedProjectNamesArrow.show();
				selectedProjectLabel = projectLabel;
			} else {
				projectLabel.setStyleName("ws-app-pref-fieldtype");
			}
			projectLabel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (selectedProjectLabel != null) {
						selectedProjectLabel
								.setStyleName("ws-app-pref-fieldtype");
					}
					arrowLabel.show();
					if (!projectLabel.equals(selectedProjectLabel)) {
						selectedProjectNamesArrow.hide();
					}
					selectedProjectNamesArrow = arrowLabel;
					projectLabel.setStyleName("ws-app-pref-fieldtype-onclick");
					selectedProjectLabel = projectLabel;
					setActions();
					setEmailIds();
				}
			});
			projectLabel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if (!projectLabel.equals(selectedProjectLabel)) {
						projectLabel
								.setStyleName("ws-app-pref-fieldtype-onHover");
					}
				}
			});

			projectLabel.addMouseOutHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {
					if (!projectLabel.equals(selectedProjectLabel)) {
						projectLabel.setStyleName("ws-app-pref-fieldtype");
					}
				}
			});

			stack.addMember(projectLayout);
			count++;
		}
	}

	private static void setActions() {
		Map<String, Boolean> actionsMap = NotificationPreferencesHelper.getInstance().getActionsMap(selectedProjectLabel.getContents());
		setActionsForm(approvalActionsForm, actionsMap, "action");
		
	}
	
	private void setEmailIds() {
        String[] list = NotificationPreferencesHelper.getInstance().getEmailIDsArray(selectedProjectLabel.getContents());
        emailsGrid.selectAllRecords();
        emailsGrid.removeSelectedData();
        for(String a : list){
        	if(!a.equals("NO_EMAILS") && !a.equalsIgnoreCase(" ") && !a.isEmpty()){
        		ListGridRecord record = new ListGridRecord();
        		record.setAttribute("EmailIDs", a);
        		emailsGrid.addData(record);
        	}
        }
	}

	private static void setActionsForm(DynamicForm actionsForm,
			Map<String, Boolean> actionsMap, final String type) {
		final CheckboxItem[] items = new CheckboxItem[actionsMap.size()];
		int count = 0;
		for (Entry<String, Boolean> entry : actionsMap.entrySet()) {
			String action = entry.getKey();
			final CheckboxItem item = new CheckboxItem();
			item.setTitle(OperatorDisplayNameUtil.getOperator(action));
			item.setShowTitle(false);
			item.setTextBoxStyle("ws-app-pref-operators");
			item.setValue(entry.getValue());
			item.setAttribute("op_value", action);
			items[count++] = item;
			item.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					if(type.equalsIgnoreCase("action")){
					NotificationPreferencesHelper.getInstance()
							.updateProjectActionsString(
									selectedProjectLabel.getContents(),
									item.getAttribute("op_value"),item.getValueAsBoolean());
					}
					applyNotifyButton.enable();
				}
			});
		}
		actionsForm.setItems(items);
	}

	public static void disableApplyButton() {
		applyNotifyButton.disable();
	}
}
