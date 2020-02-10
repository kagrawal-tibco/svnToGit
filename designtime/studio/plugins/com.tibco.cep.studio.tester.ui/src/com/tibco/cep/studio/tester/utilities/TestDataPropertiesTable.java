package com.tibco.cep.studio.tester.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.impl.RangeImpl;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.tester.core.model.TestDataModel;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.editor.data.TestDataEditor;
import com.tibco.cep.studio.tester.ui.utils.TesterUIUtils;
import com.tibco.cep.studio.tester.utilities.GenerateTaskModel.GenerationOptions;
import com.tibco.cep.studio.tester.utilities.GenerateTaskModel.GenerationType;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.forms.components.EditableComboBoxCellEditor;
import com.tibco.cep.studio.ui.forms.components.UneditableComboBoxCellEditor;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

/**
 * @author mgujrath
 * 
 */
public class TestDataPropertiesTable {

	private TableViewer tableViewer;
	private Table tableReference;
	private Composite composite;
	private Map<String, String> tableColumnsWithType;
	private String[] PROPS;
	private Entity entity;
	private ArrayList<String> actualColumnNames;
	private TestDataEditor editor;
	public TestDataModel model;
	private Map<String, String> tempMap = new HashMap<String, String>();
	public List<Object> tblObj = new ArrayList<Object>();
	private ArrayList<String> tableColumnNames=new ArrayList<String>();
	private Map<String,List<String>> dependentConceptItems=new HashMap<String,List<String>>();
	private Map<String,List<String>> columnDomainValues=new HashMap<String,List<String>>();
	private int rowCounter=0;
	public ToolBarProvider tbp;
	public Map<String, List<String>> getDependentConceptItems() {
		return dependentConceptItems;
	}

	public TestDataPropertiesTable(Composite composite, Entity entity,
			LinkedHashMap<String, String> tableColumnsWithType,
			TestDataEditor editor2, ArrayList<String> actualColumnNames) {

		this.composite = composite;
		this.editor = editor2;
		this.entity = entity;
		this.actualColumnNames = actualColumnNames;
		this.tableColumnsWithType = tableColumnsWithType;
		tableColumnNames.addAll(tableColumnsWithType.keySet());
		createDependentConceptItems();

	}
	
	private void createDependentConceptItems() {
		List<IResource> output = new ArrayList<IResource>();
		EList<PropertyDefinition> list = null;
		if (entity instanceof Scorecard) {
			list = ((Scorecard) editor.getEntity()).getAllProperties();
		} else if (entity instanceof Event) {
			list = ((Event) editor.getEntity()).getAllUserProperties();
		} else if (entity instanceof Concept) {
			list = ((Concept) editor.getEntity()).getAllProperties();
		}
		int iterator = 0;
		while (iterator < tableColumnsWithType.size()) {
			String columnType = tableColumnsWithType.get(tableColumnNames
					.get(iterator));
			if (columnType.equalsIgnoreCase("ConceptReference")	|| columnType.equalsIgnoreCase("ContainedConcept") || columnType.equalsIgnoreCase("ContainedConcept-Multiple") || columnType.equalsIgnoreCase("ConceptReference-Multiple")) {
				String conceptPath = getConceptPath(list, tableColumnNames,iterator);
				Entity dependentConcept = CommonIndexUtils.getEntity(editor.getProject().getName(), conceptPath);
				IFile file = IndexUtils.getFile(editor.getProject().getName(),dependentConcept);
				IPath path = file.getLocation().removeLastSegments(2);
				IProject project = file.getProject();
				try {
					IResource[] members = project.members();
					for (IResource res : members) {
						if (res.getType() == 1) {
							if (res.getFileExtension().equalsIgnoreCase("concepttestdata")) {
								output.add(res);
							}
						}
						if (res.getType() == 2) {
							iterateFolder((IFolder) res, output);
						}
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<String> columnNames = getAllColumnNames(dependentConcept);
				Map<String,Integer> filePath = new LinkedHashMap<String,Integer>();
				for (IResource res : output) {
					if (TesterCoreUtils.getEntityInfo(res.getLocation().toString()).equalsIgnoreCase(dependentConcept.getFullPath())) {
						filePath.put("/"+res.getFullPath().removeFirstSegments(1).toString(),calculateDependency(res.getLocation().toString(), dependentConcept,columnNames));
					}
				}
				List<String> arr = new ArrayList<String>();
				for (String fp:filePath.keySet()) {
					int rowCount=filePath.get(fp);
					fp=fp.split("\\.")[0];
					fp = fp.replace(TesterCoreUtils.FORWARD_SLASH, TesterCoreUtils.DOT);
					fp = fp.replaceFirst("\\.", "");
					for(int i=0;i<rowCount;i++){
						arr.add( fp +TesterCoreUtils.DOT+ "[" + i + "]");
					}
				}
				dependentConceptItems.put(tableColumnNames.get(iterator), arr);
			}
			iterator++;
		}
	}

	public void iterateFolder(IFolder folder, List output) {
		try {
			IResource members[] = folder.members();
			for (int i = 0; i < members.length; i++) {
				if (members[i].getType() == 1) {
					if (members[i].getFileExtension() != null) {
						if (members[i].getFileExtension().equalsIgnoreCase(
								"concepttestdata")) {
							output.add(members[i]);
						}
					}
				}
				if (members[i].getType() == 2) {
					iterateFolder((IFolder) members[i], output);
				}
			}
			return;
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	class EntityContentProviderSwt implements IStructuredContentProvider {
		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		}

		@Override
		public Object[] getElements(Object element) {
			return ((List<?>) element).toArray();
		}
	}

	abstract class EntityLabelProvider implements ITableLabelProvider {
	}

	class EntityCellModifier implements ICellModifier {

		private TableViewer viewer;
		TestDataEditor editor;

		EntityCellModifier(TableViewer tableViewer, TestDataEditor editor) {
			this.viewer = tableViewer;
			this.editor = editor;
		}

		@Override
		public boolean canModify(Object arg0, String arg1) {
			if (arg1.equalsIgnoreCase("Use")) {
				return false;
			}
			return true;
		}

		@Override
		public Object getValue(Object element, String property) {

			@SuppressWarnings("rawtypes")
			LinkedHashMap list = (LinkedHashMap) element;
			if (tempMap.containsKey(property)) {
				if (list.containsKey(property)) {
					if (tempMap.get(property).equalsIgnoreCase("boolean")) {
						if ((list.get(property)).toString().equalsIgnoreCase(
								("true")))
							return 0;
						else
							return 1;
					}
					if (tempMap.get(property).equalsIgnoreCase("ContainedConcept") ||
						tempMap.get(property).equalsIgnoreCase("ConceptReference") 
						/*tempMap.get(property).equalsIgnoreCase("ConceptReference-Multiple") ||
						tempMap.get(property).equalsIgnoreCase("ContainedConcept-Multiple")*/) {

					    if (list.get(property) == null|| list.get(property).equals("")) {
							 	return 0;
						}
						String columnValue = list.get(property).toString();						 
						List<String> values=dependentConceptItems.get(property);
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
							return Integer.parseInt(list.get(property).toString().split(TesterCoreUtils.MARKER)[0]);
						}
					}
					else if (tempMap.get(property).equalsIgnoreCase("String-Domain")
							|| tempMap.get(property).equalsIgnoreCase("long-Domain")
							|| tempMap.get(property).equalsIgnoreCase("int-Domain")
							|| tempMap.get(property).equalsIgnoreCase("double-Domain")
							|| tempMap.get(property).equalsIgnoreCase("datetime-Domain")
							|| tempMap.get(property).equalsIgnoreCase("boolean-Domain")) {
							
							List<String> values=columnDomainValues.get(property);
							if(values==null)
								return 0;
						
							if (list.get(property) == null|| list.get(property).equals("")) {
								return 0;
							}
							String currentValue = list.get(property).toString();
							if(currentValue!=null && !currentValue.isEmpty()){
								int index= values.indexOf(currentValue);
								
								if(index>=0)
									return index;
								else
									return currentValue;
							}
							else
								return 0;
					}
				}

			}
			if((list.get(property).toString()).contains(TesterCoreUtils.MARKER)){
				return (list.get(property).toString()).charAt(0) +"";
			}
			return list.get(property).toString();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void modify(Object element, String property, Object value) {

			if (element instanceof Item)
				element = ((Item) element).getData();
			String columnType = tableColumnsWithType.get(property);
			@SuppressWarnings("rawtypes")
			LinkedHashMap p = (LinkedHashMap) element;
			String oldValue = null;
			String tempValue = value.toString();
			if (tempMap.containsKey(property)) {

				if (tempMap.get(property).equalsIgnoreCase("boolean")) {
					tempValue = PropertyTableConstants.booleanItems[Integer.parseInt(value.toString())];
					oldValue = p.get(property).toString();
					p.put(property, tempValue);
				}
				if (tempMap.get(property).equalsIgnoreCase("ContainedConcept")|| tempMap.get(property).equalsIgnoreCase("ConceptReference") ||
						tempMap.get(property).equalsIgnoreCase("ContainedConcept-Multiple")|| tempMap.get(property).equalsIgnoreCase("ConceptReference-Multiple")) {
					
					if (value.toString().equalsIgnoreCase("-1")) {
						tempValue = "";
					}else {
						tempValue = value.toString();
					}
					oldValue = p.get(property).toString();
					p.put(property, tempValue);
				}
				else if (tempMap.get(property).equalsIgnoreCase("String-Domain")
						|| tempMap.get(property).equalsIgnoreCase("long-Domain")
						|| tempMap.get(property).equalsIgnoreCase("int-Domain")
						|| tempMap.get(property).equalsIgnoreCase("double-Domain")
						|| tempMap.get(property).equalsIgnoreCase("datetime-Domain")
						|| tempMap.get(property).equalsIgnoreCase("boolean-Domain")) {
						
						String type=tempMap.get(property).substring(0, tempMap.get(property).indexOf("-Domain"));
					
						List<String> values=columnDomainValues.get(property);
						if (value.toString().equalsIgnoreCase("-1")) {
							tempValue = "";
						}else {
							if(value instanceof Integer)
								tempValue = values.get(Integer.parseInt(value.toString()));
							else
								tempValue=value.toString();
						}
						
						if (type.equalsIgnoreCase("int")) {
							if (!StudioUIUtils.isNumeric(tempValue)) {
								reportErrorMessage();
							} else {
								oldValue = p.get(property).toString();
								p.put(property, tempValue);
							}
						}
						else if (type.equalsIgnoreCase("boolean")) {
							if("true".equals(tempValue)||"false".equals(tempValue)){
								oldValue = p.get(property).toString();
								p.put(property, tempValue);
							}
							else
								reportErrorMessage();
						}
						else if (type.equalsIgnoreCase("datetime")) {
								oldValue = p.get(property).toString();
								p.put(property, tempValue);
						}else if (type.equalsIgnoreCase("Long")) {
							try {
								if (tempValue.isEmpty()){
									oldValue = p.get(property).toString();
									p.put(property, tempValue); // to allow blank fields
								}
								else {
									Long.parseLong(tempValue);
									oldValue = p.get(property).toString();
									p.put(property, tempValue);
								}
							} catch (Exception e) {
								reportErrorMessage();
							}

						}else if (type.equalsIgnoreCase("Double")) {
							try {
								if (tempValue.isEmpty()){
									oldValue = p.get(property).toString();
									p.put(property, tempValue); // to allow blank fields
								}
								else {
									Double.parseDouble(tempValue);
									oldValue = p.get(property).toString();
									p.put(property, tempValue);
								}
							} catch (NumberFormatException e) {
								reportErrorMessage();
							}
//							if (tempValue.contains(".")) {
//								String[] parts = tempValue.split("\\.");
//								for (String string : parts) {
//									if (!StudioUIUtils.isNumeric(string)) {
//										reportErrorMessage();
//										flag = 1;
//									}
//								}
//								if (flag == 0) {
//									p.put(property, tempValue);
//								}
//							} else {
//								if (!StudioUIUtils.isNumeric(tempValue)) {
//									reportErrorMessage();
//								} else {
//									oldValue = p.get(property).toString();
//									p.put(property, tempValue);
//								}
//							}
						}else{
								oldValue = p.get(property).toString();
								p.put(property, tempValue);
						}
				}
			}else {
				if (columnType.equalsIgnoreCase("int")) {
					if (!StudioUIUtils.isNumeric(tempValue)) {
						reportErrorMessage();
					} else {
						oldValue = p.get(property).toString();
						p.put(property, tempValue);
					}
				}else if (columnType.equalsIgnoreCase("datetime")) {
						oldValue = p.get(property).toString();
						p.put(property, tempValue);
				}else if (columnType.equalsIgnoreCase("Long")) {
					try {
						if (tempValue.isEmpty()){
							oldValue = p.get(property).toString();
							p.put(property, tempValue); // to allow blank fields
						}
						else {
							Long.parseLong(tempValue);
							oldValue = p.get(property).toString();
							p.put(property, tempValue);
						}
					} catch (Exception e) {
						reportErrorMessage();
					}

				}else if (columnType.equalsIgnoreCase("Double")) {
					try {
						if (tempValue.isEmpty()){
							oldValue = p.get(property).toString();
							p.put(property, tempValue); // to allow blank fields
						}
						else {
							Double.parseDouble(tempValue);
							oldValue = p.get(property).toString();
							p.put(property, tempValue);
						}
					} catch (NumberFormatException e) {
						reportErrorMessage();
					}
				}else if (columnType.equalsIgnoreCase("long-Multiple")
						|| columnType.equalsIgnoreCase("int-Multiple")
						|| columnType.equalsIgnoreCase("double-Multiple")
						|| columnType.equalsIgnoreCase("boolean-Multiple")) {
					if (columnType.equalsIgnoreCase("int-Multiple")) {
						boolean isValidValue=true;
						String[] values = tempValue.split(";");
						for(int i=0 ; i < values.length;i++){
							if(!StudioUIUtils.isNumeric(values[i])){
								isValidValue = false;
								break;
							}
						}
						if (!isValidValue) {
							reportErrorMessage();
						} else {
							oldValue = p.get(property).toString();
							p.put(property, tempValue);
						}
					}else if (columnType.equalsIgnoreCase("long-Multiple")) {
						try {
							String[] values = tempValue.split(";");
							for(int i=0 ; i < values.length;i++){
								Long.parseLong(values[i]);
							}
							oldValue = p.get(property).toString();
							p.put(property, tempValue);
						} catch (Exception e) {
							reportErrorMessage();
						}

					}else if (columnType.equalsIgnoreCase("double-Multiple")) {
						boolean isValidValue=true;
						String[] values = tempValue.split(";");
						for(int i=0 ; i < values.length;i++){
							int flag = 0;
							if (values[i].contains(".")) {
								String[] parts = values[i].split("\\.");
								for (String string : parts) {
									if (!StudioUIUtils.isNumeric(string)) {
										flag = 1;
									}
								}
								if (flag == 1) {
									isValidValue = false;
									break;
								}
							}else {
								if (!StudioUIUtils.isNumeric(values[i])) {
									isValidValue = false;
									break;
								}
							}
						}
						if (!isValidValue) {
							reportErrorMessage();
						} else {
							oldValue = p.get(property).toString();
							p.put(property, tempValue);
						}
				    }else if(columnType.equalsIgnoreCase("boolean-Multiple")){
				    	boolean isValidValue=true;
						String[] values = tempValue.split(";");
						for(int i=0 ; i < values.length;i++){
							if(!("true".equals(values[i]) || "false".equals(values[i]))){
								isValidValue = false;
								break;
							}
						}
						if (!isValidValue) {
							reportErrorMessage();
						} else {
							oldValue = p.get(property).toString();
							p.put(property, tempValue);
						}
				    }
				}
				else{
						oldValue = p.get(property).toString();
						p.put(property, tempValue);
				}
			//	oldValue = p.get(property).toString();
			//	p.put(property, tempValue);
			}
			//oldValue = p.get(property).toString();
			//p.put(property, tempValue);
			
			if (oldValue != null && value != null) {
				if (!oldValue.equals(value.toString())) {
					editor.modified();
				}
			}
			viewer.refresh();
			model.getVectors().clear();
			TableItem[] tblItem = viewer.getTable().getItems();
			for (int i = 0; i < tblItem.length; i++) {

				LinkedHashMap<String, String> lhm = (LinkedHashMap<String, String>) (tblItem[i])
						.getData();
				Vector<String> vector = new Vector<String>();
				vector.add(entity.getFullPath());
				if (entity instanceof Event) {
					vector.add(model.getTestData().get(i).get(1));
				}
				vector.add(model.getTestData().get(i).get(0));
				Set<String> keySet = lhm.keySet();
				for (String str : keySet) {
					vector.add(lhm.get(str).toString());
				}
				model.getVectors().add(vector);

			}
		}
	}
	
	private void reportErrorMessage() {
//		Display.getDefault().asyncExec(new Runnable() {
//		public void run() {
			MessageDialog.openError(Display.getDefault()
					.getActiveShell(), "Invalid Property",
					"Invalid Property.");
//		}
//	});
	}

	public void updateData() {
		model.getVectors().clear();
		TableItem[] tblItem = tableViewer.getTable().getItems();
		for (int i = 0; i < tblItem.length; i++) {
			LinkedHashMap<String, String> lhm = (LinkedHashMap) (tblItem[i])
					.getData();
			Vector<String> vector = new Vector<String>();
			vector.add(entity.getFullPath());
			if (entity instanceof Event) {
				vector.add(model.getTestData().get(i).get(1));
			}
			vector.add(model.getTestData().get(i).get(0));
			Set<String> keySet = lhm.keySet();
			for (String str : keySet) {
				String val = lhm.get(str);
				if (val != null) {
					vector.add(val.toString());
				}
			}
			model.getVectors().add(vector);
		}

	}

	public Table createTable(Composite composite, final Entity entity) {
		String entityType = null;
		boolean flag = false;
		// input=new ArrayList<LinkedHashMap<String,String>>();
		LinkedHashMap<String, String> list = new LinkedHashMap<String, String>();
		if (entity instanceof Scorecard) {
			entityType = "Scorecard";
			flag = true;
		} else if (entity instanceof Event) {
			entityType = "Event";
		} else if (entity instanceof Concept) {
			if (flag != true) {
				entityType = "Concept";
			}
		}
		try {
			model = TesterCoreUtils.getDataFromXML(editor.getTestFilePath(),
					entityType, editor.getEntity(), tableColumnNames);
			if(TesterCoreUtils.isIncompatible){
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Error","Test Data is not compatible with the entities in the project.");
				TesterCoreUtils.isIncompatible=false;
				return null;
			}
			// model.setTableColumnNames(tableColumnNames);
			// model.setEntity(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int inputCounter = 0;
		
		for (List<String> str : model.getTestData()) {
			list = new LinkedHashMap<String, String>();
			inputCounter = 0;

			String columnName="";
			for (String s : str) {
				columnName=actualColumnNames.get(inputCounter++);
				if(!TesterCoreUtils.UID_COL_NAME.equals(columnName))
					list.put(columnName, s);
				
			}
			list.put(TesterCoreUtils.UID_COL_NAME,rowCounter+"");
			rowCounter++;
			model.getInput().add(list);
		}
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		tbp = createToolBar(entity);
		tableViewer = new TableViewer(composite, SWT.CHECK | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);
		if (entity instanceof Event) {
			tableViewer.setContentProvider(new EntityContentProviderSwt());
			tableViewer.setLabelProvider(new EntityLabelProvider() {

				@SuppressWarnings("rawtypes")
				@Override
				public String getColumnText(Object element, int columnIndex) {
					@SuppressWarnings("rawtypes")
					HashMap map = (HashMap) element;
					switch (columnIndex) {
					case 0:
						return null;
					}
					Object val = map.get(actualColumnNames.get(columnIndex - 1));
					if (val != null) {
						String columnValue = val.toString();
						return columnValue;
					}
					return null;
				}

				@Override
				public void addListener(ILabelProviderListener listener) {
				}

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

		}
		if (entity instanceof Concept) {

			tableViewer.setContentProvider(new EntityContentProviderSwt());
			tableViewer.setLabelProvider(new EntityLabelProvider() {

				@Override
				public Image getColumnImage(Object element, int columnIndex) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getColumnText(Object element, int columnIndex) {
					// TODO Auto-generated method stub
					@SuppressWarnings("rawtypes")
					HashMap map = (HashMap) element;
					switch (columnIndex) {
					case 0:
						return null;
					}
					Object mapVal = map.get(actualColumnNames.get(columnIndex - 1));
					String columnValue = mapVal != null ? mapVal.toString() : "";
					if (dependentConceptItems.size() > 0) {
						if (tableColumnsWithType.get(actualColumnNames.get(columnIndex - 1)).equalsIgnoreCase("ConceptReference") ||
						    tableColumnsWithType.get(actualColumnNames.get(columnIndex - 1)).equalsIgnoreCase("ContainedConcept") ||
						    tableColumnsWithType.get(actualColumnNames.get(columnIndex - 1)).equalsIgnoreCase("ContainedConcept-Multiple") ||
						    tableColumnsWithType.get(actualColumnNames.get(columnIndex - 1)).equalsIgnoreCase("ConceptReference-Multiple")) {
							if (columnValue.equalsIgnoreCase("")) {
								return columnValue;
							}
							List<String> values=dependentConceptItems.get(actualColumnNames.get(columnIndex - 1));
							String parts[]=columnValue.split(TesterCoreUtils.MARKER);
							if(parts.length==2){
								parts[1]=parts[1].replace(TesterCoreUtils.FORWARD_SLASH, TesterCoreUtils.DOT);
								parts[1]=parts[1].replaceFirst("\\.", "").trim();
								for(String val:values){
									if(val.equalsIgnoreCase(parts[1]+TesterCoreUtils.DOT+"["+parts[0]+"]")){
										return val;
									}
								}
							}
							else{
								if(columnValue.contains(";")){
									String vals[]=columnValue.split(";");
									String newColumnValue="";
									for(String val:vals){
										if(Integer.parseInt(val)>=0)
											newColumnValue=newColumnValue + (dependentConceptItems.get(actualColumnNames.get(columnIndex - 1))).get(Integer.parseInt(val))+";";
										else
											newColumnValue=newColumnValue+";";
									}
									if(newColumnValue.length()>0)
										columnValue=newColumnValue.substring(0,(newColumnValue.length()-1));
									else
										columnValue="";
									
								}else{
									columnValue = (dependentConceptItems.get(actualColumnNames.get(columnIndex - 1))).get(Integer.parseInt(columnValue));
								}
							}
						}
					}
					return columnValue;
				}

				@Override
				public void addListener(ILabelProviderListener listener) {
					// TODO Auto-generated method stub

				}

				@Override
				public void dispose() {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean isLabelProperty(Object element, String property) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void removeListener(ILabelProviderListener listener) {
					// TODO Auto-generated method stub

				}

			});

		}
		// Set up the table
		Table table = tableViewer.getTable();

		final Button selectAllbutton = new Button(composite, SWT.CHECK);
		selectAllbutton.setText("Select / DeSelect All");
		selectAllbutton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] tblItem = tableViewer.getTable().getItems();
				for (TableItem item : tblItem) {
					item.setChecked(selectAllbutton.getSelection());
					for (int index = 0; index < model.getSelectRowData().size(); index++) {
						model.getSelectRowData().set(index,
								selectAllbutton.getSelection());
					}
					editor.modified();
					updateData();
				}
			}
		});
		
		table.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL| GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		TesterUIUtils.setColumnImages(table,tableColumnsWithType,tableColumnNames);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		PROPS = new String[tableColumnsWithType.size()];
		int i = 0;
		for (String prop : tableColumnsWithType.keySet()) {
			PROPS[i++] = prop;
		}
		tableViewer.setColumnProperties(PROPS);
		tableReference = table;
		
		table.getColumn(1).setWidth(0);
		autoTableLayout(table);
		
		table.getColumn(0).setResizable(false);
		
		if(table.getColumns()!=null&&table.getColumns().length!=0)
			table.getColumn(table.getColumns().length-1).setResizable(false);
		
		tableViewer.setInput(model.getInput());
		setTablecheckBox();
		tableViewer.getTable().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				if (event.detail == SWT.CHECK) {
					TableItem item = (TableItem) event.item;
					if (item.isDisposed()) {
						return;
					}
					boolean oldValue = model.getSelectRowData().get(model.getInput().indexOf((LinkedHashMap) item.getData()));
					model.getSelectRowData().set(model.getInput().indexOf((LinkedHashMap) item.getData()),item.getChecked());

					model.getVectors().clear();

					TableItem[] tblItem = tableViewer.getTable().getItems();
					for (int i = 0; i < tblItem.length; i++) {
						LinkedHashMap<String, String> lhm = (LinkedHashMap) (tblItem[i])
								.getData();
						Vector<String> vector = new Vector<String>();
						vector.add(entity.getFullPath());
						if (entity instanceof Event) {
							vector.add(model.getTestData().get(i).get(1));
						}
						vector.add(model.getTestData().get(i).get(0));
						Set<String> keySet = lhm.keySet();
						for (String str : keySet) {
							if ("ExtID".equals(str) || "Payload".equals(str)) {
//								continue; // already added above
							}
							vector.add(lhm.get(str).toString());
						}
						model.getVectors().add(vector);
					}

					if(!(oldValue == item.getChecked())){
						editor.modified();
					}
				}
			}
		});
		
		tbp.getDeleteItem().setEnabled(false);
		tbp.getDuplicateItem().setEnabled(false);
		tableViewer.getTable().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (tableViewer.getTable().getItemCount() > 0) {
					if (editor.isEnabled()) {
						tbp.getDeleteItem().setEnabled(true);
						tbp.getDuplicateItem().setEnabled(true);
					}
				}
			}
		});
		table.pack();
		return table;
	}

	public void setTablecheckBox() {
		TableItem[] tblItem = tableViewer.getTable().getItems();
		for (TableItem item : tblItem) {
			item.setChecked(model.getSelectRowData().get(
					model.getInput().indexOf((LinkedHashMap) item.getData())));
		}
	}
	protected void autoTableLayout(Table table) {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(table);
		autoTableLayout.addColumnData(new ColumnWeightData(10, false));
		for (int loop = 1; loop < table.getColumns().length; loop++) {
			if(TesterCoreUtils.UID_COL_NAME.equals(table.getColumn(loop).getText()))
				autoTableLayout.addColumnData(new ColumnWeightData(0,false));
			else
				autoTableLayout.addColumnData(new ColumnWeightData(20));
			
		}
		table.setLayout(autoTableLayout);
	}

	public void createCellEditor(LinkedHashMap<String, String> tableColumnsWithType) {
		int editorCount = 0;
		CellEditor[] editors = new CellEditor[tableColumnsWithType.size()];
		String[][] domainEntries = null;
		int iterator = 0; 
		EList<PropertyDefinition> list = null;

		if (entity instanceof Scorecard) {
			list = ((Scorecard) editor.getEntity()).getAllProperties();
		} else if (entity instanceof Event) {
			list = ((Event) editor.getEntity()).getAllUserProperties();
		} else if (entity instanceof Concept) {
			list = ((Concept) editor.getEntity()).getAllProperties();
		}
		
		while (iterator < tableColumnsWithType.size()) {

			String columnType = tableColumnsWithType.get(tableColumnNames
					.get(iterator));
			if (columnType.equalsIgnoreCase("use")) {
				editors[editorCount++] = new CheckboxCellEditor(tableReference);
				iterator++;
				continue;
			}
			else if (columnType.equalsIgnoreCase("payload")) {
				if (TesterCoreUtils.isPayLoadPropertyUIEnabled) {
					editors[editorCount++] = new PayLoadCellEditor(
							tableReference, columnType, editor);
				}
				iterator++;
				continue;
			}
			else if (columnType.equalsIgnoreCase("datetime")) {
				domainEntries = getDomainEntries(iterator);
				editors[editorCount++] = new DateTimeEditor(tableReference,domainEntries); ;
				iterator++;
				continue;
			}
			else if (columnType.equalsIgnoreCase("String-Multiple")
					|| columnType.equalsIgnoreCase("long-Multiple")
					|| columnType.equalsIgnoreCase("int-Multiple")
					|| columnType.equalsIgnoreCase("double-Multiple")
					|| columnType.equalsIgnoreCase("datetime-Multiple")
					|| columnType.equalsIgnoreCase("CONTAINEDCONCEPT-Multiple")
					|| columnType.equalsIgnoreCase("ConceptReference-Multiple")
					|| columnType.equalsIgnoreCase("boolean-Multiple")) {
				
				String items[]=null;
				for(String str:dependentConceptItems.keySet()){
					if(tableColumnNames.get(iterator).equalsIgnoreCase(str)){
						int index=0;
						List<String> itemList=dependentConceptItems.get(str);
						items=new String[itemList.size()];
						for(String str1:itemList){
							items[index++]=str1;
						}
					}
				}
			
				domainEntries = getDomainEntries(iterator);
				editors[editorCount++] = new MultipleValuesCellDialogEditor(
						tableReference, columnType,
						tableColumnNames.get(iterator), editor, model,domainEntries,items);
				if(columnType.equalsIgnoreCase("CONTAINEDCONCEPT-Multiple")|| columnType.equalsIgnoreCase("ConceptReference-Multiple")){
					tempMap.put(tableColumnNames.get(iterator), columnType);
				}
				iterator++;
				continue;
			}
			else if (columnType.equalsIgnoreCase("boolean")) {
				editors[editorCount++] = new UneditableComboBoxCellEditor(tableReference,
						PropertyTableConstants.booleanItems);
				tempMap.put(tableColumnNames.get(iterator), columnType);
			}
			
			else if(columnType.equalsIgnoreCase("ConceptReference")||columnType.equalsIgnoreCase("ContainedConcept")){
				
				String items[]=null;
				for(String str:dependentConceptItems.keySet()){
					if(tableColumnNames.get(iterator).equalsIgnoreCase(str)){
						int index=0;
						List<String> itemList=dependentConceptItems.get(str);
						items=new String[itemList.size()];
						for(String str1:itemList){
							items[index++]=str1;
						}
					}
				}
				editors[editorCount++]=new UneditableComboBoxCellEditor(tableReference,items);
				tempMap.put(tableColumnNames.get(iterator), columnType);
			}
			else if (columnType.equalsIgnoreCase("String-Domain")
					|| columnType.equalsIgnoreCase("long-Domain")
					|| columnType.equalsIgnoreCase("int-Domain")
					|| columnType.equalsIgnoreCase("double-Domain")
					|| columnType.equalsIgnoreCase("datetime-Domain")
					|| columnType.equalsIgnoreCase("boolean-Domain")) {
				
				Map<String, DomainEntry> domainEntriesMap = getDomainEntriesMap(iterator);
				ArrayList<String> domainValues=new ArrayList<String>();
				for(Entry<String, DomainEntry> entry : domainEntriesMap.entrySet() ){
					if(!(entry.getKey().equalsIgnoreCase("*") || entry.getValue() instanceof RangeImpl))
						domainValues.add(entry.getKey());
				}
				
				editors[editorCount++]=new EditableComboBoxCellEditor(tableReference,domainValues.toArray(new String[domainValues.size()]),SWT.NONE);
				
				columnDomainValues.put(tableColumnNames.get(iterator), domainValues);
				
				tempMap.put(tableColumnNames.get(iterator), columnType);
			}
			else {
				editors[editorCount++] = new TextCellEditor(tableReference);
			}
			iterator++;
		}
		tableViewer.setCellModifier(new EntityCellModifier(this.tableViewer,editor));
		tableViewer.setCellEditors(editors);
	}
	
	private ToolBarProvider createToolBar(final Entity entity) {
		final ToolBarProvider tbp = new ToolBarProvider(composite) {
			protected Image getDuplicateImage() {
				return StudioTesterUIPlugin.getDefault().getImage(
						"/icons/duplicate_testdata.png");
			}
		};
		tbp.setShowBackgroundColor(false);
		tbp.setShowButtonText(true);
		ToolBar toolbar = tbp.createToolbar(false, false, true,true);
		ToolItem generateItem = new ToolItem(toolbar, SWT.PUSH);
		generateItem.setImage(StudioUIPlugin.getDefault().getImage("/icons/doc/generate_doc.gif"));
		generateItem.setToolTipText("Generate Random Test Data");
		generateItem.setText("Generate Random Test Data");
		generateItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				generateRowsAction();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		toolbar.pack();
		Listener listener = new Listener() {

			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				ToolItem toolItem = (ToolItem) arg0.widget;
				if (toolItem.getText().equalsIgnoreCase(
						tbp.getAddItem().getText())) {
					addAction(tbp);
				} else if (toolItem.getText().equalsIgnoreCase(
						tbp.getDeleteItem().getText())) {
					removeAction(tbp);
				} else if (toolItem.getText().equalsIgnoreCase(
						tbp.getDuplicateItem().getText())) {
					duplicateAction(tbp);
				} else if (toolItem.getText().equalsIgnoreCase(
						tbp.getFitcontentItem().getText())) {
					fitContentAction();
				} else if (toolItem.getText().equalsIgnoreCase(
						"Generate Random Test Data")) {
					generateRowsAction();
				}
			}
		};

		tbp.setAllItemSelectionListener(listener);
		return tbp;
	}

	protected void generateRowsAction() {
		GenerateTestDataDialog diag = new GenerateTestDataDialog(this.editor.getSite().getShell(), this.entity);
		int ret = diag.open();
		if (ret != Dialog.OK) {
			return;
		}
		GenerateTaskModel generationTask = diag.getGenerationTask();
		int numRows = generationTask.getNumRows();
		Map<PropertyDefinition, GenerationOptions> options = generationTask.getOptions();
		for (int i = 0; i < numRows; i++) {
			ArrayList<String> list = new ArrayList<String>();
			model.getSelectRowData().add(true);
			if (entity instanceof Event) {
				list.add("");
			}
			list.add("");
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			for (String str : actualColumnNames) {
				if(TesterCoreUtils.UID_COL_NAME.equals(str))
				{
					map.put(str,(rowCounter++)+"");
					list.add((rowCounter)+"");
				}
				else
				{
					GenerationOptions option = getGenerationOptions(str, options);
					String value = getGeneratedValue(str, option);
					map.put(str, value);
					list.add("");
				}
			}
			
			model.getTestData().add(list);
			model.getInput().add(map);
		}

		setTablecheckBox();
		tableViewer.setInput(model.getInput());
		tableViewer.refresh();
		editor.modified();
		updateData();
	
	}

	private String getGeneratedValue(String str, GenerationOptions option) {
		if (option == null || !option.isEnabled()) {
			return "";
		}
		if (option.isAllowNull()) {
			double nullProbability = option.getNullProbability();
			Random randomNum = new Random();
			double randDouble = randomNum.nextDouble()*100;
			if (randDouble < nullProbability) {
				return "";
			}
		}
		GenerationType generationType = option.getGenerationType();
		PropertyDefinition prop = ((PropertyDefinition) option.getProperty());
		PROPERTY_TYPES type = prop.getType();
		boolean useLetters = true;
		boolean useNumbers = true;

		EList<DomainInstance> domainInstances = prop.getDomainInstances();
		List<DomainEntry> allEntries = getAllEntries(domainInstances);
		
		String min = option.getMin();
		if (min == null || min.length() == 0) {
			if (type == PROPERTY_TYPES.LONG || type == PROPERTY_TYPES.DOUBLE || type == PROPERTY_TYPES.INTEGER) {
				min = "0.0";
			} else if (type == PROPERTY_TYPES.DATE_TIME) {
		        SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
		        min = sdf.format(new Date());
			}
			else {
				min = "4.0";
			}
		}
		String max = option.getMax();
		if (max == null || max.length() == 0) {
			if (type == PROPERTY_TYPES.LONG || type == PROPERTY_TYPES.DOUBLE || type == PROPERTY_TYPES.INTEGER) {
				max = "10000.0";
			} else if (type == PROPERTY_TYPES.DATE_TIME) {
		        SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
		        GregorianCalendar cal = new GregorianCalendar();
		        cal.add(Calendar.YEAR, 1);
		        max = sdf.format(cal.getTime());
			} else {
				max = "4.0";
			}
		}
		double minInt = 0;
		double maxInt = 0;
		if (type == PROPERTY_TYPES.LONG || type == PROPERTY_TYPES.DOUBLE || type == PROPERTY_TYPES.INTEGER || type == PROPERTY_TYPES.STRING) {
			minInt = Double.valueOf(min);
			maxInt = Double.valueOf(max);
		}
		
		if (allEntries.size() > 0) {
			if (generationType != GenerationType.INCREMENTAL) {
				minInt = 0;
			}
			maxInt = allEntries.size() - 1;
		}

		Random randomNum = new Random();
		switch (generationType) {
		case CONSTANT:
			return option.getPrefix();

		case PREFIXED: {
			double len = minInt;
			if (maxInt > minInt) {
				len += randomNum.nextInt((int) (maxInt - minInt + 1));
			}
			
			return option.getPrefix() + RandomStringUtils.random((int)len, useLetters, useNumbers);
		}
			
		case RANDOM: {
			double len = minInt;
			if (maxInt > minInt) {
				len += randomNum.nextInt((int) (maxInt - minInt + 1));
			}
			if (allEntries.size() > 0) {
				DomainEntry entry = allEntries.get((int) len);
				return entry.getValue();
			}
			if (type == PROPERTY_TYPES.DOUBLE) {
				return String.valueOf(((double)randomNum.nextDouble()*(maxInt - minInt)));
			}
			if (type == PROPERTY_TYPES.LONG || type == PROPERTY_TYPES.INTEGER) {
				return String.valueOf((int)len);
			}
			if (type == PROPERTY_TYPES.BOOLEAN) {
				return Boolean.toString(randomNum.nextBoolean());
			}
			if (type == PROPERTY_TYPES.DATE_TIME) {
				// lower and upper are dates, parse and find middle
				SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
				try {
					long start = sdf.parse(min).getTime();
					long end = sdf.parse(max).getTime();
					long time = start;
					if (end > start) {
						time += ((long)(randomNum.nextDouble()*(end - start)));
					}
					String format = sdf.format(new Date(time));
					return format;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			} 
			return RandomStringUtils.random((int)len, useLetters, useNumbers);
		}
		
		case INCREMENTAL: {
			String currVal = option.getMin();
			try {
				double d = Double.parseDouble(option.getIncrementBy());
				if (type == PROPERTY_TYPES.DATE_TIME) {
					// lower and upper are dates
					SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
					try {
						Date newMin = getNewDate(option);
						long end = sdf.parse(max).getTime();
						long time = newMin.getTime();
						if (time > end) {
							time = end;
						}
						String format = sdf.format(new Date(time));
						option.setMin(format);
						return format;
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				}
				d += Double.parseDouble(currVal);
				if (d > Double.parseDouble(option.getMax())) {
					return option.getMax();
				}
				String newVal = "";
				if (type == PROPERTY_TYPES.INTEGER || type == PROPERTY_TYPES.LONG) {
					newVal = String.valueOf((long)d);					
				} else {
					newVal = String.valueOf(d);
				}
				option.setMin(newVal);
				return newVal;
			} catch (NumberFormatException e) {
			}
			break;
		}
			
		case ENUMERATE:
			if (allEntries.size() == 0) {
				return "";
			}
			int enumCtr = option.getEnumCtr();
			try {
				if (enumCtr >= allEntries.size()) {
					enumCtr = 0;
				}
				option.setEnumCtr(enumCtr+1);
				DomainEntry entry = allEntries.get(enumCtr);
				if (entry instanceof Range) {
					String lower = ((Range) entry).getLower();
					String upper = ((Range) entry).getUpper();

					boolean includeLower = ((Range) entry).isLowerInclusive();
					boolean includeUpper = ((Range) entry).isUpperInclusive();
					
					if (type == PROPERTY_TYPES.DATE_TIME) {
						// lower and upper are dates, parse and find middle
						SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
						try {
							long start = sdf.parse(lower).getTime();
							if (!includeLower) {
								start++;
							}
							long end = sdf.parse(upper).getTime();
							if (!includeUpper) {
								end--;
							}
							long time = start + ((long)(randomNum.nextDouble()*(end - start)));
							String format = sdf.format(new Date(time));
							return format;
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
					} else {
						double l = Double.parseDouble(lower);
						if (!includeLower) {
							l++;
						}
						double u = Double.parseDouble(upper);
						if (!includeUpper) {
							u--;
						}
						double base = l;
						if (u > l) {
							base += randomNum.nextInt((int) (maxInt - minInt + 1));
						}
						if (type == PROPERTY_TYPES.DOUBLE) {
							return String.valueOf(base + randomNum.nextDouble());
						}
						if (type == PROPERTY_TYPES.LONG || type == PROPERTY_TYPES.INTEGER) {
							return String.valueOf((int)base);
						}
					}
				} else {
					return entry.getValue();
				}
			} catch (NumberFormatException e) {
			}
			
		default:
			break;
		}
		return "";
	}

	private Date getNewDate(GenerationOptions option) {
		SimpleDateFormat sdf = new SimpleDateFormat(TesterCoreUtils.DATE_TIME_PATTERN);
		Date date;
		try {
			date = sdf.parse(option.getMin());
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			String unit = option.getIncrementUnit();
			long d = Long.parseLong(option.getIncrementBy());
			
			switch (unit) {
			case "Milliseconds":
				cal.add(Calendar.MILLISECOND, (int) d);
				break;

			case "Seconds":
				cal.add(Calendar.SECOND, (int) d);
				break;
				
			case "Minutes":
				cal.add(Calendar.MINUTE, (int) d);
				break;
				
			case "Hours":
				cal.add(Calendar.HOUR, (int) d);
				break;
				
			case "Days":
				cal.add(Calendar.DAY_OF_MONTH, (int) d);
				break;
				
			case "Months":
				cal.add(Calendar.MONTH, (int) d);
				break;
				
			case "Years":
				cal.add(Calendar.YEAR, (int) d);
				break;
				
			default:
				break;
			}
			return cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	private List<DomainEntry> getAllEntries(EList<DomainInstance> domainInstances) {
		List<DomainEntry> entries = new ArrayList<>();
		for (DomainInstance domainInstance : domainInstances) {
			//Get the domain corresponding to this instance
			String domainPath = domainInstance.getResourcePath();
			Domain domain = CommonIndexUtils.getDomain(this.editor.getProjectName(), domainPath);

			//Do for all super domains
			Domain superDomain = domain;
			while (superDomain != null) {
				entries.addAll(superDomain.getEntries());
				String superDomainPath = superDomain.getSuperDomainPath();
				/**
				 * This is done to get updated value from index, 
				 * and not the cached in-memory one
				 */
				superDomain = CommonIndexUtils.getDomain(this.editor.getProjectName(), superDomainPath);
			}
		}
		return entries;
	}

	private GenerationOptions getGenerationOptions(String str, Map<PropertyDefinition, GenerationOptions> options) {
		Collection<GenerationOptions> values = options.values();
		for (GenerationOptions op : values) {
			if (op.getProperty().getName().equals(str)) {
				return op;
			}
		}
		return null;
	}

	public void addAction( ToolBarProvider toolBarProvider) {
		ArrayList<String> list = new ArrayList<String>();
		model.getSelectRowData().add(true);
		if (entity instanceof Event) {
			list.add("");
		}
		// model.getExtId().add("");
		list.add("");
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (String str : actualColumnNames) {
			if(TesterCoreUtils.UID_COL_NAME.equals(str))
			{
				map.put(str,(rowCounter++)+"");
				list.add((rowCounter)+"");
			}
			else
			{
				map.put(str, "");
				list.add("");
			}
		}
		
		
		model.getTestData().add(list);
		model.getInput().add(map);
		tableViewer.setInput(model.getInput());
		tableViewer.refresh();
		setTablecheckBox();
		editor.modified();
		updateData();
	
	}

	public void removeAction(ToolBarProvider tbp) {
		StructuredSelection strSel = (StructuredSelection) tableViewer.getSelection();
		if(!strSel.isEmpty()){
			Iterator iter = strSel.iterator();
			while (iter.hasNext()) {
				@SuppressWarnings("unchecked")
				LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) iter.next();
				int index = model.getInput().indexOf(map);
				model.getSelectRowData().remove(index);
				model.getInput().remove(index);
				model.getTestData().remove(index);
				tableViewer.setInput(model.getInput());
				tableViewer.refresh();
				setTablecheckBox();
				editor.modified();
				updateData();
			}
		}
		tableViewer.setInput(model.getInput());
		tableViewer.refresh();
		tbp.getDeleteItem().setEnabled(false);
		tbp.getDuplicateItem().setEnabled(false);
	}

	

	public void duplicateAction(ToolBarProvider tbp) {
		ArrayList<String> list = new ArrayList<String>();
		StructuredSelection strSel = (StructuredSelection) tableViewer.getSelection();
		if(!strSel.isEmpty()){
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) strSel.getFirstElement();
			int index = model.getInput().indexOf(map);
			model.getSelectRowData().add(model.getSelectRowData().get(index));
			if (entity instanceof Event) {
				// model.getPayload().add(model.getPayload().get(index));
				list.add(model.getTestData().get(index).get(1));
			}
			// model.getExtId().add(model.getExtId().get(index));
			list.add(model.getTestData().get(index).get(0));
			LinkedHashMap<String, String> newMap = new LinkedHashMap<String, String>();
			for (String str : actualColumnNames) {
				if(TesterCoreUtils.UID_COL_NAME.equals(str)){
					newMap.put(str,(rowCounter++)+"");
					list.add((rowCounter)+"");
				}
				else{
					newMap.put(str, map.get(str));
					list.add(map.get(str));
				}
			}
			model.getTestData().add(list);
			model.getInput().add(newMap);
			tableViewer.setInput(model.getInput());
			tableViewer.refresh();
			setTablecheckBox();
			TableItem[] itemList = tableViewer.getTable().getItems();
			if(itemList.length>0)
				tableViewer.setSelection(new StructuredSelection(tableViewer.getElementAt(itemList.length-1)),true);
			editor.modified();
			updateData();
		}
		tableViewer.refresh();

	}

	public void fitContentAction(){
		int width = 0;
		TableColumn[] tableColumns = tableViewer.getTable().getColumns();
		for (TableColumn tc : tableColumns) {
			if(!TesterCoreUtils.UID_COL_NAME.equals(tc.getText()))
				width = width + tc.getWidth();
		}
		for (TableColumn tc : tableColumns) {
			if(!TesterCoreUtils.UID_COL_NAME.equals(tc.getText()))
				tc.setWidth(width / tableColumns.length);
		}
		for (TableColumn tc : tableColumns) {
			if(!TesterCoreUtils.UID_COL_NAME.equals(tc.getText()))
				tc.pack();
		}
	}
	
	private int calculateDependency(String filePath, Entity dependentEntity, ArrayList<String> columnNames2){
		try {
			if(filePath!=null){
				TestDataModel model=TesterCoreUtils.getDataFromXML(filePath, "Concept", dependentEntity, columnNames2);
				int rowCount=model.getSelectRowData().size();
				return rowCount;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	

	private String getConceptPath(EList<PropertyDefinition> list, ArrayList<String> tableColumnNames, int iterator){
		String conceptPath=null;
		for(PropertyDefinition pd:list){
			if(tableColumnNames.get(iterator).equalsIgnoreCase(pd.getName())){
				if(pd.getType().equals(PROPERTY_TYPES.CONCEPT)||pd.getType().equals(PROPERTY_TYPES.CONCEPT_REFERENCE)){
					conceptPath=pd.getConceptTypePath();
					return conceptPath;
				}
			}
		}
		return conceptPath;
	}
	
	private ArrayList<String> getAllColumnNames(Entity entity){
		
		ArrayList<String> columnNames=new ArrayList<String>();
		columnNames.add("Use");
		columnNames.add("ExtId");
		for(PropertyDefinition pd:((Concept) entity).getAllProperties()){
			columnNames.add(pd.getName());
		}
		return columnNames;
	}
	
	
	private String[][] getDomainEntries( int iterator){
		EList<PropertyDefinition> list = null;
		String [][] domainEntries = null;
		PropertyDefinition pd = null;

		if (entity instanceof Scorecard) {
			list = ((Scorecard) editor.getEntity()).getAllProperties();
			pd= (PropertyDefinition) list.get(iterator-2);
		} else if (entity instanceof Event) {
			list = ((Event) editor.getEntity()).getAllUserProperties();
			pd= (PropertyDefinition) list.get(iterator-3);
		} else if (entity instanceof Concept) {
			list = ((Concept) editor.getEntity()).getAllProperties();
			pd= (PropertyDefinition) list.get(iterator-2);
		}
		
		String propertyPath=pd.getFullPath();

		List<DomainInstance> domainInstances =TesterUIUtils.getDomains(propertyPath, editor.getProjectName());
		if (domainInstances != null && domainInstances.size() > 0) { 
			domainEntries =  TesterUIUtils.getDomainEntryDescriptions(
					domainInstances, editor.getProjectName());
		}
		return domainEntries;
	}
	
	private ArrayList<String> getDomainEntriesList( int iterator){
		EList<PropertyDefinition> list = null;
		ArrayList<String> domainEntries = new ArrayList<String>();
		PropertyDefinition pd = null;

		if (entity instanceof Scorecard) {
			list = ((Scorecard) editor.getEntity()).getAllProperties();
			pd= (PropertyDefinition) list.get(iterator-2);
		} else if (entity instanceof Event) {
			list = ((Event) editor.getEntity()).getAllUserProperties();
			pd= (PropertyDefinition) list.get(iterator-3);
		} else if (entity instanceof Concept) {
			list = ((Concept) editor.getEntity()).getAllProperties();
			pd= (PropertyDefinition) list.get(iterator-2);
		}
		
		String propertyPath=pd.getFullPath();

		List<DomainInstance> domainInstances =TesterUIUtils.getDomains(propertyPath, editor.getProjectName());
		if (domainInstances != null && domainInstances.size() > 0) { 
			domainEntries=(ArrayList<String>) TesterUIUtils.getDomainEntryStrings(domainInstances,editor.getProjectName());

		}
		return domainEntries;
	}
	
	private Map<String, DomainEntry> getDomainEntriesMap( int iterator){
		EList<PropertyDefinition> list = null;
		Map<String, DomainEntry> domainEntries=new HashMap<String,DomainEntry>();
		PropertyDefinition pd = null;

		if (entity instanceof Scorecard) {
			list = ((Scorecard) editor.getEntity()).getAllProperties();
			pd= (PropertyDefinition) list.get(iterator-2);
		} else if (entity instanceof Event) {
			list = ((Event) editor.getEntity()).getAllUserProperties();
			pd= (PropertyDefinition) list.get(iterator-3);
		} else if (entity instanceof Concept) {
			list = ((Concept) editor.getEntity()).getAllProperties();
			pd= (PropertyDefinition) list.get(iterator-2);
		}
		
		String propertyPath=pd.getFullPath();

		List<DomainInstance> domainInstances =TesterUIUtils.getDomains(propertyPath, editor.getProjectName());
		if (domainInstances != null && domainInstances.size() > 0) { 
			domainEntries=TesterUIUtils.getDomainEntriesMap(domainInstances,editor.getProjectName());

		}
		return domainEntries;
	}

	public static PropertyDefinition getPropertyDefinition(String columnName,
			EList<PropertyDefinition> list) {
		for (PropertyDefinition propertyDefinition : list) {
			if (propertyDefinition.getName().equals(columnName)) {
				return propertyDefinition;
			}
		}
		return null;
	}

	public ArrayList<String> getTableColumnNames() {
		return tableColumnNames;
	}

	public void setTableColumnNames(ArrayList<String> tableColumnNames) {
		this.tableColumnNames = tableColumnNames;
	}

	public TestDataModel getModel() {
		return model;
	}

	public void setModel(TestDataModel model) {
		this.model = model;
	}

}
