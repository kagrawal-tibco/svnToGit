package com.tibco.cep.runtime.scheduler.impl;

import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.PayloadValidationHelper;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.impl.ProcessingContextImpl;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Feb 5, 2007
 * Time: 6:00:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTaskController implements TaskController {
    protected RuleSession ruleSession;
    boolean running;
    private volatile long windowStart=0L;
    private volatile long lastUpdateTime=0L;
    private long numMsgsInWindow=0L;
    private long numJobsProcessed=0L;
    public DefaultTaskController(RuleSession session) {
        ruleSession = session;
        running     = false;
    }

    public int threadCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void start() {
    }

    protected void incrementNumJobs() {
        long now=System.nanoTime();
        if ((windowStart==0L) || ((now-lastUpdateTime) > 2000000000L)){
            windowStart=lastUpdateTime=now;
            numMsgsInWindow=1L;
        } else {
            lastUpdateTime=now;
            ++numMsgsInWindow;
        }
        ++numJobsProcessed;
    }

    public double getJobRate() {
        long denom= lastUpdateTime-windowStart;
        if (denom != 0) {
            return numMsgsInWindow/(1.0*denom*1000000000L);
        } else {
            return 0.0;
        }
    }

    public long getNumJobsProcessed() {
        return numJobsProcessed;
    }

    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void suspend() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void shutdown() {
        while (isRunning()) {
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void processEvent(Channel.Destination dest, SimpleEvent event, EventContext ctx) throws Exception {
        ((RuleServiceProviderImpl) ruleSession.getRuleServiceProvider()).ensureRSP();
        running = true;
        long start=System.currentTimeMillis();
        try {
            SerializationContext sci = new DefaultSerializationContext(ruleSession, dest);

            if (event == null) { //try deserializing the event
                if (dest.getEventSerializer() != null) {
                    event = dest.getEventSerializer().deserialize(ctx.getMessage(), sci);
                    if (event != null) {
                        event.setContext(ctx);
                        ((SimpleEventImpl)event).setDestinationURI(dest.getURI());
                        if (PayloadValidationHelper.ENABLED) {
                            PayloadValidationHelper.validate(event, sci);
                        }
                    }
                }
            }

            if (event != null) {
                this.ruleSession.getProcessingContextProvider().setProcessingContext(
                        new ProcessingContextImpl(this.ruleSession, dest, start, event));

                RuleSessionConfig.InputDestinationConfig destConfig = sci.getDeployedDestinationConfig();
            	if(destConfig == null) {
            		throw new RuntimeException("Can't find input destination " + dest.getURI()+ " for agent-class " + ruleSession.getName());
            	}
                RuleFunction preprocessor = destConfig.getPreprocessor();
                if (preprocessor != null) {
                    ((RuleSessionImpl) ruleSession).preprocessPassthru(preprocessor, event);
                } else {
                    ruleSession.assertObject(event, true);
                }
            }
        }
        finally {
    		this.ruleSession.getProcessingContextProvider().setProcessingContext(null);
            incrementNumJobs();
            running = false;
        }
    }

    public void processTask(String dispatcherName, Runnable task) throws Exception {
        ((RuleServiceProviderImpl) ruleSession.getRuleServiceProvider()).ensureRSP();
        running = true;
        try {
            task.run();
        } finally {
            incrementNumJobs();
            running=false;
        }
    }
}
