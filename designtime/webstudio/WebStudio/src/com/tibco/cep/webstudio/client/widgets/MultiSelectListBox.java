package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.decisiontable.AbstractDecisionTableEditor;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.constraint.StringTokenizer;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;

public class MultiSelectListBox extends FormItem {

    private TextItem textItem = null;
    private ListGrid dropDownListGrid = null;
	private Window dropDownPopup = null;
	private boolean isDropDown = false;
	private IButton okButton, cancelButton;
	private DataSource dataSource = null; 
	private boolean isMultiSelect = false;
	private String valueAttribute = "value";
	private List<String> selectedValues = null;
	private List<String> selectedValuesTemp = null;
	private String valuesSeparator = " || ";
	private EditorHandler editorHandler = null;
	private PROPERTY_TYPES columnType;
	
	private static WebStudioClientLogger logger = WebStudioClientLogger.getLogger(MultiSelectListBox.class.getName());
	
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private TableColumn column;
	private AbstractDecisionTableEditor editor;
	private boolean isFieldSettingDiag;
	private CheckboxItem checkBoxItem;
	
	public MultiSelectListBox(TextItem textItem, DataSource dataSource, boolean isMultiSelect, final String valueAttribute, 
									String selectedValuesStr, String separator, EditorHandler editorHandler, PROPERTY_TYPES columnType, TableColumn column, AbstractDecisionTableEditor editor, boolean isFieldSettingDiag) {

		textItem.setInitHandler(new FormItemInitHandler() {
			@Override
			public void onInit(FormItem item) {
				MultiSelectListBox.this.textItem = new TextItem(item.getJsObj());
				MultiSelectListBox.this.textItem.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						handleTextItemClicked();
					}
				});
			}
		});
		
		textItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (!WebStudio.get().getUserPreference().isAllowCustomDomainValues()) { 
					event.cancel();
				}				
			}
		});
		
		textItem.setIcons(new PickerIcon(PickerIcon.COMBO_BOX));
		textItem.addIconClickHandler(new IconClickHandler() {
			@Override
			public void onIconClick(IconClickEvent event) {
				handleTextItemClicked();
			}
		});
		
		this.columnType = columnType;
		this.dataSource = dataSource;
		this.isMultiSelect = isMultiSelect;
		this.valueAttribute = valueAttribute;
		this.valuesSeparator = separator;
		this.editorHandler = editorHandler;
		this.column = column;
		this.editor = editor;
		this.isFieldSettingDiag = isFieldSettingDiag;
		
		this.selectedValues = new ArrayList<String>();
		this.selectedValuesTemp = new ArrayList<String>();
		if (selectedValuesStr != null && !selectedValuesStr.isEmpty()) {
			if(!selectedValuesStr.equalsIgnoreCase("null")){
				StringTokenizer stringTokenizer = new StringTokenizer(selectedValuesStr, this.valuesSeparator.trim());			
				while (stringTokenizer.hasMoreTokens()) {
					String token = stringTokenizer.nextToken().trim();
					if (columnType == PROPERTY_TYPES.STRING && token.startsWith("\"") && token.endsWith("\"")) {
						token = token.substring(1, token.length() - 1);//Remove quotations
					}
					this.selectedValues.add(token);
				}
			}
		}
		initialize();
	}    
    
	public void destroy() {
		Canvas[] items = this.dropDownPopup.getItems();
		if (items != null) {
			for(int i = 0; i < items.length; i++) {
				items[i].destroy();
			}
		}
		this.dropDownPopup.destroy();
	}
	
	private void initialize() {
		dropDownPopup = new Window();  
        dropDownPopup.setWidth(320);  
        dropDownPopup.setHeight(260);
        dropDownPopup.setShowMinimizeButton(false);
        dropDownPopup.setShowMaximizeButton(false);
        dropDownPopup.setShowHeader(false);
        dropDownPopup.setShowFooter(false);
        dropDownPopup.setCanDragReposition(false);
        dropDownPopup.setShowStatusBar(false);
        dropDownPopup.setShowEdges(false);
        dropDownPopup.setIsModal(true);
        dropDownPopup.setDismissOnEscape(true);
        dropDownPopup.setDismissOnOutsideClick(true);
		
		Layout vLayout = new VLayout(3);
		vLayout.setAutoHeight();
		vLayout.setWidth100();
		vLayout.setLayoutBottomMargin(5);
		vLayout.setLayoutLeftMargin(5);
		vLayout.setLayoutRightMargin(5);
		vLayout.setLayoutTopMargin(5);
		vLayout.setMembersMargin(5);
		vLayout.setBackgroundColor("white");
		vLayout.setAlign(Alignment.CENTER);
		vLayout.setAlign(VerticalAlignment.CENTER);      
		
		dropDownListGrid = new ListGrid(); 
        dropDownListGrid.setWidth100();  
        dropDownListGrid.setHeight(200);
        dropDownListGrid.setAlign(Alignment.CENTER);
        dropDownListGrid.setMargin(0);
        dropDownListGrid.setShowAllRecords(true);  
        dropDownListGrid.setSelectionType(isMultiSelect ? SelectionStyle.SIMPLE : SelectionStyle.SINGLE);  
        dropDownListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
        dropDownListGrid.setAutoFetchData(true); 
        dropDownListGrid.setDataFetchMode(FetchMode.LOCAL);
        dropDownListGrid.setShowFilterEditor(true);
        dropDownListGrid.setAutoFetchTextMatchStyle(TextMatchStyle.STARTS_WITH);
        dropDownListGrid.setFilterOnKeypress(true);

        ListGridField[] listGridFields = null;
        DataSourceField[] fields = dataSource.getFields(); 
        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>(); 
        for (int i = 0; i < fields.length; i++) {
        	ListGridField listGridField = new ListGridField(fields[i].getName(), fields[i].getTitle());
        	listGridFieldList.add(listGridField);
        }
        listGridFields = listGridFieldList.toArray(new ListGridField[listGridFieldList.size()]);
        dropDownListGrid.setFields(listGridFields);
        dropDownListGrid.setDataSource(dataSource);
        
		vLayout.addMember(dropDownListGrid);
		vLayout.addMember(createActionButtons());      
		dropDownPopup.addItem(vLayout);
		
		addHandlers();
	}
		
	private void addHandlers() {		
				
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {					
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				StringBuffer strBuff = new StringBuffer();
				for (int i = 0; i < selectedValues.size(); i++) {
					String value = selectedValues.get(i);
					if (value.equals("*")) {
						strBuff = new StringBuffer();
						strBuff.append(value);
						break;
					}
					if (columnType == PROPERTY_TYPES.STRING) {
						value = "\"" + value + "\"";
					}
					strBuff.append(value);
					if (i < (selectedValues.size() - 1)) {
						strBuff.append(valuesSeparator);
					}					
				}
				if (!strBuff.toString().equals(textItem.getValue())) {
					if(isFieldSettingDiag){
						textItem.setValue(strBuff.toString());
						if(strBuff.toString().equalsIgnoreCase("")){
							checkBoxItem.disable();
						}else{
							checkBoxItem.enable();
						}
						column.setDefaultCellText(strBuff.toString());
					}else{
						editorHandler.startEditing();
						textItem.setValue(strBuff.toString());
						editorHandler.endEditing();
					}
				} else {
					if(!isFieldSettingDiag){
						editorHandler.cancelEditing();
					}
				}
				MultiSelectListBox.this.dropDownPopup.hide();
			}
		});
		
		cancelButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				MultiSelectListBox.this.dropDownPopup.hide();
				if(!isFieldSettingDiag)
				editorHandler.cancelEditing();
			}
		});
		
		dropDownListGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {			
			@Override
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				selectedValuesTemp.clear();
				selectedValuesTemp.addAll(selectedValues);
				if(!isMultiSelect){
					selectedValues.clear();	
				}
				ListGridRecord[] records = dropDownListGrid.getSelectedRecords();
				for (int i = 0; i < records.length; i++) {
					ListGridRecord record = records[i];
					String value = record.getAttribute("value");
					if(!selectedValues.contains(value))
					selectedValues.add(value);
				}
			}
		});	

        dropDownListGrid.addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				setSelection();
				dropDownPopup.setHeight(dropDownPopup.getHeight());
			}
		});
        
        dropDownListGrid.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				String value = event.getRecord().getAttribute("value");
				if(selectedValuesTemp.contains(value)){
					selectedValues.remove(value);
				}
				
			}
		});

	}
		
	private HLayout createActionButtons() {
		HLayout buttonContainer = new HLayout(10);
		buttonContainer.setWidth100();
		buttonContainer.setHeight(40);
		buttonContainer.setAlign(Alignment.CENTER);
		buttonContainer.setAlign(VerticalAlignment.BOTTOM);

		okButton = new IButton(globalMsgBundle.button_ok());
		okButton.setWidth(100);  
		okButton.setShowRollOver(true);  
		okButton.setShowDisabled(true);  
		okButton.setShowDown(true);
		okButton.setAlign(Alignment.CENTER);
		okButton.setValign(VerticalAlignment.CENTER);
		buttonContainer.addMember(okButton);
		
		cancelButton = new IButton(globalMsgBundle.button_cancel());
		cancelButton.setWidth(100);  
		cancelButton.setShowRollOver(true);  
		cancelButton.setShowDisabled(true);  
		cancelButton.setShowDown(true);
		cancelButton.setAlign(Alignment.CENTER);
		cancelButton.setValign(VerticalAlignment.CENTER);
		buttonContainer.addMember(cancelButton);
		
		return buttonContainer;
	}
	
	private void setSelection() {
		if (selectedValues != null && !selectedValues.isEmpty()) {
			List<String> selectedValues2 = new ArrayList<String>(selectedValues);
			ListGridRecord gridRecords[] = dropDownListGrid.getRecords();
			for (int i = 0; i < gridRecords.length; i++) {
				String value = gridRecords[i].getAttribute(valueAttribute);	
				if (selectedValues2.contains(value)) {
					dropDownListGrid.selectRecord(gridRecords[i]);
				}	
			}			
		}
	}
	
	public static interface EditorHandler {		
		public void startEditing();
		public void endEditing();
		public void cancelEditing();
	}
	
	/**
	 * Handle click event of textbox or its picker icon. 
	 */
	private void handleTextItemClicked() {
		if (!isDropDown) {
			dropDownPopup.show();
			dropDownListGrid.clearCriteria();
			int textItemWidth = MultiSelectListBox.this.textItem.getWidth();					        
			if (textItemWidth > dropDownPopup.getWidth()) {
				dropDownPopup.setWidth(textItemWidth);
			}
			if (MultiSelectListBox.this.textItem.getPageTop() + MultiSelectListBox.this.textItem.getHeight() + dropDownPopup.getHeight() >= com.google.gwt.user.client.Window.getClientHeight()){
				dropDownPopup.moveTo(MultiSelectListBox.this.textItem.getPageLeft() - (dropDownPopup.getWidth() - textItemWidth), 
						MultiSelectListBox.this.textItem.getPageTop() - dropDownPopup.getHeight());
			} else {
				dropDownPopup.moveTo(MultiSelectListBox.this.textItem.getPageLeft() - (dropDownPopup.getWidth() - textItemWidth), 
						MultiSelectListBox.this.textItem.getPageTop() + MultiSelectListBox.this.textItem.getHeight());
			}

		}
		isDropDown = !isDropDown;
	}
	
	public void setIgnoreExistingCheckbox(CheckboxItem checkBoxItem){
		this.checkBoxItem = checkBoxItem;
	}
}
