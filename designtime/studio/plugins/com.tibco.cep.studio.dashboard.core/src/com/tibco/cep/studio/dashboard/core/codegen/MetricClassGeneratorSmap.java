package com.tibco.cep.studio.dashboard.core.codegen;

import static com.tibco.be.parser.codegen.CGConstants.BRK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.be.parser.codegen.CGUtil;
import com.tibco.be.parser.codegen.ConceptClassGeneratorSmap;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.MethodRecWriter;
import com.tibco.be.parser.codegen.StateMachineBlockLineBuffer;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.studio.core.functions.model.EMFMetricMethodModelFunction;
import com.tibco.cep.studio.core.util.mapper.MapperMetricUtils;
import com.tibco.cep.studio.core.util.mapper.MapperMetricUtils.FieldInfo;

public class MetricClassGeneratorSmap extends ConceptClassGeneratorSmap {

    protected static final String DVM_VAR_NAME = "dvm";
    protected static final String DVMS_VAR_NAME = "dvms";
    protected static final String SYSTEM_FIELD_SUFFIX = "_SY_SM";
    protected static final String EXTID_SEPARATOR = "^";
//    protected static final String COMPUTE_METHOD_NAME = "compute";
    protected static final String COMPUTE_ROLLING_TIME_METHOD_NAME = "computeRollingTime";
//    protected static final String LOOKUP_METHOD_NAME = "lookup";
    protected static final String GET_METHOD_NAME = "get";
    protected static final String INIT_METHOD_NAME = "initializeConfig";

//    protected static final String GET_METRIC_EXT_ID_METHOD_NAME = "getMetricExtId";
    // note - also defined in MetricHelper
    // private static final String COMPUTE_USING_DATA_OBJECT_METHOD_NAME =
    // "compute";
    // private static final String COMPUTE_WITH_IS_SET_FLAG_METHOD_NAME =
    // "compute";

    protected static final String IS_SET = "_isSet";
    protected static final int BOOLEAN_TYPE = 4;

    @SuppressWarnings("rawtypes")
    protected MetricClassGeneratorSmap(Concept cept, Properties oversizeStringConstants
            , Map ruleFnUsage, Map<String,StateMachineBlockLineBuffer> smblbMap, JavaClassWriter cc, Ontology o
            , Map<String, Map<String, int[]>> propInfoCache)
    {
    	super(cept, oversizeStringConstants, ruleFnUsage, smblbMap, cc, o, propInfoCache, true);
    }

    @SuppressWarnings("rawtypes")
    // Generate metric file
    public static JavaClassWriter makeMetricFile(Concept cept, Properties oversizeStringConstants, Map ruleFnUsage
    		, Map<String, StateMachineBlockLineBuffer> smblbMap, Ontology o
    		, Map<String, Map<String, int[]>> propInfoCache) throws Exception
    {
    	JavaClassWriter cc = new JavaClassWriter(cept.getName(),null);
        cc.setAccess("public");
    	MetricClassGeneratorSmap gen = new MetricClassGeneratorSmap(cept, oversizeStringConstants, ruleFnUsage, smblbMap, cc, o, propInfoCache);
        List<FieldInfo> computeOrderList = new ArrayList<FieldInfo>();
        Map<String, FieldInfo> fieldMap = MapperMetricUtils.generateMetricFieldInfo(cept, computeOrderList);

        gen.makeConceptFile_base();

        addMetricInitializationMethod(gen.cept, gen.cc);
        addMetricStaticVariables(gen.cept, gen.cc);
        addMetricMethod(gen.cept, gen.cc, fieldMap, computeOrderList);

        return gen.cc;
    }

    @SuppressWarnings("rawtypes")
    // Generate DVM file
    public static JavaClassWriter makeMetricSupportFile(Concept cept, Properties oversizeStringConstants
    		, Map ruleFnUsage, Map<String, StateMachineBlockLineBuffer> smblbMap, Ontology o
    		, Map<String, Map<String, int[]>> propInfoCache) throws Exception
    {

    	if (cept.isMetricTrackingEnabled() == false) {
    		throw new Exception("Incorrect settings in "+cept.getFullPath()+" for metric support class generation");
    	}
        List<FieldInfo> computeOrderList = new ArrayList<FieldInfo>();
        @SuppressWarnings("unused")
		Map<String, FieldInfo> fieldMap = MapperMetricUtils.generateMetricFieldInfo(cept, computeOrderList);

        JavaClassWriter cc = new JavaClassWriter(cept.getName(),null);
        cc.setAccess("public");

        MetricClassGeneratorSmap gen = new MetricClassGeneratorSmap(cept, oversizeStringConstants, ruleFnUsage, smblbMap, cc, o, propInfoCache);
        gen.makeConceptFile_base();

        return gen.cc;
    }

    @Override
    protected void setSuper() {
        String superClassName;
        //Modified By Anand 04/02/2012 as needed for cache query support in views
        if (cept.isMetricTrackingEnabled() == true) {
            superClassName = com.tibco.cep.runtime.model.element.impl.MetricDVMImpl.class.getName();
        } else {
            superClassName = com.tibco.cep.runtime.model.element.impl.MetricImpl.class.getName();
        }
        Concept superConcept = cept.getSuperConcept();
        if (superConcept != null) {
            superClassName = getFSName(superConcept);
            //Modified By Anand 04/02/2012 as needed for cache query support in views
            if (cept.isMetricTrackingEnabled() == true) {
                superClassName += "DVM";
            }
        }
        cc.setSuperClass(superClassName);
    }

    private static void addMetricInitializationMethod(Concept cept, JavaClassWriter cc) {
        StringBuilder body = new StringBuilder();
        MethodRecWriter mr = cc.createMethod(INIT_METHOD_NAME);
        mr.setAccess("private static");
        body.append("if (_isConfigured == false) {" + BRK);
        body.append("lockIsEnabled = ((com.tibco.be.util.BEProperties) " + CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getProperties()).getBoolean(\"be.engine.metric.lock.enable\", true);" + BRK);
        body.append("lockTimeout = ((com.tibco.be.util.BEProperties) " + CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getProperties()).getLong(\"be.engine.metric.lock.timeout\", 1000);" + BRK);
        body.append("lockRetryCount = ((com.tibco.be.util.BEProperties) " + CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getProperties()).getInt(\"be.engine.metric.lock.retrycount\", 10);" + BRK);
        body.append("lockIsLocal = ((com.tibco.be.util.BEProperties) " + CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getProperties()).getBoolean(\"be.engine.metric.lock.islocal\", true);" + BRK);
        body.append("com.tibco.be.util.config.cdd.ClusterConfig  cc = (com.tibco.be.util.config.cdd.ClusterConfig) ((com.tibco.be.util.BEProperties) " + CGConstants.getCurrentRuleSession +
        		".getRuleServiceProvider().getProperties()).get(com.tibco.cep.runtime.util.SystemProperty.CLUSTER_CONFIG.getPropertyName());" + BRK);
        //body.append("com.tibco.be.util.config.cdd.CacheManagerConfig cmc = (com.tibco.be.util.config.cdd.CacheManagerConfig) cc.getObjectManagement().getObjectManager();" + BRK);
        body.append("com.tibco.be.util.config.cdd.CacheManagerConfig cmc = (com.tibco.be.util.config.cdd.CacheManagerConfig) cc.getObjectManagement().getCacheManager();" + BRK);
        body.append("com.tibco.be.util.config.cdd.DomainObjectsConfig dosc = cmc.getDomainObjects();" + BRK);
        body.append("dvmIsEnabled = dosc.getEnableTracking(\"" + cept.getNamespace() + cept.getName() + "\");" + BRK);
        body.append("_isConfigured = true;" + BRK);
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Lock retry count : \" + lockRetryCount);" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Lock retry timeout : \" + lockTimeout);" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Lock is enabled : \" + lockIsEnabled);" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Lock is local : \" + lockIsLocal);" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Tracking metric is enabled : \" + dvmIsEnabled);" + BRK);
        body.append("}" + BRK);
        body.append("}" + BRK);
        mr.setBody(body);
    }

    private static void addMetricStaticVariables(Concept cept, JavaClassWriter cc) {
        cc.addMember( "private static final", "com.tibco.cep.kernel.service.logging.Logger", "logger", "com.tibco.cep.kernel.service.logging.LogManagerFactory.getLogManager().getLogger(\"metric.runtime.codegen\")");
        cc.addMember("private static", "boolean", "_isConfigured", "false");
        cc.addMember("private static", "long", "lockTimeout", "1000");
        cc.addMember("private static", "int", "lockRetryCount", "10");
        cc.addMember("private static", "boolean", "lockIsLocal", "true");
        cc.addMember("private static", "boolean", "lockIsEnabled", "true");
        cc.addMember("private static", "boolean", "dvmIsEnabled", "true");
    }

    protected static void addMetricMethod(Concept cept, JavaClassWriter cc, Map<String, FieldInfo> metricFieldMap, List<FieldInfo> computeOrderList) {
    	//TODO - Needs to be enabled after Timer Metric code is complete
//        boolean isRollingTimeMetric = ((ConceptAdapter) cept).isRollingTimeMetric();
    	boolean isRollingTimeMetric = false;

        getMetricExtIdMethod(cc, cept, metricFieldMap, computeOrderList);
        getDataObjectComputeInternalMethod(cc, cept, metricFieldMap, computeOrderList);
        MetricDelegateClassGeneratorSmap.getMetricComputeInternalMethod(cc, cept, isRollingTimeMetric, metricFieldMap, computeOrderList);
        MetricDelegateClassGeneratorSmap.getMetricComputeMethod(cc, cept, metricFieldMap, computeOrderList);
    	//TODO - Needs to be enabled after Timer Metric code is complete
//        if (isRollingTimeMetric) {
//        	TimerMetricDelegateClassGeneratorSmap.getMetricComputeInternalMethod(cc, cept, isArgsOversize, metricFieldMap, computeOrderList);
//        	TimerMetricDelegateClassGeneratorSmap.getMetricComputeMethod(cc, cept, isArgsOversize, metricFieldMap, computeOrderList);
//        }
        getMetricLookupMethod(cc, cept, metricFieldMap, computeOrderList);
    	getMetricDeleteMethod(cc, cept, metricFieldMap, computeOrderList);
        getMetricMethod(cc, cept, metricFieldMap, computeOrderList);
    }

    private static void getMetricExtIdMethod(JavaClassWriter cc, Concept cept, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {
        List<FieldInfo> groupByList = new ArrayList<FieldInfo>();

        MethodRecWriter mr = cc.createMethod(EMFMetricMethodModelFunction.GET_METRIC_EXT_ID_FUNCTION_NAME);
        mr.setAccess("public static");

        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isGroupBy() == true) {
                mr.addArg(CGConstants.setArgumentTypes[fi.getType()], fi.getCodegenName());
                groupByList.add(fi);
            }
        }

        StringBuilder body = new StringBuilder();
        body.append(getMetricExtId(cept, groupByList));
        body.append("return extId;");

        mr.setReturnType("java.lang.String");
        mr.setBody(body);
    }

    protected static StringBuilder getMetricExtId(Concept cept, List<FieldInfo> groupByList) {
        StringBuilder extId = new StringBuilder("StringBuilder extIdBuf = new StringBuilder();" + BRK);

        String extIdPrefix = getFSName(cept);
        int codegenPrefixIndex = extIdPrefix.indexOf(ModelNameUtil.GENERATED_PACKAGE_PREFIX);
        if (codegenPrefixIndex != -1) {
            extIdPrefix = extIdPrefix.substring(ModelNameUtil.GENERATED_PACKAGE_PREFIX.length() + 1,extIdPrefix.length());
        }
        extId.append("extIdBuf.append(\"" + extIdPrefix + "\");" + BRK);
        Collections.sort(groupByList,new GroupByPositionComparator<FieldInfo>());
        for (FieldInfo fi : groupByList) {
            extId.append("extIdBuf.append(\"" + EXTID_SEPARATOR + "\");" + BRK);
            if (fi.getType() == PROPERTY_TYPES.DATE_TIME_VALUE) {
                extId.append("extIdBuf.append(" + fi.getCodegenName() + ".getTimeInMillis());" + BRK);
            } else {
                extId.append("extIdBuf.append(" + fi.getCodegenName() + ");" + BRK);
            }
        }
        extId.append("String extId = extIdBuf.toString();" + BRK);
        return extId;
    }

    private static void getDataObjectComputeInternalMethod(JavaClassWriter cc, Concept cept, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {
        // invoke compute internal method using appropriate properties and flags
        MethodRecWriter mr = cc.createMethod(EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME);
        mr.setAccess("public static");
        StringBuilder body = new StringBuilder();
        StringBuilder extId = new StringBuilder("StringBuilder extIdBuf = new StringBuilder();" + BRK);

        String extIdPrefix = getFSName(cept);
        int codegenPrefixIndex = extIdPrefix.indexOf(ModelNameUtil.GENERATED_PACKAGE_PREFIX);
        if (codegenPrefixIndex != -1) {
            extIdPrefix = extIdPrefix.substring(ModelNameUtil.GENERATED_PACKAGE_PREFIX.length() + 1, extIdPrefix.length());
        }
        extId.append("extIdBuf.append(\"" + extIdPrefix + "\");" + BRK);
        mr.addArg(getFSName(cept) + "DVM", "dataObject");

        // body.append("return compute(");
        body.append("return " + EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME + "(");
        boolean addNextField = false;

        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem() == true) {
                continue;
            } else if (fi.getDependingField() != null) {
                FieldInfo tmp = fieldMap.get(fi.getDependingField()[0].getPropertyName());
                if (tmp.isSystem() == false)
                    continue;
            }

            if (addNextField) {
            	body.append(",");
            }
            switch (fi.getType()) {
            case (RDFTypes.STRING_TYPEID):
                // body.append("dataObject.get"+fi.codegenName+"().getString()");
                body.append("dataObject.get" + fi.getCodegenName() + "().getString(), " + "dataObject.get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.INTEGER_TYPEID):
                // body.append("dataObject.get"+fi.codegenName+"().getInt()");
                body.append("dataObject.get" + fi.getCodegenName() + "().getInt(), " + "dataObject.get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.LONG_TYPEID):
                // body.append("dataObject.get"+fi.codegenName+"().getLong()");
                body.append("dataObject.get" + fi.getCodegenName() + "().getLong(), " + "dataObject.get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.DOUBLE_TYPEID):
                // body.append("dataObject.get"+fi.codegenName+"().getDouble()");
                body.append("dataObject.get" + fi.getCodegenName() + "().getDouble(), " + "dataObject.get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.BOOLEAN_TYPEID):
                // body.append("dataObject.get"+fi.codegenName+"().getBoolean()");
                body.append("dataObject.get" + fi.getCodegenName() + "().getBoolean(), " + "dataObject.get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.DATETIME_TYPEID):
                // body.append("dataObject.get"+fi.codegenName+"().getDateTime()");
                body.append("dataObject.get" + fi.getCodegenName() + "().getDateTime(), " + "dataObject.get" + fi.getCodegenName() + "().isSet()");
                break;
            default:// TODO - Throw exception here
                break;
            }
            addNextField = true;
        }

        body.append(");" + BRK);
        mr.setReturnType(getFSName((Concept) cept));
        mr.setBody(body);
    }

    // Construct metric specific 'lookup' method
    private static void getMetricLookupMethod(JavaClassWriter cc, Concept cept, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {
        List<FieldInfo> groupByList = new ArrayList<FieldInfo>();
        MethodRecWriter mr = cc.createMethod(EMFMetricMethodModelFunction.LOOKUP_FUNCTION_NAME);
        mr.setAccess("public static");
        StringBuilder body = new StringBuilder();

        StringBuilder extId = new StringBuilder("StringBuilder extId = new StringBuilder();" + BRK);

        String extIdPrefix = getFSName(cept);
        int codegenPrefixIndex = extIdPrefix.indexOf(ModelNameUtil.GENERATED_PACKAGE_PREFIX);
        if (codegenPrefixIndex != -1) {
            extIdPrefix = extIdPrefix.substring(ModelNameUtil.GENERATED_PACKAGE_PREFIX.length() + 1, extIdPrefix.length());
        }
        extId.append("extId.append(\"" + extIdPrefix + "\");" + BRK);

        // Create extid from GB fields
        // Need to move metric field generation to outside
        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem() == true) {
                continue;
            }
            // FIX THIS - ***** Need to maintain orders to be in sync with the
            // ontology compute method
            // argument orders. - Need to add a position parameters to FieldInfo
            if (fi.isGroupBy() == true) {
                mr.addArg(CGConstants.setArgumentTypes[fi.getType()], fi.getCodegenName());
                groupByList.add(fi);
            }
        }
        Collections.sort(groupByList, new GroupByPositionComparator<FieldInfo>());
        for (FieldInfo fi : groupByList) {
            extId.append("extId.append(\"" + EXTID_SEPARATOR + "\");" + BRK);
            if (fi.getType() == PROPERTY_TYPES.DATE_TIME_VALUE) {
                extId.append("extId.append(" + fi.getCodegenName() + ".getTimeInMillis());" + BRK);
            } else {
                extId.append("extId.append(" + fi.getCodegenName() + ");" + BRK);
            }
        }

        // Lookup metric. If none found, create a new one
        body.append(extId);
        body.append(getFSName(cept) + " instance = ");
        //body.append("(" + getFSName(cept) + ") " + "com.tibco.be.functions.object.ObjectHelper.getByExtId(extId.toString());" + BRK);
        body.append("(" + getFSName(cept) + ") " + "com.tibco.be.functions.cluster.DataGridFunctions.CacheLoadConceptByExtIdByUri(extId.toString(), false, \""+cept.getNamespace() + cept.getName()+"\");" + BRK);
        body.append("return instance;" + BRK);
        mr.setReturnType(getFSName((Concept) cept));
        mr.setBody(body);
    }

    // Construct metric specific 'delete' method
    private static void getMetricDeleteMethod(JavaClassWriter cc, Concept cept, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {
        List<FieldInfo> groupByList = new ArrayList<FieldInfo>();
        MethodRecWriter mr = cc.createMethod(EMFMetricMethodModelFunction.DELETE_FUNCTION_NAME);
        mr.setAccess("public static");

        StringBuilder body = new StringBuilder();

        //invoke lookup and create the proper arguments for the delete API
        StringBuilder lookUp = new StringBuilder(getFSName(cept)+" instance = lookup(");

        // Need to move metric field generation to outside
        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem() == true) {
                continue;
            }
            // FIX THIS - ***** Need to maintain orders to be in sync with the
            // ontology compute method
            // argument orders. - Need to add a position parameters to FieldInfo
            if (fi.isGroupBy() == true) {
                mr.addArg(CGConstants.setArgumentTypes[fi.getType()], fi.getCodegenName());
                groupByList.add(fi);
            }
        }
//        Collections.sort(groupByList, new GroupByPositionComparator<FieldInfo>());
        Iterator<FieldInfo> groupByListIterator = groupByList.iterator();
        while (groupByListIterator.hasNext()) {
			FieldInfo fi = (MapperMetricUtils.FieldInfo) groupByListIterator.next();
			lookUp.append(fi.getCodegenName());
			if (groupByListIterator.hasNext() == true) {
				lookUp.append(",");
			}
		}
        lookUp.append(");"+BRK); //lookup end
        body.append(lookUp);
        body.append("if (instance != null) {" + BRK); //instance existence check
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK); //debug log metric deletion attempt
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Attempting to delete metric : \" + instance);" + BRK);
        body.append("}" + BRK); //debug log metric deletion attempt end
        //delete the metric by ext id
        body.append("instance = (" + getFSName(cept) + ") " + "com.tibco.be.functions.object.ObjectHelper.deleteByExtId(instance.getExtId());" + BRK);
        body.append("if (instance != null) {" + BRK); //if metric deletion success
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK); //debug log metric dvms deletion attempt
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Deleted metric : \" + instance);" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Attempting to delete related dvm instances : \" + instance.getId());" + BRK);
        body.append("}" + BRK); //debug log metric dvms deletion attempt
        //try to get all the DVMs for the delete metric using query
        String dvmName = cept.getFullPath()+"DVM";
        StringBuilder dvmSelectQuery = new StringBuilder("StringBuilder dvmSelectQuery = new StringBuilder(\"select A@extId from "+dvmName+" A where A."+ConceptAdapter.METRIC_DVM_PARENT_ID_NAME+" = \");" + BRK);
        dvmSelectQuery.append("dvmSelectQuery.append(instance.getId());");
        body.append(dvmSelectQuery + BRK);
        //log the query
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK); //debug log metric dvm selection query
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Executing : \" + dvmSelectQuery);" + BRK);
        body.append("}" + BRK); //debug log metric dvm selection query close
        body.append("java.util.List dvmInstancesIds = (java.util.List)com.tibco.cep.query.functions.QueryUtilFunctions.executeInDynamicQuerySession(dvmSelectQuery.toString(), null, false, -1);"+BRK);
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK); //debug log metric dvm selection query results
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Attempting to delete \" + dvmInstancesIds.size() + \" dvm instances\");" + BRK);
        body.append("}" + BRK); //debug log metric dvm selection query results close
        body.append("java.util.Iterator dvmInstancesIdsIterator = dvmInstancesIds.iterator();" + BRK);
        body.append("while (dvmInstancesIdsIterator.hasNext() == true) {" + BRK); //dvm extids iteration
        body.append("String dvmExtId = (String)dvmInstancesIdsIterator.next();" + BRK);
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK); //debug log actual metric dvm deletion attempt
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Attempting to delete metric dvm \" + dvmExtId);" + BRK);
        body.append("}" + BRK); //debug log actual metric dvm deletion attempt
        body.append(getFSName(cept) + "DVM dvmInstance = (" + getFSName(cept) + "DVM) " + "com.tibco.be.functions.object.ObjectHelper.deleteByExtId(dvmExtId);" + BRK);
        body.append("if (dvmInstance != null) {" + BRK); //if actual metric dvm deletion success
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK);  //debug log actual metric dvm deletion success
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Deleted metric DVM : \" + dvmInstance);" + BRK);
        body.append("}" + BRK); //debug log actual metric dvm deletion success end
        body.append("}" + BRK); //if actual metric dvm deletion success end
        body.append("}" + BRK); //dvm extids iteration end
        body.append("}" + BRK); //if metric deletion success end
        body.append("}" + BRK); //if instance existence check
        body.append("return instance;" + BRK);
        mr.setReturnType(getFSName((Concept) cept));
        mr.setBody(body);
    }


    static void getMetricMethod(JavaClassWriter cc, Concept cept, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {

    	MethodRecWriter mr = null;
        mr = cc.createMethod(GET_METHOD_NAME);

        mr.setAccess("public static");
        StringBuilder body = new StringBuilder();
        List<FieldInfo> groupByList = new ArrayList<FieldInfo>();

        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem() == true) {
                continue;
            }
            if (fi.isGroupBy() == true) {
                mr.addArg(CGConstants.setArgumentTypes[fi.getType()], fi.getCodegenName());
                groupByList.add(fi);
            }
        }

        StringBuilder extId = getMetricExtId(cept, groupByList);

        // Lookup metric. If none found, create a new one
        body.append(extId);
        body.append(INIT_METHOD_NAME + "();" + BRK);
        body.append("if (lockIsEnabled == true) {" + BRK);
        body.append("int lockCurrentRetryCount = 0;" + BRK);
        body.append("boolean lockStatus = false;" + BRK);
        body.append("do {" + BRK);
        body.append("lockStatus = com.tibco.be.functions.cluster.DataGridFunctions.Lock(extId, lockTimeout, lockIsLocal);" + BRK);
        body.append("lockCurrentRetryCount++;" + BRK);
        body.append("} while (lockStatus == false && lockCurrentRetryCount < lockRetryCount);" + BRK);
        body.append("if (lockStatus == false) {" + BRK);
        //body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.WARN, \"Failed to acquire lock for metric : \" + extId);" + BRK);
        body.append("throw new java.lang.RuntimeException(\"Failed to acquire lock for metric : \" + extId);" + BRK);
        body.append("}}" + BRK); // Do not use help library at this moment
        body.append(getFSName(cept) + " instance = ");
        //body.append("(" + getFSName(cept) + ") " + "com.tibco.be.functions.object.ObjectHelper.getByExtId(extId);" + BRK);
        body.append("(" + getFSName(cept) + ") " + "com.tibco.be.functions.cluster.DataGridFunctions.CacheLoadConceptByExtIdByUri(extId.toString(), false, \""+cept.getNamespace() + cept.getName()+"\");" + BRK);
        body.append("if (instance == null) {" + BRK);

        String strId = null;
        strId = CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getIdGenerator().nextEntityId(" + getFSName(cept) + ".class)";

        body.append(" instance = new " + getFSName(cept) + "(" + strId + ", extId);" + BRK);

        // populate the GB fields of the newly created metric
        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isGroupBy() == true) {
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(" + fi.getCodegenName() + ");" + BRK);
            }
        }
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"New metric instance created for : \" + extId);" + BRK);
        body.append("}" + BRK);
        body.append("}" + BRK);

        // Assert metric and dvm into rete
        body.append("try {" + BRK);
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Metric updated : \" + instance);" + BRK);
        body.append("}" + BRK);

        body.append(CGConstants.getCurrentRuleSession + ".assertObject(instance, false);" + BRK);
        body.append("} catch (" + com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException.class.getName() + " e) {" + BRK);
        body.append("throw new java.lang.RuntimeException(e.getMessage(), e);" + BRK);
        body.append("}" + BRK);
        body.append("return instance;" + BRK);
        mr.setReturnType(getFSName((Concept) cept));
        mr.setBody(body);
    }

    static class GroupByPositionComparator<F extends FieldInfo> implements Comparator<FieldInfo> {
        GroupByPositionComparator() {
        }

        public int compare(FieldInfo o1, FieldInfo o2) {
            return (int) (o1.getGroupByPosition() - o2.getGroupByPosition());
        }

        public boolean equals(Object o) {
            System.out.println("Calling equals for EvaluationOrderComparator is not supported");
            return false;
        }
    }

    protected static String getDVMSetAuditMethodName(PropertyDefinition pd) {
        return getSetMethodName(ModelNameUtil.generatedMemberName("I_" + pd.getName()));
    }

    protected static void addIsSetCheckStart(Map<String, FieldInfo> fieldMap, FieldInfo fi, StringBuilder body) {
        if (fi.isGroupBy()) {
            return;
        } else if (fi.getDependingField() != null) {
            FieldInfo tmp = fieldMap.get(fi.getDependingField()[0]
                    .getPropertyName());
            if (tmp.isSystem() == true)
                return;
            int i = 0;
            body.append("if (");
            do {
                if (i != 0)
                    body.append(" && ");
                body.append(fieldMap.get(fi.getDependingField()[i].getPropertyName()).getCodegenName() + IS_SET);
                i++;
            } while (i < fi.getDependingField().length);
            body.append(") { " + BRK);
        } else if (fi.isSystem() == true) {
            String tmp = fi.getCodegenName();
            body.append("if (" + tmp.substring(0, tmp.length() - SYSTEM_FIELD_SUFFIX.length()) + IS_SET + ") { " + BRK);
        } else {
            body.append("if (" + fi.getCodegenName() + IS_SET + ") { " + BRK);
        }
    }

    protected static void addIsSetCheckEnd(Map<String, FieldInfo> fieldMap, FieldInfo fi, StringBuilder body) {
        if (fi.isGroupBy()) {
            return;
        } else if (fi.getDependingField() != null) {
            FieldInfo tmp = fieldMap.get(fi.getDependingField()[0].getPropertyName());
            if (tmp.isSystem() == true)
                return;
            body.append(" } " + BRK);
        } else {
            body.append(" } " + BRK);
        }
    }
    
    //removed from ConceptClassGenerator and moved here
    protected static String getSetMethodName(String varName) {
        return CGConstants.SET_PREFIX + CGUtil.firstCap(varName);
    }
    //removed from ConceptClassGenerator and moved here
    protected static String getSetMethodName(PropertyDefinition pd) {
        return getSetMethodName(ModelNameUtil.generatedMemberName(pd.getName()));
    }
}
