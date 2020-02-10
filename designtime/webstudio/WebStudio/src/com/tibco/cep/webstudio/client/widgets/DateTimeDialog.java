package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.util.RuleTemplateHelper;
/**
 * Dialog to select Date and Time for Decision table field.
 * 
 * @author Yogendra Rajput
 */
public class DateTimeDialog extends AbstractWebStudioDialog {
	
	protected SelectItem cbItem;
	
	protected SmartGwtDateTimePicker dtp;
	protected SmartGwtDateTimePicker dtp1;
	protected String dateText;
	protected CanvasItem canvasItem;
	protected CanvasItem canvasItem1;
	
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private boolean backToDashboard;
	
	/**
	 * Constructor to be used to create Update Dialog
	 * 
	 * @param selectedProject
	 * @param isUpdate
	 */
	public DateTimeDialog() {
		String title = globalMsgBundle.datepicker_select_date_time();
				
		setDialogWidth(500);
		setDialogHeight(200);
		setDialogTitle(title);		
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "checkout" + WebStudioMenubar.ICON_SUFFIX);
		initialize();
	}
	
	@Override
	public void initialize() {
		
		setWidth(500);
		setHeight(200);
		setTitle(globalMsgBundle.datepicker_select_date_time());		
		addCloseClickHandler(this);
		setAutoCenter(true);
		setAutoSize(true);
		setIsModal(true);
		setShowModalMask(true);
		VLayout layout = new VLayout(10);
		layout.setAutoHeight();
		layout.setWidth100();
		layout.setLayoutBottomMargin(10);
		layout.setLayoutLeftMargin(10);
		layout.setLayoutRightMargin(10);
		layout.setLayoutTopMargin(12);
		
		for (Widget widget : getWidgetList()){
			layout.addMember(widget);
		}
		
	
			layout.addMember(createActionButtons());

		addItem(layout);
	}
	
	private HLayout createActionButtons() {
		HLayout buttonContainer = new HLayout(10);
		buttonContainer.setWidth100();
		buttonContainer.setHeight100();
		buttonContainer.setAlign(Alignment.RIGHT);
		
		addCustomWidget(buttonContainer);

			okButton = new IButton(globalMsgBundle.button_ok());
			okButton.setWidth(100);  
			okButton.setShowRollOver(true);  
			okButton.setShowDisabled(true);  
			okButton.setShowDown(true);
			okButton.setAlign(Alignment.CENTER);
			okButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(dtp != null){
						dtp.destroyDateChooser();		
					}
					
					if(dtp1 != null){
						
						dtp1.destroyDateChooser();	
					}
					onAction();
				}
			});
			buttonContainer.addMember(okButton);
		
		
			cancelButton = new IButton("Cancel");
			cancelButton.setWidth(100);  
			cancelButton.setShowRollOver(true);  
			cancelButton.setShowDisabled(true);  
			cancelButton.setShowDown(true);
			cancelButton.setAlign(Alignment.CENTER);
			cancelButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(dtp != null){
						dtp.destroyDateChooser();		
					}
					
					if(dtp1 != null){
						dtp1.destroyDateChooser();	
					}
					doCancel();
				}
			});
			buttonContainer.addMember(cancelButton);
		
		
		return buttonContainer;		
	}
	
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		
		widgetList.add(createCheckoutForm());
		widgetList.add(createCheckoutForm1());
		
		
		return widgetList;
	}
	
	/**
	 * Create form for configuring RMS server and managed projects
	 * @return
	 */
	private HLayout createCheckoutForm() {
		HLayout header = new HLayout(5);
		header.setWidth100();
		header.setLayoutMargin(5);
		header.setBorder("1px solid grey");
		header.setBackgroundColor("#e8e8e8");
		
		VLayout formContainer = new VLayout(10);
		formContainer.setWidth("85%");
		formContainer.setLeft(0);
				
		final DynamicForm form = new DynamicForm();
		form.setWidth("100%");
		form.setNumCols(2);
		form.setColWidths("12%", "*");
        
		
		
        cbItem = new SelectItem("Select");
        cbItem.setValueMap("Matches","Between","Before","After");
        cbItem.setValue("Between");
        cbItem.setWidth("100%");
        cbItem.setAutoFetchData(true);
        

	    form.setItems(new FormItem[] {cbItem});
		header.addMember(form);		
	    return header;
	}
	
	private HLayout createCheckoutForm1() {
		HLayout header = new HLayout(5);
		header.setWidth100();
		header.setLayoutMargin(5);
		header.setBorder("1px solid grey");
		header.setBackgroundColor("#e8e8e8");
		
		VLayout formContainer = new VLayout(10);
		formContainer.setWidth("85%");
		formContainer.setLeft(0);
				
		final DynamicForm form = new DynamicForm();
		form.setWidth("100%");
		form.setNumCols(2);
		form.setColWidths("12%", "*");
        
		
		
		    dtp = new SmartGwtDateTimePicker(null);
			final CanvasItem canvasItem = new CanvasItem() {
				@Override
				public void setValue(String value) {
					if (value != null) {
						try {
							Date date = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT).parse(value);
							dtp.setValue(date);
						} catch(Exception e) {
							dtp.setValue(null);
						}
					}
				}
			};
			
			canvasItem.setShouldSaveValue(true);
			canvasItem.setTitle("After");
			
			/*dtp.addValueChangeHandler(new ValueChangeHandler<Date>() {
				@Override
				public void onValueChange(ValueChangeEvent<Date> event) {
					if (event.getValue() != null) {
						String formattedDate = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT).format(event.getValue());
						canvasItem.setValue(formattedDate);
						//((SimpleFilterValue) filterValue).setValue(formattedDate);
					}
				}
			});
			*/
			
			dtp.addDataChangedHandler(new DataChangedHandler() {
				@Override
				public void onDataChanged(DataChangedEvent event) {
					// TODO Auto-generated method stub
					Date newDate = dtp.getValue();
					canvasItem.setValue(DateTimeFormat.getFormat("yyyy-MM-dd").format(newDate));
					dtp.setVisible(false);
				}
			});
			
			HLayout hLayout = new HLayout();
			hLayout.addMember(dtp);
			hLayout.setLayoutData(dtp);//Required to disable DTP while showing RTI in readonly mode (DIFF). [Ref: RuleTemplateInstanceEditorFactory.makeElementReadOnly(Object)]
			hLayout.setPixelSize(70, 50);
			hLayout.setAlign(Alignment.CENTER);
			hLayout.setStyleName("smartGwtDateTimePicker");
			
			canvasItem.setCellHeight(30);
			canvasItem.setCanvas(hLayout);
			
			
			dtp1 = new SmartGwtDateTimePicker(null);
			final CanvasItem canvasItem1 = new CanvasItem() {
				@Override
				public void setValue(String value) {
					if (value != null) {
						try {
							Date date = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT).parse(value);
							dtp1.setValue(date);
						} catch(Exception e) {
							dtp1.setValue(null);
						}
					}
				}
			};
			
			canvasItem1.setShouldSaveValue(true);
			canvasItem1.setTitle("Before");
			
			/*dtp1.addValueChangeHandler(new ValueChangeHandler<Date>() {
				@Override
				public void onValueChange(ValueChangeEvent<Date> event) {
					if (event.getValue() != null) {
						String formattedDate = DateTimeFormat.getFormat(RuleTemplateHelper.DATE_TIME_FORMAT).format(event.getValue());
						canvasItem1.setValue(formattedDate);
						//((SimpleFilterValue) filterValue).setValue(formattedDate);
					}
				}
			});*/
			
			dtp1.addDataChangedHandler(new DataChangedHandler() {
				@Override
				public void onDataChanged(DataChangedEvent event) {
					// TODO Auto-generated method stub
					Date newDate = dtp.getValue();
					canvasItem.setValue(DateTimeFormat.getFormat("yyyy-MM-dd").format(newDate));
					dtp.setVisible(false);
				}
			});
			
			HLayout hLayout1 = new HLayout();
			hLayout1.addMember(dtp1);
			hLayout1.setLayoutData(dtp1);//Required to disable DTP while showing RTI in readonly mode (DIFF). [Ref: RuleTemplateInstanceEditorFactory.makeElementReadOnly(Object)]
			hLayout1.setPixelSize(70, 50);
			hLayout1.setAlign(Alignment.CENTER);
			hLayout1.setStyleName("smartGwtDateTimePicker1");

			cbItem.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					  canvasItem1.show();
					  canvasItem.show();
					  canvasItem1.setTitle("");
					  canvasItem.setTitle("");
					if(event.getValue().toString().equalsIgnoreCase("Matches")){
						canvasItem1.hide();
						canvasItem.setTitle("Matches");
					}else if(event.getValue().toString().equalsIgnoreCase("Before")){
						canvasItem1.hide();
						canvasItem.setTitle("Before");
					}else if(event.getValue().toString().equalsIgnoreCase("After")){
						canvasItem1.hide();
						canvasItem.setTitle("After");
					}else if(event.getValue().toString().equalsIgnoreCase("Between")){
						canvasItem.setTitle("After");
						canvasItem1.setTitle("Before");
						
					}
					
				}
			});
			
			canvasItem1.setCellHeight(30);
			canvasItem1.setCanvas(hLayout1);
			if(cbItem.getValue().toString().equalsIgnoreCase("Between")){
				form.setItems(new FormItem[] {canvasItem,canvasItem1});
				form.setTitleSuffix("");
			}else if(cbItem.getValue().toString().equalsIgnoreCase("Matches")){
				form.setItems(new FormItem[] {canvasItem});
				form.setTitleSuffix("");
			}
		header.addMember(form);		
	    return header;
	}
	
	
	
	@Override
	public void onAction() {
		if(cbItem.getValue().toString().equalsIgnoreCase("Matches")){
			Date a = dtp.getValue();
			dateText = (a.getYear()+1900)+"-"+getPropperValue((a.getMonth()+1))+"-"+getPropperValue(a.getDate())+"T"+getPropperValue(a.getHours())+":"+getPropperValue(a.getMinutes())+":"+getPropperValue(a.getSeconds());
		}else if(cbItem.getValue().toString().equalsIgnoreCase("Before")){
			Date a = dtp.getValue();
			dateText = "<"+(a.getYear()+1900)+"-"+getPropperValue((a.getMonth()+1))+"-"+getPropperValue(a.getDate())+"T"+getPropperValue(a.getHours())+":"+getPropperValue(a.getMinutes())+":"+getPropperValue(a.getSeconds());
		}else if(cbItem.getValue().toString().equalsIgnoreCase("After")){
			Date a = dtp.getValue();
			dateText = ">"+(a.getYear()+1900)+"-"+getPropperValue((a.getMonth()+1))+"-"+getPropperValue(a.getDate())+"T"+getPropperValue(a.getHours())+":"+getPropperValue(a.getMinutes())+":"+getPropperValue(a.getSeconds());
		}else{
			Date a = dtp.getValue();
			Date b = dtp1.getValue();
			dateText = ">"+(a.getYear()+1900)+"-"+getPropperValue((a.getMonth()+1))+"-"+getPropperValue(a.getDate())+"T"+getPropperValue(a.getHours())+":"+getPropperValue(a.getMinutes())+":"+getPropperValue(a.getSeconds())+ " && <"+(b.getYear()+1900)+"-"+getPropperValue((b.getMonth()+1))+"-"+getPropperValue(b.getDate())+"T"+getPropperValue(b.getHours())+":"+getPropperValue(b.getMinutes())+":"+getPropperValue(b.getSeconds());
			
		}	
			
	}
	
	public String getPropperValue(int original){
		if(original < 10){
			return "0"+original;
		}
		return String.valueOf(original);
		
	}
	
	@Override
	protected void doCancel() {
		super.doCancel();
		
		if (isBackToDashboard()) {
			WebStudio.get().showDashboardPage();
		}
	}

	/**
	 * Get whether to move back to dashboard
	 * 
	 * @return
	 */
	public boolean isBackToDashboard() {
		return backToDashboard;
	}

	/**
	 * Set if onCancel user needs to move back to dashboard
	 * 
	 * @param backToDashboard
	 */
	public void setBackToDashboard(boolean backToDashboard) {
		this.backToDashboard = backToDashboard;
	}
	
	public String getDateText(){
		return dateText;
	}
	
	public static interface EditorHandler {		
		public void startEditing();
		public void endEditing();
		public void cancelEditing();
	}
}
