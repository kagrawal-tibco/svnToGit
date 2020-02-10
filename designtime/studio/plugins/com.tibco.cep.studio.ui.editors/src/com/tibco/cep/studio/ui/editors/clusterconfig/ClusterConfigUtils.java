package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashboardAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Function;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentFunctionsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;

/**
 *
 * @author sasahoo
 *
 */
public class ClusterConfigUtils {


    public static String[] getStartupFunctionUris(
            ClusterConfigModelMgr modelmgr)
    {
        final ArrayList<String> startupFnUris = new ArrayList<String>();
        for (final AgentClass agent : modelmgr.getAgentClasses()) {
            final ArrayList<FunctionElement> functions;
            if (AgentClass.AGENT_TYPE_INFERENCE.equals(agent.type)) {
                functions = ((InfAgent) agent).agentStartupFunctionsGrpObj.agentFunctions;
            }
            else if(agent.type.equals(AgentClass.AGENT_TYPE_QUERY)){
                functions = ((QueryAgent) agent).agentStartupFunctionsGrpObj.agentFunctions;
            }
            else if(agent.type.equals(AgentClass.AGENT_TYPE_DASHBOARD)){
                functions = ((DashboardAgent) agent).agentStartupFunctionsGrpObj.agentFunctions;
            } else {
                continue;
            }
            for (final FunctionElement e : functions) {
            	addFuncUris(startupFnUris, e);
            }
        }
        return startupFnUris.toArray(new String[startupFnUris.size()]);
    }


    private static void addFuncUris(ArrayList<String> startupFnUris, FunctionElement e) {
    	if (e instanceof AgentFunctionsGrpElement) {
    		ArrayList<FunctionElement> funcList = ((AgentFunctionsGrpElement) e).functionsGrp.functions;
    		for (FunctionElement functionElement : funcList) {
    			addFuncUris(startupFnUris, functionElement);
			}
    	} else if (e instanceof FunctionsGrp) {
    		ArrayList<FunctionElement> funcList = ((FunctionsGrp) e).functions;
    		for (FunctionElement functionElement : funcList) {
    			addFuncUris(startupFnUris, functionElement);
			}
    	} else {
    		startupFnUris.add(((Function) e).uri);
    	}
	}


	public static void getAgentClassesDestinations(
            ClusterConfigModelMgr modelmgr,
            Map<String, Map<String, String>> agentDestinationPreProcessorMap)
    {
        ArrayList<AgentClass> agents = modelmgr.getAgentClasses();
        for (AgentClass agent: agents) {
            if(agent.type.equals(AgentClass.AGENT_TYPE_INFERENCE)){
                InfAgent inferenceAgent = (InfAgent)agent ;
                ArrayList<DestinationElement> agentDestinations = inferenceAgent.agentDestinationsGrpObj.agentDestinations;
                Map<String, String> destinationPreProcessorMap = new HashMap<String, String>();
                agentDestinationPreProcessorMap.put(inferenceAgent.name, destinationPreProcessorMap);
                processDestinationGroups(agentDestinations, destinationPreProcessorMap);
            }
            else if(agent.type.equals(AgentClass.AGENT_TYPE_QUERY)){
                QueryAgent queryAgent = (QueryAgent)agent ;
                ArrayList<DestinationElement> agentDestinations = queryAgent.agentDestinationsGrpObj.agentDestinations;
                Map<String, String> destinationPreProcessorMap = new HashMap<String, String>();
                agentDestinationPreProcessorMap.put(queryAgent.name, destinationPreProcessorMap);
                processDestinationGroups(agentDestinations, destinationPreProcessorMap);
            }
            else if(agent.type.equals(AgentClass.AGENT_TYPE_DASHBOARD)){
                DashboardAgent dashboardAgent = (DashboardAgent)agent ;
                ArrayList<DestinationElement> agentDestinations = dashboardAgent.agentDestinationsGrpObj.agentDestinations;
                Map<String, String> destinationPreProcessorMap = new HashMap<String, String>();
                agentDestinationPreProcessorMap.put(dashboardAgent.name, destinationPreProcessorMap);
                processDestinationGroups(agentDestinations, destinationPreProcessorMap);
            }
        }
    }

    /**
     * @param agentDestinations
     * @return
     */
    private static void processDestinationGroups(ArrayList<DestinationElement> agentDestinations, Map<String, String> map){
        for(DestinationElement destinationElement:agentDestinations){
            if(destinationElement instanceof DashInfProcQueryAgent.AgentDestinationsGrpElement){
                DestinationsGrp destGroup=((DashInfProcQueryAgent.AgentDestinationsGrpElement)destinationElement).destinationsGrp;
                for(DestinationElement dest:destGroup.destinations){
                    if (! (dest instanceof Destination))
                        continue;
                    Destination destination = (Destination)dest;
                    String destinationURI = destination.destinationVal.get(Elements.URI.localName);
                    String preProcessor = destination.destinationVal.get(Elements.PRE_PROCESSOR.localName);
                    map.put(destinationURI, preProcessor);
                }
            }

            if (! (destinationElement instanceof Destination))
                continue;
            Destination destination = (Destination)destinationElement;
            String destinationURI = destination.destinationVal.get(Elements.URI.localName);
            String preProcessor = destination.destinationVal.get(Elements.PRE_PROCESSOR.localName);
            map.put(destinationURI, preProcessor);
        }
    }

}