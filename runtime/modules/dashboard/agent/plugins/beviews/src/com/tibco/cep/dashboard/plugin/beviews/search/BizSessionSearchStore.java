package com.tibco.cep.dashboard.plugin.beviews.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.mal.managers.MALQueryManagerComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchViewComponentManager;

//TODO adding session time out feature
public final class BizSessionSearchStore {

	private static Map<BizSession,BizSessionSearchStore> sessionStore = new HashMap<BizSession, BizSessionSearchStore>();

	public static final synchronized BizSessionSearchStore getInstance(BizSession session) {
		if (session == null){
			throw new IllegalArgumentException("session cannot be null");
		}
		BizSessionSearchStore store = sessionStore.get(session);
		if (store == null){
			store = new BizSessionSearchStore(session);
			sessionStore.put(session, store);
		}
		return store;
	}

	private BizSession session;

	private BizSessionSearchStore(BizSession session) {
		this.session = session;
	}

	public void setQuerySpecs(List<QuerySpec> querySpecs) {
		session.setAttribute(MALSearchViewComponentManager.DEFINITION_TYPE, querySpecs);
	}

	@SuppressWarnings("unchecked")
	public List<QuerySpec> getQuerySpecs(){
		return (List<QuerySpec>) session.getAttribute(MALSearchViewComponentManager.DEFINITION_TYPE);
	}

	public void createQueryManagerModel() throws IncompatibleQueryException{
		List<QuerySpec> querySpecs = getQuerySpecs();
		if (querySpecs == null || querySpecs.isEmpty() == true) {
			return;
		}
		if (querySpecs.size() > 1) {
			throw new IllegalStateException("cannot create query manager model for more than one query specification");
		}
		QuerySpec querySpec = querySpecs.get(0);
		try {
			setQueryManagerModel(new QuerySpecModel((QuerySpec) querySpec.clone()));
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("could not clone query spec",e);
		}
	}

	private void setQueryManagerModel(QuerySpecModel queryManagerModel){
		session.setAttribute(MALQueryManagerComponentManager.DEFINITION_TYPE, queryManagerModel);
	}

	public QuerySpecModel getQueryManagerModel(){
		return (QuerySpecModel) session.getAttribute(MALQueryManagerComponentManager.DEFINITION_TYPE);
	}

//	public int getQueryDepth() {
//		return (Integer)getValue(BEViewsProperties.QUERY_MANAGER_EXPORT_DEPTH);
//	}
//
//	public void setQueryDepth(int queryDepth) {
//		session.setAttribute(BEViewsProperties.QUERY_MANAGER_EXPORT_DEPTH.getName(), queryDepth);
//	}
//
//	public int getQueryTypeCount() {
//		return (Integer)getValue(BEViewsProperties.QUERY_MANAGER_EXPORT_TYPE_COUNT);
//	}
//
//	public void setQueryTypeCount(int queryTypeCount) {
//		session.setAttribute(BEViewsProperties.QUERY_MANAGER_EXPORT_TYPE_COUNT.getName(), queryTypeCount);
//	}
//
//	public int getQueryAllCount() {
//		return (Integer)getValue(BEViewsProperties.QUERY_MANAGER_EXPORT_ALL_COUNT);
//	}
//
//	public void setQueryAllCount(int queryAllCount) {
//		session.setAttribute(BEViewsProperties.QUERY_MANAGER_EXPORT_ALL_COUNT.getName(), queryAllCount);
//	}
//
//	public boolean isIncludeSystemFields(){
//		return (Boolean)getValue(BEViewsProperties.QUERY_MANAGER_EXPORT_INCLUDE_SYSTEM_FIELDS);
//	}
//
//	public void setIncludeSystemFields(boolean includeSystemFields){
//		session.setAttribute(BEViewsProperties.QUERY_MANAGER_EXPORT_INCLUDE_SYSTEM_FIELDS.getName(), includeSystemFields);
//	}
//
//	private Object getValue(PropertyKey propertyKey){
//		Object value = session.getAttribute(propertyKey.getName());
//		if (value == null){
//			value = propertyKey.getDefaultValue();
//		}
//		return value;
//	}
}