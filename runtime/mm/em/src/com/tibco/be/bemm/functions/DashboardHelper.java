package com.tibco.be.bemm.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.functions.coherence.constants.CoherenceConstantFunctions;
import com.tibco.be.functions.coherence.extractor.CoherenceExtractorFunctions;
import com.tibco.be.functions.coherence.filters.CoherenceFilterFunctions;
import com.tibco.be.functions.coherence.query.CoherenceQueryFunctions;
import com.tibco.be.functions.java.util.MapHelper;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.util.StringUtilities;


@com.tibco.be.model.functions.BEPackage(
        catalog = "BEMM",
        category = "BEMM.dashboard",
        synopsis = "Functions for getting Enterprise Management Console dashboard meta-data")

public class DashboardHelper {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }


    //private static final String[] EMPTY_STR_ARRAY = new String[0];

    private static DashboardHelper instance = new DashboardHelper();

    private Properties layoutRepository;

    private Properties componentRepository;

    private Map<String, String> cTypeToMapIDMap;

    private Logger logger;

    private final String hawkHome = System.getProperty("tibco.env.HAWK_HOME");

    private DashboardHelper(){
        try {
            layoutRepository = new Properties();
            layoutRepository.load(this.getClass().getResourceAsStream("/page.templates"));
        } catch (IOException e) {
            throw new RuntimeException("could not load page repository",e);
        }

        try {
            componentRepository = new Properties();
            componentRepository.load(this.getClass().getResourceAsStream("/component.types"));
            cTypeToMapIDMap = parseComponentDefinitions();
        } catch (IOException e) {
            throw new RuntimeException("could not load component repository",e);
        }
        logger = LogManagerFactory.getLogManager().getLogger(this.getClass());
    }

    private Map<String, String> parseComponentDefinitions() {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<Object> keys = componentRepository.keys();
        while (keys.hasMoreElements()) {
            String cType = (String) keys.nextElement();
            String[] rawCompDef = splitComponentDefinition(componentRepository.getProperty(cType));
            String mapID = cType+":config";
            MapHelper.createMap(mapID);
            //put the key in here also
            MapHelper.putObject(mapID, "key", cType);
            //component type
            MapHelper.putObject(mapID, "ctype", cType.substring(cType.lastIndexOf("/")+1));
            //chart type
            MapHelper.putObject(mapID, "type", rawCompDef[0]);
            //data retrieval type
            MapHelper.putObject(mapID, "dataretrievaltype", rawCompDef[1]);
            //lets add a convenience flag indicating whether the component is subscribable or not
            if (rawCompDef[1].equalsIgnoreCase("jmx") == true) {
                MapHelper.putObject(mapID, "subscribable", true);
            }
            else if (rawCompDef[1].equalsIgnoreCase("hawk") == true
                    && hawkHome != null && hawkHome.trim().length() != 0){
                MapHelper.putObject(mapID, "subscribable", true);
            }
            else {
                MapHelper.putObject(mapID, "subscribable", false);
            }
            //series names - can be ':' separated
            String[] seriesNames = rawCompDef[2].split(":");
            MapHelper.putObject(mapID, "seriesnames", seriesNames);
            //threshold - long
            Long threshold = new Long(rawCompDef[3]);
            MapHelper.putObject(mapID, "threshold", threshold);
            //lets add a convenience flag indicating whether the component is time based or not
            if (threshold > 1000){
                MapHelper.putObject(mapID, "timebased", true);
            }
            else {
                MapHelper.putObject(mapID, "timebased", false);
            }
            //data retrieval specific properties
            String[] properties = rawCompDef[4].split("@@");
            //static category value prefix or time indicator or query or event uri
            MapHelper.putObject(mapID, "retrievalconfig1", properties[0]);
            if (properties.length > 1){
                if (rawCompDef[1].equalsIgnoreCase("query") || rawCompDef[1].equalsIgnoreCase("simulated")) {
                    //multiplier or callback rule function
                    try {
                        MapHelper.putObject(mapID, "retrievalconfig2", new Integer(properties[1]));
                    } catch (NumberFormatException e){
                        MapHelper.putObject(mapID, "retrievalconfig2", properties[1]);
                    }
                    //sort spec if present
                    if (properties.length > 2){
                        String[] sortspec = new String[properties.length-2];
                        for (int i = 0; i < sortspec.length; i++) {
                            sortspec[i] = properties[i+2];
                        }
                        MapHelper.putObject(mapID, "sortspec", sortspec);
                    }
                }
                else {
                    String[] sortspec = new String[properties.length-1];
                    for (int i = 0; i < sortspec.length; i++) {
                        sortspec[i] = properties[i+1];
                    }
                    MapHelper.putObject(mapID, "sortspec", sortspec);
                }
            }
            map.put(cType, mapID);
        }
        return map;

    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAllSupportedNodeTypes",
        synopsis = "This function returns all the node types which have associated page template XML.",
        signature = "String[] getAllSupportedNodeTypes()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns all the node types which have associated page template XML",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

    public static String[] getAllSupportedNodeTypes(){
        Set<Object> keys = instance.layoutRepository.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getPageLayout",
        synopsis = "This function returns the page template XML associated with a specific type of node. \nNote that the return XML is structured as shown below\n<pre>\n&lt;!ELEMENT panel ( page* ) &gt;\n&lt;!ELEMENT page (#PCDATA) &gt;\n&lt;!ATTLIST page name CDATA #REQUIRED&gt;\n</pre>",
        signature = "String getPageLayout(String monitoredType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "The type of the node")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the page template XML associated with a specific type of node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

    public static String getPageLayout(String monitoredType){
        return instance.layoutRepository.getProperty(monitoredType.toLowerCase(),"<page><panel name=\""+monitoredType+" Overview\"/></page>");
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAllSupportedComponentTypes",
        synopsis = "This function returns all the registered component types",
        signature = "String[] getAllSupportedComponentTypes()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns all the registered component types",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

    public static String[] getAllSupportedComponentTypes(){
        Set<Object> keys = instance.componentRepository.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getConfigMapID",
        synopsis = "This function returns the id of the map containing the parsed component definition. The\nreturned id can be used in conjunction with Util.HashMap UDF's to get the \nactual configuration values. Below is the list of the keys in the config map \n<ul>\n<li>key - The component type along with path information</li>\n<li>ctype - The component type without the path information</li>\n<li>type - The type of the component view [chart,table]</li>\n<li>dataretrievaltype - The type of data retrieval [simulated,JMX,query]</li>\n<li>seriesnames - The list of series names. [datadrive/&lt;comma-separated string&gt;]</li>\n<li>threshold - The threshold for the component. [number of data points/last XX &lt;timeunits&gt;]</li>\n<li>retrievalconfig1 - Data Retrieval Type specific configuration</li>\n<li>retrievalconfig2 - Data Retrieval Type specific configuration</li>\n<li>sortspec - Data sorting specification</li>\n</ul>",
        signature = "String getConfigMapID(String cTypeWithPath)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "getConfigMapID",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

    public static String getConfigMapID(String cTypeWithPath){
        String[] sPath = cTypeWithPath.split("/");
        int cTypePathLength = sPath.length;
        if (cTypePathLength > 5){
            instance.logger.log(Level.ERROR, "Invalid component definition key ["+cTypeWithPath+"]");
            return null;
        }
        String fusedCTypePath = searchKey(sPath);
        if (fusedCTypePath == null){
            instance.logger.log(Level.ERROR, "could not find a component definition matching ["+cTypeWithPath+"]");
            return null;
        }
        String mapID = instance.cTypeToMapIDMap.get(fusedCTypePath);
        if (mapID == null){
            instance.logger.log(Level.ERROR, "No config map id found for "+cTypeWithPath);
        }
        return mapID;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "searchByExtId",
        synopsis = "Execute a query against a named cache. specifically against extId using like operator",
        signature = "Concept[] searchByExtId(String cacheName, String extIdPattern)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extIdPattern", type = "String", desc = "The ext Id or ext Id pattern to search for"),
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "List of Concepts that satisfy the query"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute a query against a named cache. specifically against extId using like operator",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept[] searchByExtId(String cacheName, String extIdPattern) {
        String cacheProvider = TopologyHelper.getCacheProvider();
        if (cacheProvider == "Oracle") {
            Object likeFilter = CoherenceFilterHelper.C_Like(CoherenceExtractorFunctions.C_EntityExtIdGetter(), CoherenceConstantFunctions.C_StringConstant(extIdPattern));
            Object allFilter = CoherenceFilterFunctions.C_All(new Object[]{likeFilter});
            return CoherenceQueryFunctions.C_QueryConcepts(cacheName, allFilter, true);
        }
        return null;
    }

//	/**
//	 * Returns the component definition associated with a specific type of component.
//	 *
//	 * @.name getComponentDef
//	 * @.description Returns the component definition associated with a specific type of component
//	 * @.signature String[] getComponentDef(String cTypeWithPath)
//	 * @.version 3.0.2
//	 * @.see
//	 * @.mapper false
//	 * @.synopsis This function returns the component definition associated with a specific type of component.
//	 * @.domain action
//	 *
//	 * @param cTypeWithPath String A "/" separated string including path and component type
//	 * @return String[] The component definition
//	 */
//	public static String[] getComponentDef(String cTypeWithPath){
//		String[] sPath = cTypeWithPath.split("/");
//		int cTypePathLength = sPath.length;
//		if (cTypePathLength > 5){
//			throw new RuntimeException("Invalid component definition key ["+cTypeWithPath+"]");
//		}
//		String fusedCTypePath = searchKey(sPath);
//		if (fusedCTypePath == null){
//			throw new RuntimeException("could not find a component definition matching ["+cTypeWithPath+"]");
//		}
//		String compDef = instance.componentRepository.getProperty(fusedCTypePath);
//		if (compDef == null) {
//			return EMPTY_STR_ARRAY;
//		}
//		if (compDef.trim().length() == 0) {
//			return EMPTY_STR_ARRAY;
//		}
//		return splitComponentDefinition(compDef);
//	}

    private static String[] splitComponentDefinition(String compDef) {
        List<String> list = new LinkedList<String>();
        int beginIdx = 0;
        int idx = compDef.indexOf(",");
        if (idx == -1){
            return new String[]{compDef};
        }
        int splitCnt = 4;
        while (idx != -1 && splitCnt > 0) {
            list.add(compDef.substring(beginIdx,idx));
            beginIdx = idx+1;
            idx = compDef.indexOf(",",beginIdx);
            splitCnt--;
        }
        list.add(compDef.substring(beginIdx));
        return list.toArray(new String[list.size()]);
    }

    private static String searchKey(String[] componentTypePath){
        String fusedCTypePath = StringUtilities.join(componentTypePath, "/");
        if (instance.componentRepository.containsKey(fusedCTypePath) == true){
            return fusedCTypePath;
        }
        //we did not find the key, let's attempt wildcard substitution
        fusedCTypePath = searchKeyByWildCard(componentTypePath);
        if (fusedCTypePath == null && componentTypePath.length > 2){
            String[] workingCopy = new String[componentTypePath.length-1];
            //copy all elements except last two
            System.arraycopy(componentTypePath, 0, workingCopy, 0, componentTypePath.length-2);
            //copy last element
            workingCopy[workingCopy.length-1] = componentTypePath[componentTypePath.length-1];
            fusedCTypePath = searchKey(workingCopy);
        }
        return fusedCTypePath;
    }

    private static String searchKeyByWildCard(String[] componentTypePath){
        String[] workingCopy = new String[componentTypePath.length];
        System.arraycopy(componentTypePath, 0, workingCopy, 0, componentTypePath.length);
        for (int i = workingCopy.length-2; i >= 0; i--) {
            workingCopy[i] = "*";
            //find a match
            String fusedCTypePath = StringUtilities.join(workingCopy, "/");
            if (instance.componentRepository.containsKey(fusedCTypePath) == true){
                return fusedCTypePath;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String[] configProps = new String[]{"ctype","type","dataretrievaltype", "subscribable", "seriesnames","threshold", "timebased","retrievalconfig1","retrievalconfig2","sortspec"};
        String[] supportedComponentTypes = getAllSupportedComponentTypes();
        for (int i = 0; i < supportedComponentTypes.length; i++) {
            String cType = supportedComponentTypes[i];
            String mapid = getConfigMapID(cType);
            for (int j = 0; j < configProps.length; j++) {
                String configPropertyName = configProps[j];
                Object configValue = MapHelper.getObject(mapid, configPropertyName);
                if (configValue instanceof Object[]){
                    configValue = UtilFunctions.join((Object[]) configValue, ",");
                }
                System.out.println(cType+"-"+mapid+"--"+configPropertyName+"=["+configValue+"]");
            }
        }
    }

}
