package com.tibco.cep.studio.tester.core.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.table.DefaultTableModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.emf.common.util.EList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.element.impl.ConceptImpl;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.impl.SimpleEventImpl;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.debug.core.model.IRuleDebugTarget;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.tester.core.StudioTesterCorePlugin;
import com.tibco.cep.studio.tester.core.model.EntityType;
import com.tibco.cep.studio.tester.core.model.TestDataModel;
import com.tibco.cep.studio.tester.core.model.TesterResultsDatatype;
import com.tibco.cep.studio.tester.core.model.TesterResultsOperation;
import com.tibco.cep.studio.tester.core.model.TesterResultsType;
import com.tibco.cep.studio.tester.core.provider.TestResultsDataTypeDeserializer;
import com.tibco.cep.studio.tester.core.provider.TestResultsDeserializer;
import com.tibco.cep.studio.tester.core.provider.TestResultsOpsDeserializer;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.nodes.Attribute;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

public class TesterCoreUtils {

	public static final String DELIMITER = ",";

	public static final String SPLIT = ";";

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"; //$NON-NLS-1$

	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(
			DATE_TIME_PATTERN);

	public static final String CONCEPT_FILE_EXT = ".concepttestdata";

	public static final String EVENT_FILE_EXT = ".eventtestdata";

	public static final String SCORECARD_FILE_EXT = ".scorecardtestdata";

	public static boolean isMultiValuedPropertyUIEnabled = true;

	public static boolean isPayLoadPropertyUIEnabled = true;

	public static boolean isTestDataAutoSaveEnabled = true;

	private static final int TIBCO_BE_NS_LENGTH = "www.tibco.com/be/ontology"
			.length();

	public static String ENTITY_NS = "www.tibco.com/be/ontology";

	public static Map<String,List<String>> conrefdata=new HashMap<String, List<String>>();

	public static String MARKER="##";
	
	public static String DOT=".";
	
	public static String FORWARD_SLASH="/";
	
	private static List<Entity> entityList=new ArrayList<Entity>();
	
	public static boolean isIncompatible=false;
	
	public static final String  UID_COL_NAME ="uuuuuuuuuuuuuuuuuu_id";
	/**
	 * Return a {@link List} of launch configurations belonging to tester launch
	 * config type for this project.
	 * 
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public static List<String> getLaunchConfigurations(String project)
			throws CoreException {
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		ILaunchConfigurationType launchConfigType = launchManager
				.getLaunchConfigurationType("com.tibco.cep.studio.tester.launchConfigurationType");
		ILaunchConfiguration[] launchConfigurations = launchManager
				.getLaunchConfigurations(launchConfigType);

		List<String> launchConfigsForProject = new ArrayList<String>(
				launchConfigurations.length);
		for (ILaunchConfiguration launchConfiguration : launchConfigurations) {
			launchConfigsForProject.add(launchConfiguration.getName());
		}
		return launchConfigsForProject;
	}

	/**
	 * @param entityType
	 * @return
	 */
	public static String getFullPath(EntityType entityType) {
		String namespace = entityType.getNamespace();
		if (namespace != null) {
			namespace = namespace.substring(TIBCO_BE_NS_LENGTH,
					namespace.length());
		}
		return namespace;
	}

	/**
	 * 
	 * @return
	 */
	public static IRuleRunTarget getRunTarget() {
		DebugPlugin plugin = DebugPlugin.getDefault();
		if (plugin != null) {
			IDebugTarget[] targets = plugin.getLaunchManager()
					.getDebugTargets();
			// Look only for run target.
			for (IDebugTarget target : targets) {
				IRuleDebugTarget runTarget = (IRuleDebugTarget) target
						.getAdapter(IRuleRunTarget.class);
				// Get first one and return
				if (runTarget != null && runTarget.isAvailable()) {
					return runTarget;
				}
			}
		}
		return null;
	}

	/**
	 * Gets all the package names from the list of jarFiles.
	 * 
	 * @param jarFiles
	 * @return A String of all package names, separated by a newline
	 */
	public static String getPackageNames(List<JarFile> jarFiles) {
		List<String> packageNames = new ArrayList<String>();
		for (JarFile jarFile : jarFiles) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
				String entryName = entry.getName();
				if (entryName.endsWith(".class")) {
					int index = entryName.lastIndexOf('/');
					if (index == -1) {
						// default package - do not add
						continue;
					}
					String entryPath = entryName.substring(0, index);
					String packageName = entryPath.replaceAll("/", ".");
					if (!packageNames.contains(packageName)
							&& !packageName.startsWith("java.")
							&& !packageName.startsWith("sun.")) {
						packageNames.add(packageName);
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < packageNames.size(); i++) {
			sb.append(packageNames.get(i));
			if (i < packageNames.size() - 1) {
				sb.append(",\n ");
			} else {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Given the list of jarFiles, returns a String appropriate for insertion in
	 * a MANIFEST.MF Bundle-Classpath entry
	 * 
	 * @param jarFiles
	 * @return
	 */
	public static String getJarNames(List<JarFile> jarFiles) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < jarFiles.size(); i++) {
			sb.append("external:");
			sb.append(jarFiles.get(i).getName());
			if (i < jarFiles.size() - 1) {
				sb.append(",\n ");
			}
		}
		return sb.toString();
	}

	/**
	 * @author mgujrath
	 * @param filename
	 * @param entity
	 * @param model
	 * @param selectTableModel
	 */
	public static void exportDataToXMLDataFile(String filename, Entity entity,
			DefaultTableModel model, DefaultTableModel selectTableModel) {
		FileOutputStream fos = null;
		BufferedWriter bufWriter = null;
		try {
			fos = new FileOutputStream(filename);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fos,
					ModelUtils.DEFAULT_ENCODING));
			XiNode root = getTestDataXiNodeRoot(entity, model, selectTableModel);
			DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(
					bufWriter, ModelUtils.DEFAULT_ENCODING);
			root.serialize(handler);
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(new Path(filename));
			if (file.exists()) {
				try {
					CommonUtil.refresh(file, IResource.DEPTH_ZERO, false);
				} catch (Exception ce) {
					ce.printStackTrace();
				}
			}
			// XiSerializer.serialize(root, bufWriter, "UTF-8", /*pretty
			// printing*/ true);

		} catch (IOException e) {
			StudioTesterCorePlugin.log(e);
		} catch (Exception e) {
			StudioTesterCorePlugin.log(e);
		} finally {
			try {
				fos.close();
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static XiNode getTestDataXiNodeRoot(Entity entity,
			DefaultTableModel model, DefaultTableModel selectTableModel)
			throws Exception {
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		XiNode testdataRoot = factory.createElement(ExpandedName
				.makeName("testdata"));
		root.appendChild(testdataRoot);
		Vector<Vector> dataVector = model.getDataVector();
		String[] rows = new String[dataVector.size()];
		for (int i = 0; i < dataVector.size(); i++) {
			int rowlength = dataVector.elementAt(i).size();
			EList<PropertyDefinition> list = null;
			int col = 1;
			XiNode entityElement = factory.createElement(ExpandedName.makeName(
					ENTITY_NS + entity.getFullPath(), entity.getName()));

			if (selectTableModel != null) {
				// for Select Table
				boolean select = (Boolean) selectTableModel.getValueAt(i, 0);
				if (select) {
					XiNode selectAttribElement = factory.createAttribute(
							ExpandedName.makeName("isSelected"),
							selectTableModel.getValueAt(i, 0).toString());// Adding
																			// isSelected
																			// attribute
					entityElement.appendChild(selectAttribElement);
				}
			}

			if (entity instanceof com.tibco.cep.designtime.core.model.element.Concept) {
				com.tibco.cep.designtime.core.model.element.Concept concept = (com.tibco.cep.designtime.core.model.element.Concept) entity;
				list = concept.getAllProperties();
				Object extIdOb = model.getValueAt(i, col);
				String extId = extIdOb == null ? "" : extIdOb.toString();
				if (!extId.trim().equals("")) {
					XiNode extIdAttribElement = factory.createAttribute(
							ExpandedName.makeName("extId"), extId);// Adding
																	// extId
																	// attribute
					entityElement.appendChild(extIdAttribElement);
				}
				col++;
				for (int j = col; j < rowlength; j++) {
					rows[i] = (String) (dataVector.elementAt(i).elementAt(j));
					String columnName = model.getColumnName(j);
					PropertyDefinition propertyDefinition = getPropertyDefinition(
							columnName, list);
					saveXMLEntityTestData(factory, entity, propertyDefinition,
							entityElement, rows[i], columnName);
				}
			}
			if (entity instanceof Event) {
				Event event = (Event) entity;
				list = event.getAllUserProperties();

				Object extIdOb = model.getValueAt(i, 2);
				String extId = extIdOb == null ? "" : extIdOb.toString();

				if (!extId.trim().equals("")) {
					XiNode extIdAttribElement = factory.createAttribute(
							ExpandedName.makeName("extId"), extId);// Adding
																	// extId
																	// attribute
					entityElement.appendChild(extIdAttribElement);
				}

				Object payloadObj = model.getValueAt(i, 1);
				String val = payloadObj == null ? "" : payloadObj.toString();

				if (event.getPayloadString() != null && !val.trim().equals("")) {
					XiNode payloadElement = factory.createElement(ExpandedName
							.makeName("payload"));
					if (!val.trim().startsWith("<")) {
						// payload is JSON, save as an attribute
						payloadElement.setAttributeStringValue(ExpandedName.makeName("json"), val);
					} else {
						// InputStream inputstream = new
						// ByteArrayInputStream(val.getBytes("UTF-8"));
						XiNode payloadNode = XiParserFactory.newInstance().parse(
								new InputSource(new StringReader(val)));
						payloadElement.appendChild(payloadNode.getFirstChild());
					}
					entityElement.appendChild(payloadElement);
				}
				for (int j = 3; j < rowlength; j++) {
					rows[i] = (String) (dataVector.elementAt(i).elementAt(j));
					String columnName = model.getColumnName(j);
					PropertyDefinition propertyDefinition = getPropertyDefinition(
							columnName, list);
					saveXMLEntityTestData(factory, entity, propertyDefinition,
							entityElement, rows[i], columnName);
				}
			}
			testdataRoot.appendChild(entityElement);
		}
		return root;
	}

	/**
	 * @param factory
	 * @param propertyDefinition
	 * @param entityElement
	 * @param value
	 * @param property
	 * @return
	 * @throws Exception
	 */
	public static XiNode saveXMLEntityTestData(XiFactory factory,
			Entity entity, PropertyDefinition propertyDefinition,
			XiNode entityElement, String value, String property)
			throws Exception {
	
		if (entity != null) {
			if (entity instanceof com.tibco.cep.designtime.core.model.element.Concept) {
				if (propertyDefinition.isArray()) {
					String[] values = value.split(TesterCoreUtils.SPLIT, -1);
					for (String val : values) {
						XiNode element = factory.createElement(ExpandedName
								.makeName(property));
						if (propertyDefinition.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE
								|| propertyDefinition.getType() == PROPERTY_TYPES.CONCEPT) {
							setConceptRefContainedType(factory, element,
									propertyDefinition, val);
						} else {
							element.setStringValue(val);
						}
						entityElement.appendChild(element);
					}
				} else {
					XiNode element = factory.createElement(ExpandedName
							.makeName(property));
					if (propertyDefinition.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE
							|| propertyDefinition.getType() == PROPERTY_TYPES.CONCEPT) {
						setConceptRefContainedType(factory, element,
								propertyDefinition, value);
					} else {
						element.setStringValue(value);
					}
					entityElement.appendChild(element);
				}
			}
			if (entity instanceof Event) {
				XiNode element = factory.createElement(ExpandedName
						.makeName(property));
				element.setStringValue(value);
				entityElement.appendChild(element);
			}
		}
		
		return entityElement;
	}

	/**
	 * @param factory
	 * @param element
	 * @param propertyDefinition
	 * @param value
	 */
	private static void setConceptRefContainedType(XiFactory factory,
			XiNode element, PropertyDefinition propertyDefinition, String value) {
	//	String conceptTypePath = propertyDefinition.getConceptTypePath();
		List<String> list=conrefdata.get(propertyDefinition.getName());
		String val="";
		String[] parts = null;
		String resourcePath = null;
		if(value!=null&&!value.equals("")){
			val=list.get(Integer.parseInt(value.toString()));
			parts=val.split("\\.");
			if(parts!=null){
				if(parts.length==3){
					resourcePath=TesterCoreUtils.FORWARD_SLASH+parts[0]+TesterCoreUtils.FORWARD_SLASH+parts[1];
				}else if(parts.length>3){
					resourcePath="";
					for(int i=0;i<parts.length-1;i++){
						resourcePath=resourcePath+TesterCoreUtils.FORWARD_SLASH+parts[i];
					}
				}
			}
		}
		
		XiNode resPathAttribElement = factory.createAttribute(
				ExpandedName.makeName("resourcePath"), resourcePath!=null?resourcePath:"");
		element.appendChild(resPathAttribElement);

		XiNode rowIdAttribElement = factory.createAttribute(
				ExpandedName.makeName("rowNum"), parts!=null?parts[parts.length-1].charAt(parts[parts.length-1].length()-2)+"":"");
		element.appendChild(rowIdAttribElement);

		XiNode typeAttribElement = factory.createAttribute(ExpandedName
				.makeName("type"), propertyDefinition.getType().getName());
		element.appendChild(typeAttribElement);
	}

	/**
	 * @param columnName
	 * @param list
	 * @return
	 */
	public static PropertyDefinition getPropertyDefinition(String columnName,
			List<PropertyDefinition> list) {
		for (PropertyDefinition propertyDefinition : list) {
			if (propertyDefinition.getName().equals(columnName)) {
				return propertyDefinition;
			}
		}
		return null;
	}

	public static void exportDataToXMLDataFilefromCli(String filename,
			TestDataModel model,List<DesignerElement> elementList) {
		exportDataToXMLDataFile(filename, model, false, true,null,elementList);
	}

	public static void exportDataToXMLDataFile(String filename,
			TestDataModel model, boolean fromTableAnalyser, boolean fromCli,Map<String,List<String>> conrefConcepts,List<DesignerElement> elementList) {
	
		
		conrefdata=conrefConcepts;
		int loopCounter = 0;
		if (fromCli) {
			model.generateVectorforCLI();
		}
		if (fromTableAnalyser || fromCli) {
			loopCounter = model.getVectors().size();
		} else {
			loopCounter = model.getInput().size();
		}

		String ENTITY_NS = "www.tibco.com/be/ontology";
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		XiNode testdataRoot = factory.createElement(ExpandedName.makeName("testdata"));
		LinkedHashMap<String,String> properties = model.getProperties();
		
		String rate = properties.get("rate");
		if (rate != null) {
			testdataRoot.setAttributeStringValue(ExpandedName.makeName("rate"), rate);
		}
		String invokePreprocessor = properties.get("preprocessor");
		if (invokePreprocessor != null) {
			testdataRoot.setAttributeStringValue(ExpandedName.makeName("preprocessor"), invokePreprocessor);
		}
		
		String parts[]=filename.contains(FORWARD_SLASH)?filename.split(FORWARD_SLASH):filename.split("\\\\");
		XiNode nameAttribElement = factory.createAttribute(ExpandedName.makeName("name"), (parts[parts.length-1]).split("\\.")[0]);// Adding
		XiNode folderAttribElement = factory.createAttribute(ExpandedName.makeName("folder"), parts[parts.length-2]);// Adding
		XiNode projectNameAttribElement = factory.createAttribute(ExpandedName.makeName("project"), model.getEntity().getOwnerProjectName());// Adding
		XiNode entPathAttribElement = factory.createAttribute(ExpandedName.makeName("entityPath"), model.getEntity().getFullPath());// Adding
		XiNode entTypeAttribElement = factory.createAttribute(ExpandedName.makeName("type"), CommonIndexUtils.getElementType(model.getEntity()).getName());// Adding
		testdataRoot.appendChild(nameAttribElement);
		testdataRoot.appendChild(folderAttribElement);
		testdataRoot.appendChild(projectNameAttribElement);
		testdataRoot.appendChild(entPathAttribElement);
		testdataRoot.appendChild(entTypeAttribElement);
		
	//	createEntityList(elementList);
		root.appendChild(testdataRoot);
		FileOutputStream fos = null;
		BufferedWriter bufWriter = null;
		try {
			fos = new FileOutputStream(filename);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fos,
					ModelUtils.DEFAULT_ENCODING));
			String[] rows = new String[model.getVectors().size()];
			for (int i = 0; i < loopCounter; i++) {
				int rowlength = model.getVectors().elementAt(i).size();
				List<PropertyDefinition> list = null;
				int col = 1;
				XiNode entityElement = factory.createElement(ExpandedName
						.makeName(ENTITY_NS + model.getEntity().getFullPath(),
								model.getEntity().getName()));
				// for Select Table
				boolean select = (Boolean) model.getSelectRowData().get(i);
				XiNode selectAttribElement = factory.createAttribute(
						ExpandedName.makeName("isSelected"), model
								.getSelectRowData().get(i).toString());// Adding
				// isSelected
				// attribute
				entityElement.appendChild(selectAttribElement);

				if (model.getEntity() instanceof Concept) {
					Concept concept = (Concept) model.getEntity();
					
					list=getProp(concept);
					/*if(concept.getSuperConceptPath()!=null && !concept.getSuperConceptPath().isEmpty()){
						if(concept.getSuperConcept()==null){
							String superConceptPath=concept.getSuperConceptPath();
							Path path=new Path(superConceptPath);
							String superConceptName=path.lastSegment().toString();
							for(Entity ent:entityList){
								if(ent.getName().equals(superConceptName)){
									Concept superConcept=(Concept)ent;
									list=getProp(superConcept);
									break;
								}
							}
						}
					}*/
					
				//	list = getAllConceptProperties(concept);
					Object extIdOb=null;
					if(!fromCli && !fromTableAnalyser){
						extIdOb = model.getVectors().get(i).get(2);
					}
					else{
						extIdOb = model.getTestData().get(i).get(0);
					}
					
					
					String extId = extIdOb == null ? "" : extIdOb.toString();
					// FIXME : We should always write out the payload and extid, otherwise it throws off the index/offsets when it is suddenly added and
					// pushes out values to the improper location
					if (!extId.trim().equals("")) {
						XiNode extIdAttribElement = factory.createAttribute(
								ExpandedName.makeName("extId"), extId);// Adding
																		// extId
																		// attribute
						entityElement.appendChild(extIdAttribElement);
					}
					col++;
					for (int j = 1, k = 2; k < rowlength; j++, k++) {
						rows[i] = (String) (model.getVectors().elementAt(i)
								.elementAt(k));
						if(rows[i].contains(TesterCoreUtils.MARKER)){
							rows[i]=rows[i].split(TesterCoreUtils.MARKER)[0];
						}
						String columnName = model.getTableColumnNames().get(j);
						if (!columnName.equalsIgnoreCase("Use")
								&& !columnName.equalsIgnoreCase("ExtId")
								&& !columnName.equalsIgnoreCase("Payload")
								&& !columnName.equalsIgnoreCase(UID_COL_NAME)) {
							PropertyDefinition propertyDefinition = getPropertyDefinition(
									columnName, list);
							String multipleValues = rows[i];
							if (multipleValues.contains(";")) {
								String split[] = multipleValues.split(";");
								for (String str : split) {
									saveXMLEntityTestData(factory,
											model.getEntity(),
											propertyDefinition, entityElement,
											str, columnName);
								}
								continue;
							}
							saveXMLEntityTestData(factory, model.getEntity(),
									propertyDefinition, entityElement, rows[i],
									columnName);
						}
					}
				}
				if (model.getEntity() instanceof Event) {
					Event event = (Event) model.getEntity();
					list = event.getAllUserProperties();
					Object extIdOb = null;
					
					if(!fromCli && !fromTableAnalyser){
						extIdOb = model.getVectors().get(i).get(4);
					}
					else{
						extIdOb = model.getTestData().get(i).get(1);
					}
					String extId = extIdOb == null ? "" : extIdOb.toString();
					if (!extId.trim().equals("")) {
						XiNode extIdAttribElement = factory.createAttribute(
								ExpandedName.makeName("extId"), extId);
						entityElement.appendChild(extIdAttribElement);
					}
					
					Object payloadObj=null;
					if(!fromCli && !fromTableAnalyser){
						payloadObj = model.getVectors().get(i).get(3);
					}
					else{
						payloadObj = model.getTestData().get(i).get(0);
					}
					
					String val = payloadObj == null ? "" : payloadObj
							.toString();
					if (event.getPayloadString() != null
							&& !val.trim().equals("")) {
						XiNode payloadElement = factory
								.createElement(ExpandedName.makeName("payload"));
						if (!val.trim().startsWith("<")) {
							// payload is JSON, save as an attribute
							payloadElement.setAttributeStringValue(ExpandedName.makeName("json"), val);
						} else {
							// InputStream inputstream = new
							// ByteArrayInputStream(val.getBytes("UTF-8"));
							XiNode payloadNode = XiParserFactory.newInstance()
									.parse(new InputSource(new StringReader(val)));
							payloadElement.appendChild(payloadNode.getFirstChild());
						}
						entityElement.appendChild(payloadElement);
					}
					for (int j = 1, k = 3; k < rowlength; j++, k++) {
						rows[i] = (String) (model.getVectors().elementAt(i)
								.elementAt(k));
						String columnName = null;
						columnName = model.getTableColumnNames().get(j);
						if (!columnName.equalsIgnoreCase("Use")
								&& !columnName.equalsIgnoreCase("ExtId")
								&& !columnName.equalsIgnoreCase("Payload")
								&& !columnName.equalsIgnoreCase(UID_COL_NAME)) {
							PropertyDefinition propertyDefinition = getPropertyDefinition(
									columnName, list);
							saveXMLEntityTestData(factory, model.getEntity(),
									propertyDefinition, entityElement, rows[i],
									columnName);
						}
					}
				}
				testdataRoot.appendChild(entityElement);
			}

			DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(
					bufWriter, ModelUtils.DEFAULT_ENCODING);
			root.serialize(handler);
			if (!fromCli) {
				IFile file = ResourcesPlugin.getWorkspace().getRoot()
						.getFileForLocation(new Path(filename));
				if (file.exists()) {
					try {
						CommonUtil.refresh(file, IResource.DEPTH_ZERO, false);
					} catch (Exception ce) {
						ce.printStackTrace();
					}
				}
			}
			// XiSerializer.serialize(root, bufWriter, "UTF-8", /*pretty
			// printing*/ true);

		} catch (IOException e) {
			StudioTesterCorePlugin.log(e);
		} catch (Exception e) {
			StudioTesterCorePlugin.log(e);
		} finally {
			try {
				fos.close();
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	private static void createEntityList(List<DesignerElement> elementList){
		for (DesignerElement element: elementList) {
			if (element instanceof EntityElement) {
				Entity entity = ((EntityElement)element).getEntity();
				entityList.add(entity);
			}
		}
	}


	public static void exportDataToXMLDataFileAppend(String filename,
			Entity entity, TestDataModel newModel,
			List<String> tableColumnNames, boolean fromTableAnalyser) {

		int loopCounter = newModel.getAppendedVectors().size();
		String entityType = null;
		boolean flag = false;
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
		TestDataModel existingModel;
		TestDataModel model = null;
		try {
			existingModel = getDataFromXML(filename, entityType, entity,
					new ArrayList<String>());
			model = combineModel(existingModel, newModel);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ENTITY_NS = "www.tibco.com/be/ontology";
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		XiNode testdataRoot = factory.createElement(ExpandedName
				.makeName("testdata"));
		root.appendChild(testdataRoot);
		FileOutputStream fos = null;
		BufferedWriter bufWriter = null;
		try {
			fos = new FileOutputStream(filename);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fos,
					ModelUtils.DEFAULT_ENCODING));
			String[] rows = new String[model.getAppendedVectors().size()];
			for (int i = 0; i < loopCounter; i++) {
				int rowlength = model.getAppendedVectors().elementAt(i).size();
				EList<PropertyDefinition> list = null;
				int col = 1;
				XiNode entityElement = factory.createElement(ExpandedName
						.makeName(ENTITY_NS + entity.getFullPath(),
								entity.getName()));
				// for Select Table
				boolean select = (Boolean) model.getSelectRowData().get(i);

				XiNode selectAttribElement = factory.createAttribute(
						ExpandedName.makeName("isSelected"), model
								.getSelectRowData().get(i).toString());// Adding
				// isSelected
				// attribute
				entityElement.appendChild(selectAttribElement);

				if (entity instanceof Concept) {
					Concept concept = (Concept) entity;
					list = concept.getAllProperties();
					Object extIdOb = model.getTestData().get(i).get(0);
					String extId = extIdOb == null ? "" : extIdOb.toString();
					if (!extId.trim().equals("")) {
						XiNode extIdAttribElement = factory.createAttribute(
								ExpandedName.makeName("extId"), extId);// Adding
																		// extId
																		// attribute
						entityElement.appendChild(extIdAttribElement);
					}
					col++;
					for (int j = 3, k = 2; j < rowlength; j++, k++) {
						rows[i] = (String) (model.getAppendedVectors()
								.elementAt(i).elementAt(j));
						String columnName = tableColumnNames.get(k);
						if (!columnName.equalsIgnoreCase("Use")
								&& !columnName.equalsIgnoreCase("ExtId")
								&& !columnName.equalsIgnoreCase("Payload")
								&& !columnName.equalsIgnoreCase(UID_COL_NAME)) {
							PropertyDefinition propertyDefinition = getPropertyDefinition(
									columnName, list);
							saveXMLEntityTestData(factory, entity,
									propertyDefinition, entityElement, rows[i],
									columnName);
						}
					}
				}
				if (entity instanceof Event) {
					Event event = (Event) entity;
					list = event.getAllUserProperties();
					Object extIdOb = model.getTestData().get(i).get(0);
					String extId = extIdOb == null ? "" : extIdOb.toString();
					if (!extId.trim().equals("")) {
						XiNode extIdAttribElement = factory.createAttribute(
								ExpandedName.makeName("extId"), extId);
						entityElement.appendChild(extIdAttribElement);
					}
					Object payloadObj = model.getTestData().get(i).get(1);
					String val = payloadObj == null ? "" : payloadObj
							.toString();
					if (event.getPayloadString() != null
							&& !val.trim().equals("")) {
						XiNode payloadElement = factory
								.createElement(ExpandedName.makeName("payload"));
						if (!val.trim().startsWith("<")) {
							// payload is JSON, save as an attribute
							payloadElement.setAttributeStringValue(ExpandedName.makeName("json"), val);
						} else {
							// InputStream inputstream = new
							// ByteArrayInputStream(val.getBytes("UTF-8"));
							XiNode payloadNode = XiParserFactory.newInstance()
									.parse(new InputSource(new StringReader(val)));
							payloadElement.appendChild(payloadNode.getFirstChild());
						}
						entityElement.appendChild(payloadElement);
					}
					for (int j = 4, k = 3; j < rowlength; j++, k++) {
						rows[i] = (String) (newModel.getAppendedVectors()
								.elementAt(i).elementAt(j));
						String columnName = null;
						columnName = tableColumnNames.get(k);
						if (!columnName.equalsIgnoreCase("Use")
								&& !columnName.equalsIgnoreCase("ExtId")
								&& !columnName.equalsIgnoreCase("Payload")
								&& !columnName.equalsIgnoreCase(UID_COL_NAME)) {
							PropertyDefinition propertyDefinition = getPropertyDefinition(
									columnName, list);
							saveXMLEntityTestData(factory, entity,
									propertyDefinition, entityElement, rows[i],
									columnName);
						}
					}
				}
				testdataRoot.appendChild(entityElement);
			}

			DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(
					bufWriter, ModelUtils.DEFAULT_ENCODING);
			root.serialize(handler);
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(new Path(filename));
			if (file.exists()) {
				try {
					CommonUtil.refresh(file, IResource.DEPTH_ZERO, false);
				} catch (Exception ce) {
					ce.printStackTrace();
				}
			}
			// XiSerializer.serialize(root, bufWriter, "UTF-8", /*pretty
			// printing*/ true);

		} catch (IOException e) {
			StudioTesterCorePlugin.log(e);
		} catch (Exception e) {
			StudioTesterCorePlugin.log(e);
		} finally {
			try {
				fos.close();
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static TestDataModel getDataFromXML(String filename,
			String entityType, Entity entity, List<String> tableColumnNames)
			throws Exception {

		ArrayList<String> list = new ArrayList<String>();
		TestDataModel model = new TestDataModel(entity, tableColumnNames,
				new ArrayList<List<String>>());
		File file = new File(filename);
		if (file.exists() && file.length() > 0) {
			FileInputStream fis = new FileInputStream(file);
			XiNode rootNode = XiParserFactory.newInstance().parse(
					new InputSource(fis));
			XiNode mainNode = rootNode.getFirstChild();
			Iterator attributes = mainNode.getAttributes();
			LinkedHashMap<String,String> properties = model.getProperties();
			while (attributes.hasNext()) {
				Object childNode = attributes.next();
				if (childNode instanceof Attribute) {
					Attribute attr = (Attribute) childNode;
					properties.put(attr.getName().localName, attr.getStringValue());
				}
			}
			Iterator<XiNode> subNodeIterator = mainNode.getChildren();
			while (subNodeIterator.hasNext()) {
				XiNode childNode = subNodeIterator.next();
				XiNode selectAttrNode = childNode.getAttribute(ExpandedName
						.makeName("isSelected"));
				boolean select = selectAttrNode.getStringValue() == null ? false
						: Boolean.parseBoolean(selectAttrNode.getStringValue());
				model.getSelectRowData().add(select);
				model.setEntityInfo(entity.toString());
				list = new ArrayList<String>();
				if (entityType.equals("Concept")||entityType.equals("Scorecard")) {
					XiNode attrNode = childNode.getAttribute(ExpandedName
							.makeName("extId"));
					if (attrNode != null) {
						// model.getExtId().add(attrNode.getStringValue() ==
						// null ? "" : attrNode.getStringValue());
						list.add(attrNode.getStringValue() == null ? ""
								: attrNode.getStringValue());
					} else {
						// model.getExtId().add("");
						list.add("");
					}
				}
				Iterator<XiNode> propertyNodeIterator = childNode.getChildren();
				if (entityType.equals("Event")) {
					XiNode payloadNode = childNode.getFirstChild();
					String entityString = "";
					if (payloadNode != null
							&& payloadNode.getName().equals(
									ExpandedName.makeName("payload"))) {
						// v.add(payloadNode.getStringValue() == null ? "" :
						// payloadNode.getStringValue());
						String jsonString = payloadNode.getAttributeStringValue(ExpandedName.makeName("json"));
						if (jsonString != null && !jsonString.isEmpty()) {
							entityString = jsonString;
						} else {
							XiNode node = payloadNode.getFirstChild();
							if (node != null) {
								StringWriter stringWriter = new StringWriter();
								DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(
										stringWriter, "UTF-8");
								node.serialize(handler);
								entityString = stringWriter.toString();
							}
						}
					}
					// model.getPayload().add(entityString);
					list.add(entityString);
					XiNode attrNode = childNode.getAttribute(ExpandedName
							.makeName("extId"));
					if (attrNode != null) {
						// model.getExtId().add(attrNode.getStringValue() ==
						// null ? "" : attrNode.getStringValue());
						list.add(attrNode.getStringValue() == null ? ""
								: attrNode.getStringValue());
					} else {
						// model.getExtId().add("");
						list.add("");
					}
					List<String> nodeNames=new ArrayList<String>();
					isIncompatible=false;
				/*	while (propertyNodeIterator.hasNext()) {
						XiNode propertyNode = propertyNodeIterator.next();
						nodeNames.add(propertyNode.getName().toString());
						if(tableColumnNames.contains(propertyNode.getName().toString())){
							if (!propertyNode.getName().equals(ExpandedName.makeName("payload"))) {
								list.add(propertyNode.getStringValue());
							}
							if (!propertyNodeIterator.hasNext()) {
								for(String colName:tableColumnNames){
									if(!colName.equalsIgnoreCase("Use")&&!colName.equalsIgnoreCase("ExtId")&&!colName.equalsIgnoreCase("Payload")&&!colName.equalsIgnoreCase(UID_COL_NAME)){
										if(!nodeNames.contains(colName)){
											//XiNode node=XiSupport.getXiFactory().createElement(ExpandedName.makeName(colName));
											list.add("");
										}
									}
								}
								//model.getTestData().add(list);
							}
							
						}
						else{
							//isIncompatible=true;
						}*/
						
						for(String colName : tableColumnNames){
							Iterator<XiNode> propertyNodeItr = childNode.getChildren();
							Boolean isNodeExist = false;
							if (colName.equalsIgnoreCase("ExtId") || colName.equalsIgnoreCase("Payload")) {
								// these have already been added, skip or the column offsets will be incorrect
								continue;
							}
							while(propertyNodeItr.hasNext()){
								XiNode propertyNode = propertyNodeItr.next();
								System.out.println(propertyNode.getName().toString());
								if(propertyNode.getName().toString().equalsIgnoreCase(colName)){
									list.add(propertyNode.getStringValue());
									nodeNames.add(propertyNode.getName().toString());
									isNodeExist = true;
								}
							}
							if(!isNodeExist && !colName.equalsIgnoreCase("Use")&&!colName.equalsIgnoreCase("ExtId") && !colName.equalsIgnoreCase("Payload")&&!colName.equalsIgnoreCase(UID_COL_NAME)){
								list.add("");
							}
						}
					
				} else {
					List<XiNode> propertyNodes = new ArrayList<XiNode>();
					isIncompatible=false;
					/*while (propertyNodeIterator.hasNext()) {
						XiNode propertyNode = propertyNodeIterator.next();
						if(tableColumnNames.contains(propertyNode.getName().toString())){		//handling delete property case.
							propertyNodes.add(propertyNode);
							
						}
						else{ 
							//isIncompatible=true;															
						}
					}*/
					
					for(String colName : tableColumnNames){
						Iterator<XiNode> propertyNodeItr = childNode.getChildren();
						Boolean isNodeExist = false;
						while(propertyNodeItr.hasNext()){
							XiNode propertyNode = propertyNodeItr.next();
							
							if(propertyNode.getName().toString().equalsIgnoreCase(colName)){
								propertyNodes.add(propertyNode);
								isNodeExist = true;
							}
						}
						if(!isNodeExist && !colName.equalsIgnoreCase("Use")&&!colName.equalsIgnoreCase("ExtId")){
							XiNode node=XiSupport.getXiFactory().createElement(ExpandedName.makeName(colName));
							propertyNodes.add(node);
						}
					}
					
					List<String> nodeNames=new ArrayList<String>();
					for(XiNode pNode:propertyNodes){
						nodeNames.add(pNode.getName().toString());
					}
					if(propertyNodes.size()!=tableColumnNames.size()-2){
						for(String colName:tableColumnNames){
							if(!colName.equalsIgnoreCase("Use")&&!colName.equalsIgnoreCase("ExtId")){
								if(!nodeNames.contains(colName)){
									XiNode node=XiSupport.getXiFactory().createElement(ExpandedName.makeName(colName));
									propertyNodes.add(node);
								}
							}
						}
					}

					List<XiNode> mulNodes = new ArrayList<XiNode>();
					for (int k = 0; k < propertyNodes.size(); k++) {
						if (k > 0
								&& propertyNodes
										.get(k)
										.getName()
										.equals(propertyNodes.get(k - 1)
												.getName())) {

							if (!mulNodes.contains(propertyNodes.get(k - 1))) {
								mulNodes.add(propertyNodes.get(k - 1));
							}

							if (!mulNodes.contains(propertyNodes.get(k))) {
								mulNodes.add(propertyNodes.get(k));
							}

							if ((k + 1 < propertyNodes.size())
									&& (!propertyNodes
											.get(k)
											.getName()
											.equals(propertyNodes.get(k + 1)
													.getName()))) {
								setColumnValue(mulNodes, list);
								// String fValue =
								// mulNodes.get(0).getStringValue().toString();
								// for (int l = 1; l < mulNodes.size(); l++) {
								// fValue = fValue + TestUtil.SPLIT +
								// mulNodes.get(l).getStringValue();
								// }
								//
								// v.add(fValue);
							}
							if (k + 1 == propertyNodes.size()
									&& mulNodes.size() > 0) {

								// String fValue =
								// mulNodes.get(0).getStringValue().toString();
								// for (int l = 1; l < mulNodes.size(); l++) {
								// fValue = fValue + TestUtil.SPLIT +
								// mulNodes.get(l).getStringValue();
								// }
								// v.add(fValue);

								setColumnValue(mulNodes, list);

							}
						} else {
							if ((k + 1 < propertyNodes.size())
									&& (!propertyNodes
											.get(k)
											.getName()
											.equals(propertyNodes.get(k + 1)
													.getName()))) {
								// v.add(propertyNodes.get(k).getStringValue());
								setColumnValue(propertyNodes.get(k), list,
										propertyNodes.get(k).getStringValue());
							} else {
								if (k == propertyNodes.size() - 1) {
									if (!mulNodes
											.contains(propertyNodes.get(k))) {
										// v.add(propertyNodes.get(k).getStringValue());
										setColumnValue(propertyNodes.get(k),
												list, propertyNodes.get(k)
														.getStringValue());
									}
								}
							}
							mulNodes = new ArrayList<XiNode>();
						}

					}
				//	model.getTestData().add(list);
				}
				model.getTestData().add(list);
			}
			fis.close();
		}
		return model;
	}

	private static void setColumnValue(List<XiNode> mulNodes,
			ArrayList<String> v) {
		String fValue = "";
	
		XiNode typeNode = mulNodes.get(0).getAttribute(
				ExpandedName.makeName("type"));
		if (typeNode != null
				&& (typeNode.getStringValue().equals(
						PROPERTY_TYPES.CONCEPT_REFERENCE.getName()) || typeNode
						.getStringValue().equals(
								PROPERTY_TYPES.CONCEPT.getName()))) {
			fValue = mulNodes.get(0).getAttributeStringValue(
					ExpandedName.makeName("rowNum"));
			
			for (int l = 1; l < mulNodes.size(); l++) {
				fValue = fValue
						+ TesterCoreUtils.SPLIT
						+ mulNodes.get(l).getAttributeStringValue(
								ExpandedName.makeName("rowNum"));
			}

		} else {
			fValue = mulNodes.get(0).getStringValue().toString();
			for (int l = 1; l < mulNodes.size(); l++) {
				fValue = fValue + TesterCoreUtils.SPLIT
						+ mulNodes.get(l).getStringValue();
			}
		}
		v.add(fValue);
	}

	private static void setColumnValue(XiNode node, ArrayList<String> v,
			String value) {
		/*String resourcePath="";
		resourcePath=mulNodes.get(0).getAttributeStringValue(
				ExpandedName.makeName("resourcePath"));*/
		XiNode typeNode = node.getAttribute(ExpandedName.makeName("type"));
		if (typeNode != null
				&& (typeNode.getStringValue().equals(
						PROPERTY_TYPES.CONCEPT_REFERENCE.getName()) || typeNode
						.getStringValue().equals(
								PROPERTY_TYPES.CONCEPT.getName()))) {
			String fValue = node.getAttributeStringValue(ExpandedName
					.makeName("rowNum"));
			String resourcePath=node.getAttributeStringValue(ExpandedName.makeName("resourcePath"));
			if(fValue!=null&&!fValue.equals("")){
				v.add(fValue+TesterCoreUtils.MARKER + resourcePath);
			}
			else{
				v.add(fValue);
			}
		} else {
			v.add(value);
		}
	}

	public static TestDataModel combineModel(TestDataModel existingModel,
			TestDataModel newModel) {

		TestDataModel model = new TestDataModel(existingModel.getEntity(),
				existingModel.getTableColumnNames(),
				existingModel.getTestData());
		model.setEntityInfo(existingModel.getEntityInfo());
		// existingModel.getExtId().addAll(newModel.getExtId());
		// model.setExtId(existingModel.getExtId());
		// existingModel.getPayload().addAll(newModel.getPayload());
		// model.setPayload(existingModel.getPayload());
		existingModel.getSelectRowData().addAll(newModel.getSelectRowData());
		model.setSelectRowData(existingModel.getSelectRowData());
		existingModel.getTestData().addAll(newModel.getTestData());
		model.setTestData(existingModel.getTestData());
		existingModel.getAppendedVectors()
				.addAll(newModel.getAppendedVectors());
		model.setAppendedVectors(existingModel.getAppendedVectors());
		existingModel.getInput().addAll(newModel.getInput());
		model.setInput(existingModel.getInput());
		existingModel.getVectors().addAll(newModel.getVectors());
		model.setVectors(existingModel.getVectors());

		return model;
	}

	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static TesterResultsType processTestResult(Object data,
			String className) throws Exception {
		String responseString = (String) data;
		// StudioTesterCorePlugin.debug(className, "Response String {0}",
		// responseString);
		if (responseString != null) {
			TestResultsDeserializer deserializer = new TestResultsDeserializer();
			TesterResultsType testerResultsType = deserializer.deserialize(responseString);
			return testerResultsType;
			// return TestResultMarshaller.deserialize(responseString);
		}
		return null;
	}

	/**
	 * 
	 * @param resultRelPath
	 * @param projectName
	 * @param testerResultsType
	 * @param outputPath
	 * @param cli
	 * @return
	 * @throws Exception
	 */
	public static IFile saveTestResult(String resultRelPath,
			String projectName, TesterResultsType testerResultsType,
			String outputPath, boolean cli) throws Exception {

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		String projectPath = project.getLocation().toString();

		StringBuilder stringBuilder = new StringBuilder(projectPath);
		stringBuilder.append(outputPath);
		stringBuilder.append("/");
		stringBuilder.append(resultRelPath);
		stringBuilder.append(".resultdata");
		String file = stringBuilder.toString();
		File f = new File(file);
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();

		TestResultsDeserializer deserializer = new TestResultsDeserializer();
		deserializer.serialize(file, testerResultsType);
		// TestResultMarshaller.serialize(URI.createFileURI(file),
		// testerResultsType);

		if (!cli) {
			// refreshing project
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
		}

		stringBuilder = new StringBuilder(outputPath);
		stringBuilder.append("/");
		stringBuilder.append(resultRelPath);
		stringBuilder.append(".resultdata");
		IPath path = Path.fromOSString(stringBuilder.toString());
		IFile ifile = project.getFile(path);

		return ifile;
	}
	
	public static boolean saveTestResultCli(String resultPath, String resultFile, Object testerResults)throws Exception{
		
		StringBuilder stringBuilder = new StringBuilder(resultPath);
		stringBuilder.append("/");
		stringBuilder.append(resultFile);
		stringBuilder.append(".resultdata");
		String file = stringBuilder.toString();
		File f = new File(file);
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();

		if(testerResults instanceof TesterResultsType){
		
			TestResultsDeserializer deserializer = new TestResultsDeserializer();
			deserializer.serialize(file, (TesterResultsType) testerResults);
			
		}else if(testerResults instanceof TesterResultsOperation){
			
			TestResultsOpsDeserializer deserializer = new TestResultsOpsDeserializer();
			deserializer.serialize(file, (TesterResultsOperation) testerResults);
			
		}if(testerResults instanceof TesterResultsDatatype){
			TestResultsDataTypeDeserializer deserializer = new TestResultsDataTypeDeserializer();
			deserializer.serialize(file, (TesterResultsDatatype) testerResults);
		}else{
			return false;
		}
		
		return true;
	}

	public static IFile saveTestResultOps(String resultRelPath,
			String projectName, TesterResultsOperation testerResultsType,
			String outputPath, boolean cli) throws Exception {

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		String projectPath = project.getLocation().toString();

		StringBuilder stringBuilder = new StringBuilder(projectPath);
		stringBuilder.append(outputPath);
		stringBuilder.append("/");
		stringBuilder.append(resultRelPath+"-ops");
		stringBuilder.append(".resultdata");
		String file = stringBuilder.toString();
		File f = new File(file);
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();

		TestResultsOpsDeserializer deserializer = new TestResultsOpsDeserializer();
		deserializer.serialize(file, testerResultsType);
		// TestResultMarshaller.serialize(URI.createFileURI(file),
		// testerResultsType);

		if (!cli) {
			// refreshing project
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
		}

		stringBuilder = new StringBuilder(outputPath);
		stringBuilder.append("/");
		stringBuilder.append(resultRelPath+"-ops");
		stringBuilder.append(".resultdata");
		IPath path = Path.fromOSString(stringBuilder.toString());
		IFile ifile = project.getFile(path);

		return ifile;
	}
	
	public static IFile saveTestResultType(String resultRelPath,
			String projectName, TesterResultsDatatype testerResultsType,
			String outputPath, boolean cli) throws Exception {

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		String projectPath = project.getLocation().toString();

		StringBuilder stringBuilder = new StringBuilder(projectPath);
		stringBuilder.append(outputPath);
		stringBuilder.append("/");
		stringBuilder.append(resultRelPath+"-type");
		stringBuilder.append(".resultdata");
		String file = stringBuilder.toString();
		File f = new File(file);
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();

		TestResultsDataTypeDeserializer deserializer = new TestResultsDataTypeDeserializer();
		deserializer.serialize(file, testerResultsType);
		// TestResultMarshaller.serialize(URI.createFileURI(file),
		// testerResultsType);

		if (!cli) {
			// refreshing project
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
		}

		stringBuilder = new StringBuilder(outputPath);
		stringBuilder.append("/");
		stringBuilder.append(resultRelPath+"-type");
		stringBuilder.append(".resultdata");
		IPath path = Path.fromOSString(stringBuilder.toString());
		IFile ifile = project.getFile(path);

		return ifile;
	}

	public static IFile saveTestResultByType(String resultRelPath,
			String projectName, TesterResultsType testerResultsType,
			String outputPath, boolean cli) throws Exception {

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		String projectPath = project.getLocation().toString();

		StringBuilder stringBuilder = new StringBuilder(projectPath);
		stringBuilder.append(outputPath);
		stringBuilder.append("/");
		stringBuilder.append(resultRelPath);
		stringBuilder.append(".resultdata");
		String file = stringBuilder.toString();
		File f = new File(file);
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (f.exists()) {
			f.delete();
		}
		f.createNewFile();

		TestResultsDeserializer deserializer = new TestResultsDeserializer();
		deserializer.serialize(file, testerResultsType);
		// TestResultMarshaller.serialize(URI.createFileURI(file),
		// testerResultsType);

		if (!cli) {
			// refreshing project
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
		}

		stringBuilder = new StringBuilder(outputPath);
		stringBuilder.append("/");
		stringBuilder.append(resultRelPath);
		stringBuilder.append(".resultdata");
		IPath path = Path.fromOSString(stringBuilder.toString());
		IFile ifile = project.getFile(path);

		return ifile;
	}

	public static List<File> getFilesList(File directory, String type) {
		List<File> filesList = new ArrayList<File>();
		if (!directory.isDirectory()) {
			return filesList;
		}
		File[] containedFiles = directory.listFiles();
		for (File file : containedFiles) {
			if (file.isDirectory()) {
				filesList.addAll(TesterCoreUtils.getFilesList(file, type));
			} else if (file.getName().endsWith("." + type)) {
				filesList.add(file);
			}
		}
		return filesList;
	}
	
	public static String getEntityInfo(String fileName){
		File file = new File(fileName);
		if(!file.exists()){
			fileName=fileName.replaceFirst("/TestData", "");
			file = new File(fileName);
		}
		if (file.exists() && file.length() > 0) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				XiNode rootNode = XiParserFactory.newInstance().parse(
						new InputSource(fis));
				XiNode mainNode = rootNode.getFirstChild();
				XiNode entityPathNode=mainNode.getAttribute(ExpandedName.makeName("entityPath"));
				if(entityPathNode!=null){
					return entityPathNode.getStringValue();
				}
				Iterator<XiNode> subNodeIterator = mainNode.getChildren();
				while (subNodeIterator.hasNext()) {
					XiNode childNode = subNodeIterator.next();
					String splits[]=childNode.getName().toString().split("www.tibco.com/be/ontology");
					return (splits[1].split("}"))[0];
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return fileName;
	}
	
	public static List<IResource> getTestDataItems(IProject project){
		List<IResource> output=new ArrayList<IResource>();
		IResource[] members=null;
		try {
			members=project.members();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < members.length; i++) {
			if (members[i].getType() == 1) {
				if (members[i].getFileExtension().equalsIgnoreCase(
						"concepttestdata")
						|| members[i].getFileExtension()
								.equalsIgnoreCase("eventtestdata")
						|| members[i].getFileExtension()
								.equalsIgnoreCase(
										"scorecardtestdata")) {
					output.add(members[i]);
				}
			}
			if (members[i].getType() == 2) {
				iterateFolder((IFolder) members[i], output);
			}
		}
		return output;
	}
	
	public static void iterateFolder(IFolder folder, List<IResource> output) {
		try {
			IResource members[] = folder.members();
			for (int i = 0; i < members.length; i++) {

				if (members[i].getType() == 1) {
					if (members[i].getFileExtension() != null) {
						if (members[i].getFileExtension().equalsIgnoreCase(
								"concepttestdata")
								|| members[i].getFileExtension()
										.equalsIgnoreCase("eventtestdata")
								|| members[i].getFileExtension()
										.equalsIgnoreCase("scorecardtestdata")) {
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
	
	public static List<PropertyDefinition> getProp(Concept concept){
		if(concept==null){
			return null;
		}
		List<PropertyDefinition> propertyList = new ArrayList<PropertyDefinition>();
		if(concept.getSuperConceptPath()!=null && !concept.getSuperConceptPath().isEmpty()){
			if(concept.getSuperConcept()==null){
				String superConceptPath=concept.getSuperConceptPath();
				Path path=new Path(superConceptPath);
				String superConceptName=path.lastSegment().toString();
				for(Entity ent:entityList){
					if(ent.getName().equals(superConceptName)){
						Concept superConcept=(Concept)ent;
						propertyList.addAll(getProp(superConcept));
						break;
					}
				}
			}
		}
		propertyList.addAll(concept.getAllPropertyDefinitions());
		return propertyList;
	}
	
	public static void processModel(TestDataModel model,String testDataFilePath){
		
		Vector<Vector> dataVector = new Vector<Vector>();
		dataVector = model.getVectors();
		for(Vector v : dataVector){
			if(model.getEntity() instanceof ConceptImpl){
				v.remove(1);
			}
			else if(model.getEntity() instanceof SimpleEventImpl){
				v.remove(1);
				v.remove(2);
			}
				
		}
		
		TestDataModel existingModel=null;
		Entity resource = model.getEntity();
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
		try {
			existingModel = TesterCoreUtils.getDataFromXML(testDataFilePath, entityType, resource,model.getTableColumnNames());
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

		
	}

}