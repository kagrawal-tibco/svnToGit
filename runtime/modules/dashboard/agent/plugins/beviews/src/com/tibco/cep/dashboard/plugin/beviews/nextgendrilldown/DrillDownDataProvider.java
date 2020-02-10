package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.OrderBySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizer;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePath;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.DrillDownTreePathElement;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.FieldValueDrillDownTreePathElement;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.InstanceIDDrillDownTreePathElement;
import com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path.TypeIDDrillDownTreePathElement;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class DrillDownDataProvider extends DrillDownHelper {

	private MetricDrilldownProvider metricDrilldownProvider;

	private ConceptDrilldownProvider conceptDrilldownProvider;

	private QueryExecutor queryExecutor;

	private boolean externalQueryExecutor;

	public DrillDownDataProvider(Logger logger, Properties properties, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, properties, exceptionHandler, messageGenerator);
		// gives us metric drill down information
		metricDrilldownProvider = new MetricDrilldownProvider();
		// gives us concept drill down information
		conceptDrilldownProvider = new ConceptDrilldownProvider();
		externalQueryExecutor = false;
	}

	public void setQueryExecutor(QueryExecutor queryExecutor) {
		this.queryExecutor = queryExecutor;
		this.externalQueryExecutor = this.queryExecutor != null;
	}

	public QueryExecutor getQueryExecutor() {
		if (externalQueryExecutor == true) {
			return queryExecutor;
		}
		return null;
	}

	public DrillDownResponse executeQuery(DrillDownRequest request) throws QueryException {
		// gives us the data
		if (externalQueryExecutor == false) {
			queryExecutor = ViewsQueryExecutorFactory.getInstance().createImplementation();
		}
		try {
			DrillDownResponse response = new DrillDownResponse();
			// get the path
			DrillDownTreePath path = request.getPath();
			if (path != null && path.getLeaf() instanceof InstanceIDDrillDownTreePathElement) {
				// user has expanded a data row
				List<Tuple> data = getChildTypes(request, queryExecutor);
				// set the actual data
				response.setData(path.getRawPath(), data);
				// set total count
				response.setTotalCount(path.getRawPath(), data.size());
			} else {
				QuerySpec[] queries = generateQuery(request, queryExecutor);
				if (queries != null) {
					//we have more then one query
					for (QuerySpec query : queries) {
						//we will use the type id of the query as the key
						String key = request.getPath() == null ? query.getSchema().getTypeID() : request.getPath().getRawPath();
						List<Tuple> data = null;
						int count = 0;
						if (StringUtil.isEmptyOrBlank(request.getGroupByField()) == false) {
							//we are looking @ group by request
							data = getData(request, queryExecutor, query);
						} else {
							// get the count
							count = getCount(query, queryExecutor);
							if (count != 0) {
								//add a order by clause if not present, else AS does not guarantee the same order for repeated executions of the same query
								if (query.getOrderByFields().isEmpty() == true) {
									query.addOrderByField(query.getSchema().getIDField().getFieldName(), true);
								}
								data = getData(request, queryExecutor, query);
							} else {
								data = Collections.emptyList();
							}
						}
						// set the actual data
						response.setData(key, data);
						// set total count
						response.setTotalCount(key, count);
					}
				}
				else {
					// set the actual data
					response.setData(DrillDownResponse.EMPTY_RESPONSE_KEY, new ArrayList<Tuple>(0));
					// set total count
					response.setTotalCount(DrillDownResponse.EMPTY_RESPONSE_KEY, 0);
				}
			}
			return response;
		} finally {
			if (externalQueryExecutor == false && queryExecutor != null) {
				queryExecutor.close();
			}
		}
	}

	private List<Tuple> getChildTypes(DrillDownRequest request, QueryExecutor queryExecutor) throws QueryException {
		InstanceIDDrillDownTreePathElement leaf = (InstanceIDDrillDownTreePathElement) request.getPath().getLeaf();
		Entity entity = leaf.getEntity();
		TypeSpecificDrilldownProvider typeSpecificDrilldownProvider = getTypeSpecificDrilldownProvider(entity);
		return typeSpecificDrilldownProvider.getNextInLines(logger, entity, leaf.getInstanceID(), queryExecutor);
	}

	private TypeSpecificDrilldownProvider getTypeSpecificDrilldownProvider(Entity entity) {
		TypeSpecificDrilldownProvider typeSpecificDrilldownProvider = entity instanceof Metric ? metricDrilldownProvider : conceptDrilldownProvider;
		return typeSpecificDrilldownProvider;
	}

	private int getCount(QuerySpec query, QueryExecutor queryExecutor) throws QueryException {
		int queryHashCode = -1;
		try {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "getCount::Firing [" + query + "] with query hashcode as " + queryHashCode);
			}
			long stime = System.currentTimeMillis();
			int count = queryExecutor.countQuery(new ViewsQuery(query));
			long etime = System.currentTimeMillis();
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "getCount::Got count as [" + count + "] for query[hashcode=" + queryHashCode + "] in " + (etime - stime) + " msecs...");
			}
			return count;
		} catch (Exception e) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "getCount::Execution of query[hashcode=" + queryHashCode + "] failed due to " + e);
			}
			if (e instanceof QueryException) {
				throw (QueryException) e;
			}
			throw new QueryException(e);
		}
	}

	private List<Tuple> getData(DrillDownRequest request, QueryExecutor queryExecutor, QuerySpec query) throws QueryException {
		int skip = request.getStartIndex() == -1 ? 0 : request.getStartIndex() - 1;
		int pageSize = request.getPageSize();
		int tableCount = request.getExistingTypeTableCount();
		int maxTableCount = request.getMaxTableCount();
		int pageCount = request.getExistingPageCount();
		int maxPageCount = request.getMaxPageCount();
		DrillDownRequest.PAGINATION_MODE paginationMode = request.getPaginationMode();
		ResultSet resultSet = null;
		int queryHashCode = -1;
		try {
			boolean isPaginationAppendMode = paginationMode.compareTo(DrillDownRequest.PAGINATION_MODE.append) == 0;
			queryHashCode = query.hashCode();
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "getData::Firing [" + query + "] with queryspec hashcode as " + queryHashCode);
			}
			long stime = System.currentTimeMillis();
			resultSet = queryExecutor.executeQuery(new ViewsQuery(query));
			long etime = System.currentTimeMillis();
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "getData::Execution of QuerySpec[hashcode=" + queryHashCode + "] took " + (etime - stime) + " msecs with result set as [" + resultSet + "]");
			}
			List<Tuple> data = new LinkedList<Tuple>();
			// INFO we are using index to skip over tuples in the result set, not very efficient
			while (skip > 0 && resultSet.next() == true) {
				skip--;
			}
			int count = 0;
			boolean queryHasGroupBy = !query.getGroupByFields().isEmpty();
			while (resultSet.next() == true) {
				data.add(resultSet.getTuple());
				count++;
				if (queryHasGroupBy == false) {
					// check if we have exceeded the table count
					if (isPaginationAppendMode == true
							&& count + tableCount > maxTableCount) {
						// we have reached the limit of the table count, break
						break;
					}
					// check if we have exceeded the page count
					if (count + pageCount > maxPageCount) {
						// we have reached the limit of the page count, break
						break;
					}
					if (data.size() == pageSize) {
						// we have reached the limit of the table page count, break
						break;
					}
				}
			}
			return data;
		} catch (Exception e) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "getData::Execution of QuerySpec[hashcode=" + queryHashCode + "] failed due to " + e);
			}
			if (e instanceof QueryException) {
				throw (QueryException) e;
			}
			throw new QueryException(e);
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	QuerySpec[] generateQuery(DrillDownRequest request, QueryExecutor queryExecutor) throws QueryException {
		QuerySpec[] querySpecs = createQuerySpecs(request, queryExecutor);
		if (querySpecs != null) {
			String groupByFieldDisplayName = request.getGroupByField();
			//do we have a group by or a order by field in the request
			if (StringUtil.isEmptyOrBlank(groupByFieldDisplayName) == false || request.getOrderByList().isEmpty() == false) {
				//yes, we do - then we should have only one query in the querySpecs
				if (querySpecs.length == 1) {
					EntityVisualizer entityVisualizer = entityVisualizerProvider.getEntityVisualizerById(querySpecs[0].getSchema().getTypeID());
					// apply group by
					if (StringUtil.isEmptyOrBlank(groupByFieldDisplayName) == false) {
						querySpecs[0].addGroupByField(entityVisualizer.getName(groupByFieldDisplayName));
						// querySpec.addProjectionField(new QueryProjectionField(querySpec.getSchema(), groupByField));
						// querySpec.addProjectionField(new QueryProjectionField(querySpec.getSchema(), BEViewsQueryExecutorImpl.GROUPBY_CNT_FLD_ID, QueryProjectionField.COUNT));
					}
					// apply sort
					for (OrderBySpec orderBySpec : request.getOrderByList()) {
						querySpecs[0].addOrderByField(entityVisualizer.getName(orderBySpec.getOrderByField()), orderBySpec.getAscending());
					}
				}
				else {
					String message = messageGenerator.getMessage("drilldown.query.generation.incompatible.query.request", getMessageGeneratorArgs(request));
					throw new QueryException(message);
				}
			}
		}
		return querySpecs;
	}

	private QuerySpec[] createQuerySpecs(DrillDownRequest request, QueryExecutor queryExecutor) throws QueryException {
		DrillDownTreePath path = request.getPath();
		if (path == null) {
			Collection<QuerySpec> querySpecs = request.getQuerySpecs();
			if (querySpecs.isEmpty() == false) {
				QuerySpec[] clonedQuerySpecs = querySpecs.toArray(new QuerySpec[querySpecs.size()]);
				for (int i = 0; i < clonedQuerySpecs.length; i++) {
					try {
						clonedQuerySpecs[i] = (QuerySpec) clonedQuerySpecs[i].clone();
					} catch (CloneNotSupportedException e) {
						String message = messageGenerator.getMessage("drilldown.query.generation.root.query.clone.failure", getMessageGeneratorArgs(request));
						throw new QueryException(message, e);
					}
				}
				return clonedQuerySpecs;
			}
			return null;
		}
		// now traverse up the path till we hit a TypeIDDrillDownTreePathElement
		List<FieldValueDrillDownTreePathElement> trailingFieldValuePathElements = new LinkedList<FieldValueDrillDownTreePathElement>();
		DrillDownTreePathElement immediateParentOfTrailingFieldValuePathElements = path.readTrailingFieldValuePathElements(trailingFieldValuePathElements);
		// now pathElement is either TypeIDDrillDownTreePathElement or InstanceIDDrillDownTreePathElement
		if (immediateParentOfTrailingFieldValuePathElements instanceof TypeIDDrillDownTreePathElement) {
			QuerySpec querySpec = null;
			// we are in business, lets do further analysis
			TypeIDDrillDownTreePathElement typeIDDrillDownTreePathElement = (TypeIDDrillDownTreePathElement) immediateParentOfTrailingFieldValuePathElements;
			Entity entity = typeIDDrillDownTreePathElement.getEntity();
			DrillDownTreePathElement grandParent = path.getParent(typeIDDrillDownTreePathElement);
			if (grandParent == null) {
				//we do not have a parent which means we represent the root, use the entity type id to get the right query spec in session
				try {
					querySpec = (QuerySpec) request.getQuerySpec(entity.getGUID()).clone();
				} catch (CloneNotSupportedException e) {
					String message = messageGenerator.getMessage("drilldown.query.generation.root.query.clone.failure", getMessageGeneratorArgs(request));
					throw new QueryException(message, e);
				}
			}
			else if (grandParent instanceof InstanceIDDrillDownTreePathElement) {
				// we are in big time business now , enough analysis, lets get results
				InstanceIDDrillDownTreePathElement instanceIDDrillDownTreePathElement = (InstanceIDDrillDownTreePathElement) grandParent;
				Entity parentEntity = instanceIDDrillDownTreePathElement.getEntity();
				querySpec = getTypeSpecificDrilldownProvider(entity).getDrillDownQuery(logger, parentEntity, instanceIDDrillDownTreePathElement.getInstanceID(), entity.getGUID(), queryExecutor);
			} else {
				throw new IllegalArgumentException(messageGenerator.getMessage("drilldown.query.generation.invalid.parent.found", getMessageGeneratorArgs(request, immediateParentOfTrailingFieldValuePathElements.getToken(), grandParent.getToken())));
			}
			if (querySpec != null) {
				EntityVisualizer entityVisualizer = entityVisualizerProvider.getEntityVisualizerById(querySpec.getSchema().getTypeID());
				// apply trailing fieldvalue pathelements as filter conditions
				for (FieldValueDrillDownTreePathElement trailingPathElement : trailingFieldValuePathElements) {
					String fieldName = entityVisualizer.getName(trailingPathElement.getFieldName());
					//when doing drilldown with @id group by, you may end up with a situation like
					//select * from /concept A where @id = 10 and @id 10
					//A is a child (by reference) of a concept B , so to find A
					//we add @id = 10 (by getting the value 10 from concept B)
					//then we do a group by on A by @id, so we add @id = 10 again when expanding
					//the group by header row
					if (hasConditionFor(querySpec.getCondition(), fieldName) == false) {
						if (trailingPathElement.getValue().isNull() == true) {
							querySpec.addAndCondition(new QueryPredicate(querySpec.getSchema(), fieldName, QueryPredicate.IS_NULL));
						}
						else {
							querySpec.addAndCondition(new QueryPredicate(querySpec.getSchema(), fieldName, QueryPredicate.EQ, trailingPathElement.getValue()));
						}
					}
				}
			}
			return new QuerySpec[]{querySpec};
		} else if (immediateParentOfTrailingFieldValuePathElements instanceof InstanceIDDrillDownTreePathElement) {
			// we cannot deal with InstanceIDDrillDownTreePathElement, that is the job of com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.DrillDownDataProvider.getChildTypes(DrillDownRequest)
			throw new IllegalArgumentException(messageGenerator.getMessage("drilldown.query.generation.instance.pathelement.found", getMessageGeneratorArgs(request, immediateParentOfTrailingFieldValuePathElements.getToken())));
		}
		return null;
	}

	private boolean hasConditionFor(QueryCondition condition, String fieldName) {
//		if (condition instanceof QueryUnaryTerm) {
//			QueryUnaryTerm unaryTerm = (QueryUnaryTerm) condition;
//			return hasConditionFor(unaryTerm.getTerm(), fieldName);
//		}
//		else if (condition instanceof QueryBinaryTerm) {
//			QueryBinaryTerm binaryTerm = (QueryBinaryTerm) condition;
//			return hasConditionFor(binaryTerm.getLeftTerm(), fieldName) || hasConditionFor(binaryTerm.getRightTerm(), fieldName);
//		}
//		else if (condition instanceof QueryPredicate) {
//			QueryPredicate predicate = (QueryPredicate) condition;
//			return predicate.evalLeftArgument().getValue().equals(fieldName);
//		}
		//PATCH by passing condition presence check since we dont get right results for the situation fieldA is not null and then group by fieldA
		return false;
	}
}