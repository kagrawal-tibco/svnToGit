package com.tibco.cep.kernel.core.rete;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.tibco.cep.kernel.concurrent.Guard;
import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.BaseTimeManager;
import com.tibco.cep.kernel.core.base.EntityHandleMap;
import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.OperationList;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.conflict.AgendaItem;
import com.tibco.cep.kernel.core.rete.conflict.AgendaItemRecycler;
import com.tibco.cep.kernel.core.rete.conflict.ConflictResolver;
import com.tibco.cep.kernel.core.rete.conflict.ScoreBasedConflictResolver;
import com.tibco.cep.kernel.helper.ActionExecutionContext;
import com.tibco.cep.kernel.helper.EventExpiryExecutionContext;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.FunctionExecutionContext;
import com.tibco.cep.kernel.helper.FunctionMapArgsExecutionContext;
import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.entity.Mutable;
import com.tibco.cep.kernel.model.entity.StateMachineElement;
import com.tibco.cep.kernel.model.knowledgebase.ChangeListener;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.ExceptionHandler;
import com.tibco.cep.kernel.service.ResourceManager;
import com.tibco.cep.kernel.service.impl.DefaultTimeManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 17, 2006
 * Time: 5:32:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReteWM extends WorkingMemoryImpl /* implements ReteWMMBean */ {
    /**
     * For the debugger to work, these constants MUST be kept up in synch with the code...
     *
     * RESOLVE_CONFLICT_ENTRY_LINE_NUMBER:
     * This is the first valid line of the resolve conflict method, and looks as follows:
     * AgendaItem item = m_resolver.getNext();
     *
     * CURRENT_RULE_LINE_NUMBER:
     * This is the line in the resolve conflict method that looks as follows:
     * Object[] objs    = item.objects;
     *
     * PERFORM_OPS_LINE_NUMBER:
     * This is the line in the resolve conflict method that looks as follows:
     * m_opList.performOps();
     *
     * RESOLVE_CONFLICT_EXIT_LINE_NUMBER
     * This is the line in the resolve conflict method that looks sa follows:
     * m_opList.retractZeroTTLEvents();
     */
    public static final int RESOLVE_CONFLICT_ENTRY_LINE_NUMBER = 230;
    public static final int CURRENT_RULE_LINE_NUMBER = 239;
    public static final int PERFORM_OPS_LINE_NUMBER = 264;
    public static final int RESOLVE_CONFLICT_EXIT_LINE_NUMBER = 279;

    final protected ReteNetwork      m_rete;
    final private Set              m_nonEqJoinMessages;
    final private Set              m_multiEqJoinMessages;
    private final ThreadLocal      m_resolver=new ThreadLocal();
    private ConflictResolver       l_resolver = ReteWM.createConflictResolver();
    protected ReteDispatcher       m_reteListener;

    protected boolean activeModeOn;

    private CompositeAction m_startup;
    private Action m_activation;

    private ThreadLocal loadingObjects = new ThreadLocal();

    private Guard defaultGuard;
    final Logger logger;
    
    public ReteWM(String name, LogManager logManager, ExceptionHandler expHandler, BaseObjectManager objectManager, BaseTimeManager timeManager, ConflictResolver resolver) {
        this(name, logManager, expHandler, objectManager, timeManager, resolver, false, false);
    }

    public ReteWM(String name, LogManager logManager, ExceptionHandler expHandler,
                  BaseObjectManager objectManager, BaseTimeManager timeManager,
                  ConflictResolver resolver, boolean isMultiEngineMode, boolean isConcurrent) {
        super(name, logManager, expHandler, objectManager, timeManager, isMultiEngineMode, isConcurrent);
        logger = logManager.getLogger(ReteWM.class);
        if(resolver == null) {
            l_resolver = ReteWM.createConflictResolver();
        }
        else {
            l_resolver = resolver;
        }
        m_rete                = new ReteNetwork(this);
        m_nonEqJoinMessages   = new TreeSet();
        m_multiEqJoinMessages = new TreeSet();
        activeModeOn          = false;

        if (this.isConcurrent) {
            defaultGuard= new NoOpGuard();
        } else {
            defaultGuard = new DefaultGuard();
        }

        m_reteListener = new ReteDispatcher();
        // this.registerMBean();
    }

    /////////////////////////////////////////////// Management ///////////////////////////////////////////////
    public boolean getActiveMode() {
        return activeModeOn;
    }

    synchronized public void setActiveMode(boolean activate) {
        if(activeModeOn == activate) return;
        activeModeOn = activate;
        if(activate) {  //change from false to true
            preActivate();
            if (m_startup != null && !m_objectManager.isObjectStore()) {
                invoke(m_startup, null);
                m_startup = null;
            }
            if(m_activation != null)
                invoke(m_activation, null);
        }
    }

    synchronized public void init(
            Action startup,
            Action activation,
            Set rules)
            throws Exception {
        //m_rete.init();
        m_startup = new CompositeActionWrapper(startup);
        _init(activation, rules);
    }
    
    synchronized public void init(
            CompositeAction startup,
            Action activation,
            Set rules)
            throws Exception {
        //m_rete.init();
        m_startup = startup;
        _init(activation, rules);
    }
    
    
    private void _init(Action activation, Set rules) throws Exception {
        m_activation = activation;
        m_objectManager.init(this);
        m_shutdown = false;
        if(rules == null) return;
        for (Object rule : rules) {
            if (rule instanceof Class) {
                final Rule r = this.m_ruleLoader.getRule((Class) rule);
                this.m_ruleLoader.deployRuleToWM(r.getUri());
            } else if (rule instanceof Rule) {
                this.m_ruleLoader.deployRuleToWM(((Rule) rule).getUri());
            } else {
                throw new Exception("Not supporting rule type <" + rule + ">");
            }
        }
        m_ruleTobeApplied.clear();
    }

    synchronized public void start(boolean _activeMode) throws Exception {
        if (m_ruleTobeApplied.size() != 0)
            throw new Exception("Some rules " + m_ruleTobeApplied + " are not being applied");

        //reset the conflict resolver
        cleanResolver();

        boolean chgToPrimary = false;
        if(!activeModeOn && _activeMode)
            chgToPrimary = true;

        activeModeOn = _activeMode;

        //start the rete network
//        m_rete.start();

        //print the rete network
//        if(logger.isEnabledFor(Level.DEBUG)) {
//            String reteNetwork = printReteNetwork();
//            m_logger.log(Level.DEBUG,reteNetwork);
//        }

        m_objectManager.start();

        //call startup function
        if (activeModeOn && m_startup != null) {
        	try {
        		invoke(m_startup, null);
        	} catch (Exception ex) {
                logger.log(Level.ERROR, ex, "Error while invoking startup functions");
            }
            m_startup = null;
        }

        if(m_objectManager.isObjectStore()) {   //set startup to null if support FT
            m_startup = null;
        }

        if(activeModeOn && m_activation != null)
            invoke(m_activation, null);

        m_timeManager.start();
    }

    synchronized public void stop() throws Exception {
        stop(null);
    }
    
    private void stop(CompositeAction shutdown) throws Exception {
        m_timeManager.stop();
//        m_rete.stop();
        if(getActiveMode() && shutdown != null) {
            invoke(shutdown, null);
        }
        m_objectManager.stop();

        //Dynamic Rete networks have a static reference - a mem leak.
        Set rules = m_rete.rules;
        HashSet rulesCopy = new HashSet(rules);
        for (Object o : rulesCopy) {
            Rule rule = (Rule) o;
            removeRule(rule);
        }
        m_rete.reset();
    }

    public void fastReset()
    {

        m_rete.fastReset();

    }

    synchronized public void stopAndShutdown(Action shutdown) throws Exception {
    	stopAndShutdown(new CompositeActionWrapper(shutdown));
    	
    }
    synchronized public void stopAndShutdown(CompositeAction shutdown) throws Exception {
        stop(shutdown);
        if(!m_shutdown) {
            m_timeManager.stop();
            m_timeManager.shutdown();
            m_reteListener.off();
            m_objectManager.shutdown();
            setActiveMode(false);
            m_shutdown = true;
        }
    }

    synchronized public void testerReset() {
    	reset(true);
    }
    
    synchronized public void reset() {
    	reset(false);
    }
    
    private void reset(boolean testerReset) {
        //stop the time manager
        m_timeManager.stop();
        numberOfRulesFired.clear();

        //get all the named instances
        List namedInstances = new LinkedList();
        Iterator ite = getNamedInstanceIterator();
        while(ite.hasNext()) {
            Object next = ite.next();
            if(next != null) {
                namedInstances.add(next);
            }
        }

        //reset all contained components
        m_objectManager.reset();
        m_rete.reset();
        if (testerReset && m_timeManager instanceof DefaultTimeManager) {
        	// this is purely to maintain the existing behavior while properly resetting the tester
        	((DefaultTimeManager) m_timeManager).resetWithContext();
        } else {
        	m_timeManager.reset();
        }

        //reassert the named instance
        ite = namedInstances.iterator();
        while(ite.hasNext()) {
            try {                         //todo - recordAsserted is called here
                Object next = ite.next();
                if(next != null) {
                    assertHandleInternal(getAddElementHandle((Element)next));  //todo - check with Niknuj if ok to reassert - om.addObject again!
                }
            } catch (DuplicateExtIdException e) {
                //impossible
                e.printStackTrace();
            }
        }
        cleanResolver();

        //restart the timer
        //m_timeManager.start();
    }
    
    synchronized public void suspend() {
        m_timeManager.stop();
    }
    
    synchronized public void resume() {
        m_timeManager.start();
    }

    /*
    synchronized public void setReteListener(ReteListener reteListener) {
        m_reteListener = reteListener;
    }
    */

    synchronized public void addReteListener(ReteListener reteListener) {
        m_reteListener.add(reteListener);
    }

    public boolean removeReteListener(ReteListener reteListener) {
        return m_reteListener.remove(reteListener);
    }

    public void cleanResolver() {
        AgendaItem item = getResolver().getNext();
        while (item != null) {
            AgendaItemRecycler.recycle(item);
            item = getResolver().getNext();
        }
        getResolver().reset();
    }

    /////////////////////////////////////////////// Action and Execution ///////////////////////////////////////////////
    private void resolveConflict() {
        ConflictResolver resolver=getResolver();
        resolveConflict(resolver);
    }

    protected void resolveConflict(ConflictResolver resolver) {
        AgendaItem item = resolver.getNext();
        while (item != null) {
            if(item.rule.isActive()) {
                if(logger.isEnabledFor(Level.DEBUG)) {
                    StringBuffer buffer = new StringBuffer();
                    item.printAgendaItem(buffer);
                    resolver.printAgenda(buffer);
                    logger.log(Level.DEBUG,ResourceManager.formatString("wm.ruleAgenda", buffer.toString()));
                }
                Object[] objs    = item.objects;
                Action[] actions = item.rule.getActions();
                incrementRuleFiredCount(item.rule.getUri());
                setCurrentContext(item);
                if(m_reteListener !=null) {
                    m_reteListener.actionStart(ReteListener.ACTION_RULE_ACTION, item.rule);
                }
                for(int i = 0; i < actions.length; i++) {

//todo Why does this swallow the exception?
                    try {
                        if(!m_shutdown) actions[i].execute(objs);
//todo Why does this swallow the exception?
                    }
                    catch(RuntimeException ex) {
                        String exceptStr = ResourceManager.formatString("rule.action.exception",
                                                                        item.rule.getName()+ "(" + item.rule.getDescription() +")",
                                                                        item.rule.getActions()[i],
                                                                        Format.objsToStr(objs));
                        //logger.log(Level.ERROR, ex, exceptStr);
                        m_expHandler.handleRuleException(ex, exceptStr, item.rule.getUri(), objs);
                    }
                }
                for (ChangeListener changeListener : changeListeners) {
                    changeListener.ruleFired(item);
                }
                if (m_reteListener != null)
                    m_reteListener.actionExecuted();

                getOpList().performOps(item.rule.forwardChain());
                if (m_reteListener != null)
                    m_reteListener.actionEnd(getResolver().size());

                resetCurrentContext();  //resetCurrentContext has to be called after performOps
                AgendaItemRecycler.recycle(item);
            }
            else {
                AgendaItemRecycler.recycle(item);
            }
            item = resolver.getNext();
        }
        resolver.reset();
        if(!m_shutdown) {
            OperationList opList= getOpList();
            opList.startTTLTimers();
            opList.retractZeroTTLEvents();
        }
    }

    public void executeRules() {
        getGuard().lock();
        if(m_shutdown) return;
        try {
            new BeTransaction("executeRules") {
                @Override
                protected void doTxnWork()  {
                    WorkingMemory wm = current.get();
                    if (wm == ReteWM.this) {
                        throw new RuntimeException("Can't executeRules inside the WorkingMemory itself");
                    }
                    else if (wm == null) {
                        current.set(ReteWM.this);

                        cleanup(null);

                        try {
                            if (m_reteListener != null) {
                                m_reteListener.rtcStart(ReteListener.RTC_EXECUTE_RULE, null);
                            }
                            resolveConflict();
                        }
                        finally {
                            current.set(null);
                            if (m_reteListener != null) {
                                m_reteListener.rtcResolved();
                                processRecorded();  //apply
                                m_reteListener.rtcEnd();
                            }
                            else {
                                processRecorded();  //apply
                            }
                        }
                    }
                    else {
                        throw new RuntimeException("Can't call executeRules in working memory <" + ReteWM.this +
                                "> from another working memory <" + wm + ">");
                    }
                }
            }.execute();
        } finally {
            getGuard().unlock();
        }
    }

    public void invoke(final Action action, final Object[] objs) {
    	invoke(new CompositeActionWrapper(action), objs);
    }
    protected void invoke(final CompositeAction action, final Object[] objs) {
        getGuard().lock();
        if(!activeModeOn) return;
        try {
/*
    todo For some reason using a transaction here causes problems on agent activation:

    [main] - [runtime.service] [inference-class] Got runtime exception while invoking Action com.tibco.cep.runtime.session.impl.RuleSessionImpl$ActivateAction@4defb0be Objects <>
    java.lang.RuntimeException: Can't assertObject <AdvisoryEvent@id=2> in working memory <inference-class> from another working memory <com.tibco.cep.kernel.core.rete.ReteWM$2@25b8737f>
        at com.tibco.cep.kernel.core.rete.ReteWM.assertObject(ReteWM.java:1596)
        at com.tibco.cep.runtime.session.impl.RuleSessionImpl.assertObject(RuleSessionImpl.java:1220)
        at com.tibco.cep.runtime.session.impl.RuleSessionImpl$ActivateAction.execute(RuleSessionImpl.java:1803)
        at com.tibco.cep.kernel.core.rete.ReteWM$2.doTxnWork(ReteWM.java:413)
        at com.tibco.cep.kernel.core.rete.BeTransaction.run(BeTransaction.java:41)
        at com.kabira.platform.Transaction.execute(Transaction.java:303)
        at com.tibco.cep.kernel.core.rete.ReteWM.invoke(ReteWM.java:384)
        at com.tibco.cep.kernel.core.rete.ReteWM.start(ReteWM.java:182)
        at com.tibco.cep.runtime.session.impl.RuleSessionImpl.start(RuleSessionImpl.java:310)
        at com.tibco.cep.runtime.service.cluster.agent.InferenceAgent.onActivate(InferenceAgent.java:987)
        at com.tibco.cep.runtime.service.cluster.agent.AbstractCacheAgent.activate(AbstractCacheAgent.java:150)
*/
            new BeTransaction("invokeAction") {
                @Override
                protected void doTxnWork() {
                    WorkingMemory wm = current.get();
                    if (wm == ReteWM.this) {
                        try {
                            if (!m_shutdown) {
                                action.execute(objs);
                            }
                        }
                        catch (RuntimeException ex) {
                            String expStr = ResourceManager
                                    .formatString("action.invoke.exception", action, Format.objsToStr(objs));
                            m_expHandler.handleException(ex, expStr);
                        }
                    }
                    else if (wm == null) {
                        if (m_shutdown) {
                            return;
                        }
                        current.set(ReteWM.this);
                        if (m_reteListener != null) {
                            m_reteListener.rtcStart(ReteListener.RTC_INVOKE_ACTION, action);
                            m_reteListener.actionStart(ReteListener.ACTION_INVOKE_ACTION, action);
                        }
                        try {
                            ActionExecutionContext ac = new ActionExecutionContext(action, objs);
                            setCurrentContext(ac);
                            for(int ii = 0, count = action.getComponentCount(); ii < count; ii++) {
	                            try {
	                                action.executeComponent(ii, objs);
	                            }
	                            catch (RuntimeException ex) {
	                                String expStr = ResourceManager
	                                        .formatString("action.invoke.exception", action, Format.objsToStr(objs));
	                                m_expHandler.handleException(ex, expStr);
	                            }
                            }
                            for (ChangeListener changeListener : changeListeners) {
                                changeListener.actionExecuted(ac);
                            }
                            if (m_reteListener != null) {
                                m_reteListener.actionExecuted();
                                if (getOpList().performOps()) { //
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                    m_reteListener.actionEnd(getResolver().size());
                                    resolveConflict();
                                }
                                else {
                                    m_reteListener.actionEnd(0);
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                }
                            }
                            else {
                                if (getOpList().performOps()) { //
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                    resolveConflict();
                                }
                                else {
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                }
                            }
                        }
                        finally {
                            current.set(null);
                            if (m_reteListener != null) {
                                m_reteListener.rtcResolved();
                                processRecorded();  //apply
                                m_reteListener.rtcEnd();
                            }
                            else {
                                processRecorded();  //apply
                            }
                        }
                    }
                    else {
                        throw new RuntimeException("Can't call invoke Action in working memory <" + this +
                                "> from another working memory <" + wm + ">");
                    }
                }
            }.execute();
        } finally {
            getGuard().unlock();
        }
    }

    public Object invoke(final RuleFunction function, final Object[] args) {
        getGuard().lock();
        try {
            final Object[] retValHolder = new Object[1];

            new BeTransaction("invoke-" + function.getSignature()) {
                @Override
                protected void doTxnWork() {
                    if (m_shutdown) {
                        return;
                    }
                    if (!activeModeOn) {
                        throw new RuntimeException("Can't invoke function when Working Memory in deactivate Mode");
                    }
                    WorkingMemory wm = current.get();
                    if (wm == ReteWM.this) {
                        try {
                            cleanup(null);

                            retValHolder[0] = function.invoke(args);

                            return;
                        }
                        catch (RuntimeException ex) {
                            logger.log(Level.ERROR, ResourceManager
                                    .formatString("rulefunction.invoke.exception", function.getSignature(),
                                            Format.objsToStr(args)), ex);
                            throw ex;
                        }
                    }
                    else if (wm == null) {
                        current.set(ReteWM.this);

                        cleanup(null);

                        if (m_reteListener != null) {
                            m_reteListener.rtcStart(ReteListener.RTC_INVOKE_FUNCTION, function);
                            m_reteListener.actionStart(ReteListener.ACTION_INVOKE_FUNCTION, function);
                        }

                        Object retValue = null;
                        RuntimeException error = null;
                        try {
                            FunctionExecutionContext fc = new FunctionExecutionContext(function, args);
                            setCurrentContext(fc);
                            try {
                                retValue = function.invoke(args);
                            }
                            catch (RuntimeException ex) {
                                logger.log(Level.ERROR, ResourceManager
                                        .formatString("rulefunction.invoke.exception", function.getSignature(),
                                                Format.objsToStr(args)), ex);
                                error = ex;
                            }
                            for (ChangeListener changeListener : changeListeners) {
                                changeListener.functionExecuted(fc);
                            }
                            if (m_reteListener != null) {
                                m_reteListener.actionExecuted();
                                if (getOpList().performOps()) { //
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                    m_reteListener.actionEnd(getResolver().size());
                                    resolveConflict();
                                }
                                else {
                                    m_reteListener.actionEnd(0);
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                }
                            }
                            else {
                                if (getOpList().performOps()) { //
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                    resolveConflict();
                                }
                                else {
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                }
                            }

                            if (error != null)   //if there is an err, throw it.
                            {
                                throw error;
                            }
                            else {
                                retValHolder[0] = retValue;
                                return;
                            }
                        }
                        finally {
                            current.set(null);
                            if (m_reteListener != null) {
                                m_reteListener.rtcResolved();
                                processRecorded();  //apply
                                m_reteListener.rtcEnd();
                            }
                            else {
                                processRecorded();  //apply
                            }
                        }
                    }
                    else {
                        throw new RuntimeException("Can't call invoke RuleFunction in working memory <" + ReteWM.this +
                                "> from another working memory <" + wm + ">");
                    }
                }
            }.execute();

            return retValHolder[0];
        } finally {
            getGuard().unlock();
        }
    }

    public Object invoke(final RuleFunction function, final Map args) {
        getGuard().lock();
        try {
            final Object[] retValHolder = new Object[1];

            new BeTransaction("invoke-" + function.getSignature()) {
                @Override
                protected void doTxnWork() {
                    if(m_shutdown) {return;}
                    if(!activeModeOn) throw new RuntimeException("Can't invoke function when Working Memory in deactivate Mode");
                    WorkingMemory wm = current.get();
                    if(wm == ReteWM.this) {
                        try {
                            cleanup(null);

                            retValHolder[0] = function.invoke(args);
                            return;
                        }
                        catch(RuntimeException ex) {
                            logger.log(Level.ERROR,ResourceManager.formatString("rulefunction.invoke.exception", function.getSignature(), args.toString()), ex);
                            throw ex;
                        }
                    }
                    else if (wm == null) {
                        current.set(ReteWM.this);

                        cleanup(null);

                        if(m_reteListener != null) {
                            m_reteListener.rtcStart(ReteListener.RTC_INVOKE_FUNCTION, function);
                            m_reteListener.actionStart(ReteListener.ACTION_INVOKE_FUNCTION, function);
                        }
                        Object retValue = null;
                        RuntimeException error = null;
                        try {
                            FunctionMapArgsExecutionContext fc = new FunctionMapArgsExecutionContext(function, args);
                            setCurrentContext(fc);
                            try {
                                retValue = function.invoke(args);
                            }
                            catch(RuntimeException ex) {
                                logger.log(Level.ERROR,ResourceManager.formatString("rulefunction.invoke.exception", function.getSignature(), args.toString()), ex);
                                error = ex;
                            }
                            for (ChangeListener changeListener : changeListeners) {
                                changeListener.functionExecuted(fc);
                            }
                            if(m_reteListener != null) {
                                m_reteListener.actionExecuted();
                                if(getOpList().performOps()) { //
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                    m_reteListener.actionEnd(getResolver().size());
                                    resolveConflict();
                                }
                                else {
                                    m_reteListener.actionEnd(0);
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                }
                            }
                            else {
                                if(getOpList().performOps()) { //
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                    resolveConflict();
                                }
                                else {
                                    resetCurrentContext();  //resetCurrentContext has to be called after performOps
                                }
                            }

                            if(error != null)   //if there is an err, throw it.
                                throw error;
                            else{
                                retValHolder[0] = retValue;
                                return;
                            }
                        }
                        finally {
                            current.set(null);
                            if(m_reteListener != null) {
                                m_reteListener.rtcResolved();
                                processRecorded();  //apply
                                m_reteListener.rtcEnd();
                            }
                            else {
                                processRecorded();  //apply
                            }
                        }
                    }
                    else {
                        throw new RuntimeException("Can't call invoke RuleFunction in working memory <" + ReteWM.this + "> from another working memory <" + wm + ">");
                    }
                }
            }.execute();

            return retValHolder[0];
        } finally {
            getGuard().unlock();
        }
    }

    public void expireEvent(final AbstractEventHandle handle) {
        getGuard().lock();
        try {
            new BeTransaction("expire-event-"+handle.getEventId()){
                @Override
                protected void doTxnWork() {
                    if (m_shutdown || !activeModeOn) return;
                    if (handle.isRetracted()) return ; //deleted and removed from map
                    current.set(ReteWM.this);
                    try {
                        Event e = (Event)handle.getObject();
                        if(e != null) {
                            if(m_reteListener != null)
                                m_reteListener.rtcStart(ReteListener.RTC_EVENT_EXPIRED, e);
                            if(retractHandleInternal(handle) && e.hasExpiryAction()) {
                                eventExpiryActions((Event) handle.getObject());
                            }
                        }
                    }
                    finally {
                        current.set(null);
                        if(m_reteListener != null) {
                            m_reteListener.rtcResolved();
                            processRecorded();  //apply
                            m_reteListener.rtcEnd();
                        }
                        else {
                            processRecorded();  //apply
                        }
                    }
                }
            }.execute();
        } finally {
            getGuard().unlock();
        }
    }

    protected void eventExpiryActions(Event event) {
        EventExpiryExecutionContext ec = new EventExpiryExecutionContext(event);
        setCurrentContext(ec);
        if(m_reteListener != null)
            m_reteListener.actionStart(ReteListener.ACTION_EVENT_EXPIRY, event);
        try {
            event.onExpiry();
        }
        catch(RuntimeException ex) {
            String expStr = ResourceManager.formatString("event.expiry.exception", event);
            m_expHandler.handleException(ex, expStr);
            //logger.log(Level.ERROR,ResourceManager.formatString("event.expiry.exception", getName(), event), ex);
        }
        for (ChangeListener changeListener : changeListeners) {
            changeListener.eventExpiryExecuted(ec);
        }
        if(m_reteListener != null) {
            m_reteListener.actionExecuted();
            if(getOpList().performOps()) { //
                resetCurrentContext();  //resetCurrentContext has to be called after performOps
                m_reteListener.actionEnd(getResolver().size());
                resolveConflict();
            }
            else {
                m_reteListener.actionEnd(0);
                resetCurrentContext();  //resetCurrentContext has to be called after performOps
            }
        }
        else {
            if(getOpList().performOps()) { //
                resetCurrentContext();  //resetCurrentContext has to be called after performOps
                resolveConflict();
            }
            else {
                resetCurrentContext();  //resetCurrentContext has to be called after performOps
            }
        }
    }

    public Object[][] findMatches(Rule rule, Object[] argument, boolean executeRuleAction, boolean dirtyRead) {
        MatchedList results;
        if(dirtyRead)
            results = this.m_rete.findMatch(rule, argument);
        else {
            synchronized(this) {
                results = this.m_rete.findMatch(rule, argument);
            }
        }
        if((results == null) || (results.numRow() == 0))
            return new Object[0][];

        Object[][] ret = new Object[results.numRow()][];
        if(executeRuleAction) {
            int index = 0;
            for(int i = 0; i < results.length(); i++) {
                if(results.getRow(i) == null)
                    continue;
                ret[index] = results.getRow(i);
                Action[] actions = rule.getActions();
                for(int j = 0; j < actions.length; j++) {
                    try {
                        actions[j].execute(ret[index]);
                    }
                    catch(RuntimeException ex) {
                        String expStr = ResourceManager.formatString("rule.invoke.exception", rule, Format.objsToStr(ret[index]));
                        m_expHandler.handleRuleException(ex, expStr, rule.getUri(), ret[index]);
                    }
                }
                index++;
            }
        }
        else {
            int index = 0;
            for(int i = 0; i < results.length(); i++) {
                if(results.getRow(i) == null)
                    continue;
                ret[index] = results.getRow(i);
                index++;
            }
        }
        return ret;
    }

    /////////////////////////////////////////////// Rule Management ///////////////////////////////////////////////

    synchronized public void applyObjectToAddedRules() {
        logger.log(Level.INFO,ResourceManager.getString("ruleloader.applyObjects.start"));
        throw new RuntimeException("not implemented yet");
//        getLogger().logInfo(ResourceManager.getString("ruleloader.applyObjects.end"));
    }

    synchronized public void loadObjectToAddedRule() {
        if(m_ruleTobeApplied.size() == 0) return;
        Map tempEventClassNodes = new HashMap();
        Map tempElementClassNodes = new HashMap();
        Map tempEntityClassNodes = new HashMap();
        Map tempObjectClassNodes = new HashMap();

        logger.log(Level.INFO,ResourceManager.getString("ruleloader.loadObjects.start"));

        Iterator rules = m_ruleTobeApplied.iterator();
        while(rules.hasNext()) {
            Rule rule = (Rule) rules.next();
            m_rete.buildTempClassMaps(rule, false, tempEventClassNodes, tempElementClassNodes, tempEntityClassNodes, tempObjectClassNodes);
        }
        setLoadingObjects(true);
        current.set(this);
        try {
            if(tempEventClassNodes.size() > 0) {
                Iterator ite = getEventHandleIterator();
                while(ite.hasNext()) {
                    BaseHandle handle = (BaseHandle) ite.next();
                    if(!handle.isEvicted() && !handle.isRetracted()) {
                        ClassNode cn = (ClassNode) tempEventClassNodes.get(handle.getTypeInfo().getType());
                        if(cn != null) {
                            cn.assertObject(handle);
                        }
                    }
                }
            }
            if(tempElementClassNodes.size() > 0) {
                Iterator ite = getElementHandleIterator();
                while(ite.hasNext()) {
                    BaseHandle handle = (BaseHandle) ite.next();
                    if(!handle.isEvicted() && !handle.isRetracted()) {
                        ClassNode cn = (ClassNode) tempElementClassNodes.get(handle.getTypeInfo().getType());
                        if(cn != null) {
                            cn.assertObject(handle);
                        }
                    }

                }
            }
            if(tempEntityClassNodes.size() > 0) {
                Iterator ite = getEntityHandleIterator();
                while(ite.hasNext()) {
                    BaseHandle handle = (BaseHandle) ite.next();
                    if(!handle.isEvicted() && !handle.isRetracted()) {
                        ClassNode cn = (ClassNode) tempEntityClassNodes.get(handle.getTypeInfo().getType());
                        if(cn != null) {
                            cn.assertObject(handle);
                        }
                    }
                }
            }
            if(tempObjectClassNodes.size() > 0) {
                Iterator ite = getObjectHandleIterator();
                while(ite.hasNext()) {
                    BaseHandle handle = (BaseHandle) ite.next();
                    if(!handle.isEvicted()&& !handle.isRetracted()) {
                        ClassNode cn = (ClassNode) tempObjectClassNodes.get(handle.getTypeInfo().getType());
                        if(cn != null) {
                            cn.assertObject(handle);
                        }
                    }
                }
            }
            m_ruleTobeApplied.clear();
            logger.log(Level.INFO,ResourceManager.getString("ruleloader.loadObjects.end"));
        }
        finally {
            setLoadingObjects(false);
            current.set(null);
            processRecorded();  //cleanup ref - no profiler hook
        }
    }

    synchronized public Rule addRule(Rule rule) throws SetupException {
        m_ruleTobeApplied.add(rule);
        m_rete.addRule(rule, this.m_nonEqJoinMessages, this.m_multiEqJoinMessages);

        Identifier[] identifiers = rule.getIdentifiers();
        for (int i=0; i < identifiers.length; i++) {
            Class type = identifiers[i].getType();
            if(type != null && Event.class.isAssignableFrom(type)) {
                registerEvent(type);
            }
        }
        return rule;
    }

    synchronized public Rule removeRule(Rule rule) throws SetupException {
        Rule ret = m_rete.removeRule(rule);
        Identifier[] identifiers = rule.getIdentifiers();
        for (int i=0; i < identifiers.length; i++) {
            Class type = identifiers[i].getType();
            if(type != null && Event.class.isAssignableFrom(type)) {
                if(!m_rete.getClassNode(type).hasRule)
                    unregisterEvent(type);
            }
        }
        return ret;
    }

    ///////////////////////////////////////////////// Knowlegdebase /////////////////////////////////////////////////
//    public void executeRulesWithContext() {
//        final RuleExecutionContext ruleContext=RuleExecutionContext.getRuleExecutionContext();
//        if (ruleContext == null) {
//            return;
//        }
//
//        WorkingMemory wm = current.get();
//        if(wm == this) {
//            throw new RuntimeException("Can't executeRules inside the WorkingMemory itself");
//        }
//        else if (wm == null) {
//            current.set(this);
//            getGuard().lock();
//            try {
//            //synchronized(this) {
//
//                new BeTransaction("executeRules") {
//                    @Override
//                    protected void doTxnWork() {
//                        if(m_shutdown) return;
//                        if(m_reteListener != null)
//                            m_reteListener.rtcStart(ReteListener.RTC_POST_PROCESS, ruleContext.getDescription());
//                        try {
//
//                            Iterator repeatTimeEvents= ruleContext.getRepeatTimeEvents();
//                            if (repeatTimeEvents != null) {
//                                while (repeatTimeEvents.hasNext()) {
//                                    Map.Entry entry= (Map.Entry) repeatTimeEvents.next();
//                                    Event obj= (Event) entry.getKey();
//                                    int times= (Integer) entry.getValue();
//                                    ReteWM.this.fireRepeatEvent(obj, times);
//                                }
//                            }
//                            current.set(ReteWM.this);
//                            Iterator added=ruleContext.getAsserted();
//                            ArrayList handles2Assert= new ArrayList();
//                            if (added != null) {
//                                while(added.hasNext()) {
//                                    Object obj= added.next();
//                                    BaseHandle handle =null;
//                                    if (obj instanceof BaseHandle) {
//                                        handle = (BaseHandle) obj;
//                                    } else {
//                                        handle =m_objectManager.getAddHandle(obj);
//                                    }
//                                    handles2Assert.add(handle);
//                                }
//                            }
//                            reloadFromCache(ruleContext);
//
//                            for (Object handle : handles2Assert) {
//                                assertHandleInternal((BaseHandle) handle);
//                            }
//
//                            Iterator retracted = ruleContext.getRetracted();
//                            while(retracted.hasNext()) {
//                                Object obj = retracted.next();
//                                retractObjects_(obj, null);
//                            }
//
//                            Iterator modified=ruleContext.getModified();
//                            if (modified != null) {
//                                while (modified.hasNext()) {
//                                    Map.Entry entry= (Map.Entry) modified.next();
//                                    Element obj= (Element) entry.getKey();
//                                    int [] dirtyBits= (int[]) entry.getValue();
//                                    BaseHandle h=getAddHandle(obj);
//                                    if (!h.isRetracted_OR_isMarkedDelete()) {
//                                        if (h.isAsserted()) {
//                                            ReteWM.this.modifyHandleInternal(h,dirtyBits,false);
//                                        } else {
//                                            ReteWM.this.assertHandleInternal(h);
//                                        }
//                                    }
//                                }
//                            }
//
//                            Iterator cleanup=ruleContext.getCleanupElements();
//                            if (cleanup != null) {
//                                while (cleanup.hasNext()) {
//                                    Long id= (Long) cleanup.next();
//                                    BaseHandle h= getElementHandle(id);
//                                    if (h != null) {
//                                        ReteWM.this.cleanupHandleInternal(h);
//                                    }
//                                }
//                            }
//
//                            cleanup=ruleContext.getCleanupEvents();
//                            if (cleanup != null) {
//                                while (cleanup.hasNext()) {
//                                    Long id= (Long) cleanup.next();
//                                    BaseHandle h= getEventHandle(id);
//                                    if (h != null) {
//                                        ReteWM.this.cleanupHandleInternal(h);
//                                    }
//                                }
//                            }
//
//                            Iterator expiries= ruleContext.getExpiries();
//                            if (expiries != null) {
//                                while (expiries.hasNext()) {
//                                    Object obj= expiries.next();
//                                    AbstractEventHandle h= (AbstractEventHandle) getHandle(obj);
//                                    if (h != null) {
//                                        ReteWM.this.expireEvent(h);
//                                    }
//                                }
//                            }
//
//                            resolveConflict();
//                        }
//                        catch (Exception ex) {
//                            throw new RuntimeException(ex);
//                        }
//                        finally {
//                            current.set(null);
//                            if(m_reteListener != null) {
//                                m_reteListener.rtcResolved();
//                                processRecorded();  //apply
//                                m_reteListener.rtcEnd();
//                            }
//                            else {
//                                processRecorded();  //apply
//                            }
//                        }
//                    }
//                }.execute();
//            } finally {
//                getGuard().unlock();
//            }
//        }
//        else {
//            throw new RuntimeException("WorkingMemory " + getName() + " can't executeRules in WorkingMemory " + this.getName());
//        }
//    }

    public void applyDelete(String description, long id,
                            Class entityClz, List reloadFromCacheObjects,
                            List assertHandles, LinkedHashSet deletedObjects,
                            List reevaluateObjects) {
        WorkingMemory wm = current.get();
        if(wm == this) {
            throw new RuntimeException("Can't executeRules inside the WorkingMemory itself");
        }
        else if (wm == null) {
            current.set(this);
            getGuard().lock();
            try {
            //synchronized(this) {
                if(m_shutdown) return;
                if(m_reteListener != null)
                    m_reteListener.rtcStart(ReteListener.RTC_POST_PROCESS, description);
                try {

                    ArrayList handles2Assert= new ArrayList();
                    ArrayList handles2Load = new ArrayList();

                    if (reloadFromCacheObjects != null) {
                        Iterator ite = reloadFromCacheObjects.iterator();
                        while(ite.hasNext()) {
                            Object obj= ite.next();
                            BaseHandle handle =null;
                            if (obj instanceof BaseHandle) {
                                handle = (BaseHandle) obj;
                            } else {
                                handle =m_objectManager.getHandle(obj);
                                if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                    continue;
                                }
                            }
                            handles2Load.add(handle);
                        }
                    }

                    if (handles2Load.size() > 0) {
                        reloadHandleInternal(handles2Load);
                    }

                    if (assertHandles != null) {
                        Iterator ite = assertHandles.iterator();
                        while(ite.hasNext()) {
                            Object obj= ite.next();
                            BaseHandle handle =null;
                            if (obj instanceof BaseHandle) {
                                handle = (BaseHandle) obj;
                            } else {
                                handle =m_objectManager.getAddHandle(obj);
                            }
                            handles2Assert.add(handle);
                        }
                    }

                    //reloadFromCache(reloadFromCacheObjects);

                    for (Object handle : handles2Assert) {
                        assertHandleInternal((BaseHandle) handle);
                    }

                    if (deletedObjects != null) {
                        Iterator ite = deletedObjects.iterator();
                        while(ite.hasNext()) {
                            Object obj = ite.next();
                            BaseHandle h= this.getAddHandle(obj);
                            if (h != null) {
                                retractHandleInternal(h);
                            }
                            //retractObjects_(obj, null);
                        }
                    }

                    if (Element.class.isAssignableFrom(entityClz)) {
                        BaseHandle h=this.getElementHandle(id);
                        if (h != null) {
                            cleanupHandleInternal(h);
                        }
                    } else if (Event.class.isAssignableFrom(entityClz)) {
                        BaseHandle h=this.getEventHandle(id);
                        if (h != null) {
                            cleanupHandleInternal(h);
                        }
                    }

                    if (reevaluateObjects != null) {
                        List handles2reevaluate = new ArrayList();
                        Iterator ite = reevaluateObjects.iterator();
                        while(ite.hasNext()) {
                            Object obj= ite.next();
                            BaseHandle handle =null;
                            if (obj instanceof BaseHandle) {
                                handle = (BaseHandle) obj;
                            } else {
                                handle =m_objectManager.getAddHandle(obj);
                            }
                            if(handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                continue;
                            }
                            handles2reevaluate.add(handle);
                        }

                        for (Object handle : handles2reevaluate) {
                            reevaluateHandleInternal((BaseHandle) handle);
                        }
                    }
                    resolveConflict();
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    current.set(null);
                    if(m_reteListener != null) {
                        m_reteListener.rtcResolved();
                        processRecorded();  //apply
                        m_reteListener.rtcEnd();
                    }
                    else {
                        processRecorded();  //apply
                    }
                }
            } finally {
                getGuard().unlock();
            }
        }
        else {
            throw new RuntimeException("WorkingMemory " + getName() + " can't executeRules in WorkingMemory " + this.getName());
        }
    }

    public void applyElementChanges(String description, long id, Class entityClz, int [] dirtyBits,
                                List reloadFromCacheObjects, List assertHandles,
                                LinkedHashSet deletedObjects,
                                List reevaluateObjects, boolean loadOnly) {
        WorkingMemory wm = current.get();
        if(wm == this) {
            throw new RuntimeException("Can't executeRules inside the WorkingMemory itself");
        }
        else if (wm == null) {
            current.set(this);
            getGuard().lock();
            try {
            //synchronized(this) {
                if(m_shutdown) return;
                if(m_reteListener != null)
                    m_reteListener.rtcStart(ReteListener.RTC_POST_PROCESS, description);
                try {

                    ArrayList handles2Assert= new ArrayList();
                    ArrayList handles2Load = new ArrayList();

                    if (reloadFromCacheObjects != null) {
                        Iterator ite = reloadFromCacheObjects.iterator();
                        while(ite.hasNext()) {
                            Object obj= ite.next();
                            BaseHandle handle =null;
                            if (obj instanceof BaseHandle) {
                                handle = (BaseHandle) obj;
                            } else {
                                handle =m_objectManager.getHandle(obj);
                            }
                            if (handle != null) {
                                if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                    continue;
                                }
                                handles2Load .add(handle);
                            }
                        }
                    }

                    if (handles2Load.size() > 0) {
                        reloadHandleInternal(handles2Load);
                    }

                    Element changedElement=m_objectManager.getElement(id, entityClz, true, false);
                    if (changedElement != null) {
                        BaseHandle handle=m_objectManager.getAddElementHandle(changedElement);
                        if(loadOnly) {   //nick - added for loadOnly (Andreas issue)
                            setLoadingObjects(true);
                            try {
                                if(!handle.isRetracted_OR_isMarkedDelete()) {
                                    removeFromRete(handle);
                                    putInRete(handle);
                                    //recordTouchHandle(handle);  -- todo - do I need to set this?? Nick
                                }
                            }
                            finally {
                                setLoadingObjects(false);
                            }
                        }
                        else {
                        if (handle.isInRete() ) {
                            // Modify
                            modifyHandleInternal(handle, dirtyBits,false);
                        } else {
                            if(!handle.isRetracted_OR_isMarkedDelete()) {
                                handles2Assert.add(handle);
                            }
                        }
                    }
                    }

                    if (assertHandles != null) {
                        Iterator ite = assertHandles.iterator();
                        while(ite.hasNext()) {
                            Object obj= ite.next();
                            BaseHandle handle =null;
                            if (obj instanceof BaseHandle) {
                                handle = (BaseHandle) obj;
                            } else {
                                handle =m_objectManager.getAddHandle(obj);
                            }
                            handles2Assert.add(handle);
                        }
                    }



                    //reloadFromCache(reloadFromCacheObjects);


                    for (Object handle : handles2Assert) {
                        assertHandleInternal((BaseHandle) handle);
                    }

                    if (deletedObjects != null) {
                        Iterator ite = deletedObjects.iterator();
                        while(ite.hasNext()) {
                            Object obj = ite.next();
                            BaseHandle h= this.getAddHandle(obj);
                            if (h != null) {
                                retractHandleInternal(h);
                            }
                            //retractObjects_(obj, null);
                        }
                    }

                    if (reevaluateObjects != null) {
                        List handles2reevaluate = new ArrayList();
                        Iterator ite = reevaluateObjects.iterator();
                        while(ite.hasNext()) {
                            Object obj= ite.next();
                            BaseHandle handle =null;
                            if (obj instanceof BaseHandle) {
                                handle = (BaseHandle) obj;
                            } else {
                                handle =m_objectManager.getAddHandle(obj);
                            }
                            if(handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                continue;
                            }
                            handles2reevaluate.add(handle);
                        }

                        for (Object handle : handles2reevaluate) {
                            reevaluateHandleInternal((BaseHandle) handle);
                        }
                    }

                    resolveConflict();
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    current.set(null);
                    if(m_reteListener != null) {
                        m_reteListener.rtcResolved();
                        processRecorded();  //apply
                        m_reteListener.rtcEnd();
                    }
                    else {
                        processRecorded();  //apply
                    }
                }
            } finally {
                getGuard().unlock();
            }
        }
        else {
            throw new RuntimeException("WorkingMemory " + getName() + " can't executeRules in WorkingMemory " + this.getName());
        }
    }

    public void executeRules(final String description,
                             final List reloadFromCacheObjects,
                             final List assertHandles,
                             final LinkedHashSet deletedObjects,
                             final List reevaluateObjects) {
        WorkingMemory wm = current.get();
        if(wm == this) {
            throw new RuntimeException("Can't executeRules inside the WorkingMemory itself");
        }
        else if (wm == null) {
            current.set(this);
            getGuard().lock();
            try {
                new BeTransaction("executeRules") {
                    @Override
                    protected void doTxnWork() {
                        if(m_shutdown) return;

                        cleanup(null);

                        if(m_reteListener != null)
                            m_reteListener.rtcStart(ReteListener.RTC_POST_PROCESS, description);
                        ConflictResolver resolver=getResolver();
                        try {
                            ArrayList handles2Assert= new ArrayList();
                            ArrayList handles2Load = new ArrayList();

                            if (reloadFromCacheObjects != null) {
                                Iterator ite = reloadFromCacheObjects.iterator();
                                while(ite.hasNext()) {
                                    Object obj= ite.next();
                                    BaseHandle handle =null;
                                    //AA: as of July 15, 2014 this object will always be instanceof Entity?
                                    if (obj instanceof BaseHandle) {
                                        handle = (BaseHandle) obj;
                                    } else {
                                        handle =m_objectManager.getHandle(obj);
                                    }
                                    if (handle != null) {
                                      //if((handle.isInRete() && handle.getSharingLevel() != EntitySharingLevel.UNSHARED) || handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                        if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                            continue;
                                        }
                                        handles2Load.add(handle);
                                    }
                                }
                            }

                            if (handles2Load.size() > 0)
                                reloadHandleInternal(handles2Load);


                            if (deletedObjects != null) {
                                Iterator ite = deletedObjects.iterator();
                                while(ite.hasNext()) {
                                    Object obj = ite.next();
                                    BaseHandle h= ReteWM.this.getHandle(obj);
                                    if (h != null) {
                                        retractHandleInternal(h);
                                    }
                                    //retractObjects_(obj, null);
                                }
                            }

                            if (assertHandles != null) {
                                Iterator ite = assertHandles.iterator();
                                while(ite.hasNext()) {
                                    Object obj= ite.next();
                                    BaseHandle handle =null;
                                    if (obj instanceof BaseHandle) {
                                        handle = (BaseHandle) obj;
                                    } else {
                                        handle =m_objectManager.getAddHandle(obj);
                                    }
                                    if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                        continue;
                                    }
                                    handles2Assert.add(handle);
                                }
                            }

                            for (Object handle : handles2Assert) {
                                assertHandleInternal((BaseHandle) handle);
                            }

                            if (reevaluateObjects != null) {
                                List handles2reevaluate = new ArrayList();
                                Iterator ite = reevaluateObjects.iterator();
                                while(ite.hasNext()) {
                                    Object obj= ite.next();
                                    BaseHandle handle =null;
                                    if (obj instanceof BaseHandle) {
                                        handle = (BaseHandle) obj;
                                    } else {
                                        handle =m_objectManager.getAddHandle(obj);
                                    }
                                    if(handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
                                        continue;
                                    }
                                    handles2reevaluate.add(handle);
                                }

                                for (Object handle : handles2reevaluate) {
                                    reevaluateHandleInternal((BaseHandle) handle);
                                }
                            }

                            resolveConflict(resolver);
                        }
                        catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        finally {
                            current.set(null);
                            if(m_reteListener != null) {
                                m_reteListener.rtcResolved();
                                processRecorded();  //apply
                                m_reteListener.rtcEnd();
                            }
                            else {
                                processRecorded();  //apply
                            }
                        }
                    }
                }.execute();
            } finally {
                getGuard().unlock();
            }
        }
        else {
            throw new RuntimeException("WorkingMemory " + getName() + " can't executeRules in WorkingMemory " + this.getName());
        }
    }

//    public void executeRules(String description, List modifyElements,
//                             List dirtyBitMap, List deletedObjects,
//                             List reloaded, List reevaluateObjects) throws DuplicateExtIdException{
//        WorkingMemory wm = current.get();
//        if(wm == this) {
//            throw new RuntimeException("Can't executeRules inside the WorkingMemory itself");
//        }
//        else if (wm == null) {
//            current.set(this);
//            getGuard().lock();
//            try {
//
//            //synchronized(this) {
//                if(m_shutdown) return;
//                if(m_reteListener != null)
//                    m_reteListener.rtcStart(ReteListener.RTC_POST_PROCESS, description);
//                try {
//                    current.set(this);
//
//                    //reloadFromCache(reloaded);
//                    Iterator ite = deletedObjects.iterator();
//                    while(ite.hasNext()) {
//                        Handle handle = (Handle) ite.next();
//                        current.set(this);
//                        cleanupHandle_(handle);
//                    }
//                    for (int i=0; i < modifyElements.size(); i++) {
//                        Element obj= (Element) modifyElements.get(i);
//                        int [] bitMap = (int[]) dirtyBitMap.get(i);
//                        current.set(this);
//                        this.modifyElement(obj, bitMap, false);
//                    }
//                    for (int i=0; i < reloaded.size(); i++) {
//                        Element obj= (Element) reloaded.get(i);
//                        //int [] bitMap = (int[]) dirtyBitMap.get(i);
//                        current.set(this);
//                        this.modifyElement(obj, null, false);
//                    }
//
//                    if (reevaluateObjects != null) {
//                        List handles2reevaluate = new ArrayList();
//                        ite = reevaluateObjects.iterator();
//                        while(ite.hasNext()) {
//                            Object obj= ite.next();
//                            BaseHandle handle =null;
//                            if (obj instanceof BaseHandle) {
//                                handle = (BaseHandle) obj;
//                            } else {
//                                handle =m_objectManager.getAddHandle(obj);
//                            }
//                            if(handle.isRetracted_OR_isMarkedDelete() || (handle.getObject() == null)) {
//                                continue;
//                            }
//                            handles2reevaluate.add(handle);
//                        }
//
//                        for (Object handle : handles2reevaluate) {
//                            reevaluateHandleInternal((BaseHandle) handle);
//                        }
//                    }
//
//                    current.set(this);
//                    resolveConflict();
//                }
//                finally {
//                    current.set(null);
//                    if(m_reteListener != null) {
//                        m_reteListener.rtcResolved();
//                        processRecorded();  //apply
//                        m_reteListener.rtcEnd();
//                    }
//                    else {
//                        processRecorded();  //apply
//                    }
//                }
//            } finally {
//                getGuard().unlock();
//            }
//        }
//        else {
//            throw new RuntimeException("WorkingMemory " + getName() + " can't executeRules in WorkingMemory " + this.getName());
//        }
//    }

    public Handle assertObject(Object obj, boolean executeRules) throws DuplicateExtIdException {
        WorkingMemory wm = current.get();
        if(wm == this) {    //from action
            if(obj instanceof Handle) {
               Handle h =(Handle)obj;
               obj = h.getObject();
               if(obj == null) return h;
            }
            return getOpList().opAssertObj(obj);
        }
        else if (wm == null) {  //from outside
            List children = null;
            Handle handle = null;
            current.set(this);
            getGuard().lock();
            try {

            //synchronized(this) {
                if(m_shutdown) return null;
                if(!activeModeOn) {  //workaround for RuleBased TimeEvent in 2nd
                    try {
                        if((obj instanceof AbstractEventHandle) || (obj instanceof Event)){
                            if (logger.isEnabledFor(Level.DEBUG))  //special case for rule based timeevent asserting in secondary
                                logger.log(Level.DEBUG,"WorkingMemory.assertObject() is called for obj <" + obj + "> when active mode is false");
                            return this.loadObject(obj);
                        }
                        else {  ///should never happen
                            throw new RuntimeException("Can't assert Object " + obj + " when activeMode is false");
                        }
                    }
                    catch(DuplicateException ex) {
                        ex.printStackTrace(); //should never happen
                    }
                }
                try {
                    if (obj instanceof Handle) {
                        Handle h = (Handle)obj;
                        obj = h.getObject();
                        if(obj == null) return h;
                    }

                    if (executeRules && m_reteListener != null)
                        m_reteListener.rtcStart(ReteListener.RTC_OBJECT_ASSERTED, obj);

                    if(obj instanceof Element) {
                        children = ((Element)obj).getChildren();
                    }
                    handle = assertObjects_(obj, children);
                    if (executeRules)
                        resolveConflict();
                    return handle;
                }
                finally {
                    current.set(null);
                    if(executeRules) {
                        if(m_reteListener != null) {
                            m_reteListener.rtcResolved();
                            processRecorded();  //apply
                            m_reteListener.rtcEnd();
                        }
                        else {
                            processRecorded();  //apply
                        }
                    }
                }
            } finally {
                getGuard().unlock();
            }
        }
        else { //from other working memory.
            throw new RuntimeException("Can't assertObject <" + obj + "> in working memory <" + this + "> from another working memory <" + wm + ">");
        }
    }

    public boolean modifyObject(Object obj, boolean executeRules, boolean recordThis) {
        WorkingMemory wm = current.get();
        if(wm == this) {    //from action
            if(obj instanceof BaseHandle)
                return getOpList().opModifyObj((BaseHandle)obj, recordThis);
            else
                return getOpList().opModifyObj(getHandle(obj), recordThis);
        }
        else if(wm == null) {   //from outside
            current.set(this);
            getGuard().lock();
            try {

         //   synchronized(this) {
                try {
                    if(obj instanceof BaseHandle) {
                        if(((BaseHandle)obj).isRetracted_OR_isMarkedDelete()) return false;
                        if(executeRules) {
                            if(m_reteListener != null)
                                m_reteListener.rtcStart(ReteListener.RTC_OBJECT_MODIFIED, ((BaseHandle)obj).getObject());
                            return modifyHandle_execute((BaseHandle)obj, recordThis);
                        }
                        else
                            return modifyHandleInternal((BaseHandle)obj, null, recordThis);
                    }
                    else {
                        if(executeRules && m_reteListener != null)
                            m_reteListener.rtcStart(ReteListener.RTC_OBJECT_MODIFIED, obj);

                        BaseHandle handle = getHandle(obj);
                        if(handle == null) {
                            logger.log(Level.WARN,"Can't get the Handle for modifying Object <" + obj + ">");
                            if(executeRules) resolveConflict();
                            return false;
                        }
                        if(executeRules)
                            return modifyHandle_execute(handle, recordThis);
                        else
                            return modifyHandleInternal(handle, null, recordThis);
                    }
                }
                finally {
                    current.set(null);
                    if(executeRules) {
                        if(m_reteListener != null) {
                            m_reteListener.rtcResolved();
                            processRecorded();  //apply
                            m_reteListener.rtcEnd();
                        }
                        else {
                            processRecorded();  //apply
                        }
                    }
                }
            } finally {
                getGuard().unlock();
            }
        }
        else //from another working memory.
            throw new RuntimeException("Can't modifyObject <" + obj + "> in working memory <" + this + "> from another working memory <" + wm + ">");
    }

    public void retractObject(Object obj, boolean executeRules) {
        WorkingMemory wm = current.get();
        if(wm == this) {    //from action
            if(obj instanceof BaseHandle)
                getOpList().opRetractObj((BaseHandle)obj);
            else {
                getOpList().opRetractObj(getHandle(obj));
            }
        }
        else if(wm == null) {    //from outside
            current.set(this);
            getGuard().lock();
            try {
            // synchronized(this) {
                try {
                    if(obj instanceof BaseHandle) {
                        if(executeRules && m_reteListener != null)
                            m_reteListener.rtcStart(ReteListener.RTC_OBJECT_DELETED, ((BaseHandle)obj).getObject());

                        if(obj instanceof AbstractElementHandle) {
                            Element element = (Element) ((AbstractElementHandle)obj).getObject();
                            if(element != null) {
                                List children = element.getChildren();
                                retractObjects_(element, children);
                            }
                        }
                        else {
                            retractHandleInternal((BaseHandle) obj);
                        }
                    }
                    else {
                        if(executeRules && m_reteListener != null)
                            m_reteListener.rtcStart(ReteListener.RTC_OBJECT_DELETED, obj);

                        if(obj instanceof Element) {
                            List children = ((Element)obj).getChildren();
                            retractObjects_(obj, children);
                        }
                        else {
                            retractObjects_(obj, null);
                        }
                    }
                    if(executeRules && !getResolver().isEmpty())
                        resolveConflict();
                }
                finally {
                    current.set(null);
                    if(executeRules) {
                        if(m_reteListener != null) {
                            m_reteListener.rtcResolved();
                            processRecorded();  //apply
                            m_reteListener.rtcEnd();
                        }
                        else {
                            processRecorded();  //apply
                        }
                    }
                }
            } finally {
                getGuard().unlock();
            }
        }
        else //from another working memory.
            throw new RuntimeException("Can't retractObject <" + obj + "> in working memory <" + this + "> from another working memory <" + wm + ">");
    }

    ////////////////////////////////////////// Recover or Synchronization ////////////////////////////////////////
//    public Handle loadEvictedHandle(long id, String extId, Class type) throws DuplicateExtIdException, DuplicateException {
//        try {
//            BaseHandle h;
//            if(Element.class.isAssignableFrom(type)) {
//                h = m_objectManager.loadEvictedElementHandle(id, extId, type);
//            }
//            else if (Event.class.isAssignableFrom(type)) {
//                h = m_objectManager.loadEvictedEventHandle(id, extId, type);
//            }
//            else {
//                throw new RuntimeException("Unsupported type <" + type.getName() + "> for loadEvictedHandle");
//            }
//            h.setAsserted();
//            return h;
//        }
//        catch(Exception ex) {
//            logger.log(Level.ERROR, ex, ex.getMessage());
//            if (ex instanceof DuplicateException)
//                throw ((DuplicateException)ex);
//            if (ex instanceof DuplicateExtIdException)
//                throw ((DuplicateExtIdException)ex);
//            else
//                throw new RuntimeException(ex);
//        }
//    }


    public Handle loadScheduleEvent(Event evt, long delay) {
        m_timeManager.loadScheduleOnceOnlyEvent(evt, delay);
        return getEventHandle(evt);
    }

    synchronized public Handle loadObject(Object obj) throws DuplicateExtIdException, DuplicateException {
        setLoadingObjects(true);
        current.set(this);
        try {
            return loadObject_(obj);
        }
        finally {
            setLoadingObjects(false);
            current.set(null);
            processRecorded();  //cleanup ref - no profiler hook
            if(!this.getOpList().isEmpty()) {
                throw new RuntimeException("OperationList is not empty: " + getOpList().performOpsListToString());
            }
        }
    }

    private Handle loadObject_(Object obj) throws DuplicateExtIdException, DuplicateException {
        try{
            BaseHandle handle= null;
            if(obj instanceof Handle) {
                handle = (BaseHandle)obj;
                obj = handle.getObject();
                if(obj == null) return handle;
            } else {
                handle = getAddHandle(obj);
            }
            //should do the same thing as assertObjectInternal
            if(handle.isAsserted_OR_isRetracted_OR_isMarkedDelete()) return handle;
            handle.setAsserted();
            if (handle instanceof AbstractEventHandle) {
                long ttl = ((Event)obj).getTTL();
                if (ttl >= 0) {
                    m_timeManager.scheduleEventExpiry(handle, ttl);
                    if(ttl == 0 && logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG,"Scheduling 0 TTL Event <" + obj + "> in Recovery or Secondary FT Node");
                    }
                }
            }
            else if (obj instanceof Entity) {
                ((Entity)obj).start(handle);
            }
            putInRete(handle);
            return handle;
        }
        catch(Exception ex) {
            logger.log(Level.ERROR,ex,ex.getMessage());
            if (ex instanceof DuplicateException)
                throw ((DuplicateException)ex);
            if (ex instanceof DuplicateExtIdException)
                throw ((DuplicateExtIdException)ex);
            else
                throw new RuntimeException(ex);
        }
    }

    synchronized public Handle reloadObject(Object obj)  throws DuplicateExtIdException, DuplicateException {
        setLoadingObjects(true);
        current.set(this);
        try {
            if(obj instanceof Handle) {
                Handle h = (Handle)obj;
                obj = h.getObject();
                if(obj == null) return h;
            }
            BaseHandle handle = getHandle(obj);
            if(handle == null) {
                logger.log(Level.WARN,"Not able to get the handle when reloading Object <" + obj + ">, loadObject...");
                return loadObject_(obj);
            }
            else if(handle.isRetracted_OR_isMarkedDelete()) return null;
            removeFromRete(handle);
            putInRete(handle);
            return handle;
        }
        finally {
            setLoadingObjects(false);
            current.set(null);
            processRecorded();  //cleanup ref - no profiler hook
            if(!this.getOpList().isEmpty()) {
                throw new RuntimeException("OperationList is not empty: " + getOpList().performOpsListToString());
            }
        }
    }

//    synchronized public boolean unloadObject(Object obj) {
//        setLoadingObjects(true);
//        current.set(this);
//        try {
//            if(obj instanceof Handle)
//               obj = ((Handle)obj).getObject();
//            if(obj != null) {
//                BaseHandle handle;
//                if(obj instanceof Event) {
//                    handle = removeEventHandle(((Event)obj).getId());
//                    if(handle == null) return false;
//                    ((AbstractEventHandle)handle).cancelTimer();
//    //                ((Event)obj).delete();
//                }
//                else if(obj instanceof Element) {
//                    handle = removeElementHandle(((Element)obj).getId());
//                    if(handle == null) return false;
//    //                ((Element)obj).delete();
//                }
//                else if(obj instanceof Entity) {
//                    handle = removeEntityHandle(((Entity)obj).getId());
//                    if(handle == null) return false;
//    //                ((Entity)obj).delete();
//                }
//                else {
//                    handle = removeObjectHandle(obj);
//                    if(handle == null) return false;
//                }
//                removeFromRete(handle);
//            }
//            return true;
//        }
//        finally {
//            setLoadingObjects(false);
//            current.set(null);
//            processRecorded();  //cleanup ref only - no profiler hook
//            if(!this.getOpList().isEmpty()) {
//                throw new RuntimeException("OperationList is not empty: " + getOpList().performOpsListToString());
//            }
//        }
//    }
//
//    synchronized public boolean unloadElement(long id) {
//        setLoadingObjects(true);
//        current.set(this);
//        try {
//            BaseHandle handle = removeElementHandle(id);
//            if(handle == null) return false;
//            //((Element)obj).delete();  //very dangerous - shortcut for Puneet's stuff
//            removeFromRete(handle);
//            return true;
//        }
//        finally {
//            setLoadingObjects(false);
//            current.set(null);
//            processRecorded();  //cleanup ref only - no profiler hook
//            if(!this.getOpList().isEmpty()) {
//                throw new RuntimeException("OperationList is not empty: " + getOpList().performOpsListToString());
//            }
//        }
//    }
//
//    synchronized public boolean unloadEvent(long id) {
//        setLoadingObjects(true);
//        current.set(this);
//        try {
//            BaseHandle handle = removeEventHandle(id);
//            if(handle == null) return false;
//            ((AbstractEventHandle)handle).cancelTimer();
//            //((Event)obj).delete();  //dangerous - shortcut for Puneet's stuff
//            removeFromRete(handle);
//            return true;
//        }
//        finally {
//            setLoadingObjects(false);
//            current.set(null);
//            processRecorded();  //cleanup ref only - no profiler hook
//            if(!this.getOpList().isEmpty()) {
//                throw new RuntimeException("OperationList is not empty: " + getOpList().performOpsListToString());
//            }
//        }
//    }

    public void modifyElement(Element element, int[] dirtyBitArray, boolean recordThis) throws DuplicateExtIdException {
        WorkingMemory wm = current.get();
//        if(wm == this) {
//            throw new RuntimeException("Can't modifyElement <" + element + "> from working memory itself");
//        }
//        else if (wm == null) {
            current.set(this);
            getGuard().lock();
            try {
                try {
                    BaseHandle handle = getAddElementHandle(element);
                    if(handle.isRetracted_OR_isMarkedDelete())
                        return;
                    if(m_reteListener != null)
                        m_reteListener.rtcStart(ReteListener.RTC_OBJECT_MODIFIED, element);

                    if(handle.isAsserted()) {  //this is modify
                        // handle.setRef(element);
                        this.modifyHandleInternal(handle, dirtyBitArray, recordThis);
                    }
                    else { //this is new
                        assertHandleInternal(handle);
                    }
                    //resolveConflict();
                }
                finally {
                    current.set(null);
                    if(m_reteListener != null) {
                        m_reteListener.rtcResolved();
                        processRecorded();  //apply
                        m_reteListener.rtcEnd();
                    }
                    else
                        processRecorded();  //apply
                    if(!this.getOpList().isEmpty()) {
                        throw new RuntimeException("OperationList is not empty: " + getOpList().performOpsListToString());
                    }
                }
            } finally {
                getGuard().unlock();
            }
//        }
//        else {
//            throw new RuntimeException("Can't modifyElement <" + element + "> in working memory <" + this + "> from another working memory <" + wm + ">");
//        }
    }


    ////////////////////////////////////////// Private Knowlegdebase API ////////////////////////////////////////
    private Handle assertObjects_(Object parent, List children) throws DuplicateExtIdException {
        if(children != null) {
            try {
                Iterator ite = children.iterator();
                while(ite.hasNext()) {
                    assertHandleInternal(getAddElementHandle((Element)ite.next()));
                }
                return assertHandleInternal(getAddElementHandle((Element)parent));
            }
            catch(DuplicateExtIdException ex) {
                Iterator ite = children.iterator();
                while(ite.hasNext()) {
                    Element element = (Element) ite.next();
                    BaseHandle handle = getElementHandle(element);
                    if (handle == null) element.delete();
                    else retractHandleInternal(handle);
                }
                ((Element)parent).delete();
                throw ex;
            }
        } else {
            try {
                return assertHandleInternal(getAddHandle(parent));
            }
            catch(DuplicateExtIdException ex) {
                if(parent instanceof Element) {
                    ((Element)parent).delete();
                }
                throw ex;
            }
        }
    }

    private boolean modifyHandle_execute(Handle handle, boolean recordThis) {
        if(modifyHandleInternal((BaseHandle) handle, null, recordThis)) {
            if(!getResolver().isEmpty()) {   //optimize for event here
                resolveConflict();
            }
            return true;
        }
        else {
            return false;
        }
    }

    private void retractObjects_(Object parent, List children) {
        BaseHandle handle;
        if (children != null) {
            Iterator ite = children.iterator();
            while(ite.hasNext()) {
                handle = getElementHandle((Element) ite.next());
                if(handle != null)
                    retractHandleInternal(handle);
            }
        }
        handle = getHandle(parent);
        if(handle != null)
            retractHandleInternal(handle);
    }

    public void fireRepeatEvent(Event repeatEvent, int times) throws DuplicateExtIdException {
        if(!this.activeModeOn) return;
        current.set(this);
        getRtcOpList().setTrigger(repeatEvent);
        if(m_reteListener != null)
            m_reteListener.rtcStart(ReteListener.RTC_REPEAT_TIMEEVENT, repeatEvent);
        getGuard().lock();
        try {
            try {
                BaseHandle handle = getAddEventHandle(repeatEvent);
                removeFromRete(handle);
                for(int i = 0; i < times; i++) {
                    //assume static time event - 0 TTL, no expiry action
                    handle.setAsserted();
                    repeatEvent.start(handle);

                    putInRete(handle);
                    resolveConflict();

                    if (!handle.isRetracted()) {
                        //time event is not being retracted earlier in rules
    //                    timeEvent.delete();
                        removeFromRete(handle);
                        handle.clearAsserted();
                    }
                    else {
                        //retracted earlier in rules
                        handle = getAddEventHandle(repeatEvent);
                    }
                }
            }
            catch(Exception ex) {
                logger.log(Level.ERROR, ex, ex.getMessage());
                if (ex instanceof DuplicateExtIdException)
                    throw ((DuplicateExtIdException)ex);
                else
                    throw new RuntimeException(ex);
            }
            finally {
                current.set(null);
                if(m_reteListener != null) {
                    m_reteListener.rtcResolved();
                    processRecorded();  //apply
                    m_reteListener.rtcEnd();
                }
                else
                    processRecorded();  //apply
            }
        } finally {
            getGuard().unlock();
        }
    }

//    public void reloadFromCache(RuleExecutionContext ruleContext) {
//        if(ruleContext == null)
//            return;
//        boolean savedFlag = isLoadingObjects();
//        setLoadingObjects(true);
//        try {
//            Iterator reloaded=ruleContext.getReloaded();
//            if (reloaded != null) {
//                while (reloaded.hasNext()) {
//                    Object obj=reloaded.next();
//                    BaseHandle handle = this.getHandle(obj);
//                    if (handle == null) {
//                        try {
//                            handle=this.getAddHandle(obj);
//                            try {
//                                setLoadingObjects(false);
//                                assertHandleInternal(handle);
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            } finally {
//                                setLoadingObjects(true);
//                            }
//                        } catch (DuplicateExtIdException dex) {
//                            dex.printStackTrace();
//                        }
//                    } else {
//                        if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete())
//                            continue;
//                        else {
//                            try {
//                                setLoadingObjects(false);
//                                assertHandleInternal(handle);
//                                this.putInRete(handle);
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            } finally {
//                                setLoadingObjects(true);
//                            }
//                        }
//                    }
//                }
//            }
//        } finally {
//            setLoadingObjects(savedFlag);
//        }
//    }

    protected void reloadHandleInternal(java.util.List<BaseHandle> handles) {
        boolean savedFlag = isLoadingObjects();
        setLoadingObjects(true);
        try {
            Iterator<BaseHandle> all_handles = handles.iterator();
            while (all_handles.hasNext()) {
                BaseHandle h= all_handles.next();
                if(h.isInRete()|| h.isRetracted_OR_isMarkedDelete())
                    continue;
                else {
                    Object obj=h.getObject();
                    if (h instanceof AbstractElementHandle) {
                        ((Element)obj).start(h);
                    }
                    else if (h instanceof EntityHandleMap.EntityHandle) {
                        ((Entity)obj).start(h);
                    }
                    h.setAsserted();
//                    recordAsserted(h);
                    this.putInRete(h);
                }
            }
        } finally {
            setLoadingObjects(savedFlag);
        }
    }

    public void reloadFromCache(Object list_or_obj) {
        if(list_or_obj == null)
            return;
        boolean savedFlag = isLoadingObjects();
        setLoadingObjects(true);
        try {
            if(list_or_obj instanceof List) {
                Iterator ite = ((List)list_or_obj).iterator();
                while(ite.hasNext()) {
                    Object obj = ite.next();
                    BaseHandle handle = this.getHandle(obj);
                    if (handle != null) {
                        if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || handle.isMarkedAssert()) {
                            continue;
                        }
                        if (handle instanceof AbstractElementHandle) {
                            ((Element)obj).start(handle);
                        }
                        else if (handle instanceof EntityHandleMap.EntityHandle) {
                            ((Entity)obj).start(handle);
                        }
                        handle.setAsserted();
                        //recordAsserted(handle);
                        this.putInRete(handle);
                    }
                }
            }
            else {
                BaseHandle handle = this.getHandle(list_or_obj);
                if (handle != null) {
                    if(handle.isInRete()|| handle.isRetracted_OR_isMarkedDelete() || handle.isMarkedAssert()) {
                        return;
                    }
                    if (handle instanceof AbstractElementHandle) {
                        ((Element)list_or_obj).start(handle);
                    }
                    else if (handle instanceof EntityHandleMap.EntityHandle) {
                        ((Entity)list_or_obj).start(handle);
                    }
                    handle.setAsserted();
                    //recordAsserted(handle);
                    this.putInRete(handle);

                }
            }
        }
        finally {
            setLoadingObjects(savedFlag);
        }
    }

//    synchronized public void retrieveObject(Object obj) {
//        WorkingMemory wm = current.get();
//        if (wm == this) {    //from action
//            throw new RuntimeException("Can't retrieveObject <" + obj + "> inside working memory <" + wm + "> itself");
//        } else if (wm == null) {  //from outside
//            current.set(this);
//            try {
//                Exception err = null;
//                try {
//                    BaseHandle handle;
//                    if (obj instanceof Event) {
//                        handle = this.getEventHandle((Event) obj);
//                    } else if (obj instanceof Element) {
//                        handle = this.getElementHandle((Element) obj);
//                    } else if (obj instanceof Entity) {
//                        handle = this.getEntityHandle((Entity) obj);
//                    } else {
//                        handle = this.getObjectHandle(obj);
//                    }
//                    if (handle.isRetracted_OR_isMarkedDelete()) return;
//                    if (!handle.isEvicted())
//                        throw new RuntimeException("Handle for object <" + obj + "> was not evicted earlier");
//                    handle.setRef(obj);
////                    handle.setAsserted();
////                    if (this.activeModeOn)
////                        recordAsserted(handle);
////                    else
////                        throw new RuntimeException("PROGRAM ERROR: Can't retrieveObject <" + handle.getObject() + "> when activeMode is off");
//
//                    putInRete(handle);
//                }
//                catch(Exception ex) {
//                    logger.log(Level.ERROR,"Got exception calling retrieveObject for object <" + obj +">", ex);
//                    err = ex;
//                }
//                resolveConflict();
//                if(err != null)
//                    throw new RuntimeException(err);
//            }
//            finally {
//                current.set(null);
//                processRecorded();
//            }
//        } else  //from other working memory.
//            throw new RuntimeException("Can't retrieveObject <" + obj + "> in working memory <" + this + "> from another working memory <" + wm + ">");
//    }

//    synchronized public void reevaluateElement(long id) {
//        WorkingMemory wm = current.get();
//        if (wm == this) {    //from action
//            throw new RuntimeException("Can't Re-evaluate Element of Id <" + id + "> inside working memory <" + wm + "> itself");
//        } else if (wm == null) {  //from outside
//            current.set(this);
//            try {
//                BaseHandle handle = this.getElementHandle(id);
//                if(handle == null)
//                    throw new RuntimeException("Can't find Element of Id = " + id);
//                if (handle.isRetracted_OR_isMarkedDelete()) return;
//                removeFromRete(handle);
//                putInRete(handle);
//                resolveConflict();
//            }
//            finally {
//                current.set(null);
//                processRecorded();
//            }
//        } else  {//from other working memory.
//            throw new RuntimeException("Can't Re-evaluate Element of Id <" + id + "> in working memory <" + this + "> from another working memory <" + wm + ">");
//        }
//    }

    protected void reevaluateHandleInternal(BaseHandle handle) {
        if (!handle.isAsserted()) {
            assertHandleInternal(handle);
        } else {
            removeFromRete(handle);
            putInRete(handle);
        }
    }

    public void reevaluateElements(long[] ids) {

        WorkingMemory wm = current.get();
        if (wm == this) {    //from action
            String idsStr = "";
            for(int i = 0; i < ids.length; i++) {
                if(i == 0) idsStr += ids[i];
                else idsStr = idsStr + ", " + ids[i];
            }
            throw new RuntimeException("Can't Re-evaluate Element of Ids <" + idsStr + "> inside working memory <" + wm + "> itself");
        } else if (wm == null) {  //from outside
            current.set(this);
            getGuard().lock();
            try {
                try {
                    if(ids == null) return;
                    if(m_reteListener != null)
                        m_reteListener.rtcStart(ReteListener.RTC_REEVALUATE_ELEMENT, ids);

                    BaseHandle[] handles = new BaseHandle[ids.length];
                    for(int i = 0; i < ids.length; i++) {
                        BaseHandle handle = this.getElementHandle(ids[i]);
                        if (handle != null) {
                            if(handle == null || handle.isRetracted_OR_isMarkedDelete())
                                handles[i] = null;
                            else {
                                handles[i] = handle;
                                removeFromRete(handle);
                            }
                        } else {
                            Element elem= m_objectManager.getElement(ids[i]);
                            if (elem != null) {
                                try {
                                    BaseHandle h=m_objectManager.getAddElementHandle(elem);
                                    if (h != null) {
                                        handles[i]=h;
                                    }
                                } catch (DuplicateExtIdException dex) {
                                    throw new RuntimeException(dex);
                                }
                            }
                        }
                    }
                    for(int i = 0; i < ids.length; i++) {
                        if(handles[i] != null)
                            putInRete(handles[i]);
                    }
                    resolveConflict();
                }
                finally {
                    current.set(null);
                    if(m_reteListener != null) {
                        m_reteListener.rtcResolved();
                        processRecorded();  //apply
                        m_reteListener.rtcEnd();
                    }
                    else
                        processRecorded();  //apply
                }
            } finally {
                getGuard().unlock();
            }
            } else  {//from other working memory.
                String idsStr = "";
                for(int i = 0; i < ids.length; i++) {
                    if(i == 0) idsStr += ids[i];
                    else idsStr = idsStr + ", " + ids[i];
                }
                throw new RuntimeException("Can't Re-evaluate Element of Ids <" + idsStr + "> in working memory <" + this + "> from another working memory <" + wm + ">");
            }

    }

    public void reevaluateElements(Collection IDs) {
        WorkingMemory wm = current.get();
        if (wm == this) {    //from action
            throw new RuntimeException("Can't Re-evaluate Element of Ids <" + IDs + "> inside working memory <" + wm + "> itself");
        } else if (wm == null) {  //from outside
            current.set(this);
            getGuard().lock();
            try {

                try {
                    if((IDs == null) || (IDs.size() ==0)) return;
                    if(m_reteListener != null)
                        m_reteListener.rtcStart(ReteListener.RTC_REEVALUATE_ELEMENT, IDs);

                    BaseHandle[] handles = new BaseHandle[IDs.size()];
                    Iterator allIDs= IDs.iterator();
                    int cur=0;
                    while (allIDs.hasNext()) {
                        long id= ((Long) allIDs.next()).longValue();
                        BaseHandle handle = this.getElementHandle(id);
                        if (handle != null) {
                            if(handle == null || handle.isRetracted_OR_isMarkedDelete())
                                handles[cur] = null;
                            else {
                                handles[cur] = handle;
                                removeFromRete(handle);
                            }
                        } else {
                            Element elem= m_objectManager.getElement(id);
                            if (elem != null) {
                                try {
                                    BaseHandle h=m_objectManager.getAddElementHandle(elem);
                                    if (h != null) {
                                        handles[cur]=h;
                                    }
                                } catch (DuplicateExtIdException dex) {
                                    throw new RuntimeException(dex);
                                }
                            }
                        }

                        ++cur;
                    }
                    for(int i = 0; i < IDs.size(); i++) {
                        if(handles[i] != null)
                            putInRete(handles[i]);
                    }
                    resolveConflict();
                }
                finally {
                    current.set(null);
                    if(m_reteListener != null) {
                        m_reteListener.rtcResolved();
                        processRecorded();  //apply
                        m_reteListener.rtcEnd();
                    }
                    else
                        processRecorded();  //apply
                }
            } finally {
                getGuard().unlock();
            }
        } else  {//from other working memory.
            throw new RuntimeException("Can't Re-evaluate Element of Ids <" + IDs + "> in working memory <" + this + "> from another working memory <" + wm + ">");
        }
    }

    synchronized public void reevaluateEvents(Collection IDs) {
        WorkingMemory wm = current.get();
        if (wm == this) {    //from action
            throw new RuntimeException("Can't Re-evaluate Element of Ids <" + IDs + "> inside working memory <" + wm + "> itself");
        } else if (wm == null) {  //from outside
            current.set(this);
            getGuard().lock();
            try {


                try {
                    if((IDs == null) || (IDs.size() ==0)) return;
                    if(m_reteListener != null)
                        m_reteListener.rtcStart(ReteListener.RTC_REEVALUATE_ELEMENT, IDs);

                    BaseHandle[] handles = new BaseHandle[IDs.size()];
                    Iterator allIDs= IDs.iterator();
                    int cur=0;
                    while (allIDs.hasNext()) {
                        long id= ((Long) allIDs.next()).longValue();
                        BaseHandle handle = this.getEventHandle(id);
                        if (handle != null) {
                            if(handle == null || handle.isRetracted_OR_isMarkedDelete())
                                handles[cur] = null;
                            else {
                                handles[cur] = handle;
                                removeFromRete(handle);
                            }
                        } else {
                            Event evt= m_objectManager.getEvent(id);
                            if (evt != null) {
                                try {
                                    BaseHandle h=m_objectManager.getAddEventHandle(evt);
                                    if (h != null) {
                                        handles[cur]=h;
                                    }
                                } catch (DuplicateExtIdException dex) {
                                    throw new RuntimeException(dex);
                                }
                            }
                        }
                        ++cur;
                    }
                    for(int i = 0; i < IDs.size(); i++) {
                        if(handles[i] != null)
                            putInRete(handles[i]);
                    }
                    resolveConflict();
                }
                finally {
                    current.set(null);
                    if(m_reteListener != null) {
                        m_reteListener.rtcResolved();
                        processRecorded();  //apply
                        m_reteListener.rtcEnd();
                    }
                    else
                        processRecorded();  //apply
                }
            } finally {
                getGuard().unlock();
            }
        } else  {//from other working memory.
            throw new RuntimeException("Can't Re-evaluate Element of Ids <" + IDs + "> in working memory <" + this + "> from another working memory <" + wm + ">");
        }
    }

//    synchronized public void retrieveObject(long id, Class type) {
//        WorkingMemory wm = current.get();
//        if (wm == this) {    //from action
//            String objStr = type.getName() + "@id=" + id;
//            throw new RuntimeException("Can't retrieveObject <" + objStr + "> inside working memory <" + wm + "> itself");
//        } else if (wm == null) {  //from outside
//            current.set(this);
//            try {
//                Exception err = null;
//                try {
//                    BaseHandle handle;
//                    if (Event.class.isAssignableFrom(type)) {
//                        handle = this.getEventHandle(id);
//                    } else if (Element.class.isAssignableFrom(type)) {
//                        handle = this.getElementHandle(id);
//                    } else if (Entity.class.isAssignableFrom(type)) {
//                        handle = this.getEntityHandle(id);
//                    } else {
//                        String objStr = type.getName() + "@id=" + id;
//                        throw new RuntimeException("Can't retrieveObject <" + objStr + "> for object");
//                    }
//                    if (handle.isRetracted_OR_isMarkedDelete()) return;
//                    if (!handle.isEvicted()) {
//                        String objStr = type.getName() + "@id=" + id;
//                        throw new RuntimeException("Handle for <" + objStr + "> was not evicted earlier");
//                    }
//                    putInRete(handle);
//                }
//                catch(Exception ex) {
//                    String objStr = type.getName() + "@id=" + id;
//                    logger.log(Level.ERROR,"Got exception calling retrieveObject for object <" + objStr +">", ex);
//                    err = ex;
//                }
//                resolveConflict();
//                if(err != null)
//                    throw new RuntimeException(err);
//            }
//            finally {
//                current.set(null);
//                processRecorded();
//            }
//        } else  {//from other working memory.
//            String objStr = type.getName() + "@id=" + id;
//            throw new RuntimeException("Can't retrieveObject <" + objStr + "> in working memory <" + this + "> from another working memory <" + wm + ">");
//        }
//    }


//    public void evictObject(Object obj) {
//        BaseHandle handle;
//        if(obj instanceof Element) {
//            List children = ((Element)obj).getChildIds();
//            if(children != null) {
//                Iterator ite = children.iterator();
//                while(ite.hasNext()) {
//                    handle = getElementHandle(((Long) ite.next()).longValue());
//                    if(handle != null)
//                        evictHandleInternal(handle);
//                }
//            }
//            handle = getElementHandle((Element) obj);
//            if(handle != null)
//                evictHandleInternal(handle);
//        }
//        else {
//            handle = getHandle(obj);
//            if(handle != null)
//                evictHandleInternal(handle);
//        }
//    }
    /////////////////////////////////////////////// Support ///////////////////////////////////////////////
    public void evictHandle(BaseHandle handle) {
        if (handle.isRetracted()) return;
        removeFromRete(handle);
        handle.evict();
    }

    protected Handle assertHandleInternal(BaseHandle handle) {
        return assertHandleInternal(handle, true);
    }

    protected Handle assertHandleInternal(BaseHandle handle, boolean forwardChain) {
        if(handle.isAsserted_OR_isRetracted_OR_isMarkedDelete()) return handle;
        handle.setAsserted();
        Object obj = handle.getObject();
        if(obj != null) {
            if (handle instanceof AbstractEventHandle) {
                ((Event)obj).start(handle);
                long ttl = ((Event)obj).getTTL();
                if (ttl == 0) {
                    getOpList().zeroTTLEvent((AbstractEventHandle) handle);
                }
                else if(ttl > 0) {
                    getOpList().nonZeroTTLEvent((AbstractEventHandle) handle);
                }
            }
            else if (handle instanceof AbstractElementHandle) {
                ((Element)obj).start(handle);
            }
            else if (handle instanceof EntityHandleMap.EntityHandle) {
                ((Entity)obj).start(handle);
            }
        }

        if(this.activeModeOn)
            recordAsserted(handle);
        else
            throw new RuntimeException("PROGRAM ERROR: Can't assert object <" + handle.getObject() + "> when activeMode is off");

        if (forwardChain)
            putInRete(handle);
        return handle;
    }

    protected boolean modifyHandleInternal(BaseHandle handle, int[] overrideDirtyBitMap, boolean recordThisModification) {
        return modifyHandleInternal(handle, overrideDirtyBitMap, recordThisModification, true);
   }

    protected boolean modifyHandleInternal(BaseHandle handle, int[] overrideDirtyBitMap, boolean recordThisModification, boolean forwardChain) {
        if(handle.isRetracted_OR_isMarkedDelete()) return false;
        if(this.activeModeOn) {
            if(recordThisModification)
                recordModified(handle);
            else
                recordTouchHandle(handle);
        }
        else
            throw new RuntimeException("PROGRAM ERROR: Can't modify object <" + handle.getObject() + "> when activeMode is off");
        if (forwardChain) {
            getResolver().objectModified(handle, overrideDirtyBitMap);
            m_rete.modifyObject(handle, overrideDirtyBitMap);
        }
        handle.setInRete();
        Object obj = handle.getObject();
        if(obj instanceof Mutable) {
            getRtcOpList().saveAndClearMutableDirtyBitArray(handle,((Mutable)obj).getDirtyBitArray());  //((Mutable)obj).clearDirtyBitArray()
            ((Mutable) obj).clearChildrenDirtyBits();
            handle.clearDirty();
        }
        return true;
   }

    public boolean stateChanged(Object obj, int index) {
        WorkingMemory wm = current.get();
        if(wm == this) {    //from action
            if(obj instanceof BaseHandle) {
                if (m_rete.stateChangeHasRules((BaseHandle)obj, index)) {
                    return getOpList().opStateChangeObj((BaseHandle)obj, index);
                } else {
                    return true;
                }
            }
            else {
                BaseHandle h= getHandle(obj);
                if (m_rete.stateChangeHasRules(h, index)) {
                    return getOpList().opStateChangeObj(h, index);
                } else {
                    return true;
                }
            }
        }
        else //from another working memory.
            throw new RuntimeException("Can't modifyObject <" + obj + "> in working memory <" + this + "> from another working memory <" + wm + ">");
    }

    protected boolean stateChangeInternal(BaseHandle handle, int index) {
        if(handle.isRetracted_OR_isMarkedDelete()) return false;
        if(this.activeModeOn) {
            recordModified(handle);
        }
        else
            throw new RuntimeException("PROGRAM ERROR: Can't modify object <" + handle.getObject() + "> when activeMode is off");
        //getResolver().objectModified(handle, overrideDirtyBitMap);

        StateMachineElement obj= (StateMachineElement) handle.getObject();
        Element parent=obj.getOwnerElement();
        Handle elementHandle=((BaseObjectManager) getObjectManager()).getHandle(parent);
        getResolver().objectModified(elementHandle, ((Mutable) parent).getDirtyBitArray());

        m_rete.stateChangeObject(handle, index);

//        StringBuffer buf= new StringBuffer();
//        getResolver().printAgenda(buf);
        handle.setInRete();
        Object o = handle.getObject();
        if(o instanceof Mutable) {
            getRtcOpList().saveAndClearMutableDirtyBitArray(handle,((Mutable)o).getDirtyBitArray());  //((Mutable)obj).clearDirtyBitArray()
            ((Mutable) obj).clearChildrenDirtyBits();
            handle.clearDirty();
        }
        return true;
   }

    public boolean retractHandleInternal(BaseHandle handle, RuleExecutionContext ruleContext, boolean forwardChain) {
        if (handle.isRetracted()) {
            return false; //deleted and removed from map
        }
        if (handle instanceof AbstractEventHandle) {
            AbstractEventHandle evtHandle = (AbstractEventHandle) handle;
            removeEventHandle(evtHandle);
            evtHandle.cancelTimer();
//            ((Event)evtHandle.getObject()).delete();
        }  else {
            Object obj = handle.getObject();
            if(obj != null) {
                if (handle instanceof AbstractElementHandle) {
                    removeElementHandle((AbstractElementHandle)handle);
                    ((Element)obj).delete();
                }
                else if (handle instanceof EntityHandleMap.EntityHandle) {
                    Entity entity = (Entity) obj;
                    removeEntityHandle(entity.getId());
    //            entity.delete();
                }
                else {
                    removeObjectHandle(obj);
                }
            }
        }
        if(this.activeModeOn) {
            recordRetracted(handle);   //to be safe, put this here
        }
        else
            throw new RuntimeException("PROGRAM ERROR: Can't retract object <" + handle.getObject() + "> when activeMode is off");

        if (forwardChain) {
            if (ruleContext != null) {
                ruleContext.getResolver().objectRemoved(handle);
            } else {
                getResolver().objectRemoved(handle);
            }
        }
        removeFromRete(handle);
        return true;
    }

    //return true only when the object is asserted and not retracted
    protected boolean retractHandleInternal(BaseHandle handle) {
        return retractHandleInternal(handle, null, true);
    }

    protected boolean retractHandleInternal(BaseHandle handle, boolean forwardChain) {
        return retractHandleInternal(handle, null, forwardChain);
    }

    public void  cleanupHandle(Handle handle) {
        WorkingMemory wm = current.get();
        if(wm == this) {    //from action
            getOpList().opCleanupHandle(handle);
        }
        else if(wm == null) {    //from outside
            current.set(this);
            getGuard().lock();

            try {
                try {
                    this.cleanupHandleInternal((BaseHandle) handle);
                    //todo - execute any rule??
//                    if(!m_resolver.isEmpty())
//                        resolveConflict();
                }
                finally {
                    current.set(null);
                    //todo - execute any rule??
                    //processRecorded??
                }
            } finally {
                getGuard().unlock();
            }
        }
        else //from another working memory.
            throw new RuntimeException("Can't cleanup handle <" + handle + "> in working memory <" + this + "> from another working memory <" + wm + ">");
    }

    private void  cleanupHandle_(Handle handle) {
        this.cleanupHandleInternal((BaseHandle) handle);
    }


    protected boolean cleanupHandleInternal(BaseHandle handle) {
        if (handle.isRetracted()) return false; //deleted and removed from map
        if (handle instanceof AbstractEventHandle) {
            AbstractEventHandle evtHandle = (AbstractEventHandle) handle;
            removeEventHandle(evtHandle);
            evtHandle.cancelTimer();
//            ((Event)evtHandle.getObject()).delete();
        }
        else if (handle instanceof AbstractElementHandle) {
            removeElementHandle((AbstractElementHandle) handle);
//            element.delete();
        }
        else {
            Object obj = handle.getObject();
            if(obj != null) {
                if (handle instanceof EntityHandleMap.EntityHandle) {
                    Entity entity = (Entity) obj;
                    //which is safer to return false or true?
                    //nothing checks the return value at the time of this change
                    removeEntityHandle(entity.getId());
        //            entity.delete();
                }
                else {
                    //which is safer to return false or true?
                    //nothing checks the return value at the time of this change
                    removeObjectHandle(obj);
                }
            }
        }
//        if(this.activeModeOn)
//            recordRetracted(handle);   //to be safe, put this here
//        else
//            throw new RuntimeException("PROGRAM ERROR: Can't retract object <" + handle.getObject() + "> when activeMode is off");
        getResolver().objectRemoved(handle);
        removeFromRete(handle);
        return true;
    }

//    private void modifyInRete(BaseHandle handle) {
//        m_rete.modifyObject(handle);
//        handle.setInRete();
//    }

    protected void putInRete(BaseHandle handle) {
        if(handle.isInRete()) {
            //throw new RuntimeException("Handle " + handle + " already put in Rete");
        }
        else {
        	if(handle instanceof AbstractElementHandle) {
                //clear any dirty bits that may have been set before the object was asserted
                //these should have already been copied into the handle if they need to be saved
        		Object obj = handle.getObject();
                if(obj instanceof Mutable) {
                	((Mutable)obj).clearDirtyBitArray();
                }
        	}
            m_rete.assertObject(handle);
            handle.setInRete();
        }
    }

    private void removeFromRete(BaseHandle handle) {
        if(handle.isInRete()) {
            m_rete.retractObject(handle);
            handle.clearInRete();
        }
    }

    synchronized public String getWarningMessages() {
        String retString = null;
        if(m_nonEqJoinMessages != null && m_nonEqJoinMessages.size() > 0) {
            StringBuffer buf = new StringBuffer();
            Iterator ite = m_nonEqJoinMessages.iterator();
            while(ite.hasNext()) {
                buf.append(Format.BRK).append("\t").append((String)ite.next());
            }
            retString = ResourceManager.formatString("rule.condition.nonEquivalent", buf.toString());
        }
        if(m_multiEqJoinMessages != null && m_multiEqJoinMessages.size() > 0) {
            StringBuffer buf = new StringBuffer();
            Iterator ite = m_multiEqJoinMessages.iterator();
            while(ite.hasNext()) {
                buf.append(Format.BRK).append("\t").append((String)ite.next());
            }
            if(retString == null)
                retString = ResourceManager.formatString("rule.condition.multiEquivalent", buf.toString());
            else
                retString += ResourceManager.formatString("rule.condition.multiEquivalent",buf.toString());
        }
        return retString;
    }

    synchronized public void clearWarningMessages() {
        m_nonEqJoinMessages.clear();
        m_multiEqJoinMessages.clear();
    }

    synchronized public String printReteNetwork() {
        return ResourceManager.formatString("wm.reteNetwork", m_rete.printNetwork());
    }
    
    synchronized public String printReteNetworkXML() {
        return m_rete.printNetworkXML();
    }    

    public TypeInfo getTypeInfo(Class objectType) {
        return (TypeInfo) m_rete.classNodes.get(objectType);
    }

    public void registerType(Class objectType) {
        m_rete.getClassNode(objectType);
    }

    /*
    public ReteListener getReteListener() {
        return m_reteListener;
    }
    */

    public ReteListener getReteListener(Class clazz) {
        //look in the dispatcher
        return m_reteListener.getReteListener(clazz);
    }

    public ConflictResolver getResolver() {
        RuleExecutionContext rc=RuleExecutionContext.getRuleExecutionContext();
        if (rc != null) {
            return rc.getResolver();
        } else {
            if (isConcurrent()) {
                ConflictResolver resolver= (ConflictResolver) m_resolver.get();
                if (resolver == null) {
                    resolver=ReteWM.createConflictResolver();
                    m_resolver.set(resolver);
                }
                return resolver;
            } else {
                return l_resolver;
            }
        }
    }

    public com.tibco.cep.kernel.concurrent.Guard getGuard() {
        return defaultGuard;
    }

    public boolean isLoadingObjects() {
        Boolean isLoading= (Boolean) loadingObjects.get();
        if (isLoading == null) {
            isLoading=Boolean.FALSE;
            loadingObjects.set(isLoading);
        }
        return isLoading.booleanValue();
    }

    public void setLoadingObjects(boolean loadingObjects) {
        this.loadingObjects.set(loadingObjects);
    }
    
    public void initEntitySharingLevels() throws SetupException {
        for(ClassNode cn : (Collection<ClassNode>) m_rete.classNodes.values()) {
            if(cn.m_super == null) {
                initSharingLevelRecursive(cn);
            }
        }
        
        //Now that sharing levels are set properly, initialize the join tables in the ReteNetwork
        for(ClassNode cn : (Collection<ClassNode>) m_rete.classNodes.values()) {
            for(NodeLink ln : cn.classLinks) {
                initJoinTables(ln);
            }
        }
    }
    
    private void initJoinTables(NodeLink link) throws SetupException {
        if(link != null) initJoinTables(link.m_child);
    }
    private void initJoinTables(Node node) throws SetupException {
        if(node != null) {
            if(node instanceof JoinNode) {
                ((JoinNode)node).leftTable.initTableImpl(this);
                ((JoinNode)node).rightTable.initTableImpl(this);
            }
            initJoinTables(node.m_nextNodeLink);
        }
    }
    
    /*
        State machine classes have an EntityConfiguration in CacheAgentConfiguration
        that is the same as their container, so this code should work without special
        cases for state machines.
     */
    private EntitySharingLevel initSharingLevelRecursive(ClassNode cn) {
        EntitySharingLevel localLev = m_objectManager.getLocalSharingLevel(cn.getType());
        if(localLev == null) localLev = EntitySharingLevel.DEFAULT;
        cn.setLocalSharingLevel(localLev);
        
        EntitySharingLevel recursiveLev = localLev;
        for(ClassNode child : (List<ClassNode>)cn.subNodes) {
            EntitySharingLevel childLev = initSharingLevelRecursive(child);
            if(recursiveLev == EntitySharingLevel.UNUSED) {
                recursiveLev = childLev;
            } else if(childLev != EntitySharingLevel.UNUSED && recursiveLev != childLev) { 
                    recursiveLev = EntitySharingLevel.MIXED;
            }
        }
        
        cn.setRecursiveSharingLevel(recursiveLev);
        return recursiveLev;
    }

    static ConflictResolver createConflictResolver() {
        String sResolver = System.getProperty("be.engine.kernel.conflictresolver");
        if(sResolver != null  && !sResolver.equals("")) {
            try {
                Class c = Class.forName(sResolver);
                return (ConflictResolver) c.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return new ScoreBasedConflictResolver();
    }
    
    
    /////////////////////////////// MBean START ///////////////////////////////
    
    /*
    private boolean mbeanRegistered = false;
    
    private void registerMBean() {
    	if (!mbeanRegistered) {
    		mbeanRegistered = true;
	        try {
	            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
	            // ObjectName name = new ObjectName("com.tibco.be:type=ReteWM,service=ReteNetwork-" +
   	            ObjectName name = new ObjectName("com.tibco.be:type=Agent,object=ReteWM,service=ReteNetwork-" +
	            	Thread.currentThread().getId());
	            mbs.registerMBean(this, name);
	        }
	        catch (javax.management.InstanceAlreadyExistsException jmxe) {
	        }
	        catch (Exception ex) {
	            ex.printStackTrace();
	        }
    	}
    } 
    */   
    
    synchronized public void saveReteNetworkToString() {
    	String filename = "retenetwork-threadID-" + Thread.currentThread().getId();
    	this.saveReteNetwork(filename + ".txt", 0);
    }

    synchronized public void saveReteNetworkToString(String filename) {
    	this.saveReteNetwork(filename, 0);
    }

    synchronized public void saveReteNetworkToXML() {
    	String filename = "retenetwork-threadID-" + Thread.currentThread().getId();
    	this.saveReteNetwork(filename+".retenet", 1);
    }

    synchronized public void saveReteNetworkToXML(String filename) {
    	this.saveReteNetwork(filename + ".retenet", 1);
    }
    
    private void saveReteNetwork(String filename, int type) {
    	File file = new File(filename);
        file.getParentFile().mkdirs();
        OutputStream streamToClose = null;

        try {
            FileOutputStream fos = new FileOutputStream(file);
            streamToClose = fos;
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            streamToClose = bos;
            PrintStream ps = new PrintStream(bos);
            streamToClose = ps;

            logger.log(Level.INFO, "Writing Rete network to file: " + file.getAbsolutePath());

            // if we want string representation
            if (type == 0) {
            	ps.print(this.printReteNetwork());
            }
            else {
            	ps.print(this.printReteNetworkXML());
            }

            logger.log(Level.INFO, "Successfully wrote Rete network to file: " + file.getAbsolutePath());

            streamToClose.flush();
            streamToClose.close();
            streamToClose = null;
        }
        catch (IOException e) {
            if (streamToClose != null) {
                try {
                    streamToClose.flush();
                    streamToClose.close();
                }
                catch (IOException e1) {
                    logger.log(Level.WARN,
                            "[" + ReteWM.class.getSimpleName() + "-ReteNetworkSave]",
                            "Error occurred while writing Rete Network to file: " +
                                    file.getAbsolutePath(), e1);
                }
            }
            // throw e;
        }
    }
    
    public boolean getConcurrent() {
    	return this.isConcurrent();
    }
    
    //////////////////// MBean END    
}
