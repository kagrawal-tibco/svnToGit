package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.cacheCellEnables;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getBEDateTime;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.tibco.cep.webstudio.client.decisiontable.model.MetaData;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;
/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTablePropertyTab extends HLayout implements ClickHandler, ChangedHandler {

	private static final String BOTTOM_PROPERIES_GROUP = "PROPERIES_GROUP";
	protected DTMessages dtMessages = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);

	private Button tableBtn;
	private Button ruleBtn;
	private Button cellBtn;

	private DynamicForm ruleForm;
	private DynamicForm tableForm;
	private DynamicForm cellForm;

	private VStack buttonStack;
	
	private TextItem idItem;
	private TextAreaItem ruleCommentsAreaItem;
	private SelectItem rulePrioritySelectItem;
	
	private TextItem nameItem;
	private DateTimeItem effectiveDateItem;
	private DateTimeItem expiryDateItem;
	private CheckboxItem takeActionsRowItem;
	private SelectItem tablePrioritySelectItem;
	private TextItem lastModifiedItem;
	private TextItem versionItem;
	private TextItem implementsItem;
   
	private TextItem expiryDateTextItem;
    private TextItem  effectiveDateTextItem; 
    private DateChooser expiryDateField;
    private DateChooser effectiveDateField;
	
//	private TextAreaItem cellValueAreaItem;
	private CheckboxItem cellEnabledItem;
	private TextAreaItem cellCommentsAreaItem;

	private Table table;
	@SuppressWarnings("unused")
	private TableRule rule;
	private TableRuleVariable ruleVar;
	
	private boolean singleRowExec = false; 
	boolean isDecisionTable = true;
	
	private String tablePriority = "5";
	private List<MetaData> tabmdlist;
	private List<MetaData> rlmdlist;
	private String ruleDesc = "";
	private String rulePriority = "5";
	private String effDateStr = "";
	private String expDateStr = "";
	
	private Date effDate;
	private Date expDate;
	
	private AbstractTableEditor editor;
	public DecisionTablePropertyTab() {
		setWidth100();
		setHeight100();   
		setMembersMargin(5);
		
		createPropertiesTab();
	}
	
	public void createPropertiesTab() {
		buttonStack = new VStack();
		buttonStack.setWidth(50);
		buttonStack.setHeight(200);
		if(LocaleInfo.getCurrentLocale().isRTL()){
			buttonStack.setAlign(Alignment.RIGHT);
		}
		else{
			buttonStack.setAlign(Alignment.LEFT);
		}

		tableBtn = new Button(dtMessages.dtPropertyTabGeneral());
		tableBtn.setActionType(SelectionType.RADIO);
		tableBtn.setRadioGroup(BOTTOM_PROPERIES_GROUP);
		tableBtn.addClickHandler(this);

		ruleBtn = new Button(dtMessages.dtPropertyTabRule());
		ruleBtn.setActionType(SelectionType.RADIO);
		ruleBtn.setRadioGroup(BOTTOM_PROPERIES_GROUP);
		ruleBtn.addClickHandler(this);

		cellBtn = new Button(dtMessages.dtPropertyTabCell());
		cellBtn.setActionType(SelectionType.RADIO);
		cellBtn.setRadioGroup(BOTTOM_PROPERIES_GROUP);
		cellBtn.addClickHandler(this);

		buttonStack.setMembers(tableBtn, ruleBtn, cellBtn);

		setMembers(buttonStack);

		createTablePropertiesSection(this);
		createRulePropertiesSection(this);
		createCellPropertiesSection(this);

		tableBtn.setSelected(true);
		tableForm.setVisible(true);
		ruleForm.setVisible(false);
		cellForm.setVisible(false);
	}

	private void createTablePropertiesSection(HLayout layout) {
		tableForm = new DynamicForm();   
		tableForm.setWidth(650);
		tableForm.setHeight(200);
		tableForm.setIsGroup(true);   
//		tableForm.setShowShadow(true);
		tableForm.setGroupTitle(dtMessages.dtPropertyTabGeneral());   
		
		nameItem = new TextItem();   
		nameItem.setTitle(dtMessages.dtPropertyTabName());   
		nameItem.setCanEdit(false);
		nameItem.setWidth(150);
		nameItem.setVisible(false);
		
		takeActionsRowItem = new CheckboxItem();   
		takeActionsRowItem.setTitle(dtMessages.dtPropertyTabrowatmost());
		takeActionsRowItem.setLabelAsTitle(true);
		takeActionsRowItem.setStartRow(true);
		takeActionsRowItem.setEndRow(true);
		takeActionsRowItem.addChangedHandler(this);
		
		tablePrioritySelectItem = new SelectItem();   
		tablePrioritySelectItem.setTitle(dtMessages.dtPropertyTabpriority());   
		tablePrioritySelectItem.setValueMap("1",	"2", "3", "4", "5", "6", "7", "8", "9", "10");  
		tablePrioritySelectItem.addChangedHandler(this);
		tablePrioritySelectItem.setStartRow(true);
		tablePrioritySelectItem.setEndRow(true);
		tablePrioritySelectItem.setWidth(300);
		
		lastModifiedItem = new TextItem();   
		lastModifiedItem.setTitle(dtMessages.dtPropertyTablastmodfiedby());   
		lastModifiedItem.setCanEdit(false);
		lastModifiedItem.setWidth(300);
		lastModifiedItem.setStartRow(true);
		lastModifiedItem.setEndRow(true);
		lastModifiedItem.setDisabled(true);
		
		versionItem = new TextItem();   
		versionItem.setTitle(dtMessages.dtPropertyTabVersion());   
		versionItem.setCanEdit(false);
		versionItem.setWidth(300);
		versionItem.setStartRow(true);
		versionItem.setEndRow(true);
		versionItem.setDisabled(true);
		
		implementsItem = new TextItem();   
		implementsItem.setTitle(dtMessages.dtPropertyTabRfImplements());   
		implementsItem.setCanEdit(false);
		implementsItem.setWidth(300);
		implementsItem.setStartRow(true);
		implementsItem.setEndRow(true);
		implementsItem.setDisabled(true);		
		
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			nameItem.setWidth(250); 
	        takeActionsRowItem.setWidth(250);
	        tablePrioritySelectItem.setWidth(250);
	        lastModifiedItem.setWidth(250);
	        versionItem.setWidth(250);
	        implementsItem.setWidth(250);
	        tableForm.setNumCols(6);	        
				 
			final ButtonItem effectiveDateButtonItem = new ButtonItem(); 
			effectiveDateButtonItem.setIcon("[SKIN]DynamicForm/date_control.png");
			effectiveDateButtonItem.setBaseStyle("buttonDate");
			effectiveDateButtonItem.setAutoFit(true);
			effectiveDateButtonItem.setStartRow(false);  
		    effectiveDateButtonItem.setEndRow(false); 
		    effectiveDateButtonItem.setWidth(20);
		    
		    effectiveDateTextItem = new TextItem(); 	
	        effectiveDateTextItem.setTitle(dtMessages.dtPropertyTabeffectdate());   
	        effectiveDateTextItem.setWrapTitle(false); 
	        effectiveDateTextItem.setWidth(250);
	        effectiveDateTextItem.setStartRow(true);
	        effectiveDateTextItem.setEndRow(false);	          
	       
	        final CanvasItem effectiveDatecanvasItem = new CanvasItem();
	        final CanvasItem expiryDatecanvasItem = new CanvasItem();
	        effectiveDatecanvasItem.setShowTitle(false);
	        effectiveDatecanvasItem.setStartRow(false);
	        effectiveDatecanvasItem.setEndRow(true);
	        effectiveDatecanvasItem.setRowSpan(6); 
			
	        effectiveDateButtonItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler()  {  
				@Override
				public void onClick(
						com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					// TODO Auto-generated method stub	
					if(expiryDateField != null){
						expiryDateField.destroy();
					}
					effectiveDateField = new DateChooser();
					effectiveDateField.setPosition("absolute");
					effectiveDateField.setFirstDayOfWeek(6);   
					effectiveDateField.setBaseWeekendStyle("dateChooserWeekendStyle");
					effectiveDateField.setShowCancelButton(true);		            
		            effectiveDatecanvasItem.setCanvas(effectiveDateField);
		        	if(LocaleInfo.getCurrentLocale().isRTL()){
		        		effectiveDateField.setStyleName("dcDTPropertyTab");
		    		}
	                tableForm.redraw();
	                effectiveDateField.addDataChangedHandler(new DataChangedHandler() {
						@Override
						public void onDataChanged(DataChangedEvent event) {
							// TODO Auto-generated method stub
							String newDateStr = "";
							Date newDate = null;
							Date date = effectiveDateField.getData();	                        
	                        DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	                        String formatedDate = dateFormat.format(date);
	                        DateTimeFormat timeFormat = DateTimeFormat.getFormat("HH:MM:SS");
	                        String formatedTime = timeFormat.format(date);
	                        String selectedDate = formatedDate + "T" + formatedTime;
	                        effectiveDateTextItem.setValue(selectedDate);
	                        newDateStr = selectedDate.toString();
	    					newDate = getDate(newDateStr);
	    					try {
	    					if(newDate == null){						
	    						ErrorMessageDialog.showError("Invalid date format.");
	    					}
	    					
	    					boolean valid = true;
	    					if (newDate != null && expiryDateTextItem.getValue() != null && !expiryDateTextItem.getValue().toString().isEmpty()) {
	    						String expDateStr = expiryDateTextItem.getValue().toString();
	    						Date expDate = getDate(expDateStr);
	    						if (newDate.after(expDate)) {
	    							valid = false;
	    							ErrorMessageDialog.showError("Effective Date must be less than Expiry Date.", new BooleanCallback() {	    								
	    								@Override
	    								public void execute(Boolean value) {
	    									if (value && effDateStr != null && !effDateStr.isEmpty()) {
	    										effectiveDateTextItem.setValue(effDateStr);
	    									}
	    									if (value && (effDateStr == null || effDateStr.isEmpty())) {
	    										effectiveDateTextItem.setValue("");
	    									}
	    								}
	    							});
	    						}
	    					}
	    					
	    					if (newDate == null && effDate != null) {
	    						effDate = newDate;
	    						effDateStr = newDateStr;
	    						if (tabmdlist != null) {
	    							for (MetaData md : tabmdlist) {
	    								if (md.getName().equals("EffectiveDate")) {
	    									md.setValue("");
	    								}
	    							}
	    						}
	    						editor.makeDirty(isDecisionTable);
	    					}
	    					if (newDate != null && !newDate.equals(effDate) && valid) {
	    						effDate = newDate;
	    						effDateStr = newDateStr;
	    						if (tabmdlist != null) {
	    							for (MetaData md : tabmdlist) {
	    								if (md.getName().equals("EffectiveDate")) {
	    									md.setValue(effDateStr);
	    								}
	    							}
	    						}
	    						editor.makeDirty(isDecisionTable);
	    					 }
	    				    } catch (Exception e) {
	    						e.printStackTrace();
	    					}
	    					effectiveDateField.destroy();
						}
	             	});                
	                tableForm.redraw();
				}  
	        }); 
	        
	        expiryDateTextItem = new TextItem(); 		 
			final ButtonItem expiryDateButtonItem = new ButtonItem(); 
			expiryDateButtonItem.setIcon("[SKIN]DynamicForm/date_control.png");
			expiryDateButtonItem.setBaseStyle("buttonDate");
		    expiryDateButtonItem.setStartRow(false);  
	        expiryDateButtonItem.setEndRow(false);
	        expiryDateButtonItem.setWidth(20);     
	        
	        expiryDateTextItem.setTitle(dtMessages.dtPropertyTabexpirydate());   
	        expiryDateTextItem.setWrapTitle(false);  
	        expiryDateTextItem.setWidth(250);
	        expiryDateTextItem.setStartRow(true);
	        expiryDateTextItem.setEndRow(false);        
	        
	       
			expiryDatecanvasItem.setShowTitle(false);
			expiryDatecanvasItem.setStartRow(false);
			expiryDatecanvasItem.setEndRow(true);
			expiryDatecanvasItem.setRowSpan(6);
	        expiryDateButtonItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler()  {  
				@Override
				public void onClick(
						com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					// TODO Auto-generated method stub
					if(effectiveDateField != null){
						effectiveDateField.destroy();
					}
					 expiryDateField = new DateChooser();
					 expiryDateField.setPosition("absolute");
		             expiryDateField.setFirstDayOfWeek(6);
		             expiryDateField.setBaseWeekendStyle("dateChooserWeekendStyle");
		             expiryDateField.setShowCancelButton(true);
		             expiryDatecanvasItem.setCanvas(expiryDateField);
		             if(LocaleInfo.getCurrentLocale().isRTL()){
			        		expiryDateField.setStyleName("dcDTPropertyTab");
			    	}
	                 tableForm.redraw();
	                 expiryDateField.addDataChangedHandler(new DataChangedHandler() {
						@Override
						public void onDataChanged(DataChangedEvent event) {
							String newDateStr = "";
							Date newDate = null;
							// TODO Auto-generated method stub
							Date date = expiryDateField.getData();	                        
	                        DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	                        String formatedDate = dateFormat.format(date);
	                        DateTimeFormat timeFormat = DateTimeFormat.getFormat("HH:MM:SS");
	                        String formatedTime = timeFormat.format(date);
	                        String selectedDate = formatedDate + "T" + formatedTime;
	                        expiryDateTextItem.setValue(selectedDate);
	                        newDateStr = selectedDate.toString();
	    					newDate = getDate(newDateStr);	    					
	    					try {
	    						if (expiryDateTextItem.getValue() != null) {
	    							newDateStr = expiryDateTextItem.getValue().toString();
	    							newDate = getDate(newDateStr);
	    							if(newDate == null){						
	    								ErrorMessageDialog.showError("Invalid date format.");
	    								newDate = expDate;
	    								expiryDateTextItem.setValue(expDate);
	    							}
	    						}
	    						boolean valid = true;
	    						if (newDate != null && effectiveDateTextItem.getValue() != null 
	    								&& !effectiveDateTextItem.getValue().toString().isEmpty()) {
	    							String effDateStr = effectiveDateTextItem.getValue().toString();
	    							Date effDate = getDate(effDateStr);
	    							if (newDate.before(effDate)) {
	    								valid = false;
	    								ErrorMessageDialog.showError("Expiry Date must be greater than Effective Date.", new BooleanCallback() {	    									
	    									@Override
	    									public void execute(Boolean value) {
	    										if (value && expDateStr != null && !expDateStr.isEmpty()) {
	    											expiryDateTextItem.setValue(expDateStr);
	    										}
	    										if (value && (expDateStr == null || expDateStr.isEmpty())) {
	    											expiryDateTextItem.setValue("");
	    										}
	    									}
	    								});
	    							}
	    						}
	    						if (newDate == null && expDate != null) {
	    							expDate = newDate;
	    							expDateStr = newDateStr;
	    							if (tabmdlist != null) {
	    								for (MetaData md : tabmdlist) {
	    									if (md.getName().equals("ExpiryDate")) {
	    										md.setValue("");
	    									}
	    								}
	    							}
	    							editor.makeDirty(isDecisionTable);
	    						}
	    						if (newDate != null && !newDate.equals(expDate) && valid) {
	    							expDate = newDate;
	    							expDateStr = newDateStr;
	    							if (tabmdlist != null) {
	    								for (MetaData md : tabmdlist) {
	    									if (md.getName().equals("ExpiryDate")) {
	    										md.setValue(expDateStr);
	    									}
	    								}
	    							}
	    							editor.makeDirty(isDecisionTable);
	    						}
	    					} catch (Exception e) {
	    						e.printStackTrace();
	    					}
	                        expiryDateField.destroy();	                       
						}
	             	});
	                tableForm.redraw();
				}  
	        }); 
	        
	        tableForm.setItems(nameItem, 
			     	effectiveDateTextItem,
			     	effectiveDateButtonItem,
			     	effectiveDatecanvasItem,
			     	expiryDateTextItem,
			     	expiryDateButtonItem,
			     	expiryDatecanvasItem,
		            takeActionsRowItem, 
		            tablePrioritySelectItem, 
		            lastModifiedItem, 
		            versionItem, 
		            implementsItem);   

		}
		else{
		effectiveDateItem = new DateTimeItem(); 
		effectiveDateItem.setTitle(dtMessages.dtPropertyTabeffectdate()); 
		effectiveDateItem.addChangedHandler(this);
		effectiveDateItem.setWidth(168);
		effectiveDateItem.setEditorValueFormatter(new FormItemValueFormatter() {
			@Override
			public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
				String date = getBEDateTime((Date)value);
				return date;
			}
		});
		
		expiryDateItem = new DateTimeItem();
		expiryDateItem.setTitle(dtMessages.dtPropertyTabexpirydate());  
		expiryDateItem.addChangedHandler(this);
		expiryDateItem.setWidth(168);
		expiryDateItem.setStartRow(true);
		expiryDateItem.setEndRow(true);
		expiryDateItem.setEditorValueFormatter(new FormItemValueFormatter() {
			@Override
			public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
				String date = getBEDateTime((Date)value);
				return date;
			}
		});
		
		tableForm.setItems(nameItem, 
		           effectiveDateItem, 
		           expiryDateItem, 
		           takeActionsRowItem, 
		           tablePrioritySelectItem, 
		           lastModifiedItem, 
		           versionItem, 
		           implementsItem);   
		}
		layout.addMember(tableForm);
	}
	
	private void createRulePropertiesSection(HLayout layout) {
		ruleForm = new DynamicForm();   
		ruleForm.setWidth(650);
		ruleForm.setHeight(200);
		ruleForm.setIsGroup(true);   
//		ruleForm.setShowShadow(true);
		ruleForm.setGroupTitle(dtMessages.dtPropertyTabRule());   
		
		idItem = new TextItem();   
		idItem.setTitle(dtMessages.dtPropertyTabId());   
		idItem.setCanEdit(false);
		idItem.setWidth(300);
		idItem.setDisabled(true);
		
		ruleCommentsAreaItem = new TextAreaItem();   
		ruleCommentsAreaItem.setTitle(dtMessages.dtPropertyTabComments());   
		ruleCommentsAreaItem.setWidth(500);
		ruleCommentsAreaItem.setHeight(100);
		ruleCommentsAreaItem.addChangedHandler(this);

		rulePrioritySelectItem = new SelectItem();   
		rulePrioritySelectItem.setTitle(dtMessages.dtPropertyTabpriority());   
		rulePrioritySelectItem.setValueMap("1",	"2", "3", "4", "5", "6", "7", "8", "9", "10");   
		rulePrioritySelectItem.addChangedHandler(this);
		rulePrioritySelectItem.setWidth(300);
		
		ruleForm.setItems(idItem, rulePrioritySelectItem, ruleCommentsAreaItem);   
		layout.addMember(ruleForm);
	}

	
	private void createCellPropertiesSection(HLayout layout) {
		cellForm = new DynamicForm();
		cellForm.setWidth(650);
		cellForm.setHeight(200);
		cellForm.setIsGroup(true);   
//		cellForm.setShowShadow(true);
		cellForm.setGroupTitle(dtMessages.dtPropertyTabCell()); 

//		cellValueAreaItem = new TextAreaItem();   
//		cellValueAreaItem.setTitle(dtMessages.dtPropertyTabValue());   
//		cellValueAreaItem.setHeight(100); 
//		cellValueAreaItem.addChangedHandler(this);
//		cellValueAreaItem.setCanEdit(false);
		
		cellEnabledItem = new CheckboxItem();   
		cellEnabledItem.setTitle(dtMessages.dtPropertyTabEnabled());   
		cellEnabledItem.setLabelAsTitle(true);
		cellEnabledItem.addChangedHandler(this);
		
		cellCommentsAreaItem = new TextAreaItem();   
		cellCommentsAreaItem.setTitle(dtMessages.dtPropertyTabComments());   
		cellCommentsAreaItem.setWidth(500);
		cellCommentsAreaItem.setHeight(100);
		cellCommentsAreaItem.addChangedHandler(this);

		cellForm.setFields(/*cellValueAreaItem,*/ cellEnabledItem, cellCommentsAreaItem);   
		layout.addMember(cellForm);   
	}
	
	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.events.ClickHandler#onClick(com.smartgwt.client.widgets.events.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == tableBtn) {
			tableForm.setVisible(true);
			cellForm.setVisible(false);
			ruleForm.setVisible(false);
		}
		if (event.getSource() == ruleBtn) {
			tableForm.setVisible(false);
			cellForm.setVisible(false);
			ruleForm.setVisible(true);
		}
		if (event.getSource() == cellBtn) {
			tableForm.setVisible(false);
			cellForm.setVisible(true);
			ruleForm.setVisible(false);
		}
	}
	
	public void refresh(AbstractTableEditor editor,
			            Table table, 
						TableRule rule,
						TableRuleVariable ruleVar, 
						boolean isEditorOpen, 
			            boolean ruleselected, 
			            boolean cellselected, 
			            boolean isDecisionTable) {
		this.editor = editor;
		this.table = table;
		this.rule = rule;
		this.ruleVar = ruleVar;
		this.isDecisionTable = isDecisionTable;
		
		//Table level
		String name  = table.getName();
		String rlimplements = table.getImplements();
		effDateStr = "";
		expDateStr = "";
		singleRowExec = false; 
		tablePriority = "5";
		
		tabmdlist = table.getMetaData();
		if (tabmdlist != null) {
			for (MetaData md : tabmdlist) {
				 if (md.getName().equals("EffectiveDate") && md.getValue() != null) {
					 effDateStr = md.getValue().toString();
				 }
				 if (md.getName().equals("ExpiryDate") && md.getValue() != null) {
					 expDateStr = md.getValue().toString();
				 }
				 if (md.getName().equals("SingleRowExecution") && md.getValue() != null) {
					 singleRowExec = Boolean.parseBoolean(md.getValue().toString());
				 }
				 if (md.getName().equals("Priority") && md.getValue() != null && !md.getValue().toString().isEmpty()) {
					 tablePriority = md.getValue().toString();
				 }
			}
		}

		nameItem.setValue(name);
		if (!effDateStr.isEmpty()) {
			effDate = getDate(effDateStr);
			effectiveDateItem.setValue(effDateStr);
		}
		if (!expDateStr.isEmpty()) {
			expDate = getDate(expDateStr);
			expiryDateItem.setValue(expDateStr);
		}
		
//		effectiveDateItem.setCanEdit(false);
//		expiryDateItem.setCanEdit(false);

		takeActionsRowItem.setValue(singleRowExec);
		tablePrioritySelectItem.setValue(tablePriority);
		lastModifiedItem.setValue("");
		versionItem.setValue("0.0");
		implementsItem.setValue(rlimplements);
		
		if (ruleVar != null) {
			rule  = ruleVar.getTableRule();
		}
		
		if (rule != null) {
			//Rule Level
			String ruleId = rule.getId();
			ruleDesc = "";
			rulePriority = "5";
			rlmdlist = rule.getMetaData();
			if (rlmdlist != null) {
				for (MetaData md : rlmdlist) {
					if (md.getName().equals("Description")) {
						ruleDesc = md.getValue().toString();
					}
					if (md.getName().equals("Priority")) {
						rulePriority = md.getValue().toString();
					}
				}
			} 
			idItem.setValue(ruleId);
			ruleCommentsAreaItem.setValue(ruleDesc);
			rulePrioritySelectItem.setValue(rulePriority);
		}

		//cell level
		if (ruleVar != null) {
//			String cellValue = ruleVar.getExpression();
			String cellcomment = ruleVar.getComment();
			boolean cellenabled = ruleVar.isEnabled(); 

//			cellValueAreaItem.setValue(cellValue);
			cellEnabledItem.setValue(cellenabled);
			cellCommentsAreaItem.setValue(cellcomment);
		}
		
		if (isEditorOpen) {
			tableBtn.setVisible(true);
			ruleBtn.setVisible(false);
			cellBtn.setVisible(false);

			tableForm.setVisible(true);
			cellForm.setVisible(false);
			ruleForm.setVisible(false);
		} else if (ruleselected) {
			tableBtn.setVisible(true);
			ruleBtn.setVisible(true);
			cellBtn.setVisible(false);

			tableForm.setVisible(false);
			cellForm.setVisible(false);
			ruleForm.setVisible(true);
			
		} else if (cellselected) {
			tableBtn.setVisible(true);
			ruleBtn.setVisible(true);
			cellBtn.setVisible(true);
			
			tableForm.setVisible(false);
			ruleForm.setVisible(false);
			cellForm.setVisible(true);	
			
		}
		
		if (editor.isReadOnly()) {
			makeReadOnly();
		}
	}

	@Override
	public void onChanged(ChangedEvent event) {
		if (event.getItem() == effectiveDateItem) {
			try {
				String newDateStr = "";
				Date newDate = null;
				if (effectiveDateItem.getValue() != null) {
					newDateStr = effectiveDateItem.getValue().toString();
					newDate = getDate(newDateStr);
					if(newDate == null){						
						ErrorMessageDialog.showError("Invalid date format.");
						newDate = effDate;
						effectiveDateItem.setValue(effDate);
					}
				}
				boolean valid = true;
				if (newDate != null && expiryDateItem.getValue() != null && !expiryDateItem.getValue().toString().isEmpty()) {
					String expDateStr = expiryDateItem.getValue().toString();
					Date expDate = getDate(expDateStr);
					if (newDate.after(expDate)) {
						valid = false;
						ErrorMessageDialog.showError("Effective Date must be less than Expiry Date.", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if (value && effDateStr != null && !effDateStr.isEmpty()) {
									effectiveDateItem.setValue(effDateStr);
								}
								if (value && (effDateStr == null || effDateStr.isEmpty())) {
									effectiveDateItem.setValue("");
								}
							}
						});
					}
				}
				if (newDate == null && effDate != null) {
					effDate = newDate;
					effDateStr = newDateStr;
					if (tabmdlist != null) {
						boolean effectiveDatePresent = false;
						for (MetaData md : tabmdlist) {
							if (md.getName().equals("EffectiveDate")) {
								effectiveDatePresent = true;
								md.setValue("");
							}
						}
						if (!effectiveDatePresent) {
							tabmdlist.add(new MetaData("EffectiveDate", "DateTime", ""));
						}
					}
					editor.makeDirty(isDecisionTable);
				}
				if (newDate != null && !newDate.equals(effDate) && valid) {
					effDate = newDate;
					effDateStr = newDateStr;
					if (tabmdlist != null) {
						boolean effectiveDatePresent = false;
						for (MetaData md : tabmdlist) {
							if (md.getName().equals("EffectiveDate")) {
								effectiveDatePresent = true;
								md.setValue(effDateStr);
							}
						}
						if (!effectiveDatePresent) {
							tabmdlist.add(new MetaData("EffectiveDate", "DateTime", effDateStr));
						}
					}
					editor.makeDirty(isDecisionTable);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (event.getItem() == expiryDateItem) {
			try {
				String newDateStr = "";
				Date newDate = null;
				if (expiryDateItem.getValue() != null) {
					newDateStr = expiryDateItem.getValue().toString();
					newDate = getDate(newDateStr);
					if(newDate == null){						
						ErrorMessageDialog.showError("Invalid date format.");
						newDate = expDate;
						expiryDateItem.setValue(expDate);
					}
				}
				boolean valid = true;
				if (newDate != null && effectiveDateItem.getValue() != null 
						&& !effectiveDateItem.getValue().toString().isEmpty()) {
					String effDateStr = effectiveDateItem.getValue().toString();
					Date effDate = getDate(effDateStr);
					if (newDate.before(effDate)) {
						valid = false;
						ErrorMessageDialog.showError("Expiry Date must be greater than Effective Date.", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if (value && expDateStr != null && !expDateStr.isEmpty()) {
									expiryDateItem.setValue(expDateStr);
								}
								if (value && (expDateStr == null || expDateStr.isEmpty())) {
									expiryDateItem.setValue("");
								}
							}
						});
					}
				}
				if (newDate == null && expDate != null) {
					expDate = newDate;
					expDateStr = newDateStr;
					if (tabmdlist != null) {
						boolean expiryDatePresent = false;
						for (MetaData md : tabmdlist) {
							if (md.getName().equals("ExpiryDate")) {
								expiryDatePresent = true;
								md.setValue("");
							}
						}
						if (!expiryDatePresent) {
							tabmdlist.add(new MetaData("ExpiryDate", "DateTime", ""));
						}
					}
					editor.makeDirty(isDecisionTable);
				}
				if (newDate != null && !newDate.equals(expDate) && valid) {
					expDate = newDate;
					expDateStr = newDateStr;
					if (tabmdlist != null) {
						boolean expiryDatePresent = false;
						for (MetaData md : tabmdlist) {
							if (md.getName().equals("ExpiryDate")) {
								expiryDatePresent = true;
								md.setValue(expDateStr);
							}
						}
						if (!expiryDatePresent) {
							tabmdlist.add(new MetaData("ExpiryDate", "DateTime", expDateStr));
						}
					}
					editor.makeDirty(isDecisionTable);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (event.getItem() == takeActionsRowItem) {
			if (singleRowExec != (Boolean)takeActionsRowItem.getValue()) {
				if (tabmdlist != null) {
					boolean singleRowPresent = false;
					for (MetaData md : tabmdlist) {
						if (md.getName().equals("SingleRowExecution")) {
							singleRowPresent = true;
							md.setValue(takeActionsRowItem.getValue());
						}
					}
					if (!singleRowPresent) {
						tabmdlist.add(new MetaData("SingleRowExecution", "Boolean", takeActionsRowItem.getValue()));
					}
				}
				singleRowExec = (Boolean)takeActionsRowItem.getValue();
				editor.makeDirty(isDecisionTable);
			}
		} else if (event.getItem() == tablePrioritySelectItem) {
			if (Integer.parseInt(tablePriority) != Integer.parseInt(tablePrioritySelectItem.getValue().toString())) {
				if (tabmdlist != null) {
					boolean priorityPresent = false;
					for (MetaData md : tabmdlist) {
						 if (md.getName().equals("Priority")) {
							 priorityPresent = true;
							 md.setValue(tablePrioritySelectItem.getValue());
						 }
					}
					if (!priorityPresent) {
						tabmdlist.add(new MetaData("Priority", "Integer", tablePrioritySelectItem.getValue()));
					}
				}
				tablePriority = tablePrioritySelectItem.getValue().toString();
				editor.makeDirty(isDecisionTable);
			}
		} else if (event.getItem() == ruleCommentsAreaItem) {
			if (!ruleDesc.equals(ruleCommentsAreaItem.getValue().toString())) {
				if (rlmdlist != null) {
					boolean descPresent = false;
					for (MetaData md : rlmdlist) {
						if (md.getName().equals("Description")) {
							descPresent = true;
							md.setValue(ruleCommentsAreaItem.getValue());
						}
					}
					if (!descPresent) {
						rlmdlist.add(new MetaData("Description", "String", ruleCommentsAreaItem.getValue()));
					}
				} else {
					rlmdlist = new ArrayList<MetaData>();
					rlmdlist.add(new MetaData("Description", "String", ruleCommentsAreaItem.getValue()));
				}
				editor.makeDirty(isDecisionTable);
				ruleDesc = ruleCommentsAreaItem.getValue().toString();
			}
		} else if (event.getItem() == rulePrioritySelectItem) {
			if (Integer.parseInt(rulePriority) != Integer.parseInt(rulePrioritySelectItem.getValue().toString())) {
				if (rlmdlist != null) {
					boolean priorityPresent = false;
					for (MetaData md : rlmdlist) {
						if (md.getName().equals("Priority")) {
							priorityPresent = true;
							md.setValue(rulePrioritySelectItem.getValue());
						}
					}
					if (!priorityPresent) {
						rlmdlist.add(new MetaData("Priority", "Integer", rulePrioritySelectItem.getValue()));
					}
				} else {
					rlmdlist = new ArrayList<MetaData>();
					rlmdlist.add(new MetaData("Priority", "Integer", rulePrioritySelectItem.getValue()));
				}
				rulePriority = rulePrioritySelectItem.getValue().toString();
				editor.makeDirty(isDecisionTable);
			}
		}  else if (event.getItem() == cellEnabledItem) {
			if (ruleVar.isEnabled() != (Boolean)cellEnabledItem.getValue()) {
				ruleVar.setEnabled((Boolean)cellEnabledItem.getValue());
				//For Cell Enable/Disable
				cacheCellEnables(ruleVar, table, editor, isDecisionTable);
				editor.enableCells(isDecisionTable);

				editor.makeDirty(isDecisionTable);
			}
		} else if (event.getItem() == cellCommentsAreaItem) {
			if (!ruleVar.getComment().equals(cellCommentsAreaItem.getValue().toString())) {
				ruleVar.setComment(cellCommentsAreaItem.getValue().toString());
				editor.makeDirty(isDecisionTable);
			}
		}
	}
	
	private void makeReadOnly() {
		ruleCommentsAreaItem.setDisabled(true);
		rulePrioritySelectItem.setDisabled(true);
		
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			effectiveDateField.setDisabled(true);
			effectiveDateTextItem.setDisabled(true);
			expiryDateField.setDisabled(true);
			expiryDateTextItem.setDisabled(true);
		}
		else{
			effectiveDateItem.setDisabled(true);
			expiryDateItem.setDisabled(true);
		}
		
		takeActionsRowItem.setDisabled(true);
		tablePrioritySelectItem.setDisabled(true);
		lastModifiedItem.setDisabled(true);
		
		cellEnabledItem.setDisabled(true);
		cellCommentsAreaItem.setDisabled(true);
		
		cellBtn.setVisible(false);
	}

}