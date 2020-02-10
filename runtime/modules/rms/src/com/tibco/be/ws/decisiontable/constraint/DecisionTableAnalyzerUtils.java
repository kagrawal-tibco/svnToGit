package com.tibco.be.ws.decisiontable.constraint;

import static com.tibco.be.ws.decisiontable.constraint.DecisionTableAnalyzerConstants.DONT_CARE;
import static com.tibco.be.ws.decisiontable.constraint.Operator.AND_OP;
import static com.tibco.be.ws.decisiontable.constraint.Operator.OR_OP;
import static com.tibco.cep.decision.table.language.DTLanguageUtil.COND_OPERATORS.EQUALS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;


/**
 * Utility class 
 * @author vdhumal
 */
public class DecisionTableAnalyzerUtils {

	public enum COND_OPERATORS {
		OR("||"), AND("&&"), EQUALS("=="), GT(">"), LT("<"), GTE(">="), LTE("<="), NE("!="), NE2("<>"), ASSIGNMNT("=");

		private final String opString;

		private COND_OPERATORS(String value) {
			this.opString = value;
		}

		public String opString() {
			return opString;
		}

		public static String ASTERISK = "*";

		private static final COND_OPERATORS[] VALUES_ARRAY =
				new COND_OPERATORS[] {
			OR,
			AND,
			EQUALS,
			GT,
			LT,
			GTE,
			LTE,
			NE,
			NE2,
			ASSIGNMNT
		};

		public static COND_OPERATORS getByOpString(String opString) {
			for (int i = 0; i < VALUES_ARRAY.length; ++i) {
				COND_OPERATORS result = VALUES_ARRAY[i];
				if (result.opString.equals(opString)) {
					return result;
				}
			}
			return null;
		}
	}
	
	public static String FORWARD_SLASH="/";
	public static String MARKER="##";
	public static final String CONCEPT_FILE_EXT = ".concepttestdata";
	public static final String SPLIT = ";";
	
	/**
	 * Canonicalize the input expression to normalize the spaces
	 * @param expression
	 * @return
	 */
	public static String canonicalizeExpression(String expression) {
		if (expression.trim().length() == 0) {
			return "";
		}
		
		String[] rangeElements = new String[] {expression};
		//Check for ==
		String equalsString = EQUALS.opString();
		int equalsOpsIndex = 
			expression.indexOf(equalsString);
		if (equalsOpsIndex != -1) {
			rangeElements = expression.split(equalsString);
			
			StringBuilder stringBuilder = new StringBuilder();
			for (String element : rangeElements) {
				stringBuilder.append(element.trim());
			}
			return stringBuilder.toString();
		}
		int andIndex = expression.indexOf(AND_OP);
		if (andIndex != -1) {
			rangeElements = expression.split(AND_OP);
		}
		int orIndex = expression.indexOf(OR_OP);
		if (orIndex != -1) {
			String orOp = "\\|\\|";
			rangeElements = expression.split(orOp);
		}
		StringBuilder canonicalized = new StringBuilder();
		for (String rangeEle : rangeElements) {
			rangeEle = rangeEle.trim();
			char opChar = rangeEle.charAt(0);
			
			if ('>' == opChar || '<' == opChar) {
				canonicalized.append(opChar);
				//Courtesy existing code
				int index = 1;
				//Check if the next operator is = or operand
				char next = rangeEle.charAt(1);
				if ('=' == next) {
					//Concat the 2 operators
					canonicalized.append(next);
					index = 2;
				}
				String lhsOperand = rangeEle.substring(index);
				lhsOperand = lhsOperand.trim();
				//Append this with one space
				canonicalized.append(" ");
				canonicalized.append(lhsOperand);
				
			} else {
				canonicalized.append(rangeEle);
			}
			//Check if and is added
			int containsAndIndex = canonicalized.indexOf(AND_OP);
			if (andIndex != -1 && containsAndIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(AND_OP);
				canonicalized.append(" ");
			}
			//Check if || is added
			int containsORIndex = canonicalized.indexOf(OR_OP);
			if (orIndex != -1 && containsORIndex == -1) {
				canonicalized.append(" ");
				canonicalized.append(OR_OP);
				canonicalized.append(" ");
			}
		}
		return canonicalized.toString();
	}
	
	/**
	 * @param columnName
	 * @param decisionTable
	 * @return
	 */
	public static ColumnFilter getColumnFilter(String columnName, DecisionTable decisionTable) {
		List<Filter> allFilters = decisionTable.getAllFilters(columnName);
		ColumnFilter columnFilter = null;		
		if (allFilters.size() == 0)
			return null;
		if (allFilters.size() == 1) {
			Filter filter = allFilters.get(0);			
			if (filter instanceof EqualsFilter) {
				EqualsFilter equalsFilter = (EqualsFilter) filter;
				columnFilter = getColumnEqualsFilter(columnName, equalsFilter);				
			} else if (filter instanceof RangeFilter) {
				columnFilter = new ColumnFilter(columnName);
				RangeFilter rf = (RangeFilter) filter;
				Object[] minmax = rf.getMinMax(true);
				columnFilter.setRange(minmax[0], minmax[1]);
			}
		} else if (allFilters.size() == 2) {
			boolean hasRangeFilter = false;
			EqualsFilter combinationFilter = null;
			for (Filter temp : allFilters) {
				if (temp instanceof RangeFilter) {
					hasRangeFilter = true;
				}
				if (temp instanceof EqualsFilter) {
					combinationFilter = (EqualsFilter)temp;
				}
			}
			//Should be one ComboFilter and one RangeFilter, 
			//Signifying a combination of ranges and equality checks
			if (hasRangeFilter) {
				//Do this only if there is a range filter.
				Object[] minMax = getMinMax(allFilters);
				if (minMax == null) {
					// could not find range
					return null;
				}
				Filter filter1 = allFilters.get(0);
				Filter filter2 = allFilters.get(1);
				RangeFilter rangeFilter = null;

				if (filter1 instanceof RangeFilter) {
					rangeFilter = (RangeFilter) filter1;
				} else if (filter2 instanceof RangeFilter) {
					rangeFilter = (RangeFilter) filter2;
				}

				Object[] range = rangeFilter.getMinMax(true);
				
				range[0] = getMinFromMultiplefilters(range[0],minMax[0]);
				range[1] = getMaxFromMultiplefilters(range[1],minMax[1]);
				columnFilter = new ColumnFilter(columnName);
				columnFilter.setRange(range[0], range[1]);
			} else {
				//Resort to equals case
				columnFilter = getColumnEqualsFilter(columnName, combinationFilter);
			}
		}
		
		return columnFilter;
	}
	
	private static Object getMaxFromMultiplefilters(Object rangeMax, Object eqMax) {
		if(rangeMax instanceof Double){
			eqMax = Double.parseDouble(eqMax.toString());
			if((double)eqMax > (double)rangeMax){
				return eqMax;
			}else{
				return rangeMax;
			}
		}else if(rangeMax instanceof Long){
			eqMax = Long.parseLong(eqMax.toString());
			if((long)eqMax > (long)rangeMax){
				return eqMax;
			}else{
				return rangeMax;
			}
		}else if(rangeMax instanceof Integer){
			eqMax = Integer.parseInt(eqMax.toString());
			if((int)eqMax > (int)rangeMax){
				return eqMax;
			}else{
				return rangeMax;
			}
		}
		return rangeMax;
	}

	private static Object getMinFromMultiplefilters(Object rangeMin, Object eqMin) {
		
		if(rangeMin instanceof Double){
			eqMin = Double.parseDouble(eqMin.toString());
			if((double)eqMin < (double)rangeMin){
				return eqMin;
			}else{
				return rangeMin;
			}
		}else if(rangeMin instanceof Long){
			eqMin = Long.parseLong(eqMin.toString());
			if((long)eqMin < (long)rangeMin){
				return eqMin;
			}else{
				return rangeMin;
			}
		}else if(rangeMin instanceof Integer){
			eqMin = Integer.parseInt(eqMin.toString());
			if((int)eqMin < (int)rangeMin){
				return eqMin;
			}else{
				return rangeMin;
			}
		}
		return rangeMin;
	}

	private static ColumnFilter getColumnEqualsFilter(String columnName, EqualsFilter equalsFilter) {
		ColumnFilter columnFilter = new ColumnFilter(columnName); 
		TreeSet<Object> items = new TreeSet<Object>();
		Set<Object> keySet = equalsFilter.getKeySet();
		for (Object key : keySet) {
			HashSet<Cell> cells = equalsFilter.get(key);
			if (null == cells) continue;
			for (Cell cell : cells) {
				if (cell.isEnabled()) {
					items.add(key);
				}
			}
		}
		columnFilter.addItems(items);
		
		return columnFilter;
	}
	public static Object[] getMinMax(List<Filter> allFilters) {
		Filter filter1 = allFilters.get(0);
		Filter filter2 = allFilters.get(1);
		if (filter1 instanceof EqualsFilter) {
			// need to find the min/max of the equality to ensure the range
			// covers those values
			EqualsFilter eq = (EqualsFilter) filter1;
			return getMinMax(eq);
		} else if (filter2 instanceof EqualsFilter) {
			// need to find the min/max of the equality to ensure the range
			// covers those values
			EqualsFilter eq = (EqualsFilter) filter2;
			return getMinMax(eq);
		}
		return null;
	}

	private static Object[] getMinMax(EqualsFilter equalsFilter) {
		Object min = Long.MAX_VALUE;
		Object max = Long.MIN_VALUE;

		HashSet<Object> enabledKeys = new LinkedHashSet<Object>();
		Set<Object> keySet = equalsFilter.getKeySet();
		for (Object object : keySet) {
			HashSet<Cell> cells = equalsFilter.get(object);
			for (Cell cell : cells) {
				if (cell.isEnabled()) {

					// TO DO: Get the Column type and put a check
					enabledKeys.add(object);
					continue;
				}
			}
		}

		Object[] array = enabledKeys.toArray();
		for (Object object : array) {
			if (object instanceof String) {
				String value = (String) object;
				// check for ANDed cells
				if (value.contains(COND_OPERATORS.OR.opString())) {
					StringTokenizer tokens = new StringTokenizer(value,
							COND_OPERATORS.OR.opString());
					while (tokens.hasMoreTokens()) {
						String token = tokens.nextToken().trim();
						if (!DONT_CARE.equals(token)) {
							try {
								if (value.contains(".")) {
									Double val = Double.valueOf(token);
									if(min instanceof Long){
										min = (long) Math.min((long) min,
												Math.floor(val));
									}else{
										min = (long) Math.min((double) min,
												Math.floor(val));
									}

									if(max instanceof Long){
										max = (long) Math.max((long) max,
												Math.floor(val));
									}else{
										max = (long) Math.max((double) max,
												Math.floor(val));
									}

								} else {
									if (token.contains("L")) {
										token = token.replaceAll("L", "").trim();
									}
									if (token.contains("l")) {
										token = token.replaceAll("l,", "").trim();
									}
									long val = Long.valueOf(token);
									min = Math.min((long) min, val);
									max = Math.max((long) max, val);
								}
							} catch (NumberFormatException nfe) {
								try {
									DateFormat format = new SimpleDateFormat(
											"yyyy-MM-dd'T'HH:mm:ss");
									if (max instanceof Long) {
										max = format
												.parse("1601-01-01T00:00:00");

									}
									if (min instanceof Long) {
										min = format
												.parse("9999-12-31T00:00:00");
									}
									Date val = format.parse(value);

									long maxTime = ((Date) max).getTime();
									long minTime = ((Date) min).getTime();
									long currentTime = val.getTime();
									min = new Date(Math.min(minTime,
											currentTime));
									max = new Date(Math.max(maxTime,
											currentTime));

								} catch (ParseException parseException) {
									continue;
								}

							}
						}
					}
				} else {
					if (!DONT_CARE.equals(value)) {
						try {
							if (value.contains(".")) {
								Double val = Double.valueOf(value);
								if(min instanceof Long){
									min = (long) Math.min((long) min,
											Math.floor(val));
								}else{
									min = (long) Math.min((double) min,
											Math.floor(val));
								}
								if(max instanceof Long){
									max = (long) Math.max((long) max,
											Math.floor(val));
								}else{
									max = (long) Math.max((double) max,
											Math.floor(val));
								}

							} else {
								if (value.contains("L")) {
									value = value.replaceAll("L", "").trim();
								}
								if (value.contains("l")) {
									value = value.replaceAll("l,", "").trim();
								}
								long val = Long.valueOf(value);
								min = Math.min((long) min, val);
								max = Math.max((long) max, val);
							}

						} catch (NumberFormatException nfe) {
							try {
								DateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd'T'HH:mm:ss");
								if (max instanceof Long) {
									max = format.parse("1601-01-01T00:00:00");

								}
								if (min instanceof Long) {
									min = format.parse("9999-12-31T00:00:00");
								}
								Date val = format.parse(value);

								long maxTime = ((Date) max).getTime();
								long minTime = ((Date) min).getTime();
								long currentTime = val.getTime();
								min = new Date(Math.min(minTime, currentTime));
								max = new Date(Math.max(maxTime, currentTime));

							} catch (ParseException parseException) {
								continue;
							}

						}
					}
				}
			} else if (object instanceof Date) {
			}
		}
		return new Object[] { min, max };
	}
	public static PropertyDefinition getConceptPropertyDefinition(TestDataModel testDataModel, String testDataColumnName) {
		PropertyDefinition property = null;
		Entity entity = testDataModel.getEntity();
		EList<PropertyDefinition> properties = null;
		if (entity instanceof Concept) {
			Concept concept = (Concept) entity;
			properties = concept.getAllPropertyDefinitions();
			Iterator<PropertyDefinition> itrProp = properties.iterator();
			while (itrProp.hasNext()) {
				PropertyDefinition prop = itrProp.next();
				if (prop.getName().equals(testDataColumnName)) {					
					property = prop;
					break;
				}
			}
		}
		
		return property;
	}
	
	public static boolean isContainedOrReferenceConcept(PropertyDefinition property, String testDataColumnName) {
		boolean isContainedOrReferenceConcept = false;
		if (property != null && property.getName().equals(testDataColumnName) 
					&& (property.getType().equals(PROPERTY_TYPES.CONCEPT_REFERENCE) || property.getType().equals(PROPERTY_TYPES.CONCEPT))) {
			isContainedOrReferenceConcept = true;
		}
		return isContainedOrReferenceConcept;
	}
	
	public static List<List<String>> getSelectedTestData(TestDataModel testDataModel) {
		List<List<String>> testData = testDataModel.getTestData();
		List<List<String>> selectedTestData = new ArrayList<List<String>>();
		List<Boolean> rowSelection = testDataModel.getSelectRowData();
		for (int row = 0; row < testData.size(); row++) {
			if (rowSelection.get(row))
				selectedTestData.add(testData.get(row));				
		}
		
		return selectedTestData;
	}
	
	/**
	 * @param projectName
	 * @param entityPath
	 * @param testDataFile
	 * @return
	 * @throws Exception
	 */
	public static TestDataModel getTestDataModel(String scsRootURL, String projectName, String entityPath, String testDataFile) throws Exception {
		
		Entity entity = CommonIndexUtils.getEntity(projectName, entityPath);
		EList<PropertyDefinition> list = null;						
		if(entity instanceof Scorecard){
			list = ((Scorecard) entity).getAllProperties();
		}
		else if(entity instanceof Event){
			list = ((Event) entity).getAllUserProperties();
		}
		else if(entity instanceof Concept){
			list = ((Concept) entity).getAllProperties();									
		}

		ArrayList<String> tableColumnNames = new ArrayList<String>();

		String entityType = null;
		if (entity instanceof Scorecard) {
			entityType = "Scorecard";
		} else if (entity instanceof Event) {
			entityType = "Event";
			tableColumnNames.add("Payload");
		} else if (entity instanceof Concept) {
			entityType = "Concept";
		}

		tableColumnNames.add("ExtId");
		Iterator<PropertyDefinition> itr2 = list.iterator();
		while(itr2.hasNext()){
			PropertyDefinition pd = itr2.next();
			tableColumnNames.add(pd.getName());
		}

		String testDataFileLocation = scsRootURL + File.separator + projectName + testDataFile;
		TestDataModel testDataModel = DecisionTableAnalyzerUtils.getDataFromXML(testDataFileLocation, entityType, entity, tableColumnNames);
		
		return testDataModel;
	}	
	
	private static TestDataModel getDataFromXML(String filename,
			String entityType, Entity entity, ArrayList<String> tableColumnNames)
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
					List<String> nodeNames=new ArrayList<String>();
					while (propertyNodeIterator.hasNext()) {
						XiNode propertyNode = propertyNodeIterator.next();
						nodeNames.add(propertyNode.getName().toString());
						if(tableColumnNames.contains(propertyNode.getName().toString())){
							if (!propertyNode.getName().equals(ExpandedName.makeName("payload"))) {
								list.add(propertyNode.getStringValue());
							}
							if (!propertyNodeIterator.hasNext()) {
								for(String colName:tableColumnNames){
									if(!colName.equalsIgnoreCase("Use")&&!colName.equalsIgnoreCase("ExtId")&&!colName.equalsIgnoreCase("Payload")){
										if(!nodeNames.contains(colName)){
											//XiNode node=XiSupport.getXiFactory().createElement(ExpandedName.makeName(colName));
											list.add("");
										}
									}
								}
								//model.getTestData().add(list);
							}
							
						}
					}
				} else {
					List<XiNode> propertyNodes = new ArrayList<XiNode>();
					while (propertyNodeIterator.hasNext()) {
						XiNode propertyNode = propertyNodeIterator.next();
						if(tableColumnNames.contains(propertyNode.getName().toString())){		//handling delete property case.
							propertyNodes.add(propertyNode);
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
				if(select){
					model.getTestData().add(list);
				}
			}
			fis.close();
		}
		return model;
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
				v.add(fValue+DecisionTableAnalyzerUtils.MARKER + resourcePath);
			}
			else{
				v.add(fValue);
			}
		} else {
			v.add(value);
		}
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
						+ DecisionTableAnalyzerUtils.SPLIT
						+ mulNodes.get(l).getAttributeStringValue(
								ExpandedName.makeName("rowNum"));
			}

		} else {
			fValue = mulNodes.get(0).getStringValue().toString();
			for (int l = 1; l < mulNodes.size(); l++) {
				fValue = fValue + DecisionTableAnalyzerUtils.SPLIT
						+ mulNodes.get(l).getStringValue();
			}
		}
		v.add(fValue);
	}
	
	public static String getEntityInfo(String testDataFile){		
		File file = new File(testDataFile);
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
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return null;
	}
	
    /**
     * @param rootURL SCS root URL
     * @param projectName
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void loadProjectIndex(String rootURL, String projectName, String archivePath) throws MalformedURLException, IOException {
    	File archiveFile = null;
    	if (archivePath != null) {
    		archiveFile = new File(archivePath);
    	}	
		if (archiveFile != null && archiveFile.exists() && archiveFile.isFile()) {
			loadProjectIndex(projectName, archiveFile);
		} else {
			IndexBuilder builder = new IndexBuilder(new File(rootURL + File.separator + projectName));
			DesignerProject index = builder.loadProject();
			StudioProjectCache.getInstance().putIndex(projectName, index);
		}   	
    }
    
    /**
     * @param projectName
     * @param archivePath Path of the EAR
     * @throws MalformedURLException
     * @throws IOException
     */
	private static void loadProjectIndex(String projectName, File archiveFile) throws MalformedURLException, IOException {

    	DesignerProject index = StudioProjectCache.getInstance().getIndex(projectName);
    	if (index == null) {
	    	java.net.URI earURI = archiveFile.toURI();
	    	ResourceSet resourceSet = new ResourceSetImpl();
	        final Resource resource = resourceSet.createResource(
	                URI.createURI("archive:jar:" + earURI.toString() + "!/Shared%20Archive.sar!/" + ".idx"));
	
	        final File earFile = new File (earURI);
	        final int length = (int) earFile.length();
	        final byte[] fileAsBytes = new byte[length];
	        
	        final InputStream is = earURI.toURL().openStream();
	        try {
	            for (int numRead = 0, offset = 0; (numRead >= 0) && (length > offset); offset += numRead) {
	                numRead = is.read(fileAsBytes, offset, length - offset);
	            }
	        } finally {
	            is.close();
	        }
	
	        final ByteArrayInputStream bais = new ByteArrayInputStream(fileAsBytes);
	        try {
	            final ZipInputStream earZis = new ZipInputStream(bais);
	            try {
	                for (ZipEntry earEntry = earZis.getNextEntry(); null != earEntry; earEntry = earZis.getNextEntry()) {
	                    if ("Shared Archive.sar".equals(earEntry.getName())) {
	                        final ZipInputStream sarZis = new ZipInputStream(earZis);
	                        try {
	                            for (ZipEntry sarEntry = sarZis.getNextEntry(); null != sarEntry; sarEntry = sarZis.getNextEntry()) {
	                                if (".idx".equals(sarEntry.getName())) {
	                                    resource.load(sarZis, null);
	                                    break;
	                                }
	                            }
	                        } finally {
	                            sarZis.close();
	                        }
	                        break;
	                    }
	                }
	            } finally {
	                earZis.close();
	            }
	        } finally {
	            bais.close();
	        }
		
	        DesignerProject designerProject = null;
	        if (resource.getContents().size() > 0) {
	        	designerProject = (DesignerProject) resource.getContents().get(0);
	        	StudioProjectCache.getInstance().putIndex(projectName, designerProject);
	        }     	
    	}    
    } 

}
