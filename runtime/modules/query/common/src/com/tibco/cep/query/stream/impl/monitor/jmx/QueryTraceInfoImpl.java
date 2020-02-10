package com.tibco.cep.query.stream.impl.monitor.jmx;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.LinkedList;

import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

import com.tibco.cep.query.stream.impl.monitor.model.QueryTraceInfo;
import com.tibco.cep.query.stream.monitor.QueryWatcher;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jul 25, 2008
 * Time: 4:52:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryTraceInfoImpl extends QueryTraceInfo implements com.tibco.cep.query.stream.impl.monitor.model.Info {

    private ObjectName objNameQueryTrace;
    private MBeanServer mbs;
    private Collection<QueryWatcher.Run> capturedRuns;
    //private QueryInfoImpl queryInfoObj;                                    todo delete
                      //todo ASHWIN about refresh()  the other alternative is to pass "currentReteQuery" and "monitor" as parameters
    public QueryTraceInfoImpl(ObjectName objNameQueryMBean, Collection<QueryWatcher.Run> capturedRuns, QueryInfoImpl queryInfoObj ) {    //todo should I declare in signature, or use Try Catch block
        super(queryInfoObj);
        try {
            objNameQueryTrace = new ObjectName(objNameQueryMBean + ",QueryTrace=QueryTrace");      //todo .toString() or +"" is better ????
            mbs = ManagementFactory.getPlatformMBeanServer();
            this.capturedRuns = new LinkedList<QueryWatcher.Run>(capturedRuns);
      //      this.queryInfoObj = queryInfoObj;                                  todo delete
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }

    public void register() {
        int lineNum = 1;
            //create an instance of numberFormat and uses it to format the descriptor that contains the trace information
            //to be printed in the J-console
        NumberFormat numForm = NumberFormat.getInstance();
        ((DecimalFormat) numForm).applyLocalizedPattern("0000");

        String runSpace = ".      ", stepSpace = ".           ";
        try {
                //create model MBean descriptor required fields
            Descriptor traceDesc = new DescriptorSupport();
            traceDesc.setField("name", "QueryTrace");
            traceDesc.setField("descriptorType", "MBean");
            if ( capturedRuns.isEmpty() )
                traceDesc.setField("... Gathering trace info ...", "Try refreshing again in the next few seconds");

            for (QueryWatcher.Run capturedRun : capturedRuns) {
                long runStartTime = capturedRun.getStartTime();
                QueryWatcher.Status status = capturedRun.getStatus();
                Throwable error = capturedRun.getError();
                long runEndTime = capturedRun.getEndTime();

                    //put run information in the descriptor
                traceDesc.setField(numForm.format(lineNum++) +". Run", "");
                traceDesc.setField(numForm.format(lineNum++) + runSpace + "start-time", new Timestamp(runStartTime)+"");
                traceDesc.setField(numForm.format(lineNum++) + runSpace + "end-time", new Timestamp(runEndTime)+"");
                traceDesc.setField(numForm.format(lineNum++) + runSpace + "status", status+"");
                traceDesc.setField(numForm.format(lineNum++) + runSpace + "error", ((error == null) ? "-" : error.toString())+"");

                QueryWatcher.Step step = capturedRun.getFirstStep();              //todo isn't better the variable declarations outside the loop???
                while (step != null) {
                    long stepStartTime = step.getStartTime();
                    String stepId = step.getResourceId().generateSequenceToParentString();
                    long stepEndTime = step.getEndTime();
                        //put step information in the descriptor
                    traceDesc.setField(numForm.format(lineNum++) + runSpace + "Step ", stepId+"");
                    traceDesc.setField(numForm.format(lineNum++) + stepSpace + "start-time", new Timestamp(stepStartTime)+"");
                    traceDesc.setField(numForm.format(lineNum++) + stepSpace + "end-time", new Timestamp(stepEndTime)+"");
                    step = step.getNextStep();
                }
            }

            ModelMBeanOperationInfo[] mmboi = { new ModelMBeanOperationInfo("Refreshes the query-trace log information",QueryTraceInfoImpl.class.getDeclaredMethod("refresh"))};

            ModelMBeanInfo mmbi = new ModelMBeanInfoSupport(QueryTraceInfoImpl.class.getName(), "Displays statistics and other query logging information",
                                                            null, null, mmboi, null, traceDesc);
            RequiredModelMBean requiredModelMBean = new RequiredModelMBean(mmbi);
            requiredModelMBean.setManagedResource(this, "ObjectReference");
            mbs.registerMBean(requiredModelMBean, objNameQueryTrace);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() throws Exception, InstanceNotFoundException {
        unregister();
        enableTracing();
    }

    public void unregister() throws MBeanRegistrationException, InstanceNotFoundException {
        if (mbs.isRegistered(objNameQueryTrace))
            mbs.unregisterMBean(objNameQueryTrace);
    }
}
