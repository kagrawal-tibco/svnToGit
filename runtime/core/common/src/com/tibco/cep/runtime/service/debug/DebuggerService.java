package com.tibco.cep.runtime.service.debug;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.Service;
import com.tibco.cep.runtime.service.tester.core.ReteChangeWatchdogNotificationManager;
import com.tibco.cep.runtime.service.tester.core.TesterRuleServiceProvider;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Apr 10, 2009
 * Time: 1:06:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class DebuggerService implements Runnable,Service {

    private RuleServiceProvider rsp;

    private Executor debugExecutor;

    private LinkedBlockingQueue<DebugTask> debugQueue;

    private boolean bRunning = true;

    private static Class sax2EventClass;

    private static Class sax2ConceptClass;

    private static Class debugTaskFactoryClass;

    private static SmElement debugElement;

    private static MutableElement dataElement;

    int sessionCounter = 0;

    static {
        try {
            sax2ConceptClass = Class.forName("com.tibco.be.functions.object.SAX4ConceptInstance");
            sax2EventClass   = Class.forName("com.tibco.be.functions.event.SAX4EventInstance");
            debugTaskFactoryClass = Class.forName("com.tibco.cep.runtime.service.debug.DebugTaskFactory");

            MutableSchema schema = SmFactory.newInstance().createMutableSchema();
            schema.setNamespace("http://www.tibco.com/be/debug/input");

            MutableType debugType = MutableSupport.createType(schema,"debug_input");
            MutableSupport.addOptionalLocalElement(debugType, XSDL.ANYTYPE_NAME.localName, XSDL.ANY_TYPE);

            debugElement = MutableSupport.createElement(schema, "debug_input", debugType);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * singleton instance
     */
    static DebuggerService thisInstance;

    /**
     * thread local to store the instance
     */
    private ThreadLocal<DebuggerService> threadLocal = new ThreadLocal<DebuggerService>();

    /**
     * hidden constructor
     */
    private DebuggerService() {
        super();
        setRuleServiceProvider(rsp);
        debugQueue = new LinkedBlockingQueue<DebugTask>(100);
        debugExecutor = Executors.newSingleThreadExecutor(new DebugServiceThreadFactory());
    }

    public static DebuggerService getInstance() {
        if (thisInstance == null) {
            thisInstance = new DebuggerService();
        }
        return thisInstance;
    }

    public DebuggerService getCurrentService() {
        return threadLocal.get();
    }

    public void setCurrentService(DebuggerService service) {
        threadLocal.set(service);
    }

    /**
     * @return the rsp
     */
    public RuleServiceProvider getRuleServiceProvider() {
        return rsp;
    }
    
    Object getTaskTypeRegistry() {
    	try {
			Class clazz = Class.forName("com.tibco.cep.bpmn.runtime.activity.TaskRegistry");
			Method m = clazz.getDeclaredMethod("getInstance");
			Object inst = m.invoke(null);
			if(inst != null) {
				return inst;
			}
		} catch (Exception e) {
			rsp.getLogger(DebuggerService.class).log(Level.ERROR, "Failed to getTaskTypeRegistry",e);
		} 
    	return Collections.EMPTY_LIST;
    }

    /**
     * @param rsp the rsp to set
     */
    public void setRuleServiceProvider(RuleServiceProvider rsp) {
        this.rsp = new TesterRuleServiceProvider(rsp);
    }

    /**
     * @return the sax2EventClass
     */
    public static Class getSax2EventClass() {
        return sax2EventClass;
    }

    /**
     * @return the sax2ConceptClass
     */
    public static Class getSax2ConceptClass() {
        return sax2ConceptClass;
    }

    public void queueTask(DebugTask task) throws InterruptedException {
        debugQueue.put(task);
    }

    public static SmElement getDebugInputType() {
        return debugElement;
    }

    public static MutableElement getDataElement() {
        return dataElement;
    }

    public String getId() {
        return getClass().getName();
    }

    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        if (otherArgs == null || otherArgs.length < 1) {
            throw new RuntimeException("DebuggerService expects the first var arg to be RuleServiceProvider");
        }
        setRuleServiceProvider((RuleServiceProvider) otherArgs[0]);
        //TODO listen to notifications only for tester
        registerListeners();
    }

	public void start() throws Exception {
        debugExecutor.execute(this);
    }

    public void stop() throws Exception {
        bRunning = false;
    }

    private void registerListeners() {
        RuleRuntime ruleRuntime = rsp.getRuleRuntime();
        RuleSession[] ruleSessions = ruleRuntime.getRuleSessions();

        for (RuleSession ruleSession : ruleSessions) {
            if (ruleSession instanceof RuleSessionImpl) {
                RuleSessionImpl ruleSessionImpl = (RuleSessionImpl)ruleSession;
                ReteChangeWatchdogNotificationManager listener = new ReteChangeWatchdogNotificationManager(rsp, ruleSession);
                ((ReteWM)ruleSessionImpl.getWorkingMemory()).addChangeListener(listener);
                ((ReteWM)ruleSessionImpl.getWorkingMemory()).addReteListener(listener);
                ((RuleServiceProviderImpl)ruleSessionImpl.getRuleServiceProvider()).setLifecycleListener(listener);
            }
        }
    }

    public void run() {

        setCurrentService(this);

        while (bRunning) {
            try {
                DebugTask task =  debugQueue.poll(50, TimeUnit.MILLISECONDS);
                processTask(task);
            }
            catch (Exception e) {
            	this.rsp.getLogger(DebuggerService.class).log(Level.ERROR, e, "Error while executing debug task");
            }
        }

        setCurrentService(null);

    }

    /**
     * DebugSession request a method entry break point if all the threads in the
     * VM are resumed.
     *
     * @param task
     */
    public void processTask (DebugTask task) {
        if (task != null) {
            task.run();
            if (task instanceof DebugResponseTask) {
                notifyResponse(((DebugResponseTask)task).getResponse());
            }
        }
    }

    /**
     * @param response
     */
    public void notifyResponse(Object response) {
        if (response != null) {
            getRuleServiceProvider().getLogger(DebuggerService.class).log(Level.INFO, "Debug task response sent.");
        }
    }

    class DebugServiceThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread t =  new Thread(r, DebuggerService.class.getName());
            t.setDaemon(true);
            return t;
        }
    }
}
