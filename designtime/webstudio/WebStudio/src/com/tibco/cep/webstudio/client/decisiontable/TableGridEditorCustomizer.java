package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getActionColumns;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getBEDate;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getColumn;

import java.util.Date;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.widgets.DateTimeDialog;
import com.tibco.cep.webstudio.client.widgets.MultiSelectListBox;


/**
 * 
 * @author sasahoo
 *
 */
public class TableGridEditorCustomizer implements ListGridEditorCustomizer {

	protected DTMessages dtMessages = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	private AbstractDecisionTableEditor editor;
	private MultiSelectListBox domainComboBox = null;
	private boolean decisionTable;
	public static final String EMPTY = "<NA>";//$NON-NLS-N$
	public static final String ASTERISK = "*";
	public TextItem textItemDate;
	private static WebStudioClientLogger logger = WebStudioClientLogger.getLogger(MultiSelectListBox.class.getName());

	public TableGridEditorCustomizer(AbstractDecisionTableEditor editor) {
		this.editor = editor;
	}

	@Override
	public FormItem getEditor(ListGridEditorContext context) {
		final ListGridField field = context.getEditField();  
		if (field.getName().equalsIgnoreCase("ID")) {
			TextItem textItem = new TextItem();
			textItem.setCanEdit(false);
			return textItem;
		}
		final TableColumn col = getColumn(decisionTable ? 
				editor.getTable().getDecisionTable().getColumns() : editor.getTable().getExceptionTable().getColumns(), field.getName());

		if (context.getEditedRecord() instanceof TableRuleVariableRecord) {
			TableRuleVariableRecord record = (TableRuleVariableRecord)context.getEditedRecord();
			String ruleId = record.getRule().getId();
			String fieldName = field.getName();
			PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(col.getPropertyType()));
			if (!editor.canEdit(ruleId, fieldName, decisionTable)) {
				if (type == PROPERTY_TYPES.BOOLEAN && !col.isArrayProperty()) {
					CheckboxItem ckbItem = new CheckboxItem();   
					ckbItem.setCanEdit(false);
					return ckbItem;
				} else {
					TextItem textItem = new TextItem();
					textItem.setCanEdit(false);
					return textItem;
				}
			}
		}
		
		if(col.hasAssociatedDomain()) {
			field.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum,
						int colNum) {
					if(value == null) return null;
					String val  = value.toString().replace(",", "||");
					return val;
				}
			});

			String columnDelimiter = null;
			if (com.tibco.cep.webstudio.client.decisiontable.model.ColumnType.ACTION.getName().equalsIgnoreCase(col.getColumnType())) {
				columnDelimiter = DecisionTableConstants.ACTION_COLUMN_DELIMITER;
			} else {
				columnDelimiter = DecisionTableConstants.CONDITION_COLUMN_DELIMITER;
			}
			String argumentPath =  "", argumentType = "";
			String columnName = field.getName();			
			int index = columnName.indexOf(columnDelimiter);
			String alias =  columnName.substring(0, index);
			String propertyName = columnName.substring(index + columnDelimiter.length());
			for (ArgumentData argData : editor.getTable().getArguments()) {
				if (argData.getAlias().equals(alias)) {
					argumentPath = argData.getPropertyPath();
					argumentType = argData.getResourceType();
					break;
				}
			}
					
			final FormItem item = getDomainItem(context, col, argumentType, argumentPath, propertyName);
			context.getEditField().setEditorType(item);
			return item;
		}
		else if (col.getPropertyType() != null) {
			PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(col.getPropertyType()));
			if (type == PROPERTY_TYPES.DATE_TIME) {
//				field.setEditValueParser(new CellEditValueParser() {
//					@Override
//					public Object parse(Object value, ListGridRecord record, int rowNum,
//							int colNum) {
//						if (value != null && !value.toString().isEmpty()) {
//							value = value.toString().replace("T", "");
//							int index = value.toString().lastIndexOf("-");
//							String first = value.toString().substring(0, index + 3);
//							String last = value.toString().substring(index + 3);
//							return first + "T" + last;
//						}
//						return "";
//					}
//				});
//				DateTimeItem dateItem = new DateTimeItem(); 
//				dateItem.setEditorValueFormatter(new FormItemValueFormatter() {
//					@Override
//					public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
//						return getBEDate((Date)value);
//					}
//				});
//				return dateItem;
				
				
				// New DateTime Implementation
				
				textItemDate = new TextItem();
				textItemDate.setCanEdit(true);
				textItemDate.setInitHandler(new FormItemInitHandler() {
					
					@Override
					public void onInit(FormItem item) {
						
						textItemDate = new TextItem(item.getJsObj());
						textItemDate.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								DecisionTableUtils.getDateTimeDialog(textItemDate);
							}
								

						});
						
						
					}
				});
	            textItemDate.setShowTitle(false);
				textItemDate.setWidth("210px");
					
                return textItemDate;	
				
			}
		}
		return context.getDefaultProperties();   
	}

	private FormItem getDomainItem(final ListGridEditorContext context, TableColumn col, String argumentType, String argumentPath, String propertyName) { 
		boolean isActionCol = false;
		List<TableColumn> cols = getActionColumns(decisionTable ? editor.getTable().getDecisionTable() 
																: editor.getTable().getExceptionTable());

		String domainPath = editor.getProjectName() + argumentPath + "/" + propertyName;
		domainPath = domainPath.replace("/", "$");
		if (cols.contains(col)) {
			isActionCol = true;
			domainPath = domainPath + "$$";
		}
		DomainDataSource domainDataSource = DomainDataSource.getInstance(domainPath);
		domainDataSource.clearRequestParameters();		
		domainDataSource.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, editor.getProjectName()));
		domainDataSource.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_ARGUMENT_PATH, argumentPath));
		domainDataSource.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_ARGUMENT_TYPE, argumentType));
		domainDataSource.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_ARGUMENT_PROPERTY_NAME, propertyName));			
		domainDataSource.addRequestParameters(new RequestParameter("isConditionColumn", !isActionCol));
		
		String recordValue = context.getEditedRecord().getAttributeAsString(col.getName());
			
		TextItem item = new TextItem();
		item.setShowTitle(false);
		
		if (domainComboBox != null)
			domainComboBox.destroy();
		
		MultiSelectListBox.EditorHandler editorHandler = new MultiSelectListBox.EditorHandler() {
			@Override
			public void endEditing() {
				ListGrid listGrid = decisionTable ? editor.dtTable : editor.expTable;
				listGrid.endEditing();
			}

			@Override
			public void cancelEditing() {
				ListGrid listGrid = decisionTable ? editor.dtTable : editor.expTable;
				listGrid.cancelEditing();
			}

			@Override
			public void startEditing() {
				ListGrid listGrid = decisionTable ? editor.dtTable : editor.expTable;
				int colNum = decisionTable ? editor.dtTable.getFieldNum(context.getEditField().getName()) : editor.expTable.getFieldNum(context.getEditField().getName());
				listGrid.startEditing(context.getRowNum(), colNum, true);
			}			
		};
		PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(col.getPropertyType()));
		domainComboBox = new MultiSelectListBox(item, domainDataSource, !isActionCol, "value", recordValue, " || ", editorHandler, type, null, editor, false);

		return item;		
	}
	
	public boolean isDecisionTable() {
		return decisionTable;
	}

	public void setDecisionTable(boolean decisionTable) {
		this.decisionTable = decisionTable;
	}		
}
