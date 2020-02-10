package com.tibco.be.bw.plugin;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.bw.factory.BWLogger;
import com.tibco.be.functions.event.EventHelper;
import com.tibco.be.functions.object.ObjectHelper;
import com.tibco.be.model.types.Converter;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.factories.ClusterConfigFactory;
import com.tibco.bw.store.RepoAgent;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.pe.PEMain;
import com.tibco.pe.core.Engine;
import com.tibco.pe.plugin.Activity;
import com.tibco.pe.plugin.ActivityContext;
import com.tibco.pe.plugin.ActivityController;
import com.tibco.pe.plugin.ActivityException;
import com.tibco.pe.plugin.ProcessContext;
import com.tibco.pe.plugin.ProcessDefinitionContext;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.navigation.NodeKindNodeTest;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 14, 2007
 * Time: 4:12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEInvokeRuleFunction extends Activity {

    public static final String TYPE_NAME_CONFIG = "BEInvokeRuleFunctionConfig";
    public static final String TYPE_NAME_INPUT = "BEInvokeRuleFunctionInput";

    private static final ExpandedName SESSION_QNAME = ExpandedName.makeName("rulesession");
    private static final ExpandedName RFN_QNAME = ExpandedName.makeName("rulefunction");
    private static final ExpandedName LOCKWM = ExpandedName.makeName("lockWM");
    private static final ExpandedName ASYNC = ExpandedName.makeName("async");
    private static final ExpandedName ARGUMENTS_QNAME = ExpandedName.makeName("arguments");

    private static boolean bAsyncSupport = false;
    private static SmElement configType = null;

    private String rspName;  //RuleServiceProvider Name
    private String rfnFQName; //RuleFunction Fully Qualified Name;
    private String repoUrl;
    private String cddUrl;
    private String puid;
    private SmElement outputElement;
    private RuleServiceProvider rspProvider = null;


    static {
        try {
            MutableSchema schema = SmFactory.newInstance().createMutableSchema();
            schema.setNamespace(BEPluginConstants.NAMESPACE + "/BEInvokeRuleFunction");

            MutableType configT = MutableSupport.createType(schema,TYPE_NAME_CONFIG);
            MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.RSPREF.getLocalName(), XSDL.STRING);
            MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.RULEFUNCTIONREF.getLocalName(), XSDL.STRING);

            configType = MutableSupport.createElement(schema,TYPE_NAME_CONFIG,configT);

            bAsyncSupport = Boolean.parseBoolean(System.getProperty("TIBCO.BE.function.catalog.BusinessWorks.asyncSupport", "false"));
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }


    public void setConfigParms(
            XiNode configNode,
            RepoAgent repoAgent,
            ProcessDefinitionContext processDefinitionContext)
            throws ActivityException {

        super.setConfigParms(configNode, repoAgent, processDefinitionContext);

        this.rfnFQName = XiChild.getString(configNode, BEPluginConstants.RULEFUNCTIONREF);
        if (null != this.rfnFQName) {
            final int indexOfDot = this.rfnFQName.indexOf(".");
            if (indexOfDot != -1) {
                this.rfnFQName = this.rfnFQName.substring(0, indexOfDot);
            }
        }

        this.rspName = XiChild.getString(configNode, BEPluginConstants.RSPREF);
        if ((null == this.rspName) || this.rspName.trim().isEmpty()) {
            this.repoUrl = null;
            this.cddUrl = null;
            this.puid = null;
        } else {
            this.rspName = this.rspName.trim();

            try {
                final RspConfigValues rspConfig = BEBWUtils.getRspConfigValues(this.rspName, this.repoAgent);
                this.cddUrl = rspConfig.getCddUrl();
                this.puid = rspConfig.getPuid();
                this.repoUrl = rspConfig.getRepoUrl();
                if ((null == this.repoUrl) || this.repoUrl.trim().isEmpty()) {
                    this.repoUrl = null;
                } else {
                    this.repoUrl = this.repoUrl.trim();
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new ActivityException("Error while setting configuration");
            }
        }//else
    }


    public void init(ActivityContext activityContext) throws ActivityException {
        super.init(activityContext);
        try {

            synchronized(BEBWUtils.GUARD) {

                //fix for CR 1-9LG21R: when BE is the container, ServiceRegistry has been inited by BEMain;
//                if(!serviceRegistryInited){
//                    ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
//                    registry.init();

//                    serviceRegistryInited = true;
//                }

                if ((this.rspName == null)
                        || (this.repoUrl == null)
                        || (this.cddUrl == null)) {
                    throw new ActivityException("Null RuleServiceProvider or RepoUrl or CDD URL (in BW container)");
                }

                final RuleServiceProviderManager rspm = RuleServiceProviderManager.getInstance();
                this.rspProvider = rspm.getDefaultProvider();
                if (null == this.rspProvider) {
                    this.rspProvider = rspm.getProvider(this.rspName);
                    if (rspProvider == null) {
                        Properties env = new Properties();
                        final ClusterConfig config = new ClusterConfigFactory().newConfig(this.cddUrl);
                        if (null == config) {
                            throw new IllegalArgumentException(
                                    "Could not read CDD: " + SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName());
                        }
                        env.put(SystemProperty.CLUSTER_CONFIG.getPropertyName(), config);
                        env.setProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName(), this.cddUrl);
                        env.setProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName(), this.puid);
                        env.setProperty("tibco.repourl", repoUrl );
                        if (PEMain.isMain) {
                            env.setProperty("com.tibco.pe.PEMain", "true");
                        }
                        if (Engine.testMode) {
                            env.setProperty("com.tibco.pe.Engine.testMode", "true");
                        }
//                        if (traPath != null && !traPath.trim().equals("")) {
//                        	env.setProperty("be.bootstrap.property.file", traPath.trim());
//                        }
                        env.setProperty(SystemProperty.TRACE_LOGGER_CLASS_NAME.getPropertyName(), BWLogger.class.getName());
                        this.rspProvider = rspm.newProvider(this.rspName, env);
                        rspProvider.configure(RuleServiceProvider.MODE_API);
                    }
                }

                if (rspProvider == null) {
                    throw new ActivityException ("No RuleServiceProvider configured, or Activity is not executing inside BE Container, please check your configuration");
                }

            }

        } catch (ActivityException ae) {
            //ec.logException("Error while initializing BEInvokeRuleFunction", ae);
            ae.printStackTrace();
            throw ae;
        }catch (Exception e) {
            //ec.logException("Error while initializing BEInvokeRuleFunction", new ActivityException(e.getMessage()));
            e.printStackTrace();
            throw new ActivityException("Error while initializing BEInvokeRuleFunction",e.getMessage());
        }

    }



    /**
     * Delegates the BE work to a worker thread and go into "pending" state
     *
     */
    public XiNode eval(ProcessContext processContext, XiNode input) throws ActivityException {

        try {

            //SS commented it - doesnt make any sense to create a thread pool. BW already has it.
            //use lockWM as false, to ensure multiple threads will work.
            //BEWorker worker = new BEWorker(processContext, input);
            /*Thread t = new Thread (worker);
               t.setDaemon(true);
               t.start();*/
            //ExecutorServiceProvider.getExecutorService().execute(worker);
            //processContext.getActivityController().setPending(0);
            return dowork(processContext, input);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivityException("Error in eval() method:"+e.getMessage());
        }
    }


    public XiNode dowork(ProcessContext processContext, XiNode input) throws Exception {

        if ((rspName == null || repoUrl == null) && RuleServiceProviderManager.getInstance().getDefaultProvider() == null) {
            throw new ActivityException("Null RuleServiceProvider or RepoUrl (in BW container)");
        }
        input = input.getFirstChild();
        RuleSessionImpl session = getRuleSessionImpl(input);
        String overrideFnName   = getRuleFunctionName(input);
        boolean lockWM          = getLockWM(input);
        boolean async           = getAsync(input); //Support for asynchronous call - note output is always null.
        RuleFunction runtimeRuleFn = session.getRuleFunction(overrideFnName);
        Object[] args = convert2ObjectArgs(session, runtimeRuleFn, input);

        Object ret = invokeRuleFunction(session, runtimeRuleFn, args, lockWM, async);

        if (outputElement == null || ret == null) {
            return null;
        }

        XiNode doc = XiFactoryFactory.newInstance().createDocument();
        XiNode output = doc.appendElement(outputElement.getExpandedName());
        fillXiNode(runtimeRuleFn, ret, output, session);
        //XiSerializer.serialize(doc, System.out, true);
        return doc;


    }


    private boolean getAsync(XiNode input) {
        boolean async = false;
        XiNode asyncNode = XiChild.getChild(input, ASYNC);
        if (asyncNode != null && asyncNode.getStringValue().equalsIgnoreCase("true")) {
            async = true;
        }
        return async;
    }


    private boolean getLockWM(XiNode input) {
        boolean lockWM = true;
        XiNode lockWMNode = XiChild.getChild(input, LOCKWM);
        if (lockWMNode != null && !lockWMNode.getStringValue().equalsIgnoreCase("true")) {
            lockWM = false;
        }
        return lockWM;
    }


    private Object invokeRuleFunction(RuleSessionImpl session, RuleFunction runtimeRuleFn, Object[] args, boolean lockWM, boolean bAsyncSupport) throws Exception {
        if (!bAsyncSupport) {
            Object ret = session.invokeFunction(runtimeRuleFn, args, lockWM);
            return ret;
        }

        session.getTaskController().processTask(TaskController.SYSTEM_DEFAULT_DISPATCHER_NAME, new BEBWJob(session, runtimeRuleFn, args, lockWM));
        return null;
    }


    private String getRuleFunctionName(XiNode input) {
        String overrideFnName =null ;
        try{
            overrideFnName = XiChild.getString(input, RFN_QNAME);
        } catch (Exception e)  {
        }
        overrideFnName = (overrideFnName == null ? rfnFQName : overrideFnName);
        return overrideFnName;
    }


    private RuleSessionImpl getRuleSessionImpl(XiNode input) throws ActivityException {
        String sessionName = null;
        RuleSessionImpl session = null;
        try {
            sessionName = XiChild.getChild(input, SESSION_QNAME).getStringValue();
        } catch (Exception ignored) {
        } catch (Throwable t) {
            throw new ActivityException("Unexpected problem when trying to get the rule session name.", input, t);
        }

        if (sessionName == null || sessionName.trim().equals("")) {
            // check if there is only 1 rulesession and use that (default)
            if (rspProvider.getRuleRuntime().getRuleSessions().length == 1) {
                //sessionName = rspProvider.getRuleRuntime().getRuleSessions()[0].getName();
                session = (RuleSessionImpl) rspProvider.getRuleRuntime().getRuleSessions()[0];
            } else {
                throw new ActivityException ("RuleServiceProvider has multiple rulesessions. Rulesession name is required.");
            }
        } else {
            // Be aware of rulesessions in multi-agents case
//            String[] sessionNames = ((RuleSessionManagerImpl)rspProvider.getRuleRuntime()).getRuleSessionNames();
//            for (String sname: sessionNames) {
//                if(sname.equals(sessionName)) {
//                    session = (RuleSessionImpl) rspProvider.getRuleRuntime().getRuleSession(sname);
//                break;
//                }
//            }
            session = (RuleSessionImpl) this.rspProvider.getRuleRuntime().getRuleSession(sessionName);
        }

        if (session == null) {
            throw new ActivityException(buildSessionErrorMessage(sessionName).toString());
        }
        return session;
    }


    private StringBuilder buildSessionErrorMessage(String sessionName) {
        StringBuilder builder = new StringBuilder();
        builder.append("Rulesession [ ").append(sessionName).append(" ] not configured in RuleServiceProvider : ").append(rspName);
        builder.append(". Available rulesessions: ");
        String[] sessionNames = ((RuleSessionManagerImpl)rspProvider.getRuleRuntime()).getRuleSessionNames();
        for (String sname: sessionNames) {
            builder.append(" [ ").append(sname).append(" ]");
        }
        return builder;
    }


    private void fillXiNode(RuleFunction ruleFn, Object ret, XiNode output, RuleSession session) throws Exception {
        RuleFunction.ParameterDescriptor[] pds = ruleFn.getParameterDescriptors();
        RuleFunction.ParameterDescriptor returnType = pds[pds.length -1]; //the last one

        Class type = returnType.getType();

        if (java.lang.String.class.isAssignableFrom(type)) {
            output.setTypedValue(Converter.getStringProperty(ret.toString()));
        }
        else if (int.class.isAssignableFrom(type)) {
            output.setTypedValue(Converter.getIntegerProperty(((Integer)ret).intValue()));
        }
        else if (long.class.isAssignableFrom(type)) {
            output.setTypedValue(Converter.getLongProperty(((Long)ret).longValue()));
        }
        else if (double.class.isAssignableFrom(type)) {
            output.setTypedValue(Converter.getDoubleProperty(((Double)ret).doubleValue()));
        }
        else if (java.util.Calendar.class.isAssignableFrom(type)) {
            output.setTypedValue(Converter.getDateTimeProperty((Calendar)ret));

        }
        else if (boolean.class.isAssignableFrom(type)) {
            output.setTypedValue(Converter.getBooleanProperty(((Boolean)ret).booleanValue()));
        }
        else if (Event.class.isAssignableFrom(type)) {
            ((SimpleEvent)ret).toXiNode(output);
            upCastToEvent(type, (SimpleEvent)ret, output, session);
        }
        else if (Concept.class.isAssignableFrom(type)) {
            ((RuleSessionImpl)session).setSession();  // CR:1-9HI1QN session needs to be set when ConceptImpl dereferences referenced concept.
            ((Concept)ret).toXiNode(output);
            ((RuleSessionImpl)session).resetSession();
            upCastToConcept(type, (Concept)ret, output, session);
        }
        else {
            throw new Exception ("Object/Any type not supported");
        }

    }


    private Object[] convert2ObjectArgs(RuleSessionImpl session, RuleFunction ruleFn, XiNode input) throws Exception {


        RuleFunction.ParameterDescriptor[] descriptors = ruleFn.getParameterDescriptors();
        Object [] parameters = new Object[descriptors.length - 1];
        XiNode arguments = XiChild.getChild(input, ARGUMENTS_QNAME);
        if (arguments == null) return parameters;
        Iterator itr = arguments.getChildren(XiNodeUtilities.NODE_TEST_ELEMENT);

        for (int i=0; itr.hasNext(); i++)
        {
            XiNode node = (XiNode) itr.next();

            if (XiNodeUtilities.isNil(node)) {
                parameters[i] = null;
                continue;
            }

            RuleFunction.ParameterDescriptor pd = descriptors[i];
            Class type = pd.getType();

            if (java.lang.String.class.isAssignableFrom(type)) {
                parameters[i] = node.getStringValue();
            }
            else if (int.class.isAssignableFrom(type)) {
                parameters[i] = new Integer(node.getAtomicValue().castAsInt());
            }
            else if (long.class.isAssignableFrom(type)) {
                parameters[i] = new Long(node.getAtomicValue().castAsInt());
            }
            else if (double.class.isAssignableFrom(type)) {
                parameters[i] = new Double(node.getAtomicValue().castAsDouble());
            }
            else if (java.util.Calendar.class.isAssignableFrom(type)) {
                XsDateTime dt = (XsDateTime)node.getItem(0).getTypedValue();
                parameters[i] = Converter.convertDateTimeProperty(dt);
            }
            else if (boolean.class.isAssignableFrom(type)) {
                parameters[i] = new Boolean(node.getAtomicValue().castAsBoolean());
            }
            else if (Event.class.isAssignableFrom(type)) {
                parameters[i] = EventHelper.newEventInstance(session, type, node);
            }
            else if (Concept.class.isAssignableFrom(type)) {
                parameters[i] = ObjectHelper.newConceptInstance(session, type, node, false);
            }
            else {
                throw new Exception ("Object/Any type not supported");
            }


        }

        return parameters;
    }

    public void destroy() throws Exception {

        synchronized(BEBWUtils.GUARD) {
            final RuleServiceProviderManager rspm = RuleServiceProviderManager.getInstance();
            if ((this.rspProvider != null) && (this.rspProvider != rspm.getDefaultProvider())) {
                //only stop RSP if its not the container.
                rspProvider.shutdown();
                this.rspProvider = null;
                rspm.removeProvider(this.rspName);
            }
        }
    }


    public SmElement getConfigClass() {
        return configType;
    }


    public SmElement getInputClass() {
        MutableSchema schema = SmFactory.newInstance().createMutableSchema();
        MutableType inputT = MutableSupport.createType(schema,TYPE_NAME_INPUT);
        try {
            SmElement fnEle =BEBWUtils.getElement(this.repoUrl, repoAgent,rfnFQName, "arguments");
            if (fnEle != null) {
                MutableSupport.addOptionalLocalElement(inputT, "rulesession", XSDL.STRING);
                MutableSupport.addOptionalLocalElement(inputT, "rulefunction", XSDL.STRING);
                MutableSupport.addOptionalLocalElement(inputT, "lockWM", XSDL.BOOLEAN);
                MutableSupport.addOptionalLocalElement(inputT, fnEle.getName(), fnEle.getType());
                if (bAsyncSupport) {
                    MutableSupport.addOptionalLocalElement(inputT, "async", XSDL.BOOLEAN);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SmElement inputType = MutableSupport.createElement(schema,TYPE_NAME_INPUT,inputT);
        return inputType;


    }


    public SmElement getOutputClass() {

        try {
            SmElement fnEle = BEBWUtils.getElement(this.repoUrl, repoAgent,rfnFQName, "return");
            outputElement = fnEle;
            return fnEle;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }


    public SmParticleTerm getInputTerm() {
        return getInputClass();
    }


    private SimpleEvent createEventInstance(Class clz, RuleSession session) {
        try {
            Constructor cons = clz.getConstructor(new Class[] {long.class});
            SimpleEvent evt=(SimpleEvent) cons.newInstance(new Object[] {new Long(session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz))});
            return evt;
        } catch (Exception e) {
        }
        return null;
    }


    private Concept createConceptInstance(Class clz, RuleSession session) {
        try {
            Constructor cons = clz.getConstructor(new Class[] { long.class });
            Concept cept = (Concept) cons.newInstance(new Object[] { new Long(
                    session.getRuleServiceProvider().getIdGenerator()
                    	.nextEntityId(clz)) });
            return cept;
        } catch (Exception e) {
        }
        return null;
    }


    private void upCastToEvent(Class clz, SimpleEvent event, XiNode output,
                               RuleSession session) {
        if (!clz.isAssignableFrom(event.getClass())) {
            return;
        } else if (clz.equals(Event.class) || clz.equals(SimpleEvent.class)) {
            // is a special case and remove all properties. only attributes will remain
            Iterator childs = output.getChildren();
            while (childs.hasNext()) {
                XiNode child = (XiNode) childs.next();
                output.removeChild(child);
            }
        } else if (!clz.equals(event.getClass())) {
            // event is a subclass of clz
            SimpleEvent e = createEventInstance(clz, session);
            Set propsSet = new HashSet();
            String[] props = e.getPropertyNames();
            for (int i = 0; i < props.length; i++) {
                propsSet.add(props[i]);
            }

            Iterator i = output.getChildren(XiNodeUtilities.NODE_TEST_ELEMENT);
            while (i.hasNext()) {
                XiNode n = (XiNode) i.next();
                String propName = n.getName().localName;
                if (!propsSet.contains(propName)) {
                    output.removeChild(n, true);
                }
            }
        }
    }


    private void upCastToConcept (Class clz, Concept cept, XiNode output, RuleSession session) {
        if (!clz.isAssignableFrom(cept.getClass())) {
            return;
        } else if (clz.equals(Concept.class)) {
            // is a special case and remove all properties. only attributes will remain
            Iterator childs = output.getChildren();
            while (childs.hasNext()) {
                XiNode child = (XiNode) childs.next();
                output.removeChild(child);
            }
        } else if (!clz.equals(cept.getClass())) {
            // cept is a subclass of clz
            Concept cept1 = createConceptInstance(clz, session);
            Iterator i = output.getChildren(XiNodeUtilities.NODE_TEST_ELEMENT);
            while (i.hasNext()) {
                XiNode n = (XiNode) i.next();
                String propName = n.getName().localName;
                if (cept1.getProperty(propName) == null) {
                    // property does not exist, remove from output
                    output.removeChild(n, true);
                }
            }
        }
    }


    /**
     * Performs any final processing that the activity requires. This method is
     * invoked only if the <code>eval()</code> method has issued an <code>{@link
     * ActivityController#setPending(long) ActivityController.setPending()}</code>
     * request. If such a request has been issued, the engine calls this method after
     * the <code>{@link ActivityController#setReady(Object)
     * ActivityController.setReady()}</code> method has been invoked. The closure
     * object is passed to the <code>ActivityController.setReady()</code> method.
     *
     * @param            pc The process context provides information about the process
     *                   in which the activity is executing, such as the process id
     *                   and the process name.
     * @param            closure The object that contains the data passed to the
     *                   <code>ActivityController.setReady() </code>method when the
     *                   activity is ready for final processing. If an exception is
     *                   thrown, the closure object can be used to pass this exception
     *                   back to the engine.
     */
    public XiNode postEval(ProcessContext pc, Object closure) throws ActivityException {
        if (closure == null) {
            return null;
        } else if (closure instanceof XiNode) {
            return (XiNode)closure;
        } else if (closure instanceof ActivityException) {
            throw (ActivityException)closure;
        } else if (closure instanceof Throwable) {
            // wrap as an ActivityException
            throw new ActivityException("Activity invocation failed", null, (Throwable) closure);
        } else {
            throw new ActivityException("Activity.postEval: unexpected data type for closure - "+closure.getClass().getName());
        }
    }



//    /**
//     *
//     * @author bgokhale
//     * This is a worker delegate class in which the real "eval" happens.
//     * Its started in a new thread so as to relieve the calling BW engine thread.
//     *
//     * The Activity's eval method calls
//     * ActivityController.setPending() after starting a BE worker thread.
//     *
//     * When Activity.eval returns, the above call causes the
//     * engine thread to be relieved.
//     *
//     * BEWorker then does BE work and calls
//     * ActivityController.setReady(). This causes Activity.postEval to be invoked
//     * giving control back to the BW process
//     *
//     *
//     */
//
//    class BEWorker implements Runnable {
//        ProcessContext processContext;
//        XiNode input;
//        Object returnVal; //can be XiNode or an Exception
//
//        BEWorker (ProcessContext processContext, XiNode input) {
//            this.processContext = processContext;
//            this.input = input;
//        }
//
//        public void run() {
//            try {
//                XiNode output = dowork(processContext, input);
//                returnVal = output;
//            } catch (ActivityException ae) {
//                ae.printStackTrace();
//                returnVal = ae;
//            } catch (Throwable t) {
//                t.printStackTrace();
//                returnVal = t;
//            } finally {
//                try {
//                    processContext.getActivityController().setReady(returnVal);
//                } catch (ActivityException ae) {
//                    ae.printStackTrace();
//                }
//            }
//        }
//    }

    
    class BEBWJob implements Runnable {

        RuleSession session;
        RuleFunction rfn;
        Object[] parameters;
        boolean bSync;

        BEBWJob(RuleSession session, RuleFunction rfn, Object[] parameters, boolean bSync) {
            this.session = session;
            this.rfn = rfn;
            this.parameters = parameters;
            this.bSync = bSync;
        }

        public void run() {
            ((RuleSessionImpl)session).invokeFunction(rfn, parameters, bSync);
        }

    }


}
