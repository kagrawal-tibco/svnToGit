package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.Tuple;

public class DrillDownResponse {

	public static final String EMPTY_RESPONSE_KEY = "#EMPTY#";

	private Map<String,ResponseData> dataMap;

	private boolean pageMaxCountReached;

	public DrillDownResponse(){
		dataMap = new HashMap<String, ResponseData>();
	}

	public void setData(String key, List<Tuple> data) {
		ResponseData responseData = new ResponseData();
		responseData.data = data;
		dataMap.put(key, responseData);
	}

	public Collection<String> getKeys() {
		return dataMap.keySet();
	}

	public List<Tuple> getData(String key) {
		if (dataMap.containsKey(key) == true) {
			return dataMap.get(key).data;
		}
		return null;
	}

	public void setTotalCount(String key, int totalCount) {
		if (dataMap.containsKey(key) == false) {
			throw new IllegalStateException(key+" is not registered");
		}
		dataMap.get(key).totalCount = totalCount;
	}

	public int getTotalCount(String key) {
		if (dataMap.containsKey(key) == true) {
			return dataMap.get(key).totalCount;
		}
		return 0;
	}

	public boolean isDataPaginated(String key){
		if (dataMap.containsKey(key) == true) {
			ResponseData responseData = dataMap.get(key);
			if (responseData.data.isEmpty() == false) {
				return responseData.data.size() < responseData.totalCount;
			}
		}
		return false;
	}

	public boolean isTableMaxCountReached(String key) {
		if (dataMap.containsKey(key) == true) {
			return dataMap.get(key).tableMaxCountReached;
		}
		return false;
	}

	public void setTableMaxCountReached(String key, boolean tableMaxCountReached) {
		if (dataMap.containsKey(key) == false) {
			throw new IllegalStateException(key+" is not registered");
		}
		dataMap.get(key).tableMaxCountReached = tableMaxCountReached;
	}

	public boolean isPageMaxCountReached() {
		return pageMaxCountReached;
	}

	public void setPageMaxCountReached(boolean pageMaxCountReached) {
		this.pageMaxCountReached = pageMaxCountReached;
	}

	private class ResponseData {

		List<Tuple> data;

		int totalCount;

		boolean tableMaxCountReached;

	}


}