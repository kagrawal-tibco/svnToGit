//package com.tibco.cep.repo.provider.adapters;
//
//
//import com.tibco.be.util.config.*;
//import com.tibco.cep.repo.Project;
//import com.tibco.cep.repo.provider.BEArchiveResourceProvider;
//import com.tibco.objectrepo.vfile.VFileStream;
//
//import java.io.InputStream;
//
///*
//* User: Nicolas Prade
//* Date: Sep 4, 2009
//* Time: 6:09:14 PM
//*/
//
//
//public class ProcessingUnitToBEArchiveResourceProviderAdapter
//        extends BEArchiveResourceProvider {
//
//
//    private final ClusterConfig clusterConfig;
//    private final ProcessingUnitConfig puConfig;
//
//
//    public ProcessingUnitToBEArchiveResourceProviderAdapter(
//            ClusterConfig clusterConfig,
//            String processingUnitId) {
//        this.clusterConfig = clusterConfig;
//
//        this.puConfig = clusterConfig.getProcessingUnits().get(processingUnitId);
//        if (null == puConfig) {
//            throw new IllegalArgumentException("Not found");
//        }
//
//        for (AgentConfig agentConfig : puConfig.getAgents()) {
//            final AbstractAgentClassConfig agentClassConfig = agentConfig.getAgentClass();
//            String name = agentConfig.getKey();
//            if (null == name) {
//                name = agentClassConfig.getId();
//            }
//            if (agentClassConfig instanceof CacheAgentClassConfig) {
//                this.beArchives.add(
//                        new CacheAgentToBEArchiveAdapter(clusterConfig,
//                                (CacheAgentClassConfig) agentClassConfig, name));
//            } else if (agentClassConfig instanceof DashboardAgentClassConfig) {
//                this.beArchives.add(
//                        new DashboardAgentToBEArchiveAdapter(clusterConfig,
//                                (DashboardAgentClassConfig) agentClassConfig, name));
//            } else if (agentClassConfig instanceof InferenceAgentClassConfig) {
//                this.beArchives.add(
//                        new InferenceAgentToBEArchiveAdapter(clusterConfig,
//                                (InferenceAgentClassConfig) agentClassConfig, name));
//            } else if (agentClassConfig instanceof QueryAgentClassConfig) {
//                this.beArchives.add(
//                        new QueryAgentToBEArchiveAdapter(clusterConfig,
//                                (QueryAgentClassConfig) agentClassConfig, name));
//            }
//        }
//    }
//
//
//    public int deserializeResource(
//            String uri,
//            InputStream is,
//            Project project,
//            VFileStream stream)
//            throws Exception {
//        if (uri.toLowerCase().endsWith(CONFIGXML)) {
//            return SUCCESS_STOP;
//        } else {
//            return super.deserializeResource(uri, is, project, stream);
//        }
//    }
//
////    public Map getDesignTimeVersions() {
////        return super.getDesignTimeVersions();//todo where do we get this from now?
////    }
//
//
////    public boolean supportsResource(String uri) {
////        return (!uri.toLowerCase().endsWith(CONFIGXML))
////                && super.supportsResource(uri);
////    }
//
//}
