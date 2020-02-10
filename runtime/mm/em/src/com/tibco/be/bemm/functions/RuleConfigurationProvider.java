/**
 *
 */
package com.tibco.be.bemm.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.functions.java.util.MapHelper;
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
import java.util.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BEMM",
        category = "BEMM.rules",
        synopsis = "Functions for getting rules configuration")
public class RuleConfigurationProvider {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }


    private static final String RULESCONFIG_FILENAME = "rulesconfig.xml";

    private static RuleConfigurationProvider instance;

    //we use a getInstance method to force the creation of the TopologyHelper
    //in a BE engine thread. That will guarantee to give us current rule session

    private static RuleConfigurationProvider getInstance(){
        if (instance == null){
            instance = new RuleConfigurationProvider();
        }
        return instance;
    }

    private Logger logger;

    private HashMap<String, String[]> entityTypeToPropNameMap;

    private LinkedHashMap<String, String[]> entityTypePropNameToPropValueMap;

    private LinkedHashMap<String, List<String>> entityTypePropDtlsToFilterMapIdMap;

    //testing purpose
    private RuleConfigurationProvider(boolean testing){
        entityTypeToPropNameMap = new HashMap<String, String[]>();
        entityTypePropNameToPropValueMap = new LinkedHashMap<String, String[]>();
        entityTypePropDtlsToFilterMapIdMap = new LinkedHashMap<String, List<String>>();
    }
    //testing purpose
    private RuleConfigurationProvider(String path){
        entityTypeToPropNameMap = new HashMap<String, String[]>();
        entityTypePropNameToPropValueMap = new LinkedHashMap<String, String[]>();
        entityTypePropDtlsToFilterMapIdMap = new LinkedHashMap<String, List<String>>();

	    this.parseXML(this.getConfigNodeFromCDD(path));

    }

    private RuleConfigurationProvider(){
        entityTypeToPropNameMap = new HashMap<String, String[]>();
        entityTypePropNameToPropValueMap = new LinkedHashMap<String, String[]>();
        entityTypePropDtlsToFilterMapIdMap = new LinkedHashMap<String, List<String>>();
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        logger = currRuleServiceProvider.getLogger(this.getClass());

        String filepath = currRuleServiceProvider.getProperties().getProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName());
        XiNode ruleConfigNode = getConfigNodeFromCDD(filepath);
        if(ruleConfigNode != null){
        	this.parseXML(ruleConfigNode);
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
        String rulesConfigXMLFilePath = configDir + RULESCONFIG_FILENAME;
        File rulesConfigXMLFileObj = new File(rulesConfigXMLFilePath);
        if (rulesConfigXMLFileObj.exists() == false){
            logger.log(Level.WARN, configDir.substring(0, configDir.length()-1)+" does not contain "+RULESCONFIG_FILENAME);
            return;
        }
        parseXML(getConfigNode(rulesConfigXMLFileObj));
    }

    public XiNode getConfigNode(File ruleConfigFile){
        try {
            InputStream is = new FileInputStream(ruleConfigFile);
            //create an instance of the parser
            XiParser parser = XiParserFactory.newInstance();
            // open up the xml file
            final XiNode doc = parser.parse(new InputSource(is));
            XiNode parent = doc.getFirstChild();
            return parent;
        } catch (FileNotFoundException e) {
        	throw new RuntimeException("could not find "+ruleConfigFile,e);
        } catch (SAXException e) {
            throw new RuntimeException("could not parse "+ruleConfigFile,e);
        } catch (IOException e) {
            throw new RuntimeException("could not load "+ruleConfigFile,e);
        }
    }

    private XiNode getConfigNodeFromCDD(String filepath){
    	XiNode ruleConfig = null;
        try{
	        SmNamespaceProvider smNsProvider = new ConfigSchemaProvider();
	        XiNode clusterDoc = XiSupport.getParser().parse(new InputSource(new FileInputStream(filepath)), smNsProvider);
	        XiNode clusterNode = XiChild.getChild(clusterDoc, ConfigNS.Elements.CLUSTER);
	        XiNode agentClasses = XiChild.getChild(clusterNode, ConfigNS.Elements.AGENT_CLASSES);
	        XiNode mmAgent = XiChild.getChild(agentClasses, ConfigNS.Elements.MM_AGENT_CLASS);
	        ruleConfig = XiChild.getChild(mmAgent, ConfigNS.Elements.RULE_CONFIG);
        }
        catch(Exception e){

        }
    	return ruleConfig;
    }

    private void parseXML(XiNode parent) {
        try {
            Iterator childIterator = XiChild.getIterator(parent);
            while (childIterator.hasNext()) {
                XiNode entityNode = (XiNode) childIterator.next();
                //get type
                String entityType = entityNode.getAttributeStringValue(ExpandedName.makeName("path"));
                Iterator propertyIterator = XiChild.getIterator(entityNode);
                while (propertyIterator.hasNext()) {
                    XiNode setPropNode = (XiNode) propertyIterator.next();

                    //Property Name
                    String propName = setPropNode.getAttributeStringValue(ExpandedName.makeName("name"));
                    String[] propNames = entityTypeToPropNameMap.get(entityType);
                    if (propNames == null){
                        //no prop name found create a new array
                        propNames = new String[]{propName};
                    }
                    else {
                        //search for the prop key
                        if (search(propNames, propName) == -1){
                            //prop key not found , add to the prop name array and sort the array
                            String[] extendedPropNames = new String[propNames.length+1];
                            System.arraycopy(propNames, 0, extendedPropNames, 0, propNames.length);
                            extendedPropNames[propNames.length] = propName;
                            propNames = extendedPropNames;
                        }
                    }
                    //update the map for entity type to prop names
                    entityTypeToPropNameMap.put(entityType, propNames);

                    //Property Value
                    String propValue = setPropNode.getAttributeStringValue(ExpandedName.makeName("value"));
                    String key = entityType + "/" + propName;
                    String[] propValues = entityTypePropNameToPropValueMap.get(key);
                    if (propValues == null){
                        //no prop value found create a new array
                        propValues = new String[]{propValue};
                    }
                    else {
                        //search for the prop value
                        if (search(propValues, propValue) == -1){
                            //prop value not found , add to the prop value array and sort the array
                            String[] extendedPropValues = new String[propValues.length+1];
                            System.arraycopy(propValues, 0, extendedPropValues, 0, propValues.length);
                            extendedPropValues[propValues.length] = propValue;
                            propValues = extendedPropValues;
                        }
                    }
                    //update the map for entitytype/propname with propValues
                    entityTypePropNameToPropValueMap.put(key, propValues);

                    key = key + "/" + propValue;

                    List<String> filterMapIdList = entityTypePropDtlsToFilterMapIdMap.get(key);
                    if (filterMapIdList == null){
                        filterMapIdList = new LinkedList<String>();
                        entityTypePropDtlsToFilterMapIdMap.put(key, filterMapIdList);
                    }

                    Iterator childFilterIterator = XiChild.getIterator(setPropNode);
                    while (childFilterIterator.hasNext()) {
                        XiNode filterNode = (XiNode) childFilterIterator.next();
                        String filterType = filterNode.getName().getLocalName();
                        String childType = filterNode.getAttributeStringValue(ExpandedName.makeName("path"));
                        String tolerance = filterNode.getAttributeStringValue(ExpandedName.makeName("tolerance"));
                        String range = filterNode.getAttributeStringValue(ExpandedName.makeName("range"));
                        String mapID = key + "/filter#" + filterMapIdList.size();
                        MapHelper.createMap(mapID);
                        MapHelper.putObject(mapID, "filtertype", filterType);
                        MapHelper.putObject(mapID, "childtype", childType);
                        if (tolerance != null && !tolerance.trim().equals("")){
                            MapHelper.putObject(mapID, "tolerance", Integer.parseInt(tolerance));
                        }
                        else {
                            MapHelper.putObject(mapID, "tolerance", 0);
                        }
                        if (range != null && !range.trim().equals("")){
                            MapHelper.putObject(mapID, "range", Long.parseLong(range));
                        }
                        int i = 0;
                        Object[] properties = new Object[XiChild.getChildCount(filterNode)];
                        Iterator filterPropIterator = XiChild.getIterator(filterNode);
                        while (filterPropIterator.hasNext()) {
                            XiNode filterPropNode = (XiNode) filterPropIterator.next();
                            String filterPropName = filterPropNode.getAttributeStringValue(ExpandedName.makeName("name"));
                            String filterPropValue = filterPropNode.getAttributeStringValue(ExpandedName.makeName("value"));
                            properties[i++] = new String[]{filterPropName,filterPropValue};
                        }
                        MapHelper.putObject(mapID, "properties", properties);
                        filterMapIdList.add(mapID);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("could not parse ruleconfig.",e);
        }
    }

    private int search(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element) == true){
                return i;
            }
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEntityTypes",
        synopsis = "This function returns the list of entity types which have rule configurations",
        signature = "getEntityTypes()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the list of entity types which have rule configurations",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getEntityTypes(){
        Set<String> entityTypeKeySet = RuleConfigurationProvider.getInstance().entityTypeToPropNameMap.keySet();
        return entityTypeKeySet.toArray(new String[entityTypeKeySet.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getPropertyNameOptions",
        synopsis = "This function returns the list of property names under a entity type which have rule configurations",
        signature = "getPropertyNameOptions(String entityType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "The entity type")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the list of property names under a entity type which have rule configurations",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getPropertyNameOptions(String entityType){
        RuleConfigurationProvider provider = RuleConfigurationProvider.getInstance();
        String[] propertyNames = provider.entityTypeToPropNameMap.get(entityType);
        return propertyNames;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getPropertyValueOptions",
        synopsis = "This function returns the list of the values which are configured for a property under a entity type",
        signature = "getPropertyValueOptions(String entityType,String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "The entity type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "The property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the list of the values which are configured for a property under a entity type",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getPropertyValueOptions(String entityType,String propertyName){
        RuleConfigurationProvider provider = RuleConfigurationProvider.getInstance();
        String[] propertyValues = provider.entityTypePropNameToPropValueMap.get(entityType+"/"+propertyName);
        return propertyValues;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getTargetEntityFiltersCount",
        synopsis = "This function returns the count of filters for a given value for a property under a entity type",
        signature = "getTargetEntityFiltersCount(String entityType,String propertyName,String propertyValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "The entity type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "The property name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyValue", type = "String", desc = "The property value")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the count of filters for a given value for a property under a entity type",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static int getTargetEntityFiltersCount(String entityType,String propertyName,String propertyValue){
        RuleConfigurationProvider provider = RuleConfigurationProvider.getInstance();
        List<String> filterMapIdList = provider.entityTypePropDtlsToFilterMapIdMap.get(entityType+"/"+propertyName+"/"+propertyValue);
        if (filterMapIdList == null){
            return 0;
        }
        return filterMapIdList.size();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getTargetEntityFilterMapId",
        synopsis = "This function returns the id of the map which contains the filter information at a given index for a given value for a property under a entity type",
        signature = "getTargetEntityFilterMapId(String entityType,String propertyName,String propertyValue,int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityType", type = "String", desc = "The entity type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "The property name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyValue", type = "String", desc = "The property value"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "The", desc = "index")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the id of the map which contains the filter information at a given index for a given value for a property under a entity type",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getTargetEntityFilterMapId(String entityType,String propertyName,String propertyValue,int index){
        RuleConfigurationProvider provider = RuleConfigurationProvider.getInstance();
        List<String> filterList = provider.entityTypePropDtlsToFilterMapIdMap.get(entityType+"/"+propertyName+"/"+propertyValue);
        if (filterList == null){
            return null;
        }
        return filterList.get(index);
    }


    public static void main(String[] args) {
        RuleConfigurationProvider p = new RuleConfigurationProvider(false);
        //RuleConfigurationProvider p = new RuleConfigurationProvider("c:/Users/Nick/Desktop/BEMM.cdd");
        RuleConfigurationProvider.instance = p;
        p.parseXML(p.getConfigNode(new File("em/config/rulesconfig.xml")));
        String[] entityTypes = (String[]) RuleConfigurationProvider.getEntityTypes();
        for (String entityType : entityTypes) {
            String[] propertyNameOptions = (String[]) RuleConfigurationProvider.getPropertyNameOptions(entityType);
            for (String propertyNameOption : propertyNameOptions) {
                String[] propertyValueOptions = (String[]) RuleConfigurationProvider.getPropertyValueOptions(entityType, propertyNameOption);
                for (String propertyValueOption : propertyValueOptions) {
                    int filtersCount = RuleConfigurationProvider.getTargetEntityFiltersCount(entityType, propertyNameOption, propertyValueOption);
                    System.out.println(entityType+"/"+propertyNameOption+"/"+propertyValueOption);
                    for (int i = 0; i < filtersCount; i++) {
                        String mapID = RuleConfigurationProvider.getTargetEntityFilterMapId(entityType, propertyNameOption, propertyValueOption, i);
                        System.out.println("\t FilterType = "+MapHelper.get(mapID, "filtertype"));
                        System.out.println("\t ChildType = "+MapHelper.get(mapID, "childtype"));
                        System.out.println("\t Tolerance = "+MapHelper.getObject(mapID, "tolerance"));
                        System.out.println("\t Range = "+MapHelper.getObject(mapID, "range"));
                        Object[] properties = (Object[]) MapHelper.getObject(mapID, "properties");
                        for (int j = 0; j < properties.length; j++) {
                            String[] property = (String[]) properties[j];
                            System.out.println("\t\t Property#"+j+" Name = "+property[0]);
                            System.out.println("\t\t Property#"+j+" Value = "+property[1]);
                        }
                    }
                }
            }
        }
    }
}
