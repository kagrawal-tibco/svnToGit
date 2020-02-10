package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.OrderBySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePath;
import com.tibco.cep.dashboard.security.SecurityToken;

public class DrillDownRequest {

	public static enum PAGINATION_MODE {
		append, replace
	};

	private SecurityToken token;

	private Map<String,QuerySpec> querySpecsMap;

	private DrillDownTreePath path;

	private String groupByField;

	private List<OrderBySpec> orderByList;

	private int startIndex;

	private int existingTypeTableCount;

	private int existingPageCount;

	private boolean initialSortRequest;

	private int pageSize;

	private int maxTableCount;

	private int maxPageCount;

	private PAGINATION_MODE paginationMode;

	public DrillDownRequest(SecurityToken token, List<QuerySpec> querySpecs, DrillDownTreePath path, int pageSize, int maxTableCount, int maxPageCount, PAGINATION_MODE paginationMode) {
		this.token = token;
		this.querySpecsMap = new HashMap<String, QuerySpec>();
		for (QuerySpec querySpec : querySpecs) {
			querySpecsMap.put(querySpec.getSchema().getTypeID(), querySpec);
		}
		this.path = path;
		this.startIndex = -1;
		orderByList = new LinkedList<OrderBySpec>();
		this.pageSize = pageSize;
		this.maxTableCount = maxTableCount;
		this.maxPageCount = maxPageCount;
		this.paginationMode = paginationMode;
	}


	public SecurityToken getToken() {
		return token;
	}

	public Collection<String> getQuerySpecKeys(){
		return querySpecsMap.keySet();
	}

	public Collection<QuerySpec> getQuerySpecs(){
		return querySpecsMap.values();
	}

	public QuerySpec getQuerySpec(String typeid) {
		return querySpecsMap.get(typeid);
	}

	public final DrillDownTreePath getPath() {
		return path;
	}

	public final String getGroupByField() {
		return groupByField;
	}

	public final void setGroupByField(String groupByField) {
		this.groupByField = groupByField;
	}

	public final int getStartIndex() {
		return startIndex;
	}

	public final void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void addOrderBy(String fieldName, boolean ascending) {
		orderByList.add(new OrderBySpec(fieldName, ascending));
	}

	public void removeOrderBy(String fieldName) {
		ListIterator<OrderBySpec> listIterator = orderByList.listIterator();
		while (listIterator.hasNext()) {
			OrderBySpec orderBySpec = listIterator.next();
			if (orderBySpec.getOrderByField().equals(fieldName) == true) {
				listIterator.remove();
				break;
			}
		}
	}

	public List<OrderBySpec> getOrderByList() {
		return orderByList;
	}

	public void setInitialSortRequest(boolean initialSortRequest) {
		this.initialSortRequest = initialSortRequest;
	}

	public boolean isInitialSortRequest() {
		return initialSortRequest;
	}

	public int getExistingTypeTableCount() {
		return existingTypeTableCount;
	}

	public void setExistingTypeTableCount(int existingTypeTableCount) {
		this.existingTypeTableCount = existingTypeTableCount;
	}

	public int getExistingPageCount() {
		return existingPageCount;
	}

	public void setExistingPageCount(int existingPageCount) {
		this.existingPageCount = existingPageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMaxPageCount() {
		return maxPageCount;
	}

	public int getMaxTableCount() {
		return maxTableCount;
	}

	public PAGINATION_MODE getPaginationMode() {
		return paginationMode;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("DrillDownRequest[");
//		sb.append("token=");
//		sb.append(token);
//		sb.append(",sessionid=");
//		sb.append(session.getId());
		sb.append("path=");
		sb.append(path);
		sb.append(",orderbyfields=");
		sb.append(orderByList.toString());
		sb.append(",groupbyfield=");
		sb.append(groupByField);
		sb.append(",startindex=");
		sb.append(startIndex);
		sb.append("]");
		return sb.toString();
	}



}