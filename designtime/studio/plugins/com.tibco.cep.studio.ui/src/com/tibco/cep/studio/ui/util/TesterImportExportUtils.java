/**
 * 
 */
package com.tibco.cep.studio.ui.util;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

/**
 * @author mgujrath
 *
 */
public class TesterImportExportUtils {
	
	public static com.tibco.cep.studio.core.testdata.exportHandler.TestData getDataFromXML(String filename,
			String entityType, Entity entity, List<String> tableColumnNames)
			throws Exception {
		
		List<ArrayList<String>> testData=new ArrayList<ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		com.tibco.cep.studio.core.testdata.exportHandler.TestData model = new com.tibco.cep.studio.core.testdata.exportHandler.TestData(entity, tableColumnNames,testData);
		File file = new File(filename);
		if (file.exists() && file.length() > 0) {
			FileInputStream fis = new FileInputStream(file);
			XiNode rootNode = XiParserFactory.newInstance().parse(
					new InputSource(fis));
			XiNode mainNode = rootNode.getFirstChild();
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
				if (entityType.equals("Concept")
						|| entityType.equals("Scorecard")) {
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
						XiNode node = payloadNode.getFirstChild();
						if (node != null) {
							StringWriter stringWriter = new StringWriter();
							DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(
									stringWriter, "UTF-8");
							node.serialize(handler);
							entityString = stringWriter.toString();
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
					while (propertyNodeIterator.hasNext()) {
						XiNode propertyNode = propertyNodeIterator.next();
						if (!propertyNode.getName().equals(
								ExpandedName.makeName("payload"))) {
							list.add(propertyNode.getStringValue());

							// model.getTestData().add(propertyNode.getStringValue());
						}
						if (!propertyNodeIterator.hasNext()) {

							model.getTestData().add(list);
							// model.getTestData().add("#");

						}
					}
				} else {
					List<XiNode> propertyNodes = new ArrayList<XiNode>();
					while (propertyNodeIterator.hasNext()) {
						XiNode propertyNode = propertyNodeIterator.next();
						propertyNodes.add(propertyNode);
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
					// model.getTestData().add("#");
					model.getTestData().add(list);
				}

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
						+ ";"
						+ mulNodes.get(l).getAttributeStringValue(
								ExpandedName.makeName("rowNum"));
			}

		} else {
			fValue = mulNodes.get(0).getStringValue().toString();
			for (int l = 1; l < mulNodes.size(); l++) {
				fValue = fValue + ";"
						+ mulNodes.get(l).getStringValue();
			}
		}
		v.add(fValue);
	}

	private static void setColumnValue(XiNode node, ArrayList<String> v,
			String value) {
		XiNode typeNode = node.getAttribute(ExpandedName.makeName("type"));
		if (typeNode != null
				&& (typeNode.getStringValue().equals(
						PROPERTY_TYPES.CONCEPT_REFERENCE.getName()) || typeNode
						.getStringValue().equals(
								PROPERTY_TYPES.CONCEPT.getName()))) {
			String fValue = node.getAttributeStringValue(ExpandedName
					.makeName("rowNum"));
			v.add(fValue);
		} else {
			v.add(value);
		}
	}

	
	public static void exportDataToXMLDataFile(String filename,
			com.tibco.cep.studio.core.testdata.exportHandler.TestData model, boolean fromTableAnalyser, boolean fromCli) {
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
		XiNode testdataRoot = factory.createElement(ExpandedName
				.makeName("testdata"));
		root.appendChild(testdataRoot);
		FileOutputStream fos = null;
		BufferedWriter bufWriter = null;
		try {
			
			fos = new FileOutputStream(new File(filename));
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
				boolean select = (Boolean) model.getSelectRowData().get(i);
				XiNode selectAttribElement = factory.createAttribute(
						ExpandedName.makeName("isSelected"), model
								.getSelectRowData().get(i).toString());// Adding
				// isSelected
				// attribute
				entityElement.appendChild(selectAttribElement);

				if (model.getEntity() instanceof Concept) {
					Concept concept = (Concept) model.getEntity();
					list = CommonIndexUtils.getAllConceptProperties(concept);
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
					for (int j = 1, k = 2; k < rowlength; j++, k++) {
						rows[i] = (String) (model.getVectors().elementAt(i)
								.elementAt(k));
						String columnName = model.getTableColumnNames().get(j);
						if (!columnName.equalsIgnoreCase("Use")
								&& !columnName.equalsIgnoreCase("ExtId")
								&& !columnName.equalsIgnoreCase("Payload")) {
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
					Object extIdOb = model.getTestData().get(i).get(1);
					String extId = extIdOb == null ? "" : extIdOb.toString();
					if (!extId.trim().equals("")) {
						XiNode extIdAttribElement = factory.createAttribute(
								ExpandedName.makeName("extId"), extId);
						entityElement.appendChild(extIdAttribElement);
					}
					Object payloadObj = model.getTestData().get(i).get(0);
					String val = payloadObj == null ? "" : payloadObj
							.toString();
					if (event.getPayloadString() != null
							&& !val.trim().equals("")) {
						XiNode payloadElement = factory
								.createElement(ExpandedName.makeName("payload"));
						// InputStream inputstream = new
						// ByteArrayInputStream(val.getBytes("UTF-8"));
						XiNode payloadNode = XiParserFactory.newInstance()
								.parse(new InputSource(new StringReader(val)));
						payloadElement.appendChild(payloadNode.getFirstChild());
						entityElement.appendChild(payloadElement);
					}
					for (int j = 1, k = 3; k < rowlength; j++, k++) {
						rows[i] = (String) (model.getVectors().elementAt(i)
								.elementAt(k));
						String columnName = null;
						columnName = model.getTableColumnNames().get(j);
						if (!columnName.equalsIgnoreCase("Use")
								&& !columnName.equalsIgnoreCase("ExtId")
								&& !columnName.equalsIgnoreCase("Payload")) {
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
//			StudioTesterCorePlugin.log(e);
		} catch (Exception e) {
//			StudioTesterCorePlugin.log(e);
		} finally {
			try {
				fos.close();
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static PropertyDefinition getPropertyDefinition(String columnName,
			List<PropertyDefinition> list) {
		for (PropertyDefinition propertyDefinition : list) {
			if (propertyDefinition.getName().equals(columnName)) {
				return propertyDefinition;
			}
		}
		return null;
	}

	
	public static XiNode saveXMLEntityTestData(XiFactory factory,
			Entity entity, PropertyDefinition propertyDefinition,
			XiNode entityElement, String value, String property)
			throws Exception {
		if (entity != null) {
			if (entity instanceof com.tibco.cep.designtime.core.model.element.Concept) {
				if (propertyDefinition.isArray()) {
					String[] values = value.split(";", -1);
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
	
	private static void setConceptRefContainedType(XiFactory factory,
			XiNode element, PropertyDefinition propertyDefinition, String value) {
		String conceptTypePath = propertyDefinition.getConceptTypePath();

		XiNode resPathAttribElement = factory.createAttribute(
				ExpandedName.makeName("resourcePath"), conceptTypePath);
		element.appendChild(resPathAttribElement);

		XiNode rowIdAttribElement = factory.createAttribute(
				ExpandedName.makeName("rowNum"), value);
		element.appendChild(rowIdAttribElement);

		XiNode typeAttribElement = factory.createAttribute(ExpandedName
				.makeName("type"), propertyDefinition.getType().getName());
		element.appendChild(typeAttribElement);
	}

	
	public static void exportDataToXMLDataFileAppend(String filename,
			Entity entity, com.tibco.cep.studio.core.testdata.exportHandler.TestData model2,
			List<String> tableColumnNames, Boolean fromTableAnalyser) {

		int loopCounter = model2.getAppendedVectors().size();
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
		com.tibco.cep.studio.core.testdata.exportHandler.TestData existingModel;
		com.tibco.cep.studio.core.testdata.exportHandler.TestData model = null;
		try {
			existingModel = getDataFromXML(filename, entityType, entity,
					new ArrayList<String>());
			model = combineModel(existingModel, model2);

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
					for (int j = 1, k = 0; j < rowlength; j++, k++) {
						rows[i] = (String) (model.getAppendedVectors().elementAt(i).elementAt(j));
						String columnName = tableColumnNames.get(k);
						if (!columnName.equalsIgnoreCase("Use")
								&& !columnName.equalsIgnoreCase("ExtId")
								&& !columnName.equalsIgnoreCase("Payload")) {
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
						// InputStream inputstream = new
						// ByteArrayInputStream(val.getBytes("UTF-8"));
						XiNode payloadNode = XiParserFactory.newInstance()
								.parse(new InputSource(new StringReader(val)));
						payloadElement.appendChild(payloadNode.getFirstChild());
						entityElement.appendChild(payloadElement);
					}
					for (int j = 4, k = 3; j < rowlength; j++, k++) {
						rows[i] = (String) (model2.getAppendedVectors()
								.elementAt(i).elementAt(j));
						String columnName = null;
						columnName = tableColumnNames.get(k);
						if (!columnName.equalsIgnoreCase("Use")
								&& !columnName.equalsIgnoreCase("ExtId")
								&& !columnName.equalsIgnoreCase("Payload")) {
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
//			StudioTesterCorePlugin.log(e);
		} catch (Exception e) {
//			StudioTesterCorePlugin.log(e);
		} finally {
			try {
				fos.close();
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	
	public static com.tibco.cep.studio.core.testdata.exportHandler.TestData combineModel(com.tibco.cep.studio.core.testdata.exportHandler.TestData existingModel,
			com.tibco.cep.studio.core.testdata.exportHandler.TestData model2) {

		com.tibco.cep.studio.core.testdata.exportHandler.TestData model = new com.tibco.cep.studio.core.testdata.exportHandler.TestData(existingModel.getEntity(),
				existingModel.getTableColumnNames(),
				existingModel.getTestData());
		model.setEntityInfo(existingModel.getEntityInfo());
		// existingModel.getExtId().addAll(newModel.getExtId());
		// model.setExtId(existingModel.getExtId());
		// existingModel.getPayload().addAll(newModel.getPayload());
		// model.setPayload(existingModel.getPayload());
		existingModel.getSelectRowData().addAll(model2.getSelectRowData());
		model.setSelectRowData(existingModel.getSelectRowData());
		existingModel.getTestData().addAll(model2.getTestData());
		model.setTestData(existingModel.getTestData());
		existingModel.getAppendedVectors()
				.addAll(model2.getAppendedVectors());
		model.setAppendedVectors(existingModel.getAppendedVectors());
		existingModel.getInput().addAll(model2.getInput());
		model.setInput(existingModel.getInput());
		existingModel.getVectors().addAll(model2.getVectors());
		model.setVectors(existingModel.getVectors());

		return model;
	}




}
