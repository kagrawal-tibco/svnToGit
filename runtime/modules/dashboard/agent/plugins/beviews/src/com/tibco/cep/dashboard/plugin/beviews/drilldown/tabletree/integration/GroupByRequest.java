package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HREFLink;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

/**
 * @author rajesh
 * 
 */
public class GroupByRequest {
	
	public static final String MODEL_GROUP_FIELD = "_mgrpfld_";

	public static final String GROUP_FIELD = "_grpfld_";

	private List<String> nestedTableParams = new ArrayList<String>();
	
	private List<String> combinedList = new ArrayList<String>();
	
	private Map<String, String> modelParams = new HashMap<String, String>();
	
	private String newGroupBy = null;
	
	private boolean grouped = false;

	public GroupByRequest() {
	}

	public GroupByRequest(BizSessionRequest request) {
		grouped = TableTreeConstants.CMD_GROUP_BY.equals(request.getParameter(TableTreeConstants.KEY_CMD));
		processParams(request);
		fillModelGroupByFieldMap(request);
	}

	private void processParams(BizSessionRequest request) {
		Map<String, String> mapGroupFields = new TreeMap<String, String>();
		Iterator<String> requestParams = request.getParameterNames();
		while (requestParams.hasNext()) {
			String param = requestParams.next().toString();
			if (param.startsWith(GROUP_FIELD) && param.length() != GROUP_FIELD.length()) {
				String sIndex = param.substring(GROUP_FIELD.length() + 1);
				if (sIndex.length() == 0) {
					continue;
				}
				int index = Integer.parseInt(sIndex);
				mapGroupFields.put(String.valueOf(index), request.getParameter(param));
			}
		}
		nestedTableParams = new ArrayList<String>(mapGroupFields.values());
		combinedList = new ArrayList<String>(mapGroupFields.values());

		newGroupBy = request.getParameter(GROUP_FIELD);
		if (newGroupBy != null) {
			combinedList.add(newGroupBy);
		}
	}

	private void fillModelGroupByFieldMap(BizSessionRequest request) {
		Iterator<String> requestParams = request.getParameterNames();
		while (requestParams.hasNext()) {
			String param = requestParams.next().toString();
			if (param.startsWith(MODEL_GROUP_FIELD)) {

				String fieldName = param.substring(MODEL_GROUP_FIELD.length());
				modelParams.put(fieldName, request.getParameter(param));
			}
		}
		return;
	}

	public boolean updateGroupFieldParam(HREFLink link) {
		try {
			Iterator<String> iterator = nestedTableParams.iterator();
			String grpFieldName = iterator.next();
			link.addParameter(GROUP_FIELD, grpFieldName);
			int grpIndex = 0;
			while (iterator.hasNext()) {
				grpFieldName = iterator.next();
				link.addParameter(GROUP_FIELD + TableTree.PATH_SEPARATOR + grpIndex, grpFieldName);
				grpIndex++;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void updateModelGroupFieldParam(HREFLink link, String modelGroupFieldValue) {
		Iterator<Map.Entry<String, String>> iterator = modelParams.entrySet().iterator();
		int grpIndex = 0;
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String modelGrpFieldName = entry.getKey().toString();
			String modelGrpFieldValue = entry.getValue().toString();
			link.addParameter(MODEL_GROUP_FIELD + modelGrpFieldName, modelGrpFieldValue);
			grpIndex++;
		}
		if (getGroupByField() != null && modelGroupFieldValue != null) {
			link.addParameter(MODEL_GROUP_FIELD + getGroupByField(), modelGroupFieldValue);
		}
	}

	public String getGroupByField() {
		return newGroupBy;
	}

	public Map<String, String> getModelGroupByFieldMap() {
		return modelParams;
	}

	public List<String> getGroupMenuFilterList() {
		return new ArrayList<String>(modelParams.keySet());
	}

	public List<String> getCombinedGroupByList() {
		return combinedList;
	}

	public List<String> getGroupByListForNestedTableHeaders() {
		return nestedTableParams;
	}

	public boolean isGrouped() {
		return grouped || modelParams.size() > 0;
	}

}