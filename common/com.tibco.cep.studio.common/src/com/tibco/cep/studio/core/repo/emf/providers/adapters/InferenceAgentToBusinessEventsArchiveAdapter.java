package com.tibco.cep.studio.core.repo.emf.providers.adapters;


import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.ObjectManagerConfig;
import com.tibco.be.util.config.cdd.RulesConfig;
import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE;
import com.tibco.cep.designtime.core.model.archive.CACHE_CONFIG_TYPE;
import com.tibco.cep.designtime.core.model.archive.InputDestination;
import com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE;
import com.tibco.cep.designtime.core.model.archive.impl.BusinessEventsArchiveResourceImpl;

/*
* User: Nicolas Prade
* Date: Sep 3, 2009
* Time: 7:20:06 PM
*/


public class InferenceAgentToBusinessEventsArchiveAdapter
        extends BusinessEventsArchiveResourceImpl {


    protected final ClusterConfig clusterConfig;
    protected final DestinationsAdapter destinationsAdapter;
    protected final InferenceAgentClassConfig agentClassConfig;
    protected final ObjectManagerAdapter objectManagerAdapter;


    public InferenceAgentToBusinessEventsArchiveAdapter(
            ClusterConfig config,
            InferenceAgentClassConfig agentClassConfig,
            String agentKey) {
        super();
        this.agentClassConfig = agentClassConfig;
        this.clusterConfig = config;
        this.destinationsAdapter = new DestinationsAdapter(config,
        		agentClassConfig.getDestinations(), this,
                ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INPUT_DESTINATIONS);
        this.objectManagerAdapter = new ObjectManagerAdapter(
        		(ObjectManagerConfig) config.getObjectManagement().eContents().get(0));
        super.setName(agentKey);
        super.setArchiveType(null);
    }


    @Override
    public EList<AdvancedEntitySetting> getAdvancedSettings() {
        return null;//todo
    }


    protected InferenceAgentClassConfig getAgentGroupConfig() {
        return this.agentClassConfig;
    }


    @Override
    public String getAgentGroupName() {
        return this.agentClassConfig.getId();
    }


    @Override
    public BE_ARCHIVE_TYPE getArchiveType() {
        return BE_ARCHIVE_TYPE.INFERENCE;
    }


    @Override
    public CACHE_CONFIG_TYPE getCacheConfigType() {
        return null; //todo
    }


    public ClusterConfig getClusterConfig() {
        return this.clusterConfig;
    }


    @Override
    public EList<String> getIncludedRuleSets() {
        return this.getIncludedRuleSets(this.agentClassConfig.getRules());
    }


    private EList<String> getIncludedRuleSets(
            RulesConfig rulesConfig) {
        final EList<String> uris = new EDataTypeUniqueEList<String>(String.class, this,
                ArchivePackage.BUSINESS_EVENTS_ARCHIVE_RESOURCE__INCLUDED_RULE_SETS);
        uris.addAll(rulesConfig.getAllUris());
        return uris;
    }


    @Override
    public EList<InputDestination> getInputDestinations() {
        return this.destinationsAdapter.getInputDestinations();
    }


    public OBJECT_MGMT_TYPE getObjectMgmtType() {
        return this.objectManagerAdapter.getType();
    }


    public int getOmCacheSize() {
        return this.objectManagerAdapter.getCacheSize();
    }


    public int getOmCheckPtInterval() {
        return this.objectManagerAdapter.getCheckpointInterval();
    }


    public int getOmCheckPtOpsLimit() {
        return this.objectManagerAdapter.getCheckpointOpsLimit();
    }


    public String getOmDbEnvironmentDir() {
        return this.objectManagerAdapter.getDbEnvironmentDir();
    }


    public String getOmtgCacheConfigFile() {
        return this.objectManagerAdapter.getCacheConfigFile();
    }


    public String getOmtgCustomRecoveryFactory() {
        return this.objectManagerAdapter.getCustomRecoveryFactory();
    }


    public String getOmtgGlobalCache() {
        return this.getName();
    }


    @Override
    public EList<String> getShutdownActions() {
        return null; //todo
    }


    @Override
    public EList<String> getStartupActions() {
        return null; //todo
    }


    @Override
    public boolean isAllDestinations() {
        return false; //todo
    }


    @Override
    public boolean isAllRuleSets() {
        return false; //todo
    }


    @Override
    public boolean isOmDeletePolicy() {
        return false; //todo
    }


    @Override
    public boolean isOmNoRecovery() {
        return false; //todo
    }


    @Override
    public void setAgentGroupName(String newAgentGroupName) {
        super.setAgentGroupName(newAgentGroupName);    //To change body of overridden methods use File | Settings | File Templates.
    }


    @Override
    public void setAllDestinations(boolean newAllDestinations) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setAllRuleSets(boolean newAllRuleSets) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setArchiveType(BE_ARCHIVE_TYPE newArchiveType) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setAuthor(String newAuthor) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setCacheConfigType(CACHE_CONFIG_TYPE newCacheConfigType) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setCompilePath(String newCompilePath) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setCompileWithDebug(boolean newCompileWithDebug) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setDeleteTempFiles(boolean newDeleteTempFiles) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setDescription(String newDescription) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setExtraClassPath(String newExtraClassPath) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setObjectMgmtType(OBJECT_MGMT_TYPE newObjectMgmtType) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmCacheSize(int newOmCacheSize) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmCheckPtInterval(int newOmCheckPtInterval) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmCheckPtOpsLimit(int newOmCheckPtOpsLimit) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmDbEnvironmentDir(String newOmDbEnvironmentDir) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmDeletePolicy(boolean newOmDeletePolicy) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmNoRecovery(boolean newOmNoRecovery) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmtgCacheConfigFile(String newOmtgCacheConfigFile) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmtgCustomRecoveryFactory(String newOmtgCustomRecoveryFactory) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setOmtgGlobalCache(String newOmtgGlobalCache) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setName(String newName) {
        throw new UnsupportedOperationException();
    }

}