package com.tibco.cep.studio.tester.ui.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.editor.data.TestDataEditor;
import com.tibco.cep.studio.tester.utilities.DateTimeEditor;
import com.tibco.cep.studio.ui.forms.components.UneditableComboBoxCellEditor;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

/**
 * 
 * @author aasingh
 *
 */
public class MultipleValuesCellDialog extends Dialog implements SelectionListener, ModifyListener {

	protected String title; 
	private Text payloadStringText;
	private String valueToBePassed,initializationString,columnName,columnType;
	private LinkedHashMap<Integer,Object> model;
	private TableViewer tableViewer;
	private TestDataEditor editor;
	private int tableCounter =0,counter =0;
	private String[][] domainEntries;
	private String[] items;
	private Map<String, String> tempMap = new HashMap<String, String>();

	/**
	 * @param parentShell
	 * @param dialogTitle
	 * @param items 
	 * @param table
	 * @param row
	 * @param column
	 */
	public MultipleValuesCellDialog( TestDataEditor editor,Shell parentShell,String columnName,String columnType,
			String dialogTitle ,String initializationString,  String [][] domainEntries, String[] items
			) {
		super(parentShell);
		this.columnName = columnName;
		this.columnType = columnType;
		this.title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		model = new LinkedHashMap<Integer,Object>();
		this.editor = editor;
		this.initializationString = initializationString;
		this.domainEntries = domainEntries;
		this.items=items;
	}
	
	public MultipleValuesCellDialog(Shell shell){
		super(shell);
	}

	/*
	 * (non-Javadoc) Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Value");
	}

	/*
	 * @see Dialog.createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// initialize the dialog units
		initializeDialogUnits(parent);
		//	Point defaultSpacing = LayoutConstants.getSpacing();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(parent);
		createDialogAndButtonArea(parent);
		return parent;
	}

	/*
	 * @see IconAndMessageDialog#createDialogAndButtonArea(Composite)
	 */
	protected void createDialogAndButtonArea(Composite parent) {
		// create the dialog area and button bar
		dialogArea = createDialogArea(parent);
		buttonBar = createButtonBar(parent);
		applyDialogFont(parent);
	}


	class EntityContentProviderSwt implements IStructuredContentProvider {
		@Override
		public void dispose() {}
		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {}
		@Override
		public Object[] getElements(Object element) {
			return (new ArrayList<Map.Entry<Integer, Object>>(model.entrySet())).toArray();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {

		Table tableReference;

		Composite composite = new Composite(parent,0);
		composite.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 300;
		data.widthHint = 300;
		composite.setLayoutData(data);
		final ToolBarProvider tbp=createToolBar(composite);
		tableViewer=new TableViewer(composite, SWT.BORDER|SWT.FULL_SELECTION|SWT.MULTI|SWT.V_SCROLL);

		tableViewer.setContentProvider(new EntityContentProviderSwt());
		tableViewer.setLabelProvider(new ITableLabelProvider() {

			@Override
			public String getColumnText(Object element, int columnIndex) {

				@SuppressWarnings("unchecked")
				Map.Entry<Integer,Object> mapEntry = (Map.Entry<Integer,Object>) element;

				switch(columnIndex){
				case 0:
					return ""+  mapEntry.getKey();
				case 1:
					if(columnType.equalsIgnoreCase("ConceptReference-Multiple") || columnType.equalsIgnoreCase("ContainedConcept-Multiple")){
						if(mapEntry.getValue()!=null && !(mapEntry.getValue().equals(""))){
								if(editor.getTestDataDesignViewer().testerPropertiesTable.getDependentConceptItems().get(columnName).size() >0){
									String val=editor.getTestDataDesignViewer().testerPropertiesTable.getDependentConceptItems().get(columnName).get(Integer.parseInt((String) mapEntry.getValue()));
									return val;
								}else{
									return "";
								}
						}
					}
					return (String) mapEntry.getValue();
				}

				return null;
			}
			@Override
			public void addListener(ILabelProviderListener listener) {}
			@Override
			public void dispose() {
			}
			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
			@Override
			public void removeListener(ILabelProviderListener listener) {
			}
			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		// Set up the table
		final Table table = tableViewer.getTable();
		String [] tableColumnNames = {"#",columnName};
		table.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL| GridData.GRAB_VERTICAL| GridData.FILL_BOTH));
		
		table.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		        String string = "";
		        TableItem[] selection = table.getSelection();
		        if(selection !=null&&selection.length>0 &&tbp!=null && tbp.getDeleteItem()!=null)
		        	tbp.getDeleteItem().setEnabled(true);
		      }
		    });
		
		for(int i=0;i<2;i++){
			TableColumn tableColumn=new TableColumn(table, SWT.NONE);
			tableColumn.setText(tableColumnNames[i]);
			
		}	
		for (int i = 0; i< table.getColumnCount();  i++) {  
			table.getColumn(i).pack();
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setColumnProperties(tableColumnNames);
		tableReference=table;
		autoTableLayout(table);
		table.getColumn(0).setResizable(false);
		initializeTable();
		tableViewer.setInput(model);
		
		tableViewer.getTable().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableViewer.getTable().getItemCount() > 0) {
					if (editor.isEnabled()) {
						tbp.getDeleteItem().setEnabled(true);
					}
				}
			}
		});
		
		
		table.pack();
		createCellEditor(tableReference,tableViewer);
		return null;
	}
	
	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(
				table);
		for (int loop = 0; loop < table.getColumns().length; loop++) {
			if(loop==0)
			autoTableLayout.addColumnData(new ColumnWeightData(20));
			else
			autoTableLayout.addColumnData(new ColumnWeightData(80));
		}
		table.setLayout(autoTableLayout);
	}

	public void initializeTable(){
		int start = -1;
		counter = 1;                          // indexing starts from One
		if (initializationString!=null){
			String temp1 = "";

			int length = initializationString.length();
			while(length>0){

				start =initializationString.indexOf(";");
				if(start != -1)
					temp1 = initializationString.substring(0,start);
				else {
					temp1 = initializationString;
					start = initializationString.length();
				}
				model.put(counter++, temp1);
				try{
					initializationString = initializationString.substring(start + 1);
				}
				catch(IndexOutOfBoundsException e){
					break;
				}
				length = initializationString.length();
			}
			tableCounter = counter;

		}
	}

	public void createCellEditor(Composite tableReference, final TableViewer tableViewer){
		CellEditor[] editors = new CellEditor[2];
		editors[0] = new TextCellEditor(tableReference);
		
		if(columnType.equalsIgnoreCase("datetime-Multiple")){
			editors[0] = new DateTimeEditor(tableReference,domainEntries);
			editors[1] = new DateTimeEditor(tableReference,domainEntries); 
		}
		else if(columnType.equalsIgnoreCase("CONTAINEDCONCEPT-Multiple")	|| columnType.equalsIgnoreCase("ConceptReference-Multiple")){
			editors[1]=new UneditableComboBoxCellEditor(tableReference,items);
			tempMap.put(columnName, columnType);
		}
		
		else editors[1] = new TextCellEditor(tableReference);

		tableViewer.setCellModifier(new ICellModifier(){

			@Override
			public boolean canModify(Object element, String property) {
				// TODO Auto-generated method stub
				if (property.equalsIgnoreCase("#")){
					return false;
				}
				return true;
			}

			@Override
			public Object getValue(Object element, String property) {
				Map.Entry list=(Map.Entry)element;
				if (tempMap.containsKey(property)) {
						if (tempMap.get(property).equalsIgnoreCase("ContainedConcept-Multiple")|| tempMap.get(property).equalsIgnoreCase("ConceptReference-Multiple")) {
							if (list.getValue() == null|| list.getValue().equals("")) {
							 	return 0;
						    }
							String columnValue = list.getValue().toString();						 
							List<String> values=editor.getTestDataDesignViewer().testerPropertiesTable.getDependentConceptItems().get(property);
							String parts[]=columnValue.split(TesterCoreUtils.MARKER);
							if(parts.length==2){
								parts[1]=parts[1].replace(TesterCoreUtils.FORWARD_SLASH, TesterCoreUtils.DOT);
								parts[1]=parts[1].replaceFirst("\\.", "").trim();
								for(String val:values){
									if(val.equalsIgnoreCase(parts[1]+TesterCoreUtils.DOT+"["+parts[0]+"]")){
										return values.indexOf(val);
									}
								}
							}
							else{
								return Integer.parseInt(list.getValue().toString().split(TesterCoreUtils.MARKER)[0]);

							}
						}
				//	}
				}
				if (property.equalsIgnoreCase("#")){
					return null;
				}
				else  {
					@SuppressWarnings("unchecked")
					Map.Entry<Integer, Object> mapEntry= (Map.Entry<Integer, Object>)element;  
					return mapEntry.getValue();
				}
			}

			@Override
			public void modify(Object element, String property, Object value) {
				// TODO Auto-generated method stub
				
				String valueString = value.toString();
				
				if(property.equalsIgnoreCase("#")){
					return;
				}

				if (element instanceof Item){
					element = ((Item)element).getData();
					@SuppressWarnings("unchecked")
					Map.Entry<Integer, Object> mapEntry= (Map.Entry<Integer, Object>)element;
					mapEntry.setValue(valueString);	
				}
				tableViewer.refresh();
			}
		});
		tableViewer.setCellEditors(editors);

	}

	private boolean isString(String name) {
 		if (name == "") return true;									// to allow empty fields.
 	    return name.matches("[a-zA-Z][a-zA-Z0-9\\. ]*");
 	}
	

	private ToolBarProvider createToolBar( Composite composite){
		final ToolBarProvider tbp=new ToolBarProvider(composite) {
			protected Image getDuplicateImage() {
				return StudioTesterUIPlugin.getDefault().getImage("/icons/duplicate_testdata.png");
			}
		};
		tbp.setShowBackgroundColor(false);
		tbp.setShowButtonText(true);
		tbp.createToolbar(false, false,false,false);
		tbp.getDeleteItem().setEnabled(false);

		Listener listener = new Listener() {
			
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				// TODO Auto-generated method stub

				ToolItem toolItem=(ToolItem) arg0.widget;
				if(toolItem.getText().equalsIgnoreCase(tbp.getAddItem().getText())){
					addAction();
				}
				else if(toolItem.getText().equalsIgnoreCase(tbp.getDeleteItem().getText())){
					removeAction(tbp);
				}

			}
		};

		tbp.setAllItemSelectionListener(listener);
		
		return tbp;

	}

	public void addAction(){
		model.put(tableCounter++,"");
		tableViewer.setInput(model);
		editor.modified();
		tableViewer.refresh();
	}


	public void removeAction(ToolBarProvider tbp){
		StructuredSelection strSel=(StructuredSelection) tableViewer.getSelection();
		@SuppressWarnings("unchecked")
		Map.Entry<Integer,Object> mapEntry = (Map.Entry<Integer, Object>) strSel.getFirstElement();
		if(mapEntry!=null){
			model.remove(mapEntry.getKey());
			tableViewer.setInput(model);
			editor.modified();
			tableViewer.refresh();
		}
		tbp.getDeleteItem().setEnabled(false);
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		persistValuesInCell();
		super.okPressed();
	}

	protected void persistValuesInCell(){
		Collection<Object> abc = model.values();
		Iterator<Object> it =  abc.iterator();
		String temp = "",temp1 = "";

		while(it.hasNext()){
			temp1 = it.next().toString();
			if(temp1 != "" && !"-1".equals(temp1))     temp += temp1 + ";";

			else continue;
		}
		if(temp.length()>0){
			temp = temp.substring(0, temp.length()-1);
		}
		valueToBePassed = temp;
	}


	public String passValue(){
		return valueToBePassed;
	}

	/**
	 * Get the number of columns in the layout of the Shell of the dialog.
	 */

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */

	/**
	 * @param filename
	 * @return
	 * @throws IOException
	 */


	@Override
	public void modifyText(ModifyEvent e) {
		if (e.getSource() == payloadStringText) {
			if (payloadStringText.getText().trim().length() > 0 ) {
				getButton(IDialogConstants.OK_ID).setEnabled(true);
			} else {
				getButton(IDialogConstants.OK_ID).setEnabled(false);
			}
		}

	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}


}