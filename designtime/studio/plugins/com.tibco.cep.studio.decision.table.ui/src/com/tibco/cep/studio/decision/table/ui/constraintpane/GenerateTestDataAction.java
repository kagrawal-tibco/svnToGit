package com.tibco.cep.studio.decision.table.ui.constraintpane;

import static com.tibco.cep.studio.decision.table.constraintpane.Operator.AND_OP;
import static com.tibco.cep.studio.decision.table.constraintpane.Operator.OR_OP;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createTestData;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.invokeOnDisplayThread;

import java.awt.Component;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewSite;

import com.tibco.cep.decision.table.language.DateTimeLanguageUtil;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerView;
import com.tibco.cep.studio.decision.table.constraintpane.Messages;
import com.tibco.cep.studio.decision.table.constraintpane.MultiSelectComboBox;
import com.tibco.cep.studio.decision.table.constraintpane.RangeSlider;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.editor.input.IDecisionTableEditorInput;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.tester.core.model.TestDataModel;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;


public class GenerateTestDataAction extends Action {

	private Map<String, Widget> fComponents;

	private IDecisionTableEditor fCurrentEditor;

	private DecisionTableAnalyzerView analyzerView;

	private static final String CLASS = GenerateTestDataAction.class.getName();

	private Map<String, Component> selectedComponentsMap;
	
	private Map<String,List<String>> dependentConceptItems=new HashMap<String,List<String>>();

	/**
	 * @param analyzerView
	 * @param selectedComponentsMap 
	 */
	public GenerateTestDataAction(DecisionTableAnalyzerView analyzerView) {

		setText(Messages.getString("DecisionTableAnalyzerView.Generate"));
		setToolTipText(Messages.getString("DecisionTableAnalyzerView.Generate"));
		setImageDescriptor(DecisionTableUIPlugin.getImageDescriptor("icons/genTestData16x16.png"));
		this.analyzerView = analyzerView;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		try {
			this.fComponents = analyzerView.getComponents();
			this.fCurrentEditor = analyzerView.getCurrentEditor();
			if (fComponents == null || fCurrentEditor == null) return;
			DecisionTableUIPlugin.debug(CLASS, "Generating test data");
			Date start = new Date();
//			GenerateTestDataWizard wizard= new GenerateTestDataWizard(fComponents, analyzerView);
//			// Instantiates the wizard container with the wizard and opens it
//			wizard.init();
//			WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
//			dialog.create();
//			dialog.open();
			//this.fComponents = selectedComponentsMap;
			generateTestData();
			Date end = new Date();
			DecisionTableUIPlugin.debug(CLASS, "Finished Generating Test Data in {0}: ", (end.getTime() - start.getTime()));
		} catch(Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}

	/*
	 * The following steps are taken to generate test data:
	 * 1) Collect the selected values from the components in the view
	 * 2) Determine the Concepts/Events declared in the current rule function impl
	 * 3) Load any existing test data for those Concepts/Events
	 * 4) Append one or more rows of test data based on the different
	 * permutations of the selection.  The order of the test data is
	 * based on the properties of the concept/event.
	 */
	public void generateTestData() throws Exception {
		HashMap<String, Object> columnToValueMap = new HashMap<String, Object>();
		for (String columnName : fComponents.keySet()) {
			Widget comp = fComponents.get(columnName);
			Object value=getComponentValue(comp,columnName);
			columnToValueMap.put(columnName, value);
		}
		/*TableModel declTableModel = fCurrentEditor.getDecisionTableDesignViewer().getDeclTableModel();
		int columnCount = declTableModel.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			declTableModel.getColumnName(i);
		}*/
		IEditorInput editorInput = fCurrentEditor.getEditorInput();
		if (editorInput instanceof IDecisionTableEditorInput) {
			IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)editorInput;
			AbstractResource template = decisionTableEditorInput.getVirtualRuleFunction();

			String projectName = decisionTableEditorInput.getProjectName();

			List<Concept> conceptList = getAllConcepts(projectName);
			List<Event> eventList = getAllEvents(projectName);
			if (null != template) {
				Table tableEModel = decisionTableEditorInput.getTableEModel();
				List<Argument> arguments = tableEModel.getArgument();
				Set<Argument> refArguments = new LinkedHashSet<Argument>(); // unique arguments only

				List<com.tibco.cep.decision.table.model.dtmodel.Column> columns = tableEModel.getDecisionTable().getColumns().getColumn();
				for (Argument argument : arguments) { // collect only referenced arguments
					for (com.tibco.cep.decision.table.model.dtmodel.Column column : columns) {

						if (column.getColumnType() != ColumnType.CONDITION) {
							continue;
						}
						String path = column.getPropertyPath();
						int pos = path.lastIndexOf("/");
						String containedPropPath = null;
						if (pos != -1) { // concept or event
							containedPropPath = path.substring(0, pos);
							if (containedPropPath.equals(argument.getProperty().getPath())) {
								refArguments.add(argument);
							}
						} else {
							containedPropPath = path;
							if (containedPropPath.equals(argument.getProperty().getAlias())) {
								refArguments.add(argument);
							}
						}
						
						String columnName=column.getName();
						String relationshipArray[]=columnName.split("\\.");
						if(relationshipArray.length==3){
							if(relationshipArray[0].equalsIgnoreCase(argument.getProperty().getAlias())){
								Argument newArgument = DtmodelFactory.eINSTANCE.createArgument();
								ArgumentProperty argProperty = DtmodelFactory.eINSTANCE.createArgumentProperty();
								argProperty.setPath(containedPropPath);
								String alias[]=containedPropPath.split("/");
								argProperty.setAlias(alias[alias.length-1]);
								argProperty.setResourceType(ResourceType.CONCEPT);
								newArgument.setProperty(argProperty);
								refArguments.add(newArgument);
								refArguments.add(argument);
							}
					
						}
					}
				}
				
				//Check for no data selected in Table Analyzer
				boolean foundData = false;
				for (Object value : columnToValueMap.values()) {
					if (value != null) {
						foundData = true;
						break;
					}
				}
				
				if (!foundData) {
					showMessage(Messages.getString("DecisionTableAnalyzerView.Generate.Title"), Messages.getString("DecisionTableAnalyzerView.Generate.Empty.Desc"));
					return;
				}
				
				for (Argument argument : refArguments) {
					processArgument(projectName,
							        argument, 
							        columnToValueMap,
							        conceptList,
							        eventList);
				}
			}
		}
	}

	/**
	 * Generate a {@link List} of all concepts in the project
	 * @param projectName
	 * @return
	 */
	private List<Concept> getAllConcepts(String projectName) {
		List<DesignerElement> conceptElements = null;
		if (projectName != null) {
			conceptElements = IndexUtils.getAllElements(projectName, ELEMENT_TYPES.CONCEPT);
		}
		List<Concept> concepts = new ArrayList<Concept>(conceptElements.size());
		for (DesignerElement conceptElement : conceptElements) {
			if (conceptElement instanceof EntityElement) {
				EntityElement entityElement = (EntityElement)conceptElement;
				concepts.add((Concept)entityElement.getEntity());
			}
		}
		return concepts;
	}

	/**
	 * Generate a {@link List} of all events in the project
	 * @param projectName
	 * @return
	 */
	private List<Event> getAllEvents(String projectName) {
		if (projectName != null) {
			return IndexUtils.getAllEvents(projectName);
		}
		return null;
	}

	/**
	 * @param projectName -> The project this table exists in
	 * @param argument
	 * @param columnToValueMap
	 * @param conceptList -> All concepts
	 * @param eventList -> All events
	 */
	private void processArgument(String projectName, 
								 Argument argument, 
								 HashMap<String,Object> columnToValueMap, 
								 List<Concept> conceptList, 
								 List<Event> eventList) {
		ArgumentProperty property = argument.getProperty();
		String path = property.getPath();
		String alias = property.getAlias();
		ResourceType resourceType = property.getResourceType();
		if (resourceType == ResourceType.UNDEFINED) {
			String message = MessageFormat
					.format(Messages
							.getString("DecisionTableAnalyzerView.Generate.UnknownType.message"),
							path, alias);
			showMessage(
					Messages.getString("DecisionTableAnalyzerView.Generate.Title"),
					message);
			return;
		}
		// need to get concept/event, and it's properties
		EList<PropertyDefinition> properties = null;
		Entity resource = null;
		String testDataFilePath = null;
		Concept concept = null;
		Event event;
		Vector<String> columnIdentifiers = new Vector<String>(2);
		if (resourceType == ResourceType.CONCEPT) {
			resource = getEntity(path, conceptList);
			concept = (Concept) resource;
			properties = concept != null ? concept.getAllProperties() : null;
			testDataFilePath = createTestData(concept, projectName, alias,
					CommonIndexUtils.CONCEPT_EXTENSION, "concepttestdata");
			if (testDataFilePath == null) {
				return;
			}
			columnIdentifiers.add("Use");
			columnIdentifiers.add("ExtId");
			EList<PropertyDefinition> propList = concept.getAllProperties();
			for (PropertyDefinition prop : propList) {
				columnIdentifiers.add(prop.getName());
			}

		} else if (property.getResourceType() == ResourceType.EVENT) {
			resource = getEntity(path, eventList);
			event = (Event) resource;
			properties = event != null ? event.getAllUserProperties() : null;
			testDataFilePath = createTestData(event, projectName, alias,
					CommonIndexUtils.EVENT_EXTENSION, "eventtestdata");
			if (testDataFilePath == null) {
				return;
			}
			columnIdentifiers.add("Use");

			/**
			 * Event Payload.
			 */
			if (TesterCoreUtils.isPayLoadPropertyUIEnabled) {
				columnIdentifiers.add("Payload");
			}
			columnIdentifiers.add("ExtId");
			EList<PropertyDefinition> propList = event.getAllUserProperties();
			for (PropertyDefinition prop : propList) {
				columnIdentifiers.add(prop.getName());

			}
		}

		List<Object> allValues = new ArrayList<Object>();
		if (properties != null) {
			for (PropertyDefinition prop : properties) {
				String columnName = alias + "." + prop.getName(); //$NON-NLS-1$
				if(property.isArray()){
					columnName = alias + "[]"+ "." + prop.getName(); 
				}
				for (String col : columnToValueMap.keySet()) {
					if (col.startsWith(columnName)) {
						columnName = col;
						break;
					}
				}
				Object object = columnToValueMap.get(columnName); 
				if(object==null){
					for (String col : columnToValueMap.keySet()) {
						if (col.endsWith(prop.getName())) {
							columnName = col;
							break;
						}
					}
					object = columnToValueMap.get(columnName); 
				}
				if(prop.getType()==PROPERTY_TYPES.CONCEPT || prop.getType()==PROPERTY_TYPES.CONCEPT_REFERENCE){
					object=0;
					List<IResource> output = new ArrayList<IResource>();
					EList<PropertyDefinition> list = concept.getAllProperties();
					String conceptPath =prop.getConceptTypePath();
					Entity dependentConcept = CommonIndexUtils.getEntity(projectName, conceptPath);
					IFile file = IndexUtils.getFile(projectName,dependentConcept);
					IPath path1 = file.getLocation().removeLastSegments(2);
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
							dependentConceptItems.put(prop.getName(), arr);
				}
				allValues.add(object);
			}
		}
		try {
			@SuppressWarnings("rawtypes")
			Vector<Vector> dataVector = new Vector<Vector>();
			final int count = processValues(/* writer, */
			new StringBuffer(), dataVector, resource, allValues, 0, 0, false);
			// DefaultTableModel model = new DefaultTableModel(dataVector,
			// columnIdentifiers);
			
			TestDataModel model = new TestDataModel(resource, null,
					new ArrayList<List<String>>());
			if (testDataFilePath != null) {
				model.setEntityInfo(resource.toString());

				for (int i = 0; i < count; i++) {
					model.getSelectRowData().add(true);
				}

				for (Vector<String> vector : dataVector) {
					ArrayList<String> list = new ArrayList<String>();
					/*if (resource instanceof Event) {
						list.add("");
					}*/
					for (int i = 1; i < vector.size(); i++) {
						list.add(vector.get(i));
					}
					model.getTestData().add(list);
				}
				ArrayList<String> tableColumnNames = new ArrayList<String>();
				for (String vector : columnIdentifiers) {
					tableColumnNames.add(vector);
				}
				model.setTableColumnNames(tableColumnNames);
				String entityType = null;
				boolean flag = false;
				if (resource instanceof Scorecard) {
					entityType = "Scorecard";
					flag = true;
				} else if (resource instanceof Event) {
					entityType = "Event";
				} else if (resource instanceof Concept) {
					if (flag != true) {
						entityType = "Concept";
					}
				}
				
				TestDataModel existingModel=null;
				try {
					existingModel = TesterCoreUtils.getDataFromXML(testDataFilePath, entityType, resource,tableColumnNames);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//From testData of existing model..create vector..
				
				
				Vector<Vector> vectors = new Vector<Vector>();
				int loopCounter = existingModel.getSelectRowData().size();
				for (int i = 0; i < loopCounter; i++) {

					Vector<String> vector = new Vector<String>();
					vector.add(resource.getFullPath());
					if(resource instanceof Concept){
						vector.add(existingModel.getTestData().get(i).get(1));
						List<String> data = existingModel.getTestData().get(i);
						for (String str : data) {
							vector.add(str);
						}
					}
					if (resource instanceof Event) {
						List<String> data = existingModel.getTestData().get(i);
						vector.add("");
						for (String str : data) {
							vector.add(str);
						}
					}

					vectors.add(vector);
				}

				for (Vector<String> vet : dataVector) {
					vet.add(1, "");
				}
				vectors.addAll(dataVector);
				model.setAppendedVectors(vectors);
				model.setVectors(dataVector);

						
				if (StudioUIUtils.NO_OPTION_SELECTED != 1) {
					if (resource instanceof Event) { //Add extra blank column for event
						for (Vector<String> vector : dataVector) {
							vector.add(1, "");
						}
					}					
					TesterCoreUtils.exportDataToXMLDataFile(testDataFilePath,
							model, true, false,dependentConceptItems,null);
				} else {
					TesterCoreUtils.exportDataToXMLDataFileAppend(
							testDataFilePath, resource, model,
							tableColumnNames, true);
				}

			}
			if (count > 0 && StudioUIUtils.NO_OPTION_SELECTED != 1) {
				String message = MessageFormat
						.format(Messages
								.getString("DecisionTableAnalyzerView.Generate.Finish.message"),
								testDataFilePath, count, resource.getName());
				message = message.replace('\\', '/');
				showMessage(
						Messages.getString("DecisionTableAnalyzerView.Generate.Title"),
						message);
			}
			if (StudioUIUtils.NO_OPTION_SELECTED == 1) {
				String message = MessageFormat
						.format(Messages
								.getString("DecisionTableAnalyzerView.Generate.Finish.append.message"),
								testDataFilePath, count, resource.getName());
				message = message.replace('\\', '/');
				showMessage(
						Messages.getString("DecisionTableAnalyzerView.Generate.Title"),
						message);

			}
			StudioUIUtils.NO_OPTION_SELECTED = 0;
		} catch (IOException e) {
			DecisionTableUIPlugin.log(e);
		} finally {
		}
	}
	/**
	 * @param writer
	 * @param rowData
	 * @param resource
	 * @param allValues
	 * @param currentIndex
	 * @param count
	 * @param foundData
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private int processValues(
			StringBuffer rowData, 
			Vector<Vector> dataVector,
			Entity resource, 
			List<Object> allValues,
			int currentIndex, 
			int count, 
			boolean foundData) throws IOException {
		if (currentIndex == 0) {
			rowData.append(resource.getFolder());
			rowData.append(resource.getName());
			rowData.append(TesterCoreUtils.DELIMITER);
			if (resource instanceof Concept) {
				rowData.append(""); //ExtId
				rowData.append(TesterCoreUtils.DELIMITER);
			}
			if (resource instanceof Event && TesterCoreUtils.isPayLoadPropertyUIEnabled) {
				rowData.append("");
				rowData.append(TesterCoreUtils.DELIMITER);
				rowData.append(""); //ExtId
				rowData.append(TesterCoreUtils.DELIMITER);
			}
		}
		for (int i=currentIndex; i<allValues.size(); i++) {
			Object object = allValues.get(i);
			if (object == null) {
				rowData.append(TesterCoreUtils.DELIMITER);
			} else if (object instanceof Object[]) {
				foundData = true;
				Object[] arr = (Object[]) object;
				for (Object obj : arr) {
					StringBuffer newBuffer = new StringBuffer(rowData);
					if (obj.toString().equals("DateTime.now()")) {
						obj = DateTimeLanguageUtil.SDF.format(new Date());
					}
					newBuffer.append(obj);
					newBuffer.append(TesterCoreUtils.DELIMITER);
					count = processValues(/*writer, */newBuffer, dataVector, resource, allValues, i+1, count, true);
				}
				return count;
			} else if (object instanceof int[]) {
				// this is a range, take the average of the two and use that (?)
				int[] arr = (int[]) object;
				if (arr.length == 2) {
					foundData = true;

					//generate test data for Average
//					StringBuffer newBuffer = new StringBuffer(rowData);
//					int avg = arr[0] + arr[1] / 2;
//					newBuffer.append(avg);
//					newBuffer.append(TesterCoreUtils.DELIMITER);
//					count = processValues(/*writer, */newBuffer, dataVector, resource, allValues, i+1, count, true);
					if (arr[0] == arr[1]) {
						StringBuffer newBuffer = new StringBuffer(rowData);
						newBuffer.append(arr[0]);
						newBuffer.append(TesterCoreUtils.DELIMITER);
						count = processValues(/*writer, */newBuffer, dataVector, resource, allValues, i+1, count, true);
					} else {
						//Generate for min and max values @since 5.1
						for (int value : arr) {
							StringBuffer newBuffer = new StringBuffer(rowData);
							newBuffer.append(value);
							newBuffer.append(TesterCoreUtils.DELIMITER);
							count = processValues(/*writer, */newBuffer, dataVector, resource, allValues, i+1, count, true);
						}
					}
					
//					int interval = DecisionTableUIPlugin.getDefault().getPreferenceStore().getInt(PreferenceConstants.INTEGER_RANGE_VALUE);
//					int diff = (arr[1] - arr[0]) / (interval );
//					int value  = 0;
//					for(int index = 0; index <= interval  ; index ++) {
//						StringBuffer newBuffer = new StringBuffer(rowData);
//						value = arr[0] + (diff * index);
//						newBuffer.append(value);
//						newBuffer.append(TesterCoreUtils.DELIMITER);
//						count = processValues(/*writer, */newBuffer, dataVector, resource, allValues, i+1, count, true);
//					}
					return count;
				} else if (object instanceof double[]) {
					double[] arr_d = (double[]) object;
					if (arr.length == 2) {
						foundData = true;
						//Generate for min and max values @since 5.1
						for (double value : arr_d) {
							StringBuffer newBuffer = new StringBuffer(rowData);
							newBuffer.append(value);
							newBuffer.append(TesterCoreUtils.DELIMITER);
							count = processValues(/*writer, */newBuffer, dataVector, resource, allValues, i+1, count, true);
						}
						return count;
					} 
				} else if (object instanceof long[]) {
					long[] arr_d = (long[]) object;
					if (arr.length == 2) {
						foundData = true;
						//Generate for min and max values @since 5.1
						for (long value : arr_d) {
							StringBuffer newBuffer = new StringBuffer(rowData);
							newBuffer.append(value);
							newBuffer.append(TesterCoreUtils.DELIMITER);
							count = processValues(/*writer, */newBuffer, dataVector, resource, allValues, i+1, count, true);
						}
   						return count;
					} 
				} else {
					rowData.append(TesterCoreUtils.DELIMITER);
				}
			}else if(object instanceof Integer){
				
				foundData = true;
				int val = (Integer) object;
				StringBuffer newBuffer = new StringBuffer(rowData);
				newBuffer.append(val);
				newBuffer.append(TesterCoreUtils.DELIMITER);
				count = processValues(/*writer, */newBuffer, dataVector, resource, allValues, i+1, count, true);
				return count;
			}
		}
		rowData.deleteCharAt(rowData.length()-1);
		//		rowData.append('\n');
		if (foundData) {
			Vector<String> v = new Vector<String>();
			String[] tokens = rowData.toString().split(TesterCoreUtils.DELIMITER, -1);
			for (int i = 0; i < tokens.length; ++i) {
				if(tokens[i].matches("^\\[[0-9]+\\].*$")){
					tokens[i]=tokens[i].substring(3);
				}
				v.add(tokens[i].trim());
			}
			dataVector.add(v);
			//			writer.write(rowData.toString());
			return count+1;
		}
		return count;
	}

	/**
	 * @param title
	 * @param message
	 */
	private void showMessage(final String title, final String message) {

		invokeOnDisplayThread(new Runnable() {
			public void run() {
				MessageDialog.openInformation(getViewSite().getShell(), title, message);
			}
		}, false);
	}

	/**
	 * @param comp
	 * @param columnName 
	 * @return
	 */
	private Object getComponentValue(Widget comp, String columnName) {
		if (comp instanceof MultiSelectComboBox) {
			Object selectedItem = ((MultiSelectComboBox)comp).getSelectedObjects();
			if (selectedItem instanceof Object[]) {
				return selectedItem;
			} else if (selectedItem == null) {
				// no filter was selected, do we want to add all values for this filter?
				//				Collection<List<Cell>> values = ((EqualsFilter)filter).map.values();
				//				for (List<Cell> list : values) {
				//					for (Cell cell : list) {
				//						addCellEntry(columnName, cellsToHighlight, cell.getId());
				//					}
				//				}
			}
		} 
		else if (comp instanceof RangeSlider) {
			Object lowValue = ((RangeSlider)comp).getLowerValue();
			Object highValue = ((RangeSlider)comp).getUpperValue();
			return new Object[] { lowValue, highValue };
		}else if (comp instanceof Text && !(columnName.contains("upper") || columnName.contains("lower"))){

			Text textField = (Text) comp;
			String text = textField.getText();
			try {
				String[] rangeElements = new String[] {text};
				boolean isBounded = false;
				if (text.lastIndexOf(AND_OP) != -1) {
					rangeElements = text.split(AND_OP);
				} else if (text.lastIndexOf(OR_OP) != -1) {
					String orOp = "\\|\\|";
					rangeElements = text.split(orOp);
				}
				Object one = rangeElements[0].substring(2).trim();
				Object two = rangeElements[1].substring(2).trim();

				return new Object[] { one, two };
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @param path
	 * @param resourceList
	 * @return
	 */
	private <T extends Entity> T getEntity(String path, 
			List<T> resourceList) {
		for (T resource : resourceList) {
			String folder = resource.getFolder();
			String name = resource.getName();
			String fullPath = folder + name;
			if (path.equals(fullPath)) {
				return resource;
			}
		}
		return null;
	}

	public IViewSite getViewSite() {
		return analyzerView.getViewSite();
	}

	public Map<String, Component> getSelectedComponentsMap() {
		return selectedComponentsMap;
	}

	public void setSelectedComponentsMap(
			Map<String, Component> selectedComponentsMap) {
		this.selectedComponentsMap = selectedComponentsMap;
	}

	public Map<String, Widget> getFComponents() {
		return fComponents;
	}

	public void setFComponents(Map<String, Widget> components) {
		fComponents = components;
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
	
	private ArrayList<String> getAllColumnNames(Entity entity){
		
		ArrayList<String> columnNames=new ArrayList<String>();
		columnNames.add("Use");
		columnNames.add("ExtId");
		for(PropertyDefinition pd:((Concept) entity).getAllProperties()){
			columnNames.add(pd.getName());
		}
		return columnNames;
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
	
}
