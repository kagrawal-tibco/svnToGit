package com.tibco.be.bemm.functions;

import com.tibco.be.functions.java.util.MapHelper;
import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.util.XiSupport;
import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.ConfigSchemaProvider;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmNamespaceProvider;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BEMM",
        category = "BEMM.alerts",
        synopsis = "Functions for getting alerts configuration")
public class AlertConfigurationProvider {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }


    private static final String ALERTSCONFIG_FILENAME = "alertsconfig.xml";

    private static AlertConfigurationProvider instance;

    //we use a getInstance method to force the creation of the TopologyHelper
    //in a BE engine thread. That will guarantee to give us current rule session

    private static AlertConfigurationProvider getInstance(){
        if (instance == null){
            instance = new AlertConfigurationProvider();
        }
        return instance;
    }

    private Logger logger;

    private HashSet<String> enabledEntityTypes;

    private HashSet<String> triggeringChartTypes;

    private HashMap<String,Set<String>> entityTypePathToTriggeringCharts;

    private LinkedHashMap<String, List<String>> triggeringChartTypeToAlertCfgIdsMap;

    private Map<String, MessageFormat> alertMessages;

//    ONLY USED FOR TESTING. It stopped working with the new logger, so it was commented out
    private AlertConfigurationProvider(boolean testing){
        //logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
        enabledEntityTypes = new HashSet<String>();
        triggeringChartTypes = new HashSet<String>();
        entityTypePathToTriggeringCharts = new HashMap<String, Set<String>>();
        triggeringChartTypeToAlertCfgIdsMap = new LinkedHashMap<String, List<String>>();
        alertMessages = new HashMap<String, MessageFormat>();
    }
    //Testing purpose
    private AlertConfigurationProvider(String filepath){
        enabledEntityTypes = new HashSet<String>();
        triggeringChartTypes = new HashSet<String>();
        entityTypePathToTriggeringCharts = new HashMap<String, Set<String>>();
        triggeringChartTypeToAlertCfgIdsMap = new LinkedHashMap<String, List<String>>();
        alertMessages = new HashMap<String, MessageFormat>();
        this.parseXML(this.getConfigNodeFromCDD(filepath));
    }

    private AlertConfigurationProvider(){
        enabledEntityTypes = new HashSet<String>();
        triggeringChartTypes = new HashSet<String>();
        entityTypePathToTriggeringCharts = new HashMap<String, Set<String>>();
        triggeringChartTypeToAlertCfgIdsMap = new LinkedHashMap<String, List<String>>();
        alertMessages = new HashMap<String, MessageFormat>();
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        logger = currRuleServiceProvider.getLogger(this.getClass());

        String filepath = currRuleServiceProvider.getProperties().getProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName());
        XiNode alertConfigNode = getConfigNodeFromCDD(filepath);
        if(alertConfigNode != null){
        	this.parseXML(alertConfigNode);
        	return;
        }
        String configDir = currRuleServiceProvider.getProperties().getProperty("be.mm.config.dir.location");
        if (configDir == null || configDir.trim().length() == 0){
            logger.log(Level.WARN, "Missing or invalid be.mm.config.dir.location");
            return;
        }
        if (configDir.endsWith(File.separator) == false){
            configDir = configDir + File.separator;
        }
        String alertsConfigXMLFilePath = configDir + ALERTSCONFIG_FILENAME;
        File alertsConfigXMLFileObj = new File(alertsConfigXMLFilePath);
        if (alertsConfigXMLFileObj.exists() == false){
            logger.log(Level.WARN, configDir.substring(0, configDir.length()-1)+" does not contain "+ALERTSCONFIG_FILENAME);
            return;
        }
        this.parseXML(getConfigNode(alertsConfigXMLFileObj));
    }


    public XiNode getConfigNode(File alertsConfigFile){
        try {
            InputStream is = new FileInputStream(alertsConfigFile);
            //create an instance of the parser
            XiParser parser = XiParserFactory.newInstance();
            // open up the xml file
            final XiNode doc = parser.parse(new InputSource(is));
            XiNode parent = doc.getFirstChild();
            return parent;
        } catch (FileNotFoundException e) {
        	throw new RuntimeException("could not find "+alertsConfigFile,e);
        } catch (SAXException e) {
            throw new RuntimeException("could not parse "+alertsConfigFile,e);
        } catch (IOException e) {
            throw new RuntimeException("could not load "+alertsConfigFile,e);
        }
    }

    private XiNode getConfigNodeFromCDD(String filepath){
    	XiNode alertConfig = null;
        try{
	        SmNamespaceProvider smNsProvider = new ConfigSchemaProvider();
	        XiNode clusterDoc = XiSupport.getParser().parse(new InputSource(new FileInputStream(filepath)), smNsProvider);
	        XiNode clusterNode = XiChild.getChild(clusterDoc, ConfigNS.Elements.CLUSTER);
	        XiNode agentClasses = XiChild.getChild(clusterNode, ConfigNS.Elements.AGENT_CLASSES);
	        XiNode mmAgent = XiChild.getChild(agentClasses, ConfigNS.Elements.MM_AGENT_CLASS);
	        alertConfig = XiChild.getChild(mmAgent, ConfigNS.Elements.ALERT_CONFIG_SET);
        }
        catch(Exception e){

        }
    	return alertConfig;
    }

    private void parseXML(XiNode parent) {
        try {
            Iterator childIterator = XiChild.getIterator(parent);
            while (childIterator.hasNext()) {
                XiNode alertConfigNode = (XiNode) childIterator.next();
                //get condition
                Iterator conditionIterator = XiChild.getIterator(alertConfigNode, ConfigNS.Elements.CONDITION);
                while (conditionIterator.hasNext()) {
                    XiNode conditionNode = (XiNode) conditionIterator.next();
                    //get trigger condition, only first will be picked up
                    Iterator triggerIterator = XiChild.getIterator(conditionNode);
                    if (triggerIterator.hasNext() == true) {
                        XiNode trigger = (XiNode) triggerIterator.next();

                        String propName = trigger.getAttributeStringValue(ExpandedName.makeName("path")) + "/" + trigger.getAttributeStringValue(ExpandedName.makeName("name"));
                        Object[] propNameElements = parse(propName);
                        String rawEntityType = (String) propNameElements[0];
                        String[] entityTypes = (String[]) propNameElements[1];
                        //add entities to hash set
                        enabledEntityTypes.addAll(Arrays.asList(entityTypes));
                        //add chart type to hash set
                        triggeringChartTypes.add((String) propNameElements[2]);

                        String referencePropName = trigger.getAttributeStringValue(ExpandedName.makeName("reference"));

                        for (int i = 0; i < entityTypes.length; i++) {
                            boolean generateMap = true;
                            //create configuration map
                            String key = entityTypes[i] + "/" + propNameElements[2];

                            //add chart type to entity trigger hash map
                            Set<String> entityFilteredChartTypes = entityTypePathToTriggeringCharts.get(entityTypes[i]);
                            if (entityFilteredChartTypes == null) {
                                entityFilteredChartTypes = new HashSet<String>();
                                entityTypePathToTriggeringCharts.put((String) entityTypes[i], entityFilteredChartTypes);
                            }
                            //add map id to alert config map
                            List<String> mapIDs = triggeringChartTypeToAlertCfgIdsMap.get(key);
                            if (mapIDs == null) {
                                mapIDs = new LinkedList<String>();
                                triggeringChartTypeToAlertCfgIdsMap.put(key, mapIDs);
                            }
                            else {
                                boolean currRawEntityTypeWildCardBased = rawEntityType.contains("*");
                                //do some check
                                ListIterator<String> mapIDslistIterator = mapIDs.listIterator();
                                while (mapIDslistIterator.hasNext()) {
                                    String existingMapID = mapIDslistIterator.next();
                                    String existingRawEntityType = MapHelper.get(existingMapID, "rawentity");
                                    String existingTriggerCondition = MapHelper.get(existingMapID, "property").substring(existingRawEntityType.length()+1);
                                    String existingReferenceCondition = MapHelper.get(existingMapID, "reference");
                                    if (propName.indexOf(existingTriggerCondition) != -1 && referencePropName.equals(existingReferenceCondition) == true) {
                                        boolean existingRawEntityTypeWildCardBased = existingRawEntityType.contains("*");
                                        if (existingRawEntityTypeWildCardBased == true){
                                            //existing is wildcard based
                                            if (currRawEntityTypeWildCardBased == true){
                                                //current is wildcard based, we let current supersede
                                                logger.log(Level.WARN, propName+" conflicts with "+MapHelper.get(existingMapID, "property")+",overriding with "+propName);
                                            }
                                            else {
                                                //current is NOT wildcard based, it supersedes existing one
                                                logger.log(Level.DEBUG, propName+" overrides "+MapHelper.get(existingMapID, "property"));
                                            }
                                            MapHelper.deleteMap(existingMapID);
                                            mapIDslistIterator.remove();
                                            break;
                                        }
                                        else {
                                            //existing in NOT wildcard based
                                            if (currRawEntityTypeWildCardBased == true){
                                                //current is wildcard based, existing one supersedes current one
                                                logger.log(Level.DEBUG, MapHelper.get(existingMapID, "property")+" overrides "+propName);
                                                generateMap = false;
                                                break;
                                            }
                                            else {
                                                //current is NOT wildcard based, we allow this combination
                                                //we continue the search
                                            }
                                        }
                                    }
                                }
                            }

                            if (generateMap == true) {
                                String mapID = key + "/alertconfig#" + triggeringChartTypeToAlertCfgIdsMap.size() + mapIDs.size();
                                MapHelper.createMap(mapID);
                                mapIDs.add(mapID);
                                entityFilteredChartTypes.add((String) propNameElements[2]);
                                //trigger properties
                                MapHelper.putObject(mapID, "property", propName);
                                MapHelper.putObject(mapID, "rawentity", rawEntityType);
                                MapHelper.putObject(mapID, "entity", entityTypes[i]);
                                MapHelper.putObject(mapID, "charttype", propNameElements[2]);
                                MapHelper.putObject(mapID, "seriesname", propNameElements[3]);
                                MapHelper.putObject(mapID, "categoryvalue", propNameElements[4]);
                                MapHelper.putObject(mapID, "valuelocation", propNameElements[5]);
                                //reference properties

                                MapHelper.putObject(mapID, "reference", referencePropName);
                                try {
                                    MapHelper.putObject(mapID, "constantreference", Integer.parseInt(referencePropName));
                                } catch (NumberFormatException e) {
                                    String[] splits = referencePropName.split("/");
                                    MapHelper.putObject(mapID, "referenceentity", entityTypes[i]);
                                    MapHelper.putObject(mapID, "referencecharttype", propNameElements[2]);
                                    MapHelper.putObject(mapID, "referenceseriesname", splits[0]);
                                    if (splits.length > 1) {
                                        MapHelper.putObject(mapID, "referencecategoryvalue", splits[1]);
                                        if (splits.length > 2) {
                                            MapHelper.putObject(mapID, "referencevaluelocation", Integer.parseInt(splits[2]));
                                        }
                                    }
                                }
                                //threshold
                                String threshold = trigger.getAttributeStringValue(ExpandedName.makeName("threshold"));
                                MapHelper.putObject(mapID, "threshold", Double.parseDouble(threshold));
                                //projection
                                Iterator projectionIterator = XiChild.getIterator(alertConfigNode, ConfigNS.Elements.PROJECTION);
                                if (projectionIterator.hasNext() == true) {
                                    XiNode projection = (XiNode) projectionIterator.next();
                                    Iterator setPropsIterator = XiChild.getIterator(projection);
                                    Object[] properties = new Object[XiChild.getChildCount(projection)];
                                    int j = 0;
                                    while (setPropsIterator.hasNext()) {
                                        XiNode projPropertyNode = (XiNode) setPropsIterator.next();
                                        String projPropName = projPropertyNode.getAttributeStringValue(ExpandedName.makeName("name"));
                                        String projPropValue = projPropertyNode.getAttributeStringValue(ExpandedName.makeName("value"));
                                        if (projPropName.equalsIgnoreCase("message") == true) {
                                            alertMessages.put(mapID, new MessageFormat(projPropValue));
                                        }
                                        properties[j++] = new String[] { projPropName, projPropValue };
                                    }
                                    MapHelper.putObject(mapID, "projection", properties);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("could not parse alertconfigset",e);
        }
    }

    private Object[] parse(String nameWithPath){
        //we will first isolate the entity path
        //search for
        ArrayList<Object> parsedResults = new ArrayList<Object>();
        String[] splits = nameWithPath.split("/");
        EntityTypePath entityTypePath = new EntityTypePath();
        for (int i = 0; i < splits.length; i++) {
            String split = splits[i];
            if (entityTypePath != null && entityTypePath.addElement(split) == false){
                parsedResults.add(entityTypePath.rawPath.toString());
                parsedResults.add(entityTypePath.paths);
                entityTypePath = null;
            }
            if (entityTypePath == null){
                if (i+1 == splits.length){
                    try {
                        parsedResults.add(Integer.parseInt(split));
                    } catch (NumberFormatException e) {
                        parsedResults.add(split);
                    }
                }
                else {
                    parsedResults.add(split);
                }
            }
        }
        if (parsedResults.size() == 4){
            parsedResults.add("*");
        }
        if (parsedResults.size() == 5){
            parsedResults.add(-1);
        }
        return parsedResults.toArray(new Object[parsedResults.size()]);
    }

    private static String massageEntityTypePath(String entityTypePath){
        if (entityTypePath.startsWith("site") == false){
            return "site/"+entityTypePath;
        }
        return entityTypePath;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEntityTypes",
        synopsis = "This function returns all the entity types which are configured for alerts.\nNote that the entity type has path information also.",
        signature = "String[] getEntityTypes()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns all the entity types which are configured for alerts",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getEntityTypes(){
        HashSet<String> set = AlertConfigurationProvider.getInstance().enabledEntityTypes;
        if (set == null || set.size() == 0){
            return null;
        }
        return set.toArray(new String[set.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getChartTypes",
        synopsis = "This function returns all the chart types which are configured for alerts.",
        signature = "String[] getChartTypes()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns all the chart types which are configured for alerts",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getChartTypes(){
        HashSet<String> set = AlertConfigurationProvider.getInstance().triggeringChartTypes;
        if (set == null || set.size() == 0){
            return null;
        }
        return set.toArray(new String[set.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getChartTypesForEntity",
        synopsis = "This function returns all the chart types which are configured for alerts for a entity.",
        signature = "String[] getChartTypesForEntity(String entityPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityPath", type = "String", desc = "The entity including the path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns all the chart types which are configured for alerts for a entity",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getChartTypesForEntity(String entityPath){
        entityPath = massageEntityTypePath(entityPath);
        Set<String> chartTypeSet = AlertConfigurationProvider.getInstance().entityTypePathToTriggeringCharts.get(entityPath);
        if (chartTypeSet == null || chartTypeSet.size() == 0){
            return null;
        }
        return chartTypeSet.toArray(new String[chartTypeSet.size()]);
    }

    public static boolean isConfigured(String entityPath, String chartType){
        entityPath = massageEntityTypePath(entityPath);
        Set<String> chartTypeSet = AlertConfigurationProvider.getInstance().entityTypePathToTriggeringCharts.get(entityPath);
        if (chartTypeSet == null || chartTypeSet.size() == 0){
            return false;
        }
        return chartTypeSet.contains(chartType);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAlertConfigMapIds",
        synopsis = "This function returns an array of the ids for maps. The maps contain the alert configuration \nfor the entity and the chart type",
        signature = "String[] getAlertConfigMapIds(String entityPaht,String chartType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityPath", type = "String", desc = "The entity including the path"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "chartType", type = "String", desc = "The chart type")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of ids for maps, containing the alert configuration",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getAlertConfigMapIds(String entityPath, String chartType){
        entityPath = massageEntityTypePath(entityPath);
        List<String> mapIDs = AlertConfigurationProvider.getInstance().triggeringChartTypeToAlertCfgIdsMap.get(entityPath+"/"+chartType);
        if (mapIDs == null || mapIDs.isEmpty() == true){
            return null;
        }
        return mapIDs.toArray(new String[mapIDs.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFormattedMessage",
        synopsis = "This function returns a formatted meesage for a alert configuration",
        signature = "String getFormattedMessage(String mapID,String monitoredEntityName,Object categoryValue,Object triggerValue,Object referenceValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mapID", type = "String", desc = "The id of the map containing the alert configuration"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "monitoredEntityName", type = "String", desc = "The name of a entity"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "categoryValue", type = "String", desc = "The category value"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "triggerValue", type = "Object", desc = "The triggering value"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "referenceValue", type = "Object", desc = "The reference value")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a formatted meesage for a alert configuration",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getFormattedMessage(String mapID,String monitoredEntityName,Object categoryValue,Object triggerValue,Object referenceValue){
        MessageFormat messageFormat = AlertConfigurationProvider.getInstance().alertMessages.get(mapID);
        if (messageFormat == null){
            return "";
        }
        return messageFormat.format(new Object[]{monitoredEntityName,categoryValue,triggerValue,referenceValue}, new StringBuffer(), null).toString();
    }

    class EntityTypePath {

        int elementCounter = -1;
        String[] paths;

        StringBuilder rawPath = new StringBuilder();

        boolean addElement(String element) {
            if (element.equals("*")  == true || TopologyHelper.isDomainElement(element) == true){
                elementCounter++;
                String[] expandedElements = process(element);
                if (paths == null){
                    paths = new String[expandedElements.length];
                }
                else if (paths.length == 1){
                    String temp = paths[0];
                    paths = new String[expandedElements.length];
                    for (int i = 0; i < paths.length; i++) {
                        paths[i] = new String(temp);
                    }
                }else {
                    throw new IllegalStateException(paths+" incompitible with expanded elements");
                }
                for (int i = 0; i < expandedElements.length; i++) {
                    if (paths[i] == null){
                        paths[i] = expandedElements[i];
                    }
                    else {
                        paths[i] = paths[i] + "/" + expandedElements[i];
                    }
                }
                if (rawPath.length() != 0){
                    rawPath.append("/");
                }
                rawPath.append(element);
                return true;
            }
            return false;
        }

        String[] process(String element){
            if (element.equals("*") == true){
                switch(elementCounter){
                    case 0:
                        return new String[]{"site"};
                    case 1:
                        return new String[]{"cluster"};
                    case 2:
                        return new String[]{"machine"};
                    case 3:
                        return new String[]{"process"};
                    case 4:
                        return new String[]{"inference","query","cache"};
                    default:
                        throw new IllegalArgumentException("Unknown element type["+element+"]");

                }
            }
            return new String[]{element};
        }
    }

    public static void main(String[] args) {
        //AlertConfigurationProvider p = new AlertConfigurationProvider("c:/Users/Nick/Desktop/BEMM.cdd");
    	AlertConfigurationProvider p = new AlertConfigurationProvider(true);
        AlertConfigurationProvider.instance = p;
        p.parseXML(p.getConfigNode(new File("em/config/alertsconfig.xml")));
        String[] entityTypes = (String[]) AlertConfigurationProvider.getEntityTypes();
        for (int i = 0; i < entityTypes.length; i++) {
            String entityType = entityTypes[i];
            System.out.println(entityType);
            String[] chartTypesForEntity = (String[]) AlertConfigurationProvider.getChartTypesForEntity(entityType);
            for (int j = 0; j < chartTypesForEntity.length; j++) {
                String chartTypeForEntity = chartTypesForEntity[j];
                System.out.println("Chart Type = "+chartTypeForEntity);
                String[] mapIds = (String[]) AlertConfigurationProvider.getAlertConfigMapIds(entityType,chartTypeForEntity);
                for (int l = 0; l < mapIds.length; l++) {
                    String mapID = mapIds[l];
                    System.out.println("\t Alert Map ID = "+mapID);
                    System.out.println("\t\t Trigger Property = "+MapHelper.get(mapID, "property"));
                    System.out.println("\t\t Trigger Entity = "+MapHelper.get(mapID, "entity"));
                    System.out.println("\t\t Trigger Chart Type = "+MapHelper.get(mapID, "charttype"));
                    System.out.println("\t\t Trigger Series Name = "+MapHelper.get(mapID, "seriesname"));
                    System.out.println("\t\t Trigger Series Category value = "+MapHelper.get(mapID, "categoryvalue"));
                    System.out.println("\t\t Trigger Series value Location = "+MapHelper.getObject(mapID, "valuelocation"));
                    System.out.println("\t\t Constant Reference = "+MapHelper.getObject(mapID, "constantreference"));
                    System.out.println("\t\t Reference = "+MapHelper.get(mapID, "reference"));
                    System.out.println("\t\t Reference Entity = "+MapHelper.get(mapID, "referenceentity"));
                    System.out.println("\t\t Reference Chart Type = "+MapHelper.get(mapID, "referencecharttype"));
                    System.out.println("\t\t Reference Series Name = "+MapHelper.get(mapID, "referenceseriesname"));
                    System.out.println("\t\t Reference Series Category value = "+MapHelper.get(mapID, "referencecategoryvalue"));
                    System.out.println("\t\t Reference Series value Location = "+MapHelper.getObject(mapID, "referencevaluelocation"));

                    System.out.println("\t\t threshold = "+MapHelper.getObject(mapID, "threshold"));
                    Object[] properties = (Object[]) MapHelper.getObject(mapID, "projection");
                    for (int k = 0; k < properties.length; k++) {
                        String[] property = (String[]) properties[k];
                        System.out.println("\t\t\t Property#"+k+" Name = "+property[0]);
                        System.out.println("\t\t\t Property#"+k+" Value = "+property[1]);
                        System.out.println("\t\t\t Property#"+k+" Value = "+AlertConfigurationProvider.getFormattedMessage(mapID, "Monitored Entity #"+k+l, null,k+l, k*l));
                    }

                }
            }
        }
    }
}
