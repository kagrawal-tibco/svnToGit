package com.tibco.cep.dashboard.plugin.beviews.biz.drilldown;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.be.functions.object.ObjectHelper;
import com.tibco.cep.dashboard.common.data.FieldValueArray;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.data.ConceptTupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleConvertor;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.model.NextInLine;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.DrilldownProvider;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.TypeSpecificDrilldownProvider;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.related.ConceptValueObject;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrillDownConfiguration;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.runtime.model.element.Concept;

public class ShowRelatedConceptsAction extends BaseSessionCheckerAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		String childTypeId = request.getParameter("typeid");
		if (StringUtil.isEmptyOrBlank(childTypeId) == true){
			request.setAttribute("errormessage", "No type id specified");
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		}
		Entity childEntity;
		try {
			childEntity = EntityCache.getInstance().getEntity(childTypeId);
		} catch (IllegalArgumentException e) {
			request.setAttribute("errormessage", e.getMessage());
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		}
		String name = EntityVisualizerProvider.getInstance().getEntityVisualizer(childEntity).getName();
		String childId = request.getParameter("instanceid");
		if (StringUtil.isEmptyOrBlank(childTypeId) == true){
			request.setAttribute("errormessage", "No id specified");
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		}
		DrilldownProvider drilldownProvider;
		try {
			drilldownProvider = DrilldownProvider.getInstance(request.getSession());
		} catch (QueryException e) {
			request.setAttribute("errormessage", "could not access drilldown provider to fetch data");
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		}
		Tuple childTuple;
		try {
			childTuple = drilldownProvider.getTuple(childTypeId, childId);
		} catch (QueryException e) {
			request.setAttribute("errormessage", "could find data for "+name+" with id as "+childId);
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		}
		try {
			//parents list 
			Map<String, List<ConceptValueObject>> topLevelConcepts = new LinkedHashMap<String, List<ConceptValueObject>>();
			//type name with largest number of columns 
			String widestConcept = "";
			int largestFieldCount = -1;
			//find the field which points to parent in the childTuple
			ConceptTupleSchema schema = (ConceptTupleSchema) childTuple.getSchema();
			TupleSchemaField parentIdField = schema.getParentIdField();
			String[] parentIds = null;
			if (parentIdField.isArray() == true) {
				FieldValueArray fieldValueArray = childTuple.getFieldArrayValueByName(parentIdField.getFieldName());
				Comparable<?>[] values = fieldValueArray.getValues();
				parentIds = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					parentIds[i] = String.valueOf(values[i]);
				}
			}
			else {
				parentIds = new String[]{String.valueOf(childTuple.getFieldValueByName(parentIdField.getFieldName()))};
			}		
			for (String parentId : parentIds) {
				if (StringUtil.isEmptyOrBlank(parentId) == false) {
					Concept concept = ObjectHelper.getById(Long.parseLong(parentId));
					if (concept != null) {
						Tuple tuple = TupleConvertor.getInstance().convertToTuple(concept);
						ConceptValueObject parentValueObject = createConceptValueObject(tuple);
						if (parentValueObject.getFields().size() > largestFieldCount) {
							largestFieldCount = parentValueObject.getFields().size();
							widestConcept = parentValueObject.getTypeName();
						}
						List<ConceptValueObject> valueObjects = topLevelConcepts.get(parentValueObject.getTypeName());
						if (valueObjects == null) {
							valueObjects = new LinkedList<ConceptValueObject>();
							topLevelConcepts.put(parentValueObject.getTypeName(), valueObjects);
						}
						valueObjects.add(parentValueObject);
						//traverse thru the parent value object and add all children 
						TypeSpecificDrilldownProvider typeSpecificDrilldownProvider = drilldownProvider.getTypeSpecificDrilldownProvider(tuple.getTypeId());
						List<NextInLine> nextInLines = typeSpecificDrilldownProvider.getNextInLines(logger, tuple, new HashMap<String, String>(), drilldownProvider);
						for (NextInLine nextInLine : nextInLines) {
							QuerySpec drillDownQuery = typeSpecificDrilldownProvider.getDrillDownQuery(logger, tuple, nextInLine.getTypeID());
							String resultSetId = drilldownProvider.getInstanceData(drillDownQuery, new HashMap<String, String>(), null, false);
							List<Tuple> children = drilldownProvider.getNextSet(resultSetId, DrillDownConfiguration.getTablePageSize());
							for (Tuple child : children) {
								parentValueObject.addChild(createConceptValueObject(child));
							}
						}
					}
				}
			}
			request.setAttribute("widesttypename", widestConcept);
			request.setAttribute("datamodel", topLevelConcepts);
			request.setAttribute("errormessage", "Success");
			return VelocityViewHelper.getInstance().prepareRespone(request, "success");
		} catch (NumberFormatException e) {
			request.setAttribute("errormessage", "Invalid parent id found in "+name+"[id="+childId+"]");
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		} catch (FatalException e) {
			request.setAttribute("errormessage", "Failed to fetch related concepts for "+name+"[id="+childId+"]");
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		} catch (QueryException e) {
			request.setAttribute("errormessage", "Failed to fetch related concepts for "+name+"[id="+childId+"]");
			return VelocityViewHelper.getInstance().prepareRespone(request, "error");
		}
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		request.setAttribute("errormessage", "Your session has timed out");
		return VelocityViewHelper.getInstance().prepareRespone(request, "error");
	}
	

	private ConceptValueObject createConceptValueObject(Tuple tuple) {
		EntityVisualizer entityVisualizer = EntityVisualizerProvider.getInstance().getEntityVisualizerById(tuple.getTypeId());
		ConceptValueObject valueObject = new ConceptValueObject(tuple.getTypeId(),entityVisualizer.getName(),tuple.getId());
		Map<String, String> displayableFields = entityVisualizer.getDisplayableFields();
		for (String fieldName : displayableFields.keySet()) {
			valueObject.addField(fieldName, tuple.getFieldValueByName(fieldName).toString());
		}
		return valueObject;
	}
}
