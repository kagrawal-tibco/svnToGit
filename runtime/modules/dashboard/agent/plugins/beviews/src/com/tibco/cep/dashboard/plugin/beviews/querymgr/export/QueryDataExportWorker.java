package com.tibco.cep.dashboard.plugin.beviews.querymgr.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.common.query.TupleBasedResultSetImpl;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.DrilldownQuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownDataConvertor;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownDataNodeMerger;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownDataProvider;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownRequest;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownRequest.PAGINATION_MODE;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownResponse;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePath;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePathParser;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataNode;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class QueryDataExportWorker {

	private Logger logger;

	private Properties properties;

	private ExceptionHandler exceptionHandler;

	private MessageGenerator messageGenerator;

	private DrillDownTreePathParser drillDownTreePathParser;

	private DrillDownDataProvider drillDownDataProvider;

	private DrillDownDataConvertor drillDownDataConvertor;

	private DrillDownDataNodeMerger drillDownDataNodeMerger;

	private SecurityToken token;

	private QuerySpec querySpec;

	private String queryTypeId;

	private int depth;

	private int perTypeCount;

	private int overallCount;

	private DrillDownDataTree drillDownDataTree;

	private int actualOverAllCount;

	private int pageSize;

	private CachedQueryExecutor cachedQueryExecutor;

	public QueryDataExportWorker(Logger logger, Properties properties, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super();
		this.properties = properties;
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		depth = -1;
		perTypeCount = -1;
		overallCount = -1;
	}

	public void setToken(SecurityToken token) {
		this.token = token;
	}

	public void setQuerySpec(QuerySpec querySpec) {
		this.querySpec = querySpec;
		this.queryTypeId = this.querySpec.getSchema().getTypeID();
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setPerTypeCount(int perTypeCount) {
		this.perTypeCount = perTypeCount;
	}

	public void setOverallCount(int overallCount) {
		this.overallCount = overallCount;
	}

	public void run() throws QueryException {
		if (querySpec == null) {
			throw new IllegalStateException("No initial query specification provided");
		}
		drillDownTreePathParser = new DrillDownTreePathParser(logger, exceptionHandler, messageGenerator, EntityCache.getInstance());
		drillDownDataProvider = new DrillDownDataProvider(logger, properties, exceptionHandler, messageGenerator);
		drillDownDataConvertor = new DrillDownDataConvertor(logger, properties, exceptionHandler, messageGenerator);
		drillDownDataNodeMerger = new DrillDownDataNodeMerger(logger, properties, exceptionHandler, messageGenerator);
		cachedQueryExecutor = new CachedQueryExecutor();
		QueryExecutor existingQueryExecutor = drillDownDataProvider.getQueryExecutor();
		try {
			drillDownDataProvider.setQueryExecutor(cachedQueryExecutor);
			pageSize = Math.min(perTypeCount, overallCount);
			drillDownDataTree = new DrillDownDataTree();
			DrillDownRequest request = new DrillDownRequest(token, Arrays.asList(querySpec), null, overallCount, Integer.MAX_VALUE, Integer.MAX_VALUE, PAGINATION_MODE.replace);
			logger.log(Level.INFO, "Exporting data for %s with per type count as %d and overall count as %d and depth as %d", querySpec.toString(), perTypeCount, overallCount, depth);
			populateTree(request, depth, true);
		} finally {
			drillDownDataProvider.setQueryExecutor(existingQueryExecutor);
			cachedQueryExecutor.close();
			cachedQueryExecutor = null;
		}
	}

	public DrillDownDataTree getDrillDownDataTree() {
		return drillDownDataTree;
	}

	private void populateTree(DrillDownRequest request, int currentDepth, boolean applySizingLimit) throws QueryException {
		//check if the overall count has been reached
		if (actualOverAllCount >= overallCount) {
			//we have reached the limit, exit
			return;
		}
		//check if the depth has been reached
		if (currentDepth == -1) {
			//we have reached the depth, exit
			return;
		}
		int actualPageSize = Math.min(pageSize, overallCount - actualOverAllCount);
		int existingPageSize = request.getPageSize();
		try {
			request.setPageSize(actualPageSize);
			//get max data associated with the request using perTypeCnt and overallCnt
			DrillDownResponse drillDownResponse = getData(request, applySizingLimit);
			//convert data for session storage
			Map<String, List<DrillDownDataNode>> dataNodes = drillDownDataConvertor.convertDataToNodes(request, drillDownResponse);
			//merge the data with existing data set in the session
			drillDownDataNodeMerger.mergeData(request, drillDownDataTree, dataNodes);
			String key = request.getPath() == null ? queryTypeId : request.getPath().getRawPath();
			List<DrillDownDataNode> childDataNodes = dataNodes.get(key);
			if (request.getPath() == null && childDataNodes.isEmpty() == false) {
				childDataNodes = childDataNodes.get(0).getChildren();
			}
			for (DrillDownDataNode childDataNode : childDataNodes) {
				//compute new path
				DrillDownTreePath path = computePath(request.getPath(), childDataNode);
				DrillDownRequest childRequest = new DrillDownRequest(request.getToken(), Arrays.asList(querySpec), path, request.getPageSize(),request.getMaxTableCount(), request.getMaxPageCount(), request.getPaginationMode());
				if (childDataNode.getKind().compareTo(DrillDownDataNode.KIND.TYPE) == -1) {
					//decrement depth for all non type nodes
					populateTree(childRequest, currentDepth - 1, false);
				}
				else {
					//maintain the depth for type nodes, people dont percieve type nodes as depth
					populateTree(childRequest, currentDepth, true);
				}
			}
		} finally {
			request.setPageSize(existingPageSize);
		}
	}

	private DrillDownTreePath computePath(DrillDownTreePath parent, DrillDownDataNode child) {
		StringBuilder sb = new StringBuilder(parent == null ? child.getEntityID() : parent.getRawPath());
		sb.append("/");
		sb.append(child.getIdentifier());
		return drillDownTreePathParser.parse(sb.toString());
	}

	private DrillDownResponse getData(DrillDownRequest drillDownRequest, boolean applySizingLimits) throws QueryException {
		String key = drillDownRequest.getPath() == null ? queryTypeId : drillDownRequest.getPath().getRawPath();
		if (perTypeCount == 0 || overallCount == 0) {
			DrillDownResponse drillDownResponse = new DrillDownResponse();
			drillDownResponse.setData(key, new ArrayList<Tuple>(0));
			drillDownResponse.setPageMaxCountReached(true);
			drillDownResponse.setTableMaxCountReached(key, true);
			drillDownResponse.setTotalCount(key, 0);
			return drillDownResponse;
		}
		try {
			DrillDownResponse drillDownResponse = drillDownDataProvider.executeQuery(drillDownRequest);
			List<Tuple> data = drillDownResponse.getData(key);
			cachedQueryExecutor.addTuplesToCache(data);
			int count = data.size();//drillDownResponse.getTotalCount(key);
			boolean fetchMore = applySizingLimits == true ? count < perTypeCount && count < overallCount : true;
			while (drillDownResponse.isDataPaginated(key) == true && fetchMore == true) {
				drillDownRequest.setStartIndex(count+1);
				drillDownResponse = drillDownDataProvider.executeQuery(drillDownRequest);
				data.addAll(drillDownResponse.getData(key));
				count = data.size();//drillDownResponse.getTotalCount(key);
				fetchMore = applySizingLimits == true ? count < perTypeCount && count < overallCount : false;
			}
			if (applySizingLimits == true) {
				int sizeLimit = Math.min(perTypeCount,overallCount);
				if (count > sizeLimit) {
					//we have more data than requested. this will happen since DrillDownDataProvider
					//does not provide API to set max per type count and over all count
					data = new LinkedList<Tuple>(data.subList(0, sizeLimit));
				}
				if (actualOverAllCount + data.size() > overallCount) {
					//we are going to go over the limit, so limit the data
					data = new LinkedList<Tuple>(data.subList(0, overallCount-actualOverAllCount));
				}
			}
			drillDownResponse.setData(key, data);
			drillDownResponse.setTotalCount(key, count);
			drillDownResponse.setTableMaxCountReached(key, true);
			if (applySizingLimits == true) {
				actualOverAllCount = actualOverAllCount + data.size();
			}
			logger.log(Level.INFO, "Fetched %d for %s using %s", count, key, querySpec.toString());
			return drillDownResponse;
		} finally {
			drillDownRequest.setStartIndex(-1);
		}
	}


	private class CachedQueryExecutor implements QueryExecutor {

		private QueryExecutor executor;

		private Map<String,Tuple> cachedConcepts;

		CachedQueryExecutor() throws QueryException {
			executor = ViewsQueryExecutorFactory.getInstance().createImplementation();
			cachedConcepts = new HashMap<String, Tuple>();
		}

		void addTuplesToCache(List<Tuple> tuples) {
			for (Tuple tuple : tuples) {
				if (tuple.getSchema().isDynamic() == false) {
					cachedConcepts.put(tuple.getId(), tuple);
				}
			}
		}

		@Override
		public int countQuery(Query query) throws QueryException {
			String id = getID(query);
			if (id != null) {
				if (cachedConcepts.containsKey(id) == true) {
					logger.log(Level.DEBUG, "Returning 1 as count for %s based on internal cache", query.toString());
					return 1;
				}
			}
			return executor.countQuery(query);
		}

		@Override
		public ResultSet executeQuery(Query query) throws QueryException {
			String id = getID(query);
			if (id != null) {
				Tuple tuple = cachedConcepts.get(id);
				if (tuple != null) {
					logger.log(Level.DEBUG, "Returning %s as result for %s based on internal cache", tuple.toString(), query.toString());
					return new TupleBasedResultSetImpl(Arrays.asList(tuple));
				}
			}
			return executor.executeQuery(query);
		}

		private String getID(Query query) {
			if (query instanceof ViewsQuery) {
				ViewsQuery viewsQuery = (ViewsQuery) query;
				QuerySpec querySpec = viewsQuery.getQuerySpec();
				if (querySpec != null && querySpec instanceof DrilldownQuerySpec == false && querySpec.getCondition() instanceof QueryPredicate) {
					QueryPredicate predicate = (QueryPredicate) querySpec.getCondition();
					if (predicate.evalLeftArgument().getValue().equals(querySpec.getSchema().getIDField().getFieldName()) == true) {
						return predicate.evalRightArgument().toString();
					}
				}
			}
			return null;
		}

		@Override
		public void close() {
			executor.close();
			cachedConcepts.clear();
		}
	}
}
