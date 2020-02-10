/**
 *
 */
package com.tibco.be.bemm.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.bemm.functions.RemoteMetricsCollector.COLLECTOR_TYPE;

import java.util.HashMap;
import java.util.Map;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BEMM",
        category = "BEMM.metrics",
        synopsis = "Functions for getting metrics from monitorable entities in the BE runtime cluster")
public class RemoteMetricsProvider {

	private static Map<String,RemoteMetricsCollector> collectorIndex = new HashMap<String, RemoteMetricsCollector>();
	private static final String hawkHome = System.getProperty("tibco.env.HAWK_HOME");
	@com.tibco.be.model.functions.BEFunction(
        name = "subscribe",
        synopsis = "This function subscribes for updates for a particular type of statistics. \nThe updates are sent via the receiving event.",
        signature = "subscribe(String name,String monitoredEntityName,String[] properties,String type,String receivingEventURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "A unique name for the subscription. This is set as name in the receivingEvent"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "monitoredEntityName", type = "String", desc = "The name of the entity whose stats are needed"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String[]", desc = "The properties of the monitorable entity which contain the connectivity information"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "The type which designates a set of statistics")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Subscribes for updates for a particular type of statistics",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

	public static void subscribe(String name,String monitoredEntityName,String collectorType,String[] properties,String type, String receivingEventURI){
		int idx = name.lastIndexOf(":");
		String path;
		if(idx>0) path = name.substring(0, idx);
		else path = name;
		RemoteMetricsCollector statCollector = collectorIndex.get(path);
		if (statCollector == null){
			COLLECTOR_TYPE collectorTypeObj = RemoteMetricsCollector.COLLECTOR_TYPE.valueOf(collectorType);
			if (collectorTypeObj.compareTo(COLLECTOR_TYPE.HAWK) == 0) {
                if (hawkHome != null && hawkHome.trim().length() != 0) {
			        statCollector = new RemoteMetricsCollectorHawkImpl(monitoredEntityName,collectorTypeObj,properties);
                } else return;
            } else {
                statCollector = new RemoteMetricsCollectorJmxImpl(monitoredEntityName,collectorTypeObj,properties);
            }
			collectorIndex.put(path, statCollector);
		}
		statCollector.subscribe(name,type, receivingEventURI);
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "unsubscribe",
        synopsis = "This function unsubscribes from updates for a particular type of statistics.",
        signature = "void unsubscribe(String name,String type)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "A unique name for the subscription. This is set as name in the receivingEvent")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Unsubscribes from updates for a particular type of statistics",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )

	public static void unsubscribe(String name,String type){
		RemoteMetricsCollector statCollector = collectorIndex.get(name);
		if (statCollector != null) {
			statCollector.unsubscribe(type);
		}
	}

	public static void releaseCollector(String name){
		RemoteMetricsCollector statCollector = collectorIndex.remove(name);
		if(statCollector!=null) statCollector.destroy();
		statCollector = null;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "snapshot",
        synopsis = "Provides a snapshot read of a particular type of JMX statistics. The JMX\nstatistics are returned via a event (which is determined by receivingEventURI).",
        signature = "SimpleEvent snapshot(String name,String monitoredEntityName,String[] properties,String type,String receivingEventURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityFqName", type = "String", desc = "A unique name for the snapshot. This is set as name in the receivingEvent"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "monitoredEntityName", type = "String", desc = "The name of the entity whose stats are needed"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String[]", desc = "The properties of the monitorable entity which contain the connectivity information"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "The type which designates a set of statistics"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "receivingEventURI", type = "String", desc = "The URI of the simple event which is triggered whenever the statistics are updated")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Provides a snapshot read of a particular type of statistics.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object snapshot(String entityFqName,String monitoredEntityName,String collectorType,String[] properties,String type,String receivingEventURI){
		int idx = entityFqName.lastIndexOf(":");
		String path;
		if(idx>0) path = entityFqName.substring(0, idx);
		else path = entityFqName;
		RemoteMetricsCollector statCollector = collectorIndex.get(path);
		if (statCollector == null){
			COLLECTOR_TYPE collectorTypeObj = RemoteMetricsCollector.COLLECTOR_TYPE.valueOf(collectorType);
            if (collectorTypeObj == COLLECTOR_TYPE.HAWK) {
                if (hawkHome != null && hawkHome.trim().length() != 0) {
			        statCollector = new RemoteMetricsCollectorHawkImpl(monitoredEntityName,collectorTypeObj,properties);
                } else return null;
            } else {
                statCollector = new RemoteMetricsCollectorJmxImpl(monitoredEntityName, collectorTypeObj, properties, entityFqName);
            }
                collectorIndex.put(path, statCollector);
		}
		return statCollector.snapshot(entityFqName,type, receivingEventURI);
	}

}
