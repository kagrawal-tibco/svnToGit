package com.tibco.be.bw.plugin;

import com.tibco.bw.store.RepoAgent;
import com.tibco.objectrepo.object.ObjectProvider;
import com.tibco.pe.plugin.GlobalVariablesUtils;
import com.tibco.pe.plugin.ProcessDefinitionContext;
import com.tibco.pe.plugin.SharedConfiguration;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 14, 2007
 * Time: 3:23:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class BERuleServiceProviderConfiguration extends SharedConfiguration  {

    /**
     * Returns the element for this type of shared configuration.
     */

    private static final SmSchema SCHEMA;

    public static final SmElement SHARED;


    //@code-review: as per extensibility, you should not build schemas programatically. Define your schemas in an .xml file and parse
    // see the bw tutorial examples.
    static {
        MutableSchema schema;
        schema = SmFactory.newInstance().createMutableSchema();
        schema.setNamespace(BEPluginConstants.NAMESPACE + "/ruleserviceprovider");

        MutableType config = MutableSupport.createType(schema, BEPluginConstants.RSP_TYPE_NAME);
        SHARED = MutableSupport.createElement(schema, BEPluginConstants.RSP_ELEMENT_NAME, config);
        MutableSupport.addRequiredLocalElement(config, BEPluginConstants.RSP_ELEMENT_ENGINE_NAME, XSDL.STRING);
        MutableSupport.addRequiredLocalElement(config, BEPluginConstants.RSP_ELEMENT_START_FLAG, XSDL.BOOLEAN);
        MutableSupport.addRequiredLocalElement(config, BEPluginConstants.RSP_ELEMENT_REPOURL, XSDL.STRING);
        MutableSupport.addRequiredLocalElement(config, BEPluginConstants.RSP_ELEMENT_CDD, XSDL.STRING);
        MutableSupport.addRequiredLocalElement(config, BEPluginConstants.RSP_ELEMENT_PUID, XSDL.STRING);
        //MutableSupport.addRequiredLocalElement(config, BEPluginConstants.RSP_ELEMENT_TRAPATH, XSDL.STRING);

        schema.lock();
        SCHEMA = schema;
    }


    public SmElement getConfigClass() {
        return SHARED;
    }


    @Override
    public XiNode getConfigParms() {
        System.out.println("getConfigParms");
        return super.getConfigParms();
    }


    @Override
    public void setConfigParms(XiNode xiNode, RepoAgent repoAgent) throws Exception {
        System.out.println("setConfigParms2");
        super.setConfigParms(xiNode, repoAgent);
    }


    @Override
    public void setConfigParms(XiNode configNode, RepoAgent repoAgent, ProcessDefinitionContext pdc) throws Exception {
        System.out.println("setConfigParms3");
        super.setConfigParms(configNode, repoAgent, pdc);

        final ObjectProvider provider = repoAgent.getObjectProvider();

        final XiNode node = GlobalVariablesUtils.resolveGlobalVariables(configNode, provider,
                provider.getProjectId(repoAgent.getVFileFactory()));

        final String repoUrl = XiChild.getString(node, BEPluginConstants.XNAME_REPOURL);
        final String cddUrl = XiChild.getString(node, BEPluginConstants.XNAME_CDD);
        final String puid = XiChild.getString(node, BEPluginConstants.XNAME_PUID);
    }


}
