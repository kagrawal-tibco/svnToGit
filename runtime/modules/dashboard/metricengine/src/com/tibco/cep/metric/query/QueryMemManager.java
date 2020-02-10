package com.tibco.cep.metric.query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.metric.evaluator.ASTWalker;
import com.tibco.cep.metric.importer.MetricLoader;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

//singleton
public class QueryMemManager extends QueryManager {

	private static final String DRILL_CMD = "drill";
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private final static Logger _logger = LogManagerFactory.getLogManager()
			.getLogger(QueryMemManager.class);

	private static QueryManager _instance = new QueryMemManager();

	private MetricLoader _loader = null;

	enum SortDirection {
		ASC, DESC;
	}

	private QueryMemManager() {
		_loader = new MetricLoader();
	}

	public static QueryManager getInstance() {
		return _instance;
	}

	public void init() throws Exception {
		synchronized (_instance) {
			if (_bInitialized) {
				return;
			}
		}
		_loader.init();
		_bInitialized = true;
	}

	@Override
	protected int countQuery(String query, Map<String, String> values) {

		List<Concept> valueList = null;

		_logger.log(Level.DEBUG, "Execute count query : " + query);

		try {
			Map<String, String> tokens = new HashMap<String, String>();
			parseQuery(query, tokens);

			String cmd = tokens.get(T_CMD);
			String proj = tokens.get(T_PROJ);
			String cond = tokens.get(T_COND);

			List<Concept> metrics = null;
			if (cmd.toLowerCase().indexOf(DRILL_CMD) != -1) {
				metrics = _loader.fetchMetricsDVM();
				valueList = matchMetrics(metrics, cmd, proj+"DVM", cond);
			} else if (checkProjIsMetric(proj)){
				metrics = _loader.fetchMetrics();
				valueList = matchMetrics(metrics, cmd, proj, cond);
			} else {
				metrics = _loader.fetchConcepts();
				valueList = matchMetrics(metrics, cmd, proj, cond);
			}
		} catch (Exception e) {
			_logger.log(Level.INFO, "Failed to execute query : " + query, e);
		}
		return valueList != null ? valueList.size() : 0;
	}

	private boolean checkProjIsMetric(String proj) throws ClassNotFoundException {
		RuleServiceProviderManager rspm = RuleServiceProviderManager.getInstance();
		RuleServiceProvider rsp = rspm.getDefaultProvider();
		Class entityClass = ((ClassLoader) rsp.getTypeManager()).loadClass(proj);
		if (com.tibco.cep.runtime.model.element.Metric.class.isAssignableFrom(entityClass)) {
			return true;
		}
		return false;
	}

	@Override
	protected Map<String, Integer> groupByQuery(String query,
			Map<String, String> values) {

		List<Concept> valueList = null;

		_logger.log(Level.DEBUG, "Execute group by query : " + query);

		Map<String, String> tokens = new HashMap<String, String>();

		try {
			parseQuery(query, tokens);
		} catch (Exception e) {
			_logger.log(Level.INFO, "Failed to parse query : " + query, e);
		}

		String cmd = tokens.get(T_CMD);
		String proj = tokens.get(T_PROJ);
		String cond = tokens.get(T_COND);
		String groupBy = tokens.get(T_GBY);
		String groupByFieldName = tokens.get(T_GBYFN);

		try {
			List<Concept> metrics;
			metrics = _loader.fetchAllMetrics();
			valueList = matchMetrics(metrics, cmd, proj, cond);
		} catch (Exception e) {
			_logger.log(Level.INFO, "Failed to execute query : " + query, e);
		}

		Map<String, Integer> valueMap = new HashMap<String, Integer>();
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		if (groupBy != null && groupByFieldName != null) {
			for (Concept metric : valueList) {
				try {
					if (metric.getPropertyValue(groupByFieldName) != null) {
						Integer i = valueMap.get(groupByFieldName);
						if (i != null) {
							valueMap.put(groupByFieldName, i + 1);
						} else {
							valueMap.put(groupByFieldName, 1);
						}
					}
				} catch (NoSuchFieldException e) {
					_logger.log(Level.WARN,
							"Metric field missing in group by value map : ["
									+ groupByFieldName + "] in the metric ["
									+ metric.getClass().getSimpleName() + "]",
							e);
				}
			}

			for (Concept metric : valueList) {
				for (String key : valueMap.keySet()) {
					Integer count = valueMap.get(key);
					try {
						Object value = metric.getPropertyValue(key);
						valueMap.remove(key);
						if (value instanceof Calendar) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									DATE_FORMAT);
							String sval = sdf.format(((Calendar) value)
									.getTime());
							countMap.put(sval, count);
						} else {
							countMap.put(value.toString(), count);
						}
					} catch (NoSuchFieldException e) {
						_logger.log(Level.WARN,
								"Metric field missing in group by count : ["
										+ groupByFieldName
										+ "] in the metric ["
										+ metric.getClass().getSimpleName()
										+ "]", e);
					}
				}
			}
		}

		return countMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Iterator query(String query, Map<String, String> values) {

		List<Concept> valueList = null;

		_logger.log(Level.DEBUG, "Execute query : " + query);

		try {
			Map<String, String> tokens = new HashMap<String, String>();

			parseQuery(query, tokens);

			String cmd = tokens.get(T_CMD);
			String proj = tokens.get(T_PROJ);
			String cond = tokens.get(T_COND);
			String sortBy = tokens.get(T_OBY);
			String sortDirection = tokens.get(T_SDIR);

			List<Concept> metrics = null;
			if (cmd.toLowerCase().indexOf(DRILL_CMD) != -1) {
				metrics = _loader.fetchMetricsDVM();
				valueList = matchMetrics(metrics, cmd, proj+"DVM", cond);
			} else {
				metrics = _loader.fetchMetrics();
				valueList = matchMetrics(metrics, cmd, proj, cond);
			}

			if (valueList != null && sortBy != null && sortDirection != null) {
				List<String> sortByList = new LinkedList<String>();
				sortByList.add(sortBy);
				OrderByComparator comparator = new OrderByComparator(
						sortByList, SortDirection.valueOf(sortDirection
								.toUpperCase()));
				Collections.sort(valueList, comparator);
			}

		} catch (Exception e) {
			_logger.log(Level.INFO, "Failed to execute query : " + query, e);
		}
		return valueList != null ? valueList.iterator() : null;
	}

	private List<Concept> matchMetrics(List<Concept> metrics, String cmd,
			String proj, String cond) throws Exception {
		List<Concept> valueList = new ArrayList<Concept>();
		ASTWalker evaluator = new ASTWalker(cond);

		if (cmd == null) {
			return null;
		}

		if (cmd.toLowerCase().indexOf(DRILL_CMD) != -1) {
			evaluator.setTestForPID();
		}
		for (Concept metric : metrics) {
			if (proj == null
					|| metric.getClass().getCanonicalName().equals(proj)) {
				if (evaluator.evaluate(metric)) {
					valueList.add(metric);
				}
			}
		}
		return valueList.size() != 0 ? valueList : null;
	}

	@SuppressWarnings("unchecked")
	public void closeQueryResult(Iterator itr) {
		// Nothing to be done here!
	}

	class OrderByComparator implements Comparator<Concept> {

		List<String> fields;
		SortDirection sortDirection;

		public OrderByComparator(List<String> fields,
				SortDirection sortDirection) {
			this.fields = fields;
			this.sortDirection = sortDirection;
		}

		@SuppressWarnings("unchecked")
		public int compare(Concept o1, Concept o2) {
			for (String field : fields) {
				try {
					if (o1.getPropertyValue(field) == null) {
						_logger.log(Level.ERROR,
								"Metric field missing in O1 Order By : ["
										+ field + "] in the metric ["
										+ o1.getClass().getSimpleName() + "]");
						return -1;
					}
					if (o2.getPropertyValue(field) == null) {
						_logger.log(Level.ERROR,
								"Metric field missing in O2 Order By : ["
										+ field + "] in the metric ["
										+ o2.getClass().getSimpleName() + "]");
						return -1;
					}
					switch (sortDirection) {
					case ASC:
						return ((Comparable) o1.getPropertyValue(field))
								.compareTo((Comparable) o2
										.getPropertyValue(field));
					case DESC:
						return ((Comparable) o1.getPropertyValue(field))
								.compareTo((Comparable) o2
										.getPropertyValue(field));
					default:
					}
				} catch (NoSuchFieldException e) {
					_logger.log(Level.WARN,
							"Metric field missing in Order By : [" + field
									+ "] in the metric ["
									+ o1.getClass().getSimpleName() + ", "
									+ o2.getClass().getSimpleName() + "]", e);
				}
			}
			return 0;
		}
	}
}
