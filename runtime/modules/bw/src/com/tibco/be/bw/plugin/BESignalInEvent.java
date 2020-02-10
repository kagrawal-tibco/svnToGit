package com.tibco.be.bw.plugin;

import com.tibco.bw.store.RepoAgent;
import com.tibco.pe.core.SignalInActivity;
import com.tibco.pe.plugin.ActivityContext;
import com.tibco.pe.plugin.ActivityException;
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

/*
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Aug 14, 2004
 * Time: 12:50:00 PM
 * To change this template use File | Settings | File Templates.
 */

public class BESignalInEvent extends SignalInActivity {

    public static final String TYPE_NAME_CONFIG	= "BESignalInEventConfig";
    public static final String TYPE_NAME_OUTPUT	= "BEReceiveEventOutput";

    private static final SmElement m_configType;

    private static MutableSchema m_schema;

    static {
        m_schema = SmFactory.newInstance().createMutableSchema();
        m_schema.setNamespace(BEPluginConstants.NAMESPACE);

        MutableType configT = MutableSupport.createType(m_schema,TYPE_NAME_CONFIG);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.EVENTREF.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENTITYNS.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENTITYNAME.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.ENCODING.getLocalName(), XSDL.STRING);
        MutableSupport.addRequiredLocalElement(configT, BEPluginConstants.IS_TEXT.getLocalName(), XSDL.BOOLEAN);

        m_configType = MutableSupport.createElement(m_schema,TYPE_NAME_CONFIG,configT);
    }//static


    private BEReceiveEvent m_eventSource = new BEReceiveEvent(true);
    protected String eventRef;


    public BESignalInEvent () {
        super();
        setEventSource(m_eventSource);
    }//constr



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
        //todo: Probably something similar to BEReceiveEvent.initializeClient()
    }


    public SmElement getOutputClass() {
        final MutableType outputType = MutableSupport.createType(m_schema,TYPE_NAME_OUTPUT);
//      todo: add support for byteEncoding?
//      MutableSupport.addOptionalLocalElement(outputType, BEPluginConstants.BYTE_ENCODING.getLocalName(), XSDL.STRING);
        final SmElement outputClass = MutableSupport.createElement(m_schema, TYPE_NAME_OUTPUT, outputType);
        final XiNode configNode = this.getConfigParms();
        final String rspRef = XiChild.getString(configNode, BEPluginConstants.RSPREF);
        try {
            final RspConfigValues rspConfigValues = BEBWUtils.getRspConfigValues(this.configParms, this.repoAgent);
            if (null != rspConfigValues) {
                final String[] nsNm = BEBWUtils.getNsNm(this.eventRef, rspRef, this.repoAgent);
                final SmElement eventType = BEBWUtils.getElement(rspConfigValues.getRepoUrl(), this.repoAgent,
                        nsNm[0], nsNm[1]);
                if (null != eventType) {
                    MutableSupport.addOptionalElement(outputType, eventType);
                }//if
            }
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
        return outputClass;
    }//getOutputClass


    public void destroy(){
        if(m_eventSource != null) {
            m_eventSource.destroy();
            m_eventSource = null;
        }
    }


    public SmElement getConfigClass() {
        return m_configType;
    }

}//class






