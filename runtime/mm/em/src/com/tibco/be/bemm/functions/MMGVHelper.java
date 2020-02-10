/**
 * 
 */
package com.tibco.be.bemm.functions;

import java.io.StringReader;
import java.net.URLDecoder;
import java.util.Iterator;

import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.be.util.config.topology.BemmMappedHost;
import com.tibco.be.util.config.topology.MMGlobalVarsNS;
import com.tibco.be.util.config.topology.MMIoNS;
import com.tibco.be.util.config.topology.MMIoTableXml;
import com.tibco.be.util.config.topology.MMIoXml;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.exception.BEMMException;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * @author Nick Xu
 *
 */
public class MMGVHelper {
	
	private static MMGVHelper instance;
	protected static final XiParser PARSER = XiParserFactory.newInstance();
	private Logger logger;

	public MMGVHelper(){
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        logger = currRuleServiceProvider.getLogger(this.getClass());
	}

	static synchronized MMGVHelper getInstance() {
        if (instance == null){
            instance = new MMGVHelper();
        }
        return instance;
    }

    /** @param dusStIdsTokenStr Tokenized string with the site topology file ids of the
     * deployment units selected (in the UI) to be deployed. Token is # */
	public String getDeploymentGV(String machine, String dusStIdsTokenStr) {
        try {
            dusStIdsTokenStr = URLDecoder.decode(dusStIdsTokenStr, "UTF-8");
            BemmMappedHost bm = TopologyLoader.getInstance().getMappedHost(machine);

            if(bm != null) {
                XiNode gvsXml= XiSupport.getXiFactory().createElement(MMGlobalVarsNS.Elements.MACHINE);
                gvsXml.setAttributeStringValue(MMGlobalVarsNS.Attributes.MFQN, machine);

                String[] duStIds = dusStIdsTokenStr.split("#");

                for (String duId : duStIds) {
                    gvsXml.appendChild(bm.getDeployUnitFromId(duId).readGVs());
                }
                return XiSerializer.serialize(gvsXml);
            }
            else
                return "Invalid Machine Name.";
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while trying to read global variables.",e);
        }
    }

    //New value of the GVs for all the DU's selected for deployment is sent by the UI
    public String setDeploymentGV(String machineFqPath, String gvsXml){    //TODO: GOOD Exception handling  AND Encode dusXML
        if (gvsXml == null || gvsXml.trim().isEmpty()) {
            getInstance().logger.log(Level.INFO,"No changes in Global Variables values detected hence Save operation not executed");
            return MMIoXml.infoXML("Save GVs", "No changes in Global Variables values detected hence Save operation not executed");
        }

        BemmMappedHost bm = null;           //RETURN XML
        try {
            bm = TopologyLoader.getInstance().getMappedHost(machineFqPath);
        } catch (BEMMException e) {
            bm=null;    //it's handled below
        }

        if(bm != null) {
            try {
                XiNode gvsXmlNode= PARSER.parse(new InputSource(new StringReader(gvsXml)));
                XiNode machineElem = XiChild.getChild(gvsXmlNode, MMGlobalVarsNS.Elements.MACHINE);
                MMIoTableXml statusTable = new MMIoTableXml();

                final String[] colsNames = {
                    MMIoNS.Table.SaveGvsColNames.DU_NAME,
                    MMIoNS.Table.SaveGvsColNames.STATUS,
                    MMIoNS.Table.SaveGvsColNames.LOCAL_FILE_PATH,
                    MMIoNS.Table.SaveGvsColNames.INFO};

                for (Iterator iter = XiChild.getIterator(machineElem, MMGlobalVarsNS.Elements.GVS); iter.hasNext();) {
                    XiNode gvsElem = (XiNode) iter.next();
                    final String duName = gvsElem.getAttributeStringValue(MMGlobalVarsNS.Attributes.DUN);
                    String[] colsValues = bm.getDeployUnit(duName).saveGVs(gvsElem);

                    MMIoTableXml.Row row = statusTable.addRow();
                    row.addColumns(colsNames,colsValues);
                }
                return statusTable.serialize("Save Global Variables");
            } catch (Exception e) {
                getInstance().logger.log(Level.ERROR, e, "Exception occurred while trying to save global variables");
                throw new RuntimeException("Exception occurred while trying to save global variables.",e);
            }
        } else {
            getInstance().logger.log(Level.ERROR, "Invalid Machine Name: %s. Global Variables NOT saved.", machineFqPath);
			return MMIoXml.warningXML("Save Global Variables",
                    String.format("Invalid Machine Name: %s. Global Variables NOT saved.", machineFqPath));
        }
	}
	
}
