package com.tibco.cep.metric.query;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;

public abstract class QueryManager {

	final static Logger logger = LogManagerFactory.getLogManager().getLogger(
			QueryManager.class);

	protected static final String T_CMD = "command";
	protected static final String T_PROJ = "projection";
	private static final String T_TYPE = "type";
	protected static final String T_COND = "condition";
	protected static final String T_GBY = "groupby";
	protected static final String T_OBY = "orderby";
	protected static final String T_SDIR = "sortdirection";
	protected static final String T_GBYFN = "groupbyfieldname";
	protected static boolean _bInitialized = false;

	static QueryManager manager = null;

	public static QueryManager getInstance() {
		synchronized (QueryManager.class) {
			if (manager == null) {
				RuleServiceProviderManager rspm = RuleServiceProviderManager
						.getInstance();
				RuleServiceProvider rsp = rspm.getDefaultProvider();
				BEProperties props = (BEProperties) rsp.getProperties();
				boolean backingStoreEnabled = props.getBoolean(
						SystemProperty.CLUSTER_HAS_BACKING_STORE
								.getPropertyName(), false);
				boolean queryMemManagerEnabled = props.getBoolean(
						"be.engine.metric.memory.enable", false);
				if (!backingStoreEnabled && queryMemManagerEnabled) {
					logger
							.log(
									Level.WARN,
									"Backing store has not been enabled, it is strongly recommended to enable the backing store");
					logger
							.log(
									Level.WARN,
									"Starting metric engine in memory only mode, this mode should be enabled for development purpose only");
					manager = QueryMemManager.getInstance();
				} else {
					manager = QueryDBManager.getInstance();
				}
			}
		}
		return manager;
	}

	public abstract void init() throws Exception;

	public int countQuery(String query) {
		return countQuery(query, null);
	}

	protected abstract int countQuery(String query, Map<String, String> values);

	@SuppressWarnings("unchecked")
	public Iterator query(String query) {
		return query(query, null);
	}

	@SuppressWarnings("unchecked")
	protected abstract Iterator query(String query, Map<String, String> values);

	public Map<String, Integer> groupByQuery(String query) {
		return groupByQuery(query, null);
	}

	protected abstract Map<String, Integer> groupByQuery(String query,
			Map<String, String> values);

	@SuppressWarnings("unchecked")
	public abstract void closeQueryResult(Iterator itr);

	protected boolean parseQuery(String query, Map<String, String> tokens)
			throws Exception {
		// String pattern =
		// "( *from *)([A-Za-z.]*)( *where *)([( ]*)([|()A-Za-z =']*)([)]*)( *order *by *)??";
		// String pattern =
		// "( *from *)([A-Za-z.]*)( *where *)([|()A-Za-z_$ =']*)(?: *order *by *)([A-Za-z_$]*)( *desc)?";
		// String pattern =
		// " *(select|drill|drilldown) *(.*)( *from *)([0-9A-Za-z._]+)( *where *)([0-9@|()A-Za-z_$\" ='><!]*)( *group *by *([@0-9A-Za-z_$]+))?( *order *by *([@0-9A-Za-z_$]+))? *(desc|asc)?";
		// Look for <command><project fields><type name><the rest after 'where'>
		String pattern = " *(select|drill|drilldown) *(.*)(?: *from *)([0-9A-Za-z._]+)(?: *(?:where *))*(.*)";
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		boolean found = false;
		String condition = null;
		while (m.find()) {
			found = true;
			for (int i = 0; i <= m.groupCount(); i++) {
				logger.log(Level.DEBUG, "Group found(" + i + ") : >"
						+ m.group(i) + "<");
				String token = (m.group(i) != null ? m.group(i).trim() : null);
				switch (i) {
				case 1:
					tokens.put(T_CMD, token);
					break;
				case 2:
					tokens.put(T_PROJ, token);
					break;
				case 3:
					tokens.put(T_PROJ, token);
					break;
				case 4:
					condition = token;
					tokens.put(T_COND, token);
					break;
				default:
					break;
				}
			}
			logger.log(Level.DEBUG, "");
			break;
		}
		if (found == false) {
			logger.log(Level.INFO, "Cannot parse the query : " + query);
			return found;
		}

		if (condition == null) {
			return found;
		} else {
			condition = condition.trim();
		}

		boolean conditionExtracted = false;
		pattern = " *group *by *([@0-9A-Za-z_$]+)";
		p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		m = p.matcher(condition);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				logger.log(Level.DEBUG, "  group by found(" + i + ") : >"
						+ m.group(i) + "<");
				if (i == 0) {
					int groupStart = m.start();
					String realCondition = condition.substring(0, groupStart);
					logger.log(Level.DEBUG, "  (Group by)Real condition : "
							+ realCondition);
					tokens.put(T_COND, realCondition.trim());
					conditionExtracted = true;
				} else if (i == 1) {
					tokens.put(T_GBY, m.group(1).trim());
					tokens.put(T_GBYFN, m.group(1).trim());
				}
			}
		}
		pattern = " *order *by *([@0-9A-Za-z_$]+) *(asc|desc)?";
		p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		m = p.matcher(condition);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				logger.log(Level.DEBUG, "  order by found(" + i + ") : >"
						+ m.group(i) + "<");
				if (i == 0 && conditionExtracted == false) {
					int groupStart = m.start();
					String realCondition = condition.substring(0, groupStart);
					logger.log(Level.DEBUG, "  (Order by)Real condition : "
							+ realCondition);
					tokens.put(T_COND, realCondition.trim());
					conditionExtracted = true;
				} else if (i == 1) {
					tokens.put(T_OBY, m.group(i).trim());
				} else if (i == 2) {
					String dir = (m.group(i) != null ? m.group(i).trim() : null);
					tokens.put(T_SDIR, dir);
				}
			}
		}
		return found;
	}

}