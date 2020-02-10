package com.tibco.cep.studio.core.repo.emf.providers.adapters;


import org.eclipse.emf.common.util.EList;

import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
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


public class CacheAgentToBusinessEventsArchiveAdapter
        extends BusinessEventsArchiveResourceImpl {


    protected final ClusterConfig clusterConfig;
    protected final CacheAgentClassConfig agentGroupConfig;
    protected final ObjectManagerAdapter objectManagerAdapter;


    public CacheAgentToBusinessEventsArchiveAdapter(
            ClusterConfig config,
            CacheAgentClassConfig agentGroupConfig,
            String agentKey) {
        super();
        this.agentGroupConfig = agentGroupConfig;
        this.clusterConfig = config;
        this.objectManagerAdapter = new ObjectManagerAdapter(config.getObjectManagement().getCacheManager());
        super.setName(agentKey);
        super.setArchiveType(null);
    }


    public CacheAgentClassConfig getAgentGroupConfig() {
        return this.agentGroupConfig;
    }


    public ClusterConfig getClusterConfig() {
        return this.clusterConfig;
    }


    public EList<AdvancedEntitySetting> getAdvancedSettings() {
        return null;//todo
    }


    public String getAgentGroupName() {
        return this.agentGroupConfig.getId();
    }


    public BE_ARCHIVE_TYPE getArchiveType() {
        return null;
    }


    public CACHE_CONFIG_TYPE getCacheConfigType() {
        return null; //todo
    }


    public String getCompilePath() {
        return ""; //todo
    }


    public String getExtraClassPath() {
        return ""; //todo
    }


    public EList<String> getIncludedRuleSets() {
        return null;
    }


    public EList<InputDestination> getInputDestinations() {
        return null;//todo
    }


    public OBJECT_MGMT_TYPE getObjectMgmtType() {
        return OBJECT_MGMT_TYPE.CACHE;
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


    public EList<String> getShutdownActions() {
        return null;
    }


    public EList<String> getStartupActions() {
        return null;
    }


    public boolean isAllDestinations() {
        return false;//todo
    }


    public boolean isAllRuleSets() {
        return false;
    }


    public boolean isCompileWithDebug() {
        return false; //todo
    }


    public boolean isDeleteTempFiles() {
        return false; //todo
    }


    public boolean isOmDeletePolicy() {
        return false;//todo
    }


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