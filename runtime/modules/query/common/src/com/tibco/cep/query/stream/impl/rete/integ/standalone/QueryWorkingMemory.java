package com.tibco.cep.query.stream.impl.rete.integ.standalone;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.concurrent.Guard;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.DefaultGuard;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.impl.DefaultExceptionHandler;
import com.tibco.cep.kernel.service.impl.DefaultTimeManager;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.stream.core.Registry;

/*
 * Author: Ashwin Jayaprakash Date: Mar 7, 2008 Time: 2:52:46 PM
 */

public class QueryWorkingMemory extends WorkingMemoryImpl {
    private final Guard defaultGuard;

    protected QueryObjectManager objectManager;

    protected String regionName;

    /**
     * @param regionName
     * @param objectManager This OM's lifecycle will be maintained entirely by this WM.
     */
    //TODO - NEED TO PASS IN THE LOGMANAGER
    public QueryWorkingMemory(String regionName, QueryObjectManager objectManager, LogManager logManager) {
        super(regionName, logManager, new DefaultExceptionHandler(findLogger()), objectManager,
                new DefaultTimeManager(new Action() {
                    public void execute(Object[] objects) {
                    }

                    public Identifier[] getIdentifiers() {
                        return null;
                    }

                    public Rule getRule() {
                        return null;
                    }
                }), false, false);

        this.defaultGuard = new DefaultGuard();
        this.objectManager = objectManager;
        this.regionName = regionName;
    }

    private static Logger findLogger() {
        return (Logger) Registry.getInstance().getComponent(
                com.tibco.cep.query.stream.monitor.Logger.class);
    }

    public QueryObjectManager getObjectManager() {
        return objectManager;
    }

    public boolean isConcurrent() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Invokes {@link #start()}.
     *
     * @param activeMode Ignored parameter.
     * @throws Exception
     */
    public void start(boolean activeMode) throws Exception {
        start();
    }

    public void start() throws Exception {
        objectManager.init(this);
        objectManager.start();
    }

    public void stop() throws Exception {
        objectManager.stop();
    }
    
    public void suspend() {}
    public void resume() {}

    public Guard getGuard() {
        return defaultGuard;
    }

    // ----------

//    @Override
//    public void executeRules(String description, List modifyElements, List dirtyBitMap,
//                             List deletedObjects, List reloaded, List reevaluate) throws DuplicateExtIdException {
//        throw new UnsupportedOperationException();
//    }

    @Override
    public Rule addRule(Rule rule) throws SetupException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void applyObjectToAddedRules() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Handle assertHandleInternal(BaseHandle handle) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void eventExpiryActions(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void evictHandle(BaseHandle handle) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void applyElementChanges(String description, long id,
    		Class entityClz, int[] dirtyBits, List reloadFromCacheObjects,
    		List assertHandles, LinkedHashSet deletedObjects,
    		List reevaluateObjects, boolean reloadOnly) {
    	throw new UnsupportedOperationException();
    	
    }

    public void applyDelete(String description, long id, Class entityClz, List reloadFromCacheObjects, List assertHandles, LinkedHashSet deletedObjects, List reevaluate) {
        throw new UnsupportedOperationException();
    }

    public boolean stateChanged(Object obj, int index) {
        throw new UnsupportedOperationException();
    }

    protected boolean stateChangeInternal(BaseHandle handle, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void expireEvent(AbstractEventHandle handle) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[][] findMatches(Rule rule, Object[] argument, boolean executeRuleAction,
                                  boolean dirtyRead) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fireRepeatEvent(Event repeatEvent, int times) throws DuplicateExtIdException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TypeInfo getTypeInfo(Class objectClass) {
        throw new UnsupportedOperationException();
    }

//    @Override
//    public Handle loadEvictedHandle(long id, String extId, Class type)
//            throws DuplicateExtIdException, DuplicateException {
//        throw new UnsupportedOperationException();
//    }

    @Override
    public Handle loadObject(Object obj) throws DuplicateExtIdException, DuplicateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadObjectToAddedRule() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Handle loadScheduleEvent(Event evt, long delay) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void modifyElement(Element element, int[] dirtyBitArray, boolean recordThisModification)
            throws DuplicateExtIdException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean modifyHandleInternal(BaseHandle handle, int[] overrideDirtyBitMap,
                                           boolean recordThisModification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reevaluateElements(long[] ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reevaluateElements(Collection IDs) {
        throw new UnsupportedOperationException();
    }

    public void reevaluateEvents(Collection IDs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerType(Class objectType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Handle reloadObject(Object obj) throws DuplicateExtIdException, DuplicateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Rule removeRule(Rule rule) throws SetupException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean retractHandleInternal(BaseHandle handle) {
        throw new UnsupportedOperationException();
    }

    protected Handle assertHandleInternal(BaseHandle handle, boolean forwardChain) {
        throw new UnsupportedOperationException();
    }

    protected boolean modifyHandleInternal(BaseHandle handle, int[] overrideDirtyBitMap, boolean recordThisModification, boolean forwardChain) {
        throw new UnsupportedOperationException();
    }

    protected boolean retractHandleInternal(BaseHandle handle, boolean forwardChain) {
        throw new UnsupportedOperationException();
    }

//    @Override
//    public boolean unloadElement(long id) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean unloadEvent(long id) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean unloadObject(Object obj) {
//        throw new UnsupportedOperationException();
//    }

    public Handle assertObject(Object obj, boolean executeRule) throws DuplicateExtIdException {
        throw new UnsupportedOperationException();
    }

    public void clearWarningMessages() {
        throw new UnsupportedOperationException();
    }

    public void setConcurrentMode(boolean isConcurrent) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void executeRules() {
        throw new UnsupportedOperationException();
    }

    public boolean getActiveMode() {
        throw new UnsupportedOperationException();
    }

    public String getWarningMessages() {
        throw new UnsupportedOperationException();
    }

    public void init(Action startup, Action activate, Set rules) throws Exception {
        throw new UnsupportedOperationException();
    }

    public void invoke(Action action, Object[] objs) {
        throw new UnsupportedOperationException();
    }

    public Object invoke(RuleFunction function, Object[] args) {
        throw new UnsupportedOperationException();
    }

    public Object invoke(RuleFunction function, Map args) {
        throw new UnsupportedOperationException();
    }

    public boolean modifyObject(Object obj, boolean executeRule, boolean recordThis) {
        throw new UnsupportedOperationException();
    }

    public void reset() {
        throw new UnsupportedOperationException();
    }

    public void retractObject(Object obj, boolean executeRule) {
        throw new UnsupportedOperationException();
    }

    public void setActiveMode(boolean activeMode) {
        throw new UnsupportedOperationException();
    }

    protected boolean cleanupHandleInternal(BaseHandle handle) {
        throw new UnsupportedOperationException();
    }

    public void stopAndShutdown(Action shutdown) throws Exception {
        throw new UnsupportedOperationException();
    }

    //this method is only needed by DefaultDistributedCacheBasedStore
    public void initEntitySharingLevels() {}
}
