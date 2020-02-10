package com.tibco.cep.studio.ui.editors;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.RuleTemplateTextAndDialogCellEditor;
/**
 * 
 * @author aasingh
 *
 */
public class RuleTemplateViewsTable {
	private TableViewer tableViewer;
	private int NO_OF_COLUMNS;
	private Table tableReference;
	private Composite composite;
	private FormToolkit toolkit;
	private RuleTemplateFormEditor editor;
	private String[] PROPS;
	private boolean updown;
	private RuleTemplateBindingsFormViewer formViewer;

	
	public RuleTemplateViewsTable( FormToolkit toolkit , Composite composite, RuleTemplateFormEditor editor, boolean updown , RuleTemplateBindingsFormViewer formViewer){
		this.composite = composite;
		this.editor = editor;
		this.toolkit = toolkit;
		this.updown = updown;
		this.formViewer = formViewer;
		NO_OF_COLUMNS = 4;
	}
	
	public TableViewer getTableViewer(){
		return this.tableViewer;
	}

	public void createButton( FormToolkit toolkit , Composite composite , boolean updown){
		Composite base = toolkit.createComposite(composite,SWT.NULL);
		GridLayout layout = new GridLayout(1, false);
		layout.numColumns =1;
		layout.marginWidth = 2;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;

		base.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 25;
		base.setLayoutData(gd);
		formViewer.createToolbar(base, updown);
	}
	
	public Table createTable(List <String> columnNames) {
		createButton(toolkit,composite,updown);
		PROPS = new String[NO_OF_COLUMNS];
		int i = 0;
		for (String prop : columnNames) {
			PROPS[i++] = prop;
		}
		ArrayContentProvider ContentProvider =new ArrayContentProvider() ;
		RuleTemplateLabelProvider labelProvider = new RuleTemplateLabelProvider();
		Composite tableComp = new Composite(composite, GridData.BEGINNING);
		GridData gdtable = new GridData(GridData.FILL_BOTH);
		tableComp.setLayoutData(gdtable);
		GridLayout gridtablelayout = new GridLayout(1, false);
		tableComp.setLayout(gridtablelayout);
		tableViewer = new TableViewer(tableComp, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);
		tableViewer.setContentProvider(ContentProvider);
		tableViewer.setLabelProvider(labelProvider.getLabel());

		Table table = tableViewer.getTable();
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 500;
		table.setLayoutData(gd);
		for (int k = 0; k < NO_OF_COLUMNS; k++) {
			new TableColumn(table, SWT.NONE).setText(columnNames.get(k));
		}
		for (int it = 0; it < table.getColumnCount(); it++) {
			table.getColumn(it).pack();
		}
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setColumnProperties(PROPS);
		tableReference = table;
		autoTableLayout(table);
		tableViewer.refresh();
		table.pack();
		return table;
	}


	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		table.setLayout(autoTableLayout);
	}


	public void createCellEditor(List<String> tableColumnsWithType) {
		int editorCount = 0;
		CellEditor[] editors = new CellEditor[NO_OF_COLUMNS];
		int iterator = 0;
		while (iterator < tableColumnsWithType.size()) {
			String columnName = tableColumnsWithType.get(iterator);
			if (columnName.equals(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.type"))) {
				editors[editorCount++] = new TextCellEditor(tableReference);
			}
			if (columnName.equals(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.alias"))) {
				editors[editorCount++] = new TextCellEditor(tableReference);
			}
			if (columnName.equals(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.expression"))) {
				editors[editorCount++] = new TextCellEditor(tableReference);
			}
			if (columnName.equals(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.domainmodel"))) {
				editors[editorCount++] = new RuleTemplateTextAndDialogCellEditor(
						tableReference, columnName);
			}
			iterator++;
		}
		tableViewer
		.setCellModifier(new RuleTemplateTableCellModifier(tableViewer, editor));
		tableViewer.setCellEditors(editors);
	}

	public void refreshTable() {
		tableViewer.setInput(null);
		tableViewer.refresh();
	}


	class RuleTemplateTableCellModifier implements ICellModifier {

		private TableViewer viewer;
		private RuleTemplateFormEditor editor;
		public RuleTemplateTableCellModifier(TableViewer tableViewer,
				RuleTemplateFormEditor editor) {
			this.viewer = tableViewer;
			this.editor = editor;
		}


		@Override
		public boolean canModify(Object element, String property) {
			// TODO Auto-generated method stub
			if(property.equals(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.type"))){
				return false;
			}
			return true;

		}

		@Override
		public Object getValue(Object element, String property) {
			Binding p = (Binding) element;
			if(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.type").equals(property))
				return p.isArray()? p.getType() + "[]": p.getType();
			if(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.alias").equals(property))
				return p.getIdName();
			if (Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.expression").equals(property))
				return p.getExpression();
			if(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.domainmodel").equals(property))
				return p.getDomainModelPath();
			else
				return null;
		}

		@Override
		public void modify(Object element, String property, Object value) {
			String id = "";
			if (element instanceof Item)
				element = ((Item) element).getData();

			Binding p = (Binding) element;
			Object oldValue = null;

			if (Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.type").equals(property)) {
				oldValue = p.getType(); 
				p.setType((String)value);
			} else if (Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.alias").equals(property)) {
				id = p.getIdName();
				oldValue = p.getIdName();
				p.setIdName((String)value);
			} else if (Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.expression").equals(property)) {
				oldValue = p.getExpression();
				p.setExpression((String)value);
			} else if (Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.domainmodel").equals(property)) {
				oldValue = p.getDomainModelPath();
				p.setDomainModelPath((String)value);  
			}
			
			if(value!=null&&oldValue!=null){
				if (value.toString().equals(oldValue.toString()) && !value.toString().isEmpty()) {
					return ;
				}
				else {
					editor.modified();
				}
			}

			if(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.expression").equals(property))
				formViewer.updateDeclarationStatements(p.getType(),p.getIdName(),(String)oldValue, (String)value, RulesParser.RULE_TEMPLATE_DECL, RulesParser.BINDINGS_BLOCK, RulesParser.EXPRESSION,null);
			if(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.domainmodel").equals(property)){
				formViewer.updateDeclarationStatements(p.getType(),p.getIdName(),(String)oldValue, (String)value, RulesParser.RULE_TEMPLATE_DECL, RulesParser.BINDINGS_BLOCK, RulesParser.DOMAIN_MODEL,null);
			}
			if(Messages.getString("RuleTemplateBindingsFormViewer.ColumnName.alias").equals(property)){
				if (EntityNameHelper.isValidBEEntityIdentifier((String)value) &&  (!StudioUIUtils.isSymbolPresent(formViewer.getCommonCompilable().getSymbols().getSymbolList(), (String)value))) {
					formViewer.updateDeclarationStatements(p.getType(),id,(String)oldValue, (String)value, RulesParser.RULE_TEMPLATE_DECL, RulesParser.BINDINGS_BLOCK, RulesParser.BINDING_STATEMENT,null);
				}
			}
			viewer.refresh();
	}

	}
	
	class RuleTemplateLabelProvider  {

		public RuleTemplateLabelProvider(){
		}

		public ITableLabelProvider getLabel(){
			   
			   ITableLabelProvider  LabelProvider = new ITableLabelProvider() {
				
				@Override
				public void removeListener(ILabelProviderListener listener) {
				}
				
				@Override
				public boolean isLabelProperty(Object element, String property) {
					return false;
				}
				
				@Override
				public void dispose() {
				}
				
				@Override
				public void addListener(ILabelProviderListener listener) {
				}
				
				@Override
				public String getColumnText(Object element, int columnIndex) {
					Binding symbol= (Binding)element;
					switch (columnIndex){
					case 0:
						return symbol.isArray()? symbol.getType() + "[]": symbol.getType();
					case 1:
						return symbol.getIdName();
					case 2:
						return symbol.getExpression(); 	
					case 3:
						return symbol.getDomainModelPath();
					}
					return null;
				}

				@Override
				public Image getColumnImage(Object element, int columnIndex) {
					return null;
				}
					
			};
			  return LabelProvider; 
		   }
	}

}