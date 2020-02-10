package com.tibco.be.util.config.topology;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 9/15/11
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MMGlobalVarsNS {
    String URL = "http://tibco.com/businessevents/configuration/5.6";

    interface Elements {
        ExpandedName MACHINE = ExpandedName.makeName("Machine");
        ExpandedName GVS = ExpandedName.makeName("GlobalVariables");
        ExpandedName GV= ExpandedName.makeName("GlobalVariable");

    }

    interface Attributes {
        ExpandedName MFQN = ExpandedName.makeName("mfqn");
        ExpandedName DUN = ExpandedName.makeName("dun");
        ExpandedName NAME= ExpandedName.makeName("name");
        ExpandedName CURR_VALUE= ExpandedName.makeName("currentValue");
        ExpandedName DEF_VALUE= ExpandedName.makeName("defaultValue");
        ExpandedName VALUE= ExpandedName.makeName("value");     //to be used when writing values to the deployed cdd file
    }

    interface Table {
        interface SaveGVColNames {
            String DU_NAME = "Deployment Unit Name";
            String DEPLOY_STATUS = "Status";
            String LOCAL_FILE_LOC= "Local File Location";
            String INFO = "Info";
        }
    }
}
