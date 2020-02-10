package com.tibco.cep.query.aggregate.as;

import static com.tibco.be.model.functions.FunctionDomain.QUERY;
import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.query.aggregate.Aggregates;
import com.tibco.cep.query.api.QueryResultSet;
import com.tibco.cep.query.api.impl.local.AggregateResultSet;
import com.tibco.cep.query.functions.QueryUtilFunctions;
import com.tibco.cep.query.functions.ResultSetFunctions;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASEntityDao;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 12/6/12
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */

@com.tibco.be.model.functions.BEPackage(
        catalog = "CEP Query",
        category = "Query.Datagrid.Aggregate",
        synopsis = "Query aggregate functions")

public class AsAggregateFunctions {

    static ASEntityDao findDao(String entityUri) {
        try {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            if (("class" + SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0]).equals(cluster.getDaoProvider().getClass())) {
                throw new RuntimeException("This data grid provider [" + cluster.getDaoProvider().getClass()
                        + "] is not compatible with the aggregate functions");
            }
            TypeManager.TypeDescriptor td = cluster.getRuleServiceProvider().getTypeManager().getTypeDescriptor(entityUri);
            if (td != null) {
                EntityDao dao = cluster.getDaoProvider().getEntityDao(td.getImplClass());

                return (ASEntityDao) dao;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new IllegalArgumentException("Entity " + entityUri + " not found");
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "count",
            synopsis = "Count the number of entities of the URI specified.",
            signature = "void count(String resultSetName, String entityUri)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Count the number of entities of the URI specified.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void count(String resultSetName, String entityUri) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsCountAggregator.class, null, null)
                .execute();
        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.COUNT.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);

    }

    @com.tibco.be.model.functions.BEFunction(
            name = "countList",
            synopsis = "Count the number of entities of the URI specified.",
            signature = "Object countList(String resultSetName, String entityUri)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Count the number of entities of the URI specified.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object countList(String resultSetName, String entityUri) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsCountAggregator.class, null, null)
                .execute();
        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterCount",
            synopsis = "Count the number of entities of the specified URI, which satisfies the condition in the filter.",
            signature = "void filterCount(String resultSetName,String entityUri, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Count the number of entities of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void filterCount(String resultSetName, String entityUri, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsCountAggregator.class, null, filter)
                .execute();
        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.COUNT.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterCountList",
            synopsis = "Count the number of entities of the specified URI, which satisfies the condition in the filter.",
            signature = "Object filterCountList(String resultSetName,String entityUri, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Count the number of entities of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object filterCountList(String resultSetName, String entityUri, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsCountAggregator.class, null, filter)
                .execute();
        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupCount",
            synopsis = "Count the number of entities of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "void groupCount(String resultSetName, String entityUri, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Count the number of entities of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void groupCount(String resultSetName, String entityUri, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsCountAggregator.class, null, filter, groupByFieldNames)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.COUNT.toString(), groupByFieldNames);
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupCountList",
            synopsis = "Count the number of entities of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "Object groupCountList(String resultSetName, String entityUri, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column or an Object[] of columns."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Count the number of entities of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = "<code> String resultSetName = \"groupCountListRS\" ; <br>"+
            		"Object resultSet = Query.Datagrid.Aggregate.groupCountList(resultSetName, \"/Concepts/Employee\",\"age < 4\", \"age\", \"exp\"); <br> " +
            		"//Iterate over the result set <br>" +
            		"while(Query.Util.sizeOfList(resultList) > 0){ <br> " +
                         "&nbsp&nbsp&nbsp  Object[] s = Query.Util.removeFromList(resultList, 0); <br>"+ 
                         "&nbsp&nbsp&nbsp  /* s[0]-- age, s[1]-- exp, s[2]-- count of the employee entities <br>" +
                         "&nbsp&nbsp&nbsp satisfying the filter condition for this age and exp */<br>" +
            			 "&nbsp&nbsp&nbsp  System.debugOut(resultSetName + \" :: \" + s[0] + \" : \"+ s[1] + \" : \"+ s[2]); <br> "+		
            		"} </code>"
    )
    public static Object groupCountList(String resultSetName, String entityUri, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsCountAggregator.class, null, filter, groupByFieldNames)
                .execute();
        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString(), groupByFieldNames);

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "min",
            synopsis = "Find the minimum of the entities from the field specified by field name, of the specified URI.",
            signature = "void min(String resultSetName, String entityUri, String fieldName)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the minimum of the entities from the field specified by field name, of the specified URI.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void min(String resultSetName, String entityUri, String fieldName) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMinAggregator.class, fieldName, null)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.MIN.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "minList",
            synopsis = "Find the minimum of the entities from the field specified by field name, of the specified URI.",
            signature = "Object minList(String resultSetName, String entityUri, String fieldName)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the minimum of the entities from the field specified by field name, of the specified URI.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object minList(String resultSetName, String entityUri, String fieldName) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMinAggregator.class, fieldName, null)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterMin",
            synopsis = "Find the minimum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            signature = "void filterMin(String resultSetName, String entityUri, String fieldName, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the minimum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void filterMin(String resultSetName, String entityUri, String fieldName, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMinAggregator.class, fieldName, filter)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.MIN.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterMinList",
            synopsis = "Find the minimum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            signature = "Object filterMinList(String resultSetName, String entityUri, String fieldName, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the minimum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object filterMinList(String resultSetName, String entityUri, String fieldName, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMinAggregator.class, fieldName, filter)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }


    @com.tibco.be.model.functions.BEFunction(
            name = "groupMin",
            synopsis = "Find the minimum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "void groupMin(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for finding the minimum value."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the minimum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void groupMin(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMinAggregator.class, fieldName, filter, groupByFieldNames)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.MIN.toString(), groupByFieldNames);
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupMinList",
            synopsis = "Find the minimum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "Object groupMinList(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for finding the minimum value."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column or an Object[] of columns."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the minimum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = "<code>String resultSetName = \"groupMinListRS\" ; <br>"+
            		"Object resultSet = Query.Datagrid.Aggregate.groupMinList(resultSetName, \"/Concepts/Employee\",\"exp\",\"age < 4\", \"age\", \"dept\"); <br> " +
            		"//Iterate over the result set <br>" +
            		"while(Query.Util.sizeOfList(resultList) > 0){ <br> " +
                         "&nbsp&nbsp&nbsp  Object[] s = Query.Util.removeFromList(resultList, 0); <br>"+ 
                         "&nbsp&nbsp&nbsp  /* s[0]-- age, s[1]-- exp, s[2]-- minimum exp from the employee entities <br>" +
                         "&nbsp&nbsp&nbsp satisfying the filter condition for this age and exp */<br>" +
            			 "&nbsp&nbsp&nbsp  System.debugOut(resultSetName + \" :: \" + s[0] + \" : \"+ s[1] + \" : \"+ s[2]); <br> "+		
            		"}</code>"
    )
    public static Object groupMinList(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMinAggregator.class, fieldName, filter, groupByFieldNames)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString(), groupByFieldNames);

        return l;
    }


    @com.tibco.be.model.functions.BEFunction(
            name = "max",
            synopsis = "Find the maximum of the entities from the field specified by field name, of the specified URI.",
            signature = "void max(String resultSetName, String entityUri, String fieldName)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the maximum of the entities from the field specified by field name, of the specified URI.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void max(String resultSetName, String entityUri, String fieldName) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMaxAggregator.class, fieldName, null)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.MAX.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "maxList",
            synopsis = "Find the maximum of the entities from the field specified by field name, of the specified URI.",
            signature = "Object maxList(String resultSetName, String entityUri, String fieldName)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the maximum of the entities from the field specified by field name, of the specified URI.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object maxList(String resultSetName, String entityUri, String fieldName) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();

        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMaxAggregator.class, fieldName, null)
                .execute();


        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterMax",
            synopsis = "Find the maximum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            signature = "void filterMax(String resultSetName, String entityUri, String fieldName, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for finding the minimum value."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the maximum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void filterMax(String resultSetName, String entityUri, String fieldName, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMaxAggregator.class, fieldName, filter)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.MAX.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterMaxList",
            synopsis = "Find the maximum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            signature = "Object filterMaxList(String resultSetName, String entityUri, String fieldName, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for finding the minimum value."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the maximum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object filterMaxList(String resultSetName, String entityUri, String fieldName, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMaxAggregator.class, fieldName, filter)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupMax",
            synopsis = "Find the maximum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "void groupMax(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for finding the minimum value."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the maximum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void groupMax(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMaxAggregator.class, fieldName, filter, groupByFieldNames)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.MAX.toString(), groupByFieldNames);
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupMaxList",
            synopsis = "Find the maximum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "Object groupMaxList(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for finding the minimum value."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column or an Object[] of columns."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the maximum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = "<code>String resultSetName = \"groupMaxListRS\" ; <br>"+
            		"Object resultSet = Query.Datagrid.Aggregate.groupMaxList(resultSetName, \"/Concepts/Employee\",\"exp\",\"age < 4\", \"age\", \"dept\"); <br> " +
            		"//Iterate over the result set <br>" +
            		"while(Query.Util.sizeOfList(resultList) > 0){ <br> " +
                         "&nbsp&nbsp&nbsp  Object[] s = Query.Util.removeFromList(resultList, 0); <br>"+ 
                         "&nbsp&nbsp&nbsp  /* s[0]-- age, s[1]-- exp, s[2]-- maximum exp from the employee entities <br>" +
                         "&nbsp&nbsp&nbsp satisfying the filter condition for this age and exp */<br>" +
            			 "&nbsp&nbsp&nbsp  System.debugOut(resultSetName + \" :: \" + s[0] + \" : \"+ s[1] + \" : \"+ s[2]); <br> "+		
            		"}<code>"
    )
    public static Object groupMaxList(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsMaxAggregator.class, fieldName, filter, groupByFieldNames)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString(), groupByFieldNames);

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "sum",
            synopsis = "Find the sum of the entities from the field specified by field name, of the specified URI.",
            signature = "void sum(String resultSetName, String entityUri, String fieldName)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the sum of the entities from the field specified by field name, of the specified URI.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void sum(String resultSetName, String entityUri, String fieldName) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsSumAggregator.class, fieldName, null)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.SUM.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "sumList",
            synopsis = "Find the sum of the entities from the field specified by field name, of the specified URI.",
            signature = "Object sumList(String resultSetName, String entityUri, String fieldName)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the sum of the entities from the field specified by field name, of the specified URI.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object sumList(String resultSetName, String entityUri, String fieldName) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsSumAggregator.class, fieldName, null)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;

    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterSum",
            synopsis = "Find the sum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            signature = "void filterSum(String resultSetName, String entityUri, String fieldName, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the sum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void filterSum(String resultSetName, String entityUri, String fieldName, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsSumAggregator.class, fieldName, filter)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.SUM.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterSumList",
            synopsis = "Find the sum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            signature = "Object filterSumList(String resultSetName, String entityUri, String fieldName, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the sum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object filterSumList(String resultSetName, String entityUri, String fieldName, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsSumAggregator.class, fieldName, filter)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupSum",
            synopsis = "Find the sum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "void groupSum(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the sum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void groupSum(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsSumAggregator.class, fieldName, filter, groupByFieldNames)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.SUM.toString(), groupByFieldNames);
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupSumList",
            synopsis = "Find the sum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "Object groupSumList(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column or an Object[] of columns."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the sum of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = "<code>String resultSetName = \"groupSumListRS\" ; <br>"+
            		"Object resultSet = Query.Datagrid.Aggregate.groupSumList(resultSetName, \"/Concepts/Employee\",\"exp\",\"age < 4\", \"age\", \"dept\"); <br> " +
            		"//Iterate over the result set <br>" +
            		"while(Query.Util.sizeOfList(resultList) > 0){ <br> " +
                         "&nbsp&nbsp&nbsp  Object[] s = Query.Util.removeFromList(resultList, 0); <br>"+ 
                         "&nbsp&nbsp&nbsp  /* s[0]-- age, s[1]-- exp, s[2]-- sum of exp of the employee entities <br>" +
                         "&nbsp&nbsp&nbsp satisfying the filter condition for this age and exp */<br>" +
            			 "&nbsp&nbsp&nbsp  System.debugOut(resultSetName + \" :: \" + s[0] + \" : \"+ s[1] + \" : \"+ s[2]); <br> "+		
            		"}</code>"
    )
    public static Object groupSumList(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsSumAggregator.class, fieldName, filter, groupByFieldNames)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString(), groupByFieldNames);

        return l;
    }


    @com.tibco.be.model.functions.BEFunction(
            name = "avg",
            synopsis = "Find the average of the entities from the field specified by field name, of the specified URI.",
            signature = "void avg(String resultSetName, String entityUri, String fieldName)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the average of the entities from the field specified by field name, of the specified URI.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
      public static void avg(String resultSetName, String entityUri, String fieldName) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsAvgAggregator.class, fieldName, null)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.AVG.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "avgList",
            synopsis = "Find the average of the entities from the field specified by field name, of the specified URI.",
            signature = "Object avgList(String resultSetName, String entityUri, String fieldName)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the average of the entities from the field specified by field name, of the specified URI.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object avgList(String resultSetName, String entityUri, String fieldName) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsAvgAggregator.class, fieldName, null)
                .execute();


        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterAvg",
            synopsis = "Find the average of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            signature = "void filterAvg(String resultSetName, String entityUri, String fieldName, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the average of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void filterAvg(String resultSetName, String entityUri, String fieldName, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsAvgAggregator.class, fieldName, filter)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.AVG.toString());
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "filterAvgList",
            synopsis = "Find the average of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            signature = "Object filterAvgList(String resultSetName, String entityUri, String fieldName, String filter)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the average of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static Object filterAvgList(String resultSetName, String entityUri, String fieldName, String filter) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsAvgAggregator.class, fieldName, filter)
                .execute();


        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString());

        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupAvg",
            synopsis = "Find the average of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "void groupAvg(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void",
                    desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the average of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = ""
    )
    public static void groupAvg(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsAvgAggregator.class, fieldName, filter, groupByFieldNames)
                .execute();

        QueryResultSet queryResultset = new AggregateResultSet(maps[0], Aggregates.AVG.toString(), groupByFieldNames);
        UUID uid = UUID.randomUUID();
        ResultSetFunctions.register(uid.toString(), resultSetName, queryResultset);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "groupAvgList",
            synopsis = "Find the average of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            signature = "Object groupAvgList(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames)",
            params = {
                    @FunctionParamDescriptor(name = "resultSetName", type = "String", desc = "The resultset name"),
                    @FunctionParamDescriptor(name = "entityUri", type = "String", desc = "The entity uri."),
                    @FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Name of the field used for computation."),
                    @FunctionParamDescriptor(name = "filter", type = "String", desc = "Condition to be satisfied by the entities in the result."),
                    @FunctionParamDescriptor(name = "groupByFieldNames", type = "String...", desc = "Names of the fields in groupBy clause.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object",
                    desc = "Return a List of rows. Each row may be a single Object column or an Object[] of columns."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Find the average of the entities from the field specified by field name, of the specified URI, which satisfies the condition in the filter for each group.",
            cautions = "none",
            fndomain = {QUERY, ACTION},
            example = "<code>String resultSetName = \"groupAvgListRS\" ; <br>"+
            		"Object resultSet = Query.Datagrid.Aggregate.groupAvgList(resultSetName, \"/Concepts/Employee\",\"exp\",\"age < 4\", \"age\", \"dept\"); <br> " +
            		"//Iterate over the result set <br>" +
            		"while(Query.Util.sizeOfList(resultList) > 0){ <br> " +
                         "&nbsp&nbsp&nbsp  Object[] s = Query.Util.removeFromList(resultList, 0); <br>"+ 
                         "&nbsp&nbsp&nbsp  /* s[0]-- age, s[1]-- exp, s[2]-- average exp of the employee entities <br>" +
                         "&nbsp&nbsp&nbsp satisfying the filter condition for this age and exp */<br>" +
            			 "&nbsp&nbsp&nbsp  System.debugOut(resultSetName + \" :: \" + s[0] + \" : \"+ s[1] + \" : \"+ s[2]); <br> "+		
            		"}<code>"
    )
    public static Object groupAvgList(String resultSetName, String entityUri, String fieldName, String filter, String... groupByFieldNames) {
        ASEntityDao dao = findDao(entityUri);
        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
        Map[] maps = new AsAggregator(spaceMap.getSpace())
                .add(AsAvgAggregator.class, fieldName, filter, groupByFieldNames)
                .execute();

        List l = (List) QueryUtilFunctions.executeInDynamicQuerySession_AsAggregateFunctions(maps[0], resultSetName, Aggregates.COUNT.toString(), groupByFieldNames);

        return l;
    }

//    public static Object customAggregate(Class<? extends Aggregate> c, String entityUri, String fieldName) {
////        AsAggregator.add(c, fieldName, null);
////        Map[] results = AsAggregator.execute();
////        return results;
//
//        ASEntityDao dao = findDao(entityUri);
//        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
//        Map[] maps = new AsAggregator(spaceMap.getSpace())
//                .add(c, fieldName, null)
//                .execute();
//        //todo
//        return null;
//    }
//
//    public static Map[] customAggregate(Class<? extends Aggregate> c, String entityUri, String fieldName, String filter) {
////        AsAggregator.add(c, fieldName, filter);
////        Map[] results = AsAggregator.execute();
////        return results;
//        ASEntityDao dao = findDao(entityUri);
//        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
//        Map[] maps = new AsAggregator(spaceMap.getSpace())
//                .add(c, fieldName, filter)
//                .execute();
//        //todo
//        return null;
//    }
//
//    public static Map[] customAggregate(Class<? extends Aggregate> c, String entityUri,  String fieldName, String filter, String... groupByFieldNames) {
////        AsAggregator.add(c, fieldName, filter, groupByFieldNames);
////        Map[] results = AsAggregator.execute();
////        return results;
//
//        ASEntityDao dao = findDao(entityUri);
//        SpaceMap spaceMap = (SpaceMap) dao.getInternal();
//        Map[] maps = new AsAggregator(spaceMap.getSpace())
//                .add(c, fieldName, filter, groupByFieldNames)
//                .execute();
//        //todo
//        return null;
//    }
}
