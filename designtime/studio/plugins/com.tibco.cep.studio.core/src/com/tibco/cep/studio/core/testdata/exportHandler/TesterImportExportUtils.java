/**
 * 
 */
package com.tibco.cep.studio.core.testdata.exportHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

/**
 * @author mgujrath
 *
 */
public class TesterImportExportUtils {
	
	public static TestData getDataFromXML(String filename,
			String entityType, Entity entity, ArrayList<String> tableColumnNames)
			throws Exception {

		ArrayList<String> list = new ArrayList<String>();
		TestData model = new TestData(entity, tableColumnNames,new ArrayList<ArrayList<String>>());
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


}
