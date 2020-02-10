package com.tibco.cep.driver.local;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.model.ReferenceCountAble;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.NotModeledEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.ResourceManager;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Apr 15, 2006
 * Time: 6:38:04 PM
 * To change this template use File | Settings | File Templates.
 */

public class LocalQueueDestination extends AbstractDestination<LocalChannel> {
    protected BlockingQueue queue;
    //protected Map queuesubscribers = new HashMap ();
    protected List listeners;
    protected int queueSize;
    Logger logger;
    protected int timeOut = -1;
    protected boolean close = false;

    protected LocalQueueDestination (LocalChannel channel, DestinationConfig config) {
        super(channel, config);
        logger = channel.getLogger();
        final String sizeStr = (String) this.getSubstitutedPropertyValue("Size", true);
        final String timeoutStr = (String) this.getSubstitutedPropertyValue("TimeOut", true);
        if (sizeStr != null && sizeStr.length() != 0) queueSize = Integer.valueOf(sizeStr).intValue();
        if (timeoutStr != null && timeoutStr.length() != 0) timeOut = Integer.valueOf(timeoutStr).intValue();

        if (queueSize > 0) queue = new LinkedBlockingQueue(queueSize-1);
        else queue = new LinkedBlockingQueue();
        listeners = new ArrayList();
    }


    public void connect() throws Exception {
        close = false;
    }

    public void bind(RuleSession session) throws Exception {
        close = false;
        listeners.add(new LQDispatcher(session));
        super.bind(session);
    }

    protected void destroy(RuleSession session) throws Exception {
        Iterator r = listeners.iterator();
        while (r.hasNext()) {
            LQDispatcher lsnr = (LQDispatcher)r.next();
            if (lsnr.ruleSession == session) {
                lsnr.stopThis=true;
                lsnr.interrupt();
                listeners.remove(lsnr);
                return;
            }
        }
    }

    private void printConfig() {
        StringBuffer sb = new StringBuffer();
        Iterator ite = config.getProperties().entrySet().iterator();
        boolean first = true;
        while(ite.hasNext()) {
            Map.Entry e = (Map.Entry) ite.next();
            if(!first)
                sb.append(" ");
            else
                first = false;
            final CharSequence value = channel.getGlobalVariables().substituteVariables((String) e.getValue());
            sb.append(e.getKey()).append("=").append(value);
        }
        channel.getLogger().log(Level.INFO,
                ResourceManager.getInstance().formatMessage("channel.destination.config", getURI(), sb));
    }


    public void start(int mode) throws Exception {
        close = false;
        printConfig();
        final Logger logger = channel.getLogger();
        final boolean canLogInfo = logger.isEnabledFor(Level.INFO);
        final int listenerMode = (ChannelManager.ACTIVE_MODE == mode) && this.userControlled
                ? ChannelManager.SUSPEND_MODE : mode;
        Iterator j = listeners.iterator();
        while (j.hasNext()) {
            LQDispatcher dispatcher = (LQDispatcher) j.next();
            dispatcher.start(listenerMode);
            if (canLogInfo) {
                logger.log(Level.INFO,
                        ResourceManager.getInstance().formatMessage(
                                "channel.destination.listener.started." + listenerMode,
                                this.getURI(),
                                dispatcher.ruleSession.getName(),
                                ""));
            }
        }
        super.start(mode);
    }

    public void stop() {
        final boolean canLogInfo = logger.isEnabledFor(Level.INFO);
        Iterator j = listeners.iterator();
        while (j.hasNext()) {
            LQDispatcher dispatcher = (LQDispatcher) j.next();
            dispatcher.stopDispatcher();
            j.remove();
            if (canLogInfo) {
                logger.log(Level.INFO,
                        ResourceManager.getInstance().formatMessage(
                                "channel.destination.listener.stopped",
                                this.getURI(),
                                dispatcher.ruleSession.getName(),
                                ""));
            }
        }
    }


    public void resume() {
        final Logger logger = this.channel.getLogger();

        if (!this.userControlled) {
            synchronized (this.syncObject) {
                if (this.suspended) {
                    this.suspended = false;
                    try {
                        this.start(ChannelManager.ACTIVE_MODE);
                    } catch (Exception e) {
                        this.suspended = true;
                        logger.log(Level.ERROR, "Cannot resume destination %s - %s", this.getURI(), e.getMessage());
                    }
                }
                this.syncObject.notifyAll();
            }
        } else {
            logger.log(Level.DEBUG, "Destination [%s] is suspended. Listener is not started.", this.getURI());
        }
    }



    public int getQueueDepth() {
        return queue.size();
    }

    public int getQueueCapacity() {
        return queue.remainingCapacity();
    }

    public void close()  {
        close = true;
        stop();
    }

    @Override
    public AbstractEventContext createEventContext(Object message) {
        return new LQEventContext(message);
    }

    public int send(SimpleEvent event, Map overrideData) throws Exception {
        if (queue != null && getBoundSessions().size() > 0) {
            try {
                if (queue.offer(event, timeOut, TimeUnit.MILLISECONDS)) {
                    EventContext ctx = event.getContext();
                    if (ctx != null) {
                        if (ctx instanceof ReferenceCountAble) {
                            ((ReferenceCountAble)ctx).incrementCount();
                        }
                    }
                    channel.getLogger().log(Level.DEBUG, "sending event %s", event);
                    return 0;
                }
            }
            catch(InterruptedException ie) {
                if(!close) ie.printStackTrace();
            }
        } else {
            //return success code if the message is successfully skipped because the
            //destination was not found
            return 0;
        }
        //else {
        //    throw new Exception ("destination not found: " + getURI());
        //}
        return -1;
    }
    
    @Override
	public Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData)
			throws Exception {
    	throw new Exception("requestEvent() is not supported with Local Channel");
	}
    
    class LQDispatcher extends Thread {
        boolean stopThis =  false;
        RuleSession ruleSession;
        boolean started = false;

        public LQDispatcher(RuleSession session) {
            super(getURI() + "-" + session.getName());
            this.ruleSession = session;
            setDaemon(true);
        }

        public void run() {
            stopThis = false;
            while (!stopThis) {
                try {
                    //Object e = queue.take();  //todo - SSUBRAMANI - need a timeout here
                    Object e = queue.poll(1000, TimeUnit.MILLISECONDS); //1 Secs
                    if (e != null) {
                        SimpleEvent se = transformEvent(ruleSession, (SimpleEvent)e);
                        LocalQueueDestination.this.onMessage(ruleSession, se);
                    }
                }
                catch(InterruptedException ie) {
                    if(stopThis) break;
                    else ie.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private SimpleEvent transformEvent(RuleSession ruleSession, SimpleEvent event) throws Exception {
            TypeManager typeManager = ruleSession.getRuleServiceProvider().getTypeManager();
            SimpleEvent te = null;

            if (ruleSession instanceof RuleSessionImpl) {
                if (event instanceof NotModeledEvent) {
                    te = (SimpleEvent) typeManager.createEntity(event.getExpandedName());

                }
                else return event;
            }
            else { //BW RuleSession... or other type of RuleSession
                if (event instanceof NotModeledEvent) {
                    return event;
                }
                te = (SimpleEvent) typeManager.createEntity(event.getExpandedName());  //The Typemanager will be XiEntityFactory or its variant
            }

            String[] propNames = event.getPropertyNames();
            for (int i=0; i<propNames.length; i++) {
                String propName = propNames[i];
                te.setProperty(propName, event.getProperty(propName));
            }
            te.setPayload(event.getPayload());
            te.setContext(event.getContext()); //?Not sure what this means.

            return te;
        }

        protected void start(int mode) {

            if (mode == ChannelManager.ACTIVE_MODE) {
                if (!started) {
                    started = true;
                    start();

                }
                else {
                    LocalQueueDestination.this.resume();
                }
            }
            else if (mode == ChannelManager.PASSIVE_MODE) {
                //do nothing
            }

            else if (mode == ChannelManager.SUSPEND_MODE) {
                LocalQueueDestination.this.suspend();
                if(!started){
                	started = true;
                	start();
                }
            }
        }

        protected void stopDispatcher() {
            stopThis = true;
            started = false;
            interrupt();
        }

    }

    public class LQEventContext extends AbstractEventContext{
        Object message;

        public LQEventContext(Object message) {
            this.message = message;
        }

        @Override
        public boolean reply(SimpleEvent replyEvent) {
            return false;
        }

        @Override
        public Destination getDestination() {
            return LocalQueueDestination.this;
        }

        @Override
        public Object getMessage() {
        	return message;
        }

		@Override
		public String replyImmediate(SimpleEvent replyEvent) {
			// TODO Auto-generated method stub
			return null;
		}
    }
}


