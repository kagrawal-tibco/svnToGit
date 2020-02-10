package com.tibco.be.bw.plugin;


import com.tibco.be.bw.XiEvent;
import com.tibco.bw.store.RepoAgent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.pe.plugin.Activity;
import com.tibco.pe.plugin.ActivityContext;
import com.tibco.pe.plugin.ActivityException;
import com.tibco.pe.plugin.EngineContext;
import com.tibco.pe.plugin.ProcessContext;
import com.tibco.pe.plugin.ProcessDefinitionContext;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 30, 2004
 * Time: 11:02:10 PM
 * To change this template use File | Settings | File Templates.
 */

public class BESendEvent extends Activity {


    public static final String TYPE_NAME_CONFIG	= "BESendEventConfig";
    public static final String TYPE_NAME_INPUT	= "BESendEventInput";
    public static final String TYPE_NAME_OUTPUT	= "BESendEventOutput";

    private static final SmElement configType, outputType;
    private static MutableSchema schema;

    static {
        schema = SmFactory.newInstance().createMutableSchema();
        schema.setNamespace(BEPluginConstants.NAMESPACE);

        MutableType configT = MutableSupport.createType(schema,TYPE_NAME_CONFIG);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.EVENTREF.getLocalName(), XSDL.STRING);
        MutableSupport.addOptionalLocalElement(configT, BEPluginConstants.DESTINATIONREF.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENTITYNS.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENTITYNAME.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENCODING.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.IS_TEXT.getLocalName(), XSDL.BOOLEAN);

        configType = MutableSupport.createElement(schema,TYPE_NAME_CONFIG,configT);

        // Output Type
        MutableType outputT = MutableSupport.createType(schema, TYPE_NAME_OUTPUT);
        MutableSupport.addOptionalLocalElement(outputT, "returnCode", XSDL.INTEGER);
        MutableSupport.addOptionalLocalElement(outputT, "returnMsg", XSDL.STRING);

        outputType=MutableSupport.createElement(schema, TYPE_NAME_OUTPUT, outputT);
    }


    // Variables that hold the connection to BusinessEvents
    protected BEClient m_beClient;
    protected String m_destinationRef;
    protected String eventRef;
    protected Logger logger;


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

    public void init(ActivityContext context) throws ActivityException {

        super.init(context);
        try {


            initializeClient(context.getEngineContext());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivityException(e.getMessage());
        }
    }

    void initializeClient(EngineContext context) throws Exception{
        try {
            String s1 = XiChild.getString(configParms, BEPluginConstants.EVENTREF);
            int idx = s1.lastIndexOf('.');
            this.eventRef = (idx < 0 ) ? s1 : s1.substring(0, idx);
            m_destinationRef = XiChild.getString(configParms, BEPluginConstants.DESTINATIONREF);

            final RspConfigValues rspConfigValues = BEBWUtils.getRspConfigValues(this.configParms, this.repoAgent);
            this.m_beClient = new BEClient(context, rspConfigValues);
            this.logger = m_beClient.getRuleServiceProvider().getLogger(this.getClass());
            if (m_destinationRef == null || m_destinationRef.length() == 0) {
                m_destinationRef = m_beClient.getEventDefaultDestinationURI(eventRef);
            }

            if(m_destinationRef == null || m_destinationRef.length() == 0) {
                throw new Exception("Both Custom Destination and the Default Destination of Event are null");
            }

            int idx1 = m_destinationRef.lastIndexOf(".channel");
            if (idx1 > 0) {
                StringBuffer sb = new StringBuffer (m_destinationRef);
                m_destinationRef = sb.delete(idx1, idx1 + 8).toString();
            }

            //m_eventSender = m_session.createEventSender(m_eventClass, m_destinationRef);
            //checkDestination();
        } catch (Exception e) {
            if (m_beClient != null) {
                m_beClient.destroy();
                m_beClient=null;
                this.logger = null;
            }
            e.printStackTrace();
            throw e;
        }
    }



    public XiNode eval(ProcessContext pc, XiNode inputData) throws ActivityException {
        try {
            inputData=inputData.getFirstChild();
            XiNode eventData= XiChild.getFirstChild(inputData);
            XiEvent event = (XiEvent)m_beClient.getRuleServiceProvider().getTypeManager().createEntity(eventRef);
            event.setXiNode(eventData);

            Channel.Destination dest = m_beClient.getDestination(m_destinationRef);

            if (dest == null) {
                String msg = "Invalid or Null Destination [" + m_destinationRef + "] specified";
                this.logger.log(Level.ERROR, msg);
                throw new ActivityException(msg);
            }

            dest.getChannel().connect();
            dest.send(event, null);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivityException(e.getMessage());
        }
        return null;
    }




    public XiNode postEval(ProcessContext p, XiNode inputData, Object reply) {
        // should not get here.  Expecting cancelled method to be called when sleep time is over
        return null;
    }

    public SmElement getOutputClass() {
        //return outputType;
        return null;
    }

    
    public SmElement getInputClass() {
        MutableType inputT = MutableSupport.createType(schema,TYPE_NAME_INPUT);
        //todo: add support for byteEncoding?
//        MutableSupport.addOptionalLocalElement(inputT, BEPluginConstants.BYTE_ENCODING.getLocalName(), XSDL.STRING);
        SmElement inputType = MutableSupport.createElement(schema,TYPE_NAME_INPUT,inputT);

        XiNode configNode= this.getConfigParms();
        final String rspRef = XiChild.getString(configNode, BEPluginConstants.RSPREF);
        try {
            final RspConfigValues rspConfigValues = BEBWUtils.getRspConfigValues(this.configParms, this.repoAgent);
            if (null != rspConfigValues) {
                final String[] nsNm = BEBWUtils.getNsNm(this.eventRef, rspRef, this.repoAgent);
                final SmElement eventType = BEBWUtils.getElement(rspConfigValues.getRepoUrl(), this.repoAgent,
                        nsNm[0], nsNm[1]);
                if (eventType != null) {
                    MutableSupport.addOptionalElement(inputT, eventType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputType;
    }


    public void destroy() throws java.lang.Exception {
        if(m_beClient != null) {
            m_beClient.destroy();
            m_beClient = null;
            this.logger = null;
        }
    }

    public SmElement getConfigClass() {
        return configType;
    }

}




