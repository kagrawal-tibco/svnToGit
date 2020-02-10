package com.tibco.cep.metric.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.be.jdbcstore.impl.DBConceptMap;
import com.tibco.be.jdbcstore.impl.DBEntityMap;
import com.tibco.be.jdbcstore.impl.DBEntityMap.DBFieldMap;
import com.tibco.be.jdbcstore.impl.DBManager;
import com.tibco.be.jdbcstore.impl.DBAdapter;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;

public class QueryDBManager extends QueryManager {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(QueryDBManager.class);


    private static Map<String,String> TYPEFLD_BACKINGSTOREFLD_MAP = new HashMap<String, String>();

    static {
        TYPEFLD_BACKINGSTOREFLD_MAP.put("@id", "id$");
        TYPEFLD_BACKINGSTOREFLD_MAP.put("@extId", "extId$");
    }

    private DBManager _dbManager = null;
    private static QueryDBManager _instance = new QueryDBManager();
    private ArrayList<DBAdapter> _dbAdapters = new ArrayList<DBAdapter>();

    private QueryDBManager() {
    }

    public static QueryManager getInstance() {
        return _instance;
    }
    
    public void init() throws Exception {
        synchronized(_instance) {
            if (_bInitialized) {
                return;
            }
            _dbManager = DBManager.getInstance();
            _dbManager.createDBAdapters(_dbAdapters);
        }
    }

    // get the connection from its own adapters(connections)
    // pass connection to DBAdapter for query
    // query needs to handle select and drilldown
    // select * from metric
    // drill from metric
    protected int countQuery(String query, Map<String, String> values) {
        logger.log(Level.DEBUG, "Execute count query : " + query);
        Map<String, String> queryTokens = new HashMap<String, String>();
        List valueList = new ArrayList();
        List<Integer> typeList = new ArrayList<Integer>();
        try {
            translateQueryToSql(query, null, queryTokens, valueList, typeList);
            DBConceptMap cmap = (DBConceptMap) _dbManager.getEntityPropsMap().get(queryTokens.get(T_PROJ));
            for (int j=0; j < _dbAdapters.size(); j++) {
                DBAdapter config = _dbAdapters.get(j);
                if (config.isActive()) {
                    return config.loadMetricCount(cmap.getTableName(), queryTokens.get(T_COND), valueList, typeList);
                }
            }

        }
        catch (Exception e) {
            logger.log(Level.INFO, "Failed to execute query : " + query, e);
        }
        return 0;
    }

    protected Iterator query(String query, Map<String, String> values) {
        logger.log(Level.DEBUG, "Execute query : " + query);
        Map<String, String> queryTokens = new HashMap<String, String>();
        List valueList = new ArrayList();
        List<Integer> typeList = new ArrayList<Integer>();

        try {
            translateQueryToSql(query, null, queryTokens, valueList, typeList);
            DBConceptMap cmap = (DBConceptMap) _dbManager.getEntityPropsMap().get(queryTokens.get(T_PROJ));
            for (int j=0; j < _dbAdapters.size(); j++) {
                DBAdapter config = _dbAdapters.get(j);
                if (config.isActive()) {
                    return config.loadMetrics(queryTokens.get(T_COND), queryTokens.get(T_OBY), queryTokens.get(T_SDIR), cmap, valueList, typeList);
                }
            }
        }
        catch (Exception e) {
            logger.log(Level.INFO, "Failed to execute query : " + query, e);
        }
        return null;
    }
    
    protected Map<String, Integer> groupByQuery(String query, Map<String, String> values) {
        logger.log(Level.DEBUG, "Execute group by query : " + query);
        Map<String, String> queryTokens = new HashMap<String, String>();
        List valueList = new ArrayList();
        List<Integer> typeList = new ArrayList<Integer>();

        try {
            translateQueryToSql(query, null, queryTokens, valueList, typeList);
            DBConceptMap cmap = (DBConceptMap) _dbManager.getEntityPropsMap().get(queryTokens.get(T_PROJ));
            DBEntityMap.DBFieldMap fMap = /*cmap.getFieldMap(queryTokens.get(T_GBYFN)); */findDBFieldMap(queryTokens.get(T_GBYFN), cmap);
            for (int j=0; j < _dbAdapters.size(); j++) {
                DBAdapter config = _dbAdapters.get(j);
                if (config.isActive()) {
                    return config.loadMetricGroups(cmap.getTableName(), queryTokens.get(T_COND), 
                           queryTokens.get(T_GBY), queryTokens.get(T_OBY), queryTokens.get(T_SDIR), valueList, typeList, fMap.tableFieldMappingType);
                }
            }

        }
        catch (Exception e) {
            logger.log(Level.INFO, "Failed to execute query : " + query, e);
        }
        return new HashMap<String, Integer>();
    }
    
    //Result set iterator needs to be closed by calling this method
    public void closeQueryResult(Iterator itr) {
        _dbManager.closeLoadResult(itr);
    }
    
    public Iterator testQuery() throws Exception {
        String[] queries = {"select * from be.gen.Ontology.NewMetric where GroupOne = 'A' and GroupTwo = 'B' order by time_last_modified$"};
        for(int i=0; i<queries.length; i++) {
            Iterator itr = query(queries[i], null);
        }
        return null;
    }

    // convert into query string into sql query string
    private void translateQueryToSql(String query, Map<String, String> values, Map<String, String> tokens, List valueList, List<Integer> typeList) throws Exception {
        boolean isDrilldown = false;

        if (!parseQuery(query, tokens)) {
            logger.log(Level.INFO, "Cannot parse query : " + query);
        }

        String command = tokens.get(T_CMD);
        if (command.toLowerCase().indexOf("select") != -1) {
        }
        else if (command.toLowerCase().indexOf("drill") != -1) {
            isDrilldown = true;
        }
        else {
            logger.log(Level.INFO, "Invalid query command : " + command);
        }
        String typeName = tokens.get(T_PROJ);
        if (isDrilldown) {
            typeName += "DVM";
            tokens.put(T_PROJ, typeName);
        }
        String tableName = _dbManager.getTableName(typeName);
        if (tableName == null) {
            if (isDrilldown) {
                logger.log(Level.INFO, "Cannot find matching drilldown table for : " + typeName);
            }
            else {
                logger.log(Level.INFO, "Cannot find matching table for : " + typeName);
            }
        }
        else {
            logger.log(Level.DEBUG, "Using " + tableName + " for query");
            tokens.put("tablename", tableName);
        }
        String condition = tokens.get(T_COND);
        if (condition == null) {
            logger.log(Level.INFO, "No condition found for query : " + query);
        }

        DBConceptMap cmap = (DBConceptMap) _dbManager.getEntityPropsMap().get(typeName);
        String sqlCondition = translateCondition(condition, cmap, isDrilldown, valueList, typeList);
        tokens.put(T_COND, sqlCondition);

        String groupBy = tokens.get(T_GBY);
        if (groupBy != null) {
            String sqlGroupBy = translateField(groupBy, cmap);
            tokens.put(T_GBY, sqlGroupBy);
        }

        String orderBy = tokens.get(T_OBY);
        if (orderBy != null) {
            String sqlOrderBy = translateField(orderBy, cmap);
            tokens.put(T_OBY, sqlOrderBy);
        }
    }

    private String translateCondition(String condition, DBConceptMap entityMap, boolean isDrilldown, List valueList, List<Integer> typeList) throws Exception {
        StringBuffer sb = new StringBuffer();
        Translator translator = new Translator(condition, valueList, typeList);
        translator.translateCondition(sb, entityMap, isDrilldown);
        String convertedCondition = sb.toString();
        logger.log(Level.DEBUG, "Converted condition : " + convertedCondition);
        return convertedCondition;
    }

    private String translateField(String field, DBConceptMap entityMap) {
        DBEntityMap.DBFieldMap fMap = findDBFieldMap(field, entityMap);
        return "T." + fMap.tableFieldName;
    }
    
    private DBFieldMap findDBFieldMap(String field, DBConceptMap entityMap){
    	if (TYPEFLD_BACKINGSTOREFLD_MAP.containsKey(field) == true) {
    		field = TYPEFLD_BACKINGSTOREFLD_MAP.get(field);
    	}
        DBFieldMap fieldMap = entityMap.getFieldMap(field);
        if (fieldMap == null) {
        	throw new RuntimeException("could not find a field named '"+field+"' in "+entityMap.getEntityClass().getName());
        }
		return fieldMap;
    }

    public static void main(String[] args) throws Exception {
        QueryManager qm = QueryManager.getInstance();
        qm.init();
        qm.query("select * from be.gen.Ontology.NewMetric where GroupOne = \"A\" and GroupTwo = \"B\"");
    }
}
