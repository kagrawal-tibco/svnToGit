package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.ConfigurationProperties;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.common.query.GroupByTuple;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.OrderBySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALExternalURLUtils;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePathParser;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.FieldValueDrillDownTreePathElement;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataNode;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchPageManager;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.DrillDownModel;
import com.tibco.cep.dashboard.psvr.ogl.model.RowConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TextConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.TextModel;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ColumnAlignment;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.RowConfigType;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.variables.VariableContext;
import com.tibco.cep.dashboard.psvr.variables.VariableInterpreter;
import com.tibco.cep.dashboard.psvr.vizengine.LinkGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.LinkGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class DrillDownDataConvertor extends DrillDownHelper {

	private static MessageFormat NAME_FORMAT = new MessageFormat("{0} ({1})");

	private static MessageFormat GROUP_BY_NAME_FORMAT = new MessageFormat("{0}: {1} ({2})");

	private ActionConfigType showRelatedElementsAction;

	private String baseURI;

	public DrillDownDataConvertor(Logger logger, Properties properties, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, properties, exceptionHandler, messageGenerator);
		baseURI = String.valueOf(ConfigurationProperties.PULL_REQUEST_BASE_URL.getValue(properties));

		String[][] dynamicParams = new String[][] { new String[] { "link", ActionConfigGenerator.CURRENTDATAROW_LINK_DYN_PARAM } };
		showRelatedElementsAction = ActionConfigUtils.createActionConfig("Show Related Elements", CommandType.LAUNCHINTERNALLINK, false, null, dynamicParams, null, null);
	}

	public Map<String, List<DrillDownDataNode>> convertDataToNodes(DrillDownRequest request, DrillDownResponse response) {
		Map<String, List<DrillDownDataNode>> nodeMap = new HashMap<String, List<DrillDownDataNode>>();
		Collection<String> keys = response.getKeys();
		for (String key : keys) {
			List<Tuple> tuples = response.getData(key);
			List<DrillDownDataNode> nodes = new LinkedList<DrillDownDataNode>();
			if (tuples.size() > 0) {
				for (Tuple tuple : tuples) {
					nodes.add(convertToDataNode(tuple));
				}
				if (request.getPath() == null) {
					// PATCH the key is the type id
					String name = entityVisualizerProvider.getEntityVisualizerById(key).getName();
					DrillDownDataNode node = new DrillDownDataNode(key, NAME_FORMAT.format(new Object[] { name, response.getTotalCount(key) }));
					node.setKind(DrillDownDataNode.KIND.TYPE);
					node.setDynamicData(false);
					node.setEntityID(key);
					for (DrillDownDataNode childNode : nodes) {
						node.addChild(childNode);
					}
					nodes = Arrays.asList(node);
				}
			}
			nodeMap.put(key, nodes);
		}
		return nodeMap;
	}

	private DrillDownDataNode convertToDataNode(Tuple tuple) {
		String name = null;
		String identifier = tuple.getId();
		// by default the kind is INSTANCE
		DrillDownDataNode.KIND kind = DrillDownDataNode.KIND.INSTANCE;
		if (tuple instanceof NextInLineTuple) {
			// get entity name
			String entityName = entityVisualizerProvider.getEntityVisualizerById(tuple.getTypeId()).getName();
			// get entity count
			int count = (Integer) tuple.getFieldValueByName(NextInLineTuple.FIELD_NAME_COUNT).getValue();
			// format the name
			name = NAME_FORMAT.format(new Object[] { entityName, count });
			// use typeid as identifier
			identifier = tuple.getTypeId();
			// set kind to TYPE
			kind = DrillDownDataNode.KIND.TYPE;
		} else if (tuple instanceof GroupByTuple) {
			// get the field name
			String fieldName = (String) tuple.getFieldValueByName(GroupByTuple.FIELD_NAME_FIELDNAME).getValue();
			// get the field value
			FieldValue tupleFieldValue = tuple.getFieldValueByName(GroupByTuple.FIELD_NAME_FIELDVALUE);
			String fieldValue = tupleFieldValue.toString();
			// get the count
			long count = (Long) tuple.getFieldValueByName(GroupByTuple.FIELD_NAME_GROUP_BY_AGGR).getValue();
			// format the name
			name = GROUP_BY_NAME_FORMAT.format(new Object[] { fieldName, fieldValue, count });
			// use fieldname=fieldvalue as identifier
			if (tupleFieldValue.isNull() == true) {
				identifier = fieldName + "=" + DrillDownTreePathParser.NULL_VALUE_IDENTIFIER+ "=true";
			}
			else {
				identifier = fieldName + "=" + DrillDownTreePathParser.escapeValue(fieldValue);
			}
			// set kind to GROUP_BY
			kind = DrillDownDataNode.KIND.GROUP_BY;
		}
		DrillDownDataNode node = new DrillDownDataNode(identifier, name);
		node.setKind(kind);
		node.setDynamicData(tuple.getSchema().isDynamic());
		node.setEntityID(tuple.getTypeId());
		if (name == null) {
			// we are dealing with a regular tuple
			EntityVisualizer entityVisualizer = entityVisualizerProvider.getEntityVisualizerById(tuple.getTypeId());
			int count = tuple.getSchema().getFieldCount();
			for (int i = 0; i < count; i++) {
				TupleSchemaField field = tuple.getSchema().getFieldByPosition(i);
				if (field.isArtificalField() == false) {
					String fieldName = field.getFieldName();
					String fieldDisplayName = entityVisualizer.getDisplayableName(fieldName);
					//add the field if it is a system field or has a display name (EntityVisualizer tells us which fields to include via the getDisplayableName() API
					if (field.isSystemField() == true || fieldDisplayName != null) {
						//system fields are not included in the displayable field list, so we use thier name
						if (fieldDisplayName == null) {
							fieldDisplayName = fieldName;
						}
						if (field.isArray() == true) {
							node.addField(fieldName, fieldDisplayName, tuple.getFieldArrayValueByName(fieldName), getDisplayValue(tuple.getSchema().getFieldByName(fieldName), tuple.getFieldArrayValueByName(fieldName)), field.isSystemField());
						} else {
							node.addField(fieldName, fieldDisplayName, tuple.getFieldValueByName(fieldName), getDisplayValue(tuple.getSchema().getFieldByName(fieldName), tuple.getFieldValueByName(fieldName)), field.isSystemField());
						}
					}
				}
			}
		}
		return node;
	}

	public DrillDownModel convertDataToXML(DrillDownRequest request, DrillDownResponse response) {
		DrillDownModel drillDownModel = new DrillDownModel();
		Collection<String> keys = response.getKeys();
		if (keys.size() == 1 && keys.iterator().next().equals(DrillDownResponse.EMPTY_RESPONSE_KEY) == true) {
			//we have empty response
			return drillDownModel;
		}
		for (String key : keys) {
			List<Tuple> tuples = response.getData(key);
			TextModel textModel = new TextModel();
			textModel.setComponentType("TypeTable");
			if (request.getPath() == null) {
				String entityName = EntityVisualizerProvider.getInstance().getEntityVisualizerById(key).getName();
				textModel.setComponentID(key);
				textModel.setComponentName(NAME_FORMAT.format(new Object[] { entityName, response.getTotalCount(key) }));
			}
			if (tuples.isEmpty() == false) {
				boolean pageRequest = request.getStartIndex() > 0;
				//add the header if this is the initial sort request or the first page request
				boolean addHeader = pageRequest == false || request.isInitialSortRequest() == true;
				VisualizationData visualizationData = getVisualizationData(request, tuples, addHeader, request.getOrderByList());
				Map<String,ActionConfigType> actionConfigs = new LinkedHashMap<String, ActionConfigType>();
				TextConfig textConfig = null;
				int i = 0;
				for (Tuple tuple : tuples) {
					TupleSchema tupleSchema = tuple.getSchema();
					Entity entity = EntityCache.getInstance().getEntity(tupleSchema.getTypeID());
					if (pageRequest == false) {
						// no , we are not
						if (tupleSchema.isDynamic() == true || request.getPath() == null) {
							// add group by action config if tuple is dynamic or we have no path
							ActionConfigType groupByActions = createGroupByActions(entity, request);
							if (groupByActions != null) {
								//set the qualifier to the data row id
								groupByActions.setQualifier(visualizationData.getDataRow(i).getId());
								// register the action against the entity to prevent duplication & get real separation
								actionConfigs.put(entity.getGUID(), groupByActions);
							}
						}
					}
					if (textConfig == null) {
						// add row config
						String dynamicTupleHeaderVisualType = ((tuple instanceof NextInLineTuple) == true) ? "TypeRow" : "GroupByRow";
						textConfig = getVisualizationConfig(request, tuple, dynamicTupleHeaderVisualType, tupleSchema.isDynamic() == false);
						//set the max count
						textConfig.setMaxRows(response.getTotalCount(key));
						//set the page size
						textConfig.setPageSize(response.getData(key).size());
						//set the text config into the text model
						textModel.setTextConfig(textConfig);
					}
					i++;
				}
				//add the qualified action configs
				boolean addQualifier = actionConfigs.size() > 1;
				for (Map.Entry<String, ActionConfigType> qualifiedActionConfigEntry : actionConfigs.entrySet()) {
					ActionConfigType actionConfig = qualifiedActionConfigEntry.getValue();
					if (addQualifier == false) {
						//unset the qualifier if not needed
						actionConfig.setQualifier(null);
					}
					textModel.addActionConfig(actionConfig);
				}
				// add visualization data
				textModel.setVisualizationData(visualizationData);
			}
			drillDownModel.addTextModel(textModel);
		}
		return drillDownModel;
	}

	private VisualizationData getVisualizationData(DrillDownRequest request, List<Tuple> tuples, boolean addHeader, List<OrderBySpec> orderBySpecs) {
		Map<String, Boolean> explodedOrderBySpecs = new HashMap<String, Boolean>();
		for (OrderBySpec orderBySpec : orderBySpecs) {
			explodedOrderBySpecs.put(orderBySpec.getOrderByField(), orderBySpec.getAscending());
		}
		VisualizationData vizData = new VisualizationData();
		int i = 1;

		for (Tuple tuple : tuples) {
			Entity entity = EntityCache.getInstance().getEntity(tuple.getTypeId());
			EntityVisualizer entityVisualizer = entityVisualizerProvider.getEntityVisualizerById(tuple.getTypeId());
			if (tuple.getSchema().isDynamic() == true) {
				// we add header rows for dynamic tuples
				DataRow row = new DataRow();
				// set template type as header
				row.setTemplateType(RowConfigType.HEADER);
				// set template id
				row.setTemplateID("1");
				// add a single data column
				DataColumn dataColumn = new DataColumn();
				if (tuple instanceof NextInLineTuple) {
					// set row id
					row.setId(tuple.getTypeId());
					// set id
					dataColumn.setId("0");
					// set visual type
					row.setVisualType("TypeRow");
					// get entity name
					String entityName = entityVisualizer.getName();
					// get entity count
					int count = (Integer) tuple.getFieldValueByName(NextInLineTuple.FIELD_NAME_COUNT).getValue();
					// format the name
					dataColumn.setDisplayValue(NAME_FORMAT.format(new Object[] { entityName, count }));
				} else if (tuple instanceof GroupByTuple) {
					// set visual type
					row.setVisualType("GroupByRow");
					// get the field name
					String fieldName = (String) tuple.getFieldValueByName(GroupByTuple.FIELD_NAME_FIELDNAME).getValue();
					// get the field value
					FieldValue tupleFieldValue = tuple.getFieldValueByName(GroupByTuple.FIELD_NAME_FIELDVALUE);
					String fieldValue = tupleFieldValue.toString();
					// get the count
					long count = (Long) tuple.getFieldValueByName(GroupByTuple.FIELD_NAME_GROUP_BY_AGGR).getValue();
					// format the name
					dataColumn.setDisplayValue(GROUP_BY_NAME_FORMAT.format(new Object[] { fieldName, fieldValue, count }));
					// add type specific attribute for field name
					TypeSpecificAttribute fieldNameAttr = new TypeSpecificAttribute();
					fieldNameAttr.setName(GroupByTuple.FIELD_NAME_FIELDNAME);
					fieldNameAttr.setContent(fieldName);
					dataColumn.addTypeSpecificAttribute(fieldNameAttr);
					// add type specific attribute for field value
					TypeSpecificAttribute fieldNameValue = new TypeSpecificAttribute();
					fieldNameValue.setName(GroupByTuple.FIELD_NAME_FIELDVALUE);
					fieldNameValue.setContent(fieldValue);
					dataColumn.addTypeSpecificAttribute(fieldNameValue);
					// set row id
//					if (request.getPath() == null) {
//						row.setId(tuple.getTypeId()+"/"+fieldName + "=" + fieldValue);
//					}
//					else {
//						row.setId(fieldName + "=" + fieldValue);
//					}
					if (tupleFieldValue.isNull() == true) {
						row.setId(fieldName + "=" + encode(DrillDownTreePathParser.NULL_VALUE_IDENTIFIER+"=true"));
					}
					else {
						//we need to escape the field value to replace '/' with "//"
						fieldValue = fieldValue.replace("/", "//");
						row.setId(fieldName + "=" + encode(DrillDownTreePathParser.escapeValue(fieldValue)));
					}
					// set id
					dataColumn.setId("0");
				}
				// add datacolumn to data row TODO check with Mark , if we can reuse the datarow and add many datacolumns?
				row.addDataColumn(dataColumn);
				// add row to visualization data
				vizData.addDataRow(row);
			} else {
				// we are dealing with regular tuple
				if (i == 1 && addHeader == true) {
					// add header
					DataRow headerRow = new DataRow();
					// set id
					headerRow.setId("-1");
					// set template type as header
					headerRow.setTemplateType(RowConfigType.HEADER);
					// set template id
					headerRow.setTemplateID("1");
					// set visual type
					headerRow.setVisualType("TypeTableHeaderRow");
					// add all exposed fields as data columns
					Map<String, String> displayableFields = entityVisualizer.getDisplayableFields();
					int fieldIdx = 0;
					for (String fieldDisplayName : displayableFields.values()) {
						// add a data column for each field
						DataColumn dataColumn = new DataColumn();
						// set id
						dataColumn.setId(String.valueOf(fieldIdx));
						// set display value
						dataColumn.setDisplayValue(fieldDisplayName);
						// set sort value
						if (explodedOrderBySpecs.containsKey(fieldDisplayName) == true) {
							dataColumn.setSortValue(explodedOrderBySpecs.get(fieldDisplayName) == true ? "asc" : "desc");
						}
						// add data column to header row
						headerRow.addDataColumn(dataColumn);
						fieldIdx++;
					}
					i++;
					// add row to visualization data
					vizData.addDataRow(headerRow);
				}
				// add data
				DataRow dataRow = new DataRow();
				// set id
//				if (request.getPath() == null) {
//					dataRow.setId(tuple.getTypeId()+"/"+tuple.getId());
//				}
//				else {
//					dataRow.setId(tuple.getId());
//				}
				dataRow.setId(tuple.getId());
				// set template type as header
				dataRow.setTemplateType(RowConfigType.DATA);
				// set template id
				dataRow.setTemplateID("2");
				// set visual type
				dataRow.setVisualType("TypeDataRow");
				// add all exposed fields as data columns [displayableFields is a map of field name & field display name]
				Map<String, String> displayableFields = entityVisualizer.getDisplayableFields();
				int fieldIdx = 0;

				for (String fieldName : displayableFields.keySet()) {
					// add a data column for each field
					DataColumn dataColumn = new DataColumn();
					// set id
					dataColumn.setId(String.valueOf(fieldIdx));
					// set value
					FieldValue value = null;
					if (tuple.getSchema().getFieldByName(fieldName).isArray() == true) {
						value = tuple.getFieldArrayValueByName(fieldName);
					} else {
						value = tuple.getFieldValueByName(fieldName);
					}
					dataColumn.setValue(String.valueOf(value.getValue()));
					// set display value
					dataColumn.setDisplayValue(getDisplayValue(tuple.getSchema().getFieldByName(fieldName), value));
					// set link if needed
					String externalURL = MALExternalURLUtils.getURLLink(entity.getGUID(), fieldName);
					if (externalURL != null) {
						try {
							VariableContext variableContext = new VariableContext(logger, request.getToken(), properties, tuple);
							externalURL = VariableInterpreter.getInstance().interpret(externalURL, variableContext, true);
						} catch (ParseException e) {
							logger.log(Level.WARN, "could not parse " + externalURL, e);
							externalURL = null;
						}
					}
					dataColumn.setLink(externalURL);
					// add data column to header row
					dataRow.addDataColumn(dataColumn);
					fieldIdx++;
				}

				//add related page link if needed
				if ((entity instanceof Metric) == false) {
					//we are dealing with a concept, so we need to add a related link
					LinkGenerator relatedLink = LinkGeneratorFactory.getInstance().generateRelatedLink(baseURI, request.getToken(), entity.getGUID(), tuple.getId());
					if (relatedLink != null) {
						dataRow.setLink(relatedLink.toString());
					}
				}
				// add row to visualization data
				vizData.addDataRow(dataRow);
			}
			i++;
		}
		return vizData;
	}

	private TextConfig getVisualizationConfig(DrillDownRequest request, Tuple tuple, String headerVisualType, boolean addDataRowConfig) {
		TupleSchema tupleSchema = tuple.getSchema();
		TextConfig textConfig = new TextConfig();
		textConfig.setComponentID(tupleSchema.getTypeID());
		EntityVisualizer entityVisualizer = entityVisualizerProvider.getEntityVisualizerById(tupleSchema.getTypeID());
		// we add header row config for dynamic tuples
		RowConfig headerRowConfig = new RowConfig();
		// set id
		headerRowConfig.setId("1");
		// set template type as header
		headerRowConfig.setTemplateType(RowConfigType.HEADER);
		if (tupleSchema.isDynamic() == true) {
			// set visual type
			headerRowConfig.setVisualType(headerVisualType);
			// add a single column config
			ColumnConfig columnConfig = new ColumnConfig();
			// set id
			columnConfig.setId("0");
			// set alignment to left
			columnConfig.setAlign(ColumnAlignment.LEFT);
			// add the column config to row config
			headerRowConfig.addColumnConfig(columnConfig);
			// add header row config to text config
			textConfig.addRowConfig(headerRowConfig);
		} else {
			// we are dealing with regular tuple
			int fieldsCnt = entityVisualizer.getDisplayableFields().size();
			for (int i = 0; i < fieldsCnt; i++) {
				// add a single column config
				ColumnConfig columnConfig = new ColumnConfig();
				// set id
				columnConfig.setId(String.valueOf(i));
				// set alignment to left
				columnConfig.setAlign(ColumnAlignment.LEFT);
				headerRowConfig.addColumnConfig(columnConfig);
			}
			// add header row config to text config
			textConfig.addRowConfig(headerRowConfig);
			if (addDataRowConfig == true) {
				// we add data row config
				RowConfig dataRowConfig = new RowConfig();
				// set id
				dataRowConfig.setId("2");
				// set template type as header
				dataRowConfig.setTemplateType(RowConfigType.DATA);
				Set<Entry<String, String>> fieldsEntrySet = entityVisualizer.getDisplayableFields().entrySet();
				int i = 0;
				for (Entry<String, String> fieldEntry : fieldsEntrySet) {
					// add a single column config
					ColumnConfig columnConfig = new ColumnConfig();
					// set id
					columnConfig.setId(String.valueOf(i));
					// set alignment to left
					columnConfig.setAlign(getAlignment(tupleSchema.getFieldByName(fieldEntry.getKey()).getFieldDataType()));
					dataRowConfig.addColumnConfig(columnConfig);
					i++;
				}
				//add action config if needed
				Entity entity = EntityCache.getInstance().getEntity(tupleSchema.getTypeID());
				if ((entity instanceof Metric) == false) {
					SecurityToken token = request.getToken();
					try {
						if (TokenRoleProfile.getInstance(token).getViewsConfigHelper().getPagesByType(MALSearchPageManager.DEFINITION_TYPE).length != 0) {
							dataRowConfig.setActionConfig(ActionConfigUtils.createActionConfigSet("ROOT", false, Arrays.asList(showRelatedElementsAction)));
						}
					} catch (Exception e) {
						logger.log(Level.WARN, "Could not generate show related elements action for "+entity.getFullPath(),e);
					}
				}
				// add data row config to text config
				textConfig.addRowConfig(dataRowConfig);
			}
		}
		return textConfig;
	}

	private ActionConfigType createGroupByActions(Entity entity, DrillDownRequest request) {
		TupleSchema schema = TupleSchemaFactory.getInstance().getTupleSchema(entity);
		EntityVisualizer entityVisualizer = entityVisualizerProvider.getEntityVisualizer(entity);
		List<String> fieldDisplayNamesToSkip = new LinkedList<String>();
		if (StringUtil.isEmptyOrBlank(request.getGroupByField()) == false) {
			// incoming group by field is already a display name
			fieldDisplayNamesToSkip.add(request.getGroupByField());
		}
		List<FieldValueDrillDownTreePathElement> trailingFieldValuePathElements = new LinkedList<FieldValueDrillDownTreePathElement>();
		if (request.getPath() != null) {
			request.getPath().readTrailingFieldValuePathElements(trailingFieldValuePathElements);
		}
		for (FieldValueDrillDownTreePathElement fieldValueDrillDownTreePathElement : trailingFieldValuePathElements) {
			// add the display name of the field
			fieldDisplayNamesToSkip.add(entityVisualizer.getDisplayableName(fieldValueDrillDownTreePathElement.getFieldName()));
		}
		List<ActionConfigType> groupByFieldActions = new LinkedList<ActionConfigType>();
		Map<String, String> displayableFields = entityVisualizer.getDisplayableFields();
		for (Map.Entry<String, String> entry : displayableFields.entrySet()) {
			String name = entry.getKey();
			String displayName = entry.getValue();
			TupleSchemaField schemaField = schema.getFieldByName(name);
//			if (schemaField.isSystemField() == true) {
//				//skip system fields since group by queries do not work well with them
//				continue;
//			}
			if (schemaField.isArtificalField() == true) {
				//skip artificial fields since group by queries will not understand our own creations
				continue;
			}
			if (fieldDisplayNamesToSkip.contains(displayName) == true) {
				// skip the field if it is requested as group by field , also skip all trailing com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.FieldValueDrillDownTreePathElement
				continue;
			}
			groupByFieldActions.add(ActionConfigUtils.createActionConfig(displayName, CommandType.USERDEFINED, false, null, null, null, null));
		}
		if (groupByFieldActions.isEmpty() == false) {
			List<ActionConfigType> actions = new LinkedList<ActionConfigType>();
			// add group by actions
			actions.add(ActionConfigUtils.createActionConfigSet("GROUPBY", false, groupByFieldActions));			return ActionConfigUtils.createActionConfigSet("ROOT", false, actions);
		}
		return null;
	}


	private String getDisplayValue(TupleSchemaField field, FieldValue value) {
		if (value.isNull() == true) {
			String nullDisplayValue = (String) BEViewsProperties.NULL_VALUE_DISPLAY_VALUE.getValue(properties);
			if (nullDisplayValue == null) {
				return value.toString();
			}
			return nullDisplayValue;
		}
		DataType dataType = value.getDataType();
		if (BuiltInTypes.STRING.equals(dataType) == true) {
			return value.toString();
		}
		if (BuiltInTypes.BOOLEAN.equals(dataType) == true) {
			return value.toString();
		}
		if (BuiltInTypes.DATETIME.equals(dataType) == true) {
			return value.toString();
		}
		if (field.isSystemField() == true) {
			return value.toString();
		}
		if (BuiltInTypes.DOUBLE.equals(dataType) == true) {
			String pattern = (String) BEViewsProperties.DECIMAL_FORMAT.getValue(properties);
			if (StringUtil.isEmptyOrBlank(pattern) == true) {
				NumberFormat format = NumberFormat.getInstance();
				format.setMaximumFractionDigits(2);
				format.setMinimumFractionDigits(2);
				return format.format((Double) value.getValue());
			}
			MessageFormat format = new MessageFormat("{0,number," + pattern + "}");
			return format.format(new Object[] { (Double) value.getValue() });
		}
		if (BuiltInTypes.FLOAT.equals(dataType) == true) {
			String pattern = (String) BEViewsProperties.DECIMAL_FORMAT.getValue(properties);
			if (StringUtil.isEmptyOrBlank(pattern) == true) {
				NumberFormat format = NumberFormat.getInstance();
				format.setMaximumFractionDigits(2);
				format.setMinimumFractionDigits(2);
				return format.format((Float) value.getValue());
			}
			MessageFormat format = new MessageFormat("{0,number," + pattern + "}");
			return format.format(new Object[] { (Float) value.getValue() });
		}
		if (BuiltInTypes.LONG.equals(dataType) == true) {
			String pattern = (String) BEViewsProperties.INTEGER_FORMAT.getValue(properties);
			if (StringUtil.isEmptyOrBlank(pattern) == true) {
				NumberFormat format = NumberFormat.getInstance();
				return format.format((Long) value.getValue());
			}
			MessageFormat format = new MessageFormat("{0,number," + pattern + "}");
			return format.format(new Object[] { (Long) value.getValue() });
		}
		if (BuiltInTypes.INTEGER.equals(dataType) == true) {
			String pattern = (String) BEViewsProperties.INTEGER_FORMAT.getValue(properties);
			if (StringUtil.isEmptyOrBlank(pattern) == true) {
				NumberFormat format = NumberFormat.getInstance();
				return format.format((Integer) value.getValue());
			}
			MessageFormat format = new MessageFormat("{0,number," + pattern + "}");
			return format.format(new Object[] { (Integer) value.getValue() });
		}
		return value.toString();
	}

	private ColumnAlignment getAlignment(DataType dataType) {
		if (BuiltInTypes.STRING.equals(dataType) == true) {
			return ColumnAlignment.LEFT;
		}
		if (BuiltInTypes.BOOLEAN.equals(dataType) == true) {
			return ColumnAlignment.LEFT;
		}
		if (BuiltInTypes.DATETIME.equals(dataType) == true) {
			return ColumnAlignment.LEFT;
		}
		return ColumnAlignment.RIGHT;
	}

	private String encode(String str) {
		try {
			return URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

}
