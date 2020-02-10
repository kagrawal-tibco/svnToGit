package com.tibco.be.bw.plugin;


import com.tibco.bw.store.RepoAgent;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.pe.plugin.ActivityException;
import com.tibco.pe.plugin.EngineContext;
import com.tibco.pe.plugin.EventContext;
import com.tibco.pe.plugin.EventSource;
import com.tibco.pe.plugin.EventSourceContext;
import com.tibco.pe.plugin.ProcessContext;
import com.tibco.pe.plugin.ProcessDefinitionContext;
import com.tibco.plugin.PluginException;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;


/*
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Aug 14, 2004
 * Time: 11:47:00 AM
 * To change this template use File | Settings | File Templates.
 */

public class BEReceiveEvent extends EventSource  {


    public static final String TYPE_NAME_CONFIG	= "BEReceiveEventConfig";
    public static final String TYPE_NAME_INPUT	= "BEReceiveEventInput";
    public static final String TYPE_NAME_OUTPUT	= "BEReceiveEventOutput";

    private static MutableSchema m_schema;

    protected boolean m_isActive = false;
    protected boolean bIsSignalIn = false;


    static {
        m_schema = SmFactory.newInstance().createMutableSchema();
        m_schema.setNamespace(BEPluginConstants.NAMESPACE);

        MutableType configT = MutableSupport.createType(m_schema,TYPE_NAME_CONFIG);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.EVENTREF.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENTITYNS.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENTITYNAME.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENCODING.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.IS_TEXT.getLocalName(), XSDL.BOOLEAN);

        MutableSupport.createElement(m_schema,TYPE_NAME_CONFIG, configT);
    }//static


    protected BEClient m_beClient;
    protected String m_destinationRef;
    protected String eventRef;


    public BEReceiveEvent() {
        super();
        this.bIsSignalIn = false;
    }
    public BEReceiveEvent(boolean isSignalIn) {
        super();
        this.bIsSignalIn = isSignalIn;
    }


    public void setConfigParms(
            XiNode configNode,
            RepoAgent repoAgent,
            ProcessDefinitionContext processDefinitionContext)
            throws ActivityException {

        super.setConfigParms(configNode, repoAgent, processDefinitionContext);

        this.eventRef = XiChild.getString(configNode, BEPluginConstants.EVENTREF);
        if (null != this.eventRef) {
            final int indexOfDot = this.eventRef.indexOf(".");
            if (indexOfDot != -1) {
                this.eventRef = this.eventRef.substring(0, indexOfDot);
            }
        }
    }


    public boolean cancelled(ProcessContext pc, XiNode inputData) {
        // sleep is over.  return without requesting exception be thrown.
        return false;
    }


    public void init(EventSourceContext context) throws ActivityException {
        super.init(context);
        try {

            initializeClient(context.getEngineContext());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ActivityException(e.getMessage());
        }
    }


    void initializeClient(EngineContext context) throws Exception{
        try {
            eventRef = XiChild.getString(configParms, BEPluginConstants.EVENTREF);
            m_destinationRef = XiChild.getString(configParms, BEPluginConstants.DESTINATIONREF);
            final RspConfigValues rspConfigValues = BEBWUtils.getRspConfigValues(this.configParms, this.repoAgent);
            this.m_beClient = new BEClient(context, rspConfigValues);
            if (m_destinationRef == null || m_destinationRef.length() == 0) {
                m_destinationRef = m_beClient.getEventDefaultDestinationURI(eventRef);
            }

            if(m_destinationRef == null || m_destinationRef.length() == 0) {
                throw new Exception("Both Custom Destination and the Default Destination of Event are null");
            }

            String chURI = m_destinationRef.substring(0, m_destinationRef.lastIndexOf('/'));
            if (chURI.endsWith(".channel")) {
                m_destinationRef = m_destinationRef.substring(0,m_destinationRef.lastIndexOf('/')-8)
                        + m_destinationRef.substring(m_destinationRef.lastIndexOf('/'));
            }

        }
        catch(Exception ex) {
            if (m_beClient != null) {
                m_beClient.destroy();
                m_beClient=null;
            }
            throw ex;
        }
    }

    Channel.Destination m_destination;
    public synchronized void activate() throws ActivityException {
        try {
            if (!m_isActive) {
                if (m_destination != null) {
                    System.out.println("Resuming destination...");
                    m_destination.resume();
                    return;
                }
                Channel.Destination dest = createDestination();

                if (dest == null) {
                    throw new Exception("Destination to activate is not available. Destination URI = " + m_destinationRef);
                }

                Event event = m_beClient.getEventDescription(eventRef);
                BWSession session = new BWSession(m_beClient.getRuleServiceProvider(), context, TYPE_NAME_OUTPUT, event, dest );
                dest.getChannel().connect();
                dest.connect();
                dest.bind(session);     //do the binding with BWSession
                dest.getChannel().start(ChannelManager.ACTIVE_MODE);
                dest.start(ChannelManager.ACTIVE_MODE);
                m_isActive = true;
                m_destination = dest; //make a reference and use it later
            }
            else {
                if (m_destination != null) {
                    m_destination.resume();
                }
                else {
                    throw new Exception("Trying to activate a null destination, please create one");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivityException(e.getMessage());
        }//catch
    }//activate

    private Channel.Destination createDestination() {
        Channel.Destination dest = m_beClient.getDestination(m_destinationRef);
        if (dest != null) {
            Channel chan = dest.getChannel();
            DestinationConfig config = dest.getConfig();

            /**
             * Important - Create a new destination (Clone semantics) from the exisiting destination and its config
             */
            dest = chan.createDestination(config);
            return dest;
        } else {
            return null;
        }
    }


    public void deactivate() {
        try {

            System.out.println("DeActivating destination...");
            Channel.Destination dest = m_destination;
            dest.suspend();
            m_isActive = false;
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }




    public boolean isActive() {
        return m_isActive;
    }


    public SmElement getOutputClass() {
        MutableType outputT = MutableSupport.createType(m_schema,TYPE_NAME_OUTPUT);
        //todo: add support for byteEncoding?
//        MutableSupport.addOptionalLocalElement(outputT, BEPluginConstants.BYTE_ENCODING.getLocalName(), XSDL.STRING);
        SmElement inputType = MutableSupport.createElement(m_schema,TYPE_NAME_OUTPUT,outputT);
        XiNode configNode= this.getConfigParms();
        final String rspRef = XiChild.getString(configNode, BEPluginConstants.RSPREF);
        try {
            final RspConfigValues rspConfigValues = BEBWUtils.getRspConfigValues(this.configParms, this.repoAgent);
            final String[] nsNm = BEBWUtils.getNsNm(this.eventRef, rspRef, this.repoAgent);
            final SmElement eventType = BEBWUtils.getElement(rspConfigValues.getRepoUrl(), this.repoAgent,
                    nsNm[0], nsNm[1]);
            if (eventType != null) {
                MutableSupport.addOptionalElement(outputT, eventType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputType;
    }


    public void destroy(){
        if(m_beClient != null) {
            if (m_destination != null) {
                try {
                    m_destination.suspend();
                    m_destination.getChannel().close();
                    m_destination.close();
                }
                catch (Exception e) { //eat the exception }
                }
                m_beClient.destroy();
                m_beClient = null;
            }
        }

    }//class
}

    class BEEventContext extends EventContext {

        protected SimpleEvent m_event;
        protected XiNode m_eventNode;
        protected PluginException m_exception;

        public BEEventContext (SimpleEvent event, XiNode eventNode) {
            this.m_event=event;
            this.m_eventNode=eventNode;
        }

        public BEEventContext (PluginException pluginException) {
            m_exception = pluginException;
        }

        public void confirm() throws ActivityException {
            checkForException();
            if (m_event != null) {
                try {
                    if (null != m_event.getContext()) {
                        m_event.getContext().acknowledge();
                    }
                    m_event=null;
                } catch (Exception e) {
                    throw new ActivityException(e.getMessage());
                }
            }
            super.confirm();
        }

        public void setException(PluginException pluginException) {
            m_exception = pluginException;
        }

        public void checkForException() throws ActivityException {
            if (null != m_exception) {
                final PluginException pluginException = m_exception;
                m_exception = null;
                throw pluginException;
            }//if
        }//checkForException

        public XiNode postProcess(XiNode xiNode) throws ActivityException {
            checkForException();
            return m_eventNode;
        }//postProcess
    }





