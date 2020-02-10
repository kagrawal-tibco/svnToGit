/**
 * 
 */
package com.tibco.be.util.config.topology;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.xml.datamodel.XiNode;

import java.util.ArrayList;


/**
 * @author Nick
 *
 */
public class BemmPU {
    private String duName;   //The duName is to guarantee a unique id for this PU. This unique id is refered to
                             //as DuNameQualifiedId from hereon. It is used to differentiate the PUs with the
                             //same name, but from different DU's, that are deployed to this host.
	private String id;                    //The id (PuStId) attribute of the PU element in the st file. Do not confuse with puid
	private String puid;
	private String jmxPort;
	private String isUseAsEngineName;
	private ArrayList<BemmAgent> agentList;

	public BemmPU(String cddPath, XiNode puXiNode, String duName, Logger logger) throws Exception {
        this.duName = duName;
		parseAndCreateObjs(cddPath, puXiNode);
	}

	private void parseAndCreateObjs(String cddPath, XiNode puXiNode) throws Exception {
		id = puXiNode.getAttributeStringValue(TopologyNS.Attributes.ID).trim();
		puid = puXiNode.getAttributeStringValue(TopologyNS.Attributes.PUID).trim();
		isUseAsEngineName = puXiNode.getAttributeStringValue(TopologyNS.Attributes.USE_AS_ENGINE_NAME).trim();

        if(isUseAsEngineName == null || isUseAsEngineName.isEmpty())
			isUseAsEngineName = "false";

        jmxPort = puXiNode.getAttributeStringValue(TopologyNS.Attributes.JMX_PORT).trim();
        agentList = CddConfigUtil.getInstance(cddPath).getAgentList(puid);
	}

	public String getId() {
		return id;
	}

    /**
     * Returns this PU Id preceded (qualified) by it's parent DU name. The token is the char '~'
     * Example: DU_NAME~PU_ID
     * This qualified Id must be used to allow multiple DU's deployment
     * @return String with the pattern DU_NAME~PU_ID */
    public String getDuNameQualifiedId() {
		return duName+"~"+id;
	}

	public String getPuid() {
		return puid;
	}

	public ArrayList<BemmAgent> getAgentList() {
		return agentList;
	}

	public String getJmxPort() {
		return jmxPort;
	}

	public boolean getUseAsEngineName() {
		return isUseAsEngineName.equalsIgnoreCase("true");
	}
}
