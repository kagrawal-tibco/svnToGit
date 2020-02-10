package com.tibco.be.functions.bw;


import java.util.Map;

import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.model.rule.impl.ParameterDescriptorImpl;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.pe.core.AgentJobCreator;
import com.tibco.pe.core.Engine;
import com.tibco.pe.core.JobListener;
import com.tibco.pe.core.JobPool;
import com.tibco.pe.core.Workflow;
import com.tibco.pe.plugin.ActivityException;
import com.tibco.pe.plugin.AgentContext;
import com.tibco.pe.plugin.ServiceAgent;
import com.tibco.pe.plugin.ServiceAgentRestartInfo;
import com.tibco.sdk.MTrackingInfo;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmElement;


/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: May 21, 2007
 * Time: 2:06:35 PM
 */
public class BEServiceAgent implements ServiceAgent {
    public static final String NAME = "BEServiceAgent";
    private static BEServiceAgent thisAgent;

    private AgentContext m_context;
    private boolean m_active;
    private volatile boolean jobInProgress = false;

//    public static XiFactory xifactory = XiFactoryFactory.newInstance();


    public BEServiceAgent() {
        m_active = false;
        thisAgent = this;
    }



    public static  BEServiceAgent getInstance() throws Exception {
        synchronized(NAME) {
            if (thisAgent != null && !Engine.testMode)  return thisAgent;
            JobPool pool = (JobPool) BWSupport.getStaticField(Engine.class, JobPool.class);
            XiFactory factory = XiSupport.getXiFactory();
            XiNode nd = factory.createElement(ExpandedName.makeName("ServiceAgent"));
            XiChild.appendString(nd, ExpandedName.makeName("class"), BEServiceAgent.class.getName());
            XiNode doc = factory.createDocument();
            doc.appendChild(nd);
            AgentJobCreator agent = new AgentJobCreator(pool, BEServiceAgent.NAME, doc);
            agent.init();
            return thisAgent;
        }

    }


    public void init(XiNode xiNode, AgentContext agentContext) throws ActivityException {
        m_context = agentContext;
    }

    public void activate() throws ActivityException {
        if(!m_active) {
            m_active = true;
        }
    }

    public void deactivate() {
        synchronized(NAME) {
            if(m_active) {
                m_active = false;
                thisAgent.destroy();
                thisAgent = null;
            }
        }
    }

    public boolean isActive() {
        return m_active;
    }

    public void destroy() {
        deactivate();
        m_context = null;
    }

    // todo: what do I do with this
    public ServiceAgentRestartInfo onRestart(long l, MTrackingInfo mTrackingInfo) {
        return null;
    }

    public long startProcess(String processName, Object input, RuleSession session, RuleFunction ruleFn, Object context, boolean sync) throws Exception {
        Workflow wf = m_context.getWorkflow(processName + ".process");
        //sync = Boolean.parseBoolean(session.getRuleServiceProvider().getProperties().getProperty("com.tibco.bebw.service.callback.executeSync", "false"));
        BEAgentEventContext ctx = new BEAgentEventContext(this, processName, session, context, ruleFn, sync);
        XiNode doc = null;
        if (input != null)    {
            doc = XiNodeBuilder.makeDocument(input, (SmElement)wf.getProcessInput());
        }

        long l = m_context.startProcess(doc, ctx, wf, (JobListener)ctx, true);
        ctx.setJobId(l);
        ctx.setJobIdSet(true);
        return l;
    }



    public Event invokeProcess(final String processName, final Object input, final RuleSession session, long timeout) throws Exception{
        jobInProgress = false;
        final ProcessResult result = new ProcessResult();
        final ProcessCallbackFunction function = new ProcessCallbackFunction(result);

//        Thread processInvoker = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    long l = startProcess(processName, input, session, function, null);
//                } catch (Exception e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//            }
//        });
//        processInvoker.start();
        jobInProgress = true;
        long l = startProcess(processName, input, session, function, null, true); // use sync=true to notate this call is for invokeProcess. callback function invocation will still be async
        synchronized (result) {
            if (jobInProgress) { // jobInProgress may have been set to false in the callback thread
                result.wait(timeout);
                jobInProgress = false;
            }
        }

        if (result.status == -1) { // return AdvisoryEvent
            if (result.se == null) { // Invocation must be timed out as otherwise an AdvisoryEvent will be returned when result.status==-1
                session.assertObject(new AdvisoryEventImpl(session.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class),
                        null, AdvisoryEvent.CATEGORY_ENGINE, "INVOKE BW PROCESS",
                        "BW process: " + "Process: " + processName + " (JobId=" + l + ")" + " timed out after " + timeout + " (milliseconds)"), true);
            } else {
                session.assertObject(result.se, true);
            }
            return null;
        } else { // result.status==0, BW process finished
            return result.se;
        }
    }

    static final RuleFunction.ParameterDescriptor[] parameterDesc = new RuleFunction.ParameterDescriptor[]
            {
                    new ParameterDescriptorImpl("jobId", long.class, true),
                    new ParameterDescriptorImpl("status", int.class, true),
                    new ParameterDescriptorImpl("data", SimpleEvent.class, true),
                    new ParameterDescriptorImpl("closure", Object.class, true),
                    new ParameterDescriptorImpl("return", Object.class, true)


            };

    class ProcessCallbackFunction implements RuleFunction {


        ProcessResult result;
        public ProcessCallbackFunction(ProcessResult result) {
            this.result = result;
            this.result.jobId = -1;
            this.result.status = -1;
            this.result.se = null;
        }


        public Object invoke(Object[] args) {
            synchronized (result) {
                RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(BEServiceAgent.class)
                        .log(Level.DEBUG, "#### Invoked");
                result.jobId = ((Long)args[0]).longValue();
                result.status = ((Integer)args[1]).intValue();
                result.se = (Event) args[2];
                jobInProgress = false;
                result.notifyAll();
                return null;
            }
        }

        public Object invoke(Map map) {
            RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger(BEServiceAgent.class)
                    .log(Level.DEBUG, "#### Invoked");
            Object[] vals = new Object[4];
            vals[0] = map.get("jobId");
            vals[1] = map.get("status");
            vals[2] = map.get("data");
            vals[3] = map.get("closure");
            return invoke(vals);
        }

        public String getSignature() {
            return null;
        }

        public ParameterDescriptor[] getParameterDescriptors() {
            return parameterDesc;
        }
    }

    class ProcessResult {
        long jobId;
        int status;
        Event se;
    }
}
