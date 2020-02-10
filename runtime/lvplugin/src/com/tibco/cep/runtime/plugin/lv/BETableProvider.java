package com.tibco.cep.runtime.plugin.lv;

import com.streambase.liveview.client.*;
import com.streambase.liveview.server.event.AbstractTupleEvent;
import com.streambase.liveview.server.event.BeginDeleteEvent;
import com.streambase.liveview.server.event.BeginSnapshotEvent;
import com.streambase.liveview.server.event.EndDeleteEvent;
import com.streambase.liveview.server.event.EndSnapshotEvent;
import com.streambase.liveview.server.event.EventListener;
import com.streambase.liveview.server.event.QueryExceptionEvent;
import com.streambase.liveview.server.event.TupleAddedEvent;
import com.streambase.liveview.server.event.TupleRemovedEvent;
import com.streambase.liveview.server.query.QueryModel;
import com.streambase.liveview.server.table.CatalogedTable;
import com.streambase.liveview.server.table.Table;
import com.streambase.liveview.server.table.plugin.TableNameMapper;
import com.streambase.liveview.server.table.plugin.TableProvider;
import com.streambase.liveview.server.table.plugin.TableProviderControl;
import com.streambase.liveview.server.table.plugin.TableProviderParameters;
import com.streambase.sb.CompleteDataType;
import com.streambase.sb.Schema;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.studio.core.adapters.EventPropertyDefinitionAdapter;
import com.tibco.cep.util.ResourceManager;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Ameya
 * Date: 10/7/14
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class BETableProvider implements TableProvider {

    //RuleSession ruleSession;
    //TypeManager typeManager;
    //static final Logger LOG = LogManagerFactory.getLogManager().getLogger(BETableProvider.class);
    String traFile, cddFile,repoUrl, processingUnit;
    RuleServiceProvider provider;
    /*
    Debug mode mostly be useless due to incompatibility between logging of LV and BE. But, still providing option for now
     */
    final boolean DEBUG = false;
    Properties env;

    private String clusterName;
    private static final String CLUSTER_NAME = "cluster.name";
    private static final String LIVEVIEW_GROUPNAME = "liveview.groupname";
    protected String liveViewGroupName;

    final String TRA_CONFIG = "be.bootstrap.property.file";
    final String CDD_CONFIG = "com.tibco.be.config.path";
    final String REPO_CONFIG = "tibco.repourl";
    final String UINT_CONFIG = "com.tibco.be.config.unit.id";

    private static final String LISTEN_URL = "listen.url";
    private static final String DISCOVERY_URL = "discovery.url";
    private static final String MEMBER_NAME = "member.name";

    protected static final EnumSet<LiveViewTableCapability> TABLE_CAPABILITIES = EnumSet.of(
            LiveViewTableCapability.SNAPSHOT,
            LiveViewTableCapability.CONTINUOUS,
            LiveViewTableCapability.DELETE,
            LiveViewTableCapability.PUBLISH,
            LiveViewTableCapability.AGGREGATE_QUERIES
    );

    protected static final EnumSet<LiveViewQueryLanguage> TABLE_QUERYLANGUAGES = EnumSet.of(LiveViewQueryLanguage.OTHER);

    private TableNameMapper mapper;
    private TableProviderParameters parameters;
    private TableProviderControl helper;

    protected static CatalogedTable catalogedTable;

    Map <String, PropertyDefinition> NAME_TO_PROP = new ConcurrentHashMap<>();

    @Override
    public void initialize(String id, TableProviderControl helper,
                           TableProviderParameters parameters, TableNameMapper mapper)
            throws LiveViewException {
        this.helper = helper;
        this.parameters = parameters;
        this.mapper = mapper;

        env = new Properties();
        clusterName = parameters.getString(CLUSTER_NAME, null);
        liveViewGroupName = parameters.getString(LIVEVIEW_GROUPNAME, clusterName);

        traFile = parameters.getString(TRA_CONFIG, null);
        cddFile = parameters.getString(CDD_CONFIG, null);
        repoUrl = parameters.getString(REPO_CONFIG, null);
        processingUnit = parameters.getString(UINT_CONFIG, null);

        env.put(REPO_CONFIG, repoUrl);
        env.put(TRA_CONFIG, traFile);
        env.put(CDD_CONFIG, cddFile);
        env.put(UINT_CONFIG, processingUnit);

        if(DEBUG)
            env.put("com.tibco.cep.debugger.service.enabled", Boolean.TRUE.toString());

//        Thread t1 = new Thread(new Runnable() {
//
//            public void run() {
                try {
                    System.out.println("Starting BE cluster");
                    RuleServiceProviderManager providerManager = RuleServiceProviderManager.getInstance();
                    System.out.println("env = " + env);
                    provider = providerManager.newProvider("LVBEConfig", env);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("BE Cluster About to start");
                RuleServiceProviderManager.getInstance().setDefaultProvider(provider);
                ResourceManager manager = ResourceManager.getInstance();
//                manager.addResourceBundle("com.tibco.cep.container.messages", provider.getLocale());
                try {

                    provider.initProject();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//            }
//        }); t1.start();
//        try {
//            t1.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }



    @Override
    public void shutdown() {
        // TODO Auto-generated method stub

    }



    @Override
    public void start() throws LiveViewException, InterruptedException {

        try {
            provider.configure(RuleServiceProvider.MODE_CLUSTER);
            while (((RuleServiceProviderImpl) provider).getStatus() != 15) {
                Thread.sleep(10);
            }
            System.out.println("BE Cluster is ready to fire queries");
            //LOG.log(Level.INFO,"BE Cluster is ready to fire queries");
            clusterName = provider.getCluster().getClusterName();

            if (liveViewGroupName == null) {
                liveViewGroupName = clusterName;
            }

            Collection conceptsScorecard= provider.getProject().getOntology().getConcepts();
            Collection events= provider.getProject().getOntology().getEvents();

            for(Object e : events) {
                String eveName = ((com.tibco.cep.designtime.model.event.Event)e).getName();
                if(eveName != null) {
                    CatalogedTable cTable = createCatalogedTable(e, eveName);
                    if (cTable != null) {
                        helper.insert(cTable);
                    }
                }
            }

            for(Object c : conceptsScorecard) {
                if(c instanceof com.tibco.cep.designtime.model.element.Concept && !((ConceptAdapter)c).isAScorecard()) {
                    String conceptName = ((com.tibco.cep.designtime.model.element.Concept)c).getName();
                    if(conceptName != null) {
                        CatalogedTable cTable = createCatalogedTable(c, conceptName);
                        if (cTable != null) {
                            helper.insert(cTable);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Schema.Field> parseConceptProperties(List fieldDefs){
        List<Schema.Field> fields = new ArrayList<Schema.Field>(fieldDefs.size());
        for(Object o : fieldDefs){
            PropertyDefinition fieldDef = (PropertyDefinition) o;
            String fieldName = fieldDef.getName();
            NAME_TO_PROP.put(fieldName, fieldDef);
            //FieldType type = fieldDef.getClass().getTypeParameters();
            CompleteDataType completeType;

            boolean isArray = fieldDef.isArray();
            switch (fieldDef.getType()) {
                //            case BLOB:
                //                completeType = CompleteDataType.forBlob();
                //                break;
                case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                    completeType = isArray ? CompleteDataType.forBoolList() : CompleteDataType.forBool();
                    break;
                //            case CHAR:
                //            case SHORT:
                case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                    completeType = isArray ? CompleteDataType.forIntList() : CompleteDataType.forInt();
                    break;
                case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                    completeType = isArray ? CompleteDataType.forTimestampList() : CompleteDataType.forTimestamp();
                    break;
                //            case FLOAT:
                case PropertyDefinition.PROPERTY_TYPE_REAL:
                    completeType = isArray? CompleteDataType.forDoubleList() : CompleteDataType.forDouble();
                    break;
                case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                case PropertyDefinition.PROPERTY_TYPE_LONG:
                    completeType = isArray ? CompleteDataType.forLongList() : CompleteDataType.forLong();
                    break;
                case PropertyDefinition.PROPERTY_TYPE_STRING:
                    completeType = isArray? CompleteDataType.forStringList() : CompleteDataType.forString();
                    break;


                default:
                    throw new RuntimeException("Unrecognized Data Type from BE: " + fieldDef.getType());
            }
            fields.add(new Schema.Field(fieldName, completeType));
        }
        return  fields;
    }

    public List<Schema.Field> parseEventProperites( List fieldDefs) {

        List<Schema.Field> fields = new ArrayList<Schema.Field>(fieldDefs.size());
        for(Object o : fieldDefs){
            EventPropertyDefinition fieldDef = (EventPropertyDefinitionAdapter) o;
            String fieldName = fieldDef.getPropertyName();
            //NAME_TO_PROP.put(fieldName, fieldDef);
            //FieldType type = fieldDef.getClass().getTypeParameters();
            CompleteDataType completeType;

            //boolean isArray = fieldDef.isArray();
            if(fieldDef.getType().equals((RDFTypes.STRING)))
                completeType =  CompleteDataType.forString();
            else if(fieldDef.getType().equals((RDFTypes.BOOLEAN)))
                completeType = CompleteDataType.forBool();
            else if(fieldDef.getType().equals((RDFTypes.INTEGER)))
                completeType = CompleteDataType.forInt();
            else if(fieldDef.getType().equals((RDFTypes.DATETIME)))
                completeType = CompleteDataType.forTimestamp();
            else if(fieldDef.getType().equals((RDFTypes.DOUBLE)))
                completeType = CompleteDataType.forDouble();
            else if(fieldDef.getType().equals((RDFTypes.LONG)))
                completeType = CompleteDataType.forLong();
            else
                throw new RuntimeException("Unrecognized Data Type from BE: " + fieldDef.getType());

//                switch (fieldDef.getType()) {
//                //            case BLOB:
//                //                completeType = CompleteDataType.forBlob();
//                //                break;
//                case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
//                    completeType = isArray ? CompleteDataType.forBoolList() : CompleteDataType.forBool();
//                    break;
//                //            case CHAR:
//                //            case SHORT:
//                case PropertyDefinition.PROPERTY_TYPE_INTEGER:
//                    completeType = isArray ? CompleteDataType.forIntList() : CompleteDataType.forInt();
//                    break;
//                case PropertyDefinition.PROPERTY_TYPE_DATETIME:
//                    completeType = isArray ? CompleteDataType.forTimestampList() : CompleteDataType.forTimestamp();
//                    break;
//                //            case FLOAT:
//                case PropertyDefinition.PROPERTY_TYPE_REAL:
//                    completeType = isArray? CompleteDataType.forDoubleList() : CompleteDataType.forDouble();
//                    break;
//                case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
//                case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
//                case PropertyDefinition.PROPERTY_TYPE_LONG:
//                    completeType = isArray ? CompleteDataType.forLongList() : CompleteDataType.forLong();
//                    break;
//                case PropertyDefinition.PROPERTY_TYPE_STRING:
//                    completeType = isArray? CompleteDataType.forStringList() : CompleteDataType.forString();
//                    break;
//
//
//                default:
//                    throw new RuntimeException("Unrecognized Data Type from BE: " + fieldDef.getType());
//            }
            fields.add(new Schema.Field(fieldName, completeType));
        }
        return  fields;

    }

    public CatalogedTable createCatalogedTable(Object eventConcept, String name) {

        CatalogedTable cTable = new CatalogedTable(name);
        cTable.setCapabilities(TABLE_CAPABILITIES);
        cTable.setEnabled(true);
        cTable.setGroup(liveViewGroupName);
        cTable.setQueryLanguages(TABLE_QUERYLANGUAGES);
        cTable.setRequiredClientCapabilities(LiveViewClientCapability.REQ_CLIENT_CAPABILITIES_COMPLEX_KEYS_ONLY);

        List fieldDefs = new ArrayList();
        List<Schema.Field> fields = new ArrayList<Schema.Field>(fieldDefs.size());

        if (eventConcept instanceof com.tibco.cep.designtime.model.event.Event) {
            fieldDefs = ((com.tibco.cep.designtime.model.event.Event)eventConcept).getAllUserProperties();
            fields = parseEventProperites(fieldDefs);
        }

        else {

            if (eventConcept instanceof com.tibco.cep.designtime.model.element.Concept) {
                fieldDefs = ((com.tibco.cep.designtime.model.element.Concept)eventConcept).getAllPropertyDefinitions();
                fields = parseConceptProperties(fieldDefs);
            }
        }

        //List<Schema.Field> fields = new ArrayList<Schema.Field>(fieldDefs.size());

        cTable.setSchema(new Schema(name + "Schema", fields));
        List <String> keyFields = new ArrayList<String>();
        keyFields.add("id");


//		if (eventConcept instanceof com.tibco.cep.designtime.model.event.Event) {
//			keyFields.add(((com.tibco.cep.designtime.model.event.Event)eventConcept).getGUID());
//		}
//
//		else {
//			if (eventConcept instanceof com.tibco.cep.designtime.model.element.Concept) {
//				keyFields.add(((com.tibco.cep.designtime.model.element.Concept)eventConcept).getGUID());
//			}
//		}
        cTable.setKeyFields(keyFields);

        BETable table = new BETable(this, eventConcept, cTable);
        String objectName = null;
        if (eventConcept instanceof com.tibco.cep.designtime.model.event.Event) {
            objectName = ((com.tibco.cep.designtime.model.event.Event)eventConcept).getName();
            System.out.println("Creating LiveView table from BE Event "+objectName);
            //LOG.log(Level.INFO,"Creating LiveView table from BE Event %s",objectName);
        }

        else {
            if (eventConcept instanceof com.tibco.cep.designtime.model.element.Concept) {
                objectName = ((com.tibco.cep.designtime.model.element.Concept)eventConcept).getName();
                System.out.println("Creating LiveView table from BE Concept "+objectName);
                //LOG.log(Level.INFO,"Creating LiveView table from BE Concept %s",objectName);
            }
        }
        cTable.setRuntimeTable(table).setGroup("BE").setTableSpaceRef(objectName);


        return cTable;
    }

    public static TableProviderControl makeHelper() {
        TableProviderControl helper1 = new TableProviderControl() {

            @Override
            public void upsert(CatalogedTable catalogedTable) {
            }

            @Override
            public void update(CatalogedTable catalogedTable) {
            }

            @Override
            public void shutMeDown() {
            }

            @Override
            public void disableAllAndRestart() {
            }

            @Override
            public void setEnabled(String tableId, boolean enabled) {
            }

            @Override
            public void insert(CatalogedTable ct) {
                catalogedTable = ct;
            }

            @Override
            public CatalogedTable getTable(String remappedName) {
                return null;
            }

            @Override
            public void delete(String remappedName) {
            }

            @Override
            public void clearRetryCount() {
            }
        };
        return helper1;
    }

    public static TableNameMapper makeMapper() {
        TableNameMapper m = new TableNameMapper() {
            @Override
            public String remap(String name) {
                return name;
            }

        };
        return m;
    }

    public static TableProviderParameters makeParams() {
        TableProviderParameters p = new TableProviderParameters() {

            @Override
            public String getString(String parameterKey, String defaultValue) {
                if (MEMBER_NAME.equals(parameterKey)) {
                    return "BE";
                }
                if (DISCOVERY_URL.equals(parameterKey)) {
                    return "tibpgm";
                }
                if (LISTEN_URL.equals(parameterKey)) {
                    return "tcp";
                }
                return defaultValue;
            }

            @Override
            public int getInt(String parameterKey, int defaultValue) {
                return 0;
            }

            @Override
            public double getDouble(String parameterKey, double defaultValue) {
                return 0;
            }

            @Override
            public List<String> getMultivalue(String parameterKey) {
                return Collections.EMPTY_LIST;
            }

            @Override
            public Set<String> getKeys() {
                return Collections.EMPTY_SET;
            }

        };
        return p;
    }


    public static void main(String[] args) {
        Set<Entry<String,String>> set = System.getenv().entrySet();
        for (Entry<String, String> entry : set) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        BETableProvider prov = new BETableProvider();
        TableProviderParameters p = makeParams();
        TableNameMapper m = makeMapper();
        TableProviderControl helper1 = makeHelper();

        try {
            prov.initialize("id", helper1, p, m);
            prov.start();

            while (catalogedTable == null) {
                Thread.sleep(1000);
            }

            Table rTable = catalogedTable.getRuntimeTable();
            QueryModel queryModel = rTable.parseQuery(catalogedTable, "", LiveViewQueryType.SNAPSHOT_AND_CONTINUOUS, false);
            EventListener evListener = new EventListener() {

                @Override
                public void deleteBegin(BeginDeleteEvent evt) {
                    System.out.println("BETableProvider.main(...).new EventListener() {...}.deleteBegin: evt = " + evt);
                }

                @Override
                public void deleteEnd(EndDeleteEvent evt) {
                    System.out.println("BETableProvider.main(...).new EventListener() {...}.deleteEnd: evt = " + evt);
                }

                @Override
                public void exceptionRaised(QueryExceptionEvent evt) {
                    System.out.println("BETableProvider.main(...).new EventListener() {...}.exceptionRaised: evt = " + evt);
                }

                @Override
                public EnumSet<LiveViewClientCapability> getClientCapabilities() {
                    return LiveViewClientCapability.CURRENT_LV_CLIENT_CAPABILITY;
                }

                @Override
                public String getId() {
                    return "abc"; //place holder
                }

                @Override
                public void snapshotBegin(BeginSnapshotEvent evt) {
                    System.out.println("BETableProvider.main(...).new EventListener() {...}.snapshotBegin: evt = " + evt);
                }

                @Override
                public void snapshotEnd(EndSnapshotEvent evt) {
                    System.out.println("BETableProvider.main(...).new EventListener() {...}.snapshotEnd: evt = " + evt);
                }

                @Override
                public void tupleAdded(TupleAddedEvent evt) {
                    System.out.println("BETableProvider.main(...).new EventListener() {...}.main: evt = " + evt);
                }

                @Override
                public void tupleRemoved(TupleRemovedEvent evt) {
                    System.out.println("BETableProvider.main(...).new EventListener() {...}.tupleRemoved: evt = " + evt);
                }

                @Override
                public void tupleUpdated(AbstractTupleEvent evt) {
                    System.out.println("BETableProvider.main(...).new EventListener() {...}.main: evt = " + evt);
                }

            };
            rTable.addListener(evListener, LiveViewQueryType.SNAPSHOT_AND_CONTINUOUS, queryModel);

            while (true) {
                Thread.sleep(1000);
            }

        } catch (LiveViewException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




}

