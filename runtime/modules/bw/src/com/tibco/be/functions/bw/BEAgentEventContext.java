package com.tibco.be.functions.bw;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.tibco.be.functions.event.EventHelper;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.pe.core.JobEvent;
import com.tibco.pe.core.JobListener;
import com.tibco.pe.plugin.AgentEventContext;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: Jun 12, 2007
 * Time: 6:30:22 PM
 */
public class BEAgentEventContext extends AgentEventContext implements JobListener {
    private BEServiceAgent agent;
    private String processName;

    private RuleFunction ruleFn;
    private RuleSession session;
    private Object closure;
    private long jobId;
    private boolean sync;
    private volatile boolean jobIdSet = false;


    public BEAgentEventContext(BEServiceAgent agent, String processName, RuleSession session, Object context, RuleFunction ruleFn, boolean sync) {
        this.agent = agent;
        this.processName = processName;
        this.session = session;
        this.ruleFn = ruleFn;
        closure = context;
        jobId = -1L;
        this.sync = sync;
    }

    public String getProcessName() {
        return processName;
    }

    public RuleSession getRuleSession() {
        return session;
    }

    public Object getContext() {
        return closure;
    }


    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobIdSet(boolean jobIdSet) {
        this.jobIdSet = jobIdSet;
    }

    public boolean isJobIdSet() {
        return jobIdSet;
    }


    public boolean afterExecution(JobEvent jobEvent) {
        return true;
    }

    public boolean beforeExecution(JobEvent jobEvent) {
/*
        System.out.println(jobEvent.processData.getVariableNames());
        XiNode node = jobEvent.processData.getVariable("_processContext").getValue();
        node = node.getFirstChild();
        try {
            jobId = XiChild.getInt(node, ExpandedName.makeName("ProcessId"));
        } catch (XmlAtomicValueCastException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
*/
        //jobEvent.processData.list(System.out);
        return true;
    }

    public void errorLogged(String string, String string1, XiNode xiNode, long l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void processCalled(JobEvent jobEvent, String string, boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stateChanged(boolean b, long l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void trackAborted(String string, long l, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void transitionEvaluated(String string, String string1, String string2, boolean b, long l, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean wantsActivityInput() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
    //AgentEvent Context implementation
    /**
     *
     * @param outNode
     */

    public void failed(XiNode outNode) {
        try {
            this.session.getRuleServiceProvider().getLogger(BEAgentEventContext.class).log(Level.DEBUG,
                    "BW process failed");
            notify(-1, outNode);
        }
        catch (Exception e) {
            throw new RuntimeException (e);
        }
    }

    public void finished(XiNode outNode) {
        try {
            this.session.getRuleServiceProvider().getLogger(BEAgentEventContext.class).log(Level.DEBUG,
                    "BW process finished");
            notify(0, outNode);
        }
        catch (Exception e) {
            throw new RuntimeException (e);
        }
    }

    protected void notify(int status, XiNode outNode) throws Exception {
        if (ruleFn == null) {
        	return;
        }
        while (!isJobIdSet()) {
            this.session.getRuleServiceProvider().getLogger(BEAgentEventContext.class).log(Level.DEBUG,
                    "JobId is not set yet. Sleep for 5 ms...");
            Thread.sleep(5);
        }
        RuleFunction.ParameterDescriptor[] descriptors = ruleFn.getParameterDescriptors();
        Object [] parameters = new Object[descriptors.length - 1];

        parameters[0] = new Long(jobId);
        parameters[1] = new Integer(status);
        parameters[2] = transform2Event(status, outNode);
        parameters[3] = closure;

        if (sync || session.getTaskController() == null) {
            ((RuleSessionImpl)session).invokeFunction(ruleFn, parameters, false); // always invoke rullfunction asynchronously
        }
        else {
            session.getTaskController().processTask(TaskController.SYSTEM_DEFAULT_DISPATCHER_NAME, new BEBWJob(session, ruleFn, parameters, false)); // always invoke rullfunction asynchronously           
        }
    }

    private Event transform2Event(int status, XiNode outNode) {
        try {
            if (status == 0) { // finished
                if (outNode == null) {
                    return null;
                }
                XiNode eventNode = XiChild.getFirstChild(outNode);
                return EventHelper.newEventInstance(session, SimpleEvent.class, eventNode);
            } else { // status == -1, failed. Always return an AdvisoryEvent
                String msg = null;
                if (outNode != null) {
                    StringWriter sw = new StringWriter();
                    XiSerializer.serialize(outNode, sw);
                    msg = sw.toString();
                } else {
                    msg = "BW process: " + processName + " (JobId=" + jobId + ") failed. No return event.";
                }
                Event evt = new AdvisoryEventImpl(session.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class), null, AdvisoryEvent.CATEGORY_ENGINE, "INVOKE BW PROCESS", msg);
                return evt;
            }
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            pw.close();
            Event evt = new AdvisoryEventImpl(session.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class), null, "BEBW", "INVOKE BW PROCESS", sw.toString());
            return evt;
        }
    }

}
