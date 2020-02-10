package com.tibco.rta.service.transport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.InputSource;

import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.FactMessageContext;
import com.tibco.rta.common.service.RuntimeService;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.common.service.transport.http.DefaultMessageContext;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.MetricFunctionsRepository;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.RtaSchemaModelFactory;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.ActionFunctionsRepository;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.runtime.ServerConfiguration;
import com.tibco.rta.model.runtime.ServerConfigurationCollection;
import com.tibco.rta.model.serialize.impl.ModelJSONSerializer;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.query.FactResultTuple;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryResultTupleCollection;
import com.tibco.rta.query.QueryServiceDelegate;
import com.tibco.rta.runtime.model.serialize.RuntimeModelJSONDeserializer;
import com.tibco.rta.service.admin.AdminService;
import com.tibco.rta.service.heartbeat.HeartbeatService;
import com.tibco.rta.service.metric.MetricCalculationServiceDelegate;
import com.tibco.rta.service.metric.MetricIntrospectionService;
import com.tibco.rta.service.metric.MetricService;
import com.tibco.rta.service.query.QueryService;
import com.tibco.rta.service.rules.RuleService;
import com.tibco.rta.util.ChildMetricData;
import com.tibco.rta.util.IOUtils;
import com.tibco.rta.util.InvalidQueryException;
import com.tibco.rta.util.ServiceConstants;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 7/11/12 Time: 10:21 AM A delegate class which routes all service invocation calls to appropriate services. Name of operation is exactly identical to the actual service op.
 */
public class ServiceDelegate {

    Properties configProps;

    public ServiceDelegate() {
        configProps = ServiceProviderManager.getInstance().getConfiguration();
    }

    /**
     * TODO take serialization type param
     *
     * @param startStopService
     * The Actual service instance to invoke
     * @param properties
     * Optional properties required to invoke
     * @param serializedSchemaStream
     * @throws Exception
     */
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_TRANSPORT.getCategory());

    public Message saveSchema(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {
        RtaSchema schema = RtaSchemaModelFactory.getInstance().createSchema(new InputSource(serializedSchemaStream));

        if (startStopService instanceof AdminService) {
            AdminService adminService = (AdminService) startStopService;

            if (adminService.getSchema(schema.getName()) != null) {
                throw new Exception(String.format("Schema with name = [%s] already exists", schema.getName()));
            }

            adminService.saveSchema(schema);
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Admin service");
        }
        return new Message();
    }

    public Message getRules(StartStopService startStopService, Properties properties, InputStream serializedRuleStream) throws Exception {
        Message message = new Message();
        String statusCode = ServiceConstants.SUCCESS_STATUS_CODE;
        List<RuleDef> rules = null;

        if (startStopService instanceof RuleService) {
            RuleService ruleService = (RuleService) startStopService;
            try {
                rules = ruleService.getRules();
            } catch (Exception e) {
                statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                String errorMessage = String.format("Error while getting rules= [%s]\n", e);
                message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            }
            List<RuleDef> validRules = new ArrayList<RuleDef>();

            if (rules != null) {
                statusCode = ServiceConstants.SUCCESS_STATUS_CODE;
                for (RuleDef ruleDef : rules) {
                    if (!ruleDef.isStreamingQuery()) {
                        validRules.add(ruleDef);
                    }
                }

                for (int i = 0; i < validRules.size(); i++) {
                    message.getMessageProperties().setProperty(ServiceConstants.RULENAME + i, validRules.get(i).getName());
                }
            }

        } else {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = "Illegal Service. Expecting Rule service";
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
        }
        message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        return message;
    }

    public Message getAllRuleDefs(StartStopService startStopService, Properties properties, InputStream serializedRuleStream) throws Exception {
        Message message = new Message();
        String statusCode = ServiceConstants.SUCCESS_STATUS_CODE;

        if (startStopService instanceof RuleService) {
            RuleService ruleService = (RuleService) startStopService;
            List<RuleDef> rules = ruleService.getRules();
            List<RuleDef> validRules = new ArrayList<RuleDef>();

            if (rules != null) {
                for (RuleDef ruleDef : rules) {
                    if (!ruleDef.isStreamingQuery()) {
                        validRules.add(ruleDef);
                    }
                }

                statusCode = ServiceConstants.SUCCESS_STATUS_CODE;
                String rulesString = SerializationUtils.serializeRuleDefs(validRules);
                message.setPayload(rulesString);
            }
        } else {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = "Illegal Service. Expecting Rule service";
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
        }
        message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        return message;
    }

    /**
     * TODO take serialization type param
     *
     * @param startStopService     The Actual service instance to invoke
     * @param properties           Optional properties required to invoke
     * @param serializedRuleStream
     * @throws Exception
     */
    public Message createRule(StartStopService startStopService, Properties properties, InputStream serializedRuleStream) throws Exception {
        RuleDef ruleDef = SerializationUtils.deserializeRule(new InputSource(serializedRuleStream));

        Message message = new Message();
        String statusCode = ServiceConstants.SUCCESS_STATUS_CODE;

        if (startStopService instanceof RuleService) {
            RuleService ruleService = (RuleService) startStopService;

            if (ruleService.getRuleDef(ruleDef.getName()) != null) {
                statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                String errorMessage = String.format("Rule with name = [%s] already exists", ruleDef.getName());
                message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            }
            try {
                ruleService.createRule(ruleDef);
            } catch (Exception e) {
                statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                String errorMessage = String.format("Error while creating rule= [%s]\n" + e, ruleDef.getName());
                message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            }
        } else {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = "Illegal Service. Expecting Rule service";
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
        }
        message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        return message;
    }

    /**
     * TODO take serialization type param
     *
     * @param startStopService     The Actual service instance to invoke
     * @param properties           Optional properties required to invoke
     * @param serializedRuleStream
     * @throws Exception
     */
    public Message updateRule(StartStopService startStopService, Properties properties, InputStream serializedRuleStream) throws Exception {
        RuleDef ruleDef;
        ruleDef = SerializationUtils.deserializeRule(new InputSource(serializedRuleStream));

        String statusCode = ServiceConstants.SUCCESS_STATUS_CODE;
        Message message = new Message();

        if (startStopService instanceof RuleService) {
            RuleService ruleService = (RuleService) startStopService;
            RuleDef previousRule = ruleService.getRuleDef(ruleDef.getName());

            if (previousRule == null) {
                statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                String errorMessage = String.format("Rule with name = [%s] does not exist", ruleDef.getName());
                message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            } else if (previousRule.getVersion().equals(ruleDef.getVersion())) {
                statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                String errorMessage = String.format("Rule with name = [%s] with version [%s] already exits", ruleDef.getName(), ruleDef.getVersion());
                message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            } else {
                statusCode = ServiceConstants.SUCCESS_STATUS_CODE;
                try {
                    ruleService.updateRule(ruleDef);
                    String successMessage = String.format("Rule with name = [%s] updated to version [%s]", ruleDef.getName(), ruleDef.getVersion());
                    message.getMessageProperties().setProperty(ServiceConstants.SUCCESS, successMessage);
                } catch (Exception e) {
                    statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                    String errorMessage = String.format("Error while updating rule= [%s]\n" + e, ruleDef.getName());
                    message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
                }
            }
        } else {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = "Illegal Service. Expecting Rule service";
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
        }

        message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        return message;
    }

    /**
     * TODO take serialization type param
     *
     * @param startStopService     The Actual service instance to invoke
     * @param properties           Optional properties required to invoke
     * @param serializedRuleStream
     * @throws Exception
     */
    public Message deleteRule(StartStopService startStopService, Properties properties, InputStream serializedRuleStream) throws Exception {
        Message message = new Message();
        String statusCode = ServiceConstants.SUCCESS_STATUS_CODE;

        if (startStopService instanceof RuleService) {
            RuleService ruleService = (RuleService) startStopService;
            String ruleName = properties.getProperty(ServiceConstants.RULENAME);
            try {
                ruleService.deleteRule(ruleName);
                String successMessage = String.format("Rule with name = [%s] deleted", ruleName);
                message.getMessageProperties().setProperty(ServiceConstants.SUCCESS, successMessage);
            } catch (Exception e) {
                statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                String errorMessage = String.format("Error while deleting rule= [%s]\nException= [%s] ", ruleName, e);
                message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            }

        } else {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = "Illegal Service. Expecting Rule service";
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
        }

        message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        return message;
    }

    /**
     * TODO take serialization type param
     *
     * @param startStopService     The Actual service instance to invoke
     * @param properties           Optional properties required to invoke
     * @param serializedRuleStream
     * @throws Exception
     */
    public Message closeSession(StartStopService startStopService, Properties properties, InputStream serializedRuleStream) throws Exception {
        if (startStopService instanceof ServerSessionRegistry) {
            String sessionId = properties.getProperty(ServiceConstants.SESSION_ID);
            ServerSessionRegistry sessionRegistry = (ServerSessionRegistry) startStopService;
            sessionRegistry.removeServerSession(sessionId);
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Session service");
        }
        return new Message();
    }

    /**
     * TODO take serialization type param
     *
     * @param startStopService       The Actual service instance to invoke
     * @param properties             Optional properties required to invoke
     * @param serializedSchemaStream
     * @throws Exception
     */
    public Message removeSchema(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {
        if (startStopService instanceof AdminService) {
            AdminService adminService = (AdminService) startStopService;
            String schemaName = properties.getProperty(ServiceConstants.SCHEMANAME);
            adminService.removeSchema(schemaName);
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Admin service");
        }
        return new Message();
    }

    public <C extends FactMessageContext> Message assertFacts(StartStopService startStopService, Properties properties, C messageContext, List<Fact> facts) throws Exception {
        Message message = new Message();
        MetricCalculationServiceDelegate metricServiceDelegate;

        if (startStopService instanceof MetricService) {
            metricServiceDelegate = MetricCalculationServiceDelegate.INSTANCE;
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Aggregation service");
        }

        String publisherSessionId = properties.getProperty(ServiceConstants.SESSION_ID);

        List<String> transactionIds = new ArrayList<String>(facts.size());

        String useBatchingStr = (String) ConfigProperty.RTA_TRANSACTION_USE_BATCHING.getValue(configProps);
        boolean useBatching = useBatchingStr == null || useBatchingStr.equals("true");
        // Set batch size in message context.
        if (messageContext != null) {
            messageContext.setBatchSize(facts.size());
        }

        if (!useBatching) {
            for (Fact fact : facts) {
                TransactionEvent transactionEvent = metricServiceDelegate.assertFact(publisherSessionId, messageContext, fact);
                transactionIds.add(transactionEvent.getTransactionId());
            }
        } else {
            TransactionEvent transactionEvent = metricServiceDelegate.assertFact(publisherSessionId, messageContext, facts);
            transactionIds.add(transactionEvent.getTransactionId());
        }
        String transIds = StringUtils.join(transactionIds, ',');
        message.addProperty("transactionIds", transIds);
        return message;
    }

    /**
     * @param startStopService The Actual service instance to invoke
     * @param serializedFacts
     * @throws Exception
     */
    public <C extends FactMessageContext> Message assertFactBytes(StartStopService startStopService, Properties properties, C messageContext, byte[] serializedFacts) throws Exception {
//		List<Fact> facts = new RuntimeModelJSONDeserializer().deserializeFacts(serializedFacts);
//        List<Fact> facts = new RuntimeModelBytesCustomDeserializer().deserializeFacts(serializedFacts);
        return null;
//		return assertFact(startStopService, properties, messageContext, facts);
    }


    public Message assertFact(StartStopService startStopService,
                              Properties properties,
                              InputStream serializedFactStream) throws Exception {
        List<Fact> facts = new RuntimeModelJSONDeserializer().deserializeFacts(new InputSource(serializedFactStream));
        return assertFacts(startStopService, properties, new DefaultMessageContext(System.currentTimeMillis()), facts);
    }

    /**
     * TODO take serialization type param
     *
     * @param startStopService       The Actual service instance to invoke
     * @param serializedSchemaStream
     * @throws Exception
     */
    public Message getSchema(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {
        RtaSchema requiredSchema;
        String schemaName = properties.getProperty(ServiceConstants.SCHEMANAME);

        if (startStopService instanceof AdminService) {
            AdminService adminService = (AdminService) startStopService;
            requiredSchema = adminService.getSchema(schemaName);
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Admin service");
        }

        Message message = new Message();
        if (requiredSchema == null) {
            message.addProperty(ServiceConstants.ERROR, String.format("Schema name [%s] not found", schemaName));
        } else {
            message.setPayload(SerializationUtils.serializeSchema(requiredSchema));
        }
        return message;
    }


    public Message getAllActionFunctionDescriptors(StartStopService startStopService, Properties properties, InputStream serializedFunctionStream) throws Exception {
        Collection<ActionFunctionDescriptor> ads = ActionFunctionsRepository.INSTANCE.getActionFunctionDescriptors();

        Message message = new Message();
        if (ads != null) {
            message.setPayload(SerializationUtils.serializeAllActionDesc(ads));
        }
        return message;
    }


    public Message getAllMetricFunctionDescriptors(StartStopService startStopService, Properties properties, InputStream serializedFunctionStream) throws Exception {
        List<MetricFunctionDescriptor> mfds = MetricFunctionsRepository.INSTANCE.getMetricFunctionDescriptors();

        Message message = new Message();
        if (mfds != null) {
            message.setPayload(SerializationUtils.serializeAllFunctionDesc(mfds));
        }
        return message;
    }


    public Message getMetricFunctionDescriptorForMeasurement(StartStopService startStopService,
                                                             Properties properties,
                                                             InputStream serializedFunctionStream) throws Exception {
        MetricFunctionDescriptor mfd;
        if (startStopService instanceof AdminService) {
            AdminService adminService = (AdminService) startStopService;
            String measurementName = properties.getProperty(ServiceConstants.MEASUREMENTNAME);
            String schemaName = properties.getProperty(ServiceConstants.SCHEMANAME);
            RtaSchema schema = adminService.getSchema(schemaName);
            Measurement measurement = schema.getMeasurement(measurementName);
            mfd = measurement.getMetricFunctionDescriptor();
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Rule service");
        }

        Message message = new Message();
        if (mfd != null) {
            message.setPayload(SerializationUtils.serialize(mfd));
        }
        return message;
    }

    /**
     * TODO take serialization type param
     *
     * @param startStopService     The Actual service instance to invoke
     * @param serializedRuleStream
     * @throws Exception
     */
    public Message getRule(StartStopService startStopService, Properties properties, InputStream serializedRuleStream) throws Exception {
        RuleDef requiredRule = null;
        Message message = new Message();
        String statusCode = ServiceConstants.SUCCESS_STATUS_CODE;

        if (startStopService instanceof RuleService) {
            RuleService ruleService = (RuleService) startStopService;
            String ruleName = properties.getProperty(ServiceConstants.RULENAME);
            try {
                requiredRule = ruleService.getRuleDef(ruleName);
            } catch (Exception e) {
                statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                String errorMessage = String.format("Error while getting rule= [%s]\n" + e, ruleName);
                message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            }
        } else {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = "Illegal Service. Expecting Rule service";
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
        }

        if (requiredRule != null) {
            String ruleXML = SerializationUtils.serializeRule(requiredRule);
            message.setPayload(ruleXML);
        }
        message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        return message;
    }


    public Message registerQuery(StartStopService startStopService, Properties properties, InputStream serializedQueryDefStream) throws Exception {
        QueryDef queryDef;
        QueryServiceDelegate queryServiceDelegate;

        String publisherSessionId = properties.getProperty(ServiceConstants.SESSION_ID);
        Message message = new Message();

        if (startStopService instanceof QueryService) {
            queryDef = SerializationUtils.deserializeQuery(new InputSource(serializedQueryDefStream));
            queryServiceDelegate = QueryServiceDelegate.INSTANCE;
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Query service");
        }

        try {
            String correlationId = queryServiceDelegate.registerQuery(publisherSessionId, queryDef);
            // Will be null for streaming queries
            if (correlationId != null) {
                message.addProperty(ServiceConstants.BROWSER_ID, correlationId);
            }
        } catch (Exception e) {
            message.addProperty(ServiceConstants.ERROR, e.getMessage());
        }

        return message;
    }


    public Message unregisterQuery(StartStopService startStopService, Properties properties, InputStream serializedQueryDefStream) throws Exception {
        String queryName;
        QueryServiceDelegate queryServiceDelegate;

        String publisherSessionId = properties.getProperty(ServiceConstants.SESSION_ID);

        if (startStopService instanceof QueryService) {
            queryName = properties.getProperty(ServiceConstants.QUERY_NAME);
            queryServiceDelegate = QueryServiceDelegate.INSTANCE;
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Query service");
        }

        QueryDef queryDef = queryServiceDelegate.unregisterQuery(publisherSessionId, queryName);
        Message message = new Message();
        // Will be null for streaming queries
        if (queryDef != null) {
            message.addProperty(ServiceConstants.QUERY_NAME, queryName);
        } else {
            message.addProperty(ServiceConstants.ERROR, String.format("No query matching name [%s] found", queryName));
        }
        return message;
    }


    public Message hasNextResult(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {
        boolean available = false;
        QueryServiceDelegate queryServiceDelegate;
        Message message = new Message();
        String statusCode;


        try {

            if (startStopService instanceof QueryService) {
                String browserCorId = properties.getProperty(ServiceConstants.BROWSER_ID);
                queryServiceDelegate = QueryServiceDelegate.INSTANCE;
                available = queryServiceDelegate.hasNext(browserCorId);
            }
        } catch (InvalidQueryException e) {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = e.getMessage();
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        }
        message.addProperty(ServiceConstants.AVAILABLE, Boolean.toString(available));
        return message;
    }


    public Message nextResult(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {
        List<QueryResultTuple> resultTuples = null;
        QueryServiceDelegate queryServiceDelegate;
        Message message = new Message();
        String statusCode;

        try {
            if (startStopService instanceof QueryService) {
                queryServiceDelegate = QueryServiceDelegate.INSTANCE;
                String browserCorId = properties.getProperty(ServiceConstants.BROWSER_ID);
                resultTuples = queryServiceDelegate.getNext(browserCorId);
            }
        } catch (InvalidQueryException e) {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = e.getMessage();
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        }
        if (resultTuples != null) {
            QueryResultTupleCollection tupleCollection = new QueryResultTupleCollection(resultTuples);
            String payload = ModelJSONSerializer.INSTANCE.serializeQueryResults(tupleCollection);
            message.setPayload(payload);
        }
        return message;
    }


    public Message stopBrowser(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {
        boolean available = false;

        String publisherSessionId = properties.getProperty(ServiceConstants.SESSION_ID);

        if (startStopService instanceof QueryService) {
            String browserCorId = properties.getProperty(ServiceConstants.BROWSER_ID);
            QueryServiceDelegate queryServiceDelegate = QueryServiceDelegate.INSTANCE;
            queryServiceDelegate.removeBrowserMapping(publisherSessionId, browserCorId);
        } else if (startStopService instanceof MetricIntrospectionService) {
            String browserCorId = properties.getProperty(ServiceConstants.BROWSER_ID);
            MetricIntrospectionService metricIntrospectionService = (MetricIntrospectionService) startStopService;
            metricIntrospectionService.removeBrowserMapping(browserCorId);
        }
        return new Message();
    }


    public Message getMetricChildFactsBrowser(StartStopService startStopService, Properties properties, InputStream serializedMetricStream) throws Exception {
        MetricIntrospectionService metricIntrospectionService;
        ChildMetricData childMetricData = null;

        if (startStopService instanceof MetricIntrospectionService) {
            metricIntrospectionService = (MetricIntrospectionService) startStopService;
            Object object = IOUtils.deserialize(serializedMetricStream);
            if (object instanceof Metric) {
                Metric metric = (Metric) object;
                childMetricData = new ChildMetricData(metric, null);
            } else if (object instanceof ChildMetricData) {
                childMetricData = (ChildMetricData) object;
            }
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Metric introspection service");
        }
        String correlationId = metricIntrospectionService.getConstituentFactsBrowser(childMetricData.getMetric(), childMetricData.getOrderList());
        Message message = new Message();

        if (correlationId != null) {
            message.addProperty(ServiceConstants.BROWSER_ID, correlationId);
        }
        return message;
    }


    public Message getChildMetricsBrowser(StartStopService startStopService, Properties properties, InputStream serializedMetricStream) throws Exception {
        ChildMetricData childMetricData = null;
        MetricIntrospectionService metricIntrospectionService;

        if (startStopService instanceof MetricIntrospectionService) {
            metricIntrospectionService = (MetricIntrospectionService) startStopService;
            Object object = IOUtils.deserialize(serializedMetricStream);

            if (object instanceof Metric) {
                Metric metric = (Metric) object;
                childMetricData = new ChildMetricData(metric, null);
            } else if (object instanceof ChildMetricData) {
                childMetricData = (ChildMetricData) object;
            }
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Metric introspection service");
        }
        String correlationId = metricIntrospectionService.getChildMetricBrowser(childMetricData.getMetric(), childMetricData.getOrderList());
        Message message = new Message();

        if (correlationId != null) {
            message.addProperty(ServiceConstants.BROWSER_ID, correlationId);
        }
        return message;
    }


    public Message hasNextChild(StartStopService startStopService, Properties properties, InputStream serializedMetricStream) throws Exception {

        MetricIntrospectionService metricIntrospectionService;
        boolean available;

        if (startStopService instanceof MetricIntrospectionService) {
            metricIntrospectionService = (MetricIntrospectionService) startStopService;
            String browserCorId = properties.getProperty(ServiceConstants.BROWSER_ID);
            available = metricIntrospectionService.hasNext(browserCorId);
        } else {
            throw new IllegalArgumentException("Illegal Service. Expecting Metric introspection service");
        }
        Message message = new Message();
        message.addProperty(ServiceConstants.AVAILABLE, Boolean.toString(available));
        return message;
    }


    public Message nextChild(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {

        MetricIntrospectionService metricIntrospectionService;
        Object nextChild = null;

        if (startStopService instanceof MetricIntrospectionService) {
            metricIntrospectionService = (MetricIntrospectionService) startStopService;
            String browserCorId = properties.getProperty(ServiceConstants.BROWSER_ID);
            nextChild = metricIntrospectionService.next(browserCorId);
        }
        Message message = new Message();
        QueryResultTupleCollection resultTupleCollection = null;

        if (nextChild instanceof MetricResultTuple) {
            MetricResultTuple metricResultTuple = (MetricResultTuple) nextChild;
            QueryResultTuple resultTuple = new QueryResultTuple();
            resultTuple.setMetricResultTuple(metricResultTuple);

            resultTupleCollection = new QueryResultTupleCollection<QueryResultTuple>(resultTuple);
        } else if (nextChild instanceof FactImpl) {
            FactImpl fact = (FactImpl) nextChild;
            FactResultTuple resultTuple = new FactResultTuple((FactKeyImpl) fact.getKey(), fact.getAttributes());
            resultTupleCollection = new QueryResultTupleCollection<FactResultTuple>(resultTuple);
        }
        String payload = ModelJSONSerializer.INSTANCE.serializeQueryResults(resultTupleCollection);
        message.setPayload(payload);
        return message;
    }


    public Message processHeartbeat(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {

        HeartbeatService heartbeatService;

        if (startStopService instanceof HeartbeatService) {
            heartbeatService = (HeartbeatService) startStopService;
            String sessionId = properties.getProperty(ServiceConstants.SESSION_ID);
            heartbeatService.processHeartbeat(sessionId);
        }
        return new Message();
    }


    public Message getRuntimeConfiguration(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {
        RuntimeService runtimeService = (RuntimeService) startStopService;
        Collection<ServerConfiguration> configurations = runtimeService.getRuntimeConfiguration();
        Message message = new Message();
        String serialized = ModelJSONSerializer.INSTANCE.serializeConfig(new ServerConfigurationCollection(configurations));
        message.setPayload(serialized);
        return message;
    }

    public Message clearAlerts(StartStopService startStopService, Properties properties, InputStream serializedQueryDefStream) throws Exception {
        Message message = new Message();
        String statusCode = ServiceConstants.SUCCESS_STATUS_CODE;
        QueryDef queryDef;
        if (startStopService instanceof RuleService) {
            RuleService ruleService = (RuleService) startStopService;
            queryDef = SerializationUtils.deserializeQuery(new InputSource(serializedQueryDefStream));

            try {
                ruleService.clearAlerts(queryDef);
                message.getMessageProperties().setProperty(ServiceConstants.SUCCESS, "Alerts deleted with specified ids");
            } catch (Exception e) {
                statusCode = ServiceConstants.FAILURE_STATUS_CODE;
                String errorMessage = String.format("Error while clearing alerts \nException= [%s] ", e);
                message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
            }

        } else {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = "Illegal Service. Expecting Rule service";
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
        }

        message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        return message;
    }

    public Message getAllSchemas(StartStopService startStopService, Properties properties, InputStream serializedSchemaStream) throws Exception {
        Message message = new Message();
        String statusCode = ServiceConstants.SUCCESS_STATUS_CODE;

        if (startStopService instanceof AdminService) {
            AdminService adminService = (AdminService) startStopService;
            Collection<RtaSchema> schemas = adminService.getAllSchemas();
            statusCode = ServiceConstants.SUCCESS_STATUS_CODE;
            String schemaString = SerializationUtils.serializeSchemas(new ArrayList<RtaSchema>(schemas));
            message.setPayload(schemaString);
        } else {
            statusCode = ServiceConstants.FAILURE_STATUS_CODE;
            String errorMessage = "Illegal Service. Expecting Admin Service";
            message.getMessageProperties().setProperty(ServiceConstants.ERROR, errorMessage);
        }
        message.getMessageProperties().setProperty(ServiceConstants.STATUS_CODE, statusCode);
        return message;
    }
}
